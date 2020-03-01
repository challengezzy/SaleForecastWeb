/**********************************************************************
 *$RCSfile:DaoConversionSet.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ConversionSet;


/**
 * <li>Title: DaoConversionSet.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class DaoConversionSet extends Dao
{

	public DaoConversionSet(Session _session)
	{
		
		super(_session);
		// TODO Auto-generated constructor stub
		
	}

	public int getConversionSetStat(List<Long> Ids)
	{
		Criteria crit = this.getSession().createCriteria( ConversionSet.class );
		crit.add(Restrictions.in("product.id", Ids));
				
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );

		Integer countValue = (Integer) crit.uniqueResult();
		
		return countValue;
	}	
	
	public List<ConversionSet> getConversionSets(List<Long> Ids, int _pageIndex, int _pageSize)
	{
		Criteria crit = this.getSession().createCriteria(ConversionSet.class);
		if(Ids!=null)
		{
			crit.add(Restrictions.in("product.id", Ids));
		}
//		String sql=" where productid in ( -1 ";
//		for(Long id:Ids)
//		{
//			sql = sql+" , "+id;
//		}
//		sql  = sql+" )";
//		crit.add( Restrictions.sqlRestriction(sql) );
		if( _pageIndex > 0 )
		{
			// 分页查询
			crit.setFirstResult( (_pageIndex-1) * _pageSize );
			crit.setMaxResults( _pageSize );
		}
		
		List<ConversionSet> rstList = crit.list();		
		
		return rstList;
	}
	
	public ConversionSet getConversionSetByProductId(Long id)
	{
		Criteria crit = this.getSession().createCriteria(ConversionSet.class);
		crit.add(Restrictions.eq( "product.id", id ) );
		return (ConversionSet)crit.uniqueResult();
	}
}

/**********************************************************************
 *$RCSfile:DaoConversionSet.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:DaoConversionSet.java,v $
 *********************************************************************/