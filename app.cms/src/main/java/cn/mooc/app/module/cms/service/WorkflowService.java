package cn.mooc.app.module.cms.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowGroup;
import cn.mooc.app.module.cms.data.entity.WorkflowLog;
import cn.mooc.app.module.cms.data.entity.WorkflowProcess;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.entity.WorkflowStepRole;
import cn.mooc.app.module.cms.data.rds.WorkflowRepository;
import cn.mooc.app.module.cms.orm.RowSide;
import cn.mooc.app.module.cms.util.Reflections;

/**
 * WorkflowServiceImpl
 * 
 * @author csmooc
 * 
 */
@Service
@Transactional(readOnly = true)
public class WorkflowService  {
	public Workflow getWorkflowById(Integer workflowId) {
		return dao.findOne(workflowId);
	}
	
	public List<Workflow> findList(Integer siteId, Map<String, Object> params, Sort sort) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (siteId != null) {
			searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Workflow> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, sort);
	}
	public Page<Workflow> findPage(Integer siteId, Map<String, Object> params, PagerParam pagerParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (siteId != null) {
			searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Workflow> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pagerParam.getPageRequest());
	}
	public List<Workflow> findList(Integer siteId, Map<String, Object> searchParams, PagerParam pagerParam) {
		Page<Workflow> page = findPage(siteId, searchParams, pagerParam);
		return page.getContent();
	}

	public RowSide<Workflow> findSide(Integer siteId, Map<String, Object> params, Workflow bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Workflow>();
		}
		PagerParam limit = RowSide.limitable(position, sort);
		List<Workflow> list = findList(siteId, params, limit);
		return RowSide.create(list, bean);
	}

