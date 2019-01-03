package cn.mooc.app.core.plugin.manager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginsSpringInjector implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(PluginsSpringInjector.class);

    private ApplicationContext applicationContext;
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		this.applicationContext = applicationContext;
		
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        injectPluginBeans(registry);
		
	}
	
	private void injectPluginBeans(BeanDefinitionRegistry registry) {
		
		PluginsManager pluginsManager = applicationContext.getBean(PluginsManager.class);
		pluginsManager.loadAllPlugin();
		pluginsManager.loadJarPlugins(registry);
		
	}

}