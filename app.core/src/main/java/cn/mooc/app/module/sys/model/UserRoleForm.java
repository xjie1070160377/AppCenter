package cn.mooc.app.module.sys.model;

import java.util.ArrayList;
import java.util.List;

public class UserRoleForm {

	private long id;	
	private String userName;
	private List<Long> roles = new ArrayList<Long>();
	
	public long getId() {
		return id;
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
	public List<Long> getRoles() {
		return roles;
	}
	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}
	
	
	
}
