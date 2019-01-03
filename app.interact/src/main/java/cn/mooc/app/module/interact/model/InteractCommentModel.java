package cn.mooc.app.module.interact.model;

import java.util.Date;

import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.enums.CommentType;

public class InteractCommentModel {
	
	private String id;
	private Integer commentId;
	/**
	 * 文章标题
	 */
	private String infoTitle;
	/**
	 * 评论人名称
	 */
	private String userName;
	/**
	 * 评论时间
	 */
	private Date createTime;
	/**
	 * 审核人名称
	 */
	private String auditUserName;
	/**
	 * 审核时间
	 */
	private Date auditTime;
	/**
	 * 评论状态 0：未审核， 1：已审核
	 */
	private int state;
	
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 评论人IP地址
	 */
	private String ip;
	private String ftype;
	private String ftypeText;
	
	public InteractCommentModel(InteractComment model){
		this.setId(model.get_id());
		this.setCommentId(model.getCommentId());
		this.setAuditTime(model.getAuditTime());
		this.setAuditUserName(model.getAuditUserName());
		this.setContent(model.getContent());
		this.setCreateTime(model.getCreateTime());
		this.setInfoTitle(model.getInfoTitle());
		this.setUserName(model.getUserName());
		this.setIp(model.getIp());
		this.setState(model.getState());
		this.setFtype(model.getFtype().toString());
		if(model.getFtype() != null){
			this.setFtypeText(model.getFtype().getValue());
		}else{
			this.setFtypeText("");
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getFtypeText() {
		return ftypeText;
	}
	public void setFtypeText(String ftypeText) {
		this.ftypeText = ftypeText;
	}
}
