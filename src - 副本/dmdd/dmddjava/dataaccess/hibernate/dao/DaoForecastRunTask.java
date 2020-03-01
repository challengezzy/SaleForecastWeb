/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ForecastRunTask;

/**
 * @author liuzhen
 *
 */
public class DaoForecastRunTask extends Dao
{

	/**
	 * @param session
	 */
	public DaoForecastRunTask( Session session ) {
		super( session );
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 查询_compilePeriod 的最近的预测运行任务
	 * @param _compilePeriod
	 * @return
	 */
	public ForecastRunTask getLatestForecastRunTask( int _compilePeriod )
	{				
		Criteria crit = this.getSession().createCriteria(ForecastRunTask.class);
		
		crit.add( Restrictions.eq( "compilePeriod", _compilePeriod) );
		crit.addOrder( Order.desc( "id" ) );
		crit.setFetchSize(1);
		
		
		List<ForecastRunTask> rstList = crit.list();
		if( rstList != null && !(rstList.isEmpty()) )
		{
			return rstList.get( 0 );
		}
		
		return null;
	}	
	
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 */
	public int getForecastRunTasksStat(String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( ForecastRunTask.class );
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
	public List<ForecastRunTask> getForecastRunTasks(String _sqlRestriction, int _pageIndex, int _pageSize)
	{
		Criteria crit = this.getSession().createCriteria( ForecastRunTask.class );
		
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
 		
		List<ForecastRunTask> rstList = crit.list();
		return rstList;
	}		

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
