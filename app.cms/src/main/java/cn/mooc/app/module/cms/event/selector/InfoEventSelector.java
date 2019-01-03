package cn.mooc.app.module.cms.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.SysEventListener;
import cn.mooc.app.core.event.selector.EventSelector;
import cn.mooc.app.module.cms.event.listener.InfoEventListener;

public class InfoEventSelector implements EventSelector {

	@Override
	public boolean canAccept(SysEventListener listener) {
		return InfoEventListener.class.isAssignableFrom(listener.getClass());
	}

	@Override
	public void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext) {
		InfoEventListener eventListener = (InfoEventListener) listener;
		eventListener.changed(event, sysContext);
	}

}
