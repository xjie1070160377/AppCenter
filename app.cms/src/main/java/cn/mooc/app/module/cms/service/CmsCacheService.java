package cn.mooc.app.module.cms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.module.cms.data.entity.Site;

@Service
public class CmsCacheService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SiteService siteService;
	/**
	 * 数据缓存过期时间，单位：秒
	 */
	private static long expire = 60 * 60 * 24;
	
	public Site getSiteByDomainCache(String domain) {
		
		String cacheKey = "app.cms.site." + domain;
		Site cacheValue = this.cacheService.getCache(cacheKey);
		logger.debug("读取缓存结果 key：" + cacheKey + " value：" + cacheValue);
		
		if(cacheValue!=null){
			return cacheValue;
		}else {
			Site site = this.siteService.getSiteByDomain(domain);

			this.cacheService.setCache(cacheKey, site, expire);
			return site;
		}
	}
	
	public boolean clearSiteByDomainCache(String domain){
		String cacheKey = "app.cms.site." + domain;
		return cacheService.delCache(cacheKey);
				
	}

	public Site getDefaultSiteCache() {
		String cacheKey = "app.cms.site.default";
		Site cacheValue = this.cacheService.getCache(cacheKey);
		logger.debug("读取缓存结果 key：" + cacheKey + " value：" + cacheValue);
		
		if(cacheValue!=null){
			return cacheValue;
		}else {
			Site site = this.siteService.getDefaultSite();
			if(site!=null){
				this.cacheService.setCache(cacheKey, site, expire);
			}
			return site;
		}
	}
	
	public boolean clearDefaultSiteCache(){
		String cacheKey = "app.cms.site.default";
		return cacheService.delCache(cacheKey);
				
	}
	
	
}
