/**
 * 
 */
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemHistoryAd;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAd;

/**
 * @author liuzhen
 *
 */
public class BizDataDefItemHistoryAdBDConvertor extends BizDataDefItemBDConvertor
{

	/**
	 * 
	 */
	public BizDataDefItemHistoryAdBDConvertor()
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
		BBizDataDefItemHistoryAd bBizDataDefItemHistoryAd = null;
		BizDataDefItemHistoryAd   bizDataDefItemHistoryAd = null;	
		
		if( b_obj == null )
		{
			bBizDataDefItemHistoryAd = new BBizDataDefItemHistoryAd();
		}
		else
		{
			bBizDataDefItemHistoryAd = (BBizDataDefItemHistoryAd) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemHistoryAd = (BizDataDefItemHistoryAd) d_obj;
		}		

		super.btod(bBizDataDefItemHistoryAd, bizDataDefItemHistoryAd);
		
		if( bBizDataDefItemHistoryAd.getHistoryBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData historyBizData = bizDataBDConvertor.btod( bBizDataDefItemHistoryAd.getHistoryBizData(), true );
			bizDataDefItemHistoryAd.setHistoryBizData(historyBizData);
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
		BizDataDefItemHistoryAd bizDataDefItemHistoryAd = new BizDataDefItemHistoryAd();
		this.btod(b_obj, bizDataDefItemHistoryAd);
		return bizDataDefItemHistoryAd;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性historyBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemHistoryAd   bizDataDefItemHistoryAd = null;
		BBizDataDefItemHistoryAd bBizDataDefItemHistoryAd = null;
				
		if( d_obj == null )
		{
			bizDataDefItemHistoryAd = new BizDataDefItemHistoryAd();
		}
		else
		{
			bizDataDefItemHistoryAd = (BizDataDefItemHistoryAd) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemHistoryAd = (BBizDataDefItemHistoryAd) b_obj;
		}
		
		super.dtob(bizDataDefItemHistoryAd, bBizDataDefItemHistoryAd);
		
		if( bizDataDefItemHistoryAd.getHistoryBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData historyBizData = bizDataBDConvertor.dtob( bizDataDefItemHistoryAd.getHistoryBizData(), true );
			bBizDataDefItemHistoryAd.setHistoryBizData(historyBizData);
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
		BBizDataDefItemHistoryAd bBizDataDefItemHistoryAd = new BBizDataDefItemHistoryAd();
		this.dtob(d_obj, bBizDataDefItemHistoryAd);
		return bBizDataDefItemHistoryAd;
	}

}
