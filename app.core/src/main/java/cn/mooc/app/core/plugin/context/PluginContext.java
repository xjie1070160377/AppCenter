package cn.mooc.app.core.plugin.context;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.event.EventRegistry;
import cn.mooc.app.core.plugin.manager.PluginsClassLoader;
import cn.mooc.app.core.plugin.manager.PluginsRegister;

public interface PluginContext {
	
	PluginsRegister getPluginsRegister();
	
	WebApplicationContext getWebApplicationContext();
	
	PluginsClassLoader getPluginsClassLoader();
	
	ServletContext getServletContext();
	
	EventRegistry getEventRegistry();
	
	String getSysConfig(String key);
	
	int getSysConfigInt(String key, int def);
	
	double getSysConfigDouble(String key, double def);
	
}
