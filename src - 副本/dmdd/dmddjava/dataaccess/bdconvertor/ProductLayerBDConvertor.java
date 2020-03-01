/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;

/**
 * @author liuzhen
 *
 */
public class ProductLayerBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ProductLayerBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BProductLayer bProductLayer = null;
		ProductLayer  productLayer  = null;
		if( b_obj == null )
		{
			bProductLayer = new BProductLayer();
		}
		else
		{
			bProductLayer = (BProductLayer)b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			productLayer = (ProductLayer)d_obj;
		}
		
		productLayer.setVersion( bProductLayer.getVersion() );
		productLayer.setId( bProductLayer.getId() );
		productLayer.setValue( bProductLayer.getValue() );
		productLayer.setDescription( bProductLayer.getDescription() );
		productLayer.setComments( bProductLayer.getComments() );
		
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		ProductLayer productLayer = new ProductLayer();
		this.btod(b_obj, productLayer);
		return productLayer;
	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ProductLayer  productLayer  = null;
		BProductLayer bProductLayer = null;
		
		if( d_obj == null )
		{
			productLayer = new ProductLayer();
		}
		else
		{
			productLayer = (ProductLayer)d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bProductLayer = (BProductLayer)b_obj;
		}
		
		bProductLayer.setVersion( productLayer.getVersion() );
		bProductLayer.setId( productLayer.getId() );
		bProductLayer.setValue( productLayer.getValue() );
		bProductLayer.setDescription( productLayer.getDescription() );
		bProductLayer.setComments( productLayer.getComments() );

	}

	/* (non-Javadoc)
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BProductLayer bProductLayer = new BProductLayer();
		this.dtob(d_obj, bProductLayer);
		return bProductLayer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
