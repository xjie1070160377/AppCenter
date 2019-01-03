package cn.mooc.app.module.interact.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.service.InteractCommentService;


@Controller
public class CommentWebController {
	@Autowired
	private WebContext webContext;
	@Autowired
	private InteractCommentService service;

	@RequestMapping(value="/comment_submit")
	public String commentSubmit(InteractComment entity, String successUrl, String errorUrl, String warningUrl, HttpServletRequest request,
			HttpServletResponse response, Model modelMap) throws java.lang.Exception{
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
//		if(entity.getFtype().equals(InteractComment.FTYPE_COMMENT)){
		if(entity.getFtype().equals(CommentType.Comment)){
			Integer sourceId = entity.getSourceId();
			InteractComment refComment = service.getInteractCommentByCommentId(sourceId);
			entity.setSourceUserId(refComment.getUserId());
			entity.setSourceUserName(refComment.getUserName());
		}
		service.saveInteractComment(entity);
		return successUrl;
	}
	@RequestMapping("/comment_delete")
	@ResponseBody
	public boolean commentDelete(Integer id, HttpServletRequest request){
		return service.cancelCommentByCommentId(id);
	}
}
