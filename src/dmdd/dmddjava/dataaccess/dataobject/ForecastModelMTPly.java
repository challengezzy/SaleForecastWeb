package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMTPly extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000032;

    /** persistent field */
    private Double percentValue;

    /** full constructor */
    public ForecastModelMTPly(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, Double percentValue) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.percentValue = percentValue;
    }

    /** default constructor */
    public ForecastModelMTPly() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_TPLY );
    }

    /** minimal constructor */
    public ForecastModelMTPly(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Double percentValue) {
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
