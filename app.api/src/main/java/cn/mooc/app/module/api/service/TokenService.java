package cn.mooc.app.module.api.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.api.utils.RedisTokenUtil;

@Service
public class TokenService {

	@Autowired
	private WebContext webContext;
	@Autowired
	private RedisTokenUtil redisTokenUtil;
	@Autowired
	private CacheService cacheService;
	
	public ResultData checkToken(String token) {
		JsonMapper mapper = new JsonMapper();
		if(StringUtils.isEmpty(token)) {
			return ResultData.createError("token不能为空！", "2");
		}
		String UID = redisTokenUtil.getUID(token);
		if (StringUtils.isNotEmpty(UID)) {
			SysUserEntity user = this.webContext.getSysUser(StringUtil.string2Int(UID));
			if (user.getStatus() == 0) {
				return ResultData.createError("登录名为：" + user.getUserName() + "用户已被管理员禁用", "0");
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", user.getUserName());
			map.put("UID", UID);
			return ResultData.createSuccess(mapper.toJson(map), "0");
		}
		return ResultData.createError("当前token未匹配到用户，请重新认证！", "1");
	}
	

}
