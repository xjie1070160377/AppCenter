package cn.mooc.app.module.cms.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * WorkflowStep
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_workflow_step")
public class WorkflowStep implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}

	private Integer id;
//	private List<WorkflowStepRole> stepRoles = new ArrayList<WorkflowStepRole>(0);
	private List<WorkflowStepRole> stepRoles = new ArrayList<WorkflowStepRole>(
			0);
	private List<WorkflowProcess> processes = new ArrayList<WorkflowProcess>(
			0);

	private Workflow workflow;

	private String name;
	private Integer seq;
	private Integer toend;

	@Id
	@Column(name = "workflowstep_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_workflow_step", pkColumnValue = "t_cms_workflow_step", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_workflow_step")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "step")
//	@OrderBy("roleIndex")
//	public List<WorkflowStepRole> getStepRoles() {
//		return stepRoles;
//	}
//
//	public void setStepRoles(List<WorkflowStepRole> stepRoles) {
//		this.stepRoles = stepRoles;
//	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id", nullable = false)
	public Workflow getWorkflow() {
		return this.workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Column(name = "toend")
	public Integer getToend() {
		return toend;
	}

	public void setToend(Integer toend) {
		this.toend = toend;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "step")
	@OrderBy("roleIndex")
	public List<WorkflowStepRole> getStepRoles() {
		return stepRoles;
	}

	public void setStepRoles(List<WorkflowStepRole> stepRoles) {
		this.stepRoles = stepRoles;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "step")
	@OrderBy("id")
	public List<WorkflowProcess> getProcesses() {
		return processes;
	}

	public void setProcesses(List<WorkflowProcess> processes) {
		this.processes = processes;
	}
}
