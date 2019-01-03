package cn.mooc.app.module.points.mcenter.controller;

import cn.mooc.app.core.controller.ModuleController;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.SiteService;

public class PointsModuleController  extends ModuleController {

	@Override
	public String getModuleName() {
		
		return "points";
	}
	
	public int getCurrentSiteId() {
		//
		Site site = this.getCurrentSite();
		if(site!=null){
			return site.getId();
		}else{
			return 1;
		}
	}
	
	public Site getCurrentSite(){
		SiteService siteService = this.webContext.getWebApplicationContext().getBean(SiteService.class);
		return siteService.getCurrentSite();
		
	}
	
	public Site changeCurrentSite(Site site){
		SiteService siteService = this.webContext.getWebApplicationContext().getBean(SiteService.class);
		return siteService.changeCurrentSite(site);
	}

}
