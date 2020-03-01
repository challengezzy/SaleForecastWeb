/**
 * 
 */
package dmdd.dmddjava.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.dataobject.ProductCharacter;
import dmdd.dmddjava.dataaccess.dataobject.ProductProCharacter;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProductLayer;

/**
 * @author liuzhen
 *
 */
public class UtilProduct
{

	/**
	 * 
	 */
	public UtilProduct()
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

	public static List<Product> getDetailProducts(Product _organization)
	{

		if (_organization == null)
		{
			return null;
		}

		List<Product> rstList = new ArrayList<Product>();
		if (_organization.getIsCatalog() == BizConst.GLOBAL_YESNO_NO)
		{
			rstList.add(_organization);
			return rstList;
		}
		else
		{
			if (_organization.getSubProducts() != null)
			{
				Iterator<Product> itr_subProducts = _organization.getSubProducts().iterator();
				while (itr_subProducts.hasNext())
				{
					Product subProduct = itr_subProducts.next();
					List<Product> rstList4subProduct = getDetailProducts(subProduct);
					if (rstList4subProduct != null)
					{
						rstList.addAll(rstList4subProduct);
					}
				}

				return rstList;
			}
			else
			{
				return null;
			}
		}
	}

	/**
	 * 算法功能：获得 _detailProduct 的 _productLayerValue 层次的祖先
	 * 算法前提：1. _detailProduct 是明细产品
	 * 算法注意：_detailProduct 的层次高于_productLayerValue对应的层次的话，返回_detailProduct本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static Product getProjectProductByLayer(Product _detailProduct, int _productLayerValue)
	{
		if (_detailProduct == null)
		{
			return null;
		}

		if (_detailProduct.getProductLayer().getValue() <= _productLayerValue)
		{
			return _detailProduct;
		}

		Product projectProduct = _detailProduct.getParentProduct();
		while (projectProduct != null)
		{
			if (projectProduct.getProductLayer().getValue() == _productLayerValue)
			{
				return projectProduct;
			}

			projectProduct = projectProduct.getParentProduct();
		}

		return null; // impossible result
	}
	
	/**
	 * 算法功能：获得 _detailProduct 的 _productLayerValue 层次的祖先
	 * 算法前提：1. _detailProduct 是明细产品
	 * 算法注意：_detailProduct 的层次高于_productLayerValue对应的层次的话，返回_detailProduct本身。虽属于不合理情况，但健壮性要求下，可以接受
	 */
	public static BProduct getProjectProductByLayer(BProduct _detailProduct, int _productLayerValue)
	{
		if (_detailProduct == null)
		{
			return null;
		}

		if (_detailProduct.getProductLayer().getValue() <= _productLayerValue)
		{
			return _detailProduct;
		}

		BProduct projectProduct = _detailProduct.getParentProduct();
		while (projectProduct != null)
		{
			if (projectProduct.getProductLayer().getValue() == _productLayerValue)
			{
				return projectProduct;
			}

			projectProduct = projectProduct.getParentProduct();
		}

		return null; // impossible result
	}
	
	public static ProductCharacter getDetailProductCharacter( Product _detailProduct, String _productCharacterType )
	{		
		if( _detailProduct == null )
		{
			return null;
		}

		if( _detailProduct.getProductProCharacters() == null || _detailProduct.getProductProCharacters().isEmpty() )
		{
			return null;
		}
		
		Iterator<ProductProCharacter> itr_ProductProCharacter = _detailProduct.getProductProCharacters().iterator();
		while( itr_ProductProCharacter.hasNext() )
		{
			ProductProCharacter productProCharacter = itr_ProductProCharacter.next();
			if( productProCharacter.getProductCharacter().getType().equals( _productCharacterType ) )
			{
				return productProCharacter.getProductCharacter();
			}
		}
		
		return null;
	}	
	
