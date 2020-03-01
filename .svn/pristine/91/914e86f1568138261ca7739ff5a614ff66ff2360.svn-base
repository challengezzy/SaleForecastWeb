/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.OrganizationOrgCharacter;

/**
 * @author liuzhen
 *
 */
public class DaoOrganizationOrgCharacter extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOrganizationOrgCharacter( Session _session )
	{
		super( _session );
		// TODO Auto-generated constructor stub
	}

	public List<OrganizationOrgCharacter> getOrganizationOrgCharactersByOrgCharacterId(Long _orgCharacterid)
	{
		Criteria crit = this.getSession().createCriteria(OrganizationOrgCharacter.class);
		crit.add( Restrictions.eq( "organizationCharacter.id", _orgCharacterid ) );
		
		List<OrganizationOrgCharacter> list = crit.list();
		
		return list;
		
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

}
