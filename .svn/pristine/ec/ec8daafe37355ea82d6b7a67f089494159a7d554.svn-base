package dmdd.dmddjava.service.systemservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.Session;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.utils.UtilBizData;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dm.ForecastDataDM;
import dmdd.dmddjava.dm.HistoryDataDM;
import dmdd.dmddjava.service.forecastservice.AmountForecastService;
import dmdd.dmddjava.service.historyservice.AmountHistoryCalContext;
import dmdd.dmddjava.service.historyservice.AmountHistoryService;

public class KitRefreshService {
	
	/**
	 * 是否在运行折合数据计算
	 */
	public static boolean isRunningKit = false;
	
	public static int hisBizDataTotal = 0; //历史业务数据数
	
	public static int fcBizDataTotal = 0 ;//预测业务数据数
	
	public static int hisBizDataRunned = 0; //已计算历史业务数据数
	
	public static int fcBizDataRunned = 0 ;//已计算预测业务数据数
	
	/**
	 * 上一次运行时间，系统重启后失效
	 */
	public static String lastRunTime = "NON";
	
	/**
	 * 返回折合套装运行进度
	 * @return
	 */
	public static String getRunningProgress(){
		if(isRunningKit == false ){
			return "折合数据已刷新完成或未启动,上次运行["+ lastRunTime +"]";
		}

		String tip = "折合运行中，进度["+ (hisBizDataRunned+fcBizDataRunned) +"/ "+ (hisBizDataTotal+fcBizDataTotal) +"]";
		
		return tip;
	}
	
	public void initAmountHistoryData() throws Exception {
		long start = System.currentTimeMillis();
		CoolLogger.getLogger().info("开始初始化历史折合数据");

		HistoryDataDM historyDataDM = new HistoryDataDM();
		Session session = HibernateSessionFactory.getSession();
		DaoBizData bizDataDao = new DaoBizData(session);

		// 获取historyData中的所有业务数据ID， 已去重
		List<Long> bizDataIds = historyDataDM.getBizDataIds();
		
		hisBizDataTotal = bizDataIds.size();
		isRunningKit = true;
		hisBizDataRunned = 0;
		
		final AtomicInteger count = new AtomicInteger(0);
		final AmountHistoryService amountHistoryService = new AmountHistoryService();
		for (Long bizDataId : bizDataIds) {
			BizData bizData = bizDataDao.getBizDataById(bizDataId);
			if (bizData == null) {
				CoolLogger.getLogger().warn("历史数据表中， 业务数据id[" + bizDataId + "] 不存在");
				continue;
			}
			
			//是否支持折和计算
		    if (UtilBizData.matchNoSuitSupport(bizData.getIsSuitSupport())){
		    	CoolLogger.getLogger().warn("预测数据表中， 业务数据id[" + bizDataId + "] 不支持折和计算");
		    	continue;
		    }
			
			// 当前是否已经是折合业务数据， 如是跳过计算
			String bizDataCode = bizData.getCode();
			if (bizDataCode.endsWith(BizConst.AMOUNT_BIZ_DATA_SUFFIX)) {
				CoolLogger.getLogger().warn("历史数据表中， 业务数据id[" + bizDataId + "]已经是折合业务数据， 无需计算");
				hisBizDataRunned ++;
				continue;
			}

			// 获取目标折合业务数据
			String amountBizDataCode = bizDataCode + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			final BizData targetAmountBizData = bizDataDao.getBizDataByCode(amountBizDataCode);
			if (targetAmountBizData == null) {
				throw new Exception(String.format("历史数据表中, 业务数据id[%d] 对应的折合业务数据[%s]不存在", bizDataId, amountBizDataCode));
			}

			Long[] maxminIds = historyDataDM.getMaxMinID(bizDataId);
			Long minId = maxminIds[1], maxId = maxminIds[0];
			while (minId <= maxId) {
				Long begin = minId;
				Long end = minId + 10000;
				List<AmountHistoryCalContext> calContextList = historyDataDM.getHistoryCalContextDatas(bizDataId, begin, end,targetAmountBizData);
				CoolLogger.getLogger().info(String.format("历史数据表中， 业务数据id[%d] 共 [%d]条历史数据, Id begin[%d], Id End[%d]，开始计算折合数据 "
							, bizDataId, calContextList.size(), begin, end));
				amountHistoryService.caculateAmountData(calContextList,true);
//				final CountDownLatch latch = new CountDownLatch(historyDatas.size());
//				for (final HistoryData historyData : historyDatas) {
//					pool.execute(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								//historyService.refreshAmountHistoryData(historyData, targetAmountBizData);
//								count.incrementAndGet();
//							} catch (Exception e) {
//								CoolLogger.getLogger().error(String.format("初始化历史折合数据失败, historyData[%s]", historyData.toString()), e);
//							} finally {
//								latch.countDown();
//							}
//						}
//					});
//				}
//				latch.await();
				minId = end + 1;
			}
			hisBizDataRunned ++;

		}

		CoolLogger.getLogger().info("异步初始化历史折合数据成功， 共构建历史折合数据[" + count.get() + "] 条"+ "耗时["+ (System.currentTimeMillis()-start) +"]ms");
	}

