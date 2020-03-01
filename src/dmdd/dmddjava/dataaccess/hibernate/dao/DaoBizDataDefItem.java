/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemFcComb;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAd;
import dmdd.dmddjava.dataaccess.dataobject.BizDataDefItemHistoryAdR;

/**
 * @author liuzhen
 * 
 */
public class DaoBizDataDefItem extends Dao {

    /**
     * @param _session
     */
    public DaoBizDataDefItem(Session _session) {
        super(_session);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public BizData getBizDataHistoryAdByBizDataHistoryId(Long _BizDataHistoryId) {
        Criteria crit = this.getSession().createCriteria(BizDataDefItemHistoryAd.class);
        crit.add(Restrictions.eq("historyBizData.id", _BizDataHistoryId));
        BizDataDefItemHistoryAd bizDataDefItemHistoryAd = (BizDataDefItemHistoryAd) crit.uniqueResult();
        return bizDataDefItemHistoryAd.getBizData();
    }

    public List<BizData> getBizDataHistoryAdRByBizDataHistoryAdId(Long _BizDataHistoryAdId) {
        List<BizData> rstList = new ArrayList<BizData>();
        Criteria crit = this.getSession().createCriteria(BizDataDefItemHistoryAdR.class);
        crit.add(Restrictions.eq("historyAdBizData.id", _BizDataHistoryAdId));
        List<BizDataDefItemHistoryAdR> listBizDataDefItemHistoryAdR = crit.list();
        if (listBizDataDefItemHistoryAdR != null) {
            for (int i = 0; i < listBizDataDefItemHistoryAdR.size(); i = i + 1) {
                rstList.add(listBizDataDefItemHistoryAdR.get(i).getBizData());
            }

        }

        return rstList;
    }

    public HashMap<Long, BizData> getBizDatasFcCombByBizDataFcHandId(Long _bizDataIdFcHand) {
        Criteria crit = this.getSession().createCriteria(BizDataDefItemFcComb.class);
        crit.add(Restrictions.eq("itemBizData.id", _bizDataIdFcHand));
        List<BizDataDefItemFcComb> list4BizDataDefItemFcComb = crit.list();

        // 用HashMap收集FcComb，去除重复的
        HashMap<Long, BizData> hmap_Id_BizDataFcComb = new HashMap<Long, BizData>();
        if (list4BizDataDefItemFcComb != null) {
            for (int i = 0; i < list4BizDataDefItemFcComb.size(); i++) {
                BizDataDefItemFcComb bizDataDefItemFcComb = list4BizDataDefItemFcComb.get(i);
                hmap_Id_BizDataFcComb.put(bizDataDefItemFcComb.getId(), bizDataDefItemFcComb.getBizData());
            }
        }

        return hmap_Id_BizDataFcComb;
    }

    public void deleteByBizDataId(Long bizDataId) {
        String sqlUpdate = "delete from BIZDATADEFITEM where bizdataid =" + bizDataId;
        this.getSession().createSQLQuery(sqlUpdate).executeUpdate();
    }

}
