/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;

import dmdd.dmddjava.dataaccess.dataobject.SysPeriod;

/**
 * @author liuzhen
 *
 */
public class DaoSysPeriod extends Dao
{

	/**
	 * @param _session
	 */
	public DaoSysPeriod( Session _session )
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
	
	public SysPeriod getSysPeriod()
	{
		Criteria crit = this.getSession().createCriteria( SysPeriod.class );
		// 这个表里只会且只能有一条记录
		SysPeriod sysPeriod = (SysPeriod) crit.uniqueResult();		
		return sysPeriod;
	}	

}
