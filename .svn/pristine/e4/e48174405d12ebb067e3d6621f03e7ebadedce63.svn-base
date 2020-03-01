package dmdd.dmddjava.service.historyservice.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.dataaccess.aidobject.ABImHistoryData;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizDataDefItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.service.historyservice.AmountHistoryCalContext;
import dmdd.dmddjava.service.historyservice.AmountHistoryService;

/**
 * <p>Title: 历史数据导入</p>
 * <p>Description: 处理历史数据导入，导入后进行对应业务数据的折合计算</p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jun 17, 2017 10:18:59 PM
 */
public class HistoryImportService {
	
	protected Logger logger = CoolLogger.getLogger(this.getClass());

    protected CommDMO dmo = new CommDMO(false);
	
	//private HistoryDataDM historyDataDM = new HistoryDataDM();
	
	private final AmountHistoryService amountHistoryService = new AmountHistoryService();

	/**
	 * 历史数据导入
	 * @param _bizDataHistory
	 * @param _bUnitGroup
	 * @param _list4ABImHistoryData
	 * @return
	 * @throws Exception
	 */
	public List<ABImHistoryData> saveHistoryDatas4ImportUI(BBizData _bizDataHistory, BUnitGroup _bUnitGroup,
			List<ABImHistoryData> _list4ABImHistoryData) throws Exception {
		
		List<ABImHistoryData> rstList = new ArrayList<ABImHistoryData>();
		if (_bizDataHistory == null || _bUnitGroup == null || CollectionUtils.isEmpty(_list4ABImHistoryData)) {
			throw new Exception("Key Paramete is not empty !");
		}
		
		// 检查服务器状态是否可以提供服务
		ServerEnvironment.getInstance().checkSystemStatus();

		long beginTime = System.currentTimeMillis();
		logger.info("历史数据导入开始...");
		final ImHistoryDataContext context = new ImHistoryDataContext();
		
		BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
		BizData bizData_History = (BizData) bizDataBDConvertor.btod(_bizDataHistory);

		Session session = HibernateSessionFactory.getSession();
		DaoBizData bizDataDao = new DaoBizData(session);

		BizData bizData_HistoryAd = null;
		List<Long> listBizDataId_HistoryAdR = new ArrayList<Long>();

		DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem(session);
		// 查询历史类业务数据对应的历史调整类业务数据和调整原因类业务数据 begin
		bizData_HistoryAd = daoBizDataDefItem.getBizDataHistoryAdByBizDataHistoryId(_bizDataHistory.getId());
		
		List<BizData> listBizData_HistoryAdR = null;
		if (bizData_HistoryAd != null) {
			listBizData_HistoryAdR = daoBizDataDefItem.getBizDataHistoryAdRByBizDataHistoryAdId(bizData_HistoryAd.getId());
			if (listBizData_HistoryAdR != null) {
				for (int i = 0; i < listBizData_HistoryAdR.size(); i++) {
					listBizDataId_HistoryAdR.add(listBizData_HistoryAdR.get(i).getId());
				}
			}
		}
		
		//折合数据数据查询
		if( ServerEnvironment.getInstance().isSuitSupport() ){
			BizData amountBizDataHistory = bizDataDao.getBizDataByCode(bizData_History.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
			context.setAmountBizDataHistory(amountBizDataHistory);
			
			if(bizData_HistoryAd != null){
				//历史调整数据折合
				BizData amountBizDataHistoryAd = bizDataDao.getBizDataByCode(bizData_HistoryAd.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
				context.setAmountBizDataHistoryAd(amountBizDataHistoryAd);
				
				if(CollectionUtils.isNotEmpty(listBizData_HistoryAdR) ){
					//历史调整原因类
					List<Long> listAmountBizDataId_HistoryAdR = new ArrayList<Long>(listBizData_HistoryAdR.size());
					for (int i = 0; i < listBizData_HistoryAdR.size(); i++) {
						BizData amountHisAdR = bizDataDao.getBizDataByCode(listBizData_HistoryAdR.get(i).getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
						if (amountHisAdR != null) {
							listAmountBizDataId_HistoryAdR.add(amountHisAdR.getId());
						}
					}
					context.setListAmountBizDataId_HistoryAdR(listAmountBizDataId_HistoryAdR);
				}
			}
			
		}
		
		logger.info("查询历史类业务数据对应的历史调整类业务数据和调整原因类业务数据OK");
		
		context.setBizDataHistory(bizData_History);
		context.setBizDataHistoryAd(bizData_HistoryAd);
		context.setUnitGroup(_bUnitGroup);
		
		context.setListBizDataId_HistoryAdR(listBizDataId_HistoryAdR);
		context.setCalContextList(new ArrayList<AmountHistoryCalContext>(_list4ABImHistoryData.size()*12));
		
		ExecutorService pool = null;
		try {
			ABImHistoryData abImHistoryData = null;

			Iterator<ABImHistoryData> itr_list4ABImHistoryData = _list4ABImHistoryData.iterator();
			while (itr_list4ABImHistoryData.hasNext()) {
				abImHistoryData = itr_list4ABImHistoryData.next();
				
				//逐行处理要导入的数据
				try{
					saveRowHistoryData(context,abImHistoryData,session);
				}catch (Exception e) {
					abImHistoryData.setImportResult(e.toString());
					logger.error("历史数据["+ abImHistoryData.getProductCode() +"]["+ abImHistoryData.getOrganizationCode() +"]行导入异常," + e.toString());
					e.printStackTrace();
				}
				
				rstList.add(abImHistoryData);
			}

			long hisSaveCost = System.currentTimeMillis()- beginTime;
			
			long curTime = System.currentTimeMillis();
			// 新建折合业务数据处理线程池
			pool = Executors.newFixedThreadPool(8);
			final CountDownLatch latch = new CountDownLatch(context.getCalContextList().size());
			if (ServerEnvironment.getInstance().isSuitSupport() && context.getAmountBizDataHistory() != null ) {
				for(final AmountHistoryCalContext calContext : context.getCalContextList()){
					pool.execute(new Runnable() {
						@Override
						public void run() {
							long start2 = System.currentTimeMillis();
							try {
								int period = calContext.getPeriod();
								Double value = calContext.getOriValue();
								
								int isSuit = calContext.getIsSuit();
								Long proId = calContext.getProId();
								Long orgId = calContext.getOrgId();
								amountHistoryService.calAmountHistoryData(calContext.getBizDataId(), calContext.getAmountBizDataId()
											, proId, orgId, isSuit, period, value, calContext.getBizDataIdAd(),calContext.getAmountBizDataIdAd(),true);
								
								dmo.commit(DMOConst.DS_DEFAULT);
								logger.debug("历史导入时更新折合数据完成,耗时[" + (System.currentTimeMillis() - start2) + "]ms");
							} catch (Exception e) {
								logger.error("历史数据导入时，计算折合数据异常", e);
							}finally{
								latch.countDown();
							}
						}
					});
				}
				latch.await();
			}
			long amountSaveCost = System.currentTimeMillis()- curTime;
			int totalNum = context.getDealNum()+context.getCalContextList().size();
			logger.info("历史数据导入成功!共处理[" + totalNum + "]条更新历史导入耗时[" + hisSaveCost + "]ms,折合计算耗时[" + amountSaveCost + "]ms");
		} catch (Exception ex) {
			logger.error("历史数据导入异常");
			ex.printStackTrace();
			throw ex;
		} finally {
			if (session.isOpen()) {
				session.close();
			}
			dmo.releaseContext(DMOConst.DS_DEFAULT);
			
			if(pool != null){
				pool.shutdown();
			}
			
		}
		return rstList;
	}
	
	/**
	 * 处理每一行的历史数据,同时需要处理到历史调整类、历史调整原因类数据
	 * @throws Exception
	 */
	private ABImHistoryData saveRowHistoryData(ImHistoryDataContext context,ABImHistoryData abImHistoryData,Session session) throws Exception{
		
		final ImHistoryDataRowContext rowContext = new ImHistoryDataRowContext();
		//数据合法性校验
		validateAbImHistoryData(abImHistoryData, rowContext, context);
		
		BizData bizDataHistory = context.getBizDataHistory();
		BizData bizDataHistoryAd = context.getBizDataHistoryAd();
		Product detailProduct = rowContext.getProduct();
		Organization detailOrganization = rowContext.getOrganization();
		int periodBegin = abImHistoryData.getPeriodBegin();
		int periodEnd = abImHistoryData.getPeriodEnd();
		
		long start = System.currentTimeMillis();
		Transaction trsa = null;
		try {
			DaoHistoryData daoHistoryData = new DaoHistoryData(session);

			// 处理每月数据 begin
			final List<HistoryData> listHistoryData_new = new ArrayList<HistoryData>();
			final List<HistoryData> listHistoryData_upd = new ArrayList<HistoryData>();

			int periodDiff = UtilPeriod.getPeriodDifference(periodBegin, periodEnd);
			
			//处理每个期间的数据
			for (int periodLoc = 0; periodLoc <= periodDiff; periodLoc++) {
				
				Double value = abImHistoryData.pubFun4getPeriodDataValue(periodLoc);
				int period = UtilPeriod.getPeriod(periodBegin, periodLoc);
				
				//增加对折合计算的处理准备
				if(ServerEnvironment.getInstance().isSuitSupport()){
					AmountHistoryCalContext calContext = new AmountHistoryCalContext();
					calContext.setPeriod(period);
					calContext.setOrgId(detailOrganization.getId());
					calContext.setProId(detailProduct.getId());
					calContext.setIsSuit(detailProduct.getIsSuit());
					calContext.setOriValue(value);
					calContext.setBizDataId(bizDataHistory.getId());
					calContext.setAmountBizDataId(context.getAmountBizDataHistory().getId());
					calContext.setBizDataIdAd(bizDataHistoryAd.getId());
					if( context.getAmountBizDataHistoryAd() != null ){
						calContext.setAmountBizDataIdAd( context.getAmountBizDataHistoryAd().getId());
					}
					context.addCalContext(calContext);
				}

				HistoryData historyData_inDB = daoHistoryData.getHistoryData(detailProduct.getId(), detailOrganization.getId(), period, bizDataHistory.getId());
				
				if (historyData_inDB == null) {
					// 新建历史数据
					HistoryData historyData_new = new HistoryData();
					historyData_new.setProduct(detailProduct);
					historyData_new.setOrganization(detailOrganization);
					historyData_new.setPeriod(period);
					historyData_new.setBizData(bizDataHistory);
					historyData_new.setValue(value);

					listHistoryData_new.add(historyData_new);

					// 新建历史调整数据
					HistoryData historyData_new_Ad = new HistoryData();
					historyData_new_Ad.setProduct(detailProduct);
					historyData_new_Ad.setOrganization(detailOrganization);
					historyData_new_Ad.setPeriod(period);
					historyData_new_Ad.setBizData(bizDataHistoryAd);
					historyData_new_Ad.setValue(value);

					listHistoryData_new.add(historyData_new_Ad);

				} else {
					// 更新历史数据
					HistoryData historyData_upd = historyData_inDB;
					historyData_upd.setValue(value);
					listHistoryData_upd.add(historyData_upd);

					// 更新历史调整数据 begin
					HistoryData historyData_upd_Ad = daoHistoryData.getHistoryData(detailProduct.getId(), detailOrganization.getId(), period,bizDataHistoryAd.getId());
					if (historyData_upd_Ad == null) {
						// 新建历史调整数据
						HistoryData historyData_new_Ad = new HistoryData();
						historyData_new_Ad.setProduct(detailProduct);
						historyData_new_Ad.setOrganization(detailOrganization);
						historyData_new_Ad.setPeriod(period);
						historyData_new_Ad.setBizData(bizDataHistoryAd);
						historyData_new_Ad.setValue(value);

						listHistoryData_new.add(historyData_new_Ad);
					} else {
						// 更新历史调整数据
						historyData_upd_Ad.setValue(value);
						listHistoryData_upd.add(historyData_upd_Ad);

						//如果存在历史调整原因类数据，则删除之
						if ( CollectionUtils.isNotEmpty(context.getListBizDataId_HistoryAdR()) ) {
							// 删除历史调整原因类数据
//							List<HistoryData> listHistoryData_inDB_AdR = daoHistoryData.getHistoryDatas(detailProduct.getId(),
//									detailOrganization.getId(), period, context.getListBizDataId_HistoryAdR() );
//							if (CollectionUtils.isNotEmpty(listHistoryData_inDB_AdR) ) {
//								listHistoryData_del.addAll(listHistoryData_inDB_AdR);
//							}

							// 增加对应折合类业务数据的删除
//							List<HistoryData> listAmountHistoryData_inDB_AdR = daoHistoryData.getHistoryDatas(detailProduct.getId(),
//									detailOrganization.getId(), period, listAmountBizDataId_HistoryAdR);
//							if (listAmountHistoryData_inDB_AdR != null) {
//								listAmountHistoryData_del.addAll(listAmountHistoryData_inDB_AdR);
//							}

						}// 删除历史调整原因类数据 end
					}// 更新历史调整数据 end
				}//更新历史数据end 
			}//end for 处理每月数据 

			// 调用持久化方法 ,更新历史数据和历史调整类数据 begin
			trsa = session.beginTransaction();
			if (CollectionUtils.isNotEmpty(listHistoryData_new) ) {
				for (int j = 0; j < listHistoryData_new.size(); j++) {
					daoHistoryData.save(listHistoryData_new.get(j));
				}
			}

			if (CollectionUtils.isNotEmpty(listHistoryData_upd) ) {
				for (int j = 0; j < listHistoryData_upd.size(); j++) {
					daoHistoryData.update(listHistoryData_upd.get(j));
				}
			}
			
			int delNum = 0;
			// 删除调整原因类历史数据
			if ( CollectionUtils.isNotEmpty(context.getListBizDataId_HistoryAdR()) ) {
				delNum += daoHistoryData.deleteHistoryDatas(detailProduct.getId(), detailOrganization.getId()
							, periodBegin, periodEnd, context.getListBizDataId_HistoryAdR());
			}
			
			// 删除调整原因类历史数据 - 折合
			if( CollectionUtils.isNotEmpty(context.getListAmountBizDataId_HistoryAdR())){
				delNum += daoHistoryData.deleteHistoryDatas(detailProduct.getId(), detailOrganization.getId()
						, periodBegin, periodEnd, context.getListAmountBizDataId_HistoryAdR() );
			}

			trsa.commit();
			// 调用持久化方法 end

			logger.info("原始数据导入处理完成,耗时[" + (System.currentTimeMillis() - start) + "]ms");
			
			abImHistoryData.setImportResult(BizConst.IMPORT_RESULT_SUCCESS);
			int impNumber = listHistoryData_new.size() + listHistoryData_upd.size() + delNum;
			
			context.setDealNum( context.getDealNum()+impNumber );
			
		} catch (Exception ex) {
			if (trsa != null) {
				trsa.rollback();
			}
			ex.printStackTrace();
		} finally {
			
		}
		
		return abImHistoryData;
	}
	
	/**
	 * 检验要导入数据的合法性
	 * @param abImHistoryData
	 * @param rowContext
	 * @param context
	 * @return
	 */
	private void validateAbImHistoryData(ABImHistoryData abImHistoryData,ImHistoryDataRowContext rowContext,ImHistoryDataContext context)
		throws Exception {
		
		// 起始期间检查
		int periodBegin = abImHistoryData.getPeriodBegin();
		if (periodBegin == SysConst.PERIOD_NULL) {
			throw new Exception("There is no Begin Period");
		}

		// 终止期间检查
		int periodEnd = abImHistoryData.getPeriodEnd();
		if (periodEnd == SysConst.PERIOD_NULL) {
			throw new Exception("There is no End Period");
		}
		
		if(periodEnd < periodBegin){
			throw new Exception("End Period less than Begin Period");
		}
		
		BProduct detailProduct = ServerEnvironment.getInstance().getBProduct(abImHistoryData.getProductCode());
		//产品检查
		if (detailProduct == null) {
			throw new Exception("Can not find Detail Product by the Code");
		}
		
		if(detailProduct.getIsCatalog() == BizConst.GLOBAL_YESNO_YES){
			throw new Exception("Product is not a detail product");
		}
		
		//产品的单位组
		if (detailProduct.getUnitGroup() == null) {
			throw new Exception("Detail Product has no UnitGroup");
		}
		
		BUnitGroup unitGroup = context.getUnitGroup();
		if (detailProduct.getUnitGroup().getId().longValue() != unitGroup.getId().longValue()) {
			throw new Exception("Detail Product's UOM Group does not match with that of parameter");
		}
		
		
		//组织检查
		BOrganization detailOrganization = ServerEnvironment.getInstance().getBOrganization(abImHistoryData.getOrganizationCode());
		if (detailOrganization == null) {
			throw new Exception("Can not find Detail Organization by the Code");
		}
		
		if(detailOrganization.getIsCatalog() == BizConst.GLOBAL_YESNO_YES){
			throw new Exception("Organization is not a detail organization");
		}
		
		ProductBDConvertor productBDConvertor = new ProductBDConvertor();
        Product product = (Product) productBDConvertor.btod(detailProduct, false);
        rowContext.setProduct(product);
        
        OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
		Organization organization = (Organization) organizationBDConvertor.btod(detailOrganization, false );
		rowContext.setOrganization(organization);
		
		//TODO 判断产品组合，是否在下期的预测策略中
//		DaoForecastInst daoForecastInst = new DaoForecastInst(session);
//		Integer[] arr4ForecastInstIsValid = new Integer[] { BizConst.GLOBAL_YESNO_YES };
//		List<ForecastInst> listForecastInst_inDB = daoForecastInst.getAllForecastInsts(arr4ForecastInstIsValid);
//		for (ForecastInst forecastInst : listForecastInst_inDB) {
//			if (forecastInst.getNextIsValid() == BizConst.GLOBAL_YESNO_YES) {
//				for (ForecastInstNextProOrg forecastInstNextProOrg : forecastInst.getForecastInstNextProOrgs()) {
//					AProOrg proorg_ = new AProOrg();
//					proorg_.setProduct(forecastInstNextProOrg.getProduct());
//					proorg_.setOrganization(forecastInstNextProOrg.getOrganization());
//					listProOrg4FrorecastIns.add(proorg_);
//					proCodes.put(forecastInstNextProOrg.getProduct().getCode(), forecastInstNextProOrg.getProduct());
//					orgCodes.put(forecastInstNextProOrg.getOrganization().getCode(), forecastInstNextProOrg.getOrganization());
//					proorgCodes.put(forecastInstNextProOrg.getProduct().getCode(), forecastInstNextProOrg.getOrganization().getCode());
//				}
//			}
//		}
//		
//		boolean isProOrgInForecast = false; //产品组织是否包含在下期的范围内
//		if (!isProOrgInForecast)// 不包含在有效的预测下期范围内
//		{
//			BSysParam param = ServerEnvironment.getInstance().getSysParam(BizConst.SYSPARAM_CODE_HISTORYDATAIMPORTWARN);
//			if (param != null && Integer.parseInt(param.getValue()) == BizConst.GLOBAL_YESNO_YES) {
//				AProOrg aProOrg = new AProOrg();
//				aProOrg.setProduct(detailProduct);
//				aProOrg.setOrganization(detailOrganization);
//				boolean isContains = false;
//				for (AProOrg aProOrg_ : listProOrg4FrorecastIns) {
//					int relation = UtilProOrg.relationOf(aProOrg_, aProOrg);
//					if (relation == UtilProOrg.RELATION_FIRST2SECOND_COVERING || relation == UtilProOrg.RELATION_FIRST2SECOND_EQUAL) {
//						isContains = true;
//						break;
//					}
//				}
//				if (!isContains) {
//					abImHistoryData.setWarnInfo(BizConst.HISTORYDATA_IMPORT_WARNINFO_4);
//				}
//			}
//
//		}
      //valid end

	}
	
	
}
