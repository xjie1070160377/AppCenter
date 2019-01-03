package cn.mooc.app.module.cms.web.controller;

import java.util.List;
import java.util.Map;

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
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.util.UserTypeUtil;


@Controller
public class NodeWebController {
	@Autowired
	private SiteService siteService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysDataService sysDataService;
	

	@RequestMapping(value = "/node/{nodeId:[0-9]+}")
	public String node(@PathVariable Integer nodeId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return nodeByPagePath(nodeId, page, request, response, modelMap);
	}
	
	@RequestMapping(value = "/node/{nodeId:[0-9]+}}")
	public String nodeByPagePath(@PathVariable Integer nodeId,
			Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("site", site);
		
		return doNode(nodeId, page, site, request, modelMap);
	}
	
	private String doNode(Integer nodeId, Integer page, Site site,
			HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Node node = nodeService.getNodeById(nodeId);
		if (node == null) {
			return "/error";
		}
		if(node.getSite().getId() != site.getId()){
			site = node.getSite();
			modelMap.addAttribute("site", site);
		}
		
		String linkUrl = node.getLinkUrl();
		if (StringUtils.isNotBlank(linkUrl)) {
			return "redirect:" + linkUrl;
		}
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("text", node.getText());

		modelMap.addAttribute("page", page);
		String template = node.getTemplate();
		if (StringUtils.isNotBlank(template)) {
			return template;
		}
		return "/list";
	}
}
