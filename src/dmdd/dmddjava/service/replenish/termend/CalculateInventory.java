package dmdd.dmddjava.service.replenish.termend;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import dmdd.dmddjava.dataaccess.bizobject.BReplenishTermEnd;
import dmdd.dmddjava.dataaccess.utils.ReplenishContext;

public abstract class CalculateInventory {

	protected static final int daysPerPeriod = 30;

	/** 期末库存计算 */
	abstract void groupEexecute(ReplenishContext context);

	public void execute(ReplenishContext context) {
		Multimap<String, BReplenishTermEnd> termEndMaps = context
				.getTermEndMap();
		List<String> listKeys = Lists.newArrayList(termEndMaps.keySet());
		for (String key : listKeys) {
			List<BReplenishTermEnd> termEndList = (List<BReplenishTermEnd>) termEndMaps
					.get(key);
			Collections.sort(termEndList, new Comparator<BReplenishTermEnd>() {
				@Override
				public int compare(BReplenishTermEnd o1, BReplenishTermEnd o2) {
					return o1.getPeriod() - o2.getPeriod();
				}
			});
			context.setCurrentTermEnds(termEndList);
			groupEexecute(context);
		}
	}

	protected BigDecimal calcEndRangeSum(List<BReplenishTermEnd> currentTermEnds,
			int range) {
		int len = currentTermEnds.size();
		BigDecimal sum = new BigDecimal(0);
		for (int i = len - 1; i > 0 && i >= len - range; i--) {
			BigDecimal sellout = currentTermEnds.get(i).getSellout();
			if (sellout != null) {
				sum = sum.add(sellout);
			}
		}
		return sum;
	}

	protected BigDecimal calcTermEndByStockDay(BigDecimal sum, int range,
			Long stockDay) {
		return sum.divide(new BigDecimal(range), BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal(stockDay))
				.divide(new BigDecimal(daysPerPeriod), BigDecimal.ROUND_HALF_UP);
	}

}
