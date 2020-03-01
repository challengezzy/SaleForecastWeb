package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemTimeHis extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000009;

    /** persistent field */
    private int timeFormula;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemTimeHis(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int timeFormula, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.timeFormula = timeFormula;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemTimeHis() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_TIMEHIS );
    }

    /** minimal constructor */
    public BBizDataDefItemTimeHis(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int timeFormula) {
      super(indicator, bizData);
        this.timeFormula = timeFormula;
    }

    public int getTimeFormula() {
        return this.timeFormula;
    }

    public void setTimeFormula(int timeFormula) {
        this.timeFormula = timeFormula;
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
