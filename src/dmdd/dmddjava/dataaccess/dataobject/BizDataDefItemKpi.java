package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BizDataDefItemKpi extends BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000007;

    /** persistent field */
    private int kpiFormula;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData bitemBizData;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData aitemBizData;

    /** full constructor */
    public BizDataDefItemKpi(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int kpiFormula, dmdd.dmddjava.dataaccess.dataobject.BizData bitemBizData, dmdd.dmddjava.dataaccess.dataobject.BizData aitemBizData) {
        super(indicator, version, bizData);
        this.kpiFormula = kpiFormula;
        this.bitemBizData = bitemBizData;
        this.aitemBizData = aitemBizData;
    }

    /** default constructor */
    public BizDataDefItemKpi() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_KPI );
    }

    /** minimal constructor */
    public BizDataDefItemKpi(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, int kpiFormula) {
      super(indicator, bizData);
        this.kpiFormula = kpiFormula;
    }

    public int getKpiFormula() {
        return this.kpiFormula;
    }

    public void setKpiFormula(int kpiFormula) {
        this.kpiFormula = kpiFormula;
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getBitemBizData() {
        return this.bitemBizData;
    }

    public void setBitemBizData(dmdd.dmddjava.dataaccess.dataobject.BizData bitemBizData) {
        this.bitemBizData = bitemBizData;
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getAitemBizData() {
        return this.aitemBizData;
    }

    public void setAitemBizData(dmdd.dmddjava.dataaccess.dataobject.BizData aitemBizData) {
        this.aitemBizData = aitemBizData;
    }

    public String toString() {
        return super.toString();
    }

}
