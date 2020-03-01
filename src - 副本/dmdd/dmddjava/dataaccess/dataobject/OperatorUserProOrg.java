package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class OperatorUserProOrg implements Serializable {
	
	public final static long serialVersionUID = -1150000004;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** full constructor */
    public OperatorUserProOrg(Long version, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser, dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.version = version;
        this.organization = organization;
        this.operatorUser = operatorUser;
        this.product = product;
    }

    /** default constructor */
    public OperatorUserProOrg() {
    }

    /** minimal constructor */
    public OperatorUserProOrg(dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser) {
        this.operatorUser = operatorUser;
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

    public dmdd.dmddjava.dataaccess.dataobject.OperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser) {
        this.operatorUser = operatorUser;
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
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		OperatorUserProOrg other = (OperatorUserProOrg) obj;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( operatorUser == null )
		{
			if( other.operatorUser != null )
				return false;
		}
		else if( !operatorUser.equals( other.operatorUser ) )
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((operatorUser == null) ? 0 : operatorUser.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

}
