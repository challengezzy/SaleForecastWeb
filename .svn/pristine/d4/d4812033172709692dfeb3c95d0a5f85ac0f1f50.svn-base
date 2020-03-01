/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastInst;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTask;
import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;

/**
 * @author liuzhen
 *
 */
public class ForecastRunTaskItemBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastRunTaskItemBDConvertor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod( Object bObj, Object dObj )
	{
		// TODO Auto-generated method stub
		BForecastRunTaskItem bForecastRunTaskItem = null;;
		ForecastRunTaskItem  forecastRunTaskItem  = null;
		
		if( bObj == null )
		{
			bForecastRunTaskItem = new BForecastRunTaskItem();
		}
		else
		{
			bForecastRunTaskItem = (BForecastRunTaskItem)bObj;
		}
		
		if( dObj == null )
		{
			return;
		}
		else
		{
			forecastRunTaskItem = (ForecastRunTaskItem)dObj;
		}

		forecastRunTaskItem.setVersion( bForecastRunTaskItem.getVersion() );
		forecastRunTaskItem.setId( bForecastRunTaskItem.getId() );
		
		forecastRunTaskItem.setSeqNo( bForecastRunTaskItem.getSeqNo() );
		forecastRunTaskItem.setIsIgnoreErrorThreshold( bForecastRunTaskItem.getIsIgnoreErrorThreshold() );
		forecastRunTaskItem.setStatus( bForecastRunTaskItem.getStatus() );
		forecastRunTaskItem.setResult( bForecastRunTaskItem.getResult() );
		forecastRunTaskItem.setResultDetail( bForecastRunTaskItem.getResultDetail() );
		
		forecastRunTaskItem.setBeginTime( bForecastRunTaskItem.getBeginTime() );
		forecastRunTaskItem.setEndTime( bForecastRunTaskItem.getEndTime() );
		forecastRunTaskItem.setEt( bForecastRunTaskItem.getEt() );
		forecastRunTaskItem.setMape( bForecastRunTaskItem.getMape() );
		forecastRunTaskItem.setMad( bForecastRunTaskItem.getMad() );
		forecastRunTaskItem.setTs( bForecastRunTaskItem.getTs() );
		forecastRunTaskItem.setMse( bForecastRunTaskItem.getMse() );
		forecastRunTaskItem.setForecastInstCode( bForecastRunTaskItem.getForecastInstCode() );
		forecastRunTaskItem.setForecastInstName( bForecastRunTaskItem.getForecastInstName() );
		forecastRunTaskItem.setForecastModelCode( bForecastRunTaskItem.getForecastModelCode() );
		forecastRunTaskItem.setForecastModelName( bForecastRunTaskItem.getForecastModelName() );		
		forecastRunTaskItem.setOutlierAnalyzePeriodNum( bForecastRunTaskItem.getOutlierAnalyzePeriodNum() );
		forecastRunTaskItem.setErrorThreshold( bForecastRunTaskItem.getErrorThreshold() );
		
		forecastRunTaskItem.setComments( bForecastRunTaskItem.getComments() );
		
		//	forecastRunTask
		if( bForecastRunTaskItem.getForecastRunTask() != null )
		{
			ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
			ForecastRunTask forecastRunTask = (ForecastRunTask) forecastRunTaskBDConvertor.btod( bForecastRunTaskItem.getForecastRunTask() );	
			forecastRunTaskItem.setForecastRunTask( forecastRunTask );
		}
		else
		{
			forecastRunTaskItem.setForecastRunTask(null);
		}
		
		//	forecastInst
		if( bForecastRunTaskItem.getForecastInst() != null )
		{
			ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
			ForecastInst forecastInst = (ForecastInst) forecastInstBDConvertor.btod( bForecastRunTaskItem.getForecastInst() );	
			forecastRunTaskItem.setForecastInst( forecastInst );
		}
		else
		{
			forecastRunTaskItem.setForecastInst(null);
		}		
		
		//	runProductLayer
		if( bForecastRunTaskItem.getRunProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			ProductLayer productLayer = (ProductLayer) productLayerBDConvertor.btod( bForecastRunTaskItem.getRunProductLayer() );	
			forecastRunTaskItem.setRunProductLayer(productLayer);
		}
		else
		{
			forecastRunTaskItem.setRunProductLayer(null);
		}			
		
		//	runOrganizationLayer
		if( bForecastRunTaskItem.getRunOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			OrganizationLayer organizationLayer = (OrganizationLayer) organizationLayerBDConvertor.btod( bForecastRunTaskItem.getRunOrganizationLayer() );	
			forecastRunTaskItem.setRunOrganizationLayer(organizationLayer);
		}
		else
		{
			forecastRunTaskItem.setRunOrganizationLayer(null);
		}		
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	@Override
	public Object btod( Object bObj )
	{
		// TODO Auto-generated method stub
		ForecastRunTaskItem forecastRunTaskItem = new ForecastRunTaskItem();
		this.btod( bObj, forecastRunTaskItem );
		return forecastRunTaskItem;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob( Object dObj, Object bObj )
	{
		// TODO Auto-generated method stub
		ForecastRunTaskItem  forecastRunTaskItem  = null;
		BForecastRunTaskItem bForecastRunTaskItem = null;
		
		if( dObj == null )
		{
			forecastRunTaskItem = new ForecastRunTaskItem();
		}
		else
		{
			forecastRunTaskItem = (ForecastRunTaskItem)dObj;
		}
		
		if( bObj == null )
		{
			return;
		}
		else
		{
			bForecastRunTaskItem = (BForecastRunTaskItem)bObj;
		}
	
		
		bForecastRunTaskItem.setVersion( forecastRunTaskItem.getVersion() );
		bForecastRunTaskItem.setId( forecastRunTaskItem.getId() );
		
		bForecastRunTaskItem.setSeqNo( forecastRunTaskItem.getSeqNo() );
		bForecastRunTaskItem.setIsIgnoreErrorThreshold( forecastRunTaskItem.getIsIgnoreErrorThreshold() );
		bForecastRunTaskItem.setStatus( forecastRunTaskItem.getStatus() );
		bForecastRunTaskItem.setResult( forecastRunTaskItem.getResult() );
		bForecastRunTaskItem.setResultDetail( forecastRunTaskItem.getResultDetail() );
		
		bForecastRunTaskItem.setBeginTime( forecastRunTaskItem.getBeginTime() );
		bForecastRunTaskItem.setEndTime( forecastRunTaskItem.getEndTime() );
		bForecastRunTaskItem.setEt( forecastRunTaskItem.getEt() );
		bForecastRunTaskItem.setMape( forecastRunTaskItem.getMape() );
		bForecastRunTaskItem.setMad( forecastRunTaskItem.getMad() );
		bForecastRunTaskItem.setTs( forecastRunTaskItem.getTs() );
		bForecastRunTaskItem.setMse( forecastRunTaskItem.getMse() );
		bForecastRunTaskItem.setForecastInstCode( forecastRunTaskItem.getForecastInstCode() );
		bForecastRunTaskItem.setForecastInstName( forecastRunTaskItem.getForecastInstName() );
		bForecastRunTaskItem.setForecastModelCode( forecastRunTaskItem.getForecastModelCode() );
		bForecastRunTaskItem.setForecastModelName( forecastRunTaskItem.getForecastModelName() );		
		bForecastRunTaskItem.setOutlierAnalyzePeriodNum( forecastRunTaskItem.getOutlierAnalyzePeriodNum() );
		bForecastRunTaskItem.setErrorThreshold( forecastRunTaskItem.getErrorThreshold() );
		
		bForecastRunTaskItem.setComments( forecastRunTaskItem.getComments() );
		
		//	forecastRunTask
		if( forecastRunTaskItem.getForecastRunTask() != null )
		{
			ForecastRunTaskBDConvertor forecastRunTaskBDConvertor = new ForecastRunTaskBDConvertor();
			BForecastRunTask bForecastRunTask = (BForecastRunTask) forecastRunTaskBDConvertor.dtob( forecastRunTaskItem.getForecastRunTask() );	
			bForecastRunTaskItem.setForecastRunTask( bForecastRunTask );
		}
		else
		{
			bForecastRunTaskItem.setForecastRunTask(null);
		}
		
		//	forecastInst
		if( forecastRunTaskItem.getForecastInst() != null )
		{
			ForecastInstBDConvertor forecastInstBDConvertor = new ForecastInstBDConvertor();
			BForecastInst bForecastInst = (BForecastInst) forecastInstBDConvertor.dtob( forecastRunTaskItem.getForecastInst() );	
			bForecastRunTaskItem.setForecastInst( bForecastInst );
		}
		else
		{
			bForecastRunTaskItem.setForecastInst(null);
		}		
		
		//	runProductLayer
		if( forecastRunTaskItem.getRunProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			BProductLayer bProductLayer = (BProductLayer) productLayerBDConvertor.dtob( forecastRunTaskItem.getRunProductLayer() );	
			bForecastRunTaskItem.setRunProductLayer(bProductLayer);
		}
		else
		{
			bForecastRunTaskItem.setRunProductLayer(null);
		}			
		
		//	runOrganizationLayer
		if( forecastRunTaskItem.getRunOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			BOrganizationLayer bOrganizationLayer = (BOrganizationLayer) organizationLayerBDConvertor.dtob( forecastRunTaskItem.getRunOrganizationLayer() );	
			bForecastRunTaskItem.setRunOrganizationLayer(bOrganizationLayer);
		}
		else
		{
			bForecastRunTaskItem.setRunOrganizationLayer(null);
		}
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob( Object dObj )
	{
		// TODO Auto-generated method stub
		BForecastRunTaskItem bForecastRunTaskItem = new BForecastRunTaskItem();
		this.dtob( dObj, bForecastRunTaskItem );
		return bForecastRunTaskItem;	
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
