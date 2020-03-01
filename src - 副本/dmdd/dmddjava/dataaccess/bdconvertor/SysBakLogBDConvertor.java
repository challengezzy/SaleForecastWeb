/**********************************************************************
 *$RCSfile:SysBakLogBDConvertor.java,v $  $Revision: 1.0 $  $Date:2011-9-6 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BSysBakLog;
import dmdd.dmddjava.dataaccess.dataobject.SysBakLog;

/**
 * <li>Title: SysBakLogBDConvertor.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */
public class SysBakLogBDConvertor implements BDConvertorInterface
{

	@Override
	public void btod( Object bObj, Object dObj )
	{
		BSysBakLog bSysBakLog = null;
		SysBakLog sysBaklog = null;
		
		if(bObj==null)
		{
			bSysBakLog = new BSysBakLog();
		}
		else
		{
			bSysBakLog = (BSysBakLog)bObj;
		}
		
		if(dObj==null)
		{
			sysBaklog = new SysBakLog();
		}
		else
		{
			sysBaklog = (SysBakLog)dObj;
		}
		
		sysBaklog.setId( bSysBakLog.getId() );
		sysBaklog.setExcuteTime( bSysBakLog.getExcuteTime() );
		sysBaklog.setBeginPeroid( bSysBakLog.getBeginPeroid() );
		sysBaklog.setEndPeroid( bSysBakLog.getEndPeroid() );
		sysBaklog.setBakHistoryAdjustLog( bSysBakLog.getBakHistoryAdjustLog());
		sysBaklog.setBakHistoryData( bSysBakLog.getBakHistoryData( ) );
		sysBaklog.setBakPriceData( bSysBakLog.getBakPriceData());
		sysBaklog.setBakForecastData( bSysBakLog.getBakForecastData() );
		sysBaklog.setBakForecastMakeLog( bSysBakLog.getBakForecastMakeLog() );
		sysBaklog.setDescription( bSysBakLog.getDescription() );
		sysBaklog.setComments( bSysBakLog.getComments() );
		sysBaklog.setOperatorUserName( bSysBakLog.getOperatorUserName() );				
	}

	@Override
	public Object btod( Object bObj )
	{
		SysBakLog sysBaklog = new SysBakLog();
		this.btod( bObj, sysBaklog );
		return sysBaklog;
	}

	@Override
	public void dtob( Object dObj, Object bObj )
	{
		BSysBakLog bSysBakLog = null;
		SysBakLog sysBaklog = null;
		
		if(bObj==null)
		{
			bSysBakLog = new BSysBakLog();
		}
		else
		{
			bSysBakLog = (BSysBakLog)bObj;
		}
		
		if(dObj==null)
		{
			sysBaklog = new SysBakLog();
		}
		else
		{
			sysBaklog = (SysBakLog)dObj;
		}

		bSysBakLog.setId( sysBaklog.getId() );
		bSysBakLog.setExcuteTime( sysBaklog.getExcuteTime() );
		bSysBakLog.setBeginPeroid( sysBaklog.getBeginPeroid() );
		bSysBakLog.setEndPeroid( sysBaklog.getEndPeroid() );
		bSysBakLog.setBakHistoryAdjustLog( sysBaklog.getBakHistoryAdjustLog());
		bSysBakLog.setBakHistoryData( sysBaklog.getBakHistoryData( ) );
		bSysBakLog.setBakPriceData( sysBaklog.getBakPriceData());
		bSysBakLog.setBakForecastData( sysBaklog.getBakForecastData() );
		bSysBakLog.setBakForecastMakeLog( sysBaklog.getBakForecastMakeLog() );
		bSysBakLog.setDescription( sysBaklog.getDescription() );
		bSysBakLog.setComments( sysBaklog.getComments() );
		bSysBakLog.setOperatorUserName( sysBaklog.getOperatorUserName() );	
		
	}

	@Override
	public Object dtob( Object dObj )
	{
		BSysBakLog bSysBakLog = new BSysBakLog();
		this.dtob( dObj,bSysBakLog );
		return bSysBakLog;
	}

}

/**********************************************************************
 *$RCSfile:SysBakLogBDConvertor.java,v $  $Revision: 1.0 $  $Date:2011-9-6 $
 *
 *$Log:SysBakLogBDConvertor.java,v $
 *********************************************************************/