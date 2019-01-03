package cn.mooc.app.module.cms.data.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "t_cms_task")
public class Task implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 运行中
	 */
	public static final int RUNNING = 0;
	/**
	 * 完成
	 */
	public static final int FINISH = 1;
	/**
	 * 终止
	 */
	public static final int ABORT = 2;
	/**
	 * 停止
	 */
	public static final int STOP = 3;
	/**
	 * 节点html生成
	 */
	public static final int NODE_HTML = 1;
	/**
	 * 信息html生成
	 */
	public static final int INFO_HTML = 2;
	/**
	 * 全文索引生成
	 */
	public static final int FULLTEXT = 3;

	@Transient
	public boolean isRunning() {
		return getStatus() == RUNNING;
	}

	@Transient
	public boolean isStop() {
		return getStatus() == STOP;
	}

	@Transient
	public void finish() {
		if (isRunning()) {
			setEndTime(new Timestamp(System.currentTimeMillis()));
			setStatus(FINISH);
		}
	}

	@Transient
	public void abort() {
		if (isRunning()) {
			setEndTime(new Timestamp(System.currentTimeMillis()));
			setStatus(ABORT);
		}
	}

	@Transient
	public void stop() {
		if (isRunning()) {
			setEndTime(new Timestamp(System.currentTimeMillis()));
			setStatus(STOP);
		}
	}

	@Transient
	public void add(int count) {
		setTotal(getTotal() + count);
	}

	@Transient
	public void applyDefaultValue() {
		if (getBeginTime() == null) {
			setBeginTime(new Timestamp(System.currentTimeMillis()));
		}
		if (getTotal() == null) {
			setTotal(0);
		}
		if (getStatus() == null) {
			setStatus(RUNNING);
		}
	}

	private Integer id;

	private Long userId;
	private Site site;

	private String name;
	private String description;
	private Date beginTime;
	private Date endTime;
	private Integer total;
	private Integer type;
	private Integer status;

	@Id
	@Column(name = "task_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_task", pkColumnValue = "t_cms_task", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_task")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "begin_time", nullable = false, length = 19)
	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "total", nullable = false)
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
