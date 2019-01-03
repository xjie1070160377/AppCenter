package cn.mooc.app.module.api.utils;

import java.io.IOException;

import com.alibaba.druid.util.Base64;


public class Base64Utils {
	
	public static String encode(String msg) {
		return Base64.byteArrayToBase64(msg.getBytes());
		
	}
	
	public static String decode(String msg) {
		return 	new String (Base64.base64ToByteArray(msg));
	}

}
