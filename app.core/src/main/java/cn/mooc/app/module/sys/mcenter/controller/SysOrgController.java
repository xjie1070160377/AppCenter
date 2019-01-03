package cn.mooc.app.module.sys.mcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.data.entity.SysOrg;
import cn.mooc.app.core.service.SysOrgService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
@Controller
@RequestMapping("/core/org")
public class SysOrgController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysOrgService service;
	
	@RequestMapping("/list")
	public String sysRoleList(Model model, PagerParam pageParam, Integer queryParentId, boolean showDescendants) {
		model.addAttribute("queryParentId", queryParentId);
		model.addAttribute("showDescendants", showDescendants);
		return ModuleView("/org/list");
	}
	
	@RequestMapping(value = "/orgTreeJson")
	@ResponseBody
	public List<Map<String, Object>> nodeTreeJson(Integer queryParentId, Model model) {
		List<Map<String, Object>> tree = service.getOrgsForJstree(queryParentId);
		return tree;
	}
	
	@RequestMapping("/orgListJson")
	@ResponseBody
	public Map<String, Object> sysOrgListJson(Model model, Integer queryParentId,
			@RequestParam(defaultValue = "true") boolean showDescendants, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(keyWord)){
			searchParams.put(columnFiled, keyWord);
		}

		List<Map<String, Object>> treeData = service.getOrgsForGridTree(searchParams, queryParentId, showDescendants);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rows", treeData);
		return resMap;
	}
	
	@RequestMapping("/edit")
	public String sysRoleAdd(Model model, Integer id, Integer parentId, Integer queryParentId, Boolean showDescendants) {
		SysOrg entity =null, parent = null;
		if(StringUtil.isNotEmpty(id)){
			entity = service.get(id);
			parent = entity.getParent();
		}
		if(parentId == null){
			parentId = queryParentId;
		}
		if(entity == null){
			entity =  new SysOrg();
			if(StringUtil.isNotEmpty(parentId)){
				parent = service.get(parentId);
			}else{
				parent = entity.getParent();
			}
		}
		if(parent == null){
			parent = new SysOrg();
		}
		model.addAttribute("entity", entity);
		model.addAttribute("parent", parent);
		model.addAttribute("queryParentId", queryParentId);
		model.addAttribute("showDescendants", showDescendants);
		return ModuleView("/org/form");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysRoleSave(Model model, SysOrg bean, Integer parentId) {
		try {
			if(bean.getId() == null){
				service.save(bean, parentId);
			}else{
				service.update(bean, parentId);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdate(Integer[] id) {
		try {
			if (ArrayUtils.isNotEmpty(id)) {
				SysOrg[] beans = service.batchUpdate(id);
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/orgDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  sysRoleDel(Integer[] id) {
		// 删除
		try {
			service.delete(id);
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
}
