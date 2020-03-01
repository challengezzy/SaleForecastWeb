package dmdd.dmddjava.dataaccess.utils;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import dmdd.dmddjava.common.enums.SafeStockMode;
import dmdd.dmddjava.dataaccess.bizobject.BReplenishTermEnd;

public class ReplenishContext {
	private List<BReplenishTermEnd> currentTermEnds;
	private Multimap<String,BReplenishTermEnd> termEndMap = ArrayListMultimap.create();
	private SafeStockMode type;
	private int averegeMonth;
	private int forecaseType;
	
	public Multimap<String, BReplenishTermEnd> getTermEndMap() {
		return termEndMap;
	}

	public SafeStockMode getType() {
		return type;
	}

	public void setType(SafeStockMode type) {
		this.type = type;
	}

	public int getAveregeMonth() {
		return averegeMonth;
	}

	public void setAveregeMonth(int averegeMonth) {
		this.averegeMonth = averegeMonth;
	}

	public int getForecaseType() {
		return forecaseType;
	}

	public void setForecaseType(int forecaseType) {
		this.forecaseType = forecaseType;
	}

	public List<BReplenishTermEnd> getCurrentTermEnds() {
		return currentTermEnds;
	}

	public void setCurrentTermEnds(List<BReplenishTermEnd> currentTermEnds) {
		this.currentTermEnds = currentTermEnds;
	}

	public void setTermEndMap(Multimap<String, BReplenishTermEnd> termEndMap) {
		this.termEndMap = termEndMap;
	}
	
}
