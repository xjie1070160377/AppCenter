package cn.mooc.app.module.push.data.entity;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CmsIosToken entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cms_ios_token")
public class IosToken implements java.io.Serializable {

	// Fields

	private String token;
	private Boolean isios;
	private Integer failCount;
	private Long id;


	@Column(name = "token", nullable = false, length = 100)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "failCount")
	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}
	
	@Id
	@Column(name = "user_id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "isios", nullable = false, length = 1)
	public Boolean getIsios() {
		return isios;
	}

	public void setIsios(Boolean isios) {
		this.isios = isios;
	}
}