package cn.mooc.app.module.guestbook.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.collections4.CollectionUtils;

import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.module.guestbook.data.entity.Message;
import cn.mooc.app.module.guestbook.enums.DocumentType;
import cn.mooc.app.module.guestbook.service.GuestbookService;
import cn.mooc.app.module.guestbook.service.GuestbookTypeService;

/**
 * @author linwei
 * @description 留言模型
 */
public class MessageModel {
	
	
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
	
	private String _id;
	
	/**
	 * 父自信件id
	 */
	private String parentId;
	
	/**
	 * 来源ID(转发)
	 */
	private String sourceId;
	
	private String sourceUserName;
	
	private String sourceText;
	
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
	private String createTime;
	
	/**
	 * 答复时间
	 */
	private Date replyTime;
	
	/**
	 * 审核时间
	 */
	private Date auditTime;
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 是否显示
	 */
	private int isVisibile;
	
	/**
	 * 是否置顶
	 */
	private int isTop;

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
	 * 审核人ID
	 */
	private String auditUserName;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 是否已经回复
	 */
	private int isReply;
	
	/**
	 * 信件分类
	 */
	private String guestbookType;
	
	/**
	 * 留言簿
	 */
	private String guestbook;
	/**
	 * 追加留言、回复列表
	 */
	private List<MessageModel> msgList;
	
	
	/**
	 * getters and setters
	 */
	public String get_id() {
		return _id;
	}
	public void set_id(String _id){
		this._id = _id;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
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
	public String getGuestbookType() {
		return guestbookType;
	}
	public void setGuestbookType(String guestbookType) {
		this.guestbookType = guestbookType;
	}
	public String getGuestbook() {
		return guestbook;
	}
	public void setGuestbook(String guestbook) {
		this.guestbook = guestbook;
	}
	
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public List<MessageModel> getMsgList() {
		return msgList;
	}
	public void setMsgList(List<MessageModel> msgList) {
		this.msgList = msgList;
	}
	public int getIsReply() {
		return isReply;
	}
	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}
	public String getSourceText(){
		return this.sourceText;
	}
	public void setSourceText(String sourceText){
		this.sourceText = sourceText;
	}
	
	
	public MessageModel(Message msg, String guestbook, String auditUserName, String sourceText,List<MessageModel> msgList){
		super();
		//回复类型
		if(msg.getType() == DocumentType.Reply){
			this._id = msg.get_id();
			this.parentId = msg.getParentId();
			this.type = msg.getType();
			this.createTime = (new SimpleDateFormat("YYYY-MM-dd HH:mm")).format(msg.getCreateTime());
			this.username = msg.getUsername();
			this.text = msg.getText();
		}else{//留言类型
			this._id = msg.get_id();
			this.title = msg.getTitle();
			this.type = msg.getType();
			this.text = msg.getText();
			this.username = msg.getUsername();
			this.isVisibile = msg.getIsVisibile();
			this.isTop = msg.getIsTop();
			this.isReply = msg.getIsReply();
			this.ip = msg.getIp();
			this.email = msg.getEmail();
			this.userId = msg.getUserId();
			this.guestbook = guestbook;
			this.createTime = (new SimpleDateFormat("YYYY-MM-dd HH:mm")).format(msg.getCreateTime());
			switch (msg.getState()) {
				case Message.SAVED:
					this.state = "未审核";
					break;
				case Message.AUDITED:
					this.state = "已审核";
					break;
				case Message.DISABLED:
					this.state = "禁用";
					break;
			}
			if(msg.getParentId() != null){
				this.parentId = msg.getParentId();
			}
			if(msg.getSourceId() != null){
				this.sourceId = msg.getSourceId();
			}
			if(msg.getSourceUserName() != null){
				this.sourceUserName = msg.getSourceUserName();
			}
			if(StringUtils.isNoneEmpty(sourceText)){
				this.sourceText = sourceText;
			}
			if(msg.getReplyTime() != null){
				this.replyTime = msg.getReplyTime();
			}
			if(msg.getAuditTime() != null){
				this.auditTime = msg.getAuditTime();
			}
			if(msg.getAuditUserId() != null){
				this.auditUserId = msg.getAuditUserId();
			}
			if(msg.getAuditUserId() != null){
				this.auditUserId = msg.getAuditUserId();
			}
			if(StringUtils.isNoneEmpty(auditUserName)){
				this.auditUserName = auditUserName;
			}
			if(CollectionUtils.isNotEmpty(msgList)){
				this.msgList = msgList;
			}
		}
	}
	
	
	
}














