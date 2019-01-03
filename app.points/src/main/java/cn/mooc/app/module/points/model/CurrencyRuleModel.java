package cn.mooc.app.module.points.model;

import cn.mooc.app.module.points.data.entity.CurrencyRule;

public class CurrencyRuleModel {

	private Integer id;

	private String caption;

	private String isspecial;
	
	private Double maxTotal;

	private String ruleCode;

	private String ruleName;

	private String status;
	
	private Double total;
	
	public CurrencyRuleModel(CurrencyRule rule){
		this.id = rule.getId();
		this.caption = rule.getCaption();
		this.isspecial = (rule.getIsspecial() != null && rule.getIsspecial()==1) ? "是" : "否";
		this.maxTotal = rule.getMaxTotal();
		this.total = rule.getTotal();
		this.ruleCode = rule.getRuleCode();
		this.ruleName = rule.getRuleName();
		this.status = (rule.getStatus()!= null && rule.getStatus() == 1) ? "有效" : "无效";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getIsspecial() {
		return isspecial;
	}

	public void setIsspecial(String isspecial) {
		this.isspecial = isspecial;
	}

	public Double getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Double maxTotal) {
		this.maxTotal = maxTotal;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
