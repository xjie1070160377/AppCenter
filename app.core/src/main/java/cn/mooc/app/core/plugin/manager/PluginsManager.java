package cn.mooc.app.core.plugin.manager;

import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import cn.mooc.app.core.plugin.PluginInfo;


public interface PluginsManager {

	public void loadAllPlugin();
	
	public void loadJarPlugins();
	
	public void loadJarPlugins(BeanDefinitionRegistry registry);
	
	public List<PluginInfo> getAllPluginInfos();
	
	public PluginInfo getPluginInfo(String pluginId);
	
	public void updatePluginInfo(PluginInfo pluginInfo) throws Exception;
	
	public void refreshPlugin(String pluginId) throws Exception;
	
	public void startPlugin(String pluginId) throws Exception;
	
	public void stopPlugin(String pluginId) throws Exception;
	
}
