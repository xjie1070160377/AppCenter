package cn.mooc.app.core.web.view;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Resource;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.context.SysContext;

public class MWebResourceLoader extends FileResourceLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysContext sysContext;
	
	public MWebResourceLoader()
	{
		String root = BeetlUtil.getWebRoot() + File.separator;
		this.setRoot(root);

	}
	
	@Override
	public Resource getResource(String key)
	{
		String themeRoot = this.getThemeRoot(key);
		//
		return super.getResource(themeRoot);
	}
	
	private String getThemeRoot(String key){
		
		String themeRoot = "default/"+ key;
		String template = sysContext.getSysConfig("sys.console.theme");
		if(StringUtils.isNoneBlank(template)){
			
			if(key.startsWith(template+"/")){
				themeRoot = key;
			}else{
				if(key.startsWith("/")){
					themeRoot = template + key;
				}else{
					themeRoot = template + "/" + key;
				}
			}
			
		}
		
		return themeRoot;
		
	}

	@Override
	public boolean exist(String key)
	{
		//
		String themeRoot = this.getThemeRoot(key);
		return new File(this.getRoot(), themeRoot).exists();
	}
	
	
	
}
