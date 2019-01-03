package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.InfoSpecial;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.entity.SpecialCategory;
import cn.mooc.app.module.cms.model.SpecialModel;
import cn.mooc.app.module.cms.service.CmsModelService;
import cn.mooc.app.module.cms.service.InfoSpecialService;
import cn.mooc.app.module.cms.service.SpecialCategoryService;
import cn.mooc.app.module.cms.service.SpecialService;

/**
 * SpecialController 专题管理控制器
 * 
 * @author hwt
 * @date 2016-05-09
 */
@Controller
@RequestMapping("/cms/special")
public class SpecialController extends CmsModuleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SpecialService specialService;
	@Autowired
	private SpecialCategoryService specialCategoryService;
	@Autowired
	private CmsModelService modelService;
	@Autowired
	private InfoSpecialService infoSpecialService;
	

	@RequestMapping("/specialList")
	public String specialList(Model model, PagerParam pageParam) {
		return ModuleView("/special/specialList");
	}

	@RequestMapping("/specialListJson")
	@ResponseBody
	@Transactional(readOnly = true)
	public Map<String, Object> specialListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Integer siteId = getCurrentSiteId();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<SpecialModel> pageData = specialService.findSpecialModelList(searchParams, pageParam, siteId);
		
		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/specialAdd")
	public String specialAdd(Model model, Integer modelId) throws Exception {
		Integer siteId = getCurrentSiteId();
		List<SpecialCategory> categoryList = specialCategoryService.getAllSpecialCategorys(siteId);
		List<CmsModel> modelList = modelService.findList(siteId, Special.MODEL_TYPE);
		
		CmsModel cmsModel = null;
		if (modelId != null) {
			cmsModel = modelService.getCmsModelById(modelId);
		}
		if (cmsModel == null) {
			cmsModel = modelService.findDefault(siteId, Special.MODEL_TYPE);
		}
		if (cmsModel == null) {
			throw new Exception("special.error.modelNotFound");
		}
		Special entity = new Special();
		entity.setModel(cmsModel);
		model.addAttribute("cmsModel", cmsModel);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("modelList", modelList);
		model.addAttribute("entity", entity);
		return ModuleView("/special/specialForm");
	}

	@RequestMapping("/specialEdit")
	public String specialEdit(Model model, Integer id, Integer modelId) throws Exception {
		Integer siteId = getCurrentSiteId();
		Special entity = specialService.getSpecialById(id);
		List<SpecialCategory> categoryList = specialCategoryService.getAllSpecialCategorys(siteId);
		List<CmsModel> modelList = modelService.findList(siteId, Special.MODEL_TYPE);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("modelList", modelList);
		model.addAttribute("entity", entity);
		CmsModel cmsModel = null;
		if (modelId != null) {
			cmsModel = modelService.getCmsModelById(modelId);
		}
		if(modelId == null){
			cmsModel = entity.getModel();
			modelId = cmsModel.getId();
		}
		if (cmsModel == null) {
			cmsModel = modelService.findDefault(siteId, Special.MODEL_TYPE);
		}
		if (cmsModel == null) {
			throw new Exception("special.error.modelNotFound");
		}
		model.addAttribute("cmsModel", cmsModel);
		return ModuleView("/special/specialForm");
	}
	
	/**
	 * 专题排序页面
	 * @return
	 */
	@RequestMapping("/specialInfoSort")
	public String specialInfoSort(Model model, Integer id) throws Exception {
		model.addAttribute("specialId", id);
		return ModuleView("/special/specialSortList");
	}
	
	/**
	 * 专题排序json数据
	 * @param attrId
	 * @param model
	 * @param pageParam
	 * @param title
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/specialInfoListJson")
	@ResponseBody
	public Map<String, Object> specialInfoListJson(Integer specialId,Model model, PagerParam pageParam, String title) throws Exception {
		pageParam.setSort(new Sort(Direction.DESC, "specialIndex"));
		Page<InfoSpecial> pageData = infoSpecialService.pageSpecialInfoBySort(title, pageParam, specialId);
		List<Map> list = new ArrayList<Map>();
		for(InfoSpecial infos : pageData.getContent()){
			Map params = new HashMap();
			params.put("id", infos.getId());
			params.put("index", infos.getSpecialIndex());
			params.put("infoId", infos.getInfo().getId());
			params.put("title", infos.getInfo().getTitle());
			params.put("publishdate", infos.getInfo().getPublishDate());
			params.put("views", infos.getInfo().getViews());
			params.put("status", infos.getInfo().getStatus());
			list.add(params);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping(value = "/batchSpecialUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchSpecialUpdate(String str) {
		try {
			if (StringUtil.isNotEmpty(str)) {
				infoSpecialService.updateSort(str);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 专题文章在专题中置顶
	 * @param str
	 * @return
	 */
	@RequestMapping(value = "/topInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> topInfo(Integer id) {
		try {
			if (StringUtil.isNotEmpty(id)) {
				infoSpecialService.setTop(id);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	

	@RequestMapping(value = "/specialSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialSave(Model model, Special entity, Integer categoryId, Integer modelId) {
		Integer siteId = getCurrentSiteId();
		try {
			this.specialService.saveSpecial(entity, categoryId, modelId, siteId, webContext.getCurrentUserId());
			logger.info("save Special, title={}.", entity.getTitle());
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}

		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/specialUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialUpdate(@ModelAttribute("entity") Special entity, Model model, Integer categoryId, Integer modelId) {
		try {
			this.specialService.updateSpecial(entity, categoryId, modelId);
			logger.info("update Special, title={}.", entity.getTitle());
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/specialDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialDel(Integer id) {
		// 删除
		try {
			specialService.delSpecial(id);
			logger.info("delete Special, id={}.", id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/specialDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialDels(Integer[] ids) {
		// 删除
		try {
			specialService.delSpecials(ids);
			logger.info("delete Specials, ids={}.", ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public Special preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? specialService.getSpecialById(oid) : null;
	}
}