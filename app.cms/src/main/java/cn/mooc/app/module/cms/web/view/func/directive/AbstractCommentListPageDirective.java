package cn.mooc.app.module.cms.web.view.func.directive;

import java.util.Map;

import org.beetl.core.Context;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.model.InteractCommentFindPageListParams;
import cn.mooc.app.module.interact.service.InteractCommentService;

/**
 * AbstractCommentListPageDirective
 * 
 * @author csmooc
 * 
 */
public abstract class AbstractCommentListPageDirective {
	public static final String FTYPE = "ftype";
	public static final String FID = "fid";

	public static final String SITE_ID = "siteId";
	public static final String STATUS = "status";
	public static final String SOURCEID = "sourceId";

	public Object doExecute(Map<String, Object> params, Context ctx, boolean isPage) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);
		InteractCommentService interactCommentService = (InteractCommentService)webApplicationContext.getBean(InteractCommentService.class);
		

		Integer[] siteId = Beetls.getIntegers(params, SITE_ID);
		if (siteId == null && params.get(SITE_ID) == null) {
			siteId = new Integer[] { siteService.getWebCurrentSiteId() };
		}
		String ftype = Beetls.getString(params, FTYPE, "Info");
		Integer fid = Beetls.getInteger(params, FID);
		Integer sourceId = Beetls.getInteger(params, SOURCEID);

		Integer[] status = Beetls.getIntegers(params, STATUS);
		
		if (status == null) {
			if(interactCommentService.getConfig_needCheck() == 1){
				status = new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND };
			}else{
				status = new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND };
			}
//			status = new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND };
		}
		try {
			InteractCommentFindPageListParams findPageListParams = new InteractCommentFindPageListParams();
			findPageListParams.setFtype(ftype);
			findPageListParams.setFid(fid);
			findPageListParams.setSourceId(sourceId);
			findPageListParams.setStatus(status);
			findPageListParams.setSiteId(siteId);
			
			if (isPage) {
				PagerParam pageParam = Beetls.getPageable(params, ctx, new String[]{"createTime"}, Direction.DESC);
				return interactCommentService.findPage(findPageListParams, pageParam);
			} else {
				PagerParam pageParam = Beetls.getLimitable(params, ctx, new String[]{"createTime"}, Direction.DESC);
				return interactCommentService.findList(findPageListParams, pageParam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
