package dmdd.dmddjava.dm;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.system.ServerEnvironment;

public class BasicDM {
	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());

	/**
	 * 删除查询条件中的临时数据
	 * 
	 * @param tag
	 * @throws Exception
	 */
	public void deleteProOrg(String tag) throws Exception {
		String sql = "delete from QUERY_PRODORG where QUERYTAG=?";
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sql, tag);
		dmo.commit(DMOConst.DS_DEFAULT);
	}

//	public int judgeDetailIdQuantity(String ids) throws Exception {
//		ids = ids.replaceAll(" ", "");
//		ids = ids.substring(2, ids.length() - 2);
//		String[] temp = ids.split("\\),\\(");
//
//		return temp.length;
//	}

	/**
	 * 把明细ID插入临时表中,供关联查询使用,tag为本次的查询标签
	 * 
	 * @param ids
	 * @param tag
	 * @throws Exception
	 */
	public void insertProORg(String ids, String tag) throws Exception {
		ids = ids.replaceAll(" ", "");
		ids = ids.substring(2, ids.length() - 2);
		String[] temp = ids.split("\\),\\(");
		
		if (ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_ORACLE)) {
			
			StringBuilder sb = new StringBuilder("insert into QUERY_PRODORG(ID,PRODUCTID,ORGANIZATIONID,QUERYTAG) SELECT S_QUERY_PRODORG.NEXTVAL,A.* FROM( ");
			sb.append("SELECT -1 C1,-1 C2,'"+ tag +"' C3 FROM DUAL ");
			int count = 0;
			for (String id : temp) {
				sb.append(" union all select " + id + ",'" + tag + "' from dual");
				count++;
				if (count == 500) {
					sb.append(" ) A ");
					dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString());
					dmo.commit(DMOConst.DS_DEFAULT);
					count = 0;
					sb = new StringBuilder("insert into QUERY_PRODORG(ID,PRODUCTID,ORGANIZATIONID,QUERYTAG) SELECT S_QUERY_PRODORG.NEXTVAL,A.* FROM( ");
					sb.append("SELECT -1 C1,-1 C2,'"+ tag +"' C3 FROM DUAL ");
				}
			}
			sb.append(" ) A ");
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString());
			//dmo.commit(DMOConst.DS_DEFAULT);
		}else if(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE).equals(BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER)){
			StringBuffer sb = new StringBuffer("insert into QUERY_PRODORG(PRODUCTID,ORGANIZATIONID,QUERYTAG) values ");
			int count = 0;
			for (String id : temp) {
				count++;
				sb.append("("+ id + ",'" + tag + "'),");
				if (count == 600) {
					dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString().substring(0,sb.length()-1) );
					dmo.commit(null);
					sb = new StringBuffer("insert into QUERY_PRODORG(PRODUCTID,ORGANIZATIONID,QUERYTAG) values ");
					count = 0;
				}
			}
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString().substring(0,sb.length()-1) );
			//dmo.commit(DMOConst.DS_DEFAULT);
		}
		dmo.commit(DMOConst.DS_DEFAULT);
	}
	
	public static boolean isOracle() {
	    return BizConst.SYSPARAM_VALUE_DATABASE_ORACLE.equals(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE));
	}
	
	public static boolean isSqlServer() {
	    return BizConst.SYSPARAM_VALUE_DATABASE_SQLSERVER.equals(ServerEnvironment.getInstance().getConfigFileParam(BizConst.SYSPARAM_CODE_DATABASE));
	}
}
