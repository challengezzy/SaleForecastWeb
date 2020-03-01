package dmdd.dmddjava.service.dimensionservice;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.service.forecastservice.PeriodVersionService;
import dmdd.dmddjava.service.systemservice.KitRefreshService;

/**
 * 主数据查询服务类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Oct 21, 2017 4:54:37 PM
 */
public class UIMainDataService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());

	private MainDataQueryService queryService = new MainDataQueryService();
	
	private KitRefreshService kitRefreshService = new KitRefreshService();
	
	/**
	 * 查询主数据的相关报表数据
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryForecastInstProOrg(Map<String,String> queryCond) throws Exception{
		String methdName = "预测策略-产品";
		logger.info("查询"+methdName+"数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = queryService.queryForecastInstProOrg(queryCond);
			logger.info("查询到["+rstList.size()+"]条"+methdName+"数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询"+methdName+"数据异常");
			e.printStackTrace();
			throw new Exception("查询"+methdName+"数据异常", e);
		}
	}
	
	/**
	 * 查询产品套装与产品关系
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryProductSuitRel(Map<String,String> queryCond) throws Exception{
		String methdName = "产品套装-产品";
		logger.info("查询"+methdName+"数据开始...,queryCond=["+queryCond+"]");
		try{
			List<Map<String,Object>> rstList = queryService.queryProductSuitRel(queryCond);
			logger.info("查询到["+rstList.size()+"]条"+methdName+"数据");
			
			return rstList;
		}catch (Exception e) {
			logger.error("查询"+methdName+"数据异常");
			e.printStackTrace();
			throw new Exception("查询"+methdName+"数据异常", e);
		}
	}
	
	/**
	 * 刷新套装的历史折合数据，非常耗时，慎用！！！！
	 * @throws Exception
	 */
	public void refreshAmountHistoryData() throws Exception{
		kitRefreshService.initAmountHistoryData();
		kitRefreshService.initAmountForecastData();
	}
	
	/**
	 * 刷新套装的预测折合数据，非常耗时，慎用！！！！
	 * @throws Exception
	 */
	public void refreshAmountForecastData() throws Exception{
		kitRefreshService.initAmountHistoryData();
		kitRefreshService.initAmountForecastData();
	}
	
	public void refreshPeriodVersion(int period) throws Exception{
		PeriodVersionService versionService = new PeriodVersionService();
		versionService.updateAllPeriodVersionData(period);
	}

}

