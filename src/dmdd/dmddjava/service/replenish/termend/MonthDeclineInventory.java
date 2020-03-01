package dmdd.dmddjava.service.replenish.termend;

import java.math.BigDecimal;
import java.util.List;

import dmdd.dmddjava.dataaccess.bizobject.BReplenishTermEnd;
import dmdd.dmddjava.dataaccess.utils.ReplenishContext;

/**
 * 按月递减
 */
public class MonthDeclineInventory extends CalculateInventory {

	protected static final int BEYOND_THE_MONTH = 3;
	
	@Override
	public void groupEexecute(ReplenishContext context) {
		List<BReplenishTermEnd> currentTermEnds = context.getCurrentTermEnds();
		int length = currentTermEnds.size();
		for (int i = 0; i < length; i++) {
			BReplenishTermEnd termEnd = currentTermEnds.get(i);
			int stockDay = termEnd.getStockDay().intValue();
			int addnum = stockDay / daysPerPeriod;
			int range = addnum;
			int modnum = stockDay % daysPerPeriod;
			if (modnum > 0) {
				range++;
			}
			int index = i + range;
			if (index < length) {
				termEnd.setTermEnd(nextAverege(currentTermEnds, i, addnum,
						modnum));
			} else {
				// 计算最后range期的总值
				BigDecimal sumTermEnd = calcEndRangeSum(currentTermEnds, BEYOND_THE_MONTH);
				termEnd.setTermEnd(lastAverege(currentTermEnds, sumTermEnd,i, addnum, modnum));
			}
		}
	}

	private String lastAverege(List<BReplenishTermEnd> currentTermEnds,
			BigDecimal sumTermEnd, int pos, int addnum, int modnum) {
		int len = currentTermEnds.size();
		BigDecimal sum = new BigDecimal(0);
		for (int i = pos + 1; i <= pos + addnum; i++) {
			if (i > len - 1) {
				sum = sum.add(sumTermEnd.divide(new BigDecimal(BEYOND_THE_MONTH),BigDecimal.ROUND_HALF_UP));
			} else {
				BigDecimal sellout = currentTermEnds.get(i).getSellout();
				if (sellout != null) {
				  sum = sum.add(sellout);
				}
			}
		}

		if (modnum > 0) {
			sum = sum.add(calcTermEndByStockDay(sumTermEnd,BEYOND_THE_MONTH,new Long(modnum)));
		}

		return sum.toString();
	}

	private String nextAverege(List<BReplenishTermEnd> currentTermEnds,
			int pos, int range, int modnum) {
		BigDecimal sum = new BigDecimal(0);
		for (int i = pos + 1; i <= pos + range; i++) {
			BigDecimal sellout = currentTermEnds.get(i).getSellout();
			if (sellout != null) {
				sum = sum.add(sellout);
			}
		}
		if (modnum > 0) {
			sum = sum.add(calcMonthAddition(
					currentTermEnds.get(pos + range + 1), modnum));
		}
		return sum.toString();
	}

	private BigDecimal calcMonthAddition(BReplenishTermEnd modTermEnd,
			int modnum) {
		return new BigDecimal(modTermEnd.getSellout().intValue()).multiply(
				new BigDecimal(modnum)).divide(new BigDecimal(daysPerPeriod),
				BigDecimal.ROUND_HALF_UP);
	}

}
