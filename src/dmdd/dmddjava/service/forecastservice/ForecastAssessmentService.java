/**********************************************************************
 *$RCSfile:ForecastAssessmentService.java,v $  $Revision: 1.0 $  $Date:2012-3-21 $
 *********************************************************************/ 
package dmdd.dmddjava.service.forecastservice;

import java.util.List;

import org.apache.log4j.Logger;

import dmdd.dmddjava.dataaccess.aidobject.ABProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItem;
import dmdd.dmddjava.dataaccess.bizobject.BBizDataDefItemForecastAssessment;

/**
 * <li>Title: ForecastAssessmentService.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class ForecastAssessmentService
{
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 创建预测考核类
	 * @param _listProOrgs 从客户端传过来，已经是明细了
	 * @param _bBizdata
	 * @return
	 * @throws Exception
	 */
	public boolean createForecastAssessment(List<ABProOrg> _listProOrgs,BBizData _bBizdata) throws Exception
	{
		long start = System.currentTimeMillis();
		logger.info("开始创建版本预测数据");
		
		ForecastAssessmentMgmt mgmt = new ForecastAssessmentMgmt(_listProOrgs,_bBizdata);
		mgmt.excute();
		mgmt.getResult();
		
		logger.info("创建版本预测数据完成，耗时"+ (System.currentTimeMillis()-start) +" ms");
		
		return true;
	}

}

/**********************************************************************
 *$RCSfile:ForecastAssessmentService.java,v $  $Revision: 1.0 $  $Date:2012-3-21 $
 *
 *$Log:ForecastAssessmentService.java,v $
 *********************************************************************/