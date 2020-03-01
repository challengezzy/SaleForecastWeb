/**********************************************************************
 *$RCSfile:BBizDataDefItemForcastAssessmenet.java,v $  $Revision: 1.0 $  $Date:2012-3-19 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

import dmdd.dmddjava.common.constant.BizConst;

/**
 * <li>Title: BBizDataDefItemForcastAssessmenet.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BBizDataDefItemForecastAssessment extends BBizDataDefItem implements Serializable 
{
	public final static long serialVersionUID = -1020000014;

	/** persistent field */
    private int startPeriod;
    
    /** persistent field */
    private int endPeriod;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData;

    /** full constructor */
    public BBizDataDefItemForecastAssessment(String indicator, Long version, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int startPeriod,int endPeriod, dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        super(indicator, version, bizData);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.itemBizData = itemBizData;
    }

    /** default constructor */
    public BBizDataDefItemForecastAssessment() {
    	this.setIndicator( BizConst.BIZDATADEFITEM_INDICATOR_FORECASTASSESSMENT );
    }

    /** minimal constructor */
    public BBizDataDefItemForecastAssessment(String indicator, dmdd.dmddjava.dataaccess.bizobject.BBizData bizData, int startPeriod,int endPeriod) {
      super(indicator, bizData);
      this.startPeriod = startPeriod;
      this.endPeriod = endPeriod;
    }

    public int getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(int startPeriod) {
    	this.startPeriod = startPeriod;
    }
    
    public int getEndPeriod()
    {
    	return this.endPeriod;
    }
    
    public void setEndPeriod(int endPeriod)
    {
    	this.endPeriod = endPeriod;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BBizData getItemBizData() {
        return this.itemBizData;
    }

    public void setItemBizData(dmdd.dmddjava.dataaccess.bizobject.BBizData itemBizData) {
        this.itemBizData = itemBizData;
    }

    public String toString() {
        return super.toString();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((itemBizData == null) ? 0 : itemBizData.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
			return true;
		if( !super.equals( obj ) )
			return false;
		if( getClass() != obj.getClass() )
			return false;
		BBizDataDefItemForecastAssessment other = (BBizDataDefItemForecastAssessment) obj;
		if( itemBizData == null )
		{
			if( other.itemBizData != null )
				return false;
		}
		else if( !itemBizData.equals( other.itemBizData ) )
			return false;
		return true;
	}

}

/**********************************************************************
 *$RCSfile:BBizDataDefItemForcastAssessmenet.java,v $  $Revision: 1.0 $  $Date:2012-3-19 $
 *
 *$Log:BBizDataDefItemForcastAssessmenet.java,v $
 *********************************************************************/