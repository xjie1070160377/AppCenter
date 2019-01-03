package cn.mooc.app.core.web.view.func;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Context;
import org.beetl.core.Function;

import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

public class AuthUserName implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		//
		Subject subject = SecurityUtils.getSubject();
		if(subject==null){
			return "";
		}
		
		LoginUserInfo principal = (LoginUserInfo)subject.getPrincipal();
		return principal.getUserName();
		
	}

}
