package cn.mooc.app.module.api.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.security.CredentialsDigest;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.utils.Digests;


@Service
public class RedisTokenUtil {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int SALT_SIZE = 8;
	
	@Autowired
	private CacheService cacheService;

	public void saveTokenUID(final String token, final String UID) {
		long expire = 48 * 3600;
		cacheService.setCache("user.token." + token, UID, expire);
		cacheService.setCache("user.uid." + UID, "user.token." + token, expire);

	}

	public String getUID(String token) {
		return cacheService.getCache("user.token." + token);

	}
	
	public long getUIDToLong(String token) {
		String uid = this.getUID(token);
		return Long.parseLong(uid);
	}
	
	public long getUIDToLong(HttpServletRequest request) {
		String token = request.getParameter("token");
		logger.debug("接收到token：{}", token);
		if(StringUtils.isBlank(token)){
			return 0;
		}
		return this.getUIDToLong(token);
	}

	public String clearOldToken(final String UID, final String oldToken) {
		this.cacheService.delCache("user.uid." + UID);
		this.cacheService.delCache("user.token." + oldToken);
		return "";

	}
	
	public String getToken(String UID, CredentialsDigest credentialsDigest) {
		byte[] saltBytes = Digests.generateSalt(SALT_SIZE);
		String raw = new Date().getTime() + "_" + UID + "_" + (int) (Math.random() * 1000000);
		return credentialsDigest.digest(raw, saltBytes);
	}
}
