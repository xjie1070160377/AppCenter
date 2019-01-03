package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the t_serv_msg_info database table.
 * 
 */
@Embeddable
public class ServMsgInfoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "msgid", nullable = false)
	private Integer msgid;

	@Column(name = "info_id", nullable = false)
	private Integer infoId;

	public ServMsgInfoPK() {
	}
	public Integer getMsgid() {
		return this.msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
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
		if (!(other instanceof ServMsgInfoPK)) {
			return false;
		}
		ServMsgInfoPK castOther = (ServMsgInfoPK)other;
		return 
			(this.msgid == castOther.msgid)
			&& (this.infoId == castOther.infoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.msgid;
		hash = hash * prime + this.infoId;
		
		return hash;
	}
}