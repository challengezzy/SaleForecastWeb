package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/** @author Hibernate CodeGenerator */
public class Product implements Serializable {

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
    private dmdd.dmddjava.dataaccess.dataobject.ProductLayer productLayer;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup;

    /** persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Product parentProduct;

    /** nullable persistent field */
    private dmdd.dmddjava.dataaccess.dataobject.Unit unit;

    /** persistent field */
    private Set<ProductProCharacter> productProCharacters;

    /** persistent field */
    private Set<Product> subProducts;

    private String pathCode;

    private Long baseUnitId;
    private Long unitGroupId;
    private Long unitId;
    private Long productLayerId;

    // 是否是套装
    private Integer isSuit;

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

    /** full constructor */
    public Product(String code, String name, int isCatalog, int isValid, String description, String comments, Long version,
            dmdd.dmddjava.dataaccess.dataobject.ProductLayer productLayer, dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup,
            dmdd.dmddjava.dataaccess.dataobject.Product parentProduct, dmdd.dmddjava.dataaccess.dataobject.Unit unit,
            Set<ProductProCharacter> productProCharacters, Set<Product> subProducts, String pathCode, Long baseUnitId) {
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

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    /** default constructor */
    public Product() {
    }
    
    public Product(Long id) {
        this.id = id;
    }

    public Long getBaseUnitId() {
        return baseUnitId;
    }

    public void setBaseUnitId(Long baseUnitId) {
        this.baseUnitId = baseUnitId;
    }

    /** minimal constructor */
    public Product(String code, String name, int isCatalog, int isValid, dmdd.dmddjava.dataaccess.dataobject.Product parentProduct,
            Set<ProductProCharacter> productProCharacters, Set<Product> subProducts) {
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

    public dmdd.dmddjava.dataaccess.dataobject.ProductLayer getProductLayer() {
        return this.productLayer;
    }

    public void setProductLayer(dmdd.dmddjava.dataaccess.dataobject.ProductLayer productLayer) {
        this.productLayer = productLayer;
    }

    public dmdd.dmddjava.dataaccess.dataobject.UnitGroup getUnitGroup() {
        return this.unitGroup;
    }

    public void setUnitGroup(dmdd.dmddjava.dataaccess.dataobject.UnitGroup unitGroup) {
        this.unitGroup = unitGroup;
    }

    public dmdd.dmddjava.dataaccess.dataobject.Product getParentProduct() {
        return this.parentProduct;
    }

    public void setParentProduct(dmdd.dmddjava.dataaccess.dataobject.Product parentProduct) {
        this.parentProduct = parentProduct;
    }

    public dmdd.dmddjava.dataaccess.dataobject.Unit getUnit() {
        return this.unit;
    }

    public void setUnit(dmdd.dmddjava.dataaccess.dataobject.Unit unit) {
        this.unit = unit;
    }

    public Set<ProductProCharacter> getProductProCharacters() {
        return this.productProCharacters;
    }

    public void setProductProCharacters(Set<ProductProCharacter> productProCharacters) {
        this.productProCharacters = productProCharacters;
    }

    public void addProductProCharacter(ProductProCharacter _productProCharacter) {
        if (_productProCharacter == null) {
            return;
        }
        if (this.productProCharacters == null) {
            this.productProCharacters = new HashSet<ProductProCharacter>();
        }
        _productProCharacter.setProduct(this);
        this.productProCharacters.add(_productProCharacter);
    }

    public Set<Product> getSubProducts() {
        return this.subProducts;
    }

    public void setSubProducts(Set<Product> subProducts) {
        this.subProducts = subProducts;
    }

    public void addSubProduct(Product _subProduct) {
        if (_subProduct == null) {
            return;
        }
        if (this.subProducts == null) {
            this.subProducts = new HashSet<Product>();
        }
        _subProduct.setParentProduct(this);
        this.subProducts.add(_subProduct);
    }

    public Integer getIsSuit() {
        return isSuit;
    }

    public void setIsSuit(Integer isSuit) {
        this.isSuit = isSuit;
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
        Product other = (Product) obj;
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
