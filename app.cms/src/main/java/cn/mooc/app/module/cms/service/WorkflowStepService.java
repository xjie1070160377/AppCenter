package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Collection;
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

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.rds.WorkflowStepRepository;
import cn.mooc.app.module.cms.data.rds.WorkflowStepRoleRepository;
import cn.mooc.app.module.cms.orm.RowSide;

@Service
@Transactional(readOnly = true)
public class WorkflowStepService  {
	public List<WorkflowStep> findList(Integer workflowId, Map<String, Object> params, Sort sort) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (workflowId != null) {
			searchParams.put(SpecOperator.EQ + "_workflow.id", workflowId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<WorkflowStep> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, sort);
	}
	public Page<WorkflowStep> findPage(Integer workflowId, Map<String, Object> params, PagerParam pagerParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (workflowId != null) {
			searchParams.put(SpecOperator.EQ + "_workflow.id", workflowId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<WorkflowStep> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pagerParam.getPageRequest());
	}
	public List<WorkflowStep> findList(Integer workflowId, Map<String, Object> params, PagerParam pagerParam) {
		Page<WorkflowStep> page = findPage(workflowId, params, pagerParam);
		return page.getContent();
	}

	public RowSide<WorkflowStep> findSide(Integer workflowId,
			Map<String, Object> params, WorkflowStep bean, Integer position,
			Sort sort) {
		if (position == null) {
			return new RowSide<WorkflowStep>();
		}
		PagerParam limit = RowSide.limitable(position, sort);
		List<WorkflowStep> list = findList(workflowId, params, limit);
		return RowSide.create(list, bean);
	}

	public WorkflowStep get(Integer id) {
		return dao.findOne(id);
	}
	/**
	 * 判断用户是否有对文章的终审权限
	 * @param infoId
	 * @param userId
	 * @return
	 */
	public boolean istoend(Integer infoId, Long userId){
		List<WorkflowStep> list = dao.findHasToEnd(infoId, userId);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断用户是否有栏目终审权限
	 * @param infoId
	 * @param userId
	 * @return
	 */
	public boolean findNodeHasToEnd(Integer nodeId, Long userId){
		List<WorkflowStep> list = dao.findNodeHasToEnd(nodeId, userId);
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	@Transactional
	public WorkflowStep save(WorkflowStep bean, Integer[] roleIds,
			Integer workflowId) {
		Workflow workflow = workflowService.get(workflowId);
		bean.setWorkflow(workflow);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		workflowStepRoleService.save(bean, roleIds);
		return bean;
	}

	@Transactional
	public WorkflowStep update(WorkflowStep bean, Integer[] roleIds) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		workflowStepRoleService.update(bean, roleIds);
		return bean;
	}

	@Transactional
	public List<WorkflowStep> batchUpdate(Integer[] id, String[] name) {
		List<WorkflowStep> beans = new ArrayList<WorkflowStep>();
		if (ArrayUtils.isEmpty(id)) {
			return beans;
		}
		WorkflowStep bean;
		for (int i = 0, len = id.length; i < len; i++) {
			bean = get(id[i]);
			bean.setName(name[i]);
			bean.setSeq(i);
			beans.add(bean);
		}
		return beans;
	}

	private WorkflowStep doDelete(Integer id) {
		WorkflowStep entity = dao.findOne(id);
		if (entity != null) {
			stepRoleRepository.deleteByWorkFlowStepId(id);
			dao.delete(entity);
		}
		return entity;
	}

	@Transactional
	public WorkflowStep delete(Integer id) {
		firePreDelete(new Integer[] { id });
		return doDelete(id);
	}

	@Transactional
	public WorkflowStep[] delete(Integer[] ids) {
		firePreDelete(ids);
		WorkflowStep[] beans = new WorkflowStep[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = doDelete(ids[i]);
		}
		return beans;
	}

	@Transactional
	public void delete(Collection<WorkflowStep> steps) {
		Integer[] ids = new Integer[steps.size()];
		int i = 0;
		for (WorkflowStep step : steps) {
			ids[i++] = step.getId();
		}
		firePreDelete(ids);
		for (WorkflowStep step : steps) {
			dao.delete(step);
		}
	}

	private void firePreDelete(Integer[] ids) {
//		if (!CollectionUtils.isEmpty(deleteListeners)) {
//			for (WorkflowStepDeleteListener listener : deleteListeners) {
//				listener.preWorkflowStepDelete(ids);
//			}
//		}
	}

//	private List<WorkflowStepDeleteListener> deleteListeners;

//	@Autowired(required = false)
//	public void setDeleteListeners(
//			List<WorkflowStepDeleteListener> deleteListeners) {
//		this.deleteListeners = deleteListeners;
//	}
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private WorkflowStepRoleService workflowStepRoleService;
	@Autowired
	private WorkflowStepRepository dao;
	@Autowired
	private WorkflowStepRoleRepository stepRoleRepository;
	
	@Transactional
	public void workflowStepMoveUp(Integer prevId, Integer id) {
		WorkflowStep prevStep = get(prevId);
		WorkflowStep step = get(id);
		if(prevStep.getSeq().equals(step.getSeq())){
			prevStep.setSeq(prevStep.getSeq() - 1);
			dao.save(prevStep);
		}else{
			Integer seq = prevStep.getSeq();
			prevStep.setSeq(step.getSeq());
			dao.save(prevStep);
			step.setSeq(seq);
			dao.save(step);
		}
	}
}
