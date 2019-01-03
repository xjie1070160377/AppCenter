package cn.mooc.app.core.context;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.data.entity.SysLogEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.enums.UStatus;
import cn.mooc.app.core.exception.ExistUserException;
import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.service.SysCacheService;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.service.UploadFileService;
import cn.mooc.app.core.service.upload.InputStreamMultipartFile;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

@Service
public class DefaultWebContext implements WebContext {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysContext sysContext;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private SysCacheService sysCacheService;
	@Autowired
	private AbstractBeanFactory beanFactory;
	@Autowired
	private PropertySourcesPlaceholderConfigurer propertySourcesPlaceholder;

	/**
	 * 从 PropertySourcesPlaceholderConfigurer 中取Property配置文件的值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		String val = this.getProperty(key);
		return (val == null) ? defaultValue : val;
	}

	public int getProperty(String key, int defaultValue) {
		String val = this.getProperty(key);
		try {
			return Integer.parseInt(val);
		} catch (Exception e) {
			return defaultValue;
		}

	}

	public double getProperty(String key, double defaultValue) {
		String val = this.getProperty(key);
		try {
			return Double.parseDouble(val);
		} catch (Exception e) {
			return defaultValue;
		}

	}

	public long getProperty(String key, long defaultValue) {
		String val = this.getProperty(key);
		try {
			return Long.parseLong(val);
		} catch (Exception e) {
			return defaultValue;
		}

	}

	public String getProperty(String key) {
		// String jdbcUrl = beanFactory.resolveEmbeddedValue("${jdbc.url}");
		String val = beanFactory.resolveEmbeddedValue("${" + key + "}");
		return val;
	}

	public Map<String, String> getPropertiesMap(String prefix) {
		if (prefix == null) {
			return Collections.emptyMap();
		}
		Map<String, String> map = new HashMap<String, String>();
		List<Properties> propertiesList = this.getPropertiesList();
		for (Properties prop : propertiesList) {
			Enumeration<?> propertyNames = prop.propertyNames();
			String key = "";
			int len = prefix.length();
			while (propertyNames.hasMoreElements()) {
				key = (String) propertyNames.nextElement();
				if (key.startsWith(prefix)) {
					map.put(key.substring(len), prop.getProperty(key));
				}
			}
		}

		return map;
	}

	public List<String> getPropertiesList(String prefix) {
		if (prefix == null) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<String>();

		List<Properties> propertiesList = this.getPropertiesList();
		for (Properties prop : propertiesList) {
			Enumeration<?> en = prop.propertyNames();
			String key;
			while (en.hasMoreElements()) {
				key = (String) en.nextElement();
				if (key.startsWith(prefix)) {
					list.add(prop.getProperty(key));
				}
			}
		}

		return list;
	}

	public List<Properties> getPropertiesList() {
		List<Properties> list = new ArrayList<Properties>();
		PropertySources propertySources = propertySourcesPlaceholder.getAppliedPropertySources();
		if (propertySources != null) {
			for (PropertySource<?> propertySource : propertySources) {
				Object source = propertySource.getSource();
				if (source instanceof Properties) {
					Properties properties = (Properties) propertySource.getSource();
					list.add(properties);
				}

			}
		}

		return list;
	}

	@Override
	public SysContext getSysContext() {
		//
		return sysContext;
	}

	public String getWebView(String module, String template) {
		// 前端模板
		if (!template.startsWith("/")) {
			template = "/".concat(template);
		}
		return "/theme/" + module + "" + template + ".html";
	}

	@Override
	public int getCurrentIntUserId() {
		return (int) this.getCurrentUserId();
	}

	@Override
	public long getCurrentUserId() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() && subject.getPrincipal() != null) {
			LoginUserInfo loginUserInfo = (LoginUserInfo) subject.getPrincipal();
			return loginUserInfo.getUserId();
		}
		return 0;
	}

	public LoginUserInfo getCurrentUserInfo() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() && subject.getPrincipal() != null) {
			LoginUserInfo loginUserInfo = (LoginUserInfo) subject.getPrincipal();
			return loginUserInfo;
		}
		return null;
	}

	public boolean hasSuperRole() {
		Subject subject = SecurityUtils.getSubject();
		return subject.hasRole("superUser");
	}

	@Override
	public boolean userHasLogin() {
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated();
	}

	@Override
	public void userLogout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	@Override
	public UploadResult uploadFile(String url) {
		try {
			URL fileUrl = new URL(url);
			return this.uploadFile(fileUrl);
		} catch (MalformedURLException e) {
			logger.error("uploadFile", e);
			UploadResult uploadResult = new UploadResult();
			uploadResult.setStatus(false);
			uploadResult.setMsg("url地址不合法 " + e.getMessage());
			return uploadResult;
		}
	}

	@Override
	public UploadResult uploadFile(URL fileUrl) {
		String fileName = fileUrl.getFile();
		try {
			InputStream inputStream = fileUrl.openStream();
			// MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
			MultipartFile multipartFile = new InputStreamMultipartFile("file", fileName, "", 0, inputStream);
			return this.uploadFile(multipartFile);
		} catch (IOException e) {
			logger.error("uploadFile", e);
			UploadResult uploadResult = new UploadResult();
			uploadResult.setStatus(false);
			uploadResult.setMsg("文件存储失败");
			return uploadResult;
		}

	}

	@Override
	public UploadResult uploadFile(String category, MultipartFile multipartFile) {
		//
		return uploadFileService.uploadFile(category, multipartFile);
	}

	@Override
	public UploadResult uploadFile(MultipartFile multipartFile) {
		//
		return uploadFileService.uploadFile(multipartFile);
	}

	@Override
	public UploadResult uploadFile(MultipartFile multipartFile, ImageParam imageParam) {
		return uploadFileService.uploadFile(multipartFile, imageParam);
	}
	
	@Override
	public UploadResult cropImage(String srcFileUrl, int topStart, int leftStart, int cutWidth, int cutHeight,	ImageParam imageParam){
		return uploadFileService.cropImage(srcFileUrl, topStart, leftStart, cutWidth, cutHeight, imageParam);
	}

	public ImageInfo getImageFile(String targetFileUrl){
		return uploadFileService.getImageFile(targetFileUrl);
	}
	
	@Override
	public long getFileSize(String targetFileUrl) {
		return uploadFileService.getFileSize(targetFileUrl);
	}

	@Override
	public boolean fileExist(String targetFileUrl) {
		return getFileSize(targetFileUrl) > 0;
	}

	@Override
	public UploadResult delFile(String targetFileUrl) {
		return uploadFileService.delFile(targetFileUrl);
	}

	@Override
	public UploadResult moveFile(String srcFileUrl, String destFileUrl) {
		return uploadFileService.moveFile(srcFileUrl, destFileUrl);
	}

	@Override
	public void sysUserLog(LogType logType, long userId, String userName, String logDesc) {
		//
		this.sysUserLog(logType, userId, userName, "sys", "NORMAL", logDesc);

	}

	@Override
	public void sysUserLog(LogType logType, String logDesc) {
		//
		long userId = 0;
		String userName = "";
		LoginUserInfo loginUser = this.getCurrentUserInfo();
		if (loginUser != null) {
			userId = loginUser.getUserId();
			userName = loginUser.getUserName();

		}

		this.sysUserLog(logType, userId, userName, "sys", "NORMAL", logDesc);

	}

	public void sysUserLog(LogType logType, String moduleName, String moduleLogType, String logDesc) {
		//
		long userId = 0;
		String userName = "";
		LoginUserInfo loginUser = this.getCurrentUserInfo();
		if (loginUser != null) {
			userId = loginUser.getUserId();
			userName = loginUser.getUserName();

		}

		this.sysUserLog(logType, userId, userName, moduleName, moduleLogType, logDesc);

	}

	@Override
	public void sysUserLog(LogType logType, long userId, String userName, String moduleName, String moduleLogType, String logDesc) {
		//
		logger.info("日志类型：{} 日志描述：{}", logType, logDesc);

		//
		String ip = this.getCurrentReqIp();
		String reqUrl = this.getCurrentReqUrl();

		// 存入MongoDb
		SysLogEntity sysLogEntity = new SysLogEntity();
		sysLogEntity.setUserId(userId);
		sysLogEntity.setUserName(userName);
		sysLogEntity.setLogType(logType);
		sysLogEntity.setOprPoint(reqUrl);
		sysLogEntity.setUserIp(ip);
		sysLogEntity.setLogDesc(logDesc);
		sysLogEntity.setCreateTime(DateTimeUtil.getCurrentTime());

		sysLogEntity.setModuleName(moduleName);
		sysLogEntity.setModuleLogType(moduleLogType);

		this.sysDataService.sysLog(sysLogEntity);

	}
	
	public void sysUserLog(LogType logType, long userId, String userName, String moduleName, String moduleLogType, String logDesc, String ip, String reqUrl) {
		//
		logger.info("日志类型：{} 日志描述：{}", logType, logDesc);

		//
		// 存入MongoDb
		SysLogEntity sysLogEntity = new SysLogEntity();
		sysLogEntity.setUserId(userId);
		sysLogEntity.setUserName(userName);
		sysLogEntity.setLogType(logType);
		sysLogEntity.setOprPoint(reqUrl);
		sysLogEntity.setUserIp(ip);
		sysLogEntity.setLogDesc(logDesc);
		sysLogEntity.setCreateTime(DateTimeUtil.getCurrentTime());

		sysLogEntity.setModuleName(moduleName);
		sysLogEntity.setModuleLogType(moduleLogType);

		this.sysDataService.sysLog(sysLogEntity);

	}

	public HttpServletRequest getHttpRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public HttpServletResponse getHttpResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}

	public ServletContext getServletContext() {
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		return servletContext;
	}

	public WebApplicationContext getWebApplicationContext() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		return webApplicationContext;
	}



	private String getCurrentReqIp() {
		HttpServletRequest request = this.getHttpRequest();
		if (request != null) {
			return ValidatorUtil.getIpAddr(request);
		} else {
			return "";
		}
	}

	private String getCurrentReqUrl() {
		HttpServletRequest request = this.getHttpRequest();
		if (request != null) {
			return request.getRequestURI();
		} else {
			return "";
		}
	}

	@Override
	public SysUserExt getSysUserExt(long userId) {
		//
		return sysCacheService.getSysUserExtCache(userId);
	}

	@Override
	public SysUserEntity getSysUser(long userId) {
		//
		return sysCacheService.getUserInfoCache(userId);
	}

	public SysUserInfo getSysUserFullInfo(long userId) {
		//
		SysUserEntity user = this.getSysUser(userId);
		SysUserExt userExt = this.getSysUserExt(userId);
		if (user == null) {
			return null;
		}

		SysUserInfo sysUserInfo = new SysUserInfo();

		sysUserInfo.setUserId(userId);
		sysUserInfo.setUserName(user.getUserName());
		sysUserInfo.setuType(user.getuType());
		
		if (userExt != null) {
			sysUserInfo.setNickName(userExt.getNickName());
			sysUserInfo.setRealName(userExt.getRealName());
			sysUserInfo.setAvatarUrl(userExt.getAvatarUrl());
			sysUserInfo.setPhone(userExt.getPhone());
			sysUserInfo.setEmail(userExt.getEmail());
			sysUserInfo.setGender(userExt.getGender());
			sysUserInfo.setBankCard(userExt.getBankCard());
		}		

		return sysUserInfo;
	}

	@Override
	public SysUserEntity createSysUser(String userName, String realName, String passWord) throws ExistUserException {
		//
		SysUserEntity entity = new SysUserEntity();
		entity.setUserName(userName);
		entity.setPassWord(passWord);
		entity.setStatus(UStatus.Enable.getValue());
		entity = sysDataService.saveSysUser(entity);

		SysUserExt sysUserExt = new SysUserExt();
		sysUserExt.setUserId(entity.getId());
		sysUserExt.setRealName(realName);
		sysUserExt.setUserName(userName);
		sysDataService.updateUserExt(sysUserExt);

		return entity;
	}

	@Override
	public SysUserEntity updateSysUser(long userId, String realName, String passWord) {
		//
		try {
			if (StringUtils.isNotBlank(passWord)) {
				sysDataService.changeSysUserPwd(userId, passWord);
			}

			SysUserExt sysUserExt = new SysUserExt();
			sysUserExt.setUserId(userId);
			sysUserExt.setRealName(realName);
			sysDataService.updateUserExt(sysUserExt);
		} catch (Exception e) {
			logger.error("updateSysUser", e);
			return null;
		}

		return sysDataService.getUserInfoById(userId);
	}

	@Override
	public boolean changeSysUserStatus(long userId, UStatus status) {
		//
		return sysDataService.changeSysUserStatus(userId, status);
	}

	public boolean delSysUser(long userId) throws Exception {
		//
		return sysDataService.delSysUser(userId);
	}

	@Override
	public SysUserExt updateAvatarUrl(long userId, String avatarUrl) {
		SysUserExt userExt = new SysUserExt();
		userExt.setUserId(userId);
		userExt.setAvatarUrl(avatarUrl);
		sysDataService.updateUserExt(userExt);
		return userExt;
	}

	public <T> T getCache(String key) {
		return sysContext.getCache(key);
	}

	public boolean setCache(String key, Object val) {
		return sysContext.setCache(key, val);
	}

	public boolean setCache(String key, Object val, long expire) {
		return sysContext.setCache(key, val, expire);
	}

	public boolean delCache(String key) {
		return sysContext.delCache(key);

	}

	@Override
	public UploadResult uploadFile(MultipartFile multipartFile, ImageParam imageParam, String destFileName) {
		return uploadFileService.uploadFile(multipartFile, imageParam, destFileName);

	}

}
