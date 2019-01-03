package cn.mooc.app.core.event;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.listener.SysEventListener;
import cn.mooc.app.core.event.selector.EventSelector;


//@Service
public class LocalEventDispatcher implements EventDispatch {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 为了方便本机测试，先放JAVA队列，正式上线改为MQ中间件
	 */
	private BlockingQueue<SysEvent> eventQueue = new LinkedBlockingQueue<SysEvent>();
	
	@Autowired
	private EventRegistry eventRegistry;
	@Autowired
	private SysContext sysContext;

	
	//@PostConstruct
	public void startService(){
		//本地测试，或单机版使用
		Thread th = new Thread() {
			public void run() {
				while (true) {
					startDispatch();
				}
			}
		};
		th.start();
		
		logger.debug("LocalEventDispatcher 工作线程启动...");
		
	}
	

	
	@Override
	public void registry(SysEventListener listener) {
		//
		eventRegistry.registry(listener);
	}

	@Override
	public void unRegistry(SysEventListener listener) {
		//
		eventRegistry.unRegistry(listener);
	}

	@Override
	public void postEvent(SysEvent event) {
		//暂不能开，需要将其独立到单独APP执行
		
		try {
			eventQueue.put(event);
		}
		catch (InterruptedException e) {
			logger.error("创建事件失败：" + e.getMessage());
		}
	}

	@Override
	public void startDispatch() {
		//这里需要优化，如果放在tomcat容器里面执行，
		//在事件突然增多时，会导致处理不过来，造成大量的等待处理事件，导致web服务无法响应
		//所以只建议在单机或本地开发环境使用
		SysEvent event = null;

		try {
			event = eventQueue.take();
			Set<SysEventListener> eventListeners = eventRegistry.getEventListeners();
			for (SysEventListener listener : eventListeners) {
				this.doSelector(listener, event);
			}
		}
		catch (InterruptedException e) {
			logger.error("中断异常", e);
		}
		
	}
	
	private void doSelector(SysEventListener listener, SysEvent event){
		
		for (EventSelector eventSelector : event.getEventSelectors()) {
			if(eventSelector.canAccept(listener)){
				try{
				eventSelector.dispatchEvent(listener, event, sysContext);
				}catch(Exception e){
					logger.error("dispatchEvent异常", e);
				}
			}
		}
	}
	
	

}
