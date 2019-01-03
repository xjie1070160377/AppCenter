package cn.mooc.app.core.schedule.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时任务的演示例子，使用注解方式
 * 
 * @author Taven
 *
 */
@Service
public class CheckHealthJob {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//@Scheduled(cron = "0/10 * * * * ?")
	public void alarmJobDemo() {
		
		/**
		 * 参考cron表达式：
		 * 
		 * 0 0 0/8 * * ?		表示：每隔8小时执行一次
		 * 0 0 5 * * ?			表示：每天早上5点执行
		 * 0 0/10 * * * ?		表示：每隔10分钟执行一次
		 * 0/10 * * * * ?		表示：每隔10秒钟执行一次
		 * 30 15 2 * * ？		表示：每天早上2点15分30执行
		 */
		
		logger.debug("★★★★★★★★★★★★★★ 执行定时任务！ ★★★★★★★★★★★★★★");
		
	}
	
	//@Scheduled(fixedDelay = 1000 * 60 * 8, initialDelay = 1000 * 60 * 2)
	public void delayJobDemo() {
		
		/**
		 * initialDelay：第一次执行前，等待多久开始执行
		 * 
		 * fixedDelay：第一次执行后，间隔多久时间执行下一次
		 * 
		 * 
		 */
		
		logger.debug("★★★★★★★★★★★★★★ 执行延迟任务！ ★★★★★★★★★★★★★★");
		
		
	}
	
}
