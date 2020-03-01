package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemHistoryAdR extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000006;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData historyAdBizData;

    /** full constructor */
    public BizDataDefItemHistoryAdR(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, dmdd.dmddjava.dataaccess.dataobject.BizData historyAdBizData) {
        super(indicator, version, bizData);
        this.historyAdBizData = historyAdBizData;
    }

    /** default constructor */
    public BizDataDefItemHistoryAdR() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_HISTORYADR );
    }

    /** minimal constructor */
    public BizDataDefItemHistoryAdR(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
      super(indicator, bizData);
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getHistoryAdBizData() {
        return this.historyAdBizData;
    }

    public void setHistoryAdBizData(dmdd.dmddjava.dataaccess.dataobject.BizData historyAdBizData) {
        this.historyAdBizData = historyAdBizData;
    }

    public String toString() {
        return super.toString();
    }

}
