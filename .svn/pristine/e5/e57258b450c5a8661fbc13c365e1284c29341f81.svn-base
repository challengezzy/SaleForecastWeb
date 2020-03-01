package dmdd.dmddjava.service.systemservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.cool.common.constant.SysConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.system.CoolServerEnvironment;
import com.cool.common.util.FileUtil;
import com.cool.dbaccess.DataSourceManager;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilMD5;
import dmdd.dmddjava.common.utils.UtilSysProbation;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.SysParamBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.SysPeriodBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTask;
import dmdd.dmddjava.dataaccess.dataobject.PeriodRollTask;
import dmdd.dmddjava.dataaccess.dataobject.SysParam;
import dmdd.dmddjava.dataaccess.dataobject.SysPeriod;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastRunTask;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoPeriodRollTask;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysParam;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysPeriod;
import dmdd.dmddjava.service.dimensionservice.BizDataService;
import dmdd.dmddjava.service.dimensionservice.DistributionCenterService;
import dmdd.dmddjava.service.dimensionservice.OrganizationService;
import dmdd.dmddjava.service.dimensionservice.ProductService;
import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author liuzhen dmdd系统启动初始化类
 */
public class DmddBootServlet extends HttpServlet {
	public final static long serialVersionUID = -9999999999L;

