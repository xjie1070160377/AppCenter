package cn.mooc.app.module.api.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.api.model.MobileListData;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.ad.data.entity.AdSlot;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.mcenter.controller.DiscoverController;
import cn.mooc.app.module.cms.model.DiscoverServer;
import cn.mooc.app.module.cms.model.DiscoverServerItem;
import cn.mooc.app.module.cms.model.MobileCategoryInfo;
import cn.mooc.app.module.cms.model.MobileFriendlink;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.model.NodeFindListPageParams;
import cn.mooc.app.module.ad.service.AdService;
import cn.mooc.app.module.ad.service.AdSlotService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.SpecialService;
import cn.mooc.app.module.cms.service.UserNodeService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.DiscoverUtil;
import cn.mooc.app.module.cms.util.UserTypeUtil;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.service.InteractMarkService;


/**
 * @author hwt
 * @version 1.0
 * 
 */
@Controller
public class MobileNodeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private NodeService nodeQueryService;

//	@Autowired
//	private FriendlinkService friendlinkService;
//	@Autowired
//	private FriendlinkTypeService friendlinkTypeService;
	@Autowired
	private TokenService tokenService;
//	@Autowired
//	private AttentionService attentionService;
	@Autowired
	private SpecialService specialService;
	@Value("${number.redmooc}")
	private String redmoocNumber;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private AdSlotService adSlotService;
	@Autowired
	private AdService adService;
	@Autowired
	private InteractMarkService interactMarkService;
	@Autowired
	private UserNodeService userNodeService;
	@Autowired
	private CacheService redisService;
	@Autowired
	private WebContext webContext;

	/**
	 * 根据主键获取栏目信息
	 * 
	 * @param cateId
	 *            栏目主键
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-node.htx" })
	public void getCategory(Integer cateId, String token, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (cateId == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定栏目主键！")));
			return;
		}
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Node node = null;
		if(UserTypeUtil.outUser(user.getuType())){
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put(SpecOperator.EQ + "_id", cateId);
			searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
			
			node = nodeQueryService.findOne(searchParams);
		}else{
			node = nodeQueryService.getNodeById(cateId);
		}
		if (node == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(cateId + "未找到指定栏目！")));
			return;
		}
		MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(cateInfo)));
	}

	/**
	 * 根据栏目编码获取栏目信息
	 * 
	 * @param number
	 *            栏目编码
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-nodeByNumber.htx" })
	public void getCategoryByNumber(String number, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (StringUtils.isEmpty(number)) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("栏目编码不能为空！")));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		Node node = null;
		if(UserTypeUtil.outUser(user.getuType())){
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put(SpecOperator.EQ + "_number", number);
			searchParams.put(SpecOperator.EQ + "_site.id", site.getId());
			searchParams.put(SpecOperator.IN + "_p6", Arrays.asList(new Integer[]{2,3}));
			
			node = nodeQueryService.findOne(searchParams);
		}else{
			node = nodeQueryService.findByNumber(site.getId(), number);
		}
		if (node == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(number + "未找到指定栏目！")));
			return;
		}
		MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(cateInfo)));
	}

	/**
	 * 获取站点一级栏目
	 * 
	 * @param siteId
	 *            站点ID，为空返回默认站点
	 * @param type
	 *            空或0返回默认一级导航，1返回红客导航
	 * @param token
	 *            令牌
	 * @return
	 */
	@RequestMapping(value = { "/m-topNode.htx" })
	public void getTopList(Integer siteId, Integer type, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (siteId == null || siteId == 0) {
			Site site = mobileCommonService.getCurrentMobileSite();
			siteId = site.getId();
		}
		Integer parentId = null;
		if (type == null || type == 0) {
			Node root = nodeQueryService.findByNumber(siteId, redmoocNumber);
			if (root != null) {
				parentId = root.getId();
			}
		} else {
			Node root = nodeQueryService.findByNumber(siteId, redmoocNumber);
			if (root != null) {
				parentId = root.getId();
			}
		}
		Sort defSort = new Sort("treeNumber");
//		Limitable limitable = new LimitRequest(null, 0, defSort);
//		PagerParam pagerParam = new PagerParam(0, "treeNumber", Direction.ASC.toString());
		NodeFindListPageParams findParams = new NodeFindListPageParams();
		findParams.setSiteId(new Integer[] { siteId });
		findParams.setParentId(parentId);
		findParams.setP2(new Integer[] { 0 });
		findParams.setP1(new Integer[] { 0 });
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			findParams.setP6(new Integer[]{2,3});
		}
