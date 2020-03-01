package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemMoney extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000012;

    /** persistent field */
    private int priceType;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemMoney(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int priceType, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.priceType = priceType;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemMoney() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_MONEY );
    }

    /** minimal constructor */
    public BBizDataDefItemMoney(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int priceType) {
      super(indicator, bizData);
        this.priceType = priceType;
    }

    public int getPriceType() {
        return this.priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
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
