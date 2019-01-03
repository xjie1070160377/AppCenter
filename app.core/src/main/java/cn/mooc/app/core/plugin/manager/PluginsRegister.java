package cn.mooc.app.core.plugin.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.plugin.IPlugin;
import cn.mooc.app.core.plugin.PluginInfo;

@Service
public class PluginsRegister {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final ReentrantLock lock = new ReentrantLock(true);
	
	private Map<String, IPlugin> plugins = new HashMap<String, IPlugin>();
	
	public void register(IPlugin plugin) {
		lock.lock();
		try {
			this.plugins.put(plugin.getPluginId(), plugin);
		}
		finally {
			lock.unlock();
		}
		
	}
	
	public void remove(IPlugin plugin) {
		lock.lock();
		try {
			
			this.plugins.remove(plugin.getPluginId());
		}
		finally {
			lock.unlock();
		}
		
	}
	
	public Map<String, IPlugin> getPlugins() {
		return this.plugins;
	}
	
	public IPlugin getPlugin(String pluginId) {
		return this.plugins.get(pluginId);
	}
	
	public List<PluginInfo> getPluginInfos() {
		List<PluginInfo> pluginInfos = new ArrayList<PluginInfo>();
		Map<String, IPlugin> plugins = this.getPlugins();
		for (IPlugin iPlugin : plugins.values()) {
			pluginInfos.add(iPlugin.getPluginInfo());
		}
		return pluginInfos;
	}
	
}