//		List<Node> list = nodeQueryService.findList(new Integer[] { siteId }, parentId, null, null, null,
//				new Integer[] { 0 }, null, null, null, null, null, null, defSort);
		List<Node> list = nodeQueryService.findList(findParams, defSort);
		List<MobileCategoryInfo> cateList = new ArrayList<MobileCategoryInfo>();
		for (Node node : list) {
			MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
			cateList.add(cateInfo);
		}
		orderByUserAttention(siteId, StringUtil.string2Int("" + userMap.get("UID")), cateList);
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(cateList, 1, cateList.size(), cateList.size())));
	}

	private void orderByUserAttention(Integer siteId, Integer userId, List<MobileCategoryInfo> cateList) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_siteId", siteId);
		searchParams.put("EQ_userId", userId);
		searchParams.put("EQ_ftype", MarkType.AttentionNode);
		List<InteractMark> attentions = interactMarkService.findInteractMarkList(searchParams);
		
//		List<Attention> attentions = attentionService.findByUserIdTypeId(siteId, userId, Attention.ATTENTION_TYPE_NODE);
		if (attentions != null && !attentions.isEmpty()) {
			List<MobileCategoryInfo> attentionList = new ArrayList<MobileCategoryInfo>();
			List<MobileCategoryInfo> normalList = new ArrayList<MobileCategoryInfo>();
			for (MobileCategoryInfo cate : cateList) {
				boolean attentioned = false;
				for (InteractMark attention : attentions) {
					if (attention.getFid() == cate.getCateId()) {
						attentioned = true;
						break;
					}
				}
				if (attentioned) {
					cate.setAttentioned(1);
					attentionList.add(cate);
				} else {
					normalList.add(cate);
				}
			}
			cateList.removeAll(cateList);
			cateList.addAll(attentionList);
			cateList.addAll(normalList);
		}
	}

	/**
	 * 获取子栏目列表
	 * 
	 * @param parentId
	 *            父栏目主键
	 * @param token
	 *            令牌
	 * @return
	 */
	@RequestMapping(value = { "/m-nodeList.htx" })
	public void getChildren(String parentId, String number, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if(StringUtil.isNull(parentId) && StringUtil.isNull(number)){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(parentId + "未找到指定栏目！")));
			return;
		}
		Integer pid = null;
		if(StringUtil.isNotEmpty(parentId)){
			if(StringUtils.isNumeric(parentId)){
				pid = StringUtil.strnull2Int(parentId);
			}else{
				number = parentId;
			}
		}else{
			if(StringUtils.isNumeric(number)){
				pid = StringUtil.strnull2Int(number);
			}
		}
		Node parent = null;
		if(StringUtil.isNotEmpty(pid)){
			parent = nodeQueryService.getNodeById(pid);
			if (parent == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(pid + "未找到指定栏目！")));
				return;
			}
		}else if(StringUtil.isNotEmpty(number)){
			Site site = mobileCommonService.getCurrentMobileSite();
			parent = nodeQueryService.findByNumber(site.getId(), number);
			if (parent == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(number + "未找到指定栏目！")));
				return;
			}
		}
		Site site = parent.getSite();
		Map<String, Object> map = new HashMap<String, Object>();
		getFriendlinks(parent.getNumber(), site, map);

		Sort defSort = new Sort("treeNumber");
//		Limitable limitable = new LimitRequest(null, 0, defSort);
		NodeFindListPageParams findParams = new NodeFindListPageParams();
		findParams.setSiteId(new Integer[] { site.getId() });
		findParams.setParentId(parent.getId());
		findParams.setP1(new Integer[] { 0 });
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			findParams.setP6(new Integer[]{2,3});
		}
		
