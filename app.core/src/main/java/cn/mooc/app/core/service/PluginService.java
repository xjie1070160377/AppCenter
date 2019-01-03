package cn.mooc.app.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.plugin.IPlugin;
import cn.mooc.app.core.plugin.PluginInfo;
import cn.mooc.app.core.plugin.PluginStatus;
import cn.mooc.app.core.plugin.manager.PluginsManager;
import cn.mooc.app.core.plugin.manager.PluginsRegister;

@Service
public class PluginService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PluginsManager pluginsManager;
	@Autowired
	private PluginsRegister pluginsRegister;
	
	public List<PluginInfo> getPluginInfos(String pluginType){
		List<PluginInfo> pluginInfos = pluginsManager.getAllPluginInfos();

		List<PluginInfo> plugins = new ArrayList<PluginInfo>();
		for (PluginInfo pluginInfo : pluginInfos) {
			if(pluginType.equals(pluginInfo.getPluginType())){
				plugins.add(pluginInfo);
			}
		}
		
		return plugins;
	}
	
	public <T> List<T> getPlugins(String pluginType, PluginStatus status){
		Collection<IPlugin> pluginList = pluginsRegister.getPlugins().values();

		List<T> plugins = new ArrayList<T>();
		for (IPlugin plugin : pluginList) {
			
			if(pluginType.equals(plugin.getPluginType()) && plugin.getPluginInfo().getStatus().equals(status)){
				plugins.add((T) plugin);
			}
		}
		
		return plugins;
	}
	
	public PluginInfo getPluginInfo(String pluginType, String pluginName){
		List<PluginInfo> pluginInfos = this.getPluginInfos(pluginType);

		for (PluginInfo pluginInfo : pluginInfos) {
			if(pluginName.equals(pluginInfo.getName())){
				return pluginInfo;
			}
		}
		
		return null;
	}
	
	public PluginInfo getPluginInfo(String pluginId){
		return this.pluginsManager.getPluginInfo(pluginId);
	}
	
	public <T extends IPlugin> T getPlugin(String pluginId){
		IPlugin plugin = this.pluginsRegister.getPlugin(pluginId);
		return (T) plugin;
	}
	
	public void startPlugin(String pluginId) throws Exception{
		pluginsManager.startPlugin(pluginId);
	}
	
	public void stopPlugin(String pluginId) throws Exception{
		pluginsManager.stopPlugin(pluginId);
	}
	
}
