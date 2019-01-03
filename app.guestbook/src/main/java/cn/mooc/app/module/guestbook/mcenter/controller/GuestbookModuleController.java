package cn.mooc.app.module.guestbook.mcenter.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.cmp.ProtectedPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.controller.ModuleController;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.module.api.utils.RedisTokenUtil;

@Controller
public class GuestbookModuleController extends ModuleController {

	@Autowired
	private RedisTokenUtil redisTokenUtil;
	@Autowired
	private WebContext webContext;
	
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "guestbook";
	}
	
	public long getUserId(HttpServletRequest req) {
		String token = (String) req.getSession().getAttribute("token");
		long userId = 0;
		if(StringUtils.isNotEmpty(token)){
			userId = redisTokenUtil.getUIDToLong(token);
		}
		if(userId == 0){
			userId = webContext.getCurrentUserId();
		}
		return userId;
	}
	
	public boolean isSuper(long userId){
		SysUserEntity user =  webContext.getSysUser(userId);
		if(user == null){
			return false;
		}
		return user.isSuperUser();
	}

}
