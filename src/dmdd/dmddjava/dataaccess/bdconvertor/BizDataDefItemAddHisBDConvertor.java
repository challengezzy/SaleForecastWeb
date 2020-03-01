/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAddHis;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAddHis;

public class BizDataDefItemAddHisBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemAddHisBDConvertor()
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
		BBizDataDefItemAddHis bBizDataDefItemAddHis = null;
		BizDataDefItemAddHis   bizDataDefItemAddHis = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemAddHis = new BBizDataDefItemAddHis();
		}
		else
		{
			bBizDataDefItemAddHis = (BBizDataDefItemAddHis) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemAddHis = (BizDataDefItemAddHis) d_obj;
		}
		
		super.btod(bBizDataDefItemAddHis, bizDataDefItemAddHis);
		
		if( bBizDataDefItemAddHis.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemAddHis.getItemBizData(), true );
			bizDataDefItemAddHis.setItemBizData(itemBizData);
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
		BizDataDefItemAddHis bizDataDefItemAddHis = new BizDataDefItemAddHis();
		this.btod(b_obj, bizDataDefItemAddHis);
		return bizDataDefItemAddHis;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemAddHis   bizDataDefItemAddHis = null;
		BBizDataDefItemAddHis bBizDataDefItemAddHis = null;
				
		if( d_obj == null )
		{
			bizDataDefItemAddHis = new BizDataDefItemAddHis();
		}
		else
		{
			bizDataDefItemAddHis = (BizDataDefItemAddHis) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemAddHis = (BBizDataDefItemAddHis) b_obj;
		}
		
		super.dtob(bizDataDefItemAddHis, bBizDataDefItemAddHis);
		
		if( bizDataDefItemAddHis.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemAddHis.getItemBizData(), true );
			bBizDataDefItemAddHis.setItemBizData(itemBizData);
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
		BBizDataDefItemAddHis bBizDataDefItemAddHis = new BBizDataDefItemAddHis();
		this.dtob(d_obj, bBizDataDefItemAddHis);
		return bBizDataDefItemAddHis;
	}

}
