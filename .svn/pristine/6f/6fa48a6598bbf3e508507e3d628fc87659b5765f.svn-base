package dmdd.dmddjava.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.cool.common.logging.CoolLogger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class UtilMD5 {
	
	private static Logger logger = CoolLogger.getLogger(UtilMD5.class);
	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			logger.info("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	} 
	  
	/**
     * aes解密
     * @param sSrc
     * @param sKey
     * @throws Exception
     */
    public static byte[] Decrypt(String sSrc, String sKey) throws Exception {
	    try {
	        // 判断Key是否正确
	        if (sKey == null) {
	        	logger.info("Key为空null");
	            return null;
	        }
	        // 判断Key是否为16位
	        if (sKey.length() != 16) {
	        	logger.info("key长度不是16位");
	            return null;
	        }
	        byte[] raw = sKey.getBytes("ASCII");
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        IvParameterSpec iv = new IvParameterSpec("0102030405060708"
	                .getBytes());
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
	        //返回被加密的文件
	        byte[] result1 = cipher.update(encrypted1, 0, encrypted1.length);
	        byte[] result2 =cipher.doFinal();
	       
	        
	        return result2;
	       
	    } catch (Exception ex) {
	        System.out.println(ex.toString());
	    }
		return null;
	}
	/**
	 * aes加密
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sKey, byte values[]) throws Exception {
	    if (sKey == null) {
	        System.out.print("Key为空null");
	        return null;
	    }
	    // 判断Key是否为16位
	    if (sKey.length() != 16) {
	        System.out.print("Key长度不是16位");
	        return null;
	    }
	    byte[] raw = sKey.getBytes();
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
	    IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	  
	    byte[] encrypted = cipher.doFinal(values);
	//    return encrypted;
	    return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String value="12";
		byte[] values= value.getBytes();
		try {
			System.out.println(UtilMD5.Encrypt("dmddabcd1234admi", values));
			System.out.println(new String(UtilMD5.Decrypt("X+aPkmdr5IPN3gmRDPy+4w==", "dmddabcd1234admi")));
			System.out.println(UtilMD5.getMD5Str("dmddGenuine"));
			System.out.println(UtilMD5.getMD5Str("sa"));
			
			int a=0;
			int c=0;
			
			do{
				--c;
				a = a-1;
				float f = 0.01f;
				double d = 0.92d;
			}while( a> -1 );
			
			System.out.println("a="+a+", c="+c);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
