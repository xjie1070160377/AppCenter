package cn.mooc.app.module.points.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.ExchangeGood;
import cn.mooc.app.module.points.data.entity.ExchangeRule;
import cn.mooc.app.module.points.model.ExchangeGoodModel;
import cn.mooc.app.module.points.model.ExchangeRuleModel;
import cn.mooc.app.module.points.service.ExchangeGoodService;
import cn.mooc.app.module.points.service.ExchangeRuleService;

/**
 * ExchangeRuleController
 * R币兑换规则控制器
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Controller
@RequestMapping("/points/exchangeRule")
public class ExchangeRuleController extends PointsModuleController {

	@Autowired
	private ExchangeRuleService exchangeRuleService;
	@Autowired
	private ExchangeGoodService exchangeGoodService;

	@RequestMapping("/exchangeRuleList")
	public String exchangeRuleList(Model model, PagerParam pageParam) {
		return ModuleView("/exchangeRule/exchangeRuleList");
	}

	@RequestMapping("/exchangeRuleListJson")
	@ResponseBody
	public Map<String, Object> exchangeRuleListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<ExchangeRule> pageData = exchangeRuleService.findExchangeRulePage(searchParams, pageParam);
		List<ExchangeRuleModel> list = new ArrayList<ExchangeRuleModel>();
		for(ExchangeRule rule : pageData.getContent()){
			ExchangeRuleModel m = new ExchangeRuleModel(rule);
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	@RequestMapping("/exchangeRuleGoodListJson")
	@ResponseBody
	public Map<String, Object> exchangeRuleGoodListJson(Integer ruleId, PagerParam pagerParam){
		List<ExchangeGood> goodList = exchangeGoodService.findByExchangeRuleId(ruleId);
		List<ExchangeGoodModel> list = new ArrayList<ExchangeGoodModel>();
		for(ExchangeGood good : goodList){
			ExchangeGoodModel m = new ExchangeGoodModel(good);
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, list.size(), 1, pagerParam);
	}

	@RequestMapping("/exchangeRuleAdd")
	public String exchangeRuleAdd(Model model) {
		model.addAttribute("bean", new ExchangeRule());

		return ModuleView("/exchangeRule/exchangeRuleForm");
	}

	@RequestMapping("/exchangeRuleEdit")
	public String exchangeRuleEdit(Model model, Integer id) {

		ExchangeRule entity = exchangeRuleService.getExchangeRuleById(id);
		model.addAttribute("bean", entity);

		return ModuleView("/exchangeRule/exchangeRuleForm");
	}

	@RequestMapping(value = "/exchangeRuleSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangeRuleSave(Model model, ExchangeRule entity, String details) {

		try {
			List<Map> detail_list =  (List<Map>)JSONUtils.parse(details);
			this.exchangeRuleService.saveExchangeRule(entity, detail_list);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/exchangeRuleUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangeRuleUpdate(@ModelAttribute("entity")ExchangeRule entity, String details, Model model) {
		try {
			List<Map> detail_list =  (List<Map>)JSONUtils.parse(details);
			this.exchangeRuleService.updateExchangeRule(entity, detail_list);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/exchangeRuleDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangeRuleDel(Integer id) {
		// 删除
		try {
			exchangeRuleService.delExchangeRule(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/exchangeRuleDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> exchangeRuleDels(Integer[] ids) {
		// 删除
		try {
			exchangeRuleService.delExchangeRules(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public ExchangeRule preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? exchangeRuleService.getExchangeRuleById(oid) : null;
	}
}