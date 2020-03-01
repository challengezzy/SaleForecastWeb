/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.OrganizationCharacterLayer;

/**
 * @author liuzhen
 *
 */
public class DaoOrganizationCharacterLayer extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOrganizationCharacterLayer( Session _session )
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
	
	public List<OrganizationCharacterLayer> getAllOrganizationCharacterLayers()
	{
		Criteria crit = this.getSession().createCriteria( OrganizationCharacterLayer.class );		
		List<OrganizationCharacterLayer> rstList = crit.list();
		return rstList;		
	}
	
	public OrganizationCharacterLayer getOrganizationCharacterLayerById( Long _id )
	{
		Object obj = this.getSession().get(OrganizationCharacterLayer.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (OrganizationCharacterLayer) obj;
	}	
	
	public OrganizationCharacterLayer getOrganizationCharacterLayerByValue( int _value )
	{
		Criteria crit = this.getSession().createCriteria( OrganizationCharacterLayer.class );
		crit.add( Restrictions.eq("value", _value));
		
		OrganizationCharacterLayer rstObj = (OrganizationCharacterLayer) crit.uniqueResult();
		
		return rstObj;
	}			

}
