package cn.mooc.app.core.web.shiro.auth.db;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

public class WebDbRolesAuthorizationFilter extends RolesAuthorizationFilter {

	
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		
		return super.isAccessAllowed(request, response, mappedValue);
		
	}
	
}
