package cn.mooc.app.core.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

/**
 * 模块的控制器基类
 * @author Taven
 *
 */
public abstract class ModuleController extends BaseController {

	public abstract String getModuleName();
	
	public String ModuleView(String viewName) {
		if(StringUtils.isNotBlank(viewName)){
			if(!viewName.startsWith("/")){
				viewName = "/".concat(viewName);
			}
			return "/".concat(this.getModuleName()).concat(viewName);
		}
		return viewName;
	}

	public ModelAndView ModuleModelAndView(String viewName) {

		return new ModelAndView(this.ModuleView(viewName));
	}
	
	
	public String getWebView(String template){
		return this.webContext.getWebView(this.getModuleName(), template);
	}
	
	public String getWebView(String model, String template){
		return this.webContext.getWebView(this.getModuleName(), template);
	}
	
}
