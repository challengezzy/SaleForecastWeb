/**********************************************************************
 *$RCSfile:BizDataDefItemForecastAssessmentBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-3-19 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bdconvertor;

import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemForecastAssessment;

/**
 * <li>Title: BizDataDefItemForecastAssessmentBDConvertor.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BizDataDefItemForecastAssessmentBDConvertor extends BizDataDefItemBDConvertor
{
	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#btod(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void btod(Object b_obj, Object d_obj)
	{
		BBizDataDefItemForecastAssessment bBizDataDefItemForecastAssessment = null;
		BizDataDefItemForecastAssessment   bizDataDefItemForecastAssessment = null;		
		
		if( b_obj == null )
		{
			bBizDataDefItemForecastAssessment = new BBizDataDefItemForecastAssessment();
		}
		else
		{
			bBizDataDefItemForecastAssessment = (BBizDataDefItemForecastAssessment) b_obj;
		}
		
		if( d_obj == null )
		{
			return;
		}
		else
		{
			bizDataDefItemForecastAssessment = (BizDataDefItemForecastAssessment) d_obj;
		}
		
		super.btod(bBizDataDefItemForecastAssessment, bizDataDefItemForecastAssessment);
		
		bizDataDefItemForecastAssessment.setStartPeriod(bBizDataDefItemForecastAssessment.getStartPeriod());
		bizDataDefItemForecastAssessment.setEndPeriod(bBizDataDefItemForecastAssessment.getEndPeriod());
		
		if( bBizDataDefItemForecastAssessment.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BizData itemBizData = bizDataBDConvertor.btod( bBizDataDefItemForecastAssessment.getItemBizData(), true );
			bizDataDefItemForecastAssessment.setItemBizData(itemBizData);
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
		BizDataDefItemForecastAssessment bizDataDefItemForecastAssessment = new BizDataDefItemForecastAssessment();
		this.btod(b_obj, bizDataDefItemForecastAssessment);
		return bizDataDefItemForecastAssessment;
	}

	/* (non-Javadoc)
	 * 基本属性,处理;
	 * 引用的对象属性itemBizData,处理; 
	 * @see dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor#dtob(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void dtob(Object d_obj, Object b_obj)
	{
		BizDataDefItemForecastAssessment   bizDataDefItemForecastAssessment = null;
		BBizDataDefItemForecastAssessment bBizDataDefItemForecastAssessment = null;
				
		if( d_obj == null )
		{
			bizDataDefItemForecastAssessment = new BizDataDefItemForecastAssessment();
		}
		else
		{
			bizDataDefItemForecastAssessment = (BizDataDefItemForecastAssessment) d_obj;
		}
		
		if( b_obj == null )
		{
			return;
		}
		else
		{
			bBizDataDefItemForecastAssessment = (BBizDataDefItemForecastAssessment) b_obj;
		}
		
		super.dtob(bizDataDefItemForecastAssessment, bBizDataDefItemForecastAssessment);
		
		bBizDataDefItemForecastAssessment.setStartPeriod( bizDataDefItemForecastAssessment.getStartPeriod() );
		bBizDataDefItemForecastAssessment.setEndPeriod( bizDataDefItemForecastAssessment.getEndPeriod() );
		
		if( bizDataDefItemForecastAssessment.getItemBizData() != null )
		{
			BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
			BBizData itemBizData = bizDataBDConvertor.dtob( bizDataDefItemForecastAssessment.getItemBizData(), true );
			bBizDataDefItemForecastAssessment.setItemBizData(itemBizData);
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
		BBizDataDefItemForecastAssessment bBizDataDefItemForecastAssessment = new BBizDataDefItemForecastAssessment();
		this.dtob(d_obj, bBizDataDefItemForecastAssessment);
		return bBizDataDefItemForecastAssessment;
	}
}

/**********************************************************************
 *$RCSfile:BizDataDefItemForecastAssessmentBDConvertor.java,v $  $Revision: 1.0 $  $Date:2012-3-19 $
 *
 *$Log:BizDataDefItemForecastAssessmentBDConvertor.java,v $
 *********************************************************************/