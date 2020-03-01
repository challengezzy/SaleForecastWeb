
/**
 * 
 */

package dmdd.dmddjava.service.securityservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.common.utils.UtilMD5;
import dmdd.dmddjava.dataaccess.aidobject.ABImOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.bdconvertor.FunPermissionBDConvertor;
import dmdd.dmddjava.dataaccess.bdconvertor.OperatorUserBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BFunPermission;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.dataobject.FunPermission;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUser;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserBizData;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserFunPermission;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserProOrg;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoFunPermission;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUser;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUserBizData;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUserFunPermission;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoSystem;
import dmdd.dmddjava.dm.MainDataDM;
import dmdd.dmddjava.dm.UiPopbScopeDM;
import dmdd.dmddjava.service.securityservice.operatoruserproorgimport.OperatorUserProOrgImportMgmt;

/**
 * @author liuzhen
 * 
 */
public class SecurityService
{

	private Logger logger = CoolLogger.getLogger(this.getClass());	
	
	private SecurityQueryService queryService = new SecurityQueryService();
	
	/**
	 * 
	 */
	public SecurityService()
	{
		// TODO Auto-generated constructor stub
	}



	// OperatorUser begin
	public BOperatorUser newOperatorUser( BOperatorUser _bOperatorUser4new ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOperatorUser4new == null )
		{
			Exception ex = new Exception( "要保存的用户对象是空对象，不能保存" );
			throw ex;
		}
		
		//	从服务器环境变量中读取用户许可数		begin
		int userLicenseNum = 3;	//	测试版用户许可数为3
		BSysParam sysParam4UserLicenseNum = ServerEnvironment.getInstance().getSysParam( BizConst.SYSPARAM_CODE_USERLICENSENUM );
		if( sysParam4UserLicenseNum != null )
		{
			int value = 0;
			try
			{
				String value_str=(new String(UtilMD5.Decrypt( sysParam4UserLicenseNum.getValue(), "dmddabcd1234admi")));
				value = Integer.parseInt( value_str );
			}
			catch(Exception ex)
			{
				//	这里不出报错信息，因为这涉及到用户许可数信息，不必给出明确提示信息
				value = 3;
			}
			userLicenseNum = value;
		}
		//	从服务器环境变量中读取用户许可数		end

		BOperatorUser bOperatorUser_rst = null;

		OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
		OperatorUser operatorUser_new = operatorUserBDConvertor.btod( _bOperatorUser4new, true );

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			
			//	检查数据库中用户是是否已达到购买许可数最大值	begin
			int existingUserNum = daoOperatorUser.getOperatorUsersStat( null, null );
			if( existingUserNum - 1 >= userLicenseNum )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_USER_LICENSENUM );
				Exception ex = new Exception( cause );
				throw ex;				
			}
			//	检查数据库中用户是是否已达到购买许可数最大值	end

			//密码加密 start
			operatorUser_new.setPassword(UtilMD5.getMD5Str(operatorUser_new.getPassword()));
			//密码加密end
			daoOperatorUser.save( operatorUser_new );

			if( operatorUser_new.getOperatorUserFunPermissions() != null && !(operatorUser_new.getOperatorUserFunPermissions().isEmpty()) )
			{
				DaoOperatorUserFunPermission daoOperatorUserFunPermission = new DaoOperatorUserFunPermission( session );
				Iterator<OperatorUserFunPermission> itr_operatorUserFunPermissions = operatorUser_new.getOperatorUserFunPermissions().iterator();
				while( itr_operatorUserFunPermissions.hasNext() )
				{
					daoOperatorUserFunPermission.save( itr_operatorUserFunPermissions.next() );
				}
			}

			if( operatorUser_new.getOperatorUserProOrgs() != null && !(operatorUser_new.getOperatorUserProOrgs().isEmpty()) )
			{
				DaoOperatorUserProOrg daoOperatorUserProOrg = new DaoOperatorUserProOrg( session );
				Iterator<OperatorUserProOrg> itr_operatorUserProOrgs = operatorUser_new.getOperatorUserProOrgs().iterator();
				while( itr_operatorUserProOrgs.hasNext() )
				{
					daoOperatorUserProOrg.save( itr_operatorUserProOrgs.next() );
				}
			}
			
			if( operatorUser_new.getOperatorUserBizDatas() != null && !(operatorUser_new.getOperatorUserBizDatas().isEmpty()) )
			{
				DaoOperatorUserBizData daoOperatorUserBizData = new DaoOperatorUserBizData( session );
				Iterator<OperatorUserBizData> itr_operatorUserBizDatas = operatorUser_new.getOperatorUserBizDatas().iterator();
				while( itr_operatorUserBizDatas.hasNext() )
				{
					daoOperatorUserBizData.save( itr_operatorUserBizDatas.next() );
				}
			}
			
			trsa.commit();

		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}

		bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_new, true );
		return bOperatorUser_rst;

	}

	public BOperatorUser updOperatorUser( BOperatorUser _bOperatorUser4upd ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOperatorUser4upd == null )
		{
			Exception ex = new Exception( "要更新的用户对象是空对象，不能更新" );
			throw ex;
		}

		String strKey4FunPermissionId = null;
		String strKey4ProIdOrgId = null;
		String strKey4BizDataId = null;
		//	数据库中OperatorUserFunPermissions OperatorUserProOrgs OperatorUserBizDatas 情况	begin
		HashMap<String, OperatorUserFunPermission> hmap_OperatorUserFunPermission_inDB = new HashMap<String, OperatorUserFunPermission>();
		HashMap<String, OperatorUserProOrg> hmap_OperatorUserProOrg_inDB = new HashMap<String, OperatorUserProOrg>();
		HashMap<String, OperatorUserBizData> hmap_OperatorUserBizData_inDB = new HashMap<String, OperatorUserBizData>();
		
		Session session_query = HibernateSessionFactory.getSession();
		Transaction trsa_query = null;
		try
		{
			trsa_query = session_query.beginTransaction();
			DaoOperatorUser daoOperatorUser_query = new DaoOperatorUser( session_query );
			OperatorUser operatorUser_InDB = daoOperatorUser_query.getOperatorUserById( _bOperatorUser4upd.getId() );
			if( operatorUser_InDB == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
			
			if( operatorUser_InDB.getOperatorUserFunPermissions() != null && !(operatorUser_InDB.getOperatorUserFunPermissions().isEmpty()) )
			{
				Iterator<OperatorUserFunPermission> itr_OperatorUserFunPermissions_inDB = operatorUser_InDB.getOperatorUserFunPermissions().iterator();
				while( itr_OperatorUserFunPermissions_inDB.hasNext() )
				{
					OperatorUserFunPermission operatorUserFunPermission = itr_OperatorUserFunPermissions_inDB.next();
					strKey4FunPermissionId = "" + operatorUserFunPermission.getFunPermission().getId();
					
					hmap_OperatorUserFunPermission_inDB.put( strKey4FunPermissionId, operatorUserFunPermission );
				}
			}		
			
			if( operatorUser_InDB.getOperatorUserProOrgs() != null && !(operatorUser_InDB.getOperatorUserProOrgs().isEmpty()) )
			{
				Iterator<OperatorUserProOrg> itr_OperatorUserProOrgs_inDB = operatorUser_InDB.getOperatorUserProOrgs().iterator();
				while( itr_OperatorUserProOrgs_inDB.hasNext() )
				{
					OperatorUserProOrg operatorUserProOrg = itr_OperatorUserProOrgs_inDB.next();
					strKey4ProIdOrgId = "" + operatorUserProOrg.getProduct().getId() + "_" + operatorUserProOrg.getOrganization().getId();
					
					hmap_OperatorUserProOrg_inDB.put( strKey4ProIdOrgId, operatorUserProOrg );
				}
			}
			
			if( operatorUser_InDB.getOperatorUserBizDatas() != null && !(operatorUser_InDB.getOperatorUserBizDatas().isEmpty()) )
			{
				Iterator<OperatorUserBizData> itr_OperatorUserBizDatas_inDB = operatorUser_InDB.getOperatorUserBizDatas().iterator();
				while( itr_OperatorUserBizDatas_inDB.hasNext() )
				{
					OperatorUserBizData operatorUserBizData = itr_OperatorUserBizDatas_inDB.next();
					strKey4BizDataId = "" + operatorUserBizData.getBizData().getId();
					
					hmap_OperatorUserBizData_inDB.put( strKey4BizDataId, operatorUserBizData );
				}
			}			
			
			trsa_query.commit();
			
			//密码加密 start
			if(!_bOperatorUser4upd.getPassword().equals(SysConst.PASSWORD_SHOW_DEFUAULT))//说明修改过密码了
				_bOperatorUser4upd.setPassword(UtilMD5.getMD5Str(_bOperatorUser4upd.getPassword()));
			else
			{
				_bOperatorUser4upd.setPassword( operatorUser_InDB.getPassword() );//否则还原密码
			}
			//密码加密end
		}
		catch( Exception ex )
		{
			if( trsa_query != null )
			{
				trsa_query.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session_query.close();
		}
		//	数据库中OperatorUserFunPermissions OperatorUserProOrgs OperatorUserBizDatas 情况	end
		
				
		OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
		OperatorUser operatorUser_upd = operatorUserBDConvertor.btod( _bOperatorUser4upd, true );
		
		//	参数中OperatorUserFunPermissions OperatorUserProOrgs OperatorUserBizDatas 情况	begin
		HashMap<String, OperatorUserFunPermission> hmap_OperatorUserFunPermission_param = new HashMap<String, OperatorUserFunPermission>();
		HashMap<String, OperatorUserProOrg> hmap_OperatorUserProOrg_param = new HashMap<String, OperatorUserProOrg>();
		HashMap<String, OperatorUserBizData> hmap_OperatorUserBizData_param = new HashMap<String, OperatorUserBizData>();
				
		if( operatorUser_upd.getOperatorUserFunPermissions() != null && !(operatorUser_upd.getOperatorUserFunPermissions().isEmpty()) )
		{
			Iterator<OperatorUserFunPermission> itr_OperatorUserFunPermissions_param = operatorUser_upd.getOperatorUserFunPermissions().iterator();
			while( itr_OperatorUserFunPermissions_param.hasNext() )
			{
				OperatorUserFunPermission operatorUserFunPermission = itr_OperatorUserFunPermissions_param.next();
				strKey4FunPermissionId = "" + operatorUserFunPermission.getFunPermission().getId();
				
				hmap_OperatorUserFunPermission_param.put( strKey4FunPermissionId, operatorUserFunPermission );
			}			
		}		
		
		if( operatorUser_upd.getOperatorUserProOrgs() != null && !(operatorUser_upd.getOperatorUserProOrgs().isEmpty()) )
		{
			Iterator<OperatorUserProOrg> itr_OperatorUserProOrgs_param = operatorUser_upd.getOperatorUserProOrgs().iterator();
			while( itr_OperatorUserProOrgs_param.hasNext() )
			{
				OperatorUserProOrg operatorUserProOrg = itr_OperatorUserProOrgs_param.next();
				strKey4ProIdOrgId = "" + operatorUserProOrg.getProduct().getId() + "_" + operatorUserProOrg.getOrganization().getId();
				
				hmap_OperatorUserProOrg_param.put( strKey4ProIdOrgId, operatorUserProOrg );
			}			
		}	
		
		if( operatorUser_upd.getOperatorUserBizDatas() != null && !(operatorUser_upd.getOperatorUserBizDatas().isEmpty()) )
		{
			Iterator<OperatorUserBizData> itr_OperatorUserBizDatas_param = operatorUser_upd.getOperatorUserBizDatas().iterator();
			while( itr_OperatorUserBizDatas_param.hasNext() )
			{
				OperatorUserBizData operatorUserBizData = itr_OperatorUserBizDatas_param.next();
				strKey4BizDataId = "" + operatorUserBizData.getBizData().getId();
				
				hmap_OperatorUserBizData_param.put( strKey4BizDataId, operatorUserBizData );
			}			
		}				
		
		//	参数中OperatorUserFunPermissions OperatorUserProOrgs OperatorUserBizDatas 情况	end
		
		//	比较param和inDB获得 OperatorUserFunPermission 增删改情况 	begin
		List<OperatorUserFunPermission> toDelOperatorUserFunPermissionList = new ArrayList<OperatorUserFunPermission>();
		List<OperatorUserFunPermission> toAddOperatorUserFunPermissionList = new ArrayList<OperatorUserFunPermission>();
		
		//	param - inDB = add	begin
		if( operatorUser_upd.getOperatorUserFunPermissions() != null && !(operatorUser_upd.getOperatorUserFunPermissions().isEmpty()) )
		{
			Iterator<OperatorUserFunPermission> itr_OperatorUserFunPermissions_param = operatorUser_upd.getOperatorUserFunPermissions().iterator();
			while( itr_OperatorUserFunPermissions_param.hasNext() )
			{
				OperatorUserFunPermission operatorUserFunPermission = itr_OperatorUserFunPermissions_param.next();
				strKey4FunPermissionId = "" + operatorUserFunPermission.getFunPermission().getId();
				
				if( hmap_OperatorUserFunPermission_inDB.get( strKey4FunPermissionId ) == null )
				{	
					toAddOperatorUserFunPermissionList.add( operatorUserFunPermission );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_OperatorUserFunPermission_inDB.values() != null && !(hmap_OperatorUserFunPermission_inDB.values().isEmpty()) )
		{
			Iterator<OperatorUserFunPermission> itr_OperatorUserFunPermissions_inDB = hmap_OperatorUserFunPermission_inDB.values().iterator();
			while( itr_OperatorUserFunPermissions_inDB.hasNext() )
			{
				OperatorUserFunPermission operatorUserFunPermission = itr_OperatorUserFunPermissions_inDB.next();
				strKey4FunPermissionId = "" + operatorUserFunPermission.getFunPermission().getId();
				
				if( hmap_OperatorUserFunPermission_param.get( strKey4FunPermissionId ) == null )
				{
					toDelOperatorUserFunPermissionList.add( operatorUserFunPermission );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 OperatorUserFunPermission 增删改情况 	end
		
		//	比较param和inDB获得 OperatorUserProOrg 增删改情况 	begin
		List<OperatorUserProOrg> toDelOperatorUserProOrgList = new ArrayList<OperatorUserProOrg>();
		List<OperatorUserProOrg> toAddOperatorUserProOrgList = new ArrayList<OperatorUserProOrg>();
		
		//	param - inDB = add	begin
		if( operatorUser_upd.getOperatorUserProOrgs() != null && !(operatorUser_upd.getOperatorUserProOrgs().isEmpty()) )
		{
			Iterator<OperatorUserProOrg> itr_OperatorUserProOrgs_param = operatorUser_upd.getOperatorUserProOrgs().iterator();
			while( itr_OperatorUserProOrgs_param.hasNext() )
			{
				OperatorUserProOrg operatorUserProOrg = itr_OperatorUserProOrgs_param.next();
				strKey4ProIdOrgId = "" + operatorUserProOrg.getProduct().getId() + "_" + operatorUserProOrg.getOrganization().getId();
				
				if( hmap_OperatorUserProOrg_inDB.get( strKey4ProIdOrgId ) == null )
				{	
					toAddOperatorUserProOrgList.add( operatorUserProOrg );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_OperatorUserProOrg_inDB.values() != null && !(hmap_OperatorUserProOrg_inDB.values().isEmpty()) )
		{
			Iterator<OperatorUserProOrg> itr_OperatorUserProOrgs_inDB = hmap_OperatorUserProOrg_inDB.values().iterator();
			while( itr_OperatorUserProOrgs_inDB.hasNext() )
			{
				OperatorUserProOrg operatorUserProOrg = itr_OperatorUserProOrgs_inDB.next();
				strKey4ProIdOrgId = "" + operatorUserProOrg.getProduct().getId() + "_" + operatorUserProOrg.getOrganization().getId();
				
				if( hmap_OperatorUserProOrg_param.get( strKey4ProIdOrgId ) == null )
				{
					toDelOperatorUserProOrgList.add( operatorUserProOrg );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 OperatorUserProOrg 增删改情况 	end		
		
		//	比较param和inDB获得 OperatorUserBizData 增删改情况 	begin
		List<OperatorUserBizData> toDelOperatorUserBizDataList = new ArrayList<OperatorUserBizData>();
		List<OperatorUserBizData> toAddOperatorUserBizDataList = new ArrayList<OperatorUserBizData>();
		List<OperatorUserBizData> toUpdOperatorUserBizDataList = new ArrayList<OperatorUserBizData>();
		
		//	param - inDB = add	begin
		if( operatorUser_upd.getOperatorUserBizDatas() != null && !(operatorUser_upd.getOperatorUserBizDatas().isEmpty()) )
		{
			Iterator<OperatorUserBizData> itr_OperatorUserBizDatas_param = operatorUser_upd.getOperatorUserBizDatas().iterator();
			while( itr_OperatorUserBizDatas_param.hasNext() )
			{
				OperatorUserBizData operatorUserBizData = itr_OperatorUserBizDatas_param.next();
				strKey4BizDataId = "" + operatorUserBizData.getBizData().getId();
				
				if( hmap_OperatorUserBizData_inDB.get( strKey4BizDataId ) == null )
				{	
					toAddOperatorUserBizDataList.add( operatorUserBizData );
				}
			}
		}				
		//	param - inDB = add	end
		
		//	inDB - param = del	begin
		if( hmap_OperatorUserBizData_inDB.values() != null && !(hmap_OperatorUserBizData_inDB.values().isEmpty()) )
		{
			Iterator<OperatorUserBizData> itr_OperatorUserBizDatas_inDB = hmap_OperatorUserBizData_inDB.values().iterator();
			while( itr_OperatorUserBizDatas_inDB.hasNext() )
			{
				OperatorUserBizData operatorUserBizData = itr_OperatorUserBizDatas_inDB.next();
				strKey4BizDataId = "" + operatorUserBizData.getBizData().getId();
				
				if( hmap_OperatorUserBizData_param.get( strKey4BizDataId ) == null )
				{
					toDelOperatorUserBizDataList.add( operatorUserBizData );
				}
				else
				{
					OperatorUserBizData operatorUserBizData_param = hmap_OperatorUserBizData_param.get( strKey4BizDataId );
					operatorUserBizData.setIsManaging( operatorUserBizData_param.getIsManaging() );
					toUpdOperatorUserBizDataList.add( operatorUserBizData );
				}
			}
		}		
		//	inDB - param = del	end
		
		//	比较param和inDB获得 OperatorUserBizData 增删改情况 	end
		
		
		//	持久化到数据库	begin
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;

		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
						
			daoOperatorUser.update( operatorUser_upd );

			DaoOperatorUserFunPermission daoOperatorUserFunPermission = new DaoOperatorUserFunPermission( session );
			for( int i = 0; i < toDelOperatorUserFunPermissionList.size(); i++ )
			{
				daoOperatorUserFunPermission.delete( toDelOperatorUserFunPermissionList.get( i ) );
			}
			for( int i = 0; i < toAddOperatorUserFunPermissionList.size(); i++ )
			{
				daoOperatorUserFunPermission.save( toAddOperatorUserFunPermissionList.get( i ) );
			}

			DaoOperatorUserProOrg daoOperatorUserProOrg = new DaoOperatorUserProOrg( session );
			for( int i = 0; i < toDelOperatorUserProOrgList.size(); i++ )
			{
				daoOperatorUserProOrg.delete( toDelOperatorUserProOrgList.get( i ) );
			}
			for( int i = 0; i < toAddOperatorUserProOrgList.size(); i++ )
			{
				daoOperatorUserProOrg.save( toAddOperatorUserProOrgList.get( i ) );
			}
			
			DaoOperatorUserBizData daoOperatorUserBizData = new DaoOperatorUserBizData( session );
			for( int i = 0; i < toDelOperatorUserBizDataList.size(); i++ )
			{
				daoOperatorUserBizData.delete( toDelOperatorUserBizDataList.get( i ) );
			}
			for( int i = 0; i < toUpdOperatorUserBizDataList.size(); i++ )
			{
				daoOperatorUserBizData.update( toUpdOperatorUserBizDataList.get( i ) );
			}
			for( int i = 0; i < toAddOperatorUserBizDataList.size(); i++ )
			{
				daoOperatorUserBizData.save( toAddOperatorUserBizDataList.get( i ) );
			}
			
			trsa.commit();

		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}
		//	持久化到数据库	end
		
		try{
			//删除掉用户不需要常用条件中的业务范围
			UiPopbScopeDM dm = new UiPopbScopeDM();
			if(toDelOperatorUserProOrgList.size()>0)
			{
				dm.deleteProOrgByOperatorUserId(toDelOperatorUserProOrgList, _bOperatorUser4upd.getId());
			}
			if(toDelOperatorUserBizDataList.size()>0)
			{
				dm.deleteBizDataByOperatorUserId(toDelOperatorUserBizDataList, _bOperatorUser4upd.getId());
			}
		}catch (Exception e) {
			logger.error("删除掉用户不需要常用条件中的业务范围失败!");
			e.printStackTrace();
		}
		
		BOperatorUser bOperatorUser_rst = this.getOperatorUserById( _bOperatorUser4upd.getId(), true );
		bOperatorUser_rst.setPassword( SysConst.PASSWORD_SHOW_DEFUAULT );
		return bOperatorUser_rst;
	}

	public void updOperatorUsers( List<BOperatorUser> _list ) throws Exception
	{
		for(BOperatorUser bOperatorUser:_list)
		{
			updOperatorUser( bOperatorUser );
		}
	}
	
	public boolean delOperatorUser( BOperatorUser _bOperatorUser4del ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOperatorUser4del == null )
		{
			Exception ex = new Exception("要删除的用户对象是空对象，不能删除");
			throw ex;
		}
		
		OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
		OperatorUser operatorUser_del = operatorUserBDConvertor.btod( _bOperatorUser4del, false ); // 数据库被设置了级联删出

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			
			daoOperatorUser.delete( operatorUser_del );
			
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}
		
		return true;
	}

	public BOperatorUser changePassword( BOperatorUser _bOperator4changePwd, String _passwordOld, String _passwordNew ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		if( _bOperator4changePwd == null )
		{
			Exception ex = new Exception( "The object to update is a null object" );
			throw ex;
		}
		OperatorUser operatorUser_upd = null;

		Session querySession = HibernateSessionFactory.getSession();
		Transaction queryTrsa = null;
		try
		{
			queryTrsa = querySession.beginTransaction();
			DaoOperatorUser queryDaoOperatorUser = new DaoOperatorUser( querySession );
			operatorUser_upd = queryDaoOperatorUser.getOperatorUserById( _bOperator4changePwd.getId() );
			queryTrsa.commit();
			if( operatorUser_upd == null )
			{
				Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OBJECT_NOT_IN_DATABASE );
				Exception ex = new Exception( cause );
				throw ex;
			}
		}
		catch( Exception ex )
		{
			if( queryTrsa != null )
			{
				queryTrsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			querySession.close();
		}

		// to do, 密码加密与解密
		if( !(operatorUser_upd.getPassword().equals( UtilMD5.getMD5Str(_passwordOld ))) )
		{
			Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_OLD_PASSWORD_NOT_CORRECT );
			Exception ex = new Exception( cause );
			throw ex;
		}

		operatorUser_upd.setVersion( _bOperator4changePwd.getVersion() ); // 防止更新脏数据
		operatorUser_upd.setPassword( UtilMD5.getMD5Str(_passwordNew));

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );

			// operatorUser
			daoOperatorUser.update( operatorUser_upd );

			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}

		BOperatorUser bOperatorUser_rst = this.getOperatorUserById( operatorUser_upd.getId(), true );
		return bOperatorUser_rst;

	}

	public int getOperatorUsersStat( String _sqlRestriction, Long _operatorUserId ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		int rst = 0;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			rst = daoOperatorUser.getOperatorUsersStat( _sqlRestriction, _operatorUserId );			
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}

		return rst;
	}
	
	
	/**
	 * _pageIndex>0 时分页查询
	 * @param _sqlRestriction
	 * @param _operatorUserId
	 * @param _pageIndex
	 * @param _pageSize
	 * @return
	 * @throws Exception
	 */
	public List<BOperatorUser> getOperatorUsers( String _sqlRestriction, Long _operatorUserId, boolean _blWithPermissions, int _pageIndex, int _pageSize ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
				
		List<BOperatorUser> rstList = new ArrayList<BOperatorUser>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			List<OperatorUser> listOperatorUser_inDB = daoOperatorUser.getOperatorUsers2( _sqlRestriction, _operatorUserId, _pageIndex, _pageSize );
			
			if( listOperatorUser_inDB != null && !(listOperatorUser_inDB.isEmpty()) )
			{
				OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
				for( int i=0; i<listOperatorUser_inDB.size(); i++ )
				{
					rstList.add( operatorUserBDConvertor.dtob( listOperatorUser_inDB.get( i ), _blWithPermissions ) );
				}
			}
			
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}
		//将密码改成1111111
		for(BOperatorUser op:rstList)
		{
			op.setPassword(SysConst.PASSWORD_SHOW_DEFUAULT);
		}
		return rstList;
	}

	public BOperatorUser getOperatorUserById( Long _id, boolean _blWithPermissions ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		//modify by zhangzy重写查询实现
		return  queryService.getOperatorUserById(_id, _blWithPermissions);
		
//		BOperatorUser bOperatorUser_rst = null;
//
//		Session session = HibernateSessionFactory.getSession();
//		Transaction trsa = null;
//		try
//		{
//			trsa = session.beginTransaction();
//			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
//			OperatorUser operatorUser_inDB = daoOperatorUser.getOperatorUserById( _id );
//			
//
//			if( operatorUser_inDB != null )
//			{
//				OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
//				bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_inDB, _blWithPermissions );
//			}
//			
//			trsa.commit();
//		}
//		catch( Exception ex )
//		{
//			if( trsa != null )
//			{
//				trsa.rollback();
//			}
//			ex.printStackTrace();
//			throw ex;
//		}
//		finally
//		{
//			session.close();
//		}
//		bOperatorUser_rst.setPassword(SysConst.PASSWORD_SHOW_DEFUAULT);
//		return bOperatorUser_rst;

	}
	// OperatorUser end
	
	public BOperatorUser getOperatorUserByUserName( String _userName) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		
		BOperatorUser bOperatorUser_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			OperatorUser operatorUser_inDB = daoOperatorUser.getOperatorUserByUserName( _userName );
			

			if( operatorUser_inDB != null )
			{
				OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
				bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_inDB, true );
				trsa.commit();
				
			}		
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}
		if(bOperatorUser_rst!=null)
			bOperatorUser_rst.setPassword(SysConst.PASSWORD_SHOW_DEFUAULT);
		return bOperatorUser_rst;

	}
	
	public BOperatorUser getOperatorUser( Long _id) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		
		BOperatorUser bOperatorUser_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			OperatorUser operatorUser_inDB = daoOperatorUser.getOperatorUser( _id );

			if( operatorUser_inDB != null )
			{
				OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
				bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_inDB, false);
			}
			
			//将业务范围挂上
			DaoOperatorUserProOrg dao_proorg = new DaoOperatorUserProOrg(session);
			bOperatorUser_rst = dao_proorg.getOperatorUserProOrgs(bOperatorUser_rst);
			
			//funpermiss
			DaoOperatorUserFunPermission dao_fun = new DaoOperatorUserFunPermission(session);
			bOperatorUser_rst = dao_fun.getOperatorUserFunPermissions(bOperatorUser_rst);
			
			//bizdatas
			DaoOperatorUserBizData dao_biz = new DaoOperatorUserBizData(session);		
			bOperatorUser_rst = dao_biz.getOperatorUserBizDatas(bOperatorUser_rst);
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}
		bOperatorUser_rst.setPassword(SysConst.PASSWORD_SHOW_DEFUAULT);
		return bOperatorUser_rst;
	}

	// FunPermission begin
	public List<BFunPermission> getAllFunPermissions() throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		
		List<BFunPermission> rstList = new ArrayList<BFunPermission>();

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoFunPermission daoFunPermission = new DaoFunPermission( session );
			List<FunPermission> listFunPermission_inDB = daoFunPermission.getAllFunPermissions();
			
			if( listFunPermission_inDB != null )
			{
				FunPermissionBDConvertor funPermissionBDConvertor = new FunPermissionBDConvertor();
				for( int i = 0; i < listFunPermission_inDB.size(); i++ )
				{
					rstList.add( (BFunPermission) funPermissionBDConvertor.dtob( listFunPermission_inDB.get( i ) ) );
				}
			}
			
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}

		return rstList;
	}

	// FunPermission end

	// login and logout begin
	public BOperatorUser doLogin( String _loginName, String _password , boolean _isShareInfo) throws Exception
	{
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ISOPEN).equalsIgnoreCase("true"))//要进行AD验证
		{
			//第一步，还原账号，去掉前缀和后缀
			String head = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_BEFOREHEAD);
			String domain = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_DOMAIN);   //邮箱的后缀名
			if(!head.equals(""))
			{
				if(_loginName.indexOf(head)>-1)
					_loginName = _loginName.substring(_loginName.indexOf(head)+head.length());
			}
			if(!domain.equals(""))
			{
				if(_loginName.indexOf(domain)>-1)
					_loginName =_loginName.substring(0,_loginName.indexOf(domain));
			}
			if(!_isShareInfo)
			{
				if(checkAD(_loginName, _password))//通过了AD,这里的password是没加密过的
				{
					BOperatorUser opuser= checkUser4AD(_loginName);
					if(opuser==null)
					{
						return null;
					}
					opuser.setPassword(UtilMD5.Encrypt("dmddabcd1234adpw", _password.getBytes()));
					return opuser;
				}
				else//没通过AD，但可能是sa登录
				{
					BOperatorUser opuser= checkUserIsSA(_loginName,_password);
					if(opuser==null)
					{
						return null;
					}
					if(opuser.getSuperiorOperatorUser()==null)
					{
						opuser.setPassword(UtilMD5.Encrypt("dmddabcd1234adpw", _password.getBytes()));
						return opuser;
					}
					else//做退出操作
					{
						doLogout(_loginName);
					}
					return null;
				}
			}
			else
			{
				byte[] temp=UtilMD5.Decrypt(_password, "dmddabcd1234adpw");
				String password = "";
				if(temp==null || temp.length<1)
					password="";
				else password = new String(temp);
				if(!checkAD(_loginName,password))//没通过AD，但可能是sa登录
				{
					BOperatorUser opuser= checkUserIsSA(_loginName,_password);
					if(opuser==null)
					{
						return null;
					}
					if(opuser.getSuperiorOperatorUser()==null)
					{
						opuser.setPassword(UtilMD5.Encrypt("dmddabcd1234adpw", _password.getBytes()));
						return opuser;
					}
					else//做退出操作
					{
						doLogout(_loginName);
					}
					return null;
				}
				else
				{
					BOperatorUser opuser= checkUser4AD(_loginName);
					if(opuser==null)
					{
						return null;
					}
					opuser.setPassword(_password);//保持一致
					return opuser;
				}
			}
		}
		//否则是正常不走AD,直接输入用户名和密码途径
		
		BOperatorUser bOperatorUser_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			OperatorUser operatorUser_inDB = daoOperatorUser.getOperatorUser( _loginName, UtilMD5.getMD5Str(_password) );
			
			DaoSystem daoSystem = new DaoSystem( session );
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			
			if( operatorUser_inDB != null )
			{
				if( !ServerEnvironment.getInstance().getConfigFileParam(BizConst.MPOP).equalsIgnoreCase("true")){
					//不允许多点登录
					//	检查是否已在不同位置同时登录		begin
					if( operatorUser_inDB.getIsOnline() == BizConst.GLOBAL_YESNO_YES &&operatorUser_inDB.getLoginTime()!=null 
							&& operatorUser_inDB.getSuperiorOperatorUser()!=null)
					{
						//	已经在线
						//	放宽限制，对于长时间登入但没登出的，认为其已经异常登出。以24小时为限
						if( currentTime.getTime() - operatorUser_inDB.getLoginTime().getTime() <= 2*60*60*1000 )
						{
							//	一天之内在其他位置登陆并且没有退出
							Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_USER_CONLOGON );
							Exception ex = new Exception( cause );
							throw ex;							
						}
					}
					//	检查是否已在不同位置同时登录		end
				}
				
				operatorUser_inDB.setLoginTime( currentTime );
				operatorUser_inDB.setLoginTimes( operatorUser_inDB.getLoginTimes() + 1 );
				operatorUser_inDB.setIsOnline( BizConst.GLOBAL_YESNO_YES );

				daoOperatorUser.update( operatorUser_inDB );
			}
			else
			{
				//	登陆失败
				return null;
			}
			trsa.commit();
			
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_inDB, true );
			
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}

		return bOperatorUser_rst;
	}	

	public BOperatorUser checkUser4AD(String _loginName) throws Exception
	{
		BOperatorUser bOperatorUser_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			OperatorUser operatorUser_inDB = daoOperatorUser.getOperatorUser( _loginName);
			DaoSystem daoSystem = new DaoSystem( session );
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			
			if( operatorUser_inDB != null )
			{
				//	检查是否已在不同位置同时登录		begin
				if( operatorUser_inDB.getIsOnline() == BizConst.GLOBAL_YESNO_YES &&operatorUser_inDB.getLoginTime()!=null)
				{
					//	已经在线
					//	放宽限制，对于长时间登入但没登出的，认为其已经异常登出。以24小时为限
					if( currentTime.getTime() - operatorUser_inDB.getLoginTime().getTime() <= 2*60*60*1000 )
					{
						//	一天之内在其他位置登陆并且没有退出
						Throwable cause = new Throwable( ExceptionConst.EXCEPTION_CAUSECODE_USER_CONLOGON );
						Exception ex = new Exception( cause );
						throw ex;							
					}
				}
				//	检查是否已在不同位置同时登录		end
				
				operatorUser_inDB.setLoginTime( currentTime );
				operatorUser_inDB.setLoginTimes( operatorUser_inDB.getLoginTimes() + 1 );
				operatorUser_inDB.setIsOnline( BizConst.GLOBAL_YESNO_YES );

				daoOperatorUser.update( operatorUser_inDB );
			}
			else
			{
				//	登陆失败
				return null;
			}
			trsa.commit();
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_inDB, true );
			
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}

		return bOperatorUser_rst;
	}
	
	public BOperatorUser checkUserIsSA(String _loginName, String _password) throws Exception
	{
		BOperatorUser bOperatorUser_rst = null;

		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoOperatorUser daoOperatorUser = new DaoOperatorUser( session );
			OperatorUser operatorUser_inDB = daoOperatorUser.getOperatorUser( _loginName, UtilMD5.getMD5Str(_password) );
			DaoSystem daoSystem = new DaoSystem( session );
			Date currentTime = daoSystem.getSysTimeAsTimeStamp();
			
			if( operatorUser_inDB != null )
			{
				//检查是否是SA登录 start.
				if(operatorUser_inDB.getSuperiorOperatorUser()!=null)
				{
					return null;
				}
				//检查是否是SA登录 end.
				
				operatorUser_inDB.setLoginTime( currentTime );
				operatorUser_inDB.setLoginTimes( operatorUser_inDB.getLoginTimes() + 1 );
				operatorUser_inDB.setIsOnline( BizConst.GLOBAL_YESNO_YES );

				daoOperatorUser.update( operatorUser_inDB );
			}
			else
			{
				//	登陆失败
				return null;
			}
			trsa.commit();
			OperatorUserBDConvertor operatorUserBDConvertor = new OperatorUserBDConvertor();
			bOperatorUser_rst = operatorUserBDConvertor.dtob( operatorUser_inDB, true );
			
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			if( session != null && session.isOpen() )
			{
				session.close();
			}
		}

		return bOperatorUser_rst;
	}
	
	public void doLogout( String _loginName ) throws Exception
	{
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			Criteria crit = session.createCriteria( OperatorUser.class );
			crit.add( Restrictions.eq( "loginName", _loginName ) );

			OperatorUser dbdobjOperatorUser = (OperatorUser) crit.uniqueResult();

			if( dbdobjOperatorUser != null )
			{
				DaoSystem daoSystem = new DaoSystem( session );
				dbdobjOperatorUser.setLogoutTime( daoSystem.getSysTimeAsTimeStamp() );
				dbdobjOperatorUser.setIsOnline( BizConst.GLOBAL_YESNO_NO );
			}

			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}
	}

	// login and logout end

	/**
	 * 检查密码的安全级别，如果安全级别低则返回false
	 */
	public boolean checkpwd4gread(String pwd)
	{
		String Number="[0-9]*";
		String str ="[a-z]*";
		String strABC ="[A-Z]*";		
		
		Pattern patternNumber = Pattern.compile(Number); 
	    if(patternNumber.matcher(pwd).matches())   
	    	return false;
	    Pattern patternStr = Pattern.compile(str); 
	    if(patternStr.matcher(pwd).matches())   
	    	return false;
	    
	    Pattern patternstrABC = Pattern.compile(strABC); 
	    if(patternstrABC.matcher(pwd).matches())   
	    	return false;
		
		return true;
	}
	/**
	 * 验证AD
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean checkAD(String userName,String password) throws Exception
	{
		if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ISOPEN).equalsIgnoreCase("false"))
			return false;

		String host = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ADADDRESS);  //AD服务器
		String port = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_ADPORT);            //端口
		String domain = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_DOMAIN);   //邮箱的后缀名
		String head = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_AD_BEFOREHEAD);
		String url = new String("ldap://" + host + ":" + port);
		String user = "";
		if(!domain.equals(""))
		{
			user = userName.indexOf(domain) > 0 ? userName : userName + domain;
		}
		else
		{
			user=userName;
		}
		if(!head.equals(""))
		{
			user = user.indexOf(head) > -1 ?user: head + user;
		}
		Hashtable<String, String> env = new Hashtable<String, String>();
		DirContext ctx;
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, user); 
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, url);
		try
		{
			ctx = new InitialDirContext(env);
			ctx.close();
			System.out.println(userName+":AD通过");
			return true;
		} 
		catch (NamingException err) 
		{
			err.printStackTrace();
			System.out.println("验证失败！");
			return false;
		}	
	}
	
	public static List<ABImOperatorUserProOrg> saveOperatorUserProOrgs4ImportUI(List<ABImOperatorUserProOrg> _lisoperatoruserproorg) throws Exception
	{
		List<ABImOperatorUserProOrg> res = new ArrayList<ABImOperatorUserProOrg>();
		OperatorUserProOrgImportMgmt mgmt = new OperatorUserProOrgImportMgmt(_lisoperatoruserproorg);
		mgmt.excute();
		res = mgmt.getResult();
		return res;
	}
		
	public boolean refreshUserStats(BOperatorUser bOperatorUser) throws Exception
	{
		if(bOperatorUser==null)
			return false;
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			Criteria crit = session.createCriteria( OperatorUser.class );
			crit.add( Restrictions.eq( "id", bOperatorUser.getId() ) );

			OperatorUser dbdobjOperatorUser = (OperatorUser) crit.uniqueResult();

			if( dbdobjOperatorUser != null )
			{
				dbdobjOperatorUser.setIsOnline(BizConst.GLOBAL_YESNO_NO)	;
			}

			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			session.close();
		}
		
		
		
		return true;
	}
	
	public boolean refreshOldData() throws Exception
	{
		logger.info("开始刷新基础数据...");
		logger.info("开始刷新主数据数据pathCode...");
		MainDataDM dm = new MainDataDM();
		if(!dm.refreshMainData())
			return false;
		
		logger.info("开始刷新常用条件中业务范围和业务数据的有效范围");
		
		UiPopbScopeDM dm_ = new UiPopbScopeDM();
		if(!dm_.refreshUiPopbScope())
			return false;
		logger.info("刷新基础数据完成");
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		SecurityService s = new SecurityService();
		try {
			s.checkAD("wuyunlong.yc", "11111111");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}	
}


/**************************************************************************
 * 
 * $RCSfile: SecurityService.java,v $ $Revision: 1.4 $ $Date: 2010/07/15 15:01:36 $
 * 
 * $Log: SecurityService.java,v $
 * Revision 1.4  2010/07/15 15:01:36  liuzhen
 * 2010.07.15 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.3  2010/07/15 14:31:24  liuzhen
 * 2010.07.15 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.2  2010/07/09 09:35:03  liuzhen
 * 2010.07.09 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 1.1  2010/07/04 07:26:57  liuzhen
 * 2010.07.04 by liuzhen
 * Committed on the Free edition of March Hare Software CVSNT Server.
 * Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 *
 * Revision 
 * 
 ***************************************************************************/
