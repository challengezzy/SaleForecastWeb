package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemHistoryAdR extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000006;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData historyAdBizData;

    /** full constructor */
    public BBizDataDefItemHistoryAdR(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, dmdd.dmddjava.dataaccess.bizobject.BBizData historyAdBizData) {
        super(indicator, version, bizData);
        this.historyAdBizData = historyAdBizData;
    }

    /** default constructor */
    public BBizDataDefItemHistoryAdR() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_HISTORYADR );
    }

    /** minimal constructor */
    public BBizDataDefItemHistoryAdR(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
      super(indicator, bizData);
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getHistoryAdBizData() {
        return this.historyAdBizData;
    }

    public void setHistoryAdBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData historyAdBizData) {
        this.historyAdBizData = historyAdBizData;
    }

    public String toString() {
        return super.toString();
    }

}
