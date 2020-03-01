package dmdd.dmddjava.common.enums;

import java.util.Arrays;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

public enum SafeStockMode {

	Aervage(1, "N个月平均"), 
	MonthDecline(2, "逐月递减");

	private Integer type;
	private String desc;

	SafeStockMode(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public static boolean exitsMode(final Integer type) {
		return Iterators.any(Arrays.asList(SafeStockMode.values()).iterator(),
				new Predicate<SafeStockMode>() {
					@Override
					public boolean apply(SafeStockMode input) {
						return input.getType() == type;
					}
				});
	}

	public static SafeStockMode getType(final Integer type) {
		return Iterators.find(Arrays.asList(SafeStockMode.values()).iterator(),
				new Predicate<SafeStockMode>() {
					@Override
					public boolean apply(SafeStockMode input) {
						return input.getType() == type;
					}
				});
	}

}
