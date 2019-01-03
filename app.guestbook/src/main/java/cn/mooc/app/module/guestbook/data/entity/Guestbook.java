package cn.mooc.app.module.guestbook.data.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.common.collect.EnumMultiset;

/**
 * @author linwei
 * @description 用户留言本实体类
 */
@Document(collection = "t_guestbook_guestbook")
public class Guestbook {
	
	
	/**
	 * @description 留言状态
	 */
	public enum State{
		/**
		 * 禁止匿名
		 */
		noAnonymous("禁止匿名留言"),
		/**
		 * 允许匿名
		 */
		allowAnonymous("允许匿名留言");
		
		private final String value;
		
		State(String value) {
	        this.value = value;
	    }
		
	    public String getValue() {
	        return value;
	    }
	}

	@Id
	private String _id;
	
	@Indexed(unique = true, dropDups = true)
	private Long userId;
	

	/**
	 * 开通时间
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 留言本名称
	 */
	private String name;
	
	/**
	 * 图标url
	 */
	private String icon;
	
	/**
	 * 简介
	 */
	private String summary;
	
	/**
	 * 留言数
	 */
	private long total;
	
	/**
	 * 答复数
	 */
	private long replys;
	
	/**
	 * 是否允许匿名留言
	 */
	private State state;
	
	/**
	 * 排名
	 */
	private Integer ranking;
	
	/**
	 * 是否审核， 0：未审核，1已审核
	 */
	private Integer audit;
	
	/**
	 * 审核人ID
	 */
	private Integer auditUserId;
	
	/**
	 * 审核时间
	 */
	private Date auditTime;
	
	
	/**
	 * 是否官方认证 0：未认证，1已认证
	 */
	private Integer official;
	/**
	 * 信箱分类ID
	 */
	private String guestbookTypeId;
	
	/**
	 * 留言
	 */
	@DBRef
	private List<Message> guestBooks;

	/**
	 * getters and setters
	 */
	public String get_id() {
		return _id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public long getReplys() {
		return replys;
	}

	public void setReplys(long replys) {
		this.replys = replys;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public List<Message> getGuestBooks() {
		return guestBooks;
	}

	public void setGuestBooks(List<Message> guestBooks) {
		this.guestBooks = guestBooks;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public Integer getOfficial() {
		return official;
	}

	public void setOfficial(Integer official) {
		this.official = official;
	}

	public String getGuestbookTypeId() {
		return guestbookTypeId;
	}
	public void setGuestbookTypeId(String guestbookTypeId) {
		this.guestbookTypeId = guestbookTypeId;
	}

	public Integer getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Integer auditUserId) {
		this.auditUserId = auditUserId;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Guestbook() {
		super();
	}

	@PersistenceConstructor
	public Guestbook(String _id, Date createTime, String name, String icon, String summary, long total, long replys, State state, Integer ranking) {
		super();
		this._id = _id;
		this.createTime = createTime;
		this.name = name;
		this.icon = icon;
		this.summary = summary;
		this.total = total;
		this.replys = replys;
		this.state = state;
		this.ranking = ranking;
	}
	
}


