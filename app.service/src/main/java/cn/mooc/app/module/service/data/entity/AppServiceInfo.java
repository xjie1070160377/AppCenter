package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * The persistent class for the t_app_service_info database table.
 * 
 */
@Entity
@Table(name="t_app_service_info")
public class AppServiceInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "serviceId", column = @Column(name = "serviceId", nullable = false)),
			@AttributeOverride(name = "infoId", column = @Column(name = "info_id", nullable = false)) })
	private AppServiceInfoPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "opTime", length = 19)
	private Date opTime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "viewTime", length = 19)
	private Date viewTime;

	//bi-directional many-to-one association to TAppService
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceId", insertable=false, updatable=false)
	private AppService appService;

	//bi-directional many-to-one association to TServInfo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="info_id", insertable=false, updatable=false)
	private ServInfo info;

	public AppServiceInfo() {
	}

	public AppServiceInfoPK getId() {
		return this.id;
	}

	public void setId(AppServiceInfoPK id) {
		this.id = id;
	}

	public Date getOpTime() {
		return this.opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public Date getViewTime() {
		return this.viewTime;
	}

	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public ServInfo getInfo() {
		return info;
	}

	public void setInfo(ServInfo info) {
		this.info = info;
	}
}