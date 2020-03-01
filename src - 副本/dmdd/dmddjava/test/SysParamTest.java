package dmdd.dmddjava.test;

import dmdd.dmddjava.common.utils.UtilMD5;

/**
 * 
 * @author zzy
 *
 */
public class SysParamTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
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
}
