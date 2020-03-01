/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMLWmaItem;

/**
 * @author liuzhen
 *
 */
public class DaoForecastModelMLWmaItem extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastModelMLWmaItem( Session _session )
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
	
	public List<ForecastModelMLWmaItem> getForecastModelMLWmaItemsByForecastModelMLWmaId(Long _forecastModelMLWmaId )
	{	
		Criteria crit = this.getSession().createCriteria( ForecastModelMLWmaItem.class );
		crit.add( Restrictions.eq("forecastModelMLWma.id", _forecastModelMLWmaId));
		
		List<ForecastModelMLWmaItem> rstList = crit.list();
		return rstList;		
	}	

}
