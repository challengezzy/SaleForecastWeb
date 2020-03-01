package dmdd.dmddjava.service.invreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;

/**
 * <p>库存报表中，预测类数据查询服务类</p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Dec 24, 2017 10:31:25 AM
 */
public class StockRowDataQueryService {
	
	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());
	
	/**
	 * 预测表数据查询forecastdata
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizData4Final
	 * @return
	 * @throws Exception
	 */
	private ABUiRowData getUiRowDatas4Forecast(BProduct product,int periodBegin, int periodEnd, BBizData bizData4Final ) throws Exception
	{
		//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowDat
		ABUiRowData abUiRowData = new ABUiRowData();
		abUiRowData.setProduct( product );
		abUiRowData.setBizData( bizData4Final );
		abUiRowData.setPeriodBegin( periodBegin );
		abUiRowData.setPeriodEnd( periodEnd );
		
		//数据查询
		String sql = "select sum(VALUE) VALUE, PERIOD from FORECASTDATA where PRODUCTID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=? group by PERIOD, BIZDATAID";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(),periodBegin,periodEnd,bizData4Final.getId());
		for (int j = 0; j < vos.length; j++) {
			HashVO vo = vos[j];
			int periodLoc = UtilPeriod.getPeriodDifference(periodBegin, vo.getIntegerValue("PERIOD"));
			abUiRowData.pubFun4setPeriodDataValue(periodLoc, vo.getDoubleValue("VALUE"));
			abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, vo.getDoubleValue("VALUE"));
		}
		
		return abUiRowData;
	}
	
	/**
	 * 预测数据查询(组合预测、统计预测、判断预测、最终预测)
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizData
	 * @return
	 * @throws Exception
	 */
	public ABUiRowData getUiRowDatas4BizData(BProduct product,int periodBegin, int periodEnd, BBizData bizData ) throws Exception
	{
		if (bizData.getType() == BizConst.BIZDATA_TYPE_FCCOMB) {
			//组合预测的情况对数据的处理
			return getUiRowDatas4FcComb(product, periodBegin, periodEnd, bizData);
			
		}else if (bizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL || bizData.getType() == BizConst.BIZDATA_TYPE_FCHAND
                || bizData.getType() == BizConst.BIZDATA_TYPE_FCFINAL || bizData.getType() == BizConst.BIZDATA_TYPE_PERIODVERSION ) {
			
			return getUiRowDatas4Forecast(product, periodBegin, periodEnd, bizData);
		}else{
			//不支持的业务数据查询类型
			ABUiRowData abUiRowData = new ABUiRowData();
			abUiRowData.setProduct( product );
			abUiRowData.setBizData( bizData );
			abUiRowData.setPeriodBegin( periodBegin );
			abUiRowData.setPeriodEnd( periodEnd );
			
			return abUiRowData;
		}
	}
	
	/**
	 * 历史类数据查询服务类
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizData
	 * @return
	 * @throws Exception
	 */
	public ABUiRowData getUiRowDatas4His(BProduct product,int periodBegin, int periodEnd, BBizData bizDataHis ) throws Exception
	{
		//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowDat
		ABUiRowData abUiRowData = new ABUiRowData();
		abUiRowData.setProduct( product );
		abUiRowData.setBizData( bizDataHis );
		abUiRowData.setPeriodBegin( periodBegin );
		abUiRowData.setPeriodEnd( periodEnd );
		
		//数据查询,添加bizData判断，防止空指针异常
		if (bizDataHis != null) {
			String sql = "select sum(VALUE) VALUE, PERIOD from HISTORYDATA where PRODUCTID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=? group by PERIOD, BIZDATAID";
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(),periodBegin,periodEnd,bizDataHis.getId());
			for (int j = 0; j < vos.length; j++) {
				HashVO vo = vos[j];
				int periodLoc = UtilPeriod.getPeriodDifference(periodBegin, vo.getIntegerValue("PERIOD"));
				abUiRowData.pubFun4setPeriodDataValue(periodLoc, vo.getDoubleValue("VALUE"));
				abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, vo.getDoubleValue("VALUE"));
			}
		}
		return abUiRowData;
	}
	
	/**
	 * 组合业务数据查询
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizDataComb
	 * @return
	 * @throws Exception
	 */
	public ABUiRowData getUiRowDatas4FcComb( BProduct product,int periodBegin, int periodEnd, BBizData bizDataComb) throws Exception
	{
		//收集还需要查询的预测类业务数据,3大类
		List<BBizData> listBBizData4FcMH = new ArrayList<BBizData>();
		List<BBizData> listBBizData4TimeFc = new ArrayList<BBizData>();
		List<BBizData> listBBizData4Version = new ArrayList<BBizData>();
		
		if( bizDataComb.getBizDataDefItems() == null || bizDataComb.getBizDataDefItems().isEmpty() ){
			logger.error("组合业务数据["+ bizDataComb.getName() +"]下没有定义明细数据，无法查询数据!!!!!!");
			throw new Exception("组合业务数据["+ bizDataComb.getName() +"]下没有定义明细数据，无法查询数据!!!!!!");
		}
		
		//遍历组合业务数据定义项,3大类
		Iterator<BBizDataDefItem> itr_BBizDataDefItems = bizDataComb.getBizDataDefItems().iterator();
		while( itr_BBizDataDefItems.hasNext() )
		{
			BBizDataDefItem bBizDataDefItem = itr_BBizDataDefItems.next();
			BBizData itemBizData = ((BBizDataDefItemFcComb)bBizDataDefItem).getItemBizData();
			if( itemBizData.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					itemBizData.getType() == BizConst.BIZDATA_TYPE_FCHAND )
			{
				listBBizData4FcMH.add(itemBizData);
			}
			else if( itemBizData.getType() == BizConst.BIZDATA_TYPE_TIMEFC )
			{
				listBBizData4TimeFc.add(itemBizData);
			}
			else if( itemBizData.getType() == BizConst.BIZDATA_TYPE_FORECASTASSESSMENT )
			{
				listBBizData4Version.add(itemBizData);
			}
		}
		
		//查询出的业务数据子项 <bizdataId,ABUiRowData>
		HashMap<Long, ABUiRowData> itemBizRowDataMap = new HashMap<Long, ABUiRowData>();
		
		
		if( CollectionUtils.isNotEmpty(listBBizData4FcMH) ){
			//查询预测数据
			for(BBizData itemBizData4FcMH : listBBizData4FcMH){
				ABUiRowData rowData4FcMH = getUiRowDatas4Forecast(product, periodBegin, periodEnd, itemBizData4FcMH);
				itemBizRowDataMap.put(itemBizData4FcMH.getId(), rowData4FcMH);
			}
		}
		
		if( CollectionUtils.isNotEmpty(listBBizData4TimeFc) )
		{//查询同期预测数据
			for(BBizData itemBizData4TimeFc : listBBizData4TimeFc){
				ABUiRowData rowData4FcMH = getUiRowDatas4TimeFc(product, periodBegin, periodEnd, itemBizData4TimeFc);
				itemBizRowDataMap.put(itemBizData4TimeFc.getId(), rowData4FcMH);
			}
		}
		
		//版本类数据
		if( CollectionUtils.isNotEmpty(listBBizData4Version) )
		{
			for(BBizData itemBizData4Version : listBBizData4Version){
				ABUiRowData rowData4Version = getUiRowDatas4TimeFc(product, periodBegin, periodEnd, itemBizData4Version);
				itemBizRowDataMap.put(itemBizData4Version.getId(), rowData4Version);
			}
		}
		
		//计算组合预测
		ABUiRowData combABUiRowData = new ABUiRowData();
		combABUiRowData.setProduct(product);
		combABUiRowData.setBizData( bizDataComb );
		combABUiRowData.setPeriodBegin( periodBegin );
		combABUiRowData.setPeriodEnd( periodBegin );
		
		int periodDiff = UtilPeriod.getPeriodDifference( periodBegin, periodEnd );
		Iterator<BBizDataDefItem> itrDefItems = bizDataComb.getBizDataDefItems().iterator();
		while( itrDefItems.hasNext() )
		{
			BBizDataDefItem bBizDataDefItem = itrDefItems.next();
			BBizData itemBizData = ((BBizDataDefItemFcComb)bBizDataDefItem).getItemBizData();
			Double coefficient = ((BBizDataDefItemFcComb)bBizDataDefItem).getCoefficient(); //占比系数
			ABUiRowData abUiRowData_item = itemBizRowDataMap.get( itemBizData.getId() );
			if( abUiRowData_item != null )
			{
				//	宽松条件，只要有一个组合项存在，则该记录有效，虽然数据不准
				for( int periodLoc=0; periodLoc<=periodDiff; periodLoc++ )
				{
					Double value = combABUiRowData.pubFun4getPeriodDataValue( periodLoc );
					if( value == null ){
						value = 0d;
					}
					 
					Double value_item = abUiRowData_item.pubFun4getPeriodDataValue( periodLoc );
					if( value_item == null ){
						value_item = 0d;
					}
					value =  value + value_item * coefficient ;
					combABUiRowData.pubFun4setPeriodDataValue( periodLoc, value );
					combABUiRowData.pubFun4setPeriodDataValueBak( periodLoc, value );
				}
			}
		}
				
		return combABUiRowData;
	}
	
	
	
	/**
	 * 版本预测类数据查询 FORECASTASSESSMENTDATA
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizData
	 * @return
	 * @throws Exception
	 */
	public ABUiRowData getUiRowDatas4Version(BProduct product,int periodBegin, int periodEnd, BBizData bizData ) throws Exception
	{
		//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowDat
		ABUiRowData abUiRowData = new ABUiRowData();
		abUiRowData.setProduct( product );
		abUiRowData.setBizData( bizData );
		abUiRowData.setPeriodBegin( periodBegin );
		abUiRowData.setPeriodEnd( periodEnd );
		
		//数据查询
		String sql = "select sum(VALUE) VALUE, PERIOD from FORECASTASSESSMENTDATA where PRODUCTID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=? group by PERIOD, BIZDATAID";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(),periodBegin,periodEnd,bizData.getId());
		for (int j = 0; j < vos.length; j++) {
			HashVO vo = vos[j];
			int periodLoc = UtilPeriod.getPeriodDifference(periodBegin, vo.getIntegerValue("PERIOD"));
			abUiRowData.pubFun4setPeriodDataValue(periodLoc, vo.getDoubleValue("VALUE"));
			abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, vo.getDoubleValue("VALUE"));
		}
		
		return abUiRowData;
	}
	
	/**
	 * 查询同期预测的预测数据
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizData
	 * @return
	 * @throws Exception
	 */
	private ABUiRowData getUiRowDatas4TimeFc( BProduct product,int periodBegin, int periodEnd, BBizData bizData4TimeFc) throws Exception
	{
		//解析出同期预测的业务数据定义  业务数据、期间公司
		Iterator<BBizDataDefItem> itr_bizDataDefItems = bizData4TimeFc.getBizDataDefItems().iterator();
		BBizDataDefItem bBizDataDefItem = itr_bizDataDefItems.next();

		int periodDiff2Current = 0;
		int timeFormulaDictValue = -1;
		BBizData itemBizDataTimeFc = ((BBizDataDefItemTimeFc) bBizDataDefItem).getItemBizData();
		timeFormulaDictValue = ((BBizDataDefItemTimeFc) bBizDataDefItem).getTimeFormula();
		
		if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST1YEAR ){
			periodDiff2Current = ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		}
		else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST2YEAR ){
			periodDiff2Current = 2*ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		}
		else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_COUNTERPARTOFLAST3YEAR ){
			periodDiff2Current = 3*ServerEnvironment.getInstance().getSysPeriod().getPeriodNumPerYear();
		}	
		else if( timeFormulaDictValue == BizConst.BIZDATADEFITEM_TIMEFORMULA_LAST1PERIOD ){
			periodDiff2Current = 1;
		}
			
		ABUiRowData abUiRowData = new ABUiRowData();
		abUiRowData.setProduct( product );
		abUiRowData.setBizData( bizData4TimeFc );
		abUiRowData.setPeriodBegin( periodBegin );
		abUiRowData.setPeriodEnd( periodEnd );
		
		int queryPeriodBegin = UtilPeriod.getPeriod( periodBegin, -periodDiff2Current);
		int queryPeriodEnd = UtilPeriod.getPeriod( periodEnd, -periodDiff2Current);
		//数据查询
		String sql = "select sum(VALUE) VALUE, PERIOD from FORECASTDATA where PRODUCTID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=? group by PERIOD, BIZDATAID";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(),queryPeriodBegin,queryPeriodEnd,itemBizDataTimeFc.getId());
		
		for (int j = 0; j < vos.length; j++) {
			HashVO vo = vos[j];
			//期间和查询出的期间值作相应平移
			int periodLoc = UtilPeriod.getPeriodDifference( periodBegin, UtilPeriod.getPeriod(vo.getIntegerValue("PERIOD"), periodDiff2Current) );
			abUiRowData.pubFun4setPeriodDataValue(periodLoc, vo.getDoubleValue("VALUE"));
			abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, vo.getDoubleValue("VALUE"));
		}
		
		return abUiRowData;
	}

}
