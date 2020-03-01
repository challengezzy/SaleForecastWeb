package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Date;


/** @author Hibernate CodeGenerator */
public class BForecastRunTaskItem implements Serializable {
	
	public final static long serialVersionUID = -1060000036;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int seqNo;    

    /** persistent field */
    private int isIgnoreErrorThreshold;

    /** persistent field */
    private int status;

    /** persistent field */
    private int result;

    /** nullable persistent field */
    private String resultDetail;

    /** nullable persistent field */
    private Date beginTime;

    /** nullable persistent field */
    private Date endTime;

    /** nullable persistent field */
    private Double et;

    /** nullable persistent field */
    private Double mape;

    /** nullable persistent field */
    private Double mad;

    /** nullable persistent field */
    private Double ts;

    /** nullable persistent field */
    private Double mse;

    /** persistent field */
    private String forecastInstCode;

    /** persistent field */
    private String forecastInstName;

    /** nullable persistent field */
    private String forecastModelCode;

    /** nullable persistent field */
    private String forecastModelName;

    /** persistent field */
    private int outlierAnalyzePeriodNum;

    /** persistent field */
    private Double errorThreshold;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProductLayer runProductLayer;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask forecastRunTask;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer runOrganizationLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BForecastInst forecastInst;

    /** full constructor */
    public BForecastRunTaskItem(int seqNo, int isIgnoreErrorThreshold, int status, int result, String resultDetail, Date beginTime, Date endTime, Double et, Double mape, Double mad, Double ts, Double mse, String forecastInstCode, String forecastInstName, String forecastModelCode, String forecastModelName, int outlierAnalyzePeriodNum, Double errorThreshold, String comments, Long version, dmdd.dmddjava.dataaccess.bizobject.BProductLayer runProductLayer, dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask forecastRunTask, dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer runOrganizationLayer, dmdd.dmddjava.dataaccess.bizobject.BForecastInst forecastInst) {
    	this.seqNo = seqNo;
        this.isIgnoreErrorThreshold = isIgnoreErrorThreshold;
        this.status = status;
        this.result = result;
        this.resultDetail = resultDetail;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.et = et;
        this.mape = mape;
        this.mad = mad;
        this.ts = ts;
        this.mse = mse;
        this.forecastInstCode = forecastInstCode;
        this.forecastInstName = forecastInstName;
        this.forecastModelCode = forecastModelCode;
        this.forecastModelName = forecastModelName;
        this.outlierAnalyzePeriodNum = outlierAnalyzePeriodNum;
        this.errorThreshold = errorThreshold;
        this.comments = comments;
        this.version = version;
        this.runProductLayer = runProductLayer;
        this.forecastRunTask = forecastRunTask;
        this.runOrganizationLayer = runOrganizationLayer;
        this.forecastInst = forecastInst;
    }

    /** default constructor */
    public BForecastRunTaskItem() {
    }

    /** minimal constructor */
    public BForecastRunTaskItem(int seqNo, int isIgnoreErrorThreshold, int status, int result, String forecastInstCode, String forecastInstName, int outlierAnalyzePeriodNum, Double errorThreshold, dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask forecastRunTask) {
    	this.seqNo = seqNo;
        this.isIgnoreErrorThreshold = isIgnoreErrorThreshold;
        this.status = status;
        this.result = result;
        this.forecastInstCode = forecastInstCode;
        this.forecastInstName = forecastInstName;
        this.outlierAnalyzePeriodNum = outlierAnalyzePeriodNum;
        this.errorThreshold = errorThreshold;
        this.forecastRunTask = forecastRunTask;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }
    
    public int getIsIgnoreErrorThreshold() {
        return this.isIgnoreErrorThreshold;
    }

    public void setIsIgnoreErrorThreshold(int isIgnoreErrorThreshold) {
        this.isIgnoreErrorThreshold = isIgnoreErrorThreshold;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultDetail() {
        return this.resultDetail;
    }

    public void setResultDetail(String resultDetail) {
        this.resultDetail = resultDetail;
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

    public Double getEt() {
        return this.et;
    }

    public void setEt(Double et) {
        this.et = et;
    }

    public Double getMape() {
        return this.mape;
    }

    public void setMape(Double mape) {
        this.mape = mape;
    }

    public Double getMad() {
        return this.mad;
    }

    public void setMad(Double mad) {
        this.mad = mad;
    }

    public Double getTs() {
        return this.ts;
    }

    public void setTs(Double ts) {
        this.ts = ts;
    }

    public Double getMse() {
        return this.mse;
    }

    public void setMse(Double mse) {
        this.mse = mse;
    }

    public String getForecastInstCode() {
        return this.forecastInstCode;
    }

    public void setForecastInstCode(String forecastInstCode) {
        this.forecastInstCode = forecastInstCode;
    }

    public String getForecastInstName() {
        return this.forecastInstName;
    }

    public void setForecastInstName(String forecastInstName) {
        this.forecastInstName = forecastInstName;
    }

    public String getForecastModelCode() {
        return this.forecastModelCode;
    }

    public void setForecastModelCode(String forecastModelCode) {
        this.forecastModelCode = forecastModelCode;
    }

    public String getForecastModelName() {
        return this.forecastModelName;
    }

    public void setForecastModelName(String forecastModelName) {
        this.forecastModelName = forecastModelName;
    }

    public int getOutlierAnalyzePeriodNum() {
        return this.outlierAnalyzePeriodNum;
    }

    public void setOutlierAnalyzePeriodNum(int outlierAnalyzePeriodNum) {
        this.outlierAnalyzePeriodNum = outlierAnalyzePeriodNum;
    }

    public Double getErrorThreshold() {
        return this.errorThreshold;
    }

    public void setErrorThreshold(Double errorThreshold) {
        this.errorThreshold = errorThreshold;
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

    public dmdd.dmddjava.dataaccess.bizobject.BProductLayer getRunProductLayer() {
        return this.runProductLayer;
    }

    public void setRunProductLayer(dmdd.dmddjava.dataaccess.bizobject.BProductLayer runProductLayer) {
        this.runProductLayer = runProductLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask getForecastRunTask() {
        return this.forecastRunTask;
    }

    public void setForecastRunTask(dmdd.dmddjava.dataaccess.bizobject.BForecastRunTask forecastRunTask) {
        this.forecastRunTask = forecastRunTask;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer getRunOrganizationLayer() {
        return this.runOrganizationLayer;
    }

    public void setRunOrganizationLayer(dmdd.dmddjava.dataaccess.bizobject.BOrganizationLayer runOrganizationLayer) {
        this.runOrganizationLayer = runOrganizationLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BForecastInst getForecastInst() {
        return this.forecastInst;
    }

    public void setForecastInst(dmdd.dmddjava.dataaccess.bizobject.BForecastInst forecastInst) {
        this.forecastInst = forecastInst;
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
		result = prime * result + ( ( forecastInst == null ) ? 0 : forecastInst.hashCode() );
		result = prime * result + ( ( forecastRunTask == null ) ? 0 : forecastRunTask.hashCode() );
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
		BForecastRunTaskItem other = (BForecastRunTaskItem) obj;
		if ( forecastInst == null )
		{
			if ( other.forecastInst != null ) return false;
		}
		else if ( !forecastInst.equals( other.forecastInst ) ) return false;
		if ( forecastRunTask == null )
		{
			if ( other.forecastRunTask != null ) return false;
		}
		else if ( !forecastRunTask.equals( other.forecastRunTask ) ) return false;
		if ( id == null )
		{
			if ( other.id != null ) return false;
		}
		else if ( !id.equals( other.id ) ) return false;
		return true;
	}

  

}
