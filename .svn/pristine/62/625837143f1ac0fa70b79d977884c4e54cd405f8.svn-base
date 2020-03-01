package dmdd.dmddjava.common.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;

import com.cool.common.logging.CoolLogger;

import dmdd.dmddjava.common.constant.BizConst;
import dmdd.dmddjava.common.constant.ExceptionConst;
import dmdd.dmddjava.dataaccess.bizobject.BDistributionCenter;
import dmdd.dmddjava.dataaccess.bizobject.BOrganization;
import dmdd.dmddjava.dataaccess.bizobject.BOrganizationCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BProduct;
import dmdd.dmddjava.dataaccess.bizobject.BProductCharacter;
import dmdd.dmddjava.dataaccess.bizobject.BSysParam;
import dmdd.dmddjava.dataaccess.bizobject.BSysPeriod;
import dmdd.dmddjava.service.dimensionservice.OrganizationService;
import dmdd.dmddjava.service.dimensionservice.ProductService;

/**
 * 服务端环境变量，缓存所有数据
 * 
 * @author liuzhen
 */
public class ServerEnvironment {
    protected final Logger logger = CoolLogger.getLogger(getClass());
    private static ServerEnvironment theInstance = null;

    /** 系统状态 */
    private int systemStatus = BizConst.SYSTEM_STATUS_NORMAL;

    private BSysPeriod sysPeriod = null;

    /** 配置文件Xml */
    private Document configDoc = null;

    private String menuXmlStr = null;

    /** 系统参数 */
    private ConcurrentHashMap<String, BSysParam> hmapSysParam = new ConcurrentHashMap<String, BSysParam>();// 这里使用ConcurrentHashMap，这样能保证线程安全

    /** 配置文件参数 */
    private ConcurrentHashMap<String, String> chmapCfmParam = new ConcurrentHashMap<String, String>();

    /** 产品树 */
    private BProduct bProductTreeRoot = null;

    /** 产品特征树 */
    private BProductCharacter bProductCharacterTreeRoot = null;
    /** 组织树 */
    private BOrganization bOrganizationTreeRoot = null;
    /** 组织特征树 */
    private BOrganizationCharacter bOrganizationCharacterTreeRoot = null;

    /** 分销中心树 */
    private BDistributionCenter bDistributionCenterTreeRoot = null;
    /** 分销中心MAP <id,DistributionCenter> */
    private HashMap<String, BDistributionCenter> dcMap = new HashMap<String, BDistributionCenter>();
    /** 分销中心MAP <code,DistributionCenter> */
    private HashMap<String, BDistributionCenter> dcCodeMap = new HashMap<String, BDistributionCenter>();

    /** 产品树节点MAP<id, product> */
    private ConcurrentHashMap<Long, BProduct> productIdMap = new ConcurrentHashMap<Long, BProduct>();
    /** 产品树节点MAP <code, product> */
    private ConcurrentHashMap<String, BProduct> productCodeMap = new ConcurrentHashMap<String, BProduct>();
    /** 产品特征树节点 map<id,productcharacter */
    private ConcurrentHashMap<Long, BProductCharacter> productCharMap = new ConcurrentHashMap<Long, BProductCharacter>();

    /** 组织树节点 map<id,productcharacter */
    private ConcurrentHashMap<Long, BOrganization> organizationIdMap = new ConcurrentHashMap<Long, BOrganization>();
    /** 组织树节点 map<code,productcharacter */
    private ConcurrentHashMap<String, BOrganization> organizationCodeMap = new ConcurrentHashMap<String, BOrganization>();

    /** 组织特征树节点 map<id,productcharacter */
    private ConcurrentHashMap<Long, BOrganizationCharacter> organizationCharMap = new ConcurrentHashMap<Long, BOrganizationCharacter>();

    private ProductService productService = new ProductService();

    private OrganizationService organizationService = new OrganizationService();

