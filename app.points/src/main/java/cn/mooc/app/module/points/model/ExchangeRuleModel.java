package cn.mooc.app.module.points.model;

import java.util.Date;

import cn.mooc.app.module.points.data.entity.ExchangeRule;

public class ExchangeRuleModel {
	private Integer id;

	private String caption;

	private Integer dayLimit;

	private Date endTime;

	private String ruleName;

	private Integer ruleType;

	private Date startTime;

	private Integer status;

	private Double total;
	
	public ExchangeRuleModel(){
		
	}
	
	public ExchangeRuleModel(ExchangeRule rule){
		setId(rule.getId());
		setCaption(rule.getCaption());
		setDayLimit(rule.getDayLimit());
		setEndTime(rule.getEndTime());
		setRuleName(rule.getRuleName());
		setRuleType(rule.getRuleType());
		setStartTime(rule.getStartTime());
		setStatus(rule.getStatus());
		setTotal(rule.getTotal());
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

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
