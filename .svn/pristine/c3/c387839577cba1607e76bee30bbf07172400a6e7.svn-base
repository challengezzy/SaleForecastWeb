/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ForecastModelMSLWmaItem;

/**
 * @author liuzhen
 *
 */
public class DaoForecastModelMSLWmaItem extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastModelMSLWmaItem( Session _session )
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
	
	public List<ForecastModelMSLWmaItem> getForecastModelMSLWmaItemsByForecastModelMSLWmaId(Long _forecastModelMSLWmaId )
	{	
		Criteria crit = this.getSession().createCriteria( ForecastModelMSLWmaItem.class );
		crit.add( Restrictions.eq("forecastModelMSLWma.id", _forecastModelMSLWmaId));
		
		List<ForecastModelMSLWmaItem> rstList = crit.list();
		return rstList;		
	}		

}
