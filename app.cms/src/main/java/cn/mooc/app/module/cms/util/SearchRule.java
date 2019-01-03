package cn.mooc.app.module.cms.util;

/**
 * 查询条件规则类
 * @author oyhx
 *
 */
public class SearchRule {
	private String field; // 查询字段
	private String op; // 查询操作
	private String data; // 选择的查询值
	
	public String getParamName(){
		if(field == null || "".equals(field)){
			return "";
		}
		return op.toUpperCase()+"_"+field;
	}
	
	public String getParamValue(){
		return data;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
