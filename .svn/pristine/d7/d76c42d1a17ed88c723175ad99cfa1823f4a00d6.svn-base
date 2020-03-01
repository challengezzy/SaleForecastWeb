package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BBizDataDefItem implements Serializable {
	
	public final static long serialVersionUID = -1020000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String indicator;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData bizData;

    /** full constructor */
    public BBizDataDefItem(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
        this.indicator = indicator;
        this.version = version;
        this.bizData = bizData;
    }

    /** default constructor */
    public BBizDataDefItem() {
    }

    /** minimal constructor */
    public BBizDataDefItem(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
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

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getBizData() {
        return this.bizData;
    }

    public void setBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData bizData) {
        this.bizData = bizData;
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
		BBizDataDefItem other = (BBizDataDefItem) obj;
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
