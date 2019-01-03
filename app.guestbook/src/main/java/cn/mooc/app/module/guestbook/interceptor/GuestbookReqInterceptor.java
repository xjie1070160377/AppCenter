package cn.mooc.app.module.guestbook.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.interceptors.ModuleInterceptorRegistry;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.nosql.GuestbookRepository;

/**
 * 拦截器 权限限制(超级管理员和留言簿所有者可以进入)
 * @author linwei
 * @date 2016年9月9日
 * @description
 */
@Service
public class GuestbookReqInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	protected WebContext webContext;
	@Autowired
	private ModuleInterceptorRegistry moduleInterceptorRegistry;
	@Autowired
	protected GuestbookRepository gRepository;

	@PostConstruct
	public void init() {
		String[] includePatterns = new String[] { "/mcenter/guestbook/**" };
		moduleInterceptorRegistry.mregistry(includePatterns, this);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Long userId = webContext.getCurrentUserId();
		Guestbook gb = gRepository.findOneByUserId(userId);
		if(gb == null && !webContext.hasSuperRole()){
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
