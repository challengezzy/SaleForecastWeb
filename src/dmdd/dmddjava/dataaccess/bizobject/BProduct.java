package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.cool.common.util.StringUtil;

/** @author Hibernate CodeGenerator */
public class BProduct implements Serializable {

    public final static long serialVersionUID = -1160000002;

    /** identifier field */
    private Long id;

    /** persistent field */
    private String code;

    /** persistent field */
    private String name;

    /** persistent field */
    private int isCatalog;

    /** persistent field */
    private int isValid;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProductLayer productLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BUnitGroup unitGroup;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BProduct parentProduct;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BUnit unit;

    /** persistent field */
    private Set<BProductProCharacter> productProCharacters;

    /** persistent field */
    private Set<BProduct> subProducts;

    private String pathCode;
    private Long unitGroupId;
    private Long unitId;
    private Long productLayerId;

    // 是否是套装
    private Integer isSuit;
    
    // 套装产品详情 ， 结构为code:number|code:number|code:number
    private Set<BSuitSkuRel> suitSkus;

    // 过期日期
    private Integer shelfLife;

    // 提前采购日期
    private Integer purchaseLeadTime;

    // 提前下架日期
    private Integer withdrawLeadTime;

    public Long getUnitGroupId() {
        return unitGroupId;
    }

    public void setUnitGroupId(Long unitGroupId) {
        this.unitGroupId = unitGroupId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getProductLayerId() {
        return productLayerId;
    }

    public void setProductLayerId(Long productLayerId) {
        this.productLayerId = productLayerId;
    }

    private Long baseUnitId;

    /** full constructor */
    public BProduct(String code, String name, int isCatalog, int isValid, String description, String comments, Long version,
            dmdd.dmddjava.dataaccess.bizobject.BProductLayer productLayer, dmdd.dmddjava.dataaccess.bizobject.BUnitGroup unitGroup,
            dmdd.dmddjava.dataaccess.bizobject.BProduct parentProduct, dmdd.dmddjava.dataaccess.bizobject.BUnit unit,
            Set<BProductProCharacter> productProCharacters, Set<BProduct> subProducts, String pathCode, Long baseUnitId) {
        this.code = code;
        this.name = name;
        this.isCatalog = isCatalog;
        this.isValid = isValid;
        this.description = description;
        this.comments = comments;
        this.version = version;
        this.productLayer = productLayer;
        this.unitGroup = unitGroup;
        this.parentProduct = parentProduct;
        this.unit = unit;
        this.productProCharacters = productProCharacters;
        this.subProducts = subProducts;
        this.pathCode = pathCode;
        this.baseUnitId = baseUnitId;
    }

    public Long getBaseUnitId() {
        return baseUnitId;
    }

    public void setBaseUnitId(Long baseUnitId) {
        this.baseUnitId = baseUnitId;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    /** default constructor */
    public BProduct() {
    }
    
    /** minimal constructor */
    public BProduct(String code, String name, int isCatalog, int isValid, dmdd.dmddjava.dataaccess.bizobject.BProduct parentProduct,
            Set<BProductProCharacter> productProCharacters, Set<BProduct> subProducts) {
        this.code = code;
        this.name = name;
        this.isCatalog = isCatalog;
        this.isValid = isValid;
        this.parentProduct = parentProduct;
        this.productProCharacters = productProCharacters;
        this.subProducts = subProducts;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsCatalog() {
        return this.isCatalog;
    }

    public void setIsCatalog(int isCatalog) {
        this.isCatalog = isCatalog;
    }

    public int getIsValid() {
        return this.isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BProductLayer getProductLayer() {
        return this.productLayer;
    }

    public void setProductLayer(dmdd.dmddjava.dataaccess.bizobject.BProductLayer productLayer) {
        this.productLayer = productLayer;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BUnitGroup getUnitGroup() {
        return this.unitGroup;
    }

    public void setUnitGroup(dmdd.dmddjava.dataaccess.bizobject.BUnitGroup unitGroup) {
        this.unitGroup = unitGroup;
    }

    public Integer getIsSuit() {
        return isSuit;
    }

    public void setIsSuit(Integer isSuit) {
        this.isSuit = isSuit;
    }
    
    public Set<BSuitSkuRel> getSuitSkus() {
        return suitSkus;
    }

    public void setSuitSkus(Set<BSuitSkuRel> suitSkus) {
        this.suitSkus = suitSkus;
    }

    public Integer getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Integer getPurchaseLeadTime() {
        return purchaseLeadTime;
    }

    public void setPurchaseLeadTime(Integer purchaseLeadTime) {
        this.purchaseLeadTime = purchaseLeadTime;
    }

    public Integer getWithdrawLeadTime() {
        return withdrawLeadTime;
    }

    public void setWithdrawLeadTime(Integer withdrawLeadTime) {
        this.withdrawLeadTime = withdrawLeadTime;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BProduct getParentProduct() {
        return this.parentProduct;
    }

    public void setParentProduct(dmdd.dmddjava.dataaccess.bizobject.BProduct parentProduct) {
        this.parentProduct = parentProduct;
    }

    public dmdd.dmddjava.dataaccess.bizobject.BUnit getUnit() {
        return this.unit;
    }

    public void setUnit(dmdd.dmddjava.dataaccess.bizobject.BUnit unit) {
        this.unit = unit;
    }

    public Set<BProductProCharacter> getProductProCharacters() {
        return this.productProCharacters;
    }

    public void setProductProCharacters(Set<BProductProCharacter> productProCharacters) {
        this.productProCharacters = productProCharacters;
    }

    public void addProductProCharacters(BProductProCharacter _productProCharacter) {
        if (_productProCharacter == null) {
            return;
        }
        if (this.productProCharacters == null) {
            this.productProCharacters = new HashSet<BProductProCharacter>();
        }
        _productProCharacter.setProduct(this);
        this.productProCharacters.add(_productProCharacter);
    }

    public Set<BProduct> getSubProducts() {
        return this.subProducts;
    }

    public void setSubProducts(Set<BProduct> subProducts) {
        this.subProducts = subProducts;
    }

    public void addSubProducts(BProduct _subProduct) {
        if (_subProduct == null) {
            return;
        }
        if (this.subProducts == null) {
            this.subProducts = new HashSet<BProduct>();
        }
        _subProduct.setParentProduct(this);
        this.subProducts.add(_subProduct);
    }

    public String toString() {
        return "" + this.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BProduct other = (BProduct) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

}
