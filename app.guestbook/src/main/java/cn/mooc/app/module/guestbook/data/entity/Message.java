package cn.mooc.app.module.guestbook.data.entity;

import java.util.Date;

import javax.persistence.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import cn.mooc.app.module.guestbook.enums.DocumentType;

/**
 * @author linwei
 * @description 留言实体类
 */
@Document(collection = "t_guestbook_message")
public class Message {
	
	/**
	 * 未审核
	 */
	public static final int SAVED = 0;
	/**
	 * 已审核
	 */
	public static final int AUDITED = 1;
	/**
	 * 禁用
	 */
	public static final int DISABLED = -1;
	
	@Id
	private String _id;
	
	/**
	 * 父自信件id
	 */
	@Indexed
	private String parentId;
	
	/**
	 * 来源ID(转发)
	 */
	@Indexed
	private String sourceId;
	
	private String sourceUserName;
	
	/**
	 * 信件标题
	 */
	private String title;
	
	/**
	 * 留言类型
	 */
	private DocumentType type;
		
	/**
	 * 留言内容
	 */
	private String text;
	
	/**
	 * 发信人用户名
	 */
	private String username;
	
	/**
	 * 留言时间
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 答复时间
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date replyTime;
	
	/**
	 * 审核时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date auditTime;
	
	/**
	 * 状态
	 */
	private int state;
	
	/**
	 * 是否显示
	 */
	private int isVisibile;
	
	/**
	 * 是否置顶
	 */
	private int isTop;
	
	/**
	 * 是否已回复
	 */
	private int isReply;
	/**
	 * 是否已读
	 */
	private int isRead;

	/**
	 * 留言ip地址
	 */
	private String ip;
	
	/**
	 * 用户邮箱
	 */
	private String email;
	
	/**
	 * 审核人ID
	 */
	private Long auditUserId;
	
	/**
	 * 用户ID
	 */
	@Indexed
	private Long userId;
	
	/**
	 * 留言簿ID
	 */
	private String guestbookId;
	
	
	
	/**
	 * getters and setters
	 */
	public String get_id() {
		return _id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
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
	public int getIsVisibile() {
		return isVisibile;
	}
	public void setIsVisibile(int isVisibile) {
		this.isVisibile = isVisibile;
	}
	public int getIsTop() {
		return isTop;
	}
	public void setIsTop(int isTop) {
		this.isTop = isTop;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public DocumentType getType() {
		return type;
	}
	public void setType(DocumentType type) {
		this.type = type;
	}
	public String getSourceUserName() {
		return sourceUserName;
	}
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
	
	public String getGuestbookId() {
		return guestbookId;
	}
	public void setGuestbookId(String guestbookId) {
		this.guestbookId = guestbookId;
	}
	public int getIsReply() {
		return isReply;
	}
	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	
}














