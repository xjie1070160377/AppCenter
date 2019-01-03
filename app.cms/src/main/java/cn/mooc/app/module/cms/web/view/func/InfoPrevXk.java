package cn.mooc.app.module.cms.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.service.InfoService;

public class InfoPrevXk implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		InfoService infoService = (InfoService)webApplicationContext.getBean(InfoService.class);
		
		String value = StringUtil.strnull(paras[0]);
		if(value == null){
			return null;
		}
		
		Integer infoId = StringUtil.string2Int(paras[0]);
		
		cn.mooc.app.module.cms.data.entity.Info info = null;
		if(infoId != null){
			info = infoService.findPrevForXk(infoId, false);
		}
		return info;
	}

}
