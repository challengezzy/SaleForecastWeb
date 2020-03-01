package dmdd.dmddjava.service.forecastservice;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.dialect.DB2390Dialect;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilBizData;
import dmdd.dmddjava.dataaccess.bizobject.BSuitSkuRel;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dm.ForecastDataDM;
import dmdd.dmddjava.dm.ProductSuitDM;

/**
 * 
 * <p>Title: 预测类折合数据数据的计算</p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jun 19, 2017 12:06:49 AM
 */
public class AmountForecastService {
	
	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(8);
	private static String qrySuitSumSql; //查询所有套装下折合的值
	private static String qrySuitProValueSql; //查询套装下各产品的值 
	private static String mergeSql;
	private static String suitSupportSql;
	
	static {
		//查询套装折合的值
		qrySuitSumSql = "SELECT SUM(D.VALUE*PS.PRODUCT_NUMBER) SUM_SUIT FROM forecastdata D,PRODUCT_SUIT PS "
			+ " WHERE D.PRODUCTID=PS.SUIT_PRODUCT_ID AND D.PERIOD =? "
			+ " AND D.BIZDATAID = ? AND D.ORGANIZATIONID = ?  AND PS.PRODUCT_ID = ? ";
		
		//insert or update
		mergeSql = "if not exists (select 1 from forecastdata where period = ? and organizationid = ? and productid = ? and bizdataid = ?) "
            + "insert into forecastdata(version,status, period, value, organizationid, productid, bizdataid, inittime,updatetime) values (0,0, ?, ?, ?, ?, ?, getdate(),getdate() ) "
            + "else update forecastdata set value = ? where period = ? and organizationid = ? and productid = ? and bizdataid = ?";
		
		qrySuitProValueSql = "SELECT D.ID,PS.PRODUCT_ID,ISNULL(D.VALUE,0) VALUE FROM PRODUCT_SUIT PS "
			+ " LEFT JOIN forecastdata D ON D.PRODUCTID=PS.PRODUCT_ID AND D.PERIOD = ? AND D.BIZDATAID = ? AND D.ORGANIZATIONID = ? "
			+ " WHERE PS.SUIT_PRODUCT_ID = ? ";
		
		suitSupportSql = "select IS_SUIT_SUPPORT from BIZDATA where id = ?";
	}
	
