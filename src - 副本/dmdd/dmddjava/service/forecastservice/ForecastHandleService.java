package dmdd.dmddjava.service.forecastservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastSetting;
import dmdd.dmddjava.dataaccess.aidobject.ABImForecastData;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumCommonData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemKpi;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoney;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoneyComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeHis;
import dmdd.dmddjava.dataaccess.bizobject.BForecastData;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInstNextProOrg;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;
import dmdd.dmddjava.dm.ForecastAssessmentDM;
import dmdd.dmddjava.dm.ForecastDataDM;
import dmdd.dmddjava.dm.HistoryDataDM;
import dmdd.dmddjava.service.uiservice.UiDataQueryService;

public class ForecastHandleService
{
	private Logger logger = CoolLogger.getLogger(this.getClass());	
	protected CommDMO dmo = new CommDMO();
	
	UiDataQueryService dataQueryService = new UiDataQueryService();
	
	/**
	 * 检查 _list4ABProOrg _list4BBizDataFcHand 范围内 是否有预测数据处于 审核冻结状态 
	 */
	public boolean checkForecastDataStatusIsInactive(List<ABProOrg> _list4ABProOrg, List<BBizData> _list4BBizDataFcHand, BSysPeriod _bSysPeriod) throws Exception {
		boolean rstBlIsInactive = false;
		try {
			long time = System.currentTimeMillis();
			logger.info("检验是否有预测数据处理审核冻结状态开始...");
			// 检查服务器状态是否可以提供服务 begin
			ServerEnvironment.getInstance().checkSystemStatus();
			// 检查服务器状态是否可以提供服务 end

			// 检查前端期间是否与服务器一致 begin
			BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
			if (_bSysPeriod == null || bSysPeriod_server == null) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH);
				Exception ex = new Exception(cause);
				throw ex;
			}
			if (_bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod()) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH);
				Exception ex = new Exception(cause);
				throw ex;
			}
			// 检查前端期间是否与服务器一致 end

			if (_list4ABProOrg == null || _list4ABProOrg.isEmpty()) {
				return rstBlIsInactive;
			}
			if (_list4BBizDataFcHand == null || _list4BBizDataFcHand.isEmpty()) {
				return rstBlIsInactive;
			}

			List<Long> list4BizDataId = new ArrayList<Long>();
			for (int i = 0; i < _list4BBizDataFcHand.size(); i++) {
				list4BizDataId.add(_list4BBizDataFcHand.get(i).getId());
			}

			ForecastDataDM dm = new ForecastDataDM();
			rstBlIsInactive = dm.checkForecastDataStatusIsInactive(	UtilProOrg.getIdsScopeStr4ProOrgs1(_list4ABProOrg),	_bSysPeriod.getForecastRunPeriod(), list4BizDataId);

			logger.info("检验数据冻结状态完成，耗时" + (System.currentTimeMillis() - time));
		} catch (Exception e) {
			throw e;
		} finally {
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return rstBlIsInactive;
	}
	
	//	UiRowData	begin
	public List<Object> getUiRowDatas( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData, BSysPeriod _bSysPeriod ) throws Exception
	{
		List<Object> rstList = null;
		try{
			// 检查服务器状态是否可以提供服务 begin
			ServerEnvironment.getInstance().checkSystemStatus();
			// 检查服务器状态是否可以提供服务 end

			if (_list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty()) {
				return null;
			}

			int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
			if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
				return null;
			}
			if (_list4BBizData == null || _list4BBizData.isEmpty()) {
				return null;
			}

			// 检查前端期间是否与服务器一致 begin
			BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
			if (_bSysPeriod == null || bSysPeriod_server == null) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH);
				Exception ex = new Exception(cause);
				throw ex;
			}
			if (_bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod()) {
				Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH);
				Exception ex = new Exception(cause);
				throw ex;
			}
			// 检查前端期间是否与服务器一致 end

			rstList = this.getUiRowDatas( _list4ABUiRowDataProOrg, _periodBegin, _periodEnd, _list4BBizData );
			
			
		}catch (Exception e) {
			
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return rstList;
	}
	
	
	private List<Object> getUiRowDatas( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData ) throws Exception
	{
		long time = System.currentTimeMillis();
		logger.info("开始预测数据查询...");
		if (_list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty()) {
			return null;
		}
		if (_list4BBizData == null || _list4BBizData.isEmpty()) {
			return null;
		}
		int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return null;
		}
		
		//	把业务数据分类		begin
		List<BBizData> list4BBizData4History = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4TimeHis = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4FcMHF = new ArrayList<BBizData>();//FcModel FcHand FcFinal
		List<BBizData> list4BBizData4TimeFc = new ArrayList<BBizData>();//TimeFc : FcComb may depend on TimeFc, so we put TimeFc before FcComb
		List<BBizData> list4BBizData4FcComb = new ArrayList<BBizData>();//FcComb		
		List<BBizData> list4BBizData4Money = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4MoneyComb = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4Kpi = new ArrayList<BBizData>();
		List<BBizData> list4BBizDataAssessment = new ArrayList<BBizData>();
		
		for (int i = 0; i < _list4BBizData.size(); i++) {
			BBizData bBizData = _list4BBizData.get(i);

			if (bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY || bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD
					|| bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR) {
				list4BBizData4History.add(bBizData);
			} else if (bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEHIS) {
				list4BBizData4TimeHis.add(bBizData);
			} else if (bBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL || bBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND
					|| bBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL) {
				list4BBizData4FcMHF.add(bBizData);
			}
			// modified by liuzhen, 2011.07.01 begin
			// FcComb may depend on TimeFc, so we put TimeFc before FcComb
			else if (bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC) {
				list4BBizData4TimeFc.add(bBizData);
			}
			// modified by liuzhen, 2011.07.01 end
			else if (bBizData.getType() == BizConst.BIZDATA_TYPE_FCCOMB) {
				list4BBizData4FcComb.add(bBizData);
			} else if (bBizData.getType() == BizConst.BIZDATA_TYPE_MONEY) {
				list4BBizData4Money.add(bBizData);
			} else if (bBizData.getType() == BizConst.BIZDATA_TYPE_MONEYCOMB) {
				list4BBizData4MoneyComb.add(bBizData);
			} else if (bBizData.getType() == BizConst.BIZDATA_TYPE_KPI) {
				list4BBizData4Kpi.add(bBizData);
			} else if (bBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT) {
				list4BBizDataAssessment.add(bBizData);
			}
		}
		//	把业务数据分类		end
		
		ForecastDataDM dm = new ForecastDataDM();
		//获取策略 begin
		HashMap<Long,ABForecastSetting> hm_settings = new HashMap<Long, ABForecastSetting>();
		//TODO TAG BY ZHANGZY 20150421, 既然是查询预测策略，可以一次性查询完毕,循环单个查询很耗时
		String listDetailProOrgIdStr = UtilProOrg.getIdsScopeStr4ListBProOrgs(_list4ABUiRowDataProOrg);
		List<ABForecastSetting> settings = dm.getForecastSettingFcNum(listDetailProOrgIdStr);
		for (ABForecastSetting setting : settings) {
			if (!hm_settings.containsKey(setting.getForecastInstId())) {
				hm_settings.put(setting.getForecastInstId(), setting);
			}
		}
		//获取策略 end

		List<Object> list_return = new LinkedList<Object>();
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		for( int i=0; i<_list4ABUiRowDataProOrg.size(); i=i+1 )
		{
			logger.debug("检索第["+i+"]条数据开始..");
			//预测策略转移到外部统一查询
			//	对一个abUiRowDataProOrg查询所有需要查询的业务数据的数据
			ABUiRowDataProOrg abUiRowDataProOrg = _list4ABUiRowDataProOrg.get( i );
			
			List<ABUiRowData> tmpList = new ArrayList<ABUiRowData>();	//	本 abUiRowDataProOrg 的所有业务数据的数据，用于帮助计算kppi

			String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( abUiRowDataProOrg );
			
			//	历史类数据		begin
			List<ABUiRowData> list4ABUiRowData4History = this.getUiRowDatas4History( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4History );
			rstList.addAll( list4ABUiRowData4History );
			tmpList.addAll( list4ABUiRowData4History );
			//	历史类数据		end	
			
			//	期间历史数据		begin				
			List<ABUiRowData> list4ABUiRowData4TimeHis = this.getUiRowDatas4TimeHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeHis );
			rstList.addAll( list4ABUiRowData4TimeHis );
			tmpList.addAll( list4ABUiRowData4TimeHis );
			//	期间历史数据		end				
			
			//	预测类数据		begin
			List<ABUiRowData> list4ABUiRowData4FcMHF = this.getUiRowDatas4FcMHF( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcMHF );
			rstList.addAll( list4ABUiRowData4FcMHF );
			tmpList.addAll( list4ABUiRowData4FcMHF );
			//	预测类数据		end	
			
			//	TimeFc		begin
			//	2011.07.01 by liuzhen: FcComb may depend on TimeFc, so we put TimeFc before FcComb 
			List<ABUiRowData> list4ABUiRowData4TimeFc = this.getUiRowDatas4TimeFc( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeFc );
			rstList.addAll( list4ABUiRowData4TimeFc );
			tmpList.addAll( list4ABUiRowData4TimeFc );
			//	TimeFc		end
			
			//预测考核类 begin
			List<ABUiRowData> list4ABUiRowData4ForecastAssessment = getUiRowDatas4ForecastAssessment( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAssessment );
			rstList.addAll( list4ABUiRowData4ForecastAssessment );
			//预测考核类 begin
			
			//	FcComb my depend on TimeFc
			List<ABUiRowData> list4ABUiRowData4FcMHT = new ArrayList<ABUiRowData>();
			list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4FcMHF );
			list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4TimeFc );
			list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4ForecastAssessment );
			List<ABUiRowData> list4ABUiRowData4FcComb = dataQueryService.getUiRowDatas4FcComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcComb, list4ABUiRowData4FcMHT );
			//	modified by liuzhen, 2011.07.01	end
			rstList.addAll( list4ABUiRowData4FcComb );
			tmpList.addAll( list4ABUiRowData4FcComb );
			
			//	金额数据		begin				
			List<ABUiRowData> list4ABUiRowData4Money = dataQueryService.getUiRowDatas4Money( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4Money );
			rstList.addAll( list4ABUiRowData4Money );
			tmpList.addAll( list4ABUiRowData4Money );
			//	金额数据		end
			
			//	组合金额数据		begin				
			List<ABUiRowData> list4ABUiRowData4MoneyComb = this.getUiRowDatas4MoneyComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4MoneyComb, list4ABUiRowData4Money );
			rstList.addAll( list4ABUiRowData4MoneyComb );
			tmpList.addAll( list4ABUiRowData4MoneyComb );
			//	组合金额数据		end
			
			//	Kpi类数据		begin
			List<ABUiRowData> list4ABUiRowData4Kpi = this.getUiRowDatas4Kpi( abUiRowDataProOrg, _periodBegin, _periodEnd, list4BBizData4Kpi, tmpList );
			rstList.addAll( list4ABUiRowData4Kpi );
			//	Kpi类数据		end
		}		
		list_return.add(hm_settings.values());
		list_return.add(rstList);
		
		logger.info("预测数据查询结束,数据行数["+rstList.size()+"]，耗时:"+(System.currentTimeMillis()-time));
		
		return list_return;
	}	
	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的预测类(FcModel FcHand FcFinal)预测数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4FcMHF
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4ForecastAssessment( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> list4BBizDataAssessment ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( list4BBizDataAssessment == null || list4BBizDataAssessment.isEmpty() )
		{
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowDataProOrg );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	查询预测类数据并填充结果数据集	begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		List<Long> list4BizDataId = new ArrayList<Long>();
		
		for( int j=0; j<list4BBizDataAssessment.size(); j=j+1 )
		{
			BBizData bBizData4FcMHF = list4BBizDataAssessment.get( j );
			
			list4BizDataId.add( bBizData4FcMHF.getId() );
			
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4FcMHF );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			hmap_bizDataId_ABUiRowData.put( bBizData4FcMHF.getId(), newABUiRowData );
		}
		//	拼出业务数据范围和结果数据集	end
		
		//	查询预测类数据并填充结果数据集		begin
		List<ASumCommonData> listASumData = null;
		ForecastAssessmentDM dm = new ForecastAssessmentDM();
		listASumData = dm.getForecastAssessment( detailProOrgIdStr, _periodBegin, _periodEnd, list4BizDataId );
		
	
		
		if( listASumData != null && !(listASumData.isEmpty()) )
		{
			for(int j=0; j<listASumData.size(); j=j+1 )
			{
				ASumCommonData aSumData = listASumData.get( j );
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get( aSumData.getBizDataId() );
				if( abUiRowData != null )
				{
					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					abUiRowData.pubFun4setPeriodDataValue( periodLoc, (double)aSumData.getValue() );
					abUiRowData.pubFun4setPeriodDataValueBak( periodLoc, (double)aSumData.getValue() );
					hmap_bizDataId_ABUiRowData.put( aSumData.getBizDataId(), abUiRowData );
				}
			}
		}
		
		if( hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty()) )
		{
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while( itr_hmap_bizDataId_ABUiRowData_values.hasNext() )
			{
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
				if (true || this.isExistForecastDatas(abUiRowData, detailProOrgIdStr) == true) 
				{
					rstList.add( abUiRowData );
				}
			}
		}

		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}

	/**
	 * 检查_abUiRowData的业务范围期间范围业务数据上是否确实有历史数据在数据库里存在
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowData计算其值,否则直接使用
	 * @param _abUiRowData
	 * @param _detailProOrgIdStr
	 * @return
	 * @throws Exception
	 */
	private boolean isExistHistoryDatas( ABUiRowData _abUiRowData, String _detailProOrgIdStr ) throws Exception
	{
		int periodDiff = UtilPeriod.getPeriodDifference( _abUiRowData.getPeriodBegin(), _abUiRowData.getPeriodEnd() );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return false;
		}
		
		for(int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
		{
			Double value = _abUiRowData.pubFun4getPeriodDataValue( periodLoc );
			if( value != null && value.longValue() != 0 )
			{
				return true;
			}
		}
		
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	begin
		boolean isExist = false;
		
		//	拼出明细范围字符串		begin 
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowData );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	期间	begin
		int periodBegin = _abUiRowData.getPeriodBegin();
		int periodEnd = _abUiRowData.getPeriodEnd();
		//	期间	end
		
		//	业务数据	begin
		Long bizDataId = -1L;
		if( _abUiRowData.getBizData().getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
		{
			//	历史调整原因类，应该以其相应的历史调整类来进行检测
			if( _abUiRowData.getBizData().getBizDataDefItems() != null && !(_abUiRowData.getBizData().getBizDataDefItems().isEmpty()) )
			{
				BBizDataDefItemHistoryAdR bBizDataDefItemHistoryAdR = (BBizDataDefItemHistoryAdR)_abUiRowData.getBizData().getBizDataDefItems().iterator().next();
				bizDataId = bBizDataDefItemHistoryAdR.getHistoryAdBizData().getId();
			}
		}
		else
		{
			bizDataId = _abUiRowData.getBizData().getId();
		}
		//	业务数据	end
		
		HistoryDataDM dm = new HistoryDataDM();
		isExist = dm.isExistHistoryDatas( detailProOrgIdStr, _abUiRowData,periodBegin, periodEnd, bizDataId );
			
		
		return isExist;
	}
	
	/**
	 * 检查_abUiRowData的业务范围期间范围业务数据上是否确实有历史数据在数据库里存在，对TimeHis
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowData计算其值,否则直接使用
	 * @param _abUiRowData
	 * @param _detailProOrgIdStr
	 * @param _periodDiff2Current
	 * @return
	 * @throws Exception
	 */
	private boolean isExistHistoryDatas4TimeHis( ABUiRowData _abUiRowData, String _detailProOrgIdStr, int _periodDiff2Current ) throws Exception
	{
		int periodDiff = UtilPeriod.getPeriodDifference( _abUiRowData.getPeriodBegin(), _abUiRowData.getPeriodEnd() );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return false;
		}
		
		for(int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
		{
			Double value = _abUiRowData.pubFun4getPeriodDataValue( periodLoc );
			if( value != null && value.longValue() != 0 )
			{
				return true;
			}
		}
		
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	begin
		boolean isExist = false;
		
		//	拼出明细范围字符串		begin 
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowData );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	期间	begin
		int periodBegin = UtilPeriod.getPeriod( _abUiRowData.getPeriodBegin(), _periodDiff2Current );
		int periodEnd = UtilPeriod.getPeriod( _abUiRowData.getPeriodEnd(), _periodDiff2Current );
		//	期间	end
		
		//	业务数据	begin
		Long bizDataId = -1L;
		if( _abUiRowData.getBizData().getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
		{
			//	历史调整原因类，应该以其相应的历史调整类来进行检测
			if( _abUiRowData.getBizData().getBizDataDefItems() != null && !(_abUiRowData.getBizData().getBizDataDefItems().isEmpty()) )
			{
				BBizDataDefItemHistoryAdR bBizDataDefItemHistoryAdR = (BBizDataDefItemHistoryAdR)_abUiRowData.getBizData().getBizDataDefItems().iterator().next();
				bizDataId = bBizDataDefItemHistoryAdR.getHistoryAdBizData().getId();
			}
		}
		else
		{
			bizDataId = _abUiRowData.getBizData().getId();
		}
		//	业务数据	end		
		
		HistoryDataDM dm = new HistoryDataDM();
		isExist = dm.isExistHistoryDatas( detailProOrgIdStr,_abUiRowData, periodBegin, periodEnd, bizDataId );
			
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	end
		
		return isExist;
	}	
	
	
	/**
	 * 检查_abUiRowData的业务范围期间范围业务数据上是否确实有预测数据在数据库里存在
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowData计算其值,否则直接使用
	 * @param _abUiRowData
	 * @param _detailProOrgIdStr
	 * @return
	 * @throws Exception
	 */
	private boolean isExistForecastDatas( ABUiRowData _abUiRowData, String _detailProOrgIdStr ) throws Exception
	{
		int periodDiff = UtilPeriod.getPeriodDifference( _abUiRowData.getPeriodBegin(), _abUiRowData.getPeriodEnd() );
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return false;
		}

		for (int periodLoc = 0; periodLoc <= periodDiff; periodLoc++) {
			Double value = _abUiRowData.pubFun4getPeriodDataValue(periodLoc);
			if (value != null && value.longValue() != 0) {
				return true;
			}
		}
		
		// 所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录 begin
		boolean isExist = false;

		// 拼出明细范围字符串 begin
		String detailProOrgIdStr = null;
		if (_detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals("")) {
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs(_abUiRowData);
		} else {
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		// 拼出明细范围字符串 end

		// 期间 begin
		int periodBegin = _abUiRowData.getPeriodBegin();
		int periodEnd = _abUiRowData.getPeriodEnd();
		// 期间 end
		ForecastDataDM dm = new ForecastDataDM();
		isExist = dm.isExistForecastDatas(detailProOrgIdStr, _abUiRowData, periodBegin, periodEnd, _abUiRowData.getBizData().getId());

		// 所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录 end

		return isExist;
	}	
	
	/**
	 * 检查_abUiRowData的业务范围期间范围业务数据上是否确实有预测数据在数据库里存在，对TimeFc
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowData计算其值,否则直接使用
	 * @param _abUiRowData
	 * @param _detailProOrgIdStr
	 * @param _periodDiff2Current
	 * @return
	 * @throws Exception
	 */
	private boolean isExistForecastDatas4TimeFc( ABUiRowData _abUiRowData, String _detailProOrgIdStr, int _periodDiff2Current ) throws Exception
	{
		int periodDiff = UtilPeriod.getPeriodDifference( _abUiRowData.getPeriodBegin(), _abUiRowData.getPeriodEnd() );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return false;
		}
		
		for(int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
		{
			Double value = _abUiRowData.pubFun4getPeriodDataValue( periodLoc );
			if( value != null && value.longValue() != 0 )
			{
				return true;
			}
		}
		
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	begin
		boolean isExist = false;
		
		//	拼出明细范围字符串		begin 
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowData );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	期间	begin
		int periodBegin = UtilPeriod.getPeriod( _abUiRowData.getPeriodBegin(), _periodDiff2Current );
		int periodEnd = UtilPeriod.getPeriod( _abUiRowData.getPeriodEnd(), _periodDiff2Current );
		//	期间	end
		
		ForecastDataDM dm = new ForecastDataDM();
		isExist = dm.isExistForecastDatas( detailProOrgIdStr,_abUiRowData, periodBegin, periodEnd, _abUiRowData.getBizData().getId() );
			
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	end
		
		return isExist;
	}	
	
	/**
	 * 检查 _abUiRowData 的业务范围内是否有相应的历史或预测数据存在来决定金额类数据是否存在
	 * @param _abUiRowData
	 * @param _detailProOrgIdStr
	 * @param _itemBizData
	 * @param _listASumMoney
	 * @return
	 * @throws Exception
	 */
	private boolean isExistSumMoneys( ABUiRowData _abUiRowData, String _detailProOrgIdStr, BBizData _itemBizData, List<ASumMoney> _listASumMoney ) throws Exception
	{
		int periodDiff = UtilPeriod.getPeriodDifference( _abUiRowData.getPeriodBegin(), _abUiRowData.getPeriodEnd() );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return false;
		}
		
		if( _listASumMoney == null || _listASumMoney.isEmpty() )
		{
			return false;
		}
		
		for( int i=0; i<_listASumMoney.size(); i++ )
		{
			if( Math.abs( _listASumMoney.get( i ).getValue() ) >= 0.0000001 )
			{
				return true;
			}			
		}
		
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	begin
		boolean isExist = false;
		
		//	拼出明细范围字符串		begin 
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowData );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	期间	begin
		int periodBegin = _abUiRowData.getPeriodBegin();
		int periodEnd = _abUiRowData.getPeriodEnd();
		//	期间	end
		
		//	业务数据	begin
		//	业务数据	end
		
		
			if( _itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
			{
				HistoryDataDM dm = new HistoryDataDM(  );
				isExist = dm.isExistHistoryDatas( detailProOrgIdStr,_abUiRowData, periodBegin, periodEnd, _itemBizData.getId() );				
			}
			else if( _itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
			{
				ForecastDataDM dm = new ForecastDataDM();
				isExist = dm.isExistForecastDatas( detailProOrgIdStr,_abUiRowData, periodBegin, periodEnd, _itemBizData.getId() );				
			}

		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	end
		
		return isExist;
	}	
	
	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的历史类(History HistoryAd HitoryAdR)历史数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4History
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4History( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4History ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();

		if (_abUiRowDataProOrg == null) {
			return rstList;
		}
		if (_list4BBizData4History == null || _list4BBizData4History.isEmpty()) {
			return rstList;
		}

		int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return rstList;
		}
		// 拼出明细范围字符串 begin
		String detailProOrgIdStr = null;
		if (_detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals("")) {
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs(_abUiRowDataProOrg);
		} else {
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		// 拼出明细范围字符串 end

		// 拼出业务数据范围和结果数据集 begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		List<Long> list4BizDataId = new ArrayList<Long>();

		for (int j = 0; j < _list4BBizData4History.size(); j = j + 1) {
			BBizData bBizData4History = _list4BBizData4History.get(j);

			list4BizDataId.add(bBizData4History.getId());

			// 每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct(_abUiRowDataProOrg.getProduct());
			newABUiRowData.setProductCharacter(_abUiRowDataProOrg.getProductCharacter());
			newABUiRowData.setOrganization(_abUiRowDataProOrg.getOrganization());
			newABUiRowData.setOrganizationCharacter(_abUiRowDataProOrg.getOrganizationCharacter());
			newABUiRowData.setDetailProOrgIds(_abUiRowDataProOrg.getDetailProOrgIds());
			newABUiRowData.setBizData(bBizData4History);
			newABUiRowData.setPeriodBegin(_periodBegin);
			newABUiRowData.setPeriodEnd(_periodEnd);
			// 每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData end

			hmap_bizDataId_ABUiRowData.put(bBizData4History.getId(), newABUiRowData);
		}
		// 拼出业务数据范围和结果数据集 end

		// 查询历史类数据并填充结果数据集 begin
		List<ASumCommonData> listASumData = null;
		HistoryDataDM dm = new HistoryDataDM();

		listASumData = dm.getSumHistoryDatas(detailProOrgIdStr, _abUiRowDataProOrg, _periodBegin, _periodEnd, list4BizDataId);

		if (listASumData != null && !(listASumData.isEmpty())) {
			for (int j = 0; j < listASumData.size(); j = j + 1) {
				ASumCommonData aSumData = listASumData.get(j);
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get(aSumData.getBizDataId());
				if (abUiRowData != null) {
					int periodLoc = UtilPeriod.getPeriodDifference(_periodBegin, aSumData.getPeriod());
					abUiRowData.pubFun4setPeriodDataValue(periodLoc, (double) aSumData.getValue());
					abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, (double) aSumData.getValue());
					hmap_bizDataId_ABUiRowData.put(aSumData.getBizDataId(), abUiRowData);
				}
			}
		}

		if (hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty())) {
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while (itr_hmap_bizDataId_ABUiRowData_values.hasNext()) {
				// TODO 此处可能导致系统变慢
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
				if ( true || this.isExistHistoryDatas(abUiRowData, detailProOrgIdStr) == true) 
				{
					rstList.add(abUiRowData);
				}
			}
		}

		// 查询历史类数据并填充结果数据集 end

		return rstList;
	}
	
	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的期间历史类(TimeHis)历史数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4TimeHis
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4TimeHis( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4TimeHis ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( _list4BBizData4TimeHis == null || _list4BBizData4TimeHis.isEmpty() )
		{
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowDataProOrg );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	拼出业务数据范围和结果数据集	begin		
		for( int j=0; j<_list4BBizData4TimeHis.size(); j=j+1 )
		{
			BBizData bBizData4TimeHis = _list4BBizData4TimeHis.get( j );
			
			//	求期间历史业务数据相应的历史业务数据和期间公式	begin
			BBizData itemBizDataHistory = null;
			int periodDiff2Current = 0;
			
			BBizDataDefItem bBizDataDefItem = null;
			if( bBizData4TimeHis.getBizDataDefItems() == null || bBizData4TimeHis.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			Iterator<BBizDataDefItem> itr_bizDataDefItems = bBizData4TimeHis.getBizDataDefItems().iterator();
			bBizDataDefItem = itr_bizDataDefItems.next();

			
			int timeFormulaDictValue = -1;
			if( bBizDataDefItem instanceof BBizDataDefItemTimeHis )
			{
				itemBizDataHistory = ((BBizDataDefItemTimeHis) bBizDataDefItem).getItemBizData();
				timeFormulaDictValue = ((BBizDataDefItemTimeHis) bBizDataDefItem).getTimeFormula();
			}
			else
			{
				continue;
			}	
			
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
			//	求期间历史业务数据相应的历史业务数据和期间公式	end
			
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4TimeHis );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			List<ASumCommonData> listASumData = null;
			
			HistoryDataDM dm = new HistoryDataDM();
			listASumData = dm.getSumHistoryDatas( detailProOrgIdStr,_abUiRowDataProOrg, UtilPeriod.getPeriod( _periodBegin, -periodDiff2Current ), UtilPeriod.getPeriod( _periodEnd, -periodDiff2Current ), itemBizDataHistory.getId() );
				
			
			if( listASumData != null && !(listASumData.isEmpty()) )
			{
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumCommonData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, UtilPeriod.getPeriod( aSumData.getPeriod(), periodDiff2Current ) );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, (double)aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, (double)aSumData.getValue() );
				}
			}
			
			//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
			if( true ||this.isExistHistoryDatas4TimeHis( newABUiRowData, detailProOrgIdStr, periodDiff2Current ) == true )
			{
				rstList.add( newABUiRowData );
			}
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
		}
		//	查询历史类数据并填充结果数据集		end			
		
		return rstList;
	}	
	
	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的预测类(FcModel FcHand FcFinal)预测数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4FcMHF
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4FcMHF( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4FcMHF ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if (_abUiRowDataProOrg == null) {
			return rstList;
		}

		int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return rstList;
		}

		if (_list4BBizData4FcMHF == null || _list4BBizData4FcMHF.isEmpty()) {
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if (_detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals("")) {
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs(_abUiRowDataProOrg);
		} else {
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	查询预测类数据并填充结果数据集	begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		List<Long> list4BizDataId = new ArrayList<Long>();
		
		for( int j=0; j<_list4BBizData4FcMHF.size(); j=j+1 )
		{
			BBizData bBizData4FcMHF = _list4BBizData4FcMHF.get( j );
			
			list4BizDataId.add( bBizData4FcMHF.getId() );
			
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4FcMHF );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			hmap_bizDataId_ABUiRowData.put( bBizData4FcMHF.getId(), newABUiRowData );
		}
		//	拼出业务数据范围和结果数据集	end
		
		//	查询预测类数据并填充结果数据集		begin
		List<ASumCommonData> listASumData = null;
		
		ForecastDataDM dm = new ForecastDataDM();
		//按第一条展示数据，查询数据值
		listASumData = dm.getSumForecastDatas( detailProOrgIdStr,_abUiRowDataProOrg, _periodBegin, _periodEnd, list4BizDataId );
		
		if (listASumData != null && !(listASumData.isEmpty())) {
			for (int j = 0; j < listASumData.size(); j = j + 1) {
				ASumCommonData aSumData = listASumData.get(j);
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get(aSumData.getBizDataId());
				if (abUiRowData != null) {
					int periodLoc = UtilPeriod.getPeriodDifference(_periodBegin, aSumData.getPeriod());
					abUiRowData.pubFun4setPeriodDataValue(periodLoc, (double) aSumData.getValue());
					abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, (double) aSumData.getValue());
					hmap_bizDataId_ABUiRowData.put(aSumData.getBizDataId(), abUiRowData);
				}
			}
		}
		
		if (hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty())) {
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while (itr_hmap_bizDataId_ABUiRowData_values.hasNext()) {
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
				if (true || this.isExistForecastDatas(abUiRowData, detailProOrgIdStr) == true) 
				{
					rstList.add(abUiRowData);
				}
			}
		}
		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}
	
	/**
	 * 2011.07.01 by liuzhen: We put TimeFc before FcComb because FcComb may depend on TimeFc
	 * 鏌ヨ _abUiRowDataProOrg 鎵�埗瀹氱殑涓氬姟鑼冨洿鍐呯殑鏈熼棿棰勬祴绫�TimeFc)棰勬祴鏁版嵁
	 * _detailProOrgIdStr 涓虹┖鏃�闇�鏍规嵁_abUiRowDataProOrg璁＄畻鍏跺�,鍚﹀垯鐩存帴浣跨敤 
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4TimeFc
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4TimeFc( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4TimeFc ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( _list4BBizData4TimeFc == null || _list4BBizData4TimeFc.isEmpty() )
		{
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowDataProOrg );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	查询预测类数据并填充结果数据集	begin		
		for( int j=0; j<_list4BBizData4TimeFc.size(); j=j+1 )
		{
			BBizData bBizData4TimeFc = _list4BBizData4TimeFc.get( j );
			
			//	求期间预测业务数据相应的预测业务数据和期间公式	begin
			BBizData itemBizDataHistory = null;
			int periodDiff2Current = 0;
			
			BBizDataDefItem bBizDataDefItem = null;
			if( bBizData4TimeFc.getBizDataDefItems() == null || bBizData4TimeFc.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			Iterator<BBizDataDefItem> itr_bizDataDefItems = bBizData4TimeFc.getBizDataDefItems().iterator();
			bBizDataDefItem = itr_bizDataDefItems.next();

			
			int timeFormulaDictValue = -1;
			if( bBizDataDefItem instanceof BBizDataDefItemTimeFc )
			{
				itemBizDataHistory = ((BBizDataDefItemTimeFc) bBizDataDefItem).getItemBizData();
				timeFormulaDictValue = ((BBizDataDefItemTimeFc) bBizDataDefItem).getTimeFormula();
			}
			else
			{
				continue;
			}	
			
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
			//	added by liuzhen, 2011.07.01	begin
			else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_LAST1PERIOD )
			{
				periodDiff2Current = 1;
			}
			//	added by liuzhen, 2011.07.01	end
			
			//求期间预测业务数据相应的预测业务数据和期间公式 end
			
			//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4TimeFc );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			List<ASumCommonData> listASumData = null;
			
			ForecastDataDM dm = new ForecastDataDM();		
			listASumData = dm.getSumForecastDatas( detailProOrgIdStr,_abUiRowDataProOrg, UtilPeriod.getPeriod( _periodBegin, -periodDiff2Current ), UtilPeriod.getPeriod( _periodEnd, -periodDiff2Current ), itemBizDataHistory.getId() );
				
			
			if( listASumData != null && !(listASumData.isEmpty()) )
			{
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumCommonData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, UtilPeriod.getPeriod( aSumData.getPeriod(), periodDiff2Current ) );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, (double)aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, (double)aSumData.getValue() );
				}
			}
			
			if( this.isExistForecastDatas4TimeFc( newABUiRowData, detailProOrgIdStr, periodDiff2Current ) == true )
			{
				rstList.add( newABUiRowData );
			}
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
		}
		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}			
	
	/**
	 *  * 查询 _abUiRowDataProOrg 所制定的业务范围内的组合预测类(FcComb)预测数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用 
	 * _list4ABUiRowData4FcMH4exist 是可用的ABUIRowData列表
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4FcComb
	 * @param _list4ABUiRowData4FcMHT4exist
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4FcComb( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4FcComb, List<ABUiRowData> _list4ABUiRowData4FcMHT4exist ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( _list4BBizData4FcComb == null || _list4BBizData4FcComb.isEmpty() )
		{
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowDataProOrg );		
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	收集已经存在的预测类数据	begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		if( _list4ABUiRowData4FcMHT4exist != null )
		{
			for(int i=0; i<_list4ABUiRowData4FcMHT4exist.size(); i++ )
			{
				ABUiRowData abUiRowData = _list4ABUiRowData4FcMHT4exist.get( i );
				hmap_bizDataId_ABUiRowData.put( abUiRowData.getBizData().getId(), abUiRowData );
			}
		}
		//	收集已经存在的预测类数据	end
		
		//	收集还需要查询的预测类业务数据	begin
		List<BBizData> listBBizData4FcMH = new ArrayList<BBizData>();
		
		HashMap<Long, BBizData> hmap_bizDataId_BBizData_FcMH = new HashMap<Long, BBizData>();
		
		//	added by liuzhen, 2011.07.01	begin
		List<BBizData> listBBizData4TimeFc = new ArrayList<BBizData>();
		HashMap<Long, BBizData> hmap_bizDataId_BBizData_TimeFc = new HashMap<Long, BBizData>();		
		//	added by liuzhen, 2011.07.01	end
		
		for( int i=0; i<_list4BBizData4FcComb.size(); i++ )
		{
			BBizData bBizDataFcComb = _list4BBizData4FcComb.get( i );
			if( bBizDataFcComb.getBizDataDefItems() == null || bBizDataFcComb.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataFcComb.getBizDataDefItems().iterator();
			while( itr_BBizDataDefItems.hasNext() )
			{
				BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
				if( bBizDataDefItem instanceof BBizDataDefItemFcComb )
				{
					BBizData itemBizData = ((BBizDataDefItemFcComb)bBizDataDefItem).getItemBizData();
					if( hmap_bizDataId_ABUiRowData.get( itemBizData.getId() ) == null )
					{
						if( itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
								itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND )
						{
							hmap_bizDataId_BBizData_FcMH.put( itemBizData.getId(), itemBizData );
						}
						//	added by liuzhen, 2011.07.01	begin
						else if( itemBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC )
						{
							hmap_bizDataId_BBizData_TimeFc.put( itemBizData.getId(), itemBizData );
						}
						//	added by liuzhen, 2011.07.01	end
					}
				}
			}
		}
		if( hmap_bizDataId_BBizData_FcMH.values() != null && !(hmap_bizDataId_BBizData_FcMH.values().isEmpty()) )
		{
			Iterator<BBizData> itr_hmap_bizDataId_BBizData_FcMH_values = hmap_bizDataId_BBizData_FcMH.values().iterator();
			while( itr_hmap_bizDataId_BBizData_FcMH_values.hasNext() )
			{
				listBBizData4FcMH.add( itr_hmap_bizDataId_BBizData_FcMH_values.next() );
			}
		}
		
		//	added by liuzhen, 2011.07.01	begin
		if( hmap_bizDataId_BBizData_TimeFc.values() != null && !(hmap_bizDataId_BBizData_TimeFc.values().isEmpty()) )
		{
			Iterator<BBizData> itr_hmap_bizDataId_BBizData_TimeFc_values = hmap_bizDataId_BBizData_TimeFc.values().iterator();
			while( itr_hmap_bizDataId_BBizData_TimeFc_values.hasNext() )
			{
				listBBizData4TimeFc.add( itr_hmap_bizDataId_BBizData_TimeFc_values.next() );
			}
		}		
		//	added by liuzhen, 2011.07.01	end
		
		//	收集还需要查询的预测类业务数据 end
		
		//查询预测数据	begin
		if( listBBizData4FcMH != null && !(listBBizData4FcMH.isEmpty()) )
		{
			List<ABUiRowData> list4ABUiRowData4unexist = this.getUiRowDatas4FcMHF( _abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4FcMH );
			if( list4ABUiRowData4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4unexist.get( i );
					//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
					if( true || this.isExistForecastDatas( abUiRowData, detailProOrgIdStr ) == true )
					{
						hmap_bizDataId_ABUiRowData.put( abUiRowData.getBizData().getId(), abUiRowData );
					}
				}
			}			
		}
		//	查询预测数据	end
		
		//	Query the TimeFc Data	begin
		//	added by liuzhen, 2011.07.01	begin
		if( listBBizData4TimeFc != null && !(listBBizData4TimeFc.isEmpty()) )
		{
			List<ABUiRowData> list4ABUiRowData4unexist = this.getUiRowDatas4TimeFc( _abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4TimeFc );
			if( list4ABUiRowData4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4unexist.get( i );
					if( this.isExistForecastDatas( abUiRowData, detailProOrgIdStr ) == true )
					{
						hmap_bizDataId_ABUiRowData.put( abUiRowData.getBizData().getId(), abUiRowData );
					}
				}
			}			
		}		
		//	added by liuzhen, 2011.07.01	end
		//	Query the TimeFc Data	end
		
		//	计算组合预测		begin		
		for( int i=0; i<_list4BBizData4FcComb.size(); i++ )
		{
			BBizData bBizDataFcComb = _list4BBizData4FcComb.get( i );
			if( bBizDataFcComb.getBizDataDefItems() == null || bBizDataFcComb.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizDataFcComb );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			boolean isExist = false;
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataFcComb.getBizDataDefItems().iterator();
			while( itr_BBizDataDefItems.hasNext() )
			{
				BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
				if( bBizDataDefItem instanceof BBizDataDefItemFcComb )
				{
					BBizData itemBizData = ((BBizDataDefItemFcComb)bBizDataDefItem).getItemBizData();
					Double coefficient = ((BBizDataDefItemFcComb)bBizDataDefItem).getCoefficient();
					ABUiRowData abUiRowData_item = hmap_bizDataId_ABUiRowData.get( itemBizData.getId() );
					if( abUiRowData_item != null )
					{
						//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准	begin
						isExist = true;
						//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准	end
						for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
						{
							Double value = newABUiRowData.pubFun4getPeriodDataValue( periodLoc );
							if( value == null )
							{
								value = 0d;
							}
							 
							Double value_item = abUiRowData_item.pubFun4getPeriodDataValue( periodLoc );
							if( value_item == null )
							{
								value_item = 0d;
							}
							
							value = value + value_item * coefficient ;
							newABUiRowData.pubFun4setPeriodDataValue( periodLoc, value );
							newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, value );
						}
					}
				}
			}
			
			if( isExist == true )
			{
				rstList.add( newABUiRowData );
			}
			
		}
		//	计算组合预测		end
				
		return rstList;
	}	
		
	
	
	private List<ABUiRowData> getUiRowDatas4Money( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4Money ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( _list4BBizData4Money == null || _list4BBizData4Money.isEmpty() )
		{
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowDataProOrg );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	查询预测类数据并填充结果数据集	begin		
		for( int j=0; j<_list4BBizData4Money.size(); j=j+1 )
		{
			BBizData bBizData4Money = _list4BBizData4Money.get( j );
			
			//	获取真正去数据库中查询的业务数据和价格类型	begin
			BBizData itemBizData = null;
			int priceTypeDictValue = -1;
			
			if( bBizData4Money.getBizDataDefItems() == null || bBizData4Money.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			
			BBizDataDefItem bBizDataDefItem = bBizData4Money.getBizDataDefItems().iterator().next(); 
			if( bBizDataDefItem instanceof BBizDataDefItemMoney )
			{
				//	itemBizData 只会是 历史类 历史调整类 历史调整原因类 模型预测类 人工预测类 最终预测类
				itemBizData = ((BBizDataDefItemMoney) bBizDataDefItem).getItemBizData();
				priceTypeDictValue = ((BBizDataDefItemMoney) bBizDataDefItem).getPriceType();
			}
			else
			{
				continue;
			}
			//	获取真正去数据库中查询的业务数据和价格类型	end
			
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4Money );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			List<ASumMoney> listASumMoney = null;
			
			
				if( itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
				{
					HistoryDataDM dm = new HistoryDataDM();	
					listASumMoney = dm.getHistoryDataSumMoneys( detailProOrgIdStr,_abUiRowDataProOrg, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );			
				}
				else if(itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL)
				{
					ForecastDataDM dm = new ForecastDataDM();		
					listASumMoney = dm.getForecastDataSumMoneys( detailProOrgIdStr, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );					
				}

			
			if( listASumMoney != null && !(listASumMoney.isEmpty()) )
			{
				for(int k=0; k<listASumMoney.size(); k=k+1 )
				{
					ASumMoney aSumMoney = listASumMoney.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumMoney.getPeriod() );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumMoney.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumMoney.getValue() );
				}
			}
			
			//TODO zzy20150426,不用判断，直接放入结果集中，有问题再调整
			if( true || this.isExistSumMoneys( newABUiRowData, detailProOrgIdStr, itemBizData, listASumMoney ) == true )
			{
				rstList.add( newABUiRowData );
			}
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
		}
		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;		
	}
	
	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的组合金额类(MoneyComb)数据
	 * _detailProOrgIdStr 为空时,需要根据_abUiRowDataProOrg计算其值,否则直接使用 
	 * _list4ABUiRowData4Money4exist 是可用的(金额类)ABUIRowData列表
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4MoneyComb
	 * @param _list4ABUiRowData4Money4exist
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4MoneyComb( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4MoneyComb, List<ABUiRowData> _list4ABUiRowData4Money4exist ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( _list4BBizData4MoneyComb == null || _list4BBizData4MoneyComb.isEmpty() )
		{
			return rstList;
		}
		
		//	拼出明细范围字符串		begin
		String detailProOrgIdStr = null;
		if( _detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals( "" ) )
		{
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( _abUiRowDataProOrg );			
		}
		else
		{
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	收集已经存在的金额类数据	begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData4money = new HashMap<Long, ABUiRowData>();
		if( _list4ABUiRowData4Money4exist != null )
		{
			for(int i=0; i<_list4ABUiRowData4Money4exist.size(); i++ )
			{
				ABUiRowData abUiRowData = _list4ABUiRowData4Money4exist.get( i );
				hmap_bizDataId_ABUiRowData4money.put( abUiRowData.getBizData().getId(), abUiRowData );
			}
		}
		//	收集已经存在的金额类数据	end
		
		//	收集还需要查询的金额类业务数据	begin
		List<BBizData> listBBizData4money = new ArrayList<BBizData>();
		
		HashMap<Long, BBizData> hmap_bizDataId_BBizData_money = new HashMap<Long, BBizData>();
				
		for( int i=0; i<_list4BBizData4MoneyComb.size(); i++ )
		{
			BBizData bBizDataMoneyComb = _list4BBizData4MoneyComb.get( i );
			if( bBizDataMoneyComb.getBizDataDefItems() == null || bBizDataMoneyComb.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataMoneyComb.getBizDataDefItems().iterator();
			while( itr_BBizDataDefItems.hasNext() )
			{
				BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
				if( bBizDataDefItem instanceof BBizDataDefItemMoneyComb )
				{
					BBizData itemBizData = ((BBizDataDefItemMoneyComb)bBizDataDefItem).getItemBizData();
					if( hmap_bizDataId_ABUiRowData4money.get( itemBizData.getId() ) == null )
					{
						if( itemBizData.getType() == BizConst.BIZDATA_TYPE_MONEY )
						{
							hmap_bizDataId_BBizData_money.put( itemBizData.getId(), itemBizData );
						}
					}
				}
			}
		}
		if( hmap_bizDataId_BBizData_money.values() != null && !(hmap_bizDataId_BBizData_money.values().isEmpty()) )
		{
			Iterator<BBizData> itr_hmap_bizDataId_BBizData_money_values = hmap_bizDataId_BBizData_money.values().iterator();
			while( itr_hmap_bizDataId_BBizData_money_values.hasNext() )
			{
				listBBizData4money.add( itr_hmap_bizDataId_BBizData_money_values.next() );
			}
		}	
		//	收集还需要查询的金额类业务数据 end
		
		//	查询金额数据	begin
		if( listBBizData4money != null && !(listBBizData4money.isEmpty()) )
		{
			List<ABUiRowData> list4ABUiRowData4money4unexist = this.getUiRowDatas4Money( _abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4money );
			if( list4ABUiRowData4money4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4money4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4money4unexist.get( i );
					hmap_bizDataId_ABUiRowData4money.put( abUiRowData.getBizData().getId(), abUiRowData );
				}
			}			
		}
		//	查询金额数据	end
		
		
		//	计算组合金额		begin		
		for( int i=0; i<_list4BBizData4MoneyComb.size(); i++ )
		{
			BBizData bBizDataMoneyComb = _list4BBizData4MoneyComb.get( i );
			if( bBizDataMoneyComb.getBizDataDefItems() == null || bBizDataMoneyComb.getBizDataDefItems().isEmpty() )
			{
				continue;
			}
			
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizDataMoneyComb );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			boolean isExist = false;
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataMoneyComb.getBizDataDefItems().iterator();
			while( itr_BBizDataDefItems.hasNext() )
			{
				BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
				if( bBizDataDefItem instanceof BBizDataDefItemMoneyComb )
				{
					BBizData itemBizData = ((BBizDataDefItemMoneyComb)bBizDataDefItem).getItemBizData();
					Double coefficient = ((BBizDataDefItemMoneyComb)bBizDataDefItem).getCoefficient();
					ABUiRowData abUiRowData_item = hmap_bizDataId_ABUiRowData4money.get( itemBizData.getId() );
					if( abUiRowData_item != null )
					{
						//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准	begin
						isExist = true;
						//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准	end
						for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
						{
							Double value = newABUiRowData.pubFun4getPeriodDataValue( periodLoc );
							if( value == null )
							{
								value = 0d;
							}
							 
							Double value_item = abUiRowData_item.pubFun4getPeriodDataValue( periodLoc );
							if( value_item == null )
							{
								value_item = 0d;
							}
							
							value = value + value_item * coefficient ;
							newABUiRowData.pubFun4setPeriodDataValue( periodLoc, value );
							newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, value );
						}
					}
				}
			}
			
			if( isExist == true )
			{
				rstList.add( newABUiRowData );
			}
			
		}
		//	计算组合金额		end
		
		return rstList;		
	}	

	
	/**
	 * 查询 _abUiRowDataProOrg 所制定的业务范围内的Kpi数据 
	 * _list4ABUiRowData4exist 是可用的ABUIRowData列表, 都是对 _abUiRowDataProOrg的，只是业务数据不一样
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4Kpi
	 * @param _list4ABUiRowData4exist
	 * @return	List<ABUiRowData>	结果对象中放的是Kpi数据的分子和分母，而不是实际的Kpi数据
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4Kpi( ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4Kpi, List<ABUiRowData> _list4ABUiRowData4exist ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		if( _abUiRowDataProOrg == null )
		{
			return rstList;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstList;
		}

		if( _list4BBizData4Kpi == null || _list4BBizData4Kpi.isEmpty() )
		{
			return rstList;
		}
		
		//	收集已经存在的业务数据的数据		begin
		HashMap<String, ABUiRowData> hmap_ppcoocb_ABUiRowData = new HashMap<String, ABUiRowData>();	
		String strKey_ppcoocb = null;
		//	_list4ABUiRowData4exist 里的数据的ppcooc都是与_abUiRowDataProOrg一致的，所以，这里只用以 bizDataId为key是对的，但为了稳妥，还是以ppcoocb为key
		if( _list4ABUiRowData4exist != null )
		{
			for(int i=0; i<_list4ABUiRowData4exist.size(); i++ )
			{
				ABUiRowData abUiRowData = _list4ABUiRowData4exist.get( i );
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( abUiRowData.getProduct(), abUiRowData.getProductCharacter(), abUiRowData.getOrganization(), abUiRowData.getOrganizationCharacter(), abUiRowData.getBizData() );
				hmap_ppcoocb_ABUiRowData.put( strKey_ppcoocb, abUiRowData );
			}
		}
		//	收集已经存在的业务数据的数据		end
		
		//	收集还需要查询的业务数据	begin
		List<BBizData> listBBizData4unexist = new ArrayList<BBizData>();
		
		HashMap<Long, BBizData> hmap_bizDataId_BBizData_unexist = new HashMap<Long, BBizData>();
		for( int i=0; i<_list4BBizData4Kpi.size(); i++ )
		{
			BBizData bBizDataKpi = _list4BBizData4Kpi.get( i );
			if( bBizDataKpi.getBizDataDefItems() == null || bBizDataKpi.getBizDataDefItems().isEmpty() || bBizDataKpi.getBizDataDefItems().size() != 1 )
			{
				continue;
			}
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataKpi.getBizDataDefItems().iterator();
			BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
			if( bBizDataDefItem instanceof BBizDataDefItemKpi )
			{
				BBizData bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
				BBizData bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();
				
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( _abUiRowDataProOrg.getProduct(), _abUiRowDataProOrg.getProductCharacter(), _abUiRowDataProOrg.getOrganization(), _abUiRowDataProOrg.getOrganizationCharacter(), bBizData_aitem );
				if( hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb ) == null )
				{
					hmap_bizDataId_BBizData_unexist.put( bBizData_aitem.getId(), bBizData_aitem );
				}
				
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( _abUiRowDataProOrg.getProduct(), _abUiRowDataProOrg.getProductCharacter(), _abUiRowDataProOrg.getOrganization(), _abUiRowDataProOrg.getOrganizationCharacter(), bBizData_bitem );
				if( hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb ) == null )
				{
					hmap_bizDataId_BBizData_unexist.put( bBizData_bitem.getId(), bBizData_bitem );
				}					
			}
		}
		
		if( hmap_bizDataId_BBizData_unexist.values() != null && !(hmap_bizDataId_BBizData_unexist.values().isEmpty()) )
		{
			Iterator<BBizData> itr_hmap_bizDataId_BBizData_unexist_values = hmap_bizDataId_BBizData_unexist.values().iterator();
			while( itr_hmap_bizDataId_BBizData_unexist_values.hasNext() )
			{
				listBBizData4unexist.add( itr_hmap_bizDataId_BBizData_unexist_values.next() );
			}
		}		
		//	收集还需要查询的业务数据	end
		
		//	查询数据	begin
		if( listBBizData4unexist != null && !(listBBizData4unexist.isEmpty()) )
		{
			List<ABUiRowDataProOrg> list4ABUiRowDataProOrg = new ArrayList<ABUiRowDataProOrg>();
			list4ABUiRowDataProOrg.add( _abUiRowDataProOrg );
	
			List<ABUiRowData> list4ABUiRowData4unexist = this.getUiRowDatas1( list4ABUiRowDataProOrg, _periodBegin, _periodEnd, listBBizData4unexist );
			if( list4ABUiRowData4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4unexist.get( i );		
					strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( abUiRowData.getProduct(), abUiRowData.getProductCharacter(), abUiRowData.getOrganization(), abUiRowData.getOrganizationCharacter(), abUiRowData.getBizData() );
					hmap_ppcoocb_ABUiRowData.put( strKey_ppcoocb, abUiRowData );					
				}
			}			
		}
		//	查询数据	end
		
		//	计算Kpi		begin	
		for( int i=0; i<_list4BBizData4Kpi.size(); i++ )
		{
			BBizData bBizDataKpi = _list4BBizData4Kpi.get( i );
			if( bBizDataKpi.getBizDataDefItems() == null || bBizDataKpi.getBizDataDefItems().isEmpty() || bBizDataKpi.getBizDataDefItems().size() != 1 )
			{
				continue;
			}
			
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizDataKpi );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			boolean isExist = false;
			
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataKpi.getBizDataDefItems().iterator();
			BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
			if( bBizDataDefItem instanceof BBizDataDefItemKpi )
			{
				BBizData bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
				BBizData bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();
				
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( newABUiRowData.getProduct(), newABUiRowData.getProductCharacter(), newABUiRowData.getOrganization(), newABUiRowData.getOrganizationCharacter(), bBizData_aitem );
				ABUiRowData abUiRowData_aitem = hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb );
				
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( newABUiRowData.getProduct(), newABUiRowData.getProductCharacter(), newABUiRowData.getOrganization(), newABUiRowData.getOrganizationCharacter(), bBizData_bitem );
				ABUiRowData abUiRowData_bitem = hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb );
				
				if( abUiRowData_aitem != null && abUiRowData_bitem != null )
				{
					//	严格条件，只有两个定义项都存在时才算存在	begin
					isExist = true;
					//	严格条件，只有两个定义项都存在时才算存在	end
					
					for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
					{
						Double value_aitem = abUiRowData_aitem.pubFun4getPeriodDataValue( periodLoc );
						if( value_aitem == null )
						{
							value_aitem = 0d;
						}
						
						Double value_bitem = abUiRowData_bitem.pubFun4getPeriodDataValue( periodLoc );
						if( value_bitem == null )
						{
							value_bitem = 0d;
						}
						
						newABUiRowData.pubFun4setPeriodDataValue( periodLoc, value_aitem );
						newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, value_bitem );						
						
//						//	以整数表示百分比
//						Long value_kpi = 0L;
//						if( value_bitem.longValue() == 0 )
//						{
//							value_kpi = 0L;
//						}
//						else
//						{
//							value_kpi = Math.round( value_aitem*1.0/value_bitem * 100 );;
//						}
//
//						newABUiRowData.pubFun4setPeriodDataValue( periodLoc, value_kpi );
//						newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, value_kpi );
					}					
				}
			}
			
			if( isExist == true )
			{
				rstList.add( newABUiRowData );
			}
		}
		//	计算Kpi		end
				
		return rstList;
	}		
	
	//	UiRowData	end	

	private List<ABUiRowData> getUiRowDatas1( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData ) throws Exception
	{
		if( _list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty() )
		{
			return null;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return null;
		}

		if( _list4BBizData == null || _list4BBizData.isEmpty() )
		{
			return null;
		}
		
		//	把业务数据分类		begin
		List<BBizData> list4BBizData4History = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4TimeHis = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4FcMHF = new ArrayList<BBizData>();	//	FcModel FcHand FcFinal
		List<BBizData> list4BBizData4TimeFc = new ArrayList<BBizData>(); // TimeFc : FcComb may depend on TimeFc, so we put TimeFc before FcComb
		List<BBizData> list4BBizData4FcComb = new ArrayList<BBizData>();	//	FcComb		
		List<BBizData> list4BBizData4Money = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4MoneyComb = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4Kpi = new ArrayList<BBizData>();
		List<BBizData> list4BBizDataAssessment = new ArrayList<BBizData>();
		
		for( int i=0; i<_list4BBizData.size(); i++ )
		{
			BBizData bBizData = _list4BBizData.get( i );
			
			if( bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
				bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD || 
				bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
			{
				list4BBizData4History.add( bBizData );
			}
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEHIS )
			{
				list4BBizData4TimeHis.add( bBizData );
			}
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					bBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
					bBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
			{
				list4BBizData4FcMHF.add( bBizData );
			}		
			//	modified by liuzhen, 2011.07.01	begin
			//	FcComb may depend on TimeFc, so we put TimeFc before FcComb
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC )
			{
				list4BBizData4TimeFc.add( bBizData );
			}
			//	modified by liuzhen, 2011.07.01	end
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_FCCOMB )
			{
				list4BBizData4FcComb.add( bBizData );
			}		
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_MONEY )
			{
				list4BBizData4Money.add( bBizData );
			}			
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_MONEYCOMB )
			{
				list4BBizData4MoneyComb.add( bBizData );
			}						
			else if( bBizData.getType() == BizConst.BIZDATA_TYPE_KPI )
			{
				list4BBizData4Kpi.add( bBizData );
			}	
			else if(bBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT)
			{
				list4BBizDataAssessment.add( bBizData );
			}
		}		
		//	把业务数据分类		end
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		for( int i=0; i<_list4ABUiRowDataProOrg.size(); i=i+1 )
		{
			//	对一个abUiRowDataProOrg查询所有需要查询的业务数据的数据
			ABUiRowDataProOrg abUiRowDataProOrg = _list4ABUiRowDataProOrg.get( i );
			
			List<ABUiRowData> tmpList = new ArrayList<ABUiRowData>();	//	本 abUiRowDataProOrg 的所有业务数据的数据，用于帮助计算kppi

			//	拼出明细范围字符串		begin
			String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( abUiRowDataProOrg );
			//	拼出明细范围字符串		end
			
			//	历史类数据		begin
			List<ABUiRowData> list4ABUiRowData4History = this.getUiRowDatas4History( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4History );
			rstList.addAll( list4ABUiRowData4History );
			tmpList.addAll( list4ABUiRowData4History );
			//	历史类数据		end	
			
			//	期间历史数据		begin				
			List<ABUiRowData> list4ABUiRowData4TimeHis = this.getUiRowDatas4TimeHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeHis );
			rstList.addAll( list4ABUiRowData4TimeHis );
			tmpList.addAll( list4ABUiRowData4TimeHis );
			//	期间历史数据		end				
			
			//	预测类数据		begin
			List<ABUiRowData> list4ABUiRowData4FcMHF = this.getUiRowDatas4FcMHF( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcMHF );
			rstList.addAll( list4ABUiRowData4FcMHF );
			tmpList.addAll( list4ABUiRowData4FcMHF );
			//	预测类数据		end	
			
			//	TimeFc		begin
			//	2011.07.01 by liuzhen: FcComb may depend on TimeFc, so we put TimeFc before FcComb 
			List<ABUiRowData> list4ABUiRowData4TimeFc = this.getUiRowDatas4TimeFc( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeFc );
			rstList.addAll( list4ABUiRowData4TimeFc );
			tmpList.addAll( list4ABUiRowData4TimeFc );
			//	TimeFc		end
			
			//	缁勫悎棰勬祴鏁版嵁		begin
			//	modified by liuzhen, 2011.07.01	begin
			/*
			List<ABUiRowData> list4ABUiRowData4FcComb = this.getUiRowDatas4FcComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcComb, list4ABUiRowData4FcMHF );
			*/
			//	FcComb my depend on TimeFc
			List<ABUiRowData> list4ABUiRowData4FcMHT = new ArrayList<ABUiRowData>();
			list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4FcMHF );
			list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4TimeFc );
			List<ABUiRowData> list4ABUiRowData4FcComb = this.getUiRowDatas4FcComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcComb, list4ABUiRowData4FcMHT );
			//	modified by liuzhen, 2011.07.01	end
			rstList.addAll( list4ABUiRowData4FcComb );
			tmpList.addAll( list4ABUiRowData4FcComb );
			//	缁勫悎棰勬祴鏁版嵁		end			
			
			//	金额数据		begin				
			List<ABUiRowData> list4ABUiRowData4Money = this.getUiRowDatas4Money( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4Money );
			rstList.addAll( list4ABUiRowData4Money );
			tmpList.addAll( list4ABUiRowData4Money );
			//	金额数据		end
			
			//	组合金额数据		begin				
			List<ABUiRowData> list4ABUiRowData4MoneyComb = this.getUiRowDatas4MoneyComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4MoneyComb, list4ABUiRowData4Money );
			rstList.addAll( list4ABUiRowData4MoneyComb );
			tmpList.addAll( list4ABUiRowData4MoneyComb );
			//	组合金额数据		end
			
			//预测考核类 begin
			List<ABUiRowData> list4ABUiRowData4ForecastAssessment = getUiRowDatas4ForecastAssessment( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAssessment );
			rstList.addAll( list4ABUiRowData4ForecastAssessment );
			//预测考核类 begin
			
			//	Kpi类数据		begin
			List<ABUiRowData> list4ABUiRowData4Kpi = this.getUiRowDatas4Kpi( abUiRowDataProOrg, _periodBegin, _periodEnd, list4BBizData4Kpi, tmpList );
			rstList.addAll( list4ABUiRowData4Kpi );
			//	Kpi类数据		end
		}
		
		return rstList;
	}
	
	/**
	 * 保存从接口导入的判断预测数据,根据BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_SI来定义主数据。
	 * @param _bizDataHand 业务数据
	 * @param _bUnitGroup
	 * @param _list4ABImForecastData
	 * @return
	 * @throws Exception
	 */
	public List<ABImForecastData> saveFocastDatas4ImportUI( BBizData _bizDataHand, BUnitGroup _bUnitGroup, List<ABImForecastData> _list4ABImForecastData ) throws Exception
	{
		//	检查服务器状态是否可以提供服务
		ServerEnvironment.getInstance().checkSystemStatus();
		
		long beginTime = System.currentTimeMillis();
		logger.info("预测数据导入开始...");
		
		List<ABImForecastData> rstList = new ArrayList<ABImForecastData>();
		if( _bizDataHand == null )
		{
			throw new Exception("Parameter is not correct");
		}
		if( _list4ABImForecastData == null || _list4ABImForecastData.isEmpty() )
		{
			throw new Exception("Parameter is not correct");
		}
		if( _bUnitGroup == null )
		{
			throw new Exception("Parameter is not correct");
		}
		
		//获取有效预测策略中所有的业务范围
		List<AProOrg> listProOrg4FrorecastIns = new ArrayList<AProOrg>();
		HashMap<String,Product> proCodes = new HashMap<String,Product>();
		HashMap<String,Organization> orgCodes = new HashMap<String,Organization>();
		HashMap<String,String> proorgCodes = new HashMap<String, String>();
		Session session_ = HibernateSessionFactory.getSession();
		Transaction trsa_ = null;
		try
		{
			trsa_ = session_.beginTransaction();

			DaoForecastInst daoForecastInst = new DaoForecastInst( session_ );
			Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES };
			List<ForecastInst> listForecastInst_inDB = daoForecastInst.getAllForecastInsts( arr4ForecastInstIsValid );	

			for(ForecastInst forecastInst:listForecastInst_inDB)
			{
				if(forecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES)
				{					
					for( ForecastInstNextProOrg forecastInstNextProOrg:forecastInst.getForecastInstNextProOrgs())
					{
						AProOrg proorg_ = new AProOrg();
						proorg_.setProduct(forecastInstNextProOrg.getProduct());
						proorg_.setOrganization(forecastInstNextProOrg.getOrganization());						
						listProOrg4FrorecastIns.add(proorg_);	
						proCodes.put(forecastInstNextProOrg.getProduct().getCode(),forecastInstNextProOrg.getProduct());
						orgCodes.put(forecastInstNextProOrg.getOrganization().getCode(),forecastInstNextProOrg.getOrganization());
						
						proorgCodes.put(forecastInstNextProOrg.getProduct().getCode()+"_"+forecastInstNextProOrg.getOrganization().getCode()
									, forecastInstNextProOrg.getOrganization().getCode());
					}					
				}
			}
			
			ABImForecastData abImForecastData = null;
			String importResult = null;
			
			int impNumber = 0;
			Iterator<ABImForecastData> itr_list4ABImHistoryData = _list4ABImForecastData.iterator();
			
			//起始、结束期间检查一次即可
			boolean isPeriodChecked = false;
			int periodBegin = SysConst.PERIOD_NULL;
			int periodEnd = SysConst.PERIOD_NULL;
			
			while( itr_list4ABImHistoryData.hasNext() )
			{
				// 逐行处理，要检查是否是明细层的产品、组织编码
				abImForecastData = itr_list4ABImHistoryData.next();
				
				if(isPeriodChecked == false){
					// 起始期间检查, 
					periodBegin = abImForecastData.getPeriodBegin();
					if ( periodBegin == SysConst.PERIOD_NULL ){
						importResult = "There is no Begin Period";
						abImForecastData.setImportResult( importResult );
						rstList.add( abImForecastData );
						break;
					}
					
					// 终止期间检查
					periodEnd = abImForecastData.getPeriodEnd();
					if ( periodEnd == SysConst.PERIOD_NULL ){
						importResult = "There is no End Period";
						abImForecastData.setImportResult( importResult );
						rstList.add( abImForecastData );
						break;
					}
					isPeriodChecked = true;//期间OK
				}
			
				Session session = HibernateSessionFactory.getSession();
				//Transaction trsa = null;		
				try
				{
					//trsa = session.beginTransaction();
					DaoForecastData daoForecastData = new DaoForecastData( session );
					
					boolean productOk = true;
					boolean orgOk = true;
					boolean isProOrgInForecast = false;

					//产品组织直接在下期预测范围内
					if( proorgCodes.containsKey(abImForecastData.getProductCode()+"_"+abImForecastData.getOrganizationCode() ) )
					{
						isProOrgInForecast=true;
					}
					
					ForecastDataDM dm = new ForecastDataDM();
					BProduct imProduct = ServerEnvironment.getInstance().getBProduct(abImForecastData.getProductCode());
					BOrganization imOrganization = ServerEnvironment.getInstance().getBOrganization(abImForecastData.getOrganizationCode() );;
					if(!isProOrgInForecast)//不包含在有效的预测下期范围内
					{
						// 产品检查
						if( imProduct == null )
						{
							importResult = "Can not find Detail Product by the Code";
							abImForecastData.setImportResult( importResult );					
							productOk= false;					
						}
						
						// 组织检查
						if( imOrganization == null )
						{
							importResult = "Can not find Detail Organization by the Code";
							abImForecastData.setImportResult( importResult );					
							orgOk = false;
						}
						
						if( imProduct.getIsCatalog() == 1 || imOrganization.getIsCatalog() ==1){
							importResult = "非明细层业务范围数据暂时不支持导入！";
							abImForecastData.setImportResult( importResult );
							rstList.add( abImForecastData );
							continue;
						}
						
						if(productOk == false && orgOk==false)
						{
							abImForecastData.setWarnInfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_3);
							rstList.add( abImForecastData );
							continue;	
						}
						else if(productOk == false && orgOk==true)
						{
							abImForecastData.setWarnInfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_1);
							rstList.add( abImForecastData );
							continue;	
						}
						else if(productOk ==  true && orgOk == false)
						{
							abImForecastData.setWarnInfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_2);
							rstList.add( abImForecastData );
							continue;	
						}
						else if(productOk == true && orgOk == true)//判断产品和组织都有,但是在下期预测有效范围之外的
						{		
							//是否允许导入？						
						}		
					}
					
					List<BForecastData> listForecastData_new = new ArrayList<BForecastData>();
					List<BForecastData> listForecastData_upd = new ArrayList<BForecastData>();
					
					//判断是否是明细产品、组织，明细的业务范围数据可以直接导入，非明细的业务范围数据需要分解后保存
					if(imProduct.getIsCatalog()== 0 && imOrganization.getIsCatalog()==0 ){
						//明细的业务范围数据可以直接导入
						// 处理每月数据 begin
						//TODO 需要从当前期间开始
						int periodDiff = UtilPeriod.getPeriodDifference( periodBegin, periodEnd );
						for ( int periodLoc = 0; periodLoc <= periodDiff; periodLoc++ )
						{
							Long value = abImForecastData.pubFun4getPeriodDataValue( periodLoc );
							int period = UtilPeriod.getPeriod( periodBegin, periodLoc );
							BForecastData fcdata = new BForecastData();
							fcdata.setProduct(imProduct);
							fcdata.setOrganization(imOrganization);
							fcdata.setBizData(_bizDataHand);
							fcdata.setPeriod(period);
							fcdata.setValue(value);
							fcdata.setUpdateTime(new Date());
							fcdata.setComments("从界面导入更新");
							
							ForecastData forecastData_inDB = daoForecastData.getForecastData( imProduct.getId(), imOrganization.getId(), period, _bizDataHand.getId());
							if( forecastData_inDB == null )
							{
								//新建预测数据
								fcdata.setInitTime(new Date());
								listForecastData_new.add( fcdata );
							}
							else
							{
								fcdata.setId(forecastData_inDB.getId());
								listForecastData_upd.add( fcdata );
								//	更新历史数据		end
							}
						}
						// 处理每月数据 end
					}else{
						//分解到明细后处理
						
					}
					
					/** 取消单位组检查，高层可以不需要单位组
					if( imProduct.getUnitGroup() == null )
					{
						importResult = "Detail Product has no UnitGroup";
						abImForecastData.setImportResult( importResult );
						rstList.add( abImForecastData );
						continue;					
					}
					if( imProduct.getUnitGroup().getId().longValue() != _bUnitGroup.getId().longValue() )
					{
						importResult = "Detail Product's UOM Group does not match with that of parameter";
						abImForecastData.setImportResult( importResult );
						rstList.add( abImForecastData );
						continue;							
					} */
					
					// 调用持久化方法 begin
					if( !(listForecastData_new.isEmpty()) )
					{
						dm.insertForecastData(listForecastData_new);					
					}
					
					if( !(listForecastData_upd.isEmpty()) )
					{
						dm.updateForecastData(listForecastData_upd);			
					}	
					
					//	导入成功	begin
					importResult = BizConst.IMPORT_RESULT_SUCCESS;
					abImForecastData.setImportResult( importResult );
					impNumber +=  listForecastData_new.size()+listForecastData_upd.size();
					
					rstList.add( abImForecastData );
					//	导入成功	end				
				}
				catch( Exception ex )
				{
					ex.printStackTrace();
				
					if( ex.getCause() != null )
					{
						importResult = ex.getCause().getMessage();
					}
					else
					{
						importResult = ex.getMessage();
					}
					abImForecastData.setImportResult( importResult );
					rstList.add( abImForecastData );				
				}
				finally
				{
					session.close();
				}
				
				//一行导入数据处理完成
			}
			
			long curTime = System.currentTimeMillis();
			logger.info("预测数据导入成功!共处理["+impNumber+"]条数据，耗时["+(curTime-beginTime)+"]ms");
			
		}
		catch( Exception ex )
		{
			if( trsa_ != null )
			{
				trsa_.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if(session_.isConnected() ||session_.isOpen())
			{
				session_.close();
			}
		}	
		return rstList;
	}
}
