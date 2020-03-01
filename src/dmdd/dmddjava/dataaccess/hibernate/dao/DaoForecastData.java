/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;

/**
 * @author liuzhen
 *
 */
public class DaoForecastData extends Dao
{

	/**
	 * @param _session
	 */
	public DaoForecastData( Session _session )
	{
		super( _session );
	}
	
	public List<ASumData> getSumForecastDatas(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, Long _bizDataId)
	{
		List<Long> _list4BizDataId = new ArrayList<Long>(1);
		_list4BizDataId.add(_bizDataId);
		
		return getSumForecastDatas(detailProOrgIds, _periodBegin, _periodEnd, _list4BizDataId);

	}

	/**
	 * 查询预测数据汇总，按期间、业务数据
	 * @param detailProOrgIds
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return
	 */
	public List<ASumData> getSumForecastDatas(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, List<Long> _list4BizDataId)
	{
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			String idsScopeStr4DetailProOrgs = "("+StringUtils.join(detailProOrgIds, ",") +")";
			sqlStr= "(productid,organizationid) in " + idsScopeStr4DetailProOrgs;
			crit.add(Restrictions.sqlRestriction(sqlStr));
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			//一条数据的条件 和多条的条件区分处理
			if(detailProOrgIds.size() == 1){
				String proOrgId = detailProOrgIds.get(0);
				String productid = proOrgId.substring(proOrgId.indexOf("(")+1, proOrgId.indexOf(",")).trim();
				String organizationid = proOrgId.substring(proOrgId.indexOf(",")+1, proOrgId.indexOf(")")).trim();
				crit.add( Restrictions.eq("product.id", new Long(productid)));
				crit.add( Restrictions.eq("organization.id", new Long(organizationid)) );
			}else{
				sqlStr = UtilProOrg.format4mssqlstr4ids(detailProOrgIds.toArray(new String[detailProOrgIds.size()]));
				crit.add(Restrictions.sqlRestriction(sqlStr));
			}
			
		}
		
		crit.add( Restrictions.ge("period", _periodBegin) );
		crit.add( Restrictions.le("period", _periodEnd) );
		crit.add(Restrictions.in("bizData.id", _list4BizDataId));
		
		crit.setProjection( Projections.projectionList()
										.add( Projections.sum( "value" ), "VALUE" )
										.add( Projections.groupProperty( "period" ), "PERIOD" )
										.add( Projections.groupProperty( "bizData" ), "BIZDATA" )
							);

