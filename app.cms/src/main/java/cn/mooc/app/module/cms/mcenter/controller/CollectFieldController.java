package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.CmsModelField;
import cn.mooc.app.module.cms.data.entity.Collect;
import cn.mooc.app.module.cms.data.entity.CollectField;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.CollectFieldService;
import cn.mooc.app.module.cms.service.CollectService;
import cn.mooc.app.module.cms.util.Reflections;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CollectFieldController
 * 采集字段控制器
 * 
 * @author linwei
 * @date 2017-06-23
 */
@Controller
@RequestMapping("/cms/collectField")
public class CollectFieldController extends CmsModuleController {

	@Autowired
	private CollectService collectService;
	@Autowired
	private CollectFieldService collectFieldService;
	
	public static final String[] EXCLUDE_FIELDS = { "node", "specials",
			"nodes", "viewGroups", "allowComment", "infoTemplate",
			"attributes", "priority", "tagKeywords", "color", "infoPath",
			"doc", "files", "images" };

	public static final String[] INCLUDE_FIELDS = { "id", "next", "views",
			"downloads" };

	@RequestMapping("/collectFieldList")
	public String collectFieldList(Model model, Integer collectId) {
		Collect collect = collectService.getCollectById(collectId);
		List<CollectField> fieldList = collect.getFields();
		model.addAttribute("collect", collect);
		model.addAttribute("list", fieldList);
		return ModuleView("/collectField/collectFieldList");
	}

	@RequestMapping("/collectFieldListJson")
	@ResponseBody
	public Map<String, Object> collectFieldListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Page<CollectField> pageData = collectFieldService.findCollectFieldPage(searchParams, pageParam);
		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/collectFieldAdd")
	public String collectFieldAdd(Model model, Integer collectId) {
		Collect collect = collectService.getCollectById(collectId);
		List<CollectField> collectFields = collect.getFields();
		CmsModel cmsModel = collect.getNode().getInfoModel();
		List<CmsModelField> modelFields = cmsModel.getEnabledFields();
		List<Map<String, String>> list = getFields(modelFields, collectFields);
		model.addAttribute("collect", collect);
		model.addAttribute("list", list);
		return ModuleView("/collectField/collectFieldForm");
	}

	@RequestMapping("/collectFieldEdit")
	public String collectFieldEdit(Model model, Integer id) {

		CollectField entity = collectFieldService.getCollectFieldById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/collectField/collectFieldForm");
	}

	@RequestMapping(value = "/collectFieldSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectFieldSave(Model model, Integer collectId,
			String[] code, String[] name,Integer[] type) {
		try {
			collectFieldService.save(code, name, type, collectId, getCurrentSiteId());
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}
	
	
	@RequestMapping(value = "/collectFieldUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectFieldUpdate(Integer collectId, Integer[] id, String[] name,
			Integer[] sourceType, String[] sourceText, String[] sourceUrl,
			String[] dataPattern, Boolean[] dataReg, String[] dataAreaPattern,
			Boolean[] dataAreaReg, String[] filter, String[] downloadType,
			String[] imageParam, String[] dateFormat) {
		try {
			collectFieldService.update(id, sourceType, sourceText,
					sourceUrl, dataPattern, dataReg, dataAreaPattern, dataAreaReg,
					filter, downloadType, imageParam, dateFormat);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/collectFieldDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectFieldDel(Integer id) {
		// 删除
		try {
			collectFieldService.delCollectField(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/collectFieldDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectFieldDels(Integer[] ids) {
		// 删除
		try {
			collectFieldService.delCollectFields(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public CollectField preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? collectFieldService.getCollectFieldById(oid) : null;
	}
	
	private static List<Map<String, String>> getFields(
			List<CmsModelField> modelFields, List<CollectField> collectFields) {
		List<Map<String, String>> fields = new ArrayList<Map<String, String>>();
		List<Object> codes = Reflections.getPropertyList(collectFields, "code");
		Map<String, String> map;
		for (String code : INCLUDE_FIELDS) {
			if (!codes.contains(code)) {
				map = new HashMap<String, String>();
				map.put("code", code);
				map.put("type", String.valueOf(CollectField.TYPE_SYSTEM));
				fields.add(map);
			}
		}
		for (CmsModelField f : modelFields) {
			String name = f.getName();
			if (!ArrayUtils.contains(EXCLUDE_FIELDS, name)
					&& !codes.contains(name)) {
				map = new HashMap<String, String>();
				map.put("code", f.getName());
				map.put("name", f.getLabel());
				int type;
				if (f.isPredefined()) {
					type = CollectField.TYPE_SYSTEM;
				} else if (f.isClob()) {
					type = CollectField.TYPE_CLOB;
				} else {
					type = CollectField.TYPE_CUSTOM;
				}
				map.put("type", String.valueOf(type));
				fields.add(map);
			}
		}
		return fields;
	}
}