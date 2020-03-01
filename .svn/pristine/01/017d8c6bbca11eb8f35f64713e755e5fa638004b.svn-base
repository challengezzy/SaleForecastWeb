package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/** @author Hibernate CodeGenerator */
public class BHistoryAdjustLog implements Serializable {
	
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
    private dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser;

    /** persistent field */
    private Set<BHistoryAdjustLogProOrg> historyAdjustLogProOrgs;

    /** persistent field */
    private Set<BHistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems;

    /** full constructor */
    public BHistoryAdjustLog(int compilePeriod, Date submitTime, String submitter, String description, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser, Set<BHistoryAdjustLogProOrg> historyAdjustLogProOrgs, Set<BHistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems) {
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
    public BHistoryAdjustLog() {
    }

    /** minimal constructor */
    public BHistoryAdjustLog(int compilePeriod, Date submitTime, String submitter, Set<BHistoryAdjustLogProOrg> historyAdjustLogProOrgs, Set<BHistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems) {
        this.compilePeriod = compilePeriod;
        this.submitTime = submitTime;
        this.submitter = submitter;
        this.historyAdjustLogProOrgs = historyAdjustLogProOrgs;
        this.historyAdjustLogAdjustItems = historyAdjustLogAdjustItems;
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

    public dmdd.dmddjava.dataaccess.bizobject.BOperatorUser getOperatorUser() {
        return this.operatorUser;
    }

    public void setOperatorUser(dmdd.dmddjava.dataaccess.bizobject.BOperatorUser operatorUser) {
        this.operatorUser = operatorUser;
    }

    public Set<BHistoryAdjustLogProOrg> getHistoryAdjustLogProOrgs() {
        return this.historyAdjustLogProOrgs;
    }

    public void setHistoryAdjustLogProOrgs(Set<BHistoryAdjustLogProOrg> historyAdjustLogProOrgs) {
        this.historyAdjustLogProOrgs = historyAdjustLogProOrgs;
    }

    public void addHistoryAdjustLogProOrg(BHistoryAdjustLogProOrg _historyAdjustLogProOrg) {
    	if( _historyAdjustLogProOrg == null )
    	{
    		return;
    	}
    	if( this.historyAdjustLogProOrgs == null )
    	{
    		this.historyAdjustLogProOrgs = new HashSet<BHistoryAdjustLogProOrg>();
    	}
    	_historyAdjustLogProOrg.setHistoryAdjustLog( this );
        this.historyAdjustLogProOrgs.add( _historyAdjustLogProOrg );
    }      
    
    
    public Set<BHistoryAdjustLogAdjustItem> getHistoryAdjustLogAdjustItems() {
        return this.historyAdjustLogAdjustItems;
    }

    public void setHistoryAdjustLogAdjustItems(Set<BHistoryAdjustLogAdjustItem> historyAdjustLogAdjustItems) {
        this.historyAdjustLogAdjustItems = historyAdjustLogAdjustItems;
    }
    
    public void addHistoryAdjustLogAdjustItem(BHistoryAdjustLogAdjustItem _historyAdjustLogAdjustItem) {
    	if( _historyAdjustLogAdjustItem == null )
    	{
    		return;
    	}
    	if( this.historyAdjustLogAdjustItems == null )
    	{
    		this.historyAdjustLogAdjustItems = new HashSet<BHistoryAdjustLogAdjustItem>();
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
		BHistoryAdjustLog other = (BHistoryAdjustLog) obj;
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
