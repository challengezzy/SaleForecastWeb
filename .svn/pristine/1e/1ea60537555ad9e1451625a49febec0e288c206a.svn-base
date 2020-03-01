package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemHistoryAd extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000005;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData;

    /** full constructor */
    public BizDataDefItemHistoryAd(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData) {
        super(indicator, version, bizData);
        this.historyBizData = historyBizData;
    }

    /** default constructor */
    public BizDataDefItemHistoryAd() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_HISTORYAD );
    }

    /** minimal constructor */
    public BizDataDefItemHistoryAd(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
      super(indicator, bizData);
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getHistoryBizData() {
        return this.historyBizData;
    }

    public void setHistoryBizData(dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData) {
        this.historyBizData = historyBizData;
    }

    public String toString() {
        return super.toString();
    }

}
