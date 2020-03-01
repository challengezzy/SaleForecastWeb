package dmdd.dmddjava.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class ReplenishUtils {

	public static Integer getMonth(Integer period, Integer addnum) {
		if (period < 1 || period < 200000 || period > 210000) {
			return -1;
		}
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(String.valueOf(period));
		String periodString = sBuilder.toString();
		SimpleDateFormat parser = new SimpleDateFormat("yyyyMM");
		Date periodAddDate = null;
		try {
			Date periodDate = parser.parse(periodString);
			periodAddDate = DateUtils.addMonths(periodDate, addnum);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		String result = parser.format(periodAddDate);
		return Integer.parseInt(result);
	}
	
}
