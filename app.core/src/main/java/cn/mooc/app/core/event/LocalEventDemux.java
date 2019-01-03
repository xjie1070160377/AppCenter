package cn.mooc.app.core.event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用本地化的事件消息队列产生消息
 * （不适用于分布式场景，分布式场景使用Redis PUSH/SUB）
 * 
 * @author Taven
 *
 */
public class LocalEventDemux {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static LocalEventDemux instance;

	private BlockingQueue<SysEvent> eventQueue = new LinkedBlockingQueue<SysEvent>();

	private LocalEventDemux() {}

	public static LocalEventDemux getInstance() {

		if (instance == null) {
			return new LocalEventDemux();
		}
		else {
			return instance;
		}
	}

	/**
	 * 触发一个异步事件
	 * 
	 * @param eventType
	 * @param obj
	 */
	public void createEvent(TriggerType triggerType, Object obj) {
		try {
			eventQueue.put(new SysEvent(triggerType, obj));
		}
		catch (InterruptedException e) {
			logger.error("创建事件失败：" + e.getMessage());
		}

	}

	/**
	 * 取事件，无事件时会阻塞
	 * 
	 * @return
	 */
	public SysEvent takeEvent() {
		SysEvent event = null;

		try {
			event = eventQueue.take();
		}
		catch (InterruptedException e) {
			logger.error("中断异常", e);
		}

		return event;
	}
	
}
