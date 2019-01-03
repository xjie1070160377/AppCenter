package cn.mooc.app.module.points.model;

import java.util.Date;

import cn.mooc.app.module.points.data.entity.DrawRule;

public class DrawRuleModel {
	private Integer id;

	private String caption;

	private Integer dayLimit;

	private Date endTime;

	private Date startTime;

	private Integer status;

	private String title;
	
	public DrawRuleModel(){
		
	}
	
	public DrawRuleModel(DrawRule rule){
		setId(rule.getId());
		setCaption(rule.getCaption());
		setDayLimit(rule.getDayLimit());
		setEndTime(rule.getEndTime());
		setStartTime(rule.getStartTime());
		setStatus(rule.getStatus());
		setTitle(rule.getTitle());
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
