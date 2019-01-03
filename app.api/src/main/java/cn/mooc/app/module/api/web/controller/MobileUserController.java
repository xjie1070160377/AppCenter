package cn.mooc.app.module.api.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.selector.RegisterEventSelector;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.security.CredentialsDigest;
import cn.mooc.app.core.security.SaltPwdUtils;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.api.model.MobileListData;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.service.SensitiveWordService;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.cms.data.entity.AppVersion;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.model.MobileCategoryInfo;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.model.MobileSpecial;
import cn.mooc.app.module.cms.model.MobileUserInfo;
import cn.mooc.app.module.ad.service.AdService;
import cn.mooc.app.module.cms.service.AppVersionService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SpecialService;
import cn.mooc.app.module.cms.service.WorkflowStepRoleService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.ShowUserNameUtil;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.model.InteractCommentFindPageListParams;
import cn.mooc.app.module.interact.service.InteractCommentService;
import cn.mooc.app.module.interact.service.InteractMarkService;
import cn.mooc.app.module.interact.service.SnsOpLogService;





/**
 * @author hwt
 * @version 1.0
 * 
 */
@Controller
public class MobileUserController extends ApiModuleController {
	
	private static final Logger logger = LoggerFactory.getLogger(MobileUserController.class);

	@Autowired
	private SnsOpLogService snsOpLogService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private AppVersionService appService;
	@Autowired
	private NodeService nodeQueryService;
	@Autowired
	private AdService adService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysContext sysContext;
	@Autowired
	private CredentialsDigest credentialsDigest;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private InteractMarkService interactMarkService;
	@Autowired
	private InteractCommentService commentService;
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private WorkflowStepRoleService workflowStepRoleService;
	@Autowired
	private InfoService infoService;

	 /**
	 * 创建用户
	 *
	 * @param userName 登录名
	 * @param password 密码
	 * @param realName 实名
	 * @param mobile 手机号
	 * @param email 邮箱
	 * @param gender 性别--M 男, F 女
	 * @param birthday 生日 yyyy-MM-dd
	 * @param nickname 昵称
	 * @param bankCard 银行卡
	 * @param request
	 * @param response
	 */
	 @RequestMapping(value = { "/m-createUser.htx" })
	public void createUser(String userName, String password, String realName, String mobile, String email,
			String gender, String birthday, String nickname,String bankCard, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		boolean exist = sysDataService.existUser(userName);
		if (exist) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("已存在" + userName + "的用户!")));
			return;
		}
		if(StringUtil.isNotEmpty(nickname)) {
			exist = sysDataService.existUserByNickName(nickname);
			if (exist) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("已存在昵称：" + nickname + "的用户!")));
				return;
			}
		}
		
		SysUserEntity user = new SysUserEntity();
		user.setUserName(userName);
		user.setPassWord(password);
		user.setStatus(1);
		//红客系统中约定：0：匿名用户，1：校外用户，10：校内实名用户
		user.setuType(1);
		user.setSuperUser(false);
		
		
		SysUserExt sysUserExt = new SysUserExt();
		sysUserExt.setRealName(StringUtils.isBlank(realName) ? userName : realName);
		sysUserExt.setNickName(nickname);
		sysUserExt.setPhone(mobile);
		sysUserExt.setEmail(email);
        sysUserExt.setBankCard(bankCard);
		if (StringUtils.isNotBlank(gender)) {
			if ("男".equals(gender)) {
				//gender = "M";
				sysUserExt.setGender(1);
			} else if ("女".equals(gender)) {
				//gender = "F";
				sysUserExt.setGender(2);
			}
			
			if ("M".equals(gender.toUpperCase()) || "F".equals(gender.toUpperCase())) {
				sysUserExt.setGender(1);
			}else if("F".equals(gender.toUpperCase())){
				sysUserExt.setGender(2);
			}

		}

