package cn.mooc.app.core.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.LoginEventListener;
import cn.mooc.app.core.event.listener.SysEventListener;

public class LoginEventSelector implements EventSelector {

	@Override
	public boolean canAccept(SysEventListener listener) {
		//
		return LoginEventListener.class.isAssignableFrom(listener.getClass());
	}

	@Override
	public void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext) {
		//
		LoginEventListener loginEventListener = (LoginEventListener) listener;
		
		loginEventListener.logined((OprEvent)event, sysContext);
		
	}

}
