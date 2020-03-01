package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BForecastModelMLEs extends BForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000011;

    /** persistent field */
    private Double smoothingFactor;

    /** persistent field */
    private int initDataPeriodNum;

    /** full constructor */
    public BForecastModelMLEs(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData, Double smoothingFactor, int initDataPeriodNum) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.smoothingFactor = smoothingFactor;
        this.initDataPeriodNum = initDataPeriodNum;
    }

    /** default constructor */
    public BForecastModelMLEs() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_LES );
    }

    /** minimal constructor */
    public BForecastModelMLEs(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Double smoothingFactor, int initDataPeriodNum) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.smoothingFactor = smoothingFactor;
        this.initDataPeriodNum = initDataPeriodNum;
    }

    public Double getSmoothingFactor() {
        return this.smoothingFactor;
    }

    public void setSmoothingFactor(Double smoothingFactor) {
        this.smoothingFactor = smoothingFactor;
    }

    public int getInitDataPeriodNum() {
        return this.initDataPeriodNum;
    }

    public void setInitDataPeriodNum(int initDataPeriodNum) {
        this.initDataPeriodNum = initDataPeriodNum;
    }

    public String toString() {
        return super.toString();
    }

}
