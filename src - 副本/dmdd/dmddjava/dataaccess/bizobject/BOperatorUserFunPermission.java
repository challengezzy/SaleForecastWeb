package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BOperatorUserFunPermission implements Serializable {
	
	public final static long serialVersionUID = -1150000003;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BFunPermission funPermission;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** full constructor */
    public BOperatorUserFunPermission(Long version, dmdd.dmddjava.dataaccess.bizobject.BFunPermission funPermission, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.version = version;
        this.funPermission = funPermission;
        this.operatorUser = operatorUser;
    }

    /** default constructor */
    public BOperatorUserFunPermission() {
    }

    /** minimal constructor */
    public BOperatorUserFunPermission(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
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

    public dmdd.dmddjava.dataaccess.bizobject.BFunPermission getFunPermission() {
        return this.funPermission;
    }

    public void setFunPermission(dmdd.dmddjava.dataaccess.bizobject.BFunPermission funPermission) {
        this.funPermission = funPermission;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
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
		BOperatorUserFunPermission other = (BOperatorUserFunPermission) obj;
		if( funPermission == null )
		{
			if( other.funPermission != null )
				return false;
		}
		else if( !funPermission.equals( other.funPermission ) )
			return false;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( operatorUser == null )
		{
			if( other.operatorUser != null )
				return false;
		}
		else if( !operatorUser.equals( other.operatorUser ) )
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
		result = prime * result + ((funPermission == null) ? 0 : funPermission.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((operatorUser == null) ? 0 : operatorUser.hashCode());
		return result;
	}

}
