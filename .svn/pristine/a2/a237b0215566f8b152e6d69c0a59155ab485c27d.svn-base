/**********************************************************************
 *$RCSfile:InterfaceStandardHistoryDataMgmt.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc.standard;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.DmddThreadPool;
import dmdd.dmddjava.dataaccess.aidobject.ABImInHistoryData;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;

/**
 * <li>Title: InterfaceStandardHistoryDataMgmt.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class InterfaceStandardHistoryDataMgmt
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private List<ABImInHistoryData> listHistoryDatas = null;
	private List<ABImInHistoryData> resultHistoryDatas = new ArrayList<ABImInHistoryData>();
	private int count=0;//计数器
	private int total=0;
	private volatile boolean  isok = false;
	private List<AProOrg> listProOrg4FrorecastIns = new ArrayList<AProOrg>();
	
	public InterfaceStandardHistoryDataMgmt(List<ABImInHistoryData> listHistoryDatas)
	{
		this.listHistoryDatas = listHistoryDatas;
	}
	
	public void excute()
	{		
		loadAllForecastIns();
		total=listHistoryDatas.size();
		for(ABImInHistoryData data:listHistoryDatas)
		{
			InterfaceStandardHistoryDataProcesser ex = new InterfaceStandardHistoryDataProcesser(data,this);
			try
			{
				DmddThreadPool.getinstance().addThreadProcessMgmt( ex );
			}
			catch( InterruptedException e )
			{
				logger.error( "导入历史数据过程出现错误",e );
			}
		}				
	}
	
	public void loadAllForecastIns()
	{
		//获取有效预测策略中所有的业务范围;
		Session session_ = HibernateSessionFactory.getSession();
		Transaction trsa_ = null;
		try
		{
			trsa_ = session_.beginTransaction();

			DaoForecastInst daoForecastInst = new DaoForecastInst( session_ );
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES };
			List<ForecastInst> listForecastInst_inDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );	

			for(ForecastInst forecastInst:listForecastInst_inDB)
			{
				if(forecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES)
				{					
					for( ForecastInstNextProOrg forecastInstNextProOrg:forecastInst.getForecastInstNextProOrgs())
					{
						AProOrg proorg_ = new AProOrg();
						proorg_.setProduct(forecastInstNextProOrg.getProduct());
						proorg_.setOrganization(forecastInstNextProOrg.getOrganization());						
						listProOrg4FrorecastIns.add(proorg_);							
					}					
				}							
			}
		}
		catch( Exception ex )
		{
			if( trsa_ != null )
			{
				trsa_.rollback();
			}
			ex.printStackTrace();			
		}
		finally
		{
			if(session_.isConnected() ||session_.isOpen())
			{
				session_.close();
			}
		}	
	}
	
	public List<AProOrg> getlistProOrg4FrorecastIns()
	{
		return listProOrg4FrorecastIns;
	}
	
	public List<ABImInHistoryData> getResult()
	{		
		check();
		
		return resultHistoryDatas;		
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
	
	public void doResult(ABImInHistoryData data)
	{
		resultHistoryDatas.add(data);
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
	
}

/**********************************************************************
 *$RCSfile:InterfaceStandardHistoryDataMgmt.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *
 *$Log:InterfaceStandardHistoryDataMgmt.java,v $
 *********************************************************************/