//		List<Node> list = nodeQueryService.findList(new Integer[] { site.getId() }, parentId, null, null, null,
//				new Integer[] { 0 }, null, null, null, null, null, null, defSort);
		List<Node> list = nodeQueryService.findList(findParams, defSort);
		List<MobileCategoryInfo> cateList = new ArrayList<MobileCategoryInfo>();
		
		for (Node node : list) {
			logger.debug(node.getName());
			MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
			logger.debug(cateInfo.toString());
			cateList.add(cateInfo);
		}
		orderByUserAttention(site.getId(), StringUtil.string2Int("" + userMap.get("UID")), cateList);
		map.put("data", cateList);
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(map, 1, cateList.size(), cateList.size())));
	}

	/**
	 * 获取子栏目分页
	 * 
	 * @param nodeId
	 *            栏目主键
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-nodePage.htx" })
	public void getNodePage(Integer nodeId, String number, Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if(StringUtil.isNull(nodeId) && StringUtil.isNull(number)){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定栏目主键！")));
			return;
		}
		Node node = null;
		if(StringUtil.isNotEmpty(nodeId)){
			node = nodeQueryService.getNodeById(nodeId);
			if (node == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(nodeId + "未找到指定栏目！")));
				return;
			}
		}else if(StringUtil.isNotEmpty(number)){
			Site site = mobileCommonService.getCurrentMobileSite();
			node = nodeQueryService.findByNumber(site.getId(), number);
			if (node == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(number + "未找到指定栏目！")));
				return;
			}
		}
		
		
		if (node == null) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(nodeId + "未找到指定栏目！")));
			return;
		}
		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
//		Sort sort = new Sort(Direction.ASC, "treeNumber");
//		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
		PagerParam pageParam = new PagerParam(pageNumber, pageSize, "treeNumber", Direction.ASC.toString());
		Site site = mobileCommonService.getCurrentMobileSite();
		Map<String, Object> map = new HashMap<String, Object>();
		if (pageNumber == 1) {
			getFriendlinks(node.getNumber(), site, map);
		}

		List<MobileCategoryInfo> list = new ArrayList<MobileCategoryInfo>();
		NodeFindListPageParams findParams = new NodeFindListPageParams();
		findParams.setSiteId(new Integer[] { site.getId() });
		findParams.setParentId(node.getId());
		findParams.setIsHidden(false);
		findParams.setP1(new Integer[] { 0 });
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			findParams.setP6(new Integer[]{2,3});
		}
		
//		Page<Node> page = nodeQueryService.findPage(new Integer[] { site.getId() }, node.getId(), null, null, false,
//				new Integer[] { 0 }, null, null, null, null, null, null, pageParam);
		Page<Node> page = nodeQueryService.findPage(findParams, pageParam);
		if (page.getContent() != null) {
			for (Node bean : page.getContent()) {
				MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(bean);
				list.add(cateInfo);
			}
		}
		map.put("data", list);
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize, Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * 专题分页
	 * 
	 * @param pageNumber
	 *            页号
	 * @param pageSize
	 *            每页条数
	 * @param token
	 *            令牌
	 */
	@RequestMapping(value = { "/m-specialPage.htx" })
	public void getSpecialPage(Integer pageNumber, Integer pageSize, String token, HttpServletRequest request,
			HttpServletResponse response) {
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
//		Sort sort = new Sort(Direction.DESC, "id");
//		Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
		PagerParam pageParam = new PagerParam(pageNumber, pageSize, "id", Direction.DESC.toString());
		Site site = mobileCommonService.getCurrentMobileSite();
		String urlFull = site.getSiteUrl();
		Map<String, Object> map = new HashMap<String, Object>();

		List<MobileCategoryInfo> list = new ArrayList<MobileCategoryInfo>();
		Page<Special> page = specialService.findAll(site.getId(), null, pageParam);
		if (page.getContent() != null) {
			for (Special bean : page.getContent()) {
				MobileCategoryInfo cateInfo = new MobileCategoryInfo();
				cateInfo.setCateId(bean.getId());
				cateInfo.setCateName(bean.getTitle());
				cateInfo.setNumber(bean.getMetaKeywords() == null ? "" : bean.getMetaKeywords());
				cateInfo.setDescription(bean.getDescription() == null ? "" : bean.getDescription());
				cateInfo.setSmallImage(MobileModelConvert.fullLinkPrefix(bean.getSmallImage(), urlFull));
				cateInfo.setIsSpecial(1);
				cateInfo.setDetailUrl("");
				cateInfo.setArticles(0);
				cateInfo.setAttentions(0);
				cateInfo.setTreeLevel(0);
				cateInfo.setTreeNumber("");
				cateInfo.setTerminated(1);
				list.add(cateInfo);
			}
		}
		map.put("data", list);
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(map, pageNumber, pageSize,
						Integer.parseInt("" + page.getTotalElements()))));
	}

	/**
	 * 获取站点所有栏目
	 * 
	 * @param siteId
	 *            站点ID，为空返回默认站点
	 * @param token
	 *            令牌
	 * @return
	 */
	@RequestMapping(value = { "/m-nodes.htx" })
	public void getNodes(Integer siteId, String token, HttpServletRequest request, HttpServletResponse response) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		if (siteId == null || siteId == 0) {
			Site site = mobileCommonService.getCurrentMobileSite();
			siteId = site.getId();
		}
		Sort defSort = new Sort("treeNumber");
//		Limitable limitable = new LimitRequest(null, 0, defSort);
		NodeFindListPageParams findParams = new NodeFindListPageParams();
		findParams.setSiteId(new Integer[] { siteId });
		findParams.setP1(new Integer[] { 0 });
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.outUser(user.getuType())){
			findParams.setP6(new Integer[]{2,3});
		}
		
