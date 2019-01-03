package cn.mooc.app.core.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.TaskJob;
import cn.mooc.app.core.data.rds.TaskJobRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;

@Service
public class TaskJobService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskJobRepository taskJobRepository;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SysContext sysContext;
	
	public TaskJob saveTaskJob(TaskJob entity){
		return this.taskJobRepository.save(entity);
	}
	
	public TaskJob createTaskJob(int jobType, String jobName, String jobData){
		TaskJob entity = new TaskJob();
		entity.setJobName(jobName);
		entity.setJobType(jobType);
		entity.setJobData(jobData);
		entity.setCreateTime(DateTimeUtil.getCurrentTime());
		entity.setUpdateTime(DateTimeUtil.getCurrentTime());
		
		return this.taskJobRepository.save(entity);
	}
	
	public void postVideoJob(String jobName, String mediaUrl) throws Exception {
		//检查视频自动加水印开关
		boolean enable = sysContext.getSysConfigInt("cms.video.watermark.enable", 1) == 1 ? true : false;
		if(enable){
			//创建一条任务数据
			TaskJob entity = this.createTaskJob(1, jobName, mediaUrl);
						
			Map<String, Object> jobData = new HashMap<String, Object>();
			jobData.put("MediaPath", mediaUrl);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("JobId", entity.getId());
			map.put("JobType", entity.getJobType());
			map.put("JobName", entity.getJobName());
			map.put("JobData", jobData);
			
			//提交给执行者执行视频转化，异步执行
			logger.debug("提交视频转换作业：{}", mediaUrl);
			cacheService.sendTopic("task_video_job", JsonUtil.toJson(map));
		}
		
		
	}
	
	public Page<TaskJob> findTaskJobList(Map<String, Object> searchParams, PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<TaskJob> spec = SpecDynamic.buildSpec(filters.values());
		
		return taskJobRepository.findAll(spec, pageParam.getPageRequest());
		
	}
}
