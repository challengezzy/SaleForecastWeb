package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Date;


/** @author Hibernate CodeGenerator */
public class BForecastErrorMappingModel implements Serializable {
	
	public final static long serialVersionUID = -1060000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String forecastInstCode;

    /** persistent field */
    private String forecastInstName;

    /** nullable persistent field */
    private String forecastModelCode;

    /** nullable persistent field */
    private String forecastModelName;

    /** persistent field */
    private int compilePeriod;

    /** persistent field */
    private int outlierAnalyzePeriodNum;

    /** persistent field */
    private Double errorThreshold;

    /** persistent field */
    private Double et;

    /** persistent field */
    private Double mape;

    /** persistent field */
    private Double mad;
    
    private Double bias;

    /** persistent field */
    private Double ts;

    /** persistent field */
    private Double mse;

    /** persistent field */
    private Date producedTime;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProductLayer runProductLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer runOrganizationLayer;

    /** full constructor */
    public BForecastErrorMappingModel(String forecastInstCode, String forecastInstName, String forecastModelCode, String forecastModelName, int compilePeriod, int outlierAnalyzePeriodNum, Double errorThreshold, Double et, Double mape, Double mad, Double ts, Double mse, Date producedTime, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BProductLayer runProductLayer, dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer runOrganizationLayer) {
        this.forecastInstCode = forecastInstCode;
        this.forecastInstName = forecastInstName;
        this.forecastModelCode = forecastModelCode;
        this.forecastModelName = forecastModelName;
        this.compilePeriod = compilePeriod;
        this.outlierAnalyzePeriodNum = outlierAnalyzePeriodNum;
        this.errorThreshold = errorThreshold;
        this.et = et;
        this.mape = mape;
        this.mad = mad;
        this.ts = ts;
        this.mse = mse;
        this.producedTime = producedTime;
        this.comments = comments;
        this.version = version;
        this.runProductLayer = runProductLayer;
        this.runOrganizationLayer = runOrganizationLayer;
    }

    /** default constructor */
    public BForecastErrorMappingModel() {
    }

    /** minimal constructor */
    public BForecastErrorMappingModel(String forecastInstCode, String forecastInstName, int compilePeriod, int outlierAnalyzePeriodNum, Double errorThreshold, Double et, Double mape, Double mad, Double ts, Double mse, Date producedTime) {
        this.forecastInstCode = forecastInstCode;
        this.forecastInstName = forecastInstName;
        this.compilePeriod = compilePeriod;
        this.outlierAnalyzePeriodNum = outlierAnalyzePeriodNum;
        this.errorThreshold = errorThreshold;
        this.et = et;
        this.mape = mape;
        this.mad = mad;
        this.ts = ts;
        this.mse = mse;
        this.producedTime = producedTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForecastInstCode() {
        return this.forecastInstCode;
    }

    public void setForecastInstCode(String forecastInstCode) {
        this.forecastInstCode = forecastInstCode;
    }

    public String getForecastInstName() {
        return this.forecastInstName;
    }

    public void setForecastInstName(String forecastInstName) {
        this.forecastInstName = forecastInstName;
    }

    public String getForecastModelCode() {
        return this.forecastModelCode;
    }

    public void setForecastModelCode(String forecastModelCode) {
        this.forecastModelCode = forecastModelCode;
    }

    public String getForecastModelName() {
        return this.forecastModelName;
    }

    public void setForecastModelName(String forecastModelName) {
        this.forecastModelName = forecastModelName;
    }

    public int getCompilePeriod() {
        return this.compilePeriod;
    }

    public void setCompilePeriod(int compilePeriod) {
        this.compilePeriod = compilePeriod;
    }

    public int getOutlierAnalyzePeriodNum() {
        return this.outlierAnalyzePeriodNum;
    }

    public void setOutlierAnalyzePeriodNum(int outlierAnalyzePeriodNum) {
        this.outlierAnalyzePeriodNum = outlierAnalyzePeriodNum;
    }

    public Double getErrorThreshold() {
        return this.errorThreshold;
    }

    public void setErrorThreshold(Double errorThreshold) {
        this.errorThreshold = errorThreshold;
    }

    public Double getEt() {
        return this.et;
    }

    public void setEt(Double et) {
        this.et = et;
    }

    public Double getMape() {
        return this.mape;
    }

    public void setMape(Double mape) {
        this.mape = mape;
    }

    public Double getBias() {
		return bias;
	}

	public void setBias(Double bias) {
		this.bias = bias;
	}

	public Double getMad() {
        return this.mad;
    }

    public void setMad(Double mad) {
        this.mad = mad;
    }

    public Double getTs() {
        return this.ts;
    }

    public void setTs(Double ts) {
        this.ts = ts;
    }

    public Double getMse() {
        return this.mse;
    }

    public void setMse(Double mse) {
        this.mse = mse;
    }

    public Date getProducedTime() {
        return this.producedTime;
    }

    public void setProducedTime(Date producedTime) {
        this.producedTime = producedTime;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BProductLayer getRunProductLayer() {
        return this.runProductLayer;
    }

    public void setRunProductLayer(dmdd.dmddjava.dataaccess.bizobject.BProductLayer runProductLayer) {
        this.runProductLayer = runProductLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer getRunOrganizationLayer() {
        return this.runOrganizationLayer;
    }

    public void setRunOrganizationLayer(dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer runOrganizationLayer) {
        this.runOrganizationLayer = runOrganizationLayer;
    }

    public String toString() {
        return "" + this.id;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		BForecastErrorMappingModel other = (BForecastErrorMappingModel) obj;
		if( compilePeriod != other.compilePeriod )
			return false;
		if( forecastInstCode == null )
		{
			if( other.forecastInstCode != null )
				return false;
		}
		else if( !forecastInstCode.equals( other.forecastInstCode ) )
			return false;
		if( forecastInstName == null )
		{
			if( other.forecastInstName != null )
				return false;
		}
		else if( !forecastInstName.equals( other.forecastInstName ) )
			return false;
		if( forecastModelCode == null )
		{
			if( other.forecastModelCode != null )
				return false;
		}
		else if( !forecastModelCode.equals( other.forecastModelCode ) )
			return false;
		if( forecastModelName == null )
		{
			if( other.forecastModelName != null )
				return false;
		}
		else if( !forecastModelName.equals( other.forecastModelName ) )
			return false;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( producedTime == null )
		{
			if( other.producedTime != null )
				return false;
		}
		else if( !producedTime.equals( other.producedTime ) )
			return false;
		if( runOrganizationLayer == null )
		{
			if( other.runOrganizationLayer != null )
				return false;
		}
		else if( !runOrganizationLayer.equals( other.runOrganizationLayer ) )
			return false;
		if( runProductLayer == null )
		{
			if( other.runProductLayer != null )
				return false;
		}
		else if( !runProductLayer.equals( other.runProductLayer ) )
			return false;
		return true;
	}

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + compilePeriod;
		result = prime * result + ((forecastInstCode == null) ? 0 : forecastInstCode.hashCode());
		result = prime * result + ((forecastInstName == null) ? 0 : forecastInstName.hashCode());
		result = prime * result + ((forecastModelCode == null) ? 0 : forecastModelCode.hashCode());
		result = prime * result + ((forecastModelName == null) ? 0 : forecastModelName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((producedTime == null) ? 0 : producedTime.hashCode());
		result = prime * result + ((runOrganizationLayer == null) ? 0 : runOrganizationLayer.hashCode());
		result = prime * result + ((runProductLayer == null) ? 0 : runProductLayer.hashCode());
		return result;
	}

}
