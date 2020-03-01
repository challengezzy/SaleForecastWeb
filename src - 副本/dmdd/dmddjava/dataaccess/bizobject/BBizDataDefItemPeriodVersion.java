package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


public class BBizDataDefItemPeriodVersion extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000009;

    /** persistent field */
    private int periodInterval;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemPeriodVersion(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int periodInterval, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.periodInterval = periodInterval;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemPeriodVersion() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_PERIODVERSION );
    }

    /** minimal constructor */
    public BBizDataDefItemPeriodVersion(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int periodInterval) {
      super(indicator, bizData);
        this.periodInterval = periodInterval;
    }

    public int getPeriodInterval() {
        return this.periodInterval;
    }

    public void setPeriodInterval(int periodInterval) {
        this.periodInterval = periodInterval;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getItemBizData() {
        return this.itemBizData;
    }

    public void setItemBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        this.itemBizData = itemBizData;
    }

    public String toString() {
        return super.toString();
    }

}
