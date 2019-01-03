package cn.mooc.app.core.event;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

public class OprEvent extends SysEvent {
	
	private String ipAddr;
	private String reqUrl;
	private LoginUserInfo loginUser;
		
	public OprEvent(){
		
	}
	
	public OprEvent(ServletRequest request){
		String ipAddr = "";
		String reqUrl = "";
		if(request instanceof HttpServletRequest){
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			ipAddr = ValidatorUtil.getIpAddr(httpServletRequest);
			reqUrl = httpServletRequest.getRequestURI();
		}
		
		this.setReqUrl(reqUrl);
		this.setIpAddr(ipAddr);
	}
	
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getReqUrl() {
		return reqUrl;
	}
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public LoginUserInfo getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(LoginUserInfo loginUser) {
		this.loginUser = loginUser;
	}
	


	
	
}
