package cn.mooc.app.core.web.shiro.auth.db;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.web.shiro.auth.filter.AuthFilterRegistry;
import cn.mooc.app.core.web.shiro.auth.filter.LogoutExtend;

public class WebLogoutFilter extends LogoutFilter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 返回URL
	 */
	public static final String FALLBACK_URL_PARAM = "fallbackUrl";
	
	@Autowired
	private AuthFilterRegistry authFilterRegistry;

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
		for (LogoutExtend logoutExtend : authFilterRegistry.getLogoutExtends()) {
			try{
			logoutExtend.beforeLogout(request, response);
			}catch (Exception e) {
				logger.error("beforeLogout", e);
			}
		}
		
		return super.preHandle(request, response);
	}

	protected String getRedirectUrl(ServletRequest req, ServletResponse resp,
			Subject subject) {
		HttpServletRequest request = (HttpServletRequest) req;
		String redirectUrl = request.getParameter(FALLBACK_URL_PARAM);
		if (StringUtils.isBlank(redirectUrl)) {
			return super.getRedirectUrl(request, resp, subject);
		}else{
			return redirectUrl;
		}
	}
}
