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
@Table(name = "t_sys_menu")
public class SysMenuEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8438331195734615952L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String menuName;
	private String menuCode;
	private long parentId;
	private String menuUrl;
	private String urlTarget = "frameContent";
	private int navMenu = 1;
	/**
	 * 菜单类型,0:会员菜单,1:后台菜单
	 */
	private int menuArea = 1;
	private int sort;
	private String iconCls;
	private String remark;
	private Date createTime;
	private Date lastUpdate;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "menus")
	@JsonBackReference
	private Set<SysRoleEntity> roles = new HashSet<SysRoleEntity>();
	
	/**
	 * 角色和权限点的多对多关系映射
	 */	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_sys_menu_perms", joinColumns = { @JoinColumn(name = "menuId") }, inverseJoinColumns = { @JoinColumn(name = "permId") })
	@JsonBackReference
	private Set<SysPermEntity> perms = new HashSet<SysPermEntity>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getUrlTarget() {
		return urlTarget;
	}
	public void setUrlTarget(String urlTarget) {
		this.urlTarget = urlTarget;
	}	
	public int getNavMenu() {
		return navMenu;
	}
	public void setNavMenu(int navMenu) {
		this.navMenu = navMenu;
	}
	
	public int getMenuArea() {
		return menuArea;
	}
	public void setMenuArea(int menuArea) {
		this.menuArea = menuArea;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Set<SysPermEntity> getPerms() {
		return perms;
	}
	public void setPerms(Set<SysPermEntity> perms) {
		this.perms = perms;
	}
	
	
}
