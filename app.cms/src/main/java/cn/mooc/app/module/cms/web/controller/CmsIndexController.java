package cn.mooc.app.module.cms.web.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cn.mooc.app.core.utils.HttpUtil;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.sys.service.IndexAction;
import cn.mooc.app.module.sys.service.IndexPageService;
import cn.mooc.app.module.sys.service.LoginAction;

@Controller
public class CmsIndexController implements IndexAction, LoginAction {
	@Autowired
	private SiteService siteService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private IndexPageService indexPageService;
	@Value("${number.redmooc}")
	private String redmoocNumber;
	
	@PostConstruct
	public void initController(){
		this.indexPageService.setIndexAction(this);
		this.indexPageService.setLoginAction(this);
	}
	
	public String login(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String errorClassName = (String) request.getAttribute("shiroLoginFailure");		
		model.addAttribute("errorClassName", errorClassName);
		
		Site site = siteService.getWebCurrentSite();
		model.addAttribute("site", site);
		
		String template = site.getTemplate("/login.html");
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		
		return "/sys_member_login";
	}


	@Override
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		Site site = siteService.getWebCurrentSite();
		model.addAttribute("site", site);
		
		Integer page = HttpUtil.getReqParamInt(request, "page", 1);
		//Node node = nodeService.findByNumber(site.getId(), redmoocNumber);
		Node node = nodeService.findRoot(site.getId());
		model.addAttribute("node", node);
		model.addAttribute("page", page);
		model.addAttribute("xkdm", "");
		model.addAttribute("xkId", 0);
		String template = node == null ? "" : node.getTemplate();
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		return "/index";
	}
}
