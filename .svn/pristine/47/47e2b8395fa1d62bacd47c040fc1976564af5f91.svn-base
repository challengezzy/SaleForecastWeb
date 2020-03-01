/**
 * 
 */
package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author liuzhen
 * 
 */
public class ABImProduct implements Serializable {

    public final static long serialVersionUID = -1010000022;

    private String parentCode = null;
    private String layerValue = null;
    private String unitGroupCode = null;
    private String unitCode = null;

    private String code = null;
    private String name = null;
    private String isCatalog = null;
    private String isValid = null;
    private String description = null;
    private String comments = null;
    // 是否是套装
    private String isSuit;

    // 套装产品详情 ， 结构为code:number|code:number|code:number
    private String suitDetail;

    // 过期日期
    private String shelfLife;

    // 提前采购日期
    private String purchaseLeadTime;

    // 提前下架日期
    private String withdrawLeadTime;

    private String importResult = null;

    /**
	 * 
	 */
    public ABImProduct() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

    /**
     * @return the parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCode
     *            the parentCode to set
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * @return the layerValue
     */
    public String getLayerValue() {
        return layerValue;
    }

    /**
     * @param layerValue
     *            the layerValue to set
     */
    public void setLayerValue(String layerValue) {
        this.layerValue = layerValue;
    }

    /**
     * @return the unitGroupCode
     */
    public String getUnitGroupCode() {
        return unitGroupCode;
    }

    /**
     * @param unitGroupCode
     *            the unitGroupCode to set
     */
    public void setUnitGroupCode(String unitGroupCode) {
        this.unitGroupCode = unitGroupCode;
    }

    /**
     * @return the unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * @param unitCode
     *            the unitCode to set
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the isCatalog
     */
    public String getIsCatalog() {
        return isCatalog;
    }

    /**
     * @param isCatalog
     *            the isCatalog to set
     */
    public void setIsCatalog(String isCatalog) {
        this.isCatalog = isCatalog;
    }

    /**
     * @return the isValid
     */
    public String getIsValid() {
        return isValid;
    }

    /**
     * @param isValid
     *            the isValid to set
     */
    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the importResult
     */
    public String getImportResult() {
        return importResult;
    }

    /**
     * @param importResult
     *            the importResult to set
     */
    public void setImportResult(String importResult) {
        this.importResult = importResult;
    }

    public String getIsSuit() {
        return StringUtils.trim(isSuit);
    }

    public void setIsSuit(String isSuit) {
        this.isSuit = isSuit;
    }

    public void setSuitDetail(String suitDetail) {
        this.suitDetail = suitDetail;
    }
    
    public String getSuitDetail() {
		return suitDetail;
	}

	public Map<String, Integer> getSuitDetailMap() {
        Map<String, Integer> detailMap = new HashMap<String, Integer>();
        for (String detail : StringUtils.split(StringUtils.trim(suitDetail), '|')) {
            String[] detailArray = StringUtils.split(StringUtils.trim(detail), ':');
            if (detailArray.length != 2) {
                throw new RuntimeException("invalid suit detail");
            }
            detailMap.put(StringUtils.trim(detailArray[0]), Integer.parseInt(StringUtils.trim(detailArray[1])));
        }

        return detailMap;
    }

    public String getShelfLife() {
        return StringUtils.trim(shelfLife);
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public String getPurchaseLeadTime() {
        return StringUtils.trim(purchaseLeadTime);
    }

    public void setPurchaseLeadTime(String purchaseLeadTime) {
        this.purchaseLeadTime = purchaseLeadTime;
    }

    public String getWithdrawLeadTime() {
        return StringUtils.trim(withdrawLeadTime);
    }

    public void setWithdrawLeadTime(String withdrawLeadTime) {
        this.withdrawLeadTime = withdrawLeadTime;
    }
}
