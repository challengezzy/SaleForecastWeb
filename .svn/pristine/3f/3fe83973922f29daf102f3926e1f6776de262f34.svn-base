package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class ForecastInstProOrg implements Serializable {
	
	public final static long serialVersionUID = -1060000005;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst;

    /** full constructor */
    public ForecastInstProOrg(Long version, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.Product product, dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst) {
        this.version = version;
        this.organization = organization;
        this.product = product;
        this.forecastInst = forecastInst;
    }

    /** default constructor */
    public ForecastInstProOrg() {
    }

    /** minimal constructor */
    public ForecastInstProOrg(dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst) {
        this.forecastInst = forecastInst;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public dmdd.dmddjava.dataaccess.dataobject.ForecastInst getForecastInst() {
        return this.forecastInst;
    }

    public void setForecastInst(dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst) {
        this.forecastInst = forecastInst;
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
		ForecastInstProOrg other = (ForecastInstProOrg) obj;
		if( forecastInst == null )
		{
			if( other.forecastInst != null )
				return false;
		}
		else if( !forecastInst.equals( other.forecastInst ) )
			return false;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( organization == null )
		{
			if( other.organization != null )
				return false;
		}
		else if( !organization.equals( other.organization ) )
			return false;
		if( product == null )
		{
			if( other.product != null )
				return false;
		}
		else if( !product.equals( other.product ) )
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
		result = prime * result + ((forecastInst == null) ? 0 : forecastInst.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

}
