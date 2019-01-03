package cn.mooc.app.core.service;

public interface CacheService {

	/**
	 * 从缓存中取数据对象
	 * 
	 * @param <T> T
	 * @param key 键的名称
	 * @return T
	 */
	<T> T getCache(String key);
	
	/**
	 * 将数据对象写入缓存
	 * 
	 * @param key 键的名称
	 * @param val 待缓存的数据
	 * @return 是否成功
	 */
	boolean setCache(String key, Object val);
	
	/**
	 * 将数据对象写入缓存，并指定有效时间
	 * 
	 * @param key 键的名称
	 * @param val 待缓存的数据
	 * @param expire 过期时间，单位：秒
	 * @return 是否成功
	 */
	boolean setCache(String key, Object val, long expire);
	
	public Long sendTopic(final String topic, final String json);
	
	public Long sendTopic(final String topic, final Object data);
	
	/**
	 * 删除指定Key的数据对象
	 * 
	 * @param key 键的名称
	 * @return 是否成功
	 */
	boolean delCache(String key);
	
	/**
	 * 支持通配符方式
	 * 例如：delCacheByPattern("sys.user.*")
	 * 
	 * @param key
	 * @return
	 */
	boolean delCacheByPattern(final String key);
	
	/**
	 * 使所有缓存失效
	 */
	void cleanCache();
	/**
	 * 检查key是否已经存在
	 * @param key
	 * @return
	 */
	boolean exists(String key);
	
}
