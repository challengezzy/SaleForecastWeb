/**********************************************************************
 *$RCSfile:ForecastRunTaskThread.java,v $  $Revision: 1.0 $  $Date:2011-12-29 $
 *********************************************************************/ 
package dmdd.dmddjava.service.systemservice;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastRunTaskItemBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.service.model.ForecastModelService;

/**
 * <li>Title: ForecastRunTaskThread.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class ForecastRunTaskThread extends Thread
{
	private Logger logger = CoolLogger.getLogger(this.getClass());
	
	private BForecastRunTaskItem forecastRunTaskitem = null;
	
	public ForecastRunTaskThread(BForecastRunTaskItem forecastRunTaskitem)
	{
		this.forecastRunTaskitem = forecastRunTaskitem;
	}
	
	public synchronized void run()
	{
		ForecastModelService forecastModelService = new ForecastModelService();
		ForecastRunTaskItemBDConvertor forecastRunTaskItemBDConvertor = new ForecastRunTaskItemBDConvertor();
		//待运行状态
		if( forecastRunTaskitem.getStatus() == BizConst.FORECASTRUNTASKITEM_STATUS_TORUN )
		{
			ForecastRunTaskItem forecastRunTaskItem = (ForecastRunTaskItem)forecastRunTaskItemBDConvertor.btod( forecastRunTaskitem );
			
			Session session_run = HibernateSessionFactory.getSession();
			Transaction trsa_run = null;					
			try
			{
				trsa_run = session_run.beginTransaction();

				DaoSystem daoSystem_runTask = new DaoSystem( session_run );
				DaoForecastRunTaskItem daoForecastRunTaskItem = new DaoForecastRunTaskItem( session_run );
				
				forecastRunTaskItem.setStatus( BizConst.FORECASTRUNTASKITEM_STATUS_RUNNING );
				forecastRunTaskItem.setBeginTime( daoSystem_runTask.getSysTimeAsTimeStamp() );
				daoForecastRunTaskItem.update( forecastRunTaskItem );
				
				trsa_run.commit();						
			}
			catch( Exception ex )
			{
				if( trsa_run != null )
				{
					trsa_run.rollback();
				}
				ex.printStackTrace();

				ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST );				
			}
			finally
			{
				if( session_run != null && session_run.isOpen() )
				{
					session_run.close();
				}
			}
			
			forecastRunTaskitem = (BForecastRunTaskItem)forecastRunTaskItemBDConvertor.dtob( forecastRunTaskItem );
		}
		
		//运行中状态
		if( forecastRunTaskitem.getStatus() == BizConst.FORECASTRUNTASKITEM_STATUS_RUNNING )
		{
			boolean blException = false;
			String rstDetail = null;
			try
			{
				long start = System.currentTimeMillis();
				logger.info("开始执行统计预测["+ forecastRunTaskitem.getForecastInstName() +"]...");
				forecastModelService.forecastInstRunMappingModel4Forecast( forecastRunTaskitem );
				long cost = System.currentTimeMillis() - start;
				logger.info("结束执行统计预测["+ forecastRunTaskitem.getForecastInstName() +"],耗时["+cost+"]ms");
			}
			catch( Exception ex )
			{
				rstDetail = ex.getMessage();
				if( rstDetail.length() > 255 )
				{
					rstDetail.substring( 0, 255 );
				}
				blException = true;
			}				
			
			if( blException == true )
			{
				ForecastRunTaskItem forecastRunTaskItem = (ForecastRunTaskItem)forecastRunTaskItemBDConvertor.btod( forecastRunTaskitem );
				
				forecastRunTaskItem.setResult( BizConst.FORECASTRUNTASKITEM_RESULT_EXCEPTION );
				forecastRunTaskItem.setStatus( BizConst.FORECASTRUNTASKITEM_STATUS_RUNNED );
				forecastRunTaskItem.setResultDetail( rstDetail );
				
				Session session_upd = HibernateSessionFactory.getSession();
				Transaction trsa_upd = null;					
				try
				{
					trsa_upd = session_upd.beginTransaction();

					DaoSystem daoSystem_upd = new DaoSystem( session_upd );
					DaoForecastRunTaskItem daoForecastRunTaskItem = new DaoForecastRunTaskItem( session_upd );
					
					forecastRunTaskItem.setEndTime( daoSystem_upd.getSysTimeAsTimeStamp() );
					daoForecastRunTaskItem.update( forecastRunTaskItem );
					
					trsa_upd.commit();						
				}
				catch( Exception ex )
				{
					if( trsa_upd != null )
					{
						trsa_upd.rollback();
					}
					ex.printStackTrace();

					ServerEnvironment.getInstance().setSystemStatus( BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST );
				}
				finally
				{
					if( session_upd != null && session_upd.isOpen() )
					{
						session_upd.close();
					}
				}						
			}
		}	
		invokMainThreadCount();
	}
	
	public void invokMainThreadCount()
	{
		ForecastRunTaskManagerThread.getInstance().setThreadCount();
	}
}

/**********************************************************************
 *$RCSfile:ForecastRunTaskThread.java,v $  $Revision: 1.0 $  $Date:2011-12-29 $
 *
 *$Log:ForecastRunTaskThread.java,v $
 *********************************************************************/