package cn.mooc.app.core.plugin.support;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.mooc.app.core.plugin.AbstractPlugin;
import cn.mooc.app.core.plugin.support.model.OAuthUserInfo;

public abstract class OAuthPlugin extends AbstractPlugin {

	/**
	 * 获取Oauth认证路径
	 * 
	 * @param returnUrl
	 * @return
	 * @throws Exception
	 */
	public abstract String getOpenLoginUrl(String returnUrl) throws Exception;
	
	/**
	 * 获取oauth用户信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public abstract OAuthUserInfo getOAuthUserInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 默认图标
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getIconDefault();
	
	/**
	 * 悬浮图标
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getIconHover();

	
	
}
