package dmdd.dmddjava.common.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 订单类型
 * 
 * @author zxc
 * 
 */
public enum OrderType {

	PO("PO", "采购订单"), 
	PR("PR", "采购申请单"), 
	PA("PA", "相当于另外一部分的PO,独立于PO之外");

	private String code;
	private String desc;

	private OrderType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static boolean isOrderType(String orderType) {
		for (OrderType type : OrderType.values()) {
			if (StringUtils.equals(type.getCode(), orderType)) {
				return true;
			}
		}
		return false;
	}

}