//		Integer[] roleIds = null, orgIds = { 1 }, groupIds = { 1 };
//		Integer orgId = 1, groupId = 1;
		
		try {
			sysDataService.saveSysUser(user);
			sysUserExt.setUserId(user.getId());
			sysUserExt.setUserName(user.getUserName());
			sysDataService.updateUserExt(sysUserExt);
			
			OprEvent oprEvent = new OprEvent(request);
			oprEvent.addParam("registerUserId", user.getId());
//			oprEvent.addParam("introducerId", null);//推荐人ID
			oprEvent.addSelector(new RegisterEventSelector());
			//发送事件
			eventDispatch.postEvent(oprEvent);
			
			logger.info("user save, username={}.", user.getUserName());
			SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(user.getId());
			MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
//			AUserInfo userInfo = AModelConvert.convertUser(user, siteUrl);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(userInfo)));
		} catch (Exception e) {
			logger.error("", e);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.getMessage())));
		}
		
		
	}

	/**
	 * 更新用户信息
	 * 
	 * @param token
	 *            令牌
	 * @param realName
	 *            实名
	 * @param mobile
	 *            手机号
	 * @param email
	 *            邮箱
	 * @param gender
	 *            性别--M 男, F 女
	 * @param birthday
	 *            生日 yyyy-MM-dd
	 * @param nickname
	 *            昵称
	 *  @param bankCard 银行卡
	 */
	@RequestMapping(value = { "/m-updateUser.htx" })
	public void updateUser(String token, String realName, String mobile, String email, String gender, String birthday, String nickname,
			String bankCard ,HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户不能修改用户信息!")));
			return;
		}
		
		SysUserExt userExt = webContext.getSysUserExt(user.getId());
		if(StringUtil.isNotEmpty(nickname)){
			nickname = sysContext.filterForbbidenContent(nickname);
			SysUserExt userExtNickname = sysDataService.gettUserByNickName(nickname);
			
			if (userExtNickname!=null && userExtNickname.getUserId()!=user.getId()) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("已存在昵称：" + nickname + "的用户!")));
				return;
			}
		}
		
		if(StringUtils.isBlank(userExt.getUserName())){
			userExt.setUserName(user.getUserName());
		}
		
		if (StringUtils.isNotEmpty(realName)) {
			userExt.setRealName(realName);
		}
		if (StringUtils.isNotEmpty(nickname)) {
			userExt.setNickName(nickname);
		}
		if (StringUtils.isNotEmpty(bankCard)) {
			userExt.setBankCard(bankCard);
		}
		if (StringUtils.isNotEmpty(mobile)) {
			if (isMobile(mobile)) {
				userExt.setPhone(mobile);
			} else {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("手机号码格式不正确!")));
				return;
			}
		}
		if (StringUtils.isNotEmpty(email)) {
			if (isEmail(email)) {
				userExt.setEmail(email);
			} else {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("邮箱格式不正确!")));
				return;
			}
		}
		
		if (StringUtils.isNotBlank(gender)) {
			if ("男".equals(gender)) {
				//gender = "M";
				userExt.setGender(1);
			} else if ("女".equals(gender)) {
				//gender = "F";
				userExt.setGender(2);
			}
			
			if ("M".equals(gender.toUpperCase())) {
				userExt.setGender(1);
			}else if("F".equals(gender.toUpperCase())){
				userExt.setGender(2);
			}

		}
		
		//密码为空，则不修改密码
		user.setPassWord(null);

        logger.debug("bankCard:"+userExt.getBankCard());
		try {
			sysDataService.saveSysUser(user);
			sysDataService.updateUserExt(userExt);			
			logger.info("user update, Nickname={}.", userExt.getNickName());
			SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(user.getId());
			MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
//			MobileUserInfo userInfo = MobileModelConvert.convertUser(user, userExt, siteUrl);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(userInfo)));
		} catch (Exception e) {
			logger.error("", e);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.getMessage())));
		}
		
	}

	/**
	 * 根据令牌获取用户信息
	 * 
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-userInfo.htx" })
	public void getUserInfo(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);

//		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
//		SysUserExt userExt = webContext.getSysUserExt(user.getId());
		SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(StringUtil.string2Int("" + userMap.get("UID")));
		MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
//		MobileUserInfo userInfo = MobileModelConvert.convertUser(user, userExt, siteUrl);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(userInfo)));
	}

	@RequestMapping(value = { "/m-personInfo.htx" })
	public void getPersonInfo(String token, Integer uid, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if(uid == null){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请输入要查看用户的ID!")));
			return;
		}
		SysUserEntity user = webContext.getSysUser(uid);
		if(user == null){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("不存在此用户!")));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(user.getId());
		MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(userInfo)));
	}
	/**
	 * 密码修改
	 * 
	 * @param token
	 *            令牌
	 * @param password
	 *            原始密码
	 * @param rawPassword
	 *            新密码
	 */
	@RequestMapping(value = { "/m-changePassword.htx" })
	public void changePassword(String token, String password, String rawPassword, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户不需要修改密码！!")));
			return;
		}
		if (StringUtils.isEmpty(password)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("原始密码不能为空！")));
			return;
		}
		if (StringUtils.isEmpty(rawPassword)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("新密码不能为空！")));
			return;
		}
		

		 byte[] saltBytes = credentialsDigest.getSaltBytes(user.getPwdSalt());
		String encPass = SaltPwdUtils.md5Password(credentialsDigest, password, saltBytes);
		
		if (encPass.equals(user.getPassWord())) {
			sysDataService.changeSysUserPwd(user.getId(), rawPassword);
			logger.info("user change password, username={}.", user.getUserName());
			webContext.sysUserLog(LogType.UserOpr, user.getId(), user.getUserName(), "APP用户成功修改密码");
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("原始密码不匹配！")));
			return;
		}
	}

	/**
	 * 上传头像
	 * 
	 * @param token
	 *            令牌
	 * @param request
	 *            表单提交请求
	 * @throws IOException
	 */
	@RequestMapping(value = "/m-uploadPhoto.htx", method = RequestMethod.POST)
	public void uploadPhoto(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("您没有此功能的权限！!")));
			return;
		}
		SysUserExt userExt = webContext.getSysUserExt(user.getId());
		UploadResult result = new UploadResult();
		try {
			MultipartFile partFile = getMultipartFile(request);
			ImageParam imageParam = new ImageParam();
			imageParam.setThumbnail(true);
			imageParam.setThumbnailHeight(150);
			imageParam.setThumbnailWidth(150);
			imageParam.setScale(false);
			result = this.webContext.uploadFile(partFile, imageParam);
			logger.info("用户ID：{} 上传文件成功：{}", user.getId(), result.getFileUrl());
		} catch (Exception e) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.toString())));
			return;
		}
		if (result.isStatus()) {
			String url = result.getFileUrl();
			if(url.lastIndexOf(".")>0){
				url = url.substring(0, url.lastIndexOf("."))+"_min"+url.substring(url.lastIndexOf("."));
			}
			userExt.setAvatarUrl(url);
			sysDataService.updateUserExt(userExt);
			SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(StringUtil.string2Int("" + userMap.get("UID")));
			MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
//			MobileUserInfo userInfo = MobileModelConvert.convertUser(user, userExt, siteUrl);
			webContext.sysUserLog(LogType.UserOpr, user.getId(), user.getUserName(), "APP用户成功修改头像");
			
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(userInfo)));
			logger.info("user upload photo, username={}.", user.getUserName());

		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("上传失败!")));
			return;
		}
	}

	private MultipartFile getMultipartFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		if (CollectionUtils.isEmpty(fileMap)) {
			throw new IllegalStateException("No upload file found!");
		}
		return fileMap.entrySet().iterator().next().getValue();
	}

	private boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	private boolean isEmail(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 手机app版本信息
	 * 
	 * @param token
	 */
	@RequestMapping(value = { "/m-app-version.htx" })
	public void getAppVersion(HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		AppVersion app = appService.getLatestVersion(site.getId());
		app.setUrl(MobileModelConvert.fullLinkPrefix(app.getUrl(), siteUrl));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", app.getId());
		dataMap.put("url", app.getUrl() == null ? "" : MobileModelConvert.fullLinkPrefix(app.getUrl(), siteUrl));
		dataMap.put("version", app.getVersion() == null ? "" : app.getVersion());
		dataMap.put("content", app.getContent() == null ? "" : app.getContent());
		dataMap.put("publishDate", app.getPublishDateStr() == null ? "" : app.getPublishDateStr());
		dataMap.put("isforce", app.getIsforce()==null?0:app.getIsforce());
		dataMap.put("patch_version", app.getPatch_version()== null ? "" : app.getPatch_version());
		dataMap.put("patch_url", app.getPatch_url()== null ? "" : MobileModelConvert.fullLinkPrefix(app.getPatch_url(), siteUrl));
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(dataMap)));
	}

	

	/**
	 * 用户关注
	 * 
	 * @param token
	 *            令牌
	 * @param typeId
	 *            关注类型 1：个人 2：栏目 3：专题
	 * @param resourceId
	 *            关注对象主键
	 */
	@RequestMapping(value = { "/m-attentionResources.htx" })
	public void attentionResources(String token, Integer typeId, Integer resourceId, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty("" + typeId)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注类型不能为空！")));
			return;
		}
		if (InteractMark.ATTENTION_TYPE_PERSONAL != typeId && InteractMark.ATTENTION_TYPE_NODE != typeId && InteractMark.ATTENTION_TYPE_Specail != typeId) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注类型只能为1:个人2:栏目 3:专题！")));
			return;
		}
		if (resourceId == null || resourceId == 0) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注对象主键不能为空！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity operator = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		Site site = mobileCommonService.getCurrentMobileSite();
		
		InteractMark interactMark = new InteractMark();
		interactMark.setFid(resourceId);
		
		if (InteractMark.ATTENTION_TYPE_PERSONAL == typeId) {
			SysUserEntity user = webContext.getSysUser(resourceId);
			if (user != null && !resourceId.equals(operator.getId())) {
				interactMark.setFtype(MarkType.AttentionUser);
				interactMark.setFtitle(user.getUserName());
			}
		} else if (InteractMark.ATTENTION_TYPE_NODE == typeId) {
			Node node = nodeQueryService.getNodeById(resourceId);
			if (node != null) {
				interactMark.setFtype(MarkType.AttentionNode);
				interactMark.setFtitle(node.getName());
			}
		} else if (InteractMark.ATTENTION_TYPE_Specail == typeId) {
			Special special = specialService.getSpecialById(resourceId);
			if (special != null) {
				interactMark.setFtype(MarkType.AttentionSpecial);
				interactMark.setFtitle(special.getTitle());
			}
		}
		if(interactMark.getFtype() == null){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注资源主键未找到！")));
			return;
		}
		if(interactMarkService.hasMark(interactMark.getFtype(), resourceId, operator.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("重复关注！")));
			return;
		}
		
		String ip = Servlets.getRemoteAddr(request);
		interactMark.setUserId(operator.getId());
		interactMark.setIp(ip);
		interactMark.setSiteId(site.getId());
		try {
			interactMarkService.saveInteractMark(interactMark);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		} catch (Exception e) {
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("资源关注失败!")));
			return;
		}
		if (InteractMark.ATTENTION_TYPE_PERSONAL == typeId) {
			SysUserEntity user = webContext.getSysUser(resourceId);
			snsOpLogService.operation(site.getId(), new Long(operator.getId()).intValue(), SnsOpLog.FTYPE_ATTENTION_USER, new Long(user.getId()).intValue(), user.getUserName(), operator.getUserName());
		} else if (InteractMark.ATTENTION_TYPE_NODE == typeId) {
			Node node = nodeQueryService.getNodeById(resourceId);
			snsOpLogService.operation(site.getId(), new Long(operator.getId()).intValue(), SnsOpLog.FTYPE_ATTENTION_NODE, node.getId(), node.getName(), operator.getUserName());
		} else if (InteractMark.ATTENTION_TYPE_Specail == typeId) {
			Special special = specialService.getSpecialById(resourceId);
			snsOpLogService.operation(site.getId(), new Long(operator.getId()).intValue(), SnsOpLog.FTYPE_ATTENTION_SPECIAL, special.getId(), special.getTitle(), operator.getUserName());
		}
	}

	/**
	 * 取消用户关注
	 * 
	 * @param token
	 *            令牌
	 * @param typeId
	 *            关注类型 1：个人 2：栏目 3：专题
	 * @param resourceId
	 *            取消关注对象主键
	 */
	@RequestMapping(value = { "/m-reAttentionResources.htx" })
	public void reAttentionResources(String token, Integer typeId, Integer resourceId, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty("" + typeId)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("取消关注类型不能为空！")));
			return;
		}
		if (InteractMark.ATTENTION_TYPE_PERSONAL != typeId && InteractMark.ATTENTION_TYPE_NODE != typeId && InteractMark.ATTENTION_TYPE_Specail != typeId) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注类型只能为1:个人2:栏目 3:专题！")));
			return;
		}
		if (resourceId == null || resourceId == 0) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("取消关注对象主键不能为空！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity operator = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		InteractMark interactMark = new InteractMark(); 
		if (InteractMark.ATTENTION_TYPE_PERSONAL == typeId) {
			interactMark.setFtype(MarkType.AttentionUser);
		} else if (InteractMark.ATTENTION_TYPE_NODE == typeId) {
			interactMark.setFtype(MarkType.AttentionNode);
		} else if (InteractMark.ATTENTION_TYPE_Specail == typeId) {
			interactMark.setFtype(MarkType.AttentionSpecial);
		}
		
		if(interactMarkService.hasMark(interactMark.getFtype(), resourceId, operator.getId())){
			try {
				interactMarkService.cancelInteractMark(resourceId, interactMark.getFtype(), operator.getId());
			} catch (Exception e) {
				e.printStackTrace();
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("取消关注对象失败！")));
				return;
			}
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章已取消点赞！")));
		}
		if (InteractMark.ATTENTION_TYPE_PERSONAL == typeId) {
			snsOpLogService.clearByUIDFtypeFid(new Long(operator.getId()).intValue(), SnsOpLog.FTYPE_ATTENTION_USER, resourceId);
		} else if (InteractMark.ATTENTION_TYPE_NODE == typeId) {
			snsOpLogService.clearByUIDFtypeFid(new Long(operator.getId()).intValue(), SnsOpLog.FTYPE_ATTENTION_NODE, resourceId);
		} else if (InteractMark.ATTENTION_TYPE_Specail == typeId) {
			snsOpLogService.clearByUIDFtypeFid(new Long(operator.getId()).intValue(), SnsOpLog.FTYPE_ATTENTION_SPECIAL, resourceId);
		}
	}

	/**
	 * 用户关注分页
	 * 
	 * @param token
	 *            令牌
	 * @param typeId
	 *            关注类型 1：个人 2：栏目 3：专题
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 */
	@RequestMapping(value = { "/m-attentionPage.htx" })
	public void getAttentionPage(String token, Integer typeId, Integer pageNumber, Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty("" + typeId)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注类型不能为空！")));
			return;
		}
		if (InteractMark.ATTENTION_TYPE_PERSONAL != typeId && InteractMark.ATTENTION_TYPE_NODE != typeId && InteractMark.ATTENTION_TYPE_Specail != typeId) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("关注类型只能为1:个人2:栏目 3:专题！")));
			return;
		}
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort sort = new Sort(Direction.DESC, "id");
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_siteId", site.getId());
		searchParams.put("EQ_userId", StringUtil.string2Int("" + userMap.get("UID")));
		Page<InteractMark> page = interactMarkService.findInteractMarkList(searchParams, pagerParam);
		
		List list = new ArrayList();
		if (page.getContent() != null) {
			if (InteractMark.ATTENTION_TYPE_PERSONAL == typeId) {
				Long[] idArr = new Long[page.getContent().size()];
				for (int i = 0; i < page.getContent().size(); i++) {
					InteractMark attention = page.getContent().get(i);
					idArr[i] = attention.getFid();
				}
				List<SysUserEntity> users = sysDataService.getUserInfoByIds(idArr);
				for (SysUserEntity user : users) {
					SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(user.getId());
					MobileUserInfo userInfo = MobileModelConvert.convertUser(sysUserInfo, siteUrl);
					list.add(userInfo);
				}
			} else if (InteractMark.ATTENTION_TYPE_NODE == typeId) {
				Integer[] idArr = new Integer[page.getContent().size()];
				for (int i = 0; i < page.getContent().size(); i++) {
					InteractMark attention = page.getContent().get(i);
					idArr[i] = new Long(attention.getFid()).intValue();
				}
				List<Node> nodes = nodeQueryService.findByIds(idArr);
				for (Node node : nodes) {
					MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
					list.add(cateInfo);
				}
			} else if (InteractMark.ATTENTION_TYPE_Specail == typeId) {
				Integer[] idArr = new Integer[page.getContent().size()];
				for (int i = 0; i < page.getContent().size(); i++) {
					InteractMark attention = page.getContent().get(i);
					idArr[i] = new Long(attention.getFid()).intValue();
				}
				List<Special> specials = specialService.findByIds(idArr);
				for (Special special : specials) {
					MobileSpecial cateInfo = MobileModelConvert.convertSpecial(special);
					list.add(cateInfo);
				}
			}
		}
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(list, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * 用户意见反馈
	 * 
	 * @param token
	 *            令牌
	 * @param title
	 *            标题
	 * @param content
	 *            正文
	 * @param tel
	 *            联系方式
	 */
	@RequestMapping(value = { "/m-feedback.htx" })
	public void feedback(String token, String title, String content, String tel, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(content)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("意见反馈正文不能为空！")));
			return;
		}
		if (StringUtils.isEmpty(tel)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("联系方式不能为空！")));
			return;
		}
		if (StringUtils.isNotEmpty(title) && title.length() > 60) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("意见反馈标题最多为60字符！")));
			return;
		}
		if (content.length() > 200) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("意见反馈正文最多为200字符！")));
			return;
		}
		title = sensitiveWordService.replace(title);
		content = sensitiveWordService.replace(content);
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		String ip = Servlets.getRemoteAddr(request);
		Site site = mobileCommonService.getCurrentMobileSite();
		
		InteractComment interactComment = new InteractComment();
		interactComment.setUserId(user.getId());
		interactComment.setUserName(user.getUserName());
		interactComment.setIp(ip);
		interactComment.setSiteId(site.getId());
