package cn.mooc.app.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SysConfigEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;

@Service
public class SysCacheService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SysDataService sysDataService;	
	/**
	 * 系统数据缓存过期时间，单位：秒
	 */
	private static long expire = 60 * 60 * 24;
	
	public SysUserExt getSysUserExtCache(long userId) {

		String cacheKey = "app.sys.user.ext." + userId;
		SysUserExt cacheValue = this.cacheService.getCache(cacheKey);
		logger.debug("读取缓存结果 key：" + cacheKey + " value：" + cacheValue);
		
		if(cacheValue!=null){
			return cacheValue;
		}else {
			SysUserExt sysUserExt = sysDataService.getSysUserExt(userId);
			if(sysUserExt==null){
				//为了增加缓存的命中率，没取到的就初始化一个
				sysUserExt = new SysUserExt();
				sysUserExt.setUserId(userId);
			}
			this.cacheService.setCache(cacheKey, sysUserExt, expire);
			return sysUserExt;
		}
		
	}
	
	public boolean clearSysUserExtCache(long userId){
		String cacheKey = "app.sys.user.ext." + userId;
		return cacheService.delCache(cacheKey);
				
	}
	
	
	public SysUserEntity getUserInfoCache(long userId) {
		
		String cacheKey = "app.sys.user.info." + userId;
		SysUserEntity cacheValue = this.cacheService.getCache(cacheKey);
		logger.debug("读取缓存结果 key：" + cacheKey + " value：" + cacheValue);
		
		if(cacheValue!=null){
			return cacheValue;
		}else {
			SysUserEntity userEntity = sysDataService.getUserInfoById(userId);
			if(userEntity != null){
				//用户基础数据如果没取到，不缓存
				this.cacheService.setCache(cacheKey, userEntity, expire);
			}
			
			return userEntity;
		}
		
	}
	
	public boolean clearUserInfoCache(long userId){
		String cacheKey = "app.sys.user.info." + userId;
		return cacheService.delCache(cacheKey);
				
	}
	
	public String getSysConfigCache(String key) {
		
		String cacheKey = "app.config." + key;
		String cacheValue = this.cacheService.getCache(cacheKey);
		logger.debug("读取缓存结果 key：" + cacheKey + " value：" + cacheValue);
		
		if(cacheValue!=null){
			return cacheValue;
		}else {
			SysConfigEntity sysConfigEntity = sysDataService.getSysConfig(key);
			if(sysConfigEntity != null){
				//
				this.cacheService.setCache(cacheKey, sysConfigEntity.getKeyValue(), expire);
				return sysConfigEntity.getKeyValue();
			}
			
			return null;
		}
		

	}
	
	public boolean clearSysConfigCache(String key){
		String cacheKey = "app.config." + key;
		return cacheService.delCache(cacheKey);
				
	}

	
	
}
