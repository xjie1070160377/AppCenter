package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.cms.data.entity.ScheduleJob;
import cn.mooc.app.module.cms.data.rds.ScheduleJobRepository;
import cn.mooc.app.module.cms.orm.SearchFilter;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * ScheduleJobService
 * 定时任务服务
 * 
 * @author linwei
 * @date 2017-06-22
 */
@Service
public class ScheduleJobService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ScheduleJobRepository scheduleJobRepository;
//	@Autowired
//	private Scheduler scheduler;
	
	public ScheduleJob getScheduleJobById(Integer id) {
		ScheduleJob entity = scheduleJobRepository.findOne(id);
		return entity;
	}
	
	public List<ScheduleJob> getAllScheduleJobs(){
		return this.scheduleJobRepository.findAll();
	}
	
	public List<ScheduleJob> findList(Integer siteId,
			Map<String, String[]> params, Sort sort) {
		return scheduleJobRepository.findAll(spec(siteId, params), sort);
	}
	
	public Page<ScheduleJob> findScheduleJobPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ScheduleJob> spec = SpecDynamic.buildSpec(filters.values());

		return scheduleJobRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ScheduleJob saveScheduleJob(ScheduleJob entity) throws Exception {
		return this.scheduleJobRepository.save(entity);
	}

	@Transactional
	public ScheduleJob updateScheduleJob(ScheduleJob entity) throws Exception{
		return this.scheduleJobRepository.save(entity);
	}
	
	@Transactional
	public boolean delScheduleJob(Integer id){
		try{
			this.scheduleJobRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delScheduleJobs(Integer[] ids){
		try{
			List<ScheduleJob> entities = this.scheduleJobRepository.findAll(Arrays.asList(ids));
			this.scheduleJobRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	private Specification<ScheduleJob> spec(final Integer siteId,
			Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<ScheduleJob> fsp = SearchFilter.spec(filters, ScheduleJob.class);
		Specification<ScheduleJob> sp = new Specification<ScheduleJob>() {
			public Predicate toPredicate(Root<ScheduleJob> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if (siteId != null) {
					pred = cb.and(pred, cb.equal(root.get("site").<Integer> get("id"), siteId));
				}
				return pred;
			}
		};
		return sp;
	}
	
//	public void fetchNextFireTime(Collection<ScheduleJob> jobs) {
//		if (CollectionUtils.isEmpty(jobs)) {
//			return;
//		}
//		for (ScheduleJob job : jobs) {
//			if (!job.isEnabled()) {
//				continue;
//			}
//			try {
//				Trigger trigger = scheduler.getTrigger(job.getTriggerKey());
//				if (trigger != null) {
//					job.setNextFireTime(trigger.getNextFireTime());
//				}
//			} catch (SchedulerException e) {
//				logger.error(null, e);
//			}
//		}
//	}
}
