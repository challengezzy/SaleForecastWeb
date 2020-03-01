/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcComb;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemFcCombBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemFcCombBDConvertor()
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
		BBizDataDefItemFcComb bBizDataDefItemFcComb = null;
		BizDataDefItemFcComb   bizDataDefItemFcComb = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemFcComb = new BBizDataDefItemFcComb();
		}
		else
		{
			bBizDataDefItemFcComb = (BBizDataDefItemFcComb) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemFcComb = (BizDataDefItemFcComb) d_obj;
		}
		
		super.btod(bBizDataDefItemFcComb, bizDataDefItemFcComb);
		
		bizDataDefItemFcComb.setCoefficient(bBizDataDefItemFcComb.getCoefficient());
		
		if( bBizDataDefItemFcComb.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemFcComb.getItemBizData(), true );
			bizDataDefItemFcComb.setItemBizData(itemBizData);
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
		BizDataDefItemFcComb bizDataDefItemFcComb = new BizDataDefItemFcComb();
		this.btod(b_obj, bizDataDefItemFcComb);
		return bizDataDefItemFcComb;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemFcComb   bizDataDefItemFcComb = null;
		BBizDataDefItemFcComb bBizDataDefItemFcComb = null;
				
		if( d_obj == null )
		{
			bizDataDefItemFcComb = new BizDataDefItemFcComb();
		}
		else
		{
			bizDataDefItemFcComb = (BizDataDefItemFcComb) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemFcComb = (BBizDataDefItemFcComb) b_obj;
		}
		
		super.dtob(bizDataDefItemFcComb, bBizDataDefItemFcComb);
		
		bBizDataDefItemFcComb.setCoefficient( bizDataDefItemFcComb.getCoefficient() );

		if( bizDataDefItemFcComb.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemFcComb.getItemBizData(), true );
			bBizDataDefItemFcComb.setItemBizData(itemBizData);
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
		BBizDataDefItemFcComb bBizDataDefItemFcComb = new BBizDataDefItemFcComb();
		this.dtob(d_obj, bBizDataDefItemFcComb);
		return bBizDataDefItemFcComb;
	}

}
