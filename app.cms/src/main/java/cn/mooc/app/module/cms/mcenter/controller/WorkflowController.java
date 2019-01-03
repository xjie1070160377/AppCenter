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

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowGroup;
import cn.mooc.app.module.cms.model.WorkflowModel;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.WorkflowGroupService;
import cn.mooc.app.module.cms.service.WorkflowService;

/**
 * WorkflowController
 * 工作流控制器
 * 
 * @author hwt
 * @date 2016-05-11
 */
@Controller
@RequestMapping("/cms/workflow")
public class WorkflowController extends CmsModuleController {

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private WorkflowGroupService workflowGroupService;

	/**
	 * 工作流管理页面
	 * @param model
	 * @param pageParam
	 * @return
	 */
	@RequestMapping("/workflowList")
	public String workflowList(Model model, PagerParam pageParam) {
		return ModuleView("/workflow/workflowList");
	}

	/**
	 * 获取工作流数据列表
	 * @param model
	 * @param pageParam
	 * @param columnFiled
	 * @param keyWord
	 * @return
	 */
	@RequestMapping("/workflowListJson")
	@ResponseBody
	public Map<String, Object> workflowListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Site site = siteService.getCurrentSite();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<Workflow> pageData = workflowService.findPage(site.getId(), searchParams, pageParam);
		List<WorkflowModel> list = new ArrayList<WorkflowModel>();
		for (Workflow workflow : pageData.getContent()) {
			WorkflowModel workflowModel = new WorkflowModel(workflow);
			list.add(workflowModel);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	

	/**
	 * 工作流数据添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/workflowAdd")
	public String workflowAdd(Model model) {
		model.addAttribute("entity", new Workflow());
		Site site = siteService.getCurrentSite();
		List<WorkflowGroup> groups = workflowGroupService.findList(site.getId());
		model.addAttribute("groups", groups);
		return ModuleView("/workflow/workflowForm");
	}

	/**
	 * 工作流数据修改页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/workflowEdit")
	public String workflowEdit(Model model, Integer id) {

		Workflow entity = workflowService.getWorkflowById(id);
		model.addAttribute("entity", entity);
		
		Site site = siteService.getCurrentSite();
		List<WorkflowGroup> groups = workflowGroupService.findList(site.getId());
		model.addAttribute("groups", groups);

		return ModuleView("/workflow/workflowForm");
	}

	/**
	 * 工作流数据添加保存
	 * @param model
	 * @param entity
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/workflowSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowSave(Model model, Workflow entity,Integer groupId) {

		try {
			Site site = siteService.getCurrentSite();
			this.workflowService.save(entity, groupId, site.getId());
//		}  catch (SaveFailureException e) {
//			e.printStackTrace();
//			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	/**
	 * 工作流数据修改保存
	 * @param entity
	 * @param model
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/workflowUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowUpdate(@ModelAttribute("entity") Workflow entity,  Model model,Integer groupId) {
		try {
			this.workflowService.update(entity, groupId);
//		} catch (SaveFailureException e) {
//			e.printStackTrace();
//			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	/**
	 * 删除指定工作流数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/workflowDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowDel(Integer id) {
		// 删除
		try {
			workflowService.delete(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	/**
	 * 批量删除工作流数据
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/workflowDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowDels(Integer[] ids) {
		// 删除
		try {
			workflowService.delete(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public Workflow preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? workflowService.getWorkflowById(oid) : null;
	}
}