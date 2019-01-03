package cn.mooc.app.module.cms.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsContribute;
import cn.mooc.app.module.cms.data.entity.CmsContributeRec;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.WorkflowLog;
import cn.mooc.app.module.cms.service.CmsContributeFileService;
import cn.mooc.app.module.cms.service.CmsContributeRecService;
import cn.mooc.app.module.cms.service.CmsContributeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.WorkflowLogService;
import cn.mooc.app.module.cms.support.Response;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.support.Uploader;
import cn.mooc.app.module.cms.util.JsonMapper;


@Controller
public class ContributeWebController {
	public static final String TEMPLATE = "tg_index.html";
	public static final String TEMPLATE_TOUGAO = "tougao.html";
	public static final String TEMPLATE_XUZHI = "tougaoxuzhi.html";
	public static final String TEMPLATE_LIST = "tougaorecord.html";
	public static final String TEMPLATE_SETTING = "tougaosetting.html";
	
	@RequestMapping(value = "/tg-main.htx")
	public String tg(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = siteService.getWebCurrentSite();
		Map<String, Object> data = modelMap.asMap();
		data.put("mark", "index");
		//ForeContext.setData(data, request);
		return site.getTemplate(TEMPLATE);
	}
	
	@RequestMapping(value = "/tg-xuzhi.htx")
	public String xuzhi(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = siteService.getWebCurrentSite();
		//ForeContext.setData(data, request);
		return site.getTemplate(TEMPLATE_XUZHI);
	}
	
	@RequestMapping(value = "/tg-tougao.htx")
	public String tougao(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = siteService.getWebCurrentSite();
		Long userId = webContext.getCurrentUserId();
		SysUserExt user = sysDataService.getSysUserExt(userId);
		if(user != null){
			CmsContribute contribute = service.findByUser(userId);
			if(contribute == null){
				contribute = new CmsContribute();
			}
			modelMap.addAttribute("contribute", contribute);
		}else{
			CmsContribute contribute = new CmsContribute();
			modelMap.addAttribute("contribute", contribute);
		}
//		data.put("mark", "tougao");
		//ForeContext.setData(data, request);
		return site.getTemplate(TEMPLATE_TOUGAO);
	}
	
	@RequestMapping(value = "/tg-upload.htx")
	public void upload(HttpServletRequest request, Integer type, HttpServletResponse response) throws IOException {
		String ip = Servlets.getRemoteAddr(request);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		JsonMapper mapper = new JsonMapper();
		
		Map params = new HashMap();
		
		if (CollectionUtils.isEmpty(fileMap)) {
			params.put("msg_code", 1);
			params.put("reason", "No upload file found!");
			params.put("result", "");
			Servlets.writeHtml(response, mapper.toJson(params));
		}else{
			MultipartFile partFile = fileMap.entrySet().iterator().next().getValue();
			UploadResult result = new UploadResult();
			result = this.webContext.uploadFile(partFile);
			
			String filetype = "";
			if(type.intValue()==1){
				filetype = Uploader.IMAGE;
			}else{
				filetype = Uploader.FILE;
			}
			Site site = siteService.getWebCurrentSite();
			Long userId = webContext.getCurrentUserId();
//			SysUserExt user = sysDataService.getSysUserExt(userId);
//			if(user != null){
//				userId = user.getId();
//			}
			if (result.isStatus()) {
				String url = result.getFileUrl()+":"+result.getFileSize()+":"+result.getFileName();
				params.put("msg_code", 0);
				params.put("reason", "");
				params.put("result", url);
				Servlets.writeHtml(response, mapper.toJson(params));
			}else{
				params.put("msg_code", 1);
				params.put("reason", "上传失败!");
				params.put("result", "");
				Servlets.writeHtml(response, mapper.toJson(params));
			}
		}
	}
	
	@RequestMapping(value = "/tg-update.htx")
	public String update(@ModelAttribute("bean") CmsContribute bean, String title, String intention, String filedetails, String imagedetails, HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) throws IOException {
		String [] files = null;
		String [] images = null;
		Long userId = webContext.getCurrentUserId();
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		if(user != null){
			bean.setUserId(userId);
		}
		if(StringUtils.isNotEmpty(filedetails)){
			files = filedetails.split(",");
		}
		if(StringUtils.isNotEmpty(imagedetails)){
			images = imagedetails.split(",");
		}
		CmsContributeRec rec = new CmsContributeRec();
		rec.setIntention(intention);
		rec.setTitle(title);
		service.save(bean, rec, files, images);
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("siteId", site.getId());
		Response resp = new Response(request, response, modelMap);
		resp.setStatus(1);
		return resp.post();
	}
	
