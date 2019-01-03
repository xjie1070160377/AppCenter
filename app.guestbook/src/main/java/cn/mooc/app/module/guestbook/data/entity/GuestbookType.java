package cn.mooc.app.module.guestbook.data.entity;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author linwei
 * @description 信箱分类
 */

@Document(collection = "t_guestbook_guestbookType")
public class GuestbookType {

	@Id
	private String _id;
	
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 排序
	 */
	private Integer order;
	/**
	 * 创建时间
	 */
	@Indexed(direction = IndexDirection.DESCENDING)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date crateTime;

	
	public String get_id() {
		return _id;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public GuestbookType() {
		super();
	}

	
}
