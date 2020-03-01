package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class OperatorUserBizData implements Serializable {
	
	public final static long serialVersionUID = -1150000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int isManaging;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData bizData;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser;

    /** full constructor */
    public OperatorUserBizData(int isManaging, Long version, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser) {
        this.isManaging = isManaging;
        this.version = version;
        this.bizData = bizData;
        this.operatorUser = operatorUser;
    }

    /** default constructor */
    public OperatorUserBizData() {
    }

    /** minimal constructor */
    public OperatorUserBizData(int isManaging, dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser) {
        this.isManaging = isManaging;
        this.operatorUser = operatorUser;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIsManaging() {
        return this.isManaging;
    }

    public void setIsManaging(int isManaging) {
        this.isManaging = isManaging;
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

    public dmdd.dmddjava.dataaccess.dataobject.OperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser) {
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
		OperatorUserBizData other = (OperatorUserBizData) obj;
		if( bizData == null )
		{
			if( other.bizData != null )
				return false;
		}
		else if( !bizData.equals( other.bizData ) )
			return false;
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
		result = prime * result + ((bizData == null) ? 0 : bizData.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
