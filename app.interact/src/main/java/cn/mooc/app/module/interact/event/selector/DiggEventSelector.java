package cn.mooc.app.module.interact.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.SysEventListener;
import cn.mooc.app.core.event.selector.EventSelector;
import cn.mooc.app.module.interact.event.listener.DiggEventListener;

public class DiggEventSelector implements EventSelector {

	@Override
	public boolean canAccept(SysEventListener listener) {
		return DiggEventListener.class.isAssignableFrom(listener.getClass());
	}

	@Override
	public void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext) {
		DiggEventListener diggEventListener = (DiggEventListener) listener;
		
		diggEventListener.digg((OprEvent)event, sysContext);
	}

}
