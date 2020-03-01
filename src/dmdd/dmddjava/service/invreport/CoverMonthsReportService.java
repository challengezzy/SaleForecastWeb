package dmdd.dmddjava.service.invreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.service.dimensionservice.BizDataService;

/**
 * 缺货风险评估报表
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Aug 13, 2017 3:07:44 PM
 */
public class CoverMonthsReportService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());
	
	private OverStockRiskDao riskDao = new OverStockRiskDao();
	private BizDataService bizDataService = new BizDataService();
	private StockRowDataQueryService rowDataQueryService = new StockRowDataQueryService();
	
	/**
	 * 根据产品编码、期间，返回该产品销售覆盖天数
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryConverMonthReportByProduct(Map<String,String> queryCond ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		String proCode = queryCond.get("proCode");
		int period = new Integer(queryCond.get("period"));
		int achieveRate = new Integer(queryCond.get("achieveRate"));
		int periodNum = new Integer(queryCond.get("periodNum"));
		
		BProduct product = ServerEnvironment.getInstance().getBProduct(proCode);
		int periodBegin = period;
		int periodEnd = UtilPeriod.getPeriod(periodBegin, periodNum);
		
		BBizData bizData4Final = bizDataService.getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_FINAL);
		//查询最终预测数据
		ABUiRowData finalData =  rowDataQueryService.getUiRowDatas4BizData(product, periodBegin, periodEnd, bizData4Final);
		rstList.add(finalData);
		
		//查询去年同期
//		BBizData bizData4TimeHis = bizDataService.getBizDataByCode("TQ001"); //TODO TQ001的编码是指定的，可通过配置的方式
//		if(bizData4TimeHis != null){
//			ABUiRowData timeHisData =  riskDao.getUiRowDatas4His(product, periodBegin, periodEnd, bizData4TimeHis);
//			rstList.add(timeHisData);
//		}
		
		//预计PO收货日期 
		
		BBizData poBizData = new BBizData();
		poBizData.setName("预计PO收货");
		ABUiRowData poData =  riskDao.getPoUiRowData(period,product, periodBegin, periodEnd, poBizData, "RECEIVING_PERIOD");
		rstList.add(poData);
		
		//查询采购订单下架期
		BBizData poOffBizData = new BBizData();
		poOffBizData.setName("采购订单下架期");
		ABUiRowData poOffData =  riskDao.getPoUiRowData(period,product, periodBegin, periodEnd, poOffBizData,"OFFSHELF_PERIOD");
		rstList.add(poOffData);
		
		//MTD PO Received 当月PO交货 和  MTD PO Received 当月PO交货下架期
		List<ABUiRowData> mtdDataList = riskDao.getMtdPoUiRowData(product, periodBegin, periodEnd);
		rstList.addAll(mtdDataList);
		ABUiRowData mtdOffData = mtdDataList.get(1); //第二个数据是下回期数据
		
		//在库库存下架期
		BBizData invStockBizData = new BBizData();
		invStockBizData.setName("在库库存下架期");
		ABUiRowData invStockOffData =  riskDao.getInvStockOffUiRowData(period,product, periodBegin, periodEnd, invStockBizData);
		rstList.add(invStockOffData);
		
		BBizData overStockBizData = new BBizData();
		overStockBizData.setName("预计过期库存");
		ABUiRowData overStockRowData = new ABUiRowData();
		overStockRowData.setProduct( product );
		overStockRowData.setBizData( overStockBizData );
		overStockRowData.setPeriodBegin( periodBegin );
		overStockRowData.setPeriodEnd( periodEnd );
		//预计过期库存(Overstock) 计算
		double preOver = 0; //前一期的缺货数量
		double preOverCompute = 0;//前一期的缺货数量，参与计算
		for(int i=0;i<=periodNum;i++){
			if(preOver > 0){ ////大于0则不参与下一期的计算
				preOverCompute = 0;
			}else{ 
				preOverCompute = preOver;
			}
			
			// SUM( PO下架 + MTD下架 + 在库库存下架 ) - 最终预测*达成率 + 上期的缺货
			double poOff = getNonNullPeriodValue(poOffData, i);
			double mtdOff = getNonNullPeriodValue(mtdOffData, i);
			double invOff = getNonNullPeriodValue(invStockOffData, i);
			double finalOff = getNonNullPeriodValue(finalData, i);
			
			preOver = poOff + mtdOff + invOff - finalOff*100/achieveRate + preOverCompute;
			
			overStockRowData.pubFun4setPeriodDataValue(i,preOver);
			
		}
		rstList.add(overStockRowData);
		logger.info("查询到["+rstList.size()+"]条OverStockRisk数据");
		//查询期初库存数
		
		Map<String,Object> rstMap = new HashMap<String, Object>();
		rstMap.put("dataList", rstList);
		rstMap.put("curPeriodStock", riskDao.getCurPeriodStockQuantityValid(product, period));
		rstMap.put("totalPO", riskDao.getPeriodTotalPO(period,product, periodBegin, periodEnd ));
		rstMap.put("standardPrice",riskDao.getProductStdPrice(product, period,false));
		
		return rstMap;
	}
	
	/**
	 * 过期库存汇总报表
	 */
	public Map<String,Object> queryRiskReportSummary(Map<String,String> queryCond ) throws Exception{
		Double sumQty = 0d;
		Double sumMoney = 0d;
		int period = new Integer(queryCond.get("period"));
		int achieveRate = new Integer(queryCond.get("achieveRate"));
		
		//查询所有产品
		List<BProduct> detailProductList = riskDao.getAllDetailProducts();
		List<Map<String,Object>> unsafeList = new ArrayList<Map<String,Object>>(detailProductList.size()) ;
		List<Map<String,Object>> rstList = Collections.synchronizedList(unsafeList); //转换成线程安全的List,可以支持多线程
		
		BBizData bizData4Final = bizDataService.getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_FINAL);
		
		int i=0;
		for(BProduct product : detailProductList){
			Map<String,Object> rowData = getProductOverStock(product, period, bizData4Final, achieveRate);
			
			sumQty += (Double)rowData.get("QUANTITY");
			sumMoney += (Double)rowData.get("MONEY");
			rstList.add(rowData);
			
			i++;
//			if(i> 10 ){
//				break;
//			}
		}
		
		Map<String,Object> rstMap = new HashMap<String, Object>();
		rstMap.put("dataList", rstList);
		rstMap.put("summaryQuantity", sumQty );
		rstMap.put("summaryMoney", sumMoney );
		
		return rstMap;
	}
	
	//针对每个产品计算OverStock
	public Map<String,Object> getProductOverStock(BProduct product,int period,BBizData bizData4Final,int achieveRate) throws Exception{
		Map<String,Object> rowData = new HashMap<String, Object>();
		int periodBegin = period;
		int periodEnd = period;
		int periodNum = periodEnd - periodBegin;
		
		//查询最终预测数据
		ABUiRowData finalData =  rowDataQueryService.getUiRowDatas4BizData(product, periodBegin, periodEnd, bizData4Final);
		
		//查询采购订单下架期
		BBizData poOffBizData = new BBizData();
		poOffBizData.setName("采购订单下架期");
		ABUiRowData poOffData =  riskDao.getPoUiRowData(period,product, periodBegin, periodEnd, poOffBizData,"OFFSHELF_PERIOD");
		
		//MTD PO Received 当月PO交货 和  MTD PO Received 当月PO交货下架期
		List<ABUiRowData> mtdDataList = riskDao.getMtdPoUiRowData(product, periodBegin, periodEnd);
		ABUiRowData mtdOffData = mtdDataList.get(1); //第二个数据是下回期数据
		
		//在库库存下架期
		BBizData invStockBizData = new BBizData();
		invStockBizData.setName("在库库存下架期");
		ABUiRowData invStockOffData =  riskDao.getInvStockOffUiRowData(period,product, periodBegin, periodEnd, invStockBizData);
		
		// SUM( PO下架 + MTD下架 + 在库库存下架 ) - 最终预测*达成率 + 上期的缺货
		int periodLoc = 0;
		double poOff = getNonNullPeriodValue(poOffData, periodLoc);
		double mtdOff = getNonNullPeriodValue(mtdOffData, periodLoc);
		double invOff = getNonNullPeriodValue(invStockOffData, periodLoc);
		double finalOff = getNonNullPeriodValue(finalData, periodLoc);
		Double overQuantity =  poOff + mtdOff + invOff - finalOff*100/achieveRate;
		
		//获取标准价格
		Double stdPrice = riskDao.getProductStdPrice(product, period,false);
		Double overMoney = overQuantity * stdPrice;
		
		//构造每一行显示的数据
		rowData.put("PROCODE", product.getCode());
		rowData.put("PRONAME", product.getName());
		rowData.put("SHELFLIFE", product.getShelfLife());
		rowData.put("STANDARDPRICE", stdPrice);
		rowData.put("QUANTITY", overQuantity);
		rowData.put("MONEY", overMoney);
		
		/** 可以支持多期计算的情况 
		BBizData overStockBizData = new BBizData();
		overStockBizData.setName("预计过期库存");
		
		ABUiRowData overStockRowData = new ABUiRowData();
		overStockRowData.setProduct( product );
		overStockRowData.setBizData( overStockBizData );
		overStockRowData.setPeriodBegin( periodBegin );
		overStockRowData.setPeriodEnd( periodEnd );
		
		//预计过期库存(Overstock) 计算
		double preOver = 0; //前一期的缺货数量
		double preOverCompute = 0;//前一期的缺货数量，参与计算
		for(int i=0;i<=periodNum;i++){
			if(preOver > 0){ ////大于0则不参与下一期的计算
				preOverCompute = 0;
			}else{ 
				preOverCompute = preOver;
			}
			
			// SUM( PO下架 + MTD下架 + 在库库存下架 ) - 最终预测*达成率 + 上期的缺货
			double poOff = getNonNullPeriodValue(poOffData, i);
			double mtdOff = getNonNullPeriodValue(mtdOffData, i);
			double invOff = getNonNullPeriodValue(invStockOffData, i);
			double finalOff = getNonNullPeriodValue(finalData, i);
			
			preOver = poOff + mtdOff + invOff - finalOff*100/achieveRate + preOverCompute;
			
			overStockRowData.pubFun4setPeriodDataValue(i,preOver);
			
		}
		*/
		return rowData;
	}
	
	public double getNonNullPeriodValue(ABUiRowData abUiRowData, int periodLoc){
		Double valueDouble = abUiRowData.pubFun4getPeriodDataValue(periodLoc);
		
		if(valueDouble == null){
			return 0;
		}else{
			return valueDouble.doubleValue();
		}
		
	}

}
