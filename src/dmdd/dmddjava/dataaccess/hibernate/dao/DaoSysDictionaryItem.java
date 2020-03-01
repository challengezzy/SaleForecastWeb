/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import dmdd.dmddjava.dataaccess.dataobject.SysDictionaryItem;

/**
 * @author liuzhen
 *
 */
public class DaoSysDictionaryItem extends Dao
{

	/**
	 * @param _session
	 */
	public DaoSysDictionaryItem( Session _session )
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
	
	public List<SysDictionaryItem> getAllSysDictionaryItems()
	{
		Criteria crit = this.getSession().createCriteria( SysDictionaryItem.class );
		crit.addOrder(Order.asc("className"))
			.addOrder(Order.asc("attributeName"))
			.addOrder(Order.asc("value"));
		
		List<SysDictionaryItem> rstList = crit.list();
		return rstList;
	}	

}
