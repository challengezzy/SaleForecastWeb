package dmdd.dmddjava.service.inventory;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.dm.inventory.InMtdDataDM;
import dmdd.dmddjava.dm.inventory.InPoprDataDM;
import dmdd.dmddjava.dm.inventory.InStockDataDM;
import dmdd.dmddjava.dm.inventory.SafeStockDataDM;
import dmdd.dmddjava.dm.inventory.StockDataDM;

/**
 * 库存数据服务类，对UI服务商用入口,配置在WEB-INF/flex/remoting-config.xml中
 * @author zzy
 *
 */
public class UIStockService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());

	//private CommDMO dmo = new CommDMO();
	
	private InPoprDataDM inPoprDataDm = new InPoprDataDM();
	private InStockDataDM inStockDataDm = new InStockDataDM();
	private InMtdDataDM inMtdDataDM = new InMtdDataDM();
	
	private StockDataDM stockdatadm = new StockDataDM();
	
	private SafeStockDataDM safeStockDm = new SafeStockDataDM();
	
	/**
	 * 库存数据导入，处理库存数据并返回每一行处理的处理结果
	 * @param stockDataList 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> importStockData(List<Map<String,String>> stockDataList) throws Exception{
		logger.info("库存数据导入开始,["+ stockDataList.size() +"]条...");
		try{
			List<Map<String,String>> rstList = inStockDataDm.importData4UI(stockDataList);
			
			return rstList;
		}catch (Exception e) {
			logger.error("执行库存数据的导入异常");
			e.printStackTrace();
			throw new Exception("执行库存数据的导入异常", e);
		}
	}
	
	
	/**
	 * 查询导入的库存明细数据
	 * 查询条件：产品、组织、期间、批次号
	 * @param stockDataList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryInStockData(Map<String,String> queryCond) throws Exception{
		logger.info("查询库存ZLIFE的导入数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = inStockDataDm.queryByCondition(queryCond);
			logger.info("查询到["+rstList.size()+"]条数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询库存ZLIFE的导入数据异常");
			e.printStackTrace();
			throw new Exception("查询库存ZLIFE的导入数据异常", e);
		}
	}
	
	/**
	 * 查询库存数据
	 * 查询条件：产品、组织、期间
	 * @param stockDataList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryStockData(Map<String,String> queryCond) throws Exception{
		logger.info("查询库存ZLIFE的导入数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = inStockDataDm.queryByCondition(queryCond);
			logger.info("查询到["+rstList.size()+"]条数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询库存ZLIFE的导入数据异常");
			e.printStackTrace();
			throw new Exception("查询库存ZLIFE的导入数据异常", e);
		}
	}
	
	/**
	 * 查询导入的库存明细数据
	 * 查询条件：产品、组织、期间、批次号
	 * @param stockDataList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryInPoprData(Map<String,String> queryCond) throws Exception{
		
		logger.info("查询POPR的导入数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = inPoprDataDm.queryByCondition(queryCond);
			logger.info("查询到["+rstList.size()+"]条数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询POPR的导入数据异常");
			e.printStackTrace();
			throw new Exception("查询POPR的导入数据异常", e);
		}
	}
	
	/**
	 * 采购订单数据导入，返回每一行处理的处理结果
	 * @param stockDataList 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> importPoprData(List<Map<String,String>> poprDataList) throws Exception{
		logger.info("采购PoPr数据导入开始,["+ poprDataList.size() +"]条...");
		try{
			List<Map<String,String>> rstList = inPoprDataDm.importData4UI(poprDataList);
			
			return rstList;
		}catch (Exception e) {
			logger.error("执行POPR的导入数据异常");
			e.printStackTrace();
			throw new Exception("执行POPR的导入数据异常", e);
		}
		
	}
	
	/**
	 * 当月交货数据导入，返回每一行处理的处理结果
	 * @param stockDataList 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> importMtdData(List<Map<String,String>> mtdDataList) throws Exception{
		logger.info("采购Mtd数据导入开始,["+ mtdDataList.size() +"]条...");
		try{
			List<Map<String,String>> rstList = inMtdDataDM.importData4UI(mtdDataList);
			
			return rstList;
		}catch (Exception e) {
			logger.error("执行库存数据的导入异常");
			e.printStackTrace();
			throw new Exception("执行库存数据的导入异常", e);
		}
		
	}
	
	/**
	 * 查询当月收货数据
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryMtdData(Map<String,String> queryCond) throws Exception{
		logger.info("查询当月收货数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = inMtdDataDM.queryByCondition(queryCond);
			logger.info("查询到["+rstList.size()+"]条数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询当月收货数据异常");
			e.printStackTrace();
			throw new Exception("查询当月收货数据异常", e);
		}
	}
	
	/**
	 * 查询汇总后的库存明细数据
	 * 查询条件：产品、组织、期间
	 * @param stockDataList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryInvStockData(Map<String,String> queryCond) throws Exception{
		logger.info("查询库存数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = stockdatadm.queryStockDataByCond(queryCond);
			logger.info("查询到["+rstList.size()+"]条库存数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询库存数据异常");
			e.printStackTrace();
			throw new Exception("查询库存数据异常", e);
		}
	}
	
	/**  查询汇总后的POPR数据 */
	public List<Map<String,Object>> queryPoprData(Map<String,String> queryCond) throws Exception{
		logger.info("查询预计收货数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = stockdatadm.queryPoprData(queryCond);
			logger.info("查询到["+rstList.size()+"]条预计收货数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询预计收货数据异常");
			e.printStackTrace();
			throw new Exception("查询预计收货数据异常", e);
		}
	}
	
	/**
	 * 安全库存导入，处理库存数据并返回每一行处理的处理结果
	 * @param stockDataList 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> importSafeStockData(List<Map<String,String>> safeStockDataList) throws Exception{
		logger.info("安全库存数据导入开始,["+ safeStockDataList.size() +"]条...");
		try{
			List<Map<String,String>> rstList = safeStockDm.importData4UI(safeStockDataList);
			
			return rstList;
		}catch (Exception e) {
			logger.error("执行安全库存数据的导入异常");
			e.printStackTrace();
			throw new Exception("执行安全库存数据的导入异常", e);
		}
	}
	
	
	/**
	 * 查询导入的库存明细数据
	 * 查询条件：产品、组织、期间、批次号
	 * @param stockDataList
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> querySafeStockData(Map<String,String> queryCond) throws Exception{
		logger.info("查询安全库存导入数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = safeStockDm.queryByCondition(queryCond);
			logger.info("查询到["+rstList.size()+"]条数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询安全库存导入数据异常");
			e.printStackTrace();
			throw new Exception("查询安全库存导入数据异常", e);
		}
	}
	
	/**
	 * 对导入的库存数据进行汇总计算
	 * @param period
	 * @return
	 * @throws Exception
	 */
	public boolean computeStockDataToPeriodAggr(int period) throws Exception{
		StockDataComputeService computeService = new StockDataComputeService();
		computeService.stockDataToPeriodAggr(period);
		
		return true;
	}
	
	/** 计算导入的POPR数据 */
	public boolean computePoprDataToPeriodAggr(int period) throws Exception{
		StockDataComputeService computeService = new StockDataComputeService();
		computeService.poprDataToPeriodAggr(period);
		
		return true;
	}
	
	/**
	 * 根据期间删除已导入的POPR数据
	 * @param period
	 * @return
	 * @throws Exception
	 */
	public boolean deletePoprImportedData(int period) throws Exception{
		StockDataComputeService computeService = new StockDataComputeService();
		computeService.deletePoprImportedData(period);
		
		return true;
	}
	
	/**
	 * 根据期间删除已导入的库存数据
	 * @param period
	 * @return
	 * @throws Exception
	 */
	public boolean deleteStockImportedData(int period) throws Exception{
		StockDataComputeService computeService = new StockDataComputeService();
		computeService.deleteStockImportedData(period);
		
		return true;
	}
	

}

