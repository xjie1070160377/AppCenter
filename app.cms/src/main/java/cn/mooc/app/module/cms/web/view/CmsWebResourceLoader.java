package cn.mooc.app.module.cms.web.view;

import java.io.File;

import org.beetl.core.Resource;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.module.cms.service.SiteService;

public class CmsWebResourceLoader extends FileResourceLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SiteService siteService;
	
	private String viewRoot;
	
	public CmsWebResourceLoader()
	{
		String root = BeetlUtil.getWebRoot() + File.separator;
		this.setRoot(root);

	}
	
	@Override
	public Resource getResource(String key)
	{
		String theme = key;
		/*if(key.startsWith("/")){
			theme = this.siteService.getThemeRoot() + key;
		}else{
			theme = this.siteService.getThemeRoot() + "/" + key;
		}*/
		//改为CMS自己绑定
		return super.getResource(theme);
	}

	public String getViewRoot() {
		return viewRoot;
	}

	public void setViewRoot(String viewRoot) {
		this.viewRoot = viewRoot;
	}
	
	
	
}
