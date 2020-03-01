/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;

/**
 * @author liuzhen
 *
 */
public class DaoUnitGroup extends Dao
{

	/**
	 * @param session
	 */
	public DaoUnitGroup( Session session ) {
		super( session );
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 */
	public int getUnitGroupsStat(String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( UnitGroup.class );
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );		
		}		
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );

		Integer countValue = (Integer) crit.uniqueResult();
		
		return countValue;
	}	
	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 */
	public List<UnitGroup> getUnitGroups( String _sqlRestriction, int _pageIndex, int _pageSize )
	{
		Criteria crit = this.getSession().createCriteria( UnitGroup.class );
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
		
		List<UnitGroup> rstList = crit.list();
		return rstList;
	}	
	
	public UnitGroup getUnitGroupById(Long _id)
	{
		Object obj = this.getSession().get(UnitGroup.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (UnitGroup) obj;
	}	
	
	public UnitGroup getUnitGroupByCode(String _code)
	{
		Criteria crit = this.getSession().createCriteria(UnitGroup.class);
		crit.add(Restrictions.eq( "code", _code ) );

		return (UnitGroup)crit.uniqueResult();
	}	

}
