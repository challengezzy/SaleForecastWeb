/**
 * 
 */

package dmdd.dmddjava.service.historyservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.logging.CoolLogger;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ABImHistoryADRData;
import dmdd.dmddjava.dataaccess.aidobject.ABImHistoryData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.HistoryAdjustLogBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OperatorUserBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OrganizationBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.ProductBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BHistoryAdjustLog;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BUnitGroup;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog;
import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLogAdjustItem;
import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLogProOrg;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizDataDefItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryAdjustLog;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryAdjustLogAdjustItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryAdjustLogProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;
import dmdd.dmddjava.service.dimensionservice.BizDataService;
import dmdd.dmddjava.service.historyservice.imp.HistoryImportService;

/**
 * @author liuzhen
 * 
 *         本类中，对 HistoryData 的处理，返回B类对象的获取，直接通过对DB操作返回的D类对象进行BD转换获得，
 *         不是通过再次查询获得，原因是因为 HistoryData 是个简单对象，不含附属的集合属性
 */
public class HistoryService {
    protected Logger logger = CoolLogger.getLogger(this.getClass());

    private HistoryImportService historyImportService = new HistoryImportService();
    
    private final AmountHistoryService amountHistoryService = new AmountHistoryService();
    
    protected CommDMO dmo = new CommDMO(false);
    
