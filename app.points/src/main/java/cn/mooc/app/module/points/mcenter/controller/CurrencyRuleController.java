package cn.mooc.app.module.points.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.points.data.entity.CurrencyCulNode;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.data.entity.CurrencySpecial;
import cn.mooc.app.module.points.model.CurrencyRuleModel;
import cn.mooc.app.module.points.model.CurrencySpecialModel;
import cn.mooc.app.module.points.service.CurrencyCulNodeService;
import cn.mooc.app.module.points.service.CurrencyRuleService;
import cn.mooc.app.module.points.service.CurrencySpecialService;

/**
 * CurrencyRuleController
 * 获取R币规则控制器
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Controller
@RequestMapping("/points/currencyRule")
public class CurrencyRuleController extends PointsModuleController {

	@Autowired
	private CurrencyRuleService currencyRuleService;
	@Autowired
	private CurrencyCulNodeService currencyCulNodeService;
	@Autowired
	private CurrencySpecialService currencySpecialService;

	@RequestMapping("/currencyRuleList")
	public String currencyRuleList(Model model, PagerParam pageParam) {
		return ModuleView("/currencyRule/currencyRuleList");
	}

	@RequestMapping("/currencyRuleListJson")
	@ResponseBody
	public Map<String, Object> currencyRuleListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<CurrencyRule> pageData = currencyRuleService.findCurrencyRulePage(searchParams, pageParam);
		List<CurrencyRuleModel> list = new ArrayList<CurrencyRuleModel>();
		for(CurrencyRule rule : pageData.getContent()){
			CurrencyRuleModel m = new CurrencyRuleModel(rule);
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping("/currencySpecialListJson")
	@ResponseBody
	public Map<String, Object> currencySpecialListJson(Model model, PagerParam pageParam, Integer ruleId) {
		List<CurrencySpecial> specialList = currencySpecialService.findByRuleId(ruleId);
		List<CurrencySpecialModel> list = new ArrayList<CurrencySpecialModel>();
		for(CurrencySpecial special : specialList){
			CurrencySpecialModel m = new CurrencySpecialModel(special);
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, specialList.size(), 1, pageParam);
	}

	@RequestMapping("/currencyRuleAdd")
	public String currencyRuleAdd(Model model) {
		model.addAttribute("bean", new CurrencyRule());
		model.addAttribute("nodeIds", "");
		model.addAttribute("nodeNames", "");

		return ModuleView("/currencyRule/currencyRuleForm");
	}

	@RequestMapping("/currencyRuleEdit")
	public String currencyRuleEdit(Model model, Integer id) {

		CurrencyRule entity = currencyRuleService.getCurrencyRuleById(id);
		model.addAttribute("bean", entity);
		
		List<CurrencyCulNode> nodeList = currencyCulNodeService.findByRuleId(id);
		String nodeIds = "";
		String nodeNames = "";
		for(CurrencyCulNode node : nodeList){
			if(!"".equals(nodeIds)){
				nodeIds += ",";
				nodeNames += ",";
			}
			nodeIds += node.getNode().getId();
			nodeNames += node.getNode().getName();
		}
		model.addAttribute("nodeIds", nodeIds);
		model.addAttribute("nodeNames", nodeNames);
		return ModuleView("/currencyRule/currencyRuleForm");
	}

	@RequestMapping(value = "/currencyRuleSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> currencyRuleSave(Model model, CurrencyRule entity, Integer[] nodeIds, Integer[] specialIds) {

		try {
			this.currencyRuleService.saveCurrencyRule(entity, nodeIds, specialIds);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/currencyRuleUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> currencyRuleUpdate(@ModelAttribute("entity") CurrencyRule entity, Integer[] nodeIds, Integer[] specialIds,  Model model) {
		try {
			this.currencyRuleService.updateCurrencyRule(entity, nodeIds, specialIds);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/currencyRuleDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> currencyRuleDel(Integer id) {
		// 删除
		try {
			currencyRuleService.delCurrencyRule(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/currencyRuleDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> currencyRuleDels(Integer[] ids) {
		// 删除
		try {
			currencyRuleService.delCurrencyRules(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public CurrencyRule preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null? currencyRuleService.getCurrencyRuleById(oid) : null;
	}
}