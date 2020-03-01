package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class UnitGroup implements Serializable {
	
	public final static long serialVersionUID = -1210000005;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private Set<Unit> units;

    /** full constructor */
    public UnitGroup(String code, String name, String description, String comments, Long version, Set<Unit> units) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.units = units;
    }

    /** default constructor */
    public UnitGroup() {
    }

    /** minimal constructor */
    public UnitGroup(String code, String name, Set<Unit> units) {
        this.code = code;
        this.name = name;
        this.units = units;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Unit> getUnits() {
        return this.units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }
    
    public void addUnit(Unit _unit) {
    	if( _unit == null )
    	{
    		return;
    	}
    	if( this.units == null )
    	{
    		this.units = new HashSet<Unit>();
    	}
    	_unit.setUnitGroup( this );
        this.units.add( _unit );
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
		UnitGroup other = (UnitGroup) obj;
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
