package cn.mooc.app.module.cms.mcenter.controller;

import java.util.HashMap;
import java.util.Map;

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

import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.WorkflowGroup;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.WorkflowGroupService;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * WorkflowGroupController
 * 流程分组控制器
 * 
 * @author oyhx
 * @date 2016-06-23
 */
@Controller
@RequestMapping("/cms/workflowGroup")
public class WorkflowGroupController extends CmsModuleController {

	@Autowired
	private WorkflowGroupService workflowGroupService;
	@Autowired
	private SiteService siteService;

	@RequestMapping("/workflowGroupList")
	public String workflowGroupList(Model model, PagerParam pageParam) {
		return ModuleView("/workflowGroup/workflowGroupList");
	}

	@RequestMapping("/workflowGroupListJson")
	@ResponseBody
	public Map<String, Object> workflowGroupListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Site site = siteService.getCurrentSite();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<WorkflowGroup> pageData = workflowGroupService.findPage(site.getId(), searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/workflowGroupAdd")
	public String workflowGroupAdd(Model model) {
		model.addAttribute("entity", new WorkflowGroup());

		return ModuleView("/workflowGroup/workflowGroupForm");
	}

	@RequestMapping("/workflowGroupEdit")
	public String workflowGroupEdit(Model model, Integer id) {

		WorkflowGroup entity = workflowGroupService.get(id);
		model.addAttribute("entity", entity);

		return ModuleView("/workflowGroup/workflowGroupForm");
	}

	@RequestMapping(value = "/workflowGroupSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowGroupSave(Model model, WorkflowGroup entity) {

		try {
			Site site = siteService.getCurrentSite();
			this.workflowGroupService.save(entity, site);
//		}  catch (SaveFailureException e) {
//			e.printStackTrace();
//			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/workflowGroupUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowGroupUpdate(@ModelAttribute("entity") WorkflowGroup entity,  Model model) {
		try {
			this.workflowGroupService.update(entity);
//		} catch (SaveFailureException e) {
//			e.printStackTrace();
//			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/workflowGroupDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowGroupDel(Integer id) {
		// 删除
		try {
			workflowGroupService.delete(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/workflowGroupDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowGroupDels(Integer[] ids) {
		// 删除
		try {
			workflowGroupService.delete(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public WorkflowGroup preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? workflowGroupService.get(oid) : null;
	}
}