package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import dmdd.dmddjava.common.constant.BizConst;


/** @author Hibernate CodeGenerator */
public class ForecastModelMLWma extends ForecastModelM implements Serializable {
	
	public final static long serialVersionUID = -1060000014;

    /** persistent field */
    private int dataPeriodNum;

    /** persistent field */
    private Set<ForecastModelMLWmaItem> forecastModelMLWmaItems;

    /** full constructor */
    public ForecastModelMLWma(String code, String name, int type, String indicator, int source, String description, String comments, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData historyBizData, int dataPeriodNum, Set<ForecastModelMLWmaItem> forecastModelMLWmaItems) {
        super(code, name, type, indicator, source, description, comments, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt, version, historyBizData);
        this.dataPeriodNum = dataPeriodNum;
        this.forecastModelMLWmaItems = forecastModelMLWmaItems;
    }

    /** default constructor */
    public ForecastModelMLWma() {
    	this.setIndicator( BizConst.FORECASTMODELM_INDICATOR_LWMA );
    }

    /** minimal constructor */
    public ForecastModelMLWma(String code, String name, int type, String indicator, int source, Double outlierFactor, int outlierAnalyzePeriodNum, int outlierDataIsAutoAdjust, Double outlierDataAdjustHistoryWgt, Double outlierDataAdjustComputeWgt, int dataPeriodNum, Set<ForecastModelMLWmaItem> forecastModelMLWmaItems) {
      super(code, name, type, indicator, source, outlierFactor, outlierAnalyzePeriodNum, outlierDataIsAutoAdjust, outlierDataAdjustHistoryWgt, outlierDataAdjustComputeWgt);
        this.dataPeriodNum = dataPeriodNum;
        this.forecastModelMLWmaItems = forecastModelMLWmaItems;
    }

    public int getDataPeriodNum() {
        return this.dataPeriodNum;
    }

    public void setDataPeriodNum(int dataPeriodNum) {
        this.dataPeriodNum = dataPeriodNum;
    }

    public Set<ForecastModelMLWmaItem> getForecastModelMLWmaItems() {
        return this.forecastModelMLWmaItems;
    }

    public void setForecastModelMLWmaItems(Set<ForecastModelMLWmaItem> forecastModelMLWmaItems) {
        this.forecastModelMLWmaItems = forecastModelMLWmaItems;
    }
    
    public void addForecastModelMLWmaItem(ForecastModelMLWmaItem _forecastModelMLWmaItem) {
    	if( _forecastModelMLWmaItem == null )
    	{
    		return;
    	}
    	if( this.forecastModelMLWmaItems == null )
    	{
    		this.forecastModelMLWmaItems = new HashSet<ForecastModelMLWmaItem>();
    	}
    	_forecastModelMLWmaItem.setForecastModelMLWma( this );
        this.forecastModelMLWmaItems.add( _forecastModelMLWmaItem );
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
