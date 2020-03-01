/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.PeriodRollTask;

/**
 * @author liuzhen
 *
 */
public class DaoPeriodRollTask extends Dao
{

	/**
	 * @param _session
	 */
	public DaoPeriodRollTask( Session _session )
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
	
	public List<PeriodRollTask> getPeriodRollTasks( int _compilePeriod )
	{				
		Criteria crit = this.getSession().createCriteria(PeriodRollTask.class);
		
		crit.add( Restrictions.eq( "compilePeriod", _compilePeriod) );
		crit.addOrder( Order.asc( "seqNo" ) );
		
		List<PeriodRollTask> rstList = crit.list();		
		
		return rstList;
	}
	
	public PeriodRollTask getPeriodRollTaskById(Long _id)
	{
		Object obj = this.getSession().get(PeriodRollTask.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (PeriodRollTask) obj;
	}		

}
