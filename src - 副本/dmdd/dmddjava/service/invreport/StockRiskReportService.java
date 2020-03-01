package dmdd.dmddjava.service.invreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.service.dimensionservice.BizDataService;

/**
 * 库存风险报表
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Oct 3, 2017 11:15:10 PM
 */
public class StockRiskReportService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());
	
	private OverStockRiskDao riskDao = new OverStockRiskDao();
	private BizDataService bizDataService = new BizDataService();
	
	private StockRowDataQueryService rowDataQueryService = new StockRowDataQueryService();
	
	/**过期库存 */
	public final static String OVER_STOCK = "OVER_STOCK";
	/**缺货库存 */
	public final static String OUTOF_STOCK = "OUTOF_STOCK";
	/**期末有效库存 */
	public final static String ENDING_STOCK = "ENDING_STOCK";
	/**扣减SS的期末有效库存 */
	public final static String SUBSTRACTSS_STOCK = "SUBSTRACTSS_STOCK";
	
	public final String OS_CDAYS_NEXT         = "OS_CDAYS_NEXT";
	public final String OS_CDAYS_MULTI        = "OS_CDAYS_MULTI";
	public final String OOS_CDAYS_NEXT        = "OOS_CDAYS_NEXT";
	public final String OOS_CDAYS_MULTI       = "OOS_CDAYS_MULTI";
	public final String ENDING_CDAYS_NEXT     = "ENDING_CDAYS_NEXT";
	public final String ENDING_CDAYS_MULTI    = "ENDING_CDAYS_MULTI";
	public final String SUBTRACTS_CDAYS_NEXT  = "SUBTRACTS_CDAYS_NEXT";
	public final String SUBTRACTS_CDAYS_MULTI = "SUBTRACTS_CDAYS_MULTI";
	
	/**
	 * 根据产品编码、期间，返回该产品的期初库存，总PO数，和缺货，覆盖期等
	 * @param queryCond
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryStockRiskReportByProduct(Map<String,String> queryCond ) throws Exception
	{
		List<ABUiRowData> rstList = new ArrayList<ABUiRowData>();
		
		String proCode = queryCond.get("proCode");
		int period = new Integer(queryCond.get("period"));
		int achieveRate = new Integer(queryCond.get("achieveRate"));
		int periodNum = new Integer(queryCond.get("periodNum"));
		boolean isIncludePr = new Boolean(queryCond.get("isPR"));
		boolean isIncludePo = new Boolean(queryCond.get("isPO"));
		
		boolean isBaseNext = new Boolean(queryCond.get("isBaseNext"));
		boolean isBaseCycle = new Boolean(queryCond.get("isBaseCycle"));
		boolean isBaseSubSS = new Boolean(queryCond.get("isBaseSubSS"));
		String bizDataCode = queryCond.get("bizDataCode").toString();
		int fcValidPeriodNum = new Integer(queryCond.get("fcValidPeriodNum"));
		
		BProduct product = ServerEnvironment.getInstance().getBProduct(proCode);
		int periodBegin = period;
		int periodEnd = UtilPeriod.getPeriod(periodBegin, periodNum);
		
		//BBizData bizData4Final = bizDataService.getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_FINAL+ BizConst.AMOUNT_BIZ_DATA_SUFFIX);
		BBizData bizData = bizDataService.getBizDataByCode(bizDataCode); //灵活使用业务数据
		
		int periodPlus = 6; //多余查询的预测数据
		//查询最终预测数据
		int queryPeriodEnd = UtilPeriod.getPeriod(periodEnd, periodPlus);
		ABUiRowData bizDataRowData =  rowDataQueryService.getUiRowDatas4BizData(product, periodBegin, queryPeriodEnd, bizData);
		rstList.add(bizDataRowData);
		
		ABUiRowData adjustFinalRowData = createUiRowData(bizData.getName()+"-调整后", product, periodBegin, periodEnd);
		rstList.add(adjustFinalRowData);
		
		//20180102 zhangzy 不再采用找0的方式，由用户设定有效的预测期间数，在有效期间数之外的数据才需求补充处理
		int periodValidEnd = UtilPeriod.getPeriod(periodBegin, fcValidPeriodNum+24);
		//计算用预测数据
		ABUiRowData calcRowData = createUiRowData(bizData.getName()+"-计算用", product, periodBegin, periodValidEnd);
		rstList.add(calcRowData);
		
		double value1 = getNonNullPeriodValue(bizDataRowData, fcValidPeriodNum-3 );
		double value3 = getNonNullPeriodValue(bizDataRowData, fcValidPeriodNum-2 );
		double value2 = getNonNullPeriodValue(bizDataRowData, fcValidPeriodNum-1 );
		
		double avg3Predit = (value1 + value2 + value3)*achieveRate/100/ 3;//有效预测期内，最后3期的平均值
		
		//设置调整后的值
		for(int i=0;i<periodNum+24;i++ ){
			Double finalPredit = getNonNullPeriodValue(bizDataRowData, i); //销售最终预测
			double sell = finalPredit*achieveRate/100 ;
			adjustFinalRowData.pubFun4setPeriodDataValue(i, sell);
			
			if(i < fcValidPeriodNum){
				calcRowData.pubFun4setPeriodDataValue(i, sell);
			}else{
				calcRowData.pubFun4setPeriodDataValue(i, avg3Predit);
			}
			
		}
		
//		//初始化调整后预测值,老算法
//		int nullPeriodStart = periodNum+periodPlus-1; //空期间开始的地方
//		int nullPeriodNum = 0; //空期间数量
//		double avg6Predit = 0;//最后6期有值的预测值
//		boolean isAvg6Ok = false;
//		for(int i=0;i<periodNum+periodPlus;i++ ){
//			Double finalPredit = getNonNullPeriodValue(bizDataRowData, i); //销售最终预测
//			if(finalPredit != null && finalPredit > 0){
//				double sell = finalPredit*achieveRate/100 ;
//				adjustFinalRowData.pubFun4setPeriodDataValue(i, sell); //设置调整后的值			
//				
//				nullPeriodNum = 0;//有非0，还原计数
//			}else{
//				if(isAvg6Ok == false){//还没有找到预测末平均值
//					if(nullPeriodNum == 2){ //已经有连续2期为0，加上本期是3期
//						double value6 = getNonNullPeriodValue(bizDataRowData, i-3);
//						double value5 = getNonNullPeriodValue(bizDataRowData, i-4);
//						double value4 = getNonNullPeriodValue(bizDataRowData, i-5);
//						double value3 = getNonNullPeriodValue(bizDataRowData, i-6);
//						double value2 = getNonNullPeriodValue(bizDataRowData, i-7);
//						double value1 = getNonNullPeriodValue(bizDataRowData, i-8);
//						
//						avg6Predit = (value1 + value2 + value3 + value4 + value5 +value6)/6;
//						nullPeriodStart = i-2;
//						isAvg6Ok = true;
//						break;
//					}else{
//						nullPeriodNum ++;
//					}
//				}
//			}
//		}
//		//设置空值
//		for(int i=nullPeriodStart; i<periodNum+periodPlus; i++){
//			adjustFinalRowData.pubFun4setPeriodDataValue(i, avg6Predit*achieveRate/100);
//		}
		
		//查询SS安全库存
		BBizData ssBizData = new BBizData();
		ssBizData.setName("安全库存");
		ABUiRowData ssData =  riskDao.getSSRowData(product, periodBegin, periodEnd, ssBizData);
		rstList.add(ssData);
		
		
		//在库库存下架期
		BBizData invStockBizData = new BBizData();
		invStockBizData.setName("在库库存下架期");
		ABUiRowData invStockOffData =  riskDao.getInvStockOffUiRowData(period,product, periodBegin, periodEnd, invStockBizData);
		rstList.add(invStockOffData);
		
		//查询去年同期
//		BBizData bizData4TimeHis = bizDataService.getBizDataByCode("TQ001"); //TQ001的编码是指定的，可通过配置的方式
//		if(bizData4TimeHis != null){
//			ABUiRowData timeHisData =  riskDao.getUiRowDatas4TimeHis(product, periodBegin, periodEnd, bizData4TimeHis);
//			rstList.add(timeHisData);
//		}
		
		
		ABUiRowData poOffData = null;
		ABUiRowData poData = null;
		if(isIncludePo){
			//预计PO收货日期 		
			BBizData poBizData = new BBizData();
			poBizData.setName("PO交货期");
			poData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, poBizData, "RECEIVING_PERIOD","PO");
			rstList.add(poData);
			
			//查询PO单下架期
			BBizData poOffBizData = new BBizData();
			poOffBizData.setName("PO下架期");
			poOffData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, poOffBizData,"OFFSHELF_PERIOD","PO");
			rstList.add(poOffData);
		}
		
		
		
		
		ABUiRowData prData = null;
		ABUiRowData prOffData = null;
		
		//预计PR--采购申请收货
		if(isIncludePr){
			BBizData prBizData = new BBizData();
			prBizData.setName("PR交货期");
			prData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, prBizData, "RECEIVING_PERIOD","PR");
			
			//查询PR单下架期
			BBizData prOffBizData = new BBizData();
			prOffBizData.setName("PR下架期");
			prOffData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, prOffBizData,"OFFSHELF_PERIOD","PR");
			
			rstList.add(prData);
			rstList.add(prOffData);
		}
		
		
		//计算期末有效库存D1, 
		//D1=D0+B1+B2-A1-C1，如果D1<0，则显示为0	(D1作为下期期初库存参与到横向循环计算, 期末有效库存没有负数，<0，则显示为0）
		//过期库存C1 所有有效库存（上期D1+B1+B2，下架期>=本月）按效期顺序扣减所有需求（A1+C2上期缺货），如果结果>0，则将其中效期<=本月的数量在此行显示
		//所有有效库存（上期D1+B1+B2，下架期>=本月）按效期顺序扣减所有需求（A1+C2上期缺货），如果结果<0，则将负数取正后在此行显示
		
		ABUiRowData endingStockRowData = createUiRowData("期末有效库存", product, periodBegin, periodEnd);
		ABUiRowData subtractSSRowData = createUiRowData("扣减SS的期末有效库存", product, periodBegin, periodEnd);
		ABUiRowData outOfStockRowData = createUiRowData("缺货库存", product, periodBegin, periodEnd);
		ABUiRowData overStockRowData = createUiRowData("过期库存", product, periodBegin, periodEnd);
		
		ABUiRowData periodOverStockRowData = createUiRowData("期间过期库存", product, periodBegin, periodEnd);
		ABUiRowData accSellAheadRowData = createUiRowData("累积可提前销售", product, periodBegin, periodEnd);
		
		ABUiRowData osCoverDaysRowData_next = createUiRowData("过期库存覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData osCoverDaysRowData_multi = createUiRowData("过期库存覆盖天数-基于循环", product, periodBegin, periodEnd);
		ABUiRowData oosCoverDaysRowData_next = createUiRowData("缺货库存覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData oosCoverDaysRowData_multi = createUiRowData("缺货库存覆盖天数-基于循环", product, periodBegin, periodEnd);
		ABUiRowData endingCoverDaysRowData_next = createUiRowData("期末有效库存覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData endingCoverDaysRowData_multi = createUiRowData("期末有效库存覆盖天数-基于循环", product, periodBegin, periodEnd);
		ABUiRowData subtractSCoverDaysRowData_next = createUiRowData("期末有效库存-SS覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData subtractSCoverDaysRowData_multi = createUiRowData("期末有效库存-SS覆盖天数-基于循环", product, periodBegin, periodEnd);
		
		double curPeriodStockValid = riskDao.getCurPeriodStockQuantityValid(product, period);//上期末有效库存
		double curPeriodStockOffShelf = riskDao.getCurPeriodStockQuantityOffShelf(product, period);//上期末已过下架期库存
		
		double totalOverStock = 0;
		double preOutOfStock = 0; //前一期的缺货数量
		double preEndingStock = curPeriodStockValid;//上一期的期末有效库存
		//double preOver = 0; //前一期的过期库存数量
		
		int accNum = 0; //累积为负的过期库存期间数，不超过产品的下架期
		int shelfNum = product.getShelfLife()-product.getWithdrawLeadTime();
		double accSellAhead = 0 ;//真实累积库存为负值，表示可以有快到期的库存提前在本期卖掉
		
		for(int i=0;i<periodNum;i++ ){
			//
			double invOff = getNonNullPeriodValue(invStockOffData, i);
			
			double ss = getNonNullPeriodValue(ssData, i);
			double sell = getNonNullPeriodValue(calcRowData, i); ; //销售,考虑预测达成率
			
			double poOff = 0;
			double po = 0;
			if(isIncludePo){
				poOff = getNonNullPeriodValue(poOffData, i);
				po = getNonNullPeriodValue(poData, i);
			}
			
			double prOff = 0;
			double pr = 0;
			if(isIncludePr){
				prOff = getNonNullPeriodValue(prOffData, i);
				pr = getNonNullPeriodValue(prData, i);
			}
			
			//1，计算过期库存   over = 当期要下架(PO_off + PR_off + INV_off )-当期销售*预测达成率 - 上期缺货.  和上期缺货没有关系？都缺货了，肯定补到上期已经卖掉了
			double periodOver = (invOff + poOff + prOff) - sell - preOutOfStock;
			double over = 0;
			
			if(periodOver > 0 && (periodOver + accSellAhead) > 0){ //当期过期，并且加上前期可售卖的部分后，还是过期。--真正的过期
				over = periodOver + accSellAhead;
				accSellAhead = 0; //已经过期的库存是要下架处理的，不参与下一期的计算
				accNum = 0;
			}else{
				over = 0; //无过期，显示值为0设置
				accSellAhead += periodOver; //提前可售卖部分做累加处理,不论本期是否有过期库存
				accNum++;
			}
			
//			if(periodOver <= 0){
//				over = 0;  //按当期计算，没有过期 ，显示为0
//				accSellAhead += periodOver; //提前可售卖部分做累加处理,不论本期是否有过期库存。置0的判断在再下一步的逻辑中
//				accNum ++;
//				
//				//不会产生过期库存的情况下，判断是不是有缺货的情况产生. 应该有一部分快到期的库存，提前在本期销售掉
//			}else if(periodOver > 0){
//				//按当期算，有过期了, 看看有没有提前可售卖的部分
//				over = periodOver + accSellAhead;
//				if(over > 0){ //减去可提前售卖的后，仍有过期库存
//					accSellAhead = 0;
//					accNum = 0;
//				}else{
//					over = 0; //无过期，显示值为0设置
//					accSellAhead += periodOver; //提前可售卖部分做累加处理,不论本期是否有过期库存。置0的判断在再下一步的逻辑中
//					accNum++;
//				}
//			}
			
			//累积了整个产品的保质期，不应该再算到可售卖中，因为产品肯定还没有生产出来
			/** zhangzy 20180107 直接扣减保质期外的销售值有问题，产品按批次过期，按批次卖的
			if(accSellAhead < 0 && (accNum == shelfNum) ){
				double firstSellAhead = getNonNullPeriodValue(periodOverStockRowData, i-shelfNum);
				accSellAhead -= firstSellAhead;
				accNum --;
			} */
			
			
			totalOverStock += over; //汇总过期库存
			
			//2 再计算期末有效库存 [ 上期末 + 本期到货(P0+PR) - sell(销售) - over(过期)   ], 有效库存小时时，才会产生缺货库存
			double ending =  preEndingStock + po + pr - sell - over;
			
			double subtractSS = ending - ss;
			
			//3计算缺货 = [sell(销售) - 上期末(preEndingStock) - 本期到货(P0+PR) ]
			double outOfStock = sell - preEndingStock - po - pr;
			if(outOfStock < 0){
				//货足够卖，没有生产缺货
				outOfStock = 0;
			}
			
			outOfStockRowData.pubFun4setPeriodDataValue(i, outOfStock);
			endingStockRowData.pubFun4setPeriodDataValue(i, ending);
			overStockRowData.pubFun4setPeriodDataValue(i, over);
			subtractSSRowData.pubFun4setPeriodDataValue(i, subtractSS);
			periodOverStockRowData.pubFun4setPeriodDataValue(i, periodOver);
			accSellAheadRowData.pubFun4setPeriodDataValue(i, accSellAhead);
			
			preEndingStock = ending; //下一轮计算中初始化
			preOutOfStock = outOfStock;
			
			if(isBaseNext){
				//覆盖天数计算
				osCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, over));
				oosCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, outOfStock));
				endingCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, ending));
			}
			
			
			
			if(isBaseCycle){
				//基于后面多期
				osCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, over));
				oosCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, outOfStock));
				endingCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, ending));
			}
			
			//基于扣减SS
			if(isBaseSubSS){
				subtractSCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, subtractSS));
				subtractSCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, subtractSS));
			}
			
		}
		
		rstList.add(endingStockRowData);
		rstList.add(overStockRowData);
		rstList.add(periodOverStockRowData); //隐藏
		rstList.add(accSellAheadRowData);
		
		rstList.add(outOfStockRowData);
		
		if(isBaseNext){
			rstList.add(osCoverDaysRowData_next);
			rstList.add(oosCoverDaysRowData_next);
			rstList.add(endingCoverDaysRowData_next);
		}
		
		if(isBaseCycle){
			rstList.add(osCoverDaysRowData_multi);
			rstList.add(oosCoverDaysRowData_multi);
			rstList.add(endingCoverDaysRowData_multi);
		}
		
		if(isBaseSubSS){
			rstList.add(subtractSSRowData);
			rstList.add(subtractSCoverDaysRowData_next);
			rstList.add(subtractSCoverDaysRowData_multi);
		}
		
		
		
		logger.info("产品["+proCode+"]的StockRisk数据计算完毕,共["+rstList.size()+"]条计算数据");
		//查询期初库存数
		
		Map<String,Object> rstMap = new HashMap<String, Object>();
		rstMap.put("dataList", rstList);
		rstMap.put("curPeriodStockValid",curPeriodStockValid );
		rstMap.put("curPeriodStockOffShelf",curPeriodStockOffShelf );
		
		//rstMap.put("totalPO", riskDao.getPeriodTotalPO(product, periodBegin, periodEnd ));
		//rstMap.put("standardPrice",riskDao.getProductStdPrice(product, period,false));
		rstMap.put("offShelfLeadTime", product.getWithdrawLeadTime()); //提前下架期
		rstMap.put("totalOffShelf", totalOverStock);
		
		return rstMap;
	}
	
	/**
	 * 基于下期计算库存覆盖天数
	 * @param finalData 预测销售
	 * @param periodIdx 期间值
	 * @param stockAmount 库存数量
	 * @return
	 */
	public double computeCoverDaysNext(ABUiRowData finalData,int periodIdx,double stockAmount){
		Double nextPeriodSell = finalData.pubFun4getPeriodDataValue(periodIdx+1);
		
		//下期预测值不存在或者小于0
		if(nextPeriodSell == null || nextPeriodSell.compareTo(0d) <= 0 ){
			return Double.NaN;
		}
		double coverDays = stockAmount / nextPeriodSell * 30;
		
		return coverDays;
	}
	
	/**
	 * 基于后面多期计算库存覆盖天数
	 * @param finalData 预测销售
	 * @param periodIdx 期间值
	 * @param stockAmount 库存数量
	 * @return
	 */
	public double computeCoverDaysMulti(ABUiRowData finalData,int periodIdx,double stockAmount){
		
		Double nextSell1 = finalData.pubFun4getPeriodDataValue(periodIdx+1);
		//下期预测值不存在或者小于0
//		if(nextSell1 == null || nextSell1.compareTo(0d) <= 0 ){
//			return Double.NaN;
//		}
		
		//zhangzy 20171017 应业务方要求，算法修正，碰到连续3期值为0时，才
		double addedStock = 0 ;//已累加的库存量
		double coverDays = 0;
		int zeroPeriodNum = 0;
		//最高算到48期
		for(int i=0;i<48;i++){
			Double periodSell = finalData.pubFun4getPeriodDataValue( periodIdx+i+1 );
			
			if(periodSell == null){
				periodSell = 0d;
			}
			//下一期没有预测值或为0的情况,停下来算已有数据的平均
//			if(periodSell == null || periodSell.compareTo(0d) <= 0 ){
//				//coverDays = stockAmount/(addedStock/i) *30;
//				zeroPeriodNum ++;
//				if(zeroPeriodNum == 3){
//					if(addedStock == 0){
//						return Double.NaN;
//					}else{
//						//扣除掉3期为0的期间后，剩余的平均值
//						coverDays = stockAmount/(addedStock/(i-zeroPeriodNum)) *30 ;
//						return coverDays;
//					}
//				}				
//			}else{
//				//遇到非0，重新初始值
//				zeroPeriodNum = 0;
//			}
			
			double leftStock = stockAmount - addedStock;
			addedStock +=  periodSell;
			
			if(leftStock < periodSell ){ //减本期不够减,前两期天数 +本期天数
				coverDays = i*30 + leftStock/periodSell*30;
				return coverDays;
			}
		}
		
		//能走到这里说明减了48期，还没减完。覆盖天数超过2年
		coverDays = 48*30;
		
		if(addedStock == 0){
			return Double.NaN;
		}
		
		return coverDays;
	}
	
	/**
	 * 库存风险报表汇总
	 */
	public Map<String,Object> queryInventoryRiskByProductSummary(Map<String,Object> queryCond ) throws Exception{
		Double total = 0d; //汇总值，数据或者金额
		
		int period = new Integer(queryCond.get("period").toString());
		int achieveRate = new Integer(queryCond.get("achieveRate").toString());
		int periodNum = new Integer(queryCond.get("periodNum").toString());
		List<String> proCodeList = (List<String>)queryCond.get("proCodeArray");
		boolean isIncludePr = new Boolean(queryCond.get("isPR").toString()); //是否包含PR
		boolean isIncludePo = new Boolean(queryCond.get("isPO").toString()); //是否包含PR
		boolean isStandardPrice = new Boolean(queryCond.get("isStandardPrice").toString()); //是否计算金额数据
		String bizDataType = queryCond.get("bizDataType").toString();
		boolean isActualPrice = new Boolean(queryCond.get("isActualPrice").toString()); //是否使用实际价格
		String bizDataCode = queryCond.get("bizDataCode").toString();
		int fcValidPeriodNum = new Integer(queryCond.get("fcValidPeriodNum").toString());
		
		//查询所有产品
		List<ABUiRowData> unsafeList = new ArrayList<ABUiRowData>(proCodeList.size()) ;
		List<ABUiRowData> rstList = Collections.synchronizedList(unsafeList); //转换成线程安全的List,可以支持多线程
		
		int i=0;
		for(String proCode : proCodeList){
			Map<String,ABUiRowData> riskDataMap = queryStockRiskDataByProduct(proCode, period, achieveRate,periodNum
						,isIncludePr,isIncludePo,isStandardPrice,bizDataType,isActualPrice,bizDataCode,fcValidPeriodNum);
			
			ABUiRowData rowData = riskDataMap.get(bizDataType);
			rstList.add(rowData );
			
			//如果是数量或金额，进行汇总统计
			if(ENDING_STOCK.equals(bizDataType) || OUTOF_STOCK.equals(bizDataType) 
					|| OVER_STOCK.equals(bizDataType) || SUBSTRACTSS_STOCK.equals(bizDataType) ){
				
				for(i=0;i<periodNum;i++){
					total += getNonNullPeriodValue(rowData, i);
				}
			}
			
			i++;
		}
		
		Map<String,Object> rstMap = new HashMap<String, Object>();
		rstMap.put("dataList", rstList);
		rstMap.put("total", total );
		
		return rstMap;
	}
	
	/**
	 * 查询查个产品的相关库存风险数据,数据汇总到汇总报表
	 * @param proCode
	 * @param period
	 * @param achieveRate
	 * @param periodNum
	 * @param isIncludePr
	 * @param isIncludePo
	 * @param isMoney 是否计算金额
	 * @param bizDataType 业务数据类型
	 * @author isAcutalPrice 是否使用实际价格
	 * @return 
	 * @throws Exception
	 */
	public Map<String,ABUiRowData> queryStockRiskDataByProduct(String proCode,int period,int achieveRate,int periodNum
			,boolean isIncludePr,boolean isIncludePo, boolean isStandardPrice,String bizDataType,boolean isAcutalPrice
			,String bizDataCode, int fcValidPeriodNum) throws Exception
	{
		Map<String,ABUiRowData> riskDataMap = new HashMap<String, ABUiRowData>();
		
		BProduct product = ServerEnvironment.getInstance().getBProduct(proCode);
		int periodBegin = period;
		int periodEnd = UtilPeriod.getPeriod(periodBegin, periodNum);
		
		//BBizData bizData4Final = bizDataService.getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_FINAL+ BizConst.AMOUNT_BIZ_DATA_SUFFIX);
		BBizData bizData = bizDataService.getBizDataByCode(bizDataCode);
		
		int periodPlus = 6; //多余查询的预测数据
		//查询最终预测数据
		int queryPeriodEnd = UtilPeriod.getPeriod(periodEnd, periodPlus);
		ABUiRowData bizDataRowData =  rowDataQueryService.getUiRowDatas4BizData(product, periodBegin, queryPeriodEnd, bizData);
		
		ABUiRowData adjustFinalRowData = createUiRowData(bizData.getName()+"-调整后", product, periodBegin, periodEnd);
		
		int periodValidEnd = UtilPeriod.getPeriod(periodBegin, fcValidPeriodNum+24);
		//计算用预测数据
		ABUiRowData calcRowData = createUiRowData(bizData.getName()+"-计算用", product, periodBegin, periodValidEnd);
		
		double value1 = getNonNullPeriodValue(bizDataRowData, fcValidPeriodNum-3 );
		double value3 = getNonNullPeriodValue(bizDataRowData, fcValidPeriodNum-2 );
		double value2 = getNonNullPeriodValue(bizDataRowData, fcValidPeriodNum-1 );
		
		double avg3Predit = (value1 + value2 + value3)*achieveRate/100/ 3;//有效预测期内，最后3期的平均值
		
		//设置调整后的值
		for(int i=0;i<periodNum+24;i++ ){
			Double finalPredit = getNonNullPeriodValue(bizDataRowData, i); //销售最终预测
			double sell = finalPredit*achieveRate/100 ;
			adjustFinalRowData.pubFun4setPeriodDataValue(i, sell);
			
			if(i < fcValidPeriodNum){
				calcRowData.pubFun4setPeriodDataValue(i, sell);
			}else{
				calcRowData.pubFun4setPeriodDataValue(i, avg3Predit);
			}
			
		}
		
//		//取预测数据时，如果连续3期值为0或null，则认为预测值已经结束。取有值的最后
//		int nullPeriodStart = periodNum+periodPlus-1; //空期间开始的地方
//		int nullPeriodNum = 0; //空期间数量
//		double avg6Predit = 0;//最后6期有值的预测值
//		boolean isAvg6Ok = false;
//		
//		//初始化调整后预测值
//		for(int i=0;i<periodNum+periodPlus;i++ ){
//			Double finalPredit = getNonNullPeriodValue(bizDataRowData, i); //销售最终预测
//			if(finalPredit != null && finalPredit > 0){
//				double sell = finalPredit*achieveRate/100 ;
//				adjustFinalRowData.pubFun4setPeriodDataValue(i, sell); //设置调整后的值			
//				
//				nullPeriodNum = 0;//有非0，还原计数
//			}else{
//				if(isAvg6Ok == false){//还没有找到预测末平均值
//					if(nullPeriodNum == 2){ //已经有连续2期为0，加上本期是3期
//						double value6 = getNonNullPeriodValue(bizDataRowData, i-3);
//						double value5 = getNonNullPeriodValue(bizDataRowData, i-4);
//						double value4 = getNonNullPeriodValue(bizDataRowData, i-5);
//						double value3 = getNonNullPeriodValue(bizDataRowData, i-6);
//						double value2 = getNonNullPeriodValue(bizDataRowData, i-7);
//						double value1 = getNonNullPeriodValue(bizDataRowData, i-8);
//						
//						avg6Predit = (value1 + value2 + value3 + value4 + value5 +value6)/6;
//						nullPeriodStart = i-2;
//						isAvg6Ok = true;
//						break;
//					}else{
//						nullPeriodNum ++;
//					}
//				}
//			}
//		}
//		//设置空值
//		for(int i=nullPeriodStart; i<periodNum+periodPlus; i++){
//			adjustFinalRowData.pubFun4setPeriodDataValue(i, avg6Predit*achieveRate/100);
//		}
		
		riskDataMap.put("finalForecast", adjustFinalRowData);
		
		//查询SS安全库存
		BBizData ssBizData = new BBizData();
		ssBizData.setName("安全库存");
		ABUiRowData ssData =  riskDao.getSSRowData(product, periodBegin, periodEnd, ssBizData);
		
		//在库库存下架期
		BBizData invStockBizData = new BBizData();
		invStockBizData.setName("在库库存下架期");
		ABUiRowData invStockOffData =  riskDao.getInvStockOffUiRowData(period,product, periodBegin, periodEnd, invStockBizData);
		
		ABUiRowData poOffData = null;
		ABUiRowData poData = null;
		if(isIncludePo){
			//预计PO收货日期 		
			BBizData poBizData = new BBizData();
			poBizData.setName("PO交货期");
			poData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, poBizData, "RECEIVING_PERIOD","PO");
			
			//查询PO单下架期
			BBizData poOffBizData = new BBizData();
			poOffBizData.setName("PO下架期");
			poOffData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, poOffBizData,"OFFSHELF_PERIOD","PO");
		}
		
		ABUiRowData prData = null;
		ABUiRowData prOffData = null;
		
		if(isIncludePr){
			//预计PR--采购申请收货
			BBizData prBizData = new BBizData();
			prBizData.setName("PR交货期");
			prData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, prBizData, "RECEIVING_PERIOD","PR");
			
			//查询PR单下架期
			BBizData prOffBizData = new BBizData();
			prOffBizData.setName("PR下架期");
			prOffData =  riskDao.getPoPrUiRowData(period,product, periodBegin, periodEnd, prOffBizData,"OFFSHELF_PERIOD","PR");
		}
		
		//计算期末有效库存D1, 
		//D1=D0+B1+B2-A1-C1，如果D1<0，则显示为0	(D1作为下期期初库存参与到横向循环计算, 期末有效库存没有负数，<0，则显示为0）
		//过期库存C1 所有有效库存（上期D1+B1+B2，下架期>=本月）按效期顺序扣减所有需求（A1+C2上期缺货），如果结果>0，则将其中效期<=本月的数量在此行显示
		//所有有效库存（上期D1+B1+B2，下架期>=本月）按效期顺序扣减所有需求（A1+C2上期缺货），如果结果<0，则将负数取正后在此行显示
		
		ABUiRowData endingStockRowData = createUiRowData("期末有效库存", product, periodBegin, periodEnd);
		ABUiRowData subtractSSRowData = createUiRowData("扣减SS的期末有效库存", product, periodBegin, periodEnd);
		ABUiRowData outOfStockRowData = createUiRowData("缺货库存", product, periodBegin, periodEnd);
		ABUiRowData overStockRowData = createUiRowData("过期库存", product, periodBegin, periodEnd);
		
		ABUiRowData periodOverStockRowData = createUiRowData("期间过期库存", product, periodBegin, periodEnd);
		ABUiRowData accSellAheadRowData = createUiRowData("累积可提前销售", product, periodBegin, periodEnd);
		
		ABUiRowData osCoverDaysRowData_next = createUiRowData("过期库存覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData osCoverDaysRowData_multi = createUiRowData("过期库存覆盖天数-基于循环", product, periodBegin, periodEnd);
		ABUiRowData oosCoverDaysRowData_next = createUiRowData("缺货库存覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData oosCoverDaysRowData_multi = createUiRowData("缺货库存覆盖天数-基于循环", product, periodBegin, periodEnd);
		ABUiRowData endingCoverDaysRowData_next = createUiRowData("期末有效库存覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData endingCoverDaysRowData_multi = createUiRowData("期末有效库存覆盖天数-基于循环", product, periodBegin, periodEnd);
		ABUiRowData subtractSCoverDaysRowData_next = createUiRowData("期末有效库存-SS覆盖天数-基于下期", product, periodBegin, periodEnd);
		ABUiRowData subtractSCoverDaysRowData_multi = createUiRowData("期末有效库存-SS覆盖天数-基于循环", product, periodBegin, periodEnd);
		
		double curPeriodStockValid = riskDao.getCurPeriodStockQuantityValid(product, period);//上期末有效库存
		
		double preOutOfStock = 0; //前一期的缺货数量
		double preEndingStock = curPeriodStockValid;//上一期的期末有效库存
		//double preOver = 0; //前一期的过期库存数量
		
		int accNum = 0; //累积为负的过期库存期间数，不超过产品的下架期
		int shelfNum = product.getShelfLife()-product.getWithdrawLeadTime();
		double accSellAhead = 0 ;//真实累积库存为负值，表示可以有快到期的库存提前在本期卖掉
		
		for(int i=0;i<periodNum;i++ ){
			//
			double invOff = getNonNullPeriodValue(invStockOffData, i);
			double ss = getNonNullPeriodValue(ssData, i);
			double sell = getNonNullPeriodValue(calcRowData, i); //调整后最终预测
			
			double poOff = 0;
			double po = 0;
			if(isIncludePo){
				poOff = getNonNullPeriodValue(poOffData, i);
				po = getNonNullPeriodValue(poData, i);
			}
			
			double prOff = 0;
			double pr = 0;
			if(isIncludePr){
				prOff = getNonNullPeriodValue(prOffData, i);
				pr = getNonNullPeriodValue(prData, i);
			}
			
			//1，计算过期库存   over = 当期要下架(PO_off + PR_off + INV_off )-当期销售*预测达成率 - 上期缺货.  和上期缺货没有关系？都缺货了，肯定补到上期已经卖掉了
			double periodOver = (invOff + poOff + prOff) - sell - preOutOfStock;
			double over = 0;
			
			if(periodOver > 0 && (periodOver + accSellAhead) > 0){ //当期过期，并且加上前期可售卖的部分后，还是过期。--真正的过期
				over = periodOver + accSellAhead;
				accSellAhead = 0; //已经过期的库存是要下架处理的，不参与下一期的计算
				accNum = 0;
			}else{
				over = 0; //无过期，显示值为0设置
				accSellAhead += periodOver; //提前可售卖部分做累加处理,不论本期是否有过期库存
				accNum++;
			}
			
			//累积了整个产品的保质期，不应该再算到可售卖中，因为产品肯定还没有生产出来
			/** if(accSellAhead < 0 && (accNum == shelfNum) ){
				double firstSellAhead = getNonNullPeriodValue(periodOverStockRowData, i-shelfNum);
				accSellAhead -= firstSellAhead;
				accNum --;
			}*/
			
			
			//2 再计算期末有效库存 [ 上期末 + 本期到货(P0+PR) - sell(销售) - over(过期)   ], 有效库存小时时，才会产生缺货库存
			double ending =  preEndingStock + po + pr - sell - over;
			
			double subtractSS = ending - ss;
			
			//3计算缺货 = [sell(销售) - 上期末(preEndingStock) - 本期到货(P0+PR) ]
			double outOfStock = sell - preEndingStock - po - pr;
			if(outOfStock < 0){
				//货足够卖，没有生产缺货
				outOfStock = 0;
			}
			
			if(isStandardPrice || isAcutalPrice ){ //是否把数量换算成金额数据
				
				Double price = riskDao.getProductStdPrice(product, period,isAcutalPrice); //每个期间的价格可能不一样，需要每次查询
				outOfStockRowData.pubFun4setPeriodDataValue(i, outOfStock* price);
				endingStockRowData.pubFun4setPeriodDataValue(i, ending* price);
				overStockRowData.pubFun4setPeriodDataValue(i, over* price);
				subtractSSRowData.pubFun4setPeriodDataValue(i, subtractSS* price);
			}else{
				outOfStockRowData.pubFun4setPeriodDataValue(i, outOfStock);
				endingStockRowData.pubFun4setPeriodDataValue(i, ending);
				overStockRowData.pubFun4setPeriodDataValue(i, over);
				subtractSSRowData.pubFun4setPeriodDataValue(i, subtractSS);
			}
			
			periodOverStockRowData.pubFun4setPeriodDataValue(i, periodOver);
			accSellAheadRowData.pubFun4setPeriodDataValue(i, accSellAhead);
			
			
			preEndingStock = ending; //下一轮计算中初始化
			preOutOfStock = outOfStock;
			
			//覆盖天数计算
			if(OS_CDAYS_NEXT.equalsIgnoreCase(bizDataType)){
				//基于下期
				osCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, over));
				
			}else if(OOS_CDAYS_NEXT.equalsIgnoreCase(bizDataType)){
				oosCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, outOfStock));
				
			}else if(ENDING_CDAYS_NEXT.equalsIgnoreCase(bizDataType)){
				endingCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, ending));
				
			}else if(OS_CDAYS_MULTI.equalsIgnoreCase(bizDataType)){
				//基于后面多期
				osCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, over));
			
			}else if(OOS_CDAYS_MULTI.equalsIgnoreCase(bizDataType)){
				oosCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, outOfStock));
			
			}else if(ENDING_CDAYS_MULTI.equalsIgnoreCase(bizDataType)){
				endingCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, ending));
			
			}else if(SUBTRACTS_CDAYS_NEXT.equalsIgnoreCase(bizDataType)){
				subtractSCoverDaysRowData_next.pubFun4setPeriodDataValue(i, computeCoverDaysNext(calcRowData, i, subtractSS));
			
			}else if(SUBTRACTS_CDAYS_MULTI.equalsIgnoreCase(bizDataType)){
				subtractSCoverDaysRowData_multi.pubFun4setPeriodDataValue(i, computeCoverDaysMulti(calcRowData, i, subtractSS));
			}
			
		}
		
		riskDataMap.put(ENDING_STOCK, endingStockRowData);
		riskDataMap.put(OVER_STOCK, overStockRowData);
		riskDataMap.put(SUBSTRACTSS_STOCK, subtractSSRowData);
		riskDataMap.put(OUTOF_STOCK, outOfStockRowData);
		
		riskDataMap.put(OS_CDAYS_NEXT        , osCoverDaysRowData_next);
		riskDataMap.put(OS_CDAYS_MULTI       , osCoverDaysRowData_multi);
		riskDataMap.put(OOS_CDAYS_NEXT       , oosCoverDaysRowData_next);
		riskDataMap.put(OOS_CDAYS_MULTI      , oosCoverDaysRowData_multi);
		riskDataMap.put(ENDING_CDAYS_NEXT    , endingCoverDaysRowData_next);
		riskDataMap.put(ENDING_CDAYS_MULTI   , endingCoverDaysRowData_multi);
		riskDataMap.put(SUBTRACTS_CDAYS_NEXT , subtractSCoverDaysRowData_next);
		riskDataMap.put(SUBTRACTS_CDAYS_MULTI, subtractSCoverDaysRowData_multi);
		
		logger.info("产品["+proCode+"]的StockRisk数据计算完毕");
		
		return riskDataMap;
	}
	
	/**
	 * 根据指定的bizDataName和product, 期间构造对应的行数据ABUiRowData
	 * @param bizDataName
	 * @param product
	 * @param periodBegin
	 * @param periodEnd
	 * @return
	 */
	public ABUiRowData createUiRowData(String bizDataName,BProduct product,int periodBegin,int periodEnd){
		BBizData bizData = new BBizData();
		bizData.setName(bizDataName);
		ABUiRowData rowData = new ABUiRowData();
		rowData.setProduct( product );
		rowData.setBizData( bizData );
		rowData.setPeriodBegin( periodBegin );
		rowData.setPeriodEnd( periodEnd );
		
		return rowData; 
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
