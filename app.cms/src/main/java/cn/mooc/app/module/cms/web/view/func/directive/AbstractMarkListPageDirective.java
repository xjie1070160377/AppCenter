package cn.mooc.app.module.cms.web.view.func.directive;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.beetl.core.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.service.InteractMarkService;

public abstract class AbstractMarkListPageDirective {
	public static final String MARKID = "markId";
	public static final String FID = "fid";

	public static final String FTYPE = "ftype";
	public static final String SITE_ID = "siteId";

	public Object doExecute(Map<String, Object> params, Context ctx, boolean isPage) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);
		InteractMarkService interactMarkService = (InteractMarkService)webApplicationContext.getBean(InteractMarkService.class);
		

		Integer[] siteId = Beetls.getIntegers(params, SITE_ID);
		if (siteId == null && params.get(SITE_ID) == null) {
			siteId = new Integer[] { siteService.getWebCurrentSiteId() };
		}
		String ftype = Beetls.getString(params, FTYPE, "InfoDigg");
		Integer fid = Beetls.getInteger(params, FID);

		Integer[] markId = Beetls.getIntegers(params, MARKID);
		
		Subject subject = SecurityUtils.getSubject();
		if(subject==null){
			return "";
		}
		
		LoginUserInfo principal = (LoginUserInfo)subject.getPrincipal();
		
		long userId = principal.getUserId();
		
		
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("IN_siteId", siteId);
			searchParams.put("EQ_markId", markId);
			searchParams.put("EQ_fid", fid);
			searchParams.put("EQ_ftype", ftype);
			searchParams.put("EQ_userId", userId);
			
			if (isPage) {
				PagerParam pageParam = Beetls.getPageable(params, ctx, new String[]{"id"}, Direction.DESC);
				return interactMarkService.findInteractMarkList(searchParams, pageParam);
			} else {
				PagerParam pageParam = Beetls.getLimitable(params, ctx, new String[]{"id"}, Direction.DESC);
				Page<InteractMark> page = interactMarkService.findInteractMarkList(searchParams, pageParam);
				return page.getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
