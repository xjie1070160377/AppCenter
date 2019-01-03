package cn.mooc.app.module.widget.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.SiteService;

@RequestMapping("/widget")
@Controller
public class DownAppController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InfoService infoQueryService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private MobileCommonService mobileCommonService;
	
	@RequestMapping("/getApp")
	public String getApp(Model model,HttpServletRequest request,HttpServletResponse response) {
		
		logger.debug("分享用户进入下载页面");
		
		return "/widget/getApp.html";
	}
	
	@RequestMapping("/getQrcode")
	public String getQrcode(Model model,HttpServletRequest request,HttpServletResponse response) {
		
		return "/widget/qrcode.html";
	}
	
	@RequestMapping("/getMovie")
	public String getMovie(Model model,HttpServletRequest request,HttpServletResponse response) {
		Site site = mobileCommonService.getCurrentMobileSite();
		if(site == null){
			site = this.siteService.getDefaultSite();
		}
		
		Sort sort = new Sort(Direction.ASC, "columnSort").and(new Sort(Direction.DESC, "priority", "publishDate", "id"));
		PagerParam pagerParam = new PagerParam(1, 1, sort);
		
		Boolean isWithImage = null;
		//影讯的专题ID为：50
		Integer[] specialId = new Integer[] { 50 };
		
		InfoFindListPageParams infoFindListPageParams = new InfoFindListPageParams();
		infoFindListPageParams.setSpecialId(specialId);
		infoFindListPageParams.setSiteId(new Integer[] { site.getId() });
		infoFindListPageParams.setIsWithImage(isWithImage);
		infoFindListPageParams.setStatus(new String[] { Info.NORMAL });
		
		int articleId = 6291;
		
		Page<Info> page = infoQueryService.findPage(infoFindListPageParams, pagerParam);
		if(page!=null && page.getContent()!=null && page.getContent().size()>0){
			articleId = page.getContent().get(0).getId();
		}
		
		return "redirect:/widget/share/detail/"+ articleId;
	}
	
	
}
