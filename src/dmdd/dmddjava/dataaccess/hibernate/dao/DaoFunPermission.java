/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import dmdd.dmddjava.dataaccess.dataobject.FunPermission;

/**
 * @author liuzhen
 *
 */
public class DaoFunPermission extends Dao
{

	/**
	 * @param _session
	 */
	public DaoFunPermission( Session _session )
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
	
	public List<FunPermission> getAllFunPermissions()
	{
		Criteria crit = this.getSession().createCriteria( FunPermission.class );
		crit.addOrder(Order.asc("code"));
		
		List<FunPermission> rstList = crit.list();
		return rstList;
	}
	

}
