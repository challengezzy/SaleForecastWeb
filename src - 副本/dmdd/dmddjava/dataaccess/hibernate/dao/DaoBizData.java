/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.dataaccess.dataobject.BizData;

/**
 * @author liuzhen
 * 
 */
public class DaoBizData extends Dao {

    /**
     * @param _session
     */
    public DaoBizData(Session _session) {
        super(_session);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    /**
     * 查询结果集统计信息
     * 
     * @param _sqlRestriction
     * @return
     */
    public int getBizDatasStat(String _sqlRestriction) {
        Criteria crit = this.getSession().createCriteria(BizData.class);
        if (_sqlRestriction != null && !(_sqlRestriction.trim().equals(""))) {
            crit.add(Restrictions.sqlRestriction(_sqlRestriction));
        }
        crit.setProjection(Projections.projectionList().add(Projections.count("id")));

        Integer countValue = (Integer) crit.uniqueResult();

        return countValue;
    }

    public List<BizData> getAllBizDatas() {
        return getBizDatas("", 0, 0);
    }
    
    /**
     * _pageIndex>0 时分页查询
     * 
     * @param _sqlRestriction
     * @param _pageIndex
     * @param _pageSize
     * @return
     */
    public List<BizData> getBizDatas(String _sqlRestriction, int _pageIndex, int _pageSize) {
        Criteria crit = this.getSession().createCriteria(BizData.class);
        if (_sqlRestriction != null && !(_sqlRestriction.trim().equals(""))) {
            crit.add(Restrictions.sqlRestriction(_sqlRestriction));
        }

        if (_pageIndex > 0) {
            // 分页查询
            crit.setFirstResult((_pageIndex - 1) * _pageSize);
            crit.setMaxResults(_pageSize);
        }
        
        crit.addOrder(Order.asc("id"));

        List<BizData> rstList = crit.list();

        return rstList;
    }

    public BizData getBizDataById(Long _id) {
        Object obj = this.getSession().get(BizData.class, _id);
        if (obj == null) {
            return null;
        }

        return (BizData) obj;
    }

    public BizData getBizDataByCode(String code) {
        Criteria crit = this.getSession().createCriteria(BizData.class);
        crit.add(Restrictions.eq("code", code));
        return (BizData) crit.uniqueResult();
    }

    public List<BizData> getBizDatasByIds(List<Long> _list4BizDataId) {
        if (_list4BizDataId == null || _list4BizDataId.isEmpty()) {
            return null;
        }

        Criteria crit = this.getSession().createCriteria(BizData.class);
        crit.add(Restrictions.in("id", _list4BizDataId));
        List<BizData> rstList = crit.list();

        return rstList;
    }

    public List<BizData> getBizDatasByTypes(Integer[] _arr4BizDataType, Integer[] _arr4BizDataIsValid) {
        if (_arr4BizDataType == null || _arr4BizDataType.length <= 0 || _arr4BizDataIsValid == null || _arr4BizDataIsValid.length <= 0) {
            return null;
        }

        Criteria crit = this.getSession().createCriteria(BizData.class);
        crit.add(Restrictions.in("type", _arr4BizDataType));
        crit.add(Restrictions.in("isValid", _arr4BizDataIsValid));
        List<BizData> rstList = crit.list();

        return rstList;
    }
    
    /**
     * 按类型查询不带折合的业务数据
     * @param _arr4BizDataType
     * @param _arr4BizDataIsValid
     * @return
     */
    public List<BizData> getBizDatasByTypesWithoutAmount(Integer[] _arr4BizDataType, Integer[] _arr4BizDataIsValid) {
        if (_arr4BizDataType == null || _arr4BizDataType.length <= 0 || _arr4BizDataIsValid == null || _arr4BizDataIsValid.length <= 0) {
            return null;
        }

        Criteria crit = this.getSession().createCriteria(BizData.class);
        crit.add(Restrictions.in("type", _arr4BizDataType));
        crit.add(Restrictions.in("isValid", _arr4BizDataIsValid));
        crit.add(Restrictions.sqlRestriction(" code not like '%SYS_AMOUNT' "));
        
        List<BizData> rstList = crit.list();

        return rstList;
    } 

    public BizData getFinalFcBizData() {
        // type = BizConst.BIZDATA_TYPE_FINALFC 的只有一个，业务上是这样设计的
        Criteria crit = this.getSession().createCriteria(BizData.class);
        crit.add(Restrictions.eq("type", BizConst.BIZDATA_TYPE_FCFINAL));
        crit.add(Restrictions.sqlRestriction(" code not like '%SYS_AMOUNT' "));
        BizData rstBizData = (BizData) crit.uniqueResult();

        return rstBizData;
    }

    public BizData getMappingModelFcBizData() {
        Criteria crit = this.getSession().createCriteria(BizData.class);
        crit.add(Restrictions.eq("code", BizConst.SYSTEMDEFINE_BIZDATA_CODE_SD_FORECAST_MAPPING));
        BizData rstBizData = (BizData) crit.uniqueResult();

        return rstBizData;
    }

    public void deleteByCode(String bizDataCode) {
        String sqlUpdate = "delete from BIZDATA where code ='" + bizDataCode + "'";
        this.getSession().createSQLQuery(sqlUpdate).executeUpdate();
    }

}
