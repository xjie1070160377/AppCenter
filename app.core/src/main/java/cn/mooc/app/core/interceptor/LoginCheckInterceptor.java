package cn.mooc.app.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.annotation.LoginCheck;
import cn.mooc.app.core.annotation.LoginExtend;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.controller.LoginCheckModuleController;

/**
 * 检查是否需要登录
 * 
 * 只要Controller 继承 LoginCheckModuleController  
 * 或 
 * 在类/方法 上加注解 @LoginCheck，可自动检查是否需要登录
 * 
 * @author Taven
 *
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebContext webContext;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Object bean = handlerMethod.getBean();
			
			LoginCheck clsLoginCheck = AnnotationUtils.findAnnotation(bean.getClass(), LoginCheck.class);
			if(clsLoginCheck!=null){
				return loginCheck(request, response, bean, clsLoginCheck);
			}
			
			LoginCheck methodLoginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);
			
			if(methodLoginCheck!=null){
				return loginCheck(request, response, bean, methodLoginCheck);
			}
			
			if(bean instanceof LoginCheckModuleController){
				//可以对当前的Controller判断继承了那个类，从而进行处理
				return loginCheck(request, response, bean, null);
			}
			
		}
		
		return true;
		
	}
	
	private boolean loginCheck(HttpServletRequest request, HttpServletResponse response, Object bean, LoginCheck loginCheck) throws Exception{
		if( !webContext.userHasLogin()){
			return redirectLogin(request, response, loginCheck);
		}else{
			
			String hasLoginMethodName = loginCheck.hasLoginMethod();
			if(StringUtils.isNotEmpty(hasLoginMethodName)){
				try{
				//Method method = ReflectionUtils.findMethod(LoginExtend.class, hasLoginMethod, HttpServletRequest.class, HttpServletResponse.class);
				Method hasLoginMethod = bean.getClass().getMethod(hasLoginMethodName, HttpServletRequest.class, HttpServletResponse.class);
				if(hasLoginMethod!=null){
					ReflectionUtils.invokeMethod(hasLoginMethod, bean, request, response);
				}
				}catch (Exception e) {
					logger.warn("无 hasLoginMethod");
				}
				
			}			
			
			return true;
		}
	}
	
	private boolean redirectLogin(HttpServletRequest request, HttpServletResponse response, LoginCheck loginCheck) throws Exception{
		String url = "/ulogin";
		if(loginCheck!=null && StringUtils.isNotEmpty(loginCheck.value())){
			url = loginCheck.value();
		}
		//实现登录成功后跳转到之前的页面
		WebUtils.saveRequest(request);
		WebUtils.issueRedirect(request, response, url);
		return false;
	}
	
	
}
