/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAddFc;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAddFc;

public class BizDataDefItemAddFcBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemAddFcBDConvertor()
	{
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItemAddFc bBizDataDefItemAddFc = null;
		BizDataDefItemAddFc   bizDataDefItemAddFc = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemAddFc = new BBizDataDefItemAddFc();
		}
		else
		{
			bBizDataDefItemAddFc = (BBizDataDefItemAddFc) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemAddFc = (BizDataDefItemAddFc) d_obj;
		}
		
		super.btod(bBizDataDefItemAddFc, bizDataDefItemAddFc);
		
		if( bBizDataDefItemAddFc.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemAddFc.getItemBizData(), true );
			bizDataDefItemAddFc.setItemBizData(itemBizData);
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
		BizDataDefItemAddFc bizDataDefItemAddFc = new BizDataDefItemAddFc();
		this.btod(b_obj, bizDataDefItemAddFc);
		return bizDataDefItemAddFc;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemAddFc   bizDataDefItemAddFc = null;
		BBizDataDefItemAddFc bBizDataDefItemAddFc = null;
				
		if( d_obj == null )
		{
			bizDataDefItemAddFc = new BizDataDefItemAddFc();
		}
		else
		{
			bizDataDefItemAddFc = (BizDataDefItemAddFc) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemAddFc = (BBizDataDefItemAddFc) b_obj;
		}
		
		super.dtob(bizDataDefItemAddFc, bBizDataDefItemAddFc);
		
		if( bizDataDefItemAddFc.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemAddFc.getItemBizData(), true );
			bBizDataDefItemAddFc.setItemBizData(itemBizData);
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
		BBizDataDefItemAddFc bBizDataDefItemAddFc = new BBizDataDefItemAddFc();
		this.dtob(d_obj, bBizDataDefItemAddFc);
		return bBizDataDefItemAddFc;
	}

}
