package cn.mooc.app.core.plugin.support;

import cn.mooc.app.core.event.EventRegistry;
import cn.mooc.app.core.plugin.AbstractPlugin;
import cn.mooc.app.core.plugin.context.PluginContext;


/**
 * 任务插件
 * 
 * 可基于各系统事件，实现各种任务插件，如登录送积分等
 * 
 * @author Taven
 *
 */
public abstract class TaskPlugin extends AbstractPlugin {
	
	public abstract void registryListener(EventRegistry eventRegistry);
	
	public abstract void unRegistryListener(EventRegistry eventRegistry);
	
	public void start(PluginContext context) throws Exception {
		super.start(context);
		this.initWork(context);
	}
	
	@Override
	public void initWork(PluginContext context) throws Exception{
		this.registryListener(context.getEventRegistry());
	}
	
	
	public void stop(PluginContext context) throws Exception {
		super.stop(context);
		this.unRegistryListener(context.getEventRegistry());
		
	}
	
}



