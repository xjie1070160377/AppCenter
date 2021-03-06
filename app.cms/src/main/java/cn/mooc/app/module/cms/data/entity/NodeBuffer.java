package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 * NodeBuffer
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_node_buffer")
public class NodeBuffer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public void applyDefaultValue() {
		if (getViews() == null) {
			setViews(0);
		}
	}

	private Integer id;
	private Node node;
	private Integer views;

	public NodeBuffer() {
	}

	public NodeBuffer(Node node) {
		this.node = node;
	}

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@MapsId
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id")
	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Column(name = "views", nullable = false)
	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}
}
