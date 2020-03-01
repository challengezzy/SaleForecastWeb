package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMSLWma extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000019;

    /** persistent field */
    private int dataPeriodNum;

    /** persistent field */
    private Double seasonSmoothingFactor;

    /** persistent field */
    private Set<ForecastModelMSLWmaItem> forecastModelMSLWmaItems;

    /** full constructor */
    public ForecastModelMSLWma(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, int dataPeriodNum, Double seasonSmoothingFactor, Set<ForecastModelMSLWmaItem> forecastModelMSLWmaItems) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.dataPeriodNum = dataPeriodNum;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
        this.forecastModelMSLWmaItems = forecastModelMSLWmaItems;
    }

    /** default constructor */
    public ForecastModelMSLWma() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_SLWMA );
    }

    /** minimal constructor */
    public ForecastModelMSLWma(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, int dataPeriodNum, Double seasonSmoothingFactor, Set<ForecastModelMSLWmaItem> forecastModelMSLWmaItems) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.dataPeriodNum = dataPeriodNum;
        this.seasonSmoothingFactor = seasonSmoothingFactor;
        this.forecastModelMSLWmaItems = forecastModelMSLWmaItems;
    }

    public int getDataPeriodNum() {
        return this.dataPeriodNum;
    }

    public void setDataPeriodNum(int dataPeriodNum) {
        this.dataPeriodNum = dataPeriodNum;
    }

    public Double getSeasonSmoothingFactor() {
        return this.seasonSmoothingFactor;
    }

    public void setSeasonSmoothingFactor(Double seasonSmoothingFactor) {
        this.seasonSmoothingFactor = seasonSmoothingFactor;
    }

    public Set<ForecastModelMSLWmaItem> getForecastModelMSLWmaItems() {
        return this.forecastModelMSLWmaItems;
    }

    public void setForecastModelMSLWmaItems(Set<ForecastModelMSLWmaItem> forecastModelMSLWmaItems) {
        this.forecastModelMSLWmaItems = forecastModelMSLWmaItems;
    }
    
    public void addForecastModelMSLWmaItem(ForecastModelMSLWmaItem _forecastModelMSLWmaItem) {
    	if( _forecastModelMSLWmaItem == null )
    	{
    		return;
    	}
    	if( this.forecastModelMSLWmaItems == null )
    	{
    		this.forecastModelMSLWmaItems = new HashSet<ForecastModelMSLWmaItem>();
    	}
    	_forecastModelMSLWmaItem.setForecastModelMSLWma( this );
        this.forecastModelMSLWmaItems.add( _forecastModelMSLWmaItem );
    }      

    public String toString() {
        return super.toString();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( !super.equals( obj ) )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		return true;
	}

}
