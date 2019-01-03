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
@Table(name = "t_cms_workflowstep_role")
public class WorkflowStepRole implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getRoleIndex() == null) {
			setRoleIndex(0);
		}
	}

	private Integer id;
	private long roleId;
	private WorkflowStep step;

	private Integer roleIndex;

	public WorkflowStepRole() {
	}

	public WorkflowStepRole(WorkflowStep step, long role, Integer roleIndex) {
		this.step = step;
		this.roleId = role;
		this.roleIndex = roleIndex;
	}

	@Id
	@Column(name = "wfsteprole_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_workflowstep_role", pkColumnValue = "t_cms_workflowstep_role", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_workflowstep_role")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "role_id")
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long role) {
		this.roleId = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflowstep_id", nullable = false)
	public WorkflowStep getStep() {
		return step;
	}

	public void setStep(WorkflowStep step) {
		this.step = step;
	}

	@Column(name = "role_index", nullable = false)
	public Integer getRoleIndex() {
		return roleIndex;
	}

	public void setRoleIndex(Integer roleIndex) {
		this.roleIndex = roleIndex;
	}

}
