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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="t_sys_permission")
public class SysPermEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4178893848482734435L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String permName;
	
	private String permCode;

	private int sort;

	private Date createTime;

	private Date lastUpdate;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,	CascadeType.MERGE}, mappedBy = "perms")
	@JsonBackReference
	private Set<SysRoleEntity> roles = new HashSet<SysRoleEntity>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,	CascadeType.MERGE}, mappedBy = "perms")
	@JsonBackReference
	private Set<SysMenuEntity> menus = new HashSet<SysMenuEntity>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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

	public Set<SysMenuEntity> getMenus() {
		return menus;
	}

	public void setMenus(Set<SysMenuEntity> menus) {
		this.menus = menus;
	}


	
	
	
}
