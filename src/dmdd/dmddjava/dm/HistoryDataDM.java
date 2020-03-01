package dmdd.dmddjava.dm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.utils.UtilPeriod;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilUUID;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowData;
import dmdd.dmddjava.dataaccess.aidobject.ABUiRowDataProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumCommonData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.service.historyservice.AmountHistoryCalContext;

public class HistoryDataDM extends BasicDM {

    public List<ASumCommonData> getSumHistoryDatas(String _idsScopeStr4DetailProOrgs, ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd,
            List<Long> _list4BizDataId) throws Exception {
        // TODO 性能改造点 zhangzy 20150421
        // logger.info("getSumHistoryDatas list begin...");
        String tag = UtilUUID.uuid();
        List<ASumCommonData> rstList = new ArrayList<ASumCommonData>();
        String sqlStr;
        boolean isTagQuery = false;
        if (_abUiRowDataProOrg.getDetailProOrgIds().size() > 5) {
            isTagQuery = true;
        }
        try {
            HashVO[] vos;
            // 如果已经是最明细层数据，则直接查询
            if (isTagQuery == false) {
                String pid = UtilProOrg.getPidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));
                String oid = UtilProOrg.getOidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));
                sqlStr = "select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from HISTORYDATA A "
                        + " where  A.PERIOD>=? and A.PERIOD<=? and A.BIZDATAID =?" + " and A.Productid = ? and A.Organizationid = ?"
                        + " group by PERIOD, BIZDATAID ";
                for (int i = 1; i < _list4BizDataId.size(); i++) {
                    sqlStr += " union all select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from HISTORYDATA A " + " where  A.PERIOD>="
                            + _periodBegin + " and A.PERIOD<=" + _periodEnd + "and A.BIZDATAID =" + _list4BizDataId.get(i) + " and A.Productid = " + pid
                            + " and A.Organizationid = " + oid + " group by PERIOD, BIZDATAID ";
                }
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _list4BizDataId.get(0), pid, oid);
            } else {
                insertProORg(_idsScopeStr4DetailProOrgs, tag);
                sqlStr = "select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from HISTORYDATA A,QUERY_PRODORG B "
                        + " where  A.PERIOD>=? and A.PERIOD<=? and A.BIZDATAID =?"
                        + " and  A.Productid = B.Productid and A.Organizationid = B.Organizationid  and QUERYTAG=?" + " group by PERIOD, BIZDATAID ";
                for (int i = 1; i < _list4BizDataId.size(); i++) {
                    sqlStr += " union all select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from HISTORYDATA A,QUERY_PRODORG B "
                            + " where  A.PERIOD>=" + _periodBegin + " and A.PERIOD<=" + _periodEnd + " and A.BIZDATAID =" + _list4BizDataId.get(i)
                            + " and  A.Productid = B.Productid and A.Organizationid = B.Organizationid  and QUERYTAG='" + tag + "' group by PERIOD, BIZDATAID ";
                }
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _list4BizDataId.get(0), tag);
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
        // logger.info("getSumHistoryDatas list end...");
        return rstList;
    }

    public List<ASumCommonData> getSumHistoryDatas(String _idsScopeStr4DetailProOrgs, ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd,
            Long bizDataId) throws Exception {
        // logger.info("getSumHistoryDatas id begin...");
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
                sqlStr = "select sum(A.VALUE) as y0_, A.PERIOD as y1_, A.BIZDATAID as y2_ from HISTORYDATA A "
                        + " where  A.PERIOD>=? and A.PERIOD<=? and A.BIZDATAID =?" + " and A.Productid = ? and A.Organizationid = ?"
                        + " group by PERIOD, BIZDATAID ";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, bizDataId, pid, oid);
            } else {
                insertProORg(_idsScopeStr4DetailProOrgs, tag);
                sqlStr = "select sum(A.VALUE) as y0_, A.PERIOD as y1_,  A.BIZDATAID as y2_  from HISTORYDATA A,QUERY_PRODORG B"
                        + " where  A.PERIOD>=? and A.PERIOD<=? and A.BIZDATAID =? "
                        + " and A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG=?" + " group by PERIOD, BIZDATAID ";

                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, bizDataId, tag);
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
        // logger.info("getSumHistoryDatas id end...");
        return rstList;
    }

    /**
     * 检查范围内是否有数据记录存在
     * 
     * @param _idsScopeStr4DetailProOrgs
     * @param _periodBegin
     * @param _periodEnd
     * @param _bizDataId
     * @return
     */
    public boolean isExistHistoryDatas(String _idsScopeStr4DetailProOrgs, ABUiRowData _abUiRowData, int _periodBegin, int _periodEnd, Long _bizDataId)
            throws Exception {
        // logger.info("isExistHistoryDatas begin...");
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
                sqlStr = "select count(A.id) from HISTORYDATA A,QUERY_PRODORG B where  A.PERIOD>=? and A.PERIOD<=? and A.BIZDATAID =?  "
                        + " and A.Productid = " + pid + " and A.Organizationid = " + oid;
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sqlStr, _periodBegin, _periodEnd, _bizDataId);
            } else {
                insertProORg(_idsScopeStr4DetailProOrgs, tag);
                sqlStr = "select count(A.id) from HISTORYDATA A,QUERY_PRODORG B where  A.PERIOD>=? and A.PERIOD<=? and A.BIZDATAID =?  "
                        + "and A.Productid = B.Productid and A.Organizationid = B.Organizationid and QUERYTAG=?";

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
        // logger.info("isExistHistoryDatas end...");
        return false;
    }

    public List<ASumMoney> getHistoryDataSumMoneys(String _idsScopeStr4DetailProOrgs, ABUiRowDataProOrg _abUiRowDataProOrg, int _periodBegin, int _periodEnd,
            Long _bizDataId, int _peiceType) throws Exception {
        // logger.info("getHistoryDataSumMoneys begin...");
        String tag = UtilUUID.uuid();
        List<ASumMoney> rstList = new ArrayList<ASumMoney>();
        boolean isTagQuery = false;
        if (_abUiRowDataProOrg.getDetailProOrgIds().size() > 1) {
            isTagQuery = true;
        }

        try {
            HashVO[] vos;
            String querySql = "";
            // 如果已经是最明细层数据，则直接查询
            if (isTagQuery == false) {
                String pid = UtilProOrg.getPidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));
                String oid = UtilProOrg.getOidByIdStr(_abUiRowDataProOrg.getDetailProOrgIds().get(0));

                if (_peiceType == BizConst.PRICE_TYPE_REAL) {
                    querySql = " select historydata.period PERIOD, sum(historydata.value * pricedata.realprice) MONEY ";
                } else {
                    querySql = " select historydata.period PERIOD, sum(historydata.value * standardprice) MONEY ";
                }
                querySql += "  from historydata, pricedata"
                        + "  where historydata.productid = pricedata.productid  and historydata.organizationid = pricedata.organizationid "
                        + " and historydata.period = pricedata.period "
                        + " and historydata.period >=? and historydata.period <= ? and historydata.bizdataid =?"
                        + " and historydata.productid =? and historydata.organizationid =?" + " group by historydata.period";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, querySql, _periodBegin, _periodEnd, _bizDataId, pid, oid);
            } else {
                insertProORg(_idsScopeStr4DetailProOrgs, tag);
                if (_peiceType == BizConst.PRICE_TYPE_REAL) {
                    querySql = " select historydata.period PERIOD, sum(historydata.value * pricedata.realprice) MONEY ";
                } else {
                    querySql = " select historydata.period PERIOD, sum(historydata.value * standardprice) MONEY ";
                }
                querySql += "  from historydata, pricedata, QUERY_PRODORG"
                        + "  where (historydata.productid = pricedata.productid)  and (historydata.organizationid = pricedata.organizationid) "
                        + " and (historydata.period = pricedata.period) "
                        + " and (historydata.period >= ?)  and (historydata.period <= ? )  and (historydata.bizdataid = ? ) "
                        + " and (QUERY_PRODORG.productid = pricedata.productid)  and (QUERY_PRODORG.organizationid = pricedata.organizationid)"
                        + " and QUERY_PRODORG.QUERYTAG=? group by historydata.period";
                vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, querySql, _periodBegin, _periodEnd, _bizDataId, tag);
            }

            for (HashVO vo : vos) {
                ASumMoney aSumMoney = new ASumMoney();
                aSumMoney.setPeriod(vo.getIntegerValue(0));
                aSumMoney.setValue(vo.getDoubleValue(1));
                rstList.add(aSumMoney);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (isTagQuery) {
                deleteProOrg(tag);
            }
        }
        // logger.info("getHistoryDataSumMoneys end...");
        return rstList;
    }

    /**
     * 获取历史数据中所有的业务数据id
     */
    public List<Long> getBizDataIds() throws Exception {
        String sql = "select distinct bizdataid from historydata";
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
        String sql = "select MAX(id) as MAX_ID, MIN(ID) as MIN_ID from historydata where bizdataid = ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, bizDataId);
        
        Long[] values = new Long[2];
        if (vos.length > 0) {
            values[0] = vos[0].getLognValue("MAX_ID");
            values[1] = vos[0].getLognValue("MIN_ID");
        }
        return values;
    }
    
    public List<HistoryData> getHistoryDatas(Long bizDataId, Long minId, Long maxId ,BizData targetAmountBizData) throws Exception {
        
        String sql = "select f.id, period, value, organizationid, productid,is_suit from historydata f,product p where p.id=f.productid and bizdataid = ? and f.id >= ? and f.id <= ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, bizDataId, minId, maxId);
        
        List<HistoryData> historyDatas = new ArrayList<HistoryData>(vos.length);
        for(int i=0;i<vos.length;i++){
            HashVO vo = vos[i];
            HistoryData historyData = new HistoryData();
            historyData.setId(vo.getLongValue("ID"));
            historyData.setPeriod(vo.getIntegerValue("PERIOD"));
            historyData.setValue(vo.getDoubleValue("VALUE"));
            historyData.setOrganization(new Organization(vo.getLongValue("ORGANIZATIONID")));
            Product pro = new Product(vo.getLongValue("PRODUCTID"));
            pro.setIsSuit(vo.getIntegerValue("IS_SUIT"));
            historyData.setProduct(pro);
            
            historyData.setBizData(new BizData(bizDataId));
            historyDatas.add(historyData);
        }
        return historyDatas;
    }
    
    public List<AmountHistoryCalContext> getHistoryCalContextDatas(Long bizDataId, Long minId, Long maxId ,BizData targetAmountBizData) throws Exception {
        
        String sql = "select f.id, period, value, organizationid, productid,is_suit from historydata f,product p where p.id=f.productid and bizdataid = ? and f.id >= ? and f.id <= ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, bizDataId, minId, maxId);
        
        List<AmountHistoryCalContext> calContextList = new ArrayList<AmountHistoryCalContext>(vos.length);
        for(int i=0;i<vos.length;i++){
            HashVO vo = vos[i];
            
            AmountHistoryCalContext calContext = new AmountHistoryCalContext();
        	calContext.setPeriod(vo.getIntegerValue("PERIOD"));
        	calContext.setOrgId(vo.getLongValue("ORGANIZATIONID"));
        	calContext.setProId(vo.getLongValue("PRODUCTID"));
        	calContext.setIsSuit(vo.getIntegerValue("IS_SUIT"));
        	calContext.setOriValue(vo.getDoubleValue("VALUE"));
        	calContext.setBizDataId(bizDataId);
        	calContext.setAmountBizDataId(targetAmountBizData.getId());
        	calContext.setBizDataIdAd(null);
        	
            calContextList.add(calContext);
        }
        return calContextList;
    }

    public void updateHistoryData(HistoryData data) throws Exception {
        int period = data.getPeriod();
        double value = data.getValue();
        long org = data.getOrganization().getId();
        long prd = data.getProduct().getId();
        long biz = data.getBizData().getId();
        if (isSqlServer()) {
            String sql = "if not exists (select 1 from HISTORYDATA where period = ? and organizationid = ? and productid = ? and bizdataid = ?) "
                    + "insert into HISTORYDATA(version, period, value, organizationid, productid, bizdataid) values (0, ?, ?, ?, ?, ?) "
                    + "else update HISTORYDATA set value = ? where period = ? and organizationid = ? and productid = ? and bizdataid = ?";
 
            dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sql, period, org, prd, biz, period, value, org, prd, biz, value, period, org, prd, biz);
        }
    }
    
    public Double getValue(int period, Long productId, Long organizationId, Long bizDataId) throws Exception {
        String sql = "select value from historydata where period = ? and productid = ? and organizationid = ? and bizdataid = ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, period, productId, organizationId, bizDataId);
        
        return vos.length > 0 ? vos[0].getDoubleValue("VALUE") : 0;
    }
    
    public Double getCumulationValue(int period, Long productId, Long organizationId, Long bizDataId) throws Exception {
        String periodStr = String.valueOf(period);
        if (periodStr.length() != 6) {
            throw new RuntimeException("invalid period ["+ period +"]");
        }
        int periodBegin = Integer.parseInt(periodStr.substring(4) + "01");
        String sql = "select sum(value) as VALUE_CUMULATION from historydata where productid = ? and organizationid = ? and bizdataid = ? and period >= ? and period <= ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productId, organizationId, bizDataId, periodBegin, period);

        return vos.length > 0 ? vos[0].getDoubleValue("VALUE_CUMULATION") : 0;
    }
    
    public Double getAverageValue(int period, int periodNumber, Long productId, Long organizationId, Long bizDataId) throws Exception {
        String periodStr = String.valueOf(period);
        if (periodStr.length() != 6) {
            throw new RuntimeException("invalid period ["+ period +"]");
        }
        
        int periodBegin = UtilPeriod.getPeriod(period, -periodNumber);
        String sql = "select sum(value) as VALUE_CUMULATION from historydata where productid = ? and organizationid = ? and bizdataid = ? and period >= ? and period < ?";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productId, organizationId, bizDataId, periodBegin, period);

        double avg = vos.length > 0 ? vos[0].getDoubleValue("VALUE_CUMULATION") / periodNumber : 0;
        return avg == 0 ? 0 : Double.parseDouble(new DecimalFormat("#.00").format(avg));
    }
}
