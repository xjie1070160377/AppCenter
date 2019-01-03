package cn.mooc.app.core.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.RegisterEventListener;
import cn.mooc.app.core.event.listener.SysEventListener;

public class RegisterEventSelector implements EventSelector {

	@Override
	public boolean canAccept(SysEventListener listener) {
		//
		return RegisterEventListener.class.isAssignableFrom(listener.getClass());
	}

	@Override
	public void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext) {
		//
		RegisterEventListener eventListener = (RegisterEventListener) listener;
		
		eventListener.register((OprEvent)event, sysContext);
		
	}

}
