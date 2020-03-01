package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String indicator;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData bizData;

    /** full constructor */
    public BizDataDefItem(String indicator, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.indicator = indicator;
        this.version = version;
        this.bizData = bizData;
    }

    /** default constructor */
    public BizDataDefItem() {
    }

    /** minimal constructor */
    public BizDataDefItem(String indicator, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.indicator = indicator;
        this.bizData = bizData;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndicator() {
        return this.indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getBizData() {
        return this.bizData;
    }

    public void setBizData(dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.bizData = bizData;
    }

    public String toString() {
        return "" + this.id + this.indicator;
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
		BizDataDefItem other = (BizDataDefItem) obj;
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
