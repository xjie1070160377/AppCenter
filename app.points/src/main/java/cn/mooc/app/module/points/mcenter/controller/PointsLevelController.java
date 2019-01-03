package cn.mooc.app.module.points.mcenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.PointsLevel;
import cn.mooc.app.module.points.service.PointsLevelService;

@Controller
@RequestMapping("/points/level")
public class PointsLevelController extends PointsModuleController{
	

	@RequestMapping("/list")
	public String list(Model modelMap) {
		return ModuleView("/pointsLevel/list");
	}
	
	@RequestMapping("/levelListJson")
	@ResponseBody
	public Map<String, Object> levelListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<PointsLevel> pageData = service.findAll(searchParams, pageParam);
		return HttpResponseUtil.successJson(pageData.getContent(), pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}

	@RequestMapping("create")
	public String create(Integer id, Model model) {
		PointsLevel bean = null;
		if (id != null) {
			bean = service.get(id);
		}else{
			bean = new PointsLevel();
		}
		model.addAttribute("bean", bean);
		return ModuleView("/pointsLevel/form");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> save(PointsLevel bean, String redirect, Model model) {
		try{
			service.save(bean);
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@ModelAttribute("bean") PointsLevel bean,  Model model) {
		try{
			service.update(bean);
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String id) {
		try{
			String[] ids = id.split(",");
			Integer[] idarray = new Integer[ids.length];
			int i = 0;
			for(String arg : ids){
				idarray[i] = StringUtil.strnull2Int(arg);
				i++;
			}
			service.delete(idarray);
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("bean")
	public PointsLevel preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}

	@Autowired
	private PointsLevelService service;
}
