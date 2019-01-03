package cn.mooc.app.module.api.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.event.EventDispatch;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.ad.data.entity.AdSlot;
import cn.mooc.app.module.ad.service.AdService;
import cn.mooc.app.module.ad.service.AdSlotService;
import cn.mooc.app.module.api.model.MobileListData;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.service.MobileService;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.api.utils.RedisCommentCountUtil;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.CmsMessage;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoImage;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.entity.WorkflowLog;
import cn.mooc.app.module.cms.fulltext.InfoFulltextService;
import cn.mooc.app.module.cms.model.CmsMessageModel;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;
import cn.mooc.app.module.cms.model.MobileArticleInfo;
import cn.mooc.app.module.cms.model.MobileArticleInfo.AuditOpinion;
import cn.mooc.app.module.cms.model.MobileCommentInfo;
import cn.mooc.app.module.cms.model.MobileFriendlink;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.service.AttributeService;
import cn.mooc.app.module.cms.service.CmsMessageService;
import cn.mooc.app.module.cms.service.InfoBufferService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.InfoSpecialService;
import cn.mooc.app.module.cms.service.InfoVisitorService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SpecialService;
import cn.mooc.app.module.cms.service.WorkflowLogService;
import cn.mooc.app.module.cms.service.WorkflowStepService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.interact.data.entity.InteractComment;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.data.entity.SnsOpLog;
import cn.mooc.app.module.interact.enums.CommentType;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.event.selector.CommentEventSelector;
import cn.mooc.app.module.interact.event.selector.DiggEventSelector;
import cn.mooc.app.module.interact.event.selector.UnCommentEventSelector;
import cn.mooc.app.module.interact.event.selector.UnDiggEventSelector;
import cn.mooc.app.module.interact.model.InteractCommentFindPageListParams;
import cn.mooc.app.module.interact.service.InteractCommentService;
import cn.mooc.app.module.interact.service.InteractMarkService;
import cn.mooc.app.module.interact.service.SnsOpLogService;



/**
 * @author hwt
 * @version 1.0
 * 
 */
@Controller
public class MobileInfoController {
	private static final Logger logger = LoggerFactory.getLogger(MobileInfoController.class);
	
	@Autowired
	private InfoService infoQueryService;
	@Autowired
	private NodeService nodeQueryService;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysContext sysContext;
	@Autowired
	private InteractCommentService commentService;
	@Autowired
	private InfoSpecialService infoSpecialService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private SnsOpLogService snsOpLogService;
	@Autowired
	private MobileService mobileService;
	@Autowired
	private InfoVisitorService infoVisitorService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private InteractMarkService interactMarkService;
	@Autowired
	private AdSlotService adSlotService;
	@Autowired
	private AdService adService;
	@Autowired
	private EventDispatch eventDispatch;
	@Autowired
	private WorkflowLogService flowLogService;
	@Autowired
	private SysDataService userService;
	@Autowired
	private CmsMessageService messageService;
	@Autowired
	private RedisCommentCountUtil redisCommentUtil;
	@Autowired
	private WorkflowStepService flowStepService;
	@Autowired
	private MobileModelConvert mobileModelConvert;
	@Autowired
	private InfoFulltextService fulltext;
	@Value("${number.home}")
	private String homeNumber;//推荐的广告栏

	@RequestMapping(value = { "/m-RtmpChange.htx" })
	public void rtmpChange(int status, HttpServletRequest request,	HttpServletResponse response) {
		
		MobileModelConvert.setUseRtmp(status == 1 ? true : false);
		
	}
	
	@RequestMapping(value = { "/m-UseCommonCss.htx" })
	public void useCommonCss(int status, HttpServletRequest request,	HttpServletResponse response) {
		
		MobileModelConvert.setUseCommonCss(status == 1 ? true : false);
		
	}
	
