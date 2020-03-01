/**********************************************************************
 *$RCSfile:BConversoinSet.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

/**
 * <li>Title: BConversoinSet.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BConversionSet implements Serializable 
{
	private static final long serialVersionUID = 1L;

	 /** identifier field */
    private Long id;
    
    /** persistent field */
    private Long version;
    
	/** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProduct product;
	
    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BConversionType conversionType;
	
	public BConversionSet()
	{
		
	}
	
	public Long getId() {
	     return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	    
	public BConversionSet(Long version,BProduct product,BConversionType conversionType)
	{
		this.version = version;
		this.product = product;
		this.conversionType = conversionType;
	}
	
	public void setProduct(BProduct product)
	{
		this.product = product;
	}
	
	public BProduct getProduct()
	{
		return this.product;
	}
	
	public void setConversionType(BConversionType conversionType)
	{
		this.conversionType = conversionType;
	}
	
	public BConversionType getConversionType()
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
		BConversionSet other = (BConversionSet) obj;
		
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
 *$RCSfile:BConversoinSet.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:BConversoinSet.java,v $
 *********************************************************************/