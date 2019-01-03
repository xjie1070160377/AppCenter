package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_user_node database table.
 * 
 */
@Entity
@Table(name="t_user_node")
public class UserNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer nodeId;

	private Long userId;

	private Integer xh;

	public UserNode() {
		
	}
	
	public void applyDefaultValue() {
		
	}

	@Id
	@Column(name = "user_node_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_user_node", pkColumnValue = "t_user_node", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_user_node")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="node_id")
	public Integer getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name="user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="xh")
	public Integer getXh() {
		return this.xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

}