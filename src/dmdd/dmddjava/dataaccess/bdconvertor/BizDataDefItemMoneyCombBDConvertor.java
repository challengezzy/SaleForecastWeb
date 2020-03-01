/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoneyComb;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemMoneyComb;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemMoneyCombBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemMoneyCombBDConvertor()
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

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItemMoneyComb bBizDataDefItemMoneyComb = null;
		BizDataDefItemMoneyComb   bizDataDefItemMoneyComb = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemMoneyComb = new BBizDataDefItemMoneyComb();
		}
		else
		{
			bBizDataDefItemMoneyComb = (BBizDataDefItemMoneyComb) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemMoneyComb = (BizDataDefItemMoneyComb) d_obj;
		}
		
		super.btod(bBizDataDefItemMoneyComb, bizDataDefItemMoneyComb);
		
		bizDataDefItemMoneyComb.setCoefficient(bBizDataDefItemMoneyComb.getCoefficient());
		
		if( bBizDataDefItemMoneyComb.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemMoneyComb.getItemBizData(), true );
			bizDataDefItemMoneyComb.setItemBizData(itemBizData);
		}
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		BizDataDefItemMoneyComb bizDataDefItemMoneyComb = new BizDataDefItemMoneyComb();
		this.btod(b_obj, bizDataDefItemMoneyComb);
		return bizDataDefItemMoneyComb;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemMoneyComb   bizDataDefItemMoneyComb = null;
		BBizDataDefItemMoneyComb bBizDataDefItemMoneyComb = null;
				
		if( d_obj == null )
		{
			bizDataDefItemMoneyComb = new BizDataDefItemMoneyComb();
		}
		else
		{
			bizDataDefItemMoneyComb = (BizDataDefItemMoneyComb) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemMoneyComb = (BBizDataDefItemMoneyComb) b_obj;
		}
		
		super.dtob(bizDataDefItemMoneyComb, bBizDataDefItemMoneyComb);
		
		bBizDataDefItemMoneyComb.setCoefficient( bizDataDefItemMoneyComb.getCoefficient() );

		if( bizDataDefItemMoneyComb.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemMoneyComb.getItemBizData(), true );
			bBizDataDefItemMoneyComb.setItemBizData(itemBizData);
		}
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BBizDataDefItemMoneyComb bBizDataDefItemMoneyComb = new BBizDataDefItemMoneyComb();
		this.dtob(d_obj, bBizDataDefItemMoneyComb);
		return bBizDataDefItemMoneyComb;
	}

}
