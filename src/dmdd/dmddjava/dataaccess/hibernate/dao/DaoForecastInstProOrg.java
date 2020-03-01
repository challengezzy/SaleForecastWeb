/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import dmdd.dmddjava.dataaccess.dataobject.ForecastInstProOrg;

/**
 * @author liuzhen
 *
 */
public class DaoForecastInstProOrg extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastInstProOrg( Session _session )
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
	
	public List<ForecastInstProOrg> getAllForecastInstProOrgs()
	{
		Criteria crit = this.getSession().createCriteria( ForecastInstProOrg.class );
		List<ForecastInstProOrg> rstList = crit.list();
		
		return rstList;
	}			

}
