/**********************************************************************
 *$RCSfile:ForecastAssessmentProcesser.java,v $  $Revision: 1.0 $  $Date:2012-3-22 $
 *********************************************************************/ 
package dmdd.dmddjava.service.forecastservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.cool.common.constant.DMOConst;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.IThreadProcess;
import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.common.utils.UtilUUID;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastAssessmentData;
import dmdd.dmddjava.dataaccess.dataobject.ForecastData;
import dmdd.dmddjava.dataaccess.dataobject.HistoryData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoForecastData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoHistoryData;
import dmdd.dmddjava.dm.BasicDM;

/**
 * <li>Title: ForecastAssessmentProcesser.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ForecastAssessmentProcesser implements IThreadProcess
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private int beginPeriod ;
	private int endPeriod;
	private BBizData bizdata_source;
	private BBizData bizdata_fs;
	private BizData bizdata;
	private List<ABProOrg> listProOrgs;
	private ForecastAssessmentMgmt mgmt;
	
	private BasicDM basicDm = new BasicDM();
	
	public ForecastAssessmentProcesser(List<ABProOrg> listProOrgs,int beginPeriod,int endPeriod,BBizData bizdata_source,BBizData bizdata_fs,ForecastAssessmentMgmt mgmt)
	{
		this.beginPeriod = beginPeriod;
		this.endPeriod = endPeriod;
		this.bizdata_source = bizdata_source;
		this.bizdata_fs = bizdata_fs;
		this.listProOrgs = listProOrgs;
		this.mgmt = mgmt;
		
	}

	@Override
	public Object doProcess()
	{
		try{
			logger.info("使用新方法正在创建一批预测考核类数据");
			long start = System.currentTimeMillis();
			String tag = UtilUUID.uuid();
			
			String ids4DetailProOrgs = UtilProOrg.getIdsScopeStr4BProOrgs(listProOrgs);
			//插入产品组合临时数据供查询用
			basicDm.insertProORg(ids4DetailProOrgs, tag);
			//sql server语句
			String insertSql = "INSERT INTO FORECASTASSESSMENTDATA(PRODUCTID,ORGANIZATIONID,PERIOD,VALUE,BIZDATAID,VERSION) VALUES(?,?,?,?,"+ bizdata_fs.getId() +",0)";
			if(basicDm.isOracle()){
				insertSql = "INSERT INTO FORECASTASSESSMENTDATA(ID,PRODUCTID,ORGANIZATIONID,PERIOD,VALUE,BIZDATAID,VERSION)" +
						" VALUES(S_FORECASTASSESSMENTDATA.NEXTVAL ?,?,?,?,"+ bizdata_fs.getId() +", 0)";
			}
			
			//判断是需要查预测数据还是历史数据
			String dataTable = "FORECASTDATA";
			if( bizdata_source.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
					bizdata_source.getType() == BizConst.BIZDATA_TYPE_HISTORYAD || 
					bizdata_source.getType() == BizConst.BIZDATA_TYPE_HISTORYADR ){
				//历史类
				dataTable = "HISTORYDATA";
			}else if( bizdata_source.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					bizdata_source.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
					bizdata_source.getType() == BizConst.BIZDATA_TYPE_FCFINAL ){
				//预测类
				dataTable = "FORECASTDATA";
			}
			
			String qrySql = "SELECT A.PRODUCTID,A.ORGANIZATIONID,A.PERIOD,A.VALUE "
					+ " FROM "+ dataTable +" A,QUERY_PRODORG B "
					+ " WHERE A.PRODUCTID = B.PRODUCTID AND A.ORGANIZATIONID = B.ORGANIZATIONID"
					+ " AND PERIOD>="+ beginPeriod +" AND PERIOD<= "+ endPeriod + " AND BIZDATAID = " + bizdata_source.getId()
					+ " AND QUERYTAG= '"+tag+"' ";
			
			CommDMO dmo = new CommDMO();
			
			int[] fromcols = new int[]{1,2,3,4}; //共4个字段
			//批量进行数据查询保存
			long rows = dmo.executeImportByDS(DMOConst.DS_DEFAULT, qrySql, fromcols, DMOConst.DS_DEFAULT, insertSql, 500);
			
			logger.info("保存["+bizdata_source.getName()+"]版本预测数据耗时"+ (System.currentTimeMillis()-start) +" ms,记录["+ rows +"]条");
		}catch (Exception e) {
			logger.error("进行版本预测数据["+bizdata_fs.getName()+"]保存时异常");
			e.printStackTrace();
		}
		
//		Session session = HibernateSessionFactory.getSession();
//		Transaction trsa = null;
//		try
//		{
//			long start = System.currentTimeMillis();
//			//老的版本预测数据保存
//			trsa = session.beginTransaction();
//			BizDataBDConvertor convertor = new BizDataBDConvertor();
//			bizdata = convertor.btod(bizdata_fs, true);
//			//从数据库中查询数据
//			List<ForecastAssessmentData> list =  getBizDataValue(session);
//			logger.info("查询["+bizdata_source.getName()+"]版本预测数据耗时"+ (System.currentTimeMillis()-start) +" ms,记录["+ list.size() +"]条");
//			
//			//保存到数据库
//			start = System.currentTimeMillis();
//			DaoForecastAssessment dao = new DaoForecastAssessment(session);
//			for(ForecastAssessmentData data:list)
//			{
//				dao.save(data);
//			}
//			trsa.commit();
//			
//			logger.info("保存["+bizdata_source.getName()+"]版本预测数据耗时"+ (System.currentTimeMillis()-start) +" ms,记录["+ list.size() +"]条");
//		}catch (Exception ex) {
//			if (trsa != null) {
//				trsa.rollback();
//			}
//			ex.printStackTrace();
//
//		} finally {
//			session.close();
//		}
		return null;
	}

	@Override
	public Object doComplete()
	{
		logger.info("处理一批预测考核类数据完成");
		mgmt.doResult();
		return null;
	}

	@Override
	public Object doStart()
	{
		
		return null;
	}
	
	private List<ForecastAssessmentData> getBizDataValue(Session session)
	{
		List<ForecastAssessmentData> list = new ArrayList<ForecastAssessmentData>();
		if( bizdata_source.getType() == BizConst.BIZDATA_TYPE_HISTORY ||
				bizdata_source.getType() == BizConst.BIZDATA_TYPE_HISTORYAD || 
				bizdata_source.getType() == BizConst.BIZDATA_TYPE_HISTORYADR )
			{
				List<HistoryData> result = getHistoryBizDataValue(session);
				for(HistoryData data:result)
				{
					ForecastAssessmentData forecastAssessmentData = new ForecastAssessmentData();
					forecastAssessmentData.setBizData(bizdata);
					forecastAssessmentData.setPeriod(data.getPeriod());
					forecastAssessmentData.setOrganization(data.getOrganization());
					forecastAssessmentData.setProduct(data.getProduct());
					forecastAssessmentData.setValue(data.getValue());
					list.add(forecastAssessmentData);
				}
			}			
			else if( bizdata_source.getType() == BizConst.BIZDATA_TYPE_FCMODEL ||
					bizdata_source.getType() == BizConst.BIZDATA_TYPE_FCHAND ||
					bizdata_source.getType() == BizConst.BIZDATA_TYPE_FCFINAL )
			{
				List<ForecastData> result = getForecastValue(session);
				for(ForecastData data:result)
				{
					ForecastAssessmentData forecastAssessmentData = new ForecastAssessmentData();
					forecastAssessmentData.setBizData(bizdata);
					forecastAssessmentData.setPeriod(data.getPeriod());
					forecastAssessmentData.setOrganization(data.getOrganization());
					forecastAssessmentData.setProduct(data.getProduct());
					forecastAssessmentData.setValue( (double)data.getValue());
					list.add(forecastAssessmentData);
				}				
			}		
			
		return list;
	}
	
	private List<HistoryData> getHistoryBizDataValue(Session session)
	{
		List<HistoryData> list = null;
		DaoHistoryData daoHistoryData = new DaoHistoryData( session );			
		list = daoHistoryData.getHistoryDatas(UtilProOrg.getIdsScopeStr4BProOrgs(listProOrgs), beginPeriod, endPeriod, bizdata_source.getId());
		return list;
	}
	
	private List<ForecastData> getForecastValue(Session session)
	{
		List<ForecastData> list = null;
		DaoForecastData daoForecastData = new DaoForecastData( session );		
		list = daoForecastData.getForecastDatas( UtilProOrg.getIdsScopeStr4BProOrgs(listProOrgs), beginPeriod, endPeriod,bizdata_source.getId()   );	
		return list;
	}
	
}

/**********************************************************************
 *$RCSfile:ForecastAssessmentProcesser.java,v $  $Revision: 1.0 $  $Date:2012-3-22 $
 *
 *$Log:ForecastAssessmentProcesser.java,v $
 *********************************************************************/