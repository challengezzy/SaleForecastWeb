package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemPeriodVersion extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000008;

    /** persistent field */
    private int periodInterval;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemPeriodVersion(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int periodInterval, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.periodInterval = periodInterval;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemPeriodVersion() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_PERIODVERSION );
    }

    /** minimal constructor */
    public BizDataDefItemPeriodVersion(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int periodInterval) {
      super(indicator, bizData);
        this.periodInterval = periodInterval;
    }

    public int getPeriodInterval() {
        return this.periodInterval;
    }

    public void setPeriodInterval(int periodInterval) {
        this.periodInterval = periodInterval;
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getItemBizData() {
        return this.itemBizData;
    }

    public void setItemBizData(dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        this.itemBizData = itemBizData;
    }

    public String toString() {
        return super.toString();
    }

}
