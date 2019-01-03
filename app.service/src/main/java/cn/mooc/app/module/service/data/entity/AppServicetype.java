package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the t_app_servicetype database table.
 * 
 */
@Entity
@Table(name="t_app_servicetype")
public class AppServicetype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "serviceTypeid", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_app_servicetype", pkColumnValue = "t_app_servicetype", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_app_servicetype")
	private Integer serviceTypeid;

	private String serviceTypeName;

	//bi-directional many-to-one association to TAppService
	@OneToMany(mappedBy="servicetype")
	@JsonBackReference
	private List<AppService> appServices;

	public AppServicetype() {
	}

	public Integer getServiceTypeid() {
		return this.serviceTypeid;
	}

	public void setServiceTypeid(Integer serviceTypeid) {
		this.serviceTypeid = serviceTypeid;
	}

	public String getServiceTypeName() {
		return this.serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public List<AppService> getAppServices() {
		return this.appServices;
	}

	public void setAppServices(List<AppService> appServices) {
		this.appServices = appServices;
	}

	public AppService addTAppService(AppService appService) {
		getAppServices().add(appService);
		appService.setServicetype(this);

		return appService;
	}

	public AppService removeTAppService(AppService appService) {
		getAppServices().remove(appService);
		appService.setServicetype(null);

		return appService;
	}

}