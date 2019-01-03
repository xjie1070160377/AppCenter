package cn.mooc.app.module.cms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.exception.WebShowException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.support.TitleText;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.interact.service.InteractCommentService;


@Controller
public class InfoWebController {
	@Autowired
	private SiteService siteService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private InteractCommentService commentService;
	@Autowired
	private WebContext webContext;
	
	
	@RequestMapping(value = "/info/{infoId:[0-9]+}")
	public String infoByPagePath(@PathVariable Integer infoId, Integer page, HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("site", site);
		//获取评论设置参数
		modelMap.addAttribute("commentAllow", commentService.getConfig_allow());
		modelMap.addAttribute("commentNeedCheck", commentService.getConfig_needCheck());
		modelMap.addAttribute("commentLength", commentService.getConfig_length());
		return doInfo(infoId, page, site, request, modelMap);
	}
	
	private String doInfo(Integer infoId, Integer page, Site site, HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Info info = infoService.getInfoById(infoId);
		if (info == null) {
			return "/error";
		}
		if (!info.isNormal()) {
			return "/error";
		}
		//判断校外用户访问校内文章
		Long userId = webContext.getCurrentUserId();
		SysUserEntity currUser = sysDataService.getUserInfoById(userId);
		if(UserTypeUtil.outUser(currUser.getuType()) && info.getP6() == 1){
			throw new WebShowException("校外用户无法访问校内文章!");
		}
		
		if(info.getSite().getId() != site.getId()){
			site = info.getSite();
			modelMap.addAttribute("site", site);
		}
//		User user = Context.getCurrentUser(request);
//		if (user != null) {
//			String ip = Servlets.getRemoteAddr(request);
//			infoVisitorService.visite(user, site, info, ip);
//		}
		if (info.isLinked()) {
			return "redirect:" + info.getLinkUrl();
		}
		page = page == null ? 1 : page;
		Node node = info.getNode();
		List<TitleText> textList = info.getTextList();
		TitleText infoText = TitleText.getTitleText(textList, page);
		String title = infoText.getTitle();
		String text = infoText.getText();
		modelMap.addAttribute("info", info);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("title", title);
		modelMap.addAttribute("text", text);

		SysUserEntity user = webContext.getSysUser(info.getCreatorId());
		String creator = user.getUserName();

		String author = info.getAuthor() != null ? info.getAuthor() : creator;
		
		modelMap.addAttribute("author", author);

//		Page<String> pagedList = new PageImpl<String>(Arrays.asList(text), new PageRequest(page - 1, 1), textList.size());
//		Map<String, Object> data = modelMap.asMap();
//		ForeContext.setData(data, request);
//		ForeContext.setPage(data, page, info, pagedList);

//		String template = Servlets.getParameter(request, "template");
//		boolean mobileTheme = Context.getCurrentMobileTheme(request);
//		if (StringUtils.isNotBlank(template)) {
//			return template;
//		} else {
//			return info.getTemplate(mobileTheme);
//		}
		String template = info.getTemplate();
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		return "info";
	}
}
