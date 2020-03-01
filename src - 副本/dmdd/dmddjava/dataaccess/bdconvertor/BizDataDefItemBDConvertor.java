/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItem;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemBDConvertor implements BDConvertorInterface
{

	/**
	 * 
	 */
	public BizDataDefItemBDConvertor()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性bizData,不处理;
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object, java.lang.Object)
	 */
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItem bBizDataDefItem = null;
		BizDataDefItem   bizDataDefItem = null;
		
		if( b_obj == null )
		{
			bBizDataDefItem = new BBizDataDefItem();
		}
		else
		{
			bBizDataDefItem = (BBizDataDefItem) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItem = (BizDataDefItem) d_obj;
		}
		
		bizDataDefItem.setVersion( bBizDataDefItem.getVersion() );
		bizDataDefItem.setId( bBizDataDefItem.getId() );
		bizDataDefItem.setIndicator( bBizDataDefItem.getIndicator() );

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性bizData,不处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#btod(java.lang.Object)
	 */
	public Object btod(Object b_obj)
	{
		BizDataDefItem bizDataDefItem = new BizDataDefItem();
		this.btod(b_obj, bizDataDefItem);
		return bizDataDefItem;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性bizData,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object, java.lang.Object)
	 */
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItem   bizDataDefItem = null;
		BBizDataDefItem bBizDataDefItem = null;
		
		if( d_obj == null )
		{
			bizDataDefItem = new BizDataDefItem();
		}
		else
		{
			bizDataDefItem = (BizDataDefItem) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItem = (BBizDataDefItem) b_obj;
		}
		
		bBizDataDefItem.setVersion( bizDataDefItem.getVersion() );
		bBizDataDefItem.setId( bizDataDefItem.getId() );
		bBizDataDefItem.setIndicator( bizDataDefItem.getIndicator() );

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性bizData,不处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BDConvertorInterface#dtob(java.lang.Object)
	 */
	public Object dtob(Object d_obj)
	{
		BBizDataDefItem bBizDataDefItem = new BBizDataDefItem();
		this.dtob(d_obj, bBizDataDefItem);
		return bBizDataDefItem;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
