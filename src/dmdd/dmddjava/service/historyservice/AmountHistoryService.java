package dmdd.dmddjava.service.historyservice;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BSuitSkuRel;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dm.HistoryDataDM;
import dmdd.dmddjava.dm.ProductSuitDM;

/**
 * <p>Title: 历史折合数据刷新</p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jun 18, 2017 1:08:48 AM
 */
public class AmountHistoryService {
	
	private HistoryDataDM historyDataDM = new HistoryDataDM();
	private ProductSuitDM productSuitDM = new ProductSuitDM();
	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
	private static String qrySuitSumSql; //查询所有套装下折合的值
	private static String qrySuitProValueSql; //查询套装下各产品的值 
	private static String mergeSql;
	
	static {
		//查询套装折合的值
		qrySuitSumSql = "SELECT SUM(D.VALUE*PS.PRODUCT_NUMBER) SUM_SUIT FROM HISTORYDATA D,PRODUCT_SUIT PS "
			+ " WHERE D.PRODUCTID=PS.SUIT_PRODUCT_ID AND D.PERIOD =? "
			+ " AND D.BIZDATAID = ? AND D.ORGANIZATIONID = ?  AND PS.PRODUCT_ID = ? ";
		
		//insert or update
		mergeSql = "if not exists (select 1 from HISTORYDATA where period = ? and organizationid = ? and productid = ? and bizdataid = ?) "
            + "insert into HISTORYDATA(version, period, value, organizationid, productid, bizdataid) values (0, ?, ?, ?, ?, ?) "
            + "else update HISTORYDATA set value = ? where period = ? and organizationid = ? and productid = ? and bizdataid = ?";
		
		qrySuitProValueSql = "SELECT D.ID,PS.PRODUCT_ID,ISNULL(D.VALUE,0) VALUE FROM PRODUCT_SUIT PS "
			+ " LEFT JOIN HISTORYDATA D ON D.PRODUCTID=PS.PRODUCT_ID AND D.PERIOD = ? AND D.BIZDATAID = ? AND D.ORGANIZATIONID = ? "
			+ " WHERE PS.SUIT_PRODUCT_ID = ? ";
	}
	
	/**
	 * 折合数据计算
	 * @param bizdataId
	 * @param amountBizDataId
	 * @param proId
	 * @param orgId
	 * @param isSuit
	 * @param period
	 * @param oriValue 非折合的原始值
	 * @throws Exception
	 */
	public void calAmountHistoryData(Long bizdataId,Long amountBizDataId ,Long proId,Long orgId
			,int isSuit,int period, Double oriValue) throws Exception {
		//不包含历史调整类数据的情况
		calAmountHistoryData(amountBizDataId, amountBizDataId, proId, orgId, isSuit, period, oriValue, null, null,false);
	}
	
	public void calAmountHistoryData(Long bizdataId,Long amountBizDataId ,Long proId,Long orgId
			,int isSuit,int period, Double oriValue,Long bizdataIdAd,Long amountBizDataIdAd,boolean isSuitUpdate) throws Exception {
		
		if(isSuit == BizConst.GLOBAL_YESNO_YES){ //产品是套装
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql, period, orgId, proId, amountBizDataId, 
            		period, oriValue, orgId, proId, amountBizDataId, 
            		oriValue, period, orgId, proId, amountBizDataId);
			
			if(amountBizDataIdAd != null){
				//在历史数据导入时，历史调整类数据值=历史数据值，同步更新
	            dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql, period, orgId, proId, amountBizDataIdAd, 
	            		period, oriValue, orgId, proId, amountBizDataIdAd, 
	            		oriValue, period, orgId, proId, amountBizDataIdAd);
			}
			
