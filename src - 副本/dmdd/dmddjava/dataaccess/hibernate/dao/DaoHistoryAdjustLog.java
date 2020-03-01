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

import dmdd.dmddjava.dataaccess.dataobject.HistoryAdjustLog;

/**
 * @author liuzhen
 *
 */
public class DaoHistoryAdjustLog extends Dao
{

	/**
	 * @param _session
	 */
	public DaoHistoryAdjustLog( Session _session )
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
	public int getHistoryAdjustLogsStat(String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( HistoryAdjustLog.class );
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
	public List<HistoryAdjustLog> getHistoryAdjustLogs( String _sqlRestriction, int _pageIndex, int _pageSize )
	{
		Query query =null;
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			 query= this.getSession().createQuery("select new HistoryAdjustLog(id,compilePeriod,submitTime,submitter,description) from HistoryAdjustLog where "+ _sqlRestriction ) ;		
		}
		
		if( _pageIndex > 0 )
		{
			// 分页查询
			query.setFirstResult( (_pageIndex-1) * _pageSize );
			query.setMaxResults( _pageSize );
		}
		
		List<HistoryAdjustLog> rstList = query.list();
		return rstList;
	}	

	public HistoryAdjustLog getHistoryAdjustLog(Long id)
	{
		Criteria crit = this.getSession().createCriteria( HistoryAdjustLog.class );
		crit.add(  Restrictions.eq( "id",id ) );		
		
		return (HistoryAdjustLog)crit.uniqueResult();
	}
	
}
