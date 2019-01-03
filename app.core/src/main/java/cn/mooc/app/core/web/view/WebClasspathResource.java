package cn.mooc.app.core.web.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


public class WebClasspathResource extends Resource
{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	
	protected String charset = "UTF-8";
	
	String path = null;

	File file = null;
	long lastModified = 0;

	public WebClasspathResource(String key, String path, ResourceLoader resourceLoader)
	{
		super(key, resourceLoader);
		this.path = path;
	}
	
	public URL getFromClassPathFirst(String path) {
		if (StringUtils.isBlank(path)) {
			return null;
		}
		
		//从spring加载的jar包中加载模板资源
		try {
			if (!path.startsWith("classpath:")) {
				path = "classpath:"+path.replace("//", "/");
			}
			org.springframework.core.io.Resource[] resources = resolver.getResources(path);
			for (org.springframework.core.io.Resource res : resources) {
				if(res.exists()){
					logger.debug("找到资源：{}", res.getURL());
					return res.getURL();
				}
				
			}
		} catch (IOException e) {
			logger.error("getFromClassPathFirst", e);
		}

		return null;
	}

	@Override
	public Reader openReader()
	{
		URL url = this.getFromClassPathFirst(path);
		if(url==null){
			url = resourceLoader.getClass().getResource(path);
		}
		

		if (url == null)
		{
			BeetlException be = new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR);
			be.resourceId = this.id;
			throw be;
		}
		InputStream is;
		try
		{
			is = url.openStream();
		}
		catch (IOException e1)
		{
			BeetlException be = new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR);
			be.resourceId = this.id;
			throw be;
		}

		if (is == null)
		{
			BeetlException be = new BeetlException(BeetlException.TEMPLATE_LOAD_ERROR);
			be.resourceId = this.id;
			throw be;
		}

		if (url.getProtocol().equals("file"))
		{
			file = new File(url.getFile());
			lastModified = file.lastModified();
		}

		Reader br;
		try
		{
			br = new BufferedReader(new InputStreamReader(is, charset));
			return br;
		}
		catch (UnsupportedEncodingException e)
		{
			return null;
		}
	}

	@Override
	public boolean isModified()
	{
		if (file != null)
		{
			return file.lastModified() != this.lastModified;
		}
		else
		{
			return false;
		}
	}

	@Override
	public String getId()
	{
		return id;
	}

}
