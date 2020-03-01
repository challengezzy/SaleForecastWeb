/**********************************************************************
 *$RCSfile:BConversionType.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
/**
 * <li>Title: BConversionType.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BConversionType implements Serializable 
{
	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;
    
    /** persistent field */
    private long proportion;

    /** persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BUnitGroup unitGroup;

    /** full constructor */
    public BConversionType(String code, String name,long proportion, Long version,dmdd.dmddjava.dataaccess.bizobject.BUnitGroup unitGroup) {
        this.code = code;
        this.name = name;
        this.proportion = proportion;
        this.version = version;
        this.unitGroup = unitGroup;
        
    }

    /** default constructor */
    public BConversionType() {
    }

    /** minimal constructor */
    public BConversionType(String code, String name) {
        this.code = code;
        this.name = name;

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
    
    public void setProportion(long proportion)
    {
    	this.proportion = proportion;
    }

    public long getProportion()
    {
    	return this.proportion;
    }
    
    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setUnitGroup(BUnitGroup unitGroup)
    {
    	this.unitGroup = unitGroup;
    }
    
    public BUnitGroup getUnitGroup()
    {
    	return this.unitGroup;
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
		BConversionType other = (BConversionType) obj;
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
/**********************************************************************
 *$RCSfile:BConversionType.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:BConversionType.java,v $
 *********************************************************************/