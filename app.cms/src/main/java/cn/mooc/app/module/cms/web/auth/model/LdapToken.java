package cn.mooc.app.module.cms.web.auth.model;

import org.apache.shiro.authc.UsernamePasswordToken;

public class LdapToken extends UsernamePasswordToken {

	private boolean ldapLoginSuccess;

	public boolean isLdapLoginSuccess() {
		return ldapLoginSuccess;
	}

	public void setLdapLoginSuccess(boolean ldapLoginSuccess) {
		this.ldapLoginSuccess = ldapLoginSuccess;
	}
}