	@RequestMapping(value = { "/m-shortUrl.htx" })
	public void getShortUrl(Integer articleId, String token, HttpServletRequest request,	HttpServletResponse response) {
		//根据当前用户，返回一个供他分享到外网的URL地址
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		
		//返回一个完整对应的URL
		String infoUrl = mobileCommonService.getInfoUrl(articleId);
		
		//直接变成短码的URL，可根据该短码知道是谁分享的，并统计出谁分享的链接打开的次数最多
		//String infoUrl = mobileCommonService.getShortUrlForInfo(articleId);
		
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(infoUrl)));
		
	}
	
	/**
	 * 获取单篇文章
	 * 
	 * @param articleId
	 *            文章主键
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-article.htx" })
	public void getArticleById(Integer articleId, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (articleId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Info info = null;
		if(UserTypeUtil.outUser(user.getuType())){
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put(SpecOperator.EQ + "_id", articleId);
			searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
			info = infoQueryService.findOne(searchParams);
		}else{
			info = infoQueryService.getInfoById(articleId);
		}
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, true, false);
		
		this.showHeader(info, articleInfo);
		
		//显示 一审 二审 三审信息	
		this.showFooterFlowlogs(info, articleInfo);		

		//在内容末尾增加栏目显示
		if(info.getStatus().equals(Info.AUDITING)){
			appendNodeInfo(info, articleInfo);
		}
		
		
		if(interactMarkService.hasMark(MarkType.AttentionUser, info.getCreatorId(), user.getId())){
			articleInfo.setAttention(1); //是否关注了作者
		}
		if(interactMarkService.hasMark(MarkType.InfoDigg, articleId, user.getId())){
			articleInfo.setDigged(1); //是否点赞
		}
		if(interactMarkService.hasMark(MarkType.InfoSave, articleId, user.getId())){
			articleInfo.setCollected(1); //是否收藏
		}

		infoBufferService.updateViews(articleId);
		infoSpecialService.updateViewsByInfoId(articleId);
		Site site = mobileCommonService.getCurrentMobileSite();
		String ip = Servlets.getRemoteAddr(request);
		infoVisitorService.visite(user, site, info, ip);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(articleInfo)));
	}
	
	private void showHeader(Info info, MobileArticleInfo articleInfo){
		StringBuilder header = new StringBuilder();	
		if (articleInfo.getHasImages() != null && articleInfo.getHasImages().intValue() == 1) {
			header.append(articleInfo.getAuthor());
			if(StringUtils.isNotBlank(articleInfo.getSource())){
				header.append("	");
				header.append(articleInfo.getSource());
			}
			
			
		}else {
			header.append("<font color=gray>"+articleInfo.getAuthor()+"</font>");
			if(StringUtils.isNotBlank(articleInfo.getSource())){
				header.append("&nbsp;&nbsp;&nbsp;&nbsp;");
				header.append("<font color=gray>"+articleInfo.getSource()+"</font>");
			}

		}
		
		articleInfo.setHeader(header.toString());
		
	}
	
	private void showFooterFlowlogs(Info info, MobileArticleInfo articleInfo){
		List<WorkflowLog> flowLogs = flowLogService.findListByInfo(info.getId(), Info.WORKFLOW_TYPE, false);
		if(flowLogs!=null && flowLogs.size()>0){
			int i = 0;
			//
			String[] lables = new String[]{"一审","二审","三审","四审","五审","六审","七审","八审","九审","十审"};
			StringBuilder footer = new StringBuilder();	
			footer.append("<p style='line-height:1.6;'>");
//			for (WorkflowLog workflowLog : flowLogs) {
//				SysUserInfo sysUser = webContext.getSysUserFullInfo(workflowLog.getUserId());
//				String userName = sysUser == null ? "" : sysUser.getUName();
//				String lableName = i < lables.length ? lables[i] : "审核：";
//				footer.append("<font color=gray>");
//				footer.append(lableName+"：" + userName);
//				footer.append("</font>");
//				footer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
//				i++;
//				
//				if(i%2==0){
//					//每2个一排
//					footer.append("<br>");
//				}
//				
//			}
			SysUserInfo sysUser = webContext.getSysUserFullInfo(flowLogs.get(0).getUserId());
			String userName = sysUser == null ? "" : sysUser.getUName();
			footer.append("<font color=gray>");
			footer.append("责任编辑：" + userName);
			footer.append("</font>");
			footer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			footer.append("</p>");
			
			articleInfo.setFooter(footer.toString());
		}
	}

	private void appendNodeInfo(Info info, MobileArticleInfo articleInfo){
		//
		StringBuilder mark = new StringBuilder();
		
		mark.append("<p style='margin-top: 5px;margin-bottom: 5px;color: #8a6d3b;border:1px solid;border-color: #faebcc;height: 25px;padding-left: 5px;background-color: #fcf8e3;font-size: 12px;border-radius: 4px;line-height: 25px;'>");
		mark.append(info.getNode().getDisplayName());
		mark.append("</p>");

		if(StringUtils.isNotEmpty(articleInfo.getContent())){
			articleInfo.setContent(articleInfo.getContent() + mark.toString().replaceAll("所有 - ", ""));
		}
		
	}
	
	/**
	 * 获取栏目下文章列表
	 * 
	 * @param cateId
	 *            栏目ID, 如果为0,则表示所有分类
	 * @param attrType
	 *            0:所有, 1:最新的文章, 2:最热的文章
	 * @param hasPic
	 *            0:所有, 1:带图的文章, 2:不带图的文章
	 * @param rows
	 *            最多返回条数
	 * @param token
	 *            令牌
	 * 
	 */
	@RequestMapping(value = { "/m-articleList.htx" })
	public void getArticleList(Integer cateId, Integer attrType, Integer hasPic, Integer rows, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();

		Integer[] nodeId = null;
		if (cateId != null && cateId != 0) {
			nodeId = new Integer[] { cateId };
		}
		if (rows == null || rows == 0) {
			rows = 10;
		}
		Sort defSort = new Sort(Direction.DESC, "priority", "publishDate", "id");
		if (attrType != null && attrType == 2) {
			defSort = new Sort(Direction.DESC, "views");
		}
		PagerParam pagerParam = new PagerParam(1,rows, defSort);
		Site site = mobileCommonService.getCurrentMobileSite();
		Boolean isWithImage = null;
		if (hasPic != null) {
			if (hasPic == 1) {
				isWithImage = true;
			} else if (hasPic == 2) {
				isWithImage = false;
			}
		}
		InfoFindListPageParams findParams = new InfoFindListPageParams();
		findParams.setNodeId(nodeId);
		findParams.setSiteId(new Integer[] { site.getId() });
		findParams.setIsWithImage(isWithImage);
		findParams.setStatus(new String[] { Info.NORMAL });
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			findParams.setP6(new Integer[]{2,3});
		}
		List<Info> list = infoQueryService.findList(findParams, pagerParam);

		for (Info info : list) {
			MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, false, true);
			if (articleInfo != null) {
				listInfos.add(articleInfo);
			}
		}

		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(listInfos, 1, listInfos.size(), listInfos.size())));
	}

	/**
	 * 获取栏目下文章分页
	 * 
	 * @param cateId
	 *            栏目ID, 如果为0,则表示所有分类
	 * @param attrType
	 *            0:所有, 1:最新的文章, 2:最热的文章
	 * @param hasPic
	 *            0:所有, 1:带图的文章, 2:不带图的文章
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param isSpecial
	 *            是否专题下文章
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-articlePage.htx" })
	public void getArticlePage(Integer cateId, Integer attrType, Integer hasPic, Integer hasAudio, String attr, Integer pageNumber, Integer pageSize,
		Integer isSpecial, String token, Integer showDescendants, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		//Sort sort = new Sort(Direction.ASC, "columnSort").and(new Sort(Direction.DESC, "priority", "publishDate", "id"));
		Sort sort =new Sort(Direction.DESC, "priority", "publishDate", "id");
	
		if (attrType != null && attrType == 2) {
			sort = new Sort(Direction.DESC, "views");
		}
		
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		Site site = mobileCommonService.getCurrentMobileSite();
		Boolean isWithImage = null;
		if (hasPic != null) {
			if (hasPic == 1) {
				isWithImage = true;
			} else if (hasPic == 2) {
				isWithImage = false;
			}
		}
		Boolean isAudio = null;
		if(hasAudio != null){
			if(hasAudio == 1){
				isAudio = true;
			}else if(hasAudio == 2){
				isAudio = false;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Info> page = null;
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if (isSpecial != null && isSpecial == 1) {
			if (cateId == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定专题主键！")));
				return;
			}
			Special special = specialService.getSpecialById(cateId);
			if (special == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(cateId + "未找到指定专题！")));
				return;
			}
			specialService.updateViews(special);
			Integer[] specialId = new Integer[] { cateId };
			if (pageNumber == 1) {
				getFriendlinks(special.getMetaKeywords(), site, map);
			}

			//点击进入文章明细
			flag = 1;
			InfoFindListPageParams infoFindListPageParams = new InfoFindListPageParams();
			infoFindListPageParams.setSpecialId(specialId);
			infoFindListPageParams.setSiteId(new Integer[] { site.getId() });
			infoFindListPageParams.setIsWithImage(isWithImage);
			infoFindListPageParams.setStatus(new String[] { Info.NORMAL });
//			infoFindListPageParams.setIsAudio(isAudio);
			
			if(UserTypeUtil.outUser(user.getuType())){
				infoFindListPageParams.setP6(new Integer[]{2,3});
			}
			page = infoQueryService.findPage(infoFindListPageParams, pagerParam);
		} else {
			cateId = cateId == null ? 0 : cateId;
			Integer[] nodeId = null;
			//logger.debug("测试数据：cateId："+cateId);
			Node node = nodeQueryService.getNodeById(cateId);
			//logger.debug("测试数据：site.getId()"+site.getId());
			//logger.debug("测试数据：redmoocNumber"+redmoocNumber);
			if (node == null) {
				node = nodeQueryService.findByNumber(site.getId(), homeNumber);
				if(node==null) {
					//logger.debug("测试数据：node为空");
					node = nodeQueryService.getNodeById(237);
				}
			} else {
				//logger.debug("测试数据：node不是为空");
				nodeId = new Integer[] { node.getId() };
			}
			
			if (pageNumber == 1) {
				
				//logger.debug("测试数据：node.getNumber()"+node.getNumber());
				getFriendlinks(node.getNumber(), site, map);
			}
			Integer[] attrId = null;
			if (StringUtils.isNotEmpty(attr)) {
				List<Attribute> attrs = attributeService.findByNumbers(new String[] { attr }, new Integer[] { site.getId() });
				if (attrs != null && !attrs.isEmpty()) {
					List<Integer> list = new ArrayList<Integer>(); 
					for (Attribute a : attrs) {
						list.add(a.getId());
					}
					attrId = list.toArray(new Integer[list.size()]);
				}
			}
			InfoFindListPageParams infoFindListPageParams = new InfoFindListPageParams();
			infoFindListPageParams.setNodeId(nodeId);
			infoFindListPageParams.setAttrId(attrId);
			infoFindListPageParams.setSiteId(new Integer[] { site.getId() });
			infoFindListPageParams.setIsWithImage(isWithImage);
			infoFindListPageParams.setStatus(new String[] { Info.NORMAL });
			infoFindListPageParams.setIsAudio(isAudio);
			
			if(showDescendants != null && showDescendants == 1){
				infoFindListPageParams.setShowDescendants(true);
				infoFindListPageParams.setNodeNumber(node.getTreeNumber());
			}else{
				infoFindListPageParams.setShowDescendants(false);
			}
			
			if(UserTypeUtil.outUser(user.getuType())){
				infoFindListPageParams.setP6(new Integer[]{2,3});
			}
			if(StringUtils.isNotEmpty(attr)){
				sort = new Sort(Direction.ASC, "infoAttrs.seq");
				//sort = new Sort(Direction.DESC, "priority", "publishDate", "id");
				pagerParam = new PagerParam(pageNumber, pageSize, sort);
				page = infoQueryService.findPage(infoFindListPageParams, pagerParam);
			}else{
				page = infoQueryService.findPage(infoFindListPageParams, pagerParam);
			}
		}

		if (page.getContent() != null) {
			for (Info info : page.getContent()) {
				MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, false, true);
				if (articleInfo != null) {
					if(attr!=null && attr.equals("recommend") && info.getP5() !=null){
						articleInfo.setShowType(info.getP5());
					}
					articleInfo.setIsToDetail(flag);
					listInfos.add(articleInfo);
				}
			}
		}

		map.put("data", listInfos);
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}
	
	/**
	 * 判断是否有终审权限
	 * @param articleId
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-istoend.htx" })
	public void istoend(Integer articleId, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户无此权限")));
			return;
		}
		if(articleId == null){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("不存在此文章！")));
		}
		if(flowStepService.istoend(articleId, user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("")));
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("没有终审权限！")));
		}
	}
	/**
	 * 文章终审
	 * @param infoId
	 * @param token
	 * @param opinion
	 * @param status
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-toend.htx" })
	public void toend(Integer articleId, String token, Integer status,  HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户无此权限")));
			return;
		}
		try{
			infoQueryService.toEnd(articleId, user.getId());
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("审核成功！")));
		}catch(Exception e){
			logger.error("article toend fail:", e);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.getMessage())));
		}
	}

	/**
	 * 审核文章
	 * @param pageNumber
	 * @param pageSize
	 * @param type 1：待审；2：退稿；3：已审核；
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-auditInfoPage.htx" })
	public void getAuditInfoPage(Integer pageNumber, Integer pageSize, Integer type, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户无此权限")));
			return;
		}
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		if(StringUtil.isNull(type)){
			type = 1;
		}
//		Sort sort = new Sort(Direction.ASC, "columnSort").and(new Sort(Direction.DESC, "priority", "publishDate", "id"));
		
//		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		Site site = mobileCommonService.getCurrentMobileSite();
		Map<String, Object> map = new HashMap<String, Object>();
//		Page<Info> page = infoQueryService.findPageAuditInfo(user.getId(), null, pagerParam);
		Page<Info> page = null;
		if(type == 1){
			page = infoQueryService.findAuditPage(user.getId(), pageNumber-1, pageSize);
		}else if(type == 2){
			page = infoQueryService.findAuditBackPage(user.getId(), pageNumber-1, pageSize);	
		}else if(type == 3){
			page = infoQueryService.findHasAuditPage(user.getId(), pageNumber-1, pageSize);
		}

		if (page.getContent() != null) {
			for (Info info : page.getContent()) {
				MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, false, true);
				
				if (articleInfo != null) {
					List<WorkflowLog> flowLogs = flowLogService.findListByInfo(info.getId(), Info.WORKFLOW_TYPE, true);
					SimpleDateFormat fortmat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					if(flowLogs != null && flowLogs.size() > 0){
						List<AuditOpinion> olist = new ArrayList<AuditOpinion>();
						for(WorkflowLog log : flowLogs){
							AuditOpinion opinion = AuditOpinion.getInstance();
							opinion.setCreationDate(fortmat.format(log.getCreationDate()));
							opinion.setOpinion(log.getOpinion());
							SysUserInfo u = webContext.getSysUserFullInfo(log.getUserId());
							opinion.setUserName(u.getUserName());
							olist.add(opinion);
						}
						articleInfo.setAuditOpinions(olist);
						articleInfo.setIsReject(1);
					}
					if(type == 2){
						articleInfo.setIsReject(1);
					}else{
						articleInfo.setIsReject(0);
					}
					articleInfo.setIsToDetail(flag);
					
					
					listInfos.add(articleInfo);
				}
			}
		}

		map.put("data", listInfos);
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}
	

	
	/**
	 * 查看文章审核日志
	 * @param infoId
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-viewlog.htx" })
	public void viewlog(Integer infoId, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户无此权限")));
			return;
		}
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<WorkflowLog> flowLogs = flowLogService.findListByInfo(infoId, Info.WORKFLOW_TYPE, false);
		List<Map> logList = new ArrayList<Map>();
		if(flowLogs != null && flowLogs.size() > 0){
			
			for(WorkflowLog flowLog : flowLogs){
				Map params =new HashMap();
				
				SysUserInfo sysUser = webContext.getSysUserFullInfo(flowLog.getUserId());
				String userName = sysUser == null ? "无" : sysUser.getUName();
				String from = flowLog.getFrom();
				params.put("from", from + "："+userName);

				params.put("to", flowLog.getTo());
				params.put("time", format.format(flowLog.getCreationDate()));
				params.put("opinion", flowLog.getOpinion()==null?"":flowLog.getOpinion());
				logList.add(params);
			}
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(logList)));
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("没有审核流程!")));
		}
	}
	
	/**
	 * 审核文章
	 * @param infoId
	 * @param token
	 * @param opinion
	 * @param status
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-auditInfo.htx" })
	public void auditInfo(Integer infoId, String token, String opinion, Integer status,  HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户无此权限")));
			return;
		}
		try{
			Sort sort = new Sort(Direction.ASC, "columnSort").and(new Sort(Direction.DESC, "priority", "publishDate", "id"));
			PagerParam pagerParam = new PagerParam(1, 5, sort);
			Page<Info> page = infoQueryService.findPageAuditInfo(user.getId(), infoId, pagerParam);
			
			if(page != null && page.getContent().size() > 0){
				if(status.intValue()==1){
					infoQueryService.pass(new Integer[]{infoId}, user.getId(), opinion);
				}else{
					infoQueryService.reject(new Integer[]{infoId}, user.getId(), opinion, false, user.getId(), user.getUserName(), ValidatorUtil.getIpAddr(request), request.getRequestURI());
				}
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("审核成功！")));
			}else{
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("没有文章审核权限或文章已被审核！")));
			}
			
		}catch(Exception e){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("审核失败！")));
		}
	}
	
	/**
	 * 根据多个ID，获取多篇文章
	 * 
	 * @param ids
	 *            多文章主键
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-articles.htx" })
	public void getArticleByIds(Integer[] ids, String token, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (ids == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();
		for (int i = 0; i < ids.length; i++) {
			Info info = null;
			if(UserTypeUtil.outUser(user.getuType())){
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put(SpecOperator.EQ + "_id", ids[i]);
				searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
				info = infoQueryService.findOne(searchParams);
			}else{
				info = infoQueryService.getInfoById(ids[i]);
			}
			
			MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, false, true);
			if (articleInfo != null) {
				listInfos.add(articleInfo);
			}
		}
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(listInfos, 1, listInfos.size(), listInfos.size())));
	}

	/**
	 * 文章点赞
	 * 
	 * @param token
	 *            令牌
	 * @param articleId
	 *            文章主键
	 */
	@RequestMapping(value = { "/m-article-diggs.htx" })
	public void diggArticle(String token, Integer articleId, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (articleId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Info info = infoQueryService.getInfoById(articleId);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getDiggs()+1)));
			return;
		}
