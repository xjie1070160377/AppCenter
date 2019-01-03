package cn.mooc.app.module.interact.data.entity;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import cn.mooc.app.module.interact.enums.MarkType;

@Document(collection="t_interact_mark")
public class InteractMark {
	/**
	 * 关注个人
	 */
	public static final Integer ATTENTION_TYPE_PERSONAL = 1;
	/**
	 * 关注栏目
	 */
	public static final Integer ATTENTION_TYPE_NODE = 2;
	/**
	 * 关注专题
	 */
	public static final Integer ATTENTION_TYPE_Specail = 3;
	
	@Id
	private String _id;
	
	@Indexed(unique = true, dropDups = true)
	private Integer markId;
	
	@Indexed
	private long fid;
	
	@Indexed
	private MarkType ftype;
	
	private String ftitle;
	
	@Indexed(direction = IndexDirection.DESCENDING)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@Indexed
	private long userId;
	
	private String userName;
	
	private String ip;
	
	private Integer siteId;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public MarkType getFtype() {
		return ftype;
	}

	public void setFtype(MarkType ftype) {
		this.ftype = ftype;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getMarkId() {
		return markId;
	}

	public void setMarkId(Integer markId) {
		this.markId = markId;
	}
}
