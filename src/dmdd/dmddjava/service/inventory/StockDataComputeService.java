package dmdd.dmddjava.service.inventory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.common.utils.UtilPeriod;

/**
 * 库存数据计算
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jul 22, 2017 1:57:37 PM
 */
public class StockDataComputeService {

	private Logger logger = CoolLogger.getLogger(this.getClass());

	private CommDMO dmo = new CommDMO();
	
	public void stockDataToPeriodAggr(int period) throws Exception{
		
		logger.debug("开始计算["+period+"]期间的库存汇总数据...");
		long start = System.currentTimeMillis();
		
		//删除已计算数据
		String delSql = "DELETE FROM INV_STOCK_DATA WHERE PERIOD = " + period;
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delSql);
		dmo.commit(DMOConst.DS_DEFAULT);
		
		//1,按期间查询导入的库存数据，汇总到月. 库存汇总到总维度
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT PERIOD,PRODUCTID,"); //organizationid
		sb.append(" LEFT(T.EXPIRE_DATE,6) EXPIRE_PERIOD,COUNT(1) BATCH_NUM,SUM(QUANTITY) SUM_QUANTITY,");
		sb.append(" (SELECT P.WITHDRAW_LEAD_TIME FROM PRODUCT P WHERE P.ID = T.PRODUCTID ) OFFLT,");
		sb.append(" (SELECT P.SHELF_LIFE FROM PRODUCT P WHERE P.ID = T.PRODUCTID ) SHELF_LIFE");
		sb.append(" FROM IN_STOCK_DATA T WHERE T.PERIOD=? ");
		sb.append(" GROUP BY PERIOD,PRODUCTID,LEFT(T.EXPIRE_DATE,6) ");
		sb.append("");
		
