package cn.mooc.app.core.plugin.manager;

import java.net.URL;
import java.net.URLClassLoader;


public class PluginsClassLoader extends URLClassLoader {

	public PluginsClassLoader(ClassLoader parent) {
		
		super(new URL[0], parent);

		
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}

}