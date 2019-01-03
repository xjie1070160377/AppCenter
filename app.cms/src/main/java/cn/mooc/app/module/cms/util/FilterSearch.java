package cn.mooc.app.module.cms.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 查询条件过滤类
 * @author oyhx
 *
 */
public class FilterSearch {
	private String groupOp; // 多字段查询时分组类型，主要是AND或者OR

	private List<SearchRule> rules; // 多字段查询时候，查询条件的集合
	
	public Map<String, Object> getParamsMap(){
		Map<String, Object> params = new TreeMap<String, Object>();
		for (SearchRule rule : rules) {
			params.put(rule.getParamName(), rule.getParamValue());
		}
		return params;
	}

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	}

	public List<SearchRule> getRules() {
		return rules;
	}

	public void setRules(List<SearchRule> rules) {
		this.rules = rules;
	}
}
