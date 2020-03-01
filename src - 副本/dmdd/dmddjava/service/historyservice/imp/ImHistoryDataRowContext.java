package dmdd.dmddjava.service.historyservice.imp;

import dmdd.dmddjava.dataaccess.aidobject.ABImHistoryData;
import dmdd.dmddjava.dataaccess.dataobject.Organization;
import dmdd.dmddjava.dataaccess.dataobject.Product;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jun 17, 2017 10:18:44 PM
 */
public class ImHistoryDataRowContext {

	private Product product;
	
	private Organization organization;
	
	private ABImHistoryData abImHistoryData;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ABImHistoryData getAbImHistoryData() {
		return abImHistoryData;
	}

	public void setAbImHistoryData(ABImHistoryData abImHistoryData) {
		this.abImHistoryData = abImHistoryData;
	}
}
