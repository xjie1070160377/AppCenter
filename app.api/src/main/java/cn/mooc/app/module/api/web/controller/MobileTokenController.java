package cn.mooc.app.module.api.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.TriggerType;
import cn.mooc.app.core.event.selector.LoginEventSelector;
import cn.mooc.app.core.model.LdapPerson;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.security.CredentialsDigest;
import cn.mooc.app.core.security.SaltPwdUtils;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.service.LdapLoginService;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.api.extend.MobileTokenExtends;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.service.MobileSyncTokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.api.utils.MobileRSA;
import cn.mooc.app.module.api.utils.RedisTokenUtil;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.model.MobileUserInfo;
import cn.mooc.app.module.cms.service.CmsContext;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.service.SnsOpLogService;


/**
 * @author hwt
 * @version 1.0
 * 
 */
@Controller
public class MobileTokenController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		

	@Autowired
	private WebContext webContext;
	@Autowired
	private CmsContext cmsContext;
	@Autowired
	private LdapLoginService ldapLoginService;
	@Autowired
	private CredentialsDigest credentialsDigest;
	@Autowired
	private RedisTokenUtil redisTokenUtil;
	@Autowired
	private MobileRSA rsaUtil;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private SnsOpLogService snsOpLogService;
	@Autowired
	private MobileSyncTokenService mobileSyncTokenService;

	/**
	 * 用户登录，返回token、用户信息
	 * 
	 * @param username
	 *            登录名
	 * @param password
	 *            密码
	 */
	@RequestMapping(value = { "/m-token.htx" })
	public void getToken(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		String ip = Servlets.getRemoteAddr(request);
		if (StringUtils.isEmpty(username)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("登录名不能为空！")));
			return;
		}
		if (StringUtils.isEmpty(password)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("密码不能为空！")));
			return;
		}
		String ausername = "", apassword = "";
		try{
			ausername = rsaUtil.decrypt(username);
			
			apassword = rsaUtil.decrypt(password);
			
		}catch(Exception e){
			try{
				ausername = rsaUtil.olddecrypt(username);
				
				apassword = rsaUtil.olddecrypt(password);
			}catch(Exception ex){
//				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("安全校验失败！！")));
//				return;
				ausername = username;
				apassword = password;
			}
		}
		username = ausername;
		password = apassword;
		SysUserEntity user = sysDataService.getUserInfo(username);
		
		if (user == null) {
			logger.debug("{} 用户不存在，尝试从OLDAP登录", username);
			LdapPerson ldapPerson = ldapLoginService.login(username, password);
			if (ldapPerson.loginSuccess) {
				if (user == null) {
					user = addUser(username, ip, password, ldapPerson);
				}
				renderSuccessToken(request, response, mapper, user, null, ip);
				return;
			}
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("未找到登录名为：" + username + "的用户！")));
			return;
		}
		if (user.getStatus() == 0) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("登录名为：" + user.getUserName() + "用户已被管理员禁用！")));
			return;
		}
		
		SysUserExt userExt = this.sysDataService.getSysUserExt(user.getId());
		
		
		byte[] saltBytes = credentialsDigest.getSaltBytes(user.getPwdSalt());
		String encPass = SaltPwdUtils.md5Password(credentialsDigest, password, saltBytes);
		if (encPass.equals(user.getPassWord())) {
			renderSuccessToken(request, response, mapper, user,userExt, ip);
		} else {
			
			logger.debug("{} 用户密码校验失败，尝试从OLDAP登录", username);
			LdapPerson ldapPerson = ldapLoginService.login(username, password);
			if (ldapPerson.loginSuccess) {
				renderSuccessToken(request, response, mapper, user, userExt, ip);
				return;
			}
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("登录名为：" + username + "用户的密码不匹配！")));
			return;
		}
	}

	private void renderSuccessToken(HttpServletRequest request, HttpServletResponse response, JsonMapper mapper, SysUserEntity user,SysUserExt sysUserExt, String ip) {

		String token = redisTokenUtil.getToken("" + user.getId(), credentialsDigest);
		redisTokenUtil.saveTokenUID(token, "" + user.getId());
		Site site = cmsContext.getWebCurrentSite(request);
		String siteUrl = site.getSiteUrl();
		SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(user.getId());
		MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
//		MobileUserInfo userInfo = MobileModelConvert.convertUser(user, sysUserExt, siteUrl);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("data", userInfo);
//		webContext.sysUserLog(LogType.UserLogin, user.getId(), user.getUserName(), "用户登录成功.");
		//logService.loginSuccess(ip, user.getId());
		//snsOpLogService.operation(site, user.getId(), SnsOpLog.FTYPE_LOGIN, user.getId(), "登录", user.getRName());
		logger.debug("user login, message={}.", "UID:" + user.getId() + ",username:" + user.getUserName() + ",token:" + token);
		//登录完成，并且token已经创建，下面对其他模块进行token同步
		//
		this.syncUserToken(user.getId(), user.getUserName(), token, null);
		
		LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
		OprEvent oprEvent = new OprEvent(request);
		oprEvent.setLoginUser(shiroUser);
		oprEvent.setTriggerType(TriggerType.User);
		oprEvent.addSelector(new LoginEventSelector());
		//发送事件，暂时屏蔽
		//eventDispatch.postEvent(oprEvent);
		
		//关闭异步事件，可以用下面的方法记录登录次数
		webContext.sysUserLog(LogType.UserLogin, user.getId(), user.getUserName(), "用户登录成功");
		
		snsOpLogService.operation(site.getId(), new Long(user.getId()).intValue() , SnsOpLog.FTYPE_LOGIN, new Long(user.getId()).intValue(), user.getUserName(), user.getUserName());
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(map)));
	}

	private SysUserEntity addUser(String username, String ip, String password, LdapPerson ldapPerson) {
		SysUserEntity user = new SysUserEntity();
		user.setUserName(username);
		user.setPassWord(password);
		user.setStatus(1);
		//红客系统中约定：0：校外用户，10：校内实名用户
		user.setuType(10);
		user.setSuperUser(false);
		
		SysUserExt sysUserExt = new SysUserExt();
		sysUserExt.setRealName(ldapPerson.getDisplayName());
		sysUserExt.setNickName(ldapPerson.getDisplayName());
		sysUserExt.setPhone("");
		sysUserExt.setEmail(username + "@gfkd.mtn");
		sysUserExt.setGender(0);
		
		if(StringUtils.isBlank(sysUserExt.getUserName())){
			sysUserExt.setUserName(user.getUserName());
		}
		
		Integer[] roleIds = null, orgIds = { 1 }, groupIds = { 1 };
		Integer orgId = 1, groupId = 1;
		
		try {
			sysDataService.saveSysUser(user);
			sysUserExt.setUserId(user.getId());
			sysUserExt.setUserName(user.getUserName());
			sysDataService.updateUserExt(sysUserExt);
		
		} catch (Exception e) {
			logger.error("", e);
		}		
		
		return user;
	}

	/**
	 * 重置token
	 * 
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-refreshToken.htx" })
	public void refreshToken(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		if (StringUtils.isEmpty(token)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("token不能为空！")));
			return;
		}
		String UID = redisTokenUtil.getUID(token);
		if (StringUtils.isNotEmpty(UID)) {

			SysUserEntity user = webContext.getSysUser(StringUtil.string2Int(UID));
			//SysUserExt userExt = this.sysDataService.getSysUserExt(user.getId());
			if (user.getStatus() == 0) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("登录名为：" + user.getUserName() + "用户已被管理员禁用！")));
				return;
			} else {
				String newToken = redisTokenUtil.getToken("" + user.getId(), credentialsDigest);
				redisTokenUtil.clearOldToken("" + user.getId(), token);
				redisTokenUtil.saveTokenUID(newToken, "" + user.getId());
				Site site = cmsContext.getWebCurrentSite(request);
				// Map<String, String> map = new HashMap<String, String>();
				// map.put("token", newToken);
				String ip = Servlets.getRemoteAddr(request);
				//webContext.sysUserLog(LogType.UserLogin, user.getId(), user.getUserName(), "用户登录成功");
				LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
				OprEvent oprEvent = new OprEvent(request);
				oprEvent.setLoginUser(shiroUser);
				oprEvent.setTriggerType(TriggerType.User);
				oprEvent.addSelector(new LoginEventSelector());
				//发送事件
				eventDispatch.postEvent(oprEvent);
				//logService.loginSuccess(ip, user.getId());
				//snsOpLogService.operation(site, user.getId(), SnsOpLog.FTYPE_LOGIN, user.getId(), "登录", user.getRName());
				logger.debug("user login, message={}.", "UID:" + user.getId() + ",username:" + user.getUserName() + ",token:" + token);
				//
				this.syncUserToken(user.getId(), user.getUserName(), newToken, token);
				
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(newToken)));
			}
		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("当前token未匹配到用户，请重新认证！。")));
			return;
		}
	}

	/**
	 * 注销token
	 * 
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-clearToken.htx" })
	public void clearToken(String token, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		if (StringUtils.isEmpty(token)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("token不能为空！")));
			return;
		}
		String UID = redisTokenUtil.getUID(token);
		if (StringUtils.isNotEmpty(UID)) {
			SysUserEntity user = webContext.getSysUser(StringUtil.string2Int(UID));
			if (user.getStatus() == 0) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("登录名为：" + user.getUserName() + "用户已被管理员禁用！")));
				return;
			} else {
				redisTokenUtil.clearOldToken("" + user.getId(), token);
				logger.debug("user logout, message={}.", "UID:" + user.getId() + ",username:" + user.getUserName() + ",token:" + token);
				//				
				this.syncUserToken(user.getId(), user.getUserName(), null, token);
				
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
			}
		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("当前token未匹配到用户，请重新认证！。")));
			return;
		}
	}

	public void syncUserToken(long uid, String name, String newToken, String oldToken){
		List<MobileTokenExtends> tokenExtends = mobileSyncTokenService.getTokenExtends();
		for (MobileTokenExtends mobileTokenExtends : tokenExtends) {
			try {
				mobileTokenExtends.syncUserToken(uid, name, newToken, oldToken);
			} catch (Exception e) {
				logger.error("syncUserToken执行异常", e);
			}
		}
	}

}
