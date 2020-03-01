package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemFcHand extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000004;

    /** persistent field */
    private Double coefficient;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemFcHand(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, Double coefficient, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.coefficient = coefficient;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemFcHand() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_FCHAND );
    }

    /** minimal constructor */
    public BizDataDefItemFcHand(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, Double coefficient) {
      super(indicator, bizData);
        this.coefficient = coefficient;
    }

    public Double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
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
