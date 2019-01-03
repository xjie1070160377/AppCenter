package cn.mooc.app.module.interact.mcenter.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.model.InteractMarkModel;
import cn.mooc.app.module.interact.service.InteractMarkService;

/**
 * InteractMarkController
 * 标记控制器
 * 
 * @author oyhx
 * @date 2016-05-13
 */
@Controller
@RequestMapping("/interact/interactMark")
public class InteractMarkController extends InteractModuleController {

	@Autowired
	private InteractMarkService interactMarkService;

	@RequestMapping("/interactMarkList")
	public String interactMarkList(Model model, PagerParam pageParam) {
		return ModuleView("/interactMark/interactMarkList");
	}

	@RequestMapping("/interactMarkListJson")
	@ResponseBody
	public Map<String, Object> interactMarkListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if("EQ_infoId".equals(columnFiled) && !"".equals(keyWord)){
			searchParams.put(columnFiled, Long.parseLong(keyWord.toString()));
		}else{
			searchParams.put(columnFiled, keyWord);
		}
		Page<InteractMark> pageData = interactMarkService.findInteractMarkList(searchParams, pageParam);
		List<InteractMarkModel> markModels = new ArrayList<InteractMarkModel>();
		for (InteractMark mark : pageData.getContent()) {
			InteractMarkModel markModel = new InteractMarkModel(mark);
			markModels.add(markModel);
		}
		return HttpResponseUtil.successJson(markModels, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}

	@RequestMapping("/interactMarkAdd")
	public String interactMarkAdd(Model model) {
		model.addAttribute("entity", new InteractMark());
		
		return ModuleView("/interactMark/interactMarkForm");
	}

	@RequestMapping("/interactMarkEdit")
	public String interactMarkEdit(Model model, String id) {

		InteractMark entity = interactMarkService.getInteractMarkById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/interactMark/interactMarkForm");
	}

	@RequestMapping(value = "/interactMarkSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactMarkSave(Model model, InteractMark entity) {

		try {
//			entity.setFtype(MarkType.InfoSave);
			LoginUserInfo loginUserInfo = webContext.getCurrentUserInfo();
			entity.setUserId(loginUserInfo.getUserId());
			entity.setUserName(loginUserInfo.getUserName());
			this.interactMarkService.saveInteractMark(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/interactMarkUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactMarkUpdate(@ModelAttribute("entity") InteractMark entity,  Model model) {
		try {
			this.interactMarkService.updateInteractMark(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/interactMarkDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactMarkDel(String id) {
		// 删除
		try {
			interactMarkService.delInteractMark(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/interactMarkCancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactMarkCancel(String id) {
		// 删除
		try {
			InteractMark mark = interactMarkService.getInteractMarkById(id);
			interactMarkService.cancelInteractMark(mark.getFid(), mark.getFtype(), mark.getUserId());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	

	@RequestMapping(value = "/interactMarkDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> interactMarkDels(String[] ids) {
		// 删除
		try {
			interactMarkService.delInteractMarks(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public InteractMark preloadBean(@RequestParam(required = false) String oid, org.springframework.ui.Model modelMap) {
		return StringUtils.isNotEmpty(oid) ? interactMarkService.getInteractMarkById(oid) : null;
	}
}