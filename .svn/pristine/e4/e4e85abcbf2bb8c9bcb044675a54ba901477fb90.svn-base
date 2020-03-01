package dmdd.dmddjava.service.replenish;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.util.DBUtil;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABImReplenishStockDays;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataCurrentProDC;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProDC;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProDCDetail;
import dmdd.dmddjava.dataaccess.bizobject.BDistributionCenter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.service.dimensionservice.DistributionCenterService;
import dmdd.dmddjava.service.dimensionservice.ProductService;

/**
 * 补货计划相关服务类
 * 
 * @author jerry
 * @date Aug 7, 2013
 */
public class ReplenishService {
	private Logger logger = CoolLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();

	/**
	 * import stockdays
	 * 
	 * @param _bizData
	 * @param _list4ABImReplenishData
	 * @return
	 * @throws Exception
	 */
	public List<ABImReplenishStockDays> saveStockDaysDatas4ImportUI(List<ABImReplenishStockDays> _list4ABImReplenishData) throws Exception {
		// 检查服务器状态是否可以提供服务 begin
		ServerEnvironment.getInstance().checkSystemStatus();
		// 检查服务器状态是否可以提供服务 end

		List<ABImReplenishStockDays> rstList = new ArrayList<ABImReplenishStockDays>();

		if (_list4ABImReplenishData == null || _list4ABImReplenishData.isEmpty()) {
			throw new Exception("Paramete is not correct");
		}

		String importResult = null;
		List<String> sqls = new ArrayList<String>();
		try {
			for (ABImReplenishStockDays abImReplenishData : _list4ABImReplenishData) {
				ProductService proService = new ProductService();
				BProduct detailProduct = proService.getProductByCode(abImReplenishData.getProductCode());
				if (detailProduct == null) {
					importResult = "Can not find Detail Product by the Code";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				// dc检查
				DistributionCenterService dcService = new DistributionCenterService();
				BDistributionCenter dc = dcService.qeuryDCByCode(abImReplenishData.getDcCode());
				if (dc == null) {
					importResult = "Can not find Detail DistributionCenter by the Code";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				// 起始期间检查
				int periodBegin = abImReplenishData.getPeriodBegin();
				if (periodBegin == SysConst.PERIOD_NULL) {
					importResult = "There is no Begin Period";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				// 终止期间检查
				int periodEnd = abImReplenishData.getPeriodEnd();
				if (periodEnd == SysConst.PERIOD_NULL) {
					importResult = "There is no End Period";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				int periodDiff = UtilPeriod.getPeriodDifference(periodBegin, periodEnd);
				for (int periodLoc = 0; periodLoc <= periodDiff; periodLoc++) {
					Long value = abImReplenishData.pubFun4getPeriodDataValue(periodLoc);
					String sql = "";
					int period = UtilPeriod.getPeriod(periodBegin, periodLoc);

					// 先判断是否存在
					if (UtilReplenish.checkExists4ReplenishData(detailProduct.getId(), dc.getId(), period)) {
						sql = "update replenishdata set stockday=" + value + ",updatetime=" + DBUtil.getSysDate() + " where period=" + period + " and productid=" + detailProduct.getId()
								+ " and dcid=" + dc.getId();
					} else {
						sql = "insert into replenishdata(" + DBUtil.getInsertId() + "productid,dcid,period,stockday,comments,inittime,updatetime,version)values("
								+ DBUtil.getSeqValue("S_REPLENISHDATA") + detailProduct.getId() + "," + dc.getId() + "," + period + "," + value + ",''," + DBUtil.getSysDate() + ","
								+ DBUtil.getSysDate() + ",0)";
					}
					sqls.add(sql);
				}

				// 导入成功 begin
				importResult = BizConst.IMPORT_RESULT_SUCCESS;
				abImReplenishData.setImportResult(importResult);

				rstList.add(abImReplenishData);
				// 导入成功 end

			}
			dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
			dmo.commit(DMOConst.DS_DEFAULT);
		} catch (Exception ex) {

			ex.printStackTrace();

			if (ex.getCause() != null) {
				importResult = ex.getCause().getMessage();
			} else {
				importResult = ex.getMessage();
			}
			dmo.rollback(DMOConst.DS_DEFAULT);

		} finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}

		return rstList;
	}

	public List<ABImReplenishStockDays> saveTermEndDatas4ImportUI(

	List<ABImReplenishStockDays> _list4ABImReplenishData) throws Exception {
		// 检查服务器状态是否可以提供服务 begin
		ServerEnvironment.getInstance().checkSystemStatus();
		// 检查服务器状态是否可以提供服务 end

		List<ABImReplenishStockDays> rstList = new ArrayList<ABImReplenishStockDays>();

		if (_list4ABImReplenishData == null || _list4ABImReplenishData.isEmpty()) {
			throw new Exception("Paramete is not correct");
		}
		int period_current = ServerEnvironment.getInstance().getSysPeriod().getCompilePeriod();
		int period_last = UtilPeriod.getPeriod(period_current, -1);
		String importResult = null;
		List<String> sqls = new ArrayList<String>();
		try {
			for (ABImReplenishStockDays abImReplenishData : _list4ABImReplenishData) {
				ProductService proService = new ProductService();
				BProduct detailProduct = proService.getProductByCode(abImReplenishData.getProductCode());
				if (detailProduct == null) {
					importResult = "Can not find Detail Product by the Code";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				// dc检查
				DistributionCenterService dcService = new DistributionCenterService();
				BDistributionCenter dc = dcService.qeuryDCByCode(abImReplenishData.getDcCode());
				if (dc == null) {
					importResult = "Can not find Detail DistributionCenter by the Code";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				// 起始期间检查
				int periodBegin = abImReplenishData.getPeriodBegin();
				if (periodBegin == SysConst.PERIOD_NULL) {
					importResult = "There is no Begin Period";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				// 终止期间检查
				int periodEnd = abImReplenishData.getPeriodEnd();
				if (periodEnd == SysConst.PERIOD_NULL) {
					importResult = "There is no End Period";
					abImReplenishData.setImportResult(importResult);
					rstList.add(abImReplenishData);
					continue;
				}

				int periodDiff = UtilPeriod.getPeriodDifference(periodBegin, periodEnd);
				for (int periodLoc = 0; periodLoc <= periodDiff; periodLoc++) {
					Long value = abImReplenishData.pubFun4getPeriodDataValue(periodLoc);
					String sql = "";
					int period = UtilPeriod.getPeriod(periodBegin, periodLoc);

					// 判断replenishhisdata中的数据情况 - 期末数据
					// 先判断是否存在
					if (UtilReplenish.checkExists4HistoryReplenish(detailProduct.getId(), dc.getId(), period)) {
						sql = "update replenishhisdata set termend=" + value + "  where period=" + period + " and productid=" + detailProduct.getId() + " and  dcid=" + dc.getId();
					} else {
						sql = "insert into replenishhisdata(" + DBUtil.getInsertId() + "productid,dcid,period,termend,comments,inittime,version)values("
								+ DBUtil.getSeqValue("S_REPLENISHDATA") + detailProduct.getId() + "," + dc.getId() + "," + period + "," + value + ",''," + DBUtil.getSysDate() + ",0)";
					}
					sqls.add(sql);

					// 判断是否导入了当前期间的前一期，如果有则更新replenishdata表中的termbegin字段，否则不过任何操作
					if (period == period_last) {
						// 判断replenishdata中的情况 - 期初数据
						if (UtilReplenish.checkExists4ReplenishData(detailProduct.getId(), dc.getId(), period_current)) {
							sql = "update replenishdata set termbegin=" + value + ",updatetime=" + DBUtil.getSysDate() + "  where period=" + period_current + " and productid="
									+ detailProduct.getId() + " and  dcid=" + dc.getId();
						} else {
							sql = "insert into replenishdata(" + DBUtil.getInsertId() + "productid,dcid,period,termbegin,comments,inittime,updatetime,version)values("
									+ DBUtil.getSeqValue("S_REPLENISHDATA") + detailProduct.getId() + "," + dc.getId() + "," + period_current + "," + value + ",''," + DBUtil.getSysDate()
									+ "," + DBUtil.getSysDate() + ",0)";
						}
						sqls.add(sql);
					}

				}

				// 导入成功 begin
				importResult = BizConst.IMPORT_RESULT_SUCCESS;
				abImReplenishData.setImportResult(importResult);

				rstList.add(abImReplenishData);
				// 导入成功 end

			}

			dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
			dmo.commit(DMOConst.DS_DEFAULT);
		} catch (Exception ex) {

			ex.printStackTrace();

			if (ex.getCause() != null) {
				importResult = ex.getCause().getMessage();
			} else {
				importResult = ex.getMessage();
			}
			dmo.rollback(DMOConst.DS_DEFAULT);

		} finally {
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}

		return rstList;
	}
	
	/**
	 * 按分仓查询补货计算数据
	 * @param pidList
	 * @param dcidList
	 * @param beginPeriod
	 * @param endPeriod
	 * @param dataType 数据列类型
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowDataProDC> getDataGroupByDC(List<String> pidList,List<String> dcidList,int beginPeriod,int endPeriod,String dataType) throws Exception{
		ReplenishQueryService queryService = new ReplenishQueryService();
		List<ABUiRowDataProDC> rstList = new ArrayList<ABUiRowDataProDC>();
		try{
			
			rstList = queryService.getReplenishDataByMultiPeriod(pidList, dcidList, beginPeriod, endPeriod,dataType);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据出错:"+e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return rstList;
	}
	
	/**
	 * 查询指定产品、分仓的多期间数据
	 * @param proId
	 * @param dcId
	 * @param beginPeriod
	 * @param endPeriod
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowDataProDCDetail> getDetailDataByProDC(String proId,String dcId,int beginPeriod,int endPeriod) throws Exception{
		ReplenishQueryService queryService = new ReplenishQueryService();
		List<ABUiRowDataProDCDetail> rstList = new ArrayList<ABUiRowDataProDCDetail>();
		try{
			
			rstList = queryService.getDetailDataByProDC(proId, dcId, beginPeriod, endPeriod);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据出错:"+e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return rstList;
	}
	
	/**
	 * 获取单期间补货计划数据
	 * @param pidList 产品ID数组
	 * @param dcidList DC ID数组
	 * @param period 期间
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowDataCurrentProDC> getReplenishDataByPeriod(List<String> pidList,List<String> dcidList,int period) throws Exception{
		ReplenishQueryService queryService = new ReplenishQueryService();
		List<ABUiRowDataCurrentProDC> rstList = new ArrayList<ABUiRowDataCurrentProDC>();
		try{
			
			rstList = queryService.getReplenishDataByPeriod(pidList, dcidList, period);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询当前期数据出错:"+e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return rstList;
	}
	
	/**
	 * 补货数据计算刷新
	 * @return
	 * @throws Exception
	 */
	public String replenishCompute(String operator) throws Exception{
		ReplenishComputeService computeService = new ReplenishComputeService();
		
		return computeService.replenishCompute(operator);
	}

	
	/**
	 * 按分仓查询补货计算历史数据
	 * @param pidList
	 * @param dcidList
	 * @param beginPeriod
	 * @param endPeriod
	 * @param dataType 数据列类型
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowDataProDC> getHistoryDataGroupByDC(List<String> pidList,List<String> dcidList,int beginPeriod,int endPeriod,String dataType) throws Exception{
		ReplenishQueryService queryService = new ReplenishQueryService();
		List<ABUiRowDataProDC> rstList = new ArrayList<ABUiRowDataProDC>();
		try{
			
			rstList = queryService.getReplenishHistoryDataByMultiPeriod(pidList, dcidList, beginPeriod, endPeriod,dataType);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据出错:"+e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return rstList;
	}
	
	/**
	 * 查询指定产品、分仓的多期间历史数据
	 * @param proId
	 * @param dcId
	 * @param beginPeriod
	 * @param endPeriod
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowDataProDCDetail> getDetailHistoryDataByProDC(String proId,String dcId,int beginPeriod,int endPeriod) throws Exception{
		ReplenishQueryService queryService = new ReplenishQueryService();
		List<ABUiRowDataProDCDetail> rstList = new ArrayList<ABUiRowDataProDCDetail>();
		try{
			
			rstList = queryService.getDetailHistoryDataByProDC(proId, dcId, beginPeriod, endPeriod);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("查询数据出错:"+e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return rstList;
	}
	
}