    public synchronized static ServerEnvironment getInstance() {
        if (theInstance == null) {
            theInstance = new ServerEnvironment();
        }
        return theInstance;
    }

    public ServerEnvironment() {
        System.out.println("创建 ServerEnvironment ...");
    }

    public int getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(int _systemStatus) {
        systemStatus = _systemStatus;
    }

    public BSysPeriod getSysPeriod() {
        return sysPeriod;
    }

    public void setSysPeriod(BSysPeriod sysPeriod) {
        this.sysPeriod = sysPeriod;
    }

    public void putSysParam(BSysParam _sysParam) {
        if (_sysParam == null) {
            return;
        }

        hmapSysParam.put(_sysParam.getCode(), _sysParam);
    }

    public BSysParam getSysParam(String _sysParamCode) {
        if (_sysParamCode == null) {
            return null;
        }
        return hmapSysParam.get(_sysParamCode);
    }

    public List<BSysParam> getAllSysParams() {
        List<BSysParam> rstList = new ArrayList<BSysParam>();

        if (!(hmapSysParam.values().isEmpty())) {
            Iterator<BSysParam> itr_hmapSysParam_values = hmapSysParam.values().iterator();
            while (itr_hmapSysParam_values.hasNext()) {
                rstList.add(itr_hmapSysParam_values.next());
            }
        }

        return rstList;
    }

