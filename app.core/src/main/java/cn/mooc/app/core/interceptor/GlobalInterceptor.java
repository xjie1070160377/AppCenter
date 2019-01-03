package cn.mooc.app.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.utils.ValidatorUtil;


/**
 * 全站拦截器
 * 
 * @author Taven
 *
 */
public class GlobalInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String context = request.getContextPath();
		String requestURI = request.getRequestURI();
		String ip = ValidatorUtil.getIpAddr(request);
		
		logger.debug("当前请求地址：{} method：{} IP：{} ", requestURI, request.getMethod(), ip);
		
		//
		//return "redirect:/index";
		//response.sendRedirect(path + "/index");
		
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