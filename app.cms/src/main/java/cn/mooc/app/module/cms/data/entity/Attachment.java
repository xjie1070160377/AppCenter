package cn.mooc.app.module.cms.data.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.mooc.app.module.cms.util.Files;



/**
 * Attachment
 * 
 * @author csmooc
 * 
 */
@Entity
@Table(name = "t_cms_attachment")
public class Attachment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	public boolean isUsed() {
		Set<AttachmentRef> refs = getRefs();
		return refs != null && !refs.isEmpty();
	}

	@Transient
	public String getSize() {
		Long length = getLength();
		return Files.getSize(length);
	}

	@Transient
	public void applyDefaultValue() {
		if (getIp() == null) {
			setIp("localhost");
		}
		if (getTime() == null) {
			setTime(new Timestamp(System.currentTimeMillis()));
		}
	}

	private Integer id;
	private Set<AttachmentRef> refs = new HashSet<AttachmentRef>(0);

	private Site site;
	private Integer userId;

	private String name;
	private String ip;
	private Date time;
	private Long length;

	@Id
	@Column(name = "attachment_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_attachment", pkColumnValue = "t_cms_attachment", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_attachment")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE }, mappedBy = "attachment")
	public Set<AttachmentRef> getRefs() {
		return this.refs;
	}

	public void setRefs(Set<AttachmentRef> refs) {
		this.refs = refs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "name", nullable = false, length = 150)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time", nullable = false, length = 19)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "length")
	public Long getLength() {
		return this.length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

}
