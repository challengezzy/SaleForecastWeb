/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer;

/**
 * @author liuzhen
 *
 */
public class ProductCharacterLayerBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ProductCharacterLayerBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BProductCharacterLayer bProductCharacterLayer = null;
		ProductCharacterLayer  productCharacterLayer  = null;
		if( b_obj == null )
		{
			bProductCharacterLayer = new BProductCharacterLayer();
		}
		else
		{
			bProductCharacterLayer = (BProductCharacterLayer)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			productCharacterLayer = (ProductCharacterLayer)d_obj;
		}
		
		productCharacterLayer.setVersion( bProductCharacterLayer.getVersion() );
		productCharacterLayer.setId( bProductCharacterLayer.getId() );
		productCharacterLayer.setValue( bProductCharacterLayer.getValue() );
		productCharacterLayer.setDescription( bProductCharacterLayer.getDescription() );
		productCharacterLayer.setComments( bProductCharacterLayer.getComments() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		ProductCharacterLayer productCharacterLayer = new ProductCharacterLayer();
		this.btod(b_obj, productCharacterLayer);
		return productCharacterLayer;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ProductCharacterLayer  productCharacterLayer  = null;
		BProductCharacterLayer bProductCharacterLayer = null;
		
		if( d_obj == null )
		{
			productCharacterLayer = new ProductCharacterLayer();
		}
		else
		{
			productCharacterLayer = (ProductCharacterLayer)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bProductCharacterLayer = (BProductCharacterLayer)b_obj;
		}
		
		bProductCharacterLayer.setVersion( productCharacterLayer.getVersion() );
		bProductCharacterLayer.setId( productCharacterLayer.getId() );
		bProductCharacterLayer.setValue( productCharacterLayer.getValue() );
		bProductCharacterLayer.setDescription( productCharacterLayer.getDescription() );
		bProductCharacterLayer.setComments( productCharacterLayer.getComments() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BProductCharacterLayer bProductCharacterLayer = new BProductCharacterLayer();
		this.dtob(d_obj, bProductCharacterLayer);
		return bProductCharacterLayer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
