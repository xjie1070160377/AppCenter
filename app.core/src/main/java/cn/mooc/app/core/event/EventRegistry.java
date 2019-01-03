package cn.mooc.app.core.event;

import java.util.Set;

import cn.mooc.app.core.event.listener.SysEventListener;

public interface EventRegistry {

	/**
	 * 注册消息监听者
	 * @param listener
	 */
	public void registry(SysEventListener listener);
	
	/**
	 * 取消消息监听者
	 * @param listener
	 */
	public void unRegistry(SysEventListener listener);
	
	public Set<SysEventListener> getEventListeners();
	
}
