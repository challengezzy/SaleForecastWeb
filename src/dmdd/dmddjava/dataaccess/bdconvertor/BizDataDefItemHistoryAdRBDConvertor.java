/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAdR;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemHistoryAdRBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemHistoryAdRBDConvertor()
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
	 * 引用的对象属性historyBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItemHistoryAdR bBizDataDefItemHistoryAdR = null;
		BizDataDefItemHistoryAdR   bizDataDefItemHistoryAdR = null;	
		
		if( b_obj == null )
		{
			bBizDataDefItemHistoryAdR = new BBizDataDefItemHistoryAdR();
		}
		else
		{
			bBizDataDefItemHistoryAdR = (BBizDataDefItemHistoryAdR) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemHistoryAdR = (BizDataDefItemHistoryAdR) d_obj;
		}		

		super.btod(bBizDataDefItemHistoryAdR, bizDataDefItemHistoryAdR);
		
		if( bBizDataDefItemHistoryAdR.getHistoryAdBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData historyBizData = bizDataBDConvertor.btod( bBizDataDefItemHistoryAdR.getHistoryAdBizData(), true );
			bizDataDefItemHistoryAdR.setHistoryAdBizData(historyBizData);
		}		
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyBizData,处理;  
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object)
	 */
	@Override
	public Object btod(Object b_obj)
	{
		BizDataDefItemHistoryAdR bizDataDefItemHistoryAdR = new BizDataDefItemHistoryAdR();
		this.btod(b_obj, bizDataDefItemHistoryAdR);
		return bizDataDefItemHistoryAdR;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemHistoryAdR   bizDataDefItemHistoryAdR = null;
		BBizDataDefItemHistoryAdR bBizDataDefItemHistoryAdR = null;
				
		if( d_obj == null )
		{
			bizDataDefItemHistoryAdR = new BizDataDefItemHistoryAdR();
		}
		else
		{
			bizDataDefItemHistoryAdR = (BizDataDefItemHistoryAdR) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemHistoryAdR = (BBizDataDefItemHistoryAdR) b_obj;
		}
		
		super.dtob(bizDataDefItemHistoryAdR, bBizDataDefItemHistoryAdR);
		
		if( bizDataDefItemHistoryAdR.getHistoryAdBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData historyBizData = bizDataBDConvertor.dtob( bizDataDefItemHistoryAdR.getHistoryAdBizData(), true );
			bBizDataDefItemHistoryAdR.setHistoryAdBizData(historyBizData);
		}			

	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object)
	 */
	@Override
	public Object dtob(Object d_obj)
	{
		BBizDataDefItemHistoryAdR bBizDataDefItemHistoryAdR = new BBizDataDefItemHistoryAdR();
		this.dtob(d_obj, bBizDataDefItemHistoryAdR);
		return bBizDataDefItemHistoryAdR;
	}

}
