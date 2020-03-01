/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 *
 */
public class ForecastModelMAAnalogItemBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ForecastModelMAAnalogItemBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod( Object b_obj, Object d_obj )
	{
		BForecastModelMAAnalogItem bForecastModelMAAnalogItem = null;;
		ForecastModelMAAnalogItem  forecastModelMAAnalogItem  = null;
		
		if( b_obj == null )
		{
			bForecastModelMAAnalogItem = new BForecastModelMAAnalogItem();
		}
		else
		{
			bForecastModelMAAnalogItem = (BForecastModelMAAnalogItem)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			forecastModelMAAnalogItem = (ForecastModelMAAnalogItem)d_obj;
		}
		
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
				
		forecastModelMAAnalogItem.setVersion( bForecastModelMAAnalogItem.getVersion() );
		forecastModelMAAnalogItem.setId( bForecastModelMAAnalogItem.getId() );
		forecastModelMAAnalogItem.setProduct( (Product)productBDConvertor.btod( bForecastModelMAAnalogItem.getProduct() ) );
		forecastModelMAAnalogItem.setOrganization( (Organization)organizationBDConvertor.btod( bForecastModelMAAnalogItem.getOrganization() ) );
		forecastModelMAAnalogItem.setCoefficient( bForecastModelMAAnalogItem.getCoefficient() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod( Object b_obj )
	{
		if( b_obj == null )
		{
			return null;
		}
		
		ForecastModelMAAnalogItem forecastModelMAAnalogItem = new ForecastModelMAAnalogItem();
		this.btod( b_obj, forecastModelMAAnalogItem );
		return forecastModelMAAnalogItem;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob( Object d_obj, Object b_obj )
	{
		ForecastModelMAAnalogItem  forecastModelMAAnalogItem  = null;
		BForecastModelMAAnalogItem bForecastModelMAAnalogItem = null;
		
		if( d_obj == null )
		{
			forecastModelMAAnalogItem = new ForecastModelMAAnalogItem();
		}
		else
		{
			forecastModelMAAnalogItem = (ForecastModelMAAnalogItem)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bForecastModelMAAnalogItem = (BForecastModelMAAnalogItem)b_obj;
		}
		
		bForecastModelMAAnalogItem.setVersion( forecastModelMAAnalogItem.getVersion() );
		bForecastModelMAAnalogItem.setId( forecastModelMAAnalogItem.getId() );
		//	直接从内存常驻变量中获取，获取完整树上的节点	begin
		bForecastModelMAAnalogItem.setProduct( ServerEnvironment.getInstance().getBProduct( forecastModelMAAnalogItem.getProduct().getId() ) );
		bForecastModelMAAnalogItem.setOrganization( ServerEnvironment.getInstance().getBOrganization( forecastModelMAAnalogItem.getOrganization().getId() ) );
		//	直接从内存常驻变量中获取，获取完整树上的节点	end
		
		bForecastModelMAAnalogItem.setCoefficient( forecastModelMAAnalogItem.getCoefficient() );
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob( Object d_obj )
	{
		if( d_obj == null )
		{
			return null;
		}
		
		BForecastModelMAAnalogItem bForecastModelMAAnalogItem = new BForecastModelMAAnalogItem();
		this.dtob( d_obj, bForecastModelMAAnalogItem );
		return bForecastModelMAAnalogItem;	
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
