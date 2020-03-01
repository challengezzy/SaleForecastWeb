/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.ForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;

/**
 * @author liuzhen
 *
 */
public class ForecastErrorMappingModelBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastErrorMappingModelBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BForecastErrorMappingModel bForecastErrorMappingModel = null;
		ForecastErrorMappingModel   forecastErrorMappingModel = null;
		
		if( b_obj == null )
		{
			bForecastErrorMappingModel = new BForecastErrorMappingModel();
		}
		else
		{
			bForecastErrorMappingModel = (BForecastErrorMappingModel)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastErrorMappingModel = (ForecastErrorMappingModel)d_obj;
		}
		
		forecastErrorMappingModel.setVersion( bForecastErrorMappingModel.getVersion() );
		forecastErrorMappingModel.setId( bForecastErrorMappingModel.getId() );
		forecastErrorMappingModel.setForecastInstCode( bForecastErrorMappingModel.getForecastInstCode() );
		forecastErrorMappingModel.setForecastInstName( bForecastErrorMappingModel.getForecastInstName() );
		forecastErrorMappingModel.setForecastModelCode( bForecastErrorMappingModel.getForecastModelCode() );
		forecastErrorMappingModel.setForecastModelName( bForecastErrorMappingModel.getForecastModelName() );
		forecastErrorMappingModel.setCompilePeriod( bForecastErrorMappingModel.getCompilePeriod() );		
		forecastErrorMappingModel.setOutlierAnalyzePeriodNum( bForecastErrorMappingModel.getOutlierAnalyzePeriodNum() );
		forecastErrorMappingModel.setErrorThreshold( bForecastErrorMappingModel.getErrorThreshold() );
		forecastErrorMappingModel.setEt( bForecastErrorMappingModel.getEt() );
		forecastErrorMappingModel.setMape( bForecastErrorMappingModel.getMape() );
		forecastErrorMappingModel.setMad( bForecastErrorMappingModel.getMad() );
		forecastErrorMappingModel.setTs( bForecastErrorMappingModel.getTs() );
		forecastErrorMappingModel.setMse( bForecastErrorMappingModel.getMse() );
		forecastErrorMappingModel.setProducedTime( bForecastErrorMappingModel.getProducedTime() );
		forecastErrorMappingModel.setComments( bForecastErrorMappingModel.getComments() );
		
		//    runProductLayer
		if( bForecastErrorMappingModel.getRunProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			ProductLayer productLayer = (ProductLayer) productLayerBDConvertor.btod( bForecastErrorMappingModel.getRunProductLayer() );	
			forecastErrorMappingModel.setRunProductLayer(productLayer);
		}
		else
		{
			forecastErrorMappingModel.setRunProductLayer(null);
		}		
		
		//    runOrganizationLayer
		if( bForecastErrorMappingModel.getRunOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			OrganizationLayer organizationLayer = (OrganizationLayer) organizationLayerBDConvertor.btod( bForecastErrorMappingModel.getRunOrganizationLayer() );	
			forecastErrorMappingModel.setRunOrganizationLayer(organizationLayer);
		}
		else
		{
			forecastErrorMappingModel.setRunOrganizationLayer(null);
		}	
		

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		ForecastErrorMappingModel forecastErrorMappingModel = new ForecastErrorMappingModel();
		this.btod(b_obj, forecastErrorMappingModel);
		return forecastErrorMappingModel;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ForecastErrorMappingModel   forecastErrorMappingModel = null;
		BForecastErrorMappingModel bForecastErrorMappingModel = null;
		
		if( d_obj == null )
		{
			forecastErrorMappingModel = new ForecastErrorMappingModel();
		}
		else
		{
			forecastErrorMappingModel = (ForecastErrorMappingModel)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastErrorMappingModel = (BForecastErrorMappingModel)b_obj;
		}
		
		bForecastErrorMappingModel.setVersion( forecastErrorMappingModel.getVersion() );
		bForecastErrorMappingModel.setId( forecastErrorMappingModel.getId() );
		bForecastErrorMappingModel.setForecastInstCode( forecastErrorMappingModel.getForecastInstCode() );
		bForecastErrorMappingModel.setForecastInstName( forecastErrorMappingModel.getForecastInstName() );
		bForecastErrorMappingModel.setForecastModelCode( forecastErrorMappingModel.getForecastModelCode() );
		bForecastErrorMappingModel.setForecastModelName( forecastErrorMappingModel.getForecastModelName() );
		bForecastErrorMappingModel.setCompilePeriod( forecastErrorMappingModel.getCompilePeriod() );		
		bForecastErrorMappingModel.setOutlierAnalyzePeriodNum( forecastErrorMappingModel.getOutlierAnalyzePeriodNum() );
		bForecastErrorMappingModel.setErrorThreshold( forecastErrorMappingModel.getErrorThreshold() );
		bForecastErrorMappingModel.setEt( forecastErrorMappingModel.getEt() );
		bForecastErrorMappingModel.setMape( forecastErrorMappingModel.getMape() );
		bForecastErrorMappingModel.setMad( forecastErrorMappingModel.getMad() );
		bForecastErrorMappingModel.setTs( forecastErrorMappingModel.getTs() );
		bForecastErrorMappingModel.setMse( forecastErrorMappingModel.getMse() );
		bForecastErrorMappingModel.setProducedTime( forecastErrorMappingModel.getProducedTime() );
		bForecastErrorMappingModel.setComments( forecastErrorMappingModel.getComments() );
		

		//    runProductLayer
		if( forecastErrorMappingModel.getRunProductLayer() != null )
		{
			ProductLayerBDConvertor productLayerBDConvertor = new ProductLayerBDConvertor();
			BProductLayer bProductLayer = (BProductLayer) productLayerBDConvertor.dtob( forecastErrorMappingModel.getRunProductLayer() );	
			bForecastErrorMappingModel.setRunProductLayer(bProductLayer);
		}
		else
		{
			bForecastErrorMappingModel.setRunProductLayer(null);
		}		
		
		//    runOrganizationLayer
		if( forecastErrorMappingModel.getRunOrganizationLayer() != null )
		{
			OrganizationLayerBDConvertor organizationLayerBDConvertor = new OrganizationLayerBDConvertor();
			BOrganizationLayer bOrganizationLayer = (BOrganizationLayer) organizationLayerBDConvertor.dtob( forecastErrorMappingModel.getRunOrganizationLayer() );	
			bForecastErrorMappingModel.setRunOrganizationLayer(bOrganizationLayer);
		}
		else
		{
			bForecastErrorMappingModel.setRunOrganizationLayer(null);
		}	

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BForecastErrorMappingModel bForecastErrorMappingModel = new BForecastErrorMappingModel();
		this.dtob(d_obj, bForecastErrorMappingModel);
		return bForecastErrorMappingModel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
