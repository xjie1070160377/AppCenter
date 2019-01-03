package cn.mooc.app.module.api.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.JsonObject;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.model.ImageParam;
import cn.mooc.app.core.model.UploadResult;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.api.model.MobileListData;
import cn.mooc.app.module.api.model.MobileReadilyTake;
import cn.mooc.app.module.api.model.MobileReadilyTakeConvert;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.Base64Utils;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.support.Uploader;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.service.InteractMarkService;
@Controller
public class ReadilyTakeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static String readily_Number = "readilyTake";
	private final static String market_Number = "market";
	private final static String Time_Photo_Number = "timePhotoAlbum";
	@Autowired
	private TokenService tokenService;
//	@Autowired
//	private MessageSource messageSource;
	@Autowired
	private InfoService infoService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysContext sysContext;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private InteractMarkService interactMarkService;
//	@Autowired
//	private SnsOpLogService snsOpLogService;
	
	@Value("${app.api.thumbnailWidth}")
	private int thumbnailWidth = 0;
	
	@Value("${app.api.thumbnailHeight}")
	private int thumbnailHeight = 0;
	
	@Value("${app.api.scaleWidth}")
	private int scaleWidth = 0;
	
	@Value("${app.api.scaleHeight}")
	private int scaleHeight = 0;

	/**
	 * 添加随手拍
	 * @param token
	 * @param title	附带文字
	 * @param longitude 经度
	 * @param latitude	纬度
	 * @param address	详细地址
	 * @param metaDescription 曝光，光圈，快门，ISO,相机
	 * @param request
	 * @param response
	 * @param infoType 默认    随手拍1 跳蚤市场2   时光相册3
	 * @throws IOException
	 */
	@RequestMapping(value = "/m-addReadilyTake.htx", method = RequestMethod.POST)
	public void publishInfo(String token, String title, String longitude, String latitude, String infoType,
			String address, String metaDescription, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(title)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文字描述不能为空！")));
			return;
		}
				
		if(StringUtils.isEmpty(infoType)){
			infoType = "1";
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("您没有执行此操作的权限！")));
			return;
		}
		
		List<UploadResult> results = new ArrayList<UploadResult>();
		try {
			//上传文件
			upload(results, site, user.getId(), request, response, Uploader.IMAGE, true, scaleWidth, scaleHeight, true, thumbnailWidth, thumbnailHeight, false);
		} catch (Exception e) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.toString())));
			return;
		}
		String nodeNumber = "";
		if(infoType.equals("1")){
			nodeNumber = readily_Number;
		}else if(infoType.equals("2")){
			nodeNumber = market_Number;
		}else {
			nodeNumber = Time_Photo_Number;
		}
		Node node = nodeService.findByNumber(site.getId(), nodeNumber);
		Info info = new Info();
		String status = Info.NORMAL;
		if(UserTypeUtil.outUser(user.getuType())){
			status = Info.AUDITING; 
		}
//		if(nodeNumber.equals(Time_Photo_Number)){
//			status = Info.DRAFT; 
//		}
		info.setNode(node);
		info.setSite(site);
		
		
		InfoDetail detail = new InfoDetail();
		detail.setTitle(title);
		detail.setMetaDescription(metaDescription);
		
		List<InfoImage> images = new ArrayList<InfoImage>();
		if (results != null) {
			InfoImage infoImage;
			for (int i = 0, len = results.size(); i < len; i++) {
				infoImage = new InfoImage("", "", results.get(i).getFileUrl());
				images.add(infoImage);
			}
		}
		
		Map<String, String> customs = new HashMap<String,String>();
		customs.put("longitude", longitude);
		customs.put("latitude", latitude);
		customs.put("address", address);
		
		String ip = Servlets.getRemoteAddr(request);
		customs.put("ipAddress", ip);
		Map<String, String> clobs = new HashMap<String, String>();
		try {
			info = infoService.saveInfo(info, detail, null, customs, clobs, images, null, null, null,node.getId(), user.getId(), status, site.getId());
			webContext.sysUserLog(LogType.UserOpr, "新增随手拍：" + info.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("保存失败！")));
			return;
		}