	/**
	 * 计算 _bProduct_1 与  _bProduct_2 的关系
	 * @param _bProduct_1
	 * @param _bProduct_2
	 * @return
	 */
	public static int relationOf( BProduct _bProduct_1, BProduct _bProduct_2 )
	{
		if( _bProduct_1 == null || _bProduct_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _bProduct_1.getId().longValue() == _bProduct_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		if( _bProduct_1.getProductLayer().getValue() == _bProduct_2.getProductLayer().getValue() )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		boolean blSwitched = false;
		BProduct bProduct_1 = null;
		BProduct bProduct_2 = null;
		if( _bProduct_1.getProductLayer().getValue() > _bProduct_2.getProductLayer().getValue() )
		{
			bProduct_1 = _bProduct_2;
			bProduct_2 = _bProduct_1;
			blSwitched = true;
		}	
		else
		{
			bProduct_1 = _bProduct_1;
			bProduct_2 = _bProduct_2;
			blSwitched = false;				
		}			
		
		// 爬到一个层次上
		BProduct ancestorBProduct = bProduct_2.getParentProduct();
		while( ancestorBProduct != null  )
		{	
			if( ancestorBProduct.getProductLayer().getValue() == bProduct_1.getProductLayer().getValue() )
			{
				break;
			}			
			ancestorBProduct = ancestorBProduct.getParentProduct();
		}		
		// ancestorBProduct 不会为空
		if( ancestorBProduct.getId().longValue() == bProduct_1.getId().longValue() )
		{
			// bProduct_1 是 bProduct_2的祖先
			if( blSwitched == false )
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
	}		
	
	/**
	 * 计算 _product_1 与  _product_2 的关系, 重新获取session
	 * @param _product_1
	 * @param _product_2
	 * @return
	 */
	public static int relationOf_2( Product _product_1, Product _product_2 )
	{
		if( _product_1 == null || _product_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _product_1.getId().longValue() == _product_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		int value1 = getProductLayoutValueByProductCode(_product_1.getCode());
		int value2 = getProductLayoutValueByProductCode(_product_2.getCode());
		if( value1 == value2 )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		boolean blSwitched = false;
		Product product_1 = null;
		Product product_2 = null;
		if( value1 > value2 )
		{
			product_1 = _product_2;
			product_2 = _product_1;
			blSwitched = true;
		}	
		else
		{
			product_1 = _product_1;
			product_2 = _product_2;
			blSwitched = false;				
		}			
		
		// 爬到一个层次上
		Product ancestorProduct = product_2.getParentProduct();
		int _vaule2 = getProductLayoutValueByProductCode(ancestorProduct.getCode());
		while( ancestorProduct != null  )
		{	
			if( _vaule2 ==value1 )
			{
				break;
			}			
			ancestorProduct = ancestorProduct.getParentProduct();
		}		
		// ancestorProduct 不会为空
		if( ancestorProduct.getId().longValue() == product_1.getId().longValue() )
		{
			// product_1 是 product_2的祖先
			if( blSwitched == false )
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
	}	
	
	/**
	 * 根据pathcode计算
	 * @param _product_1
	 * @param _product_2
	 * @return
	 */
	public static int relationOf_pathCode( Product _product_1, Product _product_2 )
	{
		if( _product_1 == null || _product_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _product_1.getId().longValue() == _product_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		
		String pathCode1 = _product_1.getPathCode();
		String pathCode2 = _product_2.getPathCode();
		
		if(pathCode1.indexOf(pathCode2)>=0)//p2>p1
		{
			return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
		}
		else if(pathCode2.indexOf(pathCode1)>=0)//p1>p2
		{
			return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		
//		int value1 = getProductLayoutValueByProductCode(_product_1.getCode());
//		int value2 = getProductLayoutValueByProductCode(_product_2.getCode());
//		if( value1 == value2 )
//		{
//			// id 不等，又在一个层次上
//			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
//		}
//		
//		boolean blSwitched = false;
//		Product product_1 = null;
//		Product product_2 = null;
//		if( value1 > value2 )
//		{
//			product_1 = _product_2;
//			product_2 = _product_1;
//			blSwitched = true;
//		}	
//		else
//		{
//			product_1 = _product_1;
//			product_2 = _product_2;
//			blSwitched = false;				
//		}			
//		
//		// 爬到一个层次上
//		Product ancestorProduct = product_2.getParentProduct();
//		int _vaule2 = getProductLayoutValueByProductCode(ancestorProduct.getCode());
//		while( ancestorProduct != null  )
//		{	
//			if( _vaule2 ==value1 )
//			{
//				break;
//			}			
//			ancestorProduct = ancestorProduct.getParentProduct();
//		}		
//		// ancestorProduct 不会为空
//		if( ancestorProduct.getId().longValue() == product_1.getId().longValue() )
//		{
//			// product_1 是 product_2的祖先
//			if( blSwitched == false )
//			{
//				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
//			}
//			else
//			{
//				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
//			}
//		}
//		else
//		{
//			// 无关
//			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
//		}
	}	
	/**
	 * 计算 _product_1 与  _product_2 的关系
	 * @param _product_1
	 * @param _product_2
	 * @return
	 */
	public static int relationOf( Product _product_1, Product _product_2 )
	{
		if( _product_1 == null || _product_2 == null )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED;
		}
		if( _product_1.getId().longValue() == _product_2.getId().longValue() )
		{
			return UtilProOrg.RELATION_FIRST2SECOND_EQUAL;
		}
		if( _product_1.getProductLayer().getValue() == _product_2.getProductLayer().getValue() )
		{
			// id 不等，又在一个层次上
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
		
		boolean blSwitched = false;
		Product product_1 = null;
		Product product_2 = null;
		if( _product_1.getProductLayer().getValue() > _product_2.getProductLayer().getValue() )
		{
			product_1 = _product_2;
			product_2 = _product_1;
			blSwitched = true;
		}	
		else
		{
			product_1 = _product_1;
			product_2 = _product_2;
			blSwitched = false;				
		}			
		
		// 爬到一个层次上
		Product ancestorProduct = product_2.getParentProduct();
		while( ancestorProduct != null  )
		{	
			if( ancestorProduct.getProductLayer().getValue() ==product_1.getProductLayer().getValue() )
			{
				break;
			}			
			ancestorProduct = ancestorProduct.getParentProduct();
		}		
		// ancestorProduct 不会为空
		if( ancestorProduct.getId().longValue() == product_1.getId().longValue() )
		{
			// product_1 是 product_2的祖先
			if( blSwitched == false )
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERING;
			}
			else
			{
				return UtilProOrg.RELATION_FIRST2SECOND_COVERED;
			}
		}
		else
		{
			// 无关
			return UtilProOrg.RELATION_FIRST2SECOND_IRRELATED; 
		}
	}	
	
	/**
	 * 在 _productTreeRoot 为根的树中寻找 id = _tagrgetProductId 的树节点
	 * @param _tagrgetProductId
	 * @param _productTreeRoot
	 * @return
	 */
	public static Product getProductInTreeByRecursion( Long _tagrgetProductId, Product _productTreeRoot )
	{
		if( _tagrgetProductId == null )
		{
			return null;
		}
		
		if( _productTreeRoot == null )
		{
			return null;				
		}
		
		if( _productTreeRoot.getId().longValue() == _tagrgetProductId.longValue() )
		{
			return _productTreeRoot;
		}
		else if( _productTreeRoot.getSubProducts() == null || (_productTreeRoot.getSubProducts().isEmpty()) )
		{
			return null;
		}

		Iterator<Product> itr_SubProducts = _productTreeRoot.getSubProducts().iterator();
		while( itr_SubProducts.hasNext() )
		{
			Product subProduct = itr_SubProducts.next();
			Product rstTargetProduct = getProductInTreeByRecursion(_tagrgetProductId, subProduct );
			if( rstTargetProduct != null )
			{
				return rstTargetProduct;
			}
		}
		return null;
	}	
	
	public static List<BProduct> getDetailProducts(BProduct _bProduct)
	{

		if (_bProduct == null)
		{
			return null;
		}

		List<BProduct> rstList = new ArrayList<BProduct>();
		if (_bProduct.getIsCatalog() == BizConst.GLOBAL_YESNO_NO)
		{
			rstList.add(_bProduct);
			return rstList;
		}
		else
		{
			if (_bProduct.getSubProducts() != null)
			{
				Iterator<BProduct> itr_subProducts = _bProduct.getSubProducts().iterator();
				while (itr_subProducts.hasNext())
				{
					BProduct subProduct = itr_subProducts.next();
					List<BProduct> rstList4subProduct = getDetailProducts(subProduct);
					if (rstList4subProduct != null)
					{
						rstList.addAll(rstList4subProduct);
					}
				}

				return rstList;
			}
			else
			{
				return null;
			}
		}
	}
	
	public static int getProductLayoutValueByProductCode(String code)
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		int value = -1;
		try
		{
			trsa = session.beginTransaction();
			DaoProductLayer dao = new DaoProductLayer(session);
			value = dao.getProudctLayourValueByProductCode(code);
			return value;
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
		return -1;
	}
	
	public static List<Long> getIds(List<BProduct> list)
	{
		List<Long> ids = new ArrayList<Long>();
		if(list ==null|| list.isEmpty())
		{
			return null;
		}
		for(BProduct product:list)
		{
			ids.add(product.getId());
		}
		
		return ids;
	}
}
