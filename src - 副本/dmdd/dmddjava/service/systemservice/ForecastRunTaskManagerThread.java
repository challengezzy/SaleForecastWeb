/**
 * 
 */
package dmdd.dmddjava.service.systemservice;

import java.util.Iterator;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastRunTaskBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTask;
import dmdd.dmddjava.dataaccess.dataobject.SysPeriod;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastRunTask;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSysPeriod;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;

/**
 * @author liuzhen
 *
 */
public class ForecastRunTaskManagerThread extends Thread
{
	
	private final Logger						logger		= Logger.getLogger( this.getClass().getName() );
	private static ForecastRunTaskManagerThread	theInstance	= null;

	private int count_thread = 0;
	
	public synchronized static ForecastRunTaskManagerThread getInstance()
	{
		if( theInstance == null )
		{
			theInstance = new ForecastRunTaskManagerThread();
		}
		return theInstance;
	}	

	/**
	 * 
	 */
	public ForecastRunTaskManagerThread() {
		// TODO Auto-generated constructor stub
		logger.info( "创建 ForecastRunTaskManagerThread ..." );
	}


	public void setThreadCount()
	{
		this.count_thread ++;
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		//super.run();
		try
		{
			while( !interrupted() )
			{
				if( ServerEnvironment.getInstance().getSystemStatus() == BizConst.SYSTEM_STATUS_RUNNINGFORECAST )
				{
					logger.info( " ForecastRunTaskManagerThread process start!" );
					processForecastRunTask();
					logger.info( " ForecastRunTaskManagerThread process finish!" );
				}
				sleep( 500 ); // 睡眠
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
	
	
	private void processForecastRunTask() throws Exception
	{	
		//	读出本期最新预测任务	begin
		BForecastRunTask latestBForecastRunTask = null; 
		Session session_readTask = HibernateSessionFactory.getSession();
		Transaction trsa_readTask = null;
		try
		{
			trsa_readTask = session_readTask.beginTransaction();
			
			DaoSysPeriod daoSysPeriod = new DaoSysPeriod( session_readTask );

			SysPeriod currentSysPeriod = daoSysPeriod.getSysPeriod();
			if( currentSysPeriod == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_NO_RECORD_IN_SYSPERIOD );
				Exception ex = new Exception( cause );
				throw ex;
			}

			DaoForecastRunTask daoForecastRunTask_readTask = new DaoForecastRunTask( session_readTask );
			ForecastRunTask latestForecastRunTask_inDB = daoForecastRunTask_readTask.getLatestForecastRunTask( currentSysPeriod.getCompilePeriod() );
			if( latestForecastRunTask_inDB != null && latestForecastRunTask_inDB.getStatus() == BizConst.FORECASTRUNTASK_STATUS_RUNNING )
			{
				ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
				
				latestBForecastRunTask = forecastRunTaskBDConvertor.dtob( latestForecastRunTask_inDB, true );			
			}

			trsa_readTask.commit();
			
		}
		catch( Exception ex )
		{
			if( trsa_readTask != null )
			{
				trsa_readTask.rollback();
			}
			
			ex.printStackTrace();

			ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST );
			throw ex;
		}
		finally
		{
			if( session_readTask != null && session_readTask.isOpen() )
			{
				session_readTask.close();
			}
		}	
		//	读出本期最新预测任务	end
		
		
		//	逐个处理预测任务项		begin
		
		if( latestBForecastRunTask != null && latestBForecastRunTask.getStatus() == BizConst.FORECASTRUNTASK_STATUS_RUNNING && !(latestBForecastRunTask.getForecastRunTaskItems().isEmpty()) )
		{
			this.count_thread = 0;
			int runCount = latestBForecastRunTask.getForecastRunTaskItems().size();
			
			
			Iterator<BForecastRunTaskItem> itr_BForecastRunTaskItem = latestBForecastRunTask.getForecastRunTaskItems().iterator();
			while( itr_BForecastRunTaskItem.hasNext() )
			{
				BForecastRunTaskItem bForecastRunTaskItem = itr_BForecastRunTaskItem.next();
				ForecastRunTaskThread thread = new ForecastRunTaskThread( bForecastRunTaskItem );
				thread.start();
			}
			
			//判断是否都执行完成
			try
			{
				int time = 0;
				while(true)
				{
					if(this.count_thread != runCount)
					{
						sleep(1000);
						time++;
						if(time>21600)//都他妈的运行了6个小时了，就别折腾了
						{
							System.out.println("---------------------运行统计预测已经6个小时了，太不正常了，肯定有问题-----------");
							
							ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST );
							break;
						}
					}
					else
					{
						break;
					}
					
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();

				ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST );
			}
			
			Session session_updTask = HibernateSessionFactory.getSession();
			Transaction trsa_updTask = null;
			try
			{
				trsa_updTask = session_updTask.beginTransaction();
				
				DaoForecastRunTask daoForecastRunTask = new DaoForecastRunTask( session_updTask );
				DaoSystem daoSystem = new DaoSystem( session_updTask );
				
				latestBForecastRunTask.setStatus( BizConst.FORECASTRUNTASK_STATUS_RUNNED );	
				latestBForecastRunTask.setFinishTime( daoSystem.getSysTimeAsTimeStamp() );

				ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
				daoForecastRunTask.update( forecastRunTaskBDConvertor.btod( latestBForecastRunTask ) );
				
				trsa_updTask.commit();
				
			}
			catch( Exception ex )
			{
				if( trsa_updTask != null )
				{
					trsa_updTask.rollback();
				}
				
				ex.printStackTrace();

				ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST );
				throw ex;
			}
			finally
			{
				if( session_updTask != null && session_updTask.isOpen() )
				{
					session_updTask.close();
				}
			}				
		}
		//	逐个处理预测任务项		end		

		//	更新服务器环境变量	begin
		ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_NORMAL );		
		//	更新服务器环境变量	end
	}	

}
