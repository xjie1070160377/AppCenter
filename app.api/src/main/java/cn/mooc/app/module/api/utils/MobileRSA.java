package cn.mooc.app.module.api.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MobileRSA { 
	
	@Value("${RSA.filepath}")
	private String privateFile;
	
	private String privateKeyStr;
	
	private RSAPrivateKey privateKey;
	
	@Value("${RSA.oldfilepath}")
	private String oldprivateFile;
	
	private String oldprivateKeyStr;
	
	private RSAPrivateKey oldprivateKey;
	
	@PostConstruct
	public void init(){
		try{  
			loadprivateKey();
			loadPrivateKeyByStr();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static byte[] decryptBASE64(String key) throws Exception{
		return Base64.decodeBase64(key);
	}
	
	public static String encryptBASE64(byte[] key) throws Exception{
		return new String(Base64.encodeBase64(key));
	}
	
	public void loadprivateKey() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(privateFile));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			sb.append(readLine);
		}
		br.close();
		privateKeyStr = sb.toString();
		privateKeyStr = privateKeyStr.replaceAll("-----\\w+ PRIVATE KEY-----", "").replace("\n", ""); 
		if(!StringUtils.isEmpty(oldprivateFile)){
			br = new BufferedReader(new FileReader(oldprivateFile));
			sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			oldprivateKeyStr = sb.toString();
		}
	}
	
    /**
     * 从字符串中加载私钥
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public void loadPrivateKeyByStr()  
            throws Exception {  
        try {  
            byte[] buffer = decryptBASE64(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
            
            if(!StringUtils.isEmpty(oldprivateKeyStr)){
            	buffer = decryptBASE64(oldprivateKeyStr);  
                keySpec = new PKCS8EncodedKeySpec(buffer);  
                oldprivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec); 
            }
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }  
    
    /** 
     * 私钥解密过程 
     *  
     * @param privateKey 
     *            私钥 
     * @param cipherData 
     *            密文数据 
     * @return 明文 
     * @throws Exception 
     *             解密过程中的异常信息 
     */  
    public String decrypt(String cipherData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(decryptBASE64(cipherData));  
            return new String(output);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
    }
    /**
     * 
     * @param cipherData
     * @return
     * @throws Exception
     */
    public String olddecrypt(String cipherData)  
            throws Exception {  
        if (oldprivateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, oldprivateKey);  
            byte[] output = cipher.doFinal(decryptBASE64(cipherData));  
            return new String(output);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
    }

	public String getPrivateFile() {
		return privateFile;
	}

	public void setPrivateFile(String privateFile) {
		this.privateFile = privateFile;
	}

	public String getOldprivateFile() {
		return oldprivateFile;
	}

	public void setOldprivateFile(String oldprivateFile) {
		this.oldprivateFile = oldprivateFile;
	}  
    
}
