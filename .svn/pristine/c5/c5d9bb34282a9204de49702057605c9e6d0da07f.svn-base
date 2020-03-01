package dmdd.dmddjava.service.uiservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAddFc;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAddHis;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemAvgHis;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemKpi;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoney;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemMoneyComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeHis;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastAssessment;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;

public class UiDataQueryService {
	
    private Logger logger = CoolLogger.getLogger(this.getClass());
	//private static ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(30);
	
	public List<ABUiRowData> getUiRowDatas( List<ABUiRowDataProOrg> _list4ABUiRowDataProOrg, final int _periodBegin, final int _periodEnd, List<BBizData> _list4BBizData ) throws Exception
	{
	    //检验产品组织数据
        if (_list4ABUiRowDataProOrg == null || _list4ABUiRowDataProOrg.isEmpty()) {
            return Collections.emptyList();
        }
        //检验期间
        int periodDiff = UtilPeriod.getPeriodDifference(_periodBegin, _periodEnd);
        if (periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0) {
            return Collections.emptyList();
        }

        //业务数据
        if (_list4BBizData == null || _list4BBizData.isEmpty()) {
            return Collections.emptyList();
        }
        
        //  把业务数据分类     begin
        final List<BBizData> list4BBizData4History = new ArrayList<BBizData>();
        final List<BBizData> list4BBizData4TimeHis = new ArrayList<BBizData>(); //同期历史
        final List<BBizData> list4BBizData4FcMHF = new ArrayList<BBizData>(); //  判断预测 FcModel FcHand FcFinal
        final List<BBizData> list4BBizData4TimeFc = new ArrayList<BBizData>(); // TimeFc : FcComb may depend on TimeFc, so we put TimeFc before FcComb
        final List<BBizData> list4BBizData4FcComb = new ArrayList<BBizData>();    //组合预测      
        final List<BBizData> list4BBizData4Money = new ArrayList<BBizData>(); //金额
        final List<BBizData> list4BBizData4MoneyComb = new ArrayList<BBizData>();//金额组合
        final List<BBizData> list4BBizData4Kpi = new ArrayList<BBizData>(); //KPI
        final List<BBizData> list4BBizDataAssessment = new ArrayList<BBizData>();//版本预测
        final List<BBizData> list4BBizDataAddHis = new ArrayList<BBizData>(); //历史累积
        final List<BBizData> list4BBizDataAddFc = new ArrayList<BBizData>(); //预测累积
        final List<BBizData> list4BBizDataAvgHis = new ArrayList<BBizData>();//历史平均
        
        for (int i = 0; i < _list4BBizData.size(); i++) {
            BBizData bBizData = _list4BBizData.get(i);

            if (bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORY || bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYAD
                    || bBizData.getType() == BizConst.BIZDATA_TYPE_HISTORYADR) {
                list4BBizData4History.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEHIS) {
                list4BBizData4TimeHis.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL || bBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND
                    || bBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL || bBizData.getType() == BizConst.BIZDATA_TYPE_PERIODVERSION ) {
            	//此3种归为一类的原因是，它们不需要计算，查出的值即是显示值
                list4BBizData4FcMHF.add(bBizData);
                
            }else if (bBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC) {
                list4BBizData4TimeFc.add(bBizData);
            }else if (bBizData.getType() == BizConst.BIZDATA_TYPE_FCCOMB) {
                list4BBizData4FcComb.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_MONEY) {
                list4BBizData4Money.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_MONEYCOMB) {
                list4BBizData4MoneyComb.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_KPI) {
                list4BBizData4Kpi.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT) {
                list4BBizDataAssessment.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_ADDHIS){
            	list4BBizDataAddHis.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_ADDFC){
            	list4BBizDataAddFc.add(bBizData);
            } else if (bBizData.getType() == BizConst.BIZDATA_TYPE_AVGHIS){
            	list4BBizDataAvgHis.add(bBizData);
            } 
        }   
        //  把业务数据分类     end
        
        List<ABUiRowData> unsafeList = new ArrayList<ABUiRowData>(_list4ABUiRowDataProOrg.size()*_list4BBizData.size());
        final List<ABUiRowData> rstList = Collections.synchronizedList(unsafeList);//使用线程安全的list,后续对该list做多线程add
        final CountDownLatch latch = new CountDownLatch(_list4ABUiRowDataProOrg.size());
        
        //logger.info("当前活跃线程数["+threadPool.getActiveCount()+"],PoolSize["+threadPool.getPoolSize()+"],本次处理的任务线程数["+ latch.getCount() +"]") ;
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor)Executors.newFixedThreadPool(8);
        for( int i=0; i<_list4ABUiRowDataProOrg.size(); i=i+1 ) {
            final ABUiRowDataProOrg abUiRowDataProOrg = _list4ABUiRowDataProOrg.get( i );
            threadPool.execute(new Runnable() {
                
                public void run() {
                    try {
                        //  对一个abUiRowDataProOrg查询所有需要查询的业务数据的数据
                        List<ABUiRowData> tmpList = new ArrayList<ABUiRowData>();   // 本 abUiRowDataProOrg 的所有业务数据的数据，用于帮助计算kppi

                        //  拼出明细范围字符串       begin
                        String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs( abUiRowDataProOrg );
                        //  拼出明细范围字符串       end
                        
                        //  历史类数据       begin
                        List<ABUiRowData> list4ABUiRowData4History = getUiRowDatas4History( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4History );
                        rstList.addAll( list4ABUiRowData4History );
                        tmpList.addAll( list4ABUiRowData4History );
                        //  历史类数据       end 
                        
                        //  期间历史数据      begin               
                        List<ABUiRowData> list4ABUiRowData4TimeHis = getUiRowDatas4TimeHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeHis );
                        rstList.addAll( list4ABUiRowData4TimeHis );
                        tmpList.addAll( list4ABUiRowData4TimeHis );
                        //  期间历史数据      end             
                        
                        // 预测类数据  M-N版本预测类数据、人工预测、最终预测、统计预测
                        List<ABUiRowData> list4ABUiRowData4FcMHF = getUiRowDatas4FcMHF( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcMHF );
                        rstList.addAll( list4ABUiRowData4FcMHF );
                        tmpList.addAll( list4ABUiRowData4FcMHF );
                        //  预测类数据       end 
                        
                        //  TimeFc      begin
                        //  2011.07.01 by liuzhen: FcComb may depend on TimeFc, so we put TimeFc before FcComb 
                        List<ABUiRowData> list4ABUiRowData4TimeFc = getUiRowDatas4TimeFc( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4TimeFc );
                        rstList.addAll( list4ABUiRowData4TimeFc );
                        tmpList.addAll( list4ABUiRowData4TimeFc );
                        //  TimeFc      end
                        
                        //历史累积数据
                        List<ABUiRowData> list4ABUiRowData4AddHis = getUiRowDatas4AddHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAddHis );
                        rstList.addAll( list4ABUiRowData4AddHis );
                        
                        //预测累积数据
                        List<ABUiRowData> list4ABUiRowData4AddFc = getUiRowDatas4AddFc( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAddFc );
                        rstList.addAll( list4ABUiRowData4AddFc );
                        
                        //历史平均数据
                        List<ABUiRowData> list4ABUiRowData4AvgHis = getUiRowDatas4AvgHis( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAvgHis );
                        rstList.addAll( list4ABUiRowData4AvgHis );
                        
                        //预测考核类,版本预测 begin
                        List<ABUiRowData> list4ABUiRowData4ForecastAssessment = getUiRowDatas4ForecastAssessment( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAssessment );
                        rstList.addAll( list4ABUiRowData4ForecastAssessment );
                        //预测考核类 begin
                        
                        //组合预测数据组成：统计、判断、最终+同期预测+版本预测(20170611 add)
                        List<ABUiRowData> list4ABUiRowData4FcMHT = new ArrayList<ABUiRowData>();
                        list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4FcMHF );
                        list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4TimeFc );
                        list4ABUiRowData4FcMHT.addAll( list4ABUiRowData4ForecastAssessment);//版本预测值
                        List<ABUiRowData> list4ABUiRowData4FcComb = getUiRowDatas4FcComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4FcComb, list4ABUiRowData4FcMHT );
                        //  modified by liuzhen, 2011.07.01 end
                        rstList.addAll( list4ABUiRowData4FcComb );
                        tmpList.addAll( list4ABUiRowData4FcComb );
                        
