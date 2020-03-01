/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoney;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemMoney;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemMoneyBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemMoneyBDConvertor()
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
		BBizDataDefItemMoney bBizDataDefItemMoney = null;
		BizDataDefItemMoney   bizDataDefItemMoney = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemMoney = new BBizDataDefItemMoney();
		}
		else
		{
			bBizDataDefItemMoney = (BBizDataDefItemMoney) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemMoney = (BizDataDefItemMoney) d_obj;
		}
		
		super.btod(bBizDataDefItemMoney, bizDataDefItemMoney);
		
		bizDataDefItemMoney.setPriceType( bBizDataDefItemMoney.getPriceType() );
		
		if( bBizDataDefItemMoney.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemMoney.getItemBizData(), true );
			bizDataDefItemMoney.setItemBizData(itemBizData);
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
		BizDataDefItemMoney bizDataDefItemMoney = new BizDataDefItemMoney();
		this.btod(b_obj, bizDataDefItemMoney);
		return bizDataDefItemMoney;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemMoney   bizDataDefItemMoney = null;
		BBizDataDefItemMoney bBizDataDefItemMoney = null;
				
		if( d_obj == null )
		{
			bizDataDefItemMoney = new BizDataDefItemMoney();
		}
		else
		{
			bizDataDefItemMoney = (BizDataDefItemMoney) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemMoney = (BBizDataDefItemMoney) b_obj;
		}
		
		super.dtob(bizDataDefItemMoney, bBizDataDefItemMoney);
		
		bBizDataDefItemMoney.setPriceType( bizDataDefItemMoney.getPriceType() );

		if( bizDataDefItemMoney.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemMoney.getItemBizData(), true );
			bBizDataDefItemMoney.setItemBizData(itemBizData);
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
		BBizDataDefItemMoney bBizDataDefItemMoney = new BBizDataDefItemMoney();
		this.dtob(d_obj, bBizDataDefItemMoney);
		return bBizDataDefItemMoney;
	}

}
