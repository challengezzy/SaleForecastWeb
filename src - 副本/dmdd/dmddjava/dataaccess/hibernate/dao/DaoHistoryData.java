/**
 * 
 */
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilStrKey;
import dmdd.dmddjava.common.utils.UtilString;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * @author liuzhen
 *
 */
public class DaoHistoryData extends Dao
{

	/**
	 * @param _session
	 */
	public DaoHistoryData( Session _session )
	{
		super( _session );
	}
	
	public List<ASumData> getSumHistoryDatas(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, Long _bizDataId)
	{
		List<Long> _list4BizDataId = new ArrayList<Long>(1);
		_list4BizDataId.add(_bizDataId);
		
		return getSumHistoryDatas(detailProOrgIds, _periodBegin, _periodEnd, _list4BizDataId);
	}

	/**
	 * 查询历史数据,按期间、业务数据ID汇总
	 * @param detailProOrgIds
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return
	 */
	public List<ASumData> getSumHistoryDatas(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, List<Long> _list4BizDataId)
	{
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
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
		crit.addOrder(Order.asc("PERIOD"));//按期间排序

		List<Object> objList = crit.list();
		
		if( objList != null )
		{
			List<ASumData> rstList = new ArrayList<ASumData>(objList.size());
			for( int i=0; i<objList.size(); i++ )
			{
				Object[] obj = (Object[])objList.get( i );
				
				ASumData aSumData = new ASumData();
				aSumData.setValue( (Double) obj[0] );
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
	 * 单独为立邦开发的查询value>0的加合
	 * @param _idsScopeStr4DetailProOrgs
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return
	 */
	public List<ASumData> getSumHistoryDatas4Nippon(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, List<Long> _list4BizDataId)
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
		sqlStr = sqlStr+" and value>0 ";
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
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
				aSumData.setValue( (Double) obj[0] );
				aSumData.setPeriod( (Integer) obj[1] );		
				aSumData.setBizData( (BizData) obj[2] );
				
				rstList.add( aSumData );
			}
		}

		return rstList;
	}	
	
	public ASumData getSumHistoryData(String _idsScopeStr4DetailProOrgs, int _period, Long _bizDataId)
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

