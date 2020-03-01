package dmdd.dmddjava.service.replenish;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.util.DBUtil;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import dmdd.dmddjava.common.enums.SafeStockMode;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.ReplenishUtils;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.bizobject.BReplenishTermEnd;
import dmdd.dmddjava.dataaccess.utils.ReplenishContext;
import dmdd.dmddjava.service.replenish.termend.CalculateInventoryFactory;

/**
 * 计算生成补货计算数据
 * 
 * @author jerry
 * @date Aug 7, 2013
 */
public class ReplenishComputeService {

	private Logger logger = CoolLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();

	private int curPeriod = ServerEnvironment.getInstance().getSysPeriod()
			.getForecastRunPeriod(); // 获取当前系统期间
	private int endPeriod;
	private int periodLength = 12; // TODO 期间长度
	private int daysPerPeriod = 30; // TODO 每个期间天数
	private int avgPeriodNum = 3; // TODO 计算库存时，采用的基准期间数

	private int defaultStockDay = 30; // TODO 默认库存天数
	private int detailDCLayer = 3;// TODO 配置文件的环境变量中

	// 预测类型
	private int forecaseType = 23;

	public ReplenishComputeService() throws Exception {
		endPeriod = UtilPeriod.getPeriod(curPeriod, periodLength - 1);

		detailDCLayer = UtilReplenish.getMaxDcLayer();
	}

	/**
	 * 补货数据计算 SafeStockType:(1:后3月平均 2:逐月递减)
	 * 
	 * @throws Exception
	 */
	public String replenishCompute(String operator, SafeStockMode type,
			int averegeMonth) throws Exception {
		// -1 删除之前版本数据

		// 0.1 库存天数、历史期末数据已经导入到系统中
		Date startTime = new Date();
		long start = System.currentTimeMillis();
		String msg = "";
		logger.info("补货计划数据重新计算开始... ");
		try {

			// 删除没有销售组织的分仓中心数据
			deleteDCDataNotSellOut();

			// 根据分仓层次，自下而上进行分层统计计算
			int dclayer = detailDCLayer;
			ReplenishContext context = new ReplenishContext();
			context.setForecaseType(forecaseType);
			context.setType(type);
			context.setAveregeMonth(averegeMonth);
			//按月递减取每个月的安全库存天数
			Map<String,Long> stockDayMap = queryStockDayMap(type);
			
			for (; dclayer > 0; dclayer--) {
				// 1,计算SellOut数据
				refreshDcSellOutData(context,stockDayMap,dclayer);
				// 2,计算安全库存
				refreshTermEndData(context, dclayer);
				// 3,刷新期初库存
				refreshDCTermBeginData(dclayer);
				// 4,计算补货数据
				refreshDCSellInData(dclayer);

				logger.info("DC的第[" + dclayer + "]层补货计划数据更新完成！");
			}
			long cur = System.currentTimeMillis();

			msg = "补货计划数据计算成功，区间范围[" + curPeriod + "]->[" + endPeriod + "],耗时["
					+ ((cur - start) / 1000) + " s]。";
			logRefresh(operator, type, startTime, "成功", msg);
			logger.info(msg);
		} catch (Exception e) {
			msg = "补货计划数据计算异常!";
			logRefresh(operator, type, startTime, "失败", msg + e.toString());
			logger.error("补货计划数据计算异常，", e);
			throw e;
		} finally {
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}

		return msg;
	}

