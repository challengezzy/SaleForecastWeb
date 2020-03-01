package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemHistoryAd extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000005;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData;

    /** full constructor */
    public BBizDataDefItemHistoryAd(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData) {
        super(indicator, version, bizData);
        this.historyBizData = historyBizData;
    }

    /** default constructor */
    public BBizDataDefItemHistoryAd() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_HISTORYAD );
    }

    /** minimal constructor */
    public BBizDataDefItemHistoryAd(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
      super(indicator, bizData);
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getHistoryBizData() {
        return this.historyBizData;
    }

    public void setHistoryBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData) {
        this.historyBizData = historyBizData;
    }

    public String toString() {
        return super.toString();
    }

}
