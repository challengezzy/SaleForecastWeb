/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;

/**
 * @author liuzhen
 *
 */
public class DaoSystem extends Dao
{

	/**
	 * @param _session
	 */
	public DaoSystem( Session _session )
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
	
	public Date getSysTimeAsDate()
	{
		Object obj = null;
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			obj = this.getSession().createSQLQuery("select SYSDATE from DUAL").addScalar("SYSDATE", Hibernate.DATE).uniqueResult();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER))
		{
			obj = this.getSession().createSQLQuery("select  getdate() as SYSDATE").addScalar("SYSDATE", Hibernate.DATE).uniqueResult();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			obj = this.getSession().createSQLQuery("SELECT CURRENT TIMESTAMP AS SYSDATE FROM SYSIBM.SYSDUMMY1").addScalar("SYSDATE", Hibernate.DATE).uniqueResult();
		}
		if( obj == null )
		{
			return null;
		}
		return (Date)obj;
	}

	public Date getSysTimeAsTimeStamp()
	{
		Object obj = null;
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			obj = this.getSession().createSQLQuery("select SYSDATE from DUAL").addScalar("SYSDATE", Hibernate.TIMESTAMP).uniqueResult();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER))
		{
			obj = this.getSession().createSQLQuery("select  getdate() as SYSDATE").addScalar("SYSDATE", Hibernate.TIMESTAMP).uniqueResult();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			obj = this.getSession().createSQLQuery("SELECT CURRENT TIMESTAMP AS SYSDATE FROM SYSIBM.SYSDUMMY1").addScalar("SYSDATE", Hibernate.TIMESTAMP).uniqueResult();
		}
		if( obj == null )
		{
			return null;
		}
		return (Date)obj;		
	}		

}
