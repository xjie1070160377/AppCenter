package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="t_cms_node_custom")
@IdClass(value = NodeCustomPk.class)
public class NodeCustom {

	@Id
	@Column(name="node_id", nullable = false)
	private Integer nodeId;
	
	@Id
	@Column(name="key_", nullable = false)
	private String key;
	
	@Column(name = "value_", length = 2000)
	private String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="node_id", insertable=false, updatable=false)
	private Node node;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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
