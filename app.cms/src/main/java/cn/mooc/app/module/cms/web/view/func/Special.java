package cn.mooc.app.module.cms.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.service.SpecialService;

public class Special implements Function {
	
	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SpecialService query = (SpecialService) webApplicationContext.getBean(SpecialService.class);
		
		String value = StringUtil.strnull(paras[0]);
		if(value == null){
			return "";
		}
		
		Integer id = StringUtil.string2Int(paras[0]);

		cn.mooc.app.module.cms.data.entity.Special special = null;
		if(id != null){
			special = query.getSpecialById(id);
		}
		return special;
	}

}
