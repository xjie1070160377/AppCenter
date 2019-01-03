package cn.mooc.app.core.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "t_sys_role")
public class SysRoleEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -860214671319458106L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String roleName;	
	/**
	 * 0:禁用,1:启用
	 */
	private int status = 1;
	private Date createTime;
	private Date lastUpdate;
	
	/**
	 * 角色类型，方便子模块实现角色的特殊功能
	 * 
	 * 0：只能拥有已赋权的菜单，1：默认拥有超级用户权限
	 */
	private int roleType = 0;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "roles")
	@JsonBackReference
	private Set<SysUserEntity> users = new HashSet<SysUserEntity>();

	/**
	 * 角色和菜单资源的多对多关系映射
	 */	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_sys_role_menu", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "menuId") })
	@JsonBackReference
	private Set<SysMenuEntity> menus = new HashSet<SysMenuEntity>();
	
	/**
	 * 角色和权限点的多对多关系映射
	 */	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_sys_role_perms", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "permId") })
	@JsonBackReference
	private Set<SysPermEntity> perms = new HashSet<SysPermEntity>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Set<SysMenuEntity> getMenus() {
		return menus;
	}
	public void setMenus(Set<SysMenuEntity> menus) {
		this.menus = menus;
	}
	public Set<SysPermEntity> getPerms() {
		return perms;
	}
	public void setPerms(Set<SysPermEntity> perms) {
		this.perms = perms;
	}
	public Set<SysUserEntity> getUsers() {
		return users;
	}
	public void setUsers(Set<SysUserEntity> users) {
		this.users = users;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	
	
	
}