//		User user = userService.get(StringUtil.string2Int("" + userMap.get("UID")));
		if(interactMarkService.hasMark(MarkType.InfoDigg, articleId, user.getId())){
//		if (voteMarkService.isUserVoted(Info.DIGG_MARK, articleId, user.getId(), null)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章已经点过赞!")));
			return;
		}

		String ip = Servlets.getRemoteAddr(request);
		InteractMark entity = new InteractMark();
		entity.setFtype(MarkType.InfoDigg);
		entity.setFid(articleId);
		entity.setFtitle(info.getTitle());
		entity.setUserId(user.getId());
		entity.setIp(ip);
		entity.setSiteId(site.getId());
		try {
			interactMarkService.saveInteractMark(entity);
			infoQueryService.addDiggs(info.getId() , 1);
			
			LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
			OprEvent oprEvent = new OprEvent(request);
			oprEvent.setLoginUser(shiroUser);
			oprEvent.addParam("infoId", articleId);
			oprEvent.addSelector(new DiggEventSelector());
			//发送事件
			eventDispatch.postEvent(oprEvent);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章点赞失败!")));
			return;
		}
//		bufferService.updateDiggs(articleId, user.getId(), ip, cookie);
//		logService.operation("info.digg", info.getTitle(), null, info.getId(), ip, user.getId(), info.getSite().getId());
		snsOpLogService.operation(site.getId(), new Long(user.getId()).intValue() , SnsOpLog.FTYPE_DIGG, info.getId(), info.getTitle(), user.getUserName());
		logger.debug("info digg, message={}.", "fid:" + articleId + ",title:" + info.getTitle() + ",uid:" + user.getId());
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getDiggs())));
	}

	/**
	 * 取消文章点赞
	 * 
	 * @param token
	 *            令牌
	 * @param articleId
	 *            文章主键
	 */
	@RequestMapping(value = { "/m-article-reDiggs.htx" })
	public void reDiggArticle(String token, Integer articleId, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (articleId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		Info info = infoQueryService.getInfoById(articleId);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getDiggs())));
			return;
		}
		
