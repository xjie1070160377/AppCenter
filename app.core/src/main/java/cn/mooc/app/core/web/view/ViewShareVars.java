package cn.mooc.app.core.web.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;
import org.beetl.core.om.ObjectUtil;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import cn.mooc.app.core.service.FuncLoader;
import cn.mooc.app.core.service.ViewFuncRegistry;

/**
 * 支持Properties文件扩展模板共享变量
 * @author Taven
 *
 */
public class ViewShareVars {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private BeetlGroupUtilConfiguration templateConfig;
	
	private Properties properties = new Properties();
	
	private Resource[] locations;
	
	private String fileEncoding;
	
	private Map<String, Object> sharedVars;
	
	@Autowired
	private ViewFuncRegistry viewFuncRegistry;
	
	public void init(){
		
		GroupTemplate groupTemplate = this.templateConfig.getGroupTemplate();
		sharedVars = groupTemplate.getSharedVars();
		if(sharedVars==null){
			sharedVars = new HashMap<String, Object>();
			groupTemplate.setSharedVars(sharedVars);
		}
		
		
		try {
			this.loadProperties(properties);
		} catch (IOException e) {
			logger.error("loadProperties", e);
		}
		
		for (Object key : properties.keySet()) {
			sharedVars.put(key.toString(), properties.get(key));
		}
		
		//因为mvc-web.xml里面定义的bean是最后才被spring接管，暂这样处理
		viewFuncRegistry.setViewShareVars(this);
		Set<FuncLoader> funcLoaders = viewFuncRegistry.getFuncLoaders();
		for (FuncLoader funcLoader : funcLoaders) {
			try{
				funcLoader.registerFunctions();
			}catch (Exception e) {
				logger.error("registerFunctions",e);
			}
			
		}
		
		//
		
	}
	
	public void registerFunction(String name, String clsName){
		GroupTemplate groupTemplate = this.templateConfig.getGroupTemplate();
		groupTemplate.registerFunction(name, (Function) ObjectUtil.instance(clsName));
	}
	
	public void registerFunction(String name, Function fn){
		GroupTemplate groupTemplate = this.templateConfig.getGroupTemplate();
		groupTemplate.registerFunction(name, fn);
	}
	
	public void setShareVar(String key, Object val){
		this.sharedVars.put(key, val);
	}
	
	public <T> T getShareVar(String key){
		return (T) this.sharedVars.get(key);
	}

	public BeetlGroupUtilConfiguration getTemplateConfig() {
		return templateConfig;
	}

	public void setTemplateConfig(BeetlGroupUtilConfiguration templateConfig) {
		this.templateConfig = templateConfig;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Resource[] getLocations() {
		return locations;
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public void setLocation(Resource location) {
		this.locations = new Resource[] {location};
	}
	
	public void setFileEncoding(String encoding) {
		this.fileEncoding = encoding;
	}
	
	public void setLocations(Resource... locations) {
		this.locations = locations;
	}
	
	protected void loadProperties(Properties props) throws IOException {
		if (this.locations != null) {
			for (Resource location : this.locations) {
				if (logger.isInfoEnabled()) {
					logger.info("Loading properties file from " + location);
				}
				try {
					PropertiesLoaderUtils.fillProperties(props, new EncodedResource(location, this.fileEncoding));
					
				}
				catch (IOException ex) {
					throw ex;
				}
			}
		}
	}
	
}
