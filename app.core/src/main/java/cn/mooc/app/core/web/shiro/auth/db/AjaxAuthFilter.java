package cn.mooc.app.core.web.shiro.auth.db;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.TriggerType;
import cn.mooc.app.core.event.selector.LoginEventSelector;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.shiro.auth.filter.AuthFilterRegistry;
import cn.mooc.app.core.web.shiro.auth.filter.LoginCheckExtend;
import cn.mooc.app.core.web.shiro.model.AppUserToken;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

/**
 * CmsAuthenticationFilter
 * 
 * @author csmooc
 * 
 */
public class AjaxAuthFilter extends FormAuthenticationFilter {
	private Logger logger = LoggerFactory.getLogger(AjaxAuthFilter.class);

	/**
	 * 是否需要验证码
	 */
	private boolean checkValidateCode = false;

	/**
	 * 验证码对应的表单参数名称
	 */
	private String validateCodeParameter = "verifyCode";

	/**
	 * 验证码保存在session中的名称
	 */
	private String validateCodeSessionName = "verifyCode";

	private String authLoginUrl;

	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private AuthFilterRegistry authFilterRegistry;

	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		// 是否需要校验验证码
		if (checkValidateCode) {
			try {
				checkValidateCode(request);
			} catch (AuthenticationException e) {
				throw new Exception("验证码不对");
			}
		}

		return super.executeLogin(request, response);
	}

	private void checkValidateCode(ServletRequest request) throws Exception {
		HttpServletRequest _request = (HttpServletRequest) request;
		Object sessionCode = _request.getSession().getAttribute(
				validateCodeSessionName);
		String sessionValidateCode = null == sessionCode ? "" : sessionCode
				.toString();
		String postValidateCodeParameter = request
				.getParameter(validateCodeParameter);
		// 验证码输入不能为空，不区分大小写
		if (StringUtils.isBlank(postValidateCodeParameter)
				|| !sessionValidateCode
						.equalsIgnoreCase(postValidateCodeParameter)) {
			throw new Exception("验证码输入不正确");
		}
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		// 将接收到的验证对象转换为验证对象
		// return super.createToken(request, response);

		String username = getUsername(request);
		String password = getPassword(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String _loginType = WebUtils.getCleanParam(request, "loginType");

		AppUserToken appUserToken = new AppUserToken();
		appUserToken.setUsername(username);
		appUserToken.setPassword(password != null ? password.toCharArray()
				: null);
		appUserToken.setRememberMe(rememberMe);
		appUserToken.setHost(host);

		if (StringUtils.isNotBlank(_loginType)) {
			appUserToken.setLoginType(Integer.parseInt(_loginType));
		}

		return appUserToken;

	}

	@Override
	protected boolean isLoginRequest(ServletRequest request,
			ServletResponse response) {
		// 判断当前请求的页面是否为登录的Url，如果是，则直接显示页面
		return pathsMatch(this.getAuthLoginUrl(), request)
				|| super.isLoginRequest(request, response);

	}

	@Override
	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
//		String requestURI = getPathWithinApplication(request);
//		logger.debug("当前请求URL：" + requestURI);
//
//		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
//		if (isAllowed && isLoginRequest(request, response)) {
//			try {
//				issueSuccessRedirect(request, response);
//			} catch (Exception e) {
//				logger.error("", e);
//			}
//			return false;
//		}
//		return isAllowed || onAccessDenied(request, response, mappedValue);
		return executeLogin(request, response);
	}

	@Override
	public boolean onLoginSuccess(AuthenticationToken token, Subject subject,
			ServletRequest request, ServletResponse response) throws Exception {
		// 登录成功
		// 记录用户登录日志 或 IP等信息
		LoginUserInfo shiroUser = (LoginUserInfo) subject.getPrincipal();

		SysUserExt userExt = this.sysDataService.getSysUserExt(shiroUser
				.getUserId());

		shiroUser.setNickName(userExt.getNickName());
		shiroUser.setAvatarUrl(userExt.getAvatarUrl());

		logger.debug("用户 {} 登录成功", shiroUser.getUserName());
		
		OprEvent oprEvent = new OprEvent(request);
		oprEvent.setLoginUser(shiroUser);
		oprEvent.setTriggerType(TriggerType.User);
		oprEvent.addSelector(new LoginEventSelector());
		//发送事件
		eventDispatch.postEvent(oprEvent);
		
		for (LoginCheckExtend loginExtend : authFilterRegistry.getAjaxLoginExtends()) {
			try{
				loginExtend.extendCheck(shiroUser, request, response);
			}catch (Exception e) {
				logger.error("ajaxOnLoginSuccess", e);
			}
		}

		HttpResponseUtil.writeHtml((HttpServletResponse)response, "");
		return false;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		//

		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		// 登录失败

		/**
		 * 这里直接跳转后，会丢失 shiroLoginFailure 信息
		 * if(StringUtils.isNotBlank(this.getAuthLoginUrl())){ try {
		 * WebUtils.issueRedirect(request, response, this.getAuthLoginUrl()); }
		 * catch (IOException e1) { e1.printStackTrace(); } }
		 */
		System.out.println(e.getClass().getName());
		HttpResponseUtil.writeHtml((HttpServletResponse)response, e.getClass().getName());
		return false;
	}

	public boolean isCheckValidateCode() {
		return checkValidateCode;
	}

	public void setCheckValidateCode(boolean checkValidateCode) {
		this.checkValidateCode = checkValidateCode;
	}

	public String getValidateCodeParameter() {
		return validateCodeParameter;
	}

	public void setValidateCodeParameter(String validateCodeParameter) {
		this.validateCodeParameter = validateCodeParameter;
	}

	public String getValidateCodeSessionName() {
		return validateCodeSessionName;
	}

	public void setValidateCodeSessionName(String validateCodeSessionName) {
		this.validateCodeSessionName = validateCodeSessionName;
	}

	public String getAuthLoginUrl() {
		return authLoginUrl;
	}

	public void setAuthLoginUrl(String authLoginUrl) {
		this.authLoginUrl = authLoginUrl;
	}

}