	//按月递减需要用库存天数
	private Map<String,Long> queryStockDayMap(SafeStockMode type) throws Exception{
		Map<String,Long> stockDayMap= new HashMap<String,Long>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("select repl.DCID,repl.PRODUCTID,repl.PERIOD,repl.STOCKDAY from REPLENISHDATA repl");
		sb.append(" where repl.PERIOD >=? and repl.PERIOD <= ?");
		
		HashVO[] rdcVos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT,sb.toString(),curPeriod,endPeriod);
		if (rdcVos != null && rdcVos.length >0) {
			for (int i = 0; i < rdcVos.length; i++) {
				HashVO rdcvo = rdcVos[i];
				Long productid = rdcvo.getLongValue("PRODUCTID");
				Long dcid = rdcvo.getLongValue("DCID");
				int period = rdcvo.getIntegerValue("PERIOD");
				Long stockDay = rdcvo.getLongValue("STOCKDAY");
				stockDayMap.put(convertPeriodKey(productid,dcid,period), stockDay);
			}
		}
		return stockDayMap;
	}
	
	/**
	 * 删除不存在SellOut的补货数据
	 * 
	 * @throws Exception
	 */
	private void deleteDCDataNotSellOut() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("with sub_table as (select * from distributioncenter where PARENTDCID='0'");
		sb.append("union all select d.* from distributioncenter d  ");
		sb.append("inner join sub_table t on ( d.PARENTDCID = t.id ) )");
		sb.append("delete t from replenishdata as t " + " where t.period>="
				+ curPeriod + " and t.period <= " + endPeriod);
		sb.append("and t.dcid not in (select id from sub_table)");

		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString());
		dmo.commit(DMOConst.DS_DEFAULT);

		logger.debug("无效的DC补货数据删除成功！");
	}

	/**
	 * 记录补货数据计算日志
	 * 
	 * @param operator
	 * @param startDate
	 * @param result
	 * @param resultDetail
	 * @throws Exception
	 */
	private void logRefresh(String operator, SafeStockMode type,
			Date startTime, String result, String resultDetail)
			throws Exception {
		Date endTime = new Date();
		StringBuffer insertSb = new StringBuffer();
		insertSb.append("INSERT INTO REPLENISHCOMPUTELOG("
				+ DBUtil.getInsertId()
				+ " VERSION,CURRENTPERIOD,TERMENDTYPE,TERMENDNAME,OPERATOR,BEGINTIME,ENDTIME,RESULT,DETAILRESULT) ");
		insertSb.append(" VALUES("
				+ DBUtil.getSeqValue("S_REPLENISHCOMPUTELOG")
				+ " 0,?,?,?,?,?,?,?,?)");
		try {
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSb.toString(),
					curPeriod, type.getType(), type.getDesc(), operator,
					startTime, endTime, result, resultDetail);
			dmo.commit(DMOConst.DS_DEFAULT);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	// 计算期末库存
	public void refreshTermEndData(ReplenishContext context,int dclayer) throws Exception {
		//calculate
		CalculateInventoryFactory.getInstance().doCalculateInventory(context);
		Multimap<String, BReplenishTermEnd> termEndMaps = context.getTermEndMap();
		List<String> listKeys = Lists.newArrayList(termEndMaps.keySet());
		int num = 0;
		for (String key : listKeys) {
			List<BReplenishTermEnd> termEndList = (List<BReplenishTermEnd>) termEndMaps.get(key);
			for (BReplenishTermEnd termEnd:termEndList) {
				
				StringBuilder uptTermEndSb = new StringBuilder();
				uptTermEndSb.append("update repl set repl.COMPUTETIME=GETDATE(),repl.TERMEND=? ");
				//uptTermEndSb.append("dbo.gettermend(repl.PERIOD,repl.STOCKDAY,30,repl.PRODUCTID,repl.DCID)");
				uptTermEndSb.append(" from REPLENISHDATA repl");
				uptTermEndSb.append(" where repl.PRODUCTID=? and repl.DCID=? and repl.PERIOD=? and EXISTS ");
				uptTermEndSb.append("(SELECT 1 FROM DISTRIBUTIONCENTER DC WHERE DC.ID=repl.DCID AND DC.DCLAYER=?)");
				
				dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, uptTermEndSb.toString(),
						termEnd.getTermEnd(),termEnd.getProductId(),termEnd.getDcid(),
						termEnd.getPeriod(), dclayer);
				
				num++;
				// 2000条提交一次
				if (num % 2000 == 0) {
					dmo.commit(DMOConst.DS_DEFAULT);
				}
			}
		}
		dmo.commit(DMOConst.DS_DEFAULT);

		logger.info("根据SellOut数据刷新[" + curPeriod + "]-[" + endPeriod
				+ "]期末库存数据成功!");
	}

	/**
	 * 刷新期初数据
	 */
	public void refreshDCTermBeginData(int dclayer) throws Exception {
		// 1, 当前期的期初数据由历史数据(上一期期末)刷新而来，在历史数据导入中已经刷新，这里只刷新下一期及以后的数据
		//
		StringBuilder uptSb = new StringBuilder();
		uptSb.append("update repl set repl.COMPUTETIME=GETDATE(),");
		uptSb.append("repl.TERMBEGIN=(select top 1 ISNULL(sum(RD.TERMEND),0) from REPLENISHDATA RD ");
		uptSb.append("where RD.DCID=repl.DCID AND RD.PRODUCTID=repl.PRODUCTID AND RD.PERIOD=? )");
		uptSb.append(" from REPLENISHDATA repl ");
		uptSb.append(" where repl.PERIOD=? AND EXISTS");
		uptSb.append("( SELECT 1 FROM DISTRIBUTIONCENTER DC WHERE DC.ID=repl.DCID AND DC.DCLAYER=?)");

		int tmpPeriod = UtilPeriod.getPeriod(curPeriod, 1);
		// 从当前期之后，一期一期进行刷新期初库存数据
		while (tmpPeriod <= endPeriod) {
			int periodEnd = ReplenishUtils.getMonth(tmpPeriod, -1);
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, uptSb.toString(),
					periodEnd, tmpPeriod, dclayer);

			tmpPeriod = UtilPeriod.getPeriod(tmpPeriod, 1);
		}
		dmo.commit(DMOConst.DS_DEFAULT);

		logger.info("根据上期期末数据刷新[" + curPeriod + "]-[" + endPeriod
				+ "]期初库存数据成功!");

	}

	// 计算SELL-IN 数据
	public void refreshDCSellInData(int dclayer) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("update repl set repl.COMPUTETIME=GETDATE(),");
		sb.append("repl.SellIn = (repl.TERMEND+repl.SELLOUT-repl.TERMBEGIN)");
		sb.append("from REPLENISHDATA repl WHERE repl.PERIOD>= " + curPeriod
				+ " AND ");
		sb.append("EXISTS (SELECT 1 FROM DISTRIBUTIONCENTER DC WHERE DC.ID=repl.DCID AND DC.DCLAYER="
				+ dclayer + ")");
		// 再加一个条件，必须是刷新明细DC数据

		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString());
		dmo.commit(DMOConst.DS_DEFAULT);

		logger.info("刷新明细DC的[" + curPeriod + "]期间及之后SellIn数据成功！");

	}

	// 计算每个上级DC 的发出数据
	public void refreshDcSellOutData(ReplenishContext context,Map<String,Long> stockDayMap,int dclayer)
			throws Exception {
		int type = context.getForecaseType();
		// 统计上级DC的sellout数据
		StringBuilder sb = new StringBuilder();

		if (dclayer == detailDCLayer) {
			// 明细级DC的发出数据，由销售预测数据导入
			sb.append(" SELECT r.DCID,r.PID PRODUCTID,r.PERIOD,SUM(r.VALUE) V_SELLOUT FROM (");
			sb.append(" 	SELECT P.ID PID,DC.ID DCID,FD.VALUE,FD.PERIOD ");
			sb.append(" 	FROM FORECASTDATA FD,PRODUCT P,ORGANIZATION O,DISTRIBUTIONCENTER DC");
			sb.append(" 	WHERE FD.BIZDATAID=(select top 1 id from bizdata d where D.TYPE="
					+ type + ") ");// 查询最终预测数据
			sb.append("     AND FD.PERIOD>=" + curPeriod + " AND FD.PERIOD<="
					+ endPeriod);
			sb.append(" 	AND P.ID=FD.PRODUCTID AND O.ID=FD.ORGANIZATIONID AND DC.ID=O.DISTRIBUTIONCENTERID ");
			sb.append("     AND DC.ISCATALOG=0 ");
			sb.append(")r GROUP BY r.PID,r.DCID,r.PERIOD");
		} else {
			// // 查询下级DC的SELL IN总和,即为上级的发货
			sb.append("SELECT DC.ID DCID,R.PRODUCTID,R.PERIOD,");
			sb.append("(SELECT SUM(SELLIN) FROM REPLENISHDATA R1,DISTRIBUTIONCENTER D1 ");
			sb.append("  WHERE D1.ID=R1.DCID AND D1.PARENTDCID=DC.ID AND R1.PRODUCTID=R.PRODUCTID  AND R1.PERIOD=R.PERIOD ) V_SELLOUT ");
			sb.append(" FROM REPLENISHDATA R,DISTRIBUTIONCENTER DC ");
			sb.append(" WHERE DC.ID=R.DCID AND DC.DCLAYER= " + (dclayer));
			sb.append("  AND R.PERIOD>=" + curPeriod + " AND R.PERIOD<="+endPeriod); // 加上区间限制
		}

		StringBuilder insertSb = new StringBuilder();
		insertSb.append("INSERT INTO REPLENISHDATA("
				+ DBUtil.getInsertId()
				+ " VERSION,PRODUCTID,DCID,PERIOD,SELLOUT,STOCKDAY,INITTIME,UPDATETIME) ");
		insertSb.append(" VALUES(" + DBUtil.getSeqValue("S_REPLENISHDATA")
				+ " 0,?,?,?,?,?," + DBUtil.getSysDate() + ","
				+ DBUtil.getSysDate() + ")");

		String updateSql = "UPDATE REPLENISHDATA SET COMPUTETIME=GETDATE(),SELLOUT=?  "
				+ " WHERE PRODUCTID=? AND DCID=? AND PERIOD=? ";

		int num = 0;

		HashVO[] rdcVos = dmo.getHashVoArrayByDSUnlimitRows( DMOConst.DS_DEFAULT,sb.toString());

		for (int i = 0; i < rdcVos.length; i++) {
			HashVO rdcvo = rdcVos[i];
			Long productid = rdcvo.getLongValue("PRODUCTID");
			Long dcid = rdcvo.getLongValue("DCID");
			int period = rdcvo.getIntegerValue("PERIOD");
			String sellout = rdcvo.getStringValue("V_SELLOUT");// 上级DC的SELLOUT
			
			//数据转换
			String periodKey = convertPeriodKey(productid, dcid, period);
			BReplenishTermEnd replenishTermEnd = new BReplenishTermEnd()
					.setProductId(productid).setDcid(dcid).setPeriod(period)
					.setPeriod(period).setSellout(new BigDecimal(sellout!=null?sellout:"0"))
					.setStockDay(stockDayMap.containsKey(periodKey)?stockDayMap.get(periodKey):defaultStockDay);
			context.getTermEndMap().put(convertGroupKey(productid, dcid),
					replenishTermEnd);
			
			// 即是下级DC的SELLIN的总和
			// 判断数据是否存在,如果更新的记录大于一条，则说明数据存在，否则插入一条数据
			int updatedRow = dmo.executeUpdateByDS(DMOConst.DS_DEFAULT,
					updateSql, sellout, productid, dcid, period);

			if (updatedRow < 1) {
				dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSb.toString(),
						productid, dcid, period, sellout, defaultStockDay);
			}
			num++;

			// 2000条提交一次
			if (num % 2000 == 0) {
				dmo.commit(DMOConst.DS_DEFAULT);
			}
		}

		logger.info("[" + dclayer + "]级DC预测发出数据计算完成，共[" + num + "]条[DC*SKU]数据！");
	}

	private String convertGroupKey(Long productid, Long dcid) {
		return new StringBuilder().append(productid).append("_").append(dcid)
				.toString();
	}
	
	private String convertPeriodKey(Long productid, Long dcid,int period) {
		return new StringBuilder().append(productid).append("_").append(dcid)
		          .append("_").append(period).toString();
	}

	/**
	 * 补货计划期间滚动
	 * 
	 * @throws Exception
	 */
	public void periodRoll() throws Exception {
		// 期间滚动中，只对最后一个期间进行处理即可
		// TODO
		/**
		 * 历史数据都是真实数据，不从计划表中导入 1， 期间滚动，设置当前期间为新的期间，如由201211-》201212 2，
		 * 从Forecast系统导入最新一期的SaleForecast数据， 原有的重复期间数据(如201301)是否要覆盖？生成新一期的Sell
		 * Out数据 3， 导入新一期的StockDay,数据 4，导入新一期的期末库存数据 5， 进行补货计划计算
		 * 
		 * 期间滚动中，需要做的是补货计划计算，由界面完成
		 */
		logger.info("期间流动中补货数据的相关处理...");
	}

	/**
	 * 生成长度为length的数组，每个元素的值为其位置
	 * 
	 * @param length
	 * @return
	 */
	public int[] getFromCols(int length) {
		int[] fromcols = new int[length];
		for (int i = 0; i < fromcols.length; i++)
			fromcols[i] = i + 1;
		return fromcols;
	}

}
