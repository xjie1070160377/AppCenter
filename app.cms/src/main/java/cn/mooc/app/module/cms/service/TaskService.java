package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
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
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Task;
import cn.mooc.app.module.cms.data.rds.TaskRepository;
import cn.mooc.app.module.cms.orm.RowSide;

@Service
@Transactional(readOnly = true)
public class TaskService{
	public Page<Task> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Task> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public RowSide<Task> findSide(Map<String, Object> params, Task bean,
			Integer position, Sort sort) {
		if (position == null) {
			return new RowSide<Task>();
		}
		PagerParam pageParam = RowSide.limitable(position, sort);
		
		Map<String, SpecExpression> filters = SpecExpression.parse(params);
		Specification<Task> spec = SpecDynamic.buildSpec(filters.values());
		
		Page<Task> page = dao.findAll(spec, pageParam.getPageRequest());
		return RowSide.create(page.getContent(), bean);
	}

	public Task get(Integer id) {
		return dao.findOne(id);
	}

	public boolean isRunning(Integer id) {
		// 不需要任务时，id可以为null
		if (id == null) {
			return true;
		}
		Task bean = dao.findOne(id);
		if (bean == null) {
			return false;
		}
		return bean.isRunning();
	}

	@Transactional
	public void finish(Integer id) {
		Task bean = dao.findOne(id);
		if (bean != null) {
			bean.finish();
		}
	}

	@Transactional
	public void abort(Integer id) {
		Task bean = dao.findOne(id);
		if (bean != null) {
			bean.abort();
		}
	}

	@Transactional
	public Task stop(Integer id) {
		Task bean = dao.findOne(id);
		bean.stop();
		return bean;
	}

	@Transactional
	public List<Task> stop(Integer[] ids) {
		List<Task> beans = new ArrayList<Task>(ids.length);
		for (Integer id : ids) {
			beans.add(stop(id));
		}
		return beans;
	}

	@Transactional
	public void add(Integer id, int count) {
		// 不需要任务时，id可以为null
		if (id == null) {
			return;
		}
		Task bean = dao.findOne(id);
		if (bean != null) {
			bean.add(count);
		}
	}

	@Transactional
	public Task save(String name, String description, Integer type,
			Long userId, Integer siteId) {
		Task bean = new Task();
		bean.setName(name);
		bean.setType(type);
		bean.setDescription(description);

		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		bean.setUserId(userId);

		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Task update(Task bean) {
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	public Task delete(Integer id) {
		Task entity = dao.findOne(id);
		dao.delete(entity);
		return entity;
	}

	@Transactional
	public List<Task> delete(Integer[] ids) {
		List<Task> beans = new ArrayList<Task>(ids.length);
		for (Integer id : ids) {
			beans.add(delete(id));
		}
		return beans;
	}

	private SiteService siteService;

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	private TaskRepository dao;

	@Autowired
	public void setDao(TaskRepository dao) {
		this.dao = dao;
	}
}
