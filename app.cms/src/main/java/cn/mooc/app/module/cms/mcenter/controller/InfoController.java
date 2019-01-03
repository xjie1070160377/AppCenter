package cn.mooc.app.module.cms.mcenter.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.service.TaskJobService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.CmsImageType;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.data.entity.InfoSource;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.PicDic;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.data.entity.WorkflowLog;
import cn.mooc.app.module.cms.model.InfoModel;
import cn.mooc.app.module.cms.service.AttributeService;
import cn.mooc.app.module.cms.service.CmsImageTypeService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.InfoSourceService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.WorkflowLogService;
import cn.mooc.app.module.cms.service.WorkflowStepService;
import cn.mooc.app.module.cms.support.Constants;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.DangshiUtils;
import cn.mooc.app.module.interact.service.InteractCommentService;
import cn.mooc.app.module.push.service.MsgPushService;
import cn.mooc.app.module.sys.model.UserRoleForm;

/**
 * InfoController 文档控制器
 * 
 * @author hwt
 * @date 2016-05-16
 */
@Controller
@RequestMapping("/cms/info")
public class InfoController extends CmsModuleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private InfoService infoService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private InfoSourceService sourceService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private MsgPushService pushListener;
	@Autowired
	private WorkflowStepService flowStepService;
	@Autowired
	private WorkflowLogService flowLogService;
	@Autowired
	private TaskJobService taskJobService;
	@Autowired
	private CmsImageTypeService cmsImageTypeService;
	
	@Value("${push.type}")
	private String pushType;
	@Value("${push.info.title}")
	private String pushInfoTitle;
	

	@RequestMapping("/infoList")
	public String infoList(Model modelMap, PagerParam pageParam,Integer queryNodeId, 
			Boolean showDescendants, String queryStatus, String columnFiled, String keyWord, Integer start) throws UnsupportedEncodingException {
		keyWord = StringUtils.isNotEmpty(keyWord) ? java.net.URLDecoder.decode(keyWord, "UTF-8") : "";
		if (showDescendants == null) {
			showDescendants = true;
		}
		Integer siteId = getCurrentSiteId();
		if (queryNodeId == null) {
			Node node = nodeService.findRoot(siteId);
			if (node != null) {
				queryNodeId = node.getId();
			}
		}
		if (StringUtils.isEmpty(queryStatus)) {
			queryStatus = Info.STATUS_ALL;
		}
		
		modelMap.addAttribute("columnFiled", columnFiled);
		modelMap.addAttribute("keyWord", keyWord);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("queryStatus", queryStatus);
		if(start == null){
			start = 1;
		}
		modelMap.addAttribute("start", start);
		return ModuleView("/info/infoList");
	}

	@RequestMapping(value = "/nodeTreeJson")
	@ResponseBody
	public List<Map<String, Object>> nodeTreeJson(Integer queryNodeId, Model model) {
		Integer siteId = getCurrentSiteId();
		Long userId = webContext.getCurrentUserId();
		List<Map<String, Object>> tree = nodeService.getNodesForJstree(siteId, queryNodeId, false, userId);
		return tree;
	}

	@RequestMapping("/infoListJson")
	@ResponseBody
	public Map<String, Object> infoListJson(Integer queryNodeId, Boolean showDescendants, String queryStatus, Integer start, Model model, PagerParam pageParam, String columnFiled, String keyWord) throws Exception {
		int siteId = getCurrentSiteId();
		Node node;
		if (queryNodeId == null) {
			node = nodeService.findRoot(siteId);
			if (node == null) {
				throw new Exception("请先增加站点栏目再进行文章管理");
			}
		} else {
			node = nodeService.getNodeById(queryNodeId);
		}
//		if(start != null){
//			pageParam.setPage(start);
//		}
		
		
		String treeNumber = null;
		if (showDescendants != null && showDescendants) {
			treeNumber = node.getTreeNumber();
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(columnFiled)){
			if(columnFiled.equals("NONE_author")){
				//查作者
				List<Long> users = new ArrayList<Long>();
				List<SysUserEntity> sysUsers = sysDataService.findUserByUsername(keyWord);
				for (SysUserEntity sysUser : sysUsers) {
					users.add(sysUser.getId());
				}
				if(users.size()>0){
					searchParams.put(SpecOperator.IN + "_creatorId", users);
				}else{
					searchParams.put(SpecOperator.LIKE + "_detail.author", keyWord);
				}
			}else{
				searchParams.put(columnFiled, keyWord);
			}
		}
		
		/* 临时给学员编辑设置 权限  */
		Long userId = this.webContext.getCurrentUserId();
		SysUserEntity userEntity = this.sysDataService.getUserInfoById(userId);
		logger.debug("userEntity.getUserName():"+userEntity.getUserName());
		for (SysRoleEntity roleEntity : userEntity.getRoles()) {
				 if(roleEntity.getId()==9&&userEntity.getRoles().size()<=1) {//
					 searchParams.put(SpecOperator.EQ + "_creatorId", userEntity.getId()); 
				 }
				break;
		}
		
		Page<InfoModel> pageData = infoService.findInfoModelPage(searchParams, pageParam, queryNodeId, treeNumber, queryStatus, siteId, userId);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/infoAdd")
	public String infoAdd(Integer id, Integer queryNodeId, Boolean showDescendants,
			String columnFiled, String keyWord,String queryStatus, Model modelMap) throws Exception {
		int siteId = getCurrentSiteId();
		keyWord = StringUtils.isNotEmpty(keyWord) ? java.net.URLDecoder.decode(keyWord, "UTF-8") : "";
		if (id != null) {
			Info bean = infoService.getInfoById(id);
			modelMap.addAttribute("bean", bean);
		} else {
			modelMap.addAttribute("bean", new Info());
		}
		Node node;
		if (queryNodeId == null) {
			node = nodeService.findRoot(siteId);
			if (node == null) {
				throw new Exception("请先增加站点栏目再进行文章管理");
			}
			queryNodeId = node.getId();
		} else {
			node = nodeService.getNodeById(queryNodeId);
		}
		Long userId = this.webContext.getCurrentUserId();
		//加载编辑器常用图片
		List<PicDic> picDics = infoService.getPicDicList();
		List<CmsImageType> imageTypes = cmsImageTypeService.getAllCmsImageTypes();
		modelMap.addAttribute("imageTypes", imageTypes);	
		modelMap.addAttribute("istoend", flowStepService.findNodeHasToEnd(queryNodeId, userId));
		List<InfoSource> sourceList = sourceService.findList();
		CmsModel model = node.getInfoModel();
		List<Attribute> attrList = attributeService.findBySiteId(siteId);
		modelMap.addAttribute("sourceList", sourceList);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("node2", new Node());
		modelMap.addAttribute("attrList", attrList);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute(Constants.OPRT, Constants.CREATE);
		modelMap.addAttribute("SYS_ADMIN_PATH", siteService.getSYS_ADMIN_PATH());
		modelMap.addAttribute("ctxPath", siteService.getCtxPath());
		modelMap.addAttribute("picDics", picDics);
		modelMap.addAttribute("columnFiled", columnFiled);
		modelMap.addAttribute("keyWord", keyWord);
		
		//return ModuleView("/info/info_form");		
		return ModuleView("/info/info_form_h5");
				
	}

	@RequestMapping("/infoEdit")
	public String infoEdit(Integer id, Integer queryNodeId, String columnFiled, String keyWord,
			Boolean showDescendants, String queryStatus, Integer start, Integer eMode, Model modelMap) throws Exception {
		int siteId = getCurrentSiteId();
		keyWord = StringUtils.isNotEmpty(keyWord) ? java.net.URLDecoder.decode(keyWord, "UTF-8") : "";
		Info bean = infoService.getInfoById(id);
		List<Node> node2s=null;
			if(bean.getP1()!=null) {
				//获取第二栏目
				 node2s = nodeService.findByIds(new Integer[] {bean.getP1()});
			}
		
		
		Node node = bean.getNode();
		CmsModel model = bean.getModel();
		List<Attribute> attrList = attributeService.findBySiteId(siteId);

		List<Special> specials = bean.getSpecials();
		String specialIds = "";
		String specialNames = "";
		for (Special special : specials) {
			specialIds += (StringUtils.isEmpty(specialIds) ? "" : ",") + special.getId();
			specialNames += (StringUtils.isEmpty(specialNames) ? "" : ",") + special.getTitle();
		}

		List<InfoSource> sourceList = sourceService.findList();
//		List<InfoCustom> infoCustoms = bean.getInfoCustoms();
		Map<String, String> customs = bean.getCustomers();

		if (bean.getImages() != null && !bean.getImages().isEmpty()) {
			List list = new ArrayList();
			for (InfoImage image : bean.getImages()) {
				Map map = new HashMap();
				map.put("note", image.getText());
				map.put("url", image.getImage());
				map.put("fileName", image.getName());
				list.add(map);
			}
			String imgListJson = JsonUtil.toJson(list);
			imgListJson = imgListJson.replaceAll("\"", "'");
			modelMap.addAttribute("imgListJson", imgListJson);
		}
		Long userId = this.webContext.getCurrentUserId();
		
		//加载编辑器常用图片
		List<PicDic> picDics = infoService.getPicDicList();
		List<CmsImageType> imageTypes = cmsImageTypeService.getAllCmsImageTypes();
		modelMap.addAttribute("imageTypes", imageTypes);	
		modelMap.addAttribute("istoend", flowStepService.istoend(bean.getId(), userId));
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("start", start);
		modelMap.addAttribute("customs", customs);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("node", node);
		if(node2s!=null&&node2s.size()>0) {
			modelMap.addAttribute("node2", node2s.get(0));
		}else {
			modelMap.addAttribute("node2", new Node());
		}
		
		modelMap.addAttribute("attrList", attrList);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute("sourceList", sourceList);
		modelMap.addAttribute("specialIds", specialIds);
		modelMap.addAttribute("specialNames", specialNames);
		modelMap.addAttribute(Constants.OPRT, Constants.EDIT);
		modelMap.addAttribute("SYS_ADMIN_PATH", siteService.getSYS_ADMIN_PATH());
		modelMap.addAttribute("ctxPath", siteService.getCtxPath());
		modelMap.addAttribute("picDics", picDics);
		modelMap.addAttribute("columnFiled", columnFiled);
		modelMap.addAttribute("keyWord", keyWord);
	
		if(eMode==null || eMode.equals(0)) {
			return ModuleView("/info/info_form");	
		} else {
			return ModuleView("/info/info_form_h5");
		}
		
	}

	@RequestMapping("/infoView")
	public String infoView(Integer id, Integer queryNodeId, Boolean showDescendants, String queryStatus, Model modelMap) throws Exception {
		Info bean = infoService.getInfoById(id);
		SysUserEntity creator = sysDataService.getUserInfoById(bean.getCreatorId());
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		InteractCommentService interactCommentService = (InteractCommentService)webApplicationContext.getBean(InteractCommentService.class);
		Integer siteId = siteService.getCurrentSite().getId();
		if(siteId == null){
			siteId = 1;
		}
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("creator", creator);
		modelMap.addAttribute("queryNodeId", queryNodeId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute("queryStatus", queryStatus);
		modelMap.addAttribute("siteId", siteId);
		return ModuleView("/info/info_view");
	}

	@RequestMapping(value = "/infoSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoSave(Integer queryNodeId, Boolean showDescendants, String queryStatus, Info entity, InfoDetail detail, String specialIds, Integer[] attrIds, Integer nodeId,
			String tagKeywords, @RequestParam(defaultValue = "false") boolean draft, String imageListJson, String sourceId, String sourceother, HttpServletRequest request) throws Exception {
		
		if(StringUtils.isBlank(detail.getTitle())){
			return HttpResponseUtil.failureJson("请填写文章标题");
		}
		
		Integer siteId = getCurrentSiteId();
		Long userId = this.webContext.getCurrentUserId();

		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		Map<String, String> clobs = Servlets.getParameterMap(request, "clobs_");

		if (StringUtils.isBlank(detail.getMetaDescription())) {
			String title = detail.getTitle();
			detail.setMetaDescription(Info.getDescription(clobs, title));
		}
		if (!StringUtils.isEmpty(sourceId)) {
			detail.setInfoSource(new InfoSource(Integer.parseInt(sourceId)));
		} else {
			detail.setInfoSource(null);
		}
		String[] tagNames = splitTagKeywords(tagKeywords, request);
		Map<String, String> attrImages = Servlets.getParameterMap(request, "attrImages_");
		for (Map.Entry<String, String> entry : attrImages.entrySet()) {
			entry.setValue(StringUtils.trimToNull(entry.getValue()));
		}

		List<InfoImage> images = new ArrayList<InfoImage>();
		if (StringUtils.isNotEmpty(imageListJson)) {
			List imageList = JsonUtil.fromJson(imageListJson, List.class);
			InfoImage infoImage;
			for (int i = 0; i < imageList.size(); i++) {
				Map imageMap = (Map) imageList.get(i);
				String name = "" + imageMap.get("fileName");
				String url = "" + imageMap.get("url");
				String note = "" + imageMap.get("note");
				if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(note) || StringUtils.isNotBlank(url)) {
					infoImage = new InfoImage(name, note, url);
					images.add(infoImage);
				}

			}
		}
		String status = draft ? Info.DRAFT : null;
		if(!StringUtils.isEmpty(entity.getText())) {
			entity.setText(checkHtml(entity.getText()));	
		}
		
		try {
			infoService.saveInfo(entity, detail, specialIds, customs, clobs, images, attrIds, attrImages, tagNames, nodeId, userId, status, siteId);
			logger.info("save Info, title={}.", entity.getTitle());
			
			String autoLogo = request.getParameter("autoLogo");
			this.checkAutoLogo(detail, autoLogo);
			
			webContext.sysUserLog(LogType.UserOpr, "保存文章：" + entity.getTitle() +" Node：" + nodeId);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("新增失败");
		}

		return HttpResponseUtil.successJson(entity.getId()+"");
	}
	public String checkHtml(String html) {
		return html.replace("crossorigin=\"anonymous\"", "");
	}

	@RequestMapping(value = "/infoUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoUpdate(Integer queryNodeId, Boolean showDescendants, String queryStatus, @ModelAttribute("entity") Info entity, @ModelAttribute("detail") InfoDetail detail,
			String specialIds, Integer[] attrIds, Integer nodeId, String tagKeywords, Integer sourceFlag, String imageListJson, String sourceId, String sourceother,
			@RequestParam(defaultValue = "false") boolean pass, @RequestParam(defaultValue = "false") boolean recall, HttpServletRequest request) throws Exception {
		
		if(StringUtils.isBlank(detail.getTitle())){
			return HttpResponseUtil.failureJson("文章标题不能是空的");
		}
		
		Long userId = this.webContext.getCurrentUserId();
		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		Map<String, String> clobs = Servlets.getParameterMap(request, "clobs_");
		if (!StringUtils.isEmpty(sourceId)) {
			detail.setInfoSource(new InfoSource(Integer.parseInt(sourceId)));
		} else {
			detail.setInfoSource(null);
		}
		detail.setSourceFlag(sourceFlag);
		String[] tagNames = splitTagKeywords(tagKeywords, request);
		Map<String, String> attrImages = Servlets.getParameterMap(request, "attrImages_");
		for (Map.Entry<String, String> entry : attrImages.entrySet()) {
			entry.setValue(StringUtils.trimToNull(entry.getValue()));
		}
		List<InfoImage> images = new ArrayList<InfoImage>();
		if (StringUtils.isNotEmpty(imageListJson) && !"{}".equals(imageListJson)) {
			List imageList = JsonUtil.fromJson(imageListJson, List.class);
			InfoImage infoImage;
			for (int i = 0; i < imageList.size(); i++) {
				Map imageMap = (Map) imageList.get(i);
				String name = "" + imageMap.get("fileName");
				String url = "" + imageMap.get("url");
				String note = "" + imageMap.get("note");
				if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(note) || StringUtils.isNotBlank(url)) {
					infoImage = new InfoImage(name, note, url);
					images.add(infoImage);
				}
			}
		}
		if (attrIds == null) {
			attrIds = new Integer[0];
		}
		try {
			infoService.updateInfo(entity, detail, specialIds, customs, clobs, images, attrIds, attrImages, tagNames, nodeId, userId, pass, recall);
			logger.info("update Info, title={}.", entity.getTitle());
			
			String autoLogo = request.getParameter("autoLogo");
			this.checkAutoLogo(detail, autoLogo);
			
			webContext.sysUserLog(LogType.UserOpr, "修改文章：" + entity.getTitle() +" Node：" + nodeId);
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson(entity.getId()+"");
	}
	
	private void checkAutoLogo(InfoDetail detail, String autoLogo) throws Exception{
		logger.debug("checkAutoLogo：{}", detail.getVideo());
		if(StringUtils.isNotEmpty(detail.getVideo()) && detail.getVideo().endsWith(".mp4")){
			//如果上传的是mp4视频，则自动转码			
			if(StringUtils.isNotBlank(autoLogo) && autoLogo.equals("1")){
				taskJobService.postVideoJob(detail.getVideoName(), detail.getVideo());						
			}
			
		}
	}

	@RequestMapping(value = "/infoDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoDel(Integer id) {
		// 删除
		try {
			infoService.delInfo(id);
			logger.info("delete Info, id={}.", id);
			webContext.sysUserLog(LogType.UserOpr, "删除文章：" + id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/infoDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoDels(Integer[] ids) {
		// 删除
		try {
			infoService.delInfos(ids);
			logger.info("deletes Info, ids={}.", ids);
			webContext.sysUserLog(LogType.UserOpr, "删除文章：" + ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/infoCancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoCancel(Integer id) {
		// 删除
		try {
			infoService.cancelInfo(id);
			logger.info("cancel Info, id={}.", id);
			webContext.sysUserLog(LogType.UserOpr, "删除文章：" + id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/hasWorkflow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> hasWorkflow(Integer id) {
		// 删除
		try {
			Info info = infoService.getInfoById(id);
			Node node = info.getNode();
			if(node != null){
				Workflow flow = node.getWorkflow();
				if(flow != null){
					return HttpResponseUtil.successJson();
				}
			}
			return HttpResponseUtil.failureJson("");
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/hasToEnd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> hasToEnd(Integer nodeId) {
		// 删除
		try {
			Long userId = this.webContext.getCurrentUserId();
			boolean f = flowStepService.findNodeHasToEnd(nodeId, userId);
			
			if(f){
				return HttpResponseUtil.successJson();
			}else{
				return HttpResponseUtil.failureJson("");
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> transfer(Integer[] infoId, Integer nodeId) {
		// 删除
		try {
			infoService.moveNode(infoId, nodeId);
			return HttpResponseUtil.successJson();
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
	}

	@RequestMapping(value = "/infoCancels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoCancels(Integer[] ids) {
		// 删除
		try {
			infoService.cancelInfos(ids);
			logger.info("cancels Info, ids={}.", ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/infoPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> infoPass(Integer[] ids) {
		long userId = this.webContext.getCurrentUserId();
		
		if(ids==null || ids.length==0){
			return HttpResponseUtil.failureJson("请选择要发布的文章");
		}
		//批量发布
		try {
			infoService.passInfos(ids, userId);
			logger.info("pass Info, ids={}.", ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("/audit_pass")
	public String auditPass(Integer[] ids, String opinion, Integer position, Integer queryNodeId, Integer queryNodeType, Integer queryInfoPermType,
			String queryStatus, Integer start, String redirect, HttpServletRequest request, RedirectAttributes ra) {
		Long userId = this.webContext.getCurrentUserId();
		List<Info> beans = infoService.pass(ids, userId, opinion);
		
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addAttribute("start", start);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:infoEdit";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:infoView";
		} else {
			return "redirect:infoList";
		}
	}
	
	@RequestMapping("/toend")
	public String toend(Integer id, Integer position, Integer queryNodeId, Integer queryNodeType, Integer queryInfoPermType,Integer start,
			String queryStatus, String redirect, HttpServletRequest request, RedirectAttributes ra) throws Exception {
		Long userId = this.webContext.getCurrentUserId();
		Info info = infoService.toEnd(id, userId);
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addAttribute("start", start);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", id);
			ra.addAttribute("position", position);
			return "redirect:infoEdit";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", id);
			ra.addAttribute("position", position);
			return "redirect:infoView";
		} else {
			return "redirect:infoList";
		}
	}

	@RequestMapping("/audit_reject")
	public String auditReject(Integer[] ids, String opinion, Integer position, Integer queryNodeId, Integer queryNodeType, Integer queryInfoPermType,
			String queryStatus, String redirect, HttpServletRequest request, RedirectAttributes ra) {
		Long userId = this.webContext.getCurrentUserId();
		LoginUserInfo user = webContext.getCurrentUserInfo();
		List<Info> beans = infoService.reject(ids, userId, opinion, false, user.getUserId(), user.getUserName(), ValidatorUtil.getIpAddr(request), request.getRequestURI());
		
//		String ip = Servlets.getRemoteAddr(request);
//		for (Info bean : beans) {
//			logService.operation("info.auditReject", bean.getTitle(), null, bean.getId(), ip, userId, siteId);
//			logger.info("audit reject Info, title={}.", bean.getTitle());
//		}
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
//		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		
//		String specialId = Servlets.getParameter(request, SPECIALID);
//		ra.addAttribute(SPECIALID, specialId);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:infoEdit";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:infoView";
		} else {
			return "redirect:infoList";
		}
	}
	@RequestMapping("/audit_return")
	public String auditReturn(Integer[] ids, String opinion, Integer position, Integer queryNodeId, Integer queryNodeType, Integer queryInfoPermType, Integer start,
			String queryStatus, String redirect, HttpServletRequest request, RedirectAttributes ra) {
		try {
			opinion = URLDecoder.decode(opinion, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Long userId = this.webContext.getCurrentUserId();
		LoginUserInfo user = webContext.getCurrentUserInfo();
		List<Info> beans = infoService.reject(ids, userId, opinion, false, user.getUserId(), user.getUserName(), ValidatorUtil.getIpAddr(request), request.getRequestURI());
//		for (Info bean : beans) {
//			logService.operation("info.auditReturn", bean.getTitle(), null, bean.getId(), ip, userId, siteId);
//			logger.info("audit return Info, title={}.", bean.getTitle());
//		}
		ra.addAttribute("queryNodeId", queryNodeId);
		ra.addAttribute("queryNodeType", queryNodeType);
		ra.addAttribute("queryInfoPermType", queryInfoPermType);
		ra.addAttribute("queryStatus", queryStatus);
		ra.addAttribute("start", start);
//		ra.addFlashAttribute(MESSAGE, OPERATION_SUCCESS);
		
//		String specialId = Servlets.getParameter(request, SPECIALID);
//		ra.addAttribute(SPECIALID, specialId);
		if (Constants.REDIRECT_EDIT.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:infoEdit";
		} else if (Constants.REDIRECT_VIEW.equals(redirect)) {
			ra.addAttribute("id", beans.iterator().next().getId());
			ra.addAttribute("position", position);
			return "redirect:infoView";
		} else {
			return "redirect:infoList";
		}
	}
	
	@RequestMapping("pushInfo")
	public void pushInfo(Integer id, Integer flag, HttpServletRequest resq, HttpServletResponse resp) {
		
		Info info = infoService.getInfoById(id);
		String showType = "";
		if (info.getImages() != null && !info.getImages().isEmpty()) {
			showType = "1";
		}else if(info.getWithVideo() != null && info.getWithVideo()){
			showType = "2";
		}else{
			showType = "0";
		}
		LoginUserInfo user = webContext.getCurrentUserInfo();
		pushListener.pushtest(pushInfoTitle, pushType, showType, info.getTitle(), info.getId()+"", info.getSite().getId(), flag, user.getUserId(), user.getUserName(), ValidatorUtil.getIpAddr(resq), resq.getRequestURI());
		//pushListener.pushtest(info, flag);
		Servlets.writeHtml(resp, "");
	}
	
	@RequestMapping("hasLog")
	public void hasLog(Integer id, HttpServletRequest resq, HttpServletResponse resp) {
		List<WorkflowLog> flowLogs = flowLogService.findListByInfo(id, Info.WORKFLOW_TYPE, false);
		if(flowLogs != null && flowLogs.size() > 0){
			Servlets.writeHtml(resp, "1");
		}else{
			Servlets.writeHtml(resp, "");
		}
	}
	
	@RequestMapping("flowLog")
	public String flowLog(Integer id, HttpServletRequest resq, HttpServletResponse resp, Model modelMap) {
		List<WorkflowLog> flowLogs = flowLogService.findListByInfo(id, Info.WORKFLOW_TYPE, false);
		List<Map> logList = new ArrayList<Map>();
		if(flowLogs != null && flowLogs.size() > 0){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(WorkflowLog flowLog : flowLogs){
				Map params =new HashMap();
				SysUserInfo sysUser = webContext.getSysUserFullInfo(flowLog.getUserId());
				String userName = sysUser == null ? "无" : sysUser.getUName();
				String from = flowLog.getFrom();
				params.put("from", from + " ("+userName+")");
				params.put("to", flowLog.getTo());
				params.put("time", format.format(flowLog.getCreationDate()));
				params.put("opinion", flowLog.getOpinion());
				logList.add(params);
			}
			modelMap.addAttribute("logList", logList);
		}
		return ModuleView("/info/info_workflow");
	}
	@ModelAttribute
	public void preloadBean(@RequestParam(required = false) Integer oid, org.springframework.ui.Model modelMap) {
		if (oid != null) {
			Info bean = infoService.getInfoById(oid);
			if (bean != null) {
				modelMap.addAttribute("entity", bean);
				InfoDetail detail = bean.getDetail();
				if (detail == null) {
					detail = new InfoDetail();
					detail.setId(oid);
					detail.setInfo(bean);
				}
				modelMap.addAttribute("detail", detail);
			}
		}
	}
	
	private String[] splitTagKeywords(String tagKeywords, HttpServletRequest request) {
		Locale locale = RequestContextUtils.getLocale(request);
		String split = messageSource.getMessage("info.tagKeywordsSplit", null, locale);
		if (StringUtils.isNotBlank(split)) {
			tagKeywords = StringUtils.replace(tagKeywords, split, ",");
		}
		return StringUtils.split(tagKeywords, ',');
	}
	
	@RequestMapping("sd_date")
	public String editDSDate( HttpServletResponse resp, Model modelMap) { 
		Date date = new  Date();
		SimpleDateFormat formate= new SimpleDateFormat("yyyy-MM-dd");
		modelMap.addAttribute("date",formate.format(date));
		return ModuleView("/info/info_dangshi");
	}
	@RequestMapping("edite_ds")
	public String editDS(String data,HttpServletResponse resp,Model modelMap) throws Exception {
		int siteId = getCurrentSiteId();
		Info info = DangshiUtils.getHtmlRes(data);
		info.setPublishDate(new Date());
		info.setP4(4);//设置大图展示
		info.setP6(1);//校内可见
		LoginUserInfo user = webContext.getCurrentUserInfo();
		info.getDetail().setAuthor(user.getNickName()+" 新闻文化室");
		modelMap.addAttribute("bean",info );
		//绑定专题信息
		modelMap.addAttribute("specialIds", "94");
		modelMap.addAttribute("specialNames", "党史上的今天");
		
		Node node = nodeService.getNodeById(52);//固定信息港
		//加载编辑器常用图片
		List<PicDic> picDics = infoService.getPicDicList();
		List<CmsImageType> imageTypes = cmsImageTypeService.getAllCmsImageTypes();
		modelMap.addAttribute("imageTypes", imageTypes);	
		List<InfoSource> sourceList = sourceService.findList();
		CmsModel model = node.getInfoModel();
		List<Attribute> attrList = attributeService.findBySiteId(siteId);
		modelMap.addAttribute("sourceList", sourceList);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("node2", node);
		modelMap.addAttribute("attrList", attrList);
		modelMap.addAttribute(Constants.OPRT, Constants.EDIT);
		modelMap.addAttribute("SYS_ADMIN_PATH", siteService.getSYS_ADMIN_PATH());
		modelMap.addAttribute("ctxPath", siteService.getCtxPath());
		modelMap.addAttribute("picDics", picDics);
		
		return ModuleView("/info/info_form");		
		//return ModuleView("/info/info_form_h5");
				
	}
}