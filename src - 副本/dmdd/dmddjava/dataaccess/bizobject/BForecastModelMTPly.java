package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BForecastModelMTPly extends BForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000032;

    /** persistent field */
    private Double percentValue;

    /** full constructor */
    public BForecastModelMTPly(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData, Double percentValue) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.percentValue = percentValue;
    }

    /** default constructor */
    public BForecastModelMTPly() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_TPLY );
    }

    /** minimal constructor */
    public BForecastModelMTPly(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Double percentValue) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.percentValue = percentValue;
    }

    public Double getPercentValue() {
        return this.percentValue;
    }

    public void setPercentValue(Double percentValue) {
        this.percentValue = percentValue;
    }

    public String toString() {
        return super.toString();
    }

}
