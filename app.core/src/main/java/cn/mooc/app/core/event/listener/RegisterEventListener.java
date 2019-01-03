package cn.mooc.app.core.event.listener;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;

public interface RegisterEventListener extends SysEventListener {
	
	void register(OprEvent event, SysContext sysContext);
	
}
