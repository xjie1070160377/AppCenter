package cn.mooc.app.module.service.web.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UrlPathHelper;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysOrg;
import cn.mooc.app.core.service.SysOrgService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.module.service.data.entity.AppService;
import cn.mooc.app.module.service.data.entity.AppServicetype;
import cn.mooc.app.module.service.data.entity.ServInfo;
import cn.mooc.app.module.service.data.entity.ServInfoDetail;
import cn.mooc.app.module.service.data.entity.ServMsg;
import cn.mooc.app.module.service.data.entity.ServMsgInfo;
import cn.mooc.app.module.service.model.MobileAppServConvert;
import cn.mooc.app.module.service.model.MobileAppServSelfMenu;
import cn.mooc.app.module.service.model.MobileServMsg;
import cn.mooc.app.module.service.service.AppServiceService;
import cn.mooc.app.module.service.service.AppServicetypeService;
import cn.mooc.app.module.service.service.ServMsgService;
import cn.mooc.app.module.service.service.ServSelfmenuService;
/**
 * PublicPlatformController 公共平台控制器
 * @author zjj
 *
 */
@RequestMapping("/mobile/pp")
@Controller
public class PublicPlatformController extends PserviceController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebContext context;
	@Autowired
	private AppServicetypeService typeService;
	@Autowired
	private SysOrgService orgService;
	@Autowired
	private AppServiceService service;
	@Autowired
	private ServMsgService servMsgService;
	@Autowired
	private ServSelfmenuService servSelfmenuService;

	@RequestMapping(value = "/create.htx")
	public String create(Model model, HttpServletRequest request) {
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		
		List<AppServicetype> typeList = typeService.getAllAppServicetypes();
		model.addAttribute("typeList", typeList);
		List<SysOrg> orgList = orgService.findList();
		model.addAttribute("orgList", orgList);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		
		
		AppService appService = service.findByUserId(userId);
		
		if(appService != null){
			if(appService.getOrgId() != null){
				SysOrg org = orgService.get(appService.getOrgId());
				if(org != null){
					model.addAttribute("orgName", org.getName());
				}
			}
			
			model.addAttribute("appService", appService);
			if(appService.getIsFree() != null && appService.getIsFree()==1){
				if(appService.getIsChecked() != null && appService.getIsChecked()==1){
					return "redirect:notice.htx";
				}else{
					//template = "publicPlatformView.html";
				}
			}
		}else{
			model.addAttribute("appService", appService);
		}
		
		return super.getTemplate("publicPlatformCreate");
	}
	
	@RequestMapping(value = "/setting.htx")
	public String serviceSetting(Model model, HttpServletRequest request) {
		
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.findByUserId(userId);
		
		if(appService != null){
			model.addAttribute("appService", appService);
			if(appService.getIsFree() != null && appService.getIsFree()==1){
				if(appService.getIsChecked() != null && appService.getIsChecked()==1){
				}else{
					return "redirect:create.htx";
				}
			}
		}else{
			return "redirect:create.htx";
		}
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		return super.getTemplate("publicPlatform_setting");
	}
	
	@RequestMapping(value = "/update.htx")
	public String update(@ModelAttribute("bean") AppService bean, String redirect, RedirectAttributes ra, HttpServletRequest request) throws Exception {
		bean.setCreateTime(new Timestamp(new Date().getTime()));
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		bean.setCreaterUserid(userId);
		bean.setIsFree(1);
		bean.setIsChecked(0);
		service.updateAppService(bean);
//		logger.info("update AppService, title={}.", bean.getName());
		ra.addFlashAttribute(MESSAGE, SAVE_SUCCESS);
		logger.debug("update app service info");
		return "redirect:create.htx";
	}
	
	@RequestMapping(value = "/infoForm.htx")
	public String infoForm(Integer msgId, HttpServletRequest request, Model model) {
		
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.findByUserId(userId);
		model.addAttribute("appService", appService);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		if(msgId != null){
			ServMsg servMsg = servMsgService.getServMsgById(msgId);
			model.addAttribute("servMsg", servMsg);
			List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
			if(servMsg.getMsgInfos() != null){
				Iterator<ServMsgInfo> iterator = servMsg.getMsgInfos().iterator();
				while (iterator.hasNext()) {
					ServMsgInfo info = iterator.next();
					Map<Object, Object> map = new HashMap<Object, Object>();
					map.put("title", info.getInfo().getDetail().getFullTitle());
					map.put("author", info.getInfo().getDetail().getAuthor());
					map.put("img", info.getInfo().getDetail().getSmallImage());
					map.put("html", info.getInfo().getDetail().getHtml()==null?"":info.getInfo().getDetail().getHtml());
					map.put("intro", info.getInfo().getDetail().getMetaDescription());
					map.put("infoid", info.getId().getInfoId());
					map.put("index", info.getIndex());
					
					model.addAttribute("map", map);		
					list.add(map);
					
				}
			}
			model.addAttribute("servMsgList", list);
			model.addAttribute("servMsgListSize", list.size());
		}
		model.addAttribute("servMsgList0", "title is ");		
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		return super.getTemplate("publicPlatformInfoForm");
	}
	
	@RequestMapping(value = "/delMsg.htx")
	public void delMsg(Integer msgid, HttpServletRequest request, HttpServletResponse response){
		try {
			ServMsg msg = servMsgService.getServMsgById(msgid);
			msg.setMsgstate(ServMsg.MSGSTATE_CANCEL);
			servMsgService.save(msg);
			HttpResponseUtil.successJson("删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			HttpResponseUtil.failureJson("删除失败");
		}
		
		
	}

	@RequestMapping(value = "/saveMsg.htx")
	public String saveMsg(Integer serviceId, Integer msgid, String details,Integer state, HttpServletRequest request, RedirectAttributes ra) throws Exception{
		Long userId = context.getCurrentUserId();
		AppService appService = service.getAppServiceById(serviceId);
		Integer orgId = appService.getOrgId();;
		ServMsg servMsg = new ServMsg();
		servMsg.setMsgid(msgid);
		servMsg.setAppService(appService);
		servMsg.setMsgtype(ServMsg.MSGTYPE_IMGTEXT);
		servMsg.setCreatetime(new Timestamp(new Date().getTime()));
		servMsg.setUserId(userId);
		if(new Integer(1).equals(state)){
			servMsg.setMsgstate(ServMsg.MSGSTATE_DRAFT);
		}else{
			servMsg.setMsgstate(ServMsg.MSGSTATE_SENDING);
			servMsg.setPushtime(new Timestamp(new Date().getTime()));
		}
		
		List<ServMsgInfo> rsList = MobileAppServConvert.convertListMap2ServInfoList(userId, "", appService, orgId, servMsg, details);
		if(rsList.size() > 1){
			servMsg.setMsgtype(ServMsg.MSGTYPE_IMGTEXTMORE);
		}
		servMsgService.save(servMsg, rsList);
		if(new Integer(1).equals(state)){
			ra.addAttribute("msgId", servMsg.getMsgid());
			return "redirect:infoForm.htx";
		}
		return "redirect:sendMsgList.htx";
	}
	
	@RequestMapping(value = "/updateInfo.htx")
	public String updateInfo(@ModelAttribute("bean") ServInfoDetail bean,Integer serviceId, Integer orgId, String image, String redirect, RedirectAttributes ra, HttpServletRequest request) throws Exception {
//		Site site = Context.getCurrentSite(request);
//		User user = Context.getCurrentUser(request);
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.getAppServiceById(serviceId);
		
		ServInfo info = new ServInfo();
		info.setPublishDate(new Timestamp(new Date().getTime()));
		info.setUserid(userId);
		info.setOrgId(orgId);
//		info.setFSiteId(site.getId());
		info.setComments(0);
		info.setDownloads(0);
		info.setDiggs(0);
		info.setPriority(0);
		info.setViews(0);
		info.setScore(0);
		info.setIsWithImage(false);
		info.setStatus("A");
		info.setDetail(bean);
		info.setHtmlStatus("A");
		info.setAppService(appService);
		
		bean.setIsEm("A");
		bean.setIsStrong("A");
		bean.setTitle("A");
		bean.setSmallImage(image);
		bean.setInfo(info);
		
		servMsgService.save(userId, info, appService);
		return "redirect:sendMsgList.htx";
	}
	@RequestMapping(value = "/draftMsgList.htx")
	public String draftMsgList(Integer page, HttpServletRequest request, Model model) {
		page = page == null ? 1 : page;
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.findByUserId(userId);
		
		String url = getCurrentUrl(request);
		model.addAttribute("url", url);
		model.addAttribute("appService", appService);
		model.addAttribute("page", page);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());

		return super.getTemplate("publicPlatformInfo_draftMsgList");
	}
	
	@RequestMapping(value = "/sendMsgList.htx")
	public String sendMsgList(Integer page, HttpServletRequest request, Model model) {
		page = page == null ? 1 : page;
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.findByUserId(userId);
		
		model.addAttribute("appService", appService);
		model.addAttribute("page", page);
		String url = getCurrentUrl(request);
		model.addAttribute("url", url);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());

		return super.getTemplate("publicPlatformInfo_sendMsgList");
	}
	
	@RequestMapping(value = "/users.htx")
	public String usersList(Integer page, HttpServletRequest request, Model model) {
		page = page == null ? 1 : page;
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.findByUserId(userId);
		
		model.addAttribute("appService", appService);
		model.addAttribute("page", page);
		String url = getCurrentUrl(request);
		model.addAttribute("url", url);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		return super.getTemplate("publicPlatform_users");
	}
	@RequestMapping(value = "/editHeadImg.htx")
	public String editHeadImg(Integer serviceId, String src, String name, HttpServletRequest request, Model model) {
		AppService appService = service.getAppServiceById(serviceId);
		
		model.addAttribute("appService", appService);
		model.addAttribute("src", src);
		model.addAttribute("name", name);
		return super.getTemplate("publicPlatform_settingHeadImg");
	}
	@RequestMapping(value = "/editIntro.htx")
	public String editIntro(Integer serviceId, HttpServletRequest request, Model model) {
		AppService appService = service.getAppServiceById(serviceId);
		
		model.addAttribute("appService", appService);
		return super.getTemplate("publicPlatform_editIntro");
	}
	@RequestMapping(value="/saveIntro.htx")
	public String saveIntro(Integer serviceId, String intro, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		AppService appService = service.getAppServiceById(serviceId);
		appService.setIntro(intro);
		service.updateAppService(appService);
		return super.getTemplate("publicPlatform_changeService");
	}
	
	@RequestMapping(value = "/img_crop.htx")
	public String imgCrop(Integer serviceId, String src, Integer top, Integer left,Integer width, Integer height, Integer targetWidth, Integer targetHeight, String fileName,
			HttpServletRequest request, Model model)
			throws IOException {
		Long userId = context.getCurrentUserId();
//		
//		
//		context.uploadFile(multipartFile, imageParam)
//		
//		
//		PublishPoint point = site.getUploadsPublishPoint();
//		String urlPrefix = point.getUrlPrefix();
//		FileHandler fileHandler = point.getFileHandler(pathResolver);
//
//		if (!src.startsWith(urlPrefix)) {
//			return null;
//		}
//		String id = src.substring(urlPrefix.length());
//		String extension = FilenameUtils.getExtension(id);
//		String formatName = fileHandler.getFormatName(id);
//		if (formatName == null) {
//			return null;
//		}
//
//		BufferedImage buff = fileHandler.readImage(id);
//		buff = Scalr.crop(buff, left, top, width, height);
//		if (targetWidth < width || targetHeight < height) {
//			buff = Scalr.resize(buff, Scalr.Method.QUALITY, targetWidth,
//					targetHeight);
//		}
//
//		String ip = Servlets.getRemoteAddr(request);
//		
//		String url = uploadHandler.storeImage(buff, extension, formatName,
//				site, ip, userId);
//		fileHandler.delete(id);
//		
//		AppService appService = service.get(serviceId);
//		appService.setHeadImg(url);
//		service.save(appService);
//		
//		modelMap.addAttribute("name", fileName);
//		modelMap.addAttribute("imgUrl", url);
//		modelMap.addAttribute("urlName", "smallImageUrl");
//		Map<String, Object> data = modelMap.asMap();
//		ForeContext.setData(data, request);
//		return site.getTemplate("img_crop.html", mobileTheme);
		return "";
	}
	@RequestMapping(value = "/selfmenu.htx")
	public String selfmenu(HttpServletRequest request, Model model) {
		

		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.findByUserId(userId);
		model.addAttribute("appService", appService);
		List<MobileAppServSelfMenu> list = servSelfmenuService.findMobileAppServSelfMenu(appService.getServiceId());
		model.addAttribute("selfmenuList", list);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		return super.getTemplate("publicPlatform_selfmenu");
	}
	
	@RequestMapping(value = "/saveSelfmenu.htx")
	public String saveSelfmenu(Integer serviceId, String details,HttpServletRequest request, RedirectAttributes ra){
//		Site site = Context.getCurrentSite(request);
//		User user = Context.getCurrentUser(request);
//		AppService appService = service.get(serviceId);
		servSelfmenuService.update(serviceId, details);
		
		return "redirect:selfmenu.htx";
	}
	
	@RequestMapping(value = "/notice.htx")
	public String notice(HttpServletRequest request, Model model) {
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		AppService appService = service.findByUserId(userId);
		
		
		model.addAttribute("appService", appService);
		return super.getTemplate("publicPlatform_notice");
	}
	@RequestMapping(value = "/chooseServMsg.htx")
	public String chooseServMsg(Integer serviceId, Integer page, HttpServletRequest request, Model model) {
		page = page == null ? 1 : page;
		Long userId = context.getCurrentUserId();
		if(userId == null){
			return "redirect:/";
		}
		AppService appService = service.getAppServiceById(serviceId);
		
		model.addAttribute("page", page);
		String url = getCurrentUrl(request);
		model.addAttribute("url", url);
		model.addAttribute("ctxPath", context.getServletContext().getContextPath());
		
		model.addAttribute("appService", appService);
		return super.getTemplate("publicPlatform_chooseServMsg");
	}
	@RequestMapping(value = "/testSelfMenu.htx")
	public String testSelfMenu(HttpServletRequest request, Model model) {
		return super.getTemplate("testSelfMenu");
	}
	@RequestMapping(value = "/selectedMsgById-{id}.htx")
	@ResponseBody
	public Map<String, Object> selectedMsgById(@PathVariable Integer id,HttpServletRequest request, HttpServletResponse response){
		ServMsg msg = servMsgService.getServMsgById(id);
		MobileServMsg mobileServMsg = MobileAppServConvert.convertMsg("", msg);
		return HttpResponseUtil.resMapJson(true, mobileServMsg);
	}
	
	public static String getCurrentUrl(HttpServletRequest request) {
		UrlPathHelper urlPath = new UrlPathHelper();
		String uri = urlPath.getOriginatingRequestUri(request);
		String queryString = urlPath.getOriginatingQueryString(request);
		if (StringUtils.isNotBlank(queryString)) {
			uri += "?" + queryString;
		}
		return uri;
	}
}
