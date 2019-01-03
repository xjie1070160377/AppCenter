package cn.mooc.app.module.cms.mcenter.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import cn.mooc.app.module.cms.data.entity.CmsImageType;
import cn.mooc.app.module.cms.service.CmsImageTypeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CmsImageTypeController
 * 图片分类控制器
 * 
 * @author linwei
 * @date 2017-05-27
 */
@Controller
@RequestMapping("/cms/cmsImageType")
public class CmsImageTypeController extends CmsModuleController {

	@Autowired
	private CmsImageTypeService cmsImageTypeService;

	@RequestMapping("/cmsImageTypeList")
	public String cmsImageTypeList(Model model, PagerParam pageParam) {
		return ModuleView("/cmsImageType/cmsImageTypeList");
	}

	@RequestMapping("/cmsImageTypeListJson")
	@ResponseBody
	public Map<String, Object> cmsImageTypeListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Page<CmsImageType> pageData = cmsImageTypeService.findCmsImageTypePage(searchParams, pageParam);
		List<Map<String,Object>> mList = pageData.getContent().stream().map(p -> new HashMap<String,Object>(){
			{
				put("id", p.getId());
				put("name", p.getName());
				put("createTime",sdf.format(p.getCreateTime()));
			}	
		}).collect(Collectors.toList());
		return HttpResponseUtil.successJson(mList, pageData, pageParam);
	}

	@RequestMapping("/cmsImageTypeAdd")
	public String cmsImageTypeAdd(Model model) {
		model.addAttribute("entity", new CmsImageType());

		return ModuleView("/cmsImageType/cmsImageTypeForm");
	}

	@RequestMapping("/cmsImageTypeEdit")
	public String cmsImageTypeEdit(Model model, Integer id) {

		CmsImageType entity = cmsImageTypeService.getCmsImageTypeById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/cmsImageType/cmsImageTypeForm");
	}

	@RequestMapping(value = "/cmsImageTypeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageTypeSave(Model model, CmsImageType entity) {

		try {
			entity.setCreateTime(new Date());
			entity.setSite(getCurrentSite());
			this.cmsImageTypeService.saveCmsImageType(entity);
			
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/cmsImageTypeUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageTypeUpdate(@ModelAttribute("entity") CmsImageType entity,  Model model) {
		try {
			this.cmsImageTypeService.updateCmsImageType(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/cmsImageTypeDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageTypeDel(Integer id) {
		// 删除
		try {
			cmsImageTypeService.delCmsImageType(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/cmsImageTypeDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsImageTypeDels(Integer[] ids) {
		// 删除
		try {
			cmsImageTypeService.delCmsImageTypes(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public CmsImageType preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? cmsImageTypeService.getCmsImageTypeById(oid) : null;
	}
}