		StringBuilder mergeSql = new StringBuilder("if not exists (");
    	mergeSql.append(" SELECT 1 FROM INV_STOCK_DATA WHERE PERIOD=? AND ORGANIZATIONID=? AND PRODUCTID=? AND EXPIRE_PERIOD=? ");
    	mergeSql.append(" ) INSERT INTO INV_STOCK_DATA(VERSION,PERIOD,ORGANIZATIONID,PRODUCTID,EXPIRE_PERIOD,OFFSHELF_PERIOD,QUANTITY,BATCH_NUM)");
    	mergeSql.append(" VALUES(0, ?,?,?, ?,?,?,?)");
    	mergeSql.append(" else ");
    	mergeSql.append(" update INV_STOCK_DATA set version=version+1,updatetime=getdate(),EXPIRE_PERIOD=?, OFFSHELF_PERIOD=?, QUANTITY=?, BATCH_NUM=? ");
    	mergeSql.append(" WHERE PERIOD=? AND ORGANIZATIONID=? AND PRODUCTID=? AND EXPIRE_PERIOD=? ");
		
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sb.toString(), period);
		
		//2，数据更新到汇总表
		for(int i=0;i<vos.length;i++){
			//对过期期间为空的情况，使用最大期间
			HashVO vo = vos[i];
			String expireStr = vo.getStringValue("EXPIRE_PERIOD");
			int offLt = vo.getIntegerValue("OFFLT"); //下架提前期
			int expirePeriod;
			if( StringUtils.isEmpty(expireStr)){
				
				int shelfLife = vo.getIntegerValue("SHELF_LIFE");  //保质期
				expirePeriod = UtilPeriod.getPeriod(period, shelfLife-offLt);
			}else{
				expirePeriod = vo.getIntegerValue("EXPIRE_PERIOD");
			}
			
			int offShelfPeriod = UtilPeriod.getPeriod(expirePeriod, 0- offLt);
			
			String rootOrgId = getRootOrgId();
			String proId = vo.getStringValue("PRODUCTID");
			String batch_num = vo.getStringValue("BATCH_NUM");
			String sum_quantity = vo.getStringValue("SUM_QUANTITY");
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql.toString(), period,rootOrgId,proId,expirePeriod
					, period,rootOrgId,proId, expirePeriod,offShelfPeriod,sum_quantity,batch_num
					,expirePeriod,offShelfPeriod,sum_quantity,batch_num , period,rootOrgId,proId,expirePeriod);
			 
			if(i>1 && i%100 == 0){
				dmo.commit(DMOConst.DS_DEFAULT);
			}
		}
		dmo.commit(DMOConst.DS_DEFAULT);
		
		long cost = System.currentTimeMillis() - start;
		logger.debug("计算["+period+"]期间的库存汇总数据OK,耗时["+cost+"]ms");
		
		
	}
	
	/**
	 * 预计收货数据计算
	 * @param period
	 * @throws Exception
	 */
	public void poprDataToPeriodAggr(int period) throws Exception{
		
		logger.debug("开始计算["+period+"]期间的POPR汇总数据...");
		long start = System.currentTimeMillis();
		
		//1,按期间查询导入的库存数据，汇总到月. 库存汇总到总维度
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT PERIOD,PRODUCTID,ORDERTYPE,"); //organizationid
		sb.append(" LEFT(T.RECEIVING_DATE,6) RECEIVING_PERIOD,COUNT(1) BATCH_NUM,SUM(QUANTITY) SUM_QUANTITY,");
		sb.append(" (SELECT P.WITHDRAW_LEAD_TIME FROM PRODUCT P WHERE P.ID = T.PRODUCTID ) OFFLT,");
		sb.append(" (SELECT P.SHELF_LIFE FROM PRODUCT P WHERE P.ID = T.PRODUCTID ) SHELF_LIFE");
		sb.append(" FROM IN_POPR_DATA T WHERE STATUS=1 AND T.PERIOD=? ");
		sb.append(" GROUP BY PERIOD,PRODUCTID,ORDERTYPE,LEFT(T.RECEIVING_DATE,6) ");
		sb.append("");
		
		StringBuilder mergeSql = new StringBuilder("if not exists (");
    	mergeSql.append(" SELECT 1 FROM POPR_DATA WHERE PERIOD=? AND ORGANIZATIONID=? AND PRODUCTID=? AND ORDERTYPE=? AND RECEIVING_PERIOD=? ");
    	mergeSql.append(" ) INSERT INTO POPR_DATA(VERSION,PERIOD,ORGANIZATIONID,PRODUCTID,RECEIVING_PERIOD,OFFSHELF_PERIOD,QUANTITY,BATCH_NUM,ORDERTYPE,STATUS)");
    	mergeSql.append(" VALUES(0, ?,?,?, ?,?,?,? ,?,1)");
    	mergeSql.append(" else ");
    	mergeSql.append(" update POPR_DATA set version=version+1,updatetime=getdate(),RECEIVING_PERIOD=?, OFFSHELF_PERIOD=?, QUANTITY=?, BATCH_NUM=?, STATUS=1 ");
    	mergeSql.append(" WHERE PERIOD=? AND ORGANIZATIONID=? AND PRODUCTID=? AND ORDERTYPE=? AND RECEIVING_PERIOD=? ");
		
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sb.toString(), period);
		
		//2，数据更新到汇总表
		for(int i=0;i<vos.length;i++){
			//对过期期间为空的情况，使用最大期间
			HashVO vo = vos[i];
			String expireStr = vo.getStringValue("RECEIVING_PERIOD");
			int offLt = vo.getIntegerValue("OFFLT"); //下架提前期
			int shelfLife = vo.getIntegerValue("SHELF_LIFE");  //保质期
			int receivingPeriod;
			if( StringUtils.isEmpty(expireStr)){
				
				
				receivingPeriod = UtilPeriod.getPeriod(period, shelfLife-offLt+1);
			}else{
				receivingPeriod = vo.getIntegerValue("RECEIVING_PERIOD");
			}
			
			int offShelfPeriod = UtilPeriod.getPeriod(receivingPeriod, shelfLife - offLt);
			
			String rootOrgId = getRootOrgId();
			String proId = vo.getStringValue("PRODUCTID");
			String orderType = vo.getStringValue("ORDERTYPE");
			String batch_num = vo.getStringValue("BATCH_NUM");
			String sum_quantity = vo.getStringValue("SUM_QUANTITY");
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, mergeSql.toString(), period,rootOrgId,proId,orderType,receivingPeriod
					, period,rootOrgId,proId, receivingPeriod,offShelfPeriod,sum_quantity,batch_num,orderType
					,receivingPeriod,offShelfPeriod,sum_quantity,batch_num , period,rootOrgId,proId,orderType,receivingPeriod);
			 
			if(i>1 && i%100 == 0){
				dmo.commit(DMOConst.DS_DEFAULT);
			}
		}
		dmo.commit(DMOConst.DS_DEFAULT);
		
		long cost = System.currentTimeMillis() - start;
		logger.debug("计算["+period+"]期间的库存汇总数据OK,耗时["+cost+"]ms");
		
		
	}
	
	/**
	 * 查询组织的根节点ID
	 * @return
	 * @throws Exception
	 */
	public String getRootOrgId() throws Exception{
		String sql = " SELECT O.ID,O.CODE,O.NAME,O.PATHCODE FROM ORGANIZATION O WHERE O.PARENTORGANIZATIONID IS NULL ";
		HashVO[] vos = dmo.getHashVoArrayByDSUnlimitRows(DMOConst.DS_DEFAULT, sql );
		
		if(vos.length != 1){
			throw new Exception("未查询到组织的根节点或返回多个根节点");
		}
		
		String rootOrgId = vos[0].getStringValue("ID");
		
		return rootOrgId;
	}
	
	/**
	 * 按期间删除已导入的POPR数据，数据清零
	 * @param period
	 * @throws Exception
	 */
	public void deletePoprImportedData(int period) throws Exception{
		logger.info("清空["+ period +"]期间的POPR导入数据！");
		
		String delSql1 = "DELETE IN_POPR_DATA WHERE PERIOD = " + period;
		String delSql2 = "DELETE POPR_DATA WHERE PERIOD = " + period;
		
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delSql1);
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delSql2);
		
		dmo.commit(DMOConst.DS_DEFAULT);
		
	}
	
	public void deleteStockImportedData(int period) throws Exception{
		logger.info("清空["+ period +"]期间的库存导入数据！");
		
		String delSql1 = "DELETE IN_STOCK_DATA WHERE PERIOD = " + period;
		String delSql2 = "DELETE INV_STOCK_DATA WHERE PERIOD = " + period;
		
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delSql1);
		dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delSql2);
		
		dmo.commit(DMOConst.DS_DEFAULT);
		
	}
	
}
