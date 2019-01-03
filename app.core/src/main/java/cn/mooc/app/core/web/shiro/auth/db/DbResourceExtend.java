package cn.mooc.app.core.web.shiro.auth.db;

public interface DbResourceExtend {

	public void addResource(String menuUrl, String roleTag);
	
	public void addResource(String menuUrl, String filterName, String roleTag);
	
}
