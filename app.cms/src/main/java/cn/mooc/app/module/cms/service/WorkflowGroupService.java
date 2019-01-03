package cn.mooc.app.module.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.WorkflowGroup;
import cn.mooc.app.module.cms.data.rds.WorkflowGroupRepository;
import cn.mooc.app.module.cms.orm.RowSide;

/**
 * WorkflowGroupServiceImpl
 * 
 * @author csmooc
 * 
 */
@Service
@Transactional(readOnly = true)
public class WorkflowGroupService {
	public List<WorkflowGroup> findList(Integer siteId, Map<String, Object> params, Sort sort) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (siteId != null) {
			searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<WorkflowGroup> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, sort);
//		return dao.findAll(spec(siteId, params), sort);
	}

	public List<WorkflowGroup> findList(Integer siteId, Map<String, Object> params, PagerParam pagerParam) {
		Page<WorkflowGroup> page = findPage(siteId, params, pagerParam);
		return page.getContent();
	}
	public Page<WorkflowGroup> findPage(Integer siteId, Map<String, Object> params, PagerParam pagerParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (siteId != null) {
			searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<WorkflowGroup> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pagerParam.getPageRequest());
	}

	public RowSide<WorkflowGroup> findSide(Integer siteId,
			Map<String, Object> params, WorkflowGroup bean, Integer position,
			Sort sort) {
		if (position == null) {
			return new RowSide<WorkflowGroup>();
		}
//		Limitable limit = RowSide.limitable(position, sort);
		PagerParam pagerParam = RowSide.limitable(position, sort);
//		List<WorkflowGroup> list = dao.findAll(spec(siteId, params), limit);
		List<WorkflowGroup> list = findList(siteId, params, pagerParam);
		return RowSide.create(list, bean);
	}

	public List<WorkflowGroup> findList(Integer siteId) {
		return dao.findBySiteId(siteId);
	}

	public WorkflowGroup get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public WorkflowGroup save(WorkflowGroup bean, Site site) {
//		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public WorkflowGroup update(WorkflowGroup bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	private WorkflowGroup doDelete(Integer id) {
		WorkflowGroup entity = dao.findOne(id);
		if (entity != null) {
			dao.delete(entity);
		}
		return entity;
	}

	@Transactional
	public WorkflowGroup delete(Integer id) {
		firePreDelete(new Integer[] { id });
		return doDelete(id);
	}

	@Transactional
	public WorkflowGroup[] delete(Integer[] ids) {
		firePreDelete(ids);
		WorkflowGroup[] beans = new WorkflowGroup[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = doDelete(ids[i]);
		}
		return beans;
	}

	public void preSiteDelete(Integer[] ids) {
//		if (ArrayUtils.isNotEmpty(ids)) {
//			if (dao.countBySiteId(Arrays.asList(ids)) > 0) {
//				throw new DeleteException("workflowGroup.management");
//			}
//		}
	}

	private void firePreDelete(Integer[] ids) {
//		if (!CollectionUtils.isEmpty(deleteListeners)) {
//			for (WorkflowGroupDeleteListener listener : deleteListeners) {
//				listener.preWorkflowGroupDelete(ids);
//			}
//		}
	}

//	private List<WorkflowGroupDeleteListener> deleteListeners;

//	@Autowired(required = false)
//	public void setDeleteListeners(List<WorkflowGroupDeleteListener> deleteListeners) {
//		this.deleteListeners = deleteListeners;
//	}
	@Autowired
	private SiteService siteService;

	@Autowired
	private WorkflowGroupRepository dao;
}
