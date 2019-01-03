package cn.mooc.app.core.event.listener;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;

public interface LoginEventListener extends SysEventListener {
	
	void logined(OprEvent event, SysContext sysContext);
	
}
