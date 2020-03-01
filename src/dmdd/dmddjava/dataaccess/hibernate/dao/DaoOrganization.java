/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.dataobject.Organization;

/**
 * @author liuzhen
 *
 */
public class DaoOrganization extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOrganization( Session _session )
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
	
	public Organization getOrganizationTreeRoot()
	{
		Criteria crit = this.getSession().createCriteria( Organization.class );
		crit.add( Restrictions.isNull( "parentOrganization.id" ) );
		Organization treeRootOrganization = (Organization)crit.uniqueResult();
		
		return treeRootOrganization;
	}	
	
	public Organization getOrganizationById(Long _id)
	{
		Object obj = this.getSession().get(Organization.class, _id);
		if( obj == null )
		{
			return null;
		}
 
		return (Organization) obj;
	}
	
	/**
	 * 查询 _id 的所有下级
	 * @param _id
	 * @param _blIncludeSelf	是否包含 _id 本身
	 * @return
	 */
	public List<Organization> getDescendentOrganizations( Long _id, boolean _blIncludeSelf )
	{
		if( _id == null )
		{
			return null;
		}
		
		List<Organization> rstList = new ArrayList<Organization>();
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			Criteria crit = this.getSession().createCriteria( Organization.class );
			
			String sqlRestriction = null;
			if( _blIncludeSelf == true )
			{
				sqlRestriction = " (1=1) start with id = " + _id + " connect by prior id = parentorganizationid ";
			}
			else
			{
				sqlRestriction = " (1=1) start with parentorganizationid = " + _id + " connect by prior id = parentorganizationid ";
			}
	
			crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
			
			rstList= crit.list();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			if( _blIncludeSelf == true )
			{
				rstList = getOrganizations(_id);
			}
			else
			{
				Criteria crit = this.getSession().createCriteria( Organization.class );
				String sqlRestriction = "parentorganizationid ="+_id;
				crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
				List<Organization> subList =crit.list();
				for(Organization organization:subList)
				{
					rstList.addAll(getOrganizations(organization.getId()));
				}
			}
		}
		return rstList;
	}
	
	public List<Organization> getOrganizations( String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( Organization.class );
		
		if( _sqlRestriction != null && !(_sqlRestriction.equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		}
		
		List<Organization> rstList = crit.list();
		
		return rstList;
	}		
	
	
	public Organization getDetailOrganizationByCode( String _code )
	{
		Criteria crit = this.getSession().createCriteria( Organization.class );
		crit.add(Restrictions.eq( "isCatalog", BizConst.GLOBAL_YESNO_NO) );
		crit.add(Restrictions.eq( "code", _code ) );
		return (Organization) crit.uniqueResult();
	}	
	
	public Organization getOrganizationByCode( String _code)
	{
		Criteria crit = this.getSession().createCriteria( Organization.class );
		//crit.add(Restrictions.eq( "name", _name) );
		crit.add(Restrictions.eq( "code", _code ) );
		return (Organization) crit.uniqueResult();
	}
	
	/**
	 * 获取ID以及ID下面所有的集合
	 * @param _id
	 * @return
	 */
	public List<Organization> getOrganizations(Long _id)
	{
		List<Organization> rstList = new ArrayList<Organization>();
		if(_id!=null)
		{
			Organization organization = getOrganizationById(_id);
			if(organization!=null)
			{
				rstList.add(organization);
			}
		}
		Criteria crit = this.getSession().createCriteria( Organization.class );
		String sqlRestriction = " PARENTORGANIZATIONID = "+_id;
		crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
		List<Organization> _subList = crit.list();
		for(Organization _organization:_subList)
		{
			rstList.addAll(getOrganizations(_organization.getId()));
		}
		return rstList;
	}
}