//		info = infoService.save(info, detail, new Integer[]{node.getId()}, null, null, null, customs, clobs, images, null, null, null, null,
//				node.getId(), user.getId(), Info.NORMAL, site.getId());
//		logService.operation("info.save", info.getTitle(), null, info.getId(), ip, user.getId(), site.getId());
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccessMsg("您发的信息需要审核后才能显示！")));
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getId())));
		}
	}
	
	protected void upload(List<UploadResult> results, Site site, Long userId, HttpServletRequest request, HttpServletResponse response, String type,
			Boolean exact, Integer width, Integer height, Boolean thumbnail, Integer thumbnailWidth, Integer thumbnailHeight, Boolean watermark) throws IOException {
		Locale locale = RequestContextUtils.getLocale(request);
		

		String ip = Servlets.getRemoteAddr(request);
		Map<String, MultipartFile> fileMap = getMultipartFile(request);
		if (CollectionUtils.isEmpty(fileMap)) {
			return;
		}else{
			Iterator<Entry<String, MultipartFile>>  it = fileMap.entrySet().iterator();
			while(it.hasNext()){
//				UploadResult result = new UploadResult();
//				results.add(result);
//				result.setMessageSource(messageSource, locale);
				MultipartFile partFile = ((Entry<String, MultipartFile>)it.next()).getValue();
//				uploadHandler.upload2(partFile, type, site, userId, ip, result, null, exact, width, height, thumbnail, thumbnailWidth, thumbnailHeight,
//						watermark);
				ImageParam imageParam = new ImageParam();
				imageParam.setWidth(width == null ? 0 : width);
				imageParam.setHeight(height == null ? 0 : height);
				imageParam.setScale(exact == null ? false : exact);
				imageParam.setWatermark(watermark == null ? false : watermark);
				imageParam.setThumbnail(thumbnail == null ? false : thumbnail);
				imageParam.setThumbnailWidth(thumbnailWidth == null ? 0 : thumbnailWidth);
				imageParam.setThumbnailHeight(thumbnailHeight == null ? 0 : thumbnailHeight);				
				imageParam.setOriginal(true);
				UploadResult result = webContext.uploadFile(partFile, imageParam);
				logger.info("用户ID：{} 上传文件成功：{}", userId, result.getFileUrl());
				results.add(result);
				
			} 
		}
	}
	
	private Map<String, MultipartFile> getMultipartFile(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		return multipartRequest.getFileMap();
	}
	
	/**删除随手拍
	 * @param id
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-delReadilyTake.htx" })
	public void deleteArticle(Integer id, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户没有权限使用此功能")));
			return;
		}
		Info bean = infoService.getInfoById(id);
		if (bean.getCreatorId().equals(user.getId())) {
			infoService.cancelInfos(new Integer[]{id});
			String ip = Servlets.getRemoteAddr(request);
			webContext.sysUserLog(LogType.UserOpr, "删除随手拍：" + id);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("")));
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("没有权限删除此篇文章！")));
		}
		
	}
	
	/**
	 * 随手拍列表
	 * @param type  1:我的随手拍列表；2: 随手拍按点击列表,3:别人的
	 * @param infoType 1:随手拍；2：跳蚤市场
	 * @param pageNumber	页数
	 * @param pageSize	每页显示数量
	 * @param token	
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-pageReadilyTake.htx" })
	public void getArticlePage(Integer type, Integer infoType, Long userId, Integer pageNumber, Integer pageSize, 
			String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if(type == null){
			type = 1;
		}
		if(infoType == null){
			infoType = 1;
		}
		String nodeNumber = "";
		logger.debug("infoType:"+infoType);
		if(infoType.equals("1")||infoType==1){
			nodeNumber = readily_Number;
		}else if(infoType.equals("2")||infoType==2){
			nodeNumber = market_Number;
		}else {
			nodeNumber =Time_Photo_Number;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Page<Info> page = null;
		Node node = nodeService.findByNumber(site.getId(), nodeNumber);
		logger.debug("nodeNumber:"+nodeNumber+node.getId());
		
//		Map<String, String> customer = new<String, String> HashMap();
//		customer.put("infoType", infoType+"");
		//查自己的
		if(type.equals(1)){
			Sort sort = new Sort(Direction.DESC, "publishDate");
//			Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
			PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
			
			InfoFindListPageParams findParams = new InfoFindListPageParams();
			findParams.setSiteId(new Integer[] { site.getId() });
			findParams.setMainNodeId(new Integer[]{node.getId()});
			findParams.setUserId(new Long[]{user.getId()});
			findParams.setStatus(new String[] { Info.NORMAL });
//			findParams.setCustomer(customer);
			if(UserTypeUtil.anonymousUser(user.getId())){
				findParams.setP6(new Integer[]{2,3});
			}
			page = infoService.findPage(findParams, pagerParam);
//			page = infoService.findPage(null, null, null, null, null, new Integer[] { site.getId() }, new Integer[]{node.getId()}, new Integer[]{user.getId()}, null, null, null, null,
//					null, null, null, null, null, null, null, null, null, null, null, new String[] { Info.NORMAL }, null, null, null, null,
//					null, null, pageable);
		}else if(type.equals(2)){
			//随手拍列表
			Sort sort = new Sort(Direction.ASC, "columnSort").and(new Sort(Direction.DESC, "priority", "publishDate", "id"));
//			Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
			PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
			InfoFindListPageParams findParams = new InfoFindListPageParams();
			findParams.setSiteId(new Integer[] { site.getId() });
			findParams.setMainNodeId(new Integer[]{node.getId()});
			findParams.setStatus(new String[] { Info.NORMAL });
//			findParams.setCustomer(customer);
			if(UserTypeUtil.outUser(user.getuType())){
				findParams.setP6(new Integer[]{2,3});
			}
			page = infoService.findPage(findParams, pagerParam);
//			page = infoService.findPage(null, null, null, null, null, new Integer[] { site.getId() }, new Integer[]{node.getId()}, null, null, null, null, null,
//					null, null, null, null, null, null, null, null, null, null, null, new String[] { Info.NORMAL }, null, null, null, null,
//					null, null, pageable);
		}else if(type.equals(3)){
			//查看别人的随手拍
			Sort sort = new Sort(Direction.DESC, "publishDate");
//			Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
			PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
			InfoFindListPageParams findParams = new InfoFindListPageParams();
			findParams.setSiteId(new Integer[] { site.getId() });
			findParams.setMainNodeId(new Integer[]{node.getId()});
			findParams.setStatus(new String[] { Info.NORMAL });
			findParams.setUserId(new Long[]{userId});
//			findParams.setCustomer(customer);
			if(UserTypeUtil.outUser(user.getuType())){
				findParams.setP6(new Integer[]{2,3});
			}
			page = infoService.findPage(findParams, pagerParam);
//			page = infoService.findPage(null, null, null, null, null, new Integer[] { site.getId() }, new Integer[]{node.getId()}, new Integer[]{userId}, null, null, null, null,
//					null, null, null, null, null, null, null, null, null, null, null, new String[] { Info.NORMAL }, null, null, null, null,
//					null, null, pageable);
		}
		List<MobileReadilyTake> listInfos = new ArrayList<MobileReadilyTake>();
		if (page.getContent() != null) {
			for (Info info : page.getContent()) {
				Long creatorId = info.getCreatorId();
				SysUserEntity creator = webContext.getSysUser(creatorId);
				SysUserExt creatorExt = webContext.getSysUserExt(creatorId);
				MobileReadilyTake articleInfo = MobileReadilyTakeConvert.convertInfo(info, creator, creatorExt, siteUrl);
				
				if (articleInfo != null) {
					//显示的时候过滤违禁词
					String title = sysContext.filterForbbidenContent(articleInfo.getTitle());
					articleInfo.setTitle(title);
					
					
					if(interactMarkService.hasMark(MarkType.InfoDigg, info.getId(), user.getId())){
						articleInfo.setDigged(1); //是否点赞
					}
					if(interactMarkService.hasMark(MarkType.InfoSave, info.getId(), user.getId())){
						articleInfo.setCollected(1); //是否收藏
					}	
					listInfos.add(articleInfo);
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", listInfos);
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}
	/**
	 * 根据Id获取随手拍
	 * @param id
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-getReadilyTake.htx" })
	public void getArticleById(Integer id, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if (id == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Info info = null;
		if(UserTypeUtil.outUser(user.getuType())){
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put(SpecOperator.EQ + "_id", id);
			searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
			info = infoService.findOne(searchParams);
		}else{
			info = infoService.getInfoById(id);
		}
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(id + "未找到指定文章！")));
			return;
		}
		Long creatorId = info.getCreatorId();
		SysUserEntity creator = webContext.getSysUser(creatorId);
		SysUserExt creatorExt = webContext.getSysUserExt(creatorId);
		MobileReadilyTake articleInfo = MobileReadilyTakeConvert.convertInfo(info,creator,creatorExt, siteUrl);
		
		if(interactMarkService.hasMark(MarkType.InfoDigg, id, user.getId())){
			articleInfo.setDigged(1); //是否点赞
		}
		if(interactMarkService.hasMark(MarkType.InfoSave, id, user.getId())){
			articleInfo.setCollected(1); //是否收藏
		}
//		if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, user.getId(), null)) {
//			articleInfo.setDigged(1);
//		}
//		if (infoCollectService.isUserCollected(id, user.getId())) {
//			articleInfo.setCollected(1);
//		}
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(articleInfo)));
	}
	@RequestMapping(value = { "/m-photoView.htx" })
	public String getTimePhotoView(Integer id, String token,String imgs ,String typeUrl, HttpServletRequest request, HttpServletResponse response, Model modelMap) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		Info info = null;
		String showType = "item1/item1.html";//默认显示模板
		if (!resultData.isSuccess()&&id!= null) {//分享不需要传token
			info = infoService.getInfoById(id);
			if (info == null) {
				return "/error/400.html";
			}
			imgs="";
			for(InfoImage img :info.getImages()) {
				imgs +="&-&"+img.getImage();
			}
			Map<String, String> customs = info.getCustomers();
			if(customs != null){//本身设置模板
			    String tempShowType=customs.get("shwoPhotoUrl");//获取显示模板
			    if(tempShowType!=null&&!tempShowType.equals("")) {
			    	showType = tempShowType;
			    }
			    logger.debug("ReadilyTake:"+tempShowType);
			}else {
				return "/error/400.html";
			}
		}else if (id == null&&!imgs.equals("")) {//预览
			modelMap.addAttribute("imgs",Base64Utils.decode(imgs));
			showType = typeUrl;
		}else if(resultData.isSuccess()&&id!= null) {
			Site site = mobileCommonService.getCurrentMobileSite();
			String siteUrl = site.getSiteUrl();
			Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
			SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
			
			if(UserTypeUtil.outUser(user.getuType())){
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put(SpecOperator.EQ + "_id", id);
				searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
				info = infoService.findOne(searchParams);
			}else{
				info = infoService.getInfoById(id);
			}
			if (info == null) {
				return "/error/400.html";
			}
			Long creatorId = info.getCreatorId();
			SysUserEntity creator = webContext.getSysUser(creatorId);
			SysUserExt creatorExt = webContext.getSysUserExt(creatorId);
			MobileReadilyTake articleInfo = MobileReadilyTakeConvert.convertInfo(info,creator,creatorExt, siteUrl);
			if(interactMarkService.hasMark(MarkType.InfoDigg, id, user.getId())){
				articleInfo.setDigged(1); //是否点赞
			}
			if(interactMarkService.hasMark(MarkType.InfoSave, id, user.getId())){
				articleInfo.setCollected(1); //是否收藏
			}
			modelMap.addAttribute("articleInfo",articleInfo);
			imgs="";
			for(InfoImage img :info.getImages()) {
				imgs +="&-&"+img.getImage();
			}
			modelMap.addAttribute("imgs",imgs.substring(3));
			Map<String, String> customs = info.getCustomers();
			if(typeUrl!=null&&!typeUrl.equals("")) {//测试模板
				showType = typeUrl;
	    	}
			if(customs != null){//本身设置模板
			    String tempShowType=customs.get("shwoPhotoUrl");//获取显示模板
			    if(tempShowType!=null&&!tempShowType.equals("")) {
			    	showType = tempShowType;
			    }
			    logger.debug("ReadilyTake:"+tempShowType);
			}
			
		}else {
			return "/error/400.html";
		}
		showType=showType.replace("_", "/");//斜杠换成特殊字符
		return "/theme/timePhoto/"+showType;
	}
	/**发布时光相册
	 * @param id
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-publishTimePhoto.htx" })
	public void publishTimePhoto(String token,String imgs,String typeUrl, String title, String longitude, String latitude,
			String metaDescription,String address, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(title)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文字描述不能为空！")));
			return;
		}
				
		Site site = mobileCommonService.getCurrentMobileSite();
		//String siteUrl = site.getSiteUrl();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("您没有执行此操作的权限！")));
			return;
		}

		String nodeNumber = Time_Photo_Number;
		Node node = nodeService.findByNumber(site.getId(), nodeNumber);
		Info info = new Info();
		String status = Info.NORMAL;
		if(UserTypeUtil.outUser(user.getuType())){
			status = Info.AUDITING; 
		}
		info.setNode(node);
		info.setSite(site);
		InfoDetail detail = new InfoDetail();
		detail.setTitle(title);
		detail.setMetaDescription(metaDescription);
		
		List<InfoImage> images = new ArrayList<InfoImage>();
		String[] results =Base64Utils.decode(imgs).split("&-&");
		if (results != null) {
			InfoImage infoImage;
			for (int i = 0, len = results.length; i < len; i++) {
				infoImage = new InfoImage("", "", results[i]);
				images.add(infoImage);
			}
		}
		
		Map<String, String> customs = new HashMap<String,String>();
		customs.put("longitude", longitude);
		customs.put("latitude", latitude);
		customs.put("address", address);
		customs.put("shwoPhotoUrl", typeUrl);
		String ip = Servlets.getRemoteAddr(request);
		customs.put("ipAddress", ip);
		Map<String, String> clobs = new HashMap<String, String>();
		try {
			info = infoService.saveInfo(info, detail, null, customs, clobs, images, null, null, null,node.getId(), user.getId(), status, site.getId());
			webContext.sysUserLog(LogType.UserOpr, "新增随手拍：" + info.getTitle());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("保存失败！")));
			return;
		}
//		info = infoService.save(info, detail, new Integer[]{node.getId()}, null, null, null, customs, clobs, images, null, null, null, null,
//				node.getId(), user.getId(), Info.NORMAL, site.getId());
//		logService.operation("info.save", info.getTitle(), null, info.getId(), ip, user.getId(), site.getId());
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccessMsg("您发的信息需要审核后才能显示！")));
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getId())));
		}
		
	}
	
	
	
	@RequestMapping(value = "/m-uploadPreview.htx", method = RequestMethod.POST)
	public void uploadPreview(String token, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}		
		List<UploadResult> results = new ArrayList<UploadResult>();
		try {
			//上传文件
			upload(results, null, (long)0, request, response, Uploader.IMAGE, true, 1000, scaleHeight, true, 1000, thumbnailHeight, false);
		} catch (Exception e) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.toString())));
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String imgs ="";
		for(UploadResult uploadResult :results) {
			imgs +="&-&"+uploadResult.getFileUrl();
		}
		
		map.put("imgs",Base64Utils.encode(imgs.substring(3)));
		map.put("msg_code", 1);
		map.put("reason", "上传成功");
		Servlets.writeHtml(response,mapper.toJson(MobileResultData.createSuccess(map)));
	}
	
	
}
