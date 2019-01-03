package cn.mooc.app.module.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.utils.MessageFormatUtil;
import cn.mooc.app.module.api.utils.ShortUrlUtil;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.CmsContext;
import cn.mooc.app.module.cms.service.SiteService;

@Service
public class MobileCommonService {

	private static final String WEB_SESSION_SITE_NAME = "webSite";
	
	@Autowired
	private CmsContext cmsContext;
	@Autowired
	private SiteService siteService;
	
	public Site getCurrentMobileSite() {
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		//return this.cmsContext.getSite(1);
		return siteService.getWebCurrentSite();
	}
	
	public Site getCurrentSite(HttpServletRequest request) {
		Site site = (Site) request.getSession().getAttribute(WEB_SESSION_SITE_NAME);
		return site;
		
	}
	
	public String getInfoUrl(int articleId){
		//https://mapp.nudt.edu.cn/widget/share/detail/5599
		
		return MessageFormatUtil.format("https://mapp.nudt.edu.cn/widget/share/detail/{}", articleId);
		
	}
	
	public String getShortUrl(String url){
		//https://mapp.nudt.edu.cn/widget/url/6Vf2Ir
		//这里将urlCode与真实url写入Redis （暂不实现，等待后期需求）
		String urlCode = ShortUrlUtil.ShortCodeFirst(url);
		return MessageFormatUtil.format("https://mapp.nudt.edu.cn/widget/url/{}", urlCode);
		
	}
	
	public String getShortUrlForInfo(int articleId){
		String realUrl = this.getInfoUrl(articleId);
		
		return this.getShortUrl(realUrl);
		
	}
	
	
	
}
