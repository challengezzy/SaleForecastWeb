package dmdd.dmddjava.dm;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;

import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;

public class MainDataQueryDm {
	
	protected CommDMO dmo = new CommDMO();
	protected Logger logger = CoolLogger.getLogger(this.getClass());
	
	/** 根据编码查询产品，返回对象只保留了基本信息,ID,CODE,NAME */
	 public BProduct getDetailProductByCode(String proCode) {
	        BProduct product = null;
	        String sql = "select id,code,name  from product where code =? and iscatalog = 0";
	        try {
	            HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, proCode);
	            if (vos == null || vos.length < 1 || vos.length > 1) // 查询不到或者查询结果超过1个，都视为无效
	                return null;
	            else if (vos.length == 1) {
	                HashVO vo = vos[0];
	                product = new BProduct();
	                product.setId(vo.getLognValue("ID"));
	                product.setName(vo.getStringValue("NAME"));
	                product.setCode(vo.getStringValue("CODE"));
	            }
	        } catch (Exception e) {
	            logger.error("根据产品编码查询产品异常:" + e.toString());
	        } 

	        return product;
	    }
	 
	    /**
	     * 根据编码查询组织，返回对象只保留了基本信息,ID,CODE,NAME
	     * @param orgCode
	     * @return
	     */
	    public BOrganization getDetailOranizationByCode(String orgCode) {
	    	BOrganization org = null;
	        String sql = "select id,code,name  from organization where code =? and iscatalog = 0";
	        try {
	            HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql, orgCode);
	            if (vos == null || vos.length < 1 || vos.length > 1) // 查询不到或者查询结果超过1个，都视为无效
	                return null;
	            else if (vos.length == 1) {
	                HashVO vo = vos[0];
	                org = new BOrganization();
	                org.setId(vo.getLognValue("ID"));
	                org.setName(vo.getStringValue("NAME"));
	                org.setCode(vo.getStringValue("CODE"));
	            }
	        } catch (Exception e) {
	            logger.error("根据组织编码查询组织异常:" + e.toString());
	        } 

	        return org;
	    }

}
