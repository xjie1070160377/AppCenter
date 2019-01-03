package cn.mooc.app.module.interact.event.listener;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.listener.SysEventListener;

public interface UnCommentEventListener extends SysEventListener {

	void unComment(OprEvent event, SysContext sysContext);
	
}
