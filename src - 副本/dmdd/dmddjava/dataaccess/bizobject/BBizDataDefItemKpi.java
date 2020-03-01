package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItemKpi extends BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000007;

    /** persistent field */
    private int kpiFormula;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData bitemBizData;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData aitemBizData;

    /** full constructor */
    public BBizDataDefItemKpi(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int kpiFormula, dmdd.dmddjava.dataaccess.bizobject.BBizData bitemBizData, dmdd.dmddjava.dataaccess.bizobject.BBizData aitemBizData) {
        super(indicator, version, bizData);
        this.kpiFormula = kpiFormula;
        this.bitemBizData = bitemBizData;
        this.aitemBizData = aitemBizData;
    }

    /** default constructor */
    public BBizDataDefItemKpi() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_KPI );
    }

    /** minimal constructor */
    public BBizDataDefItemKpi(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int kpiFormula) {
      super(indicator, bizData);
        this.kpiFormula = kpiFormula;
    }

    public int getKpiFormula() {
        return this.kpiFormula;
    }

    public void setKpiFormula(int kpiFormula) {
        this.kpiFormula = kpiFormula;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getBitemBizData() {
        return this.bitemBizData;
    }

    public void setBitemBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData bitemBizData) {
        this.bitemBizData = bitemBizData;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getAitemBizData() {
        return this.aitemBizData;
    }

    public void setAitemBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData aitemBizData) {
        this.aitemBizData = aitemBizData;
    }

    public String toString() {
        return super.toString();
    }

}
