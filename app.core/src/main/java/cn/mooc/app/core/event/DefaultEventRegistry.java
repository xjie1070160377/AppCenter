package cn.mooc.app.core.event;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.event.listener.SysEventListener;

@Service
public class DefaultEventRegistry implements EventRegistry {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ReentrantLock lock = new ReentrantLock(true);
	
	private Set<SysEventListener> eventListeners = new HashSet<SysEventListener>();
	
	@Override
	public void registry(SysEventListener listener) {
		//
		
		logger.debug("开始注册事件监听者：{}, 注册前监听者数量：{}",listener,eventListeners.size());
		lock.lock();
		try {
			//避免重复注册事件监听者
			for (SysEventListener sysEventListener : eventListeners) {
				if(sysEventListener.getClass()== listener.getClass()){
					eventListeners.remove(sysEventListener);
					logger.debug("发现相同监听者，移除事件监听者：{}",sysEventListener);
					break;
				}
			}
			//加入
			this.eventListeners.add(listener);
			logger.debug("最后事件监听者数量：：{}", eventListeners.size());
		}
		finally {
			lock.unlock();
		}
		
	}

	@Override
	public void unRegistry(SysEventListener listener) {
		//
		lock.lock();
		try {
			this.eventListeners.remove(listener);
		}
		finally {
			lock.unlock();
		}
	}

	public Set<SysEventListener> getEventListeners() {
		return eventListeners;
	}
	
	

}
