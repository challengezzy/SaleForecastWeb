package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.List;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;

public class MainDataDM extends BasicDM
{
	 public boolean refreshMainData()throws Exception
	 {
		List<String> sqls = new ArrayList<String>();
		logger.info("refreshMainData begin...");
		try
		{
		 logger.info("step 1:deal product pathCode");
		 String sql = "select ID from PRODUCT where PARENTPRODUCTID is null";
		 HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql);
		 if(vos!=null && vos.length==1)
		 {
			 sqls.add("update  PRODUCT set PATHCODE ='"+vos[0].getLognValue("ID")+"' where PARENTPRODUCTID is null");
			 sqls.addAll(buildSubNodeSqls(vos[0].getLognValue("ID"),""+vos[0].getLognValue("ID"),"PRODUCT","PARENTPRODUCTID"));
		 }
		 dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
		 dmo.commit(DMOConst.DS_DEFAULT);
		 
		 sqls = new ArrayList<String>();
		 logger.info("step 2:deal productCharater pathCode");		 
		 sql = "select ID from PRODUCTCHARACTER where PARENTPRODUCTCHARACTERID is null";
		 vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql);
		 if(vos!=null && vos.length==1)
		 {
			 sqls.add("update  PRODUCTCHARACTER set PATHCODE ='"+vos[0].getLognValue("ID")+"' where PARENTPRODUCTCHARACTERID is null");
			 sqls.addAll(buildSubNodeSqls(vos[0].getLognValue("ID"),""+vos[0].getLognValue("ID"),"PRODUCTCHARACTER","PARENTPRODUCTCHARACTERID"));
		 }
		 dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
		 dmo.commit(DMOConst.DS_DEFAULT);
		 
		 logger.info("step 3:deal org pathCode");	
		 sqls = new ArrayList<String>();
		 sql = "select ID from ORGANIZATION where PARENTORGANIZATIONID is null";
		 vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql);
		 if(vos!=null && vos.length==1)
		 {
			 sqls.add("update  ORGANIZATION set PATHCODE ='"+vos[0].getLognValue("ID")+"' where PARENTORGANIZATIONID is null");
			 sqls.addAll(buildSubNodeSqls(vos[0].getLognValue("ID"),""+vos[0].getLognValue("ID"),"ORGANIZATION","PARENTORGANIZATIONID"));
		 }
		 dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
		 dmo.commit(DMOConst.DS_DEFAULT);
		 
		 
		 logger.info("step 4:deal orgCharater pathCode");	
		 sqls = new ArrayList<String>();
		 sql = "select ID from ORGANIZATIONCHARACTER where PARENTORGANIZATIONCHARACTERID is null";
		 vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT,sql);
		 if(vos!=null && vos.length==1)
		 {
			 sqls.add("update  ORGANIZATIONCHARACTER set PATHCODE ='"+vos[0].getLognValue("ID")+"' where PARENTORGANIZATIONCHARACTERID is null");
			 sqls.addAll(buildSubNodeSqls(vos[0].getLognValue("ID"),""+vos[0].getLognValue("ID"),"ORGANIZATIONCHARACTER","PARENTORGANIZATIONCHARACTERID"));
		 }
		 dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
		 dmo.commit(DMOConst.DS_DEFAULT); 
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
		 logger.info("refreshMainData end...");
		 return true;
	 }
	 
	 /**
	  * 递归构建刷新子节点 PathCode的UPDATE语句
	  * @param id
	  * @param pathCode
	  * @param type
	  * @param parentType
	  * @return
	  * @throws Exception
	  */
	 private List<String> buildSubNodeSqls(Long id,String pathCode,String type,String parentType) throws Exception
	 {
		 List<String> list = new ArrayList<String>();
		 String sql="select ID from "+type+" where "+parentType+" = ?";
		 HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sql,id);
		 if(vos==null || vos.length<1)
		 {
			 return list;
		 }
		 else
		 {
			 String _pathCode="";
			 Long _id =0L;
			 for(HashVO vo:vos)
			 {
				 _id = vo.getLognValue("ID");
				 _pathCode = pathCode+"-"+_id;
				 list.add("update "+type+" set PATHCODE ='"+_pathCode+"' where id="+_id);
				 list.addAll(buildSubNodeSqls(_id,_pathCode,type,parentType));				 
			 }
		 }
		 
		 return list;
	 }
	 
	 
}