    public void checkSystemStatus() throws Exception {
        if (systemStatus == BizConst.SYSTEM_STATUS_EXCEPTION_ROLLINGPERIOD) {
            Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_SYSTEMSTATUS_EXCEPTION_ROLLINGPERIOD);
            Exception ex = new Exception(cause);
            throw ex;
        } else if (systemStatus == BizConst.SYSTEM_STATUS_ROLLINGPERIOD) {
            Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_SYSTEMSTATUS_ROLLINGPERIOD);
            Exception ex = new Exception(cause);
            throw ex;
        }
        if (systemStatus == BizConst.SYSTEM_STATUS_EXCEPTION_RUNNINGFORECAST) {
            Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_SYSTEMSTATUS_EXCEPTION_RUNNINGFORECAST);
            Exception ex = new Exception(cause);
            throw ex;
        } else if (systemStatus == BizConst.SYSTEM_STATUS_RUNNINGFORECAST) {
            Throwable cause = new Throwable(ExceptionConst.EXCEPTION_CAUSECODE_SYSTEMSTATUS_RUNNINGFORECAST);
            Exception ex = new Exception(cause);
            throw ex;
        }

    }
    
    /**
     * 系统是否支持套装
     * @return
     */
    public boolean isSuitSupport(){
    	String isSuitSupport = ServerEnvironment.getInstance().getConfigFileParam(BizConst.CONFIG_IS_SUIT_SUPPORT);
    	if( "true".equalsIgnoreCase(isSuitSupport) ){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 系统是否支持套装
     * @return
     */
    public int getDecimalLength(){
    	String dlStr = ServerEnvironment.getInstance().getConfigFileParam(BizConst.DECIMAL_LENGTH);
    	int decimalLength = 2;//默认2位小数
    	if(!StringUtils.isEmpty(dlStr)){
    		decimalLength = new Integer(dlStr);
    	}
    	
    	return decimalLength;
    }

    /**
     * 获取配置文件中的参数
     * 
     * @param key
     * @return 参数值
     */
    public String getConfigFileParam(String key) {
        if (!chmapCfmParam.containsKey(key)) {
            return "";
        } else {
            return chmapCfmParam.get(key);
        }
    }

    public void putConfigParam(String key, String paramValue) {
        chmapCfmParam.put(key, paramValue);
    }

    /**
     * 设置内存常驻变量 bProductTreeRoot 和 chmapBProduct
     */
    public void setBProductTreeRoot(BProduct _bProductTreeRoot) {
        bProductTreeRoot = _bProductTreeRoot;
        productIdMap.clear();
        productCodeMap.clear();
        
        long start = System.currentTimeMillis();
        putBProductTreeToHmap(_bProductTreeRoot);
        
        logger.info("刷新产品Map缓存耗时[" + (System.currentTimeMillis() -start) + "]ms");
    }

    /**
     * 获取内存常驻变量 bProductTreeRoot
     * 
     * @return
     */
    public BProduct getBProductTreeRoot() throws Exception {
        // 注意下面的顺序一定是先特征后产品，因为产品的productService.getProductTreeRoot()中会用特征树的
        if (bProductCharacterTreeRoot == null) {
            ServerEnvironment.getInstance().setBProductCharacterTreeRoot(productService.getProductCharacterTreeRoot());
        }

        if (bProductTreeRoot == null) {
            ServerEnvironment.getInstance().setBProductTreeRoot(productService.getProductTreeRoot());
        }

        return bProductTreeRoot;
    }

    /**
     * 获取内存常驻变量 bProductTreeRoot 中 _productId 的 Product
     * 
     * @param _productId
     * @return
     */
    public BProduct getBProduct(Long _productId) {
        return productIdMap.get(_productId);
    }

    public BProduct getBProduct(String _productCode) {
        return productCodeMap.get(_productCode);
    }

    public ConcurrentHashMap<Long, BProduct> getchProduct() {
        return productIdMap;
    }

    /**
     * 递归遍历 _bProductTree，把_bProductTree及其下级节点放入 chmapBProduct
     * 
     * @param _bProductTree
     */
    private void putBProductTreeToHmap(BProduct _bProductTree) {
        if (_bProductTree != null) {
            productIdMap.put(_bProductTree.getId(), _bProductTree);
            productCodeMap.put(_bProductTree.getCode(), _bProductTree);

            if (_bProductTree.getSubProducts() != null && !(_bProductTree.getSubProducts().isEmpty())) {
                Iterator<BProduct> itr_SubProducts = _bProductTree.getSubProducts().iterator();
                while (itr_SubProducts.hasNext()) {
                    putBProductTreeToHmap(itr_SubProducts.next());
                }
            }
        }
    }

    /**
     * 设置内存常驻变量 bProductCharacterTreeRoot 和 chmapBProductCharacter
     */
    public void setBProductCharacterTreeRoot(BProductCharacter _bProductCharacterTreeRoot) {
        bProductCharacterTreeRoot = _bProductCharacterTreeRoot;
        productCharMap.clear();
        putBProductCharacterTreeToHmap(_bProductCharacterTreeRoot);
    }

    public ConcurrentHashMap<Long, BProductCharacter> gethmproductcharacter() {
        return productCharMap;
    }

    /**
     * 获取内存常驻变量 bProductCharacterTreeRoot
     * 
     * @return
     */
    public BProductCharacter getBProductCharacterTreeRoot() throws Exception {
        if (bProductCharacterTreeRoot == null) {
            ServerEnvironment.getInstance().setBProductCharacterTreeRoot(productService.getProductCharacterTreeRoot());
        }

        return bProductCharacterTreeRoot;
    }

    /**
     * 获取内存常驻变量 bProductCharacterTreeRoot 中 _productId 的 ProductCharacter
     * 
     * @param _productId
     * @return
     */
    public BProductCharacter getBProductCharacter(Long _productId) {
        return productCharMap.get(_productId);
    }

    /**
     * 递归遍历 _bProductCharacterTree，把_bProductCharacterTree及其下级节点放入
     * chmapBProductCharacter
     * 
     * @param _bProductCharacterTree
     */
    private void putBProductCharacterTreeToHmap(BProductCharacter _bProductCharacterTree) {
        if (_bProductCharacterTree != null) {
            productCharMap.put(_bProductCharacterTree.getId(), _bProductCharacterTree);
            if (_bProductCharacterTree.getSubProductCharacters() != null && !(_bProductCharacterTree.getSubProductCharacters().isEmpty())) {
                Iterator<BProductCharacter> itr_SubProductCharacters = _bProductCharacterTree.getSubProductCharacters().iterator();
                while (itr_SubProductCharacters.hasNext()) {
                    putBProductCharacterTreeToHmap(itr_SubProductCharacters.next());
                }
            }
        }
    }

    // Product end

    // Organization begin
    /**
     * 设置内存常驻变量 bOrganizationTreeRoot 和 chmapBOrganization
     */
    public void setBOrganizationTreeRoot(BOrganization _bOrganizationTreeRoot) {
        bOrganizationTreeRoot = _bOrganizationTreeRoot;
        organizationIdMap.clear();
        organizationCodeMap.clear();
        putBOrganizationTreeToHmap(_bOrganizationTreeRoot);
    }

    public ConcurrentHashMap<Long, BOrganization> getChOrganization() {
        return organizationIdMap;
    }

    /**
     * 获取内存常驻变量 bOrganizationTreeRoot
     * 
     * @return
     */
    public BOrganization getBOrganizationTreeRoot() throws Exception {
        // 注意下面的顺序一定是先特征后组织，因为组织的organizationService.getOrganizationTreeRoot()中会用特征树的
        if (bOrganizationCharacterTreeRoot == null) {
            ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot(organizationService.getOrganizationCharacterTreeRoot());
        }

        if (bOrganizationTreeRoot == null) {
            ServerEnvironment.getInstance().setBOrganizationTreeRoot(organizationService.getOrganizationTreeRoot());
        }

        return bOrganizationTreeRoot;
    }

    /**
     * 获取内存常驻变量 bOrganizationTreeRoot 中 _orgId 的 Organization
     * 
     * @param _orgId
     * @return
     */
    public BOrganization getBOrganization(Long _orgId) {
        return organizationIdMap.get(_orgId);
    }

    public BOrganization getBOrganization(String _orgCode) {
        return organizationCodeMap.get(_orgCode);
    }

    /**
     * 递归遍历 _bOrganizationTree，把_bOrganizationTree及其下级节点放入 chmapBOrganization
     * 
     * @param _bOrganizationTree
     */
    private void putBOrganizationTreeToHmap(BOrganization _bOrganizationTree) {
        if (_bOrganizationTree != null) {
            organizationIdMap.put(_bOrganizationTree.getId(), _bOrganizationTree);
            organizationCodeMap.put(_bOrganizationTree.getCode(), _bOrganizationTree);
            if (_bOrganizationTree.getSubOrganizations() != null && !(_bOrganizationTree.getSubOrganizations().isEmpty())) {
                Iterator<BOrganization> itr_SubOrganizations = _bOrganizationTree.getSubOrganizations().iterator();
                while (itr_SubOrganizations.hasNext()) {
                    putBOrganizationTreeToHmap(itr_SubOrganizations.next());
                }
            }
        }
    }

    /**
     * 设置内存常驻变量 bOrganizationCharacterTreeRoot 和 chmapBOrganizationCharacter
     */
    public void setBOrganizationCharacterTreeRoot(BOrganizationCharacter _bOrganizationCharacterTreeRoot) {
        bOrganizationCharacterTreeRoot = _bOrganizationCharacterTreeRoot;
        organizationCharMap.clear();
        putBOrganizationCharacterTreeToHmap(_bOrganizationCharacterTreeRoot);
    }

    public ConcurrentHashMap<Long, BOrganizationCharacter> getCHOrganizationCharacter() {
        return organizationCharMap;
    }

    /**
     * 获取内存常驻变量 bOrganizationCharacterTreeRoot
     * 
     * @return
     */
    public BOrganizationCharacter getBOrganizationCharacterTreeRoot() throws Exception {
        if (bOrganizationCharacterTreeRoot == null) {
            ServerEnvironment.getInstance().setBOrganizationCharacterTreeRoot(organizationService.getOrganizationCharacterTreeRoot());
        }

        return bOrganizationCharacterTreeRoot;
    }

    /**
     * 获取内存常驻变量 bOrganizationCharacterTreeRoot 中 _productId 的
     * OrganizationCharacter
     * 
     * @param _productId
     * @return
     */
    public BOrganizationCharacter getBOrganizationCharacter(Long _productId) {
        return organizationCharMap.get(_productId);
    }

    /**
     * 递归遍历 _bOrganizationCharacterTree，把_bOrganizationCharacterTree及其下级节点放入
     * chmapBOrganizationCharacter
     * 
     * @param _bOrganizationCharacterTree
     */
    private void putBOrganizationCharacterTreeToHmap(BOrganizationCharacter _bOrganizationCharacterTree) {
        if (_bOrganizationCharacterTree != null) {
            organizationCharMap.put(_bOrganizationCharacterTree.getId(), _bOrganizationCharacterTree);
            if (_bOrganizationCharacterTree.getSubOrganizationCharacters() != null && !(_bOrganizationCharacterTree.getSubOrganizationCharacters().isEmpty())) {
                Iterator<BOrganizationCharacter> itr_SubOrganizationCharacters = _bOrganizationCharacterTree.getSubOrganizationCharacters().iterator();
                while (itr_SubOrganizationCharacters.hasNext()) {
                    putBOrganizationCharacterTreeToHmap(itr_SubOrganizationCharacters.next());
                }
            }
        }
    }

    // Organization end

    public BDistributionCenter getbDistributionCenterTreeRoot() {
        return bDistributionCenterTreeRoot;
    }

    public void setbDistributionCenterTreeRoot(BDistributionCenter _bDCTreeRoot) {
        this.bDistributionCenterTreeRoot = _bDCTreeRoot;

        addDcToMap(bDistributionCenterTreeRoot);
    }

    // 把DC缓存数据存储到map,递归处理
    private void addDcToMap(BDistributionCenter _dc) {
        if (_dc == null) { // 节点为空则不处理
            return;
        }

        dcMap.put(_dc.getId() + "", _dc);
        dcCodeMap.put(_dc.getCode(), _dc);

        List<BDistributionCenter> subDcList = _dc.getSubDCs();
        if (subDcList == null || subDcList.size() == 0) { // 没有下级DC
            return;
        } else {
            for (BDistributionCenter subDc : subDcList) {
                addDcToMap(subDc);
            }
        }
    }

    public HashMap<String, BDistributionCenter> getDcMap() {
        return dcMap;
    }

    public HashMap<String, BDistributionCenter> getDcCodeMap() {
        return dcCodeMap;
    }

    public Document getConfigDoc() {
        return configDoc;
    }

    public void setConfigDoc(Document configDoc) {
        this.configDoc = configDoc;
    }

    public String getMenuXmlStr() {
        return menuXmlStr;
    }

    public void setMenuXmlStr(String menuXmlStr) {
        this.menuXmlStr = menuXmlStr;
    }

}

/************************************************************************
 * $RCSfile: ServerEnvironment.java,v $ $Revision: 1.2 $ $Date: 2010/07/15
 * 14:31:24 $ $Log: ServerEnvironment.java,v $ Revision 1.2 2010/07/15 14:31:24
 * liuzhen 2010.07.15 by liuzhen Committed on the Free edition of March Hare
 * Software CVSNT Server. Upgrade to CVS Suite for more features and support:
 * http://march-hare.com/cvsnt/
 * 
 * Revision 1.1 2010/07/04 07:26:57 liuzhen 2010.07.04 by liuzhen Committed on
 * the Free edition of March Hare Software CVSNT Server. Upgrade to CVS Suite
 * for more features and support: http://march-hare.com/cvsnt/
 * 
 * Revision
 * 
 ************************************************************************/
