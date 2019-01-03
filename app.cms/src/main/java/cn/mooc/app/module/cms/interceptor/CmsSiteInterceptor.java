package cn.mooc.app.module.cms.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.web.interceptors.ModuleInterceptorRegistry;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.CmsCacheService;
import cn.mooc.app.module.cms.service.SiteService;

@Service
public class CmsSiteInterceptor  extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ModuleInterceptorRegistry moduleInterceptorRegistry;
	@Autowired
	private SiteService siteService;
	@Autowired
	protected WebContext webContext;
	@Autowired
	private CmsCacheService cmsCacheService;
	
	@PostConstruct
	public void init(){
		String[] includePatterns = new String[]{"/**"};
		String[] excludePatterns = new String[]{"/*.ico","/uploads/**","/Uploads/**","/static/**","/theme/**","/error/**","/m-**","/tg-**","/widget/**","/views/**"};
		moduleInterceptorRegistry.registry(includePatterns, excludePatterns, this);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//URL带域名
		boolean withDomain = true;
		String domain = request.getServerName();
		
		Site site = null;
		if(withDomain){
			site = cmsCacheService.getSiteByDomainCache(domain);
		}		
		
		if (site == null) {
			site = cmsCacheService.getDefaultSiteCache();
		}
		
		if (site == null) {
			throw new Exception("无可用的站点");
		}
		
	
		siteService.setWebCurrentSite(site);
		
		//模板对应标签 ${session.themeRoot} 
		String themeRoot = this.siteService.getWebThemeRoot();
		request.getSession().setAttribute("themeRoot", themeRoot);
		request.getSession().setAttribute("themePath", this.siteService.getThemePath());
		
		request.getSession().setAttribute("siteId", site.getId());
		
		//Cookie theme = WebUtils.getCookie(request, COOKIE_THEME);
		String requestURI = request.getRequestURI();
		logger.debug("requestURI：{}", requestURI);
		
		if(site.getNeedLogin()==1 && !webContext.userHasLogin()){
			String loginUrl = this.siteService.getULoginUrl();
			if(StringUtils.isBlank(requestURI) || this.notInExcludes(requestURI)){
				String ctx = site.getContextPath();
				ctx = StringUtils.isEmpty(ctx) ? "" : ctx;
				WebUtils.issueRedirect(request, response, ctx + loginUrl);
				return false;
			}
			
		}
		
		return true;
		
	}
	
	private boolean notInExcludes(String requestURI){
		String loginUrl = this.siteService.getULoginUrl();
		return !requestURI.contains(loginUrl) && !requestURI.contains("/app.htx")  && !requestURI.contains("/download_apk.htx") && !requestURI.contains("/showVerifyCode");
	}
	
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex) throws Exception {
		siteService.resetWebCurrentSite();
		
	}
	
}
