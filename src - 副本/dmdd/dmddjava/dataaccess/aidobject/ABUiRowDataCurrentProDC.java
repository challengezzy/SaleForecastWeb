package dmdd.dmddjava.dataaccess.aidobject;

import java.io.Serializable;

/**
 * 单项补货数据对象
 * @author jerry
 * @date Aug 16, 2013
 */
public class ABUiRowDataCurrentProDC extends ABUiRowDataProDCAbstract implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sellIn = "";
	private String sellOut = "";
	private String termEnd = "";
	private String termBegin = "";
	private String stockDay = "";
	
	private String computeTime="";
	
	public String getSellIn() {
		return sellIn;
	}

	public void setSellIn(String sellIn) {
		this.sellIn = sellIn;
	}

	public String getSellOut() {
		return sellOut;
	}

	public void setSellOut(String sellOut) {
		this.sellOut = sellOut;
	}

	public String getTermEnd() {
		return termEnd;
	}

	public void setTermEnd(String termEnd) {
		this.termEnd = termEnd;
	}

	public String getTermBegin() {
		return termBegin;
	}

	public void setTermBegin(String termBegin) {
		this.termBegin = termBegin;
	}

	public String getStockDay() {
		return stockDay;
	}

	public void setStockDay(String stockDay) {
		this.stockDay = stockDay;
	}

	public String getComputeTime() {
		return computeTime;
	}

	public void setComputeTime(String computeTime) {
		this.computeTime = computeTime;
	}
}
