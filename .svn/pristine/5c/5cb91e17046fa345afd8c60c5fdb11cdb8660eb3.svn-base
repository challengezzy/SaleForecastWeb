/**
 * 
 */

package dmdd.dmddjava.service.systemservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.bdconvertor.SysPeriodBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstProOrg;
import dmdd.dmddjava.dataaccess.dataobject.PeriodRollTask;
import dmdd.dmddjava.dataaccess.dataobject.SysPeriod;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInstProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoPeriodRollTask;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysPeriod;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.service.forecastservice.PeriodVersionService;

/**
 * @author liuzhen
 * 
 */
public class PeriodRollTaskManagerThread extends Thread
{
	private final Logger						logger		= Logger.getLogger( this.getClass().getName() );
	private static PeriodRollTaskManagerThread	theInstance	= null;

	public synchronized static PeriodRollTaskManagerThread getInstance()
	{
		if( theInstance == null )
		{
			theInstance = new PeriodRollTaskManagerThread();
		}
		return theInstance;
	}

	/**
	 * 
	 */
	public PeriodRollTaskManagerThread()
	{
		logger.info( "创建 PeriodRollTaskManagerThread ..." );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		try
		{
			while( !interrupted() )
			{
				if( ServerEnvironment.getInstance().getSystemStatus() == BizConst.SYSTEM_STATUS_ROLLINGPERIOD )
				{
					logger.info( " PeriodRollTaskManagerThread process start!" );
					processPeriodRollTask();
					logger.info( " PeriodRollTaskManagerThread process finish!" );
				}
				sleep( 1000 ); // 睡眠
			}
		}
		catch( InterruptedException ex )
		{
			interrupt();
		}
		catch( Exception ex )
		{
			// 滚动任务异常终止的话，停止线程，即不再sleep
			ex.printStackTrace();
		}
	}

