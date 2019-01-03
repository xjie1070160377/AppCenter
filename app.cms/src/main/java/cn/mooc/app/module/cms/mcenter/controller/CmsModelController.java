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
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.service.CmsModelService;
import cn.mooc.app.module.cms.support.Constants;
import cn.mooc.app.module.cms.support.Servlets;

/**
 * CmsModelController 模型管理控制器
 * 
 * @author hwt
 * @date 2016-06-13
 */
@Controller
@RequestMapping("/cms/model")
public class CmsModelController extends CmsModuleController {
	private static final Logger logger = LoggerFactory.getLogger(CmsModelController.class);

	@Autowired
	private CmsModelService modelService;

	@RequestMapping("/modelList")
	public String modelList(String queryType, Model model, PagerParam pageParam) {
		List<String> types = CmsModel.getTypes();
		if (StringUtils.isBlank(queryType)) {
			queryType = types.get(0);
		}
		model.addAttribute("types", types);
		model.addAttribute("queryType", queryType);
		return ModuleView("/model/modelList");
	}

	@RequestMapping("/modelListJson")
	@ResponseBody
	public Map<String, Object> modelListJson(String queryType) {
		Integer siteId = getCurrentSiteId();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		List<CmsModel> list = modelService.findList(siteId, queryType);
		for (CmsModel model : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", model.getId());
			map.put("name", model.getName());
			map.put("number", model.getNumber());
			map.put("type", model.getType());
			map.put("seq", model.getSeq());
			results.add(map);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rows", results);
		return resMap;
	}

	@RequestMapping("/modelAdd")
	public String modelAdd(Integer id, String queryType, Model model) {
		List<String> types = CmsModel.getTypes();
		if (StringUtils.isBlank(queryType)) {
			queryType = types.get(0);
		}
		CmsModel entity = new CmsModel();
		if (id != null) {
			entity = modelService.getCmsModelById(id);
		}
		String path = CmsModel.getPaths().get(queryType);
		model.addAttribute(Constants.OPRT, Constants.CREATE);
		model.addAttribute("entity", entity);
		model.addAttribute("queryType", queryType);
		model.addAttribute("types", types);
		return ModuleView(path + "modelForm");
	}

	@RequestMapping("/modelEdit")
	public String modelEdit(String queryType, Model model, Integer id) {
		CmsModel entity = modelService.getCmsModelById(id);
		List<String> types = CmsModel.getTypes();
		if (StringUtils.isBlank(queryType)) {
			queryType = types.get(0);
		}
		String path = CmsModel.getPaths().get(queryType);
		model.addAttribute(Constants.OPRT, Constants.EDIT);
		model.addAttribute("entity", entity);
		model.addAttribute("queryType", queryType);
		model.addAttribute("types", types);
		return ModuleView(path + "modelForm");
	}

	@RequestMapping(value = "/modelSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelSave(Integer oid, Model model, CmsModel entity, HttpServletRequest request) throws Exception {
		Integer siteId = getCurrentSiteId();
		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		try {
			if (oid == null) {
				modelService.save(entity, siteId, customs);
				logger.info("save CmsModel, name={}.", entity.getName());
			} else {
				modelService.copy(oid, entity, siteId, customs);
				logger.info("copy CmsModel, name={}.", entity.getName());
			}
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}

		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/modelUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelUpdate(@ModelAttribute("entity") CmsModel entity, Model model, HttpServletRequest request) {
		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		try {
			modelService.update(entity, customs);
			logger.info("update CmsModel, name={}.", entity.getName());
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdate(Integer[] id, String[] name, String[] number) {
		try {
			if (ArrayUtils.isNotEmpty(id)) {
				CmsModel[] beans = modelService.batchUpdate(id, name, number);
				for (CmsModel bean : beans) {
					logger.info("update CmsModel, name={}.", bean.getName());
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

	@RequestMapping(value = "/modelDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelDel(Integer id) {
		// 删除
		try {
			modelService.delCmsModel(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public CmsModel preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? modelService.getCmsModelById(oid) : null;
	}
}