	public void initAmountForecastData() throws Exception {
		CoolLogger.getLogger().info("开始初始化预测折合数据");

		ForecastDataDM forecastDataDM = new ForecastDataDM();
		Session session = HibernateSessionFactory.getSession();
		DaoBizData bizDataDao = new DaoBizData(session);

		// 获取historyData中的所有业务数据ID， 已去重
		List<Long> bizDataIds = forecastDataDM.getBizDataIds();
		
		isRunningKit = true;
		fcBizDataRunned = 0;
		fcBizDataTotal = bizDataIds.size();

		final AtomicInteger count = new AtomicInteger(0);
		final AmountForecastService amountForecastService = new AmountForecastService();
		for (Long bizDataId : bizDataIds) {
			BizData bizData = bizDataDao.getBizDataById(bizDataId);
			if (bizData == null) {
				CoolLogger.getLogger().warn("预测数据表中， 业务数据id[" + bizDataId + "] 不存在");
				continue;
			}
			
			//是否支持折和计算
		    if (UtilBizData.matchNoSuitSupport(bizData.getIsSuitSupport())){
		    	CoolLogger.getLogger().warn("预测数据表中， 业务数据id[" + bizDataId + "] 不支持折和计算");
		    	continue;
		    }
			
			// 当前是否已经是折合业务数据， 如是跳过计算
			String bizDataCode = bizData.getCode();
			if (bizDataCode.endsWith(BizConst.AMOUNT_BIZ_DATA_SUFFIX)) {
				CoolLogger.getLogger().warn("预测数据表中， 业务数据id[" + bizDataId + "]已经是折合业务数据， 无需计算");
				fcBizDataRunned++;
				continue;
			}

			// 获取目标折合业务数据
			String amountBizDataCode = bizDataCode + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			final BizData targetAmountBizData = bizDataDao.getBizDataByCode(amountBizDataCode);
			if (targetAmountBizData == null) {
				throw new Exception(String.format("预测数据表中, 业务数据id[%d] 对应的折合业务数据[%s]不存在", bizDataId, amountBizDataCode));
			}

			Long[] maxminIds = forecastDataDM.getMaxMinID(bizDataId);
			Long minId = maxminIds[1], maxId = maxminIds[0];
			while (minId <= maxId) {
				Long begin = minId;
				Long end = minId + 10000;
				List<ForecastData> forecastDatas = forecastDataDM.getForecastDatas(bizDataId, begin, end,targetAmountBizData);
				CoolLogger.getLogger().info(
						String.format("预测数据表中， 业务数据id[%d] 共 [%d]条预测数据, Id begin[%d], Id End[%d]，开始计算折合数据 ", bizDataId, forecastDatas.size(), begin,
								end));
				
				amountForecastService.calculateAmountData(forecastDatas,false);
//				final CountDownLatch latch = new CountDownLatch(forecastDatas.size());
//				for (final ForecastData forecastData : forecastDatas) {
//					pool.execute(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								//数据刷新
//								amountForecastService.refreshAmountForecastData(forecastData, targetAmountBizData);
//								count.incrementAndGet();
//							} catch (Exception e) {
//								CoolLogger.getLogger().error(String.format("初始化预测折合数据失败, historyData[%s]", forecastData.toString()), e);
//							} finally {
//								latch.countDown();
//							}
//						}
//					});
//				}
				//latch.await();
				minId = end + 1;
			}
			fcBizDataRunned ++;

		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lastRunTime = formatter.format(new Date());
		isRunningKit = false;

		CoolLogger.getLogger().info("异步初始化预测折合数据成功， 共构建历史折合数据[" + count.get() + "] 条");
	}

}
