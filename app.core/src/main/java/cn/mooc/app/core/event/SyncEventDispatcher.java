package cn.mooc.app.core.event;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.listener.SysEventListener;
import cn.mooc.app.core.event.selector.EventSelector;

@Service
public class SyncEventDispatcher implements EventDispatch {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EventRegistry eventRegistry;
	@Autowired
	private SysContext sysContext;
	
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
		//直接执行
		try {
			Set<SysEventListener> eventListeners = eventRegistry.getEventListeners();
			for (SysEventListener listener : eventListeners) {
				this.doSelector(listener, event);
			}
		}
		catch (Exception e) {
			logger.error("执行postEvent异常", e);
		}
		
	}

	@Override
	public void startDispatch() {
		//直接同步方式执行，只有异步方式这里才需要执行处理逻辑
		
		
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
