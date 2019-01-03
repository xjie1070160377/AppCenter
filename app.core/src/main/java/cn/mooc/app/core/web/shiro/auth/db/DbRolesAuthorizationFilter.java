package cn.mooc.app.core.web.shiro.auth.db;

import static org.apache.shiro.util.StringUtils.split;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbRolesAuthorizationFilter extends RolesAuthorizationFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String ctxPath = "mcenter";
	
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
        if (this.appliedPaths == null || this.appliedPaths.isEmpty()) {
            return true;
        }
        
        String requestURI = getPathWithinApplication(request);
        
        Subject subject = getSubject(request, response);
        
        if(!subject.isAuthenticated()){
        	//因为优先级比较高，如果未登录，则交给其他Filter去拦截
        	return true;
        }
        
        if(subject!=null && subject.hasRole("superUser")){
        	return true;
        }
        

        
        for (String path : this.appliedPaths.keySet()) {
        	if(!path.contains("*") && ctxPathsMatch(path, request)){
        		//通配符能匹配上的，数据库方式先匹配一下        		
        		Object config = this.appliedPaths.get(path);
        		boolean allowed = isFilterChainContinued(request, response, path, config);
        		//如果默认是所有权限都需要超级管理员
        		//if(allowed){
        		//	return allowed;
        		//}
        		
        		//如果默认是所有地址都不需要权限
        		if(!allowed){
        			WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        		}
        		return allowed;
        	}
        	
        	
        }
        
        boolean regexMatch = false;
        for (String path : this.appliedPaths.keySet()) {
        	if(path.contains("*") && ctxPathsMatch(path, request)){
        		//支持菜单地址中含通配符
        		regexMatch = true;
        		Object config = this.appliedPaths.get(path);
        		if(config!=null ){
        			//
        			logger.debug("当前请求：{} 与路径：{} 权限匹配 其它信息：{}", requestURI, path, config);
        			boolean allowed = this.isAccessAllowed(request, response, config);
        			if(allowed){
        				return allowed;
            		}
        			
        		}
        	}
        	
        }
        
        if(regexMatch){
        	//有符合要求的通配符路径，并且都没有accessallowed
        	return false;
        }
        
        //this.cleanup(request, response, null);
        
        String alreadyFilteredAttributeName = getAlreadyFilteredAttributeName();
        request.removeAttribute(alreadyFilteredAttributeName);
        
       boolean allowed =  super.preHandle(request, response);
        logger.debug("super.preHandle：{}", allowed);

        return allowed;
		
	}
	
	private boolean isFilterChainContinued(ServletRequest request, ServletResponse response, String path, Object pathConfig)	throws Exception {

		if (isEnabled(request, response, path, pathConfig)) {
			//return onPreHandle(request, response, pathConfig);
			//return super.isAccessAllowed(request, response, pathConfig);
			return this.isAccessAllowed(request, response, pathConfig);
		}

		return true;
	}
    
    protected boolean ctxPathsMatch(String path, ServletRequest request) {
        String requestURI = getPathWithinApplication(request);
        if(StringUtils.isNotEmpty(ctxPath)){
        	path = "/" + ctxPath + path;
        }
		boolean isMatch = pathsMatch(path, requestURI);
        //logger.debug("请求地址：{}，权限库地址：{}，匹配结果：{}",requestURI, path, isMatch);
        return isMatch;
    }
    
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
    	
    	logger.debug("{} 资源需要的角色信息：{}", this.getName(), mappedValue);
    	//return super.isAccessAllowed(request, response, mappedValue);
    	
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        
        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        //Set<String> roles = CollectionUtils.asSet(rolesArray);
        //return subject.hasAllRoles(roles);
        for (String role : rolesArray) {
			if(subject.hasRole(role)){
				logger.debug("有角色权限 {} ", role);
				return true;
			}
		}
        return false;
    	
    }
    
    @Override
    public Filter processPathConfig(String path, String config) {

    	if(config==null){
    		return this;
    	}
    	// 通过 addToChain 添加到权限拦截中，会执行到这里来
        String[] values = null;
        if (config != null) {
            values = split(config);
        }
        
        if(this.appliedPaths.containsKey(path) && values!=null){
        	//System.out.println(values.getClass().isArray());
        	values = (String[]) ArrayUtils.addAll(values, (String[])appliedPaths.get(path));
        }

        this.appliedPaths.put(path, values);
        return this;
    }

	public String getCtxPath() {
		return ctxPath;
	}

	public void setCtxPath(String ctxPath) {
		this.ctxPath = ctxPath;
	}


    
    
    
}
