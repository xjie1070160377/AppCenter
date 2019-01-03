package cn.mooc.app.module.interact.event.selector;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.SysEvent;
import cn.mooc.app.core.event.listener.SysEventListener;
import cn.mooc.app.core.event.selector.EventSelector;
import cn.mooc.app.module.interact.event.listener.CommentEventListener;

public class CommentEventSelector implements EventSelector {

	@Override
	public boolean canAccept(SysEventListener listener) {
		return CommentEventListener.class.isAssignableFrom(listener.getClass());
	}

	@Override
	public void dispatchEvent(SysEventListener listener, SysEvent event, SysContext sysContext) {
		CommentEventListener eventListener = (CommentEventListener) listener;
		
		eventListener.comment((OprEvent)event, sysContext);
	}

}
