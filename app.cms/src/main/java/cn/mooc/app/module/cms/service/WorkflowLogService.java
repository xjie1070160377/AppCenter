package cn.mooc.app.module.cms.service;

import java.util.Arrays;
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
import cn.mooc.app.module.cms.data.entity.WorkflowLog;
import cn.mooc.app.module.cms.data.rds.WorkflowLogRepository;
import cn.mooc.app.module.cms.orm.RowSide;

@Service
@Transactional(readOnly = true)
public class WorkflowLogService  {
	
	public Page<WorkflowLog> findPage(Map<String, Object> params,PagerParam pagerParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(params);
		Specification<WorkflowLog> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pagerParam.getPageRequest());
	}
	public List<WorkflowLog> findList(Map<String, Object> params,PagerParam pagerParam) {
		Page<WorkflowLog> page = findPage(params, pagerParam);
		return page.getContent();
	}

	public RowSide<WorkflowLog> findSide(Map<String, Object> params, WorkflowLog bean, Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<WorkflowLog>();
		}
		PagerParam limit = RowSide.limitable(position, sort);
		List<WorkflowLog> list = findList(params, limit);
		return RowSide.create(list, bean);
	}

//	private Specification<WorkflowLog> spec(Map<String, String[]> params) {
//		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
//		Specification<WorkflowLog> sp = SearchFilter.spec(filters,
//				WorkflowLog.class);
//		return sp;
//	}
	
	public List<WorkflowLog> findListByInfo(Integer dataId, Integer type, boolean sftg){
		if(sftg){
			return dao.findListByTgInfo(dataId, type);
		}else{
			return dao.findListByInfo(dataId, type);
		}
	}
	
	public WorkflowLog findLastLog(Integer pid){
		return dao.findLastLog(pid);
	}

	public WorkflowLog get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public WorkflowLog save(WorkflowLog bean, Integer siteId) {
		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public WorkflowLog update(WorkflowLog bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public WorkflowLog delete(Integer id) {
		WorkflowLog entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public WorkflowLog[] delete(Integer[] ids) {
		WorkflowLog[] beans = new WorkflowLog[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = delete(ids[i]);
		}
		return beans;
	}

	public int deleteByStepId(List<Integer> stepIds) {
		return dao.deleteByStepId(stepIds);
	}

	public int deleteByWorkflowId(List<Integer> workflowIds) {
		return dao.deleteByWorkflowId(workflowIds);
	}

	public int deleteByProcessUserId(List<Integer> userIds) {
		return dao.deleteByProcessUserId(userIds);
	}

	public void preUserDelete(Integer[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			dao.deleteByUserId(Arrays.asList(ids));
		}
	}
	@Autowired
	private SiteService siteService;
	@Autowired
	private WorkflowLogRepository dao;
}
