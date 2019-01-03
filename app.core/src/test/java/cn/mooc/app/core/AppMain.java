package cn.mooc.app.core;

import java.io.File;

import cn.mooc.app.core.security.CredentialsDigest;
import cn.mooc.app.core.security.SHA1CredentialsDigest;
import cn.mooc.app.core.security.SaltPwdUtils;
import cn.mooc.app.core.utils.HttpUtil;
import javapns.org.json.JSONObject;

public class AppMain {

	public static void main(String[] args) {
		
		File dirFile = new File("d:/app");
		
//		System.out.println("exists:"+dirFile.exists());
//		System.out.println("mkdirs:"+dirFile.mkdirs());
		
     
       try {
		String str = HttpUtil.simpleGet("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=6222021914006122189&cardBinCheck=true");
		JSONObject json = new JSONObject(str);
		System.out.println(json.get("validated"));
       } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	
}
