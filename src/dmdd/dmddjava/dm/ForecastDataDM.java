package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.util.DBUtil;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilUUID;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastInstView;
import dmdd.dmddjava.dataaccess.aidobject.ABForecastSetting;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumCommonData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BForecastData;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastInst;

public class ForecastDataDM extends BasicDM {
    /**
     * 检查范围内是否有数据记录存在
     * 
     * @param _idsScopeStr4DetailProOrgs
     * @param _periodBegin
     * @param _periodEnd
     * @param _bizDataId
     * @return
     */
    public boolean isExistForecastDatas(String _idsScopeStr4DetailProOrgs, ABUiRowData _abUiRowData, int _periodBegin, int _periodEnd, Long _bizDataId)
            throws Exception {
        // logger.info("isExistForecastDatas begin...");
        String tag = UtilUUID.uuid();
        String sqlStr;
        boolean isTagQuery = false;
        if (_abUiRowData.getDetailProOrgIds().size() > 1) {
            isTagQuery = true;
        }
        try {
            HashVO[] vos;
            // 如果已经是最明细层数据，则直接查询
            if (isTagQuery == false) {
                String pid = UtilProOrg.getPidByIdStr(_abUiRowData.getDetailProOrgIds().get(0));
                String oid = UtilProOrg.getOidByIdStr(_abUiRowData.getDetailProOrgIds().get(0));
                sqlStr = "select count(A.id) from ForecastData A where  PERIOD>=? and PERIOD<=? and BIZDATAID =? "
                        + " and A.Productid = ? and A.Organizationid = ?";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _bizDataId, pid, oid);
            } else {
                insertProORg(_idsScopeStr4DetailProOrgs, tag);
                sqlStr = "select count(A.id) from ForecastData A,QUERY_PRODORG B where  PERIOD>=? and PERIOD<=? and BIZDATAID =? "
                        + " and A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG=? ";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _bizDataId, tag);
            }
            if (vos == null || vos.length < 1) {
                return false;
            } else {
                int value = vos[0].getIntegerValue(0);
                if (value > 0)
                    return true;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (isTagQuery) {
                deleteProOrg(tag);
            }
        }
        // logger.info("isExistForecastDatas end...");
        return false;
    }

    public List<ASumCommonData> getSumForecastDatas(String _idsScopeStr4DetailProOrgs, ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd,
            List<Long> _list4BizDataId) throws Exception {
        // TODO 性能改造点 zhangzy 20150421
        // logger.info("getSumForecastDatas list begin...");
        List<ASumCommonData> rstList = new ArrayList<ASumCommonData>();
        StringBuffer sqlStr = new StringBuffer();
        String tag = UtilUUID.uuid();
        boolean isTagQuery = false;
        if (_abUiRowDataProOrg.getDetailProOrgIds().size() > 1) {
            isTagQuery = true;
        }
        HashVO[] vos;
        // 如果已经是最明细层数据，则直接查询
        if (isTagQuery == false) {
            String pid = UtilProOrg.getPidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));
            String oid = UtilProOrg.getOidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));

            sqlStr.append("select VALUE as y0_, PERIOD as y1_,  BIZDATAID as y2_  from ForecastData A where  PERIOD>=?"
                    + "  and PERIOD<=? and BIZDATAID =? and A.Productid = ? and A.Organizationid = ?");
            for (int i = 1; i < _list4BizDataId.size(); i++) {
                sqlStr.append(" union all select VALUE as y0_, PERIOD as y1_,  BIZDATAID as y2_  from ForecastData A where  PERIOD>=" + _periodBegin
                        + " and PERIOD<=" + _periodEnd + " and BIZDATAID =" + _list4BizDataId.get(i) + " and A.Productid = " + pid + " and A.Organizationid = "
                        + oid);
            }
            vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr.toString(), _periodBegin, _periodEnd, _list4BizDataId.get(0), pid, oid);
        } else {
            insertProORg(_idsScopeStr4DetailProOrgs, tag);
            sqlStr.append("select sum(VALUE) as y0_, PERIOD as y1_,  BIZDATAID as y2_  from ForecastData A,QUERY_PRODORG B where  PERIOD>=?"
                    + " and PERIOD<=? and BIZDATAID =? and A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG= ?"
                    + " group by PERIOD, BIZDATAID ");
            for (int i = 1; i < _list4BizDataId.size(); i++) {
                sqlStr.append(" union all select sum(VALUE) as y0_, PERIOD as y1_,  BIZDATAID as y2_  from ForecastData A,QUERY_PRODORG B where  PERIOD>="
                        + _periodBegin + " and PERIOD<=" + _periodEnd + " and BIZDATAID =" + _list4BizDataId.get(i)
                        + " and A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG='" + tag + "' group by PERIOD, BIZDATAID ");
            }
            vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr.toString(), _periodBegin, _periodEnd, _list4BizDataId.get(0), tag);
        }

        try {
            for (int i = 0; i < vos.length; i++) {
                HashVO vo = vos[i];
                ASumCommonData aSumData = new ASumCommonData();
                aSumData.setValue(vo.getLongValue(0));
                aSumData.setPeriod(vo.getIntegerValue(1));
                aSumData.setBizDataId(vo.getLongValue(2));
                rstList.add(aSumData);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (isTagQuery) {
                deleteProOrg(tag);
            }
        }

        // logger.info("getSumForecastDatas list end...");
        return rstList;
    }

    public List<ASumCommonData> getSumForecastDatas(String _idsScopeStr4DetailProOrgs, ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd,
            Long _bizDataId) throws Exception {
        // logger.info("getSumForecastDatas id begin...");
        String tag = UtilUUID.uuid();
        List<ASumCommonData> rstList = new ArrayList<ASumCommonData>();
        String sqlStr;
        boolean isTagQuery = false;
        if (_abUiRowDataProOrg.getDetailProOrgIds().size() > 1) {
            isTagQuery = true;
        }

        try {
            HashVO[] vos;
            // 如果已经是最明细层数据，则直接查询
            if (isTagQuery == false) {
                String pid = UtilProOrg.getPidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));
                String oid = UtilProOrg.getOidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));
                sqlStr = "select sum(VALUE) as y0_, PERIOD as y1_,  BIZDATAID as y2_  from ForecastData A "
                        + " where  PERIOD>=? and PERIOD<=? and BIZDATAID =? and A.Productid = " + pid + " and A.Organizationid = " + oid
                        + "  group by PERIOD, BIZDATAID ";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _bizDataId);
            } else {
                insertProORg(_idsScopeStr4DetailProOrgs, tag);

                sqlStr = "select sum(VALUE) as y0_, PERIOD as y1_,  BIZDATAID as y2_  from ForecastData A,QUERY_PRODORG B "
                        + " where  PERIOD>=? and PERIOD<=? and BIZDATAID =? and  A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG=?  "
                        + "  group by PERIOD, BIZDATAID ";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _bizDataId, tag);
            }

            for (int i = 0; i < vos.length; i++) {
                HashVO vo = vos[i];
                ASumCommonData aSumData = new ASumCommonData();
                aSumData.setValue(vo.getLongValue(0));
                aSumData.setPeriod(vo.getIntegerValue(1));
                aSumData.setBizDataId(vo.getLongValue(2));

                rstList.add(aSumData);

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (isTagQuery) {
                deleteProOrg(tag);
            }
        }
        return rstList;
    }

    public List<ASumMoney> getForecastDataSumMoneys(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId, int _peiceType)
            throws Exception {
        // logger.info("getForecastDataSumMoneys begin...");
        String tag = UtilUUID.uuid();
        List<ASumMoney> rstList = new ArrayList<ASumMoney>();

        try {
            insertProORg(_idsScopeStr4DetailProOrgs, tag);
            String querySql = "";

            if (_peiceType == BizConst.PRICE_TYPE_REAL) {
                querySql = " select forecastdata.period PERIOD, sum(forecastdata.value * pricedata.realprice) MONEY ";
            } else {
                querySql = " select forecastdata.period PERIOD, sum(forecastdata.value * standardprice) MONEY ";
            }
            querySql = querySql
                    +
                    // " select forecastdata.period PERIOD, sum(forecastdata.value * pricedata.realprice) MONEY"
                    // +
                    "   from forecastdata, pricedata ,QUERY_PRODORG " + "  where (forecastdata.productid = pricedata.productid) "
                    + "	 and (forecastdata.organizationid = pricedata.organizationid) " + "	 and (forecastdata.period = pricedata.period) "
                    + "	 and (forecastdata.period >= ? ) " + "	 and (forecastdata.period <= ?) " + "	 and (forecastdata.bizdataid = ? ) "
                    + " and (QUERY_PRODORG.productid = pricedata.productid) " + " and (QUERY_PRODORG.organizationid = pricedata.organizationid)"
                    + " and QUERY_PRODORG.QUERYTAG=?" + " group by forecastdata.period";

            HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, querySql, _periodBegin, _periodEnd, _bizDataId, tag);
            for (HashVO vo : vos) {
                ASumMoney aSumMoney = new ASumMoney();
                aSumMoney.setPeriod(vo.getIntegerValue(0));
                aSumMoney.setValue(vo.getDoubleValue(1));

                rstList.add(aSumMoney);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteProOrg(tag);
            dmo.releaseContext(DMOConst.DS_DEFAULT);
        }
        // logger.info("getForecastDataSumMoneys end...");
        return rstList;
    }

    public boolean checkForecastDataStatusIsInactive(String _sqlRstidsScopeStr4DetailProOrgs, int period, List<Long> _list4BizDataId) throws Exception {
        logger.debug("判断预测数据是否有被审核或冻结...");
        String tag = UtilUUID.uuid();
        try {
            if (_list4BizDataId == null || _list4BizDataId.size() < 1)//
                return true;

            insertProORg(_sqlRstidsScopeStr4DetailProOrgs, tag);
            StringBuffer sql_ids = new StringBuffer();
            sql_ids.append("-1");
            for (Long id : _list4BizDataId)
                sql_ids.append("," + id);

            String sqlStr = "select count(A.id) from ForecastData  A, QUERY_PRODORG B where  PERIOD>=" + period + " and BIZDATAID in( " + sql_ids.toString()
                    + ")" + " and status=1 and B.Productid = A.Productid and B.Organizationid = A.Organizationid and QUERYTAG= '" + tag + "'";

            HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr);
            if (vos == null || vos.length < 1) {
                return false;
            } else {
                int value = vos[0].getIntegerValue(0);
                if (value > 0)
                    return true;
            }
            logger.debug("判断预测数据是否有被审核或冻结完毕");
        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteProOrg(tag);
        }
        return false;
    }

    public List<ABForecastSetting> getForecastSettingFcNum(String _sqlRstidsScopeStr4DetailProOrgs) throws Exception {
        // TODO ZHANGZY 20150421 此处也很耗时
        String tag = UtilUUID.uuid();
        List<ABForecastSetting> list = new LinkedList<ABForecastSetting>();
        try {
            insertProORg(_sqlRstidsScopeStr4DetailProOrgs, tag);
            String sql = "select distinct c.id from FORECASTINST_VIEW A,QUERY_PRODORG B,forecastinst C "
                    + " where A.Productid = B.Productid and A.Organizationid = B.Organizationid and C.id = A.FORECASTINSTID and QUERYTAG=? and c.ISVALID=1";
            HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, tag);

            if (vos.length > 0) {
                Session session = HibernateSessionFactory.getSession();
                Transaction trsa = session.beginTransaction();
                DaoForecastInst daoForecastInst = new DaoForecastInst(session);
                String sql_ = " id in(-1";
                for (HashVO vo : vos)
                    sql_ = sql_ + "," + vo.getLongValue(0);
                sql_ = sql_ + ")";

                List<ForecastInst> list_inst = daoForecastInst.getForecastInsts2(sql_, -1, -1);
                if (list_inst == null || list_inst.size() < 1)
                    return null;
                BizDataBDConvertor bizDataBDConvertor = new BizDataBDConvertor();

                for (ForecastInst forecastInst : list_inst) {
                    BBizData bBizDataFcComb4FcFinal = bizDataBDConvertor.dtob(forecastInst.getFinalFcBizData(), true);
                    BBizData bBizData4DistributeRef = bizDataBDConvertor.dtob(forecastInst.getDistributeRefBizData(), true);

                    ABForecastSetting abForecastSetting = new ABForecastSetting();
                    abForecastSetting.setForecastInstId(forecastInst.getId());
                    abForecastSetting.setFinalFcBizData(bBizDataFcComb4FcFinal);
                    abForecastSetting.setFcPeriodNum(forecastInst.getFcPeriodNum());
                    abForecastSetting.setFzPeriodNum(forecastInst.getFzPeriodNum());
                    abForecastSetting.setDistributeRefFormula(forecastInst.getDistributeRefFormula());
                    abForecastSetting.setDecomposeFormula(forecastInst.getDecomposeFormula());
                    abForecastSetting.setDistributeRefBizData(bBizData4DistributeRef);
                    abForecastSetting.setDistributeRefPeriodNum(forecastInst.getDistributeRefPeriodNum());
                    list.add(abForecastSetting);
                }
                trsa.commit();
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteProOrg(tag);
            dmo.releaseContext(DMOConst.DS_DEFAULT);
        }
        // logger.debug("getForecastSettingFcNum end...");

        return list;
    }

    public void deleteForecastInstView() throws Exception {
        String sql = "delete from FORECASTINST_VIEW";
        dmo.executeUpdateByDS(null, sql);
        dmo.commit(null);
    }

    public List<ABForecastInstView> buildDetailProOrg(Long productid, Long orgId, Long instId) throws Exception {
        List<ABForecastInstView> result = new ArrayList<ABForecastInstView>();
        List<Long> productids = buildProduct(productid);
        List<Long> orgids = buildOrg(orgId);
        ABForecastInstView view = null;
        for (Long _productid : productids) {
            for (Long _orgId : orgids) {
                view = new ABForecastInstView();
                view.setForecastInstId(instId);
                view.setOrganizationId(_orgId);
                view.setProductId(_productid);
                result.add(view);
            }
        }
        return result;
    }

    /**
     * 获取明细的产品集合
     * 
     * @param productid
     * @return
     * @throws Exception
     */
    public List<Long> buildProduct(Long productid) throws Exception {
        List<Long> list = new ArrayList<Long>();
        String sql = "select ID from product where id=? and iscatalog=0";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productid);
        if (vos == null || vos.length < 1)// 还有子节点
        {
            sql = "select id from product where parentproductid=?";
            logger.info("查询id：" + productid + "下面所有的子节点 sql=" + sql);
            vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productid);
            for (HashVO vo : vos) {
                list.addAll(buildProduct(vo.getLognValue(0)));
            }

        } else if (vos.length > 0)// 是叶子
        {
            list.add(productid);
            return list;
        }

        return list;
    }

    public List<Long> buildOrg(Long orgId) throws Exception {
        List<Long> list = new ArrayList<Long>();
        String sql = "select ID from organization where id=? and iscatalog=0";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, orgId);
        if (vos == null || vos.length < 1)// 还有子节点
        {
            sql = "select id from organization where parentorganizationid=?";
            vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, orgId);
            for (HashVO vo : vos) {
                list.addAll(buildOrg(vo.getLognValue(0)));
            }

        } else if (vos.length > 0)// 是叶子
        {
            list.add(orgId);
            return list;
        }

        return list;
    }

    /**
     * 开始创建策略与明细维度关联
     * 
     * @param list
     * @throws Exception
     */
    public void buildForecastInstView(List<ABForecastInstView> list) throws Exception {
        logger.info("开始创建策略与明细维度关联");
        String insert_id = "";
        String insert_id_values = "";
        if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
            insert_id = "ID,";
            insert_id_values = "S_QUERY_PRODORG.Nextval,";
        }

        List<String> sqls = new ArrayList<String>();
        String sql = "";
        int count = 0;
        for (ABForecastInstView view : list) {
            sql = "insert into forecastinst_view(" + insert_id + "productid,organizationid,forecastinstid) values(" + insert_id_values + view.getProductId()
                    + "," + view.getOrganizationId() + "," + view.getForecastInstId() + ")";
            sqls.add(sql);
            if (count == 1000) {
                dmo.executeBatchByDS(null, sqls);
                dmo.commit(null);
                count = 0;
                sqls = new ArrayList<String>();
            }
        }
        logger.info(sql);
        dmo.executeBatchByDS(null, sqls);
        dmo.commit(null);
        logger.info("创建策略与明细维度关联完毕");
    }

    /**
     * 批量新增插入预测数据
     * 
     * @param dataList
     * @throws Exception
     */
    public void insertForecastData(List<BForecastData> dataList) throws Exception {
        String insertSql = "insert into forecastdata(" + DBUtil.getInsertId()
                + "version,productid,organizationid,bizdataid,period,value,status,inittime,updatetime,comments)" + " values("
                + DBUtil.getSeqValue("S_FORECASTDATA") + "0,?,?,?,?,?,0,?,?,?) ";

        for (BForecastData fcdata : dataList) {
            // TODO 数据量大时做1000条批量提交
            dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSql, fcdata.getProduct().getId(), fcdata.getOrganization().getId(), fcdata.getBizData().getId(),
                    fcdata.getPeriod(), fcdata.getValue(), fcdata.getInitTime(), fcdata.getUpdateTime(), fcdata.getComments());
        }
        dmo.commit(DMOConst.DS_DEFAULT);

    }

    /**
     * 批量刷新预测数据值
     * 
     * @param dataList
     * @throws Exception
     */
    public void updateForecastData(List<BForecastData> dataList) throws Exception {
        String insertSql = "update forecastdata set value=?,updatetime=? where id=? ";

        for (BForecastData fcdata : dataList) {
            // TODO 数据量大时做1000条批量提交
            dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSql, fcdata.getValue(), fcdata.getUpdateTime(), fcdata.getId());
        }
        dmo.commit(DMOConst.DS_DEFAULT);

    }
    
    /**
     * 获取预测数据中所有的业务数据id
     */
    public List<Long> getBizDataIds() throws Exception {
        String sql = "select distinct bizdataid from forecastdata";
        HashVO[] vos;
        try {
            vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        } catch (Exception e) {
            throw e;
        }

        List<Long> bizDataIds = new ArrayList<Long>(vos.length);
        for (int i = 0; i < vos.length; i++) {
            HashVO vo = vos[i];
            bizDataIds.add(vo.getLognValue("BIZDATAID"));
        }

        return bizDataIds;
    }
    
    public Long[] getMaxMinID(Long bizDataId) throws Exception {
        String sql = "select MAX(id) as MAX_ID, MIN(ID) as MIN_ID from forecastdata where bizdataid = ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, bizDataId);
        
        Long[] values = new Long[2];
        if (vos.length > 0) {
            values[0] = vos[0].getLognValue("MAX_ID");
            values[1] = vos[0].getLognValue("MIN_ID");
        }
        return values;
    }
    
    public List<ForecastData> getForecastDatas(Long bizDataId, Long minId, Long maxId,BizData targetAmountBizData) throws Exception {
        String sql = "select f.id, period, value, organizationid, productid,is_suit from forecastdata f,product p where p.id=f.productid and bizdataid = ? and f.id >= ? and f.id <= ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, bizDataId, minId, maxId);
        
        List<ForecastData> forecastDatas = new ArrayList<ForecastData>(vos.length);
        for(int i=0;i<vos.length;i++){
            HashVO vo = vos[i];
            ForecastData forecastData = new ForecastData();
            forecastData.setId(vo.getLongValue("ID"));
            forecastData.setPeriod(vo.getIntegerValue("PERIOD"));
            forecastData.setValue(vo.getLongValue("VALUE"));
            forecastData.setOrganization(new Organization(vo.getLongValue("ORGANIZATIONID")));
            Product pro = new Product(vo.getLongValue("PRODUCTID"));
            pro.setIsSuit(vo.getIntegerValue("IS_SUIT"));
            forecastData.setProduct(pro);
            forecastData.setBizData(new BizData(bizDataId));
            forecastData.setAmountBizData(targetAmountBizData);
            forecastDatas.add(forecastData);
        }
        return forecastDatas;
    }
    
    /** 根据 bppo数据查询预测数据 */
    public ForecastData getForecastDatas(Long bizDataId, Long proId, Long orgId, int period) throws Exception {
        String sql = "select id, period, value, organizationid, productid from forecastdata where period = ? and productid=? and organizationid=? and bizdataid=? ";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, period, proId, orgId, bizDataId);
        
        if(vos.length > 0){
            HashVO vo = vos[0];
            ForecastData forecastData = new ForecastData();
            forecastData.setId(vo.getLongValue("ID"));
            forecastData.setPeriod(vo.getIntegerValue("PERIOD"));
            forecastData.setValue(vo.getLongValue("VALUE"));
            forecastData.setOrganization(new Organization(vo.getLongValue("ORGANIZATIONID")));
            forecastData.setProduct(new Product(vo.getLongValue("PRODUCTID")));
            forecastData.setBizData(new BizData(bizDataId));
            
            return forecastData;
        }
        return null;
    }
    
    public void updateForecastData(ForecastData data) throws Exception {
        int period = data.getPeriod();
        double value = data.getValue();
        long org = data.getOrganization().getId();
        long prd = data.getProduct().getId();
        long biz = data.getBizData().getId();
        if (isSqlServer()) {
            String sql = "if not exists (select 1 from forecastdata where period = ? and organizationid = ? and productid = ? and bizdataid = ?) "
                    + "insert into forecastdata(version, period, value, organizationid, productid, bizdataid) values (0, ?, ?, ?, ?, ?) "
                    + "else update forecastdata set value = ? where period = ? and organizationid = ? and productid = ? and bizdataid = ?";
 
            dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sql, period, org, prd, biz, period, value, org, prd, biz, value, period, org, prd, biz);
        }
    }
    
    public Double getValue(int period, Long productId, Long organizationId, Long bizDataId) throws Exception {
        String sql = "select value from forecastdata where period = ? and productid = ? and organizationid = ? and bizdataid = ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, period, productId, organizationId, bizDataId);
        
        return vos.length > 0 ? vos[0].getDoubleValue("VALUE") : 0;
    }
    
    public Double getCumulationValue(int period, Long productId, Long organizationId, Long bizDataId) throws Exception {
        String periodStr = String.valueOf(period);
        if (periodStr.length() != 6) {
            throw new RuntimeException("invalid period ["+ period +"]");
        }
        int periodBegin = Integer.parseInt(periodStr.substring(4) + "01");
        String sql = "select sum(value) as VALUE_CUMULATION from forecastdata where productid = ? and organizationid = ? and bizdataid = ? and period >= ? and period <= ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productId, organizationId, bizDataId, periodBegin, period);

        return vos.length > 0 ? vos[0].getDoubleValue("VALUE_CUMULATION") : 0;
    }
}
