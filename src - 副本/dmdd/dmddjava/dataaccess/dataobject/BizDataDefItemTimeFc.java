package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemTimeFc extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000008;

    /** persistent field */
    private int timeFormula;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemTimeFc(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int timeFormula, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.timeFormula = timeFormula;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemTimeFc() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_TIMEFC );
    }

    /** minimal constructor */
    public BizDataDefItemTimeFc(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int timeFormula) {
      super(indicator, bizData);
        this.timeFormula = timeFormula;
    }

    public int getTimeFormula() {
        return this.timeFormula;
    }

    public void setTimeFormula(int timeFormula) {
        this.timeFormula = timeFormula;
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
