package cn.mooc.app.core.web.shiro.auth.db;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUserFilter  extends UserFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		//super.redirectToLogin(request, response);
		HttpServletRequest _request = (HttpServletRequest) request;
		String _loginUrl;
		if (_request.getRequestURI().startsWith(_request.getContextPath() +"/mcenter")) {
			_loginUrl = "/mcenter/login";
		}else if(_request.getRequestURI().startsWith(_request.getContextPath() +"/ucenter")) {
			_loginUrl = "/login";
		}else {
		
			_loginUrl = getLoginUrl();
		}
		
        WebUtils.issueRedirect(request, response, _loginUrl);
		
		
    }
	
	
}
