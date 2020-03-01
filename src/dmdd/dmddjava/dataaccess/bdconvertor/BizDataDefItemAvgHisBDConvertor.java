/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAvgHis;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAvgHis;

public class BizDataDefItemAvgHisBDConvertor extends BizDataDefItemBDConvertor
{

	public BizDataDefItemAvgHisBDConvertor()
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
		BBizDataDefItemAvgHis bBizDataDefItemAvgHis = null;
		BizDataDefItemAvgHis   bizDataDefItemAvgHis = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemAvgHis = new BBizDataDefItemAvgHis();
		}
		else
		{
			bBizDataDefItemAvgHis = (BBizDataDefItemAvgHis) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemAvgHis = (BizDataDefItemAvgHis) d_obj;
		}
		
		super.btod(bBizDataDefItemAvgHis, bizDataDefItemAvgHis);
		
		bizDataDefItemAvgHis.setPeriodNum( bBizDataDefItemAvgHis.getPeriodNum() );
		
		if( bBizDataDefItemAvgHis.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemAvgHis.getItemBizData(), true );
			bizDataDefItemAvgHis.setItemBizData(itemBizData);
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
		BizDataDefItemAvgHis bizDataDefItemAvgHis = new BizDataDefItemAvgHis();
		this.btod(b_obj, bizDataDefItemAvgHis);
		return bizDataDefItemAvgHis;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemAvgHis   bizDataDefItemAvgHis = null;
		BBizDataDefItemAvgHis bBizDataDefItemAvgHis = null;
				
		if( d_obj == null )
		{
			bizDataDefItemAvgHis = new BizDataDefItemAvgHis();
		}
		else
		{
			bizDataDefItemAvgHis = (BizDataDefItemAvgHis) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemAvgHis = (BBizDataDefItemAvgHis) b_obj;
		}
		
		super.dtob(bizDataDefItemAvgHis, bBizDataDefItemAvgHis);
		
		bBizDataDefItemAvgHis.setPeriodNum( bizDataDefItemAvgHis.getPeriodNum() );

		if( bizDataDefItemAvgHis.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemAvgHis.getItemBizData(), true );
			bBizDataDefItemAvgHis.setItemBizData(itemBizData);
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
		BBizDataDefItemAvgHis bBizDataDefItemAvgHis = new BBizDataDefItemAvgHis();
		this.dtob(d_obj, bBizDataDefItemAvgHis);
		return bBizDataDefItemAvgHis;
	}

}
