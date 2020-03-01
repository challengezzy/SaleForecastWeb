/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcHand;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcHand;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemFcHandBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemFcHandBDConvertor()
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
		BBizDataDefItemFcHand bBizDataDefItemFcHand = null;
		BizDataDefItemFcHand   bizDataDefItemFcHand = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemFcHand = new BBizDataDefItemFcHand();
		}
		else
		{
			bBizDataDefItemFcHand = (BBizDataDefItemFcHand) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemFcHand = (BizDataDefItemFcHand) d_obj;
		}
		
		super.btod(bBizDataDefItemFcHand, bizDataDefItemFcHand);
		
		bizDataDefItemFcHand.setCoefficient(bBizDataDefItemFcHand.getCoefficient());
		
		if( bBizDataDefItemFcHand.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemFcHand.getItemBizData(), true );
			bizDataDefItemFcHand.setItemBizData(itemBizData);
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
		BizDataDefItemFcHand bizDataDefItemFcHand = new BizDataDefItemFcHand();
		this.btod(b_obj, bizDataDefItemFcHand);
		return bizDataDefItemFcHand;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemFcHand   bizDataDefItemFcHand = null;
		BBizDataDefItemFcHand bBizDataDefItemFcHand = null;
				
		if( d_obj == null )
		{
			bizDataDefItemFcHand = new BizDataDefItemFcHand();
		}
		else
		{
			bizDataDefItemFcHand = (BizDataDefItemFcHand) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemFcHand = (BBizDataDefItemFcHand) b_obj;
		}
		
		super.dtob(bizDataDefItemFcHand, bBizDataDefItemFcHand);
		
		bBizDataDefItemFcHand.setCoefficient( bizDataDefItemFcHand.getCoefficient() );

		if( bizDataDefItemFcHand.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemFcHand.getItemBizData(), true );
			bBizDataDefItemFcHand.setItemBizData(itemBizData);
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
		BBizDataDefItemFcHand bBizDataDefItemFcHand = new BBizDataDefItemFcHand();
		this.dtob(d_obj, bBizDataDefItemFcHand);
		return bBizDataDefItemFcHand;
	}

}
