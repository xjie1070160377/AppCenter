package cn.mooc.app.core.context;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SysConfigEntity;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.service.MailService;
import cn.mooc.app.core.service.SysCacheService;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.DateTimeUtil;

@Service
public class DefaultSysContext implements SysContext {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private SysCacheService sysCacheService;
	@Autowired
	private CacheService cacheService;	
	@Value("${SYS_ADMIN_PATH}")
	private String AdminPath = "mcenter";
	
	public String getAdminPath(){
		return AdminPath;
	}
	
	@Override
	public CacheService getCacheService() {
		// TODO Auto-generated method stub
		return cacheService;
	}

	@Override
	public MailService getMailService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public <T> T getCache(String key) {
		return cacheService.getCache(key);
	}
	
	public boolean setCache(String key, Object val) {
		return cacheService.setCache(key, val);
	}

	public boolean setCache(String key, Object val, long expire){
		return cacheService.setCache(key, val, expire);
	}
	
	public boolean delCache(String key) {
		return cacheService.delCache(key);
				
	}

	public boolean setSysConfig(String key, String val) {
		try{
		SysConfigEntity entity = new SysConfigEntity();
		entity.setKeyName(key);
		entity.setKeyValue(val);
		entity.setCreateTime(DateTimeUtil.getCurrentTime());
		this.sysDataService.saveSysConfig(entity);
		}catch (Exception e) {
			logger.error("setSysConfig", e);
			return false;
		}finally{
			sysCacheService.clearSysConfigCache(key);
		}
		return true;
	}
	@Override
	public String getSysConfig(String key) {
		//
		return sysCacheService.getSysConfigCache(key);
	}

	@Override
	public int getSysConfigInt(String key, int def) {
		// 
		try{
			String val = this.getSysConfig(key);		
			return Integer.parseInt(val);
		}catch(Exception e){
			//logger.error("",e);
			return def;
		}
	}

	@Override
	public double getSysConfigDouble(String key, double def) {
		//		
		try{
			String val = this.getSysConfig(key);
			return Double.parseDouble(val);
		}catch(Exception e){
			//logger.error("",e);
			return def;
		}
	}
	
	public String getForbiddenRegex(){
		String regex = this.getSysConfig("app.forbidden.word");
		if(StringUtils.isBlank(regex)){
			regex = "操|草|我日|妈|娘|你妹|骚\\.";
		}
		return regex;
	}
	
	public String filterForbbidenContent(String content){
		if(StringUtils.isEmpty(content)){
			return content;
		}
		String regex = this.getForbiddenRegex();
		if(StringUtils.isNotBlank(regex)){
			content = content.replaceAll(regex, "*");
		}
		return content;
	}


}
