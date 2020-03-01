/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeHis;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeHis;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemTimeHisBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemTimeHisBDConvertor()
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
		BBizDataDefItemTimeHis bBizDataDefItemTimeHis = null;
		BizDataDefItemTimeHis   bizDataDefItemTimeHis = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemTimeHis = new BBizDataDefItemTimeHis();
		}
		else
		{
			bBizDataDefItemTimeHis = (BBizDataDefItemTimeHis) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemTimeHis = (BizDataDefItemTimeHis) d_obj;
		}
		
		super.btod(bBizDataDefItemTimeHis, bizDataDefItemTimeHis);
		
		bizDataDefItemTimeHis.setTimeFormula( bBizDataDefItemTimeHis.getTimeFormula() );
		
		if( bBizDataDefItemTimeHis.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemTimeHis.getItemBizData(), true );
			bizDataDefItemTimeHis.setItemBizData(itemBizData);
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
		BizDataDefItemTimeHis bizDataDefItemTimeHis = new BizDataDefItemTimeHis();
		this.btod(b_obj, bizDataDefItemTimeHis);
		return bizDataDefItemTimeHis;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemTimeHis   bizDataDefItemTimeHis = null;
		BBizDataDefItemTimeHis bBizDataDefItemTimeHis = null;
				
		if( d_obj == null )
		{
			bizDataDefItemTimeHis = new BizDataDefItemTimeHis();
		}
		else
		{
			bizDataDefItemTimeHis = (BizDataDefItemTimeHis) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemTimeHis = (BBizDataDefItemTimeHis) b_obj;
		}
		
		super.dtob(bizDataDefItemTimeHis, bBizDataDefItemTimeHis);
		
		bBizDataDefItemTimeHis.setTimeFormula( bizDataDefItemTimeHis.getTimeFormula() );

		if( bizDataDefItemTimeHis.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemTimeHis.getItemBizData(), true );
			bBizDataDefItemTimeHis.setItemBizData(itemBizData);
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
		BBizDataDefItemTimeHis bBizDataDefItemTimeHis = new BBizDataDefItemTimeHis();
		this.dtob(d_obj, bBizDataDefItemTimeHis);
		return bBizDataDefItemTimeHis;
	}

}
