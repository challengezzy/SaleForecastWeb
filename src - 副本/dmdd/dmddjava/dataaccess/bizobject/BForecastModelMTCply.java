package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class BForecastModelMTCply extends BForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000028;

    /** persistent field */
    private int dataPeriodNum;

    /** full constructor */
    public BForecastModelMTCply(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData historyBizData, int dataPeriodNum) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.dataPeriodNum = dataPeriodNum;
    }

    /** default constructor */
    public BForecastModelMTCply() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_TCPLY );
    }

    /** minimal constructor */
    public BForecastModelMTCply(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, int dataPeriodNum) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.dataPeriodNum = dataPeriodNum;
    }

    public int getDataPeriodNum() {
        return this.dataPeriodNum;
    }

    public void setDataPeriodNum(int dataPeriodNum) {
        this.dataPeriodNum = dataPeriodNum;
    }

    public String toString() {
        return super.toString();
    }

}
