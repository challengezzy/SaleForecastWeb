package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;

/**
 * 历史期间累积
 * @author zzy
 *
 */
public class BizDataDefItemAddHis extends BizDataDefItem implements Serializable {

	private static final long serialVersionUID = 8198818351857719954L;
	
	/** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData;

    /** full constructor */
    public BizDataDefItemAddHis(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int timeFormula, dmdd.dmddjava.dataaccess.dataobject.BizData itemBizData) {
        super(indicator, version, bizData);
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BizDataDefItemAddHis() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_ADDHIS );
    }

    /** minimal constructor */
    public BizDataDefItemAddHis(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
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
