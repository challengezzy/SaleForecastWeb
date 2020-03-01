package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BForecastModelMLWmaItem implements Serializable {
	
	public final static long serialVersionUID = -1060000015;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int periodSeqNo;

    /** persistent field */
    private Double coefficient;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWma forecastModelMLWma;

    /** full constructor */
    public BForecastModelMLWmaItem(int periodSeqNo, Double coefficient, Long version, dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWma forecastModelMLWma) {
        this.periodSeqNo = periodSeqNo;
        this.coefficient = coefficient;
        this.version = version;
        this.forecastModelMLWma = forecastModelMLWma;
    }

    /** default constructor */
    public BForecastModelMLWmaItem() {
    }

    /** minimal constructor */
    public BForecastModelMLWmaItem(int periodSeqNo, Double coefficient, dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWma forecastModelMLWma) {
        this.periodSeqNo = periodSeqNo;
        this.coefficient = coefficient;
        this.forecastModelMLWma = forecastModelMLWma;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriodSeqNo() {
        return this.periodSeqNo;
    }

    public void setPeriodSeqNo(int periodSeqNo) {
        this.periodSeqNo = periodSeqNo;
    }

    public Double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWma getForecastModelMLWma() {
        return this.forecastModelMLWma;
    }

    public void setForecastModelMLWma(dmdd.dmddjava.dataaccess.bizobject.BForecastModelMLWma forecastModelMLWma) {
        this.forecastModelMLWma = forecastModelMLWma;
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
		BForecastModelMLWmaItem other = (BForecastModelMLWmaItem) obj;
		if( forecastModelMLWma == null )
		{
			if( other.forecastModelMLWma != null )
				return false;
		}
		else if( !forecastModelMLWma.equals( other.forecastModelMLWma ) )
			return false;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( periodSeqNo != other.periodSeqNo )
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
		result = prime * result + ((forecastModelMLWma == null) ? 0 : forecastModelMLWma.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + periodSeqNo;
		return result;
	}

}
