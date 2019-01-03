package cn.mooc.app.core.data.specifications;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

public class SpecExpression {
	
	private String fieldName;
	private SpecOperator operator;
	private Object fieldVal;
	
	public SpecExpression(String fieldName,SpecOperator operator,Object fieldVal){
		this.fieldName = fieldName;
		this.operator = operator;
		this.fieldVal = fieldVal;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public SpecOperator getOperator() {
		return operator;
	}
	public void setOperator(SpecOperator operator) {
		this.operator = operator;
	}
	public Object getFieldVal() {
		return fieldVal;
	}
	public void setFieldVal(Object fieldVal) {
		this.fieldVal = fieldVal;
	}
	
	/**
	 * searchParams中key的格式为 OPERATOR_FIELDNAME
	 * 比如对title属性的模糊查询是 search_LIKE_title，也就是表单参数名为 search_LIKE_title
	 */
	public static Map<String, SpecExpression> parse(Map<String, Object> searchParams) {
		Map<String, SpecExpression> filters = new HashMap<String, SpecExpression>();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value==null || StringUtils.isBlank(value.toString())) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			SpecOperator operator = SpecOperator.valueOf(names[0]);

			// 创建searchFilter
			SpecExpression filter = new SpecExpression(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
	
	public static String equalOpr(String fieldName){
		return "EQ_"+fieldName;
	}
	
	public static String likeOpr(String fieldName){
		return "LIKE_"+fieldName;
	}
	
	public static String greaterThanOpr(String fieldName){
		return "GT_"+fieldName;
	}
	
	public static String lessThanOpr(String fieldName){
		return "LT_"+fieldName;
	}
	
	public static String greaterThanEqualOpr(String fieldName){
		return "GTE_"+fieldName;
	}

	public static String lessThanEqualOpr(String fieldName){
		return "LTE_"+fieldName;
	}
	
	
}
