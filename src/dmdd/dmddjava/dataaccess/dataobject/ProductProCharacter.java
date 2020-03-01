package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class ProductProCharacter implements Serializable {
	
	public final static long serialVersionUID = -1160000006;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** full constructor */
    public ProductProCharacter(Long version, dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter, dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.version = version;
        this.productCharacter = productCharacter;
        this.product = product;
    }

    /** default constructor */
    public ProductProCharacter() {
    }

    /** minimal constructor */
    public ProductProCharacter(dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter, dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.productCharacter = productCharacter;
        this.product = product;
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

    public dmdd.dmddjava.dataaccess.dataobject.ProductCharacter getProductCharacter() {
        return this.productCharacter;
    }

    public void setProductCharacter(dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter) {
        this.productCharacter = productCharacter;
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
		ProductProCharacter other = (ProductProCharacter) obj;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( product == null )
		{
			if( other.product != null )
				return false;
		}
		else if( !product.equals( other.product ) )
			return false;
		if( productCharacter == null )
		{
			if( other.productCharacter != null )
				return false;
		}
		else if( !productCharacter.equals( other.productCharacter ) )
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
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((productCharacter == null) ? 0 : productCharacter.hashCode());
		return result;
	}

}
