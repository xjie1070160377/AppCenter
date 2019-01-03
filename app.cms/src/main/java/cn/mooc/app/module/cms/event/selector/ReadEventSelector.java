package cn.mooc.app.module.cms.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.SysEventListener;
import cn.mooc.app.core.event.selector.EventSelector;
import cn.mooc.app.module.cms.event.listener.ReadEventListener;

public class ReadEventSelector implements EventSelector {

	@Override
	public boolean canAccept(SysEventListener listener) {
		return ReadEventListener.class.isAssignableFrom(listener.getClass());
	}

	@Override
	public void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext) {
		ReadEventListener eventListener = (ReadEventListener) listener;
		
		eventListener.read((OprEvent)event, sysContext);
	}

}
