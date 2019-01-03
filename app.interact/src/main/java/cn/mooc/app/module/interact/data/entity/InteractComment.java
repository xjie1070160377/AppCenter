package cn.mooc.app.module.interact.data.entity;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import cn.mooc.app.module.interact.enums.CommentType;

@Document(collection = "t_interact_comment")
public class InteractComment {
	
	/**
	 * 未审核
	 */
	public static final int SAVED = 0;
	/**
	 * 已审核
	 */
	public static final int AUDITED = 1;
	/**
	 * 推荐
	 */
	public static final int RECOMMEND = 2;
	/**
	 * 屏蔽
	 */
	public static final int DISABLED = 3;
	/**
	 * 删除
	 */
	public static final int DELETED = -1;

//	/**
//	 * 对文章进行评论
//	 */
//	public static final String FTYPE_INFO = "Info";
//	/**
//	 * 对评论进行回复
//	 */
//	public static final String FTYPE_COMMENT = "Comment";
//	/**
//	 * 用户意见反馈
//	 */
//	public static final String FTYPE_FEEDBACK = "Feedback";
//	/**
//	 * 用户意见反馈回复
//	 */
//	public static final String FTYPE_FEEDBACK_REPLY = "FeedbackReply";
//	/**
//	 * 用户建言献策
//	 */
//	public static final String FTYPE_GUESTBOOK = "Guestbook";

	@Id
	private String _id;
	
	@Indexed(unique = true, dropDups = true)
	private Integer commentId;
	/**
	 * 站点ID
	 */
	@Indexed
	private Integer siteId;
	/**
	 * 外表ID,文章ID
	 */
	@Indexed
	private Integer fid;
	
	private Long fuserId;
	private String fuserName;
	
	/**
	 * 来源ID,评论对象，回评是有用
	 */
	private Integer sourceId;
	
	/**
	 * 来源用户ID
	 */
	private Long sourceUserId;
	
	private String sourceUserName;
	
	/**
	 * 外表标识
	 */
	@Indexed
	private CommentType ftype;
	/**
	 * 文章标题
	 */
	private String infoTitle;
	/**
	 * 评论人ID
	 */
	@Indexed
	private Long userId;
	/**
	 * 评论人名称
	 */
	private String userName;
	/**
	 * 评论时间
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 审核人ID
	 */
	private Long auditUserId;
	/**
	 * 审核人名称
	 */
	private String auditUserName;
	/**
	 * 审核时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	
	private String mobile;
	private String title;
	private String tag;
	
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Integer getSiteId() {
		return siteId;
	}
	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}
	public String getInfoTitle() {
		return infoTitle;
	}
	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Long getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
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
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public CommentType getFtype() {
		return ftype;
	}
	public void setFtype(CommentType ftype) {
		this.ftype = ftype;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Long getSourceUserId() {
		return sourceUserId;
	}
	public void setSourceUserId(Long sourceUserId) {
		this.sourceUserId = sourceUserId;
	}
	public String getSourceUserName() {
		return sourceUserName;
	}
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Long getFuserId() {
		return fuserId;
	}
	public void setFuserId(Long fuserId) {
		this.fuserId = fuserId;
	}
	public String getFuserName() {
		return fuserName;
	}
	public void setFuserName(String fuserName) {
		this.fuserName = fuserName;
	}
}
