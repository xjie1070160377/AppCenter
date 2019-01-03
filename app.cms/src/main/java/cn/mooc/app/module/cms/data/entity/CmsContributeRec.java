package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.mooc.app.core.utils.DateTimeUtil;


/**
 * The persistent class for the t_cms_contribute_rec database table.
 * 
 */
@Entity
@Table(name="t_cms_contribute_rec")
public class CmsContributeRec implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 投稿提交
	 */
	public final static Integer SUBMIT = 0;
	/**
	 * 投稿审核中
	 */
	public final static Integer PEDDING = 2;
	/**
	 * 投稿审核通过，发布
	 */
	public final static Integer PASS = 1;
	/**
	 * 投稿未通过审核，退稿
	 */
	public final static Integer UNPASS = -1;
	
	public void applyDefaultValue() {
		if(this.status == null){
			this.status = 0;
		}
		if(this.submitTime == null){
			this.submitTime = new Date();
		}
	}

	private Integer id;

	private Integer fid;

	private String intention;

	private String replay;

	private Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="submit_time")
	private Date submitTime;

	private String title;

	private CmsContribute contribute;
	
	private List<CmsContributeFile> images = new ArrayList<CmsContributeFile>();
	private List<CmsContributeFile> files = new ArrayList<CmsContributeFile>();
	private List<WorkflowLog> logs = new ArrayList<WorkflowLog>();


	@Id
	@Column(name = "rec_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_contribute_rec", pkColumnValue = "t_cms_contribute_rec", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_contribute_rec")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cont_id")
	public CmsContribute getContribute() {
		return this.contribute;
	}

	public void setContribute(CmsContribute contribute) {
		this.contribute = contribute;
	}

	@Column(name = "fid")
	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	@Column(name = "intention", length = 200)
	public String getIntention() {
		return this.intention;
	}

	public void setIntention(String intention) {
		this.intention = intention;
	}

	@Column(name = "replay")
	public String getReplay() {
		return this.replay;
	}

	public void setReplay(String replay) {
		this.replay = replay;
	}

	@Column(name = "status", precision = 1, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "submit_time", length = 19)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "title", length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Transient
	public List<CmsContributeFile> getImages() {
		return images;
	}

	public void setImages(List<CmsContributeFile> images) {
		this.images = images;
	}

	@Transient
	public List<CmsContributeFile> getFiles() {
		return files;
	}

	public void setFiles(List<CmsContributeFile> files) {
		this.files = files;
	}

	@Transient
	public List<WorkflowLog> getLogs() {
		return logs;
	}

	public void setLogs(List<WorkflowLog> logs) {
		this.logs = logs;
	}

	@Transient
	public String getDateStr(){
		if(submitTime != null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(submitTime);
		}else{
			return "";
		}
	}
	
	@Transient
	public String getTimeStr(){
		if(submitTime != null){
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			return format.format(submitTime);
		}else{
			return "";
		}
	}
	
	@Transient
	public String getSubmitTimeStr() {
		return DateTimeUtil.showTime(submitTime);
	}

}