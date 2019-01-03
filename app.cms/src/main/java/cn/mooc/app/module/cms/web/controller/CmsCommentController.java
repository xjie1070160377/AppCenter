package cn.mooc.app.module.cms.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.MobileCommentInfo;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.event.selector.CommentEventSelector;
import cn.mooc.app.module.interact.event.selector.UnCommentEventSelector;
import cn.mooc.app.module.interact.model.InteractCommentFindPageListParams;
import cn.mooc.app.module.interact.service.InteractCommentService;

@Controller
public class CmsCommentController {
	
	public static final String FTYPE = "ftype";
	public static final String FID = "fid";
	public static final String SITE_ID = "siteId";
	public static final String STATUS = "status";
	public static final String SOURCEID = "sourceId";
	public static final String PAGE = "page";
	public static final String PAGESIZE = "pageSize";
	private static final String SIDX = "sidx";
	private static final String SORD = "sord";
	
	@Autowired
	private SiteService siteService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private InteractCommentService service;
	@Autowired
	private InfoService infoService;
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private MobileModelConvert mobileModelConvert;
	
	@RequestMapping(value="/info/comment_submit")
	public String addComment(InteractComment entity, String successUrl, String errorUrl, String warningUrl, HttpServletRequest request,
			HttpServletResponse response, Model modelMap)throws java.lang.Exception{
		modelMap.addAttribute("siteId", entity.getSiteId());
		modelMap.addAttribute("nextUrl", request.getParameter("nextUrl"));
		if(service.getConfig_allow() == 0){
			modelMap.addAttribute("messages", "系统不允许评论.");
			return errorUrl;
		}
		if (StringUtils.isEmpty(entity.getContent())) {
			modelMap.addAttribute("messages", "评论内容不允许为空.");
			return errorUrl;
		}
		if (entity.getFtype() == null) {
			modelMap.addAttribute("messages", "评论类型不允许为空.");
			return errorUrl;
		}
		if (entity.getFid() == null) {
			modelMap.addAttribute("messages", "评论主体ID不允许为空.");
			return errorUrl;
		}
		int len = entity.getContent().length();
		int maxLen = service.getConfig_length();
		if(maxLen < len){
			modelMap.addAttribute("messages", "评论不允许超过"+maxLen+".");
			return errorUrl;
		}
		if(service.getConfig_needCheck() == 1){
			entity.setState(InteractComment.SAVED);
		}else{
			entity.setState(InteractComment.AUDITED);
		}

		LoginUserInfo user = webContext.getCurrentUserInfo();
		if(user == null){
			modelMap.addAttribute("messages", "登录超时，请重新登录.");
			return errorUrl;
		}
		entity.setUserId(user.getUserId());
		entity.setUserName(user.getUserName());
		
		String ip = ValidatorUtil.getIpAddr(request);
		entity.setIp(ip);
		entity.setCreateTime(new Date());
		if(entity.getFtype().equals(CommentType.Comment)){
			Integer sourceId = entity.getSourceId();
			InteractComment refComment = service.getInteractCommentByCommentId(sourceId);
			entity.setSourceUserId(refComment.getUserId());
			entity.setSourceUserName(refComment.getUserName());
		}
		service.saveInteractComment(entity);
		infoService.addComments(entity.getFid());
		
		LoginUserInfo shiroUser = webContext.getCurrentUserInfo();
		OprEvent oprEvent = new OprEvent(request);
		oprEvent.setLoginUser(shiroUser);
		oprEvent.addSelector(new CommentEventSelector());
		
		oprEvent.addParam("infoId", entity.getFid());
		
		//发送事件
		eventDispatch.postEvent(oprEvent);
		
		return successUrl;
	}
	
	/**
	 * 获取评论分页
	*/
	@RequestMapping(value="/info/comment_page_Json")
	@ResponseBody
	public Map<String, Object> commentPageJson(String ftype, Integer fid,Integer sourceId ,
			Integer[] status,Integer page,Integer pageSize,String[] sidx, String sord) {
		Integer siteId =  siteService.getWebCurrentSiteId() ;
		Site site = siteService.getSiteById(siteId);
		if(StringUtil.isNull(ftype)){
			ftype = "Info";
		}
		if(StringUtil.isNull(fid)){
			return HttpResponseUtil.failureJson("fid can not be null");
		}
		if (page == null) {page = 1;}
		if (pageSize == null) {pageSize = 10;}
		if(StringUtil.isNull(sidx)){
			sidx = new String[]{"createTime"};
		}
		if(StringUtil.isNull(sord)){
			sord = (Direction.DESC).toString();
		}
		if (status == null) {
			if(service.getConfig_needCheck() == 1){
				status = new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND };
			}else{
				status = new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND };
			}
		}
		try {
			InteractCommentFindPageListParams findPageListParams = new InteractCommentFindPageListParams();
			findPageListParams.setFtype(ftype);
			findPageListParams.setFid(fid);
			findPageListParams.setSourceId(sourceId);
			findPageListParams.setStatus(status);
			findPageListParams.setSiteId(new Integer[]{siteId});
			PagerParam pageParam = new PagerParam(page, pageSize, sidx, sord);
			Page<InteractComment> commentPage =  service.findPage(findPageListParams, pageParam);
			List<MobileCommentInfo> commentList = new ArrayList<MobileCommentInfo>();
			for(InteractComment comment : commentPage.getContent()){
				mobileModelConvert.converComment(commentList, comment, site.getSiteUrl());
			}
			
			return HttpResponseUtil.successJson(commentList, commentPage.getTotalElements(), commentPage.getTotalPages(), pageParam);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping("/info/comment_delete")
	@ResponseBody
	public boolean commentDelete(Integer id, HttpServletRequest request){
		InteractComment entity = service.getInteractCommentByCommentId(id);
		boolean flag = service.cancelCommentByCommentId(id);
		infoService.delComments(entity.getFid());
		
		LoginUserInfo shiroUser = webContext.getCurrentUserInfo();

		OprEvent oprEvent = new OprEvent(request);
		oprEvent.setLoginUser(shiroUser);
		oprEvent.addParam("infoId", entity.getFid());
		
		oprEvent.addSelector(new UnCommentEventSelector());
		//发送事件
		eventDispatch.postEvent(oprEvent);
		return flag;
	}

}
