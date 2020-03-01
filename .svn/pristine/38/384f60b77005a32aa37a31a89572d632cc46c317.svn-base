package dmdd.dmddjava.service.dimensionservice;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cool.common.constant.DMOConst;
import com.cool.common.util.HashVoUtil;
import com.cool.common.vo.HashVO;

import dmdd.dmddjava.dm.BasicDM;

/**
 * 数据查询类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Oct 21, 2017 4:57:22 PM
 */
public class MainDataQueryService extends BasicDM {

	/**
	 * 查询预测策略及策略下产品组织
	 * @param cond
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryForecastInstProOrg(Map<String,String> cond) throws Exception {
        String sql = "SELECT F.CODE FC_CODE,F.NAME FC_NAME,F.FCPERIODNUM,F.MAPPINGFCMODELRUNTIME LAST_RUNTIME "
        	+" ,FZPERIODNUM,ERRORTHRESHOLD,F.ISVALID,FINALFCBIZDATAID,RUNORGANIZATIONLAYERID,RUNPRODUCTLAYERID"
        	+" ,(select DESCRIPTION From PRODUCTLAYER L WHERE L.ID=F.RUNPRODUCTLAYERID) RUN_PROLAYER"
        	+" ,(select DESCRIPTION From ORGANIZATIONLAYER L WHERE L.ID=F.RUNORGANIZATIONLAYERID) RUN_ORGLAYER"
        	+" ,DISTRIBUTEREFFORMULA,DECOMPOSEFORMULA,DISTRIBUTEREFBIZDATAID,DISTRIBUTEREFPERIODNUM,F.MAPPINGFCMODELID"
        	+" ,(SELECT NAME FROM BIZDATA B WHERE B.ID = F.FINALFCBIZDATAID) FINALFC_BIZDATA"
        	+" ,(SELECT NAME FROM BIZDATA B WHERE B.ID = F.DISTRIBUTEREFBIZDATAID) DIS_BIZDATA"
        	+" ,M.CODE MODEL_CODE,M.NAME MODEL_NAME,M.HISTORYBIZDATAID"
        	+" ,(SELECT NAME FROM BIZDATA B WHERE B.ID = M.HISTORYBIZDATAID) MODELHIS_BIZDATA"
        	+" ,P.CODE PRO_CODE,P.NAME PRO_NAME,O.CODE ORG_CODE,O.NAME ORG_NAME  "
        	+" FROM FORECASTINST_PROORG FV,PRODUCT P,ORGANIZATION O,FORECASTINST F,FORECASTMODELM M "
        	+" WHERE P.ID = FV.PRODUCTID AND O.ID=FV.ORGANIZATIONID AND F.ID = FV.FORECASTINSTID AND M.ID=F.MAPPINGFCMODELID ";
        
        if( "true".equalsIgnoreCase(cond.get("blurMatch")) ){
        	//模糊查询
        	if(StringUtils.isNotEmpty(cond.get("fcCode"))){
            	sql += " and f.code like '%" + cond.get("fcCode") + "%'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("fcName"))){
            	sql += " and f.name like '%" + cond.get("fcName") + "%'";
            }
        	
        	
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code like '" + cond.get("proCode") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("proName"))){
            	sql += " and p.name like '" + cond.get("proName") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code like '" + cond.get("orgCode") + "%'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("orgName"))){
            	sql += " and o.name like '" + cond.get("orgName") + "%'";
            }
        }else{
        	if(StringUtils.isNotEmpty(cond.get("fcCode"))){
            	sql += " and f.code = '" + cond.get("fcCode") + "'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("fcName"))){
            	sql += " and f.name = '" + cond.get("fcName") + "'";
            }
        	
        	
        	if(StringUtils.isNotEmpty(cond.get("proCode"))){
            	sql += " and p.code = '" + cond.get("proCode") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("proName"))){
            	sql += " and p.name = '" + cond.get("proName") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("orgCode"))){
            	sql += " and o.code = '" + cond.get("orgCode") + "'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("orgName"))){
            	sql += " and o.name = '" + cond.get("orgName") + "'";
            }
        	
        }
        
        sql += " order by F.CODE,P.CODE,O.CODE";
        
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        List<Map<String,Object>> vosList = HashVoUtil.hashVosToMapList(vos);
        
        return vosList;
    }
	
	/**
	 * 查询产品套装关系
	 */
	public List<Map<String,Object>> queryProductSuitRel(Map<String,String> cond) throws Exception {
        String sql = "SELECT P.CODE PCODE,P.NAME PNAME,PS.PRODUCT_NUMBER,C.CODE CCODE,C.NAME CNAME"
        	+" FROM PRODUCT_SUIT PS, PRODUCT P,PRODUCT C "
        	+" WHERE P.ID = PS.SUIT_PRODUCT_ID AND C.ID=PS.PRODUCT_ID ";
        
        if( "true".equalsIgnoreCase(cond.get("blurMatch")) ){
        	//模糊查询
        	if(StringUtils.isNotEmpty(cond.get("pCode"))){
            	sql += " and p.code like '" + cond.get("pCode") + "%'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("pName"))){
            	sql += " and p.name like '" + cond.get("pName") + "%'";
            }
        	
        	
        	if(StringUtils.isNotEmpty(cond.get("cCode"))){
            	sql += " and c.code like '" + cond.get("cCode") + "%'";
            }
        	if(StringUtils.isNotEmpty(cond.get("cName"))){
            	sql += " and c.name like '" + cond.get("cName") + "%'";
            }
        	
        }else{
        	if(StringUtils.isNotEmpty(cond.get("pCode"))){
            	sql += " and p.code = '" + cond.get("pCode") + "'";
            }
        	
        	if(StringUtils.isNotEmpty(cond.get("pName"))){
            	sql += " and p.name = '" + cond.get("pName") + "'";
            }
        	
        	
        	if(StringUtils.isNotEmpty(cond.get("cCode"))){
            	sql += " and c.code = '" + cond.get("cCode") + "'";
            }
        	if(StringUtils.isNotEmpty(cond.get("cName"))){
            	sql += " and c.name = '" + cond.get("cName") + "'";
            }
        }
        
        sql += " order by P.CODE,C.CODE";
        HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql);
        List<Map<String,Object>> vosList = HashVoUtil.hashVosToMapList(vos);
        
        return vosList;
    }
	
}