//		User user = userService.get(StringUtil.string2Int("" + userMap.get("UID")));
		if(interactMarkService.hasMark(MarkType.InfoDigg, articleId, user.getId())){
//		if (voteMarkService.isUserVoted(Info.DIGG_MARK, articleId, user.getId(), null)) {
//			voteMarkService.reMark(Info.DIGG_MARK, articleId, user.getId());
			snsOpLogService.clearByUIDFtypeFid(new Long(user.getId()).intValue(), SnsOpLog.FTYPE_DIGG, info.getId());
			try {
				interactMarkService.cancelInteractMark(articleId, MarkType.InfoDigg, user.getId());
				infoQueryService.addDiggs(info.getId() , -1);
				
				LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
				OprEvent oprEvent = new OprEvent(request);
				oprEvent.setLoginUser(shiroUser);
				oprEvent.addParam("infoId", info.getId());
				oprEvent.addSelector(new UnDiggEventSelector());
				//发送事件
				eventDispatch.postEvent(oprEvent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章取消点赞失败！")));
				return;
			}
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getDiggs())));
		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章已取消点赞！")));
		}
	}
	
	/**
	 * 删除文章
	 * @param token  
	 * @param id  评论的主键
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-deleteArticle.htx" })
	public void deleteArticle(String token, Integer id, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (id == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户无此权限")));
			return;
		}
		
		Info info = infoQueryService.getInfoById(id);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(id + "未找到指定文章！")));
			return;
		}

		try {
			infoQueryService.cancelInfo(id);
			logger.info("delete Info, id={}.", id);
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("")));
		} catch (Exception e) {
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("删除文章失败："+e.getMessage())));
			return;
		}
	}
	
	/**
	 * 评论文章
	 * @param token  
	 * @param articleId  评论或者文章的主键
	 * @param fId	文章主键
	 * @param type	评论类型：1:对文章评论，2：对评论的评论
	 * @param text	评论内容
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-article-comment.htx" })
	public void commentArticle(String token, Integer articleId, Integer fId, Integer type, String text, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (articleId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return;
		}
		if (StringUtils.isEmpty(text)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("评论内容不能为空！")));
			return;
		}
//		text = sensitiveWordService.replace(text);
		if (text.length() > 100) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("评论内容最多为100字！")));
			return;
		}
		
		//过滤评论中不安全的字符
		text = ValidatorUtil.filterHtml(text);
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("您需要登录后才能发评论！")));
			return;
		}
		
		SysUserInfo sysUserInfo = webContext.getSysUserFullInfo(StringUtil.string2Int("" + userMap.get("UID")));
		Site site = mobileCommonService.getCurrentMobileSite();
		String ip = Servlets.getRemoteAddr(request);
		
		if(type.intValue()==1){
			Info info = infoQueryService.getInfoById(articleId.intValue());
			if (info == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
				return;
			}
			

			try {
				InteractComment comment = new InteractComment();
				comment.setFid(articleId.intValue());
				comment.setFuserId(info.getCreatorId());
				SysUserEntity user1 = webContext.getSysUser(info.getCreatorId());
				comment.setFuserName(user1.getUserName());
				comment.setContent(text);
				comment.setIp(ip);
				comment.setSiteId(site.getId());
				comment.setFtype(CommentType.Info);
				comment.setUserId(user.getId());
//				comment.setUserName(user.getUserName());
				comment.setUserName(sysUserInfo.getUName());
				comment.setIp(ip);
				comment.setInfoTitle(info.getTitle());
				if(commentService.getConfig_needCheck() == 1 || UserTypeUtil.outUser(user.getuType())){
					comment.setState(InteractComment.SAVED);
				}else{
					comment.setState(InteractComment.AUDITED);
				}
				
//				comment.setState(InteractComment.SAVED);
				comment.setCreateTime(new Date());
				commentService.saveInteractComment(comment);
				infoQueryService.addComments(info.getId());
				if(user.getId() != info.getCreatorId().longValue()){
					redisCommentUtil.addCount(site.getId(), info.getCreatorId());
				}
				
				LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
				OprEvent oprEvent = new OprEvent(request);
				oprEvent.setLoginUser(shiroUser);
				oprEvent.addParam("infoId", articleId);
				oprEvent.addSelector(new CommentEventSelector());
				//发送事件
				eventDispatch.postEvent(oprEvent);
				
				logger.info("comment" + CommentType.Info.toString() + ", message={}.",
						"fid:" + articleId + ",title:" + info.getTitle() + ",text:" + text.substring(0, text.length() > 100 ? 100 : text.length()));

				snsOpLogService.operation(site.getId(),new Long(user.getId()).intValue(), SnsOpLog.FTYPE_COMMENT, info.getId(), info.getTitle(), user.getUserName());
				if(commentService.getConfig_needCheck() == 1 || UserTypeUtil.outUser(user.getuType())){
					Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccessMsg("您的评论需要审核通过后才能显示！")));
				}else{
					Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getComments())));
				}
			} catch (Exception e) {
				e.printStackTrace();
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.getMessage())));
				return;
			}
		}else if(type.intValue() == 2){
			InteractComment ct = commentService.getInteractCommentByCommentId(articleId);
			if (ct == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定评论！")));
				return;
			}
			Info info = infoQueryService.getInfoById(fId);
			if (info == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(fId + "未找到指定文章！")));
				return;
			}
			
			try {
				InteractComment comment = new InteractComment();
				comment.setFid(fId);
				comment.setSourceId(articleId);
				comment.setSourceUserId(ct.getUserId());
				comment.setSourceUserName(ct.getUserName());
				comment.setContent(text);
				comment.setIp(ip);
				comment.setInfoTitle(info.getTitle());
				comment.setFuserId(info.getCreatorId());
				SysUserEntity user1 = webContext.getSysUser(info.getCreatorId());
				comment.setFuserName(user1.getUserName());
				comment.setSiteId(site.getId());
				comment.setFtype(CommentType.Comment);
				comment.setUserId(user.getId());
//				comment.setUserName(user.getUserName());
				comment.setUserName(sysUserInfo.getUName());
				comment.setCreateTime(new Date());
				comment.setIp(ip);
				if(commentService.getConfig_needCheck() == 1 || UserTypeUtil.outUser(user.getuType())){
					comment.setState(InteractComment.SAVED);
				}else{
					comment.setState(InteractComment.AUDITED);
				}
//				comment.setState(InteractComment.SAVED);
				
				commentService.saveInteractComment(comment);
				infoQueryService.addComments(info.getId());
				if(user.getId() != ct.getUserId().longValue()){
					redisCommentUtil.addCount(site.getId(), ct.getUserId());
				}
				
				LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
				
				OprEvent oprEvent = new OprEvent(request);
				oprEvent.setLoginUser(shiroUser);
				oprEvent.addParam("infoId", articleId);
				oprEvent.addSelector(new CommentEventSelector());
				//发送事件
				eventDispatch.postEvent(oprEvent);
				
				logger.info("comment" + CommentType.Comment + ", message={}.",
						"fid:" + articleId + ",title:互评" + ",text:" + text.substring(0, text.length() > 100 ? 100 : text.length()));

				snsOpLogService.operation(site.getId(), new Long(user.getId()).intValue(), SnsOpLog.FTYPE_COMMENT, articleId, "互评", user.getUserName());
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getComments())));
			} catch (Exception e) {
				e.printStackTrace();
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.getMessage())));
				return;
			}
		}
		
	}
	
	/**
	 * 获取未读数
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-comments-unread.htx" })
	public void getCommentTotal(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Site site = mobileCommonService.getCurrentMobileSite();
		int count = redisCommentUtil.getCount(site.getId(), user.getId());
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(count)));
		
	}
	
	/**
	 * 清除未读数
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-comments-clear.htx" })
	public void clearCommentTotal(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Site site = mobileCommonService.getCurrentMobileSite();
		redisCommentUtil.clearCount(site.getId(), user.getId());
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("清除成功")));
		
	}
	
	/**
	 * 删除评论
	 * @param token  
	 * @param id  评论的主键
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-deletecomment.htx" })
	public void deletecomment(String token, Integer id, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (id == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定评论主键！")));
			return;
		}
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("您没有执行此操作的权限！")));
			return;
		}
		
		
		Site site = mobileCommonService.getCurrentMobileSite();
		
		InteractComment comment = commentService.getInteractCommentByCommentId(id);
		if (comment == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(id + "未找到指定评论！")));
			return;
		}
		Info info = infoQueryService.getInfoById(comment.getFid());
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(comment.getFid() + "未找到指定文章！")));
			return;
		}
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getComments())));
			return;
		}
		try {
			commentService.cancelCommentByCommentId(id);
			infoQueryService.delComments(info.getId());
			
			LoginUserInfo shiroUser = new LoginUserInfo(user.getId(), user.getUserName());
			OprEvent oprEvent = new OprEvent(request);
			oprEvent.setLoginUser(shiroUser);
			oprEvent.addParam("infoId", comment.getFid());
			oprEvent.addSelector(new UnCommentEventSelector());
			//发送事件
			eventDispatch.postEvent(oprEvent);
			
			logger.info("comment" + CommentType.Info + ", message={}.",
					"commentId:" + id  );

			snsOpLogService.operation(site.getId(),new Long(user.getId()).intValue(), SnsOpLog.FTYPE_COMMENT_DEL, info.getId(), info.getTitle(), user.getUserName());
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(info.getComments())));
		} catch (Exception e) {
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(e.getMessage())));
			return;
		}
	}
	
	/**
	 * 文章评论分页
	 * 
	 * @param articleId
	 *            文章主键
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-article-commentPage.htx" })
	public void getCommentPage(Integer articleId, Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Info info = null;
		if(UserTypeUtil.outUser(user.getuType())){
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put(SpecOperator.EQ + "_id", articleId);
			searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
			info = infoQueryService.findOne(searchParams);
		}else{
			info = infoQueryService.getInfoById(articleId);
		}
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		List<MobileCommentInfo> listComments = new ArrayList<MobileCommentInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort defSort = new Sort(Direction.DESC, "createTime");
//		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, defSort);
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, defSort);
		
		InteractCommentFindPageListParams params = new InteractCommentFindPageListParams();
		params.setFtype(CommentType.Info.toString());
		params.setFid(articleId);
		//if(commentService.getConfig_needCheck() == 1){
			params.setStatus(new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND });
		//}else{
			//params.setStatus(new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND });
		//}
//		params.setStatus(new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND });
		params.setSiteId(new Integer[] { site.getId() });
		
		Page<InteractComment> page = commentService.findPage(params, pagerParam);

		if (page.getContent() != null) {
			for (InteractComment comment : page.getContent()) {
//				MobileCommentInfo commentInfo = 
				mobileModelConvert.converComment(listComments, comment, siteUrl);
//				if (commentInfo != null) {
//					listComments.add(commentInfo);
//				}
			}
		}

		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(listComments, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}
	
	/**
	 * 获取用户评论
	 * @param type 评论类型；1：用户的评论；2：对用户的评论
	 * @param userId 用户Id
	 * @param pageNumber
	 * @param pageSize
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-user-commentPage.htx" })
	public void getUserCommentPage(Integer type, Integer userId, Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = null;
		if(userId != null){
			user = userService.getUserInfoById(new Long(userId.toString()));
		}
		if(user == null){
			user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		}
//		User user = userService.get(StringUtil.string2Int("" + userMap.get("UID")));
		
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		List<MobileCommentInfo> listComments = new ArrayList<MobileCommentInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		//commentType ：1 我的评论    2  收到的评论
		Sort defSort = new Sort(Direction.DESC, "createTime");
//		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, defSort);
		PagerParam pageParam = new PagerParam(pageNumber, pageSize, defSort);
		if(type.intValue() == 1){
			Page<InteractComment> page = commentService.findUserCommentPage(new Long[]{user.getId()}, new Integer[] { site.getId() }, pageParam);
			if (page.getContent() != null) {
				for (InteractComment comment : page.getContent()) {
					mobileModelConvert.converUserComment(listComments, comment, siteUrl);
				}
			}
			Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(listComments, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
		}else{
			Page<InteractComment> page = commentService.findEvalUserCommentPage(new Long[]{user.getId()}, new Integer[] { site.getId() }, pageParam);
			if (page.getContent() != null) {
				for (InteractComment comment : page.getContent()) {
					MobileModelConvert.converEvalUserComment(listComments, comment, siteUrl);
				}
			}
			Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(listComments, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
		}
	}

	@RequestMapping(value = { "/m-commentPage.htx" })
	public void getCommentPage(String title, Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
//		if(StringUtil.isEmpty(title) || title.equals("_")){
//			title = "";
//			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("查询条件不能为空！")));
//			return;
//		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		List<MobileCommentInfo> listComments = new ArrayList<MobileCommentInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort defSort = new Sort(Direction.DESC, "createTime");
		PagerParam pageParam = new PagerParam(pageNumber, pageSize,defSort);
		InteractCommentFindPageListParams params = new InteractCommentFindPageListParams();
		if(commentService.getConfig_needCheck() == 1){
			params.setStatus(new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND });
		}else{
			params.setStatus(new Integer[] { InteractComment.SAVED, InteractComment.AUDITED, InteractComment.RECOMMEND });
		}
		params.setFtypes(new String[]{CommentType.Comment.toString(), CommentType.Info.toString()});
//		params.setStatus(new Integer[] { InteractComment.AUDITED, InteractComment.RECOMMEND });
		params.setContent(title);
		Page<InteractComment> page = commentService.findPage(params, pageParam);
		
		if (page.getContent() != null) {
			for (InteractComment comment : page.getContent()) {
//				MobileCommentInfo commentInfo = 
				mobileModelConvert.converUserComment(listComments, comment, siteUrl);
//				if (commentInfo != null) {
//					listComments.add(commentInfo);
//				}
			}
		}
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(listComments, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}
	
	/**
	 * 全文索引分页
	 * 
	 * @param keyWord
	 *            索引关键字
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-fulltext-page.htx" })
	public void getFulltextArticlePage(String keyWord, Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(keyWord)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("搜索关键字不能为空！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("不支持匿名用户搜索！")));
			return;
		}
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, null);
		Site site = mobileCommonService.getCurrentMobileSite();

		Page<Info> page = fulltext.page(new Integer[] { site.getId() }, null, null, null, null, new String[] { Info.NORMAL }, null, keyWord, null,
				null, null, null, null, null, null, pageable, null);

		if (page.getContent() != null) {
			for (Info info : page.getContent()) {
				MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, false, true);
				if (articleInfo != null) {
					listInfos.add(articleInfo);
				}
			}
		}

		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(listInfos, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	private void getFriendlinks(String number, Site site, Map<String, Object> map) {
		List<MobileFriendlink> list = new ArrayList<MobileFriendlink>();
		String siteUrl = site.getSiteUrl();
		AdSlot friendlinkType = adSlotService.findByNumber(number);
		if (friendlinkType != null) {
			List<Ad> ads = adService.findList(null, new Integer[] { friendlinkType.getId() }, new Sort(Direction.ASC, "seq"));
			for (Ad link : ads) {
				MobileFriendlink info = MobileModelConvert.convertFriendLink(link, siteUrl);
				if (info != null) {
					list.add(info);
				}
			}
			//if (!list.isEmpty()) {
			//	map.put("adv", list);
			//}
		}
		map.put("adv", list);
	}
	
	@RequestMapping(value = { "/m-listAd.htx" })
	public void listAd(String number, String token, HttpServletRequest request, HttpServletResponse response){
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		List<MobileFriendlink> list = new ArrayList<MobileFriendlink>();
		String siteUrl = site.getSiteUrl();
		AdSlot friendlinkType = adSlotService.findByNumber(number);
		if (friendlinkType != null) {
			List<Ad> ads = adService.findList(null, new Integer[] { friendlinkType.getId() }, new Sort(Direction.ASC, "seq"));
			for (Ad link : ads) {
				MobileFriendlink info = MobileModelConvert.convertFriendLink(link, siteUrl);
				if (info != null) {
					list.add(info);
				}
			}
			if (!list.isEmpty()) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(list)));
			}else{
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("没有找到此类型轮播图")));
			}
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("没有找到此类型轮播图")));
		}
	}

	/**
	 * 用户收藏文章
	 * 
	 * @param token
	 *            令牌
	 * @param articleId
	 *            文章主键
	 */
	@RequestMapping(value = { "/m-article-collect.htx" })
	public void collectArticle(String token, Integer articleId, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty("" + articleId)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章主键不能为空！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
			return;
		}
		
