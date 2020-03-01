package dmdd.dmddjava.service.invreport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.enums.OrderType;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.OrderConstant;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.service.dimensionservice.BizDataService;

/**
 * 预测、库存总报表
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Oct 3, 2017 11:15:10 PM
 */
public class FcInventoryService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	private OverStockRiskDao riskDao = new OverStockRiskDao();
	private BizDataService bizDataService = new BizDataService();
	private StockRowDataQueryService rowDataQueryService = new StockRowDataQueryService();
	
	public Map<String,Object> queryFcInventoryReport(Map<String,Object> queryCond ) throws Exception{
		int period = new Integer(queryCond.get("period").toString());
		int achieveRate = new Integer(queryCond.get("achieveRate").toString());
		List<String> proCodeList = (List<String>)queryCond.get("proCodeArray");
		String bizDataCode = queryCond.get("bizDataCode").toString();
		int fcValidPeriodNum = new Integer(queryCond.get("fcValidPeriodNum").toString());
		String hisDataCode = queryCond.get("hisDataCode").toString();
		
		//boolean isActualPrice = new Boolean(queryCond.get("isActualPrice").toString()); //是否使用实际价格
		
		//查询所有产品
		List<FcInvRowDataVo> unsafeList = new ArrayList<FcInvRowDataVo>(proCodeList.size()) ;
		List<FcInvRowDataVo> rstList = Collections.synchronizedList(unsafeList); //转换成线程安全的List,可以支持多线程
		
		int i=0;
		for(String proCode : proCodeList){
			logger.info("开始查询[" +proCode+ "]的预测库存报表...");
			//获取单产品数据
			FcInvRowDataVo rowData = queryFcInventoryReportByProduct(proCode, period, achieveRate,
					        bizDataCode,fcValidPeriodNum,hisDataCode);
			
			rstList.add(rowData );
			i++;
		}
		
		Map<String,Object> rstMap = new HashMap<String, Object>();
		rstMap.put("dataList", rstList);
		
		return rstMap;
	}
	
	public FcInvRowDataVo queryFcInventoryReportByProduct(String proCode, int period, int achieveRate
			,String bizDataCode, int fcValidPeriodNum,String hisDataCode) throws Exception{
		FcInvRowDataVo rowData = new FcInvRowDataVo();
		BProduct product = ServerEnvironment.getInstance().getBProduct(proCode);
		
		int periodNum = 24;
		//查询24期历史数据
		ABUiRowData hisRowData = getHisDataRowData(product, period, periodNum,hisDataCode);
		for(int i=0;i<periodNum;i++){
			rowData.setPeriodHisValue( rowData.getMaxHisIndex()-i, getNonNullPeriodValue(hisRowData, periodNum-i) );
		}
		
		//计算历史平均值
		Double hisAvg3 = getHisAvg(hisRowData, 3, periodNum);
		rowData.setHisAvg3(hisAvg3);
		rowData.setHisAvg6(getHisAvg(hisRowData, 6, periodNum));
		rowData.setHisAvg9(getHisAvg(hisRowData, 9, periodNum));
		rowData.setHisAvg12(getHisAvg(hisRowData, 12, periodNum));
		
		//查询24期预测数据
		int fcPeriodNum = 36;
		ABUiRowData adjustBizDataRowData = getAdjustBizDataRowData(product, period, achieveRate, fcPeriodNum, bizDataCode);
		for(int i=0;i<fcPeriodNum;i++){
			rowData.setPeriodFcValue(i, getNonNullPeriodValue(adjustBizDataRowData, i) );
		}
		
		int periodValidEnd = UtilPeriod.getPeriod(period, fcValidPeriodNum+24);
		//计算用预测数据
		ABUiRowData calcRowData = createUiRowData("计算用", product, period, periodValidEnd);
		double value1 = getNonNullPeriodValue(adjustBizDataRowData, fcValidPeriodNum-3 );
		double value3 = getNonNullPeriodValue(adjustBizDataRowData, fcValidPeriodNum-2 );
		double value2 = getNonNullPeriodValue(adjustBizDataRowData, fcValidPeriodNum-1 );
		
		double avg3Predit = (value1 + value2 + value3)/ 3;//有效预测期内，最后3期的平均值
		for(int i=0;i<periodNum+ 36 ;i++ ){
			Double adjustPredit = getNonNullPeriodValue(adjustBizDataRowData, i); //销售最终预测
			if(i < fcValidPeriodNum){
				calcRowData.pubFun4setPeriodDataValue(i, adjustPredit);
			}else{
				calcRowData.pubFun4setPeriodDataValue(i, avg3Predit);
			}
		}
		
		//查询期初库存
		double curStockOff = riskDao.getCurStockOffShelf(product, period, period);//在库库存在当期过期数量
		double curStockValid = riskDao.getCurPeriodStockQuantityValid(product, period);//上期末有效库存
		
		//当期期末有效库存
		double curEndStockValid = curStockValid  - adjustBizDataRowData.pubFun4getPeriodDataValue(0); //调整不扣减当期过期
		// curStockDaysCycle=当月期末有效库存覆盖天数-基于循环
		BigDecimal curStockDaysCycle = computeCoverDaysMulti(calcRowData, 1, curEndStockValid);
		double curEndStockDaysCycleDouble = curStockDaysCycle.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//补上当月天数，30天
		
		int curStockValidTo = new Integer( monthEndAddDay(period+"01", (int)curEndStockDaysCycleDouble) ); //;UtilPeriod.getPeriod(period, );
		//期末库存可销售至YYYYMMDD, 当期末库存大于0时，才设置显示该值
		if(curEndStockDaysCycleDouble >=0 ){
			rowData.setCurStockValidTo(curStockValidTo);
		}
		
		
		//查询当期PO,PA(当期的所有PO,PA订单汇总)
		Double periodPoTotal = riskDao.getPeriodPoTotalData(product,period,OrderConstant.ORDER_RECEIVING_PERIOD,OrderType.PO.getCode());
		Double curStockAndPo = curStockValid + periodPoTotal; //期初+PO
	
		//获取最大有效期G
		Integer maxPeriod = getMaxOffPeriod(period, product.getId());
		
		//客户剩余最大效期,I=G（最新下架期）- 期初库存可销售至  2天日期相减，得到天数
		Integer custLeftMaxPeriod = daysDiff(maxPeriod+"01", curStockValidTo+"");//UtilPeriod.getPeriodDifference( curStockValidTo,maxPeriod );
		rowData.setCustLeftMaxPeriod(custLeftMaxPeriod);
		
		//计算过期库存,计算到未来64期
		computeOverStock(product, period, 64 , curStockValid, calcRowData, rowData);
		
		//curStockPoDaysCycle=含期初+PO期末有效库存天数-基于循环E
		
		//当月期末有效库存+从次月开始的预测有效期间内的所有PO汇总-从次月开始的预测有效期间内所有的过期库存
		double curEndPoOutOfStock = curEndStockValid + periodPoTotal - rowData.getOverStockTotal(); 
		BigDecimal curStockPoDaysCycle = computeCoverDaysMulti(calcRowData, 1,  curEndPoOutOfStock );
		double curStockPoDaysCycleDouble = curStockPoDaysCycle.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		//含期初+PO有效期至,H=(当期期间+E)
		int curStockPoValidTo = new Integer( monthEndAddDay(period+"01", (int)curStockPoDaysCycleDouble) );//UtilPeriod.getPeriod(period, (int)curStockPoDaysCycle);
		rowData.setCurStockPoValidTo(curStockPoValidTo);
		
		//计算过期库存覆盖天数 ,过期库存汇总/调整后历史的品均3个月
		double overStockDaysCycle = 0 ;
		if(hisAvg3 > 0){
			overStockDaysCycle = rowData.getOverStockTotal() / hisAvg3 *30;
		}
		rowData.setOverStockDaysCycle( getDouble2Decimal(overStockDaysCycle) );
		
		rowData.setProCode(product.getCode());
		rowData.setProName(product.getName());
		
		rowData.setCurStockValid(curStockValid);
		rowData.setCurStockDaysCycle(curEndStockDaysCycleDouble);
		
		rowData.setPeriodPoTotal(periodPoTotal);
		rowData.setCurStockAndPo(curStockAndPo);
		rowData.setCurStockPoDaysCycle(curStockPoDaysCycleDouble);
		
		rowData.setLastOffPeriod(maxPeriod);
		
		return rowData;
	}
	
	/**
	 * 计算过期 库存(不包括PR)
	 * @param product 产品
	 * @param period 当前期间
	 * @param periodNum 向后计算期间数
	 * @param curPeriodStockValid 有效库存期间
	 * @param adjustBizDataRowData 预测数据
	 * @throws Exception
	 */
	private void computeOverStock(BProduct product,int period,int periodNum,Double curPeriodStockValid
				,ABUiRowData adjustBizDataRowData,FcInvRowDataVo rowData) throws Exception{
		int periodBegin = period;
		int periodEnd = UtilPeriod.getPeriod(periodBegin, periodNum);
		
		//预计PO收货日期 		
		BBizData poBizData = new BBizData();
		poBizData.setName("PO交货期");
		//ABUiRowData poData =  riskDao.getPoPrUiRowData(product, periodBegin, periodEnd, poBizData, "RECEIVING_PERIOD","PO");
		
		//查询PO单下架期
		BBizData poOffBizData = new BBizData();
		poOffBizData.setName("PO下架期");
		ABUiRowData poOffData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, poOffBizData,OrderConstant.ORDER_OFFSHELF_PERIOD,OrderType.PO.getCode());
		
		//在库库存下架期
		BBizData invStockBizData = new BBizData();
		invStockBizData.setName("在库库存下架期");
		ABUiRowData invStockOffData =  riskDao.getInvStockOffUiRowData(period,product, periodBegin, periodEnd, invStockBizData);
		
		ABUiRowData overStockRowData = createUiRowData("过期库存", product, periodBegin, periodEnd);
		ABUiRowData periodOverStockRowData = createUiRowData("期间过期库存", product, periodBegin, periodEnd); //非真正过期
		ABUiRowData accSellAheadRowData = createUiRowData("累积可提前销售", product, periodBegin, periodEnd);
		
		double totalOverStock = 0;
		double totalOverStockMoney = 0; //过期库存总
		double preOutOfStock = 0; //前一期的缺货数量
		//double preOver = 0; //前一期的过期库存数量
		
		int accNum = 0; //累积为负的过期库存期间数，不超过产品的下架期
		int shelfNum = product.getShelfLife()-product.getWithdrawLeadTime();
		double accSellAhead = 0 ;//真实累积库存为负值，表示可以有快到期的库存提前在本期卖掉
		
		for(int i=0;i<periodNum;i++ ){
			//
			double invOff = getNonNullPeriodValue(invStockOffData, i);
			
			double sell = getNonNullPeriodValue(adjustBizDataRowData, i); ; //销售,考虑预测达成率
			
			double poOff = getNonNullPeriodValue(poOffData, i);
			
			//1，计算过期库存   over = 当期要下架(PO_off + PA_off + PR_off + INV_off )-当期销售*预测达成率 - 上期缺货.  和上期缺货没有关系？都缺货了，肯定补到上期已经卖掉了
			double periodOver = (invOff + poOff) - sell - preOutOfStock;
			double over = 0;
			
			if(periodOver > 0 && (periodOver + accSellAhead) > 0){ //当期过期，并且加上前期可售卖的部分后，还是过期。--真正的过期
				over = periodOver + accSellAhead;
				accSellAhead = 0; //已经过期的库存是要下架处理的，不参与下一期的计算
				accNum = 0;
			}else{
				over = 0; //无过期，显示值为0设置
				accSellAhead += periodOver; //提前可售卖部分做累加处理,不论本期是否有过期库存
				accNum++;
			}
			
			//累积了整个产品的保质期，不应该再算到可售卖中，因为产品肯定还没有生产出来
			/*if(accSellAhead < 0 && (accNum == shelfNum) ){
				double firstSellAhead = getNonNullPeriodValue(periodOverStockRowData, i-shelfNum);
				accSellAhead -= firstSellAhead;
				accNum --;
			}*/
			
			totalOverStock += over; //汇总过期库存
			
			if(over > 0){
				Double price = riskDao.getProductStdPrice(product, period,false); //每个期间的价格可能不一样，需要每次查询
				totalOverStockMoney += over* price ;
			}
			
			
			overStockRowData.pubFun4setPeriodDataValue(i, over);
			periodOverStockRowData.pubFun4setPeriodDataValue(i, periodOver);
			accSellAheadRowData.pubFun4setPeriodDataValue(i, accSellAhead);
			
		}
		
		rowData.setOverStockTotal(getDouble2Decimal(totalOverStock) );
		rowData.setOverStockTotalMoney( getDouble2Decimal(totalOverStockMoney) );
	}
	
	/**
	 * 查询最大下架期间
	 * @param period
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public Integer getMaxOffPeriod(int period, Long productId) throws Exception{
		//数据查询
		Integer offPeriod = null;
		String sql = "SELECT MAX(OFFSHELF_PERIOD) MAX_OFF_PERIOD FROM INV_STOCK_DATA " +
				" WHERE PRODUCTID=? AND PERIOD=? ";
		
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productId, period);
		for (int j = 0; j < vos.length; j++) {
			HashVO vo = vos[j];
			offPeriod = vo.getIntegerValue("MAX_OFF_PERIOD");
		}
		
		if(offPeriod == null){
			offPeriod = period;
		}
		
		return offPeriod;
	}
	
	/**
	 * 求历史数据的前N期平均值
	 * @param hisRowData
	 * @param avgNum
	 * @param periodNum
	 * @return
	 */
	public Double getHisAvg(ABUiRowData hisRowData,int avgNum,int periodNum){
		Double total = 0d;
		int begin = periodNum - avgNum+1;
		for(int i=begin;i<=periodNum;i++ ){
			total += getNonNullPeriodValue(hisRowData, i);
		}
		BigDecimal bd = new BigDecimal(total/avgNum);  
		double avg = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		return avg;
	}
	
	public ABUiRowData getHisDataRowData(BProduct product,int period,int periodNum,String hisDataCode) throws Exception{
		int periodEnd = UtilPeriod.getPeriod(period, -1);
		int periodBegin = UtilPeriod.getPeriod(periodEnd, -periodNum);
		
		//历史销售-数量-调整后(折合)
		//BBizData bizData4His = bizDataService.getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORYAD_GI+ BizConst.AMOUNT_BIZ_DATA_SUFFIX);
		BBizData bizData4His = bizDataService.getBizDataByCode( hisDataCode );
		
		int periodPlus = 0; //多余查询的预测数据
		//查询历史数据
		int queryPeriodEnd = UtilPeriod.getPeriod(periodEnd, periodPlus);
		ABUiRowData hisRowData =  rowDataQueryService.getUiRowDatas4His(product, periodBegin, queryPeriodEnd, bizData4His);
		
		return hisRowData;
	}
	
	public ABUiRowData getAdjustBizDataRowData(BProduct product,int period,int achieveRate,int periodNum,String bizDataCode) throws Exception{
		int periodBegin = period;
		int periodEnd = UtilPeriod.getPeriod(periodBegin, periodNum);
		
		//BBizData bizData4Final = bizDataService.getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_FINAL+ BizConst.AMOUNT_BIZ_DATA_SUFFIX);
		//zhangzy 20171224 由最终预测数据，改为用户选择的预测数据
		BBizData bizData = bizDataService.getBizDataByCode(bizDataCode);
		
		
		int periodPlus = 0; //多余查询的预测数据
		//查询最终预测数据
		int queryPeriodEnd = UtilPeriod.getPeriod(periodEnd, periodPlus);
		//考虑组合预测的情况对数据的处理
		ABUiRowData finalData =  rowDataQueryService.getUiRowDatas4BizData(product, periodBegin, queryPeriodEnd, bizData);
		
		ABUiRowData adjustBizDataRowData = createUiRowData(bizData.getName()+"-调整后", product, periodBegin, periodEnd);
		
		//初始化调整后预测值
		for(int i=0;i<periodNum+periodPlus;i++ ){
			Double finalPredit = getNonNullPeriodValue(finalData, i); //销售最终预测
			double sell = finalPredit*achieveRate/100 ;
			adjustBizDataRowData.pubFun4setPeriodDataValue(i, sell); //设置调整后的值
		}
		
		return adjustBizDataRowData;
	}
	
	/**
	 * 基于后面多期计算库存覆盖天数
	 * @param finalData 预测销售
	 * @param periodIdx 期间值,从第几期开始
	 * @param stockAmount 库存数量
	 * @return
	 */
	private BigDecimal computeCoverDaysMulti(ABUiRowData finalData,int periodIdx,double stockAmount){
		
		//库存数量为0时，无需计算，覆盖天数为0
		if(stockAmount <=0 ){
			return BigDecimal.ZERO;
		}
		
		//zhangzy 20171017 应业务方要求，算法修正，碰到连续3期值为0时，才
		double addedStock = 0 ;//已累加的库存量
		BigDecimal coverDays;;
		int zeroPeriodNum = 0;
		//最高算到48期
		for(int i=0;i<48;i++){
			Double periodSell = finalData.pubFun4getPeriodDataValue( periodIdx+i );
			
			if(periodSell == null){
				periodSell = 0d;
			}
			//下一期没有预测值或为0的情况,停下来算已有数据的平均
//			if(periodSell == null || periodSell.compareTo(0d) <= 0 ){
//				//coverDays = stockAmount/(addedStock/i) *30;
//				zeroPeriodNum ++;
//				if(zeroPeriodNum == 3){
//					if(addedStock == 0){
//						return Double.NaN;
//					}else{
//						//扣除掉3期为0的期间后，剩余的平均值
//						coverDays = new BigDecimal( stockAmount/(addedStock/(i-zeroPeriodNum)) *30 );
//						
//						
//						return coverDays.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
//					}
//				}				
//			}else{
//				//遇到非0，重新初始值
//				zeroPeriodNum = 0;
//			}
			
			double leftStock = stockAmount - addedStock;
			addedStock +=  periodSell;
			
			if(leftStock < periodSell && periodSell > 0 ){ //减本期不够减,前两期天数 +本期天数
				coverDays = new BigDecimal( i*30 + leftStock/periodSell*30 );
				return coverDays.setScale(2,BigDecimal.ROUND_HALF_UP);
			}
		}
		
		if(addedStock == 0){
			return BigDecimal.ZERO;
		}
		
		//能走到这里说明减了36期，还没减完。覆盖天数超过2年
		
		return BigDecimal.valueOf(36*30);
	}
	
	public double getDouble2Decimal(double value){
		BigDecimal bd = new BigDecimal(value);  
		double newValue = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return newValue;
	}
	
	/**
	  * 从当月期末+N天
	  */
	public static String monthEndAddDay(String monthBeginStr, int addDay) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dt = sdf.parse(monthBeginStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, addDay-1);// 日期加N天
		Date dt1 = calendar.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}
	
	/**
	 * 返回date1 - date2相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysDiff(String date1,String date2) throws Exception
    {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dt1 = sdf.parse(date1);
		Date dt2 = sdf.parse(date2);
		
        int days = (int) ((dt1.getTime() - dt2.getTime()) / (1000*3600*24));
        return days;
    }
	
	/**
	 * 获取abUiRowData对应区间的非空值
	 * @param abUiRowData
	 * @param periodLoc
	 * @return
	 */
	public double getNonNullPeriodValue(ABUiRowData abUiRowData, int periodLoc){
		Double valueDouble = abUiRowData.pubFun4getPeriodDataValue(periodLoc);
		
		if(valueDouble == null){
			return 0;
		}else{
			return valueDouble.doubleValue();
		}
		
	}
	
	/**
	 * 根据指定的bizDataName和product, 期间构造对应的行数据ABUiRowData
	 * @param bizDataName
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @return
	 */
	public ABUiRowData createUiRowData(String bizDataName,BProduct product,int periodBegin,int periodEnd){
		BBizData bizData = new BBizData();
		bizData.setName(bizDataName);
		ABUiRowData rowData = new ABUiRowData();
		rowData.setProduct( product );
		rowData.setBizData( bizData );
		rowData.setPeriodBegin( periodBegin );
		rowData.setPeriodEnd( periodEnd );
		
		return rowData; 
	}

}
