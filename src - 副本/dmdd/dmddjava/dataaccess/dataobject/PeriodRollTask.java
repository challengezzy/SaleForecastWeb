package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.Date;


/** @author Hibernate CodeGenerator */
public class PeriodRollTask implements Serializable {
	
	public final static long serialVersionUID = -1160000001;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int compilePeriod;

    /** persistent field */
    private int seqNo;

    /** persistent field */
    private int category;

    /** persistent field */
    private int status;

    /** persistent field */
    private Date createTime;

    /** nullable persistent field */
    private Date beginTime;

    /** nullable persistent field */
    private Date endTime;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Organization organization;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst;

    /** full constructor */
    public PeriodRollTask(int compilePeriod, int seqNo, int category, int status, Date createTime, Date beginTime, Date endTime, String comments, Long version, dmdd.dmddjava.dataaccess.dataobject.Organization organization, dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst) {
        this.compilePeriod = compilePeriod;
        this.seqNo = seqNo;
        this.category = category;
        this.status = status;
        this.createTime = createTime;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.comments = comments;
        this.version = version;
        this.organization = organization;
        this.forecastInst = forecastInst;
    }

    /** default constructor */
    public PeriodRollTask() {
    }

    /** minimal constructor */
    public PeriodRollTask(int compilePeriod, int seqNo, int category, int status, Date createTime) {
        this.compilePeriod = compilePeriod;
        this.seqNo = seqNo;
        this.category = category;
        this.status = status;
        this.createTime = createTime;
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

    public int getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public dmdd.dmddjava.dataaccess.dataobject.Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(dmdd.dmddjava.dataaccess.dataobject.Organization organization) {
        this.organization = organization;
    }

    public dmdd.dmddjava.dataaccess.dataobject.ForecastInst getForecastInst() {
        return this.forecastInst;
    }

    public void setForecastInst(dmdd.dmddjava.dataaccess.dataobject.ForecastInst forecastInst) {
        this.forecastInst = forecastInst;
    }

    public String toString() {
        return "" + this.id;
    }

}
