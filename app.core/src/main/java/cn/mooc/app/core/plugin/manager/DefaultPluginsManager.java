package cn.mooc.app.core.plugin.manager;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import cn.mooc.app.core.filefilter.DirectoryFileFilter;
import cn.mooc.app.core.filefilter.JarFileFilter;
import cn.mooc.app.core.filefilter.ZipFileFilter;
import cn.mooc.app.core.plugin.IPlugin;
import cn.mooc.app.core.plugin.PluginInfo;
import cn.mooc.app.core.plugin.PluginStatus;
import cn.mooc.app.core.plugin.context.PluginContext;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.ZipFile;

@Service
public class DefaultPluginsManager implements PluginsManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private PluginContext pluginContext;
	
	private final static String pluginPath = "/plugins/";
	private final static String basePackage = "cn.mooc.app";
	private final static String basePluginPackage = "cn.mooc.app.plugin";

	@Autowired
	private PluginsRegister pluginRegister;
	@Autowired
	private XmlWebApplicationContext applicationContext;
	
	private PluginsClassLoader pluginClassLoader = new PluginsClassLoader(getClassLoader());	
	
	private File pluginFilePath = new File(this.getWebInfoPath(), pluginPath).getAbsoluteFile();

	@PostConstruct
	public void loadAllPlugin(){
		
		this.loadJarPlugins();
	}
	
	public ApplicationContext getApplicationContext(){
		return this.applicationContext;
	}
	
	public ClassLoader getClassLoader(){
		ClassLoader classLoader = this.getClass().getClassLoader();
		return classLoader;
	}

	public String getWebInfoPath(){
		URL url = this.getClassLoader().getResource("");
		File fileDir = new File(url.getPath());
		return fileDir.getParent();
		
	}
	
	public BeanDefinitionRegistry getBeanDefinitionRegistry(){
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
		return defaultListableBeanFactory;
	}
	
	public void loadJarPlugins() {
		this.extractPluginPackages();		
		this.loadJarPlugins(this.getBeanDefinitionRegistry());
	}
	
	private void extractPluginPackages(){
		//如果插件目录下有未解压的压缩包，解压它
		File[] pluginPackage = pluginFilePath.listFiles(new ZipFileFilter());

        for (File packageFile : pluginPackage) {
            try {
            	extractPluginPackage(packageFile);
            } catch (Exception e) {
            	logger.error(e.getMessage(), e);
            }
        }
        
	}
	
	private File extractPluginPackage(File packageFile)  throws Exception {
		String fileName = packageFile.getName();
        long pluginArchiveDate = packageFile.lastModified();
        File pluginDirectory = new File(pluginFilePath, fileName);
        
        if (!pluginDirectory.exists() || (pluginArchiveDate > pluginDirectory.lastModified())) {
        	logger.debug("Expand plugin archive '{}' in '{}'", packageFile, pluginDirectory);

        	// do not overwrite an old version, remove it
        	if (pluginDirectory.exists()) {
        		FileUtils.deleteDirectory(pluginDirectory);
        	}

            // create directory for plugin
            pluginDirectory.mkdirs();

            // expand '.zip' file
            ZipFile unzip = new ZipFile(packageFile, pluginDirectory);
            unzip.extract();
        }
        
        return pluginDirectory;
        
	}
	
	public void loadJarPlugins(BeanDefinitionRegistry registry) {
		
		File[] pluginDirs = pluginFilePath.listFiles(new DirectoryFileFilter());
		for (File dirFile : pluginDirs) {
			//插件目录
			String pluginDirName = dirFile.getName();
						
			try {
				//this.loadPluginFromDir(dirFile, pluginDirName);
				this.loadPluginFromDir(pluginDirName);
			} catch (Exception e) {
				logger.error("loadPluginFromDir", e);
			}
			
		}
		
		
	}
	
	private File getPluginDir(String dirName){
		File dirFile = new File(pluginFilePath.getPath()+File.separator+dirName);
		return dirFile;
	}
	
	public PluginInfo getPluginInfo(String pluginId){
		return this.getPluginInfoFromDir(pluginId);
	}
	
	private PluginInfo getPluginInfoFromDir(String dirName){
		File dirFile = this.getPluginDir(dirName);
		//读取插件配置文件
		File configFile = new File(dirFile, "config.json");
		PluginInfo pluginInfo = null;
		try {
			pluginInfo = JsonUtil.fromFile(configFile, PluginInfo.class);
			
			pluginInfo.setId(dirName);
			pluginInfo.setPluginDir(dirFile);
			pluginInfo.setConfigFile(configFile);
			
			//JsonSerializableUtil.exportToFile(configFile, pluginInfo);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("loadJarPlugins error", e);
		}
		
		return pluginInfo;
	}
	
	public void changePluginStatus(String dirName, PluginStatus pluginStatus) throws Exception{
		PluginInfo pluginInfo = this.getPluginInfoFromDir(dirName);
		if(pluginInfo==null){
			throw new Exception("插件配置信息出错");
		}
		
		pluginInfo.setStatus(pluginStatus);
		
		JsonUtil.exportToFile(pluginInfo.getConfigFile(), pluginInfo);
		this.refreshPlugin(pluginInfo.getId());
	}
	
	public void updatePluginInfo(PluginInfo pluginInfo) throws Exception{

		JsonUtil.exportToFile(pluginInfo.getConfigFile(), pluginInfo);
		this.refreshPlugin(pluginInfo.getId());
		
	}

	
	public void loadPluginFromDir(String dirName) throws Exception{
				
		BeanDefinitionRegistry registry = this.getBeanDefinitionRegistry();
		
		File dirFile = this.getPluginDir(dirName);
		
		//读取插件配置文件
		PluginInfo pluginInfo = this.getPluginInfoFromDir(dirName);
		
		if(pluginInfo==null){
			throw new Exception("插件配置信息出错");
		}
		
		if(pluginInfo.getStatus() == PluginStatus.Disable){
			//如果插件被禁止，则不加载
			//在多模块依赖中，插件依赖某个模块,
			//即使插件被禁止，在执行 pluginClassLoader.loadClass(classname) 时，也会抛 ClassNotFoundException
			return;
		}
		

		if (dirFile.exists() && dirFile.isDirectory() && dirFile.isAbsolute()) {
            File[] jars = dirFile.listFiles(new JarFileFilter());
            
            for (int i = 0; (jars != null) && (i < jars.length); ++i) {
            	try {
            		findJarFromUrlLoader(jars[i].toURI().toURL());
            		
					pluginClassLoader.addURL(jars[i].toURI().toURL());
					
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
            }

            
            try {
            	for (File file : jars) {
					JarFile jarFile = new JarFile(file);
					Enumeration<JarEntry> jarEntries = jarFile.entries(); 
					while (jarEntries.hasMoreElements()) {
						JarEntry jarEntry = jarEntries.nextElement();
						
						String entryName = jarEntry.getName();
						if (entryName.lastIndexOf(".class")>0) {
							String classname = entryName.substring(0, entryName.lastIndexOf(".class"));
							classname = classname.replace("/",".");
							Class<?> extensionClass = null;
							try{
								//logger.debug("文件：{} 类：{}", file.getAbsolutePath(), classname);
								extensionClass = pluginClassLoader.loadClass(classname);
							}catch (ClassNotFoundException e) {
								//
								logger.warn("loadClass ClassNotFound：" + classname);
							}catch (NoClassDefFoundError e) {
								//
								logger.warn("loadClass NoClassDefFoundError：" + classname);
							}catch (NoSuchMethodError e) {
								logger.warn("loadClass NoSuchMethodError：" + classname);
							}catch (Exception e) {
								logger.warn("loadClass：", e);
							}
							
							if(extensionClass==null){
								continue;
							}
							
							//是否是插件类
							boolean isPluginClass = IPlugin.class.isAssignableFrom(extensionClass);
							if(isPluginClass && !classname.startsWith(basePluginPackage)){
								//插件中的包名必须在指定空间下，否则不加载
								logger.warn("插件中的类不在要求的包名下，跳过加载，类名{}", classname);
								continue;
							}
							
				            BeanDefinition definition = new RootBeanDefinition(extensionClass);
				            
				            if(classname.startsWith(basePluginPackage)){
				            	if(pluginInfo.getStatus() == PluginStatus.Enable){
					            	//插件是启用的注册到spring
						            registry.registerBeanDefinition(classname, definition);
					            }else{
					            	try{
					            		//从容器中尝试移除
					            		registry.removeBeanDefinition(classname);
					            	}catch(Exception e){
					            		logger.warn("{} 插件的类 {} 没有注册到容器中", dirName, classname);
					            	}
					            }
			        		}
				            				            
				            
				            pluginInfo.getClassNames().add(classname);
				            
						}
						
						
					}
				}
            	
            	
				
			} catch (Exception e) {
				logger.error("Load Plugin Jars",e);
			}
            
            if(pluginInfo.getStatus() == PluginStatus.Enable){
	        	for (String classname : pluginInfo.getClassNames()) {
	        		if(!classname.startsWith(basePluginPackage)){
	        			continue;
	        		}
	        		//
	        		//Class.forName(classname);
	        		Class<?> beanCls = pluginClassLoader.loadClass(classname);
	        		if(!needSrpingManage(beanCls)){
	        			//没有加注解的，不用给spring管理
	        			continue;
	        		}
	        		
	        		/*if(beanCls.isAssignableFrom(IPlugin.class)){
	        			continue;
	        		}*/
	        		
	            	//让spring对其实例化
	            	Object object = applicationContext.getBean(classname);
		            if(object instanceof IPlugin){
		            	IPlugin plugin = (IPlugin) object;
		            	plugin.loadConfig(pluginInfo);
		            			            	
		            	this.pluginRegister.register(plugin);
		            	plugin.initWork(pluginContext);
		            	
		            }
				}
            }
            
            
        }
	}
	
	private boolean needSrpingManage(Class<?> extensionClass){
		//判断当前的class上是否有某个注解			
		Annotation annotation = AnnotationUtils.findAnnotation(extensionClass, Service.class);
		if(annotation!=null){
			return true;
		}
		
		annotation = AnnotationUtils.findAnnotation(extensionClass, Component.class);
		if(annotation!=null){
			return true;
		}
		
		return false;
	}
	
	
	private void findJarFromUrlLoader(URL jarUrl) throws Exception{
		
		for (URL url : pluginClassLoader.getURLs()) {
			if(url.sameFile(jarUrl)){
				logger.debug("已有同样的JAR文件：{}",url.toString());
				//因为 addURL 方法在已经加载过同名的JAR文件后，再次加载不会重新加载
				//会导致新增加的class在loadClass时，提示no class found，在没有找到好的方式前，暂这样处理
				ClassLoader classLoader = pluginClassLoader.newInstance(new URL[]{jarUrl}, pluginClassLoader);
				pluginClassLoader = new PluginsClassLoader(classLoader);
				
			}
		}
		
	}
	
	
	public List<PluginInfo> getAllPluginInfos() {
		List<PluginInfo> pluginInfos = new ArrayList<PluginInfo>();
		
		File[] pluginDirs = pluginFilePath.listFiles(new DirectoryFileFilter());
		for (File dirFile : pluginDirs) {
			//插件目录
			String pluginDirName = dirFile.getName();
			//读取插件配置文件
			PluginInfo pluginInfo = this.getPluginInfoFromDir(pluginDirName);
			
			if(pluginInfo==null){
				continue;
			}
			
			pluginInfos.add(pluginInfo);
			
		}
		
		return pluginInfos;
	}

	public void startPlugin(String pluginId) throws Exception {

		this.changePluginStatus(pluginId, PluginStatus.Enable);
		this.refreshPlugin(pluginId);
	}
	
	public void stopPlugin(String pluginId) throws Exception {
		IPlugin iPlugin = this.pluginRegister.getPlugin(pluginId);
		if(iPlugin!=null){
			iPlugin.stop(pluginContext);
			this.pluginRegister.remove(iPlugin);
			this.refreshPlugin(pluginId);			
		}else{
			throw new Exception("插件不存在");
		}
	}

	@Override
	public void refreshPlugin(String pluginId) throws Exception {
		
		this.loadPluginFromDir(pluginId);
		
	}

	
}
