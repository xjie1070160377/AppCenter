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

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.SpecialCategory;
import cn.mooc.app.module.cms.service.SpecialCategoryService;

/**
 * SpecialCategoryController
 * 专题分类控制器
 * 
 * @author hwt
 * @date 2016-05-06
 */
@Controller
@RequestMapping("/cms/specialCategory")
public class SpecialCategoryController extends CmsModuleController {

	@Autowired
	private SpecialCategoryService specialCategoryService;

	@RequestMapping("/specialCategoryList")
	public String specialCategoryList(Model model, PagerParam pageParam) {
		return ModuleView("/specialCategory/specialCategoryList");
	}

	@RequestMapping("/specialCategoryListJson")
	@ResponseBody
	public Map<String, Object> specialCategoryListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Integer siteId = getCurrentSiteId();

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<SpecialCategory> pageData = specialCategoryService.findSpecialCategoryList(searchParams, pageParam, siteId);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/specialCategoryAdd")
	public String specialCategoryAdd(Model model, SpecialCategory entity) {
		model.addAttribute("entity", new SpecialCategory());

		return ModuleView("/specialCategory/specialCategoryForm");
	}

	@RequestMapping("/specialCategoryEdit")
	public String specialCategoryEdit(Model model, Integer id) {
		SpecialCategory entity = specialCategoryService.getSpecialCategoryById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/specialCategory/specialCategoryForm");
	}

	@RequestMapping(value = "/specialCategorySave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialCategorySave(Model model, SpecialCategory entity) {
		Integer siteId = getCurrentSiteId();
		try {
			this.specialCategoryService.saveSpecialCategory(entity, siteId);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/specialCategoryUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialCategoryUpdate(@ModelAttribute("entity") SpecialCategory entity,  Model model) {

		try {
			this.specialCategoryService.updateSpecialCategory(entity);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/specialCategoryDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialCategoryDel(Integer id) {
		// 删除
		try {
			specialCategoryService.delSpecialCategory(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/specialCategoryDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialCategoryDels(Integer[] ids) {
		// 删除
		try {
			specialCategoryService.delSpecialCategorys(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public SpecialCategory preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? specialCategoryService.getSpecialCategoryById(oid) : null;
	}
}