		List<Object> objList = crit.list();
		
		
		if( objList != null )
		{
			List<ASumData> rstList = new ArrayList<ASumData>(objList.size());
			for( int i=0; i<objList.size(); i++ )
			{
				Object[] obj = (Object[])objList.get( i );
				
				ASumData aSumData = new ASumData();
				aSumData.setValue( (double)(Long) obj[0] );
				aSumData.setPeriod( (Integer) obj[1] );		
				aSumData.setBizData( (BizData) obj[2] );
				
				rstList.add( aSumData );
			}
			
			return rstList;
		}else{
			return new ArrayList<ASumData>(1);
		}

	}	
	
	/**
	 * 单独为立邦开发的查询预测类数据加合 value>0
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return
	 */
	public List<ASumData> getSumForecastDatas4Nippon(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, List<Long> _list4BizDataId)
	{
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}

		sqlStr = sqlStr+ " and value>0 ";
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add( Restrictions.ge("period", _periodBegin) );
		crit.add( Restrictions.le("period", _periodEnd) );
		crit.add(Restrictions.in("bizData.id", _list4BizDataId));
		
		crit.setProjection( Projections.projectionList()
										.add( Projections.sum( "value" ), "VALUE" )
										.add( Projections.groupProperty( "period" ), "PERIOD" )
										.add( Projections.groupProperty( "bizData" ), "BIZDATA" )
							);

		List<Object> objList = crit.list();
		
		List<ASumData> rstList = new ArrayList<ASumData>();
		if( objList != null )
		{
			for( int i=0; i<objList.size(); i++ )
			{
				Object[] obj = (Object[])objList.get( i );
				
				ASumData aSumData = new ASumData();
				aSumData.setValue( (double)(Long) obj[0] );
				aSumData.setPeriod( (Integer) obj[1] );		
				aSumData.setBizData( (BizData) obj[2] );
				
				rstList.add( aSumData );
			}
		}

		return rstList;
	}
	
	/**
	 * 
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _period
	 * @param _bizDataId
	 * @return
	 */
	public ASumData getSumForecastData(String _idsScopeStr4DetailProOrgs, int _period, Long _bizDataId)
	{
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add( Restrictions.eq("period", _period) );
		crit.add( Restrictions.eq("bizData.id", _bizDataId) );
		
		crit.setProjection( Projections.projectionList()
										.add( Projections.sum( "value" ), "VALUE" )
										.add( Projections.groupProperty( "period" ), "PERIOD" )
										.add( Projections.groupProperty( "bizData" ), "BIZDATA" )
							);

		Object[] obj = (Object[])crit.uniqueResult();
		ASumData aSumData = null;
		if( obj != null )
		{
			aSumData = new ASumData();
			aSumData.setValue( (double)(Long) obj[0] );
			aSumData.setPeriod( (Integer) obj[1] );		
			aSumData.setBizData( (BizData) obj[2] );			
		}

		
		return aSumData;
	}	
	
	
	public List<ASumMoney> getForecastDataSumMoneys(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId, int _peiceType )
	{		
		String querySql = ""; 
		if( _peiceType == BizConst.PRICE_TYPE_REAL )
		{
			querySql =   
			" select forecastdata.period PERIOD, sum(forecastdata.value * pricedata.realprice) MONEY ";
		}
		else
		{
			querySql =   
				" select forecastdata.period PERIOD, sum(forecastdata.value * standardprice) MONEY ";			
		}
		querySql = querySql + 
		//	" select forecastdata.period PERIOD, sum(forecastdata.value * pricedata.realprice) MONEY" +
			"   from forecastdata, pricedata " + 
			"  where (forecastdata.productid = pricedata.productid) " +
			"	 and (forecastdata.organizationid = pricedata.organizationid) " + 
			"	 and (forecastdata.period = pricedata.period) " + 
			"	 and (forecastdata.period >= " + _periodBegin + " ) " +
			"	 and (forecastdata.period <= " + _periodEnd + " ) " +
			"	 and (forecastdata.bizdataid = " + _bizDataId + " ) " ;
			
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			querySql = querySql+" and (forecastdata.productid, forecastdata.organizationid) in " + _idsScopeStr4DetailProOrgs + " " + 
			" group by forecastdata.period " ;
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{

			querySql = querySql+" and "+ UtilProOrg.format4mssqlstr4ids( _idsScopeStr4DetailProOrgs ,"forecastdata.productid","forecastdata.organizationid")+ " " + 
			" group by forecastdata.period " ;
		}
			
		List<Object> objList = this.getSession().createSQLQuery( querySql )
									.addScalar( "PERIOD", Hibernate.INTEGER )	//	0
									.addScalar( "MONEY", Hibernate.DOUBLE )		//	1
									.list();		
		
		List<ASumMoney> rstList = new ArrayList<ASumMoney>();
		if( objList != null )
		{
			for( int i=0; i<objList.size(); i++ )
			{
				Object[] obj = (Object[])objList.get( i );
				
				ASumMoney aSumMoney = new ASumMoney();
				aSumMoney.setPeriod( (Integer) obj[0] );	
				aSumMoney.setValue( (Double) obj[1] );

				rstList.add( aSumMoney );
			}
		}

		return rstList;
	}		
	
	
	/**
	 * 检查范围内是否有数据记录存在
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _bizDataId
	 * @return
	 */
	public boolean isExistForecastDatas(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, Long _bizDataId)
	{
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			String idsScopeStr4DetailProOrgs = "("+StringUtils.join(detailProOrgIds, ",") +")";
			sqlStr= "(productid,organizationid) in " + idsScopeStr4DetailProOrgs;
			crit.add(Restrictions.sqlRestriction(sqlStr));
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			//一条数据的条件 和多条的条件区分处理
			if(detailProOrgIds.size() == 1){
				String proOrgId = detailProOrgIds.get(0);
				String productid = proOrgId.substring(proOrgId.indexOf("(")+1, proOrgId.indexOf(",")).trim();
				String organizationid = proOrgId.substring(proOrgId.indexOf(",")+1, proOrgId.indexOf(")")).trim();
				crit.add( Restrictions.eq("product.id", new Long(productid)));
				crit.add( Restrictions.eq("organization.id", new Long(organizationid)) );
			}else{
				sqlStr = UtilProOrg.format4mssqlstr4ids(detailProOrgIds.toArray(new String[detailProOrgIds.size()]));
				crit.add(Restrictions.sqlRestriction(sqlStr));
			}
			
		}

		crit.add( Restrictions.ge("period", _periodBegin) );
		crit.add( Restrictions.le("period", _periodEnd) );
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );
		
		Integer countValue = (Integer) crit.uniqueResult();
		
		if( countValue.intValue() > 0 )
		{
			return true;
		}
		
		return false;
	}
	
	@Deprecated
	public boolean isExistForecastDatas(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId)
	{
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}		

		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add( Restrictions.ge("period", _periodBegin) );
		crit.add( Restrictions.le("period", _periodEnd) );
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );
		
		Integer countValue = (Integer) crit.uniqueResult();
		
		if( countValue.intValue() > 0 )
		{
			return true;
		}
		
		return false;
	}	
	
	
	/**
	 * 查询业务范围内的某期间的业务数据范围的预测数据
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _period
	 * @param _list4BizDataIds
	 * @return HashMap<String, ForecastData> : 
	 * 		String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
	 */
	public HashMap<String, ForecastData> getForecastDatas( String _idsScopeStr4DetailProOrgs, int _period, List<Long> _list4BizDataIds )
	{
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}

		HashMap<String, ForecastData> rstHashMap = new HashMap<String, ForecastData>();
		if( _list4BizDataIds == null || _list4BizDataIds.isEmpty() || _period == SysConst.PERIOD_NULL )
		{
			return rstHashMap;
		}

		Criteria crit = this.getSession().createCriteria( ForecastData.class );
		crit.add( Restrictions.sqlRestriction( sqlStr ) );		
		crit.add( Restrictions.eq( "period", _period ) );
		crit.add( Restrictions.in( "bizData.id", _list4BizDataIds ) );

		List<ForecastData> rstList = crit.list();
		if( rstList != null && !(rstList.isEmpty()) )
		{
			Iterator<ForecastData> itr_rstList = rstList.iterator();
			while( itr_rstList.hasNext() )
			{
				ForecastData forecastData = itr_rstList.next();
				String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
				rstHashMap.put( strKey4popb, forecastData );
			}
		}

		return rstHashMap;
	}
	
	/**
	 * 查询业务范围内的某期间的业务数据范围的预测数据
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _period
	 * @param _list4BizDataIds
	 * @return HashMap<String, ForecastData> : 
	 * 		String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
	 */
	public HashMap<String, ForecastData> getForecastDatasMap( String _idsScopeStr4DetailProOrgs, int _period, Long _bizDataId )
	{
		if( _bizDataId == null || _period == SysConst.PERIOD_NULL )
		{
			return new HashMap<String, ForecastData>();
		}
		
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}

		Criteria crit = this.getSession().createCriteria( ForecastData.class );
		crit.add( Restrictions.sqlRestriction( sqlStr ) );		
		crit.add( Restrictions.eq( "period", _period ) );
		crit.add( Restrictions.eq("bizData.id", _bizDataId));

		List<ForecastData> rstList = crit.list();
		HashMap<String, ForecastData> rstHashMap = new HashMap<String, ForecastData>(rstList.size());
		if( CollectionUtils.isNotEmpty(rstList) )
		{
			for( ForecastData forecastData :  rstList)
			{
				String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
				rstHashMap.put( strKey4popb, forecastData );
			}
		}

		return rstHashMap;
	}
	
	
	/**
	 * 
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _period
	 * @param _bizDataId
	 * @return
	 */
	public List<ForecastData> getForecastDatas(String _idsScopeStr4DetailProOrgs, int _period, Long _bizDataId)
	{
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add( Restrictions.eq("period", _period) );
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		
		List<ForecastData> rstList = crit.list();

		return rstList;
	}		

	/**
	 * 
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return List<ForecastData>
	 */
	public List<ForecastData> getForecastDatas(String _sqlRstidsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, List<Long> _list4BizDataId)
	{
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(_sqlRstidsScopeStr4DetailProOrgs));
		if( _periodBegin != SysConst.PERIOD_NULL )
		{
			crit.add( Restrictions.ge("period", _periodBegin) );
		}
		if( _periodEnd != SysConst.PERIOD_NULL )
		{
			crit.add( Restrictions.le("period", _periodEnd) );
		}		
		
		crit.add(Restrictions.in("bizData.id", _list4BizDataId));
		
		List<ForecastData> rstList = crit.list();

		return rstList;
	}	
	
	
	/**
	 * 
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _bizDataId
	 * @return List<ForecastData>
	 */
	public List<ForecastData> getForecastDatas(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId)
	{
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		if( _periodBegin != SysConst.PERIOD_NULL )
		{
			crit.add( Restrictions.ge("period", _periodBegin) );
		}
		if( _periodEnd != SysConst.PERIOD_NULL )
		{
			crit.add( Restrictions.le("period", _periodEnd) );
		}		
		
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		
		List<ForecastData> rstList = crit.list();

		return rstList;
	}	
	
	
	/**
	 * 
	 * @param _productId
	 * @param _organizationId
	 * @param _period
	 * @param _bizDataId
	 * @return
	 */
	public ForecastData getForecastData( Long _productId, Long _organizationId, int _period, Long _bizDataId )
	{
		Criteria crit = this.getSession().createCriteria( ForecastData.class );
		crit.add( Restrictions.eq( "product.id", _productId ) );
		crit.add( Restrictions.eq( "organization.id", _organizationId ) );
		crit.add( Restrictions.eq( "period", _period ) );
		crit.add( Restrictions.eq( "bizData.id", _bizDataId ) );

		ForecastData forecastData = (ForecastData) crit.uniqueResult();
		return forecastData;
	}	
	
	
	
	/**
	 * 
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return
	 */
	public boolean checkForecastDataStatusIsInactive(String _sqlRstidsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, List<Long> _list4BizDataId)
	{
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.sqlRestriction(_sqlRstidsScopeStr4DetailProOrgs));
		if( _periodBegin != SysConst.PERIOD_NULL )
		{
			crit.add( Restrictions.ge("period", _periodBegin) );
		}
		if( _periodEnd != SysConst.PERIOD_NULL )
		{
			crit.add( Restrictions.le("period", _periodEnd) );
		}
		crit.add(Restrictions.in("bizData.id", _list4BizDataId));
		crit.add(Restrictions.eq("status", BizConst.FORECASTDATA_STATUS_INACTIVE));
		
		crit.setProjection( Projections.projectionList().add( Projections.count( "id" ) ) );
		
		Integer countValue = (Integer) crit.uniqueResult();
		
		if( countValue.intValue() > 0 )
		{
			return true;
		}
		
		return false;

	}		
		

	/**
	 * 
	 * @param _productId
	 * @param _organizationId
	 * @param _list4period
	 * @param _list4BizDataIds
	 * @return HashMap<String, ForecastData>  UtilStrKey.getStrKey4POPB
	 */
	public HashMap<String, ForecastData> getForecastDatas( Long _productId, Long _organizationId, List<Integer>_list4period, List<Long> _list4BizDataIds )
	{
		HashMap<String, ForecastData> rstHashMap = new HashMap<String, ForecastData>();
		if( _list4BizDataIds == null || _list4BizDataIds.isEmpty() || _list4period == null || _list4period.isEmpty() )
		{
			return rstHashMap;
		}

		Criteria crit = this.getSession().createCriteria( ForecastData.class );
		crit.add( Restrictions.eq( "product.id", _productId ) );
		crit.add( Restrictions.eq( "organization.id", _organizationId ) );
		crit.add( Restrictions.in( "period", _list4period ) );
		crit.add( Restrictions.in( "bizData.id", _list4BizDataIds ) );
		
		List<ForecastData> rstList = crit.list();
		if( rstList != null && !(rstList.isEmpty()) )
		{
			Iterator<ForecastData> itr_rstList = rstList.iterator();
			while( itr_rstList.hasNext() )
			{
				ForecastData forecastData = itr_rstList.next();
				String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
				rstHashMap.put( strKey4popb, forecastData );
			}
		}		

		return rstHashMap;
	}	
	
	
	/**
	 * 
	 * @param _productId
	 * @param _organizationId
	 * @param _period
	 * @param _list4BizDataIds
	 * @return
	 */
	public HashMap<String, ForecastData> getForecastDatas( Long _productId, Long _organizationId, int _period, List<Long> _list4BizDataIds )
	{
		HashMap<String, ForecastData> rstHashMap = new HashMap<String, ForecastData>();
		if( _list4BizDataIds == null || _list4BizDataIds.isEmpty() )
		{
			return rstHashMap;
		}

		Criteria crit = this.getSession().createCriteria( ForecastData.class );
		crit.add( Restrictions.eq( "product.id", _productId ) );
		crit.add( Restrictions.eq( "organization.id", _organizationId ) );
		crit.add( Restrictions.eq( "period", _period ) );
		crit.add( Restrictions.in( "bizData.id", _list4BizDataIds ) );
		
		List<ForecastData> rstList = crit.list();
		if( rstList != null && !(rstList.isEmpty()) )
		{
			Iterator<ForecastData> itr_rstList = rstList.iterator();
			while( itr_rstList.hasNext() )
			{
				ForecastData forecastData = itr_rstList.next();
				String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
				rstHashMap.put( strKey4popb, forecastData );
			}
		}		

		return rstHashMap;
	}	
	
	
	/**
	 * 
	 * @param _productId
	 * @param _organizationId
	 * @param _list4period
	 * @param _bizDataId
	 * @return
	 */
	public HashMap<String, ForecastData> getForecastDatas( Long _productId, Long _organizationId, List<Integer>_list4period, Long _bizDataId )
	{
		HashMap<String, ForecastData> rstHashMap = new HashMap<String, ForecastData>();
		if( _list4period == null || _list4period.isEmpty() )
		{
			return rstHashMap;
		}

		Criteria crit = this.getSession().createCriteria( ForecastData.class );
		crit.add( Restrictions.eq( "product.id", _productId ) );
		crit.add( Restrictions.eq( "organization.id", _organizationId ) );
		crit.add( Restrictions.in( "period", _list4period ) );
		crit.add( Restrictions.eq( "bizData.id", _bizDataId ) );
		
		List<ForecastData> rstList = crit.list();
		if( rstList != null && !(rstList.isEmpty()) )
		{
			Iterator<ForecastData> itr_rstList = rstList.iterator();
			while( itr_rstList.hasNext() )
			{
				ForecastData forecastData = itr_rstList.next();
				String strKey4popb = UtilStrKey.getStrKey4POPB( forecastData.getProduct(), forecastData.getOrganization(), forecastData.getPeriod(), forecastData.getBizData() );
				rstHashMap.put( strKey4popb, forecastData );
			}
		}		

		return rstHashMap;
	}			

	public void activeForecastDatas( int _periodBegin, int _periodEnd )
	{
		String sqlUpdate = "update FORECASTDATA set STATUS = " + BizConst.FORECASTDATA_STATUS_ACTIVE + " where (1=1) ";
		if( _periodBegin != SysConst.PERIOD_NULL )
		{
			sqlUpdate = sqlUpdate + " and ( PERIOD >= " + _periodBegin + ") ";
		}
		if( _periodEnd != SysConst.PERIOD_NULL )
		{
			sqlUpdate = sqlUpdate + " and ( PERIOD <= " + _periodEnd + ") ";
		}

		this.getSession().createSQLQuery( sqlUpdate ).executeUpdate();
	}
	
	public void clearForecastDatasInScope( String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd )
	{
		String sqlUpdate ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlUpdate = "delete from FORECASTDATA where (PRODUCTID, ORGANIZATIONID) in " + _idsScopeStr4DetailProOrgs;
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlUpdate = "delete from FORECASTDATA where  " +UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}
	
		if( _periodBegin != SysConst.PERIOD_NULL )
		{
			sqlUpdate = sqlUpdate + " and ( PERIOD >= " + _periodBegin + ") ";
		}
		if( _periodEnd != SysConst.PERIOD_NULL )
		{
			sqlUpdate = sqlUpdate + " and ( PERIOD <= " + _periodEnd + ") ";
		}

		this.getSession().createSQLQuery( sqlUpdate ).executeUpdate();
	}
	
	
	public void clearForecastDatasOutScope( String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd )
	{
		String sqlUpdate ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlUpdate = "delete from FORECASTDATA where (PRODUCTID, ORGANIZATIONID) not in " + _idsScopeStr4DetailProOrgs;
			if( _periodBegin != SysConst.PERIOD_NULL )
			{
				sqlUpdate = sqlUpdate + " and ( PERIOD >= " + _periodBegin + ") ";
			}
			if( _periodEnd != SysConst.PERIOD_NULL )
			{
				sqlUpdate = sqlUpdate + " and ( PERIOD <= " + _periodEnd + ") ";
			}

			this.getSession().createSQLQuery( sqlUpdate ).executeUpdate();
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			String productid="",organizationid="";
			List<String> Ids4delte = delete4Ids(UtilProOrg.format4sqlstr4ids(_idsScopeStr4DetailProOrgs));
			for(String ids:Ids4delte)
			{
				productid = ids.substring(0,ids.indexOf(","));
				organizationid = ids.substring(ids.indexOf(",")+1);
				sqlUpdate=	"delete from FORECASTDATA where PRODUCTID="+productid+" and ORGANIZATIONID="+organizationid;
				
				if( _periodBegin != SysConst.PERIOD_NULL )
				{
					sqlUpdate = sqlUpdate + " and ( PERIOD >= " + _periodBegin + ") ";
				}
				if( _periodEnd != SysConst.PERIOD_NULL )
				{
					sqlUpdate = sqlUpdate + " and ( PERIOD <= " + _periodEnd + ") ";
				}
				System.out.println(sqlUpdate);
				this.getSession().createSQLQuery( sqlUpdate ).executeUpdate();				
			}
		}
		
		
	}
	
	public List<String> delete4Ids(String[] ids)
	{
		List<String> exsits = getAllProOrgDatas();
		int length = ids.length;
		for(int i=0;i<length;i++)
		{	
		    if(exsits.contains( ids[i] ))
		    {
		    	exsits.remove( ids[i] );
		    }
		}
		return exsits;
	}
	public long getForecastId(String productid,String orgid)
	{
		try
		{
		if(productid.trim().equals( "-1" )||orgid.trim().equals( "-1" ))
			return -1;
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add(Restrictions.eq( "product.id", Long.parseLong( productid )) );		
		crit.add(Restrictions.eq( "organization.id",Long.parseLong( orgid )) );		
		ForecastData data = (ForecastData)crit.uniqueResult();
		if(data==null)
			return -1;
		else return data.getId();
		}
		catch(Exception e)
		{
			System.out.println("出现错误，查询productid="+productid+" orgid="+orgid+ e.getMessage());
		}
		return -1;
	}
	
	public List<String> getAllProOrgDatas() 
	{
		List<String> rst = new ArrayList<String>();
		StringBuffer buff = new StringBuffer();
		buff.append("select PRODUCTID,ORGANIZATIONID from FORECASTDATA group by PRODUCTID,ORGANIZATIONID ");

		try
		{			
			SQLQuery query = this.getSession().createSQLQuery(buff.toString());
			query.addScalar("PRODUCTID",new org.hibernate.type.LongType());
			query.addScalar("ORGANIZATIONID",new org.hibernate.type.LongType());
			
			List result = query.list();
			for(Object obj:result)
			{
				Object[] objs = (Object[])obj;
				String code = new String();
				if(objs[0]!=null)
					code=""+(Long)objs[0];
				if(objs[1]!=null)
					code= code +","+(Long)objs[1];
				
				rst.add(code);
			}
			
		}
		catch( Exception ex )
		{		
			ex.printStackTrace();			
		}		
		
		return rst;
	}
	
	/**
	 * 根据产品所有预测数据汇总
	 * @param product
	 * @param bizdata
	 * @param periodBegin
	 * @param periodEnd
	 * @return
	 */
	public List<ASumData> getSumForecastData(BProduct product, BBizData bizData,int periodBegin,int periodEnd)
	{
		Criteria crit = this.getSession().createCriteria(ForecastData.class);
		crit.add( Restrictions.eq("product.id", product.getId()) );
		crit.add( Restrictions.ge("period", periodBegin) );
		crit.add( Restrictions.le("period", periodEnd) );
		crit.add( Restrictions.eq("bizData.id", bizData.getId()) );
		
		crit.setProjection(Projections.projectionList()
				.add(Projections.sum("value"), "VALUE")
				.add(Projections.groupProperty("period"), "PERIOD")
				.add(Projections.groupProperty("bizData"), "BIZDATA"));

		List<Object> objList = crit.list();
		if( objList != null )
		{
			List<ASumData> rstList = new ArrayList<ASumData>(objList.size());
			for( int i=0; i<objList.size(); i++ )
			{
				Object[] obj = (Object[])objList.get( i );
				
				ASumData aSumData = new ASumData();
				aSumData.setValue( (double)(Long) obj[0] );
				aSumData.setPeriod( (Integer) obj[1] );		
				aSumData.setBizData( (BizData) obj[2] );
				
				rstList.add( aSumData );
			}
			
			return rstList;
		}else{
			return new ArrayList<ASumData>(1);
		}
	}
	
}