	@Override
	public void init() throws ServletException {
		try {
			// 获取服务端完整路径
			String sysRootPath = getServletConfig().getServletContext().getRealPath("/").replace('\\', '/');
			sysRootPath = sysRootPath.replace('\\', '/');
			if (!sysRootPath.endsWith("/")) {
				sysRootPath = sysRootPath + "/";
			}

			SysConst.WEBROOT_PATH = sysRootPath;

			// 加载系统配置文件
			loadConfigXml();

			// 加载配置参数
			initConfigParam();

			// 设置数据库类型
			SysConst.DATABASE_TYPE = ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE);

			// 缓存系统菜单
			initMenu();

			// 初始化数据源
			initDataSources();

			// 初始化服务端缓存数据
			initSeverEnvironment();

			// 当系统支持产品套装， 初始化折合业务数据及历史折合、预测折合
			String isSuitSupport = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_IS_SUIT_SUPPORT);
			String initSuit = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_INIT_SUIT);
			if ("true".equals(isSuitSupport) && "true".equals(initSuit)) {
				// 初始化折合业务数据
				initBizData();
				// 异步执行折合数据计算
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							KitRefreshService kitService = new KitRefreshService();
							// 初始化历史折合
							kitService.initAmountHistoryData();
							// 初始化预测折合
							kitService.initAmountForecastData();
						} catch (Exception e) {
							CoolLogger.getLogger().error("计算折合数据失败", e);
						}
					}
				}).start();

			}

			// 检验试用期
			if (!checkProbation()) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_ILLEGAL_START);
				Exception ex = new Exception(cause);
				System.err.print("******非法启动系统，请使用正版或申请延长试用期！******");
				System.exit(0);
				throw ex;
			}

			// 启动期间滚动线程和预测任务运行线程
			initAndStartTasksManagerThreads();
		} catch (Exception ex) {
			ServerEnvironment.getInstance().setSystemStatus(BizConst.SYSTEM_STATUS_EXCEPTION_START);
			ex.printStackTrace();
			throw new ServletException("Failed to start DmddBootServlet !");
		}
	}

	/**
	 * 加载系统配置文件
	 * 
	 * @throws Exception
	 */
	private void loadConfigXml() {

		String confileFileName = "DmddConfig.xml";
		String filePath = SysConst.WEBROOT_PATH + "WEB-INF/" + confileFileName; //
		Document doc = null;
		try {
			doc = FileUtil.loadXML(filePath);
			SysConst.log4jElement = doc.getRootElement().getChild("log4j");
			CoolLogger.initLogger();
			ServerEnvironment.getInstance().setConfigDoc(doc);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("☆由于没有获得系统配置信息，系统启动失败退出。");
			System.err.println("***********************系统退出************************");
			System.exit(0);
		}
	}

	/**
	 * load配置文件参数
	 * 
	 * @return
	 */
	private void initConfigParam() {
		Logger logger = CoolLogger.getLogger(DmddBootServlet.class);
		Document doc = ServerEnvironment.getInstance().getConfigDoc();
		Element paramE = doc.getRootElement().getChild("params");
		String str_key = "";
		try {
			@SuppressWarnings("unchecked")
			List<Element> initparams = paramE.getChildren("init-param");
			if (initparams == null) {
				logger.warn("没有初始化配置参数!");
				return;
			}

			for (int i = 0; i < initparams.size(); i++) {
				if (initparams.get(i) instanceof Element) {
					Element param = (Element) initparams.get(i);
					if (param != null) {
						str_key = param.getAttributeValue("key");
						String str_value = param.getAttributeValue("value");
						String str_descr = param.getAttributeValue("descr");

						logger.debug("配置参数：" + str_key + " 取值：" + str_value + " 解释：" + str_descr);

						CoolServerEnvironment.getInstance().put(str_key, str_descr, str_value); // 往缓存中送入...
						ServerEnvironment.getInstance().putConfigParam(str_key, str_value);

					}
				}
			}
			// 兼容老配置文件的判断,设置数据库类型参数
			ServerEnvironment.getInstance().putConfigParam(BizConst.SYSPARAM_CODE_DATABASE,
					ServerEnvironment.getInstance().getConfigFileParam("database_type"));

			logger.debug("初始化基本参数结束");
		} catch (Throwable tr) {
			tr.printStackTrace();
		}
	}

	/**
	 * 初始化数据库连接池
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void initDataSources() throws JDOMException, IOException {
		long ll_1 = System.currentTimeMillis();
		Logger logger = CoolLogger.getLogger(DmddBootServlet.class);
		logger.info("开始初始化数据库连接池...");

		Document doc = ServerEnvironment.getInstance().getConfigDoc();

		Element datasources = doc.getRootElement().getChild("datasources"); // 得到datasources子结点!!
		if (datasources != null) {
			@SuppressWarnings("unchecked")
			List<Element> sources = datasources.getChildren("datasource"); // 得到所有子结点!!
			DataSourceManager.initDS(sources);

			// 服务器端设置变量
			CoolServerEnvironment.getInstance().put("defaultdatasource", DataSourceManager.getDefaultDS()); // 设置默认数据源!!
			String[] keys = DataSourceManager.getDataSources();
			CoolServerEnvironment.getInstance().put("ALLDATASOURCENAMES", keys);
			for (int i = 0; i < keys.length; i++) {
				CoolServerEnvironment.getInstance().put(keys[i], DataSourceManager.getDataSourceUrl(keys[i])); //
			}
		}
		long ll_2 = System.currentTimeMillis();

		logger.info("初始化数据库连接池结束,耗时[" + (ll_2 - ll_1) + "]");

	}

	private void initMenu() throws Exception {
		String confileFileName = "dmddmenu.xml";
		String filePath = SysConst.WEBROOT_PATH + "WEB-INF/" + confileFileName; //
		Document doc = null;
		try {
			doc = FileUtil.loadXML(filePath);// 这一步读取的目的是为空检验文件是否是合法的Xml

			String menuStr = FileUtil.readFileContent(filePath, "utf-8");
			ServerEnvironment.getInstance().setMenuXmlStr(menuStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("☆由于没有获得系统菜单[dmddmenu.xml]信息，系统启动失败退出。");
			System.err.println("***********************系统退出************************");
			System.exit(0);
		}
	}

	/**
	 * 初始服务端数据缓存
	 * 
	 * @throws Exception
	 */
	private void initSeverEnvironment() throws Exception {
		// 系统期间 begin
		BSysPeriod bSysPeriod = null;
		SysPeriodBDConvertor sysPeriodBDConvertor = new SysPeriodBDConvertor();

		Transaction trsa = null;
		Session session = HibernateSessionFactory.getSession();
		try {
			trsa = session.beginTransaction();
			DaoSysPeriod daoSysPeriod = new DaoSysPeriod(session);
			SysPeriod currentSysPeriod = daoSysPeriod.getSysPeriod();
			if (currentSysPeriod == null) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_NO_RECORD_IN_SYSPERIOD);
				Exception ex = new Exception(cause);
				throw ex;
			}
			bSysPeriod = (BSysPeriod) sysPeriodBDConvertor.dtob(currentSysPeriod);
			trsa.commit();
		} catch (Exception ex) {
			if (trsa != null) {
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		ServerEnvironment.getInstance().setSysPeriod(bSysPeriod);
		// 系统期间 end

		// 系统参数 begin
		session = HibernateSessionFactory.getSession();
		try {
			trsa = session.beginTransaction();
			DaoSysParam daoSysParam = new DaoSysParam(session);
			List<SysParam> sysPamramList_inDB = daoSysParam.getAllSysParams();
			if (sysPamramList_inDB != null) {
				SysParamBDConvertor sysParamBDConvertor = new SysParamBDConvertor();
				for (int i = 0; i < sysPamramList_inDB.size(); i++) {
					ServerEnvironment.getInstance().putSysParam((BSysParam) sysParamBDConvertor.dtob(sysPamramList_inDB.get(i)));
				}
			}
			trsa.commit();
		} catch (Exception ex) {
			if (trsa != null) {
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		} finally {
			session.close();
		}
		// 系统参数 end

		// TODO zhangzy modify
		// 不是第一次启动时初始化，按需获取
		// 内存常驻变量 begin
		ProductService productService = new ProductService();
		// 注意下面的顺序一定是先特征后产品，因为产品的productService.getProductTreeRoot()中会用特征树的
		ServerEnvironment.getInstance().setBProductCharacterTreeRoot(productService.getProductCharacterTreeRoot());
		ServerEnvironment.getInstance().setBProductTreeRoot(productService.getProductTreeRoot());

		OrganizationService organizationService = new OrganizationService();
		// 注意下面的顺序一定是先特征后组织，因为组织的organizationService.getOrganizationTreeRoot()中会用特征树的
		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot(organizationService.getOrganizationCharacterTreeRoot());
		ServerEnvironment.getInstance().setBOrganizationTreeRoot(organizationService.getOrganizationTreeRoot());
		// 内存常驻变量 end

		try {
			DistributionCenterService dcService = new DistributionCenterService();
			ServerEnvironment.getInstance().setbDistributionCenterTreeRoot(dcService.getRootDC());
		} catch (Exception e) {
			CoolLogger.getLogger().error("系统中没有分销数据！");
			e.printStackTrace();
		}
	}

	private void initBizData() throws Exception {
		CoolLogger.getLogger().info("开始初始化折合业务数据");

		Session session = HibernateSessionFactory.getSession();
		DaoBizData dao = new DaoBizData(session);
		List<BizData> bizDatas = Collections.emptyList();
		// 获取所有业务数据
		try {
			bizDatas = dao.getAllBizDatas();
		} finally {
			session.close();
		}
		// 构建已有折合业务数据
		List<String> amountCodes = new ArrayList<String>();
		for (BizData bizData : bizDatas) {
			if (bizData.getCode().endsWith(BizConst.AMOUNT_BIZ_DATA_SUFFIX)) {
				amountCodes.add(bizData.getCode());
			}
		}

		final BizDataService bizDataService = new BizDataService();
		final BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
		// 新建折合业务数据
		ExecutorService pool = Executors.newFixedThreadPool(10);
		final CountDownLatch latch = new CountDownLatch(bizDatas.size());
		final AtomicInteger count = new AtomicInteger(0);
		for (final BizData bizData : bizDatas) {
			final String targetAmountCode = bizData.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			if (!amountCodes.contains(bizData.getCode()) && !amountCodes.contains(targetAmountCode)) {
				// 非折合类业务数据
				CoolLogger.getLogger().info("开始初始化折合业务数据" + targetAmountCode);
				pool.execute(new Runnable() {
					@Override
					public void run() {
						BBizData bbizData = null;
						try {
							bbizData = bizDataBDConvertor.dtob(bizData, true);
							bbizData.setCode(targetAmountCode);
							bbizData.setName(bbizData.getName() + BizConst.AMOUNT_BIZ_DATA_NAME_SUFFIX);
							if (StringUtils.isNotEmpty(bbizData.getDescription())) {
								bbizData.setDescription(bbizData.getDescription() + BizConst.AMOUNT_BIZ_DATA_DESC_SUFFIX);
							}
							bizDataService.newAmountBizData(bbizData);
							count.incrementAndGet();
						} catch (Exception e) {
							CoolLogger.getLogger().error(bbizData, e);
						} finally {
							latch.countDown();
						}
					}
				});

			} else {
				latch.countDown();
			}
		}

		latch.await();
		pool.shutdown();
		CoolLogger.getLogger().info("初始化折合业务数据成功， 共构建业务数据[" + count.get() + "] 条");
	}

	/**
	 * 启动期间滚动线程和预测任务运行线程 期间滚动任务和预测运行任务创建的时候是互斥的，所以不会二者同时有任务需要运行
	 * 
	 * @throws Exception
	 */
	private void initAndStartTasksManagerThreads() throws Exception {
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;

		boolean isRollingPeriod = false;
		boolean isRuningForecast = false;
		try {
			trsa = session.beginTransaction();

			DaoSysPeriod daoSysPeriod = new DaoSysPeriod(session);
			SysPeriod currentSysPeriod = daoSysPeriod.getSysPeriod();
			if (currentSysPeriod == null) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_NO_RECORD_IN_SYSPERIOD);
				Exception ex = new Exception(cause);
				throw ex;
			}

			// 期间滚动任务 begin
			DaoPeriodRollTask daoPeriodRollTask = new DaoPeriodRollTask(session);

			List<PeriodRollTask> listPeriodRollTask = daoPeriodRollTask.getPeriodRollTasks(currentSysPeriod.getCompilePeriod());
			if (listPeriodRollTask != null && !(listPeriodRollTask.isEmpty())) {
				PeriodRollTask periodRollTask = null;
				for (int i = 0; i < listPeriodRollTask.size(); i = i + 1) {
					// periodRollTaskDAO.getPeriodRollTasks
					// 是按照seqNo升序查询的，这里不必再排序

					periodRollTask = listPeriodRollTask.get(i);

					if (periodRollTask.getStatus() != BizConst.PERIODROLLTASK_STATUS_RUNNED) {
						isRollingPeriod = true;
						break;
					}
				}
			}
			// 期间滚动任务 end

			// 预测运行任务 begin
			DaoForecastRunTask daoForecastRunTask = new DaoForecastRunTask(session);
			ForecastRunTask latestForecastRunTask = daoForecastRunTask.getLatestForecastRunTask(currentSysPeriod.getCompilePeriod());
			if (latestForecastRunTask != null && latestForecastRunTask.getStatus() == BizConst.FORECASTRUNTASK_STATUS_RUNNING) {
				isRuningForecast = true;
			}
			// 预测运行任务 end

			trsa.commit();
		} catch (Exception ex) {
			if (trsa != null) {
				trsa.rollback();
			}

			ex.printStackTrace();

			throw ex;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		if (isRollingPeriod == true) {
			ServerEnvironment.getInstance().setSystemStatus(BizConst.SYSTEM_STATUS_ROLLINGPERIOD);
		} else if (isRuningForecast == true) {
			ServerEnvironment.getInstance().setSystemStatus(BizConst.SYSTEM_STATUS_RUNNINGFORECAST);
		} else {
			ServerEnvironment.getInstance().setSystemStatus(BizConst.SYSTEM_STATUS_NORMAL);
		}

		PeriodRollTaskManagerThread periodRollTaskManagerThread = PeriodRollTaskManagerThread.getInstance();
		periodRollTaskManagerThread.start();

		ForecastRunTaskManagerThread forecastRunTaskManagerThread = ForecastRunTaskManagerThread.getInstance();
		forecastRunTaskManagerThread.start();

	}

	/**
	 * 版本检查、试用期时间检查、这里处理的东西你懂得
	 * 
	 * @return
	 */
	private boolean checkProbation() {
		BSysParam sysParam4Probation = ServerEnvironment.getInstance().getSysParam(BizConst.SYSPARAM_CODE_PROBATION);
		BSysParam sysParam4Genuine = ServerEnvironment.getInstance().getSysParam(BizConst.SYSPARAM_CODE_GENUINE);
		if (sysParam4Genuine == null)
			return false;
		else {
			String value_Genuine = sysParam4Genuine.getValue();
			if (!value_Genuine.startsWith(UtilMD5.getMD5Str("dmddGenuine")))// 说明为盗版或者试用版
			{
				if (sysParam4Probation == null)
					return false;
				else {
					String value = sysParam4Probation.getValue();
					String des = sysParam4Probation.getDescription();
					if (!UtilMD5.getMD5Str(value + "dmdd").equals(des))// 没用通过验证机制
						return false;
					else// 启动试用期监控线程
					{
						try {
							Scheduler SysProbationCheck_Secheuler = StdSchedulerFactory.getDefaultScheduler();
							JobDetail jobDetail = new JobDetail("SysProbationCheckJob", Scheduler.DEFAULT_GROUP, SysProbationCheckJob.class);
							CronTrigger SysProbationCheck_trigger = new CronTrigger("SysProbationCheck_trigger", null, "0 0 0/24 * * ?");
							SysProbationCheck_Secheuler.scheduleJob(jobDetail, SysProbationCheck_trigger);
							SysProbationCheck_Secheuler.start();

							// 启动时自动减少一天试用时间
							UtilSysProbation.ReductionDay();
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				}
			}
		}
		return true;
	}

}

/**************************************************************************
 * 
 * $RCSfile: DmddBootServlet.java,v $ $Revision: 1.3 $ $Date: 2010/07/17
 * 05:43:58 $
 * 
 * $Log: DmddBootServlet.java,v $ Revision 1.3 2010/07/17 05:43:58 liuzhen
 * 2010.07.17 by liuzhen Committed on the Free edition of March Hare Software
 * CVSNT Server. Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * 
 * Revision 1.2 2010/07/15 07:16:24 liuzhen 2010.07.15 by liuzhen Committed on
 * the Free edition of March Hare Software CVSNT Server. Upgrade to CVS Suite
 * for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision 1.1 2010/07/04 07:26:56 liuzhen 2010.07.04 by liuzhen Committed on
 * the Free edition of March Hare Software CVSNT Server. Upgrade to CVS Suite
 * for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision
 * 
 * 
 ***************************************************************************/
