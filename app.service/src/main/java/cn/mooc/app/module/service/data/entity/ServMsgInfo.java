package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the t_serv_msg_info database table.
 * 
 */
@Entity
@Table(name="t_serv_msg_info")
public class ServMsgInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "msgid", column = @Column(name = "msgid", nullable = false)),
			@AttributeOverride(name = "infoId", column = @Column(name = "info_id", nullable = false)) })
	private ServMsgInfoPK id;
	
	@Column(name="_index")
	private Integer index;

	//bi-directional many-to-one association to TServMsg
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="msgid", nullable = false, insertable = false, updatable = false)
	private ServMsg msg;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "info_id", nullable = false, insertable = false, updatable = false)
	private ServInfo info;

	public ServMsgInfo() {
	}

	public ServMsgInfoPK getId() {
		return this.id;
	}

	public void setId(ServMsgInfoPK id) {
		this.id = id;
	}

	public Integer getIndex() {
		return this.index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public ServMsg getMsg() {
		return msg;
	}

	public void setMsg(ServMsg msg) {
		this.msg = msg;
	}

	public ServInfo getInfo() {
		return info;
	}

	public void setInfo(ServInfo info) {
		this.info = info;
	}
}