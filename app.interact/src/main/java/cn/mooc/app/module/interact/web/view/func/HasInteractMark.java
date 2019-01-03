package cn.mooc.app.module.interact.web.view.func;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.service.InteractMarkService;

public class HasInteractMark implements Function{
	public static final String FTYPE = "ftype";
	public static final String FID = "fid";
	
	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		InteractMarkService interactMarkService = (InteractMarkService)webApplicationContext.getBean(InteractMarkService.class);
		
		Subject subject = SecurityUtils.getSubject();
		if(subject==null){
			return "";
		}
		
		LoginUserInfo principal = (LoginUserInfo)subject.getPrincipal();
		
		Map<String, Object> params = (Map<String, Object>)paras[0];
		String ftype = Beetls.getString(params, FTYPE);
		Integer fid = Beetls.getInteger(params, FID);
		
		boolean result = interactMarkService.hasMark(MarkType.valueOf(ftype), fid, principal.getUserId());
		return result;
	}

}