	@RequestMapping(value = "/tg-save.htx")
	public String save(CmsContribute bean, String title, String intention, String filedetails, String imagedetails, HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) throws IOException {
		String [] files = null;
		String [] images = null;
		Long userId = webContext.getCurrentUserId();
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		if(user != null){
			bean.setUserId(userId);
		}
		if(StringUtils.isNotEmpty(filedetails)){
			files = filedetails.split(",");
		}
		if(StringUtils.isNotEmpty(imagedetails)){
			images = imagedetails.split(",");
		}
		CmsContributeRec rec = new CmsContributeRec();
		rec.setIntention(intention);
		rec.setTitle(title);
		service.save(bean, rec, files, images);
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("siteId", site.getId());
		Response resp = new Response(request, response, modelMap);
		resp.setStatus(1);
		return resp.post();
	}
	
	@RequestMapping(value = "/tg-islogin.htx")
	public void islogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long userId = webContext.getCurrentUserId();
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		if(user == null){
			Servlets.writeHtml(response, "0");
		}else{
			Servlets.writeHtml(response, "1");
		}
	}
	
	@RequestMapping(value = "/tougao/list.htx")
	public String list(Integer pageSize, Integer pageNumber, HttpServletRequest request, org.springframework.ui.Model modelMap) throws IOException {
		Site site = siteService.getWebCurrentSite();
		Long userId = webContext.getCurrentUserId();
		pageNumber = pageNumber==null?0:pageNumber-1;
		pageSize = pageSize == null?10:pageSize;
		PagerParam pageable = new PagerParam(pageNumber, pageSize, new Sort(Direction.DESC, "submitTime"));
		Page<CmsContributeRec> pagedList = recService.findList(new Long[]{userId}, pageable);
//		pagedList.getContent().size()
		modelMap.addAttribute("pagedList", pagedList);
		//ForeContext.setData(data, request);
		return site.getTemplate(TEMPLATE_LIST);
	}
	
	@RequestMapping(value = "/tougao/logs.htx")
	public void logs(Integer id, HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) throws IOException {
		CmsContributeRec rec = recService.get(id);
		List<WorkflowLog> logs =  flowLogService.findListByInfo(rec.getFid(), Info.WORKFLOW_TYPE, false);
		
		Map params = new HashMap();
		params.put("content", rec.getReplay());
		params.put("time", rec.getTimeStr());
		List<Map> list = new ArrayList<Map>();
		if(logs != null && logs.size() > 0){
			for(WorkflowLog log : logs){
				Map p = new HashMap();
				p.put("dateStr", log.getDateStr());
				p.put("timeStr", log.getTimeStr());
				p.put("from", log.getFrom());
				p.put("to", log.getTo());
				p.put("opinion", log.getOpinion());
				list.add(p);
			}
		}
		params.put("logs", list);
		
		JsonMapper mapper = new JsonMapper();
		Servlets.writeHtml(response, mapper.toJson(params));
	}
	
	@RequestMapping(value = "/tougao/update.htx")
	public String update(HttpServletRequest request, org.springframework.ui.Model modelMap) throws IOException {
		Site site = siteService.getWebCurrentSite();
		Long userId = webContext.getCurrentUserId();
		if(userId != null){
			CmsContribute contribute = service.findByUser(userId);
			if(contribute == null){
				contribute = new CmsContribute();
			}
			modelMap.addAttribute("contribute", contribute);
		}else{
			CmsContribute contribute = new CmsContribute();
			modelMap.addAttribute("contribute", contribute);
		}
		//ForeContext.setData(data, request);
		
		return site.getTemplate(TEMPLATE_SETTING);
	}
	
	@RequestMapping(value = "/tougao/bingding.htx")
	public String bingding(@ModelAttribute("bean")CmsContribute bean, HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) throws IOException {
		Long userId = webContext.getCurrentUserId();
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		bean.setUserId(userId);
		service.save(bean);
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("siteId", site.getId());
		Response resp = new Response(request, response, modelMap);
		resp.setStatus(1);
		return resp.post();
	}
	
	@RequestMapping(value = "/tougao/save_bingding.htx")
	public String save_bingding(CmsContribute bean, HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) throws IOException {
		Long userId = webContext.getCurrentUserId();
		SysUserEntity user = sysDataService.getUserInfoById(userId);
		if(bean.getId() == null){
			CmsContribute bean1 = service.findByUser(userId);
			if(bean1 != null){
				bean.setId(bean1.getId());
			}
		}
		bean.setUserId(userId);
		service.save(bean);
		Site site = siteService.getWebCurrentSite();
		modelMap.addAttribute("siteId", site.getId());
		Response resp = new Response(request, response, modelMap);
		resp.setStatus(1);
		return resp.post();
	}
	
	@ModelAttribute("bean")
	public CmsContribute preloadBean(@RequestParam(required = false) Integer oid) {
		return oid != null ? service.get(oid) : null;
	}
	
	@Autowired
	private CmsContributeService service;
	@Autowired
	private CmsContributeRecService recService;
	@Autowired
	private CmsContributeFileService fileService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SiteService siteService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WorkflowLogService flowLogService;
}
