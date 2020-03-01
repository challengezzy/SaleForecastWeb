/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacter;

/**
 * @author liuzhen
 *
 */
public class DaoOrganizationCharacter extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOrganizationCharacter( Session _session )
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
	
	public OrganizationCharacter getOrganizationCharacterTreeRoot()
	{
		Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
		crit.add( Restrictions.isNull( "parentOrganizationCharacter.id" ) );
		OrganizationCharacter rstObj = (OrganizationCharacter)crit.uniqueResult();
		
		return rstObj;
	}	
	
	public List<String> getAllOrganizationCharacterTypes()
	{
		List<String> rstList = new ArrayList<String>();
		
		Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
		crit.add( Restrictions.isNull( "parentOrganizationCharacter.id") );
		OrganizationCharacter treeRootOrganizationCharacter = (OrganizationCharacter) crit.uniqueResult();
		
		if( treeRootOrganizationCharacter != null && treeRootOrganizationCharacter.getSubOrganizationCharacters() != null && !(treeRootOrganizationCharacter.getSubOrganizationCharacters().isEmpty()) )
		{
			Iterator<OrganizationCharacter> itr_subOrganizationCharacter = treeRootOrganizationCharacter.getSubOrganizationCharacters().iterator();
			
			// 保证了第一层之间的特征类型是互不相同的
			while( itr_subOrganizationCharacter.hasNext() )
			{
				rstList.add( itr_subOrganizationCharacter.next().getType() );
			}			
		}
		
		return rstList;
	}	
	
	public OrganizationCharacter getOrganizationCharacterById(Long _id)
	{
		Object obj = this.getSession().get(OrganizationCharacter.class, _id);
		if( obj == null )
		{
			return null;
		}
 
		return (OrganizationCharacter) obj;
	}	
	
	/**
	 * 查询 _id 的所有下级
	 * @param _id
	 * @param _blIncludeSelf	是否包含 _id 本身
	 * @return
	 */
	public List<OrganizationCharacter> getDescendentOrganizationCharacters( Long _id, boolean _blIncludeSelf )
	{
		if( _id == null )
		{
			return null;
		}
		List<OrganizationCharacter> rstList = new ArrayList<OrganizationCharacter>();
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
			
			String sqlRestriction = null;
			if( _blIncludeSelf == true )
			{
				sqlRestriction = " (1=1) start with id = " + _id + " connect by prior id = parentorganizationcharacterid ";
			}
			else
			{
				sqlRestriction = " (1=1) start with parentorganizationcharacterid = " + _id + " connect by prior id = parentorganizationcharacterid ";
			}
	
			crit.add( Restrictions.sqlRestriction( sqlRestriction ) );
			
			rstList = crit.list();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			if( _blIncludeSelf == true )
			{
				rstList =getOrganizationCharacters(_id);
			}
			else
			{

				Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );				
				String sqlRestriction = " parentorganizationcharacterid = "+_id;
				crit.add( Restrictions.sqlRestriction( sqlRestriction ) );				
				List<OrganizationCharacter> subList = crit.list();
				for(OrganizationCharacter organizationCharacter:subList)
				{
					rstList.addAll(getOrganizationCharacters(organizationCharacter.getId()));
				}				
			}
		}
		return rstList;
	}
	
	
	public List<OrganizationCharacter> getOrganizationCharacters( String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
		if( _sqlRestriction != null && !(_sqlRestriction.equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		}
		List<OrganizationCharacter> rstList = crit.list();
		return rstList;
	}	
	
	public OrganizationCharacter getDetialOrganizationCharacterByCode( String _code )
	{
		Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
		crit.add(Restrictions.eq( "isCatalog", BizConst.GLOBAL_YESNO_NO) );
		crit.add(Restrictions.eq( "code", _code ) );
		return (OrganizationCharacter) crit.uniqueResult();
	}	

	public List<OrganizationCharacter> getOrganizationCharacters(Long _id)
	{
		List<OrganizationCharacter> rstList = new ArrayList<OrganizationCharacter>();
		if(_id!=null)
		{
			OrganizationCharacter organizationCharacter = getOrganizationCharacterById(_id);
			if(organizationCharacter!=null)
			{
				rstList.add(organizationCharacter);
			}
		}
		Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
		String _sqlRestriction=  "parentorganizationcharacterid = "+ _id;
		crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		List<OrganizationCharacter> subList = crit.list();
		for(OrganizationCharacter _organizationCharacter:subList)
		{
			rstList.addAll(getOrganizationCharacters(_organizationCharacter.getId()));
		}
		return rstList;
	}
	
	public List<OrganizationCharacter> getSubOrganizationCharacters(OrganizationCharacter _organizationCharacter)
	{
		List<OrganizationCharacter> rstList = new ArrayList<OrganizationCharacter>();
		if(_organizationCharacter!=null)
			rstList.add( _organizationCharacter );
		
		Criteria crit = this.getSession().createCriteria( OrganizationCharacter.class );
		String _sqlRestriction=  "parentorganizationcharacterid = "+ _organizationCharacter.getId();
		crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );
		List<OrganizationCharacter> subList = crit.list();
		for(OrganizationCharacter organizationCharacter:subList)
		{
			rstList.addAll(getSubOrganizationCharacters(organizationCharacter));
		}
		
		return rstList;
	}
}
