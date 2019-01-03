package cn.mooc.app.core.web.shiro.auth.db;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Nameable;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysMenuEntity;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.shiro.auth.filter.AuthFilterRegistry;

/**
 * 
 * Shiro默认的权限点资源配置是通过 ini配置文件  和 注解 来加载的，加载后交给 DefaultFilterChainManager 对象管理
 * 
 * 而 DefaultFilterChainManager 是在 ShiroFilterFactoryBean 的 createInstance() 方法中创建并初始化的
 * 
 * @author Taven
 *
 */
public class DbShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String filterChainDefinitions;
	
	private DefaultFilterChainManager filterChainManager;
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private DbShiroDataService dbShiroDataService;
	@Autowired
	private AuthFilterRegistry authFilterRegistry;
	
	@PostConstruct
	public void initBinder(){
		this.loadAllPermission();
		dbShiroDataService.setDbShiroFilterFactoryBean(this);
		dbShiroDataService.setFilterChainDefinitions(filterChainDefinitions);
	}	
	
	public void loadAllPermission(){
		this.clearPermission();
		this.loadPermissionFromIni();
		this.loadPermissionFromDb();
		this.loadAllMenuFromDb();
		this.loadPermissionFromExtend();
		
		logger.debug("-------------------DbShiroFilterFactoryBean 启动成功----------------------");
	        
	}
	
	/**
	 * 加载配置文件中的权限资源
	 */
	public void loadPermissionFromIni(){
		super.setFilterChainDefinitions(this.filterChainDefinitions);
		
		// 把所有的 filterChain 配置加到 filterChainManager 中
		Map<String, String> chains = getFilterChainDefinitionMap();
		if (!CollectionUtils.isEmpty(chains)) {
			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue();
				//格式为：/m/userlist = user,perms[superUser]
				this.filterChainManager.createChain(url, chainDefinition);
			}
		}
		
	}
	
	/**
	 * 从数据库中加载权限资源
	 */
	@Transactional
	public void loadPermissionFromDb(){
		
		//从数据库中读取所有角色对应的菜单
		List<SysRoleEntity> roles = this.sysDataService.getAvailableRoles();
		
		
		for (SysRoleEntity role : roles) {
			//菜单与角色
			for (SysMenuEntity menu : role.getMenus()) {
				String menuUrl = menu.getMenuUrl();

				if(StringUtils.isNotBlank(menuUrl)){
					this.filterChainManager.addToChain(menuUrl, "dbRoles", "Role_" + role.getId());
				}
				
				
			}
			
			
		}
		
		//所有用户默认有user角色,user角色默认有首页权限
		//this.filterChainManager.addToChain("/index", "roles", "user");
		
		
	}
	
	public void loadAllMenuFromDb(){
		List<SysMenuEntity> menus = sysDataService.getAllMenus();
		for (SysMenuEntity sysMenu : menus) {
			String menuUrl = sysMenu.getMenuUrl();

			if(StringUtils.isNotBlank(menuUrl)){
				this.filterChainManager.addToChain(menuUrl, "dbRoles", "Role_All_Menu");
			}
		}
		
	}
	
	/**
	 * 加载子模块中需要额外加载的菜单资源
	 */
	public void loadPermissionFromExtend(){
		
		Set<AuthResourceExtend> authResourceExtends = dbShiroDataService.getAuthResourceExtends();
		for (AuthResourceExtend authResourceExtend : authResourceExtends) {
			
			try {
				authResourceExtend.loadPermissionResource(dbShiroDataService);
			} catch (Exception e) {
				logger.error("loadPermissionFromExtend", e);
			}
		}
		
				
	}
	
	public void createChain(String chainName, String chainDefinition) {
		this.filterChainManager.createChain(chainName, chainDefinition);
	}
	
	public void addFilter(String name, Filter filter){

        applyGlobalPropertiesIfNecessary(filter);
        if (filter instanceof Nameable) {
            ((Nameable) filter).setName(name);
        }

        this.filterChainManager.addFilter(name, filter, false);
	}
	
	public Filter getFilter(String name){
		return filterChainManager.getFilter(name);
	}
	
	public void applyGlobalPropertiesIfNecessary(Filter filter) {
        applyLoginUrlIfNecessary(filter);
        applySuccessUrlIfNecessary(filter);
        applyUnauthorizedUrlIfNecessary(filter);
    }
	
	   private void applyLoginUrlIfNecessary(Filter filter) {
	        String loginUrl = getLoginUrl();
	        if (org.apache.shiro.util.StringUtils.hasText(loginUrl) && (filter instanceof AccessControlFilter)) {
	            AccessControlFilter acFilter = (AccessControlFilter) filter;
	            //only apply the login url if they haven't explicitly configured one already:
	            String existingLoginUrl = acFilter.getLoginUrl();
	            if (AccessControlFilter.DEFAULT_LOGIN_URL.equals(existingLoginUrl)) {
	                acFilter.setLoginUrl(loginUrl);
	            }
	        }
	    }

	    private void applySuccessUrlIfNecessary(Filter filter) {
	        String successUrl = getSuccessUrl();
	        if (org.apache.shiro.util.StringUtils.hasText(successUrl) && (filter instanceof AuthenticationFilter)) {
	            AuthenticationFilter authcFilter = (AuthenticationFilter) filter;
	            //only apply the successUrl if they haven't explicitly configured one already:
	            String existingSuccessUrl = authcFilter.getSuccessUrl();
	            if (AuthenticationFilter.DEFAULT_SUCCESS_URL.equals(existingSuccessUrl)) {
	                authcFilter.setSuccessUrl(successUrl);
	            }
	        }
	    }

	    private void applyUnauthorizedUrlIfNecessary(Filter filter) {
	        String unauthorizedUrl = getUnauthorizedUrl();
	        if (org.apache.shiro.util.StringUtils.hasText(unauthorizedUrl) && (filter instanceof AuthorizationFilter)) {
	            AuthorizationFilter authzFilter = (AuthorizationFilter) filter;
	            //only apply the unauthorizedUrl if they haven't explicitly configured one already:
	            String existingUnauthorizedUrl = authzFilter.getUnauthorizedUrl();
	            if (existingUnauthorizedUrl == null) {
	                authzFilter.setUnauthorizedUrl(unauthorizedUrl);
	            }
	        }
	    }
	

	
	/**
	 * 重现加载权限资源
	 */
	public void reloadPermission(){
		this.loadAllPermission();
		this.authFilterRegistry.reLoadAllAuthFilter();
	}
	
	public void clearPermission(){
		if(this.filterChainManager==null){this.filterChainManager = this.createFilterChainManager();}
		// 清空初始权限配置
		this.filterChainManager.getFilterChains().clear();
        this.getFilterChainDefinitionMap().clear();  
	}
	
	protected AbstractShiroFilter createInstance() throws Exception {
		return super.createInstance();
	}
	
	@Override
	public DefaultFilterChainManager createFilterChainManager() {
		if(this.filterChainManager==null){
			filterChainManager = (DefaultFilterChainManager) super.createFilterChainManager();
		}		
		return filterChainManager;
	}

	public String getFilterChainDefinitions() {
		return filterChainDefinitions;
	}

	@Override
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
		//super.setFilterChainDefinitions(filterChainDefinitions);
	}

	public DefaultFilterChainManager getFilterChainManager() {
		return filterChainManager;
	}

	public void setFilterChainManager(DefaultFilterChainManager filterChainManager) {
		this.filterChainManager = filterChainManager;
	}


}
