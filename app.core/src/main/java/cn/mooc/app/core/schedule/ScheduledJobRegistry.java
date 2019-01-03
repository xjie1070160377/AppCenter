package cn.mooc.app.core.schedule;

import java.lang.reflect.Method;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Service;

/**
 * 动态添加定时任务
 * 
 * @author Taven
 *
 */
@Service
public class ScheduledJobRegistry {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();
	@Autowired
	private ApplicationContext applicationContext;

	private ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	
	@PostConstruct
	public void init(){
		
		//类似实现可以参考：ScheduledAnnotationBeanPostProcessor 类
		
		//this.registrar.setScheduler(this.scheduler);
		//this.registrar.setTaskScheduler(this.applicationContext.getBean(TaskScheduler.class));
		
		taskScheduler.setBeanName("ScheduledJobRegistry");
		taskScheduler.setPoolSize(10);
		taskScheduler.afterPropertiesSet();
		
		
		this.registrar.setTaskScheduler(taskScheduler);		
		this.registrar.afterPropertiesSet();
		
		logger.debug("ScheduledJobRegistry 初始化完成");
		
	}
	
	public void addCronJob(String cron, String clsName, String methodName) {
		Method method = BeanUtils.findMethodWithMinimalParameters(this.applicationContext.getType(clsName), methodName);
		this.addCronJob(cron, applicationContext.getBean(clsName), method);
	}
	
	public void addCronJob(String cron, Object bean, Method method){		
		//
		TimeZone timeZone = TimeZone.getDefault();
		Runnable runnable = new ScheduledMethodRunnable(bean, method);
		this.registrar.addCronTask(new CronTask(runnable, new CronTrigger(cron, timeZone)));
		
	}
	
	public void addFixedDelayJob(long fixedDelay, long initialDelay, Object bean, Method method){
		//
		Runnable runnable = new ScheduledMethodRunnable(bean, method);
		this.registrar.addFixedDelayTask(new IntervalTask(runnable, fixedDelay, initialDelay));
		
	}
	
	@PreDestroy
	public void destroy() {
		//停止tomcat容器时，自动注销
		//可以使用 @PreDestroy 注解，或实现 DisposableBean接口
		this.registrar.destroy();
	}
	
}
