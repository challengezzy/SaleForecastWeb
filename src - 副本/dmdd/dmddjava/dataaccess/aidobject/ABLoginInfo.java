package dmdd.dmddjava.dataaccess.aidobject;

public class ABLoginInfo {
    private String userName;
    private Long oparatorUserId;
    private int period;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getOparatorUserId() {
		return oparatorUserId;
	}
	public void setOparatorUserId(Long oparatorUserId) {
		this.oparatorUserId = oparatorUserId;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
    
}