//		interactComment.setFtype(InteractComment.FTYPE_FEEDBACK);
		interactComment.setFtype(CommentType.Feedback);
		interactComment.setContent(content);
		interactComment.setMobile(tel);
		interactComment.setTitle(title);
		interactComment.setState(InteractComment.SAVED);
		
		try {
			commentService.saveInteractComment(interactComment);
			logger.debug("resources feedback, message={}.", "title:" + title + ",content:" + content + ",uid:" + user.getId());
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		} catch (Exception e) {
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("用户意见反馈保存失败")));
		}
	}

	/**
	 * 用户意见反馈分页
	 * 
	 * @param token
	 *            令牌
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 */
	@RequestMapping(value = { "/m-feedbackPage.htx" })
	public void getFeedbackPage(Integer pageNumber, Integer pageSize, String token, HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Site site = mobileCommonService.getCurrentMobileSite();
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		
		InteractCommentFindPageListParams params = new InteractCommentFindPageListParams();
		params.setUserId(StringUtil.string2Long("" + userMap.get("UID")));
		params.setFtype(CommentType.Feedback.toString());
		if(commentService.getConfig_needCheck() == 1){
			params.setStatus(new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND });
		}else{
			params.setStatus(new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND });
		}
		
		params.setSiteId(new Integer[] { site.getId() });
		Page<InteractComment> page = commentService.findPage(params, pagerParam);
		
		if (page.getContent() != null) {
			for (InteractComment bean : page.getContent()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("id", bean.getCommentId());
				dataMap.put("content", bean.getContent());
				dataMap.put("creationTime", bean.getCreateTime() == null ? "" : sdf.format(bean.getCreateTime()));
				dataMap.put("creationIp", bean.getIp());
				List<InteractComment> commentReplys = commentService.findListBySourceId(bean.getCommentId());
				if(commentReplys == null || commentReplys.size() == 0){
					dataMap.put("replyContent", "");
					dataMap.put("replyTime", "");
					dataMap.put("replyIp", "");
					dataMap.put("isReply", 0);
				}else{
					InteractComment commentReply = commentReplys.get(0);
					dataMap.put("replyContent", commentReply.getContent() == null ? "" : commentReply.getContent());
					dataMap.put("replyTime", commentReply.getCreateTime() == null ? "" : sdf.format(commentReply.getCreateTime()));
					dataMap.put("replyIp", commentReply.getIp() == null ? "" : commentReply.getIp());
					dataMap.put("isReply", 1);
				}
				list.add(dataMap);
			}
		}

		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(list, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * 根据ID获取意见反馈
	 * 
	 * @param feedbackId
	 *            意见反馈主键
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-feedbackById.htx" })
	public void getFeedbackById(Integer feedbackId, String token, HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (feedbackId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("意见反馈主键不能为空！")));
			return;
		}
		InteractComment bean = commentService.getInteractCommentByCommentId(feedbackId);
		if (bean == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(feedbackId + "未找到指定意见反馈！")));
			return;
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("id", bean.getCommentId());
		dataMap.put("content", bean.getContent());
		dataMap.put("creationTime", bean.getCreateTime() == null ? "" : sdf.format(bean.getCreateTime()));
		dataMap.put("creationIp", bean.getIp());
		List<InteractComment> commentReplys = commentService.findListBySourceId(bean.getCommentId());
		if(commentReplys == null || commentReplys.size() == 0){
			dataMap.put("replyContent", "");
			dataMap.put("replyTime", "");
			dataMap.put("replyIp", "");
			dataMap.put("isReply", 0);
		}else{
			InteractComment commentReply = commentReplys.get(0);
			dataMap.put("replyContent", commentReply.getContent() == null ? "" : commentReply.getContent());
			dataMap.put("replyTime", commentReply.getCreateTime() == null ? "" : sdf.format(commentReply.getCreateTime()));
			dataMap.put("replyIp", commentReply.getIp() == null ? "" : commentReply.getIp());
			dataMap.put("isReply", 1);
		}
		
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(dataMap)));
	}

	/**
	 * 用户建言献策
	 * 
	 * @param token
	 *            令牌
	 * @param title
	 *            标题
	 * @param content
	 *            正文
	 * @param number
	 *            留言类型编码
	 */
	@RequestMapping(value = { "/m-guestbook.htx" })
	public void guestbook(String token, String title, String content, String number, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(content)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("留言正文不能为空！")));
			return;
		}
		if (StringUtils.isEmpty(number)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("留言类型不能为空！")));
			return;
		}
		if (StringUtils.isNotEmpty(title) && title.length() > 60) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("留言标题最多为60字符！")));
			return;
		}
		if (content.length() > 200) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("留言正文最多为200字符！")));
			return;
		}

		content = sensitiveWordService.replace(content);
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		String ip = Servlets.getRemoteAddr(request);
		Site site = mobileCommonService.getCurrentMobileSite();
		
		InteractComment interactComment = new InteractComment();
		interactComment.setState(InteractComment.SAVED);
		interactComment.setUserId(user.getId());
		interactComment.setUserName(user.getUserName());
		interactComment.setTitle(title);
		interactComment.setContent(content);
		interactComment.setIp(ip);
		interactComment.setSiteId(site.getId());
		interactComment.setTag(number);
