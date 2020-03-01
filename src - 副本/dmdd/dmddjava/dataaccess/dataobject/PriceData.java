package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class PriceData implements Serializable {
	
	public final static long serialVersionUID = -1160000007;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int period;

    /** persistent field */
    private Double standardPrice;

    /** persistent field */
    private Double realPrice;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** full constructor */
    public PriceData(int period, Double standardPrice, Double realPrice, String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.period = period;
        this.standardPrice = standardPrice;
        this.realPrice = realPrice;
        this.comments = comments;
        this.version = version;
        this.organization = organization;
        this.product = product;
    }

    /** default constructor */
    public PriceData() {
    }

    /** minimal constructor */
    public PriceData(int period, Double standardPrice, Double realPrice) {
        this.period = period;
        this.standardPrice = standardPrice;
        this.realPrice = realPrice;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Double getStandardPrice() {
        return this.standardPrice;
    }

    public void setStandardPrice(Double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Double getRealPrice() {
        return this.realPrice;
    }

    public void setRealPrice(Double realPrice) {
        this.realPrice = realPrice;
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
		PriceData other = (PriceData) obj;
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
		if ( period != other.period ) return false;
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
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( organization == null ) ? 0 : organization.hashCode() );
		result = prime * result + period;
		result = prime * result + ( ( product == null ) ? 0 : product.hashCode() );
		return result;
	}

}