//		User user = userService.get(StringUtil.string2Int("" + userMap.get("UID")));
		Info info = infoQueryService.getInfoById(articleId);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		
		if(interactMarkService.hasMark(MarkType.InfoSave, articleId, user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章已经收藏!")));
			return;
		}

		String ip = Servlets.getRemoteAddr(request);
		InteractMark entity = new InteractMark();
		entity.setFtype(MarkType.InfoSave);
		entity.setFid(articleId);
		entity.setFtitle(info.getTitle());
		entity.setUserId(user.getId());
		entity.setIp(ip);
		entity.setSiteId(site.getId());
		try {
			interactMarkService.saveInteractMark(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章收藏失败!")));
			return;
		}
		snsOpLogService.operation(site.getId(), new Long(user.getId()).intValue(), SnsOpLog.FTYPE_COLLECT, info.getId(), info.getTitle(), user.getUserName());
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		logger.debug("info collect, message={}.", "fid:" + articleId + ",title:" + info.getTitle() + ",uid:" + userMap.get("UID"));	
			
			
//		Integer collect = infoCollectService.collectInfo(user.getId(), site, info);
//		if (collect == 1) {
//			snsOpLogService.operation(site, user.getId(), SnsOpLog.FTYPE_COLLECT, info.getId(), info.getTitle(), user.getRName());
//			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
//			logger.debug("info collect, message={}.", "fid:" + articleId + ",title:" + info.getTitle() + ",uid:" + userMap.get("UID"));
//		} else {
//			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("重复收藏")));
//		}
	}

	/**
	 * 用户取消收藏文章
	 * 
	 * @param token
	 *            令牌
	 * @param articleId
	 *            文章主键
	 */
	@RequestMapping(value = { "/m-article-reCollect.htx" })
	public void reCollectArticle(String token, Integer articleId, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty("" + articleId)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章主键不能为空！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		if(UserTypeUtil.outUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
			return;
		}
		
//		User user = userService.get(StringUtil.string2Int("" + userMap.get("UID")));
		Info info = infoQueryService.getInfoById(articleId);
		if (info == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(articleId + "未找到指定文章！")));
			return;
		}
		
		if(interactMarkService.hasMark(MarkType.InfoSave, articleId, user.getId())){
			try {
				interactMarkService.cancelInteractMark(articleId, MarkType.InfoSave, user.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章取消收藏失败！")));
				return;
			}
			snsOpLogService.clearByUIDFtypeFid(new Long(user.getId()).intValue(), SnsOpLog.FTYPE_COLLECT, info.getId());
			logger.debug("info re collect, message={}.", "fid:" + articleId + ",title:" + info.getTitle() + ",uid:" + userMap.get("UID"));
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
		} else {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("文章已取消收藏！")));
		}
		
		
