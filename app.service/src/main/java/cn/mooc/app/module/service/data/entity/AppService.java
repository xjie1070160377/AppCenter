package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the t_app_service database table.
 * 
 */
@Entity
@Table(name="t_app_service")
public class AppService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "serviceId", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_app_service", pkColumnValue = "t_app_service", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_app_service")
	private Integer serviceId;

	private String appKey;

	private String appSecret;

	private Boolean auditResult;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "auditTime", length = 19)
	private Date auditTime;

	private String charger;

	private String checkNote;

	private Long createrUserid;

	@Column(name = "createTime", length = 19)
	private Timestamp createTime;

	private Integer docCount;

	private String email;

	private Integer goodCount;

	private String headImg;

	private String intro;

	private Integer isChecked;

	private Integer isFree;

	private Integer keepCount;

	private String mobile;

	private String note;

	@Column(name="org_id")
	private Integer orgId;

	private Integer regCount;

	private String serviceFullname;

	private String serviceName;

	private String telephone;

	//bi-directional many-to-one association to TAppServicetype
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceTypeid")
	@JsonBackReference
	private AppServicetype servicetype;

	//bi-directional many-to-one association to TAppServiceInfo
	@OneToMany(mappedBy="appService")
	@JsonBackReference
	private List<AppServiceInfo> appServicesInfos;

	//bi-directional many-to-one association to TAppServiceUser
	@OneToMany(mappedBy="appService")
	@JsonBackReference
	private List<AppServiceUser> appServiceUsers;

	//bi-directional many-to-one association to TServInfo
	@OneToMany(mappedBy="appService")
	private List<ServInfo> servInfos;

	public AppService() {
	}

	public Integer getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return this.appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Boolean getAuditResult() {
		return this.auditResult;
	}

	public void setAuditResult(Boolean auditResult) {
		this.auditResult = auditResult;
	}

	public Date getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getCharger() {
		return this.charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public String getCheckNote() {
		return this.checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}

	public Long getCreaterUserid() {
		return this.createrUserid;
	}

	public void setCreaterUserid(Long createrUserid) {
		this.createrUserid = createrUserid;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getDocCount() {
		return this.docCount;
	}

	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGoodCount() {
		return this.goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public String getHeadImg() {
		return this.headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Integer getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public Integer getIsFree() {
		return this.isFree;
	}

	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}

	public Integer getKeepCount() {
		return this.keepCount;
	}

	public void setKeepCount(Integer keepCount) {
		this.keepCount = keepCount;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getRegCount() {
		return this.regCount;
	}

	public void setRegCount(Integer regCount) {
		this.regCount = regCount;
	}

	public String getServiceFullname() {
		return this.serviceFullname;
	}

	public void setServiceFullname(String serviceFullname) {
		this.serviceFullname = serviceFullname;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public AppServicetype getServicetype() {
		return servicetype;
	}

	public void setServicetype(AppServicetype servicetype) {
		this.servicetype = servicetype;
	}

	public List<AppServiceInfo> getAppServiceInfos() {
		return this.appServicesInfos;
	}

	public void setAppServiceInfos(List<AppServiceInfo> appServicesInfos) {
		this.appServicesInfos = appServicesInfos;
	}

	public AppServiceInfo addTAppServiceInfo(AppServiceInfo TAppServiceInfo) {
		getAppServiceInfos().add(TAppServiceInfo);
		TAppServiceInfo.setAppService(this);

		return TAppServiceInfo;
	}

	public AppServiceInfo removeTAppServiceInfo(AppServiceInfo TAppServiceInfo) {
		getAppServiceInfos().remove(TAppServiceInfo);
		TAppServiceInfo.setAppService(null);

		return TAppServiceInfo;
	}

	public List<AppServiceUser> getAppServiceUsers() {
		return this.appServiceUsers;
	}

	public void setAppServiceUsers(List<AppServiceUser> appServiceUsers) {
		this.appServiceUsers = appServiceUsers;
	}

	public AppServiceUser addTAppServiceUser(AppServiceUser TAppServiceUser) {
		getAppServiceUsers().add(TAppServiceUser);
		TAppServiceUser.setAppService(this);

		return TAppServiceUser;
	}

	public AppServiceUser removeTAppServiceUser(AppServiceUser TAppServiceUser) {
		getAppServiceUsers().remove(TAppServiceUser);
		TAppServiceUser.setAppService(null);

		return TAppServiceUser;
	}

	public List<ServInfo> getServInfos() {
		return this.servInfos;
	}

	public void setServInfos(List<ServInfo> servInfos) {
		this.servInfos = servInfos;
	}

	public ServInfo addTServInfo(ServInfo servInfo) {
		getServInfos().add(servInfo);
		servInfo.setAppService(this);

		return servInfo;
	}

	public ServInfo removeTServInfo(ServInfo servInfo) {
		getServInfos().remove(servInfo);
		servInfo.setAppService(null);

		return servInfo;
	}

}