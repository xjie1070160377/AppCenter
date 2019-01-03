package cn.mooc.app.module.cms.web.view.func.directive;

import java.util.Date;
import java.util.Map;

import org.beetl.core.Context;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.SpecialService;

/**
 * AbstractSpecialListPageDirective
 * 
 * @author csmooc
 * 
 */
public abstract class AbstractSpecialListPageDirective {
	public static final String SITE_ID = "siteId";
	public static final String CATEGORY_ID = "categoryId";
	public static final String BEGIN_DATE = "beginDate";
	public static final String END_DATE = "endDate";
	public static final String IS_WITH_IMAGE = "isWithImage";
	public static final String IS_RECOMMEND = "isRecommend";

	
	public Object doExecute(Map<String, Object> params, Context ctx, boolean isPage) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);
		SpecialService specialService = (SpecialService)webApplicationContext.getBean(SpecialService.class);

		Integer[] siteId = Beetls.getIntegers(params, SITE_ID);
		if (siteId == null && params.get(SITE_ID) == null) {
			siteId = new Integer[] { siteService.getWebCurrentSiteId() };
		}
		Integer[] categoryId = Beetls.getIntegers(params, CATEGORY_ID);
		Date beginDate = Beetls.getDate(params, BEGIN_DATE);
		Date endDate = Beetls.getDate(params, END_DATE);
		Boolean isWithImage = Beetls.getBoolean(params, IS_WITH_IMAGE);
		Boolean isRecommend = Beetls.getBoolean(params, IS_RECOMMEND);

		try {
			if (isPage) {
				PagerParam pageParam = Beetls.getPageable(params, ctx, new String[]{"creationDate", "id"}, Direction.DESC);
				return specialService.findPage(siteId, categoryId, beginDate, endDate, isWithImage, isRecommend, pageParam);
			} else {
				PagerParam pageParam = Beetls.getLimitable(params, ctx, new String[]{"creationDate", "id"}, Direction.DESC);
				return specialService.findList(siteId, categoryId, beginDate, endDate, isWithImage, isRecommend, pageParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
