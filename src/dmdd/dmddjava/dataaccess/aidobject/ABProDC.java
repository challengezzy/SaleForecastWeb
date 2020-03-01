package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

public class ABProDC implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String productId = "";
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	private String productCode = "";
	private String productName = "";
	private String parouctPathCode = "";
	public String getParouctPathCode() {
		return parouctPathCode;
	}
	public void setParouctPathCode(String parouctPathCode) {
		this.parouctPathCode = parouctPathCode;
	}
	private String dcCode = "";
	private String dcName  = "";
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDcCode() {
		return dcCode;
	}
	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}
	public String getDcName() {
		return dcName;
	}
	public void setDcName(String dcName) {
		this.dcName = dcName;
	}

}