			if(isSuitUpdate){
	            //查询套装包含的产品时，顺便查出历史数据值
				HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySuitProValueSql, period, bizdataId,orgId, proId);
				for (HashVO vo : vos) {
	                // 遍历套装下的每一个单品， 刷新单品的折合历史数据
					calAmountHistoryData(bizdataId, amountBizDataId, vo.getLongValue("PRODUCT_ID"), 
							orgId, BizConst.GLOBAL_YESNO_NO, period, vo.getDoubleValue("VALUE") ,bizdataIdAd,amountBizDataIdAd,isSuitUpdate);
	            }
			}
			
		}else{ //产品是非套装
			
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySuitSumSql, period, bizdataId, orgId, proId);
			Double suitValue = 0d;
			if(vos.length > 0 && vos[0].getDoubleValue("SUM_SUIT") != null ){
				suitValue = vos[0].getDoubleValue("SUM_SUIT");
			}
			//非套装的值 = 非套装的原始值+ 所有的套装值*比例 进行累加
			double totalValue = suitValue +  oriValue;
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql, period, orgId, proId, amountBizDataId, 
            		period, totalValue, orgId, proId, amountBizDataId, 
            		totalValue, period, orgId, proId, amountBizDataId);
            
            if(amountBizDataIdAd != null){
	            //在历史数据导入时，历史调整类数据值=历史数据值，同步更新
	            dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql, period, orgId, proId, amountBizDataIdAd, 
	            		period, totalValue, orgId, proId, amountBizDataIdAd, 
	            		totalValue, period, orgId, proId, amountBizDataIdAd);
            }
			
		}
		
	}
	
	public int caculateAmountData(List<AmountHistoryCalContext> calContextList) throws  InterruptedException{
		return caculateAmountData(calContextList, true);
	}
	
	/**
	 * 启用线程池计算折合业务数据
	 * @param calContextList
	 * @return
	 * @throws InterruptedException
	 */
	public int caculateAmountData(List<AmountHistoryCalContext> calContextList,final boolean isSuitUpdate) throws  InterruptedException{
		
		if(!ServerEnvironment.getInstance().isSuitSupport()){
			return 0;//不支持折合
		}
		//集合中没有数据
		if (CollectionUtils.isEmpty(calContextList)) {
			logger.info("需要计算的折合数据计算为空");
			return 0;
		}
		long curTime = System.currentTimeMillis();
		// 新建折合业务数据处理线程池
		
		final AtomicInteger count = new AtomicInteger(0);
		
		final CountDownLatch latch = new CountDownLatch(calContextList.size());
		for(final AmountHistoryCalContext calContext : calContextList){
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					//long start2 = System.currentTimeMillis();
					try {
						int period = calContext.getPeriod();
						Double value = calContext.getOriValue();
						
						int isSuit = calContext.getIsSuit();
						Long proId = calContext.getProId();
						Long orgId = calContext.getOrgId();
						calAmountHistoryData(calContext.getBizDataId(), calContext.getAmountBizDataId()
									, proId, orgId, isSuit, period, value, calContext.getBizDataIdAd(),calContext.getAmountBizDataIdAd(),isSuitUpdate);
						
						dmo.commit(DMOConst.DS_DEFAULT);
						count.incrementAndGet();
						//logger.debug("历史导入时更新折合数据完成,耗时[" + (System.currentTimeMillis() - start2) + "]ms");
					} catch (Exception e) {
						logger.error("历史数据导入时，计算折合数据异常", e);
					}finally{
						latch.countDown();
					}
				}
			});
		}
		
		latch.await();
		
		long amountSaveCost = System.currentTimeMillis()- curTime;
		logger.info("折合数据计算完成，共更新["+ count.intValue() +"]折合数据记录,耗时["+ amountSaveCost +"]ms");
		return count.intValue();
	}
	
	/**
	 * 一条一条数据刷新，效率较低
	 * @param data
	 * @param targetAmountBizData
	 * @throws Exception
	 */
	public void refreshAmountHistoryData(HistoryData data, BizData targetAmountBizData) throws Exception {
        
        //获取套装与SKU关系
        List<BSuitSkuRel> suitSkus = productSuitDM.getSuitSkus(data.getProduct().getId());
        if (CollectionUtils.isEmpty(suitSkus)) {
            // 原产品是单品， 计算单品销量 + 套装销量
        	// 包含这个单品的所有套装
        	
        	//long start = System.currentTimeMillis();
            List<BSuitSkuRel> suitsContainsProduct = productSuitDM.getSuitSkusByProductId(data.getProduct().getId());
        	
            // 单品销量
            //Double totalValue = historyDataDM.getValue(data.getPeriod(), data.getProduct().getId(), data.getOrganization().getId(), data.getBizData().getId());
            //该数据的值就是它本身,不用从数据库中取
            double totalValue = data.getValue();
            
            for (BSuitSkuRel rel : suitsContainsProduct) {
                // 获取单个套装的销量
            	double singleValue = historyDataDM.getValue(data.getPeriod(), rel.getSuitProductId(), data.getOrganization().getId(), data.getBizData().getId());
                // 总销量 += 套装销量 * 套装中单品的个数
                totalValue += singleValue * rel.getRatio();
            }
            
            HistoryData amountHistoryData = new HistoryData(data.getPeriod(), totalValue, targetAmountBizData, data.getOrganization(), data.getProduct());
            historyDataDM.updateHistoryData(amountHistoryData);
            
            //logger.info("单品折合计算完成,套装数量["+ suitsContainsProduct.size() +"]条,耗时["+ (System.currentTimeMillis() - start) +"]ms");
        } else {
            // 原产品是套装
            HistoryData amountHistoryData = new HistoryData(data.getPeriod(), data.getValue(), targetAmountBizData, data.getOrganization(), data.getProduct());
            // 更新套装产品折合历史数据
            historyDataDM.updateHistoryData(amountHistoryData);
            
            for (BSuitSkuRel rel : suitSkus) {
                // 遍历套装下的每一个单品， 刷新单品的折合历史数据
                Product relatedProduct = new Product(rel.getProId());
                HistoryData relatedHistoryData = new HistoryData(data.getPeriod(), data.getValue(), data.getBizData(), data.getOrganization(), relatedProduct);
                refreshAmountHistoryData(relatedHistoryData, targetAmountBizData);
            }
        }
    }

}
