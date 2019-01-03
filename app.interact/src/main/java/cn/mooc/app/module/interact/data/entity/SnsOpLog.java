package cn.mooc.app.module.interact.data.entity;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="t_interact_sns_op_log")
public class SnsOpLog {

	public static final Integer FTYPE_COMMENT = 1;// 评论
	public static final Integer FTYPE_DIGG = 2;// 点赞
	public static final Integer FTYPE_COLLECT = 3;// 收藏
	public static final Integer FTYPE_ATTENTION_USER = 4;// 关注用户
	public static final Integer FTYPE_ATTENTION_NODE = 5;// 关注栏目
	public static final Integer FTYPE_LOGIN = 6;// 用户登录
	public static final Integer FTYPE_ATTENTION_SPECIAL = 7;// 关注专题
	

	public static final Integer FTYPE_COMMENT_DEL = 11;// 删除评论
	
	public static final Integer FTYPE_COLLECT_START = 21;//开始采集
	public static final Integer FTYPE_COLLECT_STOP = 22;//停止采集
	
	@Id
	private String _id;
	
	@Indexed(unique = true, dropDups = true)
	private Integer logId;
	
	private Integer siteId;
	@Indexed
	private Integer userId;
	@Indexed
	private Integer ftype;
	private Integer fid;
	private Date createTime;
	private String title;
	private String author;
	private String pyCode;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPyCode() {
		return pyCode;
	}
	public void setPyCode(String pyCode) {
		this.pyCode = pyCode;
	}
}