package cn.mooc.app.core.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.SysEventListener;

public interface EventSelector {

	boolean canAccept(SysEventListener listener);
	
	void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext);
	
}
