package cn.mooc.app.module.cms.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.service.SiteService;

public class SiteFullNameOrName implements Function{
	public static final String ID = "id";

	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SiteService query = (SiteService) webApplicationContext.getBean(SiteService.class);
		
		String value = StringUtil.strnull(paras[0]);
		if(value == null){
			return "";
		}
		
		Integer id = StringUtil.string2Int(paras[0]);

		cn.mooc.app.module.cms.data.entity.Site site = null;
		if(id != null){
			site = query.getSiteById(id);
			if(site != null){
				return site.getFullNameOrName();
			}
		}
		return "";
	}

}
