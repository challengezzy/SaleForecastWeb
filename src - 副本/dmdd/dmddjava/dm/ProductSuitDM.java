/**
 * ProductSuitDM.java
 * dmdd.dmddjava.dm
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年4月24日 		sophia
 *
 * Copyright (c) 2017, Howbuy Rights Reserved.
 */

package dmdd.dmddjava.dm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cool.common.constant.DMOConst;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.dataaccess.bizobject.BSuitSkuRel;

/**
 * ClassName:ProductSuitDM Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author leslie
 * @version
 * @since Ver 1.1
 * @Date 2017年4月24日 下午6:51:33
 * 
 * @see
 */
public class ProductSuitDM extends BasicDM {

    public void updateProductSuit(Long suitProductId, Map<Long, Integer> productIdMap) throws Exception {
        // 删除旧的套装信息
        String sql = "delete from product_suit where suit_product_id = ?";
        dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sql, suitProductId);

        // 新增套装关系详情
        String insert_id = "";
        String insert_id_values = "";
        if (isOracle()) {
            insert_id = "ID,";
            insert_id_values = "s_product_suit.nextval,";
        }

        if (productIdMap.size() > 0) {
            List<String> sqls = new ArrayList<String>();
            for (Entry<Long, Integer> entry : productIdMap.entrySet()) {
                sqls.add("insert into product_suit(" + insert_id + "version,suit_product_id,product_id,product_number) values(" + insert_id_values + "0,"
                        + suitProductId + "," + entry.getKey() + "," + entry.getValue() + ")");
            }
            dmo.executeBatchByDS(DMOConst.DS_DEFAULT, sqls);
        }
        
        dmo.commit(DMOConst.DS_DEFAULT);
        
    }
    
    public List<BSuitSkuRel> getSuitSkus(Long suitProductId) throws Exception{
    	String sql = "SELECT SUIT_PRODUCT_ID,PRODUCT_ID,PRODUCT_NUMBER,P.CODE,P.NAME FROM PRODUCT_SUIT S,PRODUCT P "
    		+ "	WHERE P.ID=S.PRODUCT_ID AND SUIT_PRODUCT_ID= ? ";
    	HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, suitProductId);
    	
    	List<BSuitSkuRel> skuList = new ArrayList<BSuitSkuRel>(vos.length);
    	for(int i=0;i<vos.length;i++){
    		HashVO vo = vos[i];
    		BSuitSkuRel sku = new BSuitSkuRel();
    		sku.setSuitProductId(vo.getLongValue("SUIT_PRODUCT_ID"));
    		sku.setProId(vo.getLongValue("PRODUCT_ID"));
    		sku.setProCode(vo.getStringValue("CODE"));
    		sku.setProName(vo.getStringValue("NAME"));
    		sku.setRatio(vo.getIntegerValue("PRODUCT_NUMBER"));
    		
    		skuList.add(sku);
    	}
    	
    	return skuList;
    }
    
    public List<BSuitSkuRel> getSuitSkusByProductId(Long productId) throws Exception{
        String sql = "SELECT SUIT_PRODUCT_ID,PRODUCT_ID,PRODUCT_NUMBER,P.CODE,P.NAME FROM PRODUCT_SUIT S,PRODUCT P "
            + " WHERE P.ID=S.PRODUCT_ID AND S.PRODUCT_ID = ? ";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, productId);
        
        List<BSuitSkuRel> skuList = new ArrayList<BSuitSkuRel>(vos.length);
        for(int i=0;i<vos.length;i++){
            HashVO vo = vos[i];
            BSuitSkuRel sku = new BSuitSkuRel();
            sku.setSuitProductId(vo.getLongValue("SUIT_PRODUCT_ID"));
            sku.setProId(vo.getLongValue("PRODUCT_ID"));
            sku.setProCode(vo.getStringValue("CODE"));
            sku.setProName(vo.getStringValue("NAME"));
            sku.setRatio(vo.getIntegerValue("PRODUCT_NUMBER"));
            
            skuList.add(sku);
        }
        
        return skuList;
    }
    
    /**
     * 将套装中code:number  转换为   id:number
     */
    public Map<Long, Integer> suitCodeToSuitProductId(Map<String, Integer> suitCodeDetail) throws Exception {
        Map<Long, Integer> suitProdictIdMap = new HashMap<Long, Integer>();
        for (Entry<String, Integer> suitCode : suitCodeDetail.entrySet()) {
            
        	String sql = "SELECT ID,CODE FROM PRODUCT WHERE CODE = ? ";
        	HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, suitCode.getKey());
        	
            if (vos.length < 1) {
                throw new Exception("product code[" + suitCode.getKey() + "] not exists");
            }
            
            suitProdictIdMap.put(vos[0].getLognValue("ID") , suitCode.getValue());
        }
        
        return suitProdictIdMap;
    }
    
}
