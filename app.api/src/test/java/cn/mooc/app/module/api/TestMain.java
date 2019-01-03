package cn.mooc.app.module.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.alibaba.druid.util.Base64;

public class TestMain {

	public static void main(String[] args) {
		String imgs = "fdjskajfhuisehfkjasjfkashdfjaskfgajsfgajskgfjasgf";

		String str1 = Base64.byteArrayToBase64(imgs.getBytes());
		System.out.println(str1);
		str1 = "L3VwbG9hZHMvcC8yMDE4MTExNC9qcGcvYzI0MjBjMjgtODFjZi00MzQyLThkZjctMThhMTUwMTU4ZWRiLmpwZw==A";
		try {
			String str = new String(Base64.base64ToByteArray(str1));
			System.out.println(str);
		} catch (IllegalArgumentException e) {
			System.out.println("异常");
			str1 = str1.substring(0, str1.length() - 1);
			String str = new String(Base64.base64ToByteArray(str1));
			System.out.println(str);
		}

	   
	}

}
