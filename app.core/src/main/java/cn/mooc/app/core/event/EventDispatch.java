package cn.mooc.app.core.event;

import cn.mooc.app.core.event.listener.SysEventListener;

/**
 * 消息产生/消息分发 综合接口
 * @author Taven
 *
 */
public interface EventDispatch {

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
	
	/**
	 * 产生一个消息事件
	 * @param triggerType
	 * @param obj
	 */
	public void postEvent(SysEvent event);
		
	/**
	 * 开始事件分发
	 */
	public void startDispatch();
	
	
	
}
