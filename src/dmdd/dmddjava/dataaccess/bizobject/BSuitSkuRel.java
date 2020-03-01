package dmdd.dmddjava.dataaccess.bizobject;

public class BSuitSkuRel {
	
    private Long suitProductId;
    
	private Long proId;
	
	private String proCode;
	
	private String proName;
	
	private int ratio; //比例

	public Long getProId() {
		return proId;
	}

	public void setSuitProductId(Long suitProductId) {
	    this.suitProductId = suitProductId;
	}
	
	public Long getSuitProductId() {
	    return this.suitProductId;
	}
	
	public void setProId(Long proId) {
		this.proId = proId;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	
	

}
