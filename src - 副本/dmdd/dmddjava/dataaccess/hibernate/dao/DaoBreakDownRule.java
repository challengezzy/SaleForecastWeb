/**********************************************************************
 *$RCSfile:DaoBreakDownRule.java,v $  $Revision: 1.0 $  $Date:2012-3-28 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.BreakDownRule;

/**
 * <li>Title: DaoBreakDownRule.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class DaoBreakDownRule extends Dao
{

	public DaoBreakDownRule(Session _session)
	{
		
		super(_session);
		// TODO Auto-generated constructor stub
		
	}

	public int getBreakDownRule(String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( BreakDownRule.class );
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		}
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );
		
		Integer countValue = (Integer) crit.uniqueResult();
		
		return countValue;
	}	
	
	public BreakDownRule getBreakDownRuleById(Long id)
	{
		Object obj = this.getSession().get(BreakDownRule.class, id);
		if( obj == null )
		{
			return null;
		}
		
		return (BreakDownRule) obj;
	}
	
	public List<BreakDownRule> getBreakDownRules(String _sqlRestriction, int _pageIndex, int _pageSize)
	{
		Criteria crit = this.getSession().createCriteria( BreakDownRule.class );
		
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		}
		
		if( _pageIndex > 0 )
		{
			// 分页查询
			crit.setFirstResult( (_pageIndex-1) * _pageSize );
			crit.setMaxResults( _pageSize );
		}
 		
		List<BreakDownRule> rstList = crit.list();
		return rstList;
	}
	
}

/**********************************************************************
 *$RCSfile:DaoBreakDownRule.java,v $  $Revision: 1.0 $  $Date:2012-3-28 $
 *
 *$Log:DaoBreakDownRule.java,v $
 *********************************************************************/