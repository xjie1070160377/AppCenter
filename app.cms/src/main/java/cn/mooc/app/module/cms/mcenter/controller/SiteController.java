package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Global;
import cn.mooc.app.module.cms.data.entity.PublishPoint;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.SiteModel;
import cn.mooc.app.module.cms.service.SiteService;

/**
 * SiteController
 * 站点信息控制器
 * 
 * @author Taven
 * @date 2016-05-13
 */
@Controller
@RequestMapping("/cms/site")
public class SiteController extends CmsModuleController {

	@Autowired
	private SiteService siteService;
	
	@RequestMapping("/changeSite")
	@ResponseBody
	public Map<String, Object> changeSite(Model model, int siteId) {
		
		Site site = this.siteService.getSiteById(siteId);
		if(site!=null){
			this.changeCurrentSite(site);
			return HttpResponseUtil.successJson();
		}else{
			return HttpResponseUtil.failureJson("站点不存在");
		}
		
	}

	@RequestMapping("/siteSettings")
	public String siteSettings(Model model) {
		
		return ModuleView("/site/siteSettings");
	}
	
	@RequestMapping("/siteList")
	public String siteList(Model model, PagerParam pageParam) {
		Global global = siteService.getDefaultGlobal();
		
		model.addAttribute("global", global);
		
		return ModuleView("/site/siteList");
	}
	
	@RequestMapping(value = "/globalUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> globalUpdate(Global entity,  Model model) {
		try {
			this.siteService.updateGlobal(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping("/siteListJson")
	@ResponseBody
	public Map<String, Object> siteListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<SiteModel> pageData = siteService.findSiteList(searchParams, pageParam);		
		
		return HttpResponseUtil.successJson(pageData, pageParam);
		
	}

	@RequestMapping("/siteAdd")
	public String siteAdd(Model model) {		
		Site site = new Site();
		site.setDef(false);
		site.setStatus(1);
		site.setShowIndex(new Byte("1"));
				
		List<String> themeList = new ArrayList<String>();
		themeList.add("default");
		
		model.addAttribute("entity", site);
		model.addAttribute("themeList", themeList);

		return ModuleView("/site/siteForm");
	}

	@RequestMapping("/siteEdit")
	public String siteEdit(Model model, Integer id) {

		Site entity = siteService.getSiteById(id);
		
		List<String> themeList = this.siteService.getSiteTemplateList(id);		
		
		model.addAttribute("entity", entity);
		model.addAttribute("themeList", themeList);

		return ModuleView("/site/siteForm");
	}

	@RequestMapping(value = "/siteSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> siteSave(Model model, Site entity) {

		try {
			Global global = new Global();
			global.setId(1);
			
			PublishPoint publishPoint = new PublishPoint();
			publishPoint.setId(1);
			
			entity.setGlobal(global);
			entity.setDef(false);
			entity.setHtmlPublishPoint(publishPoint);
			entity.setIdentifyDomain(false);
			entity.setIsStaticHome("0");
			entity.setTreeLevel(0);
			entity.setTreeNumber("0000");
			entity.setTreeMax("0000");
			
			this.siteService.saveSite(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/siteUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> siteUpdate(@ModelAttribute("entity") Site entity,  Model model, Boolean def) {
		try {
			if(def == null){
				entity.setDef(false);
			}
			this.siteService.updateSite(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/siteDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> siteDel(Integer id) {
		// 删除
		try {
			siteService.delSite(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/siteDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> siteDels(Integer[] ids) {
		// 删除
		try {
			siteService.delSites(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public Site preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid!=null ? siteService.getSiteById(oid) : null;
	}
	
	
}