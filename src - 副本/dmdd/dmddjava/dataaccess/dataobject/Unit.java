package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class Unit implements Serializable {
	
	public final static long serialVersionUID = -1210000004;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** persistent field */
    private long exchangeRate;

    /** persistent field */
    private int isBase;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup;

    /** full constructor */
    public Unit(String code, String name, long exchangeRate, int isBase, String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
        this.isBase = isBase;
        this.comments = comments;
        this.version = version;
        this.unitGroup = unitGroup;
    }

    /** default constructor */
    public Unit() {
    }

    /** minimal constructor */
    public Unit(String code, String name, long exchangeRate, int isBase, dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup) {
        this.code = code;
        this.name = name;
        this.exchangeRate = exchangeRate;
        this.isBase = isBase;
        this.unitGroup = unitGroup;
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

    public long getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public int getIsBase() {
        return this.isBase;
    }

    public void setIsBase(int isBase) {
        this.isBase = isBase;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.dataobject.UnitGroup getUnitGroup() {
        return this.unitGroup;
    }

    public void setUnitGroup(dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup) {
        this.unitGroup = unitGroup;
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
		Unit other = (Unit) obj;
		if( code == null )
		{
			if( other.code != null )
				return false;
		}
		else if( !code.equals( other.code ) )
			return false;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		if( name == null )
		{
			if( other.name != null )
				return false;
		}
		else if( !name.equals( other.name ) )
			return false;
		if( unitGroup == null )
		{
			if( other.unitGroup != null )
				return false;
		}
		else if( !unitGroup.equals( other.unitGroup ) )
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
		result = prime * result + ((unitGroup == null) ? 0 : unitGroup.hashCode());
		return result;
	}

}
