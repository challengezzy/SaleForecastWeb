/**********************************************************************
 *$RCSfile:DaoConversionType.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *********************************************************************/ 
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ConversionType;

/**
 * <li>Title: DaoConversionType.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class DaoConversionType extends Dao
{

	public DaoConversionType(Session _session)
	{
		
		super(_session);
		// TODO Auto-generated constructor stub
		
	}

	public int getConversionTypeStat(String _sqlRestriction)
	{
		Criteria crit = this.getSession().createCriteria( ConversionType.class );
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction( _sqlRestriction ) );		
		}		
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );

		Integer countValue = (Integer) crit.uniqueResult();
		
		return countValue;
	}		
	
	public List<ConversionType> getConversionTypes(String _sqlRestriction, int _pageIndex, int _pageSize )
	{				
		Criteria crit = this.getSession().createCriteria(ConversionType.class);
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add( Restrictions.sqlRestriction(_sqlRestriction) );
		}
		
		if( _pageIndex > 0 )
		{
			// 分页查询
			crit.setFirstResult( (_pageIndex-1) * _pageSize );
			crit.setMaxResults( _pageSize );
		}
		
		List<ConversionType> rstList = crit.list();		
		
		return rstList;
	}
	
	public ConversionType getConversionTypeByCode(String _code)
	{
		Criteria crit = this.getSession().createCriteria(ConversionType.class);
		crit.add(Restrictions.eq( "code", _code ) );
		return (ConversionType)crit.uniqueResult();
	}
}

/**********************************************************************
 *$RCSfile:DaoConversionType.java,v $  $Revision: 1.0 $  $Date:2012-4-15 $
 *
 *$Log:DaoConversionType.java,v $
 *********************************************************************/