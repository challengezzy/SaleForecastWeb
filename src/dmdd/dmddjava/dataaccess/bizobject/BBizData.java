package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BBizData implements Serializable {
	
	public final static long serialVersionUID = -1020000001;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** persistent field */
    private int type;

    /** persistent field */
    private int source;

    /** persistent field */
    private int isValid;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private Set<BBizDataDefItem> bizDataDefItems;
    /**是否支持折和计算*/
    public Integer isSuitSupport;
    
    /** full constructor */
    public BBizData(String code, String name, int type, int source, int isValid, String description, String comments, 
    		Long version, Set<BBizDataDefItem> bizDataDefItems,Integer isSuitSupport) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.source = source;
        this.isValid = isValid;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.bizDataDefItems = bizDataDefItems;
        this.isSuitSupport = isSuitSupport;
    }

    /** default constructor */
    public BBizData() {
    }
    
    /** minimal constructor */
    public BBizData(String code, String name, int type, int source, int isValid, Set<BBizDataDefItem> bizDataDefItems) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.source = source;
        this.isValid = isValid;
        this.bizDataDefItems = bizDataDefItems;
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSource() {
        return this.source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getIsValid() {
        return this.isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
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

    public Set<BBizDataDefItem> getBizDataDefItems() {
        return this.bizDataDefItems;
    }

    public void setBizDataDefItems(Set<BBizDataDefItem> bizDataDefItems) {
        this.bizDataDefItems = bizDataDefItems;
    }
    
    public Integer getIsSuitSupport() {
		return isSuitSupport;
	}

	public void setIsSuitSupport(Integer isSuitSupport) {
		this.isSuitSupport = isSuitSupport;
	}

	public void addBizDataDefItems(BBizDataDefItem _bizDataDefItem) {
    	if( _bizDataDefItem == null )
    	{
    		return;
    	}
    	if( this.bizDataDefItems == null )
    	{
    		this.bizDataDefItems = new HashSet<BBizDataDefItem>();
    	}
    	_bizDataDefItem.setBizData( this );
        this.bizDataDefItems.add( _bizDataDefItem );
    }
    
    public String toString() {
        return "code=" + code + ", name=" + this.name;
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
		BBizData other = (BBizData) obj;
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
