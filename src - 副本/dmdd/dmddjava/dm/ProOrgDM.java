package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.dataaccess.aidobject.AProOrg;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

public class ProOrgDM   extends BasicDM
{

	/**
	 * 以产品为基点，搜集并集
	 * @param id
	 * @param toId
	 * @param org
	 * @return
	 */
	public List<AProOrg> buildProductIntersectedProOrgs(Long id,Long toId,Organization org)
	{
		List<AProOrg> list = new ArrayList<AProOrg>();
		if(id.equals(toId))
			return list;
		AProOrg rstProOrg = null;
		Product _pro;
		Long parentId=-1L;
		try
		{
			String sql="select ID,NAME,CODE,PATHCODE,PARENTPRODUCTID from product where PARENTPRODUCTID=(select PARENTPRODUCTID from PRODUCT where ID=?)";
			HashVO[] vos =dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sql,id);
			for(HashVO vo:vos)
			{
				if(!vo.getLongValue("ID").equals(id))
				{	
					rstProOrg = new AProOrg();
					_pro = new Product();
					_pro.setId(vo.getLognValue("ID"));
					_pro.setCode(vo.getStringValue("CODE"));
					_pro.setName(vo.getStringValue("NAME"));
					_pro.setPathCode(vo.getStringValue("PATHCODE"));
					parentId = vo.getLongValue("PARENTPRODUCTID");
					rstProOrg.setProduct(_pro);
					rstProOrg.setOrganization(org);
					list.add( rstProOrg );
				}
			}
			list.addAll(buildProductIntersectedProOrgs(parentId,toId,org));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex);
		}
		finally
		{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return list;
	}
	
	public List<AProOrg> buildOrgIntersectedProOrgs(Long id,Long toId,Product pro)
	{
		List<AProOrg> list = new ArrayList<AProOrg>();
		if(id.equals(toId))
			return list;
		AProOrg rstProOrg = null;
		Organization _org;
		Long parentId=-1L;
		try
		{
			String sql="select ID,NAME,CODE,PATHCODE,PARENTORGANIZATIONID from ORGANIZATION where PARENTORGANIZATIONID=(select PARENTORGANIZATIONID from ORGANIZATION where ID=?)";
			HashVO[] vos =dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sql,id);
			for(HashVO vo:vos)
			{
				if(!vo.getLongValue("ID").equals(id))
				{	
					rstProOrg = new AProOrg();
					_org = new Organization();
					_org.setId(vo.getLognValue("ID"));
					_org.setCode(vo.getStringValue("CODE"));
					_org.setName(vo.getStringValue("NAME"));
					_org.setPathCode(vo.getStringValue("PATHCODE"));
					parentId = vo.getLongValue("PARENTORGANIZATIONID");
					rstProOrg.setProduct(pro);
					rstProOrg.setOrganization(_org);
					list.add( rstProOrg );
				}
			}
			list.addAll(buildOrgIntersectedProOrgs(parentId,toId,pro));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex);
		}
		finally
		{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return list;
	}
}
