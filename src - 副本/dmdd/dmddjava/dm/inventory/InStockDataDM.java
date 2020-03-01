package dmdd.dmddjava.dm.inventory;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cool.common.constant.DMOConst;
import com.cool.common.util.HashVoUtil;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dm.BasicDM;
import dmdd.dmddjava.dm.MainDataQueryDm;
import dmdd.dmddjava.service.validator.DateValidator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jul 19, 2017 11:32:09 PM
 */
public class InStockDataDM extends BasicDM {
	
	private MainDataQueryDm mainDataQueryDm = new MainDataQueryDm();
	
	/**从界面导入库存数据*/
    public List<Map<String,String>> importData4UI(List<Map<String,String>> dataList) throws Exception {
    	int okNum = 0; //处理成功的数据
    	int failNum = 0; //处理失败的数据
    	
    	//一条一条处理，处理失败的数据不影响成功数据的入库
    	//针对SQL SERVER的merge语句
    	StringBuilder sqlSb = new StringBuilder("if not exists (");
    	sqlSb.append(" SELECT 1 FROM IN_STOCK_DATA T WHERE PERIOD=? AND ORGANIZATIONID=? AND PRODUCTID=? AND BATCH_NO=? ");
    	sqlSb.append(" ) insert into IN_STOCK_DATA(version, period, organizationid, productid, batch_no, expire_date, quantity, comments) ");
    	sqlSb.append("  values (0, ?, ?, ?, ?, ?, ?, ?)");
    	sqlSb.append(" else ");
    	sqlSb.append(" update IN_STOCK_DATA set version=version+1,expire_date=?, quantity=?, comments=? ");
    	sqlSb.append(" WHERE PERIOD=? AND ORGANIZATIONID=? AND PRODUCTID=? AND BATCH_NO=? ");
    	sqlSb.append("");
    	
    	//String rowResult = "OK";
    	for(Map<String,String> data : dataList){
    		try{
    			String proCode = data.get("proCode");
    			String orgCode = data.get("orgCode");
    			String batchNo = data.get("batchNo");
    			String period = data.get("period");
    			String expire_date = data.get("expireDate");
    			String comments = data.get("comments");
    			//为空判断
    			if(StringUtils.isEmpty(proCode) || StringUtils.isEmpty(orgCode) 
    				|| StringUtils.isEmpty(batchNo) 
    				|| StringUtils.isEmpty(data.get("quantity")) || StringUtils.isEmpty(period) ){
    				throw new Exception("key data is not allowed null.(period,procode,orgcode,orderType,batchNo,quantity)");
    			}
    			//判断产品和组织是否存在
    			BProduct product = mainDataQueryDm.getDetailProductByCode(proCode);
    			if(product == null){
    				throw new Exception("Cannot find detail product by code ");
    			}
    			
    			if( expire_date.length() !=8 || !DateValidator.isValidateYYYYMMDD(expire_date)){
    				throw new Exception("Invalid expire date, Should by valid YYYYMMDD ");
    			}
    			
    			BOrganization org = mainDataQueryDm.getDetailOranizationByCode(data.get("orgCode"));
    			if(org == null ){
    				throw new Exception("Cannot find detail organization by code ");
    			}
    			
    			//数量必须为正数	
    			double quantity = new Double(data.get("quantity") );
    			if(quantity < 0 ){
    				throw new Exception("quantity must be a positive number ");
    			}
    			
    			try{
	    			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sqlSb.toString(), period, org.getId(),product.getId(),batchNo,
	    					period, org.getId(),product.getId(),batchNo,expire_date,quantity, comments
	    					,expire_date,quantity, comments, period, org.getId(),product.getId(),batchNo );
	    			dmo.commit(DMOConst.DS_DEFAULT);
    			}catch (Exception e) {//数据库异常
					dmo.rollback(DMOConst.DS_DEFAULT);
					throw e;
				}
    			okNum ++;
    			data.put("importResult", "OK");
    		}catch (Exception e) {
    			failNum ++;
    			data.put("importResult", e.toString());
				logger.warn("库存数据ZLIFE导入中，单条数据处理异常["+data+"]");
				e.printStackTrace();
			}
    	}
    	
    	logger.info("导入库存数据ZLIFE，本次处理["+dataList.size()+"]条，成功["+okNum+"]条，失败["+failNum+"]条");
    	
    	return dataList;
    }
	
	/**
	 * 查询导入的采购订单数据
	 * @param cond
	 * @return
	 * @throws Exception
	 */
    public List<Map<String,Object>> queryByCondition(Map<String,String> cond) throws Exception {
        String sql = "SELECT D.ID,D.PERIOD,D.PRODUCTID,D.ORGANIZATIONID,D.BATCH_NO,D.EXPIRE_DATE,D.QUANTITY,D.COMMENTS"
        	+" ,O.CODE ORGCODE,O.NAME ORGNAME,P.CODE PROCODE,P.NAME PRONAME"
        	+" FROM IN_STOCK_DATA D,PRODUCT P,ORGANIZATION O"
        	+" WHERE O.ID = D.ORGANIZATIONID AND P.ID=D.PRODUCTID ";
        
        sql += " and period = " + cond.get("period");
        if(StringUtils.isNotEmpty(cond.get("orderType"))){
        	sql += " and ordertype = '" + cond.get("orderType") + "'";
        }
        
        if(StringUtils.isNotEmpty(cond.get("expire_date_begin"))){
        	sql += " and expire_date >= '" + cond.get("expire_date_begin") + "'";
        }
        if(StringUtils.isNotEmpty(cond.get("expire_date_end"))){
        	sql += " and expire_date <= '" + cond.get("expire_date_end") + "'";
        }
        
        if( "true".equalsIgnoreCase(cond.get("blurMatch")) ){
        	//模糊查询
        	if(StringUtils.isNotEmpty(cond.get("batchNo"))){
            	sql += " and batch_no like '%" + cond.get("batchNo") + "%'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code like '" + cond.get("proCode") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code like '" + cond.get("orgCode") + "%'";
            }
        }else{
        	if(StringUtils.isNotEmpty(cond.get("batchNo"))){
            	sql += " and batch_no = '" + cond.get("batchNo") + "'";
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
