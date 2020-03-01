/**********************************************************************
 *$RCSfile:ForecastAssessmentMgmt.java,v $  $Revision: 1.0 $  $Date:2012-3-22 $
 *********************************************************************/ 
package dmdd.dmddjava.service.forecastservice;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.system.DmddThreadPool;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;

/**
 * <li>Title: ForecastAssessmentMgmt.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ForecastAssessmentMgmt
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private int count=0;//计数器
	private int total=0;
	private volatile boolean  isok = false;
	private List<ABProOrg> _listProOrgs;
	private BBizData _bBizdata;
	
	public ForecastAssessmentMgmt(List<ABProOrg> _listProOrgs,BBizData _bBizdata)
	{
		this._listProOrgs = _listProOrgs;
		this._bBizdata = _bBizdata;	
	}
	
	public void excute()
	{	
		//先删除 begin
		logger.info("删除已有的版本预测数据开始...");
		deleteForecastAssmessment(_bBizdata.getId());
		logger.info("删除已有的版本预测数据成功。");
		//先删除 end
		//根据业务数据定义的每个期间进行查询原始数据 begin
		BBizDataDefItemForecastAssessment bBizDataDefItemForecastAssessment;
		int beginPeriod = 0;
		int endPeriod = 0;
		try
		{
			
			for(BBizDataDefItem bizdataDefItem:_bBizdata.getBizDataDefItems())
			{
				if(bizdataDefItem instanceof BBizDataDefItemForecastAssessment)
				{
					total = total+1;
					bBizDataDefItemForecastAssessment = (BBizDataDefItemForecastAssessment)bizdataDefItem;
					beginPeriod = bBizDataDefItemForecastAssessment.getStartPeriod();
					endPeriod = bBizDataDefItemForecastAssessment.getEndPeriod();
					ForecastAssessmentProcesser processer = new ForecastAssessmentProcesser(_listProOrgs, beginPeriod, endPeriod, bBizDataDefItemForecastAssessment.getItemBizData(), _bBizdata, this);
					DmddThreadPool.getinstance().addThreadProcessMgmt(processer);	
					
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		//根据业务数据定义的每个期间进行查询原始数据 end
	}
	
	public void getResult()
	{		
		check();
	}
	
	//用来监控是否已经完成所有任务
	private void check()
	{
		while(true)
		{
			try
			{
				if(isOk())
				{
					Thread.interrupted();
					return ;
				}
				else 
				{					
					Thread.sleep( 1000 );					
				}
			}
			catch( InterruptedException e )
			{
				logger.info( "",e );
			}
		}
	}
	
	public void doResult()
	{
		count=count+1;
		System.out.println("count:"+count);
		if(count==total)
			resume();
			
	}
	
	private void resume()
	{
		isok=true;
	}
	
	private boolean isOk()
	{
		return isok;
	}
	
	private void deleteForecastAssmessment(Long bizdataid)
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(" delete from FORECASTASSESSMENTDATA where BIZDATAID ="+bizdataid);
			query.executeUpdate();
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
	}
}

/**********************************************************************
 *$RCSfile:ForecastAssessmentMgmt.java,v $  $Revision: 1.0 $  $Date:2012-3-22 $
 *
 *$Log:ForecastAssessmentMgmt.java,v $
 *********************************************************************/