    /**
     * 2010.12.10 by liuzhen 数据框架设计中去掉历史数据导入功能，这个方法也不再使用 保存录入界面传来的数据
     * 以行为事务来处理,但一旦某行出错即终止后继行的处理
     * 
     * @param _listABUiRowData4save
     * @throws Exception
     */
    @Deprecated
    public void saveHistoryDatas4InputUI(List<ABUiRowData> _listABUiRowData4save) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_listABUiRowData4save == null || _listABUiRowData4save.isEmpty()) {
            return;
        }
        
        int dealCount = 0;
        ProductBDConvertor productBDConvertor = new ProductBDConvertor();
        OrganizationBDConvertor organizationBDConvertor = new OrganizationBDConvertor();
        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            // 每行一个事务处理一次 begin
            for (int i = 0; i < _listABUiRowData4save.size(); i++) {
                ABUiRowData abUiRowData = _listABUiRowData4save.get(i);

                Product detailProduct = productBDConvertor.btod(abUiRowData.getProduct(), false);
                Organization detailOrganization = organizationBDConvertor.btod(abUiRowData.getOrganization(), false);
                BizData bizData_History = bizDataBDConvertor.btod(abUiRowData.getBizData(), false);

                int periodDiff = UtilPeriod.getPeriodDifference(abUiRowData.getPeriodBegin(), abUiRowData.getPeriodEnd());

                List<HistoryData> listHistoryData_new = new ArrayList<HistoryData>();
                List<HistoryData> listHistoryData_upd = new ArrayList<HistoryData>();
                List<HistoryData> listHistoryData_del = new ArrayList<HistoryData>();

                trsa = session.beginTransaction();
                DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem(session);
                DaoHistoryData daoHistoryData = new DaoHistoryData(session);

                BizData bizData_historyAd = daoBizDataDefItem.getBizDataHistoryAdByBizDataHistoryId(bizData_History.getId());
                List<Long> listBizDataId_HistoryAdR = new ArrayList<Long>();
                List<BizData> listBizData_historyAdR = daoBizDataDefItem.getBizDataHistoryAdRByBizDataHistoryAdId(bizData_historyAd.getId());
                if (listBizData_historyAdR != null) {
                    for (int j = 0; j < listBizData_historyAdR.size(); j++) {
                        listBizDataId_HistoryAdR.add(listBizData_historyAdR.get(j).getId());
                    }
                }

                for (int periodLoc = 0; periodLoc <= periodDiff; periodLoc++) {
                    int period = UtilPeriod.getPeriod(abUiRowData.getPeriodBegin(), periodLoc);
                    Double value = abUiRowData.pubFun4getPeriodDataValue(periodLoc);

                    HistoryData historyData_inDB = daoHistoryData.getHistoryData(detailProduct.getId(), detailOrganization.getId(), period,
                            bizData_History.getId());
                    if (historyData_inDB == null) {
                        // 新建历史数据 begin
                        HistoryData historyData_new = new HistoryData();
                        historyData_new.setProduct(detailProduct);
                        historyData_new.setOrganization(detailOrganization);
                        historyData_new.setPeriod(period);
                        historyData_new.setBizData(bizData_History);
                        historyData_new.setValue(value);

                        listHistoryData_new.add(historyData_new);
                        // 新建历史数据 end

                        // 新建历史调整数据 begin
                        HistoryData historyData_new_Ad = new HistoryData();
                        historyData_new_Ad.setProduct(detailProduct);
                        historyData_new_Ad.setOrganization(detailOrganization);
                        historyData_new_Ad.setPeriod(period);
                        historyData_new_Ad.setBizData(bizData_historyAd);
                        historyData_new_Ad.setValue(value);

                        listHistoryData_new.add(historyData_new_Ad);
                        // 新建历史调整数据 end

                    } else {
                        // 更新历史数据 begin
                        HistoryData historyData_upd = historyData_inDB;
                        historyData_upd.setValue(value);
                        listHistoryData_upd.add(historyData_upd);
                        // 更新历史数据 end

                        // 更新历史调整数据 begin
                        HistoryData historyData_upd_Ad = daoHistoryData.getHistoryData(detailProduct.getId(), detailOrganization.getId(), period,
                                bizData_historyAd.getId());
                        if (historyData_upd_Ad == null) {
                            // 新建历史调整数据 begin
                            HistoryData historyData_new_Ad = new HistoryData();
                            historyData_new_Ad.setProduct(detailProduct);
                            historyData_new_Ad.setOrganization(detailOrganization);
                            historyData_new_Ad.setPeriod(period);
                            historyData_new_Ad.setBizData(bizData_historyAd);
                            historyData_new_Ad.setValue(value);

                            listHistoryData_new.add(historyData_new_Ad);
                            // 新建历史调整数据 begin
                        } else {
                            // 更新历史调整数据 begin
                            historyData_upd_Ad.setValue(value);
                            listHistoryData_upd.add(historyData_upd_Ad);
                            // 更新历史调整数据 end

                            // 删除历史调整原因类数据 begin
                            if (!(listBizDataId_HistoryAdR.isEmpty())) {
                                List<HistoryData> listHistoryData_inDB_AdR = daoHistoryData.getHistoryDatas(detailProduct.getId(), detailOrganization.getId(),
                                        period, listBizDataId_HistoryAdR);
                                if (listHistoryData_inDB_AdR != null) {
                                    listHistoryData_del.addAll(listHistoryData_inDB_AdR);
                                }
                            }
                            // 删除历史调整原因类数据 end
                        }
                        // 更新历史调整数据 end
                    }
                }

                if (!(listHistoryData_new.isEmpty())) {
                    for (int j = 0; j < listHistoryData_new.size(); j++) {
                        daoHistoryData.save(listHistoryData_new.get(j));
                        dealCount++;
                    }
                }

                if (!(listHistoryData_upd.isEmpty())) {
                    for (int j = 0; j < listHistoryData_upd.size(); j++) {
                        daoHistoryData.update(listHistoryData_upd.get(j));
                        dealCount++;
                    }
                }

                if (!(listHistoryData_del.isEmpty())) {
                    for (int j = 0; j < listHistoryData_del.size(); j++) {
                        daoHistoryData.delete(listHistoryData_del.get(j));
                        dealCount++;
                    }
                }

                trsa.commit();
            }
            // 每行一个事务处理一次 end
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }
        logger.info("历史数据导入结束，共处理[" + _listABUiRowData4save.size() + "]行数据，更新["+dealCount+"]条记录");

    }

    /**
     * 保存从接口读来的销售重量数据,根据BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_SI来定义主数据。
     * 
     * @param _bUnitGroup
     * @param _list4ABImHistoryData
     * @return
     * @throws Exception
     */
    public List<ABImHistoryData> saveHistoryDatas4InterfaceUI4InvoceWeight(BUnitGroup _bUnitGroup, List<ABImHistoryData> _list4ABImHistoryData)
            throws Exception {
        BBizData _bizDatahistory = new BizDataService().getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_SI);

        return saveHistoryDatas4ImportUI(_bizDatahistory, _bUnitGroup, _list4ABImHistoryData);
    }

    /**
     * 保存从接口读来的销售收入数据,根据BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_SI来定义主数据。
     * 
     * @param _bUnitGroup
     * @param _list4ABImHistoryData
     * @return
     * @throws Exception
     */
    public List<ABImHistoryData> saveHistoryDatas4InterfaceUI4InvocePrice(BUnitGroup _bUnitGroup, List<ABImHistoryData> _list4ABImHistoryData) throws Exception {
        BBizData _bizDatahistory = new BizDataService().getBizDataByCode(BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_HISTORY_GI);

        return saveHistoryDatas4ImportUI(_bizDatahistory, _bUnitGroup, _list4ABImHistoryData);
    }

    public List<ABImHistoryData> saveHistoryDatas4ImportUI(BBizData _bizDataHistory, BUnitGroup _bUnitGroup, List<ABImHistoryData> _list4ABImHistoryData)
            throws Exception {
        // 检查服务器状态是否可以提供服务
        ServerEnvironment.getInstance().checkSystemStatus();
        
        return historyImportService.saveHistoryDatas4ImportUI(_bizDataHistory, _bUnitGroup, _list4ABImHistoryData);
    }

    /**
     * _bHistoryAdjsutLog4save 中的 HistoryAdjustLogAdjustItems
     * 起数据更新作用，_listABUiRowData4save 的作用是提供分解用的明细ProOrgId 实际上也可以用
     * _listABUiRowData4save 中的数据来起更新作用，但 _bHistoryAdjsutLog4save 中的
     * HistoryAdjustLogAdjustItems 是个半成品，可以直接用
     * 
     * @param _listABUiRowData4save
     * @param _bHistoryAdjsutLog4save
     * @throws Exception
     */
    public void saveHistoryDatas4AdjustUI(List<ABUiRowData> _listABUiRowData4save, BHistoryAdjustLog _bHistoryAdjsutLog4save) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_listABUiRowData4save == null || _listABUiRowData4save.isEmpty()) {
            return;
        }

        if (_bHistoryAdjsutLog4save == null) {
            return;
        }

        HistoryAdjustLogBDConvertor historyAdjustLogBDConvertor = new HistoryAdjustLogBDConvertor();
        HistoryAdjustLog historyAdjustLog_new = historyAdjustLogBDConvertor.btod(_bHistoryAdjsutLog4save, true, true);

        // 把ppcoocb对应的明细ProOrgId用hashmap存放起来以便后面使用 begin
        HashMap<String, String> hmap_ppcoocb_detailProOrgIdStr = new HashMap<String, String>();
        for (int i = 0; i < _listABUiRowData4save.size(); i++) {
            ABUiRowData abUiRowData = _listABUiRowData4save.get(i);

            // 拼出明细范围字符串 begin
            String detailProOrgIdStr = UtilProOrg.getIdsScopeStr4BProOrgs(abUiRowData);
            // 拼出明细范围字符串 end

            String strKey4ppcoocb = UtilStrKey.getStrKey4PPcOOcB(abUiRowData.getProduct(), abUiRowData.getProductCharacter(), abUiRowData.getOrganization(),
                    abUiRowData.getOrganizationCharacter(), abUiRowData.getBizData());
            hmap_ppcoocb_detailProOrgIdStr.put(strKey4ppcoocb, detailProOrgIdStr);
        }
        // 把ppcoocb对应的明细ProOrgId用hashmap存放起来以便后面使用 end

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoHistoryData daoHistoryData = new DaoHistoryData(session);

            /**
             * 用于存放 historyData_Ad
             */
            HashMap<String, List<HistoryData>> hmap_ppcoocpb_list4HistoryData4Ad = new HashMap<String, List<HistoryData>>();
            HashMap<String, Double> hmap_ppcoocpb_sum4HistoryData4Ad = new HashMap<String, Double>();
            HashMap<String, Long> hmap_popb_incrementValue4HisrotyData4Ad = new HashMap<String, Long>();

            List<HistoryData> listHistoryData_AdR_new = new ArrayList<HistoryData>();
            List<HistoryData> listHistoryData_AdR_upd = new ArrayList<HistoryData>();

            Iterator<HistoryAdjustLogAdjustItem> itr_HistoryAdjustLogAdjustItems = historyAdjustLog_new.getHistoryAdjustLogAdjustItems().iterator();
            while (itr_HistoryAdjustLogAdjustItems.hasNext()) {
                HistoryAdjustLogAdjustItem historyAdjustLogAdjustItem = itr_HistoryAdjustLogAdjustItems.next();

                String strKey4ppcoocb = UtilStrKey.getStrKey4PPcOOcB(historyAdjustLogAdjustItem.getProduct(), historyAdjustLogAdjustItem.getProductCharacter(),
                        historyAdjustLogAdjustItem.getOrganization(), historyAdjustLogAdjustItem.getOrganizationCharacter(),
                        historyAdjustLogAdjustItem.getBizData());

                // 做乐观的版本检查 begin
                String detailProOrgIdStr = hmap_ppcoocb_detailProOrgIdStr.get(strKey4ppcoocb);
                ASumData aSumData_inDB = daoHistoryData.getSumHistoryData(detailProOrgIdStr, historyAdjustLogAdjustItem.getPeriod(), historyAdjustLogAdjustItem
                        .getBizData().getId());
                if (aSumData_inDB == null) {
                    if (historyAdjustLogAdjustItem.getOldValue() != 0) {
                        Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_DATA_HAS_BEEN_MODIFIED);
                        Exception ex = new Exception(cause);
                        throw ex;
                    }
                } else if (aSumData_inDB.getValue().longValue() != historyAdjustLogAdjustItem.getOldValue()) {
                    Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_DATA_HAS_BEEN_MODIFIED);
                    Exception ex = new Exception(cause);
                    throw ex;
                }
                // 做乐观的版本检查 end

                // 查询已有的 historyData_AdR begin
                List<HistoryData> listHistoryData_AdR = daoHistoryData.getHistoryDatas(detailProOrgIdStr, historyAdjustLogAdjustItem.getPeriod(),
                        historyAdjustLogAdjustItem.getBizData().getId());
                HashMap<String, HistoryData> hmap_pop_HistoryData_AdR = new HashMap<String, HistoryData>();
                if (listHistoryData_AdR != null) {
                    for (int i = 0; i < listHistoryData_AdR.size(); i++) {
                        HistoryData historyData_AdR = listHistoryData_AdR.get(i);
                        String strKey4pop = UtilStrKey.getStrKey4POPB(historyData_AdR.getProduct(), historyData_AdR.getOrganization(),
                                historyData_AdR.getPeriod(), null);
                        hmap_pop_HistoryData_AdR.put(strKey4pop, historyData_AdR);
                    }
                }
                // 查询已有的 historyData_AdR end

                // 获得相应的 historyData_Ad 以进行分解 begin
                BizData bizData_historyAd = ((BizDataDefItemHistoryAdR) (historyAdjustLogAdjustItem.getBizData().getBizDataDefItems().iterator().next()))
                        .getHistoryAdBizData();
                String strKey4ppcoocpb = UtilStrKey.getStrKey4PPcOOcPB(historyAdjustLogAdjustItem.getProduct(),
                        historyAdjustLogAdjustItem.getProductCharacter(), historyAdjustLogAdjustItem.getOrganization(),
                        historyAdjustLogAdjustItem.getOrganizationCharacter(), historyAdjustLogAdjustItem.getPeriod(), bizData_historyAd);

                List<HistoryData> listHistoryData_Ad = hmap_ppcoocpb_list4HistoryData4Ad.get(strKey4ppcoocpb);
                if (listHistoryData_Ad == null) {
                    // 没有查询过，从数据库查询
                    listHistoryData_Ad = daoHistoryData.getHistoryDatas(detailProOrgIdStr, historyAdjustLogAdjustItem.getPeriod(), bizData_historyAd.getId());
                    if (listHistoryData_Ad == null || listHistoryData_Ad.isEmpty()) {
                        Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_HISTORYDATA_ADJUSTUNEXIST);
                        Exception ex = new Exception(cause);
                        throw ex;
                    }
                    hmap_ppcoocpb_list4HistoryData4Ad.put(strKey4ppcoocpb, listHistoryData_Ad);

                    Double sum_Ad = 0d;
                    for (int i = 0; i < listHistoryData_Ad.size(); i = i + 1) {
                        sum_Ad = sum_Ad + listHistoryData_Ad.get(i).getValue();
                    }
                    hmap_ppcoocpb_sum4HistoryData4Ad.put(strKey4ppcoocpb, sum_Ad);
                }
                Double sum_Ad = hmap_ppcoocpb_sum4HistoryData4Ad.get(strKey4ppcoocpb);
                // 获得相应的 historyData_Ad 以进行分解 end

                // 分解: 创建和更新historyData_AdR，并收集对 historyData_Ad
                // 的增量以便后面更新historyData_Ad begin
                int sign = 1; // 这个符号值,用于使 totalIncrementValue
                              // distributedIncrementValue incrementValue
                              // 为正值,分解时分的恰好
                Long totalIncrementValue = historyAdjustLogAdjustItem.getNewValue() - historyAdjustLogAdjustItem.getOldValue();
                if (totalIncrementValue < 0) {
                    sign = -1;
                    totalIncrementValue = 0 - totalIncrementValue;
                }

                Long distributedIncrementValue = 0L;
                for (int i = 0; i < listHistoryData_Ad.size(); i++) {
                    // 这里的分解是以
                    // HistoryData_Ad为核心，有HistoryData_Ad才会有HistoryData_AdR的量
                    HistoryData historyData_Ad = listHistoryData_Ad.get(i);

                    Long incrementValue = 0L;

                    if (sum_Ad.longValue() == 0) {
                        // 均分
                        incrementValue = Math.round(totalIncrementValue * 1.0 / listHistoryData_Ad.size());
                    } else {
                        incrementValue = Math.round(totalIncrementValue * (historyData_Ad.getValue() * 1.0 / sum_Ad));
                    }
                    distributedIncrementValue = distributedIncrementValue + incrementValue;

                    // 保证分净且不超出 begin
                    if (distributedIncrementValue > totalIncrementValue) {
                        incrementValue = incrementValue - (distributedIncrementValue - totalIncrementValue);
                        distributedIncrementValue = totalIncrementValue;
                    }

                    if (i == (listHistoryData_Ad.size() - 1)) {
                        // 最后一个
                        if (distributedIncrementValue < totalIncrementValue) {
                            incrementValue = incrementValue + (totalIncrementValue - distributedIncrementValue);
                            distributedIncrementValue = totalIncrementValue;
                        }
                    }
                    // 保证分净且不超出 end

                    String strKey4pop = UtilStrKey.getStrKey4POPB(historyData_Ad.getProduct(), historyData_Ad.getOrganization(), historyData_Ad.getPeriod(),
                            null);
                    HistoryData historyData_AdR = hmap_pop_HistoryData_AdR.get(strKey4pop);
                    if (historyData_AdR == null) {
                        // 新建
                        historyData_AdR = new HistoryData();
                        historyData_AdR.setProduct(historyData_Ad.getProduct());
                        historyData_AdR.setOrganization(historyData_Ad.getOrganization());
                        historyData_AdR.setPeriod(historyData_Ad.getPeriod());
                        historyData_AdR.setBizData(historyAdjustLogAdjustItem.getBizData());
                        historyData_AdR.setValue(sign * incrementValue);

                        listHistoryData_AdR_new.add(historyData_AdR);
                    } else {
                        // 更新
                        historyData_AdR.setValue(historyData_AdR.getValue() + sign * incrementValue);

                        listHistoryData_AdR_upd.add(historyData_AdR);
                    }

                    // 收集对historyData_Ad 的增量,这是因为不同的HistoryData_AdR的变化会对同一个
                    // historyData_Ad起作用 begin
                    String strKey4popb = UtilStrKey.getStrKey4POPB(historyData_Ad.getProduct(), historyData_Ad.getOrganization(), historyData_Ad.getPeriod(),
                            historyData_Ad.getBizData());
                    Long incremtnValue4HistoryData4Ad = hmap_popb_incrementValue4HisrotyData4Ad.get(strKey4popb);
                    if (incremtnValue4HistoryData4Ad == null) {
                        incremtnValue4HistoryData4Ad = sign * incrementValue;
                    } else {
                        incremtnValue4HistoryData4Ad = incremtnValue4HistoryData4Ad + sign * incrementValue;
                    }
                    hmap_popb_incrementValue4HisrotyData4Ad.put(strKey4popb, incremtnValue4HistoryData4Ad);
                    // 收集对historyData_Ad 的增量,这是因为不同的HistoryData_AdR的变化会对同一个
                    // historyData_Ad起作用 end

                }
                // 分解: 创建和更新historyData_AdR，并收集对 historyData_Ad
                // 的增量以便后面更新historyData_Ad end
            }

          //进行折合计算
            List<AmountHistoryCalContext> listCalContext = new LinkedList<AmountHistoryCalContext>();
            DaoBizData bizDataDao = new DaoBizData(session);
            
            // 持久化到数据库 begin
            // 更新 historyData_Ad begin
            if (hmap_ppcoocpb_list4HistoryData4Ad.values() != null && !(hmap_ppcoocpb_list4HistoryData4Ad.values().isEmpty())) {
                Iterator<List<HistoryData>> itr_hmap_ppcoocpb_list4HistoryData4Ad_values = hmap_ppcoocpb_list4HistoryData4Ad.values().iterator();
                while (itr_hmap_ppcoocpb_list4HistoryData4Ad_values.hasNext()) {
                    List<HistoryData> listHistoryData4Ad = itr_hmap_ppcoocpb_list4HistoryData4Ad_values.next();
                    if (listHistoryData4Ad != null && !(listHistoryData4Ad.isEmpty())) {
                        for (int i = 0; i < listHistoryData4Ad.size(); i = i + 1) {
                            HistoryData historyData_Ad_upd = listHistoryData4Ad.get(i);
                            String strKey4popb = UtilStrKey.getStrKey4POPB(historyData_Ad_upd.getProduct(), historyData_Ad_upd.getOrganization(),
                                    historyData_Ad_upd.getPeriod(), historyData_Ad_upd.getBizData());
                            Long incremtnValue4HistoryData4Ad = hmap_popb_incrementValue4HisrotyData4Ad.get(strKey4popb);
                            if (incremtnValue4HistoryData4Ad != null) {
                                // HistoryData_AdR的增加值是 HistoryData_Ad 的减少值:
                                // HistoryData = HistoryData_Ad + HistoryData_AdR
                                historyData_Ad_upd.setValue(historyData_Ad_upd.getValue() - incremtnValue4HistoryData4Ad);
                                daoHistoryData.update(historyData_Ad_upd);
                                
                                BizData amountBizDataHistoryAd =  bizDataDao.getBizDataByCode(historyData_Ad_upd.getBizData().getCode() 
                                																			+ BizConst.AMOUNT_BIZ_DATA_SUFFIX);
                                //加入到折合待计算
                                if(ServerEnvironment.getInstance().isSuitSupport() && amountBizDataHistoryAd != null){
                                	AmountHistoryCalContext calContext = new AmountHistoryCalContext();
                                	calContext.setPeriod(historyData_Ad_upd.getPeriod());
                					calContext.setOrgId(historyData_Ad_upd.getOrganization().getId());
                					calContext.setProId(historyData_Ad_upd.getProduct().getId());
                					calContext.setIsSuit(historyData_Ad_upd.getProduct().getIsSuit());
                					calContext.setOriValue(historyData_Ad_upd.getValue());
                					calContext.setBizDataId(amountBizDataHistoryAd.getId());
                					calContext.setAmountBizDataId(amountBizDataHistoryAd.getId());
                					calContext.setBizDataIdAd(null);
                					
                					listCalContext.add(calContext);
                                }
                                
                            }
                        }
                    }
                }
            }
            // 更新 historyData_Ad end

            for (HistoryData hisData : listHistoryData_AdR_new) {
                daoHistoryData.save(hisData);
                
                BizData amountBizDataHistoryAdR = bizDataDao.getBizDataByCode(hisData.getBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
                if(ServerEnvironment.getInstance().isSuitSupport() && amountBizDataHistoryAdR != null){
                	AmountHistoryCalContext calContext = new AmountHistoryCalContext();
                	calContext.setPeriod(hisData.getPeriod());
    				calContext.setOrgId(hisData.getOrganization().getId());
    				calContext.setProId(hisData.getProduct().getId());
    				calContext.setIsSuit(hisData.getProduct().getIsSuit());
    				calContext.setOriValue(hisData.getValue());
    				calContext.setBizDataId(amountBizDataHistoryAdR.getId());
    				calContext.setAmountBizDataId(amountBizDataHistoryAdR.getId());
    				calContext.setBizDataIdAd(null);
    				
    				listCalContext.add(calContext);
                }
                
            }

            for (HistoryData hisData : listHistoryData_AdR_upd ) {
                daoHistoryData.update(hisData);
                
                BizData amountBizDataHistoryAdR = bizDataDao.getBizDataByCode(hisData.getBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
                if(ServerEnvironment.getInstance().isSuitSupport() && amountBizDataHistoryAdR != null){
                	AmountHistoryCalContext calContext = new AmountHistoryCalContext();
                	calContext.setPeriod(hisData.getPeriod());
    				calContext.setOrgId(hisData.getOrganization().getId());
    				calContext.setProId(hisData.getProduct().getId());
    				calContext.setIsSuit(hisData.getProduct().getIsSuit());
    				calContext.setOriValue(hisData.getValue());
    				calContext.setBizDataId(amountBizDataHistoryAdR.getId());
    				calContext.setAmountBizDataId(amountBizDataHistoryAdR.getId());
    				calContext.setBizDataIdAd(null);
    				
    				listCalContext.add(calContext);
                }
            }

            // HistoryAdjustLog begin
            DaoHistoryAdjustLog daoHistoryAdjustLog = new DaoHistoryAdjustLog(session);
            DaoHistoryAdjustLogProOrg daoHistoryAdjustLogProOrg = new DaoHistoryAdjustLogProOrg(session);
            DaoHistoryAdjustLogAdjustItem daoHistoryAdjustLogAdjustItem = new DaoHistoryAdjustLogAdjustItem(session);

            daoHistoryAdjustLog.save(historyAdjustLog_new);

            if (historyAdjustLog_new.getHistoryAdjustLogProOrgs() != null && !(historyAdjustLog_new.getHistoryAdjustLogProOrgs().isEmpty())) {
                Iterator<HistoryAdjustLogProOrg> itr_HistoryAdjustLogProOrgs = historyAdjustLog_new.getHistoryAdjustLogProOrgs().iterator();
                while (itr_HistoryAdjustLogProOrgs.hasNext()) {
                    daoHistoryAdjustLogProOrg.save(itr_HistoryAdjustLogProOrgs.next());
                }
            }

            if (historyAdjustLog_new.getHistoryAdjustLogAdjustItems() != null && !(historyAdjustLog_new.getHistoryAdjustLogAdjustItems().isEmpty())) {
                itr_HistoryAdjustLogAdjustItems = historyAdjustLog_new.getHistoryAdjustLogAdjustItems().iterator();
                while (itr_HistoryAdjustLogAdjustItems.hasNext()) {
                    daoHistoryAdjustLogAdjustItem.save(itr_HistoryAdjustLogAdjustItems.next());
                }
            }
            // HistoryAdjustLog end
            trsa.commit();
            // 持久化到数据库 end
            
            amountHistoryService.caculateAmountData(listCalContext);

        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }
    }

    /**
     * 查询结果集统计信息
     * 
     * @param _sqlRestriction
     * @return
     * @throws Exception
     */
    public int getHistoryAdjustLogsStat(String _sqlRestriction) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        int rst = 0;

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoHistoryAdjustLog daoHistoryAdjustLog = new DaoHistoryAdjustLog(session);
            rst = daoHistoryAdjustLog.getHistoryAdjustLogsStat(_sqlRestriction);
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

        return rst;
    }

    /**
     * _pageIndex>0 时分页查询
     * 
     * @param _sqlRestriction
     * @param _blWithProOrgs
     * @param _blWithAdjustItems
     * @param _pageIndex
     * @param _pageSize
     * @return
     * @throws Exception
     */
    public List<BHistoryAdjustLog> getHistoryAdjustLogs(String _sqlRestriction, boolean _blWithProOrgs, boolean _blWithAdjustItems, int _pageIndex,
            int _pageSize) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        List<BHistoryAdjustLog> rstList = new ArrayList<BHistoryAdjustLog>();

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoHistoryAdjustLog daoHistoryAdjustLog = new DaoHistoryAdjustLog(session);
            List<HistoryAdjustLog> listHistoryAdjustLog_inDB = daoHistoryAdjustLog.getHistoryAdjustLogs(_sqlRestriction, _pageIndex, _pageSize);

            if (listHistoryAdjustLog_inDB != null && listHistoryAdjustLog_inDB.iterator() != null) {
                HistoryAdjustLogBDConvertor historyAdjustLogBDConvertor = new HistoryAdjustLogBDConvertor();
                for (int i = 0; i < listHistoryAdjustLog_inDB.size(); i++) {
                    rstList.add(historyAdjustLogBDConvertor.dtob(listHistoryAdjustLog_inDB.get(i), _blWithProOrgs, _blWithAdjustItems));
                }
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

        return rstList;

    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public BHistoryAdjustLog getHistoryAdjustLog(Long id) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        BHistoryAdjustLog log = null;
        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoHistoryAdjustLog daoHistoryAdjustLog = new DaoHistoryAdjustLog(session);
            HistoryAdjustLog historyAdjustLog_inDB = daoHistoryAdjustLog.getHistoryAdjustLog(id);
            HistoryAdjustLogBDConvertor historyAdjustLogBDConvertor = new HistoryAdjustLogBDConvertor();
            log = historyAdjustLogBDConvertor.dtob(historyAdjustLog_inDB, true, true);
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

        return log;

    }

    /**
     * 导入历史修正原因类。导入的值认为就是最终值，不管以前是否存在。
     * 
     * @param _bizDataHistory
     * @param _bizDataHistoryADR
     * @param _bUnitGroup
     * @param _comments
     * @param operatoruser
     * @param _list4ABImHistoryADRData
     * @return
     * @throws Exception
     */
    public List<ABImHistoryADRData> saveHistoryADRDatas4ImportUI(BBizData _bizDataHistoryADR, BUnitGroup _bUnitGroup, String _comments,
            BOperatorUser operatoruser, List<ABImHistoryADRData> _list4ABImHistoryADRData) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        List<ABImHistoryADRData> rstList = new ArrayList<ABImHistoryADRData>();
        HistoryAdjustLog historyAdjustLog = new HistoryAdjustLog();
        historyAdjustLog.setDescription(_comments);
        historyAdjustLog.setSubmitTime(new Date());
        historyAdjustLog.setCompilePeriod(ServerEnvironment.getInstance().getSysPeriod().getCompilePeriod());
        historyAdjustLog.setSubmitter(operatoruser.getUserName());
        historyAdjustLog.setHistoryAdjustLogProOrgs(new HashSet<HistoryAdjustLogProOrg>());
        historyAdjustLog.setHistoryAdjustLogAdjustItems(new HashSet<HistoryAdjustLogAdjustItem>());

        HashMap<String, List<HistoryData>> hmap_ppcoocpb_list4HistoryData4Ad = new HashMap<String, List<HistoryData>>();
        HashMap<String, Double> hmap_ppcoocpb_sum4HistoryData4Ad = new HashMap<String, Double>();
        HashMap<String, Double> hmap_popb_incrementValue4HisrotyData4Ad = new HashMap<String, Double>();

        List<HistoryData> listHistoryData_AdR_new = new ArrayList<HistoryData>();
        List<HistoryData> listHistoryData_AdR_upd = new ArrayList<HistoryData>();

        long start = System.currentTimeMillis();
        BizData historyadr;
        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
            historyAdjustLog.setOperatorUser((OperatorUser) operatorUserBDConvertor.btod(operatoruser));
            BizDataBDConvertor bizdataBDConvertor = new BizDataBDConvertor();
            historyadr = (BizData) bizdataBDConvertor.btod(_bizDataHistoryADR, true);

            DaoHistoryData daoHistoryData = new DaoHistoryData(session);

            DaoProduct daoProduct = new DaoProduct(session);
            DaoOrganization daoOrganization = new DaoOrganization(session);
            Product queryProduct = null;
            Organization queryOrganization = null;
            String importResult = "";
            int period;
            Long value;

            for (ABImHistoryADRData historyadr_ : _list4ABImHistoryADRData) {
                // 数据有效性验证，并把业务范围收集起来 begin
                queryProduct = daoProduct.getProductByCode(historyadr_.getproductCode());
                queryOrganization = daoOrganization.getOrganizationByCode(historyadr_.getorganizationCode());
                if (queryProduct == null) {
                    importResult = "Can not find  Product by the Code";
                    historyadr_.setimportResult(importResult);
                    rstList.add(historyadr_);
                    continue;
                }
                if (queryOrganization == null) {
                    importResult = "Can not find  Organization by the Code";
                    historyadr_.setimportResult(importResult);
                    rstList.add(historyadr_);
                    continue;
                }
                if (queryProduct.getUnitGroup() == null) {
                    importResult = "Detail Product has no UnitGroup";
                    historyadr_.setimportResult(importResult);
                    rstList.add(historyadr_);
                    continue;
                }
                if (queryProduct.getUnitGroup().getId().longValue() != _bUnitGroup.getId().longValue()) {
                    importResult = "Detail Product's UOM Group does not match with that of parameter";
                    historyadr_.setimportResult(importResult);
                    rstList.add(historyadr_);
                    continue;
                }
                period = historyadr_.getperiod();
                value = historyadr_.getvalue();
                if (period == SysConst.PERIOD_NULL) {
                    importResult = "There is no Period";
                    historyadr_.setimportResult(importResult);
                    rstList.add(historyadr_);
                    continue;
                }
                // 数据有效性验证，并把业务范围收集起来 end.
                Double old_value = 0d;

                String detailProOrgIdStr = UtilProOrg.getProOrgIds(queryProduct, queryOrganization);

                // 查询已有的 historyData_AdR begin
                List<HistoryData> listHistoryData_AdR = daoHistoryData.getHistoryDatas(detailProOrgIdStr, period, historyadr.getId());
                HashMap<String, HistoryData> hmap_pop_HistoryData_AdR = new HashMap<String, HistoryData>();
                if (listHistoryData_AdR != null) {
                    for (int i = 0; i < listHistoryData_AdR.size(); i++) {
                        HistoryData historyData_AdR = listHistoryData_AdR.get(i);
                        String strKey4pop = UtilStrKey.getStrKey4POPB(historyData_AdR.getProduct(), historyData_AdR.getOrganization(),
                                historyData_AdR.getPeriod(), null);
                        hmap_pop_HistoryData_AdR.put(strKey4pop, historyData_AdR);
                        old_value = old_value + historyData_AdR.getValue();
                    }
                }
                // 查询已有的 historyData_AdR end

                // 获得相应的 historyData_Ad 以进行分解 begin
                BizData bizData_historyAd = ((BizDataDefItemHistoryAdR) (historyadr.getBizDataDefItems().iterator().next())).getHistoryAdBizData();
                String strKey4ppcoocpb = UtilStrKey.getStrKey4PPcOOcPB(queryProduct, null, queryOrganization, null, period, bizData_historyAd);

                List<HistoryData> listHistoryData_Ad = hmap_ppcoocpb_list4HistoryData4Ad.get(strKey4ppcoocpb);
                if (listHistoryData_Ad == null) {
                    // 没有查询过，从数据库查询
                    listHistoryData_Ad = daoHistoryData.getHistoryDatas(detailProOrgIdStr, period, bizData_historyAd.getId());
                    if (listHistoryData_Ad == null || listHistoryData_Ad.isEmpty()) {
                        importResult = "Can not find historydata ";
                        historyadr_.setimportResult(importResult);
                        rstList.add(historyadr_);
                        continue;
                    }
                    hmap_ppcoocpb_list4HistoryData4Ad.put(strKey4ppcoocpb, listHistoryData_Ad);

                    Double sum_Ad = 0d;
                    for (int i = 0; i < listHistoryData_Ad.size(); i = i + 1) {
                        sum_Ad = sum_Ad + listHistoryData_Ad.get(i).getValue();
                    }
                    hmap_ppcoocpb_sum4HistoryData4Ad.put(strKey4ppcoocpb, sum_Ad);
                }
                Double sum_Ad = hmap_ppcoocpb_sum4HistoryData4Ad.get(strKey4ppcoocpb);
                // 获得相应的 historyData_Ad 以进行分解 end

                // 分解: 创建和更新historyData_AdR，并收集对 historyData_Ad
                // 的增量以便后面更新historyData_Ad begin
                int sign = 1; // 这个符号值,用于使 totalIncrementValue
                              // distributedIncrementValue incrementValue
                              // 为正值,分解时分的恰好
                Double totalIncrementValue = value - old_value; // 这里认为是直接导入value，不管以前是什么值。
                if (totalIncrementValue < 0) {
                    sign = -1;
                    totalIncrementValue = 0 - totalIncrementValue;
                }

                double distributedIncrementValue = 0d;
                for (int i = 0; i < listHistoryData_Ad.size(); i++) {
                    // 这里的分解是以
                    // HistoryData_Ad为核心，有HistoryData_Ad才会有HistoryData_AdR的量
                    HistoryData historyData_Ad = listHistoryData_Ad.get(i);

                    Double incrementValue = 0d;

                    if (sum_Ad.longValue() == 0) {
                        // 均分
                        // incrementValue = Math.round( totalIncrementValue *
                        // 1.0/listHistoryData_Ad.size() );
                        // modify by zhangzy 20150413 修改为支持小数的形式
                        incrementValue = totalIncrementValue * 1.0 / listHistoryData_Ad.size();
                    } else {
                        // incrementValue = Math.round( totalIncrementValue *
                        // (historyData_Ad.getValue() * 1.0 / sum_Ad) );
                        // modify by zhangzy 20150413 修改为支持小数的形式
                        incrementValue = totalIncrementValue * (historyData_Ad.getValue() * 1.0 / sum_Ad);
                    }
                    distributedIncrementValue = distributedIncrementValue + incrementValue;

                    // 保证分净且不超出 begin
                    if (distributedIncrementValue > totalIncrementValue) {
                        incrementValue = incrementValue - (distributedIncrementValue - totalIncrementValue);
                        distributedIncrementValue = totalIncrementValue;
                    }

                    if (i == (listHistoryData_Ad.size() - 1)) {
                        // 最后一个
                        if (distributedIncrementValue < totalIncrementValue) {
                            incrementValue = incrementValue + (totalIncrementValue - distributedIncrementValue);
                            distributedIncrementValue = totalIncrementValue;
                        }
                    }
                    // 保证分净且不超出 end

                    String strKey4pop = UtilStrKey.getStrKey4POPB(historyData_Ad.getProduct(), historyData_Ad.getOrganization(), historyData_Ad.getPeriod(),
                            null);
                    HistoryData historyData_AdR = hmap_pop_HistoryData_AdR.get(strKey4pop);
                    if (historyData_AdR == null) {
                        // 新建
                        historyData_AdR = new HistoryData();
                        historyData_AdR.setProduct(historyData_Ad.getProduct());
                        historyData_AdR.setOrganization(historyData_Ad.getOrganization());
                        historyData_AdR.setPeriod(historyData_Ad.getPeriod());
                        historyData_AdR.setBizData(historyadr);
                        historyData_AdR.setValue(sign * incrementValue);

                        listHistoryData_AdR_new.add(historyData_AdR);
                    } else {
                        // 更新
                        historyData_AdR.setValue(historyData_AdR.getValue() + sign * incrementValue);// 更新也视为是覆盖

                        listHistoryData_AdR_upd.add(historyData_AdR);
                    }

                    // 收集对historyData_Ad 的增量,这是因为不同的HistoryData_AdR的变化会对同一个
                    // historyData_Ad起作用 begin
                    String strKey4popb = UtilStrKey.getStrKey4POPB(historyData_Ad.getProduct(), historyData_Ad.getOrganization(), historyData_Ad.getPeriod(),
                            historyData_Ad.getBizData());
                    Double incremtnValue4HistoryData4Ad = hmap_popb_incrementValue4HisrotyData4Ad.get(strKey4popb);
                    if (incremtnValue4HistoryData4Ad == null) {
                        incremtnValue4HistoryData4Ad = sign * incrementValue;
                    } else {
                        incremtnValue4HistoryData4Ad = incremtnValue4HistoryData4Ad + sign * incrementValue;
                    }
                    hmap_popb_incrementValue4HisrotyData4Ad.put(strKey4popb, incremtnValue4HistoryData4Ad);
                    // 收集对historyData_Ad 的增量,这是因为不同的HistoryData_AdR的变化会对同一个
                    // historyData_Ad起作用 end

                }
                // 更新历史数据和历史修正类 end.

                // 创建历史修正范围和历史修正详情 begin
                HistoryAdjustLogProOrg proorg_ = new HistoryAdjustLogProOrg();
                proorg_.setProduct(queryProduct);
                proorg_.setOrganization(queryOrganization);
                proorg_.setHistoryAdjustLog(historyAdjustLog);
                historyAdjustLog.getHistoryAdjustLogProOrgs().add(proorg_);
                HistoryAdjustLogAdjustItem item = new HistoryAdjustLogAdjustItem();
                item.setHistoryAdjustLog(historyAdjustLog);
                item.setBizData(historyadr);
                item.setDescription(historyadr_.getcomments());
                item.setPeriod(period);
                item.setNewValue(value);
                item.setOldValue(0);
                item.setComments("import by excel");
                item.setOrganization(queryOrganization);
                item.setProduct(queryProduct);
                historyAdjustLog.getHistoryAdjustLogAdjustItems().add(item);
                // 创建历史修正范围和历史修正详情 end.
                historyadr_.setimportResult("OK");
                rstList.add(historyadr_);
            }
            
            //进行折合计算
            List<AmountHistoryCalContext> listCalContext = null;
            BizData amountBizDataHistoryAdR = null;
            BizData amountBizDataHistoryAd = null;
            BizData bizData_historyAd = ((BizDataDefItemHistoryAdR) (historyadr.getBizDataDefItems().iterator().next())).getHistoryAdBizData();
            if(ServerEnvironment.getInstance().isSuitSupport()){
            	DaoBizData bizDataDao = new DaoBizData(session);
                amountBizDataHistoryAdR = bizDataDao.getBizDataByCode(historyadr.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
                amountBizDataHistoryAd =  bizDataDao.getBizDataByCode(bizData_historyAd.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
                listCalContext = new LinkedList<AmountHistoryCalContext>();
            }
            

            // 持久化到数据库 begin
            // 更新 historyData_Ad begin
            if (hmap_ppcoocpb_list4HistoryData4Ad.values() != null && !(hmap_ppcoocpb_list4HistoryData4Ad.values().isEmpty())) {
                Iterator<List<HistoryData>> itr_hmap_ppcoocpb_list4HistoryData4Ad_values = hmap_ppcoocpb_list4HistoryData4Ad.values().iterator();
                while (itr_hmap_ppcoocpb_list4HistoryData4Ad_values.hasNext()) {
                    List<HistoryData> listHistoryData4Ad = itr_hmap_ppcoocpb_list4HistoryData4Ad_values.next();
                    if (listHistoryData4Ad != null && !(listHistoryData4Ad.isEmpty())) {
                        for (int i = 0; i < listHistoryData4Ad.size(); i = i + 1) {
                            HistoryData historyData_Ad_upd = listHistoryData4Ad.get(i);
                            String strKey4popb = UtilStrKey.getStrKey4POPB(historyData_Ad_upd.getProduct(), historyData_Ad_upd.getOrganization(),
                                    historyData_Ad_upd.getPeriod(), historyData_Ad_upd.getBizData());
                            Double incremtnValue4HistoryData4Ad = hmap_popb_incrementValue4HisrotyData4Ad.get(strKey4popb);
                            if (incremtnValue4HistoryData4Ad != null) {
                                // HistoryData_AdR的增加值是 HistoryData_Ad 的减少值:
                                // HistoryData = HistoryData_Ad +
                                // HistoryData_AdR
                                historyData_Ad_upd.setValue(historyData_Ad_upd.getValue() - incremtnValue4HistoryData4Ad);
                                daoHistoryData.update(historyData_Ad_upd);
                                
                                //加入到折合待计算
                                if(ServerEnvironment.getInstance().isSuitSupport()){
                                	AmountHistoryCalContext calContext = new AmountHistoryCalContext();
                                	calContext.setPeriod(historyData_Ad_upd.getPeriod());
                					calContext.setOrgId(historyData_Ad_upd.getOrganization().getId());
                					calContext.setProId(historyData_Ad_upd.getProduct().getId());
                					calContext.setIsSuit(historyData_Ad_upd.getProduct().getIsSuit());
                					calContext.setOriValue(historyData_Ad_upd.getValue());
                					calContext.setBizDataId(bizData_historyAd.getId());
                					calContext.setAmountBizDataId(amountBizDataHistoryAd.getId());
                					calContext.setBizDataIdAd(null);
                					
                					listCalContext.add(calContext);
                                }
                            }
                        }
                    }
                }
            }
            // 更新 historyData_Ad end

            for (HistoryData hisData : listHistoryData_AdR_new) {
                daoHistoryData.save(hisData);
                
                if(ServerEnvironment.getInstance().isSuitSupport()){
                	AmountHistoryCalContext calContext = new AmountHistoryCalContext();
	            	calContext.setPeriod(hisData.getPeriod());
					calContext.setOrgId(hisData.getOrganization().getId());
					calContext.setProId(hisData.getProduct().getId());
					calContext.setIsSuit(hisData.getProduct().getIsSuit());
					calContext.setOriValue(hisData.getValue());
					calContext.setBizDataId(historyadr.getId());
					calContext.setAmountBizDataId(amountBizDataHistoryAdR.getId());
					calContext.setBizDataIdAd(null);
					
					listCalContext.add(calContext);	
                }
            }

            for (HistoryData hisData : listHistoryData_AdR_upd) {
                daoHistoryData.update(hisData);
                
                if(ServerEnvironment.getInstance().isSuitSupport()){
	                AmountHistoryCalContext calContext = new AmountHistoryCalContext();
	            	calContext.setPeriod(hisData.getPeriod());
					calContext.setOrgId(hisData.getOrganization().getId());
					calContext.setProId(hisData.getProduct().getId());
					calContext.setIsSuit(hisData.getProduct().getIsSuit());
					calContext.setOriValue(hisData.getValue());
					calContext.setBizDataId(historyadr.getId());
					calContext.setAmountBizDataId(amountBizDataHistoryAdR.getId());
					calContext.setBizDataIdAd(null);
					
					listCalContext.add(calContext);
                }
            }

            // HistoryAdjustLog begin
            DaoHistoryAdjustLog daoHistoryAdjustLog = new DaoHistoryAdjustLog(session);
            DaoHistoryAdjustLogProOrg daoHistoryAdjustLogProOrg = new DaoHistoryAdjustLogProOrg(session);
            DaoHistoryAdjustLogAdjustItem daoHistoryAdjustLogAdjustItem = new DaoHistoryAdjustLogAdjustItem(session);

            daoHistoryAdjustLog.save(historyAdjustLog);

            if (historyAdjustLog.getHistoryAdjustLogProOrgs() != null && !(historyAdjustLog.getHistoryAdjustLogProOrgs().isEmpty())) {
                Iterator<HistoryAdjustLogProOrg> itr_HistoryAdjustLogProOrgs = historyAdjustLog.getHistoryAdjustLogProOrgs().iterator();
                while (itr_HistoryAdjustLogProOrgs.hasNext()) {
                    daoHistoryAdjustLogProOrg.save(itr_HistoryAdjustLogProOrgs.next());
                }
            }

            if (historyAdjustLog.getHistoryAdjustLogAdjustItems() != null && !(historyAdjustLog.getHistoryAdjustLogAdjustItems().isEmpty())) {
                for (HistoryAdjustLogAdjustItem item : historyAdjustLog.getHistoryAdjustLogAdjustItems()) {
                    daoHistoryAdjustLogAdjustItem.save(item);
                }
            }
            // HistoryAdjustLog end

            trsa.commit();
            // 持久化到数据库 end
            
            amountHistoryService.caculateAmountData(listCalContext);
            
        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();

        } finally {
            session.close();
        }

        long end = System.currentTimeMillis();
        logger.info("事件数据导入完成，耗时["+ (end-start) +"]ms");

        return rstList;
    }

}

