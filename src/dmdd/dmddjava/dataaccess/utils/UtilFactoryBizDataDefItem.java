/**
 * 
 */
package dmdd.dmddjava.dataaccess.utils;

import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemAddFcBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemAddHisBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemAvgHisBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemFcCombBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemFcHandBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemForecastAssessmentBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemHistoryAdBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemHistoryAdRBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemKpiBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemMoneyBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemMoneyCombBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemPeriodVersionBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemTimeFcBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataDefItemTimeHisBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAddFc;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAddHis;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAvgHis;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcHand;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemHistoryAd;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemKpi;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoney;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoneyComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemPeriodVersion;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeHis;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAddFc;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAddHis;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAvgHis;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcHand;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAd;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemKpi;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemMoney;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemMoneyComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemPeriodVersion;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeHis;


/**
 * @author liuzhen
 *
 */
public class UtilFactoryBizDataDefItem
{

	public static BizDataDefItemBDConvertor getBizDataDefItemBDConvertorInstance(Object _bizDataDefItem)
	{
		if( _bizDataDefItem == null )
		{
			return new BizDataDefItemBDConvertor();
		}
		
		if( _bizDataDefItem instanceof BizDataDefItemFcComb || _bizDataDefItem instanceof BBizDataDefItemFcComb )
		{
			return new BizDataDefItemFcCombBDConvertor();
		}
		
		if( _bizDataDefItem instanceof BizDataDefItemFcHand || _bizDataDefItem instanceof BBizDataDefItemFcHand )
		{
			return new BizDataDefItemFcHandBDConvertor();
		}		

		if( _bizDataDefItem instanceof BizDataDefItemHistoryAd || _bizDataDefItem instanceof BBizDataDefItemHistoryAd )
		{
			return new BizDataDefItemHistoryAdBDConvertor();
		}
		
		if( _bizDataDefItem instanceof BizDataDefItemHistoryAdR || _bizDataDefItem instanceof BBizDataDefItemHistoryAdR )
		{
			return new BizDataDefItemHistoryAdRBDConvertor();
		}		

		if( _bizDataDefItem instanceof BizDataDefItemTimeHis || _bizDataDefItem instanceof BBizDataDefItemTimeHis )
		{
			return new BizDataDefItemTimeHisBDConvertor();
		}				
		
		if( _bizDataDefItem instanceof BizDataDefItemTimeFc || _bizDataDefItem instanceof BBizDataDefItemTimeFc )
		{
			return new BizDataDefItemTimeFcBDConvertor();
		}		
		
		if( _bizDataDefItem instanceof BizDataDefItemKpi || _bizDataDefItem instanceof BBizDataDefItemKpi )
		{
			return new BizDataDefItemKpiBDConvertor();
		}
		
		if( _bizDataDefItem instanceof BizDataDefItemMoney || _bizDataDefItem instanceof BBizDataDefItemMoney )
		{
			return new BizDataDefItemMoneyBDConvertor();
		}		

		if( _bizDataDefItem instanceof BizDataDefItemMoneyComb || _bizDataDefItem instanceof BBizDataDefItemMoneyComb )
		{
			return new BizDataDefItemMoneyCombBDConvertor();
		}
		
		if( _bizDataDefItem instanceof BizDataDefItemForecastAssessment || _bizDataDefItem instanceof BBizDataDefItemForecastAssessment )
		{
			return new BizDataDefItemForecastAssessmentBDConvertor();
		}
		
		//历史平均
		if( _bizDataDefItem instanceof BizDataDefItemAvgHis || _bizDataDefItem instanceof BBizDataDefItemAvgHis )
		{
			return new BizDataDefItemAvgHisBDConvertor();
		}
		
		//历史累积
		if( _bizDataDefItem instanceof BizDataDefItemAddHis || _bizDataDefItem instanceof BBizDataDefItemAddHis )
		{
			return new BizDataDefItemAddHisBDConvertor();
		}
		
		//预测累积
		if( _bizDataDefItem instanceof BizDataDefItemAddFc || _bizDataDefItem instanceof BBizDataDefItemAddFc )
		{
			return new BizDataDefItemAddFcBDConvertor();
		}
		
		//M-N版本预测
		if( _bizDataDefItem instanceof BizDataDefItemPeriodVersion || _bizDataDefItem instanceof BBizDataDefItemPeriodVersion )
		{
			return new BizDataDefItemPeriodVersionBDConvertor();
		}
		
		return new BizDataDefItemBDConvertor();

	}

}
