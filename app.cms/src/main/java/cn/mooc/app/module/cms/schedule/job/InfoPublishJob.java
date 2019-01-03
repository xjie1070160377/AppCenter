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

import cn.mooc.app.module.cms.service.InfoService;


/**
 * 信息发布任务
 * 
 */
public class InfoPublishJob {
	private static final Logger logger = LoggerFactory
			.getLogger(InfoPublishJob.class);
	
	@Autowired
	private InfoService service;
	public static final String SITE_ID = "siteId";

	//@Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			JobDataMap map = context.getJobDetail().getJobDataMap();
			Integer siteId = map.getIntegerFromString(SITE_ID);
			service.publish(siteId);
			service.tobePublish(siteId);
			service.expired(siteId);
			logger.info("run info publish job");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
