package cn.mooc.app.core.context;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 可以支持通配符加载的messages支持
 * 
 * @author Taven
 *
 */
public class WildcardMessageSource extends ReloadableResourceBundleMessageSource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final String PROPERTIES_SUFFIX = ".properties";
	
	private ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
	
	protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
		//return super.refreshProperties(filename, propHolder);
		
		//通过通配符，自动从classpath或jar包中取资源文件
		Properties properties = new Properties();
		long lastModified = -1;
		try {
			Resource[] resources = resourceLoader.getResources(filename +PROPERTIES_SUFFIX);
			for (Resource resource : resources) {
				String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
				PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
				properties.putAll(holder.getProperties());
				if (lastModified < resource.lastModified())
					lastModified = resource.lastModified();
			}
		} catch (IOException e) {
			logger.warn("refreshProperties", e);
		}
		return new PropertiesHolder(properties, lastModified);
	}

	
}
