/**********************************************************************
 *$RCSfile:OperatorUserProOrgImProcesser.java,v $  $Revision: 1.0 $  $Date:2012-2-6 $
 *********************************************************************/ 
package dmdd.dmddjava.service.securityservice.operatoruserproorgimport;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.IThreadProcess;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilOperatoruser;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUser;

/**
 * <li>Title: OperatorUserProOrgImProcesser.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class OperatorUserProOrgImProcesser implements IThreadProcess
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private OperatorUserProOrgImportMgmt mgmt ;
	private String userName;
	private List<AProOrg> listProOrg;
	
	public OperatorUserProOrgImProcesser(OperatorUserProOrgImportMgmt mgmt,String userName,List<AProOrg> listProOrg)
	{
		this.mgmt = mgmt;
		this.userName = userName;
		this.listProOrg = listProOrg;
	}
	
	public Object doProcess()
	{
		Session session = HibernateSessionFactory.getSession();

		try
		{
			
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			OperatorUser operatoruser = daoOperatorUser.getOperatorUserByUserName(userName);
			
			String isimportdetail = ServerEnvironment.getInstance().getConfigFileParam(BizConst.ISIMPORTDETAIL);
			
			//modify by zhangzy 2013-12-3 直接把业务范围数据换算成明细数据，进行导入存储。在客户端判断是时候实际上也是使用明细
			UtilOperatoruser.adddetailProOrgsToOperatorUser(operatoruser,listProOrg);
			
//			if(isimportdetail.equals( "TRUE" ))
//			{
//				UtilOperatoruser.adddetailProOrgsToOperatorUser(operatoruser,listProOrg);
//			}
//			else
//			{
//				UtilOperatoruser.addProOrgsToOperatorUser(operatoruser,listProOrg); //判断重复的在大数据量情况下，太蛋疼了，慢！
//			}
			System.out.println("-------------------用户："+userName+"导入用户业务范围完成!");
		}
		catch( Exception ex )
		{
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}	
		
		return null;
	}


	public Object doComplete()
	{
		logger.info("用户名："+userName+" 的业务范围导入成功");
		//mgmt.doResult();
		return null;
	}


	public Object doStart()
	{
		logger.info("用户名："+userName+" 的业务范围开始进行导入...");
		return null;
	}

}

/**********************************************************************
 *$RCSfile:OperatorUserProOrgImProcesser.java,v $  $Revision: 1.0 $  $Date:2012-2-6 $
 *
 *$Log:OperatorUserProOrgImProcesser.java,v $
 *********************************************************************/