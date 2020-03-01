package dmdd.dmddjava.dataaccess.bizobject;

import java.io.Serializable;

public class BBreakDownRuleFinancialDefItem  implements Serializable
{
	
	public BBreakDownRuleFinancialDefItem()
	{
		
	}
	@Override
	public String toString() {
		return "BBreakDownFinancialDefItem [Proportion=" + proportion
				+ ", beginDate=" + beginDate + ", breakDownRule="
				+ breakDownRule + ", endDate=" + endDate + ", id=" + id
				+ ", period=" + period + ", version=" + version + ", weekCode="
				+ weekCode + "]";
	}

	public BBreakDownRuleFinancialDefItem(Long id, Integer period,
			Integer beginDate, Integer endDate, Integer proportion,
			Long version, String weekCode, BBreakDownRule breakDownRule) {
		super();
		this.id = id;
		this.period = period;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.proportion = proportion;
		this.version = version;
		this.weekCode = weekCode;
		this.breakDownRule = breakDownRule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((proportion == null) ? 0 : proportion.hashCode());
		result = prime * result
				+ ((beginDate == null) ? 0 : beginDate.hashCode());
		result = prime * result
				+ ((breakDownRule == null) ? 0 : breakDownRule.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((period == null) ? 0 : period.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result
				+ ((weekCode == null) ? 0 : weekCode.hashCode());
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
		BBreakDownRuleFinancialDefItem other = (BBreakDownRuleFinancialDefItem) obj;
		if (proportion == null) {
			if (other.proportion != null)
				return false;
		} else if (!proportion.equals(other.proportion))
			return false;
		if (beginDate == null) {
			if (other.beginDate != null)
				return false;
		} else if (!beginDate.equals(other.beginDate))
			return false;
		if (breakDownRule == null) {
			if (other.breakDownRule != null)
				return false;
		} else if (!breakDownRule.equals(other.breakDownRule))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (weekCode == null) {
			if (other.weekCode != null)
				return false;
		} else if (!weekCode.equals(other.weekCode))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Integer beginDate) {
		this.beginDate = beginDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public Integer getProportion() {
		return proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getWeekCode() {
		return weekCode;
	}

	public void setWeekCode(String weekCode) {
		this.weekCode = weekCode;
	}

	public dmdd.dmddjava.dataaccess.bizobject.BBreakDownRule getBreakDownRule() {
		return breakDownRule;
	}

	public void setBreakDownRule(
			dmdd.dmddjava.dataaccess.bizobject.BBreakDownRule breakDownRule) {
		this.breakDownRule = breakDownRule;
	}

	private static final long serialVersionUID = 1L;


	/** identifier field */
    private Long id;
    
  /** persistent field */
    private Integer period;

    private Integer beginDate;
    
    private Integer endDate;
    
    private Integer proportion;
	
    /** nullable persistent field */
    private Long version;
    
    private String weekCode;
    
    /** persistent field */
    private dmdd.dmddjava.dataaccess.bizobject.BBreakDownRule breakDownRule;

	
	
}
