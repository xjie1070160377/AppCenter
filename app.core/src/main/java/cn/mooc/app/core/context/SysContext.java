package cn.mooc.app.core.context;

import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.service.MailService;

/**
 * 系统上下文常用方法封装
 * 
 * @author Taven
 *
 */
public interface SysContext {
	
	/**
	 * @return
	 */
	String getAdminPath();
	
	/**
	 * 缓存操作接口
	 * @return
	 */
	CacheService getCacheService();

	/**
	 * 邮件发送接口
	 * @return
	 */
	MailService getMailService();

	
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
	
	
	/**
	 * 保存/更新 配置项
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	boolean setSysConfig(String key, String val);
	/**
	 * 取系统配置参数
	 * @param key
	 * @return
	 */
	public String getSysConfig(String key);


	/**
	 * 取系统配置参数，自动转int
	 * @param key
	 * @param def
	 * @return
	 */
	public int getSysConfigInt(String key, int def);


	/**
	 * 取系统配置参数，自动转double
	 * @param key
	 * @param def
	 * @return
	 */
	public double getSysConfigDouble(String key, double def);
	
	/**
	 * 过滤系统中设定的违禁词
	 * 
	 * @param content
	 * @return
	 */
	public String filterForbbidenContent(String content);
	
	
}



