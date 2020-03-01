/**
 * 
 */

package dmdd.dmddjava.service.dimensionservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilBizData;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItem;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAddFc;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAddHis;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemAvgHis;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcHand;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemForecastAssessment;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAd;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAdR;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemKpi;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemMoney;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemMoneyComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemPeriodVersion;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeFc;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemTimeHis;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizDataDefItem;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastAssessment;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;

/**
 * @author liuzhen
 * 
 */
public class BizDataService {


    public BBizData newBizData(BBizData _bBizData4new) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bBizData4new == null) {
            Exception ex = new Exception("The object to new is a null object");
            throw ex;
        }

        BBizData bBizData_rst = null;

        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();

        BizData bizData_new = bizDataBDConvertor.btod(_bBizData4new, true);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoBizData daoBizData = new DaoBizData(session);
            DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem(session);
            
            // 新增业务数据
            daoBizData.save(bizData_new);
            if (bizData_new.getBizDataDefItems() != null && !(bizData_new.getBizDataDefItems().isEmpty())) {
                Iterator<BizDataDefItem> itrbizDataDefitems = bizData_new.getBizDataDefItems().iterator();
                while (itrbizDataDefitems.hasNext()) {
                    daoBizDataDefItem.save(itrbizDataDefitems.next());
                }
            }
            
            // 增加折合业务数据
            String isSuitSupport = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_IS_SUIT_SUPPORT);
            if ("true".equals(isSuitSupport) && ! bizData_new.getCode().endsWith(BizConst.AMOUNT_BIZ_DATA_SUFFIX) && 
            	  UtilBizData.matchSuitSupport(_bBizData4new.getIsSuitSupport()) ) {
                BizData amountBizData = bizDataBDConvertor.btod(_bBizData4new, true);
                // 业务数据表新增折合业务数据
                amountBizData.setCode(amountBizData.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX);
                amountBizData.setName(amountBizData.getName() + BizConst.AMOUNT_BIZ_DATA_NAME_SUFFIX);
                if (StringUtils.isNotEmpty(amountBizData.getDescription())) {
                    amountBizData.setDescription(amountBizData.getDescription() + BizConst.AMOUNT_BIZ_DATA_DESC_SUFFIX);
                }
                //数据项定义也转换为对应的折合业务数据
                convertDefItem2Amout(amountBizData);
                
                daoBizData.save(amountBizData);
                
                // 业务数据定义表新增折合业务数据定义
                if (CollectionUtils.isNotEmpty(amountBizData.getBizDataDefItems())) {
                    for (BizDataDefItem defItem : amountBizData.getBizDataDefItems()) {
                        daoBizDataDefItem.save(defItem);
                    }
                }
            }

            trsa.commit();
            bBizData_rst = bizDataBDConvertor.dtob(bizData_new, true);

        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return bBizData_rst;

    }
    
    /**
     * 新增业务折合数据，且定义明细项也为折合项
     * @param _bBizData4new
     * @return
     * @throws Exception
     */
    public BBizData newAmountBizData(BBizData _bBizData4new) throws Exception {
    	// 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bBizData4new == null) {
            Exception ex = new Exception("The object to new is a null object");
            throw ex;
        }

        BBizData bBizData_rst = null;

        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();

        BizData bizData_new = bizDataBDConvertor.btod(_bBizData4new, true);

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoBizData daoBizData = new DaoBizData(session);
            DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem(session);
            //数据项定义也转换为对应的折合业务数据
            convertDefItem2Amout(bizData_new);
            
            // 新增业务数据
            daoBizData.save(bizData_new);
            if (bizData_new.getBizDataDefItems() != null && !(bizData_new.getBizDataDefItems().isEmpty())) {
                Iterator<BizDataDefItem> itrbizDataDefitems = bizData_new.getBizDataDefItems().iterator();
                while (itrbizDataDefitems.hasNext()) {
                    daoBizDataDefItem.save(itrbizDataDefitems.next());
                }
            }
            
            trsa.commit();
            bBizData_rst = bizDataBDConvertor.dtob(bizData_new, true);

        } catch (Exception ex) {
            if (trsa != null) {
                trsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session.close();
        }

        return bBizData_rst;
    }

    /**
     * todo:需要检查依赖于本业务数据的相关业务数据，并更新其实际数据
     * 
     * @param _bBizData4upd
     * @return
     * @throws Exception
     */
    public BBizData updBizData(BBizData _bBizData4upd) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bBizData4upd == null) {
            Exception ex = new Exception("The object to update is a null object");
            throw ex;
        }

        String strKey4bizDataDefItem = null;
        // 数据库中bizDataDefItems 情况 begin
        HashMap<String, BizDataDefItem> hmapbizDataDefitem_inDB = new HashMap<String, BizDataDefItem>();

        Session session_query = HibernateSessionFactory.getSession();
        Transaction trsa_query = null;
        try {
            trsa_query = session_query.beginTransaction();
            DaoBizData daoBizData_query = new DaoBizData(session_query);
            BizData bizData_InDB = daoBizData_query.getBizDataById(_bBizData4upd.getId());
            if (bizData_InDB == null) {
                Exception ex_unFound = new Exception("The object is not in Database! Can not be update!");
                throw ex_unFound;
            }

            if (bizData_InDB.getBizDataDefItems() != null && !(bizData_InDB.getBizDataDefItems().isEmpty())) {
                Iterator<BizDataDefItem> itrbizDataDefitems_inDB = bizData_InDB.getBizDataDefItems().iterator();
                while (itrbizDataDefitems_inDB.hasNext()) {
                    BizDataDefItem bizDataDefItem = itrbizDataDefitems_inDB.next();
                    strKey4bizDataDefItem = "" + bizDataDefItem.getId();

                    hmapbizDataDefitem_inDB.put(strKey4bizDataDefItem, bizDataDefItem);
                }
            }

            trsa_query.commit();
        } catch (Exception ex) {
            if (trsa_query != null) {
                trsa_query.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            session_query.close();
        }
        // 数据库中bizDataDefItems 情况 end

        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
        BizData bizData_upd = bizDataBDConvertor.btod(_bBizData4upd, true);

        // 参数中bizDataDefItems 情况 begin
        HashMap<String, BizDataDefItem> hmapbizDataDefitem_param = new HashMap<String, BizDataDefItem>();

        if (bizData_upd.getBizDataDefItems() != null && !(bizData_upd.getBizDataDefItems().isEmpty())) {
            Iterator<BizDataDefItem> itrbizDataDefitems_param = bizData_upd.getBizDataDefItems().iterator();
            while (itrbizDataDefitems_param.hasNext()) {
                BizDataDefItem bizDataDefItem = itrbizDataDefitems_param.next();
                strKey4bizDataDefItem = "" + bizDataDefItem.getId();

                hmapbizDataDefitem_param.put(strKey4bizDataDefItem, bizDataDefItem);
            }
        }
        // 参数中bizDataDefItems 情况 end

        // 比较param和inDB获得 BizDataDefItem 增删改情况 begin
        List<BizDataDefItem> toDelBizDataDefItemList = new ArrayList<BizDataDefItem>();
        List<BizDataDefItem> toAddBizDataDefItemList = new ArrayList<BizDataDefItem>();
        List<BizDataDefItem> toUpdBizDataDefItemList = new ArrayList<BizDataDefItem>();

        boolean bl4changed4FcComb = false;

        // param - inDB = add begin
        if (bizData_upd.getBizDataDefItems() != null && !(bizData_upd.getBizDataDefItems().isEmpty())) {
            Iterator<BizDataDefItem> itrbizDataDefitems_param = bizData_upd.getBizDataDefItems().iterator();
            while (itrbizDataDefitems_param.hasNext()) {
                BizDataDefItem bizDataDefItem = itrbizDataDefitems_param.next();
                strKey4bizDataDefItem = "" + bizDataDefItem.getId();

                if (hmapbizDataDefitem_inDB.get(strKey4bizDataDefItem) == null) {
                    toAddBizDataDefItemList.add(bizDataDefItem);
                    bl4changed4FcComb = true;
                }
            }
        }
        // param - inDB = add end

        // inDB - param = del begin
        if (hmapbizDataDefitem_inDB.values() != null && !(hmapbizDataDefitem_inDB.values().isEmpty())) {
            Iterator<BizDataDefItem> itrbizDataDefitems_inDB = hmapbizDataDefitem_inDB.values().iterator();
            while (itrbizDataDefitems_inDB.hasNext()) {
                BizDataDefItem bizDataDefItem = itrbizDataDefitems_inDB.next();
                strKey4bizDataDefItem = "" + bizDataDefItem.getId();

                if (hmapbizDataDefitem_param.get(strKey4bizDataDefItem) == null) {
                    toDelBizDataDefItemList.add(bizDataDefItem);
                    bl4changed4FcComb = true;
                } else {
                    BizDataDefItem bizDataDefItem_param = hmapbizDataDefitem_param.get(strKey4bizDataDefItem);
                    toUpdBizDataDefItemList.add(bizDataDefItem_param);

                    if (bl4changed4FcComb == false && _bBizData4upd.getType() == BizConst.BIZDATA_TYPE_FCCOMB) {
                        // 检查定义项的系数是否变化
                        if (Math.abs(((BizDataDefItemFcComb) bizDataDefItem).getCoefficient().doubleValue()
                                - ((BizDataDefItemFcComb) bizDataDefItem_param).getCoefficient().doubleValue()) > 0.0000001) {
                            bl4changed4FcComb = true;
                        }

                    }
                }
            }
        }
        // inDB - param = del end

        // 比较param和inDB获得 BizDataDefItem 增删改情况 end

        // 持久化到数据库 begin
        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;

        try {
            trsa = session.beginTransaction();
            DaoBizData daoBizData = new DaoBizData(session);
            daoBizData.update(bizData_upd);

            DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem(session);
            for (int i = 0; i < toDelBizDataDefItemList.size(); i++) {
                daoBizDataDefItem.delete(toDelBizDataDefItemList.get(i));
            }
            for (int i = 0; i < toUpdBizDataDefItemList.size(); i++) {
                daoBizDataDefItem.update(toUpdBizDataDefItemList.get(i));
            }
            for (int i = 0; i < toAddBizDataDefItemList.size(); i++) {
                daoBizDataDefItem.save(toAddBizDataDefItemList.get(i));
            }

            // 如系统支持套装， 同步修改折合业务数据的数据定义 
            boolean isSuitSupport = ServerEnvironment.getInstance().isSuitSupport();
            if ( isSuitSupport && ! bizData_upd.getCode().endsWith(BizConst.AMOUNT_BIZ_DATA_SUFFIX) &&
            	 UtilBizData.matchSuitSupport(_bBizData4upd.getIsSuitSupport()) ) {
            	//更新对应的折合数据数据定义
            	updateAmountBizData(session,_bBizData4upd);
            }
            // 如果是组合预测数据，可能会影响到最终预测数据 begin
            // 如果一个预测类别覆盖的范围过大，且该预测范围的最终预测数据的组合预测数据被更改，下述过程可能会耗用大量时间
            // 2010.12.12 by liuzhen, 根据需求：当组合预测数据被当前预测类别引用的时候，不允许修改定义的比例 end
            if (bizData_upd.getType() == BizConst.BIZDATA_TYPE_FCCOMB && bl4changed4FcComb == true) {
                DaoForecastInst daoForecastInst = new DaoForecastInst(session);

                String sqlRestriction = " ( finalFcBizDataId = " + bizData_upd.getId() + " ) ";
                sqlRestriction = sqlRestriction + " and ( isValid = " + BizConst.GLOBAL_YESNO_YES + " ) ";
                if (daoForecastInst.isExistForecastInst(sqlRestriction) == true) {
                    Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_FCCOMB_ASFCFINAL);
                    Exception ex = new Exception(cause);
                    throw ex;
                }
            }
            // 如果是组合预测数据，可能会影响到最终预测数据 end

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
        // 持久化到数据库 end

        BBizData bBizData_rst = this.getBizDataById(_bBizData4upd.getId(), true);
        return bBizData_rst;
    }
    
    /**
     * 更新折合业务数据定义及其明细项
     * @throws Exception
     */
    private void updateAmountBizData(Session session,BBizData bBizData4upd) throws Exception{
    	
    	DaoBizData daoBizData = new DaoBizData(session);
    	DaoBizDataDefItem daoBizDataDefItem = new DaoBizDataDefItem(session);
    	
    	// 获取折合业务数据code
        String amountBizDataCode = bBizData4upd.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
        // 获取折合业务数据
        BizData amountBizData = daoBizData.getBizDataByCode(amountBizDataCode);
        
        //从原始业务数据定义中，COPY出一份数据项定义
        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
        BizData cpBizData = bizDataBDConvertor.btod(bBizData4upd, true);
        
        if (amountBizData != null) {
        	
        	amountBizData.setIsValid(bBizData4upd.getIsValid());
        	daoBizData.update(amountBizData);
        	
            // 删除原有数据项定义
            daoBizDataDefItem.deleteByBizDataId(amountBizData.getId());
            
            if( !CollectionUtils.isEmpty(cpBizData.getBizDataDefItems()) ){
	            for (BizDataDefItem defItem : cpBizData.getBizDataDefItems()) {
	            	defItem.setBizData(amountBizData);
	            	//数据项定义也转换为对应的折合业务数据
	            	defItem = convertItem2Amount(defItem, daoBizData);
	                
	                daoBizDataDefItem.save(defItem);
	            }
            }
        }
    }

    /**
     * todo：需要检查是否能够删除；删除的话，注意清理当前期数据
     * 
     * @param _bBizData4del
     * @throws Exception
     */
    public boolean delBizData(BBizData _bBizData4del) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bBizData4del == null) {
            Exception ex = new Exception("The object to delete is null! Do nothing!");
            throw ex;
        }

        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
        BizData bBizData_del = (BizData) bizDataBDConvertor.btod(_bBizData4del);// 基本信息

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoBizData daoBizData = new DaoBizData(session);

            // bizDataDefItem 对 bizData 级联删除
            daoBizData.delete(bBizData_del);
            String isSuitSupport = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_IS_SUIT_SUPPORT);
            if ("true".equals(isSuitSupport)) {
                // 删除对应折合业务数据
                String amountBizDataCode = bBizData_del.getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
                daoBizData.deleteByCode(amountBizDataCode);
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

        return true;
    }

    /**
     * 查询结果集统计信息
     * 
     * @param _sqlRestriction
     * @return
     * @throws Exception
     */
    public int getBizDatasStat(String _sqlRestriction) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        int rst = 0;

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoBizData daoBizData = new DaoBizData(session);
            rst = daoBizData.getBizDatasStat(_sqlRestriction);
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
     * @param _blWithBizDataDefItems
     * @param _pageIndex
     * @param _pageSize
     * @return
     * @throws Exception
     */
    public List<BBizData> getBizDatas(String _sqlRestriction, boolean _blWithBizDataDefItems, int _pageIndex, int _pageSize) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        List<BBizData> rstList = new ArrayList<BBizData>();

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();
            DaoBizData daoBizData = new DaoBizData(session);
            List<BizData> listBizData_inDB = daoBizData.getBizDatas(_sqlRestriction, _pageIndex, _pageSize);

            if (listBizData_inDB != null && !(listBizData_inDB.isEmpty())) {
                BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
                for (int i = 0; i < listBizData_inDB.size(); i++) {
                    rstList.add(bizDataBDConvertor.dtob(listBizData_inDB.get(i), _blWithBizDataDefItems));
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

    private BBizData getBizDataById(Long _id, boolean _blWithBizDataDefItems) throws Exception {
        BBizData rstBizData = null;
        Session querySession = HibernateSessionFactory.getSession();
        Transaction queryTrsa = null;
        try {
            queryTrsa = querySession.beginTransaction();
            DaoBizData queryDaoBizData = new DaoBizData(querySession);
            BizData bizData_inDB = queryDaoBizData.getBizDataById(_id);

            if (bizData_inDB != null) {
                BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
                rstBizData = bizDataBDConvertor.dtob(bizData_inDB, _blWithBizDataDefItems);
            }

            queryTrsa.commit();
        } catch (Exception ex) {
            if (queryTrsa != null) {
                queryTrsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            querySession.close();
        }

        return rstBizData;

    }

    public BBizData getBizDataByCode(String code) throws Exception {
        BBizData rstBizData = null;
        Session querySession = HibernateSessionFactory.getSession();
        Transaction queryTrsa = null;
        try {
            queryTrsa = querySession.beginTransaction();
            DaoBizData queryDaoBizData = new DaoBizData(querySession);
            BizData bizData_inDB = queryDaoBizData.getBizDataByCode(code);

            if (bizData_inDB != null) {
                BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
                rstBizData = bizDataBDConvertor.dtob(bizData_inDB, true);
            }

            queryTrsa.commit();
        } catch (Exception ex) {
            if (queryTrsa != null) {
                queryTrsa.rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            querySession.close();
        }

        return rstBizData;
    }

    public boolean delBizDataDefItemForecastAssessment(BBizData _bBizData4del) throws Exception {
        // 检查服务器状态是否可以提供服务 begin
        ServerEnvironment.getInstance().checkSystemStatus();
        // 检查服务器状态是否可以提供服务 end

        if (_bBizData4del == null) {
            Exception ex = new Exception("The object to delete is null! Do nothing!");
            throw ex;
        }

        BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();
        BizData bBizData_del = (BizData) bizDataBDConvertor.btod(_bBizData4del);// 基本信息

        Session session = HibernateSessionFactory.getSession();
        Transaction trsa = null;
        try {
            trsa = session.beginTransaction();

            // 删除预测考核数据
            DaoForecastAssessment dao_fa = new DaoForecastAssessment(session);
            dao_fa.delForecastAssessment(bBizData_del.getId());

            DaoBizData daoBizData = new DaoBizData(session);

            // bizDataDefItem 对 bizData 级联删除
            daoBizData.delete(bBizData_del);
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

        return true;
    }
    
    /**
     * 对于折合的业务数据,转换定义项为其对应的折合数据
     * @param bizdate
     * @return
     */
    public synchronized BizData convertDefItem2Amout(BizData amountBizData) {
    	
    	Session session = HibernateSessionFactory.getSession();
        DaoBizData bizDataDao = new DaoBizData(session);
        
    	// 业务数据定义表新增折合业务数据定义
        if (CollectionUtils.isNotEmpty(amountBizData.getBizDataDefItems())) {
            for (BizDataDefItem bizDataDefitem : amountBizData.getBizDataDefItems()) {
            	bizDataDefitem = convertItem2Amount(bizDataDefitem,bizDataDao);
            	
            }// end for
           
        }
        
        return amountBizData;
    }
    
    public synchronized BizDataDefItem convertItem2Amount(BizDataDefItem bizDataDefitem,DaoBizData bizDataDao){
    	String amountBizCode;
    	BizData itemAmountBizData;
		if( bizDataDefitem instanceof BizDataDefItemFcComb ){
			BizDataDefItemFcComb itemComb = (BizDataDefItemFcComb)bizDataDefitem;
			amountBizCode = itemComb.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			itemComb.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemFcHand )
		{
			BizDataDefItemFcHand defItem = (BizDataDefItemFcHand)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemHistoryAd )
		{
			BizDataDefItemHistoryAd defItem = (BizDataDefItemHistoryAd)bizDataDefitem;
			amountBizCode = defItem.getHistoryBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setHistoryBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemHistoryAdR )
		{
			BizDataDefItemHistoryAdR defItem = (BizDataDefItemHistoryAdR)bizDataDefitem;
			amountBizCode = defItem.getHistoryAdBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setHistoryAdBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemTimeHis )
		{
			BizDataDefItemTimeHis defItem = (BizDataDefItemTimeHis)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemTimeFc )
		{
			BizDataDefItemTimeFc defItem = (BizDataDefItemTimeFc)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		} else if( bizDataDefitem instanceof BizDataDefItemKpi )
		{
			BizDataDefItemKpi defItem = (BizDataDefItemKpi)bizDataDefitem;
			amountBizCode = defItem.getAitemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setAitemBizData(itemAmountBizData);
			
			amountBizCode = defItem.getBitemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setBitemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemMoney )
		{
			BizDataDefItemMoney defItem = (BizDataDefItemMoney)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemMoneyComb )
		{
			BizDataDefItemMoneyComb defItem = (BizDataDefItemMoneyComb)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
		}			
		if( bizDataDefitem instanceof BizDataDefItemForecastAssessment)
		{
			BizDataDefItemForecastAssessment defItem = (BizDataDefItemForecastAssessment)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemAvgHis )
		{
			//增加历史平均、预测累积、预测累积
			BizDataDefItemAvgHis defItem = (BizDataDefItemAvgHis)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemAddHis )
		{
			BizDataDefItemAddHis defItem = (BizDataDefItemAddHis)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
			
		}else if( bizDataDefitem instanceof BizDataDefItemAddFc )
		{
			BizDataDefItemAddFc defItem = (BizDataDefItemAddFc)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
		}else if( bizDataDefitem instanceof BizDataDefItemPeriodVersion )
		{
			BizDataDefItemPeriodVersion defItem = (BizDataDefItemPeriodVersion)bizDataDefitem;
			amountBizCode = defItem.getItemBizData().getCode() + BizConst.AMOUNT_BIZ_DATA_SUFFIX;
			itemAmountBizData = bizDataDao.getBizDataByCode(amountBizCode);
			defItem.setItemBizData(itemAmountBizData);
		}
		
		return bizDataDefitem;
    }
    
}

/**************************************************************************
 * 
 * $RCSfile: BizDataService.java,v $ $Revision: 1.3 $ $Date: 2010/07/20 02:50:08
 * $
 * 
 * $Log: BizDataService.java,v $ Revision 1.3 2010/07/20 02:50:08 liuzhen
 * 2010.07.20 by liuzhen
 * 
 * Committed on the Free edition of March Hare Software CVSNT Server. Upgrade to
 * CVS Suite for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision 1.2 2010/07/16 02:39:36 liuzhen 2010.07.16 by liuzhen Committed on
 * the Free edition of March Hare Software CVSNT Server. Upgrade to CVS Suite
 * for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision 1.1 2010/07/04 07:26:52 liuzhen 2010.07.04 by liuzhen Committed on
 * the Free edition of March Hare Software CVSNT Server. Upgrade to CVS Suite
 * for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision
 * 
 * 
 ***************************************************************************/

