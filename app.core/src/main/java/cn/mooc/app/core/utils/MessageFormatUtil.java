package cn.mooc.app.core.utils;

import java.text.MessageFormat;

import org.slf4j.helpers.MessageFormatter;

public class MessageFormatUtil {

	
	/**
	 * 
	 * MessageFormatUtil.format("第1个{} 第2个{} 第3个{} 第4个{}", 1, 2, "3", "4");
	 * @param messagePattern
	 * @param argArray
	 * @return
	 */
	public static String format(String messagePattern, Object... argArray){
		
		return MessageFormatter.arrayFormat(messagePattern, argArray).getMessage();
		
	}
	
	/**
	 * <pre>
	 * MessageFormat.format("第1个{0} 第2个{1} 第3个{2}", 1, 2, 3);
	 * 
	 * MessageFormat.format("第1个{0} 第2个{1} 第1个{0}", 1, 2);
	 * </pre>
	 * @param messagePattern
	 * @param argArray
	 * @return
	 */
	public static String formatByNum(String messagePattern, Object... argArray){
		
		return MessageFormat.format(messagePattern, argArray);
		
	}
	
	/**
	 * format("%04d", 25) == 0025
	 * 
	 * @param num
	 * @param bit
	 * @return
	 */
	public static String format(Integer num, int bit){
		//
		return String.format("%0" + bit + "d", num);
	}
	
	/**
	 * format("%.2f", 25.464)	 == 25.46
	 * 
	 * @param num
	 * @param bit
	 * @return
	 */
	public static String format(Double num, int bit){
		//	
		return String.format("%." + bit + "f", num);
	}
	
	
}
