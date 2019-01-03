package cn.mooc.app.core.web.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.fun.FileFunctionWrapper;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.mooc.app.core.context.SysContext;


public class DefaultWebClasspathResourceLoader extends ClasspathResourceLoader
{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String root = null;
	boolean autoCheck = false;
	protected String charset = "UTF-8";
	String functionRoot = "functions";
	String functionSuffix = "fn";
	GroupTemplate gt = null;
	ClassLoader classLoader = null;
	
	@Autowired
	private SysContext sysContext;
	
	/**
	 * 使用加载beetl.jar的classloader，以及默认root为根目录
	 */
	public DefaultWebClasspathResourceLoader()
	{
		//保留，用于通过配置构造一个ResouceLoader
		//classLoader = ClassUtils.getDefaultClassLoader();
		classLoader = this.getClass().getClassLoader().getParent();
		this.root = "";

	}

	/** 使用指定的classloader
	 * @param classLoader
	 */
	public DefaultWebClasspathResourceLoader(ClassLoader classLoader)
	{

		this.classLoader = classLoader;
		this.root = "";

	}

	/**使用指定的classloader和root
	 * @param classLoader
	 * @param root 模板路径，如/com/templates/
	 */
	public DefaultWebClasspathResourceLoader(ClassLoader classLoader, String root)
	{

		this.classLoader = classLoader;
		this.root = root;

	}

	/**
	 * @param classLoader
	 * @param root
	 * @param charset 
	 */
	public DefaultWebClasspathResourceLoader(ClassLoader classLoader, String root, String charset)
	{

		this(classLoader, root);
		this.charset = charset;
	}

	/** 
	 * @param root ，/com/templates/如其后的resourceId对应的路径是root+"/"+resourceId
	 */
	public DefaultWebClasspathResourceLoader(String root)
	{

		this();
		if (root.equals("/"))
		{
			this.root = "";
		}
		else
		{
			this.root = root;
		}

	}

	public DefaultWebClasspathResourceLoader(String root, String charset)
	{

		this(root);
		this.charset = charset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.ResourceLoader#getResource(java.lang.String)
	 */
	@Override
	public Resource getResource(String key)
	{

		String themeRoot = this.getThemeRoot(key);
		logger.debug("Classpath Resource：", root + themeRoot);
		
		Resource resource = new WebClasspathResource(key, root + themeRoot, this);
		return resource;
	}
	
	private String getThemeRoot(String key){
		String themeRoot = "";
		if(key.startsWith("/")){
			themeRoot =  key;
		}else{
			themeRoot = "/"+ key;
		}
		
		return themeRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.ResourceLoader#close()
	 */
	@Override
	public void close()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isModified(Resource key)
	{
		if (this.autoCheck)
		{
			return key.isModified();
		}
		else
		{
			return false;
		}
	}

	public boolean isAutoCheck()
	{
		return autoCheck;
	}

	public void setAutoCheck(boolean autoCheck)
	{
		this.autoCheck = autoCheck;
	}

	public String getRoot()
	{
		return root;
	}

	@Override
	public void init(GroupTemplate gt)
	{
		Map<String, String> resourceMap = gt.getConf().getResourceMap();
		
		if (this.charset == null)
		{
			this.charset = resourceMap.get("charset");

		}

		this.functionSuffix = resourceMap.get("functionSuffix");

		this.autoCheck = Boolean.parseBoolean(resourceMap.get("autoCheck"));
		this.functionRoot = resourceMap.get("functionRoot");
		//初始化functions
		URL url = classLoader.getResource("");
		this.gt = gt;
		if (url.getProtocol().equals("file"))
		{

			File fnRoot = new File(url.getFile() + File.separator + root + File.separator + this.functionRoot);
			if (fnRoot.exists())
			{
				String ns = "";
				String path = "/".concat(this.functionRoot).concat("/");
				readFuntionFile(fnRoot, ns, path);
			}

		}

	}

	protected void readFuntionFile(File funtionRoot, String ns, String path)
	{
		String expected = ".".concat(this.functionSuffix);
		File[] files = funtionRoot.listFiles();
		for (File f : files)
		{
			if (f.isDirectory())
			{
				readFuntionFile(f, f.getName().concat("."), path.concat(f.getName()).concat("/"));
			}
			else if (f.getName().endsWith(functionSuffix))
			{
				String resourceId = path + f.getName();
				String fileName = f.getName();
				fileName = fileName.substring(0, (fileName.length() - functionSuffix.length() - 1));
				String functionName = ns.concat(fileName);
				FileFunctionWrapper fun = new FileFunctionWrapper(resourceId);
				gt.registerFunction(functionName, fun);
			}
		}
	}

	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	@Override
	public boolean exist(String key)
	{
		//
		//先从spring加载的jar包中加载模板资源
		String path = root + this.getThemeRoot(key);
		try {
			if (!path.startsWith("classpath:")) {
				path = "classpath:"+path.replace("//", "/");
			}
			org.springframework.core.io.Resource[] resources = resolver.getResources(path);
			for (org.springframework.core.io.Resource res : resources) {
				if(res.exists()){
					logger.debug("找到资源：{}", res.getURL());
					return true;
				}
				
			}
		} catch (IOException e) {
			logger.warn("getFromClassPath", e);
			
		}
		
		return this.classLoader.getClass().getResource(root + key) != null;
	}

	public String getCharset()
	{
		return charset;
	}

	public void setCharset(String charset)
	{
		this.charset = charset;
	}

	@Override
	public String getResourceId(Resource resource, String id)
	{
		if (resource == null)
			return id;
		else
			return BeetlUtil.getRelPath(resource.getId(), id);
	}

}
