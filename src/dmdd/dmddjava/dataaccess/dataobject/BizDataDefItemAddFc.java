package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;

/**
 * 预测期间累积
 * @author zzy
 *
 */
public class BizDataDefItemAddFc extends BizDataDefItem implements Serializable {

	private static final long serialVersionUID = -3632663030164442295L;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemAddFc(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemAddFc() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_ADDFC );
    }

    /** minimal constructor */
    public BizDataDefItemAddFc(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
      super(indicator, bizData);
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
