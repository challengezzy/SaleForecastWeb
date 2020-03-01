package dmdd.dmddjava.service.replenish.termend;

import java.math.BigDecimal;
import java.util.List;
import dmdd.dmddjava.dataaccess.bizobject.BReplenishTermEnd;
import dmdd.dmddjava.dataaccess.utils.ReplenishContext;

/**
 * N个月平均
 **/
public class AervageInventoryImpl extends CalculateInventory {

	@Override
	public void groupEexecute(ReplenishContext context) {

		int range = context.getAveregeMonth();
		List<BReplenishTermEnd> currentTermEnds = context.getCurrentTermEnds();
		int length = currentTermEnds.size();

		for (int i = 0; i < length; i++) {
			int index = i + range;
			BReplenishTermEnd termEnd = currentTermEnds.get(i);
			if (index < length) {
				termEnd.setTermEnd(nextAverege(termEnd,currentTermEnds, i, range));
			} else {
				termEnd.setTermEnd(lastAverege(termEnd,currentTermEnds, range));
			}
		}

	}

	private String lastAverege(BReplenishTermEnd termEnd,List<BReplenishTermEnd> currentTermEnds,
			int range) {
		BigDecimal aervageBigDecimal = new BigDecimal(0);
		BigDecimal sum = calcEndRangeSum(currentTermEnds,range);
		if (sum.compareTo(new BigDecimal(0)) > 0) {
			aervageBigDecimal =calcTermEndByStockDay(sum,range,termEnd.getStockDay());
		}
		return aervageBigDecimal.toPlainString();
	}

	private String nextAverege(BReplenishTermEnd termEnd,List<BReplenishTermEnd> currentTermEnds,
			int pos, int range) {
		BigDecimal aervageBigDecimal = new BigDecimal(0);
		BigDecimal sum = new BigDecimal(0);
		for (int i = pos + 1; i <= pos + range; i++) {
			BigDecimal sellout = currentTermEnds.get(i).getSellout();
			if (sellout != null) {
				sum = sum.add(sellout);
			}
		}
		if (sum.compareTo(new BigDecimal(0)) > 0) {
			aervageBigDecimal = calcTermEndByStockDay(sum,range,termEnd.getStockDay());
		}
		return aervageBigDecimal.toString();
	}

	
}
