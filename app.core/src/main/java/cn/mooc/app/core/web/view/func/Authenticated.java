package cn.mooc.app.core.web.view.func;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Context;
import org.beetl.core.Function;

public class Authenticated implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		//
		
		Subject subject = SecurityUtils.getSubject();
		if(subject==null){
			return false;
		}
		
		return subject.isAuthenticated();
		
	}

}
