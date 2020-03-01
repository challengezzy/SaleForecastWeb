/**********************************************************************
 *$RCSfile:InterfaceStandardhistoryDataADRMgmt.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *********************************************************************/ 
package dmdd.dmddjava.service.ifc.standard;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dmdd.dmddjava.common.system.DmddThreadPool;
import dmdd.dmddjava.dataaccess.aidobject.ABImInHistoryDataADR;

/**
 * <li>Title: InterfaceStandardhistoryDataADRMgmt.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class InterfaceStandardhistoryDataADRMgmt
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private List<ABImInHistoryDataADR> listHistoryDatas = null;
	private List<ABImInHistoryDataADR> resultHistoryDatas = new ArrayList<ABImInHistoryDataADR>();
	private int count=0;//计数器
	private int total=0;
	private volatile boolean  isok = false;
	
	public InterfaceStandardhistoryDataADRMgmt(List<ABImInHistoryDataADR> listHistoryDatas)
	{
		this.listHistoryDatas = listHistoryDatas;
	}
	
	public void excute()
	{		

		total=listHistoryDatas.size();
		for(ABImInHistoryDataADR data:listHistoryDatas)
		{
			InterfaceStandardHistoryDataADRProcesser ex = new InterfaceStandardHistoryDataADRProcesser(data,this);
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

	
	public List<ABImInHistoryDataADR> getResult()
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
	
	public void doResult(ABImInHistoryDataADR data)
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
 *$RCSfile:InterfaceStandardhistoryDataADRMgmt.java,v $  $Revision: 1.0 $  $Date:2012-1-10 $
 *
 *$Log:InterfaceStandardhistoryDataADRMgmt.java,v $
 *********************************************************************/