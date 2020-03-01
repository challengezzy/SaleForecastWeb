/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import dmdd.dmddjava.dataaccess.dataobject.SysParam;

/**
 * @author liuzhen
 *
 */
public class DaoSysParam extends Dao
{

	/**
	 * @param _session
	 */
	public DaoSysParam( Session _session )
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
	
	public List<SysParam> getAllSysParams()
	{
		Criteria crit = this.getSession().createCriteria( SysParam.class );
		
		List<SysParam> rstList = crit.list();
		return rstList;
	}	
	

}
