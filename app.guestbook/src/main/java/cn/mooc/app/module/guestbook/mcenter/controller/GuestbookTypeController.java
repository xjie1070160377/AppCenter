package cn.mooc.app.module.guestbook.mcenter.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import cn.mooc.app.module.guestbook.data.entity.GuestbookType;
import cn.mooc.app.module.guestbook.service.GuestbookTypeService;

/**
 * @author linwei
 * @date 2016年8月27日
 * @description 留言类型控制器
 */
@Controller
@RequestMapping("/guestbook/guestbookType")
public class GuestbookTypeController extends GuestbookModuleController {
	
	@Autowired
	private GuestbookTypeService guestbookTypeService;

	@RequestMapping("/list")
	public String list(Model model, PagerParam pageParam) {
		return ModuleView("guestbookType/list");
	}

	@RequestMapping(value="/listJson", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Page<GuestbookType> pageData = guestbookTypeService.findGuestbookTypeList(searchParams, pageParam);
		return HttpResponseUtil.successJson(pageData.getContent(), pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	/**
	 * 新增
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String Add(Model model) {
		model.addAttribute("entity", new GuestbookType());
		model.addAttribute("oprt", "save");
		return ModuleView("/guestbookType/form");
	}
	
	/**
	 * 修改
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String interactCommentEdit(Model model, String id) {
		GuestbookType entity = guestbookTypeService.getGuestbookTypeById(id);
		model.addAttribute("entity", entity);
		model.addAttribute("oprt", "update");
		return ModuleView("/guestbookType/form");
	}
	
	/**
	 * 保存
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Model model, GuestbookType entity) {
		try {
			entity.setCrateTime(new Date());
			guestbookTypeService.saveGuestbookType(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 更新
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Model model, @ModelAttribute("entity") GuestbookType entity ) {
		
		try {
			guestbookTypeService.updateGuestbookType(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 删除
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(Model model, String id ) {
		
		try {
			//TODO: 判断如果类型下有留言则提示不能删除
			guestbookTypeService.delGuestbookType(id);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} 
		return HttpResponseUtil.successJson();
	}
	
	@ModelAttribute("entity")
	public GuestbookType preloadBean(@RequestParam(required = false) String oid, org.springframework.ui.Model modelMap) {
		return StringUtils.isNotEmpty(oid) ? guestbookTypeService.getGuestbookTypeById(oid) : null;
	}

}
