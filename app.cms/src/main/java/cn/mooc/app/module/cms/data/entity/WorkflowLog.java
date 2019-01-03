package cn.mooc.app.module.cms.data.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * WorkflowLog
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_workflow_log")
public class WorkflowLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public void applyDefaultValue() {
	}

	private Integer id;

	private WorkflowProcess process;
	private long userId;
	private Site site;

	private String from;
	private String to;
	private Date creationDate;
	private String opinion;
	private Integer type;

	@Id
	@Column(name = "workflowlog_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_workflow_log", pkColumnValue = "t_cms_workflow_log", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_workflow_log")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflowprocess_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	public WorkflowProcess getProcess() {
		return this.process;
	}

	public void setProcess(WorkflowProcess process) {
		this.process = process;
	}

	@Column(name = "user_id")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return this.site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "from_", nullable = false, length = 100)
	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Column(name = "to_", nullable = false, length = 100)
	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", nullable = false, length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "opinion")
	public String getOpinion() {
		return this.opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Transient
	public String getDateStr() {
		if(creationDate != null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(creationDate);
		}else{
			return "";
		}
	}
	
	@Transient
	public String getTimeStr() {
		if(creationDate != null){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			return format.format(creationDate);
		}else{
			return "";
		}
	}

}
