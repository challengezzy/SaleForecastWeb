package dmdd.dmddjava.service.uiservice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
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
import dmdd.dmddjava.dataaccess.bizobject.BConversionSet;
import dmdd.dmddjava.dataaccess.bizobject.BConversionType;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.dataaccess.bizobject.BUnit;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastAssessment;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.service.conversion.ConversionService;

/**
 * @author liuzhen
 *
 */
public class UiRowDataService
{
	private Logger logger = CoolLogger.getLogger(this.getClass());

	private UiDataQueryService queryService = new UiDataQueryService();
	
	/**
	 * 对ui提供的接口，检验前端期间是否和服务器一致
	 * @param _list4ABUiRowDataProOrg
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData
	 * @param _bSysPeriod
	 * @return
	 * @throws Exception
	 */
	public List<ABUiRowData> getUiRowDatas( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData, BSysPeriod _bSysPeriod ) throws Exception
	{
		String uuid = UUID.randomUUID().toString();
		logger.info("线程池查询UiRowData开始["+uuid+"]...");
		//long time = System.currentTimeMillis();
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

		long begin = System.currentTimeMillis();
		List<ABUiRowData> rstList = queryService.getUiRowDatas(_list4ABUiRowDataProOrg, _periodBegin, _periodEnd, _list4BBizData);
		logger.info("线程池查询UiRowData结束,行数" + rstList.size() + ",耗时:" + (System.currentTimeMillis()-begin) +", ["+uuid+"]");
		
		return rstList;
	}
	
	
	private List<ABUiRowData> getUiRowDatas( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData ) throws Exception
	{
		//检验产品组织数据
		if (_list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty()) {
			return null;
		}
		//检验期间
		int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return null;
		}

		//业务数据
		if (_list4BBizData == null || _list4BBizData.isEmpty()) {
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
		List<ASumData> listASumData = null;
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastAssessment daoForecastData = new DaoForecastAssessment( session );			
			listASumData = daoForecastData.getForecastAssessment( detailProOrgIdStr, _periodBegin, _periodEnd, list4BizDataId );
			trsa.commit();
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
	
		
		if( listASumData != null && !(listASumData.isEmpty()) )
		{
			for(int j=0; j<listASumData.size(); j=j+1 )
			{
				ASumData aSumData = listASumData.get( j );
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get( aSumData.getBizData().getId() );
				if( abUiRowData != null )
				{
					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					abUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					abUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
					hmap_bizDataId_ABUiRowData.put( aSumData.getBizData().getId(), abUiRowData );
				}
			}
		}
		
		if( hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty()) )
		{
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while( itr_hmap_bizDataId_ABUiRowData_values.hasNext() )
			{
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				if( this.isExistForecastDatas( abUiRowData, detailProOrgIdStr ) == true )
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
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );
			isExist = daoHistoryData.isExistHistoryDatas( detailProOrgIdStr, periodBegin, periodEnd, bizDataId );
			trsa.commit();
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
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	end
		
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
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );
			isExist = daoHistoryData.isExistHistoryDatas( detailProOrgIdStr, periodBegin, periodEnd, bizDataId );
			trsa.commit();
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
		
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastData daoForecastData = new DaoForecastData( session );
			isExist = daoForecastData.isExistForecastDatas( detailProOrgIdStr, periodBegin, periodEnd, _abUiRowData.getBizData().getId() );
			trsa.commit();
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
		//	所有的值都是0 ，通过数据库查询来判断范围之内是否有数据记录	end
		
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
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastData daoForecastData = new DaoForecastData( session );
			isExist = daoForecastData.isExistForecastDatas( detailProOrgIdStr, periodBegin, periodEnd, _abUiRowData.getBizData().getId() );
			trsa.commit();
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
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			if( _itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
			{
				DaoHistoryData daoHistoryData = new DaoHistoryData( session );
				isExist = daoHistoryData.isExistHistoryDatas( detailProOrgIdStr, periodBegin, periodEnd, _itemBizData.getId() );				
			}
			else if( _itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
			{
				DaoForecastData daoForecastData = new DaoForecastData( session );
				isExist = daoForecastData.isExistForecastDatas( detailProOrgIdStr, periodBegin, periodEnd, _itemBizData.getId() );				
			}

			trsa.commit();
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

		int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
		if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
			return rstList;
		}

		if (_list4BBizData4History == null || _list4BBizData4History.isEmpty()) {
			return rstList;
		}
		
		// 拼出明细范围字符串 begin
		String detailProOrgIdStr = null;
		if (_detailProOrgIdStr == null || _detailProOrgIdStr.trim().equals("")) {
			detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs(_abUiRowDataProOrg);
		} else {
			detailProOrgIdStr = _detailProOrgIdStr;
		}
		//	拼出明细范围字符串		end
		
		//	拼出业务数据范围和结果数据集	begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		List<Long> list4BizDataId = new ArrayList<Long>();
		
		for( int j=0; j<_list4BBizData4History.size(); j=j+1 )
		{
			BBizData bBizData4History = _list4BBizData4History.get( j );
			
			list4BizDataId.add( bBizData4History.getId() );
			
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4History );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			hmap_bizDataId_ABUiRowData.put( bBizData4History.getId(), newABUiRowData );
		}
		//	拼出业务数据范围和结果数据集	end
		
		//	查询历史类数据并填充结果数据集		begin
		List<ASumData> listASumData = null;
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try {
			trsa = session.beginTransaction();
			DaoHistoryData daoHistoryData = new DaoHistoryData(session);
			listASumData = daoHistoryData.getSumHistoryDatas(_abUiRowDataProOrg.getDetailProOrgIds(), _periodBegin, _periodEnd, list4BizDataId);
			trsa.commit();
		} catch (Exception ex) {
			if (trsa != null) {
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		} finally {
			session.close();
		}
		
		if( listASumData != null && !(listASumData.isEmpty()) )
		{
			for(int j=0; j<listASumData.size(); j=j+1 )
			{
				ASumData aSumData = listASumData.get( j );
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get( aSumData.getBizData().getId() );
				if( abUiRowData != null )
				{
					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					abUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					abUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
					hmap_bizDataId_ABUiRowData.put( aSumData.getBizData().getId(), abUiRowData );
				}
			}
		}
		
		if( hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty()) )
		{
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while( itr_hmap_bizDataId_ABUiRowData_values.hasNext() )
			{
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				if( this.isExistHistoryDatas( abUiRowData, detailProOrgIdStr ) == true )
				{
					rstList.add( abUiRowData );
				}
			}
		}

		//	查询历史类数据并填充结果数据集		end			
		
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
			
