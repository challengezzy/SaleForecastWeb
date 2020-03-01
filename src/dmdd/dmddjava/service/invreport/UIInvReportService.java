package dmdd.dmddjava.service.invreport;

import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;

/**
 * 库存服务类报表，对UI服务统一入口,配置在WEB-INF/flex/remoting-config.xml中
 * @author zzy
 *
 */
public class UIInvReportService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());

	//private CommDMO dmo = new CommDMO();
	
	private OverStockRiskReportService reportService = new OverStockRiskReportService();
	
	private StockRiskReportService stockRiskService = new StockRiskReportService();
	
	private FcInventoryService fcInvService = new FcInventoryService();
	
	/**
	 * 根据产品查询库存相关风险数据的报表
	 * @param stockDataList 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryInventoryRiskByProduct(Map<String,String> queryCond) throws Exception{
		String methodStr = "根据产品查询库存风险报表";
		logger.info(methodStr + "开始...,queryCond=["+queryCond+"]");
		try{
			Map<String,Object> result = stockRiskService.queryStockRiskReportByProduct(queryCond);
			
			return result;
		}catch (Exception e) {
			logger.error(methodStr + "异常");
			e.printStackTrace();
			throw new Exception(methodStr + "数据异常", e);
		}
	}
	
	/**
	 * 查询库存风险报表汇总
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryInventoryRiskByProductSummary(Map<String,Object> queryCond) throws Exception{
		String methodStr = "查询库存风险报表汇总";
		long start = System.currentTimeMillis();
		logger.info(methodStr + "开始...,queryCond=["+queryCond+"]");
		try{
			Map<String,Object> result = stockRiskService.queryInventoryRiskByProductSummary(queryCond);
			logger.debug(methodStr + "耗时["+ (System.currentTimeMillis()-start) +"]ms");
			return result;
		}catch (Exception e) {
			logger.error(methodStr + "异常");
			e.printStackTrace();
			throw new Exception(methodStr + "数据异常", e);
		}
	}
	
	
	
	
	/**
	 * 根据产品查询过期库存风险报表
	 * @param stockDataList 
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public Map<String,Object> queryRiskReportByProduct(Map<String,String> queryCond) throws Exception{
		String methodStr = "根据产品查询过期库存风险报表";
		logger.info(methodStr + "开始...,queryCond=["+queryCond+"]");
		try{
			Map<String,Object> result = reportService.queryRiskReportByProduct(queryCond);
			
			return result;
		}catch (Exception e) {
			logger.error(methodStr + "异常");
			e.printStackTrace();
			throw new Exception(methodStr + "数据异常", e);
		}
	}
	
	@Deprecated
	public Map<String,Object> queryRiskReportSummary(Map<String,String> queryCond ) throws Exception{
		long start = System.currentTimeMillis();
		String methodStr = "根据产品查询过期库存风险汇总报表";
		logger.info(methodStr + "开始...,queryCond=["+queryCond+"]");
		try{
			Map<String,Object> result = reportService.queryRiskReportSummary(queryCond);
			
			logger.info(methodStr + "耗时["+ (System.currentTimeMillis()-start) +"]ms");
			
			return result;
		}catch (Exception e) {
			logger.error(methodStr + "异常");
			e.printStackTrace();
			throw new Exception(methodStr + "数据异常", e);
		}
	}
	
	/**
	 * 根据产品列表查询预测库存汇总报表
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryFcInventoryReport(Map<String,Object> queryCond ) throws Exception{
		long start = System.currentTimeMillis();
		String methodStr = "根据产品列表查询预测库存汇总报表";
		logger.info(methodStr + "开始...,queryCond=["+queryCond+"]");
		try{
			Map<String,Object> result = fcInvService.queryFcInventoryReport(queryCond);
			
			logger.info(methodStr + "耗时["+ (System.currentTimeMillis()-start) +"]ms");
			
			return result;
		}catch (Exception e) {
			logger.error(methodStr + "异常");
			e.printStackTrace();
			throw new Exception(methodStr + "数据异常", e);
		}
	}

}

