/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMAAnalogItem;

/**
 * @author liuzhen
 *
 */
public class DaoForecastModelMAAnalogItem extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastModelMAAnalogItem( Session _session )
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
	
	public List<ForecastModelMAAnalogItem> getForecastModelMAAnalogItemsByForecastModelMAAnalogId(Long _forecastModelMAAnalogId )
	{	
		Criteria crit = this.getSession().createCriteria( ForecastModelMAAnalogItem.class );
		crit.add( Restrictions.eq("forecastModelMAAnalog.id", _forecastModelMAAnalogId));
		
		List<ForecastModelMAAnalogItem> rstList = crit.list();
		return rstList;		
	}	

}
