package cn.mooc.app.module.sys.mcenter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.service.SysOrgService;
@Controller
@RequestMapping("/core/selector")
public class SysSelectorController extends SysModuleController {

	@RequestMapping("/orgTag")
	public String nodeTag(String id, String callback, Integer selectedId, Model modelMap) {
		modelMap.addAttribute("id", id);
		modelMap.addAttribute("callback", callback);
		modelMap.addAttribute("selectedId", selectedId);
		return ModuleView("/selector/orgTag");
	}
	
	/**
	 * 树型栏目选择器数据获取
	 * 
	 * @param queryParentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orgTreeJson")
	@ResponseBody
	public List<Map<String, Object>> orgTreeJson(Integer queryParentId, Model model) {
		List<Map<String, Object>> tree = orgService.getOrgsForJstree(queryParentId);
		return tree;
	}
	
	@Autowired
	private SysOrgService orgService;
}
