package cn.mooc.app.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringUtil {

	public static final String WEB_APP_ROOT_KEY = "deam.webAppRoot";// WebRoot路径KEY

	public static boolean equal(String s1, String s2) {
		// Assert.notNull(s1);
		// Assert.notNull(s2);
		if (s1 == null) {
			return false;
		}
		if (s2 == null) {
			return false;
		}
		if (s1.equalsIgnoreCase(s2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取WebRoot路径
	 * 
	 * @return WebRoot路径
	 */
	public static String getWebRootPath() {
		return System.getProperty(WEB_APP_ROOT_KEY);
	}

	public static int string2Int(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
		// if (StringUtils.isNumeric(str)) {
		// return Integer.parseInt(str);
		// }
		// return 0;
	}

	public static int string2Int(Object obj) {
		try {
			return Integer.parseInt(strnull(obj));
		} catch (Exception e) {
			return 0;
		}
		// if (StringUtils.isNumeric(strnull(obj))) {
		// return Integer.parseInt(strnull(obj));
		// }
		// return 0;
	}

	public static Integer strnull2Int(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return Integer.parseInt(strnull(obj));
		} catch (Exception e) {
			if (strnull(obj) != null && strnull(obj).equals("true")) {
				return 1;
			}
			return 0;
		}
		// if (StringUtils.isNumeric(strnull(obj))) {
		// return Integer.parseInt(strnull(obj));
		// }
		// return null;
	}

	public static Long strnull2Long(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return Long.parseLong(strnull(obj));
		} catch (Exception e) {
			return 0l;
		}
	}

	public static long string2Long(String str) {
		if (StringUtils.isNumeric(str)) {
			return Long.parseLong(str);
		}
		return 0l;
	}

	public static Long string2Long(Object obj) {
		if (StringUtils.isNumeric(strnull(obj))) {
			return Long.parseLong(strnull(obj));
		}
		return null;
	}

	public static Double strnull2Double(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return Double.parseDouble(strnull(obj));
		} catch (Exception e) {
			return null;
		}
	}

	public static double str2Double(Object obj) {
		if (obj == null) {
			return 0d;
		}
		try {
			return Double.parseDouble(strnull(obj));
		} catch (Exception e) {
			return 0d;
		}
	}

	public static Boolean string2Boolean(String str) {
		if (StringUtils.isNotEmpty(str)) {
			if ("true".equals(str) || "yes".equals(str) || "1".equals(str)) {
				return true;
			} else if ("false".equals(str) || "no".equals(str) || "0".equals(str)) {
				return false;
			}
		}
		return null;
	}

	public static boolean str2Boolean(String str) {
		try {
			if (StringUtils.isNotEmpty(str)) {
				if ("true".equals(str) || "yes".equals(str) || "1".equals(str)) {
					return true;
				} else if ("false".equals(str) || "no".equals(str) || "0".equals(str)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String strnull(Object obj) {
		if (obj == null) {
			return "";
		}
		if (StringUtils.isEmpty("" + obj)) {
			return "";
		}
		return "" + obj;
	}

	public static String strnull(Object Str, String rpt) {
		return strnull(strnull(Str), rpt);
	}

	public static String strnull(String Str, String rpt) {
		if (isNull(Str)) {
			return rpt;
		} else {
			return Str.trim();
		}
	}

	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		if (StringUtils.isEmpty("" + obj)) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object obj) {
		if (obj == null) {
			return false;
		}
		if (StringUtils.isEmpty("" + obj)) {
			return false;
		}
		return true;
	}

	public static boolean isNotEmpty(String str) {
		return str == null ? false : StringUtils.isNoneEmpty(str);
	}

	public static boolean isNotEmpty(Object str) {
		return str == null ? false : StringUtils.isNoneEmpty(str.toString());
	}

	public static int byteArrayToInt(byte[] b, int offset) {
		int value = 0;
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (b[i + offset] & 0x000000FF) << shift;
		}
		return value;
	}

	public static boolean repeatMax(String[] chks, String input, int max) {
		for (String chk : chks) {
			int hit = repeatCount(chk, input);
			if (hit >= (max - 1)) {
				return true;
			}
		}
		return false;
	}

    public static String trimEnd(String input, String suffix){
    	input = StringUtils.removeEnd(input, suffix);
    	if(StringUtils.endsWith(input, suffix)){
    		return trimEnd(input, suffix);
    	}
    	return input;
    }
    
    public static Map buildMap(Object... objects) {
        Map result = new HashMap();
        if (objects.length % 2 > 0) {
          throw new IllegalArgumentException("sql query parameter format error.");
        }
        for (int i = 0; i < objects.length; ) {
          if ((objects[i] instanceof String))
            result.put(objects[i], objects[(i + 1)]);
          else {
            throw new IllegalArgumentException("sql query parameter format error.");
          }
          i += 2;
        }
        return result;
      }
    
    public static String zeroAddzdy(Object string,int data){
		String newString = "";
		String zero = "";
		String args = strnull(string);
		int strlength = args.length();
		
		if(strlength >= data){
			newString = args.substring(0,data);  
		}else{
			int i = data - strlength;
			for (int j = 0; j < i; j++) {
				zero = zero + "0";
			}
			newString = zero + args;
		}
		return newString;
	}
    
//    public static void main(String[] args) {
//		System.out.println(trimEnd("1,2,3,4,,,4,,,,,x", ","));
//	}
	public static int repeatCount(String chk, String input) {
		// 计算重复次数，不包含第一个被比较的，例如 111111 为5个
		if (input.length() < chk.length()) {
			return 0;
		}
		char[] a_t = input.toCharArray();
		int count = 0;

		for (int i = 0; i < input.length() - chk.length(); i++) {
			StringBuffer buffer = new StringBuffer();
			for (int j = 0; j < chk.length(); j++) {
				buffer.append(a_t[i + j]);
			}
			if (buffer.toString().equals(chk)) {
				count++;
			}
		}

		return count;
	}
}
