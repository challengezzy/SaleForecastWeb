package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BForecastModelMTLa extends BForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000031;

    /** persistent field */
    private int dataPeriodNum;

    /** persistent field */
    private int trendDampingIsValid;

    /** persistent field */
    private Double trendDampingFactor;

    /** full constructor */
    public BForecastModelMTLa(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData, int dataPeriodNum, int trendDampingIsValid, Double trendDampingFactor) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.dataPeriodNum = dataPeriodNum;
        this.trendDampingIsValid = trendDampingIsValid;
        this.trendDampingFactor = trendDampingFactor;
    }

    /** default constructor */
    public BForecastModelMTLa() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_TLA );
    }

    /** minimal constructor */
    public BForecastModelMTLa(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, int dataPeriodNum, int trendDampingIsValid, Double trendDampingFactor) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.dataPeriodNum = dataPeriodNum;
        this.trendDampingIsValid = trendDampingIsValid;
        this.trendDampingFactor = trendDampingFactor;
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

    public String toString() {
        return super.toString();
    }

}
