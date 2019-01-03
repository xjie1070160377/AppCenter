package cn.mooc.app.core.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.beetl.core.Context;
import org.springframework.data.domain.Sort.Direction;

import cn.mooc.app.core.web.model.PagerParam;



public class Beetls {

	public static final String PAGE = "page";
	public static final String PAGESIZE = "pageSize";
	private static final String SIDX = "sidx";
	private static final String SORD = "sord";
	/**
	 * 分隔符
	 */
	public static final String SPLIT = ",";

	public static Integer getInteger(Map params, String name) {
		return StringUtil.strnull2Int(params.get(name));
	}

	public static String getString(Map params, String name) {
		return StringUtil.strnull(params.get(name));
	}

	public static String getString(Map params, String name, String defaultValue) {
		String value = StringUtil.strnull(params.get(name));
		if(value == null){
			return defaultValue;
		}
		return value;
	}

	public static Boolean getBoolean(Map<String, Object> params, String name) {
		Object value = params.get(name);
		if(value == null){
			return null;
		}try {
			Boolean result = (Boolean)value;
			return result;
		} catch (Exception e) {
			if ("true".equals(value) || "yes".equals(value) || "1".equals(value)) {
				return true;
			} else if ("false".equals(value) || "no".equals(value) || "0".equals(value)) {
				return false;
			}
		}
		
		return null;
	}

	public static boolean getBoolean(Map<String, Object> params, String name, boolean defaultValue) {
		Boolean value = getBoolean(params, name);
		if(value == null){
			return false;
		}
		return value;
	}

	public static Integer[] getIntegers(Map<String, Object> params, String name) {
		Object value = params.get(name);
		if(value instanceof List){
			List<Object> list = (List<Object>)value;
			Integer[] result = list.toArray(new Integer[list.size()]);
			return result;
		}else if (value instanceof Integer){
			Integer result = StringUtil.strnull2Int(value);
			Integer[] numberArray = new Integer[1];
			numberArray[0] = result;
			return numberArray;
		}else if (value instanceof String) {
			String text = getString(params, name);
			if (text == null) {
				return null;
			} else if (StringUtils.isBlank(text)) {
				return new Integer[0];
			}
			String[] stringArray = StringUtils.split(text, SPLIT);
			int length = stringArray.length;
			Integer[] numberArray = new Integer[length];
			try {
				for (int i = 0; i < length; i++) {
					numberArray[i] = Integer.valueOf(stringArray[i]);
				}
				return numberArray;
			} catch (NumberFormatException e) {
				
			}
		}
		
		return null;
	}
	

	public static Long[] getLongs(Map<String, Object> params, String name) {
		Object value = params.get(name);
		if(value instanceof List){
			List<Object> list = (List<Object>)value;
			Long[] result = new Long[list.size()];
			int index = 0;
			for(Object obj : list){
				result[index] = StringUtil.string2Long(obj);
				index++;
			}
//			Long[] result = list.toArray(new Long[list.size()]);
			return result;
		}else if (value instanceof Long){
			Long result = StringUtil.string2Long(value);
			Long[] numberArray = new Long[1];
			numberArray[0] = result;
			return numberArray;
		}else if (value instanceof String) {
			String text = getString(params, name);
			if (text == null) {
				return null;
			} else if (StringUtils.isBlank(text)) {
				return new Long[0];
			}
			String[] stringArray = StringUtils.split(text, SPLIT);
			int length = stringArray.length;
			Long[] numberArray = new Long[length];
			try {
				for (int i = 0; i < length; i++) {
					numberArray[i] = Long.valueOf(stringArray[i]);
				}
				return numberArray;
			} catch (NumberFormatException e) {
				
			}
		}
		
		return null;
	}

	public static String[] getStrings(Map<String, Object> params, String name) {
		Object value = params.get(name);
		if(value instanceof List){
			List<Object> list = (List<Object>)value;
			String[] result = list.toArray(new String[list.size()]);
			return result;
		}else if (value instanceof Integer){
			String result = StringUtil.strnull(value);
			String[] numberArray = new String[1];
			numberArray[0] = result;
			return numberArray;
		}else if (value instanceof String) {
			String text = getString(params, name);
			if (text == null) {
				return null;
			} else if (StringUtils.isBlank(text)) {
				return new String[0];
			}
			String[] stringArray = StringUtils.split(text, SPLIT);
			int length = stringArray.length;
			String[] numberArray = new String[length];
			try {
				for (int i = 0; i < length; i++) {
					numberArray[i] = String.valueOf(stringArray[i]);
				}
				return numberArray;
			} catch (Exception e) {
				
			}
		}
		return null;
	}
	public static PagerParam getPageable(Map<String, Object> params, Context ctx, String defSidx, Direction asc) {
		return getPageable(params, ctx, new String[] { defSidx }, asc);
	}

	public static PagerParam getPageable(Map<String, Object> params, Context ctx, String[] defSidx, Direction asc) {
		Integer page = StringUtil.strnull2Int(ctx.getGlobal("page"));
		Integer pageSize = getInteger(params, PAGESIZE);
		if(page == null){
			page = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		String[] sidx = getStrings(params, SIDX);
		String sord = getString(params, SORD);
		if(StringUtil.isNull(sidx)){
			sidx = defSidx;
		}
		if(StringUtil.isNull(sord)){
			sord = asc.toString();
		}
		return new PagerParam(page, pageSize, sidx, sord);
	}
	public static PagerParam getLimitable(Map<String, Object> params, Context ctx, String defSidx, Direction asc) {
		return getLimitable(params, ctx, new String[] { defSidx }, asc);
	}
	public static PagerParam getLimitable(Map<String, Object> params, Context ctx, String[] defSidx, Direction asc) {
		Integer pageSize = getInteger(params, PAGESIZE);
		if (pageSize == null) {
			pageSize = 0;
		}
		String[] sidx = getStrings(params, SIDX);
		String sord = getString(params, SORD);
		if(StringUtil.isNull(sidx)){
			sidx = defSidx;
		}
		if(StringUtil.isNull(sord)){
			sord = asc.toString();
		}
		return new PagerParam(1, pageSize, sidx, sord);
	}
	public static Date getDate(Map<String, Object> params, String name) {
		// TODO Auto-generated method stub
		Object value = params.get(name);
		if (value instanceof String) {
			try {
				return DateUtils.parseDate(StringUtil.strnull(value), "yyyy-MM-dd");
			} catch (ParseException e) {
				try {
					return DateUtils.parseDate(StringUtil.strnull(value), "yyyy-MM-dd HH:mm:ss");
				} catch (ParseException ex) {
					return null;
				}
			}
		}else if (value instanceof Date) {
			return (Date)value;
		}
		return null;	
//		String str = getString(params, name);
//		if(str == null){
//			return null;
//		}
//		try {
//			return DateUtils.parseDate(str, "yyyy-MM-dd");
//		} catch (ParseException e) {
//			return null;
//		}
	}

}
