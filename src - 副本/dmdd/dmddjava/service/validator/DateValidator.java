package dmdd.dmddjava.service.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * <p>Title: 日期相关的检查器</p>
 * <p>Description: </p>
 * <p>Company: dmdd</p> 
 * @author zzy
 * @date Jul 22, 2017 12:11:38 PM
 */
public class DateValidator {
	
	private static DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 判断是否是有效的YYYYMMDD日期格式
	 * @param str
	 * @return
	 */
	public static boolean isValidateYYYYMMDD(String str){
		try{
			formatter.setLenient(false);
			formatter.parse(str);
			
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}

}
