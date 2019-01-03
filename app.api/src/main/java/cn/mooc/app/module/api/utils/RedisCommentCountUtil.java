package cn.mooc.app.module.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.service.CacheService;
@Service
public class RedisCommentCountUtil {

	@Autowired
	private CacheService cacheService;
	
	public static final String suffix = "COMEMNT_COUNT";
	
	public int getCount(Integer siteId, Long userId) {
		String key = siteId + "_" + userId + "_" + suffix; 
		if(cacheService.exists(key)){
			return cacheService.getCache(key);
		}else{
			return 0;
		}
	}
	
	public void addCount(Integer siteId, Long userId) {
		String key = siteId + "_" + userId + "_" + suffix; 
		int count = this.getCount(siteId, userId);
		cacheService.setCache(key, count+1);
	}
	
	public void clearCount(Integer siteId, Long userId){
		String key = siteId + "_" + userId + "_" + suffix; 
		cacheService.delCache(key);
	}
}
