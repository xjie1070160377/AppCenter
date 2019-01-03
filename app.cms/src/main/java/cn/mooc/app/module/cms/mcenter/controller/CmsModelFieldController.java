package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.CmsModelField;
import cn.mooc.app.module.cms.service.CmsModelFieldService;
import cn.mooc.app.module.cms.service.CmsModelService;
import cn.mooc.app.module.cms.support.Constants;
import cn.mooc.app.module.cms.support.Servlets;

/**
 * CmsModelFieldController 模型字段管理控制器
 * 
 * @author hwt
 * @date 2016-06-13
 */
@Controller
@RequestMapping("/cms/modelField")
public class CmsModelFieldController extends CmsModuleController {
	private static final Logger logger = LoggerFactory.getLogger(CmsModelFieldController.class);

	@Autowired
	private CmsModelFieldService modelFieldService;
	@Autowired
	private CmsModelService modelService;

	@RequestMapping("/modelFieldList")
	public String modelFieldList(Integer modelId, Model modelMap) {
		CmsModel model = modelService.getCmsModelById(modelId);
		List<String> types = CmsModel.getTypes();

		modelMap.addAttribute("model", model);
		modelMap.addAttribute("modelId", modelId);
		modelMap.addAttribute("types", types);
		modelMap.addAttribute("queryType", model.getType());
		return ModuleView("/modelField/modelFieldList");
	}

	@RequestMapping("/modelFieldListJson")
	@ResponseBody
	public Map<String, Object> modelFieldListJson(Integer modelId) {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		List<CmsModelField> list = modelFieldService.findList(modelId);
		for (CmsModelField field : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", field.getId());
			map.put("name", field.getName());
			map.put("label", field.getLabel());
			map.put("dblColumn", field.getDblColumn());
			map.put("disabled", field.getDisabled());
			map.put("isCustom", field.isCustom());
			map.put("isClob", field.isClob());
			map.put("isPredefined", field.isPredefined());
			map.put("seq", field.getSeq());
			results.add(map);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rows", results);
		return resMap;
	}

	@RequestMapping("/predefinedList")
	public String predefinedList(Integer modelId, Model modelMap) {
		CmsModel model = modelService.getCmsModelById(modelId);
		
		
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("modelId", modelId);
		String path = CmsModel.getPaths().get(model.getType());
		return ModuleView(path + "model_predefined_list");
	}

	@RequestMapping("/modelFieldAdd")
	public String modelFieldAdd(Integer modelId, Integer id, Model modelMap) {
		CmsModel model = modelService.getCmsModelById(modelId);
		CmsModelField bean = new CmsModelField();
		if (id != null) {
			bean = modelFieldService.getCmsModelFieldById(id);
		}

		modelMap.addAttribute("model", model);
		modelMap.addAttribute("modelId", modelId);
		modelMap.addAttribute(Constants.OPRT, Constants.CREATE);
		modelMap.addAttribute("bean", bean);
		return ModuleView("/modelField/model_field_form");
	}

	@RequestMapping("/modelFieldEdit")
	public String modelFieldEdit(Integer modelId, Model modelMap, Integer id) {
		CmsModel model = modelService.getCmsModelById(modelId);

		CmsModelField bean = modelFieldService.getCmsModelFieldById(id);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("modelId", modelId);
		modelMap.addAttribute(Constants.OPRT, Constants.EDIT);
		return ModuleView("/modelField/model_field_form");
	}

	@RequestMapping(value = "/modelFieldSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelFieldSave(Integer modelId, Model model, CmsModelField bean, Boolean clob, HttpServletRequest request) {

		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		try {
			bean = modelFieldService.save(bean, modelId, customs, clob);
			logger.info("save CmsModelField, name={}.", bean.getName());
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}

		return HttpResponseUtil.successJson(bean.getId().toString());
	}

	@RequestMapping(value = "/modelFieldUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelFieldUpdate(@ModelAttribute("bean") CmsModelField bean, Model model, Boolean clob, HttpServletRequest request) {
		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		try {
			modelFieldService.update(bean, customs, clob);
			logger.info("update CmsModelField, name={}.", bean.getName());
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchSave(Integer modelId, String[] name, String[] label,
			Boolean[] dblColumn, String[] property, String[] custom) {
		try {
			if (ArrayUtils.isNotEmpty(name)) {
				if(name.length==1){
					property = new String[]{StringUtils.join(property, ",")};
					custom = new String[]{StringUtils.join(custom, ",")};
				}
				CmsModelField[] beans = modelFieldService.batchSave(modelId, name, label, dblColumn, property, custom);
				for (CmsModelField bean : beans) {
					logger.info("update CmsModelField, name={}.", bean.getName());
				}
			}
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("批量新增失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdate(Integer[] id, String[] name, String[] label, Boolean[] dblColumn) {
		try {
			if (ArrayUtils.isNotEmpty(id)) {
				CmsModelField[] beans = modelFieldService.batchUpdate(id, name, label, dblColumn);
				for (CmsModelField bean : beans) {
					logger.info("update CmsModelField, name={}.", bean.getName());
				}
			}
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("批量保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disable(Integer[] ids) {
		// 删除
		try {
			CmsModelField[] beans = modelFieldService.disable(ids);
			for (CmsModelField bean : beans) {
				logger.info("disable CmsModelField, name={}.", bean.getName());
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> enable(Integer[] ids) {
		// 删除
		try {
			CmsModelField[] beans = modelFieldService.enable(ids);
			for (CmsModelField bean : beans) {
				logger.info("enable CmsModelField, name={}.", bean.getName());
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/modelFieldDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelFieldDel(Integer[] ids) {
		// 删除
		try {
			CmsModelField[] beans = modelFieldService.delCmsModelField(ids);
			for (CmsModelField bean : beans) {
				logger.info("disable CmsModelField, name={}.", bean.getName());
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("bean")
	public CmsModelField preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? modelFieldService.getCmsModelFieldById(oid) : null;
	}
}