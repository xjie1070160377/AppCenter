package cn.mooc.app.module.push.data.entity;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * CmsIosMessagePedding entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cms_ios_message_pedding")
public class IosMessagePedding implements java.io.Serializable {

	// Fields

	private Integer id;
	private String token;
	private String title;
	private String message;
	private Integer travel;
	private Date createTime;

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_ios_message_pedding", pkColumnValue = "t_cms_ios_message_pedding", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_ios_message_pedding")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "token", length = 100)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "message", length = 200)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "travel")
	public Integer getTravel() {
		return travel;
	}

	public void setTravel(Integer travel) {
		this.travel = travel;
	}
	@Column(name = "createtime", length = 19)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}