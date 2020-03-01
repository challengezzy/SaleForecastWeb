package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BForecastMakeLog implements Serializable {
	
	public final static long serialVersionUID = -1060000006;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int compilePeriod;

    /** persistent field */
    private int actionType;

    /** persistent field */
    private Date submitTime;

    /** persistent field */
    private String submitter;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** persistent field */
    private Set<BForecastMakeLogProOrg> forecastMakeLogProOrgs;

    /** persistent field */
    private Set<BForecastMakeLogHfcItem> forecastMakeLogHfcItems;

    /** persistent field */
    private Set<BForecastMakeLogAuditItem> forecastMakeLogAuditItems;

    /** full constructor */
    public BForecastMakeLog(int compilePeriod, int actionType, Date submitTime, String submitter, String description, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser, Set<BForecastMakeLogProOrg> forecastMakeLogProOrgs, Set<BForecastMakeLogHfcItem> forecastMakeLogHfcItems, Set<BForecastMakeLogAuditItem> forecastMakeLogAuditItems) {
        this.compilePeriod = compilePeriod;
        this.actionType = actionType;
        this.submitTime = submitTime;
        this.submitter = submitter;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.operatorUser = operatorUser;
        this.forecastMakeLogProOrgs = forecastMakeLogProOrgs;
        this.forecastMakeLogHfcItems = forecastMakeLogHfcItems;
        this.forecastMakeLogAuditItems = forecastMakeLogAuditItems;
    }

    /** default constructor */
    public BForecastMakeLog() {
    }

    /** minimal constructor */
    public BForecastMakeLog(int compilePeriod, int actionType, Date submitTime, String submitter, Set<BForecastMakeLogProOrg> forecastMakeLogProOrgs, Set<BForecastMakeLogHfcItem> forecastMakeLogHfcItems, Set<BForecastMakeLogAuditItem> forecastMakeLogAuditItems) {
        this.compilePeriod = compilePeriod;
        this.actionType = actionType;
        this.submitTime = submitTime;
        this.submitter = submitter;
        this.forecastMakeLogProOrgs = forecastMakeLogProOrgs;
        this.forecastMakeLogHfcItems = forecastMakeLogHfcItems;
        this.forecastMakeLogAuditItems = forecastMakeLogAuditItems;
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

    public int getActionType() {
        return this.actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public Date getSubmitTime() {
        return this.submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitter() {
        return this.submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
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

    public dmdd.dmddjava.dataaccess.bizobject.BOperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public Set<BForecastMakeLogProOrg> getForecastMakeLogProOrgs() {
        return this.forecastMakeLogProOrgs;
    }

    public void setForecastMakeLogProOrgs(Set<BForecastMakeLogProOrg> forecastMakeLogProOrgs) {
        this.forecastMakeLogProOrgs = forecastMakeLogProOrgs;
    }
    
    public void addForecastMakeLogProOrg(BForecastMakeLogProOrg _forecastMakeLogProOrg) {
    	if( _forecastMakeLogProOrg == null )
    	{
    		return;
    	}
    	if( this.forecastMakeLogProOrgs == null )
    	{
    		this.forecastMakeLogProOrgs = new HashSet<BForecastMakeLogProOrg>();
    	}
    	_forecastMakeLogProOrg.setForecastMakeLog( this );
        this.forecastMakeLogProOrgs.add( _forecastMakeLogProOrg );
    }    

    public Set<BForecastMakeLogHfcItem> getForecastMakeLogHfcItems() {
        return this.forecastMakeLogHfcItems;
    }

    public void setForecastMakeLogHfcItems(Set<BForecastMakeLogHfcItem> forecastMakeLogHfcItems) {
        this.forecastMakeLogHfcItems = forecastMakeLogHfcItems;
    }
    
    public void addForecastMakeLogHfcItem(BForecastMakeLogHfcItem _forecastMakeLogHfcItem) {
    	if( _forecastMakeLogHfcItem == null )
    	{
    		return;
    	}
    	if( this.forecastMakeLogHfcItems == null )
    	{
    		this.forecastMakeLogHfcItems = new HashSet<BForecastMakeLogHfcItem>();
    	}
    	_forecastMakeLogHfcItem.setForecastMakeLog( this );
        this.forecastMakeLogHfcItems.add( _forecastMakeLogHfcItem );
    }      

    public Set<BForecastMakeLogAuditItem> getForecastMakeLogAuditItems() {
        return this.forecastMakeLogAuditItems;
    }

    public void setForecastMakeLogAuditItems(Set<BForecastMakeLogAuditItem> forecastMakeLogAuditItems) {
        this.forecastMakeLogAuditItems = forecastMakeLogAuditItems;
    }
    
    public void addForecastMakeLogAuditItem(BForecastMakeLogAuditItem _forecastMakeLogAuditItem) {
    	if( _forecastMakeLogAuditItem == null )
    	{
    		return;
    	}
    	if( this.forecastMakeLogAuditItems == null )
    	{
    		this.forecastMakeLogAuditItems = new HashSet<BForecastMakeLogAuditItem>();
    	}
    	_forecastMakeLogAuditItem.setForecastMakeLog( this );
        this.forecastMakeLogAuditItems.add( _forecastMakeLogAuditItem );
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
		BForecastMakeLog other = (BForecastMakeLog) obj;
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
