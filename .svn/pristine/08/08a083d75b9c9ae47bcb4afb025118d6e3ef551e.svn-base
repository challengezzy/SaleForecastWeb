/**
 * ProductSuit.java
 * dmdd.dmddjava.dataaccess.dataobject
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2017年4月24日 		sophia
 *
 * Copyright (c) 2017, Howbuy Rights Reserved.
*/

package dmdd.dmddjava.dataaccess.dataobject;
/**
 * ClassName:ProductSuit
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   leslie
 * @version  
 * @since    Ver 1.1
 * @Date	 2017年4月24日		上午10:16:37
 *
 * @see 	 
 */
public class ProductSuit {
    
    private long id;
    
    private long version;
    
    private long suitProductId;
    
    private long productId;
    
    private int productNumber;
    
    public ProductSuit() {
    }
    
    public ProductSuit(long id, long version, long suitProductId, long productId, int productNumber) {
        super();
        this.id = id;
        this.suitProductId = suitProductId;
        this.productId = productId;
        this.productNumber = productNumber;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public long getVersion() {
        return version;
    }
    
    public void setVersion(long version) {
        this.version = version;
    }

    public long getSuitProductId() {
        return suitProductId;
    }

    public void setSuitProductId(long suitProductId) {
        this.suitProductId = suitProductId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductSuit other = (ProductSuit) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ProductSuit [id=" + id + ", version=" + version + ", suitProductId=" + suitProductId + ", productId=" + productId + ", productNumber="
                + productNumber + "]";
    }
}

