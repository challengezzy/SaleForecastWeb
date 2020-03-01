/**********************************************************************
 *$RCSfile:BSysBakLog.java,v $  $Revision: 1.0 $  $Date:2011-9-6 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bizobject;
/**
 * <li>Title: BSysBakLog.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2011</li>
 * @Company: DMDD. All Rights Reserved.
 * @author luowang Of DMDD.Dept
 * @version 1.0
 */

import java.io.Serializable;
import java.util.Date;


/** @author Hibernate CodeGenerator */
public class BSysBakLog implements Serializable {

    /**
	 * 
	 */
	private static final long	serialVersionUID	= 7654969822210435608L;

    /** identifier field */
    private Long id;

    /** persistent field */
    private Date excuteTime;

    /** persistent field */
    private int beginPeroid;

    /** persistent field */
    private int endPeroid;

    /** persistent field */
    private int bakHistoryData;

    /** persistent field */
    private int bakHistoryAdjustLog;

    /** persistent field */
    private int bakPriceData;

    /** persistent field */
    private int bakForecastData;

    /** persistent field */
    private int bakForecastMakeLog;

    /** persistent field */
    private String operatorUserName;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** full constructor */
    public BSysBakLog(Date excuteTime, int beginPeroid, int endPeroid, int bakHistoryData, int bakHistoryAdjustLog, int bakPriceData, int bakForecastData, int bakForecastMakeLog, String operatorUserName, String description, String comments) {
        this.excuteTime = excuteTime;
        this.beginPeroid = beginPeroid;
        this.endPeroid = endPeroid;
        this.bakHistoryData = bakHistoryData;
        this.bakHistoryAdjustLog = bakHistoryAdjustLog;
        this.bakPriceData = bakPriceData;
        this.bakForecastData = bakForecastData;
        this.bakForecastMakeLog = bakForecastMakeLog;
        this.operatorUserName = operatorUserName;
        this.description = description;
        this.comments = comments;
    }

    /** default constructor */
    public BSysBakLog() {
    }

    /** minimal constructor */
    public BSysBakLog(Date excuteTime, int beginPeroid, int endPeroid, int bakHistoryData, int bakHistoryAdjustLog, int bakPriceData, int bakForecastData, int bakForecastMakeLog, String operatorUserName) {
        this.excuteTime = excuteTime;
        this.beginPeroid = beginPeroid;
        this.endPeroid = endPeroid;
        this.bakHistoryData = bakHistoryData;
        this.bakHistoryAdjustLog = bakHistoryAdjustLog;
        this.bakPriceData = bakPriceData;
        this.bakForecastData = bakForecastData;
        this.bakForecastMakeLog = bakForecastMakeLog;
        this.operatorUserName = operatorUserName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExcuteTime() {
        return this.excuteTime;
    }

    public void setExcuteTime(Date excuteTime) {
        this.excuteTime = excuteTime;
    }

    public int getBeginPeroid() {
        return this.beginPeroid;
    }

    public void setBeginPeroid(int beginPeroid) {
        this.beginPeroid = beginPeroid;
    }

    public int getEndPeroid() {
        return this.endPeroid;
    }

    public void setEndPeroid(int endPeroid) {
        this.endPeroid = endPeroid;
    }

    public int getBakHistoryData() {
        return this.bakHistoryData;
    }

    public void setBakHistoryData(int bakHistoryData) {
        this.bakHistoryData = bakHistoryData;
    }

    public int getBakHistoryAdjustLog() {
        return this.bakHistoryAdjustLog;
    }

    public void setBakHistoryAdjustLog(int bakHistoryAdjustLog) {
        this.bakHistoryAdjustLog = bakHistoryAdjustLog;
    }

    public int getBakPriceData() {
        return this.bakPriceData;
    }

    public void setBakPriceData(int bakPriceData) {
        this.bakPriceData = bakPriceData;
    }

    public int getBakForecastData() {
        return this.bakForecastData;
    }

    public void setBakForecastData(int bakForecastData) {
        this.bakForecastData = bakForecastData;
    }

    public int getBakForecastMakeLog() {
        return this.bakForecastMakeLog;
    }

    public void setBakForecastMakeLog(int bakForecastMakeLog) {
        this.bakForecastMakeLog = bakForecastMakeLog;
    }

    public String getOperatorUserName() {
        return this.operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
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


    public String toString() {
        return "" + this.id;
    }

    public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		BSysBakLog other = (BSysBakLog) obj;
		if( id == null )
		{
			if( other.id != null )
				return false;
		}
		else if( !id.equals( other.id ) )
			return false;
		
		return true;
	}

    public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}

/**********************************************************************
 *$RCSfile:BSysBakLog.java,v $  $Revision: 1.0 $  $Date:2011-9-6 $
 *
 *$Log:BSysBakLog.java,v $
 *********************************************************************/