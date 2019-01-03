package cn.mooc.app.module.cms.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table(name = "t_cms_node_membergroup")
public class NodeMemberGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getViewPerm() == null) {
			setViewPerm(true);
		}
		if (getContriPerm() == null) {
			setContriPerm(true);
		}
		if (getCommentPerm() == null) {
			setCommentPerm(true);
		}
	}

	private Integer id;
	private Node node;
	private Boolean viewPerm;
	private Boolean contriPerm;
	private Boolean commentPerm;

	@Id
	@Column(name = "nodemgroup_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_node_membergroup", pkColumnValue = "t_cms_node_membergroup", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_node_membergroup")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id", nullable = false)
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Column(name = "is_view_perm", nullable = false, length = 1)
	public Boolean getViewPerm() {
		return this.viewPerm;
	}

	public void setViewPerm(Boolean viewPerm) {
		this.viewPerm = viewPerm;
	}

	@Column(name = "is_contri_perm", nullable = false, length = 1)
	public Boolean getContriPerm() {
		return this.contriPerm;
	}

	public void setContriPerm(Boolean contriPerm) {
		this.contriPerm = contriPerm;
	}

	@Column(name = "is_comment_perm", nullable = false, length = 1)
	public Boolean getCommentPerm() {
		return this.commentPerm;
	}

	public void setCommentPerm(Boolean commentPerm) {
		this.commentPerm = commentPerm;
	}

}
