package cn.mooc.app.core.web.shiro.auth.db;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DbShiroDataService implements DbResourceExtend {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String filterChainDefinitions;
	
	private DbShiroFilterFactoryBean dbShiroFilterFactoryBean;

	private Set<AuthResourceExtend> authResourceExtends = new HashSet<AuthResourceExtend>();
			
	public String getFilterChainDefinitions() {
		return filterChainDefinitions;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}
	
	public DbShiroFilterFactoryBean getDbShiroFilterFactoryBean() {
		return dbShiroFilterFactoryBean;
	}

	public void setDbShiroFilterFactoryBean(DbShiroFilterFactoryBean dbShiroFilterFactoryBean) {
		this.dbShiroFilterFactoryBean = dbShiroFilterFactoryBean;
	}

	public void addChain(String chainName, String chainDefinition) {
		this.dbShiroFilterFactoryBean.createChain(chainName, chainDefinition);
	}
	
	public void addFilter(String name, Filter filter){
		this.dbShiroFilterFactoryBean.addFilter(name, filter);
		
	}
	
	public Filter getFilter(String name){
		return this.dbShiroFilterFactoryBean.getFilter(name);
	}
	
	public Map<String, Filter> getFilters() {
        return dbShiroFilterFactoryBean.getFilters();
    }
	
	public void reloadPermission() {
		this.dbShiroFilterFactoryBean.reloadPermission();
	}

	public Set<AuthResourceExtend> getAuthResourceExtends() {
		return authResourceExtends;
	}

	public void setAuthResourceExtends(Set<AuthResourceExtend> authResourceExtends) {
		this.authResourceExtends = authResourceExtends;
	}
	
	/**
	 * 添加额外的权限资源实现逻辑
	 * 
	 * @param authResourceExtend
	 */
	public void regAuthResourceExtend(AuthResourceExtend authResourceExtend){
		
		this.authResourceExtends.add(authResourceExtend);
/*		try {
			authResourceExtend.loadPermissionResource(this);
		} catch (Exception e) {
			logger.error("regAuthResourceExtend",e);
		}*/
		
	}
	
	@Override
	public void addResource(String menuUrl, String roleTag) {
		this.addResource(menuUrl, "webRoles", roleTag);
		
	}

	@Override
	public void addResource(String menuUrl, String filterName, String roleTag) {
		this.dbShiroFilterFactoryBean.getFilterChainManager().addToChain(menuUrl, filterName, roleTag);
		
	}

	
	
}
