package dmdd.dmddjava.service.securityservice;

import java.util.HashSet;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.SysConst;
import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BFunPermission;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUser;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserBizData;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserFunPermission;
import dmdd.dmddjava.dataaccess.bizobject.BOperatorUserProOrg;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;

/**
 * 用户、权限模板查询实现重写
 * @author jerry
 * @date Jul 10, 2013
 */
public class SecurityQueryService {
	
	private Logger logger = CoolLogger.getLogger(this.getClass());
	private CommDMO dmo = new CommDMO();
	
	
	public BOperatorUser getOperatorUserById( Long _id, boolean _blWithPermissions ) throws Exception
	{
		//	检查服务器状态是否可以提供服务	begin
		ServerEnvironment.getInstance().checkSystemStatus();
		//	检查服务器状态是否可以提供服务	end
		
		logger.debug("根据用户ID["+_id+"]查询用户相关信息开始....");
		long start = System.currentTimeMillis();
		BOperatorUser bOperatorUser = new BOperatorUser();

		try{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ID,VERSION,LOGINNAME,PASSWORD,USERNAME,CREATORNAME,CREATEDTIME,USEREXPIRYTIME");
			sb.append(",PWDEXPIRYTIME,ISVALID,POSITION,OFFICEADDRESS,HOMEADDRESS,TELNO,MOBILENO,EMAIL");
			sb.append(",ISONLINE,LOGINTIME,LOGOUTTIME,LOGINTIMES,COMMENTS,SUPERIORUSERID ");
			sb.append(",(SELECT USERNAME FROM OPERATORUSER P WHERE P.ID=U.SUPERIORUSERID) SUPERIORUSERNAME");
			sb.append(" FROM OPERATORUSER U WHERE ID=?");
			
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sb.toString(), _id);
			HashVO uservo = vos[0];
			
			bOperatorUser.setVersion( uservo.getLongValue("VERSION"));
			bOperatorUser.setId( uservo.getLongValue("ID") );
			bOperatorUser.setLoginName( uservo.getStringValue("LoginName") );
			bOperatorUser.setPassword( uservo.getStringValue("Password") );
			bOperatorUser.setUserName( uservo.getStringValue("UserName") );
			bOperatorUser.setCreatorName( uservo.getStringValue("CreatorName") );
			bOperatorUser.setCreatedTime( uservo.getDateValue("CreatedTime") );
			bOperatorUser.setUserExpiryTime( uservo.getDateValue("UserExpiryTime") );
			bOperatorUser.setPwdExpiryTime( uservo.getDateValue("PwdExpiryTime") );
			bOperatorUser.setIsValid( uservo.getIntegerValue("IsValid") );
			bOperatorUser.setPosition( uservo.getStringValue("Position") );
			bOperatorUser.setOfficeAddress( uservo.getStringValue("OfficeAddress") );
			bOperatorUser.setHomeAddress( uservo.getStringValue("HomeAddress") );
			bOperatorUser.setTelNo( uservo.getStringValue("TelNo") );
			bOperatorUser.setMobileNo( uservo.getStringValue("MobileNo") );
			bOperatorUser.setEmail( uservo.getStringValue("Email") );
			bOperatorUser.setIsOnline( uservo.getIntegerValue("IsOnline") );
			bOperatorUser.setLoginTime( uservo.getDateValue("LoginTime") );
			bOperatorUser.setLogoutTime( uservo.getDateValue("LogoutTime") );
			bOperatorUser.setLoginTimes( uservo.getLongValue("LoginTimes") );
			bOperatorUser.setComments( uservo.getStringValue("Comments") );
			
			BOperatorUser parentUser = new BOperatorUser();
			parentUser.setId(uservo.getLongValue("SUPERIORUSERID"));
			parentUser.setUserName(uservo.getStringValue("SUPERIORUSERNAME"));
			
			bOperatorUser.setSuperiorOperatorUser(parentUser);
			
			//设置功能权限
			if(_blWithPermissions == true){
				bOperatorUser.setOperatorUserFunPermissions(getUserFunPermissionByUserId(_id));
			}
			
			//业务数据
			bOperatorUser.setOperatorUserBizDatas(getUserBizDataByUserId(_id));
			
			//业务范围权限
			bOperatorUser.setOperatorUserProOrgs(getUserProOrgByUserId(_id));
			
			
			logger.debug("根据用户ID查询用户及权限、业务数据结束，耗时" +(System.currentTimeMillis()-start) + " ms");
		}catch( Exception ex ){
			logger.error("获取用户及其权限、业务数据失败！");
			ex.printStackTrace();
			throw ex;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		bOperatorUser.setPassword(SysConst.PASSWORD_SHOW_DEFUAULT);
		
		return bOperatorUser;

	}
	
