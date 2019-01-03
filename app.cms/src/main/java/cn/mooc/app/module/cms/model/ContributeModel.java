package cn.mooc.app.module.cms.model;

import java.io.Serializable;

import cn.mooc.app.module.cms.data.entity.CmsContributeRec;

public class ContributeModel implements Serializable {

	private Integer id;
	private String name;
	private String company;
	private String realName;
	private String title;
	private String submitTime;
	private Integer status;
	
	public ContributeModel(CmsContributeRec rec){
		this.id = rec.getId();
		this.name = rec.getContribute().getName();
		this.company = rec.getContribute().getCompany();
		this.realName = rec.getContribute().getRealName();
		this.title = rec.getTitle();
		this.submitTime = rec.getSubmitTimeStr();
		this.status = rec.getStatus();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
