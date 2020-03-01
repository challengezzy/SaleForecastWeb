/**********************************************************************
 *$RCSfile:BreakDownRule.java,v $  $Revision: 1.0 $  $Date:2012-3-26 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;


/**
 * <li>Title: BreakDownRule.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class BBreakDownRule implements Serializable 
{
	private static final long serialVersionUID = -1020003101;
	/** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;
  
    /** persistent field */
    private int type;

    /** persistent field */
    private int beginPeriod;
    
    /** persistent field */
    private int endPeriod;
    
    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** persistent field */
    private Set<BBreakDownRuleDefItem> breakDownRuleDefItems;
    
    private Set<BBreakDownRuleFinancialDefItem> breakDownRuleFinancialDefItems;
	
    /** full constructor */
    public BBreakDownRule(String code, String name, int type,int beginPeriod,int endPeriod, String description, String comments, Long version, Set<BBreakDownRuleDefItem> breakDownRuleDefItems,Set<BBreakDownRuleFinancialDefItem> breakDownRuleFinancialDefItems) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.beginPeriod = beginPeriod;
        this.endPeriod=endPeriod;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.breakDownRuleDefItems = breakDownRuleDefItems;
        this.breakDownRuleFinancialDefItems = breakDownRuleFinancialDefItems;
    }
    
    public Set<BBreakDownRuleFinancialDefItem> getBreakDownRuleFinancialDefItems() {
		return breakDownRuleFinancialDefItems;
	}

	public void setBreakDownRuleFinancialDefItems(
			Set<BBreakDownRuleFinancialDefItem> breakDownRuleFinancialDefItems) {
		this.breakDownRuleFinancialDefItems = breakDownRuleFinancialDefItems;
	}

	public BBreakDownRule()
    {
    	
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType()
    {
    	return this.type;
    }
    
    public void setType(int type)
    {
    	this.type = type;
    }
    
    public int getBeginPeriod()
    {
    	return this.beginPeriod;
    }
      
    public void setBeginPeriod(int beginPeriod)
    {
    	this.beginPeriod = beginPeriod;
    }

    public int getEndPeriod()
    {
    	return this.endPeriod;
    }
    
    public void setEndPeriod(int endPeriod)
    {
    	this.endPeriod = endPeriod;
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

    public Set<BBreakDownRuleDefItem> getBreakDownRuleDefItems() {
        return this.breakDownRuleDefItems;
    }

    public void setBreakDownRuleDefItems(Set<BBreakDownRuleDefItem> _breakDownRuleDefItems) {
        this.breakDownRuleDefItems = _breakDownRuleDefItems;
    }
	
    public void addBreakDownRuleDefItems(BBreakDownRuleDefItem _bBreakDownRuleDefItem)
    {
    	if(_bBreakDownRuleDefItem == null)
    	{
    		return ;
    	}
    	if(breakDownRuleDefItems == null)
    	{
    		breakDownRuleDefItems = new HashSet<BBreakDownRuleDefItem>();
    	}
    	_bBreakDownRuleDefItem.setBreakDownRule(this);
    	breakDownRuleDefItems.add(_bBreakDownRuleDefItem);
    }
    public void addBreakDownRuleFinancialDefItems(BBreakDownRuleFinancialDefItem _bBreakDownRuleDefItem)
    {
    	if(_bBreakDownRuleDefItem == null)
    	{
    		return ;
    	}
    	if(breakDownRuleFinancialDefItems == null)
    	{
    		breakDownRuleFinancialDefItems = new HashSet<BBreakDownRuleFinancialDefItem>();
    	}
    	_bBreakDownRuleDefItem.setBreakDownRule(this);
    	breakDownRuleFinancialDefItems.add(_bBreakDownRuleDefItem);
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
		BBreakDownRule other = (BBreakDownRule) obj;
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

/**********************************************************************
 *$RCSfile:BreakDownRule.java,v $  $Revision: 1.0 $  $Date:2012-3-26 $
 *
 *$Log:BreakDownRule.java,v $
 *********************************************************************/