package cn.mooc.app.core.web.shiro.model;

import org.apache.shiro.authc.UsernamePasswordToken;

public class AppUserToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9181308642800482304L;

	
	/**
	 * 登录类型，0 会员登录，1 卖家登录，9 后台管理登录
	 */
	private int loginType = 0;


	public int getLoginType() {
		return loginType;
	}


	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}
	
	
}
