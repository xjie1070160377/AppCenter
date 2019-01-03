package cn.mooc.app.core.web.view.func;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Context;
import org.beetl.core.Function;

public class AuthHasPermission implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		//
		if(paras.length == 0){
			return false;
		}
		String perm = (String) paras[0];
		
		Subject subject = SecurityUtils.getSubject();
		if(subject==null){
			return false;
		}
		
		return subject.isPermitted(perm);

	}

}
