
package cn.mooc.app.module.cms.event.listener;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.SysEventListener;


public interface InfoEventListener extends SysEventListener {
	
	void changed(SysEvent event, SysContext sysContext);
	
}