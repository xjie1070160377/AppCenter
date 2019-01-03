package cn.mooc.app.module.points.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.antlr.v4.runtime.ParserInterpreter;
import org.apache.commons.beanutils.BeanAccessLanguageException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.util.JqGridHandler;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.model.PointsInfoModel;
import cn.mooc.app.module.points.service.PointsInfoService;
import cn.mooc.app.module.points.service.PointsRuleService;
import javassist.expr.NewArray;

@Controller
@RequestMapping("/points/rule")
public class PointsRuleController extends PointsModuleController {

	@Autowired
	PointsRuleService service;
	@Autowired
	PointsInfoService ruleInfoService;
	
	@RequestMapping("/list")
	public ModelAndView list(Model model) {
		return ModuleModelAndView("/pointsRule/list");
	}
	
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> page(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<PointsRule> pageData = service.findAll(searchParams, pageParam);
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/pageRuleInfo")
	@ResponseBody
	public Map<String, Object> pageRuleInfo(@RequestParam(required = true) Integer id, Model model, PagerParam pageParam, HttpServletRequest request) {
		if(id == null){
			return null;
		}
		Page<PointsInfo> pis = ruleInfoService.findListByRule(id, pageParam);
		List<PointsInfoModel> mlist = new ArrayList<PointsInfoModel>();
		for(PointsInfo pi : pis.getContent()){
			PointsInfoModel m = new PointsInfoModel(pi);
			mlist.add(m);
		}
		return HttpResponseUtil.successJson(mlist, pis.getTotalElements(), pis.getTotalPages(), pageParam);
	}
	
	@RequestMapping("/pageSelectRuleInfo")
	@ResponseBody
	public Map<String, Object> pageSelectRuleInfo(@RequestParam(required = true) Integer ruleId, Integer infoId, String title, Model model, PagerParam pageParam, HttpServletRequest request) {
		Page<Map> pagedList = ruleInfoService.querySelectInfo(ruleId, infoId, title, pageParam);
		return HttpResponseUtil.successJson(pagedList, pageParam);
	}
	
	@RequestMapping("create")
	public String create(Integer id, Model model) {
		PointsRule bean = null;
		List<PointsInfo> pis = null;
		if (id != null) {
			bean = service.get(id);
			pis = ruleInfoService.findListByRule(id);
		}else{
			bean = new PointsRule();
		}
		model.addAttribute("bean", bean);
		model.addAttribute("pis", pis);
		return ModuleView("/pointsRule/form");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> save(PointsRule bean, String details, Model model) {
		try{
			if(StringUtils.isEmpty(details)){
				service.save(bean);
			}else{
				List detail_list =  (List)JSONUtils.parse(details);
				if(detail_list.size() == 0){
					service.save(bean);
				}else{
					service.save(bean, detail_list);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(PointsRule bean, String details) {
		try{
			if(StringUtils.isEmpty(details)){
				service.save(bean);
			}else{
				List detail_list =  (List)JSONUtils.parse(details);
				if(detail_list.size() == 0){
					service.save(bean);
				}else{
					service.save(bean, detail_list);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("bean")
	public PointsRule preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}
}
