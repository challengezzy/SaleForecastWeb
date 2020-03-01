/**
 * 
 */
package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProductProCharacter;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;

/**
 * @author liuzhen
 *
 */
public class UtilProductCharacter
{

	/**
	 * 
	 */
	public UtilProductCharacter()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 算法功能：获得 _detailProductCharacter 的 _productChatacterLayerValue 层次的祖先
	 * 算法前提：1. _detailProductCharacter 是明细产品
	 * 算法注意：_detailProductCharacter 的层次高于_productChatacterLayerValue对应的层次的话，返回_detailProductCharacter本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static ProductCharacter getProjectProductCharacterByLayer(ProductCharacter _detailProductCharacter, int _productChatacterLayerValue)
	{
		if (_detailProductCharacter == null)
		{
			return null;
		}

		if (_detailProductCharacter.getProductCharacterLayer().getValue() <= _productChatacterLayerValue)
		{
			return _detailProductCharacter;
		}

		ProductCharacter projectProductCharacter = _detailProductCharacter.getParentProductCharacter();
		while (projectProductCharacter != null)
		{
			if (projectProductCharacter.getProductCharacterLayer().getValue() == _productChatacterLayerValue)
			{
				return projectProductCharacter;
			}

			projectProductCharacter = projectProductCharacter.getParentProductCharacter();
		}

		return null; // impossible result
	}
	
	/**
	 * 算法功能：获得 _detailProductCharacter 的 _productChatacterLayerValue 层次的祖先
	 * 算法前提：1. _detailProductCharacter 是明细产品
	 * 算法注意：_detailProductCharacter 的层次高于_productChatacterLayerValue对应的层次的话，返回_detailProductCharacter本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static BProductCharacter getProjectProductCharacterByLayer(BProductCharacter _detailProductCharacter, int _productChatacterLayerValue)
	{
		if (_detailProductCharacter == null)
		{
			return null;
		}

		if (_detailProductCharacter.getProductCharacterLayer().getValue() <= _productChatacterLayerValue)
		{
			return _detailProductCharacter;
		}

		BProductCharacter projectProductCharacter = _detailProductCharacter.getParentProductCharacter();
		while (projectProductCharacter != null)
		{
			if (projectProductCharacter.getProductCharacterLayer().getValue() == _productChatacterLayerValue)
			{
				return projectProductCharacter;
			}

			projectProductCharacter = projectProductCharacter.getParentProductCharacter();
		}

		return null; // impossible result
	}
	/**
	 * 判断 _productCharacter_1 是否包含 _productCharacter_2
	 * 
	 */ 
	public static boolean isAncestorOf(BProductCharacter _productCharacter_1,BProductCharacter _productCharacter_2 )
	{

		if( _productCharacter_1 == null )
		{
			return false;
		}
			
		if( _productCharacter_2 == null )
		{
			return false;
		}			
		long _sourceid = _productCharacter_1.getId();
		long _targetid = _productCharacter_2.getId();
		
		if(_sourceid==_targetid)
		{
			return true;
		}
		
		BProductCharacter ancestorProductCharacter = _productCharacter_2.getParentProductCharacter();
		while( ancestorProductCharacter != null )
		{
			_targetid = ancestorProductCharacter.getId();
			
			if(_targetid ==_sourceid)
			{
				return true;
			}
			
			ancestorProductCharacter = ancestorProductCharacter.getParentProductCharacter();				
		}
		
		return false;
	}		
	
	
	public static List<ProductCharacter> getProductCharacters(ProductCharacter _productCharacter)
	{
		List<ProductCharacter> list = new ArrayList<ProductCharacter>();
		
		if(_productCharacter!=null)
		{
			list.add( _productCharacter );
		}
		
		if(_productCharacter.getSubProductCharacters()!=null && !_productCharacter.getSubProductCharacters().isEmpty())
		{
			for(ProductCharacter productCharacter:_productCharacter.getSubProductCharacters())
			{
				list.addAll(  getProductCharacters(productCharacter) );
			}	
		}
		
		return list;
	}
	
	/**
	 * 获取 _detailProduct 的 _productCharacterType 类型的产品特征
	 * 
	 */ 
	public static BProductCharacter getDetailProductCharacter(BProduct _detailProduct,String _productCharacterType )
	{		
		if( _detailProduct == null )
		{
			return null;
		}
		
		Set<BProductProCharacter> productProCharacters = _detailProduct.getProductProCharacters();
		if( productProCharacters == null )
		{
			return null;
		}			
		
		for( BProductProCharacter  productProCharacter:productProCharacters)
		{
			
			if( productProCharacter.getProductCharacter().getType().equals( _productCharacterType ))
			{
				return productProCharacter.getProductCharacter();
			}
		}
		
		return null;
	}		
}
