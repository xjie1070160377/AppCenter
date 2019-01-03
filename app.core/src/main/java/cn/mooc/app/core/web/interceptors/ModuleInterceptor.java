package cn.mooc.app.core.web.interceptors;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

public class ModuleInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private boolean mWeb = false;
	
	@Autowired
	private ModuleInterceptorRegistry dynamicInterceptorRegistry;
	
	private Set<MappedInterceptor> getHandlerInterceptors() {
		
		if(mWeb){
			return dynamicInterceptorRegistry.getMHandlerInterceptors();
		}else{
			return dynamicInterceptorRegistry.getHandlerInterceptors();
		}
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		Set<MappedInterceptor> handlerInterceptors = this.getHandlerInterceptors();
		for (MappedInterceptor mappedInterceptor : handlerInterceptors) {
			if(!dynamicInterceptorRegistry.matches(request, mappedInterceptor, mWeb)){
				continue;
			}
			
			boolean result = mappedInterceptor.preHandle(request, response, handler);
			if(!result){
				//只有每个拦截都执行成功，才会执行下一个拦截
				return false;
			}
		}
		
		return true;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
		Set<MappedInterceptor> handlerInterceptors = dynamicInterceptorRegistry.getHandlerInterceptors();
		for (MappedInterceptor mappedInterceptor : handlerInterceptors) {
			if(!dynamicInterceptorRegistry.matches(request, mappedInterceptor, mWeb)){
				continue;
			}
			mappedInterceptor.postHandle(request, response, handler, modelAndView);
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Set<MappedInterceptor> handlerInterceptors = dynamicInterceptorRegistry.getHandlerInterceptors();
		for (MappedInterceptor mappedInterceptor : handlerInterceptors) {
			if(!dynamicInterceptorRegistry.matches(request, mappedInterceptor, mWeb)){
				continue;
			}
			mappedInterceptor.afterCompletion(request, response, handler, ex);
		}
		
	}

	public boolean ismWeb() {
		return mWeb;
	}

	public void setmWeb(boolean mWeb) {
		this.mWeb = mWeb;
	}


	
	
}
