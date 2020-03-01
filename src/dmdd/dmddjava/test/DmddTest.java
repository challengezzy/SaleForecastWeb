package dmdd.dmddjava.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.cool.common.logging.CoolLogger;
import com.cool.common.system.CoolServerEnvironment;
import com.cool.dbaccess.CommDMO;
import com.cool.dbaccess.DataSourceManager;

import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.enums.SafeStockMode;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilMD5;
import dmdd.dmddjava.dataaccess.bdconvertor.SysParamBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.SysPeriodBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.SysParam;
import dmdd.dmddjava.dataaccess.dataobject.SysPeriod;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysParam;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysPeriod;
import dmdd.dmddjava.service.forecastservice.PeriodVersionService;
import dmdd.dmddjava.service.replenish.ReplenishComputeService;
import dmdd.dmddjava.service.replenish.ReplenishQueryService;

public class DmddTest {

	public static String configFileUrl = "dmdd/configure/coolconf/CoolConfig.xml";

	private static void initConfigure(String sysRootPath) throws JDOMException, IOException {
		long ll_1 = System.currentTimeMillis();
		InputStream is = DmddTest.class.getClassLoader().getResourceAsStream(configFileUrl);
		Document doc = new SAXBuilder().build(is);

		CoolLogger.getLogger(DmddTest.class).debug("开始初始化数据库连接池...");
		Element datasources = doc.getRootElement().getChild("datasources"); // 得到datasources子结点!!
		if (datasources != null) {
			List sources = datasources.getChildren("datasource"); // 得到所有子结点!!
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
		CoolLogger.getLogger(DmddTest.class).debug("初始化数据库连接池结束,耗时[" + (ll_2 - ll_1) + "]");

		String str_key = "WebAppRealPath";

		try {
			List<Element> initparams = doc.getRootElement().getChildren("init-param");
			if (initparams != null) {
				for (int i = 0; i < initparams.size(); i++) {
					if (initparams.get(i) instanceof org.jdom.Element) {
						org.jdom.Element param = (org.jdom.Element) initparams.get(i);
						if (param != null) {
							str_key = param.getAttributeValue("key");
							String str_value = param.getAttributeValue("value");
							String str_descr = param.getAttributeValue("descr");
							CoolLogger.getLogger(DmddTest.class).debug("配置参数：" + str_key + " 取值：" + str_value + " 解释：" + str_descr);
							CoolServerEnvironment.getInstance().put(str_key, str_descr, str_value); // 往缓存中送入...
							CoolLogger.getLogger(DmddTest.class).debug("[" + str_key + "] = [" + str_value + "]");
						}
					}
				}

			}

			ll_2 = System.currentTimeMillis();
			CoolLogger.getLogger(DmddTest.class).debug("初始化基本参数结束,耗时[" + (ll_2 - ll_1) + "]");
		} catch (Throwable tr) {
			tr.printStackTrace();
		}

	}
	
	private static void initSeverEnvironment() throws Exception
	{
		//	系统期间		begin
		BSysPeriod bSysPeriod = null;
		SysPeriodBDConvertor sysPeriodBDConvertor = new SysPeriodBDConvertor();
		
		Transaction trsa = null;
		Session session = HibernateSessionFactory.getSession();
		try
		{
			trsa = session.beginTransaction();
			DaoSysPeriod daoSysPeriod = new DaoSysPeriod( session );
			SysPeriod currentSysPeriod = daoSysPeriod.getSysPeriod();
			if( currentSysPeriod == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_NO_RECORD_IN_SYSPERIOD );
				Exception ex = new Exception( cause );
				throw ex;
			}
			bSysPeriod = (BSysPeriod)sysPeriodBDConvertor.dtob( currentSysPeriod );
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
		ServerEnvironment.getInstance().setSysPeriod( bSysPeriod );
		//	系统期间		end
		
		//	系统参数		begin
		session = HibernateSessionFactory.getSession();
		try
		{
			trsa = session.beginTransaction();
			DaoSysParam daoSysParam = new DaoSysParam( session );
			List<SysParam> sysPamramList_inDB = daoSysParam.getAllSysParams();
			if( sysPamramList_inDB != null )
			{
				SysParamBDConvertor sysParamBDConvertor = new SysParamBDConvertor();
				for( int i=0; i<sysPamramList_inDB.size(); i++ )
				{
					ServerEnvironment.getInstance().putSysParam( (BSysParam)sysParamBDConvertor.dtob( sysPamramList_inDB.get( i ) ) );
				}
			}
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}		
		//	系统参数		end		
		
		//配置文件参数 start
		//ServerEnvironment.getInstance().loadConfigFileParam();
		//配置文件参数 end
		
//		//	内存常驻变量	begin
//		ProductService productService = new ProductService();
//		//	注意下面的顺序一定是先特征后产品，因为产品的productService.getProductTreeRoot()中会用特征树的
//		ServerEnvironment.getInstance().setBProductCharacterTreeRoot( productService.getProductCharacterTreeRoot() );
//		ServerEnvironment.getInstance().setBProductTreeRoot( productService.getProductTreeRoot() );
//		
//		
//		OrganizationService organizationService = new OrganizationService();
//		//	注意下面的顺序一定是先特征后组织，因为组织的organizationService.getOrganizationTreeRoot()中会用特征树的
//		ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot( organizationService.getOrganizationCharacterTreeRoot() );
//		ServerEnvironment.getInstance().setBOrganizationTreeRoot( organizationService.getOrganizationTreeRoot() );
//		//	内存常驻变量	end
	}

	public static void main(String[] args) {
		CommDMO dmo = new CommDMO();
		try {
			// 初始化数据库配置信息
			initConfigure("");
			//initSeverEnvironment(); 
//
//			replenishCompute();
			String value = "31";
			String des = "3bc99c86844223f34388d16f92285119";
			String md5 = UtilMD5.getMD5Str(value+"dmdd");
			System.out.println("des=" + des + ",    md5="+md5);
			
			periodVersionTest();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放数据库连接，非常重要，数据库连接是有限的！！！！！！！！！
			// 每次事务操作完成后，均应该释放连接
			// dmo.releaseContext(ApmsConst.DS_DEFAULT);
			dmo.releaseContext();
		}
	}

	public static void replenishCompute() throws Exception {
		ReplenishComputeService service = new ReplenishComputeService();
		
		service.replenishCompute("test",SafeStockMode.Aervage,3);
	}
	
	public static void periodVersionTest() throws Exception{
		PeriodVersionService versionService = new PeriodVersionService();
		versionService.updateAllPeriodVersionData(201706);
	}
	
	public static void replenishQuery() throws Exception{
		ReplenishQueryService service = new ReplenishQueryService();
		ArrayList<String> pidList = new ArrayList<String>();
		ArrayList<String> dcidList = new ArrayList<String>();
		
		pidList.add("20");
		pidList.add("21");
		pidList.add("22");
		
		dcidList.add("16");
		dcidList.add("18");
		dcidList.add("19");
		
		int period = 201212;
		
		service.getReplenishDataByPeriod(pidList, dcidList, period);
		
	}

}
