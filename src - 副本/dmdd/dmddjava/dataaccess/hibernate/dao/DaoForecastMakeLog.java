/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ForecastMakeLog;

/**
 * @author liuzhen
 *
 */
public class DaoForecastMakeLog extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastMakeLog( Session _session )
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
	public int getForecastMakeLogsStat(String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( ForecastMakeLog.class );
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
	public List<ForecastMakeLog> getForecastMakeLogs( String _sqlRestriction, int _pageIndex, int _pageSize )
	{
		Query query = this.getSession().createQuery( "select new ForecastMakeLog(id,compilePeriod,actionType,submitTime,submitter,description) from ForecastMakeLog where"+_sqlRestriction ) ;		

		if( _pageIndex > 0 )
		{
			// 分页查询
			query.setFirstResult( (_pageIndex-1) * _pageSize );
			query.setMaxResults( _pageSize );
		}
		
		List<ForecastMakeLog> rstList = query.list();
		return rstList;
	}	

	public ForecastMakeLog getForecastMakeLog( Long id)
	{
		Object obj = this.getSession().get(ForecastMakeLog.class,id);
		if( obj == null )
		{
			return null;
		}
		return (ForecastMakeLog)obj;
	}
		
}
