package dmdd.dmddjava.service.forecastservice;

import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;

/**
 * <p>Title: 最终预测相关服务类</p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jul 8, 2017 10:01:09 PM
 */
public class FinalForecastService {

	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());
	
	/**
	 * 计算最终预测值
	 * @param finalFcBizData_combFc 最终预测数据指向的组合预测
	 * @param detailProOrg
	 * @param period
	 * @param daoForecastData
	 * @return
	 */
	public long computeFinalForecast(BizData finalFcBizData_combFc,AProOrg detailProOrg,int period,DaoForecastData daoForecastData){
		// 利用定义的公式计算最终预测值	begin
		long fcValueFinal = 0;
		
		if(CollectionUtils.isEmpty(finalFcBizData_combFc.getBizDataDefItems())){
			return fcValueFinal;
		}
		
		for(BizDataDefItem bizDataDefItem : finalFcBizData_combFc.getBizDataDefItems() )
		{
			if( ! (bizDataDefItem instanceof BizDataDefItemFcComb) ){
				logger.warn("最终预测[]的数据定义不是组合预测！！！！,最终预测定义和计算有异常");
				continue;
			}
			
			BizDataDefItemFcComb bizDataDefItemFcComb = (BizDataDefItemFcComb) bizDataDefItem;

			//非同期预测类，直接取值
			if( bizDataDefItemFcComb.getItemBizData().getType() != BizConst.BIZDATA_TYPE_TIMEFC )
			{
				//获取单项预测值
				ForecastData forecastData4ItemBizData = daoForecastData.getForecastData( detailProOrg.getProduct().getId(), detailProOrg.getOrganization().getId()
											, period, bizDataDefItemFcComb.getItemBizData().getId() );
				if( forecastData4ItemBizData != null ){
					fcValueFinal = Math.round( fcValueFinal + forecastData4ItemBizData.getValue() * bizDataDefItemFcComb.getCoefficient() );
				}
			}
			else if( bizDataDefItemFcComb.getItemBizData().getType() == BizConst.BIZDATA_TYPE_TIMEFC )
			{
				if( bizDataDefItemFcComb.getItemBizData().getBizDataDefItems() != null && !(bizDataDefItemFcComb.getItemBizData().getBizDataDefItems().isEmpty()) )
				{
					Iterator<BizDataDefItem> itr_bizDataDefItemTimeFc = bizDataDefItemFcComb.getItemBizData().getBizDataDefItems().iterator();
					if( itr_bizDataDefItemTimeFc.hasNext() )
					{
						BizDataDefItemTimeFc bizDataDefItemTimeFc = (BizDataDefItemTimeFc) itr_bizDataDefItemTimeFc.next();
						
						int periodDiff2Current = 0;
						
						int timeFormulaDictValue = bizDataDefItemTimeFc.getTimeFormula();
						
						if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST1YEAR )
						{
							periodDiff2Current = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
						}
						else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST2YEAR )
						{
							periodDiff2Current = 2*ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();;
						}
						else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST3YEAR )
						{
							periodDiff2Current = 3*ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();;
						}	
						else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_LAST1PERIOD )
						{
							periodDiff2Current = 1;
						}
						
						int period_fcTimeDependon = UtilPeriod.getPeriod( period, -periodDiff2Current );
						
						//从数据库中读取
						ForecastData forecastData_fcTimeDependon = daoForecastData.getForecastData( detailProOrg.getProduct().getId(), detailProOrg.getOrganization().getId(), period_fcTimeDependon, bizDataDefItemTimeFc.getItemBizData().getId() );
						if( forecastData_fcTimeDependon != null )
						{
							fcValueFinal = Math.round( fcValueFinal + forecastData_fcTimeDependon.getValue() * bizDataDefItemFcComb.getCoefficient() );
						}													
						
					}
				}											
			}// end timefc
			
		}
		
		return fcValueFinal;
	}
	
	/** 判断最终预测中是否包含统计预测 */
	public boolean isFinalDataContainsModelData(BizData finalFcBizData_combFc,BizData mappingModelFcBizData){
		for( BizDataDefItem bizDataDefItem : finalFcBizData_combFc.getBizDataDefItems() )
		{
			BizDataDefItemFcComb bizDataDefItemFcComb = (BizDataDefItemFcComb) bizDataDefItem;
			BizData itemBizData = bizDataDefItemFcComb.getItemBizData();
			
			if(mappingModelFcBizData.getId() == itemBizData.getId() ){
				//包含了统计预测
				return true;
			}
			
		}
		
		return false;
	}
	
}
