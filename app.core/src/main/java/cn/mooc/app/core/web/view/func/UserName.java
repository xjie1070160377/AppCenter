package cn.mooc.app.core.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.utils.StringUtil;

public class UserName implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		WebContext webContext = (WebContext) webApplicationContext.getBean(WebContext.class);
		
		// TODO Auto-generated method stub
		String value = StringUtil.strnull(paras[0]);
		if(value == null){
			return "";
		}
		
		int userId = StringUtil.string2Int(paras[0]);
		SysUserExt sysUserExt = webContext.getSysUserExt(userId);
		
		String userName = null;
		if(sysUserExt != null){
			userName = sysUserExt.getNickName() == null ? sysUserExt.getRealName() : sysUserExt.getNickName();
		}
		if (userName == null || "".equals(userName)) {
			SysUserEntity sysUser = webContext.getSysUser(userId);
			userName = sysUser.getUserName();
		}
		if(userName == null){
			userName = "";
		}
		return userName;
	}

}
