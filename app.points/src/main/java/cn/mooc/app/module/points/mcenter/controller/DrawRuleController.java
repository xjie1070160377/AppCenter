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
import cn.mooc.app.module.points.data.entity.DrawRule;
import cn.mooc.app.module.points.data.entity.GoodsChance;
import cn.mooc.app.module.points.model.DrawRuleModel;
import cn.mooc.app.module.points.model.GoodsChanceModel;
import cn.mooc.app.module.points.service.DrawRuleService;
import cn.mooc.app.module.points.service.GoodsChanceService;

/**
 * DrawRuleController
 * 抽奖规则控制器
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Controller
@RequestMapping("/points/drawRule")
public class DrawRuleController extends PointsModuleController {

	@Autowired
	private DrawRuleService drawRuleService;
	@Autowired
	private GoodsChanceService goodsChanceService;

	@RequestMapping("/drawRuleList")
	public String drawRuleList(Model model, PagerParam pageParam) {
		return ModuleView("/drawRule/drawRuleList");
	}

	@RequestMapping("/drawRuleListJson")
	@ResponseBody
	public Map<String, Object> drawRuleListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<DrawRule> pageData = drawRuleService.findDrawRulePage(searchParams, pageParam);
		List<DrawRuleModel> list = new ArrayList<DrawRuleModel>();
		for(DrawRule rule : pageData.getContent()){
			DrawRuleModel m = new DrawRuleModel(rule);
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping("/drawRuleGoodListJson")
	@ResponseBody
	public Map<String, Object> drawRuleGoodListJson(Integer ruleId, PagerParam pagerParam){
		List<GoodsChance> goodList = goodsChanceService.findByDrawRuleId(ruleId);
		List<GoodsChanceModel> list = new ArrayList<GoodsChanceModel>();
		for(GoodsChance good : goodList){
			GoodsChanceModel m = new GoodsChanceModel(good);
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, list.size(), 1, pagerParam);
	}


	@RequestMapping("/drawRuleAdd")
	public String drawRuleAdd(Model model) {
		model.addAttribute("bean", new DrawRule());

		return ModuleView("/drawRule/drawRuleForm");
	}

	@RequestMapping("/drawRuleEdit")
	public String drawRuleEdit(Model model, Integer id) {

		DrawRule entity = drawRuleService.getDrawRuleById(id);
		model.addAttribute("bean", entity);

		return ModuleView("/drawRule/drawRuleForm");
	}

	@RequestMapping(value = "/drawRuleSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> drawRuleSave(Model model, DrawRule entity, String details) {

		try {
			List<Map> detail_list =  (List<Map>)JSONUtils.parse(details);
			this.drawRuleService.saveDrawRule(entity,detail_list);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/drawRuleUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> drawRuleUpdate(@ModelAttribute("entity") DrawRule entity, String details,  Model model) {
		try {
			List<Map> detail_list =  (List<Map>)JSONUtils.parse(details);
			this.drawRuleService.updateDrawRule(entity, detail_list);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/drawRuleDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> drawRuleDel(Integer id) {
		// 删除
		try {
			drawRuleService.delDrawRule(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/drawRuleDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> drawRuleDels(Integer[] ids) {
		// 删除
		try {
			drawRuleService.delDrawRules(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public DrawRule preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? drawRuleService.getDrawRuleById(oid) : null;
	}
}