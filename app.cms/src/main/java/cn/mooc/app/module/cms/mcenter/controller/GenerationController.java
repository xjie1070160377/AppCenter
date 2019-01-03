package cn.mooc.app.module.cms.mcenter.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.fulltext.InfoFulltextGenerator;
import cn.mooc.app.module.cms.service.NodeService;

@Controller
@RequestMapping("/cms/generation")
public class GenerationController extends CmsModuleController {
	@Autowired
	private NodeService nodeService;
	@Autowired
	private InfoFulltextGenerator infoFulltextGenerator;

	@RequestMapping("fulltext_index")
	public String fulltextIndex(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		return ModuleView("/generation/fulltext_index");
	}

	@RequestMapping("fulltext_submit")
	@ResponseBody
	public Map<String, Object> fulltextSubmit(Model model, Integer nodeId) {
		Integer siteId = getCurrentSiteId();
		Long userId = this.webContext.getCurrentUserId();
		Node node = null;
		if (nodeId != null) {
			node = nodeService.getNodeById(nodeId);
		}
		infoFulltextGenerator.addDocument(siteId, node, userId);
		return HttpResponseUtil.successJson();
	}
}
