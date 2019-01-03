package cn.mooc.app.module.cms.web.view.func;

import java.util.Map;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.SpecialCategoryService;

public class SpecialCategoryList implements Function {
	public static final String SITE_ID = "siteId";
	
	@Override
	public Object call(Object[] paras, Context ctx) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SpecialCategoryService specialCategoryService = (SpecialCategoryService) webApplicationContext.getBean(SpecialCategoryService.class);
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);
		
		Map<String, Object> params = (Map<String, Object>)paras[0];
		Integer[] siteId = Beetls.getIntegers(params, SITE_ID);
		if (siteId == null && params.get(SITE_ID) == null) {
			siteId = new Integer[] { siteService.getWebCurrentSiteId()};
		}
		PagerParam pageParam = Beetls.getLimitable(params, ctx, new String[]{"seq", "id"}, Direction.ASC);
		return specialCategoryService.findList(siteId, pageParam);
	}

}
