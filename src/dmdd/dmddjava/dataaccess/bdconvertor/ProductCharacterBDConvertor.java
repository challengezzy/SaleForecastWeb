/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacterLayer;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer;

/**
 * @author liuzhen
 *
 */
public class ProductCharacterBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public ProductCharacterBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性productCharacterLayer,处理;
	 * 引用的对象属性parentProductCharacter,不处理;
	 * 下附的集合属性subProductCharacters productProCharacters,不处理  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BProductCharacter bProductCharacter = null;
		ProductCharacter   productCharacter = null;
		if( b_obj == null )
		{
			bProductCharacter = new BProductCharacter();
		}
		else
		{
			bProductCharacter = (BProductCharacter) b_obj;
		}

		if( d_obj == null )
		{
			return;
		}
		else
		{
			productCharacter = (ProductCharacter) d_obj;
		}
		
		productCharacter.setVersion( bProductCharacter.getVersion() );
		productCharacter.setId( bProductCharacter.getId() );
		productCharacter.setCode( bProductCharacter.getCode() );
		productCharacter.setName( bProductCharacter.getName() );
		productCharacter.setType( bProductCharacter.getType() );
		productCharacter.setIsCatalog( bProductCharacter.getIsCatalog() );
		productCharacter.setDescription( bProductCharacter.getDescription() );
		productCharacter.setComments( bProductCharacter.getComments() );	
		productCharacter.setPathCode(bProductCharacter.getPathCode());
		//    productCharacterLayer
		if( bProductCharacter.getProductCharacterLayer() != null )
		{
			ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
			ProductCharacterLayer productCharacterLayer = (ProductCharacterLayer) productCharacterLayerBDConvertor.btod(bProductCharacter.getProductCharacterLayer());	
			productCharacter.setProductCharacterLayer( productCharacterLayer );
		}
		else
		{
			productCharacter.setProductCharacterLayer(null);
		}				
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性productCharacterLayer,处理;
	 * 引用的对象属性parentProductCharacter,不处理;
	 * 下附的集合属性subProductCharacters productProCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		if( b_obj == null )
		{
			return null;
		}		
		ProductCharacter productCharacter = new ProductCharacter();
		this.btod(b_obj, productCharacter);
		return productCharacter;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性productCharacterLayer,处理;
	 * 引用的对象属性parentProductCharacter,不处理;
	 * 下附的集合属性subProductCharacters productProCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		ProductCharacter   productCharacter = null;
		BProductCharacter bProductCharacter = null;
		
		if( d_obj == null )
		{
			productCharacter = new ProductCharacter();
		}
		else
		{
			productCharacter = (ProductCharacter) d_obj;
		}

		if( b_obj == null )
		{
			return;
		}
		else
		{
			bProductCharacter = (BProductCharacter) b_obj;
		}
		
		bProductCharacter.setVersion( productCharacter.getVersion() );
		bProductCharacter.setId( productCharacter.getId() );
		bProductCharacter.setCode( productCharacter.getCode() );
		bProductCharacter.setName( productCharacter.getName() );
		bProductCharacter.setType( productCharacter.getType() );
		bProductCharacter.setIsCatalog( productCharacter.getIsCatalog() );
		bProductCharacter.setDescription( productCharacter.getDescription() );
		bProductCharacter.setComments( productCharacter.getComments() );
		bProductCharacter.setPathCode(productCharacter.getPathCode());
		//    productCharacterLayer
		if( productCharacter.getProductCharacterLayer() != null )
		{
			ProductCharacterLayerBDConvertor productCharacterLayerBDConvertor = new ProductCharacterLayerBDConvertor();
			BProductCharacterLayer bProductCharacterLayer = (BProductCharacterLayer) productCharacterLayerBDConvertor.dtob(productCharacter.getProductCharacterLayer());	
			bProductCharacter.setProductCharacterLayer( bProductCharacterLayer );
		}
		else
		{
			bProductCharacter.setProductCharacterLayer(null);
		}			
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性productCharacterLayer,处理;
	 * 引用的对象属性parentProductCharacter,不处理;
	 * 下附的集合属性subProductCharacters productProCharacters,不处理   
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		if( d_obj == null )
		{
			return null;
		}			
		BProductCharacter bProductCharacter = new BProductCharacter();
		this.dtob(d_obj, bProductCharacter);
		return bProductCharacter;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
