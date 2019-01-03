package cn.mooc.app.module.guestbook.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.web.interceptors.ModuleInterceptorRegistry;

@Service
public class GuestbookInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	protected WebContext webContext;
	@Autowired
	private ModuleInterceptorRegistry moduleInterceptorRegistry;

	@PostConstruct
	public void init() {
		String[] includePatterns = new String[] { "/**" };
		String[] excludePatterns = new String[] { "/static/**", "/theme/**", "/error/**", "/uploads/**", "/mcenter/**" };
		moduleInterceptorRegistry.registry(includePatterns, excludePatterns, this);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		String method = request.getMethod();
		String ctx = webContext.getHttpRequest().getContextPath();
		request.getSession().setAttribute("ctx", ctx);
		request.getSession().setAttribute("guestbook", ctx + "/theme/guestbook");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