//		List<Node> list = nodeQueryService.findList(new Integer[] { siteId }, null, null, null, null,
//				new Integer[] { 0 }, null, null, null, null, null, null, defSort);
		List<Node> list = nodeQueryService.findList(findParams, defSort);
		List<MobileCategoryInfo> cateList = new ArrayList<MobileCategoryInfo>();
		for (Node node : list) {
			MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
			cateList.add(cateInfo);
		}
		orderByUserAttention(siteId, StringUtil.string2Int("" + userMap.get("UID")), cateList);
		Servlets.writeHtml(response, mapper.toJson(MobileListData.createSuccess(cateList, 1, cateList.size(), cateList.size())));
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
			if (!list.isEmpty()) {
				map.put("adv", list);
			}
		}
	}
	

	@RequestMapping(value = { "/m-allNodes.htx" })
	public void listUserAllNode(String token, HttpServletRequest request, HttpServletResponse response){
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		Integer siteId = site.getId();
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		Long userId = StringUtil.string2Long("" + userMap.get("UID"));
		List<MobileCategoryInfo> cateList = new ArrayList<MobileCategoryInfo>();
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		List<Node> list = null;
		if(UserTypeUtil.anonymousUser(user.getId())){
			list = nodeQueryService.findNonUserNode(siteId);
		}else if(UserTypeUtil.outUser(user.getuType())){
			list = nodeQueryService.findOutUserNode(siteId, userId);
			if(list == null || list.size() == 0){
				list = nodeQueryService.findNonUserNode(siteId);
			}
		}else{
			list = nodeQueryService.findUserAllNode(siteId, userId);
		}
		for (Node node : list) {
			MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
			cateList.add(cateInfo);
		}
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(cateList, 1, cateList.size(), cateList.size())));
	}
	
	@RequestMapping(value = { "/m-userNodes.htx" })
	public void listUserNode(String token, HttpServletRequest request, HttpServletResponse response){
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		Integer siteId = site.getId();
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		Long userId = StringUtil.string2Long("" + userMap.get("UID"));
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		List<Node> list = null;
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户没有权限使用此功能")));
			return;
		}else if(UserTypeUtil.outUser(user.getuType())){
			list = nodeQueryService.findOutUserNode(siteId, userId);
		}else{
			list = nodeQueryService.findUserNode(siteId, userId);
		}
		
		List<MobileCategoryInfo> cateList = new ArrayList<MobileCategoryInfo>();
		for (Node node : list) {
			MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
			cateList.add(cateInfo);
		}
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(cateList, 1, cateList.size(), cateList.size())));
	}
	
	@RequestMapping(value = { "/m-notUserNodes.htx" })
	public void listNotUserNode(String token, HttpServletRequest request, HttpServletResponse response){
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		Integer siteId = site.getId();
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		Long userId = StringUtil.string2Long("" + userMap.get("UID"));
		
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		List<Node> list = null;
		if(UserTypeUtil.anonymousUser(user.getuType())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户没有权限使用此功能")));
			return;
		}else if(UserTypeUtil.outUser(user.getuType())){
			list = nodeQueryService.findNotOutUserCustomNode(siteId, userId);
		}else{
			list = nodeQueryService.findCustomNode(siteId, userId);
		}
		
		List<MobileCategoryInfo> cateList = new ArrayList<MobileCategoryInfo>();
		for (Node node : list) {
			MobileCategoryInfo cateInfo = MobileModelConvert.convertCategory(node);
			cateList.add(cateInfo);
		}
		Servlets.writeHtml(response,
				mapper.toJson(MobileListData.createSuccess(cateList, 1, cateList.size(), cateList.size())));
	}
	
	@RequestMapping(value = { "/m-updateUserNode.htx" })
	public void updateUserNode(String token, String nodeIds, HttpServletRequest request, HttpServletResponse response){
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return;
		}
		Site site = mobileCommonService.getCurrentMobileSite();
		Integer siteId = site.getId();
		
		Map userMap = mapper.fromJson(resultData.getDataObj(), Map.class);
		SysUserEntity user = webContext.getSysUser(StringUtil.string2Int("" + userMap.get("UID")));
		if(UserTypeUtil.anonymousUser(user.getId())){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("匿名用户没有权限使用此功能")));
			return;
		}
		
		Long userId = StringUtil.string2Long("" + userMap.get("UID"));
		if(StringUtils.isEmpty(nodeIds)){
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("要订制的栏目不能为空！")));
			return;
		}
		String[] nodes = nodeIds.split(",");
		Integer[] ids = new Integer[nodes.length]; 
		int i = 0;
		for(String id : nodes){
			ids[i] = StringUtil.strnull2Int(id);
			i++;
		}
		userNodeService.updateUserNode(userId, ids);
		Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess("")));
	}
	


}
