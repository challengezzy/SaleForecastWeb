package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class HistoryAdjustLogAdjustItem implements Serializable {
	
	public final static long serialVersionUID = -1080000003;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int period;

    /** persistent field */
    private long oldValue;

    /** persistent field */
    private long newValue;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog historyAdjustLog;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData bizData;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter organizationCharacter;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product product;

    /** full constructor */
    public HistoryAdjustLogAdjustItem(int period, long oldValue, long newValue, String description, String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog historyAdjustLog, dmdd.dmddjava.dataaccess.dataobject.BizData bizData, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter, dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter organizationCharacter, dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.period = period;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.historyAdjustLog = historyAdjustLog;
        this.bizData = bizData;
        this.organization = organization;
        this.productCharacter = productCharacter;
        this.organizationCharacter = organizationCharacter;
        this.product = product;
    }

    /** default constructor */
    public HistoryAdjustLogAdjustItem() {
    }

    /** minimal constructor */
    public HistoryAdjustLogAdjustItem(int period, long oldValue, long newValue, dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog historyAdjustLog) {
        this.period = period;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.historyAdjustLog = historyAdjustLog;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public long getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(long oldValue) {
        this.oldValue = oldValue;
    }

    public long getNewValue() {
        return this.newValue;
    }

    public void setNewValue(long newValue) {
        this.newValue = newValue;
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

    public dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog getHistoryAdjustLog() {
        return this.historyAdjustLog;
    }

    public void setHistoryAdjustLog(dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog historyAdjustLog) {
        this.historyAdjustLog = historyAdjustLog;
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getBizData() {
        return this.bizData;
    }

    public void setBizData(dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.bizData = bizData;
    }

    public dmdd.dmddjava.dataaccess.dataobject.Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(dmdd.dmddjava.dataaccess.dataobject.Organization organization) {
        this.organization = organization;
    }

    public dmdd.dmddjava.dataaccess.dataobject.ProductCharacter getProductCharacter() {
        return this.productCharacter;
    }

    public void setProductCharacter(dmdd.dmddjava.dataaccess.dataobject.ProductCharacter productCharacter) {
        this.productCharacter = productCharacter;
    }

    public dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter getOrganizationCharacter() {
        return this.organizationCharacter;
    }

    public void setOrganizationCharacter(dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter organizationCharacter) {
        this.organizationCharacter = organizationCharacter;
    }

    public dmdd.dmddjava.dataaccess.dataobject.Product getProduct() {
        return this.product;
    }

    public void setProduct(dmdd.dmddjava.dataaccess.dataobject.Product product) {
        this.product = product;
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
		if ( this == obj ) return true;
		if ( obj == null ) return false;
		if ( getClass() != obj.getClass() ) return false;
		HistoryAdjustLogAdjustItem other = (HistoryAdjustLogAdjustItem) obj;
		if ( bizData == null )
		{
			if ( other.bizData != null ) return false;
		}
		else if ( !bizData.equals( other.bizData ) ) return false;
		if ( historyAdjustLog == null )
		{
			if ( other.historyAdjustLog != null ) return false;
		}
		else if ( !historyAdjustLog.equals( other.historyAdjustLog ) ) return false;
		if ( id == null )
		{
			if ( other.id != null ) return false;
		}
		else if ( !id.equals( other.id ) ) return false;
		if ( organization == null )
		{
			if ( other.organization != null ) return false;
		}
		else if ( !organization.equals( other.organization ) ) return false;
		if ( organizationCharacter == null )
		{
			if ( other.organizationCharacter != null ) return false;
		}
		else if ( !organizationCharacter.equals( other.organizationCharacter ) ) return false;
		if ( period != other.period ) return false;
		if ( product == null )
		{
			if ( other.product != null ) return false;
		}
		else if ( !product.equals( other.product ) ) return false;
		if ( productCharacter == null )
		{
			if ( other.productCharacter != null ) return false;
		}
		else if ( !productCharacter.equals( other.productCharacter ) ) return false;
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
		result = prime * result + ( ( bizData == null ) ? 0 : bizData.hashCode() );
		result = prime * result + ( ( historyAdjustLog == null ) ? 0 : historyAdjustLog.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( organization == null ) ? 0 : organization.hashCode() );
		result = prime * result + ( ( organizationCharacter == null ) ? 0 : organizationCharacter.hashCode() );
		result = prime * result + period;
		result = prime * result + ( ( product == null ) ? 0 : product.hashCode() );
		result = prime * result + ( ( productCharacter == null ) ? 0 : productCharacter.hashCode() );
		return result;
	}

}
