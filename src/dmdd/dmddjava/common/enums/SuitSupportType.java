package dmdd.dmddjava.common.enums;

/**
 * 支持折和计算
 * @author zxc
 * 
 */
public enum SuitSupportType {
    
	Support(2),//支持
    UnSupport(1);//不支持
	
	private SuitSupportType(int suitType) {
		this.suitType = suitType;
	}

	private int suitType;


	public int getSuitType() {
		return suitType;
	}

	public void setSuitType(int suitType) {
		this.suitType = suitType;
	}
	
}
