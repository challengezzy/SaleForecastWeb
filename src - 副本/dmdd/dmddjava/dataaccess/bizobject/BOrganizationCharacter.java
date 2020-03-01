package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BOrganizationCharacter implements Serializable {
	
	public final static long serialVersionUID = -1150000006;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String type;

    /** persistent field */
    private int isCatalog;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer organizationCharacterLayer;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter parentOrganizationCharacter;

    /** persistent field */
    private Set<BOrganizationCharacter> subOrganizationCharacters;

    /** persistent field */
    private Set<BOrganizationOrgCharacter> organizationOrgCharacters;

    private String pathCode;
    
    /** full constructor */
    public BOrganizationCharacter(String code, String name, String type, int isCatalog, String description, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer organizationCharacterLayer, dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter parentOrganizationCharacter, Set<BOrganizationCharacter> subOrganizationCharacters, Set<BOrganizationOrgCharacter> organizationOrgCharacters,String pathCode) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.isCatalog = isCatalog;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.organizationCharacterLayer = organizationCharacterLayer;
        this.parentOrganizationCharacter = parentOrganizationCharacter;
        this.subOrganizationCharacters = subOrganizationCharacters;
        this.organizationOrgCharacters = organizationOrgCharacters;
        this.pathCode= pathCode;
    }

    public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
	}

	/** default constructor */
    public BOrganizationCharacter() {
    }

    /** minimal constructor */
    public BOrganizationCharacter(String code, String name, int isCatalog, dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter parentOrganizationCharacter, Set<BOrganizationCharacter> subOrganizationCharacters, Set<BOrganizationOrgCharacter> organizationOrgCharacters) {
        this.code = code;
        this.name = name;
        this.isCatalog = isCatalog;
        this.parentOrganizationCharacter = parentOrganizationCharacter;
        this.subOrganizationCharacters = subOrganizationCharacters;
        this.organizationOrgCharacters = organizationOrgCharacters;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsCatalog() {
        return this.isCatalog;
    }

    public void setIsCatalog(int isCatalog) {
        this.isCatalog = isCatalog;
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

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer getOrganizationCharacterLayer() {
        return this.organizationCharacterLayer;
    }

    public void setOrganizationCharacterLayer(dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacterLayer organizationCharacterLayer) {
        this.organizationCharacterLayer = organizationCharacterLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter getParentOrganizationCharacter() {
        return this.parentOrganizationCharacter;
    }

    public void setParentOrganizationCharacter(dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter parentOrganizationCharacter) {
        this.parentOrganizationCharacter = parentOrganizationCharacter;
    }

    public Set<BOrganizationCharacter> getSubOrganizationCharacters() {
        return this.subOrganizationCharacters;
    }

    public void setSubOrganizationCharacters(Set<BOrganizationCharacter> subOrganizationCharacters) {
        this.subOrganizationCharacters = subOrganizationCharacters;
    }
    
    public void addSubOrganizationCharacters(BOrganizationCharacter _subOrganizationCharacter) {
    	if( _subOrganizationCharacter == null )
    	{
    		return;
    	}
    	if( this.subOrganizationCharacters == null )
    	{
    		this.subOrganizationCharacters = new HashSet<BOrganizationCharacter>();
    	}
    	_subOrganizationCharacter.setParentOrganizationCharacter( this );
        this.subOrganizationCharacters.add( _subOrganizationCharacter );
    }     

    public Set<BOrganizationOrgCharacter> getOrganizationOrgCharacters() {
        return this.organizationOrgCharacters;
    }

    public void setOrganizationOrgCharacters(Set<BOrganizationOrgCharacter> organizationOrgCharacters) {
        this.organizationOrgCharacters = organizationOrgCharacters;
    }
    
    public void addOrganizationOrgCharacters(BOrganizationOrgCharacter _organizationOrgCharacter) {
    	if( _organizationOrgCharacter == null )
    	{
    		return;
    	}
    	if( this.organizationOrgCharacters == null )
    	{
    		this.organizationOrgCharacters = new HashSet<BOrganizationOrgCharacter>();
    	}
    	_organizationOrgCharacter.setOrganizationCharacter( this );
        this.organizationOrgCharacters.add( _organizationOrgCharacter );
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
		BOrganizationCharacter other = (BOrganizationCharacter) obj;
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
