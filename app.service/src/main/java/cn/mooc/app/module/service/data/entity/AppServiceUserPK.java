package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the t_app_service_user database table.
 * 
 */
@Embeddable
public class AppServiceUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long userid;

	@Column(insertable=false, updatable=false)
	private Integer serviceId;

	public AppServiceUserPK() {
	}
	public Long getUserid() {
		return this.userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Integer getServiceId() {
		return this.serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AppServiceUserPK)) {
			return false;
		}
		AppServiceUserPK castOther = (AppServiceUserPK)other;
		return 
			(this.userid == castOther.userid)
			&& (this.serviceId == castOther.serviceId);
	}

}