//	private Specification<Workflow> spec(final Integer siteId,
//			Map<String, String[]> params) {
//		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
//		final Specification<Workflow> fsp = SearchFilter.spec(filters,
//				Workflow.class);
//		Specification<Workflow> sp = new Specification<Workflow>() {
//			public Predicate toPredicate(Root<Workflow> root,
//					CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Predicate pred = fsp.toPredicate(root, query, cb);
//				if (siteId != null) {
//					pred = cb.and(pred, cb.equal(root.get("site")
//							.<Integer> get("id"), siteId));
//				}
//				return pred;
//			}
//		};
//		return sp;
//	}

	public List<Workflow> findList(Integer siteId) {
		Sort sort = new Sort("group.seq", "group.id", "seq", "id");
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Workflow> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, sort);
	}

	public Workflow get(Integer id) {
		return dao.findOne(id);
	}

	/**
	 * 新发表、审核。
	 * <ul>
	 * <li>无工作流或无步骤则结束。
	 * <li>已终审的不能再次审核，否则将重新启动流程。
	 * 
	 * @param workflow
	 * @param operator
	 * @param type
	 * @param dataId
	 * @return 步骤名称。空串代表审核通过，null代表无权限。
	 */
	@Transactional
	public String pass(Workflow workflow, Long owner, long operator,
			WorkflowProcess targetProcess, Integer dataType, Integer dataId,
			String opinion, boolean anew) {
		WorkflowProcess process = processService.findOne(dataType, dataId);
		
		if (process != null && !process.getEnd()) {
			// 流程存在，且未结束，使用原流程工作流
			workflow = process.getWorkflow();
		}
		if (workflow == null) {
			// 没有工作流，审核通过。
			if (process != null) {
				// TODO 写流程日志
				process.passEnd();
			}
			return "";
		}
		//需要重写, 判断用户是否有文档终审权限
//		if (operator.getInfoFinalPerm(workflow.getSite().getId())) {
//			// 拥有终审权限，审核通过。
//			if (process != null) {
//				// TODO 写流程日志
//				process.passEnd();
//			}
//			return "";
//		}
		List<WorkflowStep> steps = workflow.getSteps();
		int size = steps.size();
		String firstStep = "";
		if (size <= 0) {
			if (process != null) {
				// 无审核流程，审核通过。
				// TODO 写流程日志
				process.passEnd();
			}
			return "";
		}

		// 当前步骤ID
		Integer currStepId = null;
		if (process != null && !anew && !process.getEnd() && process.getStep() != null) {
			currStepId = process.getStep().getId();
		}
		// 下一个步骤
		WorkflowStep nextStep = null;
		WorkflowStep step;
		// 不在工作流中，可以提交新流程，但不能审核已终审流程。
		// 在工作流中，可以审核流程，但不能审核超过自己步骤的流程。
		for (int i = size - 1; i >= 0; i--) {
			step = steps.get(i);
			
			List<WorkflowStepRole> stepRoles = stepRoleService.findList(step.getId());
			SysUserEntity user = webContext.getSysUser(operator);
			if (Reflections.containsAny(stepRoles, "roleId", user.getRoles(), "id")) {
				firstStep = step.getName();
				break;
			}
			if (step.getId().equals(currStepId)) {
				// 没有审核权限或审核权限在当前步骤之前
				return null;
			}
			nextStep = step;
		}

		// TODO 写审核log

		Date endDate = null;
		Boolean isEnd = false;
		Boolean isRejection = false;
		// nextStep==null代表终审通过
		if (nextStep == null) {
			// 终审人员，审核结束。
			isEnd = true;
			endDate = new Timestamp(System.currentTimeMillis());
		}
		if (process != null) {
			WorkflowLog log = new WorkflowLog();
			log.setCreationDate(new Date());
			if(process.getStep() != null){
				log.setFrom(process.getStep().getName());
			}else{
				log.setFrom(firstStep);
			}
			log.setOpinion(opinion);
			log.setProcess(process);
			log.setSite(workflow.getSite());
			if(nextStep != null){
				log.setTo(nextStep.getName());
			}else{
				log.setTo("结束");
			}
			log.setType(dataType);
			log.setUserId(operator);
			logService.save(log, workflow.getSite().getId());
			
			process.setWorkflow(workflow);
			process.setStep(nextStep);
			process.setEndDate(endDate);
			process.setRejection(isRejection);
			process.setEnd(isEnd);
		} else {
			Site site = workflow.getSite();
			process = processService.save(site, workflow, nextStep, owner, targetProcess,
					dataId, isRejection, isEnd);
			
			WorkflowLog log = new WorkflowLog();
			log.setCreationDate(new Date());
			log.setFrom(firstStep);
			log.setOpinion(opinion);
			log.setProcess(process);
			log.setSite(workflow.getSite());
			if(nextStep != null){
				log.setTo(nextStep.getName());
			}else{
				log.setTo("结束");
			}
			log.setType(dataType);
			log.setUserId(operator);
			logService.save(log, workflow.getSite().getId());
		}
		return nextStep != null ? nextStep.getName() : "";
	}
	
	public String toEnd(Workflow workflow, Long owner, long operator,
			WorkflowProcess targetProcess, Integer dataType, Integer dataId){
		WorkflowProcess process = processService.findOne(dataType, dataId);
		
		if (process != null && !process.getEnd()) {
			// 流程存在，且未结束，使用原流程工作流
			workflow = process.getWorkflow();
		}
		if (workflow == null) {
			// 没有工作流，审核通过。
			if (process != null) {
				// TODO 写流程日志
				process.passEnd();
			}
			return "";
		}
		
		List<WorkflowStep> steps = workflow.getSteps();
		int size = steps.size();
		String firstStep = "";
		if (size <= 0) {
			if (process != null) {
				// 无审核流程，审核通过。
				// TODO 写流程日志
				process.passEnd();
			}
			return "";
		}

		// 当前步骤ID
		Integer currStepId = null;
		if (process != null && !process.getEnd() && process.getStep() != null) {
			currStepId = process.getStep().getId();
		}
		// 下一个步骤
		WorkflowStep step;
		// 不在工作流中，可以提交新流程，但不能审核已终审流程。
		// 在工作流中，可以审核流程，但不能审核超过自己步骤的流程。
		for (int i = size - 1; i >= 0; i--) {
			step = steps.get(i);
			
			List<WorkflowStepRole> stepRoles = stepRoleService.findList(step.getId());
			SysUserEntity user = webContext.getSysUser(operator);
			if (Reflections.containsAny(stepRoles, "roleId", user.getRoles(), "id")) {
				firstStep = step.getName();
				break;
			}
			if (step.getId().equals(currStepId)) {
				// 没有审核权限或审核权限在当前步骤之前
				return null;
			}
		}

		// TODO 写审核log

		Date endDate = new Timestamp(System.currentTimeMillis());;
		Boolean isEnd = true;
		Boolean isRejection = false;
		// nextStep==null代表终审通过
		if (process != null) {
			WorkflowLog log = new WorkflowLog();
			log.setCreationDate(new Date());
			if(process.getStep() != null){
				log.setFrom(process.getStep().getName());
			}else{
				log.setFrom(firstStep);
			}
			log.setProcess(process);
			log.setSite(workflow.getSite());
			log.setTo("结束");
			log.setType(dataType);
			log.setUserId(operator);
			logService.save(log, workflow.getSite().getId());
			
			process.setWorkflow(workflow);
			process.setStep(null);
			process.setEndDate(endDate);
			process.setRejection(isRejection);
			process.setEnd(isEnd);
		} else {
			Site site = workflow.getSite();
			process = processService.save(site, workflow, null, owner, targetProcess,
					dataId, isRejection, isEnd);
			
			WorkflowLog log = new WorkflowLog();
			log.setCreationDate(new Date());
			log.setFrom(firstStep);
			log.setProcess(process);
			log.setSite(workflow.getSite());
			log.setTo("结束");
			log.setType(dataType);
			log.setUserId(operator);
			logService.save(log, workflow.getSite().getId());
		}
		return "";
	}

	/**
	 * 
	 * @param workflow
	 * @param operator
	 * @param dataType
	 * @param dataId
	 * @param opinion
	 * @param rejectEnd
	 *            是否退稿
	 * @return 步骤名称。空串代表退稿，null代表无权限。
	 */
	@Transactional
	public String reject(Workflow workflow, Long owner, Long operator,
			WorkflowProcess targetProcess, Integer dataType, Integer dataId,
			String opinion, boolean rejectEnd) {
		WorkflowProcess process = processService.findOne(dataType, dataId);
		// 流程存在，且未结束，使用原流程工作流
		if (process != null && !process.getEnd()) {
			workflow = process.getWorkflow();
		}
		if (workflow == null) {
			// 没有工作流，退稿。
			if (process != null) {
				// TODO 写流程日志
				process.rejectEnd();
			}
			return "";
		}
		List<WorkflowStep> steps = workflow.getSteps();
		int size = steps.size();
		if (size <= 0) {
			// 无审核流程，退稿。
			if (process != null) {
				// TODO 写流程日志
				process.rejectEnd();
			}
			return "";
		}
		// 当前步骤ID
		Integer currStepId = null;
		if (process != null && !process.getEnd() && process.getStep() != null) {
			currStepId = process.getStep().getId();
		}
		// 下一个步骤
		WorkflowStep nextStep = null;
		WorkflowStep step;
		Integer siteId = workflow.getSite().getId();
		// 是否有终审权限
		boolean hasPermission = true;
//		boolean hasPermission = operator.getInfoFinalPerm(siteId);
		boolean currPoint = false;
		for (int i = 0; i < size; i++) {
			step = steps.get(i);
			if (step.getId().equals(currStepId)) {
				currPoint = true;
			}
//			if (!hasPermission && (currPoint || i == size - 1) && Reflections.containsAny(step.getRoles(), operator.getRoles(), "id")) {
//				// 有流程权限
//				hasPermission = true;
//			}
			if (!currPoint) {
				nextStep = step;
			}
		}
		// 无权限
		if (!hasPermission) {
			return null;
		}
		// 已经退稿或退回到发稿人
//		if (rejectEnd
//				|| (nextStep != null && Reflections.containsAny(
//						nextStep.getRoles(), owner.getRoles(), "id"))) {
//			nextStep = null;
//		}

		// TODO 写审核log

		Date endDate = null;
		Boolean isEnd = false;
		if (nextStep == null) {
			isEnd = true;
			endDate = new Timestamp(System.currentTimeMillis());
		}
		Boolean isRejection = true;
		if (process != null) {
			WorkflowLog log = new WorkflowLog();
			log.setCreationDate(new Date());
			if(process.getStep() != null){
				log.setFrom(process.getStep().getName());
			}else{
				log.setFrom("开始");
			}
			log.setOpinion(opinion);
			log.setProcess(process);
			log.setSite(workflow.getSite());
			if(nextStep != null){
				log.setTo(nextStep.getName());
			}else{
				log.setTo("退稿");
			}
			log.setType(dataType);
			log.setUserId(operator);
			logService.save(log, workflow.getSite().getId());
			
			process.setWorkflow(workflow);
			process.setStep(nextStep);
			process.setEndDate(endDate);
			process.setEnd(isEnd);
			process.setRejection(isRejection);
		} else {
			Site site = workflow.getSite();
			processService.save(site, workflow, nextStep, owner, targetProcess,
					dataId, isRejection, isEnd);
		}
		return nextStep != null ? nextStep.getName()+"(退稿)" : "";
	}

	@Transactional
	public Workflow save(Workflow bean, Integer groupId, Integer siteId) {
		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		WorkflowGroup group = groupService.get(groupId);
		bean.setGroup(group);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Workflow update(Workflow bean, Integer groupId) {
		WorkflowGroup group = groupService.get(groupId);
		bean.setGroup(group);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public List<Workflow> batchUpdate(Integer[] id, String[] name,
			String[] description) {
		List<Workflow> beans = new ArrayList<Workflow>();
		if (ArrayUtils.isEmpty(id)) {
			return beans;
		}
		Workflow bean;
		for (int i = 0, len = id.length; i < len; i++) {
			bean = get(id[i]);
			bean.setName(name[i]);
			bean.setDescription(description[i]);
			bean.setSeq(i);
			beans.add(bean);
		}
		return beans;
	}

	private Workflow doDelete(Integer id) {
		Workflow entity = dao.findOne(id);
		if (entity != null) {
			stepService.delete(entity.getSteps());
			dao.delete(entity);
		}
		return entity;
	}

	@Transactional
	public Workflow delete(Integer id) {
		firePreDelete(new Integer[] { id });
		return doDelete(id);
	}

	@Transactional
	public Workflow[] delete(Integer[] ids) {
		firePreDelete(ids);
		Workflow[] beans = new Workflow[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = doDelete(ids[i]);
		}
		return beans;
	}

	public void preSiteDelete(Integer[] ids) {
//		if (ArrayUtils.isNotEmpty(ids)) {
//			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
//				throw new DeleteException("workflow.management");
//			}
//		}
	}

	public void preWorkflowGroupDelete(Integer[] ids) {
//		if (ArrayUtils.isNotEmpty(ids)) {
//			if (dao.countByGroupId(Arrays.asList(ids)) > 0) {
//				throw new DeleteException("workflow.management");
//			}
//		}
	}

	private void firePreDelete(Integer[] ids) {
//		if (!CollectionUtils.isEmpty(deleteListeners)) {
//			for (WorkflowDeleteListener listener : deleteListeners) {
//				listener.preWorkflowDelete(ids);
//			}
//		}
	}

//	private List<WorkflowDeleteListener> deleteListeners;

//	@Autowired(required = false)
//	public void setDeleteListeners(List<WorkflowDeleteListener> deleteListeners) {
//		this.deleteListeners = deleteListeners;
//	}
	@Autowired
	private WorkflowStepService stepService;
	@Autowired
	private WorkflowProcessService processService;
	@Autowired
	private WorkflowGroupService groupService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private WorkflowLogService logService;
	@Autowired
	private WorkflowRepository dao;
	@Autowired
	private WorkflowStepRoleService stepRoleService;
	@Autowired
	private WebContext webContext;
	
}
