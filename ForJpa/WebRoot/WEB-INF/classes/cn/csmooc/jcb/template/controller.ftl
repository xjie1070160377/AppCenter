package ${packageName}.mcenter.controller;

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

import ${packageName}.data.entity.${baseName};
import ${packageName}.service.${baseName}Service;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * ${baseName}Controller
 * ${manager}控制器
 * 
 * @author ${author}
 * @date ${newDate?string("yyyy-MM-dd")}
 */
@Controller
@RequestMapping("/${module}/${entity}")
public class ${baseName}Controller extends ${moduleController} {

	@Autowired
	private ${baseName}Service ${entity}Service;

	@RequestMapping("/${entity}List")
	public String ${entity}List(Model model, PagerParam pageParam) {
		return ModuleView("/${entity}/${entity}List");
	}

	@RequestMapping("/${entity}ListJson")
	@ResponseBody
	public Map<String, Object> ${entity}ListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<${baseName}> pageData = ${entity}Service.find${baseName}Page(searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/${entity}Add")
	public String ${entity}Add(Model model) {
		model.addAttribute("entity", new ${baseName}());

		return ModuleView("/${entity}/${entity}Form");
	}

	@RequestMapping("/${entity}Edit")
	public String ${entity}Edit(Model model, ${pkType} id) {

		${baseName} entity = ${entity}Service.get${baseName}ById(id);
		model.addAttribute("entity", entity);

		return ModuleView("/${entity}/${entity}Form");
	}

	@RequestMapping(value = "/${entity}Save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ${entity}Save(Model model, ${baseName} entity) {

		try {
			this.${entity}Service.save${baseName}(entity);
		}  catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		}  catch (Exception e) {
			return HttpResponseUtil.failureJson("新增失败");
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/${entity}Update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ${entity}Update(@ModelAttribute("entity") ${baseName} entity,  Model model) {
		try {
			this.${entity}Service.update${baseName}(entity);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/${entity}Del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ${entity}Del(${pkType} id) {
		// 删除
		try {
			${entity}Service.del${baseName}(id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/${entity}Dels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ${entity}Dels(${pkType}[] ids) {
		// 删除
		try {
			${entity}Service.del${baseName}s(ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@ModelAttribute("entity")
	public ${baseName} preloadBean(${pkType} oid, org.springframework.ui.Model modelMap) {
		return StringUtils.isNotEmpty(oid) ? ${entity}Service.get${baseName}ById(oid) : null;
	}
}