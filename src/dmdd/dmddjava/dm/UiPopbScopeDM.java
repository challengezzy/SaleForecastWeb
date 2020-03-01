package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.common.utils.UtilProOrg;
import dmdd.dmddjava.dataaccess.aidobject.ABProOrgPathCode;
import dmdd.dmddjava.dataaccess.bdconvertor.BizDataBDConvertor;
import dmdd.dmddjava.dataaccess.bizobject.BBizData;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScope;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeBizData;
import dmdd.dmddjava.dataaccess.bizobject.BUiPopbScopeProOrg;
import dmdd.dmddjava.dataaccess.dataobject.BizData;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserBizData;
import dmdd.dmddjava.dataaccess.dataobject.OperatorUserProOrg;
import dmdd.dmddjava.dataaccess.hibernate.HibernateSessionFactory;
import dmdd.dmddjava.dataaccess.hibernate.dao.DaoBizData;


public class UiPopbScopeDM  extends BasicDM
{
	/**
	 * 根据两种id查询重用条件，返回的是过滤好的结果
	 * @param _uiPopbScopId
	 * @param _operatorUserId
	 * @return
	 * @throws Exception
	 */
	public BUiPopbScope getUiPopbScope( Long _uiPopbScopId,Long _operatorUserId ) throws Exception
	{
		logger.info("开始查询常用条件 _uiPopbScopId="+_uiPopbScopId+" _operatorUserId="+_operatorUserId);
		BUiPopbScope bUiPopbScope = new BUiPopbScope();
		
		//第一步补全BUiPopbScope基本信息
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT ID");
		buf.append(",UICODE");
		buf.append(",CODE");
		buf.append(",NAME");
		buf.append(",ISDEFAULT");
		buf.append(",ISPERIODCONTROL");
		buf.append(",PERIODOFFSETBEGIN");
		buf.append(",PERIODOFFSETEND");
		buf.append(",ISDISPLAYCONTROL");
		buf.append(",ISSHOWPRODUCT");
		buf.append(",PRODUCTLAYERID");
		buf.append(",ISSHOWPRODUCTCHARACTER");
		buf.append(",PRODUCTCHARACTERLAYERID");
		buf.append(",PRODUCTCHARACTERTYPE");
		buf.append(",ISSHOWORGANIZATION");
		buf.append(",ORGANIZATIONLAYERID");
		buf.append(",ISSHOWORGANIZATIONCHARACTER");
		buf.append(",ORGANIZATIONCHARACTERLAYERID");
		buf.append(",ORGANIZATIONCHARACTERTYPE");
		buf.append(",DESCRIPTION");
		buf.append(",COMMENTS");
		buf.append(",OPERATORUSERID");
		buf.append(" FROM UIPOPBSCOPE ");
		buf.append(" WHERE ID=?");
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, buf.toString(),_uiPopbScopId); 
		if(vos==null || vos.length<1)
		{
			return bUiPopbScope;
		}
		HashVO vo = vos[0];
		bUiPopbScope.setId(vo.getLongValue("ID"));
		bUiPopbScope.setUiCode(vo.getStringValue("UICODE"));
		bUiPopbScope.setCode(vo.getStringValue("CODE"));
		bUiPopbScope.setName(vo.getStringValue("NAME"));
		bUiPopbScope.setIsDefault(vo.getIntegerValue("ISDEFAULT"));
		bUiPopbScope.setIsPeriodControl(vo.getIntegerValue("ISPERIODCONTROL"));
		bUiPopbScope.setPeriodOffsetBegin(vo.getIntegerValue("PERIODOFFSETBEGIN"));
		bUiPopbScope.setPeriodOffsetEnd(vo.getIntegerValue("PERIODOFFSETEND"));
		bUiPopbScope.setIsDisplayControl(vo.getIntegerValue("ISDISPLAYCONTROL"));
		bUiPopbScope.setIsShowProduct(vo.getIntegerValue("ISSHOWPRODUCT"));
		bUiPopbScope.setProductLayerId(vo.getLognValue("PRODUCTLAYERID"));
		bUiPopbScope.setIsShowProductCharacter(vo.getIntegerValue("ISSHOWPRODUCTCHARACTER"));		
		bUiPopbScope.setProductCharacterLayerId(vo.getLognValue("PRODUCTCHARACTERLAYERID"));
		bUiPopbScope.setProductCharacterType(vo.getStringValue("PRODUCTCHARACTERTYPE"));
		bUiPopbScope.setIsShowOrganization(vo.getIntegerValue("ISSHOWORGANIZATION"));
		bUiPopbScope.setOrganizationLayerId(vo.getLognValue("ORGANIZATIONLAYERID"));
		bUiPopbScope.setIsShowOrganizationCharacter(vo.getIntegerValue("ISSHOWORGANIZATIONCHARACTER"));
		bUiPopbScope.setOrganizationCharacterLayerId(vo.getLognValue("ORGANIZATIONCHARACTERLAYERID"));
		bUiPopbScope.setOrganizationCharacterType(vo.getStringValue("ORGANIZATIONCHARACTERTYPE"));
		bUiPopbScope.setDescription(vo.getStringValue("DESCRIPTION"));		
		bUiPopbScope.setComments(vo.getStringValue("COMMENTS"));
		bUiPopbScope.setOperatorUserId(vo.getLognValue("OPERATORUSERID"));
		
