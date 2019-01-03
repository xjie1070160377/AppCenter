package cn.mooc.app.module.api.anonymous.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.security.CredentialsDigest;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.api.utils.RedisTokenUtil;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.model.MobileUserInfo;
import cn.mooc.app.module.cms.service.CmsContext;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.service.SnsOpLogService;

@Controller
public class AnonymousController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private CmsContext cmsContext;
	@Autowired
	private RedisTokenUtil redisTokenUtil;
	@Autowired
	private SnsOpLogService snsOpLogService;
	@Autowired
	private CredentialsDigest credentialsDigest;
	@Value("${app.anonymous.uid}")
	private long anonymous_uid = 2;
	
	@RequestMapping(value = { "/m-anonymous-token.htx" })
	public void getAnonymousToken(Model model, HttpServletRequest request, HttpServletResponse response) {
		//String ip = Servlets.getRemoteAddr(request);
		//id为0的为匿名账户
		try{
			SysUserEntity user = sysDataService.getUserInfoById(StringUtil.string2Long(anonymous_uid));
			
			String token = redisTokenUtil.getToken("" + user.getId(), credentialsDigest);
			redisTokenUtil.saveTokenUID(token, "" + user.getId());
			Site site = cmsContext.getWebCurrentSite(request);
			String siteUrl = site.getSiteUrl();
			SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(user.getId());
			MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("token", token);
			map.put("data", userInfo);
			webContext.sysUserLog(LogType.UserLogin, user.getId(), user.getUserName(), "匿名用户登录成功");
	
			logger.debug("匿名用户登录 login, message={}.", "UID:" + user.getId() + ",username:" + user.getUserName() + ",token:" + token);
			
			snsOpLogService.operation(site.getId(), new Long(user.getId()).intValue() , SnsOpLog.FTYPE_LOGIN, new Long(user.getId()).intValue(), user.getUserName(), user.getUserName());
			Servlets.writeHtml(response, JsonUtil.toJson(MobileResultData.createSuccess(map)));
		}catch(Exception ex){
			Servlets.writeHtml(response, JsonUtil.toJson(MobileResultData.createError("匿名登录失败！")));
			logger.error("匿名登录失败", ex);
			return;
		}
		
	}
	
	
}