	private void processPeriodRollTask() throws Exception
	{
		logger.info("开始处理期间滚动任务");
		List<PeriodRollTask> listPeriodRollTask = null;
		SysPeriod currentSysPeriod = null;
		
		//	读出本期的期间滚动任务	begin
		Session session_readTask = HibernateSessionFactory.getSession();
		Transaction trsa_readTask = null;
		try
		{
			trsa_readTask = session_readTask.beginTransaction();
			
			DaoSysPeriod daoSysPeriod = new DaoSysPeriod( session_readTask );
			currentSysPeriod = daoSysPeriod.getSysPeriod();
			if( currentSysPeriod == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_NO_RECORD_IN_SYSPERIOD );
				Exception ex = new Exception( cause );
				throw ex;
			}

			DaoPeriodRollTask daoPeriodRollTask_readTask = new DaoPeriodRollTask( session_readTask );

			listPeriodRollTask = daoPeriodRollTask_readTask.getPeriodRollTasks( currentSysPeriod.getCompilePeriod() );
			
			trsa_readTask.commit();
			
		}
		catch( Exception ex )
		{
			if( trsa_readTask != null )
			{
				trsa_readTask.rollback();
			}
			
			ex.printStackTrace();

			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD );
			throw ex;
		}
		finally
		{
			if( session_readTask != null && session_readTask.isOpen() )
			{
				session_readTask.close();
			}
		}	
		//	读出本期的期间滚动任务	end
		
		logger.info("期间滚动任务，当前期间[" + currentSysPeriod.getCompilePeriod() + "]，滚动预测策略任务数["+listPeriodRollTask.size()+"]个。");
		
		//	逐个处理期间滚动任务		begin	
		if( listPeriodRollTask != null && !(listPeriodRollTask.isEmpty()) )
		{
			PeriodRollTask periodRollTask = null;
			for( int i = 0; i < listPeriodRollTask.size(); i = i + 1 )
			{
				// daoPeriodRollTask.getPeriodRollTasks
				// 是按照seqNo升序查询的，这里不必再排序
				periodRollTask = listPeriodRollTask.get( i );
				if( periodRollTask.getStatus() == BizConst.PERIODROLLTASK_STATUS_TORUN )
				{
					Session session_runTask = HibernateSessionFactory.getSession();
					Transaction trsa_runtask = null;					
					try
					{
						trsa_runtask = session_runTask.beginTransaction();

						DaoSystem daoSystem_runTask = new DaoSystem( session_runTask );
						DaoPeriodRollTask daoPeriodRollTask_runTask = new DaoPeriodRollTask( session_runTask );
						
						periodRollTask.setStatus( BizConst.PERIODROLLTASK_STATUS_RUNNING );
						periodRollTask.setBeginTime( daoSystem_runTask.getSysTimeAsTimeStamp() );
						daoPeriodRollTask_runTask.update( periodRollTask );
						
						trsa_runtask.commit();						
					}
					catch( Exception ex )
					{
						if( trsa_runtask != null )
						{
							trsa_runtask.rollback();
						}
						ex.printStackTrace();

						ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD );
						throw ex;
					}
					finally
					{
						if( session_runTask != null && session_runTask.isOpen() )
						{
							session_runTask.close();
						}
					}					
				}

				if( periodRollTask.getStatus() == BizConst.PERIODROLLTASK_STATUS_RUNNING )
				{
					//	2010.12.10 by liuzhen	数据框架中设计，期间滚动时，不会对新开的历史数据操作期间生成新的0数据，因此历史数据滚动任务不必创建	begin
//					if( periodRollTask.getCategory() == BizConst.PERIODROLLTASK_CATEGORY_HISTORYDATA )
//					{
//						this.periodRollTaskRun_HistoryData( periodRollTask, currentSysPeriod );
//					}
//					else if( periodRollTask.getCategory() == BizConst.PERIODROLLTASK_CATEGORY_PERIOD )
					//编制期间任务
					if( periodRollTask.getCategory() == BizConst.PERIODROLLTASK_CATEGORY_PERIOD )
					//	2010.12.10 by liuzhen	数据框架中设计，期间滚动时，不会对新开的历史数据操作期间生成新的0数据，因此历史数据滚动任务不必创建	end
					{
						this.periodRollTaskRun_Period( periodRollTask, currentSysPeriod );
					}
					else if( periodRollTask.getCategory() == BizConst.PERIODROLLTASK_CATEGORY_FORECASTINST )
					{
						//预测策略类任务
						this.periodRollTaskRun_ForecatsInst( periodRollTask, currentSysPeriod );
					}
				}
				
				logger.info("第["+ (i+1)  +"]个滚动任务执行完毕.");
			}
		}
		//	逐个处理期间滚动任务		end		

		//	更新服务器环境变量	begin
		SysPeriodBDConvertor sysPeriodBDConvertor = new SysPeriodBDConvertor();
		Session session_serverenvironment = HibernateSessionFactory.getSession();		
		Transaction trsa_serverenvironment = null;
		try
		{
			trsa_serverenvironment = session_serverenvironment.beginTransaction();
			
			DaoSysPeriod daoSysPeriod = new DaoSysPeriod( session_serverenvironment );
			currentSysPeriod = daoSysPeriod.getSysPeriod();
			if( currentSysPeriod == null )
			{
				Exception ex_unfound = new Exception( "There is no record in database table COMPILEPERIOD" );
				throw ex_unfound;
			} 
			ServerEnvironment.getInstance().setSysPeriod( (BSysPeriod)sysPeriodBDConvertor.dtob( currentSysPeriod ) );
			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_NORMAL );
			
			trsa_serverenvironment.commit();
			
		}
		catch( Exception ex )
		{
			if( trsa_serverenvironment != null )
			{
				trsa_serverenvironment.rollback();
			}
			
			ex.printStackTrace();

			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD );
			throw ex;
		}
		finally
		{
			if( session_serverenvironment != null && session_serverenvironment.isOpen() )
			{
				session_serverenvironment.close();
			}
		}					
		//	更新服务器环境变量	end
	}

	/**
	 * 2010.12.10 by liuzhen	数据框架中设计，期间滚动时，不会对新开的历史数据操作期间生成新的0数据，因此历史数据滚动任务不必创建，这个方法也不再使用
	 * @param _periodRollTask_HistoryData
	 * @param _currentSysPeriod
	 * @throws Exception
	 */
	/*
	private void periodRollTaskRun_HistoryData( PeriodRollTask _periodRollTask_HistoryData, SysPeriod _currentSysPeriod ) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();		
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			//	重新读取_periodRollTask_HistoryData 	begin
			DaoPeriodRollTask daoPeriodRollTask = new DaoPeriodRollTask(session);
			PeriodRollTask periodRollTask_HistoryData_inDB = daoPeriodRollTask.getPeriodRollTaskById( _periodRollTask_HistoryData.getId() );
			if( periodRollTask_HistoryData_inDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_NO_RECORD_IN_SYSPERIOD );
				Exception ex = new Exception( cause );
				throw ex;
			}
			//	重新读取_periodRollTask_HistoryData	end			

			// 业务数据 begin
			DaoBizData daoBizData = new DaoBizData( session );

			Integer[] arr4BizDataIsValid4His = new Integer[] { BizConst.GLOBAL_YESNO_YES };
			Integer[] arr4BizDataType = new Integer[] { BizConst.BIZDATA_TYPE_HISTORY, BizConst.BIZDATA_TYPE_HISTORYAD };

			List<BizData> list4historyBizData = daoBizData.getBizDatasByTypes( arr4BizDataType, arr4BizDataIsValid4His );
			// 业务数据 end


			//	生成历史类业务数据	begin
			DaoProduct daoProduct = new DaoProduct( session );
			
			String sqlRestrion4detailProduct = "( isValid = " + BizConst.GLOBAL_YESNO_YES + " ) and ( isCatalog = " + BizConst.GLOBAL_YESNO_NO + " ) ";
			List<Product> list4detailProduct = daoProduct.getProducts( sqlRestrion4detailProduct );
			
			if( list4detailProduct != null && !(list4detailProduct.isEmpty()) && list4historyBizData != null && !(list4historyBizData.isEmpty()) )
			{
				DaoHistoryData daoHistoryData = new DaoHistoryData( session );
				
				int period = UtilPeriod.getPeriod( _currentSysPeriod.getHistoryOpenPeriod(), 1 );
				Organization detailOrganization = periodRollTask_HistoryData_inDB.getOrganization();
				
				for( int i=0; i<list4detailProduct.size(); i++ )
				{
					Product detailProduct = list4detailProduct.get( i );
					
					for( int j=0; j<list4historyBizData.size(); j++ )
					{
						BizData historyBizData = list4historyBizData.get( j );
						
						HistoryData newHistoryData = new HistoryData();
						newHistoryData.setPeriod( period );
						newHistoryData.setValue( 0 );
						newHistoryData.setBizData( historyBizData );
						newHistoryData.setOrganization( detailOrganization );
						newHistoryData.setProduct( detailProduct );
						
						daoHistoryData.save( newHistoryData );						
					}
				}
			}
			//	生成历史类业务数据	end

			// 更新 _periodRollTask_HistoryData 的状态 begin
			DaoSystem daoSystem = new DaoSystem( session );

			periodRollTask_HistoryData_inDB.setStatus( BizConst.PERIODROLLTASK_STATUS_RUNNED );
			periodRollTask_HistoryData_inDB.setEndTime( daoSystem.getSysTimeAsTimeStamp() );
			//	数据库读出的，不必显示条用update方法
			// 更新 _periodRollTask_HistoryData 的状态 end

			trsa.commit();

		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();

			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD );
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}			
	}
	*/


	/**
	 * 所有预测类别滚动完成后，要把期间向前滚一步
	 * 这个时候，新的期间的有效的预测数据的范围已经确定：所有的预测类别都已经滚达新的期间，预测数据范围由预测类别的预测范围决定
	 * 这个时候，需要把不需要的预测数据清除掉：
	 * 		(1)	预测范围之外，期间在当前预测开始期间（包含预测开始期间）之后的
	 * 		(2)	预测范围之内，期间在预测期间之后的
	 */
	private void periodRollTaskRun_Period( PeriodRollTask _periodRollTask_Period, SysPeriod _currentSysPeriod ) throws Exception
	{
		long start = System.currentTimeMillis();
		
		//zhangzy 20171203 版本m-n版本预测数据
		logger.info("开始执行版本m-n版本预测数据复制任务");
		
		PeriodVersionService versionService = new PeriodVersionService(); 
		versionService.updateAllPeriodVersionData(_currentSysPeriod.getCompilePeriod());
		
		logger.info("开始执行编制期间类滚动 任务");
		
		Session session = HibernateSessionFactory.getSession();	
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			// 保存_currentSysPeriod begin
			DaoSysPeriod daoSysPeriod = new DaoSysPeriod( session );

			_currentSysPeriod.setCompilePeriod( UtilPeriod.getPeriod( _currentSysPeriod.getCompilePeriod(), 1 ) );
			_currentSysPeriod.setHistoryValidPeriod( UtilPeriod.getPeriod( _currentSysPeriod.getHistoryValidPeriod(), 1 ) );
			_currentSysPeriod.setHistoryOpenPeriod( UtilPeriod.getPeriod( _currentSysPeriod.getHistoryOpenPeriod(), 1 ) );
			_currentSysPeriod.setForecastRunPeriod( UtilPeriod.getPeriod( _currentSysPeriod.getForecastRunPeriod(), 1 ) );
			_currentSysPeriod.setForecastDispPeriod( UtilPeriod.getPeriod( _currentSysPeriod.getForecastDispPeriod(), 1 ) );

			daoSysPeriod.update( _currentSysPeriod );
			// 保存_currentSysPeriod end
			
			//	释放审核冻结的状态	begin
			DaoForecastData daoForecastData = new DaoForecastData( session );
			daoForecastData.activeForecastDatas( _currentSysPeriod.getForecastRunPeriod(), SysConst.PERIOD_NULL );
			//	释放审核冻结的状态	end
			
			//	2010.12.10 by liuzhen 清除不在预测范围之内的预测数据		begin
			String idsScopeStr4DetailProOrgs_total = "( (-1,-1) ";
			
			//	逐个预测类别	begin
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES };
			List<ForecastInst> list4ForecastInstInDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );
			if( list4ForecastInstInDB != null && list4ForecastInstInDB.size() > 0 )
			{
				for( int i=0; i<list4ForecastInstInDB.size(); i++ )
				{
					ForecastInst forecastInst_inDB = list4ForecastInstInDB.get( i );
					if( forecastInst_inDB.getForecastInstProOrgs() != null && !(forecastInst_inDB.getForecastInstProOrgs().isEmpty()) )
					{
						HashMap<String, AProOrg> hmap_DetailProOrg = UtilProOrg.getDetailProOrgs4ForecastInst( forecastInst_inDB.getForecastInstProOrgs(), true );
						if( hmap_DetailProOrg != null && !(hmap_DetailProOrg.isEmpty()) )
						{
							String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( hmap_DetailProOrg );
							
							if( idsScopeStr4DetailProOrgs != null && !(idsScopeStr4DetailProOrgs.trim().equals( "" )) )
							{
								//	清除范围内预测期间之外的预测数据	begin
								int period_begin4clear = UtilPeriod.getPeriod( _currentSysPeriod.getForecastRunPeriod(), forecastInst_inDB.getFcPeriodNum() );
								daoForecastData.clearForecastDatasInScope( idsScopeStr4DetailProOrgs, period_begin4clear, SysConst.PERIOD_NULL );
								//	清除范围内预测期间之外的预测数据	end
								
								//	把范围加入到总范围	begin
								String content4idsScopeStr4DetailProOrgs = UtilProOrg.getContentOfIdsScopeStr( idsScopeStr4DetailProOrgs );
								idsScopeStr4DetailProOrgs_total = idsScopeStr4DetailProOrgs_total + " , " + content4idsScopeStr4DetailProOrgs;
								//	把范围加入到总范围	end
							}

						}
					}
				}
			}
			
			idsScopeStr4DetailProOrgs_total = idsScopeStr4DetailProOrgs_total + " ) ";
			//	逐个预测类别	end
			
			//	整个预测范围之外、期间在预测开始期间之后的		begin
			//	范围字符串太大，可能引起执行问题
			daoForecastData.clearForecastDatasOutScope( idsScopeStr4DetailProOrgs_total, _currentSysPeriod.getForecastRunPeriod(), SysConst.PERIOD_NULL );
			//	整个预测范围之外、期间在预测开始期间之后的		end
			
			//	2010.12.10 by liuzhen 清除不在预测范围之内的预测数据		end

			// 更新 _periodRollTask_Period 的状态 begin
			DaoSystem daoSystem = new DaoSystem( session );
			DaoPeriodRollTask daoPeriodRollTask = new DaoPeriodRollTask( session );

			_periodRollTask_Period.setStatus( BizConst.PERIODROLLTASK_STATUS_RUNNED );
			_periodRollTask_Period.setEndTime( daoSystem.getSysTimeAsTimeStamp() );

			daoPeriodRollTask.update( _periodRollTask_Period );
			// 更新 _periodRollTask_Period 的状态 end

			trsa.commit();

		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();

			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD );
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}			
		
		long cost = System.currentTimeMillis() - start;
		logger.info("执行编制期间类滚动任务执行结束，耗时"+ cost +" ms");
	}


	/**
	 * 当前编制期间的预测期间范围内有效的范围内：
	 * 		预测期间范围之内、屏蔽期间范围之外，
	 * 			模型预测和人工预测，没有数据的期间新建，新建的方法是拷贝向前最近的期间，拷贝不到的，置0；有数据的维持原值
	 * 			最终预测，没有数据的期间新建，有数据的更新；新建和更新的方法是根据组合预测公式计算
	 * 		预测期间范围之外，删除数据，但需要在所有预测类别处理完之后进行，所以留到最后一个滚动任务的时候去处理（BizConst.PERIODROLLTASK_CATEGORY_PERIOD）
	 * 
	 * @param _periodRollTask_ForecastInst
	 * @param _currentSysPeriod
	 * @throws Exception
	 */
	private void periodRollTaskRun_ForecatsInst( PeriodRollTask _periodRollTask_ForecastInst, SysPeriod _currentSysPeriod ) throws Exception
	{
		long start = System.currentTimeMillis();
		int dealNum = 0;
		Session session = HibernateSessionFactory.getSession();	
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();

			//	重新读取_periodRollTask_ForecastInst 	begin
			DaoPeriodRollTask daoPeriodRollTask = new DaoPeriodRollTask(session);
			PeriodRollTask periodRollTask_ForecastInst_upd = daoPeriodRollTask.getPeriodRollTaskById( _periodRollTask_ForecastInst.getId() );
			if( periodRollTask_ForecastInst_upd == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_NO_PERIODROLLTASK_FORECASTINST_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
			//	重新读取_periodRollTask_ForecastInst	end	
			
			// 滚动预测类别 begin

			List<ForecastInstProOrg> toDelForecastInstProOrgList = new ArrayList<ForecastInstProOrg>();
			List<ForecastInstProOrg> toAddForecastInstProOrgList = new ArrayList<ForecastInstProOrg>();

			ForecastInst toUpdForecastInst = periodRollTask_ForecastInst_upd.getForecastInst();
			
			logger.info("开始预测类滚动任务["+toUpdForecastInst.getName()+"],本期产品组织数["+toUpdForecastInst.getForecastInstProOrgs().size()
						+"]个，下期产品组织["+toUpdForecastInst.getForecastInstNextProOrgs().size()+"]个");
			long fcinstStart = System.currentTimeMillis();
			
			if( toUpdForecastInst.getIsValid() == BizConst.GLOBAL_YESNO_NO && toUpdForecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_NO ){
				// 无效->无效
				// 不滚动修改 forecastInst 的信息
			}
			else if( toUpdForecastInst.getIsValid() == BizConst.GLOBAL_YESNO_YES && toUpdForecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_NO ){
				// 有效->无效
				// 只滚动修改 forecastInst.isValid 的信息
				toUpdForecastInst.setIsValid( toUpdForecastInst.getNextIsValid() );
			}
			else if( (toUpdForecastInst.getIsValid() == BizConst.GLOBAL_YESNO_NO && toUpdForecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES)
					|| (toUpdForecastInst.getIsValid() == BizConst.GLOBAL_YESNO_YES && toUpdForecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES) )
			{
				// 无效->有效
				// 有效->有效
				//计算本期和下期的两个明细差集合，任务DEL和ADD的基础,因为比较值是对象，不能使用集合api的差计算
				Map<String,ForecastInstProOrg> curProOrgMap = new HashMap<String, ForecastInstProOrg>(toUpdForecastInst.getForecastInstProOrgs().size());
				Map<String,ForecastInstNextProOrg> nextProOrgMap = new HashMap<String, ForecastInstNextProOrg>(toUpdForecastInst.getForecastInstNextProOrgs().size());
				
				if( !CollectionUtils.isEmpty( toUpdForecastInst.getForecastInstProOrgs() ) )
				{	//当期的产品组织明细
					for(ForecastInstProOrg forecastInstProOrg : toUpdForecastInst.getForecastInstProOrgs()){
						String proOrgId = forecastInstProOrg.getProduct().getId() + "_" + forecastInstProOrg.getOrganization().getId();
						curProOrgMap.put(proOrgId, forecastInstProOrg);
					}
				}

				if( !CollectionUtils.isEmpty( toUpdForecastInst.getForecastInstNextProOrgs() ) )
				{
					//下期的组织产品明细
					for(ForecastInstNextProOrg forecastInstNextProOrg : toUpdForecastInst.getForecastInstNextProOrgs()){
						String proOrgId = forecastInstNextProOrg.getProduct().getId() + "_" + forecastInstNextProOrg.getOrganization().getId();
						nextProOrgMap.put(proOrgId, forecastInstNextProOrg);
						
						//判断出新增的产品组织(在当期未查到的)
						if(curProOrgMap.get(proOrgId) == null ){
							ForecastInstProOrg newForecastInstProOrg = new ForecastInstProOrg();
							newForecastInstProOrg.setOrganization( forecastInstNextProOrg.getOrganization() );
							newForecastInstProOrg.setProduct( forecastInstNextProOrg.getProduct() );
							newForecastInstProOrg.setForecastInst( toUpdForecastInst );

							toAddForecastInstProOrgList.add( newForecastInstProOrg );
						}
					}
				}
				
				if( !CollectionUtils.isEmpty( toUpdForecastInst.getForecastInstProOrgs() ) )
				{
					//计算出下期不做预测的产品组织明细
					for(ForecastInstProOrg forecastInstProOrg : toUpdForecastInst.getForecastInstProOrgs()){
						//不包含在下期的明细中
						String proOrgId = forecastInstProOrg.getProduct().getId() + "_" + forecastInstProOrg.getOrganization().getId();
						if( nextProOrgMap.get(proOrgId) == null){
							toDelForecastInstProOrgList.add( forecastInstProOrg );
						}
					}
				}
				
				toUpdForecastInst.setIsValid( toUpdForecastInst.getNextIsValid() );
				toUpdForecastInst.setFcPeriodNum( toUpdForecastInst.getNextFcPeriodNum() );
				toUpdForecastInst.setFzPeriodNum( toUpdForecastInst.getNextFzPeriodNum() );
				toUpdForecastInst.setFinalFcBizData( toUpdForecastInst.getNextFinalFcBizData() );
			}

			// 对预测类别的当前预测范围进行数据库持久化，并更新内存变量中信息 
			for( int i=0; i<toDelForecastInstProOrgList.size(); i++ ){
				//	后面会直接使用toUpdForecastInst.getForecastInstProOrgs()的集合，这里要把内存中数据设置好	
				ForecastInstProOrg forecastInstProOrg_del = toDelForecastInstProOrgList.get( i );
				toUpdForecastInst.getForecastInstProOrgs().remove( forecastInstProOrg_del );
			}
			
			for( int i=0; i<toAddForecastInstProOrgList.size(); i++ ){					
				//	后面会直接使用toUpdForecastInst.getForecastInstProOrgs()的集合，这里要把内存中数据设置好	
				ForecastInstProOrg forecastInstProOrg_new = toAddForecastInstProOrgList.get( i );
				
				toUpdForecastInst.getForecastInstProOrgs().add( forecastInstProOrg_new );
			}
			// 保存预测类别	end			
			// 滚动预测类别 end
			trsa.commit();
			logger.info("处理滚动预测类别的ProOrg期间增加和删除的数据OK，耗时["+ (System.currentTimeMillis()-fcinstStart) +"]ms");

			// 生成预测数据 begin
			//	生成算法： 
			//	1.业务范围：有效的预测类别的预测范围下有效的明细组织产品
			//	2.期间范围：预测期间之内、屏蔽期间之外
			// 	2.1  业务数据：模型预测类、有效的 人工预测类：没有的复制新建（复制算法：复制期间范围内最近的期间的数据， 没有的话置0）；有值的维持原值
			//	2.2  业务数据：最终预测：没有的计算新建，已有的计算更新 	
			
			DaoForecastData daoForecastData = new DaoForecastData( session );
			DaoBizData daoBizData = new DaoBizData( session );
			Integer[] arr4BizDataIsValid4Fc = new Integer[] { BizConst.GLOBAL_YESNO_YES };

			Integer[] arr4BizDataFcMH = new Integer[] { BizConst.BIZDATA_TYPE_FCMODEL, BizConst.BIZDATA_TYPE_FCHAND };
			List<BizData> list4BizDataFcMH = daoBizData.getBizDatasByTypes( arr4BizDataFcMH, arr4BizDataIsValid4Fc );
			if(list4BizDataFcMH == null){
				list4BizDataFcMH = new ArrayList<BizData>();
			}

			// 读取预测期间之内屏蔽区间之外的的预测数据准备 begin
			List<Long> list4BizDataFCMHIds = new ArrayList<Long>();
			for( int i = 0; i < list4BizDataFcMH.size(); i++ )
			{
				list4BizDataFCMHIds.add( list4BizDataFcMH.get( i ).getId() );
			}
			
			List<Integer> list4Periods = new ArrayList<Integer>();
			for( int i = toUpdForecastInst.getFzPeriodNum(); i < toUpdForecastInst.getFcPeriodNum(); i = i + 1 )
			{
				int period = UtilPeriod.getPeriod( _currentSysPeriod.getForecastRunPeriod(), i+1 );	//	要加1，因为是要下一期间的
				list4Periods.add( period );
			}
			// 读取预测期间之内屏蔽区间之外的的预测数据准备 end
			
			//	最终预测业务数据	begin
			BizData bizDataFcFinal = daoBizData.getFinalFcBizData();	//	最终预测业务数据
			BizData bizData_CombFc4FcFinal = toUpdForecastInst.getFinalFcBizData();		//	最终预测数据指向的组合预测
			//	最终预测业务数据	end			

			DaoSystem daoSystem = new DaoSystem( session );
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			
			if( toUpdForecastInst.getIsValid() == BizConst.GLOBAL_YESNO_YES && toUpdForecastInst.getForecastInstProOrgs() != null && !(toUpdForecastInst.getForecastInstProOrgs().isEmpty()) )
			{
				HashMap<String, AProOrg> hmap4DetailProOrgs4ForecasData = UtilProOrg.getDetailProOrgs4ForecastInst( toUpdForecastInst.getForecastInstProOrgs(), true );
				if( hmap4DetailProOrgs4ForecasData != null && hmap4DetailProOrgs4ForecasData.values() != null && !(hmap4DetailProOrgs4ForecasData.values().isEmpty()) )
				{					
					//循环到每个明细产品组织
					for(AProOrg detailProOrg4ForecasData :hmap4DetailProOrgs4ForecasData.values())
					{
						List<ForecastData> toAddForecastDataList = new ArrayList<ForecastData>();
						List<ForecastData> toUpdForecastDataList = new ArrayList<ForecastData>();
						trsa = session.beginTransaction();
						
						//	读取该业务范围、期间范围内的所有模型预测和人工预测数据	begin
						HashMap<String, ForecastData> hmap4ForecastDataFcMH = daoForecastData.getForecastDatas( detailProOrg4ForecasData.getProduct().getId(), detailProOrg4ForecasData.getOrganization().getId(), list4Periods, list4BizDataFCMHIds );												
						//	读取该业务范围、期间范围内的所有模型预测和人工预测数据	end	
						
						//	读取该业务范围、期间范围内的所有最终预测数据	begin
						HashMap<String, ForecastData> hmap4ForecastDataFcFinal = daoForecastData.getForecastDatas( detailProOrg4ForecasData.getProduct().getId(), detailProOrg4ForecasData.getOrganization().getId(), list4Periods,  bizDataFcFinal.getId() );
						//	读取该业务范围、期间范围内的所有最终预测数据	end
						
						for( int i = toUpdForecastInst.getFzPeriodNum(); i < toUpdForecastInst.getFcPeriodNum(); i = i + 1 )
						{
							//	期间
							int period = UtilPeriod.getPeriod( _currentSysPeriod.getForecastRunPeriod(), i+1 );
														
							// 模型预测数据 人工预测数据	begin
							for(BizData bizDataFcMH : list4BizDataFcMH){
								if( bizDataFcMH.getIsValid() == BizConst.GLOBAL_YESNO_NO ){
									continue;
								}
								String strKey4forecastData = UtilStrKey.getStrKey4POPB( detailProOrg4ForecasData.getProduct(), detailProOrg4ForecasData.getOrganization(), period, bizDataFcMH );
								if( hmap4ForecastDataFcMH.get( strKey4forecastData ) != null ){
									//	已有
									continue;
								}
																	
								//	新建		begin
								ForecastData forecastData_new = new ForecastData();
								forecastData_new.setBizData( bizDataFcMH );
								forecastData_new.setOrganization( detailProOrg4ForecasData.getOrganization() );
								forecastData_new.setProduct( detailProOrg4ForecasData.getProduct() );
								forecastData_new.setPeriod( period );
								//forecastData_new.setValue( forecastDataValue_copy );
								forecastData_new.setValue( 0 );//modifiy by luowang 20130701 不复制上一期数据，直接为0
								forecastData_new.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								forecastData_new.setInitTime( currentTime );
								forecastData_new.setUpdateTime( currentTime );

								//	把新建的也放到hmap中，计算最终预测时方便使用
								hmap4ForecastDataFcMH.put( strKey4forecastData, forecastData_new );
								
								toAddForecastDataList.add( forecastData_new );
								//	新建		end									
							}
							// 模型预测数据 人工预测数据	end
							
							//	最终预测		begin

							// 利用定义的公式计算最终预测值	begin
							long forecastDataValue_FcFinal = 0;
							if( bizData_CombFc4FcFinal.getBizDataDefItems() != null && !(bizData_CombFc4FcFinal.getBizDataDefItems().isEmpty()) )
							{
								Iterator<BizDataDefItem> itr_bizDataDefItems = bizData_CombFc4FcFinal.getBizDataDefItems().iterator();
								while( itr_bizDataDefItems.hasNext() )
								{
									BizDataDefItem bizDataDefItem = itr_bizDataDefItems.next();
									if( bizDataDefItem instanceof BizDataDefItemFcComb )
									{
										BizDataDefItemFcComb bizDataDefItemFcComb = (BizDataDefItemFcComb) bizDataDefItem;

										String strKey4forecastData4ItemBizData = UtilStrKey.getStrKey4POPB( detailProOrg4ForecasData.getProduct(), detailProOrg4ForecasData.getOrganization(), period, bizDataDefItemFcComb.getItemBizData() );
										ForecastData forecastData4ItemBizData = hmap4ForecastDataFcMH.get( strKey4forecastData4ItemBizData );
										if( forecastData4ItemBizData != null )
										{
											forecastDataValue_FcFinal = Math.round( forecastDataValue_FcFinal + forecastData4ItemBizData.getValue() * bizDataDefItemFcComb.getCoefficient() );
										}
										//	added by liuzhen, 2011.07.02	begin
										else if( bizDataDefItemFcComb.getItemBizData().getType() == BizConst.BIZDATA_TYPE_TIMEFC )
										{
											if( bizDataDefItemFcComb.getItemBizData().getBizDataDefItems() != null && !(bizDataDefItemFcComb.getItemBizData().getBizDataDefItems().isEmpty()) )
											{
												Iterator<BizDataDefItem> itr_bizDataDefItemTimeFc = bizDataDefItemFcComb.getItemBizData().getBizDataDefItems().iterator();
												if( itr_bizDataDefItemTimeFc.hasNext() )
												{
													BizDataDefItemTimeFc bizDataDefItemTimeFc = (BizDataDefItemTimeFc) itr_bizDataDefItemTimeFc.next();
													
													int periodDiff2Current = 0;
													
													int timeFormulaDictValue = bizDataDefItemTimeFc.getTimeFormula();
													
													if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST1YEAR )
													{
														periodDiff2Current = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
													}
													else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST2YEAR )
													{
														periodDiff2Current = 2*ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();;
													}
													else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST3YEAR )
													{
														periodDiff2Current = 3*ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();;
													}	
													else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_LAST1PERIOD )
													{
														periodDiff2Current = 1;
													}
													
													int period_fcTimeDependon = UtilPeriod.getPeriod( period, -periodDiff2Current );
													
													String strKey4popb_fcTimeDependon = UtilStrKey.getStrKey4POPB( detailProOrg4ForecasData.getProduct(), detailProOrg4ForecasData.getOrganization(), period_fcTimeDependon, bizDataDefItemTimeFc.getItemBizData() );
													ForecastData forecastData_fcTimeDependon = hmap4ForecastDataFcMH.get( strKey4popb_fcTimeDependon );
													if( forecastData_fcTimeDependon == null )
													{
														//	从数据库中读取
														forecastData_fcTimeDependon = daoForecastData.getForecastData( detailProOrg4ForecasData.getProduct().getId(), detailProOrg4ForecasData.getOrganization().getId(), period_fcTimeDependon, bizDataDefItemTimeFc.getItemBizData().getId() );
													}
													if( forecastData_fcTimeDependon != null )
													{
														forecastDataValue_FcFinal = Math.round( forecastDataValue_FcFinal + forecastData_fcTimeDependon.getValue() * bizDataDefItemFcComb.getCoefficient() );
													}													
													
												}
											}											
										}
										//	added by liuzhen, 2011.07.02	end
									}
								}
							}	
							// 利用定义的公式计算最终预测值	end	
							String strKey4forecastData_FcFinal = UtilStrKey.getStrKey4POPB( detailProOrg4ForecasData.getProduct(), detailProOrg4ForecasData.getOrganization(), period, bizDataFcFinal );

							if( hmap4ForecastDataFcFinal.get( strKey4forecastData_FcFinal ) != null )
							{
								ForecastData forecastData_upd = hmap4ForecastDataFcFinal.get( strKey4forecastData_FcFinal );
								forecastData_upd.setValue( forecastDataValue_FcFinal );
								
								forecastData_upd.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								forecastData_upd.setInitTime( currentTime );
								forecastData_upd.setUpdateTime( currentTime );	
								
								toUpdForecastDataList.add( forecastData_upd );
							}
							else
							{
								ForecastData forecastData_new = new ForecastData();
								forecastData_new.setBizData( bizDataFcFinal );
								forecastData_new.setOrganization( detailProOrg4ForecasData.getOrganization() );
								forecastData_new.setProduct( detailProOrg4ForecasData.getProduct() );
								forecastData_new.setPeriod( period );
								forecastData_new.setValue( forecastDataValue_FcFinal );
								
								forecastData_new.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								forecastData_new.setInitTime( currentTime );
								forecastData_new.setUpdateTime( currentTime );
								
								toAddForecastDataList.add( forecastData_new );
							}
							//	最终预测		end												
						}	//end 期间处理
						
						for( int n=0; n<toAddForecastDataList.size(); n++ ){
							daoForecastData.save( toAddForecastDataList.get( n ) );
							dealNum++;
						}
						
						for( int n=0; n<toUpdForecastDataList.size(); n++ ){
							daoForecastData.update( toUpdForecastDataList.get( n ) );
							dealNum++;
						}
						trsa.commit(); ///分段提交
					}//明细产品组织
					
				}
			}
			// 鐢熸垚棰勬祴鏁版嵁 end

			trsa = session.beginTransaction();
			// 保存预测类别	begin
			DaoForecastInst daoForecastInst = new DaoForecastInst(session);
			daoForecastInst.update( toUpdForecastInst );

			// 对预测类别的当前预测范围进行数据库持久化
			DaoForecastInstProOrg daoForecastInstProOrg = new DaoForecastInstProOrg( session );
			for(ForecastInstProOrg forecastInstProOrg_del : toDelForecastInstProOrgList ){
				daoForecastInstProOrg.delete( forecastInstProOrg_del );
			}
			for(ForecastInstProOrg forecastInstProOrg_new : toAddForecastInstProOrgList)
			{					
				toUpdForecastInst.getForecastInstProOrgs().add( forecastInstProOrg_new );
			}
			
			// 更新 _periodRollTask_ForecastInst 的状态 begin
			periodRollTask_ForecastInst_upd.setStatus( BizConst.PERIODROLLTASK_STATUS_RUNNED );
			periodRollTask_ForecastInst_upd.setEndTime( daoSystem.getSysTimeAsTimeStamp() );
			// 更新 _periodRollTask_ForecastInst 的状态 end
			trsa.commit();

			logger.info("预测类滚动任务["+toUpdForecastInst.getName()+"]处理结束，共处理["+dealNum+"]条记录,总耗时"+(System.currentTimeMillis()-start)+" ms");
		}
		catch( Exception ex )
		{
			if( trsa != null ){
				trsa.rollback();
			}
			ex.printStackTrace();

			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD );
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() ){
				session.close();
			}
		}			

	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}

/************************************************************************
 * $RCSfile: PeriodRollTaskManagerThread.java,v $ $Revision: 1.8 $ $Date: 2010/08/07 10:21:58 $ $Log:
 * PeriodRollTaskManagerThread.java,v $ Revision
 * 
 ************************************************************************/
