/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;
import dmdd.dmddjava.dataaccess.dataobject.UiPopbScope;

/**
 * @author liuzhen
 *
 */
public class DaoUiPopbScope extends Dao
{

	/**
	 * @param _session
	 */
	public DaoUiPopbScope( Session _session )
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
	public int getUiPopbScopesStat(String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( UiPopbScope.class );
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
	public List<UiPopbScope> getUiPopbScopes( String _sqlRestriction, int _pageIndex, int _pageSize )
	{
		Query query = this.getSession().createQuery("select new UiPopbScope(U.id,U.code,U.name,U.description,U.comments,U.uiCode) from UiPopbScope U where "+_sqlRestriction);
			if( _pageIndex > 0 )
			{
			// 分页查询
			query.setFirstResult( (_pageIndex-1) * _pageSize );
			query.setMaxResults( _pageSize );
			}
			List<UiPopbScope> result = query.list();
			return result;
	}	
	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 */
	public UiPopbScope getUiPopbScope( String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( UiPopbScope.class );
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );		
		}
		
		List<UiPopbScope> rstList = crit.list();
		if(rstList!=null && rstList.size()>0)
		return rstList.get(0);
		else return null;
	}
	
	public UiPopbScope getUiPopbScopeById(Long _id)
	{		
		Object obj = this.getSession().get(UiPopbScope.class, _id);
		if( obj == null )
		{
			return null;
		}
		return (UiPopbScope) obj;
	}		

}