			List<ASumData> listASumData = null;
			
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;
			try
			{
				trsa = session.beginTransaction();
				DaoHistoryData daoHistoryData = new DaoHistoryData( session );		 
				listASumData = daoHistoryData.getSumHistoryDatas( _abUiRowDataProOrg.getDetailProOrgIds(),
						UtilPeriod.getPeriod( _periodBegin, -periodDiff2Current ), UtilPeriod.getPeriod( _periodEnd, -periodDiff2Current ), itemBizDataHistory.getId() );
				trsa.commit();
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
			
			if( listASumData != null && !(listASumData.isEmpty()) )
			{
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, UtilPeriod.getPeriod( aSumData.getPeriod(), periodDiff2Current ) );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
				}
			}
			
			if( this.isExistHistoryDatas4TimeHis( newABUiRowData, detailProOrgIdStr, periodDiff2Current ) == true )
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
		
		// 拼出明细范围字符串 begin
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
		List<ASumData> listASumData = null;
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try {
			trsa = session.beginTransaction();
			DaoForecastData daoForecastData = new DaoForecastData(session);
			listASumData = daoForecastData.getSumForecastDatas(_abUiRowDataProOrg.getDetailProOrgIds(), _periodBegin, _periodEnd, list4BizDataId);
			trsa.commit();
		} catch (Exception ex) {
			if (trsa != null) {
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		} finally {
			session.close();
		}
		
		if (listASumData != null && !(listASumData.isEmpty())) {
			for (int j = 0; j < listASumData.size(); j = j + 1) {
				ASumData aSumData = listASumData.get(j);
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get(aSumData.getBizData().getId());
				if (abUiRowData != null) {
					int periodLoc = UtilPeriod.getPeriodDifference(_periodBegin, aSumData.getPeriod());
					abUiRowData.pubFun4setPeriodDataValue(periodLoc, aSumData.getValue());
					abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, aSumData.getValue());
					hmap_bizDataId_ABUiRowData.put(aSumData.getBizData().getId(), abUiRowData);
				}
			}
		}

		if (hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty())) {
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while (itr_hmap_bizDataId_ABUiRowData_values.hasNext()) {
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				if (this.isExistForecastDatas(abUiRowData, detailProOrgIdStr) == true) {
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
			
			List<ASumData> listASumData = null;
			
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;
			try
			{
				trsa = session.beginTransaction();
				DaoForecastData daoForecastData = new DaoForecastData( session );			
				listASumData = daoForecastData.getSumForecastDatas( _abUiRowDataProOrg.getDetailProOrgIds(), UtilPeriod.getPeriod( _periodBegin, -periodDiff2Current ), UtilPeriod.getPeriod( _periodEnd, -periodDiff2Current ), itemBizDataHistory.getId() );
				trsa.commit();
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
			
			if( listASumData != null && !(listASumData.isEmpty()) )
			{
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, UtilPeriod.getPeriod( aSumData.getPeriod(), periodDiff2Current ) );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
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
					if( this.isExistForecastDatas( abUiRowData, detailProOrgIdStr ) == true )
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
							
							//value = Math.round( value + value_item * coefficient );
							value =  value + value_item * coefficient ;
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
			
			Session session = HibernateSessionFactory.getSession();
			Transaction trsa = null;
			try
			{
				trsa = session.beginTransaction();
				if( itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
				{
					DaoHistoryData daoHistoryData = new DaoHistoryData( session );			
					listASumMoney = daoHistoryData.getHistoryDataSumMoneys( detailProOrgIdStr, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );			
				}
				else if(itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL)
				{
					DaoForecastData daoForecastData = new DaoForecastData( session );			
					listASumMoney = daoForecastData.getForecastDataSumMoneys( detailProOrgIdStr, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );					
				}

				trsa.commit();
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
			
			if( this.isExistSumMoneys( newABUiRowData, detailProOrgIdStr, itemBizData, listASumMoney ) == true )
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
							
							//value = Math.round( value + value_item * coefficient );
							value =  value + value_item * coefficient ;
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
	
			List<ABUiRowData> list4ABUiRowData4unexist = this.getUiRowDatas( list4ABUiRowDataProOrg, _periodBegin, _periodEnd, listBBizData4unexist );
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

	//权重报表 begin
	
	/**
	 * 为公式∑|A(t)-B(t)|/∑A(t)单独开发的权重报表
	 */
	public List<ABUiRowData> getUiRowDatas4Wight1(List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg,
		int _periodBegin, int _periodEnd, 
		BBizData _BBizDataKpi, BSysPeriod _bSysPeriod,int _BaseBizDataIndex,List<ABUiRowDataProOrg> _list4baseABUiRowDataProOrg
		) throws Exception
		{
//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//		检查服务器状态是否可以提供服务	end
	
		if( _list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty() )
		{
			return null;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return null;
		}

		if( _BBizDataKpi == null)
		{
			return null;
		}
		
		if(_list4baseABUiRowDataProOrg == null || _list4baseABUiRowDataProOrg.isEmpty())
		{
			return null;
		}
		
		if(_BaseBizDataIndex < 0)
		{
			return null;
		}
		
		//		检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//		检查前端期间是否与服务器一致	end
		
		//分解kpi业务数据的A和B begin
		BBizData bBizData_aitem	= null;
		BBizData bBizData_bitem = null;
		
		if( _BBizDataKpi.getBizDataDefItems() == null || _BBizDataKpi.getBizDataDefItems().isEmpty() || _BBizDataKpi.getBizDataDefItems().size() != 1 )
		{
			return null;
		}
		Iterator<BBizDataDefItem> itr_BBizDataDefItems = _BBizDataKpi.getBizDataDefItems().iterator();
		BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
		if( bBizDataDefItem instanceof BBizDataDefItemKpi )
		{
			bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
			bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();			
		}
		else //如果无A和B，则返回
		{
			return null;
		} 
	
		///分解kpi业务数据的A和B end
		
		int kpiFormula = ( (BBizDataDefItemKpi) bBizDataDefItem ).getKpiFormula();
		
		List<ABUiRowData> tempList = new ArrayList<ABUiRowData>();
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		for( int i=0; i<_list4ABUiRowDataProOrg.size(); i=i+1 )
		{

			ABUiRowDataProOrg abUiRowDataProOrg = _list4ABUiRowDataProOrg.get( i );
			ABUiRowData abUiRowData_base = null;
			tempList = getABUiRowDataList4WeightReport(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
			if(tempList ==null || tempList.isEmpty())
			{
				continue;
			}
			else 
			{
				if(tempList.get(0)!=null)
					abUiRowData_base= tempList.get(0);	
			}
			
			tempList.clear();//清空
			

			ABUiRowData abUiRowData = null;
			for(ABUiRowDataProOrg newabUiRowDataProOrg: _list4baseABUiRowDataProOrg)
			{
				if(UtilProOrg.relationOf(abUiRowDataProOrg, newabUiRowDataProOrg))//判断是否包含，如果包含，则计算
				{
					List<ABUiRowData> list_aitem = new ArrayList<ABUiRowData>();	
					List<ABUiRowData> list_bitem = new ArrayList<ABUiRowData>();
					List<ABUiRowData> list_all = new ArrayList<ABUiRowData>();
					if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
					{
						list_aitem = getABUiRowDataList4WeightReport4Nippon(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
						list_bitem = getABUiRowDataList4WeightReport4Nippon(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
					}
					else
					{
						list_aitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
						list_bitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
					}
					list_all.addAll(list_aitem);
					list_all.addAll(list_bitem);
					
					abUiRowData =getUiRowDatas4Kpi4WeightReport(newabUiRowDataProOrg,_periodBegin, _periodEnd,_BBizDataKpi,list_all,_BaseBizDataIndex);
					tempList.add(abUiRowData);
				}
				 
			}
						
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( _BBizDataKpi );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			newABUiRowData.setShowPercent(1);
			
			DecimalFormat df = new DecimalFormat("#0");
			//按照业务范围明细开始计算每个明细kpi end
			for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
			{
				for(ABUiRowData _abUiRowData:tempList)
				{
					if(_abUiRowData==null || _abUiRowData.pubFun4getPeriodDataValue(periodLoc)==null)
					{
						continue;
					}
					if(newABUiRowData.pubFun4getPeriodDataValue(periodLoc)==null)
					{						
						newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
							_abUiRowData.pubFun4getPeriodDataValue(periodLoc));
						newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
							_abUiRowData.pubFun4getPeriodDataValueBak(periodLoc));					
				
					}
					else 
					{
						newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
								_abUiRowData.pubFun4getPeriodDataValue(periodLoc)+newABUiRowData.pubFun4getPeriodDataValue(periodLoc));
							newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
								_abUiRowData.pubFun4getPeriodDataValueBak(periodLoc)+newABUiRowData.pubFun4getPeriodDataValueBak(periodLoc));	
					}	
				}
				if(abUiRowData_base.pubFun4getPeriodDataValue(periodLoc)==null || abUiRowData_base.pubFun4getPeriodDataValue(periodLoc)==0L)
					newABUiRowData.pubFun4setPeriodDataValue(periodLoc,0d);
				else
				{
					double temp_value = newABUiRowData.pubFun4getPeriodDataValue(periodLoc)/abUiRowData_base.pubFun4getPeriodDataValue(periodLoc)*100;
					String temp_Str=df.format(temp_value*100);
					Long kpi_=Long.parseLong(temp_Str);
					newABUiRowData.pubFun4setPeriodDataValue(periodLoc,temp_value);
				}					
			}
			rstList.add(newABUiRowData);
		}
		
		return rstList;
		}
	
	
	/**
	 * 权重KPI报表
	 * @param _list4ABUiRowDataProOrg 需要拼装的结果
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _BBizDataKpi kpi业务数据
	 * @param _bSysPeriod
	 * @return	_BaseBizDataIndex  基准数据编号
	 * @param _list4baseABUiRowDataProOrg 用来计算基准层次
	 * @throws Exception
	 */
	public List<ABUiRowData> getUiRowDatas4WeightReport( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg,
		int _periodBegin, int _periodEnd, 
		BBizData _BBizDataKpi, BSysPeriod _bSysPeriod,int _BaseBizDataIndex,List<ABUiRowDataProOrg> _list4baseABUiRowDataProOrg
		) throws Exception
	{
		//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//		检查服务器状态是否可以提供服务	end
	
		if( _list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty() )
		{
			return null;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return null;
		}

		if( _BBizDataKpi == null)
		{
			return null;
		}
		
		if(_list4baseABUiRowDataProOrg == null || _list4baseABUiRowDataProOrg.isEmpty())
		{
			return null;
		}
		
		if(_BaseBizDataIndex < 0)
		{
			return null;
		}
		
		//		检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//		检查前端期间是否与服务器一致	end
		
		//分解kpi业务数据的A和B begin
		BBizData bBizData_aitem	= null;
		BBizData bBizData_bitem = null;
		
		if( _BBizDataKpi.getBizDataDefItems() == null || _BBizDataKpi.getBizDataDefItems().isEmpty() || _BBizDataKpi.getBizDataDefItems().size() != 1 )
		{
			return null;
		}
		Iterator<BBizDataDefItem> itr_BBizDataDefItems = _BBizDataKpi.getBizDataDefItems().iterator();
		BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
		if( bBizDataDefItem instanceof BBizDataDefItemKpi )
		{
			bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
			bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();			
		}
		else //如果无A和B，则返回
		{
			return null;
		} 
	
		///分解kpi业务数据的A和B end
		
		int kpiFormula = ( (BBizDataDefItemKpi) bBizDataDefItem ).getKpiFormula();
		if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VII)
			return getUiRowDatas4Wight1(_list4ABUiRowDataProOrg,
					 _periodBegin,  _periodEnd, 
					 _BBizDataKpi,  _bSysPeriod, _BaseBizDataIndex, _list4baseABUiRowDataProOrg);
		
		List<ABUiRowData> tempList = new ArrayList<ABUiRowData>();
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		for( int i=0; i<_list4ABUiRowDataProOrg.size(); i=i+1 )
		{
			//先得到A和B的值，计算出权重  begin
			ABUiRowDataProOrg abUiRowDataProOrg = _list4ABUiRowDataProOrg.get( i );
			ABUiRowData abUiRowData_base = null;
			if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_A)
			{
				if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
				{
					tempList =getABUiRowDataList4WeightReport4Nippon(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
				}
				else
				{
					tempList = getABUiRowDataList4WeightReport(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
				}
			}
			else if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_B)
			{
				if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
				{
					tempList =getABUiRowDataList4WeightReport4Nippon(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
				}
				else
				{
					tempList = getABUiRowDataList4WeightReport(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);	
				}
			}
			else if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_A_add_B)//A+B作为权重
			{
				List<ABUiRowData> tempList_A ;
				List<ABUiRowData> tempList_B ;
				
				if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
				{
					tempList_A = getABUiRowDataList4WeightReport4Nippon(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
					tempList_B = getABUiRowDataList4WeightReport4Nippon(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
				}
				else
				{
					tempList_A = getABUiRowDataList4WeightReport(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
					tempList_B = getABUiRowDataList4WeightReport(abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
				}
				if(tempList_A == null || tempList_A.isEmpty())
				{
					if(tempList_B == null || tempList_B.isEmpty())
					{
						continue;
					}
					else
					{
						tempList = tempList_B;
					}
				}
				else
				{
					if(tempList_B == null || tempList_B.isEmpty())
					{
						tempList = tempList_A;
					}
					else //计算出A+B
					{
						ABUiRowData abUiRowData_aitem= tempList_A.get(0);
						ABUiRowData abUiRowData_bitem= tempList_B.get(0);
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
							abUiRowData_aitem.pubFun4setPeriodDataValue(periodLoc, value_aitem+value_bitem);
							tempList.add(0,  abUiRowData_aitem );
						}
					}
				}
			}
			if(tempList ==null || tempList.isEmpty())
			{
				continue;
			}
			else 
			{
				if(tempList.get(0)!=null)
					abUiRowData_base= tempList.get(0);	
			}
		
			//先得到A和B的值，计算出权重  end
			
			tempList.clear();//清空
			
			//按照业务范围明细开始计算每个明细kpi begin
//			for(ABProOrg abProOrg:_list4ABProOrg)
//			{
//				
//				List<BProduct> listProducts = UtilProduct.getDetailProducts(abProOrg.getProduct());
//				List<BOrganization> listOrganizations = UtilOrganization.getDetailOrganizations(abProOrg.getOrganization());
//				ABProOrg _abpg = null;
//				
//				for(BProduct bproduct:listProducts)
//				{
//					for(BOrganization borganization:listOrganizations)
//					{
			ABUiRowData abUiRowData = null;
			for(ABUiRowDataProOrg newabUiRowDataProOrg: _list4baseABUiRowDataProOrg)
			{
				if(UtilProOrg.relationOf(abUiRowDataProOrg, newabUiRowDataProOrg))//判断是否包含，如果包含，则计算
				{
					List<ABUiRowData> list_aitem = new ArrayList<ABUiRowData>();	
					List<ABUiRowData> list_bitem = new ArrayList<ABUiRowData>();
					List<ABUiRowData> list_all = new ArrayList<ABUiRowData>();
					if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
					{
						list_aitem = getABUiRowDataList4WeightReport4Nippon(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
						list_bitem = getABUiRowDataList4WeightReport4Nippon(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
					}
					else
					{
						list_aitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
						list_bitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
					}
					list_all.addAll(list_aitem);
					list_all.addAll(list_bitem);
					
					abUiRowData =getUiRowDatas4Kpi4WeightReport(newabUiRowDataProOrg,_periodBegin, _periodEnd,_BBizDataKpi,list_all,abUiRowData_base,_BaseBizDataIndex);
					tempList.add(abUiRowData);
				}
				 
			}
						
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setShowPercent(1);
			newABUiRowData.setProduct( abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( _BBizDataKpi );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			//按照业务范围明细开始计算每个明细kpi end
			for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
			{
				for(ABUiRowData _abUiRowData:tempList)
				{
					if(_abUiRowData==null || _abUiRowData.pubFun4getPeriodDataValue(periodLoc)==null)
					{
						continue;
					}
					if(newABUiRowData.pubFun4getPeriodDataValue(periodLoc)==null)
					{						
						newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
							_abUiRowData.pubFun4getPeriodDataValue(periodLoc));
						newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
							_abUiRowData.pubFun4getPeriodDataValueBak(periodLoc));					
				
					}
					else 
					{
						newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
								_abUiRowData.pubFun4getPeriodDataValue(periodLoc)+newABUiRowData.pubFun4getPeriodDataValue(periodLoc));
							newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
								_abUiRowData.pubFun4getPeriodDataValueBak(periodLoc)+newABUiRowData.pubFun4getPeriodDataValueBak(periodLoc));	
					}
						
				}
			}
			rstList.add(newABUiRowData);
		}
		
		return rstList;
	}	
	
	/**
	 * 查询明细权重kpi
	 * @param _list4ABProOrg 所选的业务范围
	 * @param _list4ABUiRowDataProOrg 需要返回的结果集
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _BBizDatakpi
	 * @param _bSysPeriod 
	 * @return	_BaseBizDataIndex	基准数据编号
	 * @throws Exception
	 */
	public List<ABUiRowData> getDetailUiRowDatas(List<ABProOrg> _list4ABProOrg, ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd, BBizData _BBizDataKpi, BSysPeriod _bSysPeriod,int _BaseBizDataIndex) throws Exception
	{
		//		检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//		检查服务器状态是否可以提供服务	end
		
		
		if( _abUiRowDataProOrg == null )
		{
			return null;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return null;
		}

		if( _BBizDataKpi == null)
		{
			return null;
		}
		
		if(_list4ABProOrg == null || _list4ABProOrg.isEmpty())
		{
			return null;
		}
		
		//	检查前端期间是否与服务器一致	begin
		BSysPeriod bSysPeriod_server = ServerEnvironment.getInstance().getSysPeriod();
		if( _bSysPeriod == null || bSysPeriod_server == null )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		if( _bSysPeriod.getCompilePeriod() != bSysPeriod_server.getCompilePeriod() )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_COMPILEPERIOD_UNMATCH );
			Exception ex = new Exception( cause );
			throw ex;
		}
		//	检查前端期间是否与服务器一致	end
		
		//	计算出A，B	begin
		BBizData bBizData_aitem	= null;
		BBizData bBizData_bitem = null;
		
	
		if( _BBizDataKpi.getBizDataDefItems() == null || _BBizDataKpi.getBizDataDefItems().isEmpty() || _BBizDataKpi.getBizDataDefItems().size() != 1 )
		{
			return null;
		}
		Iterator<BBizDataDefItem> itr_BBizDataDefItems = _BBizDataKpi.getBizDataDefItems().iterator();
		BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
		if( bBizDataDefItem instanceof BBizDataDefItemKpi )
		{
			bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
			bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();			
		}
		else //如果没有A和B，则直接返回空
		{
			return null;
		}

		//计算出A，B end
		int kpiFormula = ( (BBizDataDefItemKpi) bBizDataDefItem ).getKpiFormula();
		
		List<ABUiRowData> tempList = new ArrayList<ABUiRowData>();
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		// 根据基准编号得到A，B和权重值  begin
		ABUiRowData abUiRowData_base = null;
		if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_A)
		{			
			if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
			{
				tempList =getABUiRowDataList4WeightReport4Nippon(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
			}
			else
			{
				tempList = getABUiRowDataList4WeightReport(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
			}			
		}
		else if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_B)
		{
			if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
			{
				tempList =getABUiRowDataList4WeightReport4Nippon(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			}
			else
			{
				tempList = getABUiRowDataList4WeightReport(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			}
		}
		else if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_A_add_B)
		{
			List<ABUiRowData> tempList_A ;
			List<ABUiRowData> tempList_B ;
			
			if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
			{
				tempList_A = getABUiRowDataList4WeightReport4Nippon(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
				tempList_B = getABUiRowDataList4WeightReport4Nippon(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			}
			else
			{
				tempList_A = getABUiRowDataList4WeightReport(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
				tempList_B = getABUiRowDataList4WeightReport(_abUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			}
			
			if(tempList_A == null || tempList_A.isEmpty())
			{
				if(tempList_B == null || tempList_B.isEmpty())
				{
					return rstList;
				}
				else
				{
					tempList = tempList_B;
				}
			}
			else
			{
				if(tempList_B == null || tempList_B.isEmpty())
				{
					tempList = tempList_A;
				}
				else //算出A+B
				{
					ABUiRowData abUiRowData_aitem= tempList_A.get(0);
					ABUiRowData abUiRowData_bitem= tempList_B.get(0);
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
						abUiRowData_aitem.pubFun4setPeriodDataValue(periodLoc, value_aitem+value_bitem);
						tempList.add(0,  abUiRowData_aitem );
					}
				}
			}
		}
		
		if(tempList ==null || tempList.isEmpty())
		{
			return rstList;
		}
		else 
		{
			if(tempList.get(0)!=null)
				abUiRowData_base= tempList.get(0);	
		}
	
		// 根据基准编号得到A，B和权重值  end
		
		tempList.clear();//清空
		
		//根据具体的业务算出明细权重kpi begin
		for(ABProOrg abProOrg:_list4ABProOrg)
		{
			ABUiRowDataProOrg newabUiRowDataProOrg = new ABUiRowDataProOrg();
			newabUiRowDataProOrg.setProduct(abProOrg.getProduct());
			newabUiRowDataProOrg.setOrganization(abProOrg.getOrganization());
			newabUiRowDataProOrg.setOrganizationCharacter(_abUiRowDataProOrg.getOrganizationCharacter());
			newabUiRowDataProOrg.setProductCharacter(_abUiRowDataProOrg.getProductCharacter());	
			newabUiRowDataProOrg.setDetailProOrgIds(new ArrayList<String>());
			newabUiRowDataProOrg.getDetailProOrgIds().add(UtilProOrg.getProOrgId(abProOrg.getProduct(),abProOrg.getOrganization()));
			List<ABUiRowData> list_aitem = new ArrayList<ABUiRowData>();	
			List<ABUiRowData> list_bitem = new ArrayList<ABUiRowData>();
			List<ABUiRowData> list_all = new ArrayList<ABUiRowData>();
			if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
			{
				list_aitem = getABUiRowDataList4WeightReport4Nippon(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
				list_bitem = getABUiRowDataList4WeightReport4Nippon(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			}
			else
			{
				list_aitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
				list_bitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			}
//			list_aitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_aitem);
//			list_bitem = getABUiRowDataList4WeightReport(newabUiRowDataProOrg,_periodBegin,_periodEnd,bBizData_bitem);
			list_all.addAll(list_aitem);
			list_all.addAll(list_bitem);
			
			if(list_all.isEmpty())
			{
				continue;
			}
			
			ABUiRowData abUiRowData =getUiRowDatas4Kpi4WeightReport(newabUiRowDataProOrg,_periodBegin, _periodEnd,_BBizDataKpi,list_all,abUiRowData_base,_BaseBizDataIndex);
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct(abProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization(abProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( _BBizDataKpi );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
			{			
				if(abUiRowData!=null && abUiRowData.pubFun4getPeriodDataValue(periodLoc)!=null)
				{
					newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
							abUiRowData.pubFun4getPeriodDataValue(periodLoc));
					newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
							abUiRowData.pubFun4getPeriodDataValueBak(periodLoc));
				}
						
					else
					{
						newABUiRowData.pubFun4setPeriodDataValue(periodLoc,0d);
						newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,0d);
					}
			}
			rstList.add(newABUiRowData);				
		}
		//根据具体的业务算出明细权重kpi end
		return rstList;
	}	
	
	private List<ABUiRowData> getABUiRowDataList4WeightReport(ABUiRowDataProOrg abUiRowDataProOrg,int _periodBegin, int _periodEnd,BBizData _bBizData) throws Exception
	{
		List<BBizData> list4BBizData4History = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4TimeHis = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4FcMHF = new ArrayList<BBizData>();	//	FcModel FcHand FcFinal
		List<BBizData> list4BBizData4FcComb = new ArrayList<BBizData>();	//	FcComb
		List<BBizData> list4BBizData4TimeFc = new ArrayList<BBizData>();		
		List<BBizData> list4BBizData4Money = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4Kpi = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4MoneyComb = new ArrayList<BBizData>();
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		List<BBizData> list4BBizDataAssessment= new ArrayList<BBizData>();
		
		if( _bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
				_bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD || 
				_bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
		{
			list4BBizData4History.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEHIS )
		{
			list4BBizData4TimeHis.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
				_bBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
				_bBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
		{
			list4BBizData4FcMHF.add( _bBizData );
		}		
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_FCCOMB )
		{
			list4BBizData4FcComb.add( _bBizData );
		}		
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC )
		{
			list4BBizData4TimeFc.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_MONEY )
		{
			list4BBizData4Money.add( _bBizData );
		}			
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_MONEYCOMB )
		{
			list4BBizData4MoneyComb.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_KPI )
		{
			list4BBizData4Kpi.add( _bBizData );
		}	
		else if(_bBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT)
		{
			list4BBizDataAssessment.add( _bBizData );
		}
		//		拼出明细范围字符串		begin
		String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( abUiRowDataProOrg );
		//		拼出明细范围字符串		end
		
		//	历史类数据	begin
		List<ABUiRowData> list4ABUiRowData4History = this.getUiRowDatas4History( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4History );
		rstList.addAll( list4ABUiRowData4History );
		
		//	历史类数据 end	
		
		//	期间历史数据			begin				
		List<ABUiRowData> list4ABUiRowData4TimeHis = this.getUiRowDatas4TimeHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeHis );
		rstList.addAll( list4ABUiRowData4TimeHis );
		
		//	期间历史数据		end				
		
		//	预测类数据	begin
		List<ABUiRowData> list4ABUiRowData4FcMHF = this.getUiRowDatas4FcMHF( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcMHF );
		rstList.addAll( list4ABUiRowData4FcMHF );
		
		//	预测类数据	end	
		
		//	组合预测数据  	begin
		List<ABUiRowData> list4ABUiRowData4FcComb = this.getUiRowDatas4FcComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcComb, list4ABUiRowData4FcMHF );
		rstList.addAll( list4ABUiRowData4FcComb );
		
		//	组合预测数据  	end			
		
		//	期间预测数据		begin				
		List<ABUiRowData> list4ABUiRowData4TimeFc = this.getUiRowDatas4TimeFc( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeFc );
		rstList.addAll( list4ABUiRowData4TimeFc );
		
		//	期间预测数据		end
		
		//	金额数据		begin				
		List<ABUiRowData> list4ABUiRowData4Money = this.getUiRowDatas4Money( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4Money );
		rstList.addAll( list4ABUiRowData4Money );
		
		//	金额数据		end

//		组合金额数据		begin				
		List<ABUiRowData> list4ABUiRowData4MoneyComb = this.getUiRowDatas4MoneyComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4MoneyComb, list4ABUiRowData4Money );
		rstList.addAll( list4ABUiRowData4MoneyComb );
		//	组合金额数据		end
		
		//预测考核类 begin
		List<ABUiRowData> list4ABUiRowData4ForecastAssessment = getUiRowDatas4ForecastAssessment( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAssessment );
		rstList.addAll( list4ABUiRowData4ForecastAssessment );
		//预测考核类 begin
		return rstList;
	}
	
	/**
	 * 单独为立邦开发的查询value>0的加和。
	 * @param abUiRowDataProOrg
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _bBizData
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getABUiRowDataList4WeightReport4Nippon(ABUiRowDataProOrg abUiRowDataProOrg,int _periodBegin, int _periodEnd,BBizData _bBizData) throws Exception
	{
		List<BBizData> list4BBizData4History = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4TimeHis = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4FcMHF = new ArrayList<BBizData>();	//	FcModel FcHand FcFinal
		List<BBizData> list4BBizData4FcComb = new ArrayList<BBizData>();	//	FcComb
		List<BBizData> list4BBizData4TimeFc = new ArrayList<BBizData>();		
		List<BBizData> list4BBizData4Money = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4Kpi = new ArrayList<BBizData>();
		List<BBizData> list4BBizDataAssessment = new ArrayList<BBizData>();
		List<BBizData> list4BBizData4MoneyComb = new ArrayList<BBizData>();
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		if( _bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
				_bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD || 
				_bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
		{
			list4BBizData4History.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEHIS )
		{
			list4BBizData4TimeHis.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
				_bBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
				_bBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
		{
			list4BBizData4FcMHF.add( _bBizData );
		}		
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_FCCOMB )
		{
			list4BBizData4FcComb.add( _bBizData );
		}		
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC )
		{
			list4BBizData4TimeFc.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_MONEY )
		{
			list4BBizData4Money.add( _bBizData );
		}			
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_MONEYCOMB )
		{
			list4BBizData4MoneyComb.add( _bBizData );
		}
		else if( _bBizData.getType() == BizConst.BIZDATA_TYPE_KPI )
		{
			list4BBizData4Kpi.add( _bBizData );
		}	
		else if(_bBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT)
		{
			list4BBizDataAssessment.add( _bBizData );
		}
		//		拼出明细范围字符串		begin
		String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( abUiRowDataProOrg );
		//		拼出明细范围字符串		end
		
		//	历史类数据	begin
		List<ABUiRowData> list4ABUiRowData4History = this.getUiRowDatas4History4Nippon( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4History );
		rstList.addAll( list4ABUiRowData4History );
		
		//	历史类数据 end	
		
		//	期间历史数据			begin				
		List<ABUiRowData> list4ABUiRowData4TimeHis = this.getUiRowDatas4TimeHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeHis );
		rstList.addAll( list4ABUiRowData4TimeHis );
		
		//	期间历史数据		end				
		
		//	预测类数据	begin
		List<ABUiRowData> list4ABUiRowData4FcMHF = this.getUiRowDatas4FcMHF4Nippon( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcMHF );
		rstList.addAll( list4ABUiRowData4FcMHF );
		
		//	预测类数据	end	
		
		//	组合预测数据  	begin
		List<ABUiRowData> list4ABUiRowData4FcComb = this.getUiRowDatas4FcComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcComb, list4ABUiRowData4FcMHF );
		rstList.addAll( list4ABUiRowData4FcComb );
		
		//	组合预测数据  	end			
		
		//	期间预测数据		begin				
		List<ABUiRowData> list4ABUiRowData4TimeFc = this.getUiRowDatas4TimeFc( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeFc );
		rstList.addAll( list4ABUiRowData4TimeFc );
		
		//	期间预测数据		end
		
		//	金额数据		begin				
		List<ABUiRowData> list4ABUiRowData4Money = this.getUiRowDatas4Money( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4Money );
		rstList.addAll( list4ABUiRowData4Money );
		
		//	金额数据		end

//		组合金额数据		begin				
		List<ABUiRowData> list4ABUiRowData4MoneyComb = this.getUiRowDatas4MoneyComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4MoneyComb, list4ABUiRowData4Money );
		rstList.addAll( list4ABUiRowData4MoneyComb );
		//	组合金额数据		end
		
		//预测考核类 begin
		List<ABUiRowData> list4ABUiRowData4ForecastAssessment = getUiRowDatas4ForecastAssessment( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAssessment );
		rstList.addAll( list4ABUiRowData4ForecastAssessment );
		//预测考核类 begin
		
		return rstList;
	}
	
	/**
	 * 单独为立邦开发的查询value>0的历史数据查询
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
	private List<ABUiRowData> getUiRowDatas4History4Nippon( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4History ) throws Exception
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

		if( _list4BBizData4History == null || _list4BBizData4History.isEmpty() )
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
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData = new HashMap<Long, ABUiRowData>();
		List<Long> list4BizDataId = new ArrayList<Long>();
		
		for( int j=0; j<_list4BBizData4History.size(); j=j+1 )
		{
			BBizData bBizData4History = _list4BBizData4History.get( j );
			
			list4BizDataId.add( bBizData4History.getId() );
			
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4History );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			hmap_bizDataId_ABUiRowData.put( bBizData4History.getId(), newABUiRowData );
		}
		//	拼出业务数据范围和结果数据集	end
		
		//	查询历史类数据并填充结果数据集		begin
		List<ASumData> listASumData = null;
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoHistoryData daoHistoryData = new DaoHistoryData( session );			
			listASumData = daoHistoryData.getSumHistoryDatas4Nippon( detailProOrgIdStr, _periodBegin, _periodEnd, list4BizDataId );
			trsa.commit();
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
	
		
		if( listASumData != null && !(listASumData.isEmpty()) )
		{
			for(int j=0; j<listASumData.size(); j=j+1 )
			{
				ASumData aSumData = listASumData.get( j );
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get( aSumData.getBizData().getId() );
				if( abUiRowData != null )
				{
					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					abUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					abUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
					hmap_bizDataId_ABUiRowData.put( aSumData.getBizData().getId(), abUiRowData );
				}
			}
		}
		
		if( hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty()) )
		{
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while( itr_hmap_bizDataId_ABUiRowData_values.hasNext() )
			{
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				if( this.isExistHistoryDatas( abUiRowData, detailProOrgIdStr ) == true )
				{
					rstList.add( abUiRowData );
				}
			}
		}

		//	查询历史类数据并填充结果数据集		end			
		
		return rstList;
	}
	
	/**
	 * 单独为立邦开发的查询预测类数据 value>0
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
	private List<ABUiRowData> getUiRowDatas4FcMHF4Nippon( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4FcMHF ) throws Exception
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

		if( _list4BBizData4FcMHF == null || _list4BBizData4FcMHF.isEmpty() )
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
		List<ASumData> listASumData = null;
		
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoForecastData daoForecastData = new DaoForecastData( session );			
			listASumData = daoForecastData.getSumForecastDatas4Nippon( detailProOrgIdStr, _periodBegin, _periodEnd, list4BizDataId );
			trsa.commit();
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
	
		
		if( listASumData != null && !(listASumData.isEmpty()) )
		{
			for(int j=0; j<listASumData.size(); j=j+1 )
			{
				ASumData aSumData = listASumData.get( j );
				ABUiRowData abUiRowData = hmap_bizDataId_ABUiRowData.get( aSumData.getBizData().getId() );
				if( abUiRowData != null )
				{
					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					abUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					abUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
					hmap_bizDataId_ABUiRowData.put( aSumData.getBizData().getId(), abUiRowData );
				}
			}
		}
		
		if( hmap_bizDataId_ABUiRowData.values() != null && !(hmap_bizDataId_ABUiRowData.values().isEmpty()) )
		{
			Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
			while( itr_hmap_bizDataId_ABUiRowData_values.hasNext() )
			{
				ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
				if( this.isExistForecastDatas( abUiRowData, detailProOrgIdStr ) == true )
				{
					rstList.add( abUiRowData );
				}
			}
		}

		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}
	/**
	 * 单独为公式∑|A(t)-B(t)|/∑A(t)开发的报表
	 * @param _abUiRowDataProOrg
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _BBizData4Kpi
	 * @param _list4ABUiRowData4exist
	 * @param _BaseBizDataIndex
	 * @return
	 * @throws Exception
	 */
	private ABUiRowData getUiRowDatas4Kpi4WeightReport( ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd, BBizData _BBizData4Kpi, List<ABUiRowData> _list4ABUiRowData4exist ,int _BaseBizDataIndex )throws Exception
	{

		ABUiRowData rstData = null;
		
		if( _abUiRowDataProOrg == null )
		{
			return rstData;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstData;
		}

		if( _BBizData4Kpi == null  )
		{
			return rstData;
		}
	
		HashMap<String, ABUiRowData> hmap_ppcoocb_ABUiRowData = new HashMap<String, ABUiRowData>();	
		String strKey_ppcoocb = null;
		
		if( _list4ABUiRowData4exist != null )
		{
			for(int i=0; i<_list4ABUiRowData4exist.size(); i++ )
			{
				ABUiRowData abUiRowData = _list4ABUiRowData4exist.get( i );
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( abUiRowData.getProduct(), abUiRowData.getProductCharacter(), abUiRowData.getOrganization(), abUiRowData.getOrganizationCharacter(), abUiRowData.getBizData() );
				hmap_ppcoocb_ABUiRowData.put( strKey_ppcoocb, abUiRowData );
			}
		}
		
		
		//		计算Kpi		begin
		if( _BBizData4Kpi.getBizDataDefItems() == null || _BBizData4Kpi.getBizDataDefItems().isEmpty() || _BBizData4Kpi.getBizDataDefItems().size() != 1 )
		{
			return rstData;
		}
		
		rstData = new ABUiRowData();
		rstData.setProduct( _abUiRowDataProOrg.getProduct() );
		rstData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
		rstData.setOrganization( _abUiRowDataProOrg.getOrganization() );
		rstData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
		rstData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
		rstData.setBizData( _BBizData4Kpi );
		rstData.setPeriodBegin( _periodBegin );
		rstData.setPeriodEnd( _periodEnd );
		
		boolean isExist = false;
		
		DecimalFormat df = new DecimalFormat("#0");
		
		Iterator<BBizDataDefItem> itr_BBizDataDefItems = _BBizData4Kpi.getBizDataDefItems().iterator();
		BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
		if( bBizDataDefItem instanceof BBizDataDefItemKpi )
		{
			BBizData bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
			BBizData bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();
			
			strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( rstData.getProduct(), rstData.getProductCharacter(), rstData.getOrganization(), rstData.getOrganizationCharacter(), bBizData_aitem );
			ABUiRowData abUiRowData_aitem = hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb );
			
			strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( rstData.getProduct(), rstData.getProductCharacter(), rstData.getOrganization(), rstData.getOrganizationCharacter(), bBizData_bitem );
			ABUiRowData abUiRowData_bitem = hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb );
			
			int kpiFormula = ( (BBizDataDefItemKpi) bBizDataDefItem ).getKpiFormula();
			
//			if( abUiRowData_aitem != null && abUiRowData_bitem != null )
//			{
			//			严格条件，只有两个定义项都存在时才算存在	begin
				isExist = true;
				//				严格条件，只有两个定义项都存在时才算存在	end
				
				for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
				{
					Double value_aitem = 0d;
					Double value_bitem = 0d;
					String temp_value="";
					double kpiValue = 0;
					try
					{
						if(abUiRowData_aitem!=null)
						{
							value_aitem = abUiRowData_aitem.pubFun4getPeriodDataValue( periodLoc );
						}
						else
						{
							value_aitem = 0d;
						}
						if( value_aitem == null )
						{
							value_aitem = 0d;
						}
						
						if(abUiRowData_bitem!=null)
						{
							value_bitem = abUiRowData_bitem.pubFun4getPeriodDataValue( periodLoc );
						}
						else
						{
							value_bitem = 0d;
						}
						if( value_bitem == null )
						{
							value_bitem = 0d;
						}
						
					
						
						//开始计算权重值 end
						
						//根据公式计算kpi值，由于返回的值为long型，不能记录小数点，因此这里将所有值都乘以100，在客户端将会除以100.						
						if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VII)
						{
							kpiValue = Math.abs( value_aitem - value_bitem );
						}
						else
						{
							//	out of design
							kpiValue = 0L;						
						}
						
						temp_value=df.format(kpiValue);
						//Double kpi_=  Long.parseLong(temp_value);
						
						//计算kpi end
						rstData.pubFun4setPeriodDataValue( periodLoc, kpiValue );
						rstData.pubFun4setPeriodDataValueBak( periodLoc, kpiValue );
						
					}
						catch(Exception e)
					{
						e.printStackTrace();
					}
					
			}
		}
		
		if( isExist == true )
		{
			return rstData;
		}
		
		//	计算具体Kpi		end
				
		return null;
	
	}
	
	/**
	 * 查询权重报表所需的kpi
	 * @param _abUiRowDataProOrg
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _BBizData4Kpi
	 * @param _list4ABUiRowData4exist
	 * @param _abUiRowData_base
	 * @param _BaseBizDataIndex 基准数据编号
	 * @return
	 * @throws Exception
	 */
	private ABUiRowData getUiRowDatas4Kpi4WeightReport( ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd, BBizData _BBizData4Kpi, List<ABUiRowData> _list4ABUiRowData4exist ,ABUiRowData _abUiRowData_base,int _BaseBizDataIndex )throws Exception
	{
		ABUiRowData rstData = null;
		
		if( _abUiRowDataProOrg == null )
		{
			return rstData;
		}		
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 )
		{
			return rstData;
		}

		if( _BBizData4Kpi == null  )
		{
			return rstData;
		}
		
		if(_abUiRowData_base == null)
		{
			return rstData;
		}
		
		HashMap<String, ABUiRowData> hmap_ppcoocb_ABUiRowData = new HashMap<String, ABUiRowData>();	
		String strKey_ppcoocb = null;
		
		if( _list4ABUiRowData4exist != null )
		{
			for(int i=0; i<_list4ABUiRowData4exist.size(); i++ )
			{
				ABUiRowData abUiRowData = _list4ABUiRowData4exist.get( i );
				strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( abUiRowData.getProduct(), abUiRowData.getProductCharacter(), abUiRowData.getOrganization(), abUiRowData.getOrganizationCharacter(), abUiRowData.getBizData() );
				hmap_ppcoocb_ABUiRowData.put( strKey_ppcoocb, abUiRowData );
			}
		}
		
		
		//		计算Kpi		begin
		if( _BBizData4Kpi.getBizDataDefItems() == null || _BBizData4Kpi.getBizDataDefItems().isEmpty() || _BBizData4Kpi.getBizDataDefItems().size() != 1 )
		{
			return rstData;
		}
		
		rstData = new ABUiRowData();
		rstData.setProduct( _abUiRowDataProOrg.getProduct() );
		rstData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
		rstData.setOrganization( _abUiRowDataProOrg.getOrganization() );
		rstData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
		rstData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
		rstData.setBizData( _BBizData4Kpi );
		rstData.setPeriodBegin( _periodBegin );
		rstData.setPeriodEnd( _periodEnd );
		
		boolean isExist = false;
		double percentage= 0;
		DecimalFormat df = new DecimalFormat("#0");
		
		Iterator<BBizDataDefItem> itr_BBizDataDefItems = _BBizData4Kpi.getBizDataDefItems().iterator();
		BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
		if( bBizDataDefItem instanceof BBizDataDefItemKpi )
		{
			BBizData bBizData_aitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getAitemBizData();
			BBizData bBizData_bitem = ( (BBizDataDefItemKpi) bBizDataDefItem ).getBitemBizData();
			
			strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( rstData.getProduct(), rstData.getProductCharacter(), rstData.getOrganization(), rstData.getOrganizationCharacter(), bBizData_aitem );
			ABUiRowData abUiRowData_aitem = hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb );
			
			strKey_ppcoocb = UtilStrKey.getStrKey4PPcOOcB( rstData.getProduct(), rstData.getProductCharacter(), rstData.getOrganization(), rstData.getOrganizationCharacter(), bBizData_bitem );
			ABUiRowData abUiRowData_bitem = hmap_ppcoocb_ABUiRowData.get( strKey_ppcoocb );
			
			int kpiFormula = ( (BBizDataDefItemKpi) bBizDataDefItem ).getKpiFormula();
			
//			if( abUiRowData_aitem != null && abUiRowData_bitem != null )
//			{
			//			严格条件，只有两个定义项都存在时才算存在	begin
				isExist = true;
				//				严格条件，只有两个定义项都存在时才算存在	end
				
				for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
				{
					Double value_aitem = 0d;
					Double value_bitem = 0d;
					String temp_value="";
					double kpiValue = 0;
					Double value_base =0d;
					try
					{
						if(abUiRowData_aitem!=null)
						{
							value_aitem = abUiRowData_aitem.pubFun4getPeriodDataValue( periodLoc );
						}
						else
						{
							value_aitem = 0d;
						}
						if( value_aitem == null )
						{
							value_aitem = 0d;
						}
						
						if(abUiRowData_bitem!=null)
						{
							value_bitem = abUiRowData_bitem.pubFun4getPeriodDataValue( periodLoc );
						}
						else
						{
							value_bitem = 0d;
						}
						if( value_bitem == null )
						{
							value_bitem = 0d;
						}
						
						value_base= _abUiRowData_base.pubFun4getPeriodDataValue(periodLoc);
						if(value_base==null || value_base==0)//无基准数据
						{
							rstData.pubFun4setPeriodDataValue( periodLoc, 0d );
							rstData.pubFun4setPeriodDataValueBak( periodLoc, 0d );
							continue;
						}
						//开始计算权重值 begin 
						
						if(_BaseBizDataIndex== BizConst.REPORT_WEIGHT_BASEDATA_A)//A为基准数据
						{
							percentage = value_aitem/value_base;
						}
						else if(_BaseBizDataIndex== BizConst.REPORT_WEIGHT_BASEDATA_B)//B为基准数据
						{
							percentage = value_bitem/value_base;
						}
						else if(_BaseBizDataIndex == BizConst.REPORT_WEIGHT_BASEDATA_A_add_B)//A+B为基准数据 
						{
							percentage = (value_aitem+value_bitem)/value_base;
						}
						
						//开始计算权重值 end
						
						//根据公式计算kpi值，由于返回的值为long型，不能记录小数点，因此这里将所有值都乘以100，在客户端将会除以100.						
						if( kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_RATIO )
						{							
							if( value_aitem == 0 ) //A=0 则kpi=0
							{
									kpiValue = 0;		
							}
							else if( value_bitem == 0L )//B=0 则kpi=NaN
							{
								rstData.pubFun4setPeriodDataValue( periodLoc, null );
								rstData.pubFun4setPeriodDataValueBak( periodLoc, null );		
								continue;
							}
							else
							{
								kpiValue = value_aitem / value_bitem * 100 *percentage;									
							}										
						}
						else if( kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO )
						{
							if( value_bitem == 0 && value_aitem!=0)//A或者B为0，则kpi=100
							{
								kpiValue =  1*100*percentage ;																								
							}
							else if(Math.abs( value_aitem - value_bitem )==0)//鍒嗗瓙涓�锛屽垯kpi涓�
							{
								kpiValue = 0;	
							}
							else if(value_bitem!=0)
							{
								kpiValue = Math.abs( value_aitem - value_bitem ) / value_bitem * 100 *percentage;								
							}											
						}
						else if( kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_I )
						{
							if( value_bitem == 0 )//A=0 kpi=NaN
							{
								rstData.pubFun4setPeriodDataValue( periodLoc, null );
								rstData.pubFun4setPeriodDataValueBak( periodLoc, null );		
								continue;		
							}
							else if(1 - Math.abs( value_aitem - value_bitem )==0)
							{
								kpiValue = 0;	
							}
							else
							{
								kpiValue = (1 - Math.abs( value_aitem - value_bitem ) / value_bitem) * 100*percentage;								
							}											
						}	
						else if( kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_II ) //	|A-B|/((A+B)/2)
						{
							if( value_aitem + value_bitem == 0 )	//	分母为0
							{
								rstData.pubFun4setPeriodDataValue( periodLoc, null );
								rstData.pubFun4setPeriodDataValueBak( periodLoc, null );		
								continue;		
							}
							else if( value_aitem == value_bitem )
							{
								kpiValue = 0;	
							}
							else
							{
								kpiValue = 2.0 * Math.abs( value_aitem - value_bitem ) / (value_aitem + value_bitem) * 100 * percentage;								
							}											
						}
						else if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_III )
						{
							kpiValue =  ( value_aitem - value_bitem ) * percentage;

						}
						else if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_IV)
						{
							kpiValue =  Math.abs( value_aitem - value_bitem ) * percentage;
						}
						else if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_V)
						{
							if( value_bitem == 0 && value_aitem!=0)//A或者B为0，则kpi=100
							{
								kpiValue =  1*100*percentage ;																								
							}
							else if(Math.abs( value_aitem - value_bitem )==0)//分子为0，则kpi为0
							{
								kpiValue = 0;	
							}
							else if(value_bitem!=0)
							{
								kpiValue = (value_aitem - value_bitem ) / value_bitem * 100 *percentage;								
							}	
						}
						else if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VI)
						{
							if( value_aitem == 0  ) //A=0 则kpi=0
							{
									kpiValue = 0;		
							}
							else if( value_bitem == 0L )//B=0 则kpi=NaN
							{
								kpiValue = 0;
							}
							else if( value_aitem <0 || value_bitem<0)//A或者B小于0，则直接为0
							{
								kpiValue = 0;
							}
							else
							{
								if(value_aitem<value_bitem)
								{
									kpiValue = value_aitem / value_bitem * 100 *percentage;			
								}
								else
								{
									kpiValue = value_bitem / value_aitem * 100 *percentage;	
								}
							}		
						}
						else if(kpiFormula == BizConst.BIZDATADEFITEM_KPIFORMULA_INCREMENTRATIO_VII)
						{
							kpiValue = Math.abs( value_aitem - value_bitem );
						}
						else
						{
							//	out of design
							kpiValue = 0L;						
						}
						
						temp_value=df.format(kpiValue*100);
						Long kpi_=Long.parseLong(temp_value);
						
						//计算kpi end
						rstData.pubFun4setPeriodDataValue( periodLoc, kpiValue );
						rstData.pubFun4setPeriodDataValueBak( periodLoc, kpiValue );
						
					}
						catch(Exception e)
					{
						e.printStackTrace();
					}
					
			}
		}
		
		if( isExist == true )
		{
			return rstData;
		}
		
		//	计算具体Kpi		end
				
		return null;
	}		
	//权重报表 end
	
	//多单位显示begin 

	public List<ABUiRowData> getConversionDatas(List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData, BSysPeriod _bSysPeriod,List<ABUiRowDataProOrg> _list4ResultABUiRowDataProOrg,BUnit unit) throws Exception
	{
		try{
			List<ABUiRowData> result = new ArrayList<ABUiRowData>();
			//第一步，算出明细
			List<ABUiRowData> list_ABUiRowData =  getUiRowDatas(_list4ABUiRowDataProOrg,_periodBegin,_periodEnd,_list4BBizData,_bSysPeriod);
			if(list_ABUiRowData == null || list_ABUiRowData.size()<1)
			{
				return result;
			}
			//第二步，获取所有多单位分解规则
			List<BConversionSet> list_conversion = new ConversionService().getConversoinSets(null, -1, 1000000);
			HashMap<Long,BConversionType> hm_conversion = new HashMap<Long, BConversionType>();
			for(BConversionSet set :list_conversion)
			{
				hm_conversion.put(set.getProduct().getId(),set.getConversionType());			
			}
			//进行组合
			int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
			BConversionType type = null;
			for(BBizData bzData:_list4BBizData)
			{
				for(ABUiRowDataProOrg aBUiRowDataProOrg:_list4ResultABUiRowDataProOrg)
				{
					ABUiRowData newABUiRowData = new ABUiRowData();
					newABUiRowData.setProduct( aBUiRowDataProOrg.getProduct() );
					newABUiRowData.setProductCharacter( aBUiRowDataProOrg.getProductCharacter() );
					newABUiRowData.setOrganization( aBUiRowDataProOrg.getOrganization() );
					newABUiRowData.setOrganizationCharacter( aBUiRowDataProOrg.getOrganizationCharacter() );
					newABUiRowData.setDetailProOrgIds( aBUiRowDataProOrg.getDetailProOrgIds() );
					newABUiRowData.setBizData( bzData );
					newABUiRowData.setPeriodBegin( _periodBegin );
					newABUiRowData.setPeriodEnd( _periodEnd );
					
					for(ABUiRowData aBUiRowData:list_ABUiRowData)
					{
						if(UtilProOrg.relationOf(aBUiRowDataProOrg, aBUiRowData))
						{
							if(aBUiRowData.getBizData().getId() == bzData.getId())
							{
								
								for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
								{
									if(!hm_conversion.containsKey(aBUiRowData.getProduct().getId()))
									{
										continue;
									}
									type = hm_conversion.get(aBUiRowData.getProduct().getId());
									
									Double periodLocValue = aBUiRowData.pubFun4getPeriodDataValue(periodLoc);
									Double periodLocValueBak = aBUiRowData.pubFun4getPeriodDataValueBak(periodLoc);
									
									//判断如果在指定的期间内没有值，则不用进行换算 zhangzy 20131126
									if(periodLocValue == null || periodLocValueBak== null){
										continue;
									}
									
									if(unit.getIsBase() ==  BizConst.GLOBAL_YESNO_YES)
									{
										if(newABUiRowData.pubFun4getPeriodDataValue(periodLoc)==null)
										{						
											newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
													periodLocValue*type.getProportion());
											newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
													periodLocValueBak*type.getProportion());												
										}
										else 
										{
											newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
													periodLocValue*type.getProportion()+newABUiRowData.pubFun4getPeriodDataValue(periodLoc));
												newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
														periodLocValueBak*type.getProportion()+newABUiRowData.pubFun4getPeriodDataValueBak(periodLoc));	
										}	
									}
									else
									{
										if(newABUiRowData.pubFun4getPeriodDataValue(periodLoc)==null)
										{						
											newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
													periodLocValue*type.getProportion()/unit.getExchangeRate());
											newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
													periodLocValueBak*type.getProportion()/unit.getExchangeRate());												
										}
										else 
										{
											newABUiRowData.pubFun4setPeriodDataValue(periodLoc,
													periodLocValue*type.getProportion()/unit.getExchangeRate()+newABUiRowData.pubFun4getPeriodDataValue(periodLoc));
												newABUiRowData.pubFun4setPeriodDataValueBak(periodLoc,
														periodLocValueBak*type.getProportion()/unit.getExchangeRate()+newABUiRowData.pubFun4getPeriodDataValueBak(periodLoc));	
										}	
									}
								}
							}
						}
					}
					result.add(newABUiRowData);
				}
			}
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	//多单位显示end
	
}


/**************************************************************************
 * 
 * $RCSfile: UiRowDataService.java,v $ $Revision: 1.5 $ $Date: 
 * $
 * 
 * $Log: UiRowDataService.java,v $
 * Revision 1.5  2010/08/08 07:56:21  liuzhen
 * 2010.08.08 by liuzhen
 * Kpi锟斤拷锟斤拷锟斤拷票浠拷锟斤拷锟斤拷锟斤拷臃锟侥革拷锟斤拷突锟斤拷锟饺ワ拷锟斤拷锟�
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.4  2010/07/14 11:37:29  liuzhen
 * 2010.07.14 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.3  2010/07/12 14:31:07  liuzhen
 * 2010.07.12 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.2  2010/07/12 13:22:28  liuzhen
 * 2010.07.12 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2010/07/12 08:46:38  liuzhen
 * 2010.07.12 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 
 * 
 * 
 ***************************************************************************/
