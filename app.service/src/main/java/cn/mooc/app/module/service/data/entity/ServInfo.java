package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.MapKeyType;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the t_serv_info database table.
 * 
 */
@Entity
@Table(name="t_serv_info")
public class ServInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 信息正文KEY
	 */
	public static final String INFO_TEXT = "text";
	
	@Transient
	public String getText() {
		Map<String, String> clobs = getClobs();
		return clobs != null ? clobs.get(INFO_TEXT) : null;
	}

	@Transient
	public void setText(String text) {
		Map<String, String> clobs = getClobs();
		if (clobs == null) {
			clobs = new HashMap<String, String>();
			setClobs(clobs);
		}
		clobs.put(INFO_TEXT, text);
	}

	@Id
	@Column(name = "info_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_serv_info", pkColumnValue = "t_serv_info", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_serv_info")
	private Integer infoId;

	private Integer comments;

	private Integer diggs;

	private Integer downloads;

	@Column(name="html_status")
	private String htmlStatus;

	@Column(name="is_with_image")
	private Boolean isWithImage;

	@Column(name="org_id")
	private Integer orgId;

	private Integer p1;

	private Integer p2;

	private Integer p3;

	private Integer p4;

	private Integer p5;

	private Integer p6;

	private Integer priority;
	
	@ElementCollection
	@CollectionTable(name = "t_serv_info_clob", joinColumns = @JoinColumn(name = "info_id"))
	@MapKeyColumn(name = "key_", length = 50)
	@MapKeyType(value = @Type(type = "string"))
	@Lob
	@Column(name = "value_", nullable = false)
	private Map<String, String> clobs = new HashMap<String, String>(0);
	
	@ElementCollection
	@CollectionTable(name = "t_serv_info_file", joinColumns = @JoinColumn(name = "info_id"))
	@OrderColumn(name = "index_")
	private List<ServInfoFile> files = new ArrayList<ServInfoFile>(0);
	
	@ElementCollection
	@CollectionTable(name = "t_serv_info_image", joinColumns = @JoinColumn(name = "info_id"))
	@OrderColumn(name = "index_")
	private List<ServInfoImage> images = new ArrayList<ServInfoImage>(0);
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "publish_date", length = 19)
	private Date publishDate;

	private Integer score;

	private String status;

	private Long userid;

	private Integer views;

	//bi-directional many-to-one association to TAppServiceInfo
	@OneToMany(mappedBy="info")
	private List<AppServiceInfo> appServiceInfos;

	//bi-directional many-to-one association to TAppService
	@ManyToOne
	@JoinColumn(name="serviceId")
	private AppService appService;

	//bi-directional one-to-one association to TServInfoDetail
	@OneToOne(cascade = { CascadeType.ALL }, mappedBy="info")
	private ServInfoDetail detail;

	public ServInfo() {
	}

	public Integer getInfoId() {
		return this.infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getComments() {
		return this.comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getDiggs() {
		return this.diggs;
	}

	public void setDiggs(Integer diggs) {
		this.diggs = diggs;
	}

	public Integer getDownloads() {
		return this.downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public String getHtmlStatus() {
		return this.htmlStatus;
	}

	public void setHtmlStatus(String htmlStatus) {
		this.htmlStatus = htmlStatus;
	}

	public Boolean getIsWithImage() {
		return this.isWithImage;
	}

	public void setIsWithImage(Boolean isWithImage) {
		this.isWithImage = isWithImage;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getP1() {
		return this.p1;
	}

	public void setP1(Integer p1) {
		this.p1 = p1;
	}

	public Integer getP2() {
		return this.p2;
	}

	public void setP2(Integer p2) {
		this.p2 = p2;
	}

	public Integer getP3() {
		return this.p3;
	}

	public void setP3(Integer p3) {
		this.p3 = p3;
	}

	public Integer getP4() {
		return this.p4;
	}

	public void setP4(Integer p4) {
		this.p4 = p4;
	}

	public Integer getP5() {
		return this.p5;
	}

	public void setP5(Integer p5) {
		this.p5 = p5;
	}

	public Integer getP6() {
		return this.p6;
	}

	public void setP6(Integer p6) {
		this.p6 = p6;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Integer getViews() {
		return this.views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public List<AppServiceInfo> getAppServiceInfos() {
		return this.appServiceInfos;
	}

	public void setAppServiceInfos(List<AppServiceInfo> appServiceInfos) {
		this.appServiceInfos = appServiceInfos;
	}

	public AppServiceInfo addTAppServiceInfo(AppServiceInfo appServiceInfo) {
		getAppServiceInfos().add(appServiceInfo);
		appServiceInfo.setInfo(this);

		return appServiceInfo;
	}

	public AppServiceInfo removeTAppServiceInfo(AppServiceInfo appServiceInfo) {
		getAppServiceInfos().remove(appServiceInfo);
		appServiceInfo.setInfo(null);

		return appServiceInfo;
	}

	public AppService getAppService() {
		return this.appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public ServInfoDetail getDetail() {
		return this.detail;
	}

	public void setDetail(ServInfoDetail detail) {
		this.detail = detail;
	}
	
	public Map<String, String> getClobs() {
		return this.clobs;
	}

	public void setClobs(Map<String, String> clobs) {
		this.clobs = clobs;
	}

	public List<ServInfoFile> getFiles() {
		return files;
	}

	public void setFiles(List<ServInfoFile> files) {
		this.files = files;
	}
	
	public List<ServInfoImage> getImages() {
		return images;
	}

	public void setImages(List<ServInfoImage> images) {
		this.images = images;
	}
}