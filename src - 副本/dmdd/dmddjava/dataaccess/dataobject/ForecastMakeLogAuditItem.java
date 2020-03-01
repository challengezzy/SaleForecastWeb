package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class ForecastMakeLogAuditItem implements Serializable {
	
	public final static long serialVersionUID = -1060000007;

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog forecastMakeLog;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BizData bizData;

    /** full constructor */
    public ForecastMakeLogAuditItem(String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog forecastMakeLog, dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.comments = comments;
        this.version = version;
        this.forecastMakeLog = forecastMakeLog;
        this.bizData = bizData;
    }

    /** default constructor */
    public ForecastMakeLogAuditItem() {
    }

    /** minimal constructor */
    public ForecastMakeLogAuditItem(dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog forecastMakeLog) {
        this.forecastMakeLog = forecastMakeLog;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog getForecastMakeLog() {
        return this.forecastMakeLog;
    }

    public void setForecastMakeLog(dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog forecastMakeLog) {
        this.forecastMakeLog = forecastMakeLog;
    }

    public dmdd.dmddjava.dataaccess.dataobject.BizData getBizData() {
        return this.bizData;
    }

    public void setBizData(dmdd.dmddjava.dataaccess.dataobject.BizData bizData) {
        this.bizData = bizData;
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
		ForecastMakeLogAuditItem other = (ForecastMakeLogAuditItem) obj;
		if( bizData == null )
		{
			if( other.bizData != null )
				return false;
		}
		else if( !bizData.equals( other.bizData ) )
			return false;
		if( forecastMakeLog == null )
		{
			if( other.forecastMakeLog != null )
				return false;
		}
		else if( !forecastMakeLog.equals( other.forecastMakeLog ) )
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
		result = prime * result + ((forecastMakeLog == null) ? 0 : forecastMakeLog.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
