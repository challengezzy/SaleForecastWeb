package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class UiPopbScopeProOrg implements Serializable {
	
	public final static long serialVersionUID = -1210000003;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope;

    /** full constructor */
    public UiPopbScopeProOrg(Long version, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.Product product, dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope) {
        this.version = version;
        this.organization = organization;
        this.product = product;
        this.uiPopbScope = uiPopbScope;
    }

    /** default constructor */
    public UiPopbScopeProOrg() {
    }

    /** minimal constructor */
    public UiPopbScopeProOrg(dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope) {
        this.uiPopbScope = uiPopbScope;
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

    public dmdd.dmddjava.dataaccess.dataobject.UiPopbScope getUiPopbScope() {
        return this.uiPopbScope;
    }

    public void setUiPopbScope(dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope) {
        this.uiPopbScope = uiPopbScope;
    }

    public String toString() {
        return "" + this.id;
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
		result = prime * result + ( ( product == null ) ? 0 : product.hashCode() );
		result = prime * result + ( ( uiPopbScope == null ) ? 0 : uiPopbScope.hashCode() );
		return result;
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
		UiPopbScopeProOrg other = (UiPopbScopeProOrg) obj;
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
		if ( uiPopbScope == null )
		{
			if ( other.uiPopbScope != null ) return false;
		}
		else if ( !uiPopbScope.equals( other.uiPopbScope ) ) return false;
		return true;
	}

}
