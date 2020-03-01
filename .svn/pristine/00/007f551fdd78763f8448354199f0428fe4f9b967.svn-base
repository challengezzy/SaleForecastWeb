/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.PriceData;

/**
 * @author liuzhen
 *
 */
public class DaoPriceData extends Dao
{

	/**
	 * @param session
	 */
	public DaoPriceData( Session session ) {
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
	
	
	/**
	 * 
	 * @param _productId
	 * @param _organizationId
	 * @param _period
	 * @return
	 */
	public PriceData getPriceData( Long _productId, Long _organizationId, int _period )
	{
		Criteria crit = this.getSession().createCriteria(PriceData.class);
		crit.add(Restrictions.eq("product.id", _productId));		
		crit.add(Restrictions.eq("organization.id", _organizationId));
		crit.add(Restrictions.eq("period", _period));		
		PriceData historyData = (PriceData) crit.uniqueResult();

		return historyData;
	}
	
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 */	
	public int getPriceDatasStat( String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( PriceData.class );
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );		
		}		
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );

		Integer countValue = (Integer) crit.uniqueResult();
		
		return countValue;
	}	
	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 */
	public List<PriceData> getPriceDatas( String _sqlRestriction, int _pageIndex, int _pageSize )
	{
		Criteria crit = this.getSession().createCriteria( PriceData.class );
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );		
		}
		
		if( _pageIndex > 0 )
		{
			// 分页查询
			crit.setFirstResult( (_pageIndex-1) * _pageSize );
			crit.setMaxResults( _pageSize );
		}
		
		List<PriceData> rstList = crit.list();
		return rstList;
	}	
	
	
	
	

}
