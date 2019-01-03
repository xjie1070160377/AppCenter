package cn.mooc.app.core.plugin;

import cn.mooc.app.core.plugin.context.PluginContext;

public interface IPlugin {

	/**
	 * 插件的唯一标示，全局唯一
	 * @return
	 */
	public String getPluginId();
	
	/**
	 * 取插件的配置信息和参数
	 * 
	 * @return
	 */
	public PluginInfo getPluginInfo();
	
	
	/**
	 * 返回插件的LOGO图片，可以是URL地址或base64字符串
	 * @return
	 */
	public String getPluginLogo();
	
	/**
	 * 插件在安装之前，加载插件参数
	 * 
	 * @param pluginInfo
	 */
	public void loadConfig(PluginInfo pluginInfo);
	
	public String getPluginType();
	
	public void start(PluginContext context) throws Exception;
	
	public void stop(PluginContext context) throws Exception;
	
	public void initWork(PluginContext context) throws Exception;
	
	public void refresh(PluginContext context) throws Exception;
	
	public void install(PluginContext context) throws Exception;
	
	public void uninstall(PluginContext context) throws Exception;
	
}
