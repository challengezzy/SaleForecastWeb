/**********************************************************************
 *$RCSfile:OperatorUserProOrgImportMgmt.java,v $  $Revision: 1.0 $  $Date:2012-2-6 $
 *********************************************************************/ 
package dmdd.dmddjava.service.securityservice.operatoruserproorgimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.DmddThreadPool;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.aidobject.ABImOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUser;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOrganization;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoProduct;

/**
 * <li>Title: OperatorUserProOrgImportMgmt.java</li>
 * <li>Description: 简介</li>
 * <li>Project: dmdd</li>
 * <li>Copyright: Copyright (c) 2012</li>
 * @Company: GXLU. All Rights Reserved.
 * @author luowang Of dmdd
 * @version 1.0
 */
public class OperatorUserProOrgImportMgmt
{
	private final Logger logger = Logger.getLogger(this.getClass());
	private int count=0;//计数器
	private int total=0;
	private volatile boolean  isok = false;
	private List<ABImOperatorUserProOrg> _lisoperatoruserproorg;
	private List<ABImOperatorUserProOrg> res = new ArrayList<ABImOperatorUserProOrg>();	
	private HashMap<String,List<AProOrg>> userdata4exist= new HashMap<String,List<AProOrg>>();
	
	public OperatorUserProOrgImportMgmt(List<ABImOperatorUserProOrg> _lisoperatoruserproorg)
	{
		this._lisoperatoruserproorg = _lisoperatoruserproorg;
	}
	

	
	public void excute()
	{			
		//第一步将所有数据归并
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		List<String> list4unexist= new ArrayList<String>();//存放不存在的用户名
		try
		{		
			trsa = session.beginTransaction();
			for(ABImOperatorUserProOrg abImOperatorUserProOrg:_lisoperatoruserproorg)
			{
				String username=abImOperatorUserProOrg.getusername();
				if(list4unexist.contains(username))
				{
					String errorInfo = "Can not found this username:"+username;
					abImOperatorUserProOrg.seterrorInfo( errorInfo );
					res.add(abImOperatorUserProOrg);
					continue;
				}
				
				if(!userdata4exist.containsKey(username))//第一次处理该用户名
				{				
					DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
					OperatorUser operatoruser = daoOperatorUser.getOperatorUserByUserName(username);
					if(operatoruser==null)//用户不存在
					{	
						String errorInfo = "Can not found this username:"+username;
						abImOperatorUserProOrg.seterrorInfo( errorInfo );
						res.add(abImOperatorUserProOrg);
						list4unexist.add(username);
						continue;
					}	
					else//用户存在
					{
						//将该用户的业务范围取出来，存放到userdata4exist中
						List<AProOrg> proorgs= new ArrayList<AProOrg>();
						AProOrg aProOrg= getProOrg(abImOperatorUserProOrg,res);
						if(aProOrg==null)
							continue;
						proorgs.add(aProOrg);
						userdata4exist.put(username, proorgs);
					}					
				}
				else if(userdata4exist.containsKey(username))//已经查找过该用户名
				{					
					AProOrg aProOrg= getProOrg(abImOperatorUserProOrg,res);	
					if(aProOrg==null)
						continue;
					List<AProOrg> proOrgs=userdata4exist.get(username);
					proOrgs.add(aProOrg);					
				}	
			}
			
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}	
		total= userdata4exist.size();
		for(String username:userdata4exist.keySet())
		{
			OperatorUserProOrgImProcesser processer = new OperatorUserProOrgImProcesser(this, username, userdata4exist.get(username));
			try
			{
				DmddThreadPool.getinstance().addThreadProcessMgmt( processer );
			}
			catch( InterruptedException e )
			{
				logger.error( "导入用户业务范围出现问题",e );
			}
		}
	}
	
	/**
	 * 判断产品和组织有效性并拼装有效的OperatorUserProOrg
	 * @param operatorUser
	 * @param _abImOperatorUserProOrg
	 * @param _session
	 * @param _res
	 * @return
	 */
	private AProOrg getProOrg(ABImOperatorUserProOrg _abImOperatorUserProOrg,List<ABImOperatorUserProOrg> _res)
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			
			String isimportdetail = ServerEnvironment.getInstance().getConfigFileParam(BizConst.ISIMPORTDETAIL);
		
			//验证产品（组织）有效性
			DaoProduct daoProduct = new DaoProduct(session);
			Product product = null;// daoProduct.getProductByCode(_abImOperatorUserProOrg.getproductcode());
			if(isimportdetail.equals( "TRUE" ))
			{
				product = daoProduct.getDetailProductByCode( _abImOperatorUserProOrg.getproductcode());
			}
			else
			{
				product = daoProduct.getProductByCode(_abImOperatorUserProOrg.getproductcode());
			}
			
			if(product==null)
			{
				_abImOperatorUserProOrg.seterrorInfo("Can not found this product");
				_res.add(_abImOperatorUserProOrg);
				return null;
			}
			DaoOrganization daoOrganizaion = new DaoOrganization(session);
			String orgCode=_abImOperatorUserProOrg.getorganizationcode();
			if(orgCode.indexOf("\r")>0)
			{
				orgCode = orgCode.substring(0, orgCode.indexOf("\r"));
			}
			Organization organization =null; daoOrganizaion.getOrganizationByCode(orgCode);
			if(isimportdetail.equals( "TRUE" ))
			{
				organization = daoOrganizaion.getDetailOrganizationByCode( orgCode);
			}
			else
			{
				organization = daoOrganizaion.getOrganizationByCode(orgCode);
			}
			
			if(organization==null)
			{
				_abImOperatorUserProOrg.seterrorInfo("Can not found this organizaion");
				_res.add(_abImOperatorUserProOrg);
				return null;
			}
			
			AProOrg aProOrg= new AProOrg();
			aProOrg.setOrganization(organization);
			aProOrg.setProduct(product);
			return aProOrg;
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			if( session != null && session.isOpen() )
			{
				session.close();
			}
			ex.printStackTrace();
		}
	
		return null;
	}
	
	public void doResult()
	{		
		count=count+1;
		System.out.println("count:"+count);
		if(count==total)
			resume();
			
	}
	
	public List<ABImOperatorUserProOrg>  getResult()
	{
		//this.check();
		return res;
	}
	
	//用来监控是否已经完成所有任务
	private void check()
	{
		while(true)
		{
			try
			{
				if(isOk())
				{
					Thread.interrupted();
					return ;
				}
				else 
				{					
					Thread.sleep( 1000 );					
				}
			}
			catch( InterruptedException e )
			{
				logger.info( "",e );
			}
		}
	}
	private void resume()
	{
		isok=true;
	}
	
	private boolean isOk()
	{
		return isok;
	}
}

/**********************************************************************
 *$RCSfile:OperatorUserProOrgImportMgmt.java,v $  $Revision: 1.0 $  $Date:2012-2-6 $
 *
 *$Log:OperatorUserProOrgImportMgmt.java,v $
 *********************************************************************/