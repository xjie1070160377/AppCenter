package cn.mooc.app.core.web.view;

import java.io.File;

import org.beetl.core.Resource;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultWebResourceLoader extends FileResourceLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private String viewRoot;
	
	public DefaultWebResourceLoader()
	{
		String root = BeetlUtil.getWebRoot() + File.separator;
		this.setRoot(root);

	}
	
	@Override
	public Resource getResource(String key)
	{
		String theme = key;

		return super.getResource(theme);
	}

	public String getViewRoot() {
		return viewRoot;
	}

	public void setViewRoot(String viewRoot) {
		this.viewRoot = viewRoot;
	}
	
	@Override
	public boolean exist(String key)
	{
		//
		return new File(this.getRoot(), key).exists();
	}
	
}
