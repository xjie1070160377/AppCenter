package cn.mooc.app.module.interact.model;

import java.util.Date;

import cn.mooc.app.module.interact.data.entity.InteractMark;

public class InteractMarkModel {
	private String id;
	
	private long fid;
	
	private String ftype;
	
	private String ftypeText;
	
	private String ftitle;
	
	private Date createTime;
	
	private long userId;
	
	private String userName;
	
	private String ip;
	
	public InteractMarkModel(InteractMark entity) {
		this.setId(entity.get_id());
		this.setFid(entity.getFid());
		this.setFtype(""+entity.getFtype());
		this.setFtypeText(entity.getFtype().getValue());
		this.setFtitle(entity.getFtitle());
		this.setCreateTime(entity.getCreateTime());
		this.setUserId(entity.getUserId());
		this.setUserName(entity.getUserName());
		this.setIp(entity.getIp());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFtypeText() {
		return ftypeText;
	}

	public void setFtypeText(String ftypeText) {
		this.ftypeText = ftypeText;
	}

}
