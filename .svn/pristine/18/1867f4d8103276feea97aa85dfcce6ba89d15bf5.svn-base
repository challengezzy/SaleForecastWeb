package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BForecastRunTask implements Serializable {
	
	public final static long serialVersionUID = -1060000035;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int compilePeriod;

    /** nullable persistent field */
    private String description;

    /** persistent field */
    private String creator;

    /** persistent field */
    private Date createdTime;

    /** persistent field */
    private int status;

    /** nullable persistent field */
    private Date finishTime;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private Set<BForecastRunTaskItem> forecastRunTaskItems;

    /** full constructor */
    public BForecastRunTask(int compilePeriod, String description, String creator, Date createdTime, int status, Date finishTime, String comments, Long version, Set<BForecastRunTaskItem> forecastRunTaskItems) {
        this.compilePeriod = compilePeriod;
        this.description = description;
        this.creator = creator;
        this.createdTime = createdTime;
        this.status = status;
        this.finishTime = finishTime;
        this.comments = comments;
        this.version = version;
        this.forecastRunTaskItems = forecastRunTaskItems;
    }

    /** default constructor */
    public BForecastRunTask() {
    }

    /** minimal constructor */
    public BForecastRunTask(int compilePeriod, String creator, Date createdTime, int status, Set<BForecastRunTaskItem> forecastRunTaskItems) {
        this.compilePeriod = compilePeriod;
        this.creator = creator;
        this.createdTime = createdTime;
        this.status = status;
        this.forecastRunTaskItems = forecastRunTaskItems;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCompilePeriod() {
        return this.compilePeriod;
    }

    public void setCompilePeriod(int compilePeriod) {
        this.compilePeriod = compilePeriod;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

    public Set<BForecastRunTaskItem> getForecastRunTaskItems() {
        return this.forecastRunTaskItems;
    }

    public void setForecastRunTaskItems(Set<BForecastRunTaskItem> forecastRunTaskItems) {
        this.forecastRunTaskItems = forecastRunTaskItems;
    }
    
    public void addForecastRunTaskItem(BForecastRunTaskItem _forecastRunTaskItem) {
    	if( _forecastRunTaskItem == null )
    	{
    		return;
    	}
    	if( this.forecastRunTaskItems == null )
    	{
    		this.forecastRunTaskItems = new HashSet<BForecastRunTaskItem>();
    	}
    	_forecastRunTaskItem.setForecastRunTask( this );
    	this.forecastRunTaskItems.add( _forecastRunTaskItem );
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
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		BForecastRunTask other = (BForecastRunTask) obj;
		if ( id == null )
		{
			if ( other.id != null ) return false;
		}
		else if ( !id.equals( other.id ) ) return false;
		return true;
	}



}
