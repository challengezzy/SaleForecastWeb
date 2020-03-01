package dmdd.dmddjava.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dmdd.dmddjava.common.utils.UtilMD5;

/**
 * 
 * @author zzy
 *
 */
public class SysParamTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args)	{
		int  aa = getPeriod(2018, -1);
		int  bb = getPeriod(201801, -1);
		
		System.out.println("aa="+aa+",bb=" + bb);
		
		
		
		String value="16";
		byte[] values= value.getBytes();
		try {
			
			//加密
			System.out.println(UtilMD5.Encrypt("dmddabcd1234admi", values));
			System.out.println(new String(UtilMD5.Decrypt("MW6hEGzgGa4c1sqz3AJSew==", "dmddabcd1234admi")));
			System.out.println(UtilMD5.getMD5Str("31dmdd"));
			System.out.println(UtilMD5.getMD5Str("sa"));
			
			//解密
			String value_str=(new String(UtilMD5.Decrypt( "0fc412c1709e11e143aebf3b58befd39342940329", "dmddabcd1234admi")));
			System.out.println(value_str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static int getPeriod(int _originPeriod, int _addPeriodNum)
	{
		int year = _originPeriod/100;
		int pno = _originPeriod-year*100;
		
		int year_rst = year;
		int pno_rst = pno + _addPeriodNum;
		int periodNumPerYear = 12;
		
		while( pno_rst > periodNumPerYear || pno_rst < 1 )
		{
			if( pno_rst > periodNumPerYear )
			{
				pno_rst = pno_rst - periodNumPerYear;
				year_rst = year_rst + 1;
			}
			else if( pno_rst < 1 )
			{
				pno_rst = pno_rst + periodNumPerYear;
				year_rst = year_rst - 1;				
			}
		}
		
		int period_rst = year_rst * 100 + pno_rst;

		return period_rst;
	}
}