		Criteria crit = this.getSession().createCriteria(HistoryData.class);
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
			aSumData.setValue( (Double) obj[0] );
			aSumData.setPeriod( (Integer) obj[1] );		
			aSumData.setBizData( (BizData) obj[2] );			
		}

		
		return aSumData;
	}	
	
	
	public List<ASumMoney> getHistoryDataSumMoneys(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId, int _peiceType )
	{		
		String querySql = ""; 
		if( _peiceType == BizConst.PRICE_TYPE_REAL )
		{
			querySql = "select historydata.period PERIOD, sum(historydata.value * pricedata.realprice) MONEY ";
		}
		else
		{
			querySql = "select historydata.period PERIOD, sum(historydata.value * standardprice) MONEY ";			
		}
		querySql = querySql + 
			//" select historydata.period PERIOD, sum(historydata.value * pricedata.realprice) MONEY" +
			" from historydata, pricedata " + 
			" where (historydata.productid = pricedata.productid) " +
			" and (historydata.organizationid = pricedata.organizationid) " + 
			" and (historydata.period = pricedata.period) " + 
			" and (historydata.period >= " + _periodBegin + " ) " +
			" and (historydata.period <= " + _periodEnd + " ) " +
			" and (historydata.bizdataid = " + _bizDataId + " ) " ;
			
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			querySql = querySql+" and (historydata.productid, historydata.organizationid) in " + _idsScopeStr4DetailProOrgs + 
			" group by historydata.period " ;
			
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			querySql = querySql+" and "+ UtilProOrg.format4mssqlstr4ids( _idsScopeStr4DetailProOrgs ,"historydata.productid","historydata.organizationid") + 
			" group by historydata.period " ;
		}
		
		
		SQLQuery query = getSession().createSQLQuery( querySql );
		query.addScalar( "PERIOD", Hibernate.INTEGER );	//	0
		query.addScalar( "MONEY", Hibernate.DOUBLE ); //	1
		List<Object> objList = query.list();		
		
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
	public boolean isExistHistoryDatas(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, Long _bizDataId)
	{
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
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
	public boolean isExistHistoryDatas(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId)
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
		
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
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
	
	
	public HistoryData getHistoryData( Long _productId, Long _organizationId, int _period, Long _bizDataId )
	{
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.eq("product.id", _productId));		
		crit.add(Restrictions.eq("organization.id", _organizationId));
		crit.add(Restrictions.eq("period", _period));		
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		HistoryData historyData = (HistoryData) crit.uniqueResult();

		return historyData;
	}	
	
	
	public List<HistoryData> getHistoryDatas( Long _productId, Long _organizationId, int _period, List<Long> _listBizDataId )
	{
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.eq("product.id", _productId));		
		crit.add(Restrictions.eq("organization.id", _organizationId));
		crit.add(Restrictions.eq("period", _period));		
		crit.add(Restrictions.in("bizData.id", _listBizDataId));
		
		List<HistoryData> rstList = crit.list();

		return rstList;
	}		
	
	public List<HistoryData> getHistoryDatas(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId)
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
		
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add( Restrictions.ge("period", _periodBegin) );
		crit.add( Restrictions.le("period", _periodEnd) );
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		
		List<HistoryData> rstList = crit.list();

		return rstList;
	}	
	
	public List<HistoryData> getHistoryDatas(String _idsScopeStr4DetailProOrgs, int _period, Long _bizDataId)
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
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add( Restrictions.eq("period", _period) );
		crit.add(Restrictions.eq("bizData.id", _bizDataId));
		
		List<HistoryData> rstList = crit.list();

		return rstList;
	}	
	
	public HashMap<Integer, Long> getHistoryDataSumGroupByPeriod(String _idsScopeStr4DetailProOrgs, List<Integer> _list4period,
			Long _id4HistoryBizData)
	{
		HashMap<Integer, Long> rstHashMap = new HashMap<Integer, Long>();

		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add(Restrictions.in("period", _list4period));
		crit.add(Restrictions.eq("bizData.id", _id4HistoryBizData));
		
		crit.setProjection( Projections.projectionList()
				.add( Projections.sum( "value" ), "VALUE" )
				.add( Projections.groupProperty( "period" ), "PERIOD" )
							);

		List<Object[]> rstList = crit.list();
		if (rstList != null && !(rstList.isEmpty()))
		{
			Iterator<Object[]> itr_rstList = rstList.iterator();
			while (itr_rstList.hasNext())
			{
				Object[] objArr = itr_rstList.next();
				Long valueSum = Math.round((Double) objArr[0]);
				Integer period = (Integer) objArr[1];
				rstHashMap.put(period, valueSum);
			}
		}

		return rstHashMap;
	}
	
	public HashMap<String, Long> getHistoryDataAvgGroupByPO(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _id4HistoryBizData)
	{
		HashMap<String, Long> rstHashMap = new HashMap<String, Long>();
		
		String sqlStr ="";
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE))
		{
			sqlStr= "(productid,organizationid) in " + _idsScopeStr4DetailProOrgs;	
		}
		else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)||ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2))
		{
			sqlStr = UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs);
		}
		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add(Restrictions.ge( "period", _periodBegin));
		crit.add(Restrictions.le( "period", _periodEnd));
		crit.add(Restrictions.eq("bizData.id", _id4HistoryBizData));
		
		crit.setProjection( Projections.projectionList()
				.add( Projections.avg( "value" ), "VALUE" )
				.add( Projections.groupProperty( "product" ), "PRODUCT" )
				.add( Projections.groupProperty( "organization" ), "ORGANIZATION" )
							);
		
		List<Object> objList = crit.list();
		
		if( objList != null )
		{
			for( int i=0; i<objList.size(); i++ )
			{
				Object[] obj = (Object[])objList.get( i );
				
				Long value = Math.round( (Double) obj[0] );
				Product product = (Product) obj[1];
				Organization organization = (Organization) obj[2];
				String strKey4po = UtilStrKey.getStrKey4PO( product, organization );
				
				rstHashMap.put( strKey4po, value );
			}
		}

		return rstHashMap;
	}
	
	
	public List<HistoryData> getHistoryDatas(String _idsScopeStr4DetailProOrgs, List<Integer> _list4Period, Long _id4BizData)
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

		Criteria crit = this.getSession().createCriteria(HistoryData.class);
		crit.add(Restrictions.in("period", _list4Period));
		crit.add(Restrictions.eq("bizData.id", _id4BizData));
		crit.add(Restrictions.sqlRestriction(sqlStr));

		List<HistoryData> rstList = crit.list();

		return rstList;
	}
	
	public int deleteHistoryDatas(Long productId, Long organizationId, int periodBegin,int periodEnd, List<Long> listBizDataId )
	{
		String hql = "delete from historydata where productid="+productId+" and organizationid=" + organizationId
			+ " and period>= " + periodBegin + " and period<= " + periodEnd 
			+ " and bizdataid in " + UtilString.getInNumberString(listBizDataId) ;

		return excuteBySql(hql);
	}

}
