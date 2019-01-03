package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsContributeRec;
import cn.mooc.app.module.cms.model.ContributeModel;
import cn.mooc.app.module.cms.service.CmsContributeFileService;
import cn.mooc.app.module.cms.service.CmsContributeRecService;
import cn.mooc.app.module.cms.service.CmsContributeService;

@Controller
@RequestMapping("/cms/contribute")
public class ContributeController extends CmsModuleController{

	@RequestMapping("list")
	public String list(Model modelMap,  Integer queryStatus) {
		modelMap.addAttribute("queryStatus", queryStatus);
		return ModuleView("/contribute/list");
	}
	
	@RequestMapping("/contributeListJson")
	@ResponseBody
	public Map<String, Object> infoListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord, Integer queryStatus) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		if(queryStatus != null){
			searchParams.put("EQ_status", queryStatus);
		}

		Page<CmsContributeRec> pageData = recService.findAll(searchParams, pageParam);
		List<ContributeModel> list = new ArrayList<ContributeModel>();
		for (CmsContributeRec rec : pageData.getContent()) {
			ContributeModel md = new ContributeModel(rec);
			list.add(md);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
	
	@RequestMapping("view")
	public String view(Integer id,
			HttpServletRequest request, Model modelMap) {
		CmsContributeRec bean = recService.get(id);
		bean.setFiles(fileService.findFileByRec(bean.getId()));
		bean.setImages(fileService.findImageByRec(bean.getId()));
		modelMap.addAttribute("bean", bean);
		
		//Site site = getCurrentSite();
		//request.setAttribute("siteUrl", site.getSiteUrl());
		request.setAttribute("siteUrl", "http://192.168.1.128:8080/app.center");
		return ModuleView("/contribute/view");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(Integer id, Integer infoId, Integer status, String replay, String redirect,
			HttpServletRequest request, RedirectAttributes ra) {
		if(infoId == null && status == null){
			status = 0;
		}
		boolean b = recService.bingdingInfo(id, infoId, replay, status);
		if(!b){
			return HttpResponseUtil.failureJson("指定文章已绑定其他投稿,绑定失败！");
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("pass")
	@ResponseBody
	public Map<String, Object> pass(Integer[] ids, 
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		recService.updateStatus(ids,CmsContributeRec.PASS);
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("pedding")
	@ResponseBody
	public Map<String, Object> pedding(Integer[] ids, 
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		recService.updateStatus(ids,CmsContributeRec.PEDDING);
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("reject")
	@ResponseBody
	public Map<String, Object> reject(Integer[] ids, 
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		recService.updateStatus(ids,CmsContributeRec.UNPASS);
		return HttpResponseUtil.successJson();
	}
	
	@Autowired
	private CmsContributeService service;
	@Autowired
	private CmsContributeRecService recService;
	@Autowired
	private CmsContributeFileService fileService;
	
}
