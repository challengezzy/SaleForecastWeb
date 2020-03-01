/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ProductLayer;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;

/**
 * @author liuzhen
 *
 */
public class DaoProductLayer extends Dao
{

	/**
	 * @param _session
	 */
	public DaoProductLayer( Session _session )
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
	
	public List<ProductLayer> getAllProductLayers()
	{
		Criteria crit = this.getSession().createCriteria( ProductLayer.class );		
		List<ProductLayer> rstList = crit.list();
		return rstList;	
	}
	
	public ProductLayer getProductLayerById( Long _id )
	{
		Object obj = this.getSession().get(ProductLayer.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (ProductLayer) obj;
	}	
	
	public ProductLayer getProductLayerByValue( int _value )
	{
		Criteria crit = this.getSession().createCriteria( ProductLayer.class );
		crit.add( Restrictions.eq("value", _value));
		
		ProductLayer rstObj = (ProductLayer) crit.uniqueResult();
		
		return rstObj;
	}		

	public int getProudctLayourValueByProductCode(String code)
	{
		String sql="select PRODUCTLAYER.VALUE "+
				   "from PRODUCTLAYER,PRODUCT where PRODUCT.CODE='"+code+"' "+
                   "and PRODUCT.PRODUCTLAYERID = PRODUCTLAYER.ID "	;		

		SQLQuery query =  this.getSession().createSQLQuery(sql);
		int value = ((BigDecimal)(query.list().get(0))).intValue();
		
		return value;	
	}
}
