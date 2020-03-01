package dmdd.dmddjava.service.forecastservice;

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
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.service.dimensionservice.BizDataService;

public class PeriodVersionService {
	
	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());
	
	private BizDataService bizDataService = new BizDataService();
	
	/**
	 * 刷新所有的M-N版本预测类数据
	 * @param curPeriod
	 * @throws Exception
	 */
	public void updateAllPeriodVersionData(int curPeriod) throws Exception{
		
		//查询所有期间版本类业务数据
		String sqlBizdata = "SELECT B.ID,B.CODE,B.NAME,B.TYPE,D.ITEMBIZDATAID,D.PERIOD_INTERVAL,FB.TYPE FCBIZDATA_TYPE,FB.CODE FCBIZDATA_CODE "
			+ " FROM BIZDATA B,BIZDATADEFITEM I,BIZDATADEFITEM_PERIODVERSION D,BIZDATA FB" 
			+ " WHERE B.ISVALID=1 AND I.BIZDATAID=B.ID AND I.ID=D.ID AND FB.ID=D.ITEMBIZDATAID " ;
		
		HashVO[] bizdataVos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlBizdata);
		
		String delVersionSql = "DELETE FORECASTDATA WHERE BIZDATAID=? AND PERIOD=?";
		
		logger.info("共["+bizdataVos.length+"]条版本预测数据需要处理。");
		int num = 0;
		ArrayList<HashVO> comboBizVoList = new ArrayList<HashVO>();
		for (int j = 0; j < bizdataVos.length; j++) {
			
			HashVO vo = bizdataVos[j];
			int fcBizDataType = vo.getIntegerValue("FCBIZDATA_TYPE");
			String versionBizId = vo.getStringValue("id");
			String versionBizName = vo.getStringValue("name");
			String fcBizId = vo.getStringValue("itembizdataid");
			int interval = vo.getIntegerValue("period_interval");
			int fcPeriod = UtilPeriod.getPeriod(curPeriod, interval); //获取要查询预测数据的期间
			
			//删除当前期间的预测，更新为最新的数据
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delVersionSql, versionBizId, fcPeriod);
			dmo.commit(DMOConst.DS_DEFAULT);
			
			if( BizConst.BIZDATA_TYPE_FCCOMB == fcBizDataType){ //组合预测的版本数据单独处理，不能直接COPY出来
				comboBizVoList.add(vo);
				continue;
			}

			logger.info("开始处理["+versionBizName+"]的版本预测数据...");

			String copySql = "INSERT INTO FORECASTDATA(BIZDATAID,PRODUCTID,ORGANIZATIONID,PERIOD,VALUE,VERSION,INITTIME,UPDATETIME,COMMENTS,STATUS) " +
				" SELECT " + versionBizId +
				" ,PRODUCTID,ORGANIZATIONID,PERIOD,VALUE,VERSION,getdate(),getdate(),'期间滚动时复制',STATUS" +
				" FROM FORECASTDATA WHERE BIZDATAID=? AND PERIOD=? ";
			
			int rows = dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, copySql, fcBizId, fcPeriod);
			num += rows;
			dmo.commit(DMOConst.DS_DEFAULT);
			logger.info("处理["+versionBizName+"]的版本预测数据完成,复制["+rows+"]条预测数据");
		}
		
		List<ProOrgVo> proOrgList = null;
		if(CollectionUtils.isNotEmpty(comboBizVoList)){
			//构造所有明细产品、组织的集合
			String proSql = "SELECT ID,CODE,NAME FROM PRODUCT WHERE ISVALID =1 AND ISCATALOG = 0";
			HashVO[] proVos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, proSql);
			
			String orgSql = "SELECT ID,CODE,NAME FROM ORGANIZATION WHERE ISVALID =1 AND ISCATALOG = 0";
			HashVO[] orgVos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, orgSql);
			
			//int size = proVos.length * orgVos.length;
			proOrgList = new ArrayList<ProOrgVo>();
			
			Long proId,orgId;
			for(int i=0;i<proVos.length;i++){
				proId = proVos[i].getLongValue("ID");
				for(int j=0;j<orgVos.length;j++){
					orgId = orgVos[j].getLongValue("ID");
					proOrgList.add(new ProOrgVo(proId, orgId));
				}
			}
		}
		String insertSql = "INSERT INTO FORECASTDATA(BIZDATAID,PRODUCTID,ORGANIZATIONID,PERIOD,VALUE,VERSION,INITTIME,UPDATETIME,COMMENTS,STATUS) " 
			+ " VALUES(?,?,?,?,?,'1' ,getdate(),getdate(),'期间滚动时复制-comb',0)";
		
		
		for(HashVO vo : comboBizVoList){
			String versionBizId = vo.getStringValue("id");
			String versionBizName = vo.getStringValue("name");
			//String fcBizId = vo.getStringValue("itembizdataid"); 版本预测对应的组合预测业务数据
			int interval = vo.getIntegerValue("period_interval");
			int fcPeriod = UtilPeriod.getPeriod(curPeriod, interval); //获取要查询预测数据的期间
			int rows = 0;
			
			//版本预测对应的组合预测业务数据
			BBizData bizDataComb = bizDataService.getBizDataByCode(vo.getStringValue("FCBIZDATA_CODE"));
			//
			for(ProOrgVo proOrgVo : proOrgList){
				BProduct pro = new BProduct();
				pro.setId(proOrgVo.getProId());
				BOrganization org = new BOrganization();
				org.setId(proOrgVo.getOrgId());
				ABUiRowData combData = getUiRowDatas4FcComb(pro, org, fcPeriod, fcPeriod, bizDataComb);
				
				double value = getNonNullPeriodValue(combData,0);
				if(value > 0){
					dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSql, versionBizId, pro.getId(),  org.getId(), fcPeriod, value);
					num++;
					rows ++;
					if(rows %100 == 0){ //100 batch
						dmo.commit(DMOConst.DS_DEFAULT);
					}
				}
			}
			dmo.commit(DMOConst.DS_DEFAULT);
			logger.info("处理["+versionBizName+"]的版本预测数据完成,复制["+rows+"]条预测数据");
		}
		
		logger.info("本次刷新共["+ num +"]条M-N预测数据。");
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
	public ABUiRowData getUiRowDatas4FcComb(BProduct product,BOrganization org,int periodBegin, int periodEnd, BBizData bizDataComb) throws Exception
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
				ABUiRowData rowData4FcMH = getUiRowDatas4Forecast(product,org, periodBegin, periodEnd, itemBizData4FcMH);
				itemBizRowDataMap.put(itemBizData4FcMH.getId(), rowData4FcMH);
			}
		}
		
		if( CollectionUtils.isNotEmpty(listBBizData4TimeFc) )
		{//查询同期预测数据
			for(BBizData itemBizData4TimeFc : listBBizData4TimeFc){
				ABUiRowData rowData4FcMH = getUiRowDatas4TimeFc(product,org, periodBegin, periodEnd, itemBizData4TimeFc);
				itemBizRowDataMap.put(itemBizData4TimeFc.getId(), rowData4FcMH);
			}
		}
		
		//版本类数据
		if( CollectionUtils.isNotEmpty(listBBizData4Version) )
		{
			for(BBizData itemBizData4Version : listBBizData4Version){
				ABUiRowData rowData4Version = getUiRowDatas4TimeFc(product,org, periodBegin, periodEnd, itemBizData4Version);
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
	public ABUiRowData getUiRowDatas4Version(BProduct product,BOrganization org,int periodBegin, int periodEnd, BBizData bizData ) throws Exception
	{
		//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowDat
		ABUiRowData abUiRowData = new ABUiRowData();
		abUiRowData.setProduct( product );
		abUiRowData.setBizData( bizData );
		abUiRowData.setPeriodBegin( periodBegin );
		abUiRowData.setPeriodEnd( periodEnd );
		
		//数据查询
		String sql = "select VALUE, PERIOD from FORECASTASSESSMENTDATA where PRODUCTID=? and ORGANIZATIONID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=?";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(),org.getId(),periodBegin,periodEnd,bizData.getId());
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
	private ABUiRowData getUiRowDatas4TimeFc( BProduct product,BOrganization org,int periodBegin, int periodEnd, BBizData bizData4TimeFc) throws Exception
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
		String sql = "select VALUE, PERIOD from FORECASTDATA where PRODUCTID=? and ORGANIZATIONID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=? ";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(), org.getId(),queryPeriodBegin,queryPeriodEnd,itemBizDataTimeFc.getId());
		
		for (int j = 0; j < vos.length; j++) {
			HashVO vo = vos[j];
			//期间和查询出的期间值作相应平移
			int periodLoc = UtilPeriod.getPeriodDifference( periodBegin, UtilPeriod.getPeriod(vo.getIntegerValue("PERIOD"), periodDiff2Current) );
			abUiRowData.pubFun4setPeriodDataValue(periodLoc, vo.getDoubleValue("VALUE"));
			abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, vo.getDoubleValue("VALUE"));
		}
		
		return abUiRowData;
	}
	
	/**
	 * 预测表数据查询forecastdata
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @param bizData4Final
	 * @return
	 * @throws Exception
	 */
	private ABUiRowData getUiRowDatas4Forecast(BProduct product,BOrganization org,int periodBegin, int periodEnd, BBizData bizData4Final ) throws Exception
	{
		//每个业务数据与abUiRowDataProOrg结合生成一条abUiRowDat
		ABUiRowData abUiRowData = new ABUiRowData();
		abUiRowData.setProduct( product );
		abUiRowData.setBizData( bizData4Final );
		abUiRowData.setPeriodBegin( periodBegin );
		abUiRowData.setPeriodEnd( periodEnd );
		
		//数据查询
		String sql = "select VALUE, PERIOD from FORECASTDATA where PRODUCTID=? and ORGANIZATIONID=? and PERIOD>=? and PERIOD<=? and BIZDATAID=? ";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, product.getId(),org.getId() ,periodBegin,periodEnd,bizData4Final.getId());
		for (int j = 0; j < vos.length; j++) {
			HashVO vo = vos[j];
			int periodLoc = UtilPeriod.getPeriodDifference(periodBegin, vo.getIntegerValue("PERIOD"));
			abUiRowData.pubFun4setPeriodDataValue(periodLoc, vo.getDoubleValue("VALUE"));
			abUiRowData.pubFun4setPeriodDataValueBak(periodLoc, vo.getDoubleValue("VALUE"));
		}
		
		return abUiRowData;
	}
	
	class ProOrgVo{
		Long proId ;
		Long orgId;
		public ProOrgVo(Long proId,Long orgId){
			this.proId = proId;
			this.orgId = orgId;
		}
		public Long getProId() {
			return proId;
		}
		public Long getOrgId() {
			return orgId;
		}
	}

	/**
	 * 获取abUiRowData对应区间的非空值
	 * @param abUiRowData
	 * @param periodLoc
	 * @return
	 */
	public double getNonNullPeriodValue(ABUiRowData abUiRowData, int periodLoc){
		Double valueDouble = abUiRowData.pubFun4getPeriodDataValue(periodLoc);
		
		if(valueDouble == null){
			return 0;
		}else{
			return valueDouble.doubleValue();
		}
		
	}
	
}
