/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BPriceData;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.PriceData;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 *
 */
public class PriceDataBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public PriceDataBDConvertor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod( Object bObj, Object dObj )
	{
		BPriceData bPriceData = null;
		PriceData   priceData  = null;
		if( bObj == null )
		{
			bPriceData = new BPriceData();
		}
		else
		{
			bPriceData = (BPriceData)bObj;
		}
		
		if( dObj == null )
		{
			return;
		}
		else
		{
			priceData = (PriceData)dObj;
		}
		
		priceData.setVersion( bPriceData.getVersion() );
		priceData.setId( bPriceData.getId() );
		priceData.setPeriod( bPriceData.getPeriod() );
		priceData.setStandardPrice( bPriceData.getStandardPrice() );
		priceData.setRealPrice( bPriceData.getRealPrice() );
		priceData.setComments( bPriceData.getComments() );
		
		// product
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();
		priceData.setProduct( (Product)productBDConvertor.btod( bPriceData.getProduct() ) );
		
		// organization
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		priceData.setOrganization( (Organization)organizationBDConvertor.btod( bPriceData.getOrganization() ) );		

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	@Override
	public Object btod( Object bObj )
	{
		PriceData priceData = new PriceData();
		this.btod(bObj, priceData);
		return priceData;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob( Object dObj, Object bObj )
	{
		PriceData  priceData  = null;
		BPriceData bPriceData = null;
		
		if( dObj == null )
		{
			priceData = new PriceData();
		}
		else
		{
			priceData = (PriceData)dObj;
		}
		
		if( bObj == null )
		{
			return;
		}
		else
		{
			bPriceData = (BPriceData)bObj;
		}
		
		bPriceData.setVersion( priceData.getVersion() );
		bPriceData.setId( priceData.getId() );
		bPriceData.setPeriod( priceData.getPeriod() );
		bPriceData.setStandardPrice( priceData.getStandardPrice() );
		bPriceData.setRealPrice( priceData.getRealPrice() );
		bPriceData.setComments( priceData.getComments() );
		
		// product
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();
		bPriceData.setProduct( (BProduct)productBDConvertor.dtob( priceData.getProduct() ) );
		
		// organization
		OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		bPriceData.setOrganization( (BOrganization)organizationBDConvertor.dtob( priceData.getOrganization() ) );		

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob( Object dObj )
	{
		BPriceData bPriceData = new BPriceData();
		this.dtob(dObj, bPriceData);
		return bPriceData;
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
