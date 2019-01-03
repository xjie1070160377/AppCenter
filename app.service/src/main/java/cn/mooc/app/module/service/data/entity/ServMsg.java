package cn.mooc.app.module.service.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the t_serv_msg database table.
 * 
 */
@Entity
@Table(name="t_serv_msg")
public class ServMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//图文消息
	public static final Integer MSGTYPE_IMGTEXT = 11;
	public static final Integer MSGTYPE_IMGTEXTMORE = 12;
	
	
	public static final Integer MSGSTATE_CANCEL = -1;
	public static final Integer MSGSTATE_DRAFT = 1;
	public static final Integer MSGSTATE_SENDING = 2;

	@Id
	@Column(name = "msgid", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_serv_msg", pkColumnValue = "t_serv_msg", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_serv_msg")
	private Integer msgid;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "createtime", length = 19)
	private Date createtime;

	private Integer msgstate;

	private Integer msgtype;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "pushtime", length = 19)
	private Date pushtime;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "sendtime", length = 19)
	private Date sendtime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="serviceId")
	private AppService appService;

	@Column(name="user_id")
	private Long userId;

	//bi-directional many-to-one association to TServMsgInfo
	@OneToMany(mappedBy="msg")
	private Set<ServMsgInfo> msgInfos = new HashSet<ServMsgInfo>(0);;

	public ServMsg() {
	}

	public Integer getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getMsgstate() {
		return this.msgstate;
	}

	public void setMsgstate(Integer msgstate) {
		this.msgstate = msgstate;
	}

	public Integer getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(Integer msgtype) {
		this.msgtype = msgtype;
	}

	public Date getPushtime() {
		return this.pushtime;
	}

	public void setPushtime(Date pushtime) {
		this.pushtime = pushtime;
	}

	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Set<ServMsgInfo> getMsgInfos() {
		return this.msgInfos;
	}

	public void setMsgInfos(Set<ServMsgInfo> msgInfos) {
		this.msgInfos = msgInfos;
	}

	public ServMsgInfo addTServMsgInfo(ServMsgInfo msgInfo) {
		getMsgInfos().add(msgInfo);
		msgInfo.setMsg(this);

		return msgInfo;
	}

	public ServMsgInfo removeTServMsgInfo(ServMsgInfo msgInfo) {
		getMsgInfos().remove(msgInfo);
		msgInfo.setMsg(null);

		return msgInfo;
	}

}