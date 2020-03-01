package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class ProductCharacter implements Serializable {
	
	public final static long serialVersionUID = -1160000003;

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
    private dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer productCharacterLayer;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ProductCharacter parentProductCharacter;

    /** persistent field */
    private Set<ProductCharacter> subProductCharacters;

    /** persistent field */
    private Set<ProductProCharacter> productProCharacters;

    private String pathCode;
    
    /** full constructor */
    public ProductCharacter(String code, String name, String type, int isCatalog, String description, String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer productCharacterLayer, dmdd.dmddjava.dataaccess.dataobject.ProductCharacter parentProductCharacter, Set<ProductCharacter> subProductCharacters, Set<ProductProCharacter> productProCharacters,String pathCode) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.isCatalog = isCatalog;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.productCharacterLayer = productCharacterLayer;
        this.parentProductCharacter = parentProductCharacter;
        this.subProductCharacters = subProductCharacters;
        this.productProCharacters = productProCharacters;
        this.pathCode = pathCode;
    }

    /** default constructor */
    public ProductCharacter() {
    }

    /** minimal constructor */
    public ProductCharacter(String code, String name, int isCatalog, dmdd.dmddjava.dataaccess.dataobject.ProductCharacter parentProductCharacter, Set<ProductCharacter> subProductCharacters, Set<ProductProCharacter> productProCharacters) {
        this.code = code;
        this.name = name;
        this.isCatalog = isCatalog;
        this.parentProductCharacter = parentProductCharacter;
        this.subProductCharacters = subProductCharacters;
        this.productProCharacters = productProCharacters;
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

    public dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer getProductCharacterLayer() {
        return this.productCharacterLayer;
    }

    public void setProductCharacterLayer(dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer productCharacterLayer) {
        this.productCharacterLayer = productCharacterLayer;
    }

    public dmdd.dmddjava.dataaccess.dataobject.ProductCharacter getParentProductCharacter() {
        return this.parentProductCharacter;
    }

    public void setParentProductCharacter(dmdd.dmddjava.dataaccess.dataobject.ProductCharacter parentProductCharacter) {
        this.parentProductCharacter = parentProductCharacter;
    }

    public Set<ProductCharacter> getSubProductCharacters() {
        return this.subProductCharacters;
    }

    public void setSubProductCharacters(Set<ProductCharacter> subProductCharacters) {
        this.subProductCharacters = subProductCharacters;
    }
    
    public void addSubProductCharacters(ProductCharacter _subProductCharacter) {
    	if( _subProductCharacter == null )
    	{
    		return;
    	}
    	if( this.subProductCharacters == null )
    	{
    		this.subProductCharacters = new HashSet<ProductCharacter>();
    	}
    	_subProductCharacter.setParentProductCharacter( this );
        this.subProductCharacters.add( _subProductCharacter );
    }      

    public Set<ProductProCharacter> getProductProCharacters() {
        return this.productProCharacters;
    }

    public void setProductProCharacters(Set<ProductProCharacter> productProCharacters) {
        this.productProCharacters = productProCharacters;
    }
    
    public void addProductProCharacters(ProductProCharacter _productProCharacter) {
    	if( _productProCharacter == null )
    	{
    		return;
    	}
    	if( this.productProCharacters == null )
    	{
    		this.productProCharacters = new HashSet<ProductProCharacter>();
    	}
    	_productProCharacter.setProductCharacter( this );
        this.productProCharacters.add( _productProCharacter );
    }       

    public String toString() {
        return "" + this.id;
    }

    public String getPathCode() {
		return pathCode;
	}

	public void setPathCode(String pathCode) {
		this.pathCode = pathCode;
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
		ProductCharacter other = (ProductCharacter) obj;
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
