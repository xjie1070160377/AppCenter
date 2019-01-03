package cn.mooc.app.core.web.shiro.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface LogoutExtend {

	public void beforeLogout(ServletRequest request, ServletResponse response) throws Exception;
	
}
