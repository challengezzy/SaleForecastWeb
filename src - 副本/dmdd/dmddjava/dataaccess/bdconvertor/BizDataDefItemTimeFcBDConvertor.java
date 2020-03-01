/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeFc;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemTimeFcBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemTimeFcBDConvertor()
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
		BBizDataDefItemTimeFc bBizDataDefItemTimeFc = null;
		BizDataDefItemTimeFc   bizDataDefItemTimeFc = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemTimeFc = new BBizDataDefItemTimeFc();
		}
		else
		{
			bBizDataDefItemTimeFc = (BBizDataDefItemTimeFc) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemTimeFc = (BizDataDefItemTimeFc) d_obj;
		}
		
		super.btod(bBizDataDefItemTimeFc, bizDataDefItemTimeFc);
		
		bizDataDefItemTimeFc.setTimeFormula( bBizDataDefItemTimeFc.getTimeFormula() );
		
		if( bBizDataDefItemTimeFc.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemTimeFc.getItemBizData(), true );
			bizDataDefItemTimeFc.setItemBizData(itemBizData);
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
		BizDataDefItemTimeFc bizDataDefItemTimeFc = new BizDataDefItemTimeFc();
		this.btod(b_obj, bizDataDefItemTimeFc);
		return bizDataDefItemTimeFc;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemTimeFc   bizDataDefItemTimeFc = null;
		BBizDataDefItemTimeFc bBizDataDefItemTimeFc = null;
				
		if( d_obj == null )
		{
			bizDataDefItemTimeFc = new BizDataDefItemTimeFc();
		}
		else
		{
			bizDataDefItemTimeFc = (BizDataDefItemTimeFc) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemTimeFc = (BBizDataDefItemTimeFc) b_obj;
		}
		
		super.dtob(bizDataDefItemTimeFc, bBizDataDefItemTimeFc);
		
		bBizDataDefItemTimeFc.setTimeFormula( bizDataDefItemTimeFc.getTimeFormula() );

		if( bizDataDefItemTimeFc.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemTimeFc.getItemBizData(), true );
			bBizDataDefItemTimeFc.setItemBizData(itemBizData);
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
		BBizDataDefItemTimeFc bBizDataDefItemTimeFc = new BBizDataDefItemTimeFc();
		this.dtob(d_obj, bBizDataDefItemTimeFc);
		return bBizDataDefItemTimeFc;
	}

}
