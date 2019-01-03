package cn.mooc.app.module.cms.mcenter.controller;

import java.util.HashMap;
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
import cn.mooc.app.module.cms.data.entity.AppVersion;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.AppVersionService;

/**
 * AppVersionController
 * App版本管理控制器
 * 
 * @author Taven
 * @date 2016-05-16
 */
@Controller
@RequestMapping("/cms/appVersion")
public class AppVersionController extends CmsModuleController {

	@Autowired
	private AppVersionService appVersionService;

	@RequestMapping("/appVersionList")
	public String appVersionList(Model model, PagerParam pageParam) {
		return ModuleView("/appVersion/appVersionList");
	}

	@RequestMapping("/appVersionListJson")
	@ResponseBody
	public Map<String, Object> appVersionListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		int siteId = this.getCurrentSiteId();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_site.id", siteId);
		searchParams.put(columnFiled, keyWord);

		Page<AppVersion> pageData = appVersionService.findAppVersionList(searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/appVersionAdd")
	public String appVersionAdd(Model model) {
		int siteId = this.getCurrentSiteId();
		
		AppVersion app = new AppVersion();
		app.setIsforce(0);
		model.addAttribute("siteId", siteId);
		model.addAttribute("entity", app);

		return ModuleView("/appVersion/appVersionForm");
	}

	@RequestMapping("/appVersionEdit")
	public String appVersionEdit(Model model, Integer id) {
		int siteId = this.getCurrentSiteId();
		AppVersion entity = appVersionService.getAppVersionById(id);
		
		model.addAttribute("siteId", siteId);
		model.addAttribute("entity", entity);

		return ModuleView("/appVersion/appVersionForm");
	}

	@RequestMapping(value = "/appVersionSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> appVersionSave(Model model, AppVersion entity) {

		try {
			int siteId = this.getCurrentSiteId();
			Site site = new Site();
			site.setId(siteId);
			entity.setSite(site);
			this.appVersionService.saveAppVersion(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/appVersionUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> appVersionUpdate(AppVersion entity,  Model model) {
		try {
			this.appVersionService.updateAppVersion(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/appVersionDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> appVersionDel(Integer id) {
		// 删除
		try {
			appVersionService.delAppVersion(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/appVersionDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> appVersionDels(Integer[] ids) {
		// 删除
		try {
			appVersionService.delAppVersions(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public AppVersion preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid!=null ? appVersionService.getAppVersionById(oid) : null;
	}
}