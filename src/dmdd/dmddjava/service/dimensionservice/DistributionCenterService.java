package dmdd.dmddjava.service.dimensionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.cool.common.constant.DMOConst;
import com.cool.common.logging.CoolLogger;
import com.cool.common.util.DBUtil;
import com.cool.common.util.HashVoUtil;
import com.cool.common.vo.HashVO;
import com.cool.dbaccess.CommDMO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import dmdd.dmddjava.common.system.ServerEnvironment;
import dmdd.dmddjava.dataaccess.bizobject.BDistributionCenter;

/**
 * 分仓相关操作服务类
 * @author jerry
 * @date Aug 6, 2013
 */
public class DistributionCenterService {

	private Logger logger = CoolLogger.getLogger(this.getClass());
	
	private CommDMO dmo = new CommDMO();
	
	/**memory cache*/
	private LoadingCache<String, BDistributionCenter> dcCache = CacheBuilder
	.newBuilder().expireAfterAccess(6, TimeUnit.HOURS)
	.build(new CacheLoader<String, BDistributionCenter>() {
		@Override
		public BDistributionCenter load(String key) throws Exception {
			return qeuryDCByCode(key);
		}
	});
	
	/**
	 * 查询所有分仓
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getAllDCData() throws Exception
	{
		logger.debug("开始查询所有分仓数据:");
		List<Map<String,Object>> dcList = new ArrayList<Map<String,Object>>();
		
		try{
			String qrySql = "SELECT ID,CODE,NAME,ISCATALOG,ISVALID,DCLAYER,PARENTDCID,PATHCODE,DETAILADDRESS,VERSION,DESCRIPTION,COMMENTS FROM DISTRIBUTIONCENTER";
		
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySql);
			
			dcList.addAll(HashVoUtil.hashVosToMapList(vos));
	
			logger.debug("结束查询所有分仓数据，查询到 "+dcList.size()+"条。");
		}catch (Exception e) {
			logger.error("查询分仓数据异常:" + e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return dcList;
		
	}
	
	private void dealDcPath(BDistributionCenter _newDc){
		String pathcode = _newDc.getId()+"";
		if (_newDc.getParentdcid() == 0){
			_newDc.setParentdcid(null);
		}else{
			String parentdcid = _newDc.getParentdcid() + "";
			BDistributionCenter pDc = ServerEnvironment.getInstance().getDcMap().get(parentdcid);
			
			if(pDc != null){
				pathcode = pDc.getPathcode() + "-"+_newDc.getId();//使用ID生成PATHCODE
			}
		}
		_newDc.setPathcode(pathcode);
	}
	
	public void createDC(BDistributionCenter _newDc) throws Exception{
		logger.debug("开始新增一条分仓数据:");
		try{
			StringBuilder insertSb = new StringBuilder();
			insertSb.append("INSERT INTO DISTRIBUTIONCENTER("+DBUtil.getInsertId()+"CODE,NAME,ISCATALOG,ISVALID,DCLAYER,PARENTDCID,PATHCODE");
			insertSb.append(" ,DETAILADDRESS,DESCRIPTION,COMMENTS,VERSION)");
			insertSb.append(" VALUES("+DBUtil.getSeqValue("S_DISTRIBUTIONCENTER")+"?,?,?,?,?,?,?,?,?,?,?)");
			insertSb.append("");

			dealDcPath(_newDc);
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSb.toString(),_newDc.getCode(),_newDc.getName(),_newDc.getIscatalog()
					,_newDc.getIsvalid(),_newDc.getDclayer(),_newDc.getParentdcid(),_newDc.getPathcode()
					,_newDc.getDetailaddress(),_newDc.getDescription(),_newDc.getComments(), 1
					);
			
			dmo.commit(DMOConst.DS_DEFAULT);
			
			refreshDCCache();
			
			logger.info("新增一条DC成功["+_newDc.getName()+"]");
		}catch (Exception e) {
			dmo.rollback(DMOConst.DS_DEFAULT);
			logger.error("操作执行异常:"+ e.toString());
			e.printStackTrace();
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
	}
	
	public void updateDC(BDistributionCenter _dc) throws Exception{
		logger.debug("开始更新一条分仓数据:");
		try{
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE DISTRIBUTIONCENTER SET CODE=?,NAME=?,ISCATALOG=?,ISVALID=?,DCLAYER=?,PARENTDCID=?,PATHCODE=?");
			sb.append(" ,DETAILADDRESS=?,DESCRIPTION=?,COMMENTS=? WHERE ID=? ");
			sb.append("");

			dealDcPath(_dc);
			
			dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, sb.toString(),_dc.getCode(),_dc.getName(),_dc.getIscatalog()
					,_dc.getIsvalid(),_dc.getDclayer(),_dc.getParentdcid(),_dc.getPathcode()
					,_dc.getDetailaddress(),_dc.getDescription(),_dc.getComments(),_dc.getId()
					);
			
			dmo.commit(DMOConst.DS_DEFAULT);
			
			refreshDCCache();
			
			logger.info("更新一条DC成功["+_dc.getName()+"]");
			
		}catch (Exception e) {
			dmo.rollback(DMOConst.DS_DEFAULT);
			logger.error("操作执行异常:"+ e.toString());
			e.printStackTrace();
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
	}
	
	public String deleteDCById(String dcId) throws Exception{
		logger.debug("根据DC编码删除DC:");
		String dealResult = "删除分销中心ID=["+dcId+"]成功！";
		try{
			//DC删除前进行判断
			String refObj = "";
			boolean isRef = false;
			String sql = "SELECT 1 FROM DISTRIBUTIONCENTER DC WHERE DC.PARENTDCID = ? ";
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,dcId);
			if(vos.length>0){
				isRef = true;
				refObj = "下级DC";
			}else{
				sql = "SELECT 1 FROM ORGANIZATION O WHERE O.DISTRIBUTIONCENTERID = ? ";
				vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,dcId);
				if(vos.length>0){
					isRef = true;
					refObj = "销售组织";
				}else{
					sql = "SELECT 1 FROM REPLENISHDATA D WHERE D.DCID=? ";
					vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,dcId);
					if(vos.length>0){
						isRef = true;
						refObj = "补货数据";
					}else{
						sql = "SELECT 1 FROM REPLENISHHISDATA D WHERE D.DCID=? ";
						vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,dcId);
						if(vos.length>0){
							isRef = true;
							refObj = "历史补货数据";
						}
					}
				}
			}
			
			if(isRef){
				dealResult = "不允许删除，存在["+refObj+"]对该分销中心的引用！";
			}else{
				String delDCSql = "DELETE FROM DISTRIBUTIONCENTER WHERE ID = ?";
				dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, delDCSql, dcId);
				dmo.commit(DMOConst.DS_DEFAULT);
			}
			
			refreshDCCache();
			
		}catch (Exception e) {
			dmo.rollback(DMOConst.DS_DEFAULT);
			logger.error("操作执行异常:"+ e.toString());
			e.printStackTrace();
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		
		return dealResult;
	}
	
	public BDistributionCenter qeuryDistributionCenter(String dcCode) 	{
		try {
			return dcCache.get(dcCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据DC的编码查询DC
	 * @param dcCode
	 * @return
	 */
	public BDistributionCenter qeuryDCByCode(String dcCode) throws Exception	{
		BDistributionCenter obj = new BDistributionCenter();
		String sql = "SELECT ID,CODE,NAME,ISCATALOG,ISVALID,DCLAYER,PARENTDCID,PATHCODE,DETAILADDRESS,VERSION,DESCRIPTION,COMMENTS FROM DISTRIBUTIONCENTER where Code=?";
		try
		{
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, sql,dcCode);
			if(vos==null || vos.length<1 ||vos.length>1) //查询不到或者查询结果超过1个，都视为无效
				return null;
			else if(vos.length==1)
				obj.convert(vos[0]);
		}
		catch (Exception e) {
			logger.error("根据DC编码查询DC异常:" + e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return obj;
	}
	
	/**
	 * build dc tree
	 * @return
	 */
	public BDistributionCenter getRootDC() throws Exception
	{
		logger.info("构建DC树");
		BDistributionCenter obj = new BDistributionCenter();
		String qrySql = "SELECT ID,CODE,NAME,ISCATALOG,ISVALID,DCLAYER,PARENTDCID,PATHCODE,DETAILADDRESS,VERSION,DESCRIPTION,COMMENTS FROM DISTRIBUTIONCENTER WHERE PARENTDCID = '0'";
		try
		{
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySql);
			if(vos==null || vos.length<1 ||vos.length>1) //查询不到或者查询结果超过1个，都视为无效
				return null;
			else if(vos.length==1)
				obj.convert(vos[0]);
			obj = buildDCTree(obj);
		}
		catch (Exception e) {
			logger.error("根据DC编码查询DC异常:" + e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		logger.info("构建DC树结束");
		return obj;
	}
	
	/**
	 * 构建DC树
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private BDistributionCenter buildDCTree(BDistributionCenter obj) throws Exception
	{
		List<BDistributionCenter> subs = getSubDC(obj.getId());
		if(subs!=null && subs.size()>0)
		{
			for(BDistributionCenter obj_:subs)
			{
				obj_ = buildDCTree(obj_);
			}
			obj.setSubDCs(subs);
		}else if(obj.getIscatalog() == 1){
			obj.setSubDCs(new ArrayList<BDistributionCenter>()); //没有子节点的目录，显示为叶子节点
		}
		
		return obj;
	}
	 
	public List<BDistributionCenter> getSubDC(Long id) throws Exception
	{
		List<BDistributionCenter> list = new ArrayList<BDistributionCenter>();
		String qrySql = "SELECT ID,CODE,NAME,ISCATALOG,ISVALID,DCLAYER,PARENTDCID,PATHCODE,DETAILADDRESS,VERSION,DESCRIPTION,COMMENTS FROM DISTRIBUTIONCENTER WHERE PARENTDCID =?";
		try
		{
			HashVO[] vos = dmo.getHashVoArrayByDS(DMOConst.DS_DEFAULT, qrySql,id);
			if(vos==null || vos.length<1 ) //查询不到
				return null;
			else if(vos.length>0)
			{
				BDistributionCenter obj = null;
				for(HashVO vo:vos)
				{
					 obj = new BDistributionCenter();
					 obj.convert(vo);
					 list.add(obj);
				}
			}
		}
		catch (Exception e) {
			logger.error("根据DC编码查询DC异常:" + e.toString());
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
		return list;
	}
	

	/**
	 * DC导入，Object字段为
	 *  parentCode
	 *	dclayer
	 *  code
	 * 	name
	 * 	isCatalog
	 * 	isValid
	 *  detailAddress
	 *  description
	 *  comments
	 *  importResult --记录导入结果，成功则为BizConst.IMPORT_RESULT_SUCCESS，否则认为导入失败
	 * @return
	 */
	public List<Map<String,String>> saveDcImport(List<Map<String,String>> im_list)throws Exception
	{
		logger.info("DC数据导入开始，共["+im_list.size()+"]条数据。");
		try
		{
			StringBuilder insertSb = new StringBuilder();
			insertSb.append("INSERT INTO DISTRIBUTIONCENTER("+DBUtil.getInsertId()+"CODE,NAME,ISCATALOG,ISVALID,DCLAYER,PARENTDCID,PATHCODE");
			insertSb.append(" ,DETAILADDRESS,DESCRIPTION,COMMENTS,VERSION)");
			insertSb.append(" VALUES("+DBUtil.getSeqValue("S_DISTRIBUTIONCENTER")+" ?,?,?,?,?,?,?,?,?,?,?)");
			insertSb.append("");
			
			for(int i=0;i<im_list.size();i++){
				Map<String, String> imap = im_list.get(i);
				String parentCode = imap.get("parentCode");
				if(parentCode == null || "".equals(parentCode) ){ //顶级DC
					imap.put("importResult","父DC不允许为空!");
					continue;
				}
				
				BDistributionCenter pdc = ServerEnvironment.getInstance().getDcCodeMap().get(parentCode);
				if(pdc == null){
					imap.put("importResult","指定的parentCode["+parentCode+"]未查到数据!");
					continue;
				}
				String isCataStr = imap.get("isCatalog");
				if(isCataStr == null){
					imap.put("importResult","IsCatalog column cannot be null!");
					continue;
				}
				
				String isValidStr = imap.get("isValid");
				if(isValidStr == null){
					imap.put("importResult","IsValid column cannot be null!");
					continue;
				}
				
				Long pDclyaer = pdc.getDclayer();
				Long dclayer = pDclyaer +1;
				
				int isCata = 0;
				int isValid = 1;
				if("是".equals(isCataStr)){
					isCata = 1;
				}else{
					isCata = 0;
				}
				
				if("是".equals(isValidStr)){
					isValid = 1;
				}else{
					isValid = 0;
				}
				
				
				BDistributionCenter dc = new BDistributionCenter();
				dc.setCode(imap.get("code"));
				dc.setName(imap.get("name"));
				dc.setDclayer(dclayer);
				dc.setIscatalog(isCata );
				dc.setIsvalid(isValid);
				dc.setDetailaddress( imap.get("detailAddress"));
				dc.setDescription(imap.get("description"));
				dc.setComments(imap.get("comments"));
				
				//TODO 判断DCLAYER,isCatalog
				
				dmo.executeUpdateByDS(DMOConst.DS_DEFAULT, insertSb.toString(),dc.getCode(),dc.getName(),dc.getIscatalog()
						,dc.getIsvalid(),dc.getDclayer(),pdc.getId(),dc.getPathcode()
						,dc.getDetailaddress(),dc.getDescription(),dc.getComments(), 1
				);
				dmo.commit(DMOConst.DS_DEFAULT);
				
				imap.put("importResult","OK");
			}
			logger.info("DC数据导入结束。");
			
			refreshDCCache();
			
			return im_list;
		}
		catch (Exception e) {
			logger.error("导入DC数据时异常:" + e.toString(),e);
			throw e;
		}finally{
			dmo.releaseContext(DMOConst.DS_DEFAULT);
		}
	}
	
	/** DC数据更改后，刷新服务端缓存 */
	public void refreshDCCache() throws Exception{
		BDistributionCenter rootDc = getRootDC();
		ServerEnvironment.getInstance().setbDistributionCenterTreeRoot(rootDc);
	}
}
