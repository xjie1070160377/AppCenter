package cn.mooc.app.core.utils;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

public class SecureCryptoUtil {

	
    /** 
     * base64进制加密 
     * 
     * @param password 
     * @return 
     */ 
    public static String encrytBase64(String password) { 
        byte[] bytes = password.getBytes(); 
        return org.apache.shiro.codec.Base64.encodeToString(bytes); 
    } 
    /** 
     * base64进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptBase64(String cipherText) { 
        return org.apache.shiro.codec.Base64.decodeToString(cipherText); 
    } 
    /** 
     * 16进制加密 
     * 
     * @param password 
     * @return 
     */ 
    public static String encrytHex(String password) { 
        byte[] bytes = password.getBytes(); 
        return Hex.encodeToString(bytes); 
    } 
    /** 
     * 16进制解密 
     * @param cipherText 
     * @return 
     */ 
    public static String decryptHex(String cipherText) { 
        return new String(Hex.decode(cipherText)); 
    } 
    
    public static String randomSalt(){
    	SecureRandomNumberGenerator secureRandomNumberGenerator=new SecureRandomNumberGenerator(); 
        String salt = secureRandomNumberGenerator.nextBytes(8).toHex();
        return salt;
    }
    
    public static String md5Password(String password, String salt, int hashIterations){
    	//pwd = new Md5Hash(password, salt, hashIterations).toBase64();
		String pwd = new Md5Hash(password, salt, hashIterations).toHex();
    	
    	return pwd;
    }
    
    public static String md5Password(String password, String salt){
    	
    	return md5Password(password, salt, 1);
    }
    
    public static String md5Password(String password){
    	
    	return new Md5Hash(password).toHex();
    }
    
    public static void main(String[] args) {
    	// 17e3a1e15091a6633e5a7ad25fc891dd
		System.out.println(md5Password("admin", "cc7820730a133172"));
		System.out.println(randomSalt());
		System.out.println(md5Password("admin"));
		System.out.println(md5Password("test", "83dfcc9e23845928"));
		System.out.println(decryptBase64("hI+ejIyIkI2bws7NzMvKydPflpvCzMfJycmC"));
	}
    
}
