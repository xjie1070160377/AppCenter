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
@Table(name = "t_cms_node_role")
public class NodeRole implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getNodePerm() == null) {
			setNodePerm(true);
		}
		if (getInfoPerm() == null) {
			setInfoPerm(true);
		}
	}

	private Integer id;
	private Node node;
	private long roleId;
	private Boolean nodePerm;
	private Boolean infoPerm;
	
	public NodeRole() {
		super();
	}

	@Id
	@Column(name = "noderole_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_node_role", pkColumnValue = "t_cms_node_role", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_node_role")
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

	@Column(name = "role_id")
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Column(name = "is_node_perm", nullable = false, length = 1)
	public Boolean getNodePerm() {
		return this.nodePerm;
	}

	public void setNodePerm(Boolean nodePerm) {
		this.nodePerm = nodePerm;
	}

	@Column(name = "is_info_perm", nullable = false, length = 1)
	public Boolean getInfoPerm() {
		return this.infoPerm;
	}

	public void setInfoPerm(Boolean infoPerm) {
		this.infoPerm = infoPerm;
	}

}
