
/**
 * 
 */

package dmdd.dmddjava.service.systemservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.bdconvertor.PeriodRollTaskBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.SysBakLogBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.SysDictionaryItemBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BPeriodRollTask;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.bizobject.BSysBakLog;
import dmdd.dmddjava.dataaccess.bizobject.BSysDictionaryItem;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.PeriodRollTask;
import dmdd.dmddjava.dataaccess.dataobject.SysBakLog;
import dmdd.dmddjava.dataaccess.dataobject.SysDictionaryItem;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoPeriodRollTask;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysBakLog;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysDictionaryItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.service.dimensionservice.OrganizationService;
import dmdd.dmddjava.service.dimensionservice.ProductService;
import dmdd.dmddjava.service.dimensionservice.UnitService;

/**
 * @author liuzhen
 * 
 */
public class SystemService
{

	/**
	 * 
	 */
	public SystemService()
	{
	}

	public List<Object> getClientEnvironmentDatas() throws Exception
	{
		List<Object> rstList = new ArrayList<Object>();

		// 0
		Date sysDateTime = this.getSystemDateTime();
		rstList.add( sysDateTime );
		
		// 1
		rstList.add( ServerEnvironment.getInstance().getSystemStatus() );
		
		//2 服务端版本
		rstList.add(ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_VERSION_SERVER));
		