//		infoCollectService.reCollectInfo(user.getId(), info.getId());
//		snsOpLogService.clearByUIDFtypeFid(user.getId(), SnsOpLog.FTYPE_COLLECT, info.getId());
//		logger.debug("info re collect, message={}.", "fid:" + articleId + ",title:" + info.getTitle() + ",uid:" + userMap.get("UID"));
//		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("SUCCESS")));
	}

	/**
	 * 用户收藏文章分页
	 * 
	 * @param token
	 *            令牌
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 */
	@RequestMapping(value = { "/m-collectPage.htx" })
	public void getCollectPage(String token, Integer pageNumber, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort sort = new Sort(Direction.DESC, "createTime");
		Site site = mobileCommonService.getCurrentMobileSite();
		Integer siteId = site.getId();
//		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_siteId", siteId);
		searchParams.put("EQ_userId", user.getId());
		searchParams.put("EQ_ftype", MarkType.InfoSave);
		Page<InteractMark> page = interactMarkService.findInteractMarkList(searchParams, pagerParam);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (page.getContent() != null) {
			for (InteractMark infoCollect : page.getContent()) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("collectId", infoCollect.getMarkId());
//				map.put("articleId", infoCollect.getFid());
//				map.put("title", infoCollect.getFtitle());
//				map.put("collectDate", sdf.format(infoCollect.getCreateTime()));
//				list.add(map);
				Info info = infoQueryService.getInfoById((int)infoCollect.getFid());
				list.add(MobileModelConvert.convertCollectInfo(infoCollect, info));
			}
		}
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(list, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * 首页资源分页 （文章、专题集合）
	 * 
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-home-resourcePage.htx" })
	public void getHomeResourcePage(Integer pageNumber, Integer pageSize, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		List<Map<String, Object>> listInfos = new ArrayList<Map<String, Object>>();

		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Node node = nodeQueryService.findRoot(site.getId());
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize);

		Map<String, Object> map = new HashMap<String, Object>();
		if (pageNumber == 1) {
			getFriendlinks(node.getNumber(), site, map);
		}

		Page<Map> page = mobileService.findPage(pagerParam, site.getId());

		if (page.getContent() != null) {
			for (Map content : page.getContent()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				String imageStr = "" + content.get("smallImage");
				String description = "" + content.get("description");
				dataMap.put("articleId", content.get("id"));
				dataMap.put("title", content.get("title"));
				dataMap.put("description", (StringUtils.isEmpty(description) || "null".equals(description)) ? "" : description);
				dataMap.put("dates", content.get("publishDate"));
				dataMap.put("image",
						(StringUtils.isNotEmpty(imageStr) && !"null".equals(imageStr)) ? MobileModelConvert.fullLinkPrefix(imageStr, siteUrl) : "");
				dataMap.put("browsers", content.get("views"));

				String images = "" + content.get("images");
				if (StringUtils.isNotEmpty(images) && !"null".equals(images)) {
					String[] imageArr = ("" + content.get("images")).split(",");
					List<InfoImage> infoImages = new ArrayList<InfoImage>();
					for (String image : imageArr) {
						image = MobileModelConvert.fullLinkPrefix(image, siteUrl);
						InfoImage infoImage = new InfoImage();
						infoImage.setImage(image);
						infoImage.setName("");
						infoImage.setText("");
						infoImages.add(infoImage);
					}
					dataMap.put("images", infoImages);
					dataMap.put("hasImages", 1);
				} else {
					dataMap.put("hasImages", 0);
				}
				if (StringUtils.isNotEmpty("" + content.get("type")) && ("" + content.get("type")).equals("special")) {
					dataMap.put("isSpecial", 1);
				} else {
					dataMap.put("isSpecial", 0);
				}
				dataMap.put("hasVideo", content.get("hasVideo"));
				listInfos.add(dataMap);
			}
		}

		map.put("data", listInfos);
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * 获取消息中心信息
	 * @param pageNumber
	 * @param pageSize
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-message-page.htx" })
	public void getMessagePage(Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
//		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, null);
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize);
		
		
		pagerParam.setSort(new Sort(Direction.DESC, "createDate"));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_site.id", site.getId());
		Page<CmsMessage> pageData = messageService.findCmsMessagePage(searchParams, pagerParam);
		List<CmsMessageModel> data = new ArrayList<CmsMessageModel>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(CmsMessage message : pageData){
			CmsMessageModel model = new CmsMessageModel();
			model.setContent(message.getContent());
			model.setCreateDate(format.format(message.getCreateDate()));
			model.setId(message.getId());
			model.setTitle(message.getTitle());
			data.add(model);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize, Integer.parseInt("" + pageData.getTotalElements()))));
	}
	
	/**
	 * 获取单条最新消息
	 * @param token
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/m-lastmessage.htx" })
	public void getLastMessage(String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		CmsMessage message = messageService.findLastMessage();
		CmsMessageModel model = new CmsMessageModel();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(message != null){
			model.setContent(message.getContent());
			model.setCreateDate(format.format(message.getCreateDate()));
			model.setId(message.getId());
			model.setTitle(message.getTitle());
		}
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(model)));
	}
	
	
	
	@Autowired
	private MobileModelConvert modelConvert;

}