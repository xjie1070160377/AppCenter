package cn.mooc.app.core.context;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.enums.UStatus;
import cn.mooc.app.core.exception.ExistUserException;
import cn.mooc.app.core.model.ImageInfo;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;


/**
 * Web系统上下文常用方法封装
 * 
 * @author Taven
 *
 */
public interface WebContext {

	ServletContext getServletContext();
	
	WebApplicationContext getWebApplicationContext();
	
	HttpServletRequest getHttpRequest();
	
	String getProperty(String key, String defaultValue);
	
	int getProperty(String key, int defaultValue);
	
	double getProperty(String key, double defaultValue);
	
	long getProperty(String key, long defaultValue);
	
	String getProperty(String key);
	
	Map<String, String> getPropertiesMap(String prefix);
	
	List<String> getPropertiesList(String prefix);	
	
	SysContext getSysContext();
	
	public String getWebView(String module, String template);
	
	
	/**
	 * 取当前登录的用户ID，为了兼容之前的CMS库，转int
	 * 
	 * @return
	 */
	int getCurrentIntUserId();
	
	/**
	 * 取当前登录的用户ID
	 * @return
	 */
	long getCurrentUserId();
	
	/**
	 * 当前登录用户信息
	 * @return
	 */
	LoginUserInfo getCurrentUserInfo();
	
	
	/**
	 * 是否有超级用户权限
	 * @return
	 */
	public boolean hasSuperRole();
	
	/**
	 * 当前用户是否已登录
	 * 
	 * @return
	 */
	boolean userHasLogin();
	
	/**
	 * 将当前用户退出登录
	 */
	void userLogout();
	
	/**
	 * 记录用户日志，仅用于浏览器的登录，不支持接口登录方式
	 * @param logType 日志类型
	 * @param logDesc 日志内容
	 */
	void sysUserLog(LogType logType, String logDesc);
	
	/**
	 * @param logType
	 * @param userId
	 * @param userName
	 * @param logDesc
	 */
	void sysUserLog(LogType logType, long userId, String userName, String logDesc);
	
	/**
	 * @param logType
	 * @param moduleName
	 * @param moduleLogType
	 * @param logDesc
	 */
	void sysUserLog(LogType logType, String moduleName, String moduleLogType, String logDesc);
	
	public void sysUserLog(LogType logType, long userId, String userName, String moduleName, String moduleLogType, String logDesc, String ip, String reqUrl);
	
	/**
	 * @param logType
	 * @param userId
	 * @param userName
	 * @param moduleName
	 * @param moduleLogType
	 * @param logDesc
	 */
	void sysUserLog(LogType logType, long userId, String userName,  String moduleName, String moduleLogType, String logDesc);
	
	/**
	 * @param url
	 * @return
	 */
	UploadResult uploadFile(String url);		
	
	/**
	 * @param fileUrl
	 * @return
	 */
	UploadResult uploadFile(URL fileUrl);
	
	/**
	 * 文件存储接口
	 * 
	 * @param category	分类，可以是站点ID
	 * @param multipartFile
	 * @return
	 */
	UploadResult uploadFile(String category, MultipartFile multipartFile);
	
	/**
	 * 文件存储接口
	 * 
	 * @param multipartFile
	 * @return
	 */
	UploadResult uploadFile(MultipartFile multipartFile);
	
	/**
	 * 上传图片，并自动裁剪
	 * 
	 * @param multipartFile
	 * @param imageParam
	 * @return
	 */
	UploadResult uploadFile(MultipartFile multipartFile, ImageParam imageParam);
	
	/**
	 * 上传文件后生成自己指定的文件名，不用随机文件名
	 * 
	 * @param multipartFile
	 * @param imageParam
	 * @param destFileName 指定保存后的文件名，需上传端控制好文件名，保证唯一性，否则会覆盖同名文件
	 * @return
	 */
	UploadResult uploadFile(MultipartFile multipartFile, ImageParam imageParam, String destFileName);
	
	/**
	 * 裁剪图片
	 * 
	 * @param srcFileUrl
	 * @param topStart
	 * @param leftStart
	 * @param cutWidth
	 * @param cutHeight
	 * @param imageParam 如果为空，则只将srcFileUrl文件进行裁剪
	 * @return
	 */
	UploadResult cropImage(String srcFileUrl, int topStart, int leftStart, int cutWidth, int cutHeight,	ImageParam imageParam);
	
	
	public ImageInfo getImageFile(String targetFileUrl);
	
	long getFileSize(String targetFileUrl);
	
	boolean fileExist(String targetFileUrl);
	
	UploadResult delFile(String targetFileUrl);
	
	UploadResult moveFile(String srcFileUrl, String destFileUrl);
	
	
	/**
	 * 创建用户
	 * @param userName
	 * @param realName
	 * @param passWord
	 * @return
	 */
	SysUserEntity createSysUser(String userName, String realName, String passWord)  throws ExistUserException;
	
	/**
	 * 更新用户信息
	 * @param userId
	 * @param realName
	 * @param passWord
	 * @return
	 */
	SysUserEntity updateSysUser(long userId, String realName, String passWord);
	
	/**
	 * 改变用户状态
	 * @param userId
	 * @param status
	 * @return
	 */
	boolean changeSysUserStatus(long userId, UStatus status);
	
	/**
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	boolean delSysUser(long userId) throws Exception;
	
	/**
	 * 只取系统用户信息
	 * 
	 * @param userId
	 * @return
	 */
	SysUserEntity getSysUser(long userId);
	
	/**
	 * 只取用户扩展信息
	 * 
	 * @param userId
	 * @return
	 */
	SysUserExt getSysUserExt(long userId);
	
	
	/**
	 * @param userId
	 * @param avatarUrl
	 * @return
	 */
	SysUserExt updateAvatarUrl(long userId, String avatarUrl);
	
	
	/**
	 * 取用户所有资料，包括扩展信息
	 * 
	 * @param userId
	 * @return
	 */
	SysUserInfo getSysUserFullInfo(long userId);
	
	/**
	 * @param key
	 * @return
	 */
	public <T> T getCache(String key);
	
	/**
	 * 将数据对象写入缓存
	 * 
	 * @param key 键的名称
	 * @param val 待缓存的数据
	 * @return 是否成功
	 */
	public boolean setCache(String key, Object val);

	/**
	 * 将数据对象写入缓存，并指定有效时间
	 * 
	 * @param key 键的名称
	 * @param val 待缓存的数据
	 * @param expire 过期时间，单位：秒
	 * @return 是否成功
	 */
	public boolean setCache(String key, Object val, long expire);
	
	/**
	 * @param key
	 * @return
	 */
	public boolean delCache(String key) ;
	
}
