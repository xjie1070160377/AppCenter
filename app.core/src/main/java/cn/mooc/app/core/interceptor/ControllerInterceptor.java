package cn.mooc.app.core.interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.controller.ModuleController;

/**
 * 参考例子
 * 
 * @author Taven
 *
 */
public class ControllerInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String requestURI = request.getRequestURI();
		
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Object bean = handlerMethod.getBean();
			if(bean instanceof ModuleController){
				//可以对当前的Controller判断继承了那个类，从而进行处理
				logger.debug("当前handlerMethod：{} 继承至 {}", handlerMethod.getBean(), ModuleController.class);
			}
			
			//判断当前的Controller，在class上是否有某个注解			
			RequiresAuthentication annotation1 = AnnotationUtils.findAnnotation(bean.getClass(), RequiresAuthentication.class);
			//Controller annotation2 = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
			Annotation annotation2 = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
			
			//判断当前的Controller，在执行的action方法上是否有某个注解
			RequestMapping annotation3 = handlerMethod.getMethodAnnotation(RequestMapping.class);
			if(annotation3!=null){
				logger.debug("RequestMapping.values：{}",annotation3.value());
			}
			
			Annotation annotation4 = handlerMethod.getMethodAnnotation(ResponseBody.class);
			
			//Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(annotation);
			//handlerMethod.getMethod().getAnnotation(Controller.class);
			logger.debug("当前handler：{}",handler);
		}
		
		return super.preHandle(request, response, handler);
		
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
		
	}
	
}
