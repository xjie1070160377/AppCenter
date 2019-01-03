package cn.mooc.app.module.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.web.interceptors.ModuleInterceptorRegistry;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.SiteService;


public class ApiSiteInterceptor  extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SiteService siteService;
	@Autowired
	protected WebContext webContext;
	@Autowired
	private ModuleInterceptorRegistry moduleInterceptorRegistry;
	
	private String[] includePatterns;
	
	public void init(){

		moduleInterceptorRegistry.registry(includePatterns, null, this);
	}
	
	public void setIncludePatterns(String[] includePatterns) {
		this.includePatterns = includePatterns;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//URL带域名
		boolean withDomain = true;
		String domain = request.getServerName();
		
		Site site = null;
		if(withDomain){
			site = siteService.getSiteByDomain(domain);
		}		
		
		if (site == null) {
			site = siteService.getDefaultSite();
		}
		
		if (site == null) {
			throw new Exception("无可用的站点");
		}
		
	
		siteService.setWebCurrentSite(site);
		
		request.getSession().setAttribute("siteId", site.getId());
		
		//Cookie theme = WebUtils.getCookie(request, COOKIE_THEME);
		String requestURI = request.getRequestURI();
		logger.debug("requestURI：{}", requestURI);
		
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex) throws Exception {
		siteService.resetWebCurrentSite();
		
	}
	
}
