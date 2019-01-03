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
import cn.mooc.app.module.points.data.entity.Good;
import cn.mooc.app.module.points.model.GoodModel;
import cn.mooc.app.module.points.service.GoodService;

/**
 * GoodController
 * 商品信息控制器
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Controller
@RequestMapping("/points/good")
public class GoodController extends PointsModuleController {

	@Autowired
	private GoodService goodService;

	@RequestMapping("/goodList")
	public String goodList(Model model, PagerParam pageParam) {
		return ModuleView("/good/goodList");
	}

	@RequestMapping("/goodListJson")
	@ResponseBody
	public Map<String, Object> goodListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<Good> pageData = goodService.findGoodPage(searchParams, pageParam);
		List<GoodModel> goodList = new ArrayList<GoodModel>();
		for(Good good : pageData.getContent()){
			GoodModel m = new GoodModel(good);
			goodList.add(m);
		}

		return HttpResponseUtil.successJson(goodList, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}

	@RequestMapping("/goodAdd")
	public String goodAdd(Model model) {
		model.addAttribute("entity", new Good());

		return ModuleView("/good/goodForm");
	}

	@RequestMapping("/goodEdit")
	public String goodEdit(Model model, Integer id) {

		Good entity = goodService.getGoodById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/good/goodForm");
	}

	@RequestMapping(value = "/goodSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goodSave(Model model, Good entity) {

		try {
			this.goodService.saveGood(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/goodUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goodUpdate(@ModelAttribute("entity") Good entity,  Model model) {
		try {
			this.goodService.updateGood(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/goodDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goodDel(Integer id) {
		// 删除
		try {
			goodService.delGood(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/goodDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> goodDels(Integer[] ids) {
		// 删除
		try {
			goodService.delGoods(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public Good preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? goodService.getGoodById(oid) : null;
	}
}