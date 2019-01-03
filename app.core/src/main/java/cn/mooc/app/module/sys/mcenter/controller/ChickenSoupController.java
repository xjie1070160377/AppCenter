package cn.mooc.app.module.sys.mcenter.controller;

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
import cn.mooc.app.module.sys.data.entity.ChickenSoup;
import cn.mooc.app.module.sys.service.ChickenSoupService;

@Controller
@RequestMapping("/sys/chickensoup")
public class ChickenSoupController extends SysModuleController{
	
	@Autowired
	private ChickenSoupService chickensoupService;
	
	
	/**
	 * @description 心灵鸡汤列表页
	 * @param model
	 * @param pageParam
	 * @return
	 */
	@RequestMapping("/chickensoupList")
	public String chickensoupList(Model model, PagerParam pageParam) {
		return ModuleView("/chickenSoup/list");
	}
	
	/**
	 * @description 心灵鸡汤json分页数据
	 * @param model
	 * @param pageParam
	 * @return
	 */
	@RequestMapping("/chickensoupListJson")
	@ResponseBody
	public Map<String, Object> chickensoupListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Page<ChickenSoup> pageData = chickensoupService.findChickenSoupPage(searchParams, pageParam);
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/chickensoupAdd")
	public String chickensoupAdd(Model model) {
		model.addAttribute("entity", new ChickenSoup());

		return ModuleView("/chickenSoup/form");
	}

	@RequestMapping("/chickensoupEdit")
	public String chickensoup(Model model, String id) {
		ChickenSoup entity = chickensoupService.getChickenSoupById(id);
		model.addAttribute("entity", entity);
		return ModuleView("/chickenSoup/form");
	}
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/chickensoupSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chickensoupSave(Model model, ChickenSoup entity) {

		try {
			this.chickensoupService.savechickenSoup(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		return HttpResponseUtil.successJson();
	}
	/**
	 * 修改
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/chickensoupUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chickensoupUpdate(@ModelAttribute("entity") ChickenSoup entity, Model model) {
		try {
			this.chickensoupService.updatechickenSoup(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/chickensoupDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chickensoupDel(String id) {
		// 删除
		try {
			chickensoupService.delchickenSoup(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/chickensoupDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chickensoupDels(String[] ids) {
		// 删除
		try {
			chickensoupService.delchickenSoups(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@ModelAttribute("entity")
	public ChickenSoup preloadBean(String oid, org.springframework.ui.Model modelMap) {
		return oid != null ? chickensoupService.getChickenSoupById(oid) : null;
	}

}
