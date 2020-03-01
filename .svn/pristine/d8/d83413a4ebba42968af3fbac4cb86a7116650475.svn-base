package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


public class BBizDataDefItemAddFc extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000009;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemAddFc(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemAddFc() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_ADDFC );
    }

    /** minimal constructor */
    public BBizDataDefItemAddFc(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
      super(indicator, bizData);
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
