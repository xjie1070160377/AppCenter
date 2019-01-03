package cn.mooc.app.core.web.shiro.auth.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.shiro.auth.db.DbShiroDataService;
import cn.mooc.app.core.web.shiro.auth.db.WebDbAuthenticationFilter;

@Service
public class AuthFilterRegistry {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DbShiroDataService dbShiroDataService;
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private SysDataService sysDataService;
	
	private Map<String, AuthFilterConfig> authFilters = new HashMap<String, AuthFilterConfig>();
	
	private Set<LoginCheckExtend> ajaxLoginExtends = new HashSet<LoginCheckExtend>();
	
	private Set<LogoutExtend> logoutExtends = new HashSet<LogoutExtend>();

	public Map<String, AuthFilterConfig> getAuthFilters() {
		return authFilters;
	}
	
	public Set<LogoutExtend> getLogoutExtends() {
		return logoutExtends;
	}	

	public Set<LoginCheckExtend> getAjaxLoginExtends() {
		return ajaxLoginExtends;
	}

	public void registry(AuthFilterConfig config){
		String key = config.getFilterName() + "_" + config.getAuthLoginUrl();
		authFilters.put(key, config);
		this.loadAuthFilter(config);
		
	}
	
	public void reLoadAllAuthFilter(){
		for (String filterKey : authFilters.keySet()) {
			try {
				this.reLoadAuthFilter(filterKey);
			} catch (Exception e) {
				logger.error("reLoadAllAuthFilter", e);
			}
		}
	}
	
	public void reLoadAuthFilter(String filterKey) throws Exception{
		if(authFilters.containsKey(filterKey)){
			AuthFilterConfig config = authFilters.get(filterKey);
			this.loadAuthFilter(config);
		}else{
			
			throw new Exception("没有该filterKey的验证器");
		}
				
		
	}
	
	public Map<String, Filter> getRegFilters(){
		
		return dbShiroDataService.getFilters();
	}
	
	public void addAuthFilter(String filterName, String authUrl, Filter filter){
		//String key = filterName + "_" + authUrl;
		//authFilters.put(key, config);
		
		dbShiroDataService.addFilter(filterName, filter);
		dbShiroDataService.addChain(authUrl, filterName);
		
		//dbShiroDataService.addChain(config.getPathPrefix() + regex, config.getFilterName() + ",webRoles");
		
	}
	
		
	public void loadAuthFilter(AuthFilterConfig config){
		
		WebDbAuthenticationFilter filter = new WebDbAuthenticationFilter();
		filter.setEventDispatch(eventDispatch);
		filter.setSysDataService(sysDataService);
		filter.addLoginCheckExtend(config.getLoginCheckExtend());
		
		filter.setUsernameParam(config.getUsernameParam());
		filter.setPasswordParam(config.getPasswordParam());
		filter.setCheckValidateCode(false);
		filter.setMaxLoginFail(config.getMaxLoginFail());
		filter.setSuccessUrl(config.getSuccessUrl());
		filter.setAuthLoginUrl(config.getAuthLoginUrl());
		filter.setFilterName(config.getFilterName());
		
		dbShiroDataService.addFilter(config.getFilterName(), filter);
		dbShiroDataService.addChain(config.getAuthLoginUrl(), config.getFilterName());
		
		if (StringUtils.isNotBlank(config.getPathPrefix())) {
			String regex = "/**";
			if(config.getPathPrefix().endsWith("/")){
				regex = "**";
			}
			dbShiroDataService.addChain(config.getPathPrefix() + regex, config.getFilterName() + ",webRoles");
		}
		
		
		
	}
	
	public void addAdminLoginCheckExtend(LoginCheckExtend loginCheckExtend){
		Filter filter = dbShiroDataService.getFilter("mauth");
		if(filter!=null && filter instanceof WebDbAuthenticationFilter){
			((WebDbAuthenticationFilter) filter).addLoginCheckExtend(loginCheckExtend);
		}
		
	}
	
	public void registryAjaxLoginExtend(LoginCheckExtend loginCheckExtend){
		
		this.ajaxLoginExtends.add(loginCheckExtend);
		
	}
	
	public void registryLogoutExtend(LogoutExtend logoutExtend){
		
		this.logoutExtends.add(logoutExtend);
		
	}
	
}