//		interactComment.setFtype(InteractComment.FTYPE_GUESTBOOK);
		interactComment.setFtype(CommentType.Guestbook);

		try {
			commentService.saveInteractComment(interactComment);
			logger.debug("resources guestbook, message={}.", "title:" + title + ",content:" + content + ",uid:" + user.getId());
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		} catch (Exception e) {
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("用户建言献策保存失败")));
		}
	}

	/**
	 * 留言分页
	 * 
	 * @param token
	 *            令牌
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param number
	 *            留言类型编码
	 * @param title
	 *            标题查询
	 * @param creator
	 *            作者查询
	 */
	@RequestMapping(value = { "/m-guestbookPage.htx" })
	public void getGuestbookPage(Integer pageNumber, Integer pageSize, String token, String number, String title, String creator,
			HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(number)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("留言类型不能为空！")));
			return;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort sort = new Sort(Direction.DESC, "createTime", "id");
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		
		InteractCommentFindPageListParams params = new InteractCommentFindPageListParams();
		params.setFtype(CommentType.Guestbook.toString());
		if(commentService.getConfig_needCheck() == 1){
			params.setStatus(new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND });
		}else{
			params.setStatus(new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND });
		}
		
		params.setSiteId(new Integer[] { site.getId() });
		Page<InteractComment> page = commentService.findPage(params, pagerParam);

		if (page.getContent() != null) {
			for (InteractComment bean : page.getContent()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				SysUserExt sysUserExt = sysDataService.getSysUserExt(bean.getUserId());
				if(sysUserExt == null){
					dataMap.put("creatorPhoto","");
				}else{
					dataMap.put("creatorPhoto", sysUserExt.getAvatarUrl() == null ? "" : MobileModelConvert.fullLinkPrefix(sysUserExt.getAvatarUrl(), siteUrl));
				}
				dataMap.put("creatorId", bean.getUserId());
				dataMap.put("creator", bean.getUserName());
				List<InteractComment> replays = commentService.findListBySourceId(bean.getCommentId());
				if(replays != null && replays.size() > 0){
					InteractComment replay = replays.get(0);
					SysUserExt sysUserExtReplay = sysDataService.getSysUserExt(replay.getUserId());
					if(sysUserExtReplay == null){
						dataMap.put("replyerPhoto","");
						dataMap.put("replyerId","");
						dataMap.put("replyer","");
					}else{
						dataMap.put("creatorPhoto", sysUserExtReplay.getAvatarUrl() == null ? "" : MobileModelConvert.fullLinkPrefix(sysUserExtReplay.getAvatarUrl(), siteUrl));
						dataMap.put("replyerId",replay.getUserId());
						dataMap.put("replyer",replay.getUserName());
					}
					dataMap.put("replyContent", replay.getContent() == null ? "" : replay.getContent());
					dataMap.put("replyDate", replay.getCreateTime() == null ? "" : sdf.format(replay.getCreateTime()));
					dataMap.put("replyIp", replay.getIp() == null ? "" : replay.getIp());
					dataMap.put("reply", 1);
				}else{
					dataMap.put("replyContent", "");
					dataMap.put("replyDate", "");
					dataMap.put("replyIp", "");
					dataMap.put("reply", 0);
				}
				dataMap.put("id", bean.getCommentId());
				dataMap.put("title", bean.getTitle());
				dataMap.put("content", bean.getCommentId());
				dataMap.put("creationDate", bean.getCreateTime() == null ? "" : sdf.format(bean.getCreateTime()));
				dataMap.put("creationIp", bean.getIp());
				list.add(dataMap);
			}
		}

		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(list, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * SNS发现(分页)
	 * 
	 * @param token
	 *            令牌
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param name
	 *            模糊查询
	 */
	@RequestMapping(value = { "/m-snsOpLogPage.htx" })
	public void getSNSOpLogPage(Integer pageNumber, Integer pageSize, String token, String name, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort sort = new Sort(Direction.DESC, "id");
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Page<SnsOpLog> page = snsOpLogService.findAll(site.getId(), name, pagerParam);

		if (page.getContent() != null) {
			for (SnsOpLog opLog : page.getContent()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				SysUserEntity user = webContext.getSysUser(opLog.getUserId());
				SysUserExt userExt = webContext.getSysUserExt(opLog.getUserId());
				if(userExt == null){
					dataMap.put("icon", "");
					dataMap.put("userId", user.getId());
					dataMap.put("username", user.getUserName());
				}else{
					dataMap.put("icon", userExt.getAvatarUrl() == null ? "" : MobileModelConvert.fullLinkPrefix(userExt.getAvatarUrl(), siteUrl));
					dataMap.put("userId", user.getId());
					
					dataMap.put("username", ShowUserNameUtil.getMUserName(user.getUserName(), userExt.getNickName(), userExt.getRealName()));
				}
				dataMap.put("time", sdf.format(opLog.getCreateTime()));
				dataMap.put("title", opLog.getTitle());
				dataMap.put("type", opLog.getFtype());
				dataMap.put("fid", opLog.getFid());
				list.add(dataMap);
			}
		}

		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(list, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}
	
	@RequestMapping(value = { "/m-auditpermiss.htx" })
	public void auditpermiss(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Map params = new HashMap();
		if(workflowStepRoleService.hasInfoWorkflow(user.getId())){
			params.put("hasAuditInfo", "1");
			//待审核数量
			long count = infoService.countAuditInfoByUser(user.getId());
			params.put("total", count);
		}else{
			params.put("hasAuditInfo", "0");
		}
		
		
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(params)));
	}
}