		//读取业务范围
		bUiPopbScope.setUiPopbScopeProOrgs(getProOrgs(_uiPopbScopId));
		//设置有效的业务数据
		bUiPopbScope.setUiPopbScopeBizDatas(getBizDatas(_uiPopbScopId));
		
		logger.info("查询常用条件结束");
		return bUiPopbScope;
	}
	
	public Set<BUiPopbScopeProOrg> getProOrgs(Long _uiPopbScopId)throws Exception
	{
		logger.info("开始查询业务范围");
		Set<BUiPopbScopeProOrg> list = new HashSet<BUiPopbScopeProOrg>();
		StringBuffer buf = new StringBuffer();
		buf.append("select B.id productId,B.Code productCode, B.name productName,B.Isvalid productIsValid,B.Productlayerid, ");
		buf.append("B.Pathcode productPath ,B.Unitgroupid,B.Unitid");
		buf.append(", C.Id orgId,C.Code orgCode,C.Name orgName,C.Isvalid orgIsValid ,C.Organizationlayerid ,C.Pathcode orgPath ");
		buf.append(" from uipopbscope_proorg A,product B,organization C ");
		buf.append("where A.Productid = B.id and A.Organizationid = C.id ");
		buf.append("and A.Uipopbscopeid = ? ");
		
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, buf.toString(),_uiPopbScopId);
		BUiPopbScopeProOrg bUiPopbScopeProOrg;
		BProduct product;
		BOrganization org;
		for(HashVO vo:vos)
		{
			bUiPopbScopeProOrg = new BUiPopbScopeProOrg();
			product = new BProduct();
			product.setId(vo.getLognValue("productId"));
			product.setCode(vo.getStringValue("productCode"));
			product.setName(vo.getStringValue("productName"));
			product.setIsValid(vo.getIntegerValue("productIsValid"));
			product.setProductLayerId(vo.getLognValue("B.Productlayerid"));
			product.setPathCode(vo.getStringValue("productPath"));
			
			product.setUnitId(vo.getLognValue("Unitid"));
			product.setUnitGroupId(vo.getLognValue("Unitgroupid"));
			bUiPopbScopeProOrg.setProduct(product);
			org = new BOrganization();
			org.setId(vo.getLognValue("orgId"));
			org.setCode(vo.getStringValue("orgCode"));
			org.setName(vo.getStringValue("orgName"));
			org.setIsValid(vo.getIntegerValue("orgIsValid"));
			org.setOrganizationLayerId(vo.getLognValue("C.Organizationlayerid"));
			org.setPathCode(vo.getStringValue("orgPath"));
			bUiPopbScopeProOrg.setOrganization(org);
			list.add(bUiPopbScopeProOrg);
		}
		logger.info("查询业务范围结束");
		return list;
	}
	
	public Set<BUiPopbScopeBizData> getBizDatas(Long _uiPopbScopId)throws Exception
	{
		logger.info("开始获取条件中和权限中的业务数据交集");
		Set<BUiPopbScopeBizData> list = new HashSet<BUiPopbScopeBizData>();
		StringBuffer buf  = new StringBuffer();
		buf.append("select A.BIZDATAID ID from UIPOPBSCOPE_BIZDATA A ");
		buf.append("where A.UIPOPBSCOPEID=? ");
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, buf.toString(),_uiPopbScopId); 
		if(vos==null || vos.length<1)
			return list;
		Session session = HibernateSessionFactory.getSession();
		Transaction trsa = null;
		try
		{
			trsa = session.beginTransaction();
			DaoBizData dao = new DaoBizData(session);
			BizDataBDConvertor convertor = new BizDataBDConvertor();
			Long id;
			BizData bizdata;
			BUiPopbScopeBizData bUiPopbScopeBizData;
			for(HashVO vo:vos)
			{
				id = vo.getLognValue("ID");
				bizdata = dao.getBizDataById(id);
				BBizData bbizdata = (BBizData)convertor.dtob(bizdata, true);
				bUiPopbScopeBizData = new BUiPopbScopeBizData();
				bUiPopbScopeBizData.setBizData(bbizdata);
				list.add(bUiPopbScopeBizData);
			}
			trsa.commit();
		}
		catch( Exception ex )
		{
			if( trsa != null )
			{
				trsa.rollback();
			}
			logger.error(ex);
			throw ex;
		}
		finally
		{
			session.close();
		}
		logger.info("获取条件中和权限中的业务数据交集结束");
		return list;
	}
	
	/**
	 * 删除属于list下面的所有常用条件中的业务范围
	 * @param list
	 * @param operatorUserId
	 * @throws Exception
	 */
	public void deleteProOrgByOperatorUserId(List<OperatorUserProOrg> list,Long operatorUserId)throws Exception
	{
		logger.info("开始删除掉用户相关的常用条件中不需要的业务范围，operatorUserId="+operatorUserId);
		if(list.size()<1)
			return ;
		HashMap<String,String> hm = new HashMap<String, String>();
		for(OperatorUserProOrg proorg:list)
		{
			hm.put(proorg.getProduct().getPathCode(), proorg.getOrganization().getPathCode());
		}
		StringBuffer buf_del = new StringBuffer();
		buf_del.append("-1");
		StringBuffer buf = new StringBuffer();
		buf.append("select A.id ID, C.PATHCODE proPathCode ,D.PATHCODE orgPathCode");
		buf.append(" from UIPOPBSCOPE_PROORG A,UIPOPBSCOPE B,PRODUCT C,ORGANIZATION D");
		buf.append(" where OPERATORUSERID=? and A.UIPOPBSCOPEID = B.ID");
		buf.append(" and A.PRODUCTID = C.ID and A.ORGANIZATIONID = D.ID");
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, buf.toString(),operatorUserId); 
		if(vos==null || vos.length<1)
			return ;
		String proPathCode="";
		String orgPathCode="";
		for(HashVO vo:vos)
		{
			proPathCode = vo.getStringValue("proPathCode");
			orgPathCode = vo.getStringValue("orgPathCode");
			if(UtilProOrg.checkRelation(hm,proPathCode,orgPathCode))
			{
				buf_del.append(","+vo.getLongValue("ID"));
			}
		}
		
		if(buf_del.toString().length()>2)
		{
			String sql = "delete from UIPOPBSCOPE_PROORG where id in("+buf_del.toString()+")";
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT,sql);
			dmo.commit(DMOConst.DS_DEFAULT);
		}
		dmo.releaseContext(DMOConst.DS_DEFAULT);
		logger.info("删除不需要的常用条件中业务范围完成");
		
	}
	
	public void deleteBizDataByOperatorUserId(List<OperatorUserBizData> list,Long operatorUserId)throws Exception
	{
		logger.info("开始删除掉用户相关的常用条件中的不需要的业务数据");
		String ids = "-1";
		for(OperatorUserBizData bizdata:list)	
			ids=ids+","+bizdata.getId();
		String sql = "delete from UIPOPBSCOPE_BIZDATA where ID in("+ids+") and "+
					 "UIPOPBSCOPEID in(select ID from UIPOPBSCOPE where OPERATORUSERID=?)"	;
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT,sql,operatorUserId);
		dmo.commit(DMOConst.DS_DEFAULT);
		
		logger.info("删除用户相关的常用条件中的不需要的业务数据完成");
	}
	
	public boolean refreshUiPopbScope()throws Exception
	{
		logger.info("开始刷新常用条件中业务范围及业务数据");
		List<String> sqls = new ArrayList<String>();
		logger.info("先组织所有常用条件数据，便于分析");
		HashMap<Long,Long> hm_ui = new HashMap<Long, Long>();
		HashMap<Long,List<ABProOrgPathCode>> hm_op = new HashMap<Long, List<ABProOrgPathCode>>();
		HashMap<Long,String> hm_op_biz = new HashMap<Long,String>();
		String sql="select ID,OPERATORUSERID from UIPOPBSCOPE";
		HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
		if(vos==null || vos.length<1)//没有任何常用条件，直接返回成功
			return true;
		for(HashVO vo:vos)
		{
			hm_ui.put(vo.getLongValue("ID"), vo.getLognValue("OPERATORUSERID"));
		}
		logger.info("查询相关建立了常用条件的用户业务范围和业务数据信息");
		for(long id:hm_ui.values())
		{
			if(!hm_op.containsKey(id))
			{
				sql = "select B.PATHCODE proPathCode,C.PATHCODE orgPathCode from OPERATORUSER_PROORG A,PRODUCT B,ORGANIZATION C "+
						"where A.PRODUCTID = B.ID and A.ORGANIZATIONID = C.ID and OPERATORUSERID =?";
				vos=dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,id);
				List<ABProOrgPathCode> list = new ArrayList<ABProOrgPathCode>();
				if(vos!=null && vos.length>0)
				{
					ABProOrgPathCode pathcode= null;
					
					for(HashVO vo:vos)
					{
						pathcode = new ABProOrgPathCode();
						pathcode.setProPathCode(vo.getStringValue("proPathCode"));
						pathcode.setOrgPathCode(vo.getStringValue("orgPathCode"));
						list.add(pathcode);
						
					}
					
				}
				hm_op.put(id, list);
				
				sql="select BIZDATAID from OPERATORUSER_BIZDATA A where  OPERATORUSERID = ?";
				vos=dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,id);
				String ids = "-1";
				if(vos!=null && vos.length>0)
				{
					for(HashVO vo:vos)
					{
						ids = ids+","+vo.getLongValue("BIZDATAID");
					}
				}
				hm_op_biz.put(id, ids);
			}
		}
		logger.info("开始逐个刷新常用条件");
		try
		{
			for(long id:hm_ui.keySet())
			{
				logger.info("删除不需要的业务范围");
				sqls = new ArrayList<String>();
				sql="select A.ID , B.PATHCODE proPathCode,C.PATHCODE orgPathCode from UIPOPBSCOPE_PROORG A,PRODUCT B,ORGANIZATION C "+
					"where A.PRODUCTID = B.ID and A.ORGANIZATIONID = C.ID and UIPOPBSCOPEID=?";
				vos=dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,id);
				if(vos!=null && vos.length>0)
				{
					String proPathCode;
					String orgPathCode;
					for(HashVO vo:vos)
					{
						proPathCode = vo.getStringValue("proPathCode");
						orgPathCode = vo.getStringValue("orgPathCode");
						if(!UtilProOrg.checkRelation(hm_op.get(hm_ui.get(id)), proPathCode, orgPathCode))//需要删除
						{
							sqls.add("delete from UIPOPBSCOPE_PROORG where id="+vo.getLongValue("ID"));
						}
					}
				}
				logger.info("删除不需要的业务数据");
				sqls.add("delete from UIPOPBSCOPE_BIZDATA where BIZDATAID not in("+hm_op_biz.get(hm_ui.get(id))+") and UIPOPBSCOPEID="+id);		
				dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
				dmo.commit(DMOConst.DS_DEFAULT);
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
			dmo.rollback(DMOConst.DS_DEFAULT);
			throw ex;
		}
		finally
		{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return true;
	}
}
