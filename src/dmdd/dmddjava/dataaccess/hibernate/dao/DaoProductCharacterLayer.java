/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ProductCharacterLayer;

/**
 * @author liuzhen
 *
 */
public class DaoProductCharacterLayer extends Dao
{

	/**
	 * @param _session
	 */
	public DaoProductCharacterLayer( Session _session )
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
	
	public List<ProductCharacterLayer> getAllProductCharacterLayers()
	{
		Criteria crit = this.getSession().createCriteria( ProductCharacterLayer.class );		
		List<ProductCharacterLayer> rstList = crit.list();
		return rstList;		
	}
	
	public ProductCharacterLayer getProductCharacterLayerById( Long _id )
	{
		Object obj = this.getSession().get(ProductCharacterLayer.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (ProductCharacterLayer) obj;
	}	
	
	public ProductCharacterLayer getProductCharacterLayerByValue( int _value )
	{
		Criteria crit = this.getSession().createCriteria( ProductCharacterLayer.class );
		crit.add( Restrictions.eq("value", _value));
		
		ProductCharacterLayer rstObj = (ProductCharacterLayer) crit.uniqueResult();
		
		return rstObj;
	}			

}
