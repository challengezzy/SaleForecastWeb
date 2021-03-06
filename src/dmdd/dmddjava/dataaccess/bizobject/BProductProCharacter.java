package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BProductProCharacter implements Serializable {
	
	public final static long serialVersionUID = -1160000006;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProductCharacter productCharacter;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProduct product;

    /** full constructor */
    public BProductProCharacter(Long version, dmdd.dmddjava.dataaccess.bizobject.BProductCharacter productCharacter, dmdd.dmddjava.dataaccess.bizobject.BProduct product) {
        this.version = version;
        this.productCharacter = productCharacter;
        this.product = product;
    }

    /** default constructor */
    public BProductProCharacter() {
    }

    /** minimal constructor */
    public BProductProCharacter(dmdd.dmddjava.dataaccess.bizobject.BProductCharacter productCharacter, dmdd.dmddjava.dataaccess.bizobject.BProduct product) {
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

    public dmdd.dmddjava.dataaccess.bizobject.BProductCharacter getProductCharacter() {
        return this.productCharacter;
    }

    public void setProductCharacter(dmdd.dmddjava.dataaccess.bizobject.BProductCharacter productCharacter) {
        this.productCharacter = productCharacter;
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
		BProductProCharacter other = (BProductProCharacter) obj;
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
