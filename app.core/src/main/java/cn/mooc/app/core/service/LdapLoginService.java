package cn.mooc.app.core.service;

import cn.mooc.app.core.model.LdapPerson;

public interface LdapLoginService {
	
	public LdapPerson login(String username, String password);
	
}
