package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class HistoryAdjustLog implements Serializable {
	
	public final static long serialVersionUID = -1080000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int compilePeriod;

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
    private dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser;

    /** persistent field */
    private Set<HistoryAdjustLogProOrg> historyAdjustLogProOrgs;

    /** persistent field */
    private Set<HistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems;

    /** full constructor */
    public HistoryAdjustLog(int compilePeriod, Date submitTime, String submitter, String description, String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser, Set<HistoryAdjustLogProOrg> historyAdjustLogProOrgs, Set<HistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems) {
        this.compilePeriod = compilePeriod;
        this.submitTime = submitTime;
        this.submitter = submitter;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.operatorUser = operatorUser;
        this.historyAdjustLogProOrgs = historyAdjustLogProOrgs;
        this.historyAdjustLogAdjustItems = historyAdjustLogAdjustItems;
    }

    /** default constructor */
    public HistoryAdjustLog() {
    }

    /** minimal constructor */
    public HistoryAdjustLog(int compilePeriod, Date submitTime, String submitter, Set<HistoryAdjustLogProOrg> historyAdjustLogProOrgs, Set<HistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems) {
        this.compilePeriod = compilePeriod;
        this.submitTime = submitTime;
        this.submitter = submitter;
        this.historyAdjustLogProOrgs = historyAdjustLogProOrgs;
        this.historyAdjustLogAdjustItems = historyAdjustLogAdjustItems;
    }

    public HistoryAdjustLog(Long id,int compilePeriod, Date submitTime, String submitter, String description) {
        this.id = id;
    	this.compilePeriod = compilePeriod;
        this.submitTime = submitTime;
        this.submitter = submitter;
        this.description = description;
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

    public dmdd.dmddjava.dataaccess.dataobject.OperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.dataobject.OperatorUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public Set<HistoryAdjustLogProOrg> getHistoryAdjustLogProOrgs() {
        return this.historyAdjustLogProOrgs;
    }

    public void setHistoryAdjustLogProOrgs(Set<HistoryAdjustLogProOrg> historyAdjustLogProOrgs) {
        this.historyAdjustLogProOrgs = historyAdjustLogProOrgs;
    }

    public void addHistoryAdjustLogProOrg(HistoryAdjustLogProOrg _historyAdjustLogProOrg) {
    	if( _historyAdjustLogProOrg == null )
    	{
    		return;
    	}
    	if( this.historyAdjustLogProOrgs == null )
    	{
    		this.historyAdjustLogProOrgs = new HashSet<HistoryAdjustLogProOrg>();
    	}
    	_historyAdjustLogProOrg.setHistoryAdjustLog( this );
        this.historyAdjustLogProOrgs.add( _historyAdjustLogProOrg );
    }      
    
    
    public Set<HistoryAdjustLogAdjustItem> getHistoryAdjustLogAdjustItems() {
        return this.historyAdjustLogAdjustItems;
    }

    public void setHistoryAdjustLogAdjustItems(Set<HistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems) {
        this.historyAdjustLogAdjustItems = historyAdjustLogAdjustItems;
    }
    
    public void addHistoryAdjustLogAdjustItem(HistoryAdjustLogAdjustItem _historyAdjustLogAdjustItem) {
    	if( _historyAdjustLogAdjustItem == null )
    	{
    		return;
    	}
    	if( this.historyAdjustLogAdjustItems == null )
    	{
    		this.historyAdjustLogAdjustItems = new HashSet<HistoryAdjustLogAdjustItem>();
    	}
    	_historyAdjustLogAdjustItem.setHistoryAdjustLog( this );
        this.historyAdjustLogAdjustItems.add( _historyAdjustLogAdjustItem );
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
		HistoryAdjustLog other = (HistoryAdjustLog) obj;
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
