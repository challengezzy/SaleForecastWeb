package dmdd.dmddjava.dm.inventory;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cool.common.constant.DMOConst;
import com.cool.common.util.HashVoUtil;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.dm.BasicDM;

/**
 * 库存的相关数据操作
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jul 22, 2017 4:06:35 PM
 */
public class StockDataDM extends BasicDM {
	
	
	
	public List<Map<String,Object>> queryStockDataByCond(Map<String,String> cond) throws Exception {
        String sql = "SELECT D.ID,D.PERIOD,D.PRODUCTID,D.ORGANIZATIONID,EXPIRE_PERIOD,OFFSHELF_PERIOD,D.QUANTITY,BATCH_NUM"
        	+" ,O.CODE ORGCODE,O.NAME ORGNAME,P.CODE PROCODE,P.NAME PRONAME,P.WITHDRAW_LEAD_TIME"
        	+" FROM INV_STOCK_DATA D,PRODUCT P,ORGANIZATION O"
        	+" WHERE O.ID = D.ORGANIZATIONID AND P.ID=D.PRODUCTID ";
        
        sql += " and period = " + cond.get("period");
        
        if( "true".equalsIgnoreCase(cond.get("blurMatch")) ){
        	//模糊查询
        	if(StringUtils.isNotEmpty(cond.get("offshelf_period"))){
            	sql += " and offshelf_period like '%" + cond.get("offshelf_period") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("expire_period"))){
            	sql += " and expire_period like '" + cond.get("expire_period") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code like '" + cond.get("proCode") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code like '" + cond.get("orgCode") + "%'";
            }
        }else{
        	if(StringUtils.isNotEmpty(cond.get("offshelf_period"))){
            	sql += " and offshelf_period = '" + cond.get("offshelf_period") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("expire_period"))){
            	sql += " and expire_period = '" + cond.get("expire_period") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code = '" + cond.get("proCode") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code = '" + cond.get("orgCode") + "'";
            }
        }
        
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        List<Map<String,Object>> vosList = HashVoUtil.hashVosToMapList(vos);
        
        return vosList;
    }
	
	public List<Map<String,Object>> queryPoprData(Map<String,String> cond) throws Exception {
        String sql = "SELECT D.ID,D.PERIOD,D.PRODUCTID,D.ORGANIZATIONID,RECEIVING_PERIOD,OFFSHELF_PERIOD,D.QUANTITY,BATCH_NUM,ORDERTYPE"
        	+" ,O.CODE ORGCODE,O.NAME ORGNAME,P.CODE PROCODE,P.NAME PRONAME,P.SHELF_LIFE,P.WITHDRAW_LEAD_TIME"
        	+" FROM POPR_DATA D,PRODUCT P,ORGANIZATION O"
        	+" WHERE D.STATUS=1 AND O.ID = D.ORGANIZATIONID AND P.ID=D.PRODUCTID ";
        
        sql += " and period = " + cond.get("period");
        if(StringUtils.isNotEmpty(cond.get("orderType"))){
        	sql += " and ORDERTYPE = '" + cond.get("orderType") + "'";
        }
        
        if( "true".equalsIgnoreCase(cond.get("blurMatch")) ){
        	//模糊查询
        	if(StringUtils.isNotEmpty(cond.get("offshelf_period"))){
            	sql += " and offshelf_period like '%" + cond.get("offshelf_period") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("receiving_period"))){
            	sql += " and RECEIVING_PERIOD like '" + cond.get("RECEIVING_PERIOD") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code like '" + cond.get("proCode") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code like '" + cond.get("orgCode") + "%'";
            }
        }else{
        	if(StringUtils.isNotEmpty(cond.get("offshelf_period"))){
            	sql += " and offshelf_period = '" + cond.get("offshelf_period") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("receiving_period"))){
            	sql += " and RECEIVING_PERIOD = '" + cond.get("RECEIVING_PERIOD") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code = '" + cond.get("proCode") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code = '" + cond.get("orgCode") + "'";
            }
        }
        
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        List<Map<String,Object>> vosList = HashVoUtil.hashVosToMapList(vos);
        
        return vosList;
    }
	
	

}
