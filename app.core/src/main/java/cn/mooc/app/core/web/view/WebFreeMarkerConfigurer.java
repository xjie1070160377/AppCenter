package cn.mooc.app.core.web.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.mooc.app.core.service.FreeMarkerVarRegistry;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class WebFreeMarkerConfigurer extends FreeMarkerConfigurer {

	private Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
	
	@Autowired
	private FreeMarkerVarRegistry freeMarkerVarRegistry;
	
	public Configuration createConfiguration() throws IOException, TemplateException {
		//合并配置文件中注入的
		Map<String, Object> variables = freeMarkerVarRegistry.getVariables();
		for (String key : variables.keySet()) {
			freemarkerVariables.put(key, variables.get(key));
		}
		super.setFreemarkerVariables(freemarkerVariables);
		return super.createConfiguration();
		
	}

	public Map<String, Object> getFreemarkerVariables() {
		return freemarkerVariables;
	}

	public void setFreemarkerVariables(Map<String, Object> freemarkerVariables) {
		this.freemarkerVariables = freemarkerVariables;
	}
	
	
	
}