	//获取用户业务范围数据
	public 	HashSet<BOperatorUserProOrg> getUserProOrgByUserId(Long _id) throws Exception{
		HashSet<BOperatorUserProOrg> uProOrgSet = new HashSet<BOperatorUserProOrg>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.ID T_ID,T.VERSION T_VERSION,T.OPERATORUSERID,T.ORGANIZATIONID,T.PRODUCTID,P.CODE PROCODE,P.NAME PRONAME,O.CODE ORGCODE,O.NAME ORGNAME");
		sb.append(" FROM OPERATORUSER_PROORG T,PRODUCT P,ORGANIZATION O WHERE P.ID=T.PRODUCTID AND O.ID=T.ORGANIZATIONID AND T.OPERATORUSERID=? ");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sb.toString(),_id);
		
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			BOperatorUserProOrg proorg = new BOperatorUserProOrg();
			proorg.setId(vo.getLongValue("T_ID"));
			proorg.setVersion(vo.getLongValue("T_VERSION"));
			
			BProduct pro = new BProduct();
			pro.setId(vo.getLognValue("PRODUCTID"));
			pro.setCode(vo.getStringValue("PROCODE"));
			pro.setName(vo.getStringValue("PRONAME"));
			
			BOrganization org = new BOrganization();
			org.setId(vo.getLongValue("ORGANIZATIONID"));
			org.setCode(vo.getStringValue("ORGCODE"));
			org.setName(vo.getStringValue("ORGNAME"));
			
			proorg.setProduct(pro);
			proorg.setOrganization(org);
			
			uProOrgSet.add(proorg);
		}
		
		return uProOrgSet;
	}
	
	//获取用户功能权限
	public 	HashSet<BOperatorUserFunPermission> getUserFunPermissionByUserId(Long _id) throws Exception{
		HashSet<BOperatorUserFunPermission> ouFunPermiSet = new HashSet<BOperatorUserFunPermission>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.ID T_ID,T.VERSION T_VERSION,T.OPERATORUSERID,T.FUNPERMISSIONID");
		sb.append(",P.ID,P.VERSION,P.CODE,P.NAME,P.DESCRIPTION,P.COMMENTS");
		sb.append(" FROM OPERATORUSER_FUNPERMISSION T,FUNPERMISSION P WHERE P.ID=T.FUNPERMISSIONID AND T.OPERATORUSERID=?");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sb.toString(),_id);
		
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			BOperatorUserFunPermission funPermiData = new BOperatorUserFunPermission();
			funPermiData.setId(vo.getLongValue("T_ID"));
			funPermiData.setVersion(vo.getLongValue("T_VERSION"));
			
			BFunPermission permiData = voToFunPermission(vo);
			
			funPermiData.setFunPermission(permiData);
			
			ouFunPermiSet.add(funPermiData);
		}
		
		return ouFunPermiSet;
	}
	
	
	
	//获取用户业务数据
	public 	HashSet<BOperatorUserBizData> getUserBizDataByUserId(Long _id) throws Exception{
		HashSet<BOperatorUserBizData> ouBizDataSet = new HashSet<BOperatorUserBizData>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T.ID T_ID,T.OPERATORUSERID,T.VERSION T_VERSION,T.ISMANAGING");
		sb.append(",D.ID,D.VERSION,D.CODE,D.NAME,D.TYPE,D.SOURCE,D.ISVALID,D.DESCRIPTION,D.COMMENTS");
		sb.append(" FROM OPERATORUSER_BIZDATA T,BIZDATA D WHERE D.ID=T.BIZDATAID AND T.OPERATORUSERID=?");
		sb.append("");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sb.toString(),_id);
		
		for(int i=0;i<vos.length;i++){
			HashVO vo = vos[i];
			BOperatorUserBizData oudata = new BOperatorUserBizData();
			oudata.setId(vo.getLongValue("T_ID"));
			oudata.setVersion(vo.getLongValue("T_VERSION"));
			oudata.setIsManaging(vo.getIntegerValue("ISMANAGING"));
			
			BBizData bizData = voToBizData(vo);
			
			oudata.setBizData(bizData);
			
			ouBizDataSet.add(oudata);
		}
		
		return ouBizDataSet;
	}
	
	public BFunPermission voToFunPermission(HashVO vo) throws Exception{
		BFunPermission jObj = new BFunPermission();
		jObj.setId(vo.getLongValue("id"));
		jObj.setCode(vo.getStringValue("code"));
		jObj.setName(vo.getStringValue("name"));
		jObj.setComments(vo.getStringValue("comments"));
		jObj.setDescription(vo.getStringValue("description"));
		jObj.setVersion(vo.getLongValue("version"));
		
		return jObj;
	}
	
	/**
	 * VO对象转换成BBizData对象
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public BBizData voToBizData(HashVO vo) throws Exception{
		BBizData bizData = new BBizData();
		bizData.setId(vo.getLongValue("id"));
		bizData.setCode(vo.getStringValue("code"));
		bizData.setComments(vo.getStringValue("comments"));
		bizData.setDescription(vo.getStringValue("description"));
		bizData.setIsValid(vo.getIntegerValue("isValid"));
		bizData.setName(vo.getStringValue("name"));
		bizData.setSource(vo.getIntegerValue("source"));
		bizData.setType(vo.getIntegerValue("type"));
		bizData.setVersion(vo.getLongValue("version"));
		
		return bizData;
	}

}
