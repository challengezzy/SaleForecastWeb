package dmdd.dmddjava.common.utils;

import dmdd.dmddjava.common.enums.SuitSupportType;

public class UtilBizData {

	/**
	 * 不支持true 
	 * 1
	 */
	public static boolean matchNoSuitSupport(Integer isSuitSupport) {
		return (isSuitSupport != null && 
				SuitSupportType.UnSupport.getSuitType() == isSuitSupport);
	}

	/**
	 * 支持true
	 * 2
	 */
	public static boolean matchSuitSupport(Integer isSuitSupport) {
		return (isSuitSupport != null && 
				SuitSupportType.Support.getSuitType() == isSuitSupport);
	}

}
