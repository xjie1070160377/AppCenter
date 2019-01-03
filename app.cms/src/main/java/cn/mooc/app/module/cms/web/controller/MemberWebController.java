package cn.mooc.app.module.cms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.CmsContext;

@Controller
public class MemberWebController {
	public static final String SPACE_TEMPLATE = "sys_member_space.html";
	public static final String MY_TEMPLATE = "sys_member_my.html";
	public static final String PROFILE_TEMPLATE = "sys_member_profile.html";
	public static final String PASSWORD_TEMPLATE = "sys_member_password.html";
	public static final String EMAIL_TEMPLATE = "sys_member_email.html";
	
	@Autowired
	private WebContext webContext;
	
	@RequestMapping(value = "/my")
	public String my(HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Site site = CmsContext.getWebCurrentSite(request);
		modelMap.addAttribute("site", site);
		long userId = webContext.getCurrentUserId();
		SysUserEntity user = webContext.getSysUser(userId);
		modelMap.addAttribute("user", user);
		SysUserExt userExt = webContext.getSysUserExt(userId);
		modelMap.addAttribute("userExt", userExt);
		return site.getTemplate(MY_TEMPLATE);
	}
}
