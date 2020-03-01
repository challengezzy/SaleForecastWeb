package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;
import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;



public class OperatorUserDM  extends BasicDM
{
	/**
	 * 为用户添加用户业务范围权限
	 * @param list
	 * @param userId
	 */
	public void addProOrg4User(Collection <AProOrg>list,Long userId)throws Exception
	{
		//第一步，删除旧的
		String sql =" delete from operatoruser_proorg where operatoruserid = ?";
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sql,userId);
		dmo.commit(DMOConst.DS_DEFAULT);
		
		String insert_id = "";
		String insert_id_values="";
		if(isOracle())
		{
			insert_id="ID,";
			insert_id_values="s_operatoruser_proorg.nextval,";
		}

		
		List<String> sqls = new ArrayList<String>();
		for(AProOrg proorg:list)
		{
			sqls.add("insert into operatoruser_proorg("+insert_id+"version,operatoruserid,productid,organizationid) values("+insert_id_values+"0,"+userId+","+proorg.getProduct().getId()+","+proorg.getOrganization().getId()+")");
		}
		dmo.executeBatchByDS(DMOConst.DS_DEFAULT,sqls);
		dmo.commit(DMOConst.DS_DEFAULT);
		dmo.releaseContext(DMOConst.DS_DEFAULT);
	}
	
	/**
	 * 查询用户关联的业务范围，只需要查询出pathcode即可 
	 * @param _operatorUserId
	 * @return
	 * @throws Exception
	 */
	public List<AProOrg> getPermissionProOrgs( Long _operatorUserId)throws Exception
	{
		List<AProOrg> result = new ArrayList<AProOrg>();
		String sql = "select B.id proId,B.code proCode,B.PATHCODE proPathCode, "+
					"C.id orgId,C.CODE orgCode,C.PATHCODE orgPathCode  "+
					" from OPERATORUSER_PROORG A,PRODUCT B,ORGANIZATION C  "+
					" where A.OPERATORUSERID=? "+
					" and A.PRODUCTID = B.ID "+
					" and A.ORGANIZATIONID = C.ID";
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sql,_operatorUserId);
		AProOrg proorg;
		Product product;
		Organization org;
		for(HashVO vo:vos)
		{
			proorg = new AProOrg();
			product = new Product();
			org= new Organization();
			product.setId(vo.getLongValue("proId"));
			product.setCode(vo.getStringValue("proCode"));	
			product.setPathCode(vo.getStringValue("proPathCode"));
			org.setId(vo.getLongValue("orgId"));
			org.setCode(vo.getStringValue("orgCode"));
			org.setPathCode(vo.getStringValue("orgPathCode"));
			proorg.setProduct(product);
			proorg.setOrganization(org);
			result.add(proorg);
		}
		dmo.releaseContext(DMOConst.DS_DEFAULT);
		return result;
	}
}
