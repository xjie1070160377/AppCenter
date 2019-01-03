package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;

public class NodeCustomPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1270123565357404802L;
	private Integer nodeId;
	private String key;
	
	public NodeCustomPk(Integer nodeId, String key){
		this.nodeId = nodeId;
		this.key = key;
	}
	
	public NodeCustomPk(){
		
	}
	
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
