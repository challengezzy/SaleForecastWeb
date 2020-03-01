/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.dataaccess.dataobject.ForecastInst;

/**
 * @author liuzhen
 *
 */
public class DaoForecastInst extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastInst( Session _session )
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
	
	/**
	 * 查询结果集统计信息
	 * @param _sqlRestriction
	 * @return
	 */
	public int getForecastInstsStat(String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria( ForecastInst.class );
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
	public List<ForecastInst> getForecastInsts(String _sqlRestriction, int _pageIndex, int _pageSize)
	{
		if(_sqlRestriction == null || _sqlRestriction.equals(""))
		{
			_sqlRestriction = " 1= 1";
		}
		Query query = this.getSession().createQuery("select new ForecastInst(id,code,name,fcPeriodNum,fzPeriodNum" +
				",errorThreshold,mappingFcModelRunTime" +
				//",finalFcBizData" +
				",distributeRefFormula" +
				",decomposeFormula"+
				//",distributeRefBizData" +
				",distributeRefPeriodNum,isValid" +
				//",runProductLayer" +
				//",runOrganizationLayer" +
				") from ForecastInst where "+_sqlRestriction ) ;

		if( _pageIndex > 0 )
		{
			// 分页查询
			query.setFirstResult( (_pageIndex-1) * _pageSize );
			query.setMaxResults( _pageSize );
		}
 		
		List<ForecastInst> rstList = query.list();
		return rstList;
	}	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 */
	public List<ForecastInst> getForecastInsts2(String _sqlRestriction, int _pageIndex, int _pageSize)
	{
		Criteria crit = this.getSession().createCriteria( ForecastInst.class );
		
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
 		
		List<ForecastInst> rstList = crit.list();
		return rstList;
	}
	public ForecastInst getForecastInstById(Long _id)
	{
		Object obj = this.getSession().get(ForecastInst.class, _id);
		if( obj == null )
		{
			return null;
		}
		
		return (ForecastInst) obj;
	}	
	
	
	public List<ForecastInst> getAllForecastInsts(Integer[] _arr4ForecastInstIsValid)
	{
		if( _arr4ForecastInstIsValid == null || _arr4ForecastInstIsValid.length <= 0 )
		{
			return null;
		}
		
		Criteria crit = this.getSession().createCriteria( ForecastInst.class );
		crit.add( Restrictions.in("isValid", _arr4ForecastInstIsValid));
		
		List<ForecastInst> rstList = crit.list();
		return rstList;
	}	
	
	
	public boolean isExistForecastInst( String _sqlRestriction )
	{
		Criteria crit = this.getSession().createCriteria(ForecastInst.class);
		if( _sqlRestriction != null && !(_sqlRestriction.trim().equals( "" )) )
		{
			crit.add(Restrictions.sqlRestriction(_sqlRestriction));
		}
				
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );
		
		Integer countValue = (Integer) crit.uniqueResult();
		
		if( countValue.intValue() > 0 )
		{
			return true;
		}
		
		return false;
	}		

}
