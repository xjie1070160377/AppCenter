package cn.mooc.app.core.web.shiro.auth.db;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

/**
 * 
 * shiro默认提供的filter
 * 
		anon			org.apache.shiro.web.filter.authc.AnonymousFilter
		authc			org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		authcBasic	org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
		logout		org.apache.shiro.web.filter.authc.LogoutFilter
		noSessionCreation	org.apache.shiro.web.filter.session.NoSessionCreationFilter
		perms		org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
		port			org.apache.shiro.web.filter.authz.PortFilter
		rest			org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
		roles			org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
		ssl				org.apache.shiro.web.filter.authz.SslFilter
		user			org.apache.shiro.web.filter.authc.UserFilter
		
 * @author Taven
 *
 */
public class WebDbPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		
		return super.isAccessAllowed(request, response, mappedValue);
		
	}
	
}
