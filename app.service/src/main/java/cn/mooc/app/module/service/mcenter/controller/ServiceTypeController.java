package cn.mooc.app.module.service.mcenter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.service.data.entity.AppServicetype;
import cn.mooc.app.module.service.service.AppServicetypeService;

@Controller
@RequestMapping("/service/appServiceType")
public class ServiceTypeController extends ServiceController {

	@Autowired
	private AppServicetypeService service;
	
	@RequestMapping("/list")
	public String modelList(String queryType, Model model, PagerParam pageParam) {
		return ModuleView("/appServiceType/list");
	}
	
	@RequestMapping("/modelListJson")
	@ResponseBody
	public Map<String, Object> modelListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(keyWord)){
			searchParams.put(columnFiled, keyWord);
		}
		Page<AppServicetype> pageData = service.findAppServicetypePage(searchParams, pageParam);
		
		return HttpResponseUtil.successJson(pageData.getContent(),pageData, pageParam);
	}
	
	@RequestMapping("/edit")
	public String edit(Model model, Integer serviceTypeid) {
		AppServicetype entity = null;
		if(StringUtil.isNull(serviceTypeid)){
			entity = new AppServicetype();
		}else{
			entity = service.getAppServicetypeById(serviceTypeid);
		}
		model.addAttribute("entity", entity);

		return ModuleView("/appServiceType/form");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Model model, AppServicetype bean) {

		try {
			service.saveAppServicetype(bean);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(Model model, Integer[] id) {

		try {
			service.delAppServicetypes(id);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("删除失败.");
		}
		
		return HttpResponseUtil.successJson();
	}
}
