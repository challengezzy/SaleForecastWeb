package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMSTEsmo extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000025;

    /** persistent field */
    private Double levelSmoothingFactor;

    /** persistent field */
    private Double trendSmoothingFactor;

    /** persistent field */
    private int trendDampingIsValid;

    /** persistent field */
    private Double trendDampingFactor;

    /** persistent field */
    private Double seasonSmoothingFactor;

    /** persistent field */
    private int initDataPeriodNum;

    /** full constructor */
    public ForecastModelMSTEsmo(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, Double levelSmoothingFactor, Double trendSmoothingFactor, int trendDampingIsValid, Double trendDampingFactor, Double seasonSmoothingFactor, int initDataPeriodNum) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.levelSmoothingFactor = levelSmoothingFactor;
        this.trendSmoothingFactor = trendSmoothingFactor;
        this.trendDampingIsValid = trendDampingIsValid;
        this.trendDampingFactor = trendDampingFactor;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
        this.initDataPeriodNum = initDataPeriodNum;
    }

    /** default constructor */
    public ForecastModelMSTEsmo() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_STESMO );
    }

    /** minimal constructor */
    public ForecastModelMSTEsmo(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Double levelSmoothingFactor, Double trendSmoothingFactor, int trendDampingIsValid, Double trendDampingFactor, Double seasonSmoothingFactor, int initDataPeriodNum) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.levelSmoothingFactor = levelSmoothingFactor;
        this.trendSmoothingFactor = trendSmoothingFactor;
        this.trendDampingIsValid = trendDampingIsValid;
        this.trendDampingFactor = trendDampingFactor;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
        this.initDataPeriodNum = initDataPeriodNum;
    }

    public Double getLevelSmoothingFactor() {
        return this.levelSmoothingFactor;
    }

    public void setLevelSmoothingFactor(Double levelSmoothingFactor) {
        this.levelSmoothingFactor = levelSmoothingFactor;
    }

    public Double getTrendSmoothingFactor() {
        return this.trendSmoothingFactor;
    }

    public void setTrendSmoothingFactor(Double trendSmoothingFactor) {
        this.trendSmoothingFactor = trendSmoothingFactor;
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
