package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class ForecastModelMAAnalogItem implements Serializable {
	
	public final static long serialVersionUID = -1060000038;

    /** identifier field */
    private Long id;

    /** persistent field */
    private Double coefficient;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog forecastModelMAAnalog;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** full constructor */
    public ForecastModelMAAnalogItem(Double coefficient, Long version, dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog forecastModelMAAnalog, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.coefficient = coefficient;
        this.version = version;
        this.forecastModelMAAnalog = forecastModelMAAnalog;
        this.organization = organization;
        this.product = product;
    }

    /** default constructor */
    public ForecastModelMAAnalogItem() {
    }

    /** minimal constructor */
    public ForecastModelMAAnalogItem(Double coefficient, dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog forecastModelMAAnalog) {
        this.coefficient = coefficient;
        this.forecastModelMAAnalog = forecastModelMAAnalog;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog getForecastModelMAAnalog() {
        return this.forecastModelMAAnalog;
    }

    public void setForecastModelMAAnalog(dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalog forecastModelMAAnalog) {
        this.forecastModelMAAnalog = forecastModelMAAnalog;
    }

    public dmdd.dmddjava.dataaccess.dataobject.Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(dmdd.dmddjava.dataaccess.dataobject.Organization organization) {
        this.organization = organization;
    }

    public dmdd.dmddjava.dataaccess.dataobject.Product getProduct() {
        return this.product;
    }

    public void setProduct(dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.product = product;
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
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( getClass() != obj.getClass() ) return false;
		ForecastModelMAAnalogItem other = (ForecastModelMAAnalogItem) obj;
		if ( forecastModelMAAnalog == null )
		{
			if ( other.forecastModelMAAnalog != null ) return false;
		}
		else if ( !forecastModelMAAnalog.equals( other.forecastModelMAAnalog ) ) return false;
		if ( id == null )
		{
			if ( other.id != null ) return false;
		}
		else if ( !id.equals( other.id ) ) return false;
		if ( organization == null )
		{
			if ( other.organization != null ) return false;
		}
		else if ( !organization.equals( other.organization ) ) return false;
		if ( product == null )
		{
			if ( other.product != null ) return false;
		}
		else if ( !product.equals( other.product ) ) return false;
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
		result = prime * result + ( ( forecastModelMAAnalog == null ) ? 0 : forecastModelMAAnalog.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( organization == null ) ? 0 : organization.hashCode() );
		result = prime * result + ( ( product == null ) ? 0 : product.hashCode() );
		return result;
	}

}
