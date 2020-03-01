package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BHistoryData implements Serializable {
	
	public final static long serialVersionUID = -1080000005;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int period;

    /** persistent field */
    private Double value;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData bizData;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProduct product;

    /** full constructor */
    public BHistoryData(int period, Double value, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, dmdd.dmddjava.dataaccess.bizobject.BOrganization organization, dmdd.dmddjava.dataaccess.bizobject.BProduct product) {
        this.period = period;
        this.value = value;
        this.comments = comments;
        this.version = version;
        this.bizData = bizData;
        this.organization = organization;
        this.product = product;
    }

    /** default constructor */
    public BHistoryData() {
    }

    /** minimal constructor */
    public BHistoryData(int period, Double value) {
        this.period = period;
        this.value = value;
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

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getBizData() {
        return this.bizData;
    }

    public void setBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
        this.bizData = bizData;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganization getOrganization() {
        return this.organization;
    }

    public void setOrganization(dmdd.dmddjava.dataaccess.bizobject.BOrganization organization) {
        this.organization = organization;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BProduct getProduct() {
        return this.product;
    }

    public void setProduct(dmdd.dmddjava.dataaccess.bizobject.BProduct product) {
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
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		BHistoryData other = (BHistoryData) obj;
		if( bizData == null )
		{
			if( other.bizData != null )
				return false;
		}
		else if( !bizData.equals( other.bizData ) )
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
		if( period != other.period )
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
		result = prime * result + ((bizData == null) ? 0 : bizData.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + period;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

}
