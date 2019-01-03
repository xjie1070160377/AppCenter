package cn.mooc.app.module.cms.model;

import cn.mooc.app.module.cms.data.entity.Workflow;

public class WorkflowModel {
	private Integer id;
	private String groupName;
	private String name;
	private String description;
	private Integer seq;
	private Integer status;
	
	public WorkflowModel(){
		
	}
	
	public WorkflowModel(Workflow workflow){
		setId(workflow.getId());
		setName(workflow.getName());
		setDescription(workflow.getDescription());
		setSeq(workflow.getSeq());
		setStatus(workflow.getStatus());
		setGroupName(workflow.getGroup().getName());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
