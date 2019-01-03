package cn.mooc.app.core.web.shiro.auth.filter;

public class AuthFilterConfig {

	private String usernameParam;
	
	private String passwordParam;
	
	private String successUrl;
	
	private String authLoginUrl;
	
	private String filterName;
	
	private String pathPrefix;
	
	private int maxLoginFail = 999;
	
	private LoginCheckExtend loginCheckExtend;

	public String getUsernameParam() {
		return usernameParam;
	}

	public void setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
	}

	public String getPasswordParam() {
		return passwordParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getAuthLoginUrl() {
		return authLoginUrl;
	}

	public void setAuthLoginUrl(String authLoginUrl) {
		this.authLoginUrl = authLoginUrl;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public LoginCheckExtend getLoginCheckExtend() {
		return loginCheckExtend;
	}

	public void setLoginCheckExtend(LoginCheckExtend loginCheckExtend) {
		this.loginCheckExtend = loginCheckExtend;
	}

	public int getMaxLoginFail() {
		return maxLoginFail;
	}

	public void setMaxLoginFail(int maxLoginFail) {
		this.maxLoginFail = maxLoginFail;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public void setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}
	
	
}
