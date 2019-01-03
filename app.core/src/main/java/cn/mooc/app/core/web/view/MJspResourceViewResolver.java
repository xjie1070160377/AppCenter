package cn.mooc.app.core.web.view;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import cn.mooc.app.core.context.SysContext;

public class MJspResourceViewResolver extends InternalResourceViewResolver {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private String template;
	
	protected String getPrefix() {
		//问题：使用注入的对象，在DEBUG模式下修改代码，保存后程序会出现noclassfound错误，导致每次修改代码都要重启
		//未找到原因前，先不用注入方式，避免影响开发效率
		//String template = sysContext.getSysConfig("sys.console.theme");	
		
		if(StringUtils.isNoneBlank(template)){
			return super.getPrefix() + template;
			
		}else{
			return super.getPrefix() + "default";
		}
		
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}


	
	
	
}
