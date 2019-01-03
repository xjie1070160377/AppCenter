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

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Workflow
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_workflow")
public class Workflow implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public void applyDefaultValue() {
		if (getSeq() == null) {
			setSeq(Integer.MAX_VALUE);
		}
	}

	private Integer id;
	private List<WorkflowStep> steps = new ArrayList<WorkflowStep>(0);

	private WorkflowGroup group;
	private Site site;

	private String name;
	private String description;
	private Integer seq;
	private Integer status;

	@Id
	@Column(name = "workflow_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_workflow", pkColumnValue = "t_cms_workflow", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_workflow")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "workflow")
	@OrderBy(value = "seq asc, id asc")
	public List<WorkflowStep> getSteps() {
		return this.steps;
	}

	public void setSteps(List<WorkflowStep> steps) {
		this.steps = steps;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflowgroup_id", nullable = false)
	public WorkflowGroup getGroup() {
		return this.group;
	}

	public void setGroup(WorkflowGroup group) {
		this.group = group;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	@JsonBackReference
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "seq", nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
