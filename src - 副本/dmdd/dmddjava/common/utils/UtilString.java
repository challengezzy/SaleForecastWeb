package dmdd.dmddjava.common.utils;

import java.util.List;

public class UtilString {
	
	/**
	 * 把IDS转换为in条件值, 如 (23,2,5)
	 * @param listId
	 * @return
	 */
	public static String getInNumberString(List<Long> listId){
		boolean isFirst = true;
		String idStr = "(";
		for(Long id : listId){
			if(isFirst){
				idStr += id;
				isFirst = false;
			}else{
				idStr += "," + id;
			}
		}
		
		idStr += ")";
		
		return idStr;
	}

}
