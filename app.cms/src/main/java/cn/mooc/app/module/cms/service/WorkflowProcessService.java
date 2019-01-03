package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.Collection;
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
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowProcess;
import cn.mooc.app.module.cms.data.entity.WorkflowStep;
import cn.mooc.app.module.cms.data.rds.WorkflowProcessRepository;
import cn.mooc.app.module.cms.orm.RowSide;

@Service
@Transactional(readOnly = true)
public class WorkflowProcessService{
	public Page<WorkflowProcess> findPage(Map<String, Object> params, PagerParam pagerParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(params);
		Specification<WorkflowProcess> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pagerParam.getPageRequest());
	}
	public List<WorkflowProcess> findList(Map<String, Object> params, PagerParam pagerParam) {
		Page<WorkflowProcess> page = findPage(params, pagerParam);
		return page.getContent();
	}

	public RowSide<WorkflowProcess> findSide(Map<String, Object> params,
			WorkflowProcess bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<WorkflowProcess>();
		}
		PagerParam limit = RowSide.limitable(position, sort);
		List<WorkflowProcess> list = findList(params, limit);
		return RowSide.create(list, bean);
	}

//	private Specification<WorkflowProcess> spec(Map<String, String[]> params) {
//		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
//		Specification<WorkflowProcess> sp = SearchFilter.spec(filters,
//				WorkflowProcess.class);
//		return sp;
//	}

	public WorkflowProcess findOne(Integer dataType, Integer dataId) {
		return dao.findByDataTypeAndDataId(dataType, dataId);
	}

	public WorkflowProcess get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public WorkflowProcess save(Site site, Workflow workflow,
			WorkflowStep step, Long owner, WorkflowProcess process,
			Integer dataId, Boolean isReject, Boolean isEnd) {
		process.setSite(site);
		process.setWorkflow(workflow);
		process.setStep(step);
		process.setUserId(owner);
		process.setDataId(dataId);
		process.setRejection(isReject);
		process.setEnd(isEnd);

		process.applyDefaultValue();
		process = dao.save(process);
		return process;
	}

	@Transactional
	public WorkflowProcess update(WorkflowProcess bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public WorkflowProcess delete(Integer id) {
		WorkflowProcess entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public WorkflowProcess[] delete(Integer[] ids) {
		WorkflowProcess[] beans = new WorkflowProcess[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	@Transactional
	public WorkflowProcess delete(WorkflowProcess bean) {
		if (bean != null) {
			dao.delete(bean);
		}
		return bean;
	}

	@Transactional
	public Collection<WorkflowProcess> delete(Collection<WorkflowProcess> beans) {
		if (beans != null) {
			dao.delete(beans);
		}
		return beans;
	}

	@Transactional
	public void preWorkflowDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			dao.deleteByWorkflowId(Arrays.asList(ids));
		}
	}

	public void preWorkflowStepDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			workflowLogService.deleteByStepId(Arrays.asList(ids));
			dao.deleteByStepId(Arrays.asList(ids));
		}

	}

	@Transactional
	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			workflowLogService.deleteByProcessUserId(Arrays.asList(ids));
			dao.deleteByUserId(Arrays.asList(ids));
		}
	}
	@Autowired
	private WorkflowLogService workflowLogService;
	@Autowired
	private WorkflowProcessRepository dao;

}
