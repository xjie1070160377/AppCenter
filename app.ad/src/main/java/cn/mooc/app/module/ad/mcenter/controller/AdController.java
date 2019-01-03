package cn.mooc.app.module.ad.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.ad.data.entity.AdSlot;
import cn.mooc.app.module.ad.model.AdModel;
import cn.mooc.app.module.ad.service.AdService;
import cn.mooc.app.module.ad.service.AdSlotService;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * AdController
 * 广告控制器
 * 
 * @author oyhx
 * @date 2016-05-09
 */
@Controller
@RequestMapping("/cms/ad")
public class AdController extends AdModuleController {

	@Autowired
	private AdService adService;
	@Autowired
	private AdSlotService adSlotService;

	@RequestMapping("/adList")
	public String adList(Model model, PagerParam pageParam) {
		return ModuleView("/ad/adList");
	}

	@RequestMapping("/adListJson")
	@ResponseBody
	public Map<String, Object> adListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<Ad> pageData = adService.findAdList(searchParams, pageParam);
		List<AdModel> adModels = new ArrayList<AdModel>();
		for (Ad ad : pageData.getContent()) {
			AdModel adModel = new AdModel(ad);
			adModels.add(adModel);
		}
		return HttpResponseUtil.successJson(adModels, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}

	@RequestMapping("/adAdd")
	public String adAdd(Model model, Ad entity) {
		model.addAttribute("entity", new Ad());
		model.addAttribute("slot", new AdSlot());
		List<AdSlot> adSlots = adSlotService.getAllAdSlots();
		model.addAttribute("adSlots",adSlots);
		
		return ModuleView("/ad/adForm");
	}

	@RequestMapping("/adEdit")
	public String adEdit(Model model, Integer id) {

		Ad entity = adService.getAdById(id);
		model.addAttribute("entity", entity);
		AdSlot slot = entity.getAdSlot();
		slot = adSlotService.getAdSlotById(slot.getId());
		model.addAttribute("slot", slot);
		
		List<AdSlot> adSlots = adSlotService.getAllAdSlots();
		model.addAttribute("adSlots",adSlots);

		return ModuleView("/ad/adForm");
	}

	@RequestMapping(value = "/adSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adSave(Model model, Ad entity, Integer adSlotid) {

		try {
//			entity.setSiteId(getCurrentSiteId());
			this.adService.saveAd(entity, adSlotid);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/adUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adUpdate(@ModelAttribute("entity") Ad entity, Integer adSlotid,  Model model) {
		try {
			this.adService.updateAd(entity, adSlotid);
		}catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/adDel", method = RequestMethod.POST)
	@ResponseBody
	public boolean adDel(Integer id) {
		// 删除
		boolean result = adService.delAd(id);
		return result;
	}

	@RequestMapping(value = "/adDels", method = RequestMethod.POST)
	@ResponseBody
	public boolean adDels(Integer[] ids) {
		// 删除
		int result = adService.delAds(ids);
		return result > 0;
	}

	@ModelAttribute("entity")
	public Ad preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		
		return oid != null ? adService.getAdById(oid) : null;
	}
}