package cn.mooc.app.module.cms.schedule.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.Service.State;
import com.sun.tools.javac.file.RelativePath.RelativeDirectory;

import cn.mooc.app.core.schedule.ScheduledJobRegistry;
import cn.mooc.app.core.schedule.job.CheckHealthJob;
import cn.mooc.app.module.cms.service.CollectorService;


/**
 * 采集任务
 * 
 * @author csmooc
 * 
 */
@Service
public class CollectJob {
	private static final Logger logger = LoggerFactory.getLogger(CollectJob.class);

	private static final String DEFAULT_CRON = "0/5 * * * * ?";
	private String cron = DEFAULT_CRON;
	private Integer state = 0;
	
	@Autowired
	private ScheduledJobRegistry scheduledJobRegistry;
	@Autowired
	private CollectorService collector;
 
	
	//@Scheduled(cron = "0 0 2 * * ?")//每天凌晨2点执行一次
	public void collect() {
		collector.startAll();
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
