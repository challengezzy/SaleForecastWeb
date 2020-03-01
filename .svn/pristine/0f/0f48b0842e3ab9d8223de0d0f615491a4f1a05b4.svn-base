/**********************************************************************
 *$RCSfile:ConversionSet.java,v $  $Revision: 1.0 $  $Date:2012-4-13 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

/**
 * <li>Title: ConversionSet.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ConversionSet implements Serializable 
{
	private static final long serialVersionUID = 1L;

	 /** identifier field */
    private Long id;
    
    /** persistent field */
    private Long version;
    
	/** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;
	
    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ConversionType conversionType;
	
	public ConversionSet()
	{
		
	}
	
	public Long getId() {
	     return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	    
	public ConversionSet(Long version,Product product,ConversionType conversionType)
	{
		 this.version = version;
		this.product = product;
		this.conversionType = conversionType;
	}
	
	public void setProduct(Product product)
	{
		this.product = product;
	}
	
	public Product getProduct()
	{
		return this.product;
	}
	
	public void setConversionType(ConversionType conversionType)
	{
		this.conversionType = conversionType;
	}
	
	public ConversionType getConversionType()
	{
		return this.conversionType;
	}
	
	public Long getVersion() {
	        return this.version;
	}

	public void setVersion(Long version) {
	     this.version = version;
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
		ConversionSet other = (ConversionSet) obj;
		
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
		return result;
	}

}

/**********************************************************************
 *$RCSfile:ConversionSet.java,v $  $Revision: 1.0 $  $Date:2012-4-13 $
 *
 *$Log:ConversionSet.java,v $
 *********************************************************************/