/**********************************************************************
 *$RCSfile:DaoForecastAssessment.java,v $  $Revision: 1.0 $  $Date:2012-3-22 $
 *********************************************************************/
package dmdd.dmddjava.dataaccess.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ASumData;
import dmdd.dmddjava.dataaccess.aidobject.ASumMoney;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastAssessmentData;

/**
 * <li>Title: DaoForecastAssessment.java</li> <li>Description: 简介</li> <li>
 * Project: dmdd</li> <li>Copyright: Copyright (c) 2012</li>
 * 
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class DaoForecastAssessment extends Dao {

	public DaoForecastAssessment(Session _session) {
		super(_session);
	}

	/**
	 * 查询版本预测数据，汇总到一行
	 * @param detailProOrgIds
	 * @param _periodBegin
	 * @param _periodEnd
	 * @param _list4BizDataId
	 * @return
	 */
	public List<ASumData> getForecastAssessment(List<String> detailProOrgIds, int _periodBegin, int _periodEnd, List<Long> _list4BizDataIds){
		Criteria crit = this.getSession().createCriteria(ForecastAssessmentData.class);
		
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

		crit.add(Restrictions.ge("period", _periodBegin));
		crit.add(Restrictions.le("period", _periodEnd));
		crit.add(Restrictions.in("bizData.id", _list4BizDataIds));

		crit.setProjection(Projections.projectionList().add(Projections.sum("value"), "VALUE").add(Projections.groupProperty("period"), "PERIOD")
				.add(Projections.groupProperty("bizData"), "BIZDATA"));

		List<Object> objList = crit.list();

		List<ASumData> rstList = new ArrayList<ASumData>();
		if (objList != null) {
			for (int i = 0; i < objList.size(); i++) {
				Object[] obj = (Object[]) objList.get(i);

				ASumData aSumData = new ASumData();
				aSumData.setValue((Double) obj[0]);
				aSumData.setPeriod((Integer) obj[1]);
				aSumData.setBizData((BizData) obj[2]);

				rstList.add(aSumData);
			}
		}

		return rstList;
	}
	
	@Deprecated
	public List<ASumData> getForecastAssessment(String detailIds, int beginPeriod, int endPeriod, List<Long> listbizdataids) {
		String sqlStr = "";
		if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
			sqlStr = "(productid,organizationid) in " + detailIds;
		} else if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE)
				.equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)
				|| ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_DB2)) {
			sqlStr = UtilProOrg.format4mssqlstr4ids(detailIds);
		}

		Criteria crit = this.getSession().createCriteria(ForecastAssessmentData.class);
		crit.add(Restrictions.sqlRestriction(sqlStr));
		crit.add(Restrictions.ge("period", beginPeriod));
		crit.add(Restrictions.le("period", endPeriod));
		crit.add(Restrictions.in("bizData.id", listbizdataids));

		crit.setProjection(Projections.projectionList().add(Projections.sum("value"), "VALUE").add(Projections.groupProperty("period"), "PERIOD")
				.add(Projections.groupProperty("bizData"), "BIZDATA"));

		List<Object> objList = crit.list();

		List<ASumData> rstList = new ArrayList<ASumData>();
		if (objList != null) {
			for (int i = 0; i < objList.size(); i++) {
				Object[] obj = (Object[]) objList.get(i);

				ASumData aSumData = new ASumData();
				aSumData.setValue((Double) obj[0]);
				aSumData.setPeriod((Integer) obj[1]);
				aSumData.setBizData((BizData) obj[2]);

				rstList.add(aSumData);
			}
		}

		return rstList;
	}

	public void delForecastAssessment(Long bizdataId) {
		String sqlUpdate = "delete from FORECASTASSESSMENTDATA where BIZDATAID =" + bizdataId;
		this.getSession().createSQLQuery(sqlUpdate).executeUpdate();
	}

	/** 查询计算后的金额数据 */
	public List<ASumMoney> getAssessmentDataSumMoneys(String _idsScopeStr4DetailProOrgs, int _periodBegin, int _periodEnd, Long _bizDataId,int _peiceType) {
		String querySql = "";
		if (_peiceType == BizConst.PRICE_TYPE_REAL) {
			querySql = " select d.period PERIOD, sum(d.value * p.realprice) MONEY ";
		} else {
			querySql = " select d.period PERIOD, sum(d.value * standardprice) MONEY ";
		}
		querySql = querySql
				+ "  from FORECASTASSESSMENTDATA d, pricedata p " + "  where (d.productid = p.productid) "
				+ "	 and (d.organizationid = p.organizationid) " + "	 and (d.period = p.period) "
				+ "	 and (d.period >= " + _periodBegin + " ) " + "	 and (d.period <= " + _periodEnd + " ) "
				+ "	 and (d.bizdataid = " + _bizDataId + " ) ";

		if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
			querySql = querySql + "	 and (d.productid, d.organizationid) in " + _idsScopeStr4DetailProOrgs + " "
					+ "  group by d.period ";

		} else if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)) {
			querySql = querySql + " and " + UtilProOrg.format4mssqlstr4ids(_idsScopeStr4DetailProOrgs, "d.productid", "d.organizationid") + " "
					+ "  group by d.period ";
		}

		List<Object> objList = getSession().createSQLQuery(querySql).addScalar("PERIOD", Hibernate.INTEGER) // 0
				.addScalar("MONEY", Hibernate.DOUBLE) // 1
				.list();

		List<ASumMoney> rstList = new ArrayList<ASumMoney>();
		if (objList != null) {
			for (int i = 0; i < objList.size(); i++) {
				Object[] obj = (Object[]) objList.get(i);
				ASumMoney aSumMoney = new ASumMoney();
				aSumMoney.setPeriod((Integer) obj[0]);
				aSumMoney.setValue((Double) obj[1]);
				rstList.add(aSumMoney);
			}
		}

		return rstList;
	}

}

/**********************************************************************
 * $RCSfile:DaoForecastAssessment.java,v $ $Revision: 1.0 $ $Date:2012-3-22 $
 * 
 * $Log:DaoForecastAssessment.java,v $
 *********************************************************************/
