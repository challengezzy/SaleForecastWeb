/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.Unit;
import dmdd.dmddjava.dataaccess.dataobject.UnitGroup;

/**
 * @author liuzhen
 *
 */
public class DaoUnit extends Dao
{

	/**
	 * @param session
	 */
	public DaoUnit( Session session ) {
		super( session );
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		// TODO Auto-generated method stub

	}

	public Unit getUnitByCode(String _code,UnitGroup unitGroup)
	{
		Criteria crit = this.getSession().createCriteria(Unit.class);
		crit.add(Restrictions.eq( "code", _code ) );
		crit.add(Restrictions.eq("unitGroup", unitGroup));
		
		return (Unit)crit.uniqueResult();		
	}
}
