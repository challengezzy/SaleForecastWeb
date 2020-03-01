package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMAAnalog extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000037;

    /** persistent field */
    private int offsetPeriodNum;

    /** persistent field */
    private Set<ForecastModelMAAnalogItem> forecastModelMAAnalogItems;

    /** full constructor */
    public ForecastModelMAAnalog(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, int offsetPeriodNum, Set<ForecastModelMAAnalogItem> forecastModelMAAnalogItems) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.offsetPeriodNum = offsetPeriodNum;
        this.forecastModelMAAnalogItems = forecastModelMAAnalogItems;
    }

    /** default constructor */
    public ForecastModelMAAnalog() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_AANALOG );
    }

    /** minimal constructor */
    public ForecastModelMAAnalog(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, int offsetPeriodNum, Set<ForecastModelMAAnalogItem> forecastModelMAAnalogItems) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.offsetPeriodNum = offsetPeriodNum;
        this.forecastModelMAAnalogItems = forecastModelMAAnalogItems;
    }

    public int getOffsetPeriodNum() {
        return this.offsetPeriodNum;
    }

    public void setOffsetPeriodNum(int offsetPeriodNum) {
        this.offsetPeriodNum = offsetPeriodNum;
    }

    public Set<ForecastModelMAAnalogItem> getForecastModelMAAnalogItems() {
        return this.forecastModelMAAnalogItems;
    }

    public void setForecastModelMAAnalogItems(Set<ForecastModelMAAnalogItem> forecastModelMAAnalogItems) {
        this.forecastModelMAAnalogItems = forecastModelMAAnalogItems;
    }
    
    public void addForecastModelMAAnalogItem(ForecastModelMAAnalogItem _forecastModelMAAnalogItem) {
    	if( _forecastModelMAAnalogItem == null )
    	{
    		return;
    	}
    	if( this.forecastModelMAAnalogItems == null )
    	{
    		this.forecastModelMAAnalogItems = new HashSet<ForecastModelMAAnalogItem>();
    	}
    	_forecastModelMAAnalogItem.setForecastModelMAAnalog( this );
        this.forecastModelMAAnalogItems.add( _forecastModelMAAnalogItem );
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
