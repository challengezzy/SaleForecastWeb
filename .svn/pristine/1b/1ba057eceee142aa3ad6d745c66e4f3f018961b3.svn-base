package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemFcHand extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000004;

    /** persistent field */
    private Double coefficient;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemFcHand(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, Double coefficient, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.coefficient = coefficient;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemFcHand() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_FCHAND );
    }

    /** minimal constructor */
    public BBizDataDefItemFcHand(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, Double coefficient) {
      super(indicator, bizData);
        this.coefficient = coefficient;
    }

    public Double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
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