                        //  金额数据        begin               
                        List<ABUiRowData> list4ABUiRowData4Money = getUiRowDatas4Money( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4Money );
                        rstList.addAll( list4ABUiRowData4Money );
                        tmpList.addAll( list4ABUiRowData4Money );
                        //  金额数据        end
                        
                        //  组合金额数据      begin               
                        List<ABUiRowData> list4ABUiRowData4MoneyComb = getUiRowDatas4MoneyComb( abUiRowDataProOrg, detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizData4MoneyComb, list4ABUiRowData4Money );
                        rstList.addAll( list4ABUiRowData4MoneyComb );
                        tmpList.addAll( list4ABUiRowData4MoneyComb );
                        //  组合金额数据      end
                        
                        //  Kpi类数据      begin
                        List<ABUiRowData> list4ABUiRowData4Kpi = getUiRowDatas4Kpi( abUiRowDataProOrg, _periodBegin, _periodEnd, list4BBizData4Kpi, tmpList );
                        rstList.addAll( list4ABUiRowData4Kpi );
                        //  Kpi类数据      end
                       } catch (Exception e) {
                        	e.printStackTrace();
                        	logger.error(e);
                       } finally {
                           latch.countDown();
                       }
                }
            });
            
        }
        latch.await(2400, TimeUnit.SECONDS);
        threadPool.shutdownNow();
        return rstList;
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
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4History) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
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
				if( this.isExistHistoryDatas( abUiRowData, _detailProOrgIdStr ) == true )
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
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4TimeHis) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
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
			if (bBizDataDefItem instanceof BBizDataDefItemTimeHis) {
				itemBizDataHistory = ((BBizDataDefItemTimeHis) bBizDataDefItem).getItemBizData();
				timeFormulaDictValue = ((BBizDataDefItemTimeHis) bBizDataDefItem).getTimeFormula();
			} else {
				continue;
			}
			
			if (timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST1YEAR) {
				
				periodDiff2Current = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
				
			} else if (timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST2YEAR) {
				
				periodDiff2Current = 2 * ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
				
			} else if (timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST3YEAR) {
				
				periodDiff2Current = 3 * ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
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
			try {
				trsa = session.beginTransaction();
				DaoHistoryData daoHistoryData = new DaoHistoryData(session);
				listASumData = daoHistoryData.getSumHistoryDatas(_abUiRowDataProOrg.getDetailProOrgIds(), UtilPeriod.getPeriod(_periodBegin, -periodDiff2Current),
						UtilPeriod.getPeriod(_periodEnd, -periodDiff2Current), itemBizDataHistory.getId());
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
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, UtilPeriod.getPeriod( aSumData.getPeriod(), periodDiff2Current ) );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
				}
			}
			
			if (this.isExistHistoryDatas4TimeHis(newABUiRowData, _detailProOrgIdStr, periodDiff2Current) == true) {
				rstList.add(newABUiRowData);
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
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4FcMHF) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
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
				if ( this.isExistForecastDatas(abUiRowData, _detailProOrgIdStr) == true) {
					rstList.add(abUiRowData);
				}
			}
		}

		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}
	
	/**
	 * 查询历史累积数据，从年初到当前期间的数据
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4AddHis
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4AvgHis( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4AvgHis ) throws Exception
	{
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4AvgHis) ){
			return Collections.emptyList();
		}
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		//	查询预测类数据并填充结果数据集	begin		
		for( int j=0; j<_list4BBizData4AvgHis.size(); j=j+1 )
		{
			BBizData bBizData4AddHis = _list4BBizData4AvgHis.get( j );
			
			//该平均数据对应的业务数据
			BBizDataDefItemAvgHis bBizDataDefItem = null;
			if( bBizData4AddHis.getBizDataDefItems() == null || bBizData4AddHis.getBizDataDefItems().isEmpty() )
			{
				continue; //没有定义对应的业务数据,是否要返回异常信息到前端?
			}
			
			Iterator<BBizDataDefItem> itr_bizDataDefItems = bBizData4AddHis.getBizDataDefItems().iterator();
			bBizDataDefItem = (BBizDataDefItemAvgHis)itr_bizDataDefItems.next();

			//历史平均数据中定义的历史类业务数据
			BBizData itemBizDataHistory = bBizDataDefItem.getItemBizData();
			int avgNum = bBizDataDefItem.getPeriodNum(); //平均期间数
			
			//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4AddHis );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			List<ASumData> listASumData = new ArrayList<ASumData>();
			
			Session session = HibernateSessionFactory.getSession();
			int periodStart = UtilPeriod.getPeriod(_periodBegin, -avgNum);
			try{
				DaoHistoryData daoHistoryData = new DaoHistoryData(session);
				//包含不在查询范围内的业务数据,已经是按期间排好序的
				List<ASumData> allListASumData = daoHistoryData.getSumHistoryDatas( _abUiRowDataProOrg.getDetailProOrgIds(),periodStart,_periodEnd, itemBizDataHistory.getId() );
				
				Queue<Double> avgQueue = new LinkedList<Double>(); //分别存放向前推N期的平均值
				Queue<Double> tmpQueue = new LinkedList<Double>(); //临时队列
				
				//前N期平均值计算，不包括当前期间
				for(ASumData asumData : allListASumData){
					int dataPeriod = asumData.getPeriod();
					Double curVal = asumData.getValue();
					
					double avgNVal = 0;
					if(avgQueue.size() > 0){
						avgNVal = avgQueue.peek(); //从队列中取出第一个元素,先取出来。因为历史平均不包含当前期间值
						tmpQueue = new LinkedList<Double>(); //临时队列
					}
					
					//遍历queue,加入当前期间和现有数据计算平均值
					int n = avgQueue.size()+1;
					for(Double val : avgQueue){ 
						double newVal = ( val + curVal ) /n ;
						tmpQueue.offer(newVal); //这里的数据不对，更新值没有进入到队列中,使用临时队列!!!!
						n--;
					}
					
					avgQueue = tmpQueue;
					//加入当前期间数据到队列尾部
					avgQueue.offer(curVal);
					
					if(dataPeriod >= _periodBegin ){ //未到有效的数据期间内
						if(avgQueue.size() > avgNum){
							avgQueue.remove(); //移除第一个元素
						}
						asumData.setValue(avgNVal);
						//有效的数据,加入结果队列
						listASumData.add(asumData);
					}
					
				}//end for
			}
			catch( Exception ex ){
				logger.error("查询历史累积数据失败!");
				ex.printStackTrace();
				throw ex;
			}finally{
				session.close();
			}	
			
			//查询到的数据填充到abUiRowData
			if( listASumData != null && !(listASumData.isEmpty()) ){
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
				}
			}
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			//加入到结果集中
			rstList.add(newABUiRowData);
			
		}
		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}
	
	/**
	 * 查询历史累积数据，从年初到当前期间的数据
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4AddHis
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4AddHis( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4AddHis ) throws Exception
	{
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4AddHis) ){
			return Collections.emptyList();
		}
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		//	查询预测类数据并填充结果数据集	begin		
		for( int j=0; j<_list4BBizData4AddHis.size(); j=j+1 )
		{
			BBizData bBizData4AddHis = _list4BBizData4AddHis.get( j );
			
			//该累积数据对应的业务数据
			BBizDataDefItem bBizDataDefItem = null;
			if( bBizData4AddHis.getBizDataDefItems() == null || bBizData4AddHis.getBizDataDefItems().isEmpty() )
			{
				continue; //没有定义对应的业务数据,是否要返回异常信息到前端?
			}
			
			Iterator<BBizDataDefItem> itr_bizDataDefItems = bBizData4AddHis.getBizDataDefItems().iterator();
			bBizDataDefItem = itr_bizDataDefItems.next();

			//历史累积数据中定义的历史类业务数据
			BBizData itemBizDataHistory = ((BBizDataDefItemAddHis) bBizDataDefItem).getItemBizData();
			
			//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4AddHis );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			List<ASumData> listASumData = new ArrayList<ASumData>();
			
			Session session = HibernateSessionFactory.getSession();
			//开始期间的年份每一个期间
			int period01 = UtilPeriod.getPeriodYearBegin(_periodBegin);
			try{
				DaoHistoryData daoHistoryData = new DaoHistoryData(session);
				//包含不在查询范围内的业务数据,已经是按期间排好序的
				List<ASumData> allListASumData = daoHistoryData.getSumHistoryDatas( _abUiRowDataProOrg.getDetailProOrgIds(),period01,_periodEnd, itemBizDataHistory.getId() );
				
				//第一期期间所在年份
				int beginYear = UtilPeriod.getPeriodYear(period01);
				double ytdValue = 0;
				//累加计算
				for(ASumData asumData : allListASumData){
					int periodYear  = UtilPeriod.getPeriodYear(asumData.getPeriod());
					if(periodYear == beginYear){
						ytdValue += asumData.getValue();
						
					}else{//是新的一年
						ytdValue = asumData.getValue();
						beginYear = periodYear;
					}
					
					if(asumData.getPeriod() >= _periodBegin ){
						asumData.setValue(ytdValue);
						//有效的数据
						listASumData.add(asumData);
					}
				}//end for
			}
			catch( Exception ex ){
				logger.error("查询历史累积数据失败!");
				ex.printStackTrace();
				throw ex;
			}finally{
				session.close();
			}	
			
			//查询到的数据填充到abUiRowData
			if( listASumData != null && !(listASumData.isEmpty()) ){
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
				}
			}
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			//加入到结果集中
			rstList.add(newABUiRowData);
			
		}
		//	查询预测类数据并填充结果数据集		end			
		
		return rstList;
	}
	
	/**
	 * 查询历史累积数据，从年初到当前期间的数据
	 * @param _abUiRowDataProOrg
	 * @param _detailProOrgIdStr
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData4AddHis
	 * @return
	 * @throws Exception
	 */
	private List<ABUiRowData> getUiRowDatas4AddFc( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4AddFc ) throws Exception
	{
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4AddFc) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		//	查询预测类数据并填充结果数据集	begin		
		for( int j=0; j<_list4BBizData4AddFc.size(); j=j+1 )
		{
			BBizData bBizData4AddFc = _list4BBizData4AddFc.get( j );
			
			//该累积数据对应的业务数据
			BBizDataDefItem bBizDataDefItem = null;
			if( bBizData4AddFc.getBizDataDefItems() == null || bBizData4AddFc.getBizDataDefItems().isEmpty() )
			{
				continue; //没有定义对应的业务数据,是否要返回异常信息到前端?
			}
			
			Iterator<BBizDataDefItem> itr_bizDataDefItems = bBizData4AddFc.getBizDataDefItems().iterator();
			bBizDataDefItem = itr_bizDataDefItems.next();

			//历史累积数据中定义的历史类业务数据
			BBizData itemBizDataHistory = ((BBizDataDefItemAddFc) bBizDataDefItem).getItemBizData();
			
			//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	begin
			ABUiRowData newABUiRowData = new ABUiRowData();
			newABUiRowData.setProduct( _abUiRowDataProOrg.getProduct() );
			newABUiRowData.setProductCharacter( _abUiRowDataProOrg.getProductCharacter() );
			newABUiRowData.setOrganization( _abUiRowDataProOrg.getOrganization() );
			newABUiRowData.setOrganizationCharacter( _abUiRowDataProOrg.getOrganizationCharacter() );
			newABUiRowData.setDetailProOrgIds( _abUiRowDataProOrg.getDetailProOrgIds() );
			newABUiRowData.setBizData( bBizData4AddFc );
			newABUiRowData.setPeriodBegin( _periodBegin );
			newABUiRowData.setPeriodEnd( _periodEnd );
			
			List<ASumData> listASumData = new ArrayList<ASumData>();
			
			Session session = HibernateSessionFactory.getSession();
			int period01 = UtilPeriod.getPeriodYearBegin(_periodBegin);
			try{
				DaoForecastData daoForecastData = new DaoForecastData( session );
				//包含不在查询范围内的业务数据,已经是按期间排好序的
				List<ASumData> allListASumData = daoForecastData.getSumForecastDatas( _abUiRowDataProOrg.getDetailProOrgIds(),period01,_periodEnd, itemBizDataHistory.getId() );
				
				int beginYear = UtilPeriod.getPeriodYear(period01);
				double ytdValue = 0;
				//累加计算
				for(ASumData asumData : allListASumData){
					int periodYear  = UtilPeriod.getPeriodYear(asumData.getPeriod());
					if(periodYear == beginYear){
						ytdValue += asumData.getValue();
						
					}else{//是新的一年
						ytdValue = asumData.getValue();
						beginYear = periodYear;
					}
					
					if(asumData.getPeriod() >= _periodBegin ){
						asumData.setValue(ytdValue);
						//有效的数据
						listASumData.add(asumData);
					}
				}//end for
			}
			catch( Exception ex ){
				logger.error("查询预测累积数据失败!");
				ex.printStackTrace();
				throw ex;
			}finally{
				session.close();
			}	
			
			//查询到的数据填充到abUiRowData
			if( listASumData != null && !(listASumData.isEmpty()) ){
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumData.getPeriod() );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
				}
			}
			//	每个业务数据与abUiRowDataProOrg结合生成一条abUiRowData	end
			
			//加入到结果集中
			rstList.add(newABUiRowData);
			
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
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4TimeFc) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
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
			if (bBizDataDefItem instanceof BBizDataDefItemTimeFc) {
				itemBizDataHistory = ((BBizDataDefItemTimeFc) bBizDataDefItem).getItemBizData();
				timeFormulaDictValue = ((BBizDataDefItemTimeFc) bBizDataDefItem).getTimeFormula();
			} else {
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
			try {
				trsa = session.beginTransaction();
				DaoForecastData daoForecastData = new DaoForecastData(session);
				listASumData = daoForecastData.getSumForecastDatas(_abUiRowDataProOrg.getDetailProOrgIds(), UtilPeriod.getPeriod(_periodBegin, -periodDiff2Current),
						UtilPeriod.getPeriod(_periodEnd, -periodDiff2Current), itemBizDataHistory.getId());
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
				for(int k=0; k<listASumData.size(); k=k+1 )
				{
					ASumData aSumData = listASumData.get( k );

					int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, UtilPeriod.getPeriod( aSumData.getPeriod(), periodDiff2Current ) );
					newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumData.getValue() );
					newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumData.getValue() );
				}
			}
			
			if( this.isExistForecastDatas4TimeFc( newABUiRowData, _detailProOrgIdStr, periodDiff2Current ) == true )
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
	public List<ABUiRowData> getUiRowDatas4FcComb( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd,
			List<BBizData> _list4BBizData4FcComb, List<ABUiRowData> _list4ABUiRowData4FcMHT4exist ) throws Exception
	{
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4FcComb) ){
			return Collections.emptyList();
		}
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		//组合预测用到其它的业务数据，如果已经从DB查询过，直接取值使用
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
		
		//	收集还需要查询的预测类业务数据,3大类,增加版本预测	begin
		List<BBizData> listBBizData4FcMH = new ArrayList<BBizData>();
		//HashMap<Long, BBizData> hmap_bizDataId_BBizData_FcMH = new HashMap<Long, BBizData>();
		
		List<BBizData> listBBizData4TimeFc = new ArrayList<BBizData>();
		//HashMap<Long, BBizData> hmap_bizDataId_BBizData_TimeFc = new HashMap<Long, BBizData>();
		
		List<BBizData> listBBizData4Version = new ArrayList<BBizData>();
		//HashMap<Long, BBizData> hmap_bizDataId_BBizData_Version = new HashMap<Long, BBizData>();
		
		for( int i=0; i<_list4BBizData4FcComb.size(); i++ )
		{
			BBizData bBizDataFcComb = _list4BBizData4FcComb.get( i );
			if( bBizDataFcComb.getBizDataDefItems() == null || bBizDataFcComb.getBizDataDefItems().isEmpty() ){
				continue;
			}
			Iterator<BBizDataDefItem> itr_BBizDataDefItems = bBizDataFcComb.getBizDataDefItems().iterator();
			while( itr_BBizDataDefItems.hasNext() )
			{
				BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
				if( bBizDataDefItem instanceof BBizDataDefItemFcComb )
				{
					BBizData itemBizData = ((BBizDataDefItemFcComb)bBizDataDefItem).getItemBizData();
					if( hmap_bizDataId_ABUiRowData.get( itemBizData.getId() ) == null )//不包含在已有数据中
					{
						if( itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
								itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND )
						{
							//hmap_bizDataId_BBizData_FcMH.put( itemBizData.getId(), itemBizData );
							listBBizData4FcMH.add(itemBizData);
						}
						else if( itemBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC )
						{
							//hmap_bizDataId_BBizData_TimeFc.put( itemBizData.getId(), itemBizData );
							listBBizData4TimeFc.add(itemBizData);
						}
						else if( itemBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT )
						{
							//hmap_bizDataId_BBizData_TimeFc.put( itemBizData.getId(), itemBizData );
							listBBizData4Version.add(itemBizData);
						}
					}
				}
			}
		}
		
		//	收集还需要查询的预测类业务数据 end
		
		//查询预测数据	begin
		if( CollectionUtils.isNotEmpty(listBBizData4FcMH) )
		{
			List<ABUiRowData> list4ABUiRowData4unexist = getUiRowDatas4FcMHF( _abUiRowDataProOrg, _detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4FcMH );
			if( list4ABUiRowData4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4unexist.get( i );
					if( this.isExistForecastDatas( abUiRowData, _detailProOrgIdStr ) == true )
					{
						hmap_bizDataId_ABUiRowData.put( abUiRowData.getBizData().getId(), abUiRowData );
					}
				}
			}			
		}
		//	查询预测数据	end
		
		//	Query the TimeFc Data	begin
		if( CollectionUtils.isNotEmpty(listBBizData4TimeFc) )
		{
			List<ABUiRowData> list4ABUiRowData4unexist = this.getUiRowDatas4TimeFc( _abUiRowDataProOrg, _detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4TimeFc );
			if( list4ABUiRowData4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4unexist.get( i );
					if( this.isExistForecastDatas( abUiRowData, _detailProOrgIdStr ) == true )
					{
						hmap_bizDataId_ABUiRowData.put( abUiRowData.getBizData().getId(), abUiRowData );
					}
				}
			}			
		}	
		//	Query the TimeFc Data	end
		
		//版本类数据
		if( CollectionUtils.isNotEmpty(listBBizData4Version) )
		{
			List<ABUiRowData> list4ABUiRowData4unexist = getUiRowDatas4ForecastAssessment( _abUiRowDataProOrg, _detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4Version );
			if( list4ABUiRowData4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4unexist.size(); i++ )
				{
					ABUiRowData abUiRowData = list4ABUiRowData4unexist.get( i );
					if( this.isExistForecastDatas( abUiRowData, _detailProOrgIdStr ) == true )
					{
						hmap_bizDataId_ABUiRowData.put( abUiRowData.getBizData().getId(), abUiRowData );
					}
				}
			}			
		}
		
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
							if( value == null ){
								value = 0d;
							}
							 
							Double value_item = abUiRowData_item.pubFun4getPeriodDataValue( periodLoc );
							if( value_item == null ){
								value_item = 0d;
							}
							
							//value = Math.round( value + value_item * coefficient );
							value =  value + value_item * coefficient ;
							newABUiRowData.pubFun4setPeriodDataValue( periodLoc, value );
							newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, value );
						}
					}
				} //end if
			}
			
			if( isExist == true ){
				rstList.add( newABUiRowData );
			}
			
		}
		//	计算组合预测		end
				
		return rstList;
	}	
		
	
	/** 查询金额类数据 */
	public List<ABUiRowData> getUiRowDatas4Money( ABUiRowDataProOrg _abUiRowDataProOrg, String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData4Money ) throws Exception
	{
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg, _detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4Money) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		//	查询预测类数据并填充结果数据集	begin		
		for( int j=0; j<_list4BBizData4Money.size(); j=j+1 ){
			BBizData bBizData4Money = _list4BBizData4Money.get( j );
			
			//	获取真正去数据库中查询的业务数据和价格类型	begin
			BBizData itemBizData = null;
			int priceTypeDictValue = -1;
			
			if( CollectionUtils.isEmpty(bBizData4Money.getBizDataDefItems()) ){
				continue;
			}
			
			BBizDataDefItem bBizDataDefItem = bBizData4Money.getBizDataDefItems().iterator().next(); 
			if( bBizDataDefItem instanceof BBizDataDefItemMoney )
			{
				//	itemBizData 只会是 历史类 历史调整类 历史调整原因类 模型预测类 人工预测类 最终预测类,且只有一条
				itemBizData = ((BBizDataDefItemMoney) bBizDataDefItem).getItemBizData();
				priceTypeDictValue = ((BBizDataDefItemMoney) bBizDataDefItem).getPriceType();
			}else{
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
					listASumMoney = daoHistoryData.getHistoryDataSumMoneys( _detailProOrgIdStr, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );			
				}
				else if(itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
						itemBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL)
				{
					DaoForecastData daoForecastData = new DaoForecastData( session );			
					listASumMoney = daoForecastData.getForecastDataSumMoneys( _detailProOrgIdStr, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );					
				}else if(itemBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT){
					//20170611 增加版本类数据的支持
					DaoForecastAssessment daoForecastData = new DaoForecastAssessment(session);
					listASumMoney = daoForecastData.getAssessmentDataSumMoneys(_detailProOrgIdStr, _periodBegin, _periodEnd, itemBizData.getId(), priceTypeDictValue );
				}

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
			
			if(CollectionUtils.isEmpty(listASumMoney)){
				return rstList;
			}
			
			for(int k=0; k<listASumMoney.size(); k=k+1 ){
				ASumMoney aSumMoney = listASumMoney.get( k );

				int periodLoc = UtilPeriod.getPeriodDifference( _periodBegin, aSumMoney.getPeriod() );
				newABUiRowData.pubFun4setPeriodDataValue( periodLoc, aSumMoney.getValue() );
				newABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, aSumMoney.getValue() );
			}
			
			if( this.isExistSumMoneys( newABUiRowData, _detailProOrgIdStr, itemBizData, listASumMoney ) == true )
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
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, _list4BBizData4MoneyComb) ){
			return Collections.emptyList();
		}
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		//	收集已经存在的金额类数据,可以免从数据库中再查询begin
		HashMap<Long, ABUiRowData> hmap_bizDataId_ABUiRowData4money = new HashMap<Long, ABUiRowData>();
		if( CollectionUtils.isEmpty(_list4ABUiRowData4Money4exist) ){
			for(ABUiRowData abUiRowData : _list4ABUiRowData4Money4exist ){
				hmap_bizDataId_ABUiRowData4money.put( abUiRowData.getBizData().getId(), abUiRowData );
			}
		}
		
		//	收集还需要查询的金额类业务数据	begin
		List<BBizData> listBBizData4money = new ArrayList<BBizData>();
		
		HashMap<Long, BBizData> hmap_bizDataId_BBizData_money = new HashMap<Long, BBizData>();
				
		for( int i=0; i<_list4BBizData4MoneyComb.size(); i++ )
		{
			BBizData bBizDataMoneyComb = _list4BBizData4MoneyComb.get( i );
			if( CollectionUtils.isEmpty( bBizDataMoneyComb.getBizDataDefItems()) ){
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
		if( CollectionUtils.isNotEmpty(listBBizData4money) )
		{
			//查询金额类数据
			List<ABUiRowData> list4ABUiRowData4money4unexist = getUiRowDatas4Money( _abUiRowDataProOrg, _detailProOrgIdStr, _periodBegin, _periodEnd, listBBizData4money );
			if( list4ABUiRowData4money4unexist != null )
			{
				for( int i=0; i<list4ABUiRowData4money4unexist.size(); i++ ){
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
			if( bBizDataMoneyComb.getBizDataDefItems() == null || bBizDataMoneyComb.getBizDataDefItems().isEmpty() ){
				continue;//组合项为空的数据不处理
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
				if( bBizDataDefItem instanceof BBizDataDefItemMoneyComb ){
					BBizData itemBizData = ((BBizDataDefItemMoneyComb)bBizDataDefItem).getItemBizData();
					Double coefficient = ((BBizDataDefItemMoneyComb)bBizDataDefItem).getCoefficient();
					ABUiRowData abUiRowData_item = hmap_bizDataId_ABUiRowData4money.get( itemBizData.getId() );
					if( abUiRowData_item != null )
					{
						//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准	begin
						isExist = true;
						//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准	end
						for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ ){
							Double value = newABUiRowData.pubFun4getPeriodDataValue( periodLoc );
							if( value == null ){
								value = 0d;
							}
							 
							Double value_item = abUiRowData_item.pubFun4getPeriodDataValue( periodLoc );
							if( value_item == null ){
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
			
			if( isExist == true ){
				rstList.add( newABUiRowData );
			}
			
		}
		//	计算组合金额		end
		
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
			isExist = daoHistoryData.isExistHistoryDatas( _abUiRowData.getDetailProOrgIds(), periodBegin, periodEnd, bizDataId );
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
			isExist = daoHistoryData.isExistHistoryDatas( _abUiRowData.getDetailProOrgIds(), periodBegin, periodEnd, bizDataId );
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
			isExist = daoForecastData.isExistForecastDatas( _abUiRowData.getDetailProOrgIds(), periodBegin, periodEnd, _abUiRowData.getBizData().getId() );
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
			isExist = daoForecastData.isExistForecastDatas( _abUiRowData.getDetailProOrgIds(), periodBegin, periodEnd, _abUiRowData.getBizData().getId() );
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
				isExist = daoHistoryData.isExistHistoryDatas( _abUiRowData.getDetailProOrgIds(), periodBegin, periodEnd, _itemBizData.getId() );				
			}
			else if( _itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
					_itemBizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
			{
				DaoForecastData daoForecastData = new DaoForecastData( session );
				isExist = daoForecastData.isExistForecastDatas( _abUiRowData.getDetailProOrgIds(), periodBegin, periodEnd, _itemBizData.getId() );				
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
		//入参检验
		if( isInvalidParam(_abUiRowDataProOrg,_detailProOrgIdStr, _periodBegin, _periodEnd, list4BBizDataAssessment) ){
			return Collections.emptyList();
		}
		
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
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
		try {
			trsa = session.beginTransaction();
			DaoForecastAssessment daoForecastData = new DaoForecastAssessment(session);
			listASumData = daoForecastData.getForecastAssessment(_abUiRowDataProOrg.getDetailProOrgIds(), _periodBegin, _periodEnd, list4BizDataId);
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
		
		Iterator<ABUiRowData> itr_hmap_bizDataId_ABUiRowData_values = hmap_bizDataId_ABUiRowData.values().iterator();
		while( itr_hmap_bizDataId_ABUiRowData_values.hasNext() )
		{
			ABUiRowData abUiRowData = itr_hmap_bizDataId_ABUiRowData_values.next();
			if( this.isExistForecastDatas( abUiRowData, _detailProOrgIdStr ) == true ) 
			{
				rstList.add( abUiRowData );
			}
		}
		//	查询预测类数据并填充结果数据集		end			
		
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
			//newABUiRowData.setShowPercent(1);//以百分比显示
			
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
	
	/**
	 * 检验期间、业务数据、RowData数据、明细proorgid,是否有空数据
	 * @param _abUiRowDataProOrg
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BBizData
	 * @return
	 * @throws Exception
	 */
	public boolean isInvalidParam( ABUiRowDataProOrg _abUiRowDataProOrg,String _detailProOrgIdStr, int _periodBegin, int _periodEnd, List<BBizData> _list4BBizData ) throws Exception{
		
		if( CollectionUtils.isEmpty(_list4BBizData) ){
			return true;
		}
		
		int periodDiff = UtilPeriod.getPeriodDifference( _periodBegin, _periodEnd );
		if( periodDiff == SysConst.PERIOD_DIFF_NULL || periodDiff < 0 ){
			return true;
		}
		
		if( StringUtils.isEmpty(_detailProOrgIdStr) ){
			return true;
		}
		
		if( _abUiRowDataProOrg == null ){
			return true;
		}
		
		return false;
	}
	

}
