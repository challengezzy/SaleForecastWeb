/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.Bulletin;

/**
 * @author liuzhen
 *
 */
public class DaoBulletin extends Dao
{

	/**
	 * @param _session
	 */
	public DaoBulletin( Session _session )
	{
		super( _session );
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
	public int getBulletinsStat(String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( Bulletin.class );
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
	public List<Bulletin> getBulletins( String _sqlRestriction, int _pageIndex, int _pageSize )
	{
		Criteria crit = this.getSession().createCriteria( Bulletin.class );
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
		
		List<Bulletin> rstList = crit.list();
		return rstList;
	}	
	
	
	public Bulletin getBulletinById(Long _id)
	{
		Object obj = this.getSession().get(Bulletin.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (Bulletin) obj;
	}	
	
}
