package cn.mooc.app.module.cms.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.service.InfoService;

public class Info implements Function{

	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		InfoService query = (InfoService) webApplicationContext.getBean(InfoService.class);
		
		String value = StringUtil.strnull(paras[0]);
		if(value == null){
			return "";
		}
		
		Integer id = StringUtil.string2Int(paras[0]);

		cn.mooc.app.module.cms.data.entity.Info info = null;
		if(id != null){
			info = query.getInfoById(id);
		}
		return info;
	}

}