	public void calAmountForecastData(Long bizdataId,Long amountBizDataId ,Long proId,Long orgId
			,int isSuit,int period, Double oriValue,final boolean isSuitUpdate) throws Exception {
		
		if(isSuit == BizConst.GLOBAL_YESNO_YES){ //产品是套装
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql, period, orgId, proId, amountBizDataId, 
            		period, oriValue, orgId, proId, amountBizDataId, 
            		oriValue, period, orgId, proId, amountBizDataId);
			
            //查询套装包含的产品时，顺便查出历史数据值
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySuitProValueSql, period, bizdataId,orgId, proId);
			for (HashVO vo : vos) {
                // 遍历套装下的每一个单品， 刷新单品的折合历史数据
				calAmountForecastData(bizdataId, amountBizDataId, vo.getLongValue("PRODUCT_ID"), 
						orgId, BizConst.GLOBAL_YESNO_NO, period, vo.getDoubleValue("VALUE"),isSuitUpdate );
            }
			
		}else{ //产品是非套装
			
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySuitSumSql, period, bizdataId, orgId, proId);
			Double suitValue = 0d;
			if(vos.length > 0 && vos[0].getDoubleValue("SUM_SUIT") != null ){
				suitValue = vos[0].getDoubleValue("SUM_SUIT");
			}
			double totalValue = suitValue +  oriValue;
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql, period, orgId, proId, amountBizDataId, 
            		period, totalValue, orgId, proId, amountBizDataId, 
            		totalValue, period, orgId, proId, amountBizDataId);
	            
		}
		
	}
	
	public int calculateAmountData(List<ForecastData> forecastDataList) throws  InterruptedException{
		return calculateAmountData(forecastDataList, true);
	}
	
	/**
	 * 启用线程池计算折合业务数据
	 * @param calContextList
	 * @return
	 * @throws InterruptedException
	 */
	public int calculateAmountData(List<ForecastData> forecastDataList,final boolean isSuitUpdate) throws  InterruptedException{
		
		if(!ServerEnvironment.getInstance().isSuitSupport()){
			return 0;//不支持折合
		}
		
		//集合中没有数据
		if (CollectionUtils.isEmpty(forecastDataList)) {
			logger.info("需要计算的折合数据计算为空");
			return 0;
		}
		
		//TODO 优化为一条SQL
		Iterator<ForecastData> iterator = forecastDataList.iterator();
		while (iterator.hasNext()) {
			ForecastData forecastData = iterator.next();
			try {
				Long bizDataId = forecastData.getBizData().getId();
				HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,suitSupportSql,bizDataId);
			    Integer isSuitSupport = vos[0].getIntegerValue("IS_SUIT_SUPPORT");
			    if (UtilBizData.matchNoSuitSupport(isSuitSupport)) {
			    	logger.info("业务号为["+ bizDataId +"],不需要折和计算");
			    	iterator.remove();
			    }
			}catch (Exception e) {
				logger.error("查询业务数据出错", e);
			}
		}
		
		long curTime = System.currentTimeMillis();
		// 新建折合业务数据处理线程池
		
		final AtomicInteger count = new AtomicInteger(0);
		
		final CountDownLatch latch = new CountDownLatch(forecastDataList.size());
		for(final ForecastData fcData : forecastDataList){
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					//long start2 = System.currentTimeMillis();
					try {
						
						if(fcData.getAmountBizData() == null){
							return ;
						}
						
						int period = fcData.getPeriod();
						Double value = (double)fcData.getValue();
						
						int isSuit = fcData.getProduct().getIsSuit();
						Long proId = fcData.getProduct().getId();
						Long orgId = fcData.getOrganization().getId();
						calAmountForecastData( fcData.getBizData().getId(), fcData.getAmountBizData().getId()
									, proId, orgId, isSuit, period, value,isSuitUpdate );
						
						dmo.commit(DMOConst.DS_DEFAULT);
						count.incrementAndGet();
						//logger.debug("历史导入时更新折合数据完成,耗时[" + (System.currentTimeMillis() - start2) + "]ms");
					} catch (Exception e) {
						logger.error("预测类数据更新时，计算折合数据异常", e);
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
	 * 更新单个的折合数据
	 * @param data
	 * @param targetAmountBizData
	 * @throws Exception
	 */
	@Deprecated
	public void refreshAmountForecastData(ForecastData data, BizData targetAmountBizData) throws Exception {
        ProductSuitDM productSuitDM = new ProductSuitDM();
        ForecastDataDM forecastDataDM = new ForecastDataDM();
        
        List<BSuitSkuRel> suitSkus = productSuitDM.getSuitSkus(data.getProduct().getId());
        if (CollectionUtils.isEmpty(suitSkus)) {
            // 原产品是单品， 计算单品销量 + 套装销量
            // 单品销量
            Double totalValue = forecastDataDM.getValue(data.getPeriod(), data.getProduct().getId(), data.getOrganization().getId(), data.getBizData().getId()); 
            
            // 包含这个单品的所有套装
            List<BSuitSkuRel> suitsContainsProduct = productSuitDM.getSuitSkusByProductId(data.getProduct().getId());
            for (BSuitSkuRel rel : suitsContainsProduct) {
                // 获取单个套装的销量
                Double singleValue = forecastDataDM.getValue(data.getPeriod(), rel.getSuitProductId(), data.getOrganization().getId(), data.getBizData().getId());
                // 总销量 += 套装销量 * 套装中单品的个数
                totalValue += singleValue * rel.getRatio();
            }
            
            ForecastData amountForecastData = new ForecastData(data.getPeriod(), totalValue.longValue(), targetAmountBizData, data.getOrganization(), data.getProduct());
            forecastDataDM.updateForecastData(amountForecastData);
        } else {
            // 原产品是套装
            ForecastData amountHistoryData = new ForecastData(data.getPeriod(), data.getValue(), targetAmountBizData, data.getOrganization(), data.getProduct());
            // 更新套装产品折合历史数据
            forecastDataDM.updateForecastData(amountHistoryData);
            
            for (BSuitSkuRel rel : suitSkus) {
                // 遍历套装下的每一个单品， 刷新单品的折合历史数据
                Product relatedProduct = new Product(rel.getProId());
                ForecastData relatedForecastData = new ForecastData(data.getPeriod(), data.getValue(), data.getBizData(), data.getOrganization(), relatedProduct);
                refreshAmountForecastData(relatedForecastData, targetAmountBizData);
            }
        }
        
        new CommDMO().commit(DMOConst.DS_DEFAULT);
    }
	
}
