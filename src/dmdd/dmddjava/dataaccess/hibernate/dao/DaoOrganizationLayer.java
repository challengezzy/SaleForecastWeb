/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.OrganizationLayer;

/**
 * @author liuzhen
 *
 */
public class DaoOrganizationLayer extends Dao
{

	/**
	 * @param _session
	 */
	public DaoOrganizationLayer( Session _session )
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
	
	public List<OrganizationLayer> getAllOrganizationLayers()
	{
		Criteria crit = this.getSession().createCriteria( OrganizationLayer.class );		
		List<OrganizationLayer> rstList = crit.list();
		return rstList;	
	}
	
	public OrganizationLayer getOrganizationLayerById( Long _id )
	{
		Object obj = this.getSession().get(OrganizationLayer.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (OrganizationLayer) obj;
	}	
	
	public OrganizationLayer getOrganizationLayerByValue( int _value )
	{
		Criteria crit = this.getSession().createCriteria( OrganizationLayer.class );
		crit.add( Restrictions.eq("value", _value));
		
		OrganizationLayer rstObj = (OrganizationLayer) crit.uniqueResult();
		
		return rstObj;
	}		

	public int getOrganizationLayourValueByOrganizationCode(String code)
	{
		String sql="select ORGANIZATIONLAYER.VALUE "+
				"from ORGANIZATION,ORGANIZATIONLAYER where ORGANIZATION.CODE='"+code+"' "+
				"and ORGANIZATION.ORGANIZATIONLAYERID = ORGANIZATIONLAYER.ID "	;		

		SQLQuery query =  this.getSession().createSQLQuery(sql);
		int value = ((BigDecimal)(query.list().get(0))).intValue();
		
		return value;	
	}
}
