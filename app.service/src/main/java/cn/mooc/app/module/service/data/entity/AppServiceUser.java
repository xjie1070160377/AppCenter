package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * The persistent class for the t_app_service_user database table.
 * 
 */
@Entity
@Table(name="t_app_service_user")
public class AppServiceUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AppServiceUserPK id;

	private Boolean auditResult;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "auditTime", length = 19)
	private Date auditTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "lastTime", length = 19)
	private Date lastTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "regTime", length = 19)
	private Date regTime;

	//bi-directional many-to-one association to TAppService
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceId", insertable=false, updatable=false)
	private AppService appService;

	public AppServiceUser() {
	}

	public AppServiceUserPK getId() {
		return this.id;
	}

	public void setId(AppServiceUserPK id) {
		this.id = id;
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

	public Date getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}
}