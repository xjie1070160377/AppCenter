package cn.mooc.app.core.event;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mooc.app.core.event.listener.SysEventListener;

/**
 * 从本地消息队列中取消息，并发送给消费者
 * 
 * @author Taven
 *
 */
public class LocalEventReactor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Set<SysEventListener> eventListeners = new HashSet<SysEventListener>();

	
	
	/**
	 * 异步事件的处理服务
	 * 
	 */
	public void startService() {
		Thread th = new Thread() {
			public void run() {
				while (true) {
					try {
						//无事件时会阻塞
						SysEvent event = LocalEventDemux.getInstance().takeEvent();
						for (SysEventListener listener : eventListeners) {
							//selectorManager.select(listener, event);
						}
					}
					catch (Exception e) {
						logger.error("事件处理出错", e);
					}
				}
			}
		};
		th.start();
	}

	/**
	 * 添加一个事件监听者
	 * 
	 * @param listener
	 */
	public void addEventListener(SysEventListener listener) {
		eventListeners.add(listener);
	}

	/**
	 * 移除一个事件监听者
	 * 
	 * @param listener
	 */
	public void removeEventListener(SysEventListener listener) {
		eventListeners.remove(listener);
	}




	
}
