package cn.mooc.app.core.security;

import cn.mooc.app.core.utils.Encodes;
import cn.mooc.app.core.utils.SecureCryptoUtil;

public class SaltPwdUtils {

	public static String getRandomSalt(){
		
		String salt = SecureCryptoUtil.randomSalt();
		return salt;
	}
	
/*	public static String md5Password(String rawPassword, String salt){
		String pwd = SecureCryptoUtil.md5Password(rawPassword, salt);
		return pwd;
		
	}*/
	
	public static byte[] getRandomSaltBytes(){
		//兼容CMS加密算法
		String salt = SecureCryptoUtil.randomSalt();
		byte[] saltBytes = getSaltBytes(salt);
		return saltBytes;
	}
	
	public static byte[] getSaltBytes(String salt){		
		//兼容CMS加密算法
		byte[] saltBytes = Encodes.decodeHex(salt);
		return saltBytes;
	}
	
	
	public static String md5Password(CredentialsDigest credentialsDigest, String rawPassword, byte[] saltBytes){
		//兼容CMS加密算法
		String pwd = credentialsDigest.digest(rawPassword, saltBytes);
		return pwd;
		
	}
	
}
