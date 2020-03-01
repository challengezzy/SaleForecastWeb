package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class Organization implements Serializable {
	
	public final static long serialVersionUID = -1150000005;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** persistent field */
    private int isCatalog;

    /** persistent field */
    private int isValid;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization parentOrganization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer organizationLayer;

    /** persistent field */
    private Set<OrganizationOrgCharacter> organizationOrgCharacters;

    /** persistent field */
    private Set<Organization> subOrganizations;
    
    private String pathCode;
    
    private Long dcId;//所属分销中心ID, 不使用对象，直接作为普通字段维护
    
    private Long organizationLayerId;

    public Long getOrganizationLayerId() {
		return organizationLayerId;
	}

	public void setOrganizationLayerId(Long organizationLayerId) {
		this.organizationLayerId = organizationLayerId;
	}

	/** full constructor */
    public Organization(String code, String name, int isCatalog, int isValid, String description, 
    		String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.Organization parentOrganization, 
    		dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer organizationLayer, 
    		Set<OrganizationOrgCharacter> organizationOrgCharacters, Set<Organization> subOrganizations,
    		String pathCode,Long dcId) {
        this.code = code;
        this.name = name;
        this.isCatalog = isCatalog;
        this.isValid = isValid;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.parentOrganization = parentOrganization;
        this.organizationLayer = organizationLayer;
        this.organizationOrgCharacters = organizationOrgCharacters;
        this.subOrganizations = subOrganizations;
        this.pathCode= pathCode;
        this.dcId = dcId;
    }

    public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	/** default constructor */
    public Organization() {
    }
    
    public Organization(Long id) {
        this.id = id;
    }

    /** minimal constructor */
    public Organization(String code, String name, int isCatalog, int isValid, dmdd.dmddjava.dataaccess.dataobject.Organization parentOrganization, Set<OrganizationOrgCharacter> organizationOrgCharacters, Set<Organization> subOrganizations) {
        this.code = code;
        this.name = name;
        this.isCatalog = isCatalog;
        this.isValid = isValid;
        this.parentOrganization = parentOrganization;
        this.organizationOrgCharacters = organizationOrgCharacters;
        this.subOrganizations = subOrganizations;
    }

    
    public Long getDcId() {
		return dcId;
	}

	public void setDcId(Long dcId) {
		this.dcId = dcId;
	}

	public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsCatalog() {
        return this.isCatalog;
    }

    public void setIsCatalog(int isCatalog) {
        this.isCatalog = isCatalog;
    }

    public int getIsValid() {
        return this.isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public dmdd.dmddjava.dataaccess.dataobject.Organization getParentOrganization() {
        return this.parentOrganization;
    }

    public void setParentOrganization(dmdd.dmddjava.dataaccess.dataobject.Organization parentOrganization) {
        this.parentOrganization = parentOrganization;
    }

    public dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer getOrganizationLayer() {
        return this.organizationLayer;
    }

    public void setOrganizationLayer(dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer organizationLayer) {
        this.organizationLayer = organizationLayer;
    }

    public Set<OrganizationOrgCharacter> getOrganizationOrgCharacters() {
        return this.organizationOrgCharacters;
    }

    public void setOrganizationOrgCharacters(Set<OrganizationOrgCharacter> organizationOrgCharacters) {
        this.organizationOrgCharacters = organizationOrgCharacters;
    }
    
    public void addOrganizationOrgCharacter(OrganizationOrgCharacter _organizationOrgCharacter) {
    	if( _organizationOrgCharacter == null )
    	{
    		return;
    	}
    	if( this.organizationOrgCharacters == null )
    	{
    		this.organizationOrgCharacters = new HashSet<OrganizationOrgCharacter>();
    	}
    	_organizationOrgCharacter.setOrganization( this );
        this.organizationOrgCharacters.add( _organizationOrgCharacter );
    }      

    public Set<Organization> getSubOrganizations() {
        return this.subOrganizations;
    }

    public void setSubOrganizations(Set<Organization> subOrganizations) {
        this.subOrganizations = subOrganizations;
    }
    
    public void addSubOrganization(Organization _subOrganization) {
    	if( _subOrganization == null )
    	{
    		return;
    	}
    	if( this.subOrganizations == null )
    	{
    		this.subOrganizations = new HashSet<Organization>();
    	}
    	_subOrganization.setParentOrganization( this );
        this.subOrganizations.add( _subOrganization );
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
		Organization other = (Organization) obj;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
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
		return result;
	}

}
