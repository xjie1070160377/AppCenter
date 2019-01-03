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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="t_cms_app")
public class AppVersion {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_app", pkColumnValue = "t_cms_app", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_app")
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id", nullable = false)
	@JsonBackReference
	private Site site;
	@Column(name = "version", length = 50)
	private String version;
	@Column(name = "url")
	private String url;
	@Column(name = "content")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "publish_date", length = 19)
	private Date publishDate;
	@Column(name = "isforce")
	private Integer isforce;
	
	@Column(name = "patch_url")
	private String patch_url;
	
	@Column(name = "patch_version")
	private String patch_version;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	@Transient
	public String getPublishDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (this.getPublishDate() != null) {
			str = sdf.format(publishDate);
		}
		return str;
	}
	
	public Integer getIsforce() {
		return isforce;
	}
	public void setIsforce(Integer isforce) {
		this.isforce = isforce;
	}
	public String getPatch_url() {
		return patch_url;
	}
	public void setPatch_url(String patch_url) {
		this.patch_url = patch_url;
	}
	public String getPatch_version() {
		return patch_version;
	}
	public void setPatch_version(String patch_version) {
		this.patch_version = patch_version;
	}
	
	
	
}
