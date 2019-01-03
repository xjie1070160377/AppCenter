package cn.mooc.app.module.cms.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.module.cms.data.entity.Site;

@Service
public class CmsContext {

	@Autowired
	private WebContext webContext;

	@Autowired
	private SiteService siteService;

	public WebContext getWebContext() {
		return webContext;
	}
	
	public Site getSite(int siteId){
		return siteService.getSiteById(siteId);
	}
	
	public Site getWebCurrentSite() {
		return siteService.getWebCurrentSite();
	}
	
	public static Site getWebCurrentSite(HttpServletRequest request) {
		return SiteService.getWebCurrentSite(request);
		
	}
	


	
	
}
