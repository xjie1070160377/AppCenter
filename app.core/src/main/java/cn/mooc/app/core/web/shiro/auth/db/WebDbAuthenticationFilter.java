package cn.mooc.app.core.web.shiro.auth.db;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.TriggerType;
import cn.mooc.app.core.event.selector.LoginEventSelector;
import cn.mooc.app.core.exception.VerifyCodeException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpUtil;
import cn.mooc.app.core.web.shiro.auth.filter.LoginCheckExtend;
import cn.mooc.app.core.web.shiro.model.AppUserToken;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

/**
 * 登录提交过来的表单数据处理
 * 
 * @author Taven
 *
 */
public class WebDbAuthenticationFilter extends FormAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String filterName = "default";
	/**
	 * 是否需要验证码
	 */
	private boolean checkValidateCode = true;
	
	private int maxLoginFail = 3;
	
	private boolean chkPwdSafe = false;
		
	/**
	 * 验证码对应的表单参数名称
	 */
	private String validateCodeParameter = "verifyCode";
	
	/**
	 * 验证码保存在session中的名称
	 */
	private String validateCodeSessionName = "verifyCode";
	
	
	private String authLoginUrl;
	
	private Set<LoginCheckExtend> loginCheckExtends = new HashSet<LoginCheckExtend>();
	
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private SysDataService sysDataService;
	
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        
		//是否需要校验验证码
		boolean forceCheckCode = HttpUtil.getSessionAttr((HttpServletRequest) request, "forceCheckCode", false);
		if (checkValidateCode || forceCheckCode) {
        	try{
        		checkValidateCode(request);
        	}catch(Exception e){
        		  		
        		AuthenticationToken token = createToken(request, response);
        		return onLoginFailure(token, new VerifyCodeException("验证码不对"), request, response);
        	}
        }
		
		return super.executeLogin(request, response);       
		
	}
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		//将接收到的验证对象转换为验证对象
		//return super.createToken(request, response);
		
		String username = getUsername(request);
        String password = getPassword(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String _loginType = WebUtils.getCleanParam(request, "loginType");
        
        AppUserToken appUserToken = new AppUserToken();
        appUserToken.setUsername(username);
        appUserToken.setPassword(password != null ? password.toCharArray() : null);
        appUserToken.setRememberMe(rememberMe);
        appUserToken.setHost(host);
        
        if(StringUtils.isNotBlank(_loginType)){
        	appUserToken.setLoginType(Integer.parseInt(_loginType));
        }
        
        
        return appUserToken;
        
	}
	
	@Override
	protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
		//判断当前请求的页面是否为登录的Url，如果是，则直接显示页面
		return pathsMatch(this.getAuthLoginUrl(), request) || super.isLoginRequest(request, response);
		
	}
	
	public String getMethod(ServletRequest request){
		
		if(request instanceof HttpServletRequest){
			HttpServletRequest req = (HttpServletRequest) request;
			return req.getMethod().toUpperCase();
		}
		
		return "GET";
	}
	
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		String requestURI = getPathWithinApplication(request);
		logger.debug("当前请求URL：" + requestURI);

		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		if (isAllowed && isLoginRequest(request, response)) {
			try {
				if(requestURI.equals(this.getAuthLoginUrl())){
					
					if(this.getMethod(request).equals("POST") && SecurityUtils.getSubject().isAuthenticated()){
						return onAccessDenied(request, response, mappedValue);
					}
					return true;
				}else{
					issueSuccessRedirect(request, response);
				}
				
			} catch (Exception e) {
				logger.error("", e);
			}
			return false;
		}
		return isAllowed || onAccessDenied(request, response, mappedValue);
	}
	
	@Override
	public boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		//登录成功
		//记录用户登录日志 或 IP等信息
		LoginUserInfo shiroUser = (LoginUserInfo) subject.getPrincipal();
		
		logger.debug("用户 {} 登录成功", shiroUser.getUserName());
		
		for (LoginCheckExtend loginCheckExtend : loginCheckExtends) {
			logger.debug("有扩展校验逻辑，执行：{}", loginCheckExtend);
			if(loginCheckExtend==null){
				continue;
			}
			boolean result = loginCheckExtend.extendCheck(shiroUser, request, response);
			if(!result){
				logger.debug("{} 用户扩展校验未通过，退出登录", shiroUser.getUserName());
				subject.logout();
				this.redirectToLogin(request, response);
				return false;
			}
		}
		
		//		
		if(chkPwdSafe){
			String password = "";
			if(token instanceof UsernamePasswordToken){
				password = new String(((UsernamePasswordToken)token).getPassword());
			}
			if(StringUtils.isNotBlank(password) && !this.sysDataService.checkSysUserPwdSafe(password)){
				HttpUtil.setSessionAttr((HttpServletRequest) request, "forceChangePwd", true);
				
			}
		}
		
		
		//清空强制输入验证码的选项
		this.resetForceCheck(request);
		
		SysUserExt userExt = this.sysDataService.getSysUserExt(shiroUser.getUserId());
		
		shiroUser.setNickName(userExt.getNickName());
		shiroUser.setAvatarUrl(userExt.getAvatarUrl());
				
		OprEvent oprEvent = new OprEvent(request);
		oprEvent.setLoginUser(shiroUser);
		oprEvent.setTriggerType(TriggerType.User);
		oprEvent.addSelector(new LoginEventSelector());
		//发送事件
		eventDispatch.postEvent(oprEvent);
		
		
		return super.onLoginSuccess(token, subject, request, response);
		
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//
		logger.debug("该请求无权限访问，执行 onAccessDenied");
		return super.onAccessDenied(request, response);
	}
	
	private void resetForceCheck(ServletRequest request){
		HttpUtil.setSessionAttr((HttpServletRequest) request, "loginFaileCount", 0);
		HttpUtil.setSessionAttr((HttpServletRequest) request, "forceCheckCode", false);
	}
	
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,  ServletRequest request, ServletResponse response) {
		//登录失败
		logger.debug("用户登录失败，执行 onLoginFailure");
		
		int loginFaileCount = HttpUtil.getSessionAttr((HttpServletRequest) request, "loginFaileCount", 0);
		loginFaileCount++;
		HttpUtil.setSessionAttr((HttpServletRequest) request, "loginFaileCount", loginFaileCount);
		if(loginFaileCount>= maxLoginFail){
			HttpUtil.setSessionAttr((HttpServletRequest) request, "forceCheckCode", true);
		}
		
		/**
		 * 这里直接跳转后，会丢失 shiroLoginFailure 信息
		if(StringUtils.isNotBlank(this.getAuthLoginUrl())){
			try {
				WebUtils.issueRedirect(request, response, this.getAuthLoginUrl());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		*/

		return super.onLoginFailure(token, e, request, response);
	}
	
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		//登录成功后的跳转动作
		super.issueSuccessRedirect(request, response);
	}
	
	@Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		logger.debug("将当前请求转到登录页面，返回到：{}", this.getAuthLoginUrl());
        WebUtils.issueRedirect(request, response, this.getAuthLoginUrl());
    }
	
	private void checkValidateCode(ServletRequest request) throws Exception {
		HttpServletRequest _request = (HttpServletRequest) request;
		Object sessionCode = _request.getSession().getAttribute(validateCodeSessionName);
		String sessionValidateCode = null == sessionCode ? "" : sessionCode.toString();
		String postValidateCodeParameter = request.getParameter(validateCodeParameter);
		//验证码输入不能为空，不区分大小写
		if (StringUtils.isBlank(postValidateCodeParameter) || !sessionValidateCode.equalsIgnoreCase(postValidateCodeParameter)) {
			throw new Exception("验证码输入不正确");
		}
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

	public EventDispatch getEventDispatch() {
		return eventDispatch;
	}

	public void setEventDispatch(EventDispatch eventDispatch) {
		this.eventDispatch = eventDispatch;
	}

	public SysDataService getSysDataService() {
		return sysDataService;
	}

	public void setSysDataService(SysDataService sysDataService) {
		this.sysDataService = sysDataService;
	}

	public Set<LoginCheckExtend> getLoginCheckExtend() {
		return loginCheckExtends;
	}

	public void addLoginCheckExtend(LoginCheckExtend loginCheckExtend) {
		this.loginCheckExtends.add(loginCheckExtend);
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public int getMaxLoginFail() {
		return maxLoginFail;
	}

	public void setMaxLoginFail(int maxLoginFail) {
		this.maxLoginFail = maxLoginFail;
	}

	public boolean isChkPwdSafe() {
		return chkPwdSafe;
	}

	public void setChkPwdSafe(boolean chkPwdSafe) {
		this.chkPwdSafe = chkPwdSafe;
	}


}
