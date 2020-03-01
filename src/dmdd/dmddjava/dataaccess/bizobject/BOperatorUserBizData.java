package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class BOperatorUserBizData implements Serializable {
	
	public final static long serialVersionUID = -1150000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int isManaging;

    // ui aid field
    private Boolean bl4IsManaging;    

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData bizData;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** full constructor */
    public BOperatorUserBizData(int isManaging, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.isManaging = isManaging;
        this.version = version;
        this.bizData = bizData;
        this.operatorUser = operatorUser;
    }

    /** default constructor */
    public BOperatorUserBizData() {
    }

    /** minimal constructor */
    public BOperatorUserBizData(int isManaging, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
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

    public Boolean getBl4IsManaging() {
        return this.bl4IsManaging;
    }

    public void setBl4IsManaging(Boolean bl4IsManaging) {
        this.bl4IsManaging = bl4IsManaging;
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
		BOperatorUserBizData other = (BOperatorUserBizData) obj;
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
