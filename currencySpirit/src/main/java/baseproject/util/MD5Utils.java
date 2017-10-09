package baseproject.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5相关工具类,用来把字符串加密转换成32位的16进制,及MD5码
 */

public class MD5Utils {
	public static String encode(String text){
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			//加密转换
			byte[] digest = md.digest(text.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int a = b & 0xff;//取低八位, 取正
				String hexString = Integer.toHexString(a);
				if(hexString.length() == 1){
//					hexString = "0"+hexString;
					sb.append("0");
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return  "00000000000000000000000000000000";
	}
}
