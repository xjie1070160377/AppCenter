package cn.mooc.app.module.cms.event.listener;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.listener.SysEventListener;

public interface ReadEventListener extends SysEventListener {

	void read(OprEvent event, SysContext sysContext);
	
}