		//3数据库版本
		rstList.add(ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_VERSION_DATABASE));
		
		//4数据库类型，oracle Or sqlserver
		rstList.add(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE));
		
		return rstList;
	}

	public List<Object> getClientEnvironmentMainDatas() throws Exception
	{
		List<Object> rstList = new ArrayList<Object>();

		// 0 系统参数
		List<BSysParam> list4bobjSysParam = this.getAllSysParams();
		rstList.add( list4bobjSysParam );			
		
		// 1 系统数据字典
		List<BSysDictionaryItem> list4bobjSysDictionaryItem = this.getAllSysDictionaryItems();
		rstList.add( list4bobjSysDictionaryItem );
			
		// 2 系统期间
		BSysPeriod bSysPeriod = this.getSysPeriod();
		rstList.add( bSysPeriod );
		
		
		// 3 产品树
		BProduct productTreeRoot = ServerEnvironment.getInstance().getBProductTreeRoot();
		rstList.add( productTreeRoot );	
		
		// 4 产品特征树
		BProductCharacter productCharacterTreeRoot = ServerEnvironment.getInstance().getBProductCharacterTreeRoot();
		rstList.add( productCharacterTreeRoot );			

		// 5 产品层次
		ProductService productService = new ProductService();
		List<BProductLayer> list4bobjProductLayer = productService.getAllProductLayers();
		rstList.add( list4bobjProductLayer );
		
		// 6 产品特征层次
		List<BProductCharacterLayer> list4bobjProductCharacterLayer = productService.getAllProductCharacterLayers();
		rstList.add( list4bobjProductCharacterLayer );		
		
		// 7 组织对
		
		BOrganization organizationTreeRoot = ServerEnvironment.getInstance().getBOrganizationTreeRoot();
		rstList.add( organizationTreeRoot );
		
		// 8 组织特征树
		BOrganizationCharacter organizationCharacterTreeRoot = ServerEnvironment.getInstance().getBOrganizationCharacterTreeRoot();
		rstList.add( organizationCharacterTreeRoot );		
		
		// 9 组织层次
		OrganizationService organizationService = new OrganizationService();
		List<BOrganizationLayer> list4bobjOrganizationLayer = organizationService.getAllOrganizationLayers();
		rstList.add( list4bobjOrganizationLayer );
		
		// 10 组织特征层次
		List<BOrganizationCharacterLayer> list4bobjOrganizationCharacterLayer = organizationService.getAllOrganizationCharacterLayers();
		rstList.add( list4bobjOrganizationCharacterLayer );		
		
		//11 计量单位
		UnitService unitService= new UnitService();
		List<BUnitGroup> list4unitGroup = unitService.getUnitGroups("", true, -1, -1);
		rstList.add( list4unitGroup );
		
		//12 菜单XML
		rstList.add(ServerEnvironment.getInstance().getMenuXmlStr());
		
		//13 DC
		rstList.add(ServerEnvironment.getInstance().getbDistributionCenterTreeRoot());
		
		return rstList;
	}
	
	/**
	 * 刷新服务端主数据缓存
	 * @return
	 * @throws Exception
	 */
	public String refreshServerMainDataCache() throws Exception{
		
		return "Refresh Ok!";
	}
	
	public List<BSysDictionaryItem> getAllSysDictionaryItems() throws Exception
	{
		List<BSysDictionaryItem> rstList = new ArrayList<BSysDictionaryItem>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoSysDictionaryItem daoSysDictionaryItem = new DaoSysDictionaryItem( session );
			List<SysDictionaryItem> listSysDictionaryItem_inDB = daoSysDictionaryItem.getAllSysDictionaryItems();
			
			if( listSysDictionaryItem_inDB != null )
			{
				SysDictionaryItemBDConvertor sysDictionaryItemBDConvertor = new SysDictionaryItemBDConvertor();
				for( int i = 0; i < listSysDictionaryItem_inDB.size(); i++ )
				{
					rstList.add( (BSysDictionaryItem) sysDictionaryItemBDConvertor.dtob( listSysDictionaryItem_inDB.get( i ) ) );
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

		return rstList;
	}
	

	/**
	 * 
	 * @return
	 */
	public List<BSysParam> getAllSysParams()
	{
		//	系统参数在服务器环境变量中常驻
		List<BSysParam> rstList = ServerEnvironment.getInstance().getAllSysParams();
		return rstList;
	}	
	

	/**
	 * 
	 * @return
	 */
	public BSysPeriod getSysPeriod()
	{
		//	系统期间在服务器环境变量中常驻
		BSysPeriod bSysPeriod_rst = ServerEnvironment.getInstance().getSysPeriod();
		return bSysPeriod_rst;
	}
	
	
	/**
	 * 
	 * @param _bSysPeriod
	 * @return
	 * @throws Exception
	 */
	public List<BPeriodRollTask> getPeriodRollTasks( BSysPeriod _bSysPeriod ) throws Exception
	{
		if( _bSysPeriod == null )
		{
			throw new Exception("parameter _bSysPeriod is invalid ");
		}
		
		if( UtilPeriod.checkPeriod( _bSysPeriod.getCompilePeriod() ) == false )
		{
			throw new Exception("parameter _bSysPeriod is invalid ");
		}
		
		List<BPeriodRollTask> rstList = new ArrayList<BPeriodRollTask>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
				
			DaoPeriodRollTask daoPeriodRollTask = new DaoPeriodRollTask(session);
			List<PeriodRollTask> listPeriodRollTask_inDB = daoPeriodRollTask.getPeriodRollTasks(  _bSysPeriod.getCompilePeriod() );
			if( listPeriodRollTask_inDB != null && !(listPeriodRollTask_inDB.isEmpty()) )
			{
				PeriodRollTaskBDConvertor periodRollTaskBDConvertor = new PeriodRollTaskBDConvertor();
				for( int i=0; i<listPeriodRollTask_inDB.size(); i++ )
				{
					rstList.add( (BPeriodRollTask)periodRollTaskBDConvertor.dtob( listPeriodRollTask_inDB.get( i ) ) ); 
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

		return rstList;
	}	
	
	/**
	 * 
	 * @param _bSysPeriod
	 * @throws Exception
	 */
	public void createPeriodRollTasks( BSysPeriod _bSysPeriod4current ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod4current == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod4current.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
				
			DaoPeriodRollTask daoPeriodRollTask = new DaoPeriodRollTask(session);
			
			//	检查 _currentCompilePeriod 是否已将创建过滚动任务		begin
			List<PeriodRollTask> listPeriodRollTask_inDB = daoPeriodRollTask.getPeriodRollTasks( _bSysPeriod4current.getCompilePeriod() );
			if( listPeriodRollTask_inDB != null && !(listPeriodRollTask_inDB.isEmpty()) )
			{				
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_PERIODROLLTASK_CREATED );
				Exception ex = new Exception( cause );
				throw ex;					
			}
			//	而且数据库中加了(compilePeriod,seqNo)的唯一性约束，即使这里检查不出来，数据库也会检查
			//	检查 _bCompilePeriod 是否已将创建过滚动任务		end			
			
			DaoSystem daoSystem = new DaoSystem(session);
		
			PeriodRollTask newPeriodRollTask = null;
			int seqNo = 0;
			
			//	2010.12.10 by liuzhen	数据框架中设计，期间滚动时，不会对新开的历史数据操作期间生成新的0数据，因此历史数据滚动任务不必创建	begin
			
//			//	创建 历史数据 滚动任务		begin
//			//	以明细组织为单位，每个明细组织一个滚动任务
//			DaoOrganization daoOrganization = new DaoOrganization( session );
//			
//			String sqlRestrion4detailOrganization = "( isValid = " + BizConst.GLOBAL_YESNO_YES + " ) and ( isCatalog = " + BizConst.GLOBAL_YESNO_NO + " ) ";
//			List<Organization> list4detailOrgainzation = daoOrganization.getOrganizations( sqlRestrion4detailOrganization );
//			if( list4detailOrgainzation != null && !(list4detailOrgainzation.isEmpty()) )
//			{
//				for( int i=0; i<list4detailOrgainzation.size(); i++ )
//				{
//					Organization detailOrganization = list4detailOrgainzation.get( i );
//					
//					newPeriodRollTask = new PeriodRollTask();
//					newPeriodRollTask.setCompilePeriod( _bSysPeriod4current.getCompilePeriod() );
//					newPeriodRollTask.setSeqNo( seqNo++ );
//					newPeriodRollTask.setCategory( BizConst.PERIODROLLTASK_CATEGORY_HISTORYDATA );
//					newPeriodRollTask.setStatus( BizConst.PERIODROLLTASK_STATUS_TORUN );
//					newPeriodRollTask.setCreateTime( daoSystem.getSysTimeAsTimeStamp() );
//					newPeriodRollTask.setBeginTime( null );
//					newPeriodRollTask.setEndTime( null );
//					newPeriodRollTask.setComments( null );
//					newPeriodRollTask.setForecastInst( null );
//					newPeriodRollTask.setOrganization( detailOrganization );
//
//					daoPeriodRollTask.save( newPeriodRollTask );					
//				}
//			}
//			//	创建 历史数据 滚动任务		end
			
			//	2010.12.10 by liuzhen	数据框架中设计，期间滚动时，不会对新开的历史数据操作期间生成新的0数据，因此历史数据滚动任务不必创建	end
			
			
			//	创建 预测类别 滚动任务		begin
			//	以预测类别为单位，每个预测类别一个滚动任务
			DaoForecastInst daoForecastInst = new DaoForecastInst(session);
			
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES, BizConst.GLOBAL_YESNO_NO };
			List<ForecastInst> list4ForecastInstInDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );
			if( list4ForecastInstInDB != null && !(list4ForecastInstInDB.isEmpty()) )
			{
				for( int i=0; i<list4ForecastInstInDB.size(); i++ )
				{
					newPeriodRollTask = new PeriodRollTask();
					newPeriodRollTask.setCompilePeriod( _bSysPeriod4current.getCompilePeriod() );
					newPeriodRollTask.setSeqNo( seqNo++ );
					newPeriodRollTask.setCategory( BizConst.PERIODROLLTASK_CATEGORY_FORECASTINST );
					newPeriodRollTask.setStatus( BizConst.PERIODROLLTASK_STATUS_TORUN );
					newPeriodRollTask.setCreateTime( daoSystem.getSysTimeAsTimeStamp() );
					newPeriodRollTask.setBeginTime( null );
					newPeriodRollTask.setEndTime( null );
					newPeriodRollTask.setComments( null );
					newPeriodRollTask.setForecastInst( list4ForecastInstInDB.get( i ) );
					newPeriodRollTask.setOrganization( null );
					
					daoPeriodRollTask.save( newPeriodRollTask );
				}
			}
			//	创建 预测类比 滚动任务		end			
			
			//	创建 编制期间 滚动任务		begin
			//	期间任务一定要最后生成
			newPeriodRollTask = new PeriodRollTask();
			newPeriodRollTask.setCompilePeriod( _bSysPeriod4current.getCompilePeriod() );
			newPeriodRollTask.setSeqNo( seqNo++ );
			newPeriodRollTask.setCategory( BizConst.PERIODROLLTASK_CATEGORY_PERIOD );
			newPeriodRollTask.setStatus( BizConst.PERIODROLLTASK_STATUS_TORUN );
			newPeriodRollTask.setCreateTime( daoSystem.getSysTimeAsTimeStamp() );
			newPeriodRollTask.setBeginTime( null );
			newPeriodRollTask.setEndTime( null );
			newPeriodRollTask.setComments( null );
			newPeriodRollTask.setForecastInst( null );
			newPeriodRollTask.setOrganization( null );

			daoPeriodRollTask.save( newPeriodRollTask );			
			//	创建 编制期间 滚动任务		end						
			
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
		
		//	设置系统状态		begin
		//	PeriodRollTaskManagerThread 下次醒来时会处理滚动任务
		ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_ROLLINGPERIOD );
		//	设置系统状态		end		
	}		
	

	public Date getSystemDateTime() throws Exception
	{
		Date rstDate = new Date();
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoSystem daoSystem = new DaoSystem( session );
			rstDate = daoSystem.getSysTimeAsTimeStamp();
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
		return rstDate;
	}

	public Date getSystemDate() throws Exception
	{
		Date rstDate = new Date();
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 
			trsa = session.beginTransaction();
			DaoSystem daoSystem = new DaoSystem( session );
			rstDate = daoSystem.getSysTimeAsDate();
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
		return rstDate;
	}

	/**
	 * 获取AD开关
	 * @return
	 */
	public String getStatusADOpen()
	{
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ISOPEN)==null || ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ISOPEN).equals(""))
			return "false";
		else
			return ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ISOPEN);
	}
	
	public int getSystemStatus()
	{
		return ServerEnvironment.getInstance().getSystemStatus();
	}
	
	public boolean doSysBak(BSysBakLog _bSysBakLog) throws Exception
	{
		int periodBegin	=	_bSysBakLog.getBeginPeroid();
		int periodEnd	= _bSysBakLog.getEndPeroid();
		
		if(_bSysBakLog.getBakHistoryData() == BizConst.GLOBAL_YESNO_YES)
		{
			deleteHistoryData(periodBegin,periodEnd);
		}
		
		if(_bSysBakLog.getBakHistoryAdjustLog() == BizConst.GLOBAL_YESNO_YES)
		{
			deleteHistoryAdjustLog(periodBegin,periodEnd);
		}
		
		if(_bSysBakLog.getBakPriceData() == BizConst.GLOBAL_YESNO_YES)
		{
			deletePriceData( periodBegin, periodEnd );
		}
		
		if(_bSysBakLog.getBakForecastData() == BizConst.GLOBAL_YESNO_YES)
		{
			deleteForecastData( periodBegin, periodEnd );
		}
		
		if(_bSysBakLog.getBakForecastMakeLog() == BizConst.GLOBAL_YESNO_YES)
		{
			deleteForecastMakeLog( periodBegin, periodEnd );
		}
		
		SysBakLogBDConvertor bd = new SysBakLogBDConvertor();
		SysBakLog newSysBakLog =(SysBakLog) bd.btod( _bSysBakLog );
		
		//写备份日志
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 			
			trsa = session.beginTransaction();
			DaoSystem daoSystem = new DaoSystem( session );
			Date rstDate = daoSystem.getSysTimeAsTimeStamp();			
			DaoSysBakLog dao = new DaoSysBakLog( session );
			newSysBakLog.setExcuteTime( rstDate );
			dao.save( newSysBakLog );
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
		
		return true;
	}
	
	public List<BSysBakLog> getSysBakLog() throws Exception
	{
		List<BSysBakLog> rstRes = new ArrayList<BSysBakLog>();
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 			
			trsa = session.beginTransaction();
			DaoSysBakLog dao = new DaoSysBakLog(session);
			List<SysBakLog> list= dao.getAllSysBakLog();
			SysBakLogBDConvertor bd = new SysBakLogBDConvertor();
			for(SysBakLog sysBakLog:list)
			{
				rstRes.add( ( BSysBakLog)bd.dtob( sysBakLog ));
			}
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
		return rstRes;
	}
	
	private boolean deleteHistoryData(int periodBegin,int periodEnd) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 
			trsa = session.beginTransaction();
			String sql="delete from HistoryData where period>="+periodBegin +" and period<="+periodEnd;
			Query q = session.createQuery(sql);
			q.executeUpdate();
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

		return true;
	}
	
	private boolean deleteHistoryAdjustLog(int periodBegin,int periodEnd) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 
			trsa = session.beginTransaction();
			String sql="delete from HistoryAdjustLog where compilePeriod>="+periodBegin +" and compilePeriod<="+periodEnd;
			Query q = session.createQuery(sql);
			q.executeUpdate();
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

		return true;
	}
	
	private boolean deletePriceData(int periodBegin,int periodEnd) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 
			trsa = session.beginTransaction();
			String sql="delete from PriceData where period>="+periodBegin +" and period<="+periodEnd;
			Query q = session.createQuery(sql);
			q.executeUpdate();
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

		return true;
	}
	
	private boolean deleteForecastData(int periodBegin,int periodEnd) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 
			trsa = session.beginTransaction();
			String sql="delete from ForecastData where period>="+periodBegin +" and period<="+periodEnd;
			Query q = session.createQuery(sql);
			q.executeUpdate();
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

		return true;
	}
	
	private boolean deleteForecastMakeLog(int periodBegin,int periodEnd) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{ 
			trsa = session.beginTransaction();
			String sql="delete from ForecastMakeLog where compilePeriod>="+periodBegin +" and compilePeriod<="+periodEnd;
			Query q = session.createQuery(sql);
			q.executeUpdate();
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

		return true;
	}
	
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{

	}

}

/************************************************************************
 * $RCSfile: SystemService.java,v $  $Revision: 1.3 $  $Date: 2010/08/05 12:46:17 $
 * $Log: SystemService.java,v $
 * Revision 1.3  2010/08/05 12:46:17  liuzhen
 * 2010.08.05 by liuzhen
 * �ڼ�����������ʷ��ݹ�������ϸ��Ϊ����ϸ��֯Ϊ��λ�Ĺ�������
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.2  2010/07/15 07:16:24  liuzhen
 * 2010.07.15 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2010/07/04 07:26:56  liuzhen
 * 2010.07.04 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 
 *
 ************************************************************************/
