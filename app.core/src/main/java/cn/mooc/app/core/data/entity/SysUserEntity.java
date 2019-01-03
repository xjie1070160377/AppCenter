package cn.mooc.app.core.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "t_sys_user")
public class SysUserEntity implements Serializable {

	private static final long serialVersionUID = 1481708278710856213L;
	
	/**
	 * 校内用户
	 */
	public final static int inner_user = 10;
	/**
	 * 校外用户
	 */
	public final static int out_user = 0;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "userName")	
	private String userName;
	
	@Column(name = "passWord")
	private String passWord;
	
	@Column(name = "pwdSalt")
	private String pwdSalt;
	
	/**
	 * 0:禁用,1:启用
	 */
	@Column(name = "status")
	private int status;
	
	/**
	 * 扩展字段，用户类型/用户分类；
	 * 红客系统中约定：0：校外用户，10：校内实名用户
	 * 其他系统可以根据自己的实际需求进行约定
	 */
	@Column(name = "uType")
	private int uType;
	
	/**
	 * 冗余字段，用户别名
	 */
	@Column(name = "aliasName")
	private String aliasName;

	@Column(name = "superUser")
	private boolean superUser;
	
	@Column(name = "createTime")
	private Date createTime;
	
	@Column(name = "lastUpdate")
	private Date lastUpdate;
	
	/**
	 * 用户和角色的关系
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_sys_user_role", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })	
	private Set<SysRoleEntity> roles;
		
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL}, mappedBy = "sysUser")
	//@PrimaryKeyJoinColumn
	@JsonBackReference
	private SysUserVCoin userVCoin;

	public long getId() {
		return id;
	}
	
	public int getIntId(){
		return Integer.parseInt(id+"");
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public String getPwdSalt() {
		return pwdSalt;
	}

	public void setPwdSalt(String pwdSalt) {
		this.pwdSalt = pwdSalt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSuperUser() {
		return superUser;
	}

	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<SysRoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRoleEntity> roles) {
		this.roles = roles;
	}

	public SysUserVCoin getUserVCoin() {
		return userVCoin;
	}

	public void setUserVCoin(SysUserVCoin userVCoin) {
		this.userVCoin = userVCoin;
	}

	public int getuType() {
		return uType;
	}

	public void setuType(int uType) {
		this.uType = uType;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	
}
