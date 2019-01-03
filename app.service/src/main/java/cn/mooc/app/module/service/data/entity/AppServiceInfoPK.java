package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the t_app_service_info database table.
 * 
 */
@Embeddable
public class AppServiceInfoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer serviceId;

	@Column(name="info_id", insertable=false, updatable=false)
	private Integer infoId;

	public AppServiceInfoPK() {
	}
	
	public AppServiceInfoPK(Integer serviceId, Integer infoId) {
		this.serviceId = serviceId;
		this.infoId = infoId;
	}
	public Integer getServiceId() {
		return this.serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getInfoId() {
		return this.infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AppServiceInfoPK)) {
			return false;
		}
		AppServiceInfoPK castOther = (AppServiceInfoPK)other;
		return 
			(this.serviceId == castOther.serviceId)
			&& (this.infoId == castOther.infoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.serviceId;
		hash = hash * prime + this.infoId;
		
		return hash;
	}
}