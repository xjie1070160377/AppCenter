package cn.mooc.app.module.interact.event.listener;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.listener.SysEventListener;

public interface DiggEventListener extends SysEventListener {

	void digg(OprEvent event, SysContext sysContext);
	
}
