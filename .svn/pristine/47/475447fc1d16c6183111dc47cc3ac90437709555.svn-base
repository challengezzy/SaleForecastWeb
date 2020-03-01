package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemPeriodVersion;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemPeriodVersion;

public class BizDataDefItemPeriodVersionBDConvertor extends BizDataDefItemBDConvertor
{

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItemPeriodVersion bBizDataDefItemPeriodVersion = null;
		BizDataDefItemPeriodVersion   bizDataDefItemPeriodVersion = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemPeriodVersion = new BBizDataDefItemPeriodVersion();
		}
		else
		{
			bBizDataDefItemPeriodVersion = (BBizDataDefItemPeriodVersion) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemPeriodVersion = (BizDataDefItemPeriodVersion) d_obj;
		}
		
		super.btod(bBizDataDefItemPeriodVersion, bizDataDefItemPeriodVersion);
		bizDataDefItemPeriodVersion.setPeriodInterval(bBizDataDefItemPeriodVersion.getPeriodInterval());
		
		if( bBizDataDefItemPeriodVersion.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemPeriodVersion.getItemBizData(), true );
			bizDataDefItemPeriodVersion.setItemBizData(itemBizData);
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
		BizDataDefItemPeriodVersion bizDataDefItemPeriodVersion = new BizDataDefItemPeriodVersion();
		this.btod(b_obj, bizDataDefItemPeriodVersion);
		return bizDataDefItemPeriodVersion;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemPeriodVersion   bizDataDefItemPeriodVersion = null;
		BBizDataDefItemPeriodVersion bBizDataDefItemPeriodVersion = null;
				
		if( d_obj == null )
		{
			bizDataDefItemPeriodVersion = new BizDataDefItemPeriodVersion();
		}
		else
		{
			bizDataDefItemPeriodVersion = (BizDataDefItemPeriodVersion) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemPeriodVersion = (BBizDataDefItemPeriodVersion) b_obj;
		}
		
		super.dtob(bizDataDefItemPeriodVersion, bBizDataDefItemPeriodVersion);
		bBizDataDefItemPeriodVersion.setPeriodInterval(bizDataDefItemPeriodVersion.getPeriodInterval());
		
		if( bizDataDefItemPeriodVersion.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemPeriodVersion.getItemBizData(), true );
			bBizDataDefItemPeriodVersion.setItemBizData(itemBizData);
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
		BBizDataDefItemPeriodVersion bBizDataDefItemPeriodVersion = new BBizDataDefItemPeriodVersion();
		this.dtob(d_obj, bBizDataDefItemPeriodVersion);
		return bBizDataDefItemPeriodVersion;
	}

}
