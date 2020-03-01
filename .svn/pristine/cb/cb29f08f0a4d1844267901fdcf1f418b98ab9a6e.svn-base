package dmdd.dmddjava.service.replenish.termend;

import java.util.HashMap;
import java.util.Map;
import dmdd.dmddjava.common.enums.SafeStockMode;
import dmdd.dmddjava.dataaccess.utils.ReplenishContext;

public class CalculateInventoryFactory {

	private Map<SafeStockMode, CalculateInventory> inventoryStockMap = new HashMap<SafeStockMode, CalculateInventory>();

	private CalculateInventoryFactory() {
		inventoryStockMap
				.put(SafeStockMode.Aervage, new AervageInventoryImpl());
		inventoryStockMap.put(SafeStockMode.MonthDecline,
				new MonthDeclineInventory());
	}

	public void doCalculateInventory(
			ReplenishContext context) {
		inventoryStockMap.get(context.getType()).execute(context);
	}

	private static class CalculateInventoryInstance {
		private static final CalculateInventoryFactory factory = new CalculateInventoryFactory();
	}

	public static CalculateInventoryFactory getInstance() {
        return CalculateInventoryInstance.factory;
	}
	
}
