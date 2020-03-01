package dmdd.dmddjava.service.model;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastModelMBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastRunTaskItemBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelM;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMAAnalog;
import dmdd.dmddjava.dataaccess.bizobject.BForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.bizobject.BForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer;
import dmdd.dmddjava.dataaccess.bizobject.BProductLayer;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcHand;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelM;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalogItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLEs;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLEso;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLMa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWma;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLEs;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLEso;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLMa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWma;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWmaItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTCply;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsao;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsm;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTEsmo;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTLa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSTPly;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTCply;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTEs;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTEso;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTLa;
import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMTPly;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastErrorMappingModel;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastRunTaskItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.dataaccess.utils.UtilFactoryForecastModelM;
import dmdd.dmddjava.service.forecastservice.AmountForecastService;
import dmdd.dmddjava.service.forecastservice.FinalForecastService;

/**
 * 
 * <p>Title: 预测策略和模型服务相关类，从原ForecastService拆分出</p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jun 25, 2017 12:10:39 AM
 */
public class ForecastModelService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());	
	
    //private ForecastDataDM forecastDataDM = new ForecastDataDM();
    
    private AmountForecastService amountForecastService = new AmountForecastService();
    
    private FinalForecastService finalForecastService = new FinalForecastService();
    
    int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
	
	/**
	 * 以_bForecastRunTaskItem为参数，运行其指定的预测类别，并更新其信息
	 * @param _bForecastRunTaskItem
	 * @throws Exception
	 */
	public void forecastInstRunMappingModel4Forecast( BForecastRunTaskItem _bForecastRunTaskItem ) throws Exception
	{
		BSysPeriod bSysPeriod = ServerEnvironment.getInstance().getSysPeriod();
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{			
			trsa = session.beginTransaction();

			DaoSystem daoSystem = new DaoSystem( session );
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );

			// 从数据库读取预测类别
			DaoForecastInst daoForecastInst = new DaoForecastInst( session );
			ForecastInst forecastInst2Run = daoForecastInst.getForecastInstById( _bForecastRunTaskItem.getForecastInst().getId() );

			if( (forecastInst2Run == null) || CollectionUtils.isEmpty(forecastInst2Run.getForecastInstProOrgs()) ){
				return;
			}

			// 预测范围下的全部明细组织产品
			HashMap<String, AProOrg> hmap4DetailProOrgs = UtilProOrg.getDetailProOrgs4ForecastInst( forecastInst2Run.getForecastInstProOrgs(), true );
			if( hmap4DetailProOrgs == null || hmap4DetailProOrgs.isEmpty() ){
				return;
			}
			
			//	模型公式所需的历史数据的期间范围
			List<Integer> list4periodScope4History = new ArrayList<Integer>();
			if( forecastInst2Run.getMappingFcModel() != null ){
				// 映射模型不为空的才需要读取历史数据
				list4periodScope4History = this.getPeriodScope4HistoryData4ForecastModelM( bSysPeriod, forecastInst2Run.getMappingFcModel() );
			}
			
			//	类比模型，使用的历史数据是模型的参照项的权重和，而是不是预测范围的历史数据值。整个预测类别，不管在什么层次上运行，使用的都是一组历史数据，所以这里准备好	begin
			HashMap<Integer, Double> hmap4HistoryData_forecastModelMAAnaog = new HashMap<Integer, Double>();

			if( forecastInst2Run.getMappingFcModel() != null && forecastInst2Run.getMappingFcModel().getIndicator().equals( BizConst.FORECASTMODELM_INDICATOR_AANALOG ) )
			{
				for( int i=0; i<list4periodScope4History.size(); i++ )
				{
					hmap4HistoryData_forecastModelMAAnaog.put( list4periodScope4History.get( i ), new Double(0.0) );
				}
				
				ForecastModelMAAnalog forecastModelMAAnalog = (ForecastModelMAAnalog) forecastInst2Run.getMappingFcModel();
				
				//	历史数据
				Iterator<ForecastModelMAAnalogItem> itr_ForecastModelMAAnalogItem = forecastModelMAAnalog.getForecastModelMAAnalogItems().iterator();
				while( itr_ForecastModelMAAnalogItem.hasNext() )
				{
					ForecastModelMAAnalogItem forecastModelMAAnalogItem = itr_ForecastModelMAAnalogItem.next();
					HashMap<String, AProOrg> hmap_DetailProOrgs = UtilProOrg.getDetailProOrgs( forecastModelMAAnalogItem, true );
					String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( hmap_DetailProOrgs );
					HashMap<Integer, Long> hmap4HistoryData = daoHistoryData.getHistoryDataSumGroupByPeriod( idsScopeStr4DetailProOrgs, list4periodScope4History, forecastModelMAAnalog.getHistoryBizData().getId() );
					if( hmap4HistoryData != null )
					{
						for( int i=0; i<list4periodScope4History.size(); i++ )
						{
							Long value = hmap4HistoryData.get( list4periodScope4History.get( i ) );
							if( value != null )
							{
								Double newValue  = hmap4HistoryData_forecastModelMAAnaog.get( list4periodScope4History.get( i ) ) + value * forecastModelMAAnalogItem.getCoefficient();
								hmap4HistoryData_forecastModelMAAnaog.put( list4periodScope4History.get( i ), newValue );
							}
						}						
					}
				}
			}
			//	类比模型，使用的历史数据是模型的参照项的权重和，而是不是预测范围的历史数据值。整个预测类别，不管在什么层次上运行，使用的都是一组历史数据，所以这里准备好	end
			

			// 把这些明细组织产品按运行层次分组
			// 这里可以这样做，是因为预测类别的预测范围内的明细组织产品上都会有预测数据
			HashMap<String, List<AProOrg>> hmap4List4GroupedDetailProOrgs = UtilProOrg.getGroupedDetailProOrgs( hmap4DetailProOrgs, 
					forecastInst2Run.getRunOrganizationLayer().getValue(), forecastInst2Run.getRunProductLayer().getValue() );
			
			// 把历史数据一起准备好，因为可能要进行自适应，多次查询影响效率    begin
			HashMap<String,HashMap<Integer, Long>> hmap4Hmap4GroupedHistoryData = new HashMap<String,HashMap<Integer, Long>>();
			
			for( List<AProOrg> list4GroupedDetailProOrgs : hmap4List4GroupedDetailProOrgs.values() ){
				// 一组明细组织产品范围
				if( list4GroupedDetailProOrgs.isEmpty() ){
					continue;
				}
				
				// 该组明细组织产品范围的ID字符串
				String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );

				// 该组明细组织产品范围的历史数据准备 begin
				// hmap4HistoryData 因映射模型不同而不同
				
				// 映射模型不为空的才需要读取历史数据
				if( forecastInst2Run.getMappingFcModel() != null )
				{
					HashMap<Integer, Long> hmap4HistoryData = new HashMap<Integer, Long>();
					if( forecastInst2Run.getMappingFcModel().getIndicator().equals( BizConst.FORECASTMODELM_INDICATOR_AANALOG ) )
					{
						//	类比模型，使用的历史数据是模型的参照项的历史数据的权重和，而不是预测范围的历史数据值
						for( int i=0; i<list4periodScope4History.size(); i++ )
						{
							Double value = hmap4HistoryData_forecastModelMAAnaog.get( list4periodScope4History.get( i ) );
							if( value != null )
							{
								hmap4HistoryData.put( list4periodScope4History.get( i ), new Long(value.longValue()) );
							}
						}
					}else{
						hmap4HistoryData = daoHistoryData.getHistoryDataSumGroupByPeriod( idsScopeStr4GroupedDetailProOrgs, list4periodScope4History, forecastInst2Run.getMappingFcModel().getHistoryBizData().getId() );					
					}
					
					hmap4Hmap4GroupedHistoryData.put( idsScopeStr4GroupedDetailProOrgs, hmap4HistoryData );					
				}

			}
			// 把历史数据一起准备好，因为可能要进行自适应，多次查询影响效率    end
			

			/**按预测范围根据预测层次分的组分别进行容差分析：得到容差分析期间的预测数据和并调整后的历史数据,及预测期间预测要使用的历史数据*/
			boolean blRunForecast = true;
			// begin
			// hashMap4HashMap4HistoryData4Forecast
			// 分组存放调整后的历史数据以便进行后继的预测，以分组的明细组织产品范围的ID字符串为key
			
			HashMap<String, HashMap<Integer, Long>> hashMap4HashMap4HistoryData4Forecast = new HashMap<String, HashMap<Integer, Long>>();
			ForecastErrorMappingModel toAddForecastErrorMappingModel = null;
			if( forecastInst2Run.getMappingFcModel() != null )
			{
				// 有映射模型的如果是自适应类的，先自适应确定模型参数    begin
				if( forecastInst2Run.getMappingFcModel() instanceof ForecastModelMLEso ||
					forecastInst2Run.getMappingFcModel() instanceof ForecastModelMTEso ||
					forecastInst2Run.getMappingFcModel() instanceof ForecastModelMSLEso ||
					forecastInst2Run.getMappingFcModel() instanceof ForecastModelMSTEsao ||
					forecastInst2Run.getMappingFcModel() instanceof ForecastModelMSTEsmo )
				{
					this.optimizeForecastModelM( bSysPeriod, forecastInst2Run.getMappingFcModel(), hmap4List4GroupedDetailProOrgs, hmap4Hmap4GroupedHistoryData );
				}
				// 有映射模型的如果是自适应类的，先自适应确定模型参数    end
				
				
				// 有映射模型的才进行容差分析
				// list4HashMap4ForecastData4OutlierAnalyze
				// 存放容差期间的预测数据以便进行容差期间的误差计算
				// list4HashMap4HistoryData4OutlierAnalyze
				// 存放容差期间的调整后的历史数据以便进行容差期间的误差计算
				// periodNum4OutlierAnalyze 容差分析期间期间数
				// periodBegin4OutlierAnalyze 容差分析开始期间

				List<HashMap<Integer, Long>> list4HashMap4ForecastData4OutlierAnalyze = new ArrayList<HashMap<Integer, Long>>();
				List<HashMap<Integer, Long>> list4HashMap4HistoryData4OutlierAnalyze = new ArrayList<HashMap<Integer, Long>>();
				int periodNum4OutlierAnalyze = forecastInst2Run.getMappingFcModel().getOutlierAnalyzePeriodNum();
				int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

				for( List<AProOrg> list4GroupedDetailProOrgs : hmap4List4GroupedDetailProOrgs.values() )
				{
					// 一组明细组织产品范围
					if( list4GroupedDetailProOrgs.isEmpty() ){
						continue;
					}

					// 该组明细组织产品范围的ID字符串
					String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );
					
					// 该组明细组织产品范围的历史数据
					HashMap<Integer, Long> hmap4HistoryData = hmap4Hmap4GroupedHistoryData.get( idsScopeStr4GroupedDetailProOrgs );
					if( hmap4HistoryData == null ){
						hmap4HistoryData = new HashMap<Integer, Long>();
					}

					//XXX  真正的预测数据 begin
					HashMap<Integer, Long> hmap4ForecastData = getForecastDataByRunFormula4ForecastModelM( forecastInst2Run.getMappingFcModel(), 
									periodBegin4OutlierAnalyze, periodNum4OutlierAnalyze,hmap4HistoryData );
					
					if( hmap4ForecastData == null ){
						hmap4ForecastData = new HashMap<Integer, Long>();
					}
					// 预测数据 end

					// 计算MAD begin
					double mad = 0;
					for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
					{
						int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
						Long historyDataValue = hmap4HistoryData.get( period );
						if( historyDataValue == null )
						{
							historyDataValue = 0L;
						}
						Long forecastDataValue = hmap4ForecastData.get( period );
						if( forecastDataValue == null )
						{
							forecastDataValue = 0L;
						}
						mad = mad + Math.abs( historyDataValue - forecastDataValue );
					}
					mad = mad / periodNum4OutlierAnalyze;
					// 计算MAD end

					// 调整历史数据 begin
					if( forecastInst2Run.getMappingFcModel().getOutlierDataIsAutoAdjust() == BizConst.GLOBAL_YESNO_YES )
					{
						for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
						{
							int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
							Long historyDataValue = hmap4HistoryData.get( period );
							if( historyDataValue == null )
							{
								historyDataValue = 0L;
							}
							Long forecastDataValue = hmap4ForecastData.get( period );
							if( forecastDataValue == null )
							{
								forecastDataValue = 0L;
							}

							if( Math.abs( historyDataValue - forecastDataValue ) > forecastInst2Run.getMappingFcModel().getOutlierFactor() * 1.25 * mad )
							{
								historyDataValue = Math.round( historyDataValue * forecastInst2Run.getMappingFcModel().getOutlierDataAdjustHistoryWgt() + forecastDataValue
										* forecastInst2Run.getMappingFcModel().getOutlierDataAdjustComputeWgt() );
								hmap4HistoryData.put( period, historyDataValue );
							}
						}
					}
					// 调整历史数据 end

					// 存放调整后的历史数据以便计算容差期间的误差分析
					list4HashMap4HistoryData4OutlierAnalyze.add( hmap4HistoryData );
					// 存放预测数据以便计算容差期间的误差分析
					list4HashMap4ForecastData4OutlierAnalyze.add( hmap4ForecastData );
					// 以分组的明细组织产品范围的ID字符串为key存放调整后的历史数据，以便后面进行真正的预测
					hashMap4HashMap4HistoryData4Forecast.put( idsScopeStr4GroupedDetailProOrgs, hmap4HistoryData );
				}
				//按预测范围根据预测层次分的组分别进行容差分析：得到容差分析期间的预测数据和并调整后的历史数据,及预测期间预测要使用的历史数据
				// end

				// 针对整个预测范围产生误差分析 begin
				// 汇总历史数据和预测数据 begin
				Long[] arr4HistoryDataPeriodTotalValue4OutlierAnalyze = new Long[periodNum4OutlierAnalyze];
				Long[] arr4ForecastDataPeriodTotalValue4OutlierAnalyze = new Long[periodNum4OutlierAnalyze];
				for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
				{
					int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
					Long historyDataValue = 0L;
					if( list4HashMap4HistoryData4OutlierAnalyze != null && !(list4HashMap4HistoryData4OutlierAnalyze.isEmpty()) )
					{
						Iterator<HashMap<Integer, Long>> itr_list4HashMap4HistoryData4OutlierAnalyze = list4HashMap4HistoryData4OutlierAnalyze.iterator();
						while( itr_list4HashMap4HistoryData4OutlierAnalyze.hasNext() )
						{
							HashMap<Integer, Long> hashMap4HistoryData4OutlierAnalyze = itr_list4HashMap4HistoryData4OutlierAnalyze.next();
							Long tempHistoryDatavalue = hashMap4HistoryData4OutlierAnalyze.get( period );
							if( tempHistoryDatavalue != null )
							{
								historyDataValue = historyDataValue + tempHistoryDatavalue;
							}
						}
					}
					arr4HistoryDataPeriodTotalValue4OutlierAnalyze[i] = historyDataValue;

					Long forecastDataValue = 0L;
					if( list4HashMap4ForecastData4OutlierAnalyze != null && !(list4HashMap4ForecastData4OutlierAnalyze.isEmpty()) )
					{
						Iterator<HashMap<Integer, Long>> itr_list4HashMap4ForecastData4OutlierAnalyze = list4HashMap4ForecastData4OutlierAnalyze.iterator();
						while( itr_list4HashMap4ForecastData4OutlierAnalyze.hasNext() )
						{
							HashMap<Integer, Long> hashMap4ForecastData4OutlierAnalyze = itr_list4HashMap4ForecastData4OutlierAnalyze.next();
							Long tempForecastDatavalue = hashMap4ForecastData4OutlierAnalyze.get( period );
							if( tempForecastDatavalue != null )
							{
								forecastDataValue = forecastDataValue + tempForecastDatavalue;
							}
						}
					}
					arr4ForecastDataPeriodTotalValue4OutlierAnalyze[i] = forecastDataValue;
				}
				// 汇总历史数据和预测数据 end
				// 计算误差 begin
				toAddForecastErrorMappingModel = new ForecastErrorMappingModel();
				toAddForecastErrorMappingModel.setForecastInstCode( forecastInst2Run.getCode() );
				toAddForecastErrorMappingModel.setForecastInstName( forecastInst2Run.getName() );
				toAddForecastErrorMappingModel.setForecastModelCode( forecastInst2Run.getMappingFcModel().getCode() );
				toAddForecastErrorMappingModel.setForecastModelName( forecastInst2Run.getMappingFcModel().getName() );
				toAddForecastErrorMappingModel.setCompilePeriod( bSysPeriod.getCompilePeriod() );
				toAddForecastErrorMappingModel.setOutlierAnalyzePeriodNum( periodNum4OutlierAnalyze );
				toAddForecastErrorMappingModel.setRunOrganizationLayer( forecastInst2Run.getRunOrganizationLayer() );
				toAddForecastErrorMappingModel.setRunProductLayer( forecastInst2Run.getRunProductLayer() );
				toAddForecastErrorMappingModel.setProducedTime( daoSystem.getSysTimeAsTimeStamp() );
				toAddForecastErrorMappingModel.setErrorThreshold( forecastInst2Run.getErrorThreshold() );

				Double et = 0.0;
				Double mape = 0.0;
				Double mad = 0.0;
				Double ts = 0.0;
				Double mse = 0.0;

				for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
				{
					Long historyDataValue = arr4HistoryDataPeriodTotalValue4OutlierAnalyze[i];
					Long forecastDataValue = arr4ForecastDataPeriodTotalValue4OutlierAnalyze[i];
					et = et + historyDataValue - forecastDataValue;
					if( historyDataValue.longValue() != 0 )
					{
						mape = mape + Math.abs( (historyDataValue - forecastDataValue) * 100.0 ) / historyDataValue;
					}
					mad = mad + Math.abs( historyDataValue - forecastDataValue );
					//mse = mse + (historyDataValue - forecastDataValue) * (historyDataValue - forecastDataValue);
				}
				//	marked by liuzhen, 2011.02.18	begin
//				et = et / periodNum4OutlierAnalyze;
				//	marked by liuzhen, 2011.02.18	end
				mape = mape / periodNum4OutlierAnalyze;
				mad = mad / periodNum4OutlierAnalyze;
				if( mad.doubleValue() != 0 )
				{
					ts = et / mad;
				}
				//mse = mse / periodNum4OutlierAnalyze;
				
				NumberFormat numberFormat = NumberFormat.getInstance();
				numberFormat.setMaximumFractionDigits( 4 );
				numberFormat.setGroupingUsed( false );
				et = Double.parseDouble( numberFormat.format( et ) );
				mape = Double.parseDouble( numberFormat.format( mape ) );
				mad = Double.parseDouble( numberFormat.format( mad ) );
				ts = Double.parseDouble( numberFormat.format( ts ) );
				mse = Double.parseDouble( numberFormat.format( mse ) );
				
				toAddForecastErrorMappingModel.setEt( et );
				toAddForecastErrorMappingModel.setMape( mape );
				toAddForecastErrorMappingModel.setMad( mad );
				toAddForecastErrorMappingModel.setTs( ts );
				toAddForecastErrorMappingModel.setMse( mse );
				// 计算误差 end
				// 针对整个预测范围产生误差分析 end
				
				_bForecastRunTaskItem.setEt( et );
				_bForecastRunTaskItem.setMape( mape );
				_bForecastRunTaskItem.setMad( mad );
				_bForecastRunTaskItem.setTs( ts );
				_bForecastRunTaskItem.setMse( mse );				

				if( toAddForecastErrorMappingModel.getMape() > forecastInst2Run.getErrorThreshold() && _bForecastRunTaskItem.getIsIgnoreErrorThreshold() == BizConst.GLOBAL_YESNO_NO )
				{
					_bForecastRunTaskItem.setStatus( BizConst.FORECASTRUNTASKITEM_STATUS_RUNNED );
					_bForecastRunTaskItem.setResult( BizConst.FORECASTRUNTASKITEM_RESULT_ERRORTHRESHOLD );
	
					blRunForecast = false;
				}
			}//forecastInst2Run.getMappingFcModel() != null
			
			logger.info("["+forecastInst2Run.getName()+"]统计预测数据运算完成，开始分解刷新统计预测数据和人工、最终预测数据");
			// toUpdForecastDataList
			// toAddForecastDataList
			List<ForecastData> toAddForecastDataList = new ArrayList<ForecastData>();
			List<ForecastData> toUpdForecastDataList = new ArrayList<ForecastData>();
			DaoForecastData daoForecastData = new DaoForecastData( session );
			
			// 要处理的各种业务数据 begin
			DaoBizData daoBizData = new DaoBizData( session );

			// 预测开始期间和预测期间列表
			int periodBegin4Forecast = UtilPeriod.getPeriod( bSysPeriod.getForecastRunPeriod(), 0 );
			// 映射模型预测业务数据
			BizData mappingModelFcBizData = daoBizData.getMappingModelFcBizData();
			BizData amountMappingModelFcBizData = daoBizData.getBizDataByCode(mappingModelFcBizData.getCode()+BizConst.AMOUNT_BIZ_DATA_SUFFIX);
			
			if( blRunForecast == true )
			{
				// 逐组运行模型并分解预测数据到明晰组织产品，并在明细组织产品上计算人工预测数据、组合预测数据 begin

				// 人工预测业务数据,只查询和统计预测有关的人工预测 zhangzy 20170625	
				List<BizData> tempList4FcHandBizData = daoBizData.getBizDatasByTypesWithoutAmount( new Integer[]{BizConst.BIZDATA_TYPE_FCHAND}, new Integer[]{BizConst.GLOBAL_YESNO_YES} );
				List<Long> list4FcHandBizDataIds = new ArrayList<Long>();
				List<BizData> list4FcHandBizData = new ArrayList<BizData>(tempList4FcHandBizData.size());
				if( CollectionUtils.isNotEmpty(tempList4FcHandBizData) )
				{
					for(BizData handBizData : tempList4FcHandBizData){
						//数据定义项不为空，才说明和统计预测有关联
						if( CollectionUtils.isNotEmpty(handBizData.getBizDataDefItems()) ){
							list4FcHandBizData.add(handBizData);
							list4FcHandBizDataIds.add(handBizData.getId());
							
							//XXX 设置该业务数据对应的折合业务数据 zhangzy20170625
							BizData amountHandFcBizData = daoBizData.getBizDataByCode(handBizData.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
							handBizData.setAmountBizData(amountHandFcBizData);
						}
					}
				}
				
				// 要处理的各种业务数据 end
				for( List<AProOrg> list4GroupedDetailProOrgs : hmap4List4GroupedDetailProOrgs.values() )
				{
					// 一组明细组织产品范围
					if( list4GroupedDetailProOrgs.isEmpty() ){
						continue;
					}
					
					Date currentTime = daoSystem.getSysTimeAsTimeStamp();
					// 该组明细组织产品范围的ID字符串
					String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );

					// 预测数据 begin
					HashMap<Integer, Long> hmap4ForecastData = new HashMap<Integer, Long>();
					if( forecastInst2Run.getMappingFcModel() != null )
					{
						// 历史数据 begin
						HashMap<Integer, Long> hmap4HistoryData = hashMap4HashMap4HistoryData4Forecast.get( idsScopeStr4GroupedDetailProOrgs );
						// 历史数据 end

						//XXX 获取预测数据
						hmap4ForecastData = this.getForecastDataByRunFormula4ForecastModelM( forecastInst2Run.getMappingFcModel(), periodBegin4Forecast, forecastInst2Run.getFcPeriodNum(), hmap4HistoryData );
						if( hmap4ForecastData == null || hmap4ForecastData.isEmpty() )
						{
							hmap4ForecastData = new HashMap<Integer, Long>();
						}
					}
					else
					{
						// 没有映射模型，预测算法没有产生预测数据
						hmap4ForecastData = new HashMap<Integer, Long>();
					}
					// 预测数据 end

					// 把预测数据分解到每个明细组织产品上、并在明细组织产品上计算人工预测数据、组合预测数据 begin
					
					//	准备分解参照数据	begin

					//	按照 前N期历史平均分解，在这里处理一次，后面每个期间都参照这个数据
					//	按照 去年同期分解，这里不处理，后面每个期间的时候，计算自己相应的参照期间的信息，虽然对于超过一年的情况，会对一个参照期间多次查询，但考虑到超过一年的可能性的比例，还是用现在这种办法
					Long totalValue4Ref = 0L; // 总值
					HashMap<String, Long> hmap4Ref_po_value = new HashMap<String, Long>(); // 详情 	
					
					if( forecastInst2Run.getDistributeRefFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_AVERAGE )
					{
						//	按照前N期历史平均分解

						BizData distributeRefBizData = forecastInst2Run.getDistributeRefBizData();	//	历史类业务数据
						int distributeRefPeriodNum = forecastInst2Run.getDistributeRefPeriodNum();
						
						int periodBegin4ref = UtilPeriod.getPeriod( periodBegin4Forecast, -distributeRefPeriodNum );
						int periodEnd4ref = UtilPeriod.getPeriod( periodBegin4Forecast, -1 );
						
						hmap4Ref_po_value = daoHistoryData.getHistoryDataAvgGroupByPO( idsScopeStr4GroupedDetailProOrgs, periodBegin4ref, periodEnd4ref, distributeRefBizData.getId() );
						if( hmap4Ref_po_value == null )
						{
							totalValue4Ref = 0L;
							hmap4Ref_po_value = new HashMap<String, Long>();
						}
						if( hmap4Ref_po_value.values() != null && !(hmap4Ref_po_value.values().isEmpty()) )
						{
							totalValue4Ref = 0L;
							Iterator<Long> itr_hmap4Ref_po_value_values = hmap4Ref_po_value.values().iterator();
							while( itr_hmap4Ref_po_value_values.hasNext() )
							{
								totalValue4Ref = totalValue4Ref + itr_hmap4Ref_po_value_values.next();
							}
						}
						
					}
					else if( forecastInst2Run.getDistributeRefFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_COUNTERPARTOFLAST1YEAR )
					{
						//	按照去年同期分解
						//	按照去年同期分解，每个处理期间的参照期间是不同的，所以要分别处理，写在后面，不写在这里
					}
					//	准备分解参照数据	end				

					for( int i = forecastInst2Run.getFzPeriodNum(); i < forecastInst2Run.getFcPeriodNum(); i = i + 1 )	//	屏蔽期间外预测期间内的数据才能处理
					{
						int period = UtilPeriod.getPeriod( periodBegin4Forecast, i );
						
						//	存放本期的最新预测数据(以便能够用最新的模型预测计算人工预测、用最新的模型预测和人工预测计算组合预测)	begin
						HashMap<String, ForecastData> newestForecastDataHashMap = new HashMap<String, ForecastData>();			

						// 该查询范围该月的映射预测数据以便更新或者新建 begin
						HashMap<String, ForecastData> hmap4OldForecastData4MappingModelFc = daoForecastData.getForecastDatasMap( idsScopeStr4GroupedDetailProOrgs, period, mappingModelFcBizData.getId() );

						// 该查询范围该月的人工预测数据以便更新或者新建 begin
						HashMap<String, ForecastData> hmap4OldForecastData4FcHand = daoForecastData.getForecastDatas( idsScopeStr4GroupedDetailProOrgs, period, list4FcHandBizDataIds );
						
						//	该期间的分解参照信息	begin		
						if( forecastInst2Run.getDistributeRefFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_AVERAGE )
						{
							//	按照前N期历史平均分解
							//	参照信息已经统一算好，这里 nothing to do
							
						}
						else if( forecastInst2Run.getDistributeRefFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_COUNTERPARTOFLAST1YEAR )
						{
							//	按照去年同期分解
							totalValue4Ref = 0L; // 总值
							hmap4Ref_po_value = new HashMap<String, Long>(); // 详情 	
							

							//	参照期间	begin
							int lastYear = UtilPeriod.getPeriodYear( periodBegin4Forecast ) - 1;	//	去年是指预测开始期间的去年
							int periodNo = UtilPeriod.getPeriodNo( period );
							
							int periodRef = UtilPeriod.createPeriod( lastYear, periodNo );						
							//	参照期间	end
							
							BizData distributeRefBizData = forecastInst2Run.getDistributeRefBizData();	//	历史类业务数据
													
							List<HistoryData> listHistoryData4Ref = daoHistoryData.getHistoryDatas( idsScopeStr4GroupedDetailProOrgs, periodRef, distributeRefBizData.getId() );
							if( listHistoryData4Ref != null && !(listHistoryData4Ref.isEmpty()) )
							{
								for( int j=0; j<listHistoryData4Ref.size(); j++ )
								{
									HistoryData historyData4Ref = listHistoryData4Ref.get( j );
									
									totalValue4Ref = totalValue4Ref + Math.round(historyData4Ref.getValue());
									
									String strKey4po = UtilStrKey.getStrKey4PO( historyData4Ref.getProduct(), historyData4Ref.getOrganization() );
									hmap4Ref_po_value.put( strKey4po, Math.round(historyData4Ref.getValue()) );
								}								
							}					
						}
						//	该期间的分解参照信息	end

						// 映射模型预测数据 begin
						// 该月该范围内预测总值
						Long newTotalValue4ForecastData4MappingModelFc = hmap4ForecastData.get( period );
						if( newTotalValue4ForecastData4MappingModelFc == null )
						{
							newTotalValue4ForecastData4MappingModelFc = 0L;
						}

						Long distributedValue = 0L;
						Iterator<AProOrg> itr_list4GroupedDetailProOrgs = list4GroupedDetailProOrgs.iterator();
						while( itr_list4GroupedDetailProOrgs.hasNext() )
						{
							Long newValue = 0L;
							AProOrg detailProOrg = itr_list4GroupedDetailProOrgs.next();
							
							//	分解求得detailProOrg 上的值	begin
							if( hmap4Ref_po_value == null || hmap4Ref_po_value.isEmpty() || totalValue4Ref.longValue() == 0 )
							{
								// 没有数据可以用来参照或者
								// 参照数据都是0，把运行模型产生的数据在范围内均分
								newValue = Math.round( newTotalValue4ForecastData4MappingModelFc * 1.0 / list4GroupedDetailProOrgs.size() );
							}
							else
							{
								// 参照数据中至少有一个不是0，按比例分解
								String keyStr4po = UtilStrKey.getStrKey4PO( detailProOrg.getProduct(), detailProOrg.getOrganization() );
								Long value4Ref = hmap4Ref_po_value.get( keyStr4po );
								if( value4Ref == null )
								{
									value4Ref = 0L;
								}
								newValue = Math.round( value4Ref * 1.0 / totalValue4Ref * newTotalValue4ForecastData4MappingModelFc );
							}							

							if( distributedValue + newValue > newTotalValue4ForecastData4MappingModelFc )
							{
								// 保证不超分
								newValue = newTotalValue4ForecastData4MappingModelFc - distributedValue;
							}
							if( !(itr_list4GroupedDetailProOrgs.hasNext()) )
							{
								// 保证不剩下
								newValue = newTotalValue4ForecastData4MappingModelFc - distributedValue;
							}
							distributedValue = distributedValue + newValue;
							//	分解求得detailProOrg 上的值	end
							
							
							String strKey4popb = UtilStrKey.getStrKey4POPB( detailProOrg.getProduct(), detailProOrg.getOrganization(), period, mappingModelFcBizData );
							
							ForecastData oldForecastData4MappingModelFc = hmap4OldForecastData4MappingModelFc.get( strKey4popb );
							if( oldForecastData4MappingModelFc != null )
							{
								ForecastData toUpdForecastData = oldForecastData4MappingModelFc;
								toUpdForecastData.setValue( newValue );
								toUpdForecastData.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								toUpdForecastData.setUpdateTime( currentTime );
								toUpdForecastData.setAmountBizData(amountMappingModelFcBizData);
								
								toUpdForecastDataList.add( toUpdForecastData );
								newestForecastDataHashMap.put( strKey4popb, toUpdForecastData );
							}
							else
							{	
								ForecastData toAddForecastData = new ForecastData();
								toAddForecastData.setPeriod( period );
								toAddForecastData.setValue( newValue );
								toAddForecastData.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								toAddForecastData.setInitTime( currentTime );
								toAddForecastData.setUpdateTime( currentTime );
								toAddForecastData.setBizData( mappingModelFcBizData );
								toAddForecastData.setOrganization( detailProOrg.getOrganization() );
								toAddForecastData.setProduct( detailProOrg.getProduct() );
								toAddForecastData.setAmountBizData(amountMappingModelFcBizData);//折合业务数据

								toAddForecastDataList.add( toAddForecastData ); //TODO 埋伏
								newestForecastDataHashMap.put( strKey4popb, toAddForecastData );
							}
						}
						// 映射模型预测数据 end

						// 人工预测数据 begin
						for( AProOrg detailProOrg : list4GroupedDetailProOrgs )
						{
							if( list4FcHandBizData == null || list4FcHandBizData.isEmpty() ){
								continue;
							}

							for( BizData handFcBizData : list4FcHandBizData )
							{
								String strKey4popb = UtilStrKey.getStrKey4POPB( detailProOrg.getProduct(), detailProOrg.getOrganization(), period, handFcBizData );
								ForecastData oldForecastData4FcHand = hmap4OldForecastData4FcHand.get( strKey4popb );
								
								Long newValue4FcHand = 0L;
								
								if( CollectionUtils.isNotEmpty(handFcBizData.getBizDataDefItems()) )
								{
									// 根据FcHand BizData的定义公式用最新的映射模型预测数据计算FcHand的值
									Iterator<BizDataDefItem> itr_bizDataDefItems = handFcBizData.getBizDataDefItems().iterator();
									newValue4FcHand = 0L;
									while( itr_bizDataDefItems.hasNext() )
									{
										BizDataDefItemFcHand bizDataDefItemFcHand = (BizDataDefItemFcHand) itr_bizDataDefItems.next();
										BizData itemBizData = bizDataDefItemFcHand.getItemBizData();
										String strKey4popb_item = UtilStrKey.getStrKey4POPB( detailProOrg.getProduct(), detailProOrg.getOrganization(), period, itemBizData );
										if( itemBizData.getId().longValue() == mappingModelFcBizData.getId().longValue() )
										{
											ForecastData newestForecastData4MappingModelFc = newestForecastDataHashMap.get( strKey4popb_item );
											if( newestForecastData4MappingModelFc != null )
											{
												newValue4FcHand = Math.round( newValue4FcHand + newestForecastData4MappingModelFc.getValue() * bizDataDefItemFcHand.getCoefficient() );
											}
										}
									}
								}
								else{
									//	FcHand 不受 统计预测的影响
									if( oldForecastData4FcHand != null ){
										//	原来有的，保持原指
										newValue4FcHand = oldForecastData4FcHand.getValue();
									}else{
										//	原来没有的，取0值
										newValue4FcHand = 0L;
									}
								}


								if( oldForecastData4FcHand != null )
								{
									ForecastData toUpdForecastData = oldForecastData4FcHand;

									toUpdForecastData.setValue( newValue4FcHand );
									toUpdForecastData.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
									toUpdForecastData.setUpdateTime( currentTime );
									toUpdForecastData.setAmountBizData(handFcBizData.getAmountBizData());
									
									toUpdForecastDataList.add( toUpdForecastData );
									newestForecastDataHashMap.put( strKey4popb, toUpdForecastData );
								}
								else
								{							
									ForecastData toAddForecastData = new ForecastData();
									toAddForecastData.setPeriod( period );
									toAddForecastData.setValue( newValue4FcHand );
									toAddForecastData.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
									toAddForecastData.setInitTime( currentTime );
									toAddForecastData.setUpdateTime( currentTime );
									toAddForecastData.setBizData( handFcBizData );
									toAddForecastData.setOrganization( detailProOrg.getOrganization() );
									toAddForecastData.setProduct( detailProOrg.getProduct() );
									toAddForecastData.setAmountBizData(handFcBizData.getAmountBizData());//折合业务数据

									toAddForecastDataList.add( toAddForecastData ); //TODO 埋伏
									newestForecastDataHashMap.put( strKey4popb, toAddForecastData );
								}

							}
						}
						// 人工预测数据 end

					}
//					session.flush();
//					session.clear();
					// 每个期间把预测数据分解到每个明细组织产品上、并在明细组织产品上计算人工预测数据、最终预测数据 end
				}
				// 逐组运行模型并分解预测数据到明晰组织产品，并在明细组织产品上计算人工预测数据、最终预测数据 end		
				
				_bForecastRunTaskItem.setResult( BizConst.FORECASTRUNTASKITEM_RESULT_SUCCEED );
				_bForecastRunTaskItem.setStatus( BizConst.FORECASTRUNTASKITEM_STATUS_RUNNED );
			}


			// 数据库操作 begin
			// 容差期间的误差
			if( toAddForecastErrorMappingModel != null )
			{
				DaoForecastErrorMappingModel daoForecastErrorMappingModel = new DaoForecastErrorMappingModel( session );
				daoForecastErrorMappingModel.save( toAddForecastErrorMappingModel );
			}

			// 新增预测数据
			int count = 0;
			for (int i = 0; i < toAddForecastDataList.size(); i++) {
				daoForecastData.save(toAddForecastDataList.get(i));
				//批量更新
//				if (++count % 20 == 0) {
//					session.flush();
//					session.clear();
//				}
			}

			// 更新预测数据
			// 数据库读出的对象，不必显式调用
//			if( toUpdForecastDataList != null && !(toUpdForecastDataList.isEmpty()) )
//			{
//				for( int i=0; i<toUpdForecastDataList.size(); i++ )
//				{
//					daoForecastData.update( toUpdForecastDataList.get( i ) );
//				}
//			}
			
			// 最终预测业务数据
			BizData fcFinalBizData = daoBizData.getFinalFcBizData();
			BizData amountFcFinalBizData = daoBizData.getBizDataByCode(fcFinalBizData.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX );
			BizData finalFcBizData_combFc = forecastInst2Run.getFinalFcBizData();//	最终预测数据指向的组合预测
			
			//判断最终预测的值是否包含统计预测，如果不包含，则不必重新计算最终预测
			if( finalForecastService.isFinalDataContainsModelData(finalFcBizData_combFc, mappingModelFcBizData));
			{
				Date currentTime = daoSystem.getSysTimeAsTimeStamp();
				for( int i = forecastInst2Run.getFzPeriodNum(); i < forecastInst2Run.getFcPeriodNum(); i = i + 1 )	
					//	屏蔽期间外预测期间内的数据才能处理
				{
				
					int period = UtilPeriod.getPeriod( periodBegin4Forecast, i );
					
					for( List<AProOrg> list4GroupedDetailProOrgs : hmap4List4GroupedDetailProOrgs.values() )
					{
						// 该组明细组织产品范围的ID字符串
						String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );
						// 该查询范围该月的最终预测数据以便更新或者新建 begin
						HashMap<String, ForecastData> hmap4OldForecastData4FcFinal = daoForecastData.getForecastDatasMap( idsScopeStr4GroupedDetailProOrgs, period, fcFinalBizData.getId() );
						
						for( AProOrg detailProOrg : list4GroupedDetailProOrgs )
						{
							String strKey4popb = UtilStrKey.getStrKey4POPB( detailProOrg.getProduct(), detailProOrg.getOrganization(), period, fcFinalBizData );
							ForecastData oldForecastData4FcFinal = hmap4OldForecastData4FcFinal.get( strKey4popb );
							long newValue4FcComb = finalForecastService.computeFinalForecast(finalFcBizData_combFc, detailProOrg, period, daoForecastData);
							if( oldForecastData4FcFinal != null )
							{
								ForecastData toUpdForecastData = oldForecastData4FcFinal;
								toUpdForecastData.setValue( newValue4FcComb ); 
								toUpdForecastData.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								toUpdForecastData.setUpdateTime( currentTime );
								toUpdForecastData.setAmountBizData(amountFcFinalBizData);

								toUpdForecastDataList.add( toUpdForecastData );
							}
							else
							{
								ForecastData toAddForecastData = new ForecastData();
								toAddForecastData.setPeriod( period );
								toAddForecastData.setValue( newValue4FcComb ); 
								toAddForecastData.setStatus( BizConst.FORECASTDATA_STATUS_ACTIVE );
								toAddForecastData.setInitTime( currentTime );
								toAddForecastData.setUpdateTime( currentTime );
								toAddForecastData.setBizData( fcFinalBizData );
								toAddForecastData.setOrganization( detailProOrg.getOrganization() );
								toAddForecastData.setProduct( detailProOrg.getProduct() );
								toAddForecastData.setAmountBizData(amountFcFinalBizData);

								toAddForecastDataList.add( toAddForecastData ); //TODO 埋伏
							}
						}
					}// end hmap4List4GroupedDetailProOrgs.values()
				}
				//	最终预测数据	end
			}

			// 更新预测类别的运行时间
			forecastInst2Run.setMappingFcModelRunTime( daoSystem.getSysTimeAsTimeStamp() );
			forecastInst2Run.setIsRunned( BizConst.GLOBAL_YESNO_YES );
			// 数据库读出的对象，不必显式调用
			// daoForecastInst.updateForecastInst(forecastInst2Run);
			
			
			//	_bForecastRunTaskItem	begin
			_bForecastRunTaskItem.setEndTime( daoSystem.getSysTimeAsTimeStamp() );
				
			ForecastRunTaskItemBDConvertor forecastRunTaskItemBDConvertor = new ForecastRunTaskItemBDConvertor();
			DaoForecastRunTaskItem daoForecastRunTaskItem = new DaoForecastRunTaskItem( session );
			daoForecastRunTaskItem.update( forecastRunTaskItemBDConvertor.btod( _bForecastRunTaskItem ) );
			//	_bForecastRunTaskItem	end

			if( trsa != null )
			{
				trsa.commit();
			}

			// 数据库操作 end

			//TODO 启动一个线程进行折合数据的计算，模型预测、判断预测、最终预测
			amountForecastService.calculateAmountData(toAddForecastDataList);
			amountForecastService.calculateAmountData(toUpdForecastDataList);
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}	
		
	}
	// end forecastInstRunMappingModel4Forecast
	
	/**
	 * 获取预测模型产生人预测数据
	 * @param _forecastModelM
	 * @param periodBegin4Run
	 * @param _periodNum4Run
	 * @param _hmap4HistoryData
	 * @return
	 */
	private HashMap<Integer, Long> getForecastDataByRunFormula4ForecastModelM( ForecastModelM _forecastModelM, int periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> hmap4ForecastData = new HashMap<Integer, Long>();

		if( _forecastModelM instanceof ForecastModelMLMa )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4LMa( (ForecastModelMLMa) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMLWma )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4LWma( (ForecastModelMLWma) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}	
		else if( _forecastModelM instanceof ForecastModelMLEs )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4LEs( (ForecastModelMLEs) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMLEso )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4LEso( (ForecastModelMLEso) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}				
		
		else if( _forecastModelM instanceof ForecastModelMTPly )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4TPly( (ForecastModelMTPly) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}		
		else if( _forecastModelM instanceof ForecastModelMTCply )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4TCply( (ForecastModelMTCply) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}			
		else if( _forecastModelM instanceof ForecastModelMTEs )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4TEs( (ForecastModelMTEs) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMTEso )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4TEso( (ForecastModelMTEso) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}		
		else if( _forecastModelM instanceof ForecastModelMTLa )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4TLa( (ForecastModelMTLa) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}		
		
		else if( _forecastModelM instanceof ForecastModelMSLMa )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4SLMa( (ForecastModelMSLMa) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}	
		else if( _forecastModelM instanceof ForecastModelMSLWma )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4SLWma( (ForecastModelMSLWma) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}	
		else if( _forecastModelM instanceof ForecastModelMSLEs )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4SLEs( (ForecastModelMSLEs) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMSLEso )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4SLEso( (ForecastModelMSLEso) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}				
		
		else if( _forecastModelM instanceof ForecastModelMSTPly )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STPly( (ForecastModelMSTPly) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}		
		else if( _forecastModelM instanceof ForecastModelMSTCply )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STCply( (ForecastModelMSTCply) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}			
		else if( _forecastModelM instanceof ForecastModelMSTEsa )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STEsa( (ForecastModelMSTEsa) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMSTEsao )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STEsao( (ForecastModelMSTEsao) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}				
		else if( _forecastModelM instanceof ForecastModelMSTEsm )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STEsm( (ForecastModelMSTEsm) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMSTEsmo )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STEsmo( (ForecastModelMSTEsmo) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}			
		else if( _forecastModelM instanceof ForecastModelMSTLa )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4STLa( (ForecastModelMSTLa) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}		
		
		else if( _forecastModelM instanceof ForecastModelMAAnalog )
		{
			hmap4ForecastData = this.forecastModelMRunFormula4AAnalog( (ForecastModelMAAnalog) _forecastModelM, periodBegin4Run, _periodNum4Run, _hmap4HistoryData );
		}
		
		else
		{
			// to do
		}
		if( hmap4ForecastData == null )
		{
			hmap4ForecastData = new HashMap<Integer, Long>();
		}
		return hmap4ForecastData;
	}
	
	/**
	 * _hmap4Hmap4GroupedHistoryData 内的数据会被多次使用，本方法内要使用该数据的副本，不能修改该数据
	 */
	private double getMapeByRunForecastModelM( BSysPeriod _bSysPeriod, ForecastModelM _forecastModelM2Run, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String,HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		
		// list4HashMap4ForecastData4OutlierAnalyze
		// 存放容差期间的预测数据以便进行容差期间的误差计算
		// list4HashMap4HistoryData4OutlierAnalyze
		// 存放容差期间的调整后的历史数据以便进行容差期间的误差计算
		// periodNum4OutlierAnalyze 容差分析期间期间数
		// periodBegin4OutlierAnalyze 容差分析开始期间

		List<HashMap<Integer, Long>> list4HashMap4ForecastData4OutlierAnalyze = new ArrayList<HashMap<Integer, Long>>();
		List<HashMap<Integer, Long>> list4HashMap4HistoryData4OutlierAnalyze = new ArrayList<HashMap<Integer, Long>>();
		int periodNum4OutlierAnalyze = _forecastModelM2Run.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		Iterator<List<AProOrg>> itr_hmap4List4GroupedDetailProOrgs_values = _hmap4List4GroupedDetailProOrgs.values().iterator();
		while( itr_hmap4List4GroupedDetailProOrgs_values.hasNext() )
		{
			// 一组明细组织产品范围
			List<AProOrg> list4GroupedDetailProOrgs = itr_hmap4List4GroupedDetailProOrgs_values.next();
			if( list4GroupedDetailProOrgs.isEmpty() )
			{
				continue;
			}
			// 该组明细组织产品范围的ID字符串
			String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );

			// 该组明细组织产品范围的历史数据准备 begin
			HashMap<Integer, Long> originHmap4HistoryData = _hmap4Hmap4GroupedHistoryData.get( idsScopeStr4GroupedDetailProOrgs );			
			if( originHmap4HistoryData == null )
			{
				originHmap4HistoryData = new HashMap<Integer, Long>();
			}
			// 制作局部数据副本
			HashMap<Integer, Long> hmap4HistoryData = new HashMap<Integer, Long>();
			
			if( originHmap4HistoryData.keySet() != null && !(originHmap4HistoryData.isEmpty()) )
			{
				Iterator<Integer> itr_originHmap4HistoryData_keySet = originHmap4HistoryData.keySet().iterator();
				while( itr_originHmap4HistoryData_keySet.hasNext() )
				{
					int period = itr_originHmap4HistoryData_keySet.next();
					Long historyDataValue = 0L;
					
					Long originHistoryDataValue = originHmap4HistoryData.get( period );
					if( originHistoryDataValue != null )
					{
						historyDataValue = new Long( originHistoryDataValue );
					}
					
					hmap4HistoryData.put( period, historyDataValue );
				}
			}
			// 该组明细组织产品范围的历史数据准备 end

			//XXX 预测数据 begin
			HashMap<Integer, Long> hmap4ForecastData = this.getForecastDataByRunFormula4ForecastModelM( _forecastModelM2Run, periodBegin4OutlierAnalyze, periodNum4OutlierAnalyze,
					hmap4HistoryData );
			if( hmap4ForecastData == null )
			{
				hmap4ForecastData = new HashMap<Integer, Long>();
			}
			// 预测数据 end

			// 计算MAD begin
			double mad = 0;
			for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
			{
				int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
				Long historyDataValue = hmap4HistoryData.get( period );
				if( historyDataValue == null )
				{
					historyDataValue = 0L;
				}
				Long forecastDataValue = hmap4ForecastData.get( period );
				if( forecastDataValue == null )
				{
					forecastDataValue = 0L;
				}
				mad = mad + Math.abs( historyDataValue - forecastDataValue );
			}
			mad = mad / periodNum4OutlierAnalyze;
			// 计算MAD end

			// 调整历史数据 begin
			if( _forecastModelM2Run.getOutlierDataIsAutoAdjust() == BizConst.GLOBAL_YESNO_YES )
			{
				for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
				{
					int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
					Long historyDataValue = hmap4HistoryData.get( period );
					if( historyDataValue == null )
					{
						historyDataValue = 0L;
					}
					Long forecastDataValue = hmap4ForecastData.get( period );
					if( forecastDataValue == null )
					{
						forecastDataValue = 0L;
					}

					if( Math.abs( historyDataValue - forecastDataValue ) > _forecastModelM2Run.getOutlierFactor() * 1.25 * mad )
					{
						historyDataValue = Math.round( historyDataValue * _forecastModelM2Run.getOutlierDataAdjustHistoryWgt() + forecastDataValue
								* _forecastModelM2Run.getOutlierDataAdjustComputeWgt() );
						hmap4HistoryData.put( period, historyDataValue );
					}
				}
			}
			// 调整历史数据 end

			// 存放调整后的历史数据以便计算容差期间的误差分析
			list4HashMap4HistoryData4OutlierAnalyze.add( hmap4HistoryData );
			// 存放预测数据以便计算容差期间的误差分析
			list4HashMap4ForecastData4OutlierAnalyze.add( hmap4ForecastData );
			// 以分组的明细组织产品范围的ID字符串为key存放调整后的历史数据，以便后面进行真正的预测
			// 此处不用
			// hashMap4HashMap4HistoryData4Forecast.put( idsScopeStr4GroupedDetailProOrgs, hmap4HistoryData );
		}
		//按预测范围根据预测层次分的组分别进行容差分析：得到容差分析期间的预测数据和并调整后的历史数据,及预测期间预测要使用的历史数据
		// end

		// 针对整个预测范围产生误差分析 begin
		// 汇总历史数据和预测数据 begin
		Long[] arr4HistoryDataPeriodTotalValue4OutlierAnalyze = new Long[periodNum4OutlierAnalyze];
		Long[] arr4ForecastDataPeriodTotalValue4OutlierAnalyze = new Long[periodNum4OutlierAnalyze];
		for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			Long historyDataValue = 0L;
			if( list4HashMap4HistoryData4OutlierAnalyze != null && !(list4HashMap4HistoryData4OutlierAnalyze.isEmpty()) )
			{
				Iterator<HashMap<Integer, Long>> itr_list4HashMap4HistoryData4OutlierAnalyze = list4HashMap4HistoryData4OutlierAnalyze.iterator();
				while( itr_list4HashMap4HistoryData4OutlierAnalyze.hasNext() )
				{
					HashMap<Integer, Long> hashMap4HistoryData4OutlierAnalyze = itr_list4HashMap4HistoryData4OutlierAnalyze.next();
					Long tempHistoryDatavalue = hashMap4HistoryData4OutlierAnalyze.get( period );
					if( tempHistoryDatavalue != null )
					{
						historyDataValue = historyDataValue + tempHistoryDatavalue;
					}
				}
			}
			arr4HistoryDataPeriodTotalValue4OutlierAnalyze[i] = historyDataValue;

			Long forecastDataValue = 0L;
			if( list4HashMap4ForecastData4OutlierAnalyze != null && !(list4HashMap4ForecastData4OutlierAnalyze.isEmpty()) )
			{
				Iterator<HashMap<Integer, Long>> itr_list4HashMap4ForecastData4OutlierAnalyze = list4HashMap4ForecastData4OutlierAnalyze.iterator();
				while( itr_list4HashMap4ForecastData4OutlierAnalyze.hasNext() )
				{
					HashMap<Integer, Long> hashMap4ForecastData4OutlierAnalyze = itr_list4HashMap4ForecastData4OutlierAnalyze.next();
					Long tempForecastDatavalue = hashMap4ForecastData4OutlierAnalyze.get( period );
					if( tempForecastDatavalue != null )
					{
						forecastDataValue = forecastDataValue + tempForecastDatavalue;
					}
				}
			}
			arr4ForecastDataPeriodTotalValue4OutlierAnalyze[i] = forecastDataValue;
		}
		// 汇总历史数据和预测数据 end
		
		// 计算误差 begin
		Double mape = 0.0;

		for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
		{
			Long historyDataValue = arr4HistoryDataPeriodTotalValue4OutlierAnalyze[i];
			Long forecastDataValue = arr4ForecastDataPeriodTotalValue4OutlierAnalyze[i];

			if( historyDataValue.longValue() != 0 )
			{
				mape = mape + Math.abs( (historyDataValue - forecastDataValue) * 100.0 ) / historyDataValue;
			}

		}

		mape = mape / periodNum4OutlierAnalyze;
					
		// 计算误差 end
		// 针对整个预测范围产生误差分析 end
		
		return mape;
	}
	
	private void optimizeForecastModelMLEso( BSysPeriod _bSysPeriod, ForecastModelMLEso _forecastModelMLEso, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String, HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		if( _forecastModelMLEso == null )
		{
			return;
		}
		 
		double smoothingFactor_init = _forecastModelMLEso.getSmoothingFactor();
		double smoothingFactor_opt = smoothingFactor_init;
		double mape_opt = 0.0;
		
		for( int step=0; step<10; step++ )
		{
			double smoothingFactor_test = smoothingFactor_init + 0.1 * step;
			if( smoothingFactor_test > 1.0 )
			{
				break;
			}
			
			_forecastModelMLEso.setSmoothingFactor( smoothingFactor_test );
			
			double mape_test = this.getMapeByRunForecastModelM( _bSysPeriod, _forecastModelMLEso, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
			
			if( step == 0 )
			{
				mape_opt = mape_test;
				smoothingFactor_opt = smoothingFactor_test;
			}
			else if( mape_test < mape_opt )
			{
				mape_opt = mape_test;
				smoothingFactor_opt = smoothingFactor_test;				
			}
		}
		
		_forecastModelMLEso.setSmoothingFactor( smoothingFactor_opt );
	}
	
	private void optimizeForecastModelMTEso( BSysPeriod _bSysPeriod, ForecastModelMTEso _forecastModelMTEso, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String, HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		if( _forecastModelMTEso == null )
		{
			return;
		}

		 
		double levelSmoothingFactor_init = _forecastModelMTEso.getLevelSmoothingFactor();
		double levelSmoothingFactor_opt = levelSmoothingFactor_init;
		double trendSmoothingFactor_init = _forecastModelMTEso.getTrendSmoothingFactor();
		double trendSmoothingFactor_opt = trendSmoothingFactor_init;		
		
		double mape_opt = 0.0;
		
		for( int step_level=0; step_level<10; step_level++ )
		{
			double levelSmoothingFactor_test = levelSmoothingFactor_init + 0.1 * step_level;
			if( levelSmoothingFactor_test > 1.0 )
			{
				break;
			}
			_forecastModelMTEso.setLevelSmoothingFactor( levelSmoothingFactor_test );
			
			for( int step_trend=0; step_trend<10; step_trend++ )
			{
				double trendSmoothingFactor_test = trendSmoothingFactor_init + 0.1 * step_trend;
				if( trendSmoothingFactor_test > 1.0 )
				{
					break;
				}
				_forecastModelMTEso.setTrendSmoothingFactor( trendSmoothingFactor_test );
				
				double mape_test = this.getMapeByRunForecastModelM( _bSysPeriod, _forecastModelMTEso, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
				
				if( step_level == 0 && step_trend == 0 )
				{
					mape_opt = mape_test;
					levelSmoothingFactor_opt = levelSmoothingFactor_test;
					trendSmoothingFactor_opt = trendSmoothingFactor_test;					
				}
				else if( mape_test < mape_opt )
				{
					mape_opt = mape_test;
					levelSmoothingFactor_opt = levelSmoothingFactor_test;
					trendSmoothingFactor_opt = trendSmoothingFactor_test;			
				}				
				
			}
		}
		
		_forecastModelMTEso.setLevelSmoothingFactor( levelSmoothingFactor_opt );		
		_forecastModelMTEso.setTrendSmoothingFactor( trendSmoothingFactor_opt );
	}
	
	private void optimizeForecastModelMSLEso( BSysPeriod _bSysPeriod, ForecastModelMSLEso _forecastModelMSLEso, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String, HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		if( _forecastModelMSLEso == null )
		{
			return;
		}
		
		 
		double smoothingFactor_init = _forecastModelMSLEso.getSmoothingFactor();
		double smoothingFactor_opt = smoothingFactor_init;
		double seasonSmoothingFactor_init = _forecastModelMSLEso.getSeasonSmoothingFactor();
		double seasonSmoothingFactor_opt = seasonSmoothingFactor_init;		
		
		double mape_opt = 0.0;
		
		for( int step=0; step<10; step++ )
		{
			double smoothingFactor_test = smoothingFactor_init + 0.1 * step;
			if( smoothingFactor_test > 1.0 )
			{
				break;
			}
			_forecastModelMSLEso.setSmoothingFactor( smoothingFactor_test );
			
			for( int step_season=0; step_season<10; step_season++ )
			{
				double seasonSmoothingFactor_test = seasonSmoothingFactor_init + 0.1 * step_season;
				if( seasonSmoothingFactor_test > 1.0 )
				{
					break;
				}
				_forecastModelMSLEso.setSeasonSmoothingFactor( seasonSmoothingFactor_test );
				
				double mape_test = this.getMapeByRunForecastModelM( _bSysPeriod, _forecastModelMSLEso, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
				
				if( step == 0 && step_season == 0 )
				{
					mape_opt = mape_test;
					smoothingFactor_opt = smoothingFactor_test;
					seasonSmoothingFactor_opt = seasonSmoothingFactor_test;
				}
				else if( mape_test < mape_opt )
				{
					mape_opt = mape_test;
					smoothingFactor_opt = smoothingFactor_test;
					seasonSmoothingFactor_opt = seasonSmoothingFactor_test;			
				}
			}
		}
		
		_forecastModelMSLEso.setSmoothingFactor( smoothingFactor_opt );
		_forecastModelMSLEso.setSeasonSmoothingFactor( seasonSmoothingFactor_opt );		
	}	
	
	private void optimizeForecastModelMSTEsao( BSysPeriod _bSysPeriod, ForecastModelMSTEsao _forecastModelMSTEsao, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String, HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		if( _forecastModelMSTEsao == null )
		{
			return;
		}

		 
		double levelSmoothingFactor_init = _forecastModelMSTEsao.getLevelSmoothingFactor();
		double levelSmoothingFactor_opt = levelSmoothingFactor_init;
		double trendSmoothingFactor_init = _forecastModelMSTEsao.getTrendSmoothingFactor();
		double trendSmoothingFactor_opt = trendSmoothingFactor_init;		
		double seasonSmoothingFactor_init = _forecastModelMSTEsao.getSeasonSmoothingFactor();
		double seasonSmoothingFactor_opt = seasonSmoothingFactor_init;			
		
		double mape_opt = 0.0;
		
		for( int step_level=0; step_level<10; step_level++ )
		{
			double levelSmoothingFactor_test = levelSmoothingFactor_init + 0.1 * step_level;
			if( levelSmoothingFactor_test > 1.0 )
			{
				break;
			}
			_forecastModelMSTEsao.setLevelSmoothingFactor( levelSmoothingFactor_test );
			
			for( int step_trend=0; step_trend<10; step_trend++ )
			{
				double trendSmoothingFactor_test = trendSmoothingFactor_init + 0.1 * step_trend;
				if( trendSmoothingFactor_test > 1.0 )
				{
					break;
				}
				_forecastModelMSTEsao.setTrendSmoothingFactor( trendSmoothingFactor_test );
				
				for( int step_season=0; step_season<10; step_season++ )
				{
					double seasonSmoothingFactor_test = seasonSmoothingFactor_init + 0.1 * step_season;
					if( seasonSmoothingFactor_test > 1.0 )
					{
						break;
					}
					_forecastModelMSTEsao.setSeasonSmoothingFactor( seasonSmoothingFactor_test );
					
					
					double mape_test = this.getMapeByRunForecastModelM( _bSysPeriod, _forecastModelMSTEsao, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
					
					if( step_level == 0 && step_trend == 0 && step_season == 0 )
					{
						mape_opt = mape_test;
						levelSmoothingFactor_opt = levelSmoothingFactor_test;
						trendSmoothingFactor_opt = trendSmoothingFactor_test;	
						seasonSmoothingFactor_opt = seasonSmoothingFactor_test;
					}
					else if( mape_test < mape_opt )
					{
						mape_opt = mape_test;
						levelSmoothingFactor_opt = levelSmoothingFactor_test;
						trendSmoothingFactor_opt = trendSmoothingFactor_test;
						seasonSmoothingFactor_opt = seasonSmoothingFactor_test;
					}										
				}
			}
		}
		
		_forecastModelMSTEsao.setLevelSmoothingFactor( levelSmoothingFactor_opt );		
		_forecastModelMSTEsao.setTrendSmoothingFactor( trendSmoothingFactor_opt );
		_forecastModelMSTEsao.setSeasonSmoothingFactor( seasonSmoothingFactor_opt );
	}
	
	private void optimizeForecastModelMSTEsmo( BSysPeriod _bSysPeriod, ForecastModelMSTEsmo _forecastModelMSTEsmo, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String, HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		if( _forecastModelMSTEsmo == null )
		{
			return;
		}

		 
		double levelSmoothingFactor_init = _forecastModelMSTEsmo.getLevelSmoothingFactor();
		double levelSmoothingFactor_opt = levelSmoothingFactor_init;
		double trendSmoothingFactor_init = _forecastModelMSTEsmo.getTrendSmoothingFactor();
		double trendSmoothingFactor_opt = trendSmoothingFactor_init;		
		double seasonSmoothingFactor_init = _forecastModelMSTEsmo.getSeasonSmoothingFactor();
		double seasonSmoothingFactor_opt = seasonSmoothingFactor_init;			
		
		double mape_opt = 0.0;
		
		for( int step_level=0; step_level<10; step_level++ )
		{
			double levelSmoothingFactor_test = levelSmoothingFactor_init + 0.1 * step_level;
			if( levelSmoothingFactor_test > 1.0 )
			{
				break;
			}
			_forecastModelMSTEsmo.setLevelSmoothingFactor( levelSmoothingFactor_test );
			
			for( int step_trend=0; step_trend<10; step_trend++ )
			{
				double trendSmoothingFactor_test = trendSmoothingFactor_init + 0.1 * step_trend;
				if( trendSmoothingFactor_test > 1.0 )
				{
					break;
				}
				_forecastModelMSTEsmo.setTrendSmoothingFactor( trendSmoothingFactor_test );
				
				for( int step_season=0; step_season<10; step_season++ )
				{
					double seasonSmoothingFactor_test = seasonSmoothingFactor_init + 0.1 * step_season;
					if( seasonSmoothingFactor_test > 1.0 )
					{
						break;
					}
					_forecastModelMSTEsmo.setSeasonSmoothingFactor( seasonSmoothingFactor_test );
					
					
					double mape_test = this.getMapeByRunForecastModelM( _bSysPeriod, _forecastModelMSTEsmo, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
					
					if( step_level == 0 && step_trend == 0 && step_season == 0 )
					{
						mape_opt = mape_test;
						levelSmoothingFactor_opt = levelSmoothingFactor_test;
						trendSmoothingFactor_opt = trendSmoothingFactor_test;	
						seasonSmoothingFactor_opt = seasonSmoothingFactor_test;
					}
					else if( mape_test < mape_opt )
					{
						mape_opt = mape_test;
						levelSmoothingFactor_opt = levelSmoothingFactor_test;
						trendSmoothingFactor_opt = trendSmoothingFactor_test;
						seasonSmoothingFactor_opt = seasonSmoothingFactor_test;
					}										
				}
			}
		}
		
		_forecastModelMSTEsmo.setLevelSmoothingFactor( levelSmoothingFactor_opt );		
		_forecastModelMSTEsmo.setTrendSmoothingFactor( trendSmoothingFactor_opt );
		_forecastModelMSTEsmo.setSeasonSmoothingFactor( seasonSmoothingFactor_opt );
	}
	
	
	private void optimizeForecastModelM( BSysPeriod _bSysPeriod, ForecastModelM _forecastModelM, HashMap<String, List<AProOrg>> _hmap4List4GroupedDetailProOrgs, HashMap<String,HashMap<Integer, Long>> _hmap4Hmap4GroupedHistoryData )
	{
		// 优化过程中，修改预测类别的预测模型的参数，所以，不需要返回值
		// 被修改的预测模型是通过预测类别获取的，不会被自动保存，保留自适应初值
		
		if( _forecastModelM == null )
		{
			return;
		}
		
		if( _forecastModelM instanceof ForecastModelMLEso )
		{
			this.optimizeForecastModelMLEso( _bSysPeriod, (ForecastModelMLEso)_forecastModelM, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMTEso )
		{
			this.optimizeForecastModelMTEso( _bSysPeriod, (ForecastModelMTEso)_forecastModelM, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );			
		}
		else if( _forecastModelM instanceof ForecastModelMSLEso )
		{
			this.optimizeForecastModelMSLEso( _bSysPeriod, (ForecastModelMSLEso)_forecastModelM, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );
		}
		else if( _forecastModelM instanceof ForecastModelMSTEsao )
		{
			this.optimizeForecastModelMSTEsao( _bSysPeriod, (ForecastModelMSTEsao)_forecastModelM, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );			
		}
		else if( _forecastModelM instanceof ForecastModelMSTEsmo )
		{
			this.optimizeForecastModelMSTEsmo( _bSysPeriod, (ForecastModelMSTEsmo)_forecastModelM, _hmap4List4GroupedDetailProOrgs, _hmap4Hmap4GroupedHistoryData );			
		}		
	}


	

	// forecastinst mappingmodel run end

	// forecastmodelm 算法 begin
	
	private HashMap<Integer, Long> forecastModelMRunFormula4LMa( ForecastModelMLMa _forecastModelMLMa, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = ( FV(i-n)+FV(i-n+1)+...+FV(i-1) )/ n
		// FV(k) = HV(k) for k<0

		// 模型参数 begin
		int dataPeriodNum = _forecastModelMLMa.getDataPeriodNum();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long[] arr4DependData = new Long[dataPeriodNum+1];
		// 使用的数据的定义 end

		// 下面的算法很傻，为了简单易懂，且与类似算法保持思路一致 
		
		// 数据初始化 begin
		for( int i=1; i<=dataPeriodNum; i=i+1 )
		{
			Long historyData4Last_i = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0-i ) );
			if( historyData4Last_i == null )
			{
				historyData4Last_i = 0L;
			}	
			arr4DependData[i] = historyData4Last_i;
		}
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			Long sum = 0L;
			for( int j=1; j<=dataPeriodNum; j++ )
			{
				sum = sum + arr4DependData[j];
			}
			
			Long forecastData = Math.round( sum * 1.0 / dataPeriodNum );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			for( int j=dataPeriodNum; j>=2; j-- )
			{
				arr4DependData[j] = arr4DependData[j-1];
			}
			arr4DependData[1] = forecastData;
		}
		
		return rstHashMap4ForecastData;
	}	
	
	private HashMap<Integer, Long> forecastModelMRunFormula4LWma( ForecastModelMLWma _forecastModelMLWma, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-n)*C(n) + FV(i-n+1)*C(n-1) + ... + FV(i-1)*C(1)
		// FV(k) = HV(k) for k<0

		// 模型参数 begin
		int dataPeriodNum = _forecastModelMLWma.getDataPeriodNum();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long[] arr4DependData = new Long[dataPeriodNum+1];
		double[] arr4Coefficient = new double[dataPeriodNum+1];
		// 使用的数据的定义 end

		// 数据初始化 begin
		for( int i=1; i<=dataPeriodNum; i=i+1 )
		{
			arr4Coefficient[i] = 0.0;
		}		
		Set<ForecastModelMLWmaItem> set4ForecastModelMLWmaItems = _forecastModelMLWma.getForecastModelMLWmaItems();
		if( set4ForecastModelMLWmaItems != null && !(set4ForecastModelMLWmaItems.isEmpty()) )
		{
			Iterator<ForecastModelMLWmaItem> itr_set4ForecastModelMLWmaItems = set4ForecastModelMLWmaItems.iterator();
			while( itr_set4ForecastModelMLWmaItems.hasNext() )
			{
				ForecastModelMLWmaItem forecastModelMLWmaItem = itr_set4ForecastModelMLWmaItems.next();
				int periodSeqNo = forecastModelMLWmaItem.getPeriodSeqNo();
				if( periodSeqNo >=1 && periodSeqNo <= dataPeriodNum && forecastModelMLWmaItem.getCoefficient() != null )
				{
					
					arr4Coefficient[periodSeqNo] = forecastModelMLWmaItem.getCoefficient().doubleValue();
				}
			}
		}
		
		for( int i=1; i<=dataPeriodNum; i=i+1 )
		{
			Long historyData4Last_i = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0-i ) );
			if( historyData4Last_i == null )
			{
				historyData4Last_i = 0L;
			}	
			arr4DependData[i] = historyData4Last_i;
		}
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			double sum = 0.0;
			for( int j=1; j<=dataPeriodNum; j++ )
			{
				sum = sum + arr4DependData[j] * arr4Coefficient[j];
			}
			
			Long forecastData = Math.round( sum );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			for( int j=dataPeriodNum; j>=2; j-- )
			{
				arr4DependData[j] = arr4DependData[j-1];
			}
			arr4DependData[1] = forecastData;
			
		}
		
		return rstHashMap4ForecastData;
	}	
	
	private HashMap<Integer, Long> forecastModelMRunFormula4LEs( ForecastModelMLEs _forecastModelMLEs, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)

		// 模型参数 begin
		int initDataPeriodNum = _forecastModelMLEs.getInitDataPeriodNum();
		double alpha = _forecastModelMLEs.getSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData_Last_2 = 0L;
		Long forecastData_Last_1 = 0L;
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData_Last_2 = 0L;
		for( int i=-initDataPeriodNum; i<=-2; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			forecastData_Last_2 = forecastData_Last_2 + historyDataValue;
		}	
		
		forecastData_Last_1 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-1; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			forecastData_Last_1 = forecastData_Last_1 + historyDataValue;
		}		
		
		forecastData_Last_2 = Math.round( forecastData_Last_2 * 1.0 /(initDataPeriodNum-1) );
		forecastData_Last_1 = Math.round( forecastData_Last_1 * 1.0 /(initDataPeriodNum-1) );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算

			Long forecastData = Math.round( forecastData_Last_2 * (1.0-alpha) + forecastData_Last_1 * 1.0 * alpha );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			forecastData_Last_2 = forecastData_Last_1;
			forecastData_Last_1 = forecastData;
			
		}
		
		return rstHashMap4ForecastData;
	}		
	
	private HashMap<Integer, Long> forecastModelMRunFormula4LEso( ForecastModelMLEso _forecastModelMLEso, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)

		// 模型参数 begin
		int initDataPeriodNum = _forecastModelMLEso.getInitDataPeriodNum();
		double alpha = _forecastModelMLEso.getSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData_Last_2 = 0L;
		Long forecastData_Last_1 = 0L;
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData_Last_2 = 0L;
		for( int i=-initDataPeriodNum; i<=-2; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			forecastData_Last_2 = forecastData_Last_2 + historyDataValue;
		}	
		
		forecastData_Last_1 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-1; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			forecastData_Last_1 = forecastData_Last_1 + historyDataValue;
		}		
		
		forecastData_Last_2 = Math.round( forecastData_Last_2 * 1.0 /(initDataPeriodNum-1) );
		forecastData_Last_1 = Math.round( forecastData_Last_1 * 1.0 /(initDataPeriodNum-1) );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算

			Long forecastData = Math.round( forecastData_Last_2 * (1.0-alpha) + forecastData_Last_1 * 1.0 * alpha );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			forecastData_Last_2 = forecastData_Last_1;
			forecastData_Last_1 = forecastData;
			
		}
		
		return rstHashMap4ForecastData;
	}		
	
	private HashMap<Integer, Long> forecastModelMRunFormula4TPly( ForecastModelMTPly _forecastModelMTPly, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// -periodNumPerYear, ..., (_periodNum4Forecast-1-periodNumPerYear), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* percentValue 
		// FV(k) = HV(k)  for k<0


		// 模型参数 begin
		double percentValue = _forecastModelMTPly.getPercentValue();
		// 模型参数 end

		// 使用的数据的定义 begin
		// 使用的数据的定义 end

		// 数据初始化 begin
		// 数据初始化 end

		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			Long forecastData4LastYearPeriod = 0L;
			if( i< periodNumPerYear )
			{
				Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( historyDataValue != null )
				{
					forecastData4LastYearPeriod = historyDataValue;
				}
			}
			else
			{
				Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( forecastDataValue != null )
				{
					forecastData4LastYearPeriod = forecastDataValue;
				}				
			}
			
			Long forecastData = Math.round( forecastData4LastYearPeriod * 1.0 * percentValue * 0.01 );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据			
		}
		
		return rstHashMap4ForecastData;
	}		
	
	private HashMap<Integer, Long> forecastModelMRunFormula4TCply( ForecastModelMTCply _forecastModelMTCply, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -periodNumPerYear-n, ..., (_periodNum4Forecast-1-periodNumPerYear-n), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* ( FV(i-n) + ... + FV(i-1) ) / ( FV(i-n-periodNumPerYear) + ... + FV(i-1-periodNumPerYear) ) 
		// FV(k) = HV(k)  for k<0

		// 模型参数 begin
		int dataPeriodNum = _forecastModelMTCply.getDataPeriodNum();
		// 模型参数 end

		// 使用的数据的定义 begin
		// 使用的数据的定义 end

		// 数据初始化 begin
		// 数据初始化 end
		
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear(); 

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			
			Long forecastData4LastYearPeriod = 0L;
			if( i >= periodNumPerYear )
			{
				Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( forecastDataValue != null )
				{
					forecastData4LastYearPeriod = forecastDataValue;
				}								
			}
			else
			{
				Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( historyDataValue != null )
				{
					forecastData4LastYearPeriod = historyDataValue;
				}
			}
			
			Long sum_ThisYear = 0L;
			Long sum_LastYear = 0L;
			for( int j=1; j<=dataPeriodNum; j=j+1 )
			{
				if( i-j >= 0 )
				{
					Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j ) );
					if( forecastDataValue != null )
					{
						sum_ThisYear = sum_ThisYear + forecastDataValue;
					}					
				}
				else
				{
					Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j ) );
					if( historyDataValue != null )
					{
						sum_ThisYear = sum_ThisYear + historyDataValue;
					}					
				}
				
				if( i-j-periodNumPerYear >= 0 )
				{
					Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j-periodNumPerYear ) );
					if( forecastDataValue != null )
					{
						sum_LastYear = sum_LastYear + forecastDataValue;
					}					
				}
				else
				{
					Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j-periodNumPerYear) );
					if( historyDataValue != null )
					{
						sum_LastYear = sum_LastYear + historyDataValue;
					}					
				}				
			}
			
			Long forecastData = 0L;
			if( sum_LastYear.longValue() == 0 )
			{
				forecastData = forecastData4LastYearPeriod;
			}
			else
			{
				forecastData = Math.round( forecastData4LastYearPeriod * 1.0 * sum_ThisYear / sum_LastYear );
			} 

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据			
		}
		
		return rstHashMap4ForecastData;
	}	
	
	private HashMap<Integer, Long> forecastModelMRunFormula4TEs( ForecastModelMTEs _forecastModelMTEs, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		// 模型参数 begin
		Double levelSmoothingFactor = _forecastModelMTEs.getLevelSmoothingFactor();	// alpha
		Double trendSmoothingFactor = _forecastModelMTEs.getTrendSmoothingFactor();// beta
		Double trendDampingFactor = _forecastModelMTEs.getTrendDampingFactor(); // deta
		if( _forecastModelMTEs.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		int initDataPeriodNum = _forecastModelMTEs.getInitDataPeriodNum(); // n
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData4Last3 = null;
		Long forecastData4Last2 = null;
		Long forecastData4Last1 = null;
		Long lastEstimatedTrend = null;
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData4Last3 = 0L;
		for( int i=-initDataPeriodNum; i<=-3; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{	
				forecastData4Last3 = forecastData4Last3 + historyDataValue;
			}				
		}	
		forecastData4Last3 = Math.round( forecastData4Last3 * 1.0 / (initDataPeriodNum-2) );
		
		
		forecastData4Last2 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-2; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{				
				forecastData4Last2 = forecastData4Last2 + historyDataValue;
			}				
		}
		forecastData4Last2 = Math.round( forecastData4Last2 * 1.0 / (initDataPeriodNum-2) );
		
		
		
		forecastData4Last1 = 0L;
		for( int i=-initDataPeriodNum+2; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{				
				forecastData4Last1 = forecastData4Last1 + historyDataValue;
			}				
		}	
		forecastData4Last1 = Math.round( forecastData4Last1*1.0/(initDataPeriodNum-2) );	
		
		
		lastEstimatedTrend = 0L;
		for( int i=-initDataPeriodNum; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{				
				lastEstimatedTrend = lastEstimatedTrend + historyDataValue;
			}				
		}		
		Long historyDataLast_1 = 0L;
		historyDataLast_1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 ) );
		if( historyDataLast_1 == null )
		{
			historyDataLast_1 = 0L;
		}
		lastEstimatedTrend = historyDataLast_1 - Math.round( lastEstimatedTrend*1.0 / initDataPeriodNum );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta			
			Double estimatedTrend = ( (1-trendSmoothingFactor) * ( 
																	(1-levelSmoothingFactor) * (forecastData4Last2-forecastData4Last3) + 
																		levelSmoothingFactor * (forecastData4Last1-forecastData4Last2)
																 ) + 
										  trendSmoothingFactor * lastEstimatedTrend ) 
									* trendDampingFactor;
			
			// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)
			Long forecastData = Math.round( (1-levelSmoothingFactor) * forecastData4Last2 + levelSmoothingFactor * forecastData4Last1 + estimatedTrend );

			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}
			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 准备下一期要使用的数据
			forecastData4Last3 = forecastData4Last2;
			forecastData4Last2 = forecastData4Last1;
			forecastData4Last1 = forecastData;
			lastEstimatedTrend = Math.round( estimatedTrend );
		}
		return rstHashMap4ForecastData;
	}	
	
	private HashMap<Integer, Long> forecastModelMRunFormula4TEso( ForecastModelMTEso _forecastModelMTEso, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		// 模型参数 begin
		Double levelSmoothingFactor = _forecastModelMTEso.getLevelSmoothingFactor();	// alpha
		Double trendSmoothingFactor = _forecastModelMTEso.getTrendSmoothingFactor();// beta
		Double trendDampingFactor = _forecastModelMTEso.getTrendDampingFactor(); // deta
		if( _forecastModelMTEso.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		int initDataPeriodNum = _forecastModelMTEso.getInitDataPeriodNum(); // n
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData4Last3 = null;
		Long forecastData4Last2 = null;
		Long forecastData4Last1 = null;
		Long lastEstimatedTrend = null;
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData4Last3 = 0L;
		for( int i=-initDataPeriodNum; i<=-3; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{	
				forecastData4Last3 = forecastData4Last3 + historyDataValue;
			}				
		}	
		forecastData4Last3 = Math.round( forecastData4Last3 * 1.0 / (initDataPeriodNum-2) );
		
		
		forecastData4Last2 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-2; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{				
				forecastData4Last2 = forecastData4Last2 + historyDataValue;
			}				
		}
		forecastData4Last2 = Math.round( forecastData4Last2 * 1.0 / (initDataPeriodNum-2) );
		
		
		
		forecastData4Last1 = 0L;
		for( int i=-initDataPeriodNum+2; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{				
				forecastData4Last1 = forecastData4Last1 + historyDataValue;
			}				
		}	
		forecastData4Last1 = Math.round( forecastData4Last1*1.0/(initDataPeriodNum-2) );	
		
		
		lastEstimatedTrend = 0L;
		for( int i=-initDataPeriodNum; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{				
				lastEstimatedTrend = lastEstimatedTrend + historyDataValue;
			}				
		}		
		Long historyDataLast_1 = 0L;
		historyDataLast_1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 ) );
		if( historyDataLast_1 == null )
		{
			historyDataLast_1 = 0L;
		}
		lastEstimatedTrend = historyDataLast_1 - Math.round( lastEstimatedTrend*1.0 / initDataPeriodNum );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta			
			Double estimatedTrend = ( (1-trendSmoothingFactor) * ( 
																	(1-levelSmoothingFactor) * (forecastData4Last2-forecastData4Last3) + 
																		levelSmoothingFactor * (forecastData4Last1-forecastData4Last2)
																 ) + 
										  trendSmoothingFactor * lastEstimatedTrend ) 
									* trendDampingFactor;
			
			// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)
			Long forecastData = Math.round( (1-levelSmoothingFactor) * forecastData4Last2 + levelSmoothingFactor * forecastData4Last1 + estimatedTrend );

			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}
			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 准备下一期要使用的数据
			forecastData4Last3 = forecastData4Last2;
			forecastData4Last2 = forecastData4Last1;
			forecastData4Last1 = forecastData;
			lastEstimatedTrend = Math.round( estimatedTrend );
		}
		return rstHashMap4ForecastData;
	}		
	
	private HashMap<Integer, Long> forecastModelMRunFormula4TLa( ForecastModelMTLa _forecastModelMTLa, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -n, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为预测开始月, 即periodBegin
		// FV(i) = FV(i-1) + increment*deta^(i+1)
		// incrementValue = (HV(-1) - HV(-n))/(n-1)
		// FV(-1) = HV(-1)
		
		// 模型参数 begin
		int dataPeriodNum = _forecastModelMTLa.getDataPeriodNum();
		Double trendDampingFactor = _forecastModelMTLa.getTrendDampingFactor();
		if( _forecastModelMTLa.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		// 模型参数 end

		// 使用的数据的定义 begin
		// 使用的数据的定义 end

		// 数据初始化 begin
		Long historyData4Lastn = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0 - dataPeriodNum ) );
		if( historyData4Lastn == null )
		{
			historyData4Lastn = 0L;
		}
		Long historyData4Last1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0 - 1 ) );
		if( historyData4Last1 == null )
		{
			historyData4Last1 = 0L;
		}

		Long incrementValue = Math.round( (historyData4Last1 - historyData4Lastn) * 1.0 / (dataPeriodNum - 1) );
		Long forecastData4Last1 = historyData4Last1;
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			Long forecastData = Math.round( forecastData4Last1 + incrementValue * Math.pow( trendDampingFactor, i + 1 ) );
			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			forecastData4Last1 = forecastData;
		}
		return rstHashMap4ForecastData;
	}
	
	/**
	 * 季节移动平均法
	 * @param _forecastModelMSLMa
	 * @param _periodBegin4Run
	 * @param _periodNum4Run
	 * @param _hmap4HistoryData
	 * @return
	 */
	private HashMap<Integer, Long> forecastModelMRunFormula4SLMa( ForecastModelMSLMa _forecastModelMSLMa, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = ( FV(i-n) + FV(i-n+1) +...+ FV(i-1) )/ n 
		// FV(k) = HV(k) for k<0

		// 模型参数 begin
		int dataPeriodNum = _forecastModelMSLMa.getDataPeriodNum();
		double seasonSmoothingFactor = _forecastModelMSLMa.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long[] arr4DependData = new Long[dataPeriodNum+1];
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}
		// 使用的数据的定义 end

		// 下面的算法很傻，为了简单易懂，且与类似算法保持思路一致 
		
		// 数据初始化 begin		
		for( int i=1; i<=dataPeriodNum; i=i+1 )
		{
			Long historyData4Last_i = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0-i ) );
			if( historyData4Last_i == null )
			{
				historyData4Last_i = 0L;
			}	
			arr4DependData[i] = historyData4Last_i;
		}
		// 数据初始化 end
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			Double sum = 0.0;
			for( int j=1; j<=dataPeriodNum; j++ )
			{
				Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j + periodNumPerYear ) );
				if( seasonIndex == null )
				{
					seasonIndex = 1.0;
				}
				sum = sum + arr4DependData[j] / seasonIndex;
				//sum = sum + arr4DependData[j];
			}
			
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			Long forecastData = Math.round( sum * 1.0 / dataPeriodNum * seasonIndex );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );
			
			System.out.println("fcperiod,"+UtilPeriod.getPeriod( _periodBegin4Run, i )+",fcvalue," + forecastData+",seasonIdex," + seasonIndex);

			// 为下一期准备数据
			for( int j=dataPeriodNum; j>=2; j-- )
			{
				arr4DependData[j] = arr4DependData[j-1];
			}
			arr4DependData[1] = forecastData;
		}
		
		return rstHashMap4ForecastData;
	}	
	
	private HashMap<Integer, Long> forecastModelMRunFormula4SLWma( ForecastModelMSLWma _forecastModelMSLWma, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-n)*C(n) + FV(i-n+1)*C(n-1) + ... + FV(i-1)*C(1)
		// FV(k) = HV(k) for k<0

		// 模型参数 begin
		int dataPeriodNum = _forecastModelMSLWma.getDataPeriodNum();
		double seasonSmoothingFactor = _forecastModelMSLWma.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long[] arr4DependData = new Long[dataPeriodNum+1];
		double[] arr4Coefficient = new double[dataPeriodNum+1];
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}		
		// 使用的数据的定义 end

		// 数据初始化 begin
		for( int i=1; i<=dataPeriodNum; i=i+1 )
		{
			arr4Coefficient[i] = 0.0;
		}		
		Set<ForecastModelMSLWmaItem> set4ForecastModelMSLWmaItems = _forecastModelMSLWma.getForecastModelMSLWmaItems();
		if( set4ForecastModelMSLWmaItems != null && !(set4ForecastModelMSLWmaItems.isEmpty()) )
		{
			Iterator<ForecastModelMSLWmaItem> itr_set4ForecastModelMSLWmaItems = set4ForecastModelMSLWmaItems.iterator();
			while( itr_set4ForecastModelMSLWmaItems.hasNext() )
			{
				ForecastModelMSLWmaItem forecastModelMSLWmaItem = itr_set4ForecastModelMSLWmaItems.next();
				int periodSeqNo = forecastModelMSLWmaItem.getPeriodSeqNo();
				if( periodSeqNo >=1 && periodSeqNo <= dataPeriodNum && forecastModelMSLWmaItem.getCoefficient() != null )
				{
					
					arr4Coefficient[periodSeqNo] = forecastModelMSLWmaItem.getCoefficient().doubleValue();
				}
			}
		}
		
		for( int i=1; i<=dataPeriodNum; i=i+1 )
		{
			Long historyData4Last_i = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0-i ) );
			if( historyData4Last_i == null )
			{
				historyData4Last_i = 0L;
			}	
			arr4DependData[i] = historyData4Last_i;
		}
		// 数据初始化 end


		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			double sum = 0.0;
			for( int j=1; j<=dataPeriodNum; j++ )
			{
				Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j+periodNumPerYear ) );
				if( seasonIndex == null )
				{
					seasonIndex = 1.0;
				}
				
				sum = sum + arr4DependData[j] / seasonIndex * arr4Coefficient[j];
			}
			
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}						
			Long forecastData = Math.round( sum * seasonIndex );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			for( int j=dataPeriodNum; j>=2; j-- )
			{
				arr4DependData[j] = arr4DependData[j-1];
			}
			arr4DependData[1] = forecastData;
			
		}
		
		return rstHashMap4ForecastData;
	}	
	
	/**
	 * 季节指数平滑法
	 * @param _forecastModelMSLEs
	 * @param _periodBegin4Run
	 * @param _periodNum4Run
	 * @param _hmap4HistoryData
	 * @return
	 */
	private HashMap<Integer, Long> forecastModelMRunFormula4SLEs( ForecastModelMSLEs _forecastModelMSLEs, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)

		// 模型参数 begin
		int initDataPeriodNum = _forecastModelMSLEs.getInitDataPeriodNum();
		double alpha = _forecastModelMSLEs.getSmoothingFactor();
		double seasonSmoothingFactor = _forecastModelMSLEs.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData_Last2 = 0L;
		Long forecastData_Last1 = 0L;
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}		
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData_Last2 = 0L;
		for( int i=-initDataPeriodNum; i<=-2; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear ) );
			if( seasonIndex_historyDataValue == null )
			{
				seasonIndex_historyDataValue = 1.0;
			}	
			historyDataValue = Math.round( historyDataValue/seasonIndex_historyDataValue );
			
			forecastData_Last2 = forecastData_Last2 + historyDataValue;
		}	
		Double seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -2  ) );
		if( seasonIndex_Last2 == null )
		{
			seasonIndex_Last2 = 1.0;
		}	
		forecastData_Last2 = Math.round( forecastData_Last2 * 1.0 /(initDataPeriodNum-1) * seasonIndex_Last2 );
		
		forecastData_Last1 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-1; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear ) );
			if( seasonIndex_historyDataValue == null )
			{
				seasonIndex_historyDataValue = 1.0;
			}	
			historyDataValue = Math.round( historyDataValue/seasonIndex_historyDataValue );
			
			forecastData_Last1 = forecastData_Last1 + historyDataValue;		
		}		
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}		
		forecastData_Last1 = Math.round( forecastData_Last1 * 1.0 /(initDataPeriodNum-1) * seasonIndex_Last1 );
			
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i - 2+periodNumPerYear ) );
			if( seasonIndex_Last2 == null )
			{
				seasonIndex_Last2 = 1.0;
			}
			
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i - 1+periodNumPerYear ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}	
			
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			Long forecastData = Math.round( ( forecastData_Last2 / seasonIndex_Last2 * (1.0-alpha) + forecastData_Last1 / seasonIndex_Last1 * 1.0 * alpha ) * seasonIndex ) ;

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			forecastData_Last2 = forecastData_Last1;
			forecastData_Last1 = forecastData;
			
		}
		
		return rstHashMap4ForecastData;
	}	
	
	/** 自适应季节指数平滑法 */
	private HashMap<Integer, Long> forecastModelMRunFormula4SLEso( ForecastModelMSLEso _forecastModelMSLEso, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)

		// 模型参数 begin
		int initDataPeriodNum = _forecastModelMSLEso.getInitDataPeriodNum();
		double alpha = _forecastModelMSLEso.getSmoothingFactor();
		double seasonSmoothingFactor = _forecastModelMSLEso.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData_Last2 = 0L;
		Long forecastData_Last1 = 0L;
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}		
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData_Last2 = 0L;
		for( int i=-initDataPeriodNum; i<=-2; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear ) );
			if( seasonIndex_historyDataValue == null )
			{
				seasonIndex_historyDataValue = 1.0;
			}	
			historyDataValue = Math.round( historyDataValue/seasonIndex_historyDataValue );
			
			forecastData_Last2 = forecastData_Last2 + historyDataValue;
		}	
		Double seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -2  ) );
		if( seasonIndex_Last2 == null )
		{
			seasonIndex_Last2 = 1.0;
		}	
		forecastData_Last2 = Math.round( forecastData_Last2 * 1.0 /(initDataPeriodNum-1) * seasonIndex_Last2 );
		
		forecastData_Last1 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-1; i=i+1 )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue == null )
			{
				historyDataValue = 0L;
			}
			Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear ) );
			if( seasonIndex_historyDataValue == null )
			{
				seasonIndex_historyDataValue = 1.0;
			}	
			historyDataValue = Math.round( historyDataValue/seasonIndex_historyDataValue );
			
			forecastData_Last1 = forecastData_Last1 + historyDataValue;		
		}		
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}		
		forecastData_Last1 = Math.round( forecastData_Last1 * 1.0 /(initDataPeriodNum-1) * seasonIndex_Last1 );
			
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i - 2 ) );
			if( seasonIndex_Last2 == null )
			{
				seasonIndex_Last2 = 1.0;
			}
			
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i - 1 ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}	
			
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			Long forecastData = Math.round( ( forecastData_Last2 / seasonIndex_Last2 * (1.0-alpha) + forecastData_Last1 / seasonIndex_Last1 * 1.0 * alpha ) * seasonIndex ) ;

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			forecastData_Last2 = forecastData_Last1;
			forecastData_Last1 = forecastData;
			
		}
		
		return rstHashMap4ForecastData;
	}		
	
	/** 季节一元线性回归法 */
	private HashMap<Integer, Long> forecastModelMRunFormula4STPly( ForecastModelMSTPly _forecastModelMSTPly, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// -periodNumPerYear, ..., (_periodNum4Forecast-1-periodNumPerYear), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* percentValue 
		// FV(k) = HV(k)  for k<0


		// 模型参数 begin
		double percentValue = _forecastModelMSTPly.getPercentValue();
		double seasonSmoothingFactor = _forecastModelMSTPly.getSeasonSmoothingFactor();		
		// 模型参数 end

		// 使用的数据的定义 begin
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		// 数据初始化 end
		
		
		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			Long forecastData4LastYearPeriod = 0L;
			if( i< periodNumPerYear )
			{
				Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( historyDataValue != null )
				{
					forecastData4LastYearPeriod = historyDataValue;
				}
			}
			else
			{
				Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( forecastDataValue != null )
				{
					forecastData4LastYearPeriod = forecastDataValue;
				}				
			}
			
			Double seasonIndex4LastYearPeriod = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-periodNumPerYear  ) );
			if( seasonIndex4LastYearPeriod == null )
			{
				seasonIndex4LastYearPeriod = 1.0;
			}	
			
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}	
			
			Long forecastData = Math.round( forecastData4LastYearPeriod / seasonIndex4LastYearPeriod* 1.0 * percentValue * 0.01 * seasonIndex );

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据			
		}
		
		return rstHashMap4ForecastData;
	}		
	
	/** 季节已计去年百分比法 */
	private HashMap<Integer, Long> forecastModelMRunFormula4STCply( ForecastModelMSTCply _forecastModelMSTCply, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -periodNumPerYear-n, ..., (_periodNum4Forecast-1-periodNumPerYear-n), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* ( FV(i-n) + ... + FV(i-1) ) / ( FV(i-n-periodNumPerYear) + ... + FV(i-1-periodNumPerYear) ) 
		// FV(k) = HV(k)  for k<0

		// 模型参数 begin
		int dataPeriodNum = _forecastModelMSTCply.getDataPeriodNum();
		double seasonSmoothingFactor = _forecastModelMSTCply.getSeasonSmoothingFactor();	
		// 模型参数 end

		// 使用的数据的定义 begin
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		// 数据初始化 end
		
		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			
			Long forecastData4LastYearPeriod = 0L;
			if( i >= periodNumPerYear )
			{
				Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( forecastDataValue != null )
				{
					forecastData4LastYearPeriod = forecastDataValue;
				}								
			}
			else
			{
				Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i - periodNumPerYear ) );
				if( historyDataValue != null )
				{
					forecastData4LastYearPeriod = historyDataValue;
				}
			}			
			
			Long sum_ThisYear = 0L;
			Long sum_LastYear = 0L;
			for( int j=1; j<=dataPeriodNum; j=j+1 )
			{
				Double seasonIndex_ThisYear = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j+periodNumPerYear  ) );
				if( seasonIndex_ThisYear == null )
				{
					seasonIndex_ThisYear = 1.0;
				}	
				
				if( i-j >= 0 )
				{
					Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j ) );
					if( forecastDataValue != null )
					{
						sum_ThisYear = Math.round( sum_ThisYear + forecastDataValue / seasonIndex_ThisYear );
					}					
				}
				else
				{
					Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j ) );
					if( historyDataValue != null )
					{
						sum_ThisYear = Math.round( sum_ThisYear + historyDataValue / seasonIndex_ThisYear );
					}					
				}
				
				Double seasonIndex_LastYear = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j  ) );
				if( seasonIndex_LastYear == null )
				{
					seasonIndex_LastYear = 1.0;
				}
				
				if( i-j-periodNumPerYear >= 0 )
				{
					Long forecastDataValue = rstHashMap4ForecastData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j-periodNumPerYear ) );
					if( forecastDataValue != null )
					{
						sum_LastYear = Math.round( sum_LastYear + forecastDataValue / seasonIndex_LastYear );
					}					
				}
				else
				{
					Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-j-periodNumPerYear) );
					if( historyDataValue != null )
					{
						sum_LastYear = Math.round( sum_LastYear + historyDataValue / seasonIndex_LastYear );						
					}					
				}				
			}
			
			Long forecastData = 0L;
			if( sum_LastYear.longValue() == 0 )
			{
				forecastData = forecastData4LastYearPeriod;
			}
			else
			{
				Double seasonIndex_ThisYear = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_ThisYear == null )
				{
					seasonIndex_ThisYear = 1.0;
				}	
				
				Double seasonIndex_LastYear = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i  ) );
				if( seasonIndex_LastYear == null )
				{
					seasonIndex_LastYear = 1.0;
				}				
				
				forecastData = Math.round( forecastData4LastYearPeriod / seasonIndex_LastYear * 1.0 * sum_ThisYear / sum_LastYear * seasonIndex_ThisYear );
			} 

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据			
		}
		
		return rstHashMap4ForecastData;
	}	
	
	/** 季节趋势指数平滑法 （叠加法）*/
	private HashMap<Integer, Long> forecastModelMRunFormula4STEsa( ForecastModelMSTEsa _forecastModelMSTEsa, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) ) * seasonIndex + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		// 模型参数 begin
		Double levelSmoothingFactor = _forecastModelMSTEsa.getLevelSmoothingFactor();	// alpha
		Double trendSmoothingFactor = _forecastModelMSTEsa.getTrendSmoothingFactor();// beta
		Double trendDampingFactor = _forecastModelMSTEsa.getTrendDampingFactor(); // deta
		if( _forecastModelMSTEsa.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		int initDataPeriodNum = _forecastModelMSTEsa.getInitDataPeriodNum(); // n
		double seasonSmoothingFactor = _forecastModelMSTEsa.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData4Last3 = null;
		Long forecastData4Last2 = null;
		Long forecastData4Last1 = null;
		Long lastEstimatedTrend = null;

		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		
		forecastData4Last3 = 0L;
		for( int i=-initDataPeriodNum; i<=-3; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last3 = forecastData4Last3 + historyDataValue;
			}				
		}
		Double seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -3+periodNumPerYear  ) );
		if( seasonIndex_Last3 == null )
		{
			seasonIndex_Last3 = 1.0;
		}		
		forecastData4Last3 = Math.round( forecastData4Last3 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last3 );
		
		
		forecastData4Last2 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-2; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last2 = forecastData4Last2 + historyDataValue;
			}				
		}
		Double seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -2+periodNumPerYear  ) );
		if( seasonIndex_Last2 == null )
		{
			seasonIndex_Last2 = 1.0;
		}
		forecastData4Last2 = Math.round( forecastData4Last2 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last2 );
		
		
		
		forecastData4Last1 = 0L;
		for( int i=-initDataPeriodNum+2; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last1 = forecastData4Last1 + historyDataValue;
			}				
		}	
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}	
		forecastData4Last1 = Math.round( forecastData4Last1*1.0/(initDataPeriodNum-2) * seasonIndex_Last1 );	
		
		
		lastEstimatedTrend = 0L;
		for( int i=-initDataPeriodNum; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				lastEstimatedTrend = lastEstimatedTrend + historyDataValue;
			}				
		}		
		Long historyDataLast_1 = 0L;
		historyDataLast_1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 ) );
		if( historyDataLast_1 == null )
		{
			historyDataLast_1 = 0L;
		}
		seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}
		historyDataLast_1 = Math.round( historyDataLast_1/seasonIndex_Last1 );
		lastEstimatedTrend = historyDataLast_1 - Math.round( lastEstimatedTrend*1.0 / initDataPeriodNum );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
	
			seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-3+periodNumPerYear  ) );
			if( seasonIndex_Last3 == null )
			{
				seasonIndex_Last3 = 1.0;
			}		
			seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-2+periodNumPerYear  ) );
			if( seasonIndex_Last2 == null )
			{
				seasonIndex_Last2 = 1.0;
			}	
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-1+periodNumPerYear  ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}	
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
			Double estimatedTrend = ( (1-trendSmoothingFactor) * ( 
																	(1-levelSmoothingFactor) * (forecastData4Last2/seasonIndex_Last2-forecastData4Last3/seasonIndex_Last3) + 
																		levelSmoothingFactor * (forecastData4Last1/seasonIndex_Last1-forecastData4Last2/seasonIndex_Last2)
																 ) + 
										  trendSmoothingFactor * lastEstimatedTrend ) 
									* trendDampingFactor;
			
			// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)
			Long forecastData = Math.round( ( (1-levelSmoothingFactor) * forecastData4Last2/seasonIndex_Last2 + levelSmoothingFactor * forecastData4Last1/seasonIndex_Last1 ) * seasonIndex + estimatedTrend );

			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}
			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 准备下一期要使用的数据
			forecastData4Last3 = forecastData4Last2;
			forecastData4Last2 = forecastData4Last1;
			forecastData4Last1 = forecastData;
			lastEstimatedTrend = Math.round( estimatedTrend );
		}
		return rstHashMap4ForecastData;
	}	
		
	/** 自适应季节趋势指数平滑法 （叠加法） */
	private HashMap<Integer, Long> forecastModelMRunFormula4STEsao( ForecastModelMSTEsao _forecastModelMSTEsao, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) ) * seasonIndex + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		// 模型参数 begin
		Double levelSmoothingFactor = _forecastModelMSTEsao.getLevelSmoothingFactor();	// alpha
		Double trendSmoothingFactor = _forecastModelMSTEsao.getTrendSmoothingFactor();// beta
		Double trendDampingFactor = _forecastModelMSTEsao.getTrendDampingFactor(); // deta
		if( _forecastModelMSTEsao.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		int initDataPeriodNum = _forecastModelMSTEsao.getInitDataPeriodNum(); // n
		double seasonSmoothingFactor = _forecastModelMSTEsao.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData4Last3 = null;
		Long forecastData4Last2 = null;
		Long forecastData4Last1 = null;
		Long lastEstimatedTrend = null;

		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData4Last3 = 0L;
		for( int i=-initDataPeriodNum; i<=-3; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last3 = forecastData4Last3 + historyDataValue;
			}				
		}
		Double seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -3 +periodNumPerYear ) );
		if( seasonIndex_Last3 == null )
		{
			seasonIndex_Last3 = 1.0;
		}		
		forecastData4Last3 = Math.round( forecastData4Last3 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last3 );
		
		
		forecastData4Last2 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-2; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last2 = forecastData4Last2 + historyDataValue;
			}				
		}
		Double seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -2+periodNumPerYear  ) );
		if( seasonIndex_Last2 == null )
		{
			seasonIndex_Last2 = 1.0;
		}
		forecastData4Last2 = Math.round( forecastData4Last2 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last2 );
		
		
		
		forecastData4Last1 = 0L;
		for( int i=-initDataPeriodNum+2; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last1 = forecastData4Last1 + historyDataValue;
			}				
		}	
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}	
		forecastData4Last1 = Math.round( forecastData4Last1*1.0/(initDataPeriodNum-2) * seasonIndex_Last1 );	
		
		
		lastEstimatedTrend = 0L;
		for( int i=-initDataPeriodNum; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				lastEstimatedTrend = lastEstimatedTrend + historyDataValue;
			}				
		}		
		Long historyDataLast_1 = 0L;
		historyDataLast_1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 ) );
		if( historyDataLast_1 == null )
		{
			historyDataLast_1 = 0L;
		}
		seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}
		historyDataLast_1 = Math.round( historyDataLast_1/seasonIndex_Last1 );		
		lastEstimatedTrend = historyDataLast_1 - Math.round( lastEstimatedTrend*1.0 / initDataPeriodNum );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
	
			seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-3+periodNumPerYear  ) );
			if( seasonIndex_Last3 == null )
			{
				seasonIndex_Last3 = 1.0;
			}		
			seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-2+periodNumPerYear  ) );
			if( seasonIndex_Last2 == null )
			{
				seasonIndex_Last2 = 1.0;
			}	
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-1+periodNumPerYear  ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}	
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
			Double estimatedTrend = ( (1-trendSmoothingFactor) * ( 
																	(1-levelSmoothingFactor) * (forecastData4Last2/seasonIndex_Last2-forecastData4Last3/seasonIndex_Last3) + 
																		levelSmoothingFactor * (forecastData4Last1/seasonIndex_Last1-forecastData4Last2/seasonIndex_Last2)
																 ) + 
										  trendSmoothingFactor * lastEstimatedTrend ) 
									* trendDampingFactor;
			
			// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)
			Long forecastData = Math.round( ( (1-levelSmoothingFactor) * forecastData4Last2/seasonIndex_Last2 + levelSmoothingFactor * forecastData4Last1/seasonIndex_Last1 ) * seasonIndex + estimatedTrend );

			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}
			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 准备下一期要使用的数据
			forecastData4Last3 = forecastData4Last2;
			forecastData4Last2 = forecastData4Last1;
			forecastData4Last1 = forecastData;
			lastEstimatedTrend = Math.round( estimatedTrend );
		}
		return rstHashMap4ForecastData;
	}	
	
	/**季节趋势指数平滑法（相乘法） */
	private HashMap<Integer, Long> forecastModelMRunFormula4STEsm( ForecastModelMSTEsm _forecastModelMSTEsm, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i) ) * seasonIndex 			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		// 模型参数 begin
		Double levelSmoothingFactor = _forecastModelMSTEsm.getLevelSmoothingFactor();	// alpha
		Double trendSmoothingFactor = _forecastModelMSTEsm.getTrendSmoothingFactor();// beta
		Double trendDampingFactor = _forecastModelMSTEsm.getTrendDampingFactor(); // deta
		if( _forecastModelMSTEsm.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		int initDataPeriodNum = _forecastModelMSTEsm.getInitDataPeriodNum(); // n
		double seasonSmoothingFactor = _forecastModelMSTEsm.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData4Last3 = null;
		Long forecastData4Last2 = null;
		Long forecastData4Last1 = null;
		Long lastEstimatedTrend = null;

		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData4Last3 = 0L;
		for( int i=-initDataPeriodNum; i<=-3; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last3 = forecastData4Last3 + historyDataValue;
			}				
		}
		Double seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -3+periodNumPerYear  ) );
		if( seasonIndex_Last3 == null )
		{
			seasonIndex_Last3 = 1.0;
		}		
		forecastData4Last3 = Math.round( forecastData4Last3 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last3 );
		
		
		forecastData4Last2 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-2; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last2 = forecastData4Last2 + historyDataValue;
			}				
		}
		Double seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -2+periodNumPerYear  ) );
		if( seasonIndex_Last2 == null )
		{
			seasonIndex_Last2 = 1.0;
		}
		forecastData4Last2 = Math.round( forecastData4Last2 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last2 );
		
		
		
		forecastData4Last1 = 0L;
		for( int i=-initDataPeriodNum+2; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last1 = forecastData4Last1 + historyDataValue;
			}				
		}	
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}	
		forecastData4Last1 = Math.round( forecastData4Last1*1.0/(initDataPeriodNum-2) * seasonIndex_Last1 );	
		
		
		lastEstimatedTrend = 0L;
		for( int i=-initDataPeriodNum; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				lastEstimatedTrend = lastEstimatedTrend + historyDataValue;
			}				
		}		
		Long historyDataLast_1 = 0L;
		historyDataLast_1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 ) );
		if( historyDataLast_1 == null )
		{
			historyDataLast_1 = 0L;
		}
		seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 +periodNumPerYear ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}
		historyDataLast_1 = Math.round( historyDataLast_1/seasonIndex_Last1 );		
		lastEstimatedTrend = historyDataLast_1 - Math.round( lastEstimatedTrend*1.0 / initDataPeriodNum );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
	
			seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-3 +periodNumPerYear ) );
			if( seasonIndex_Last3 == null )
			{
				seasonIndex_Last3 = 1.0;
			}		
			seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-2 +periodNumPerYear ) );
			if( seasonIndex_Last2 == null )
			{
				seasonIndex_Last2 = 1.0;
			}	
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-1 +periodNumPerYear ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}	
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i +periodNumPerYear ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
			Double estimatedTrend = ( (1-trendSmoothingFactor) * ( 
																	(1-levelSmoothingFactor) * (forecastData4Last2/seasonIndex_Last2-forecastData4Last3/seasonIndex_Last3) + 
																		levelSmoothingFactor * (forecastData4Last1/seasonIndex_Last1-forecastData4Last2/seasonIndex_Last2)
																 ) + 
										  trendSmoothingFactor * lastEstimatedTrend ) 
									* trendDampingFactor;
			
			// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)
			Long forecastData = Math.round( ( (1-levelSmoothingFactor) * forecastData4Last2/seasonIndex_Last2 + levelSmoothingFactor * forecastData4Last1/seasonIndex_Last1 + estimatedTrend ) * seasonIndex );

			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}
			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 准备下一期要使用的数据
			forecastData4Last3 = forecastData4Last2;
			forecastData4Last2 = forecastData4Last1;
			forecastData4Last1 = forecastData;
			lastEstimatedTrend = Math.round( estimatedTrend );
		}
		return rstHashMap4ForecastData;
	}		
		
	/** 自适应季节趋势指数平滑法（相乘法） */
	private HashMap<Integer, Long> forecastModelMRunFormula4STEsmo( ForecastModelMSTEsmo _forecastModelMSTEsmo, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i) ) * seasonIndex 			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		// 模型参数 begin
		Double levelSmoothingFactor = _forecastModelMSTEsmo.getLevelSmoothingFactor();	// alpha
		Double trendSmoothingFactor = _forecastModelMSTEsmo.getTrendSmoothingFactor();// beta
		Double trendDampingFactor = _forecastModelMSTEsmo.getTrendDampingFactor(); // deta
		if( _forecastModelMSTEsmo.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		int initDataPeriodNum = _forecastModelMSTEsmo.getInitDataPeriodNum(); // n
		double seasonSmoothingFactor = _forecastModelMSTEsmo.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		Long forecastData4Last3 = null;
		Long forecastData4Last2 = null;
		Long forecastData4Last1 = null;
		Long lastEstimatedTrend = null;

		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		forecastData4Last3 = 0L;
		for( int i=-initDataPeriodNum; i<=-3; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last3 = forecastData4Last3 + historyDataValue;
			}				
		}
		Double seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -3+periodNumPerYear  ) );
		if( seasonIndex_Last3 == null )
		{
			seasonIndex_Last3 = 1.0;
		}		
		forecastData4Last3 = Math.round( forecastData4Last3 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last3 );
		
		
		forecastData4Last2 = 0L;
		for( int i=-initDataPeriodNum+1; i<=-2; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last2 = forecastData4Last2 + historyDataValue;
			}				
		}
		Double seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -2+periodNumPerYear  ) );
		if( seasonIndex_Last2 == null )
		{
			seasonIndex_Last2 = 1.0;
		}
		forecastData4Last2 = Math.round( forecastData4Last2 * 1.0 / (initDataPeriodNum-2) * seasonIndex_Last2 );
		
		
		
		forecastData4Last1 = 0L;
		for( int i=-initDataPeriodNum+2; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i +periodNumPerYear ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				forecastData4Last1 = forecastData4Last1 + historyDataValue;
			}				
		}	
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}	
		forecastData4Last1 = Math.round( forecastData4Last1*1.0/(initDataPeriodNum-2) * seasonIndex_Last1 );	
		
		
		lastEstimatedTrend = 0L;
		for( int i=-initDataPeriodNum; i<=-1; i++ )
		{
			Long historyDataValue = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i ) );
			if( historyDataValue != null )
			{
				Double seasonIndex_historyDataValue = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
				if( seasonIndex_historyDataValue == null )
				{
					seasonIndex_historyDataValue = 1.0;
				}	
				historyDataValue = Math.round( historyDataValue / seasonIndex_historyDataValue );
				
				lastEstimatedTrend = lastEstimatedTrend + historyDataValue;
			}				
		}		
		Long historyDataLast_1 = 0L;
		historyDataLast_1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, -1 ) );
		if( historyDataLast_1 == null )
		{
			historyDataLast_1 = 0L;
		}
		seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, -1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}
		historyDataLast_1 = Math.round( historyDataLast_1/seasonIndex_Last1 );		
		lastEstimatedTrend = historyDataLast_1 - Math.round( lastEstimatedTrend*1.0 / initDataPeriodNum );
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
	
			seasonIndex_Last3 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-3 +periodNumPerYear ) );
			if( seasonIndex_Last3 == null )
			{
				seasonIndex_Last3 = 1.0;
			}		
			seasonIndex_Last2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-2 +periodNumPerYear ) );
			if( seasonIndex_Last2 == null )
			{
				seasonIndex_Last2 = 1.0;
			}	
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i-1 +periodNumPerYear ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}	
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
			Double estimatedTrend = ( (1-trendSmoothingFactor) * ( 
																	(1-levelSmoothingFactor) * (forecastData4Last2/seasonIndex_Last2-forecastData4Last3/seasonIndex_Last3) + 
																		levelSmoothingFactor * (forecastData4Last1/seasonIndex_Last1-forecastData4Last2/seasonIndex_Last2)
																 ) + 
										  trendSmoothingFactor * lastEstimatedTrend ) 
									* trendDampingFactor;
			
			// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)
			Long forecastData = Math.round( ( (1-levelSmoothingFactor) * forecastData4Last2/seasonIndex_Last2 + levelSmoothingFactor * forecastData4Last1/seasonIndex_Last1 + estimatedTrend ) * seasonIndex );

			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}
			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 准备下一期要使用的数据
			forecastData4Last3 = forecastData4Last2;
			forecastData4Last2 = forecastData4Last1;
			forecastData4Last1 = forecastData;
			lastEstimatedTrend = Math.round( estimatedTrend );
		}
		return rstHashMap4ForecastData;
	}		
	
	/** 季节线性逼近法 */
	private HashMap<Integer, Long> forecastModelMRunFormula4STLa( ForecastModelMSTLa _forecastModelMSTLa, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=dataPeriodNum
		// -n, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为预测开始月, 即periodBegin
		// FV(i) = FV(i-1) + increment*deta^(i+1)
		// incrementValue = (HV(-1) - HV(-n))/(n-1)
		// FV(-1) = HV(-1)
		
		// 模型参数 begin
		int dataPeriodNum = _forecastModelMSTLa.getDataPeriodNum();
		Double trendDampingFactor = _forecastModelMSTLa.getTrendDampingFactor();
		if( _forecastModelMSTLa.getTrendDampingIsValid() == BizConst.GLOBAL_YESNO_NO )
		{
			trendDampingFactor = 1.0;
		}
		double seasonSmoothingFactor = _forecastModelMSTLa.getSeasonSmoothingFactor();
		// 模型参数 end

		// 使用的数据的定义 begin
		HashMap<Integer,Double> hmap4SeasonIndex = this.getSeasonIndex( _periodBegin4Run, _periodNum4Run, _hmap4HistoryData, seasonSmoothingFactor );
		if( hmap4SeasonIndex == null )
		{
			hmap4SeasonIndex = new HashMap<Integer,Double>();
		}			
		// 使用的数据的定义 end

		// 数据初始化 begin
		Long historyData4Lastn = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0 - dataPeriodNum ) );
		if( historyData4Lastn == null )
		{
			historyData4Lastn = 0L;
		}
		Double seasonIndex_Lastn = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, 0 - dataPeriodNum+periodNumPerYear  ) );
		if( seasonIndex_Lastn == null )
		{
			seasonIndex_Lastn = 1.0;
		}		
		
		
		Long historyData4Last1 = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, 0 - 1 ) );
		if( historyData4Last1 == null )
		{
			historyData4Last1 = 0L;
		}
		Double seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, 0 - 1+periodNumPerYear  ) );
		if( seasonIndex_Last1 == null )
		{
			seasonIndex_Last1 = 1.0;
		}		

		Long incrementValue = Math.round( (historyData4Last1/seasonIndex_Last1 - historyData4Lastn/seasonIndex_Lastn) * 1.0 / (dataPeriodNum - 1) );
		Long forecastData4Last1 = historyData4Last1;
		// 数据初始化 end

		for( int i = 0; i < _periodNum4Run; i = i + 1 )
		{
			// 计算
			seasonIndex_Last1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i - 1+periodNumPerYear  ) );
			if( seasonIndex_Last1 == null )
			{
				seasonIndex_Last1 = 1.0;
			}
			
			Double seasonIndex = hmap4SeasonIndex.get( UtilPeriod.getPeriod( _periodBegin4Run, i+periodNumPerYear  ) );
			if( seasonIndex == null )
			{
				seasonIndex = 1.0;
			}			
			
			Long forecastData = Math.round( ( forecastData4Last1/seasonIndex_Last1 + incrementValue * Math.pow( trendDampingFactor, i + 1 ) ) * seasonIndex );
			if( forecastData.longValue() < 0 )
			{
				forecastData = 0L;
			}

			// 存放
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );

			// 为下一期准备数据
			forecastData4Last1 = forecastData;
		}
		return rstHashMap4ForecastData;
	}	
	
	
	private HashMap<Integer, Long> forecastModelMRunFormula4AAnalog( ForecastModelMAAnalog _forecastModelMAAnalog, int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData )
	{
		HashMap<Integer, Long> rstHashMap4ForecastData = new HashMap<Integer, Long>();

		// 算法说明
		// n=offsetPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (n-1), ..., (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = HV(i-n) 	for 0 <= i <= n-1
		// FV(i) = 0 		for n <= i <= _periodNum4Forecast-1
		// FV(k) = HV(k) for k<0

		// 模型参数 begin
		int offsetPeriodNum = _forecastModelMAAnalog.getOffsetPeriodNum();
		// 模型参数 end

		for( int i = 0; i < offsetPeriodNum; i = i + 1 )
		{
			Long forecastData = _hmap4HistoryData.get( UtilPeriod.getPeriod( _periodBegin4Run, i-offsetPeriodNum ) );
			if( forecastData == null )
			{
				forecastData = 0L;
			}	
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );	
		}
		for( int i = offsetPeriodNum; i < _periodNum4Run-1; i = i + 1 )
		{
			Long forecastData = 0L;
			rstHashMap4ForecastData.put( UtilPeriod.getPeriod( _periodBegin4Run, i ), forecastData );	
		}		
		
		return rstHashMap4ForecastData;
	}		
	
	
	/**
	 * 把所有可能用到的期间的季节指数都算出来
	 * @param _periodBegin4Run 当前期间-容差分析期间的值
	 * @param _periodNum4Run 容差分析期间值
	 * @param _hmap4HistoryData
	 * @param seasonSmoothingFactor
	 * @return
	 */
	private HashMap<Integer, Double> getSeasonIndex(int _periodBegin4Run, int _periodNum4Run, HashMap<Integer, Long> _hmap4HistoryData, double seasonSmoothingFactor)
	{
		HashMap<Integer, Double> hmap4SeasonIndex = new HashMap<Integer, Double>();
		if( UtilPeriod.checkPeriod( _periodBegin4Run ) == false )
		{
			return hmap4SeasonIndex;
		}
		
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		// modify by zhangzy 201809225 季节历史修正，调整起始期间，不再以年初使用，直接用起始期间 period_pno1 = _periodBegin4Run;
//		int year = _periodBegin4Run/100;
//		int period_pno1 = year * 100 + 1;
		
		int period_pno1 = _periodBegin4Run;
		
		// 前面两年的历史期间的季节指数    begin
		Long avg_LastYear_2 = 0L;
		Long avg_LastYear_1 = 0L;		
		for( int i=1; i<=periodNumPerYear; i++ )
		{
			//计算出前2年，每1年的累计值
			int periodHis1Year = UtilPeriod.getPeriod( period_pno1, 0-i );
			Long historyDataValue_LastYear_1 = _hmap4HistoryData.get( periodHis1Year );
			if( historyDataValue_LastYear_1 != null )
			{
				avg_LastYear_1 = avg_LastYear_1 + historyDataValue_LastYear_1;
			}
			
			int periodHis2Year = UtilPeriod.getPeriod( period_pno1, 0-i-periodNumPerYear );
			Long historyDataValue_LastYear_2 = _hmap4HistoryData.get( periodHis2Year );
			if( historyDataValue_LastYear_2 != null )
			{
				avg_LastYear_2 = avg_LastYear_2 + historyDataValue_LastYear_2;
			}			
		}
		avg_LastYear_2 = Math.round( avg_LastYear_2 * 1.0 / periodNumPerYear );
		avg_LastYear_1 = Math.round( avg_LastYear_1 * 1.0 / periodNumPerYear );
		
		for( int i=1; i<=periodNumPerYear; i++ )
		{
			int period4SeasonIndex_LastYear_1 = UtilPeriod.getPeriod( period_pno1, 0-i );
			Long historyDataValue_LastYear_1 = _hmap4HistoryData.get( period4SeasonIndex_LastYear_1 );
			if( historyDataValue_LastYear_1 != null )
			{
				Double seasonIndex = 1.0;
				if( historyDataValue_LastYear_1.longValue() == 0 )
				{
					seasonIndex = 1.0;
				}
				else if( avg_LastYear_1.longValue() == 0 )
				{
					seasonIndex = 1.0;
				}
				else
				{
					seasonIndex = historyDataValue_LastYear_1 * 1.0 / avg_LastYear_1;
				}
				System.out.println("seasonPeriod,"+period4SeasonIndex_LastYear_1+"," + seasonIndex);
				hmap4SeasonIndex.put( period4SeasonIndex_LastYear_1, seasonIndex );
			}
			
			int period4SeasonIndex_LastYear_2 = UtilPeriod.getPeriod( period_pno1, 0-i-periodNumPerYear );
			Long historyDataValue_LastYear_2 = _hmap4HistoryData.get( period4SeasonIndex_LastYear_2 );
			if( historyDataValue_LastYear_2 != null )
			{
				Double seasonIndex = 1.0;
				if( historyDataValue_LastYear_2.longValue() == 0 )
				{
					seasonIndex = 1.0;
				}
				else if( avg_LastYear_2.longValue() == 0 )
				{
					seasonIndex = 1.0;
				}
				else
				{
					seasonIndex = historyDataValue_LastYear_2 * 1.0 / avg_LastYear_2;
				}
				
				System.out.println("seasonPeriod,"+period4SeasonIndex_LastYear_2+"," + seasonIndex);
				hmap4SeasonIndex.put( period4SeasonIndex_LastYear_2, seasonIndex );
			}
		}	
		// 前面两年的历史期间的季节指数    end
		int periodNum4smoothing = UtilPeriod.getPeriodDifference( period_pno1, _periodBegin4Run ) + _periodNum4Run + periodNumPerYear;
		for( int i=0; i<periodNum4smoothing; i++ )
		{
			int period4SeasonIndex = UtilPeriod.getPeriod( period_pno1, i );
			
			Double seasonIndex_LastYear_1 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( period_pno1, i%periodNumPerYear-periodNumPerYear ) );
			if( seasonIndex_LastYear_1 == null )
			{
				seasonIndex_LastYear_1 = 1.0;
			}
			Double seasonIndex_LastYear_2 = hmap4SeasonIndex.get( UtilPeriod.getPeriod( period_pno1, i%periodNumPerYear-2*periodNumPerYear ) );
			if( seasonIndex_LastYear_2 == null )
			{
				seasonIndex_LastYear_2 = 1.0;
			}
			
			Double seasonIndex = (1-seasonSmoothingFactor)*seasonIndex_LastYear_2 + seasonSmoothingFactor*seasonIndex_LastYear_1;
			
			System.out.println("seasonPeriod,"+period4SeasonIndex+"," + seasonIndex);
			hmap4SeasonIndex.put( period4SeasonIndex, seasonIndex );
		}
		
		return hmap4SeasonIndex;		
	}

	private List<Integer> getPeriodScope4HistoryData4ForecastModelMLMa( BSysPeriod _bSysPeriod, ForecastModelMLMa _forecastModelMLMa )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMLMa.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = ( FV(i-n)+FV(i-n+1)+...+FV(i-1) )/ n
		// FV(k) = HV(k) for k<0
			 
		int dataPeriodNum = _forecastModelMLMa.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMLWma( BSysPeriod _bSysPeriod, ForecastModelMLWma _forecastModelMLWma )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMLWma.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = ( FV(i-n)*C(n) + FV(i-n+1)*C(n-1) + ... + FV(i-1)*C(1) )/ n
		// FV(k) = HV(k) for k<0
		 
		int dataPeriodNum = _forecastModelMLWma.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMLEs( BSysPeriod _bSysPeriod, ForecastModelMLEs _forecastModelMLEs )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMLEs.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)
		
		int initDataPeriodNum = _forecastModelMLEs.getInitDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMLEso( BSysPeriod _bSysPeriod, ForecastModelMLEso _forecastModelMLEso )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMLEso.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)
		
		int initDataPeriodNum = _forecastModelMLEso.getInitDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}			
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMTPly( BSysPeriod _bSysPeriod, ForecastModelMTPly _forecastModelMTPly )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMTPly.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		// 算法说明
		// -periodNumPerYear, ..., (_periodNum4Forecast-1-periodNumPerYear), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* percentValue 
		// FV(k) = HV(k)  for k<0
		
		// 历史数据期间范围 for 容差分析 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了
		for( int i = -periodNumPerYear; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了			
		for( int i = -periodNumPerYear; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMTCply( BSysPeriod _bSysPeriod, ForecastModelMTCply _forecastModelMTCply )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		int periodNum4OutlierAnalyze = _forecastModelMTCply.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -periodNumPerYear-n, ..., (_periodNum4Forecast-1-periodNumPerYear-n), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* ( FV(i-n) + ... + FV(i-1) ) / ( FV(i-n-periodNumPerYear) + ... + FV(i-1-periodNumPerYear) ) 
		// FV(k) = HV(k)  for k<0
		
		int dataPeriodNum = _forecastModelMTCply.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了
		for( int i = 0-periodNumPerYear-dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了			
		for( int i = -periodNumPerYear-dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMTEs( BSysPeriod _bSysPeriod, ForecastModelMTEs _forecastModelMTEs )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMTEs.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		int initDataPeriodNum = _forecastModelMTEs.getInitDataPeriodNum();

		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMTEso( BSysPeriod _bSysPeriod, ForecastModelMTEso _forecastModelMTEso )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMTEso.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		int initDataPeriodNum = _forecastModelMTEso.getInitDataPeriodNum();

		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMTLa( BSysPeriod _bSysPeriod, ForecastModelMTLa _forecastModelMTLa )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMTLa.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -n, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为预测开始月, 即periodBegin
		// FV(i) = FV(i-1) + increment*deta^(i+1)
		// incrementValue = (HV(-1) - HV(-n))/(n-1)
		// FV(-1) = HV(-1)

		int dataPeriodNum = _forecastModelMTLa.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了
		for( int i = -dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了			
		for( int i = -dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSLMa( BSysPeriod _bSysPeriod, ForecastModelMSLMa _forecastModelMSLMa )
	{
//		//zzy TODO 一步到位的取出 2年+dataPeriodNum+periodNum4OutlierAnalyze 的期间数
//		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
//		int periodNum4OutlierAnalyze = _forecastModelMSLMa.getOutlierAnalyzePeriodNum();
//		int dataPeriodNum = _forecastModelMSLMa.getDataPeriodNum();
//		
//		List<Integer> list4periodScope = new ArrayList<Integer>();
//		for( int i = 0-(periodNum4OutlierAnalyze+dataPeriodNum + 3*periodNumPerYear) ; i < 0 ; i++ )
//		{
//			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
//			list4periodScope.add(period);
//		}
//		
//		return list4periodScope;
		
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSLMa.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = ( FV(i-n) + FV(i-n+1) +...+ FV(i-1) )/ n 
		// FV(k) = HV(k) for k<0
		 
		int dataPeriodNum = _forecastModelMSLMa.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		
		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - (dataPeriodNum); i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end		
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope; 		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSLWma( BSysPeriod _bSysPeriod, ForecastModelMSLWma _forecastModelMSLWma )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSLWma.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = ( FV(i-n)*C(n) + FV(i-n+1)*C(n-1) + ... + FV(i-1)*C(1) )/ n
		// FV(k) = HV(k) for k<0
		 
		int dataPeriodNum = _forecastModelMSLWma.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end		
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSLEs( BSysPeriod _bSysPeriod, ForecastModelMSLEs _forecastModelMSLEs )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSLEs.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)
		
		int initDataPeriodNum = _forecastModelMSLEs.getInitDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSLEso( BSysPeriod _bSysPeriod, ForecastModelMSLEso _forecastModelMSLEso )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSLEso.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-2)*(1-alpha) + FV(i-1)* alpha 
		// FV(-2) = ( HV(-n) + ... + HV(-2) )/(n-1)
		// FV(-1) = ( HV(-n+1) + ... + HV(-1) )/(n-1)
		
		int initDataPeriodNum = _forecastModelMSLEso.getInitDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTPly( BSysPeriod _bSysPeriod, ForecastModelMSTPly _forecastModelMSTPly )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		int periodNum4OutlierAnalyze = _forecastModelMSTPly.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// -periodNumPerYear, ..., (_periodNum4Forecast-1-periodNumPerYear), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* percentValue 
		// FV(k) = HV(k)  for k<0
		
		// 历史数据期间范围 for 容差分析 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了
		for( int i = -periodNumPerYear; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了			
		for( int i = -periodNumPerYear; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end				
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTCply( BSysPeriod _bSysPeriod, ForecastModelMSTCply _forecastModelMSTCply )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		int periodNum4OutlierAnalyze = _forecastModelMSTCply.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -periodNumPerYear-n, ..., (_periodNum4Forecast-1-periodNumPerYear-n), 0, 1, 2, 3, ... , (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = FV(i-periodNumPerYear)* ( FV(i-n) + ... + FV(i-1) ) / ( FV(i-n-periodNumPerYear) + ... + FV(i-1-periodNumPerYear) ) 
		// FV(k) = HV(k)  for k<0
		
		int dataPeriodNum = _forecastModelMSTCply.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了
		for( int i = -periodNumPerYear-dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了			
		for( int i = 0-periodNumPerYear-dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTEsa( BSysPeriod _bSysPeriod, ForecastModelMSTEsa _forecastModelMSTEsa )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSTEsa.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) ) * seasonIndex + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		int initDataPeriodNum = _forecastModelMSTEsa.getInitDataPeriodNum();

		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTEsao( BSysPeriod _bSysPeriod, ForecastModelMSTEsao _forecastModelMSTEsao )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSTEsao.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) ) * seasonIndex + ET(i)			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		int initDataPeriodNum = _forecastModelMSTEsao.getInitDataPeriodNum();

		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTEsm( BSysPeriod _bSysPeriod, ForecastModelMSTEsm _forecastModelMSTEsm )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSTEsm.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i) ) * seasonIndex			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		int initDataPeriodNum = _forecastModelMSTEsm.getInitDataPeriodNum();

		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTEsmo( BSysPeriod _bSysPeriod, ForecastModelMSTEsmo _forecastModelMSTEsmo )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSTEsmo.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=initDataPeriodNum			
		// -n, ..., -2, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为模拟预测开始月, 即periodBegin
		// ET(i) = [ (1-beta)*( (1-alpha)*(FV(i-2)-FV(i-3)) + alpha*(FV(i-1)-FV(i-2)) ) + beta*ET(i-1) ]*deta
		// FV(i) = ( (1-alpha)*FV(i-2) + alpha*FV(i-1) + ET(i) ) * seasonIndex			
		// FV(-3) = ( HV(-n) + ... + HV(-3) ) / (n-2)
		// FV(-2) = ( HV(-n+1) + ... + HV(-2) ) / (n-2)
		// FV(-1) = ( HV(-n+2) + ... + HV(-1) ) / (n-2)
		// ET(-1) = HV(-1) - ( HV(-n) + ... + HV(-1) ) / n

		int initDataPeriodNum = _forecastModelMSTEsmo.getInitDataPeriodNum();

		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - initDataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMSTLa( BSysPeriod _bSysPeriod, ForecastModelMSTLa _forecastModelMSTLa )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMSTLa.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=dataPeriodNum
		// -n, -1, 0, 1, 2, 3, ..., (_periodNum4Forecast-1)
		// 0为预测开始月, 即periodBegin
		// FV(i) = FV(i-1) + increment*deta^(i+1)
		// incrementValue = (HV(-1) - HV(-n))/(n-1)
		// FV(-1) = HV(-1)

		int dataPeriodNum = _forecastModelMSTLa.getDataPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了
		for( int i = -dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		// 取得范围比实际使用的范围可能大，但因为可能用于给前端画图示，所以，大了就大了			
		for( int i = -dataPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		// 计算季节指数需要的历史数据    begin
		hmap4periodScope.putAll( this.getPeriodScope4SeansonIndex( _bSysPeriod.getForecastRunPeriod() ) );
		// 计算季节指数需要的历史数据    end			
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}		
	
	
	private List<Integer> getPeriodScope4HistoryData4ForecastModelMAAnalog( BSysPeriod _bSysPeriod, ForecastModelMAAnalog _forecastModelMAAnalog )
	{
		HashMap<Integer, Integer> hmap4periodScope = new HashMap<Integer, Integer>();

		int periodNum4OutlierAnalyze = _forecastModelMAAnalog.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		
		// 算法说明
		// n=offsetPeriodNum
		// -n, ..., -1, 0, 1, 2, 3, ... , (n-1), ..., (_periodNum4Forecast-1)
		// 0 为预测开始月，即即periodBegin
		// FV(i) = HV(i-n) 	for 0 <= i <= n-1
		// FV(i) = 0 		for n <= i <= _periodNum4Forecast-1
		// FV(k) = HV(k) for k<0
		 
		int offsetPeriodNum = _forecastModelMAAnalog.getOffsetPeriodNum();
		// 历史数据期间范围 for 容差分析 begin
		for( int i = 0 - offsetPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 容差分析 end

		// 历史数据期间范围 for 预测 begin
		for( int i = 0 - offsetPeriodNum; i < 0; i = i + 1 )
		{
			int period = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), i );
			hmap4periodScope.put( period, period );
		}
		// 历史数据期间范围 for 预测 end
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		if( hmap4periodScope.values() != null && !(hmap4periodScope.values().isEmpty()) )
		{
			Iterator<Integer> itr_hmap4periodScope_values = hmap4periodScope.values().iterator();
			while( itr_hmap4periodScope_values.hasNext() )
			{
				list4periodScope.add( itr_hmap4periodScope_values.next() );
			}
		}

		return list4periodScope;		
	}	

	private List<Integer> getPeriodScope4HistoryData4ForecastModelM( BSysPeriod _bSysPeriod, ForecastModelM _forecastModelM )
	{		
		if( _forecastModelM instanceof ForecastModelMLMa )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMLMa( _bSysPeriod, (ForecastModelMLMa)_forecastModelM );
		}
		else if( _forecastModelM instanceof ForecastModelMLWma )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMLWma( _bSysPeriod, (ForecastModelMLWma)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMLEs )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMLEs( _bSysPeriod, (ForecastModelMLEs)_forecastModelM );
		}
		else if( _forecastModelM instanceof ForecastModelMLEso )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMLEso( _bSysPeriod, (ForecastModelMLEso)_forecastModelM );
		}				
		
		else if( _forecastModelM instanceof ForecastModelMTPly )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMTPly( _bSysPeriod, (ForecastModelMTPly)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMTCply )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMTCply( _bSysPeriod, (ForecastModelMTCply)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMTEs )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMTEs( _bSysPeriod, (ForecastModelMTEs)_forecastModelM );
		}
		else if( _forecastModelM instanceof ForecastModelMTEso )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMTEso( _bSysPeriod, (ForecastModelMTEso)_forecastModelM );
		}				
		else if( _forecastModelM instanceof ForecastModelMTLa )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMTLa( _bSysPeriod, (ForecastModelMTLa)_forecastModelM );
		}
		
		else if( _forecastModelM instanceof ForecastModelMSLMa )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSLMa( _bSysPeriod, (ForecastModelMSLMa)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMSLWma )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSLWma( _bSysPeriod, (ForecastModelMSLWma)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMSLEs )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSLEs( _bSysPeriod, (ForecastModelMSLEs)_forecastModelM );
		}		
		else if( _forecastModelM instanceof ForecastModelMSLEso )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSLEso( _bSysPeriod, (ForecastModelMSLEso)_forecastModelM );
		}				
		
		else if( _forecastModelM instanceof ForecastModelMSTPly )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTPly( _bSysPeriod, (ForecastModelMSTPly)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMSTCply )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTCply( _bSysPeriod, (ForecastModelMSTCply)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMSTEsa )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTEsa( _bSysPeriod, (ForecastModelMSTEsa)_forecastModelM );
		}	
		else if( _forecastModelM instanceof ForecastModelMSTEsao )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTEsao( _bSysPeriod, (ForecastModelMSTEsao)_forecastModelM );
		}			
		else if( _forecastModelM instanceof ForecastModelMSTEsm )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTEsm( _bSysPeriod, (ForecastModelMSTEsm)_forecastModelM );
		}
		else if( _forecastModelM instanceof ForecastModelMSTEsmo )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTEsmo( _bSysPeriod, (ForecastModelMSTEsmo)_forecastModelM );
		}					
		else if( _forecastModelM instanceof ForecastModelMSTLa )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMSTLa( _bSysPeriod, (ForecastModelMSTLa)_forecastModelM );
		}		
		
		else if( _forecastModelM instanceof ForecastModelMAAnalog )
		{
			return this.getPeriodScope4HistoryData4ForecastModelMAAnalog( _bSysPeriod, (ForecastModelMAAnalog)_forecastModelM );
		}				

		List<Integer> list4periodScope = new ArrayList<Integer>();
		return list4periodScope;
	}
	
	private HashMap<Integer, Integer> getPeriodScope4SeansonIndex( int _forecastRunPeriod)
	{
		HashMap<Integer, Integer> rstHmap4Period = new HashMap<Integer, Integer>();
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
//		int periodNum4OutlierAnalyze = _forecastModelMSLMa.getOutlierAnalyzePeriodNum();
//		int dataPeriodNum = _forecastModelMSLMa.getDataPeriodNum();
		
		List<Integer> list4periodScope = new ArrayList<Integer>();
		for( int i = 0-(6 + 3*periodNumPerYear) ; i < 0 ; i++ )
		{
			int period = UtilPeriod.getPeriod( _forecastRunPeriod, i );
			rstHmap4Period.put(period, period);
		}
		
		return rstHmap4Period;
		
		/**
		if( UtilPeriod.checkPeriod( _forecastRunPeriod ) == false )
		{
			return rstHmap4Period;
		}
	
		int periodNumPerYear = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		
		// modify by zhangzy 20180520 季节历史修正，调整起始期间，不再以年初使用，直接用起始期间 period_pno1 = _periodBegin4Run;
		int year = _forecastRunPeriod/100;
		int period_pno1 = year * 100 + 1;
		
//		int period_pno1 = _forecastRunPeriod;
		
		for( int i=1; i<=2*periodNumPerYear; i++ )
		{
			int period = UtilPeriod.getPeriod( period_pno1, 0-i );
			rstHmap4Period.put( period, period );
		}
		
		return rstHmap4Period; */
	}
	

	// forecastmodelm 算法 end
	
	public List<Object> forecastModelMSimulate( BSysPeriod _bSysPeriod, BForecastModelM _bForecastModelM, int _fcPeriodNum, 
			List<ABProOrg> _list4ProOrgScope4ForecastScope,
			BProductLayer _runProductLayer, BOrganizationLayer _runOrganizationLayer ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if ( ( _bSysPeriod == null ) || ( _bForecastModelM == null ) || ( _list4ProOrgScope4ForecastScope == null ) || ( _list4ProOrgScope4ForecastScope.isEmpty() ) || _runOrganizationLayer == null
				|| _runProductLayer == null ) 
		{ 
			return null; 
		}

		ForecastModelMBDConvertor forecastModelMBDConvertor = UtilFactoryForecastModelM.getForecastModelMBDConvertorInstance( _bForecastModelM.getIndicator() );
		ForecastModelM forecastModelM2Simulate = (ForecastModelM) forecastModelMBDConvertor.btod( _bForecastModelM );

		int periodNum4OutlierAnalyze = forecastModelM2Simulate.getOutlierAnalyzePeriodNum();
		int periodBegin4OutlierAnalyze = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 - periodNum4OutlierAnalyze );

		BForecastErrorMappingModel rstForecastErrorMappingModel = new BForecastErrorMappingModel();
		List<Long> rstList4ForecastData = new ArrayList<Long>();
		for( int i = 0; i < periodNum4OutlierAnalyze + _fcPeriodNum; i = i + 1 )
		{
			rstList4ForecastData.add( new Long( 0 ) );
		}

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );
						
			// 预测范围下的全部明细组织产品
			HashMap<String, AProOrg> hmap4DetailProOrgs = UtilProOrg.getDetailProOrgs( _list4ProOrgScope4ForecastScope, session, true );
			if( hmap4DetailProOrgs == null || hmap4DetailProOrgs.isEmpty() )
			{
				return null;
			}
			
			//	模型公式所需的历史数据的期间范围
			List<Integer> list4periodScope4History = this.getPeriodScope4HistoryData4ForecastModelM( _bSysPeriod, forecastModelM2Simulate );			
			
			//	类比模型，使用的历史数据是模型的参照项的权重和，而是不是预测范围的历史数据值。整个预测类别，不管在什么层次上运行，使用的都是一组历史数据，所以这里准备好	begin
			HashMap<Integer, Double> hmap4HistoryData_forecastModelMAAnaog = new HashMap<Integer, Double>();

			if( forecastModelM2Simulate.getIndicator().equals( BizConst.FORECASTMODELM_INDICATOR_AANALOG ) )
			{
				for( int i=0; i<list4periodScope4History.size(); i++ )
				{
					hmap4HistoryData_forecastModelMAAnaog.put( list4periodScope4History.get( i ), new Double(0.0) );
				}
				
				//	历史数据
				Iterator<BForecastModelMAAnalogItem> itr_BForecastModelMAAnalogItem = ((BForecastModelMAAnalog)_bForecastModelM).getForecastModelMAAnalogItems().iterator();
				while( itr_BForecastModelMAAnalogItem.hasNext() )
				{
					BForecastModelMAAnalogItem bForecastModelMAAnalogItem = itr_BForecastModelMAAnalogItem.next();
										
					HashMap<String, AProOrg> hmap_DetailProOrgs = UtilProOrg.getDetailProOrgs( bForecastModelMAAnalogItem, session, true );
					String idsScopeStr4DetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( hmap_DetailProOrgs );
					HashMap<Integer, Long> hmap4HistoryData = daoHistoryData.getHistoryDataSumGroupByPeriod( idsScopeStr4DetailProOrgs, list4periodScope4History, forecastModelM2Simulate.getHistoryBizData().getId() );
					if( hmap4HistoryData != null )
					{
						for( int i=0; i<list4periodScope4History.size(); i++ )
						{
							Long value = hmap4HistoryData.get( list4periodScope4History.get( i ) );
							if( value != null )
							{
								Double newValue  = hmap4HistoryData_forecastModelMAAnaog.get( list4periodScope4History.get( i ) ) + value * bForecastModelMAAnalogItem.getCoefficient();
								hmap4HistoryData_forecastModelMAAnaog.put( list4periodScope4History.get( i ), newValue );
							}
						}						
					}
				}
			}
			//	类比模型，使用的历史数据是模型的参照项的权重和，而是不是预测范围的历史数据值。整个预测类别，不管在什么层次上运行，使用的都是一组历史数据，所以这里准备好	end

			// 把这些明细组织产品按运行层次分组
			HashMap<String, List<AProOrg>> hmap4List4GroupedDetailProOrgs = UtilProOrg.getGroupedDetailProOrgs( hmap4DetailProOrgs, _runOrganizationLayer.getValue(),
					_runProductLayer.getValue() );
			Iterator<List<AProOrg>> itr_hmap4List4GroupedDetailProOrgs_values = null;				
			
			// 把历史数据一起准备好，因为可能要进行自适应，多次查询影响效率    begin
			HashMap<String,HashMap<Integer, Long>> hmap4Hmap4GroupedHistoryData = new HashMap<String,HashMap<Integer, Long>>();
			
			
			itr_hmap4List4GroupedDetailProOrgs_values = hmap4List4GroupedDetailProOrgs.values().iterator();			
			while( itr_hmap4List4GroupedDetailProOrgs_values.hasNext() )
			{
				// 一组明细组织产品范围
				List<AProOrg> list4GroupedDetailProOrgs = itr_hmap4List4GroupedDetailProOrgs_values.next();
				if( list4GroupedDetailProOrgs.isEmpty() )
				{
					continue;
				}
				
				// 该组明细组织产品范围的ID字符串
				String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );

				// 该组明细组织产品范围的历史数据准备 begin
				// hmap4HistoryData
				// 因映射模型不同而不同

				HashMap<Integer, Long> hmap4HistoryData = new HashMap<Integer, Long>();
				if( forecastModelM2Simulate.getIndicator().equals( BizConst.FORECASTMODELM_INDICATOR_AANALOG ) )
				{
					//	类比模型，使用的历史数据是模型的参照项的历史数据的权重和，而不是预测范围的历史数据值
					for( int i=0; i<list4periodScope4History.size(); i++ )
					{
						Double value = hmap4HistoryData_forecastModelMAAnaog.get( list4periodScope4History.get( i ) );
						if( value != null )
						{
							hmap4HistoryData.put( list4periodScope4History.get( i ), new Long(value.longValue()) );
						}
					}
				}
				else
				{
					hmap4HistoryData = daoHistoryData.getHistoryDataSumGroupByPeriod( idsScopeStr4GroupedDetailProOrgs, list4periodScope4History, forecastModelM2Simulate.getHistoryBizData().getId() );
				}
				
				hmap4Hmap4GroupedHistoryData.put( idsScopeStr4GroupedDetailProOrgs, hmap4HistoryData );
			}
			// 把历史数据一起准备好，因为可能要进行自适应，多次查询影响效率    end	
			
			
			// 有映射模型的如果是自适应类的，先自适应确定模型参数    begin
			if( forecastModelM2Simulate instanceof ForecastModelMLEso ||
				forecastModelM2Simulate instanceof ForecastModelMTEso ||
				forecastModelM2Simulate instanceof ForecastModelMSLEso ||
				forecastModelM2Simulate instanceof ForecastModelMSTEsao ||
				forecastModelM2Simulate instanceof ForecastModelMSTEsmo )
			{
				this.optimizeForecastModelM( _bSysPeriod, forecastModelM2Simulate, hmap4List4GroupedDetailProOrgs, hmap4Hmap4GroupedHistoryData );
			}
			// 有映射模型的如果是自适应类的，先自适应确定模型参数    end

			// 按预测范围根据预测层次分的组分别进行容差分析：得到容差分析期间的预测数据和并调整后的历史数据,及预测期间预测要使用的历史数据
			// begin
			// list4HashMap4ForecastData4OutlierAnalyze 存放容差期间的预测数据以便进行容差期间的误差计算
			// list4HashMap4HistoryData4OutlierAnalyze
			// 存放容差期间的调整后的历史数据以便进行容差期间的误差计算
			// hashMap4HashMap4HistoryData4Forecast
			// 分组存放调整后的历史数据以便进行后继的预测，以分组的明细组织产品范围的ID字符串为key
			HashMap<String, HashMap<Integer, Long>> hashMap4HashMap4HistoryData4Forecast = new HashMap<String, HashMap<Integer, Long>>();
			List<HashMap<Integer, Long>> list4HashMap4ForecastData4OutlierAnalyze = new ArrayList<HashMap<Integer, Long>>();
			List<HashMap<Integer, Long>> list4HashMap4HistoryData4OutlierAnalyze = new ArrayList<HashMap<Integer, Long>>();

			itr_hmap4List4GroupedDetailProOrgs_values = hmap4List4GroupedDetailProOrgs.values().iterator();
			while( itr_hmap4List4GroupedDetailProOrgs_values.hasNext() )
			{
				// 一组明细组织产品范围
				List<AProOrg> list4GroupedDetailProOrgs = itr_hmap4List4GroupedDetailProOrgs_values.next();
				if( list4GroupedDetailProOrgs.isEmpty() )
				{
					continue;
				}
				// 该组明细组织产品范围的ID字符串
				String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );

				// 该组明细组织产品范围的历史数据
				HashMap<Integer, Long> hmap4HistoryData = hmap4Hmap4GroupedHistoryData.get( idsScopeStr4GroupedDetailProOrgs );
				if( hmap4HistoryData == null )
				{
					hmap4HistoryData = new HashMap<Integer, Long>();
				}

				// 预测数据 begin
				HashMap<Integer, Long> hmap4ForecastData = this.getForecastDataByRunFormula4ForecastModelM( forecastModelM2Simulate, periodBegin4OutlierAnalyze, periodNum4OutlierAnalyze, hmap4HistoryData );
				if( hmap4ForecastData == null )
				{
					hmap4ForecastData = new HashMap<Integer, Long>();
				}
				// 预测数据 end

				// 把本组范围的预测数据计入预测数据总值 begin
				for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
				{
					int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
					Long forecastDataValue = hmap4ForecastData.get( period );
					if( forecastDataValue != null )
					{
						rstList4ForecastData.set( i, rstList4ForecastData.get( i ) + forecastDataValue );
					}
				}
				// 把本组范围的预测数据计入预测数据总值 end

				// 计算MAD begin
				double mad = 0;
				for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
				{
					int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
					Long historyDataValue = hmap4HistoryData.get( period );
					if( historyDataValue == null )
					{
						historyDataValue = 0L;
					}
					Long forecastDataValue = hmap4ForecastData.get( period );
					if( forecastDataValue == null )
					{
						forecastDataValue = 0L;
					}
					mad = mad + Math.abs( historyDataValue - forecastDataValue );
				}
				mad = mad / periodNum4OutlierAnalyze;
				// 计算MAD end

				// 调整历史数据 begin
				if( forecastModelM2Simulate.getOutlierDataIsAutoAdjust() == BizConst.GLOBAL_YESNO_YES )
				{
					for( int i = 0; i < periodNum4OutlierAnalyze; i++ )
					{
						int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
						Long historyDataValue = hmap4HistoryData.get( period );
						if( historyDataValue == null )
						{
							historyDataValue = 0L;
						}
						Long forecastDataValue = hmap4ForecastData.get( period );
						if( forecastDataValue == null )
						{
							forecastDataValue = 0L;
						}

						if( Math.abs( historyDataValue - forecastDataValue ) > forecastModelM2Simulate.getOutlierFactor() * 1.25 * mad )
						{
							historyDataValue = Math.round( historyDataValue * forecastModelM2Simulate.getOutlierDataAdjustHistoryWgt() + forecastDataValue
									* forecastModelM2Simulate.getOutlierDataAdjustComputeWgt() );
							hmap4HistoryData.put( period, historyDataValue );
						}
					}
				}
				// 调整历史数据 end
				// 存放调整后的历史数据以便计算容差期间的误差分析
				list4HashMap4HistoryData4OutlierAnalyze.add( hmap4HistoryData );
				// 存放预测数据以便计算容差期间的误差分析
				list4HashMap4ForecastData4OutlierAnalyze.add( hmap4ForecastData );
				// 以分组的明细组织产品范围的ID字符串为key存放调整后的历史数据，以便后面进行真正的预测
				hashMap4HashMap4HistoryData4Forecast.put( idsScopeStr4GroupedDetailProOrgs, hmap4HistoryData );
			}
			// 按预测范围根据预测层次分的组分别进行容差分析：得到容差分析期间的预测数据和并调整后的历史数据,及预测期间预测要使用的历史数据
			// end

			// 针对整个预测范围产生误差分析 begin
			// 汇总历史数据和预测数据 begin
			Long[] arr4HistoryDataPeriodTotalValue4OutlierAnalyze = new Long[periodNum4OutlierAnalyze];
			Long[] arr4ForecastDataPeriodTotalValue4OutlierAnalyze = new Long[periodNum4OutlierAnalyze];
			for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
			{
				int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
				Long historyDataValue = 0L;
				if( list4HashMap4HistoryData4OutlierAnalyze != null && !(list4HashMap4HistoryData4OutlierAnalyze.isEmpty()) )
				{
					Iterator<HashMap<Integer, Long>> itr_list4HashMap4HistoryData4OutlierAnalyze = list4HashMap4HistoryData4OutlierAnalyze.iterator();
					while( itr_list4HashMap4HistoryData4OutlierAnalyze.hasNext() )
					{
						HashMap<Integer, Long> hashMap4HistoryData4OutlierAnalyze = itr_list4HashMap4HistoryData4OutlierAnalyze.next();
						Long tempHistoryDatavalue = hashMap4HistoryData4OutlierAnalyze.get( period );
						if( tempHistoryDatavalue != null )
						{
							historyDataValue = historyDataValue + tempHistoryDatavalue;
						}
					}
				}
				arr4HistoryDataPeriodTotalValue4OutlierAnalyze[i] = historyDataValue;

				Long forecastDataValue = 0L;
				if( list4HashMap4ForecastData4OutlierAnalyze != null && !(list4HashMap4ForecastData4OutlierAnalyze.isEmpty()) )
				{
					Iterator<HashMap<Integer, Long>> itr_list4HashMap4ForecastData4OutlierAnalyze = list4HashMap4ForecastData4OutlierAnalyze.iterator();
					while( itr_list4HashMap4ForecastData4OutlierAnalyze.hasNext() )
					{
						HashMap<Integer, Long> hashMap4ForecastData4OutlierAnalyze = itr_list4HashMap4ForecastData4OutlierAnalyze.next();
						Long tempForecastDatavalue = hashMap4ForecastData4OutlierAnalyze.get( period );
						if( tempForecastDatavalue != null )
						{
							forecastDataValue = forecastDataValue + tempForecastDatavalue;
						}
					}
				}
				arr4ForecastDataPeriodTotalValue4OutlierAnalyze[i] = forecastDataValue;
			}
			// 汇总历史数据和预测数据 end
			// 计算误差 begin
			DaoSystem daoSystem = new DaoSystem( session );
			rstForecastErrorMappingModel.setForecastInstCode( "" );
			rstForecastErrorMappingModel.setForecastInstName( "" );
			rstForecastErrorMappingModel.setForecastModelCode( forecastModelM2Simulate.getCode() );
			rstForecastErrorMappingModel.setForecastModelName( forecastModelM2Simulate.getName() );
			rstForecastErrorMappingModel.setCompilePeriod( _bSysPeriod.getCompilePeriod() );
			rstForecastErrorMappingModel.setOutlierAnalyzePeriodNum( periodNum4OutlierAnalyze );
			rstForecastErrorMappingModel.setRunOrganizationLayer( _runOrganizationLayer );
			rstForecastErrorMappingModel.setRunProductLayer( _runProductLayer );
			rstForecastErrorMappingModel.setProducedTime( daoSystem.getSysTimeAsTimeStamp() );
			rstForecastErrorMappingModel.setErrorThreshold( null );

			Double et = 0.0;
			Double mape = 0.0;
			Double bias = 0.0;
			Double mad = 0.0;
			Double ts = 0.0;
			Double mse = 0.0;

			for( int i = 0; i < periodNum4OutlierAnalyze; i = i + 1 )
			{
				Long historyDataValue = arr4HistoryDataPeriodTotalValue4OutlierAnalyze[i];
				Long forecastDataValue = arr4ForecastDataPeriodTotalValue4OutlierAnalyze[i];
				et = et + historyDataValue - forecastDataValue;
				if( historyDataValue.longValue() != 0 )
				{
					mape = mape + Math.abs( (historyDataValue - forecastDataValue) * 100.0 ) / historyDataValue;
					bias = bias + (historyDataValue - forecastDataValue) * 100.0 / historyDataValue ;
				}
				mad = mad + Math.abs( historyDataValue - forecastDataValue );
				//mse = mse + (historyDataValue - forecastDataValue) * (historyDataValue - forecastDataValue);
			}
			//	marked by liuzhen, 2011.02.18	begin
//			et = et / periodNum4OutlierAnalyze;
			//	marked by liuzhen, 2011.02.18	end
			mape = mape / periodNum4OutlierAnalyze;
			bias = bias / periodNum4OutlierAnalyze;
			mad = mad / periodNum4OutlierAnalyze;
			if( mad.doubleValue() != 0 )
			{
				ts = et / mad;
			}
			//mse = mse / periodNum4OutlierAnalyze;
			
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMaximumFractionDigits( 4 );
			numberFormat.setGroupingUsed( false );
			et = Double.parseDouble( numberFormat.format( et ) );
			mape = Double.parseDouble( numberFormat.format( mape ) );
			bias = Double.parseDouble( numberFormat.format( bias ) );
			mad = Double.parseDouble( numberFormat.format( mad ) );
			ts = Double.parseDouble( numberFormat.format( ts ) );
			mse = Double.parseDouble( numberFormat.format( mse ) );
			
			rstForecastErrorMappingModel.setEt( et );
			rstForecastErrorMappingModel.setMape( mape );
			rstForecastErrorMappingModel.setBias(bias); //zhangzy 20180224
			rstForecastErrorMappingModel.setMad( mad );
			rstForecastErrorMappingModel.setTs( ts );
			rstForecastErrorMappingModel.setMse( mse );
			// 计算误差 end
			// 针对整个预测范围产生误差分析 end

			// 按预测范围根据预测层次分的组分别进行容差分析：得到容差分析期间的预测数据和并调整后的历史数据,及预测期间预测要使用的历史数据
			// end

			// 逐组运行模型	begin
			// 预测开始期间和预测期间列表
			// periodNum4Forecast, periodBegin4Forecast,
			// list4periodScope4Forecast
			int periodNum4Forecast = _fcPeriodNum;
			int periodBegin4Forecast = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), 0 );
			List<Integer> list4periodScope4Forecast = new ArrayList<Integer>();
			for( int i = 0; i < periodNum4Forecast; i = i + 1 )
			{
				list4periodScope4Forecast.add( UtilPeriod.getPeriod( periodBegin4Forecast, i ) );
			}

			itr_hmap4List4GroupedDetailProOrgs_values = hmap4List4GroupedDetailProOrgs.values().iterator();
			while( itr_hmap4List4GroupedDetailProOrgs_values.hasNext() )
			{
				// 一组明细组织产品范围
				List<AProOrg> list4GroupedDetailProOrgs = itr_hmap4List4GroupedDetailProOrgs_values.next();
				if( list4GroupedDetailProOrgs.isEmpty() )
				{
					continue;
				}

				// 该组明细组织产品范围的ID字符串
				String idsScopeStr4GroupedDetailProOrgs = UtilProOrg.getIdsScopeStr4ProOrgs( list4GroupedDetailProOrgs );

				// 历史数据 begin
				HashMap<Integer, Long> hmap4HistoryData = hashMap4HashMap4HistoryData4Forecast.get( idsScopeStr4GroupedDetailProOrgs );
				// 历史数据 end

				// 预测数据 begin
				HashMap<Integer, Long> hmap4ForecastData = this.getForecastDataByRunFormula4ForecastModelM( forecastModelM2Simulate, periodBegin4Forecast, periodNum4Forecast, hmap4HistoryData );
				if( hmap4ForecastData == null || hmap4ForecastData.isEmpty() )
				{
					hmap4ForecastData = new HashMap<Integer, Long>();
				}
				// 预测数据 end

				// 把本组范围的预测数据计入预测数据总值 begin
				for( int i = periodNum4OutlierAnalyze; i < periodNum4OutlierAnalyze + _fcPeriodNum; i = i + 1 )
				{
					int period = UtilPeriod.getPeriod( periodBegin4OutlierAnalyze, i );
					Long forecastDataValue = hmap4ForecastData.get( period );
					if( forecastDataValue != null )
					{
						rstList4ForecastData.set( i, rstList4ForecastData.get( i ) + forecastDataValue );
					}
				}
				// 把本组范围的预测数据计入预测数据总值 end
			}
			// 逐组运行模型	end

			if( trsa != null )
			{
				trsa.commit();
			}

		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}

		List<Object> rstList = new ArrayList<Object>();
		rstList.add( rstForecastErrorMappingModel );
		rstList.add( rstList4ForecastData );
		return rstList;
	}
	// forecast simulate end

}
