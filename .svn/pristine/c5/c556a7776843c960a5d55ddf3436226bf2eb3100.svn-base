package dmdd.dmddjava.service.forecastservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastSetting;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
import dmdd.dmddjava.dataaccess.bdconvertor.ForecastMakeLogBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.bizobject.BForecastMakeLog;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogHfcItem;
import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastMakeLog;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastMakeLogHfcItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastMakeLogProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.service.validator.PeriodValidator;

/**
 * 处理预测数据的保存、更新
 * @author zzy
 *
 */
public class ForecastSaveService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());
	
	private PeriodValidator periodValidator = new PeriodValidator();
	
	private AmountForecastService amountForecastService = new AmountForecastService();
	
	/**
	 * _bForecastAdjsutLog4save 中的 ForecastMakeLogAuditItems 起数据更新作用，_listABUiRowData4save 的作用是提供分解用的明细ProOrgId
	 * 实际上也可以用 _listABUiRowData4save 中的数据来起更新作用，但 _bForecastAdjsutLog4save 中的 ForecastMakeLogAuditItems 是个半成品，可以直接用 
	 * @param _listABUiRowData4save
	 * @param _bForecastAdjsutLog4save
	 * @param _bSysPeriod
	 * @throws Exception
	 */
	public String saveForecastDatas4AdjustUI( List<ABUiRowData> _listABUiRowData4save, BForecastMakeLog _bForecastAdjsutLog4save, ABForecastSetting _abForecastSetting, BSysPeriod _bSysPeriod ) throws Exception
	{
		long beginTime = System.currentTimeMillis();
        logger.info("预测数据保存开始...");
        
        String saveResultDesc = "";
        
		//	检查服务器状态是否可以提供服务
		ServerEnvironment.getInstance().checkSystemStatus();
				
		//系统期间和编译期间检查
		periodValidator.sysPeriodValidate(_bSysPeriod);
		
		if( _listABUiRowData4save == null || _listABUiRowData4save.isEmpty() || _bForecastAdjsutLog4save== null || _abForecastSetting == null )
		{
			//保存数据为空，直接返回
			throw new Exception("保存的预测数据信息为空！");
		}
		
		ForecastMakeLogBDConvertor forecastMakeLogBDConvertor = new ForecastMakeLogBDConvertor();
		ForecastMakeLog forecastMakeLog_new = forecastMakeLogBDConvertor.btod( _bForecastAdjsutLog4save, true, true, false );
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastData daoForecastData = new DaoForecastData( session );
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );
			DaoBizData daoBizData = new DaoBizData( session );
			DaoSystem daoSystem = new DaoSystem( session );
			
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			
			
			//	最终预测业务数据	begin
			BizData bizDataFcFinal = daoBizData.getFinalFcBizData();
			BizData amountBizDataFcFinal = daoBizData.getBizDataByCode(bizDataFcFinal.getCode()+BizConst.AMOUNT_BIZ_DATA_SUFFIX);
			List<ForecastData> finalFcDataList = new ArrayList<ForecastData>(); //最终预测修改的数据集合
			List<ForecastData> handFcDataList = new ArrayList<ForecastData>(); //判断预修改的数据集合
			
			HashMap<String, BizData> bizDataMap = new HashMap<String, BizData>();

			//	把ppcoocb对应的明细ProOrgId用hashmap存放起来以便后面使用 	begin
			HashMap<String, String> hmap_ppcoocb_detailProOrgIdStr = new HashMap<String, String>();
			for( int i=0; i<_listABUiRowData4save.size(); i++ )
			{
				ABUiRowData abUiRowData = _listABUiRowData4save.get( i );				
				//	拼出明细范围字符串
				String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( abUiRowData );
				
				String strKey4ppcoocb = UtilStrKey.getStrKey4PPcOOcB( abUiRowData.getProduct(), abUiRowData.getProductCharacter(), abUiRowData.getOrganization(), abUiRowData.getOrganizationCharacter(), abUiRowData.getBizData() );
				hmap_ppcoocb_detailProOrgIdStr.put( strKey4ppcoocb, detailProOrgIdStr );	
			}
			
			//	建立分解参照信息:把ppcoocb对应的分解参照处理好用 hashmap放起来方便后面使用 		begin
			HashMap<String, HashMap<String, Long>> hmap_ppcoocb_hmap4Ref_po_value = new HashMap<String, HashMap<String, Long>>();
			HashMap<String, Long> hmap_ppcoocb_totalValue4Ref = new HashMap<String, Long>();
			
			//TODO 界面中判断一下，如果是非明细数据调整，需要提示使用哪种数据分解规则
			//TODO ZHANGZY 20150624这里做一个判断，如果已经是明细层的数据，则不执行分解的判断逻辑
			
			//	按照前N期历史平均分解 可以被不同的处理期间公用，所以这里计算并缓存下来
			//	按照去年同期分解，可以被不同处理期间公用的可能性较低（预测期间减去屏蔽期间超过一年），所以这里不处理，后面具体处理
			if( _abForecastSetting.getDecomposeFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_AVERAGE )
			{
				//	按照前N期历史平均分解
				BBizData distributeRefBizData = _abForecastSetting.getDistributeRefBizData();	//	历史类业务数据
				int distributeRefPeriodNum = _abForecastSetting.getDistributeRefPeriodNum();
				
				int periodBegin4ref = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), -distributeRefPeriodNum );
				int periodEnd4ref = UtilPeriod.getPeriod( _bSysPeriod.getForecastRunPeriod(), -1 );
				
				if( hmap_ppcoocb_detailProOrgIdStr.keySet() != null && !(hmap_ppcoocb_detailProOrgIdStr.keySet().isEmpty()) )
				{
					Iterator<String> itr_hmap_ppcoocb_detailProOrgIdStr_keys = hmap_ppcoocb_detailProOrgIdStr.keySet().iterator();
					while( itr_hmap_ppcoocb_detailProOrgIdStr_keys.hasNext() )
					{
						String strKey4ppcoocb = itr_hmap_ppcoocb_detailProOrgIdStr_keys.next();
						String detailProOrgIdStr = hmap_ppcoocb_detailProOrgIdStr.get( strKey4ppcoocb );
						
						Long totalValue4Ref = 0L; // 总值
						HashMap<String, Long> hmap4Ref_po_value = new HashMap<String, Long>(); // 详情
							
						hmap4Ref_po_value = daoHistoryData.getHistoryDataAvgGroupByPO( detailProOrgIdStr, periodBegin4ref, periodEnd4ref, distributeRefBizData.getId() );
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
						hmap_ppcoocb_hmap4Ref_po_value.put( strKey4ppcoocb, hmap4Ref_po_value );
						hmap_ppcoocb_totalValue4Ref.put( strKey4ppcoocb, totalValue4Ref );						
					}
				}				
			}
			else if( _abForecastSetting.getDecomposeFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_COUNTERPARTOFLAST1YEAR )
			{
				//	按照去年同期分解
				//	后面对不同处理期间具体处理
			}
			//	建立分解参照信息:把ppcoocb对应的分解参照处理好用 hashmap放起来方便后面使用 		end		
			
			//	用于存放变化的最终预测数据，一个最终预测数据可能会被多次影响而变化，所以要放到hashmap中	begin
			HashMap<String, ForecastData> hmap_popb_ForecastData4FcFinal = new HashMap<String, ForecastData>();
			//	用于存放变化的最终预测数据，一个最终预测数据可能会被多次影响而变化，所以要放到hashmap中	end
						
			Iterator<ForecastMakeLogHfcItem> itr_ForecastMakeLogHfcItems = forecastMakeLog_new.getForecastMakeLogHfcItems().iterator();
			while( itr_ForecastMakeLogHfcItems.hasNext() )
			{
				ForecastMakeLogHfcItem forecastMakeLogHfcItem = itr_ForecastMakeLogHfcItems.next();
								
				String strKey4ppcoocb = UtilStrKey.getStrKey4PPcOOcB( forecastMakeLogHfcItem.getProduct(), forecastMakeLogHfcItem.getProductCharacter(), forecastMakeLogHfcItem.getOrganization(), forecastMakeLogHfcItem.getOrganizationCharacter(), forecastMakeLogHfcItem.getBizData() );
				
				//	做乐观的版本检查	begin, 检查预测数据是否被其它用户或者线程修改过
				String detailProOrgIdStr = hmap_ppcoocb_detailProOrgIdStr.get( strKey4ppcoocb );
				ASumData aSumData_inDB = daoForecastData.getSumForecastData( detailProOrgIdStr, forecastMakeLogHfcItem.getPeriod(), forecastMakeLogHfcItem.getBizData().getId() );
				if( aSumData_inDB == null )
				{
					if( forecastMakeLogHfcItem.getOldValue() != 0 )
					{
						Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_DATA_HAS_BEEN_MODIFIED );
						Exception ex = new Exception( cause );
						throw ex;
					}	
				}
				else if( aSumData_inDB.getValue().longValue() != forecastMakeLogHfcItem.getOldValue() )
				{
					Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_DATA_HAS_BEEN_MODIFIED );
					Exception ex = new Exception( cause );
					throw ex;
				}
				//	做乐观的版本检查	end

				//	查询已有的 forecastData_FcHand	begin
				List<ForecastData> listForecastData_FcHand = daoForecastData.getForecastDatas( detailProOrgIdStr, forecastMakeLogHfcItem.getPeriod(), forecastMakeLogHfcItem.getBizData().getId() );
				HashMap<String, ForecastData> hmap_po_ForecastData_FcHand = new HashMap<String, ForecastData>();
				if( listForecastData_FcHand != null )
				{
					for(int i=0 ;i<listForecastData_FcHand.size(); i++)
					{
						ForecastData forecastData_FcHand = listForecastData_FcHand.get( i );
						String strKey4po = UtilStrKey.getStrKey4PO( forecastData_FcHand.getProduct(), forecastData_FcHand.getOrganization() );
						hmap_po_ForecastData_FcHand.put( strKey4po, forecastData_FcHand );
					}					
				}
				else
				{
					//20150530 在界面上增加提示信息
					saveResultDesc += "数据未激活,无法保存["+forecastMakeLogHfcItem.getBizData().getName()
						+"]["+forecastMakeLogHfcItem.getProduct().getName()+"]["+forecastMakeLogHfcItem.getOrganization().getName()+"] \n";
					continue;
					//	没有Forecast_FcHand可以来承担这些调整，不进行分配,	是无效调整
				}
				//	查询已有的 forecastData_FcHand	end
								
				//	获得相应的分解参照信息以进行分解	begin
				Long totalValue4Ref = hmap_ppcoocb_totalValue4Ref.get( strKey4ppcoocb ); // 总值
				HashMap<String, Long> hmap4Ref_po_value = hmap_ppcoocb_hmap4Ref_po_value.get( strKey4ppcoocb ); // 详情
				
				if( _abForecastSetting.getDecomposeFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_AVERAGE )
				{
					totalValue4Ref = hmap_ppcoocb_totalValue4Ref.get( strKey4ppcoocb ); // 总值
					hmap4Ref_po_value = hmap_ppcoocb_hmap4Ref_po_value.get( strKey4ppcoocb ); // 详情
					
					if( totalValue4Ref == null )
					{
						totalValue4Ref = 0L;
					}
					if( hmap4Ref_po_value == null )
					{
						hmap4Ref_po_value = new HashMap<String, Long>();
					}					
				}
				else if( _abForecastSetting.getDecomposeFormula() == BizConst.FORECAST_DISTRIBUTEREFFORMULA_COUNTERPARTOFLAST1YEAR )
				{					
					totalValue4Ref = 0L; // 总值
					hmap4Ref_po_value = new HashMap<String, Long>(); // 详情
					
					//	参照期间	begin
					int lastYear = UtilPeriod.getPeriodYear( _bSysPeriod.getForecastRunPeriod() ) - 1;	//	去年是指预测开始期间的去年
					int periodNo = UtilPeriod.getPeriodNo( forecastMakeLogHfcItem.getPeriod() );
					
					int periodRef = UtilPeriod.createPeriod( lastYear, periodNo );						
					//	参照期间	end
					
					BBizData distributeRefBizData = _abForecastSetting.getDistributeRefBizData();	//	历史类业务数据					
						
					List<HistoryData> listHistoryData4Ref = daoHistoryData.getHistoryDatas( detailProOrgIdStr, periodRef, distributeRefBizData.getId() );
					if( listHistoryData4Ref != null && !(listHistoryData4Ref.isEmpty()) )
					{
						for( int j=0; j<listHistoryData4Ref.size(); j++ )
						{
							HistoryData historyData4Ref = listHistoryData4Ref.get( j );
							
							totalValue4Ref = totalValue4Ref + Math.round(historyData4Ref.getValue() );
							
							String strKey4po = UtilStrKey.getStrKey4PO( historyData4Ref.getProduct(), historyData4Ref.getOrganization() );
							hmap4Ref_po_value.put( strKey4po, Math.round(historyData4Ref.getValue()) );
						}								
					}
					
				}
				//	获得相应的分解参照信息以进行分解	end
				
				//	分解forecastMakeLogHfcItem上的增量到相应的ForecastData_FcHand上，并更新相关ForecastData_Final		begin
				int sign = 1;	// 这个符号值,用于使 totalIncrementValue distributedIncrementValue incrementValue  为正值,分解时分的恰好
				Long totalIncrementValue = forecastMakeLogHfcItem.getNewValue() - forecastMakeLogHfcItem.getOldValue();
				if( totalIncrementValue < 0 )
				{
					sign = -1;
					totalIncrementValue = 0-totalIncrementValue;
				}
				
				Long distributedIncrementValue = 0L;
				for( int i=0; i<listForecastData_FcHand.size(); i++ )
				{
					//	这里的分解是以已有的  ForecastData_FcHand为核心，不会产生新的ForecastData_FcHand值
					ForecastData forecastData_FcHand = listForecastData_FcHand.get( i );
					
					String strKey4po = UtilStrKey.getStrKey4PO( forecastData_FcHand.getProduct(), forecastData_FcHand.getOrganization() );
					
					Long incrementValue = 0L;
					
					if( totalValue4Ref == 0 )
					{
						//	均分
						incrementValue = Math.round( totalIncrementValue * 1.0/listForecastData_FcHand.size() );
					}
					else
					{
						Long value4Ref = hmap4Ref_po_value.get( strKey4po );
						if( value4Ref == null )
						{
							value4Ref = 0L;
						}
						
						incrementValue = Math.round( totalIncrementValue * (value4Ref * 1.0 / totalValue4Ref) );
					}
					distributedIncrementValue = distributedIncrementValue + incrementValue;
					
					//	保证分净且不超出	begin
					if( distributedIncrementValue > totalIncrementValue )
					{
						incrementValue = incrementValue - (distributedIncrementValue-totalIncrementValue);
						distributedIncrementValue = totalIncrementValue;
					}
					
					if( i == (listForecastData_FcHand.size()-1) )
					{
						//	最后一个
						if( distributedIncrementValue < totalIncrementValue )
						{
							incrementValue = incrementValue + (totalIncrementValue - distributedIncrementValue);
							distributedIncrementValue = totalIncrementValue;
						}
					}
					//	保证分净且不超出	end
					
					//	更新 forecastData_FcHand	begin
					forecastData_FcHand.setValue( forecastData_FcHand.getValue() + sign*incrementValue );
					forecastData_FcHand.setUpdateTime( currentTime );
					
					if(ServerEnvironment.getInstance().isSuitSupport()){
						//查询对应的折合业务数据,为避免多次查数据库，先缓存
						String amountBizCode = forecastData_FcHand.getBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
						BizData amountBizDataFcHand = bizDataMap.get(amountBizCode);
						if(amountBizDataFcHand == null){
							amountBizDataFcHand = daoBizData.getBizDataByCode(amountBizCode);
							bizDataMap.put(amountBizCode, amountBizDataFcHand);
						}						
						forecastData_FcHand.setAmountBizData(amountBizDataFcHand);
						handFcDataList.add(forecastData_FcHand); //折合计算埋伏
					}
					
					
					//	更新相应的 FcFinal	begin
					if( _abForecastSetting.getFinalFcBizData().getBizDataDefItems() != null && !(_abForecastSetting.getFinalFcBizData().getBizDataDefItems().isEmpty()) )
					{
						Iterator<BBizDataDefItem> itr_BBizDataDefItems = _abForecastSetting.getFinalFcBizData().getBizDataDefItems().iterator();
						while( itr_BBizDataDefItems.hasNext() )
						{
							BBizDataDefItemFcComb bBizDataDefItemFcComb = (BBizDataDefItemFcComb)itr_BBizDataDefItems.next();
							if( bBizDataDefItemFcComb.getItemBizData().getId().longValue() == forecastData_FcHand.getBizData().getId().longValue() )
							{
								//	最终预测是受 forecastData_FcHand 影响的
								String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData_FcHand.getProduct(), forecastData_FcHand.getOrganization(), forecastData_FcHand.getPeriod(), bizDataFcFinal );
								ForecastData forecastData_fcFinal = hmap_popb_ForecastData4FcFinal.get( strKey4popb );
								if( forecastData_fcFinal == null )
								{
									//	从数据库中读取
									forecastData_fcFinal = daoForecastData.getForecastData( forecastData_FcHand.getProduct().getId(), forecastData_FcHand.getOrganization().getId(), forecastData_FcHand.getPeriod(), bizDataFcFinal.getId() );
								}
								if( forecastData_fcFinal != null )
								{
									Long newValue = Math.round( forecastData_fcFinal.getValue() + sign*incrementValue*bBizDataDefItemFcComb.getCoefficient() );
									forecastData_fcFinal.setValue( newValue );
									forecastData_fcFinal.setUpdateTime( currentTime );
									
									forecastData_fcFinal.setAmountBizData(amountBizDataFcFinal);
									finalFcDataList.add(forecastData_fcFinal); //折合计算埋伏
									
									hmap_popb_ForecastData4FcFinal.put( strKey4popb, forecastData_fcFinal );
								}
							}
							//	added by liuzhen, 2011.07.02	begin
							//	FcFinal受TimeFc影响、TimeFc受该FcHand影响
							else if( bBizDataDefItemFcComb.getItemBizData().getType() == BizConst.BIZDATA_TYPE_TIMEFC )
							{
								if( bBizDataDefItemFcComb.getItemBizData().getBizDataDefItems() != null && !(bBizDataDefItemFcComb.getItemBizData().getBizDataDefItems().isEmpty()) )
								{
									Iterator<BBizDataDefItem> itr_bBizDataDefItemTimeFc = bBizDataDefItemFcComb.getItemBizData().getBizDataDefItems().iterator();
									if( itr_bBizDataDefItemTimeFc.hasNext() )
									{
										BBizDataDefItemTimeFc bBizDataDefItemTimeFc = (BBizDataDefItemTimeFc) itr_bBizDataDefItemTimeFc.next();
										if( bBizDataDefItemTimeFc.getItemBizData().getId().longValue() == forecastData_FcHand.getBizData().getId().longValue() )
										{
											//	FcFinal of another period is influenced
											int periodDiff2Current = 0;
											
											int timeFormulaDictValue = bBizDataDefItemTimeFc.getTimeFormula();
											
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
											
											int period_influencedByTimeFc = UtilPeriod.getPeriod( forecastData_FcHand.getPeriod(), periodDiff2Current );
											
											
											String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData_FcHand.getProduct(), forecastData_FcHand.getOrganization(), period_influencedByTimeFc, bizDataFcFinal );
											ForecastData forecastData_fcFinal = hmap_popb_ForecastData4FcFinal.get( strKey4popb );
											if( forecastData_fcFinal == null )
											{
												//	从数据库中读取
												forecastData_fcFinal = daoForecastData.getForecastData( forecastData_FcHand.getProduct().getId(), forecastData_FcHand.getOrganization().getId(), period_influencedByTimeFc, bizDataFcFinal.getId() );
											}
											if( forecastData_fcFinal != null )
											{
												Long newValue = Math.round( forecastData_fcFinal.getValue() + sign*incrementValue*bBizDataDefItemFcComb.getCoefficient() );
												forecastData_fcFinal.setValue( newValue );
												forecastData_fcFinal.setUpdateTime( currentTime );
												
												forecastData_fcFinal.setAmountBizData(amountBizDataFcFinal);
												finalFcDataList.add(forecastData_fcFinal); //折合计算埋伏
												
												hmap_popb_ForecastData4FcFinal.put( strKey4popb, forecastData_fcFinal );
											}										
										}
										
									}
								}
							}
							//	added by liuzhen, 2011.07.02	end
						}
					}
					//	更新相应的 FcFinal	end
				}				
				
				//	分解forecastMakeLogHfcItem上的增量到相应的ForecastData_FcHand上，并更新相关ForecastData_FcFinal		end
			}
			
			
			
			//	持久化到数据库	begin
			//	ForecastData_FcHand 和  ForecastData_FcComb 都是查询出来的，会自动更新，不必显式调用
			
			//	ForecastMakeLog	begin
			DaoForecastMakeLog daoForecastMakeLog = new DaoForecastMakeLog( session );
			DaoForecastMakeLogProOrg daoForecastMakeLogProOrg = new DaoForecastMakeLogProOrg( session );
			DaoForecastMakeLogHfcItem daoForecastMakeLogHfcItem = new DaoForecastMakeLogHfcItem( session );
			
			daoForecastMakeLog.save( forecastMakeLog_new );
			
			if( forecastMakeLog_new.getForecastMakeLogProOrgs() != null && !(forecastMakeLog_new.getForecastMakeLogProOrgs().isEmpty()) )
			{
				Iterator<ForecastMakeLogProOrg> itr_ForecastMakeLogProOrgs = forecastMakeLog_new.getForecastMakeLogProOrgs().iterator();
				while( itr_ForecastMakeLogProOrgs.hasNext() )
				{
					daoForecastMakeLogProOrg.save( itr_ForecastMakeLogProOrgs.next() );
				}
			}
			
			if( forecastMakeLog_new.getForecastMakeLogHfcItems() != null && !(forecastMakeLog_new.getForecastMakeLogHfcItems().isEmpty()) )
			{
				itr_ForecastMakeLogHfcItems = forecastMakeLog_new.getForecastMakeLogHfcItems().iterator();
				while( itr_ForecastMakeLogHfcItems.hasNext() )
				{
					daoForecastMakeLogHfcItem.save( itr_ForecastMakeLogHfcItems.next() );
				}
			}			
			//	ForecastMakeLog	end
			
			
			//	持久化到数据库	end
			
			trsa.commit();
			
			long endTime = System.currentTimeMillis();
	        logger.info("预测数据保存成功，耗时["+(endTime-beginTime)+"]ms");
	        
	        amountForecastService.calculateAmountData(handFcDataList);
			amountForecastService.calculateAmountData(finalFcDataList);
	        
	        return saveResultDesc;
	        
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

}
