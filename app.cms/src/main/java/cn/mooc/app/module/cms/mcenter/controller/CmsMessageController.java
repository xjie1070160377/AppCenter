package cn.mooc.app.module.cms.mcenter.controller;

import java.util.HashMap;
import java.util.Map;

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
import cn.mooc.app.module.cms.data.entity.CmsMessage;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.CmsMessageService;

/**
 * CmsMessageController
 * 消息中心控制器
 * 
 * @author zjj
 * @date 2016-08-10
 */
@Controller
@RequestMapping("/cms/message")
public class CmsMessageController extends CmsModuleController {

	@Autowired
	private CmsMessageService cmsMessageService;

	@RequestMapping("/messageList")
	public String cmsMessageList(Model model, PagerParam pageParam) {
		return ModuleView("/message/messageList");
	}

	@RequestMapping("/messageListJson")
	@ResponseBody
	public Map<String, Object> cmsMessageListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Integer siteId = this.getCurrentSiteId();
		searchParams.put("EQ_site.id", siteId);

		Page<CmsMessage> pageData = cmsMessageService.findCmsMessagePage(searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/messageAdd")
	public String cmsMessageAdd(Model model) {
		model.addAttribute("entity", new CmsMessage());

		return ModuleView("/message/messageForm");
	}

	@RequestMapping("/messageEdit")
	public String cmsMessageEdit(Model model, Integer id) {

		CmsMessage entity = cmsMessageService.getCmsMessageById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/message/messageForm");
	}

	@RequestMapping(value = "/messageSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsMessageSave(Model model, CmsMessage entity) {

		try {
			Site site = this.getCurrentSite();
			entity.setSite(site);
			this.cmsMessageService.saveCmsMessage(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/messageUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsMessageUpdate(@ModelAttribute("entity") CmsMessage entity,  Model model) {
		try {
			this.cmsMessageService.updateCmsMessage(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/messageDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsMessageDel(Integer id) {
		// 删除
		try {
			cmsMessageService.delCmsMessage(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/messageDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cmsMessageDels(Integer[] ids) {
		// 删除
		try {
			cmsMessageService.delCmsMessages(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public CmsMessage preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		return oid != null ? cmsMessageService.getCmsMessageById(oid) : null;
	}
}