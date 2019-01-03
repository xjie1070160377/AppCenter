package cn.mooc.app.module.service.mcenter.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.data.entity.SysOrg;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.SysOrgService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.service.data.entity.AppService;
import cn.mooc.app.module.service.data.entity.AppServicetype;
import cn.mooc.app.module.service.data.nodel.AppServiceModel;
import cn.mooc.app.module.service.service.AppServiceService;
import cn.mooc.app.module.service.service.AppServicetypeService;
@Controller
@RequestMapping("/service/appService")
public class AppServiceController extends ServiceController {
	
	@Autowired
	private AppServiceService service;
	@Autowired
	private SysOrgService orgService;
	@Autowired
	private AppServicetypeService typeService;
	
	@RequestMapping("/list")
	public String modelList(String queryType, Model model, PagerParam pageParam) {
		return ModuleView("/appService/appService_list");
	}
	
	@RequestMapping("/modelListJson")
	@ResponseBody
	public Map<String, Object> modelListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		Page<AppService> pageData = service.findAppServicePage(searchParams, pageParam);
		
		List<AppService> appList = pageData.getContent();
		List<AppServiceModel> list = new ArrayList<AppServiceModel>();
		for(AppService app : appList){
			String orgName = "";
			String userName = "";
			String serviceTypeName = "";
			if(app.getCreaterUserid() != null){
				SysUserInfo userInfo = this.webContext.getSysUserFullInfo(app.getCreaterUserid());
				if(userInfo != null){
					userName = userInfo.getNickName();
				}
			}
			if(app.getOrgId() != null){
				SysOrg org = orgService.get(app.getOrgId());
				if(org != null){
					orgName = org.getName();
				}
			}
			if(app.getServicetype() != null){
				serviceTypeName = app.getServicetype().getServiceTypeName();
			}
			
			AppServiceModel serviceModel = new AppServiceModel(app.getServiceId(), app.getServiceName(),app.getServiceFullname(),orgName, serviceTypeName, app.getIsChecked(), app.getCreateTime(), userName, app.getCharger(), app.getTelephone(), app.getMobile());
			list.add(serviceModel);
		}
		
		return HttpResponseUtil.successJson(list,pageData, pageParam);
	}
	
	@RequestMapping("/create")
	public String create(Model model) {
		AppService entity = new AppService();
		entity.setServicetype(new AppServicetype());
		model.addAttribute("entity", entity);
		model.addAttribute("orgName", "");
		List<AppServicetype> types = typeService.getAllAppServicetypes();
		model.addAttribute("types", types);
		model.addAttribute(OPRT, CREATE);
		return ModuleView("/appService/appService_form");
	}
	
	@RequestMapping("/edit")
	public String edit(Model model, @RequestParam(required = true) Integer id) {

		AppService entity = service.getAppServiceById(id);
		if(entity.getOrgId() != null){
			SysOrg org = orgService.get(entity.getOrgId());
			if(org != null){
				model.addAttribute("orgName", org.getFullName());
			}
		}
		List<AppServicetype> types = typeService.getAllAppServicetypes();
		model.addAttribute("types", types);
		model.addAttribute("entity", entity);
		model.addAttribute(OPRT, EDIT);
		return ModuleView("/appService/appService_form");
	}
	
	@RequestMapping("/view")
	public String view(Model model, @RequestParam(required = true) Integer id) {

		AppService entity = service.getAppServiceById(id);
		if(entity.getOrgId() != null){
			SysOrg org = orgService.get(entity.getOrgId());
			if(org != null){
				model.addAttribute("orgName", org.getFullName());
			}
		}
		List<AppServicetype> types = typeService.getAllAppServicetypes();
		model.addAttribute("types", types);
		model.addAttribute("entity", entity);
		model.addAttribute(OPRT, VIEW);
		return ModuleView("/appService/appService_form");
	}
	
	@RequestMapping("/check")
	public String check(Model model, @RequestParam(required = true) Integer id) {

		AppService entity = service.getAppServiceById(id);
		if(entity.getOrgId() != null){
			SysOrg org = orgService.get(entity.getOrgId());
			if(org != null){
				model.addAttribute("orgName", org.getFullName());
			}
		}
		List<AppServicetype> types = typeService.getAllAppServicetypes();
		model.addAttribute("types", types);
		model.addAttribute("entity", entity);
		if(entity.getIsChecked() == null || entity.getIsChecked().intValue() == 0){
			model.addAttribute(OPRT, "audit");
		}else{
			model.addAttribute(OPRT, "antiAudit");
		}
		return ModuleView("/appService/appService_form");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(Model model, AppService bean) {

		try {
			bean.setIsFree(1);
			bean.setIsChecked(0);
			bean.setCreateTime(new Timestamp(new Date().getTime()));
			Long userId = this.webContext.getCurrentUserId();
			bean.setCreaterUserid(userId);
//			User user = Context.getCurrentUser(request);
//			bean.setCmsUser(user);
			service.saveAppService(bean);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(Model model, AppService bean, Integer serviceTypeid, Integer orgId) {

		try {
			bean.setIsFree(1);
			bean.setIsChecked(0);
			bean.setCreateTime(new Timestamp(new Date().getTime()));
			Long userId = this.webContext.getCurrentUserId();
			bean.setCreaterUserid(userId);
			service.saveAppService(bean);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> audit(Model model, @RequestParam(required = true) Integer id, Integer isChecked, String checkNote) {

		try {
			AppService bean = service.getAppServiceById(id);
			if(isChecked.intValue() == 1){
				bean.setIsChecked(1);
			}else{
				bean.setIsFree(-1);
				bean.setIsChecked(-1);
			}
			bean.setCheckNote(checkNote);
			service.saveAppService(bean);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/antiAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> antiAudit(Model model, @RequestParam(required = true) Integer id) {

		try {
			AppService bean = service.getAppServiceById(id);
			bean.setIsChecked(0);
			service.saveAppService(bean);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败.");
		}
		
		return HttpResponseUtil.successJson();
	}
}
