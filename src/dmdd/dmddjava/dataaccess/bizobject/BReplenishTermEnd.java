package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class BReplenishTermEnd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7091995144337933907L;
	private Long productId;
	private Long dcid;
	private int period;
	private BigDecimal sellout;
	private String termEnd;
	private Long stockDay;

	public Long getStockDay() {
		return stockDay;
	}

	public BReplenishTermEnd setStockDay(Long stockDay) {
		this.stockDay = stockDay;
		return this;
	}

	public BReplenishTermEnd setProductId(Long productId) {
		this.productId = productId;
		return this;
	}

	public BReplenishTermEnd setDcid(Long dcid) {
		this.dcid = dcid;
		return this;
	}

	public BReplenishTermEnd setPeriod(int period) {
		this.period = period;
		return this;
	}

	public BReplenishTermEnd setSellout(BigDecimal sellout) {
		this.sellout = sellout;
		return this;
	}

	public BReplenishTermEnd setTermEnd(String termEnd) {
		this.termEnd = termEnd;
		return this;
	}

	public Long getDcid() {
		return dcid;
	}

	public String getTermEnd() {
		return termEnd;
	}

	public BigDecimal getSellout() {
		return sellout;
	}

	public int getPeriod() {
		return period;
	}

	public Long getProductId() {
		return productId;
	}
}
