package cn.mooc.app.core.web.shiro.auth.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

public interface LoginCheckExtend {

	public boolean extendCheck(LoginUserInfo userInfo, ServletRequest request, ServletResponse response) throws Exception;
	
}
