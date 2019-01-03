package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.entity.WorkflowStepRole;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.model.WorkflowModel;
import cn.mooc.app.module.cms.model.WorkflowStepModel;
import cn.mooc.app.module.cms.service.WorkflowStepRoleService;
import cn.mooc.app.module.cms.service.WorkflowStepService;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * WorkflowStepController
 * 流程步骤控制器
 * 
 * @author oyhx
 * @date 2016-06-24
 */
@Controller
@RequestMapping("/cms/workflowStep")
public class WorkflowStepController extends CmsModuleController {

	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WorkflowStepRoleService workflowStepRoleService;

	@RequestMapping("/workflowStepList")
	public String workflowStepList(Model model, PagerParam pageParam) {
		return ModuleView("/workflowStep/workflowStepList");
	}
	/**
	 * 获取工作流步骤数据列表
	 * @param model
	 * @param id
	 * @param pageParam
	 * @param columnFiled
	 * @param keyWord
	 * @return
	 */
	@RequestMapping("/workflowStepListJson")
	@ResponseBody
	public Map<String, Object> workflowStepListJson(Model model, Integer id, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<WorkflowStep> pageData = workflowStepService.findPage(id, searchParams, pageParam);
		List<WorkflowStepModel> list = new ArrayList<WorkflowStepModel>();
		for (WorkflowStep workflowStep : pageData.getContent()) {
			WorkflowStepModel workfloStepwModel = MobileModelConvert.convertWorkflowStep(workflowStep);
			list.add(workfloStepwModel);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	/**
	 * 工作流步骤数据添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/workflowStepAdd")
	public String workflowStepAdd(Model model, Integer workflowId) {
		model.addAttribute("workflowId", workflowId);
		model.addAttribute("entity", new WorkflowStep());
		List<SysRoleEntity> roles = sysDataService.findSysRoleEnabledList();
		model.addAttribute("roles", roles);
		model.addAttribute("stepRoles", new ArrayList<WorkflowStepRole>(0));
		return ModuleView("/workflow/workflowStepForm");
	}

	/**
	 * 工作流步骤数据修改页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/workflowStepEdit")
	public String workflowStepEdit(Model model, Integer id) {

		WorkflowStep entity = workflowStepService.get(id);
		model.addAttribute("workflowId", entity.getWorkflow().getId());
		model.addAttribute("entity", entity);
		List<SysRoleEntity> roles = sysDataService.findSysRoleEnabledList();
		model.addAttribute("roles", roles);
		List<WorkflowStepRole> stepRoles = workflowStepRoleService.findList(id);
		model.addAttribute("stepRoles", stepRoles);
		return ModuleView("/workflow/workflowStepForm");
	}

	/**
	 * 工作流步骤数据添加保存
	 * @param model
	 * @param entity
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/workflowStepSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowStepSave(Model model, WorkflowStep entity,Integer workflowId, Integer[] roleIds) {

		try {
			this.workflowStepService.save(entity, roleIds, workflowId);
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
	 * 工作流步骤数据修改保存
	 * @param entity
	 * @param model
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/workflowStepUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowStepUpdate(@ModelAttribute("entity") WorkflowStep entity,  Model model,Integer[] roleIds) {
		try {
			workflowStepService.update(entity, roleIds);
//		} catch (SaveFailureException e) {
//			e.printStackTrace();
//			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}
	

	@RequestMapping(value = "/workflowStepDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowStepDel(Integer id) {
		// 删除
		try {
			workflowStepService.delete(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/workflowStepDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowStepDels(Integer[] ids) {
		// 删除
		try {
			workflowStepService.delete(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	@RequestMapping(value = "/workflowStepMoveUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowStepMoveUp(Integer id , Integer prevId){
		try {
			workflowStepService.workflowStepMoveUp(prevId, id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	@RequestMapping(value = "/workflowStepMoveDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workflowStepMoveDown(Integer prevId , Integer id){
		try {
			workflowStepService.workflowStepMoveUp(prevId, id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public WorkflowStep preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? workflowStepService.get(oid) : null;
	}
}