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
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.service.AttributeService;

/**
 * AttributeController
 * 文档属性控制器
 * 
 * @author hwt
 * @date 2016-05-06
 */
@Controller
@RequestMapping("/cms/attribute")
public class AttributeController extends CmsModuleController {

	@Autowired
	private AttributeService attributeService;

	@RequestMapping("/attributeList")
	public String attributeList(Model model, PagerParam pageParam) {
		return ModuleView("/attribute/attributeList");
	}

	@RequestMapping("/attributeListJson")
	@ResponseBody
	public Map<String, Object> attributeListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Integer siteId = getCurrentSiteId();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<Attribute> pageData = attributeService.findAttributeList(searchParams, pageParam, siteId);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/attributeAdd")
	public String attributeAdd(Model model, Attribute entity) {
		model.addAttribute("entity", new Attribute());

		return ModuleView("/attribute/attributeForm");
	}

	@RequestMapping("/attributeEdit")
	public String attributeEdit(Model model, Integer id) {

		Attribute entity = attributeService.getAttributeById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/attribute/attributeForm");
	}

	@RequestMapping(value = "/attributeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> attributeSave(Model model, Attribute entity) {
		Integer siteId = getCurrentSiteId();
		try {
			this.attributeService.saveAttribute(entity, siteId);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/attributeUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> attributeUpdate(@ModelAttribute("entity") Attribute entity, Boolean scale, Boolean exact, Boolean watermark,  Model model) {

		try {
			entity.setScale(scale);
			entity.setExact(exact);
			entity.setWatermark(watermark);
			this.attributeService.updateAttribute(entity);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/attributeDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> attributeDel(Integer id) {
		// 删除
		try {
			attributeService.delAttribute(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/attributeDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> attributeDels(Integer[] ids) {
		// 删除
		try {
			attributeService.delAttributes(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public Attribute preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? attributeService.getAttributeById(oid) : null;
	}
}