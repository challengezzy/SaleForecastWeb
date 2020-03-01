package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/** @author Hibernate CodeGenerator */
public class BVersionInfo implements Serializable {
	
	public final static long serialVersionUID = -1220000001;

    /** identifier field */
    private Long id;

    /** persistent field */
    private BigDecimal buildNo;

    /** persistent field */
    private Date executeTime;

    /** nullable persistent field */
    private String scriptName;

    /** nullable persistent field */
    private String comments;

    /** full constructor */
    public BVersionInfo(BigDecimal buildNo, Date executeTime, String scriptName, String comments) {
        this.buildNo = buildNo;
        this.executeTime = executeTime;
        this.scriptName = scriptName;
        this.comments = comments;
    }

    /** default constructor */
    public BVersionInfo() {
    }

    /** minimal constructor */
    public BVersionInfo(BigDecimal buildNo, Date executeTime) {
        this.buildNo = buildNo;
        this.executeTime = executeTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBuildNo() {
        return this.buildNo;
    }

    public void setBuildNo(BigDecimal buildNo) {
        this.buildNo = buildNo;
    }

    public Date getExecuteTime() {
        return this.executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getScriptName() {
        return this.scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String toString() {
        return "" + this.id;
    }

}
