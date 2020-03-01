package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BOrganizationOrgCharacter implements Serializable {
	
	public final static long serialVersionUID = -1150000009;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganization organization;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter organizationCharacter;

    /** full constructor */
    public BOrganizationOrgCharacter(Long version, dmdd.dmddjava.dataaccess.bizobject.BOrganization organization, dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter organizationCharacter) {
        this.version = version;
        this.organization = organization;
        this.organizationCharacter = organizationCharacter;
    }

    /** default constructor */
    public BOrganizationOrgCharacter() {
    }

    /** minimal constructor */
    public BOrganizationOrgCharacter(dmdd.dmddjava.dataaccess.bizobject.BOrganization organization, dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter organizationCharacter) {
        this.organization = organization;
        this.organizationCharacter = organizationCharacter;
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

    public dmdd.dmddjava.dataaccess.bizobject.BOrganization getOrganization() {
        return this.organization;
    }

    public void setOrganization(dmdd.dmddjava.dataaccess.bizobject.BOrganization organization) {
        this.organization = organization;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter getOrganizationCharacter() {
        return this.organizationCharacter;
    }

    public void setOrganizationCharacter(dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter organizationCharacter) {
        this.organizationCharacter = organizationCharacter;
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
		BOrganizationOrgCharacter other = (BOrganizationOrgCharacter) obj;
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
		if( organizationCharacter == null )
		{
			if( other.organizationCharacter != null )
				return false;
		}
		else if( !organizationCharacter.equals( other.organizationCharacter ) )
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
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((organizationCharacter == null) ? 0 : organizationCharacter.hashCode());
		return result;
	}

}
