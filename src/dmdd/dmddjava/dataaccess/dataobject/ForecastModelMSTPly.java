package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMSTPly extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000027;

    /** persistent field */
    private Double percentValue;

    /** persistent field */
    private Double seasonSmoothingFactor;

    /** full constructor */
    public ForecastModelMSTPly(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, Double percentValue, Double seasonSmoothingFactor) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.percentValue = percentValue;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
    }

    /** default constructor */
    public ForecastModelMSTPly() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_STPLY );
    }

    /** minimal constructor */
    public ForecastModelMSTPly(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Double percentValue, Double seasonSmoothingFactor) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.percentValue = percentValue;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
    }

    public Double getPercentValue() {
        return this.percentValue;
    }

    public void setPercentValue(Double percentValue) {
        this.percentValue = percentValue;
    }

    public Double getSeasonSmoothingFactor() {
        return this.seasonSmoothingFactor;
    }

    public void setSeasonSmoothingFactor(Double seasonSmoothingFactor) {
        this.seasonSmoothingFactor = seasonSmoothingFactor;
    }

    public String toString() {
        return super.toString();
    }

}
