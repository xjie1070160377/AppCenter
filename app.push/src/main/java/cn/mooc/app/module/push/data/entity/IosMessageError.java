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
 * CmsIosMessageError entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cms_ios_message_error")
public class IosMessageError implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7568909977502439532L;
	// Fields

	private Integer id;
	private String token;
	private String title;
	private String message;
	private String errorMsg;
	private Date createTime;
	private Integer userId;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_ios_message_error", pkColumnValue = "t_cms_ios_message_error", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_ios_message_error")
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
	@Column(name = "error_msg")
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Column(name = "createtime", length = 19)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}