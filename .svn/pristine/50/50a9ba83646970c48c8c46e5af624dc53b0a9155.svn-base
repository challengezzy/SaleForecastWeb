package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemAvgHis extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000008;

    /** persistent field */
    private int periodNum;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemAvgHis(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int periodNum, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.periodNum = periodNum;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemAvgHis() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_AVGHIS );
    }

    /** minimal constructor */
    public BizDataDefItemAvgHis(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int periodNum) {
      super(indicator, bizData);
        this.periodNum = periodNum;
    }

    public int getPeriodNum() {
        return this.periodNum;
    }

    public void setPeriodNum(int periodNum) {
        this.periodNum = periodNum;
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
