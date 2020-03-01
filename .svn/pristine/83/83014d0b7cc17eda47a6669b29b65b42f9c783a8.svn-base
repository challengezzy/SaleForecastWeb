package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemMoney extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000012;

    /** persistent field */
    private int priceType;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemMoney(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int priceType, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.priceType = priceType;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemMoney() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_MONEY );
    }

    /** minimal constructor */
    public BizDataDefItemMoney(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int priceType) {
      super(indicator, bizData);
        this.priceType = priceType;
    }

    public int getPriceType() {
        return this.priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
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
