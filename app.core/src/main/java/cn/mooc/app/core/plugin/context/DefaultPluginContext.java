package cn.mooc.app.core.plugin.context;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.event.EventRegistry;
import cn.mooc.app.core.plugin.manager.PluginsClassLoader;
import cn.mooc.app.core.plugin.manager.PluginsRegister;
import cn.mooc.app.core.service.SysDataService;

@Service
public class DefaultPluginContext implements PluginContext {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private EventRegistry eventRegistry;
	@Autowired
	private PluginsRegister puginsRegister;
	@Autowired
	private SysContext sysContext;
	
	@Override
	public PluginsRegister getPluginsRegister() {
		//
		return puginsRegister;
	}

	@Override
	public WebApplicationContext getWebApplicationContext() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		return webApplicationContext;
	}

	@Override
	public PluginsClassLoader getPluginsClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		return servletContext;
	}

	@Override
	public String getSysConfig(String key) {

		return sysContext.getSysConfig(key);
	}

	@Override
	public int getSysConfigInt(String key, int def) {
		//
		return sysContext.getSysConfigInt(key, def);

	}

	@Override
	public double getSysConfigDouble(String key, double def) {
		//		
		return sysContext.getSysConfigDouble(key, def);
	}

	@Override
	public EventRegistry getEventRegistry() {
		//
		return eventRegistry;
	}


}
