package cn.mooc.app.module.cms.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.SpecialService;

@Controller
public class SpecialsController {
	public static final String TEMPLATE = "/sys_special.html";
	public static final String TEMPLATE_LIST = "/sys_specials.html";
	public static final String TEMPLATE_LIST_MYMARK = "/sys_specials_mymark.html";
	@Autowired
	private SiteService siteService;
	@Autowired
	private SpecialService specialService;
	
	@RequestMapping(value = "/specials")
	public String index(Integer isRecommend,Integer page, HttpServletRequest request, Model model) {
		Site site = siteService.getWebCurrentSite();
		model.addAttribute("site", site);
		if(isRecommend != null){
			model.addAttribute("isRecommend", true);
		}else{
			model.addAttribute("isRecommend", false);
		}
		model.addAttribute("page", page);
		String template = site.getTemplate(TEMPLATE_LIST);
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		return TEMPLATE_LIST;
	}
	
	@RequestMapping(value = "/special/{id:[0-9]+}")
	public String special(@PathVariable Integer id, Integer page,
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("site", site);
		Special special = specialService.getSpecialById(id);
		
		modelMap.addAttribute("special", special);
		String template = site.getTemplate(TEMPLATE);
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		return TEMPLATE_LIST;
	}
	@RequestMapping(value = "/myMarkSpecials")
	public String myMarkSpecials(Integer page, HttpServletRequest request, Model model){
		Site site = siteService.getWebCurrentSite();
		model.addAttribute("site", site);
		model.addAttribute("page", page);
		String template = site.getTemplate(TEMPLATE_LIST_MYMARK);
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		return TEMPLATE_LIST_MYMARK;
	}
}
