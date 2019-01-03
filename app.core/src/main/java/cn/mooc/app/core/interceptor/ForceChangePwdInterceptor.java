package cn.mooc.app.core.interceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.utils.HttpUtil;
import cn.mooc.app.core.web.interceptors.ModuleInterceptorRegistry;

@Service
public class ForceChangePwdInterceptor extends HandlerInterceptorAdapter {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ModuleInterceptorRegistry moduleInterceptorRegistry;
	
	@PostConstruct
	public void init(){
		String[] includePatterns = new String[]{"/**"};
		String[] excludePatterns = new String[]{"/uploads/**","/Uploads/**","/static/**","/theme/**","/error/**","/m-**","/tg-**", "/help/**"};
		moduleInterceptorRegistry.registry(includePatterns, excludePatterns, this);
		moduleInterceptorRegistry.mregistry(includePatterns, excludePatterns, this);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String requestURI = request.getRequestURI();
		//String ip = ValidatorUtil.getIpAddr(request);
				
		boolean forceChangePwd = HttpUtil.getSessionAttr(request, "forceChangePwd", false);
		if(forceChangePwd){
			WebUtils.issueRedirect(request, response, "/help/forceChangePwd?backUrl="+requestURI);
			return false;
		}

		
		return super.preHandle(request, response, handler);
		
		
		
	}
	
	
}
