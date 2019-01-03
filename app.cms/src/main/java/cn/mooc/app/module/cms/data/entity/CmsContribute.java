package cn.mooc.app.module.cms.data.entity;

import java.io.Serializable;

import javax.persistence.*;

import cn.mooc.app.core.data.entity.SysUserEntity;

import java.util.List;


/**
 * The persistent class for the t_cms_contribute database table.
 * 
 */
@Entity
@Table(name="t_cms_contribute")
public class CmsContribute implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String cardNo;

	private String company;

	private String email;

	private String mobileno;

	private String name;

	private String realName;

	private Long userId;

	@Id
	@Column(name = "cont_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_t_cms_contribute", pkColumnValue = "t_cms_contribute", table = "t_id_table", pkColumnName = "table_name", valueColumnName = "id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_t_cms_contribute")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "card_no", length = 20)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "company", length = 20)
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "mobileno", length = 20)
	public String getMobileno() {
		return this.mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	@Column(name = "name", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "real_name", length = 20)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}