/**********************************************************************
 *$RCSfile:BreakDownRuleDefItem.java,v $  $Revision: 1.0 $  $Date:2012-3-26 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;

/**
 * <li>Title: BreakDownRuleDefItem.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BreakDownRuleDefItem implements Serializable
{
	private static final long serialVersionUID = -1020003201;
	
	/** identifier field */
    private Long id;
    
    /** persistent field */
    private int isConnected;
    
    /** persistent field */
    private Integer period;
	
    /** persistent field */
    private int week1;

    /** persistent field */
    private int week2;
    
    /** persistent field */
    private int week3;
    
    /** persistent field */
    private int week4;
    
    /** persistent field */
    private int week5;
    
    /** persistent field */
    private int week6;
    
    /** nullable persistent field */
    private String comments;
    
    /** nullable persistent field */
    private Long version;
    
    /** nullable persistent field */
    private int firstDay;
    
    /** nullable persistent field */
    private int num_week;
    
    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.BreakDownRule breakDownRule;
    
    /** full constructor */
    public BreakDownRuleDefItem(int isConnected,int period, int week1,int week2, int week3,int week4,int week5,int week6,String comments,Long version,int firstDay,int num_week, dmdd.dmddjava.dataaccess.dataobject.BreakDownRule breakDownRule) {
        this.isConnected = isConnected;
    	this.period = period;
        this.week1 = week1;
        this.week2 = week2;
        this.week3 = week3;
        this.week4 = week4;
        this.week5 = week5;
        this.week6 = week6;
        this.comments = comments;
        this.breakDownRule = breakDownRule;
        this.version= version ;
        this.firstDay = firstDay;
        this.num_week= num_week;
    }
    
    public BreakDownRuleDefItem()
    {
    	
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIsConnected()
    {
    	return this.isConnected;
    }
    
    public void setIsConnected(int isConnected)
    {
    	this.isConnected =  isConnected;
    }
    
    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
    
    public int getWeek1()
    {
    	return this.week1;
    }
    
    public void setWeek1(int week1)
    {
    	this.week1 = week1;
    }
    
    public int getWeek2()
    {
    	return this.week2;
    }
    
    public void setWeek2(int week2)
    {
    	this.week2 = week2;
    }
    
    public int getWeek3()
    {
    	return this.week3;
    }
    
    public void setWeek3(int week3)
    {
    	this.week3 = week3;
    }
    
    public int getWeek4()
    {
    	return this.week4;
    }
    
    public void setWeek4(int week4)
    {
    	this.week4 = week4;
    }
    
    public int getWeek5()
    {
    	return this.week5;
    }
    
    public void setWeek5(int week5)
    {
    	this.week5 = week5;
    }
    
    public int getWeek6()
    {
    	return this.week6;
    }
    
    public void setWeek6(int week6)
    {
    	this.week6 = week6;
    }
    
    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public BreakDownRule getBreakDownRule()
    {
    	return this.breakDownRule;
    }
    
    public void setBreakDownRule(BreakDownRule breakDownRule)
    {
    	this.breakDownRule = breakDownRule;
    }
    

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
    public int getFirstDay()
    {
    	return this.firstDay;
    }
    
    public void setFirstDay(int firstDay)
    {
    	this.firstDay = firstDay;
    }
    
    public int getNum_week()
    {
    	return this.num_week;
    }
    
    public void setNum_week(int num_week)
    {
    	this.num_week = num_week;
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
		BreakDownRuleDefItem other = (BreakDownRuleDefItem) obj;
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
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void copyShallow(BreakDownRuleDefItem obj)
	   {
		this.isConnected = obj.isConnected;
    	this.period = obj.period;
        this.week1 = obj.week1;
        this.week2 = obj.week2;
        this.week3 = obj.week3;
        this.week4 = obj.week4;
        this.week5 = obj.week5;
        this.week6 = obj.week6;
        this.comments = obj.comments;
	   }
}

/**********************************************************************
 *$RCSfile:BreakDownRuleDefItem.java,v $  $Revision: 1.0 $  $Date:2012-3-26 $
 *
 *$Log:BreakDownRuleDefItem.java,v $
 *********************************************************************/