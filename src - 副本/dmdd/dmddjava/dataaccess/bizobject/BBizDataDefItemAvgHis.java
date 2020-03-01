package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


public class BBizDataDefItemAvgHis extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000009;

    /** persistent field */
    private int periodNum;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemAvgHis(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int periodNum, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.periodNum = periodNum;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemAvgHis() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_AVGHIS );
    }

    /** minimal constructor */
    public BBizDataDefItemAvgHis(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int periodNum) {
      super(indicator, bizData);
        this.periodNum = periodNum;
    }

    public int getPeriodNum() {
        return this.periodNum;
    }

    public void setPeriodNum(int periodNum) {
        this.periodNum = periodNum;
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
