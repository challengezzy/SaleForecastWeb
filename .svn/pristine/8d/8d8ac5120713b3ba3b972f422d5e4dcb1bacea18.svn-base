package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMSTLa extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000026;

    /** persistent field */
    private int dataPeriodNum;

    /** persistent field */
    private int trendDampingIsValid;

    /** persistent field */
    private Double trendDampingFactor;

    /** persistent field */
    private Double seasonSmoothingFactor;

    /** full constructor */
    public ForecastModelMSTLa(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, int dataPeriodNum, int trendDampingIsValid, Double trendDampingFactor, Double seasonSmoothingFactor) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.dataPeriodNum = dataPeriodNum;
        this.trendDampingIsValid = trendDampingIsValid;
        this.trendDampingFactor = trendDampingFactor;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
    }

    /** default constructor */
    public ForecastModelMSTLa() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_STLA );
    }

    /** minimal constructor */
    public ForecastModelMSTLa(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, int dataPeriodNum, int trendDampingIsValid, Double trendDampingFactor, Double seasonSmoothingFactor) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.dataPeriodNum = dataPeriodNum;
        this.trendDampingIsValid = trendDampingIsValid;
        this.trendDampingFactor = trendDampingFactor;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
    }

    public int getDataPeriodNum() {
        return this.dataPeriodNum;
    }

    public void setDataPeriodNum(int dataPeriodNum) {
        this.dataPeriodNum = dataPeriodNum;
    }

    public int getTrendDampingIsValid() {
        return this.trendDampingIsValid;
    }

    public void setTrendDampingIsValid(int trendDampingIsValid) {
        this.trendDampingIsValid = trendDampingIsValid;
    }

    public Double getTrendDampingFactor() {
        return this.trendDampingFactor;
    }

    public void setTrendDampingFactor(Double trendDampingFactor) {
        this.trendDampingFactor = trendDampingFactor;
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
