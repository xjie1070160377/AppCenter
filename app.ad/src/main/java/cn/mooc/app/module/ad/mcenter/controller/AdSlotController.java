package cn.mooc.app.module.ad.mcenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.ad.data.entity.AdSlot;
import cn.mooc.app.module.ad.service.AdSlotService;

/**
 * AdSlotController
 * 广告版位控制器
 * 
 * @author oyhx
 * @date 2016-05-06
 */
@Controller
@RequestMapping("/cms/adSlot")
public class AdSlotController extends AdModuleController {

	@Autowired
	private AdSlotService adSlotService;

	@RequestMapping("/adSlotList")
	public String adSlotList(Model model, PagerParam pageParam) {
		return ModuleView("/adSlot/adSlotList");
	}

	@RequestMapping("/adSlotListJson")
	@ResponseBody
	public Map<String, Object> adSlotListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<AdSlot> pageData = adSlotService.findAdSlotList(searchParams, pageParam);
		Map<String, Object> resMap = new HashMap<String, Object>();
//		resMap.put("id", "name");
		return HttpResponseUtil.successJson(resMap,pageData, pageParam);
	}

	@RequestMapping("/adSlotAdd")
	public String adSlotAdd(Model model, AdSlot entity) {
		model.addAttribute("entity", new AdSlot());

		return ModuleView("/adSlot/adSlotForm");
	}

	@RequestMapping("/adSlotEdit")
	public String adSlotEdit(Model model, Integer id) {

		AdSlot entity = adSlotService.getAdSlotById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/adSlot/adSlotForm");
	}

	@RequestMapping(value = "/adSlotSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adSlotSave(Model model, AdSlot entity) {

		try {
//			entity.setSiteId(getCurrentSiteId());
			this.adSlotService.saveAdSlot(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/adSlotUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adSlotUpdate(@ModelAttribute("entity") AdSlot entity,  Model model) {
		try {
			this.adSlotService.updateAdSlot(entity);
		}catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/adSlotDel", method = RequestMethod.POST)
	@ResponseBody
	public boolean adSlotDel(Integer id) {
		// 删除
		boolean result = adSlotService.delAdSlot(id);
		return result;
	}

	@RequestMapping(value = "/adSlotDels", method = RequestMethod.POST)
	@ResponseBody
	public boolean adSlotDels(Integer[] ids) {
		// 删除
		int result = adSlotService.delAdSlots(ids);
		return result > 0;
	}

	@ModelAttribute("entity")
	public AdSlot preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? adSlotService.getAdSlotById(oid) : null;
	}
}