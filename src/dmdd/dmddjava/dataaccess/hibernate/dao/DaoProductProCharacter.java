/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ProductProCharacter;

/**
 * @author liuzhen
 *
 */
public class DaoProductProCharacter extends Dao
{

	/**
	 * @param _session
	 */
	public DaoProductProCharacter( Session _session )
	{
		super( _session );
		// TODO Auto-generated constructor stub
	}

	public List<ProductProCharacter> getProductProCharactersByProCharacter(Long _proCharacterid)
	{
		
		Criteria crit = this.getSession().createCriteria(ProductProCharacter.class);
		crit.add( Restrictions.eq( "productCharacter.id", _proCharacterid ) );
		
		List<ProductProCharacter> list = crit.list();
		
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
