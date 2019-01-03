package cn.mooc.app.core.web.interceptors;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.util.UrlPathHelper;

import cn.mooc.app.core.context.SysContext;

@Service
public class ModuleInterceptorRegistry {

	private Set<MappedInterceptor> handlerInterceptors = new HashSet<MappedInterceptor>();
	
	private Set<MappedInterceptor> mHandlerInterceptors = new HashSet<MappedInterceptor>();

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	private PathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	private SysContext sysContext;

	/**
	 * 非后台的拦截器注册
	 * 
	 * @param includePatterns
	 * @param handlerInterceptor
	 */
	public void registry(String[] includePatterns, HandlerInterceptor handlerInterceptor) {
		
		this.registry(includePatterns, null, handlerInterceptor);
	}
	
	/**
	 * 前端拦截器注册
	 * 
	 * @param includePatterns
	 * @param excludePatterns
	 * @param handlerInterceptor
	 */
	public void registry(String[] includePatterns, String[] excludePatterns, HandlerInterceptor handlerInterceptor) {
		//org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
		
		MappedInterceptor mappedInterceptor = new MappedInterceptor(includePatterns, excludePatterns, handlerInterceptor);
		mappedInterceptor.setPathMatcher(pathMatcher);
		this.handlerInterceptors.add(mappedInterceptor);
	}


	public Set<MappedInterceptor> getHandlerInterceptors() {
		return handlerInterceptors;
	}
	
	/**
	 * 后台拦截器注册
	 * 
	 * @param includePatterns
	 * @param handlerInterceptor
	 */
	public void mregistry(String[] includePatterns, HandlerInterceptor handlerInterceptor) {
		
		this.mregistry(includePatterns, null, handlerInterceptor);
	}
	
	/**
	 * 后台拦截器注册
	 * 
	 * @param includePatterns
	 * @param excludePatterns
	 * @param handlerInterceptor
	 */
	public void mregistry(String[] includePatterns, String[] excludePatterns, HandlerInterceptor handlerInterceptor) {
		
		MappedInterceptor mappedInterceptor = new MappedInterceptor(includePatterns, excludePatterns, handlerInterceptor);
		mappedInterceptor.setPathMatcher(pathMatcher);
		this.mHandlerInterceptors.add(mappedInterceptor);
	}


	public Set<MappedInterceptor> getMHandlerInterceptors() {
		return mHandlerInterceptors;
	}
	
	public boolean matches(HttpServletRequest request, MappedInterceptor mappedInterceptor, boolean mWeb){
		if(mWeb){
			return this.mMatches(request, mappedInterceptor);
		}else{
			return this.matches(request, mappedInterceptor);
		}
	}
	
	public boolean matches(HttpServletRequest request, MappedInterceptor mappedInterceptor){
		//如果请求路径是 /ServletName/login，取到的是 /login
		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		return mappedInterceptor.matches(lookupPath, this.pathMatcher);
	}
	
	public boolean mMatches(HttpServletRequest request, MappedInterceptor mappedInterceptor){
		//取当前url地址，带Servlet前缀
		//如果请求路径是 /ServletName/login，取到的是 /ServletName/login
		String lookupPath = this.urlPathHelper.getPathWithinApplication(request);

		return mappedInterceptor.matches(lookupPath, this.pathMatcher);
	}
}
