package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class UiPopbScopeBizData implements Serializable {
	
	public final static long serialVersionUID = -1210000002;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData bizData;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope;

    /** full constructor */
    public UiPopbScopeBizData(Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope) {
        this.version = version;
        this.bizData = bizData;
        this.uiPopbScope = uiPopbScope;
    }

    /** default constructor */
    public UiPopbScopeBizData() {
    }

    /** minimal constructor */
    public UiPopbScopeBizData(dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope) {
        this.uiPopbScope = uiPopbScope;
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

    public dmdd.dmddjava.dataaccess.dataobject.BizData getBizData() {
        return this.bizData;
    }

    public void setBizData(dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.bizData = bizData;
    }

    public dmdd.dmddjava.dataaccess.dataobject.UiPopbScope getUiPopbScope() {
        return this.uiPopbScope;
    }

    public void setUiPopbScope(dmdd.dmddjava.dataaccess.dataobject.UiPopbScope uiPopbScope) {
        this.uiPopbScope = uiPopbScope;
    }

    public String toString() {
        return "" + this.id;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( bizData == null ) ? 0 : bizData.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( uiPopbScope == null ) ? 0 : uiPopbScope.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( getClass() != obj.getClass() ) return false;
		UiPopbScopeBizData other = (UiPopbScopeBizData) obj;
		if ( bizData == null )
		{
			if ( other.bizData != null ) return false;
		}
		else if ( !bizData.equals( other.bizData ) ) return false;
		if ( id == null )
		{
			if ( other.id != null ) return false;
		}
		else if ( !id.equals( other.id ) ) return false;
		if ( uiPopbScope == null )
		{
			if ( other.uiPopbScope != null ) return false;
		}
		else if ( !uiPopbScope.equals( other.uiPopbScope ) ) return false;
		return true;
	}

}
