package cn.mooc.app.module.widget.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.model.ResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.service.TokenService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;
import cn.mooc.app.module.cms.model.MobileArticleInfo;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.service.AttributeService;
import cn.mooc.app.module.cms.service.CmsCacheService;
import cn.mooc.app.module.cms.service.InfoBufferService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.InfoSpecialService;
import cn.mooc.app.module.cms.service.InfoVisitorService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.SpecialService;
import cn.mooc.app.module.cms.support.Servlets;
import cn.mooc.app.module.cms.util.UserTypeUtil;

@RequestMapping("/widget")
@Controller
public class CmsSpecialController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WebContext webContext;
	@Autowired
	private SiteService siteService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private InfoService infoQueryService;
	@Autowired
	private NodeService nodeQueryService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private InfoVisitorService infoVisitorService;
	@Autowired
	private InfoSpecialService infoSpecialService;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	private CmsCacheService cmsCacheService;
	
	@RequestMapping("/cms/articlePageList")
	public String articlePageList(Model model,HttpServletRequest request,HttpServletResponse response, String token, Integer cateId, Integer attrType, Integer hasPic, Integer hasAudio, String attr, Integer pageNumber, Integer pageSize,
			Integer isSpecial, Integer showDescendants){
		
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		int flag = 0;
		if (!resultData.isSuccess()) {
			
			return "/widget/authFail.html";
		}
		List<MobileArticleInfo> listInfos = new ArrayList<MobileArticleInfo>();

		if (pageNumber == null || pageNumber == 0) {
			pageNumber = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 5;
		}
		Sort sort = new Sort(Direction.ASC, "columnSort").and(new Sort(Direction.DESC, "priority", "publishDate", "id"));
		if (attrType != null && attrType == 2) {
			sort = new Sort(Direction.DESC, "views");
		}
		
		PagerParam pagerParam = new PagerParam(pageNumber, pageSize, sort);
		Site site = mobileCommonService.getCurrentMobileSite();
		if(site == null){
			site = this.siteService.getDefaultSite();
		}
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
				return "";
			}
			Special special = specialService.getSpecialById(cateId);
			if (special == null) {
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(cateId + "未找到指定专题！")));
				return "";
			}
			specialService.updateViews(special);
			Integer[] specialId = new Integer[] { cateId };


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
			Node node = nodeQueryService.getNodeById(cateId);
			if (node == null) {
				node = nodeQueryService.findRoot(site.getId());
			} else {
				nodeId = new Integer[] { node.getId() };
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
					articleInfo.setIsToDetail(flag);
					listInfos.add(articleInfo);
				}
			}
		}

		map.put("data", listInfos);
		
		return "/widget/cms/articlePageList.html";
		
	}
	
	@RequestMapping("/cms/articleDetail")
	public String articleDetail(Model model,HttpServletRequest request,HttpServletResponse response, String token, Integer articleId) {
		JsonMapper mapper = new JsonMapper();
		ResultData resultData = tokenService.checkToken(token);
		if (!resultData.isSuccess()) {
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError(resultData.getMsg(), resultData.getStatus())));
			return "";
		}
		if (articleId == null) {
			logger.debug("未指定文章");
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请指定文章主键！")));
			return "";
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
			return "";
		}
		MobileArticleInfo articleInfo = MobileModelConvert.convertInfo(info, true, false);
		

		infoBufferService.updateViews(articleId);
		infoSpecialService.updateViewsByInfoId(articleId);
		//Site site = mobileCommonService.getCurrentMobileSite();
		//String ip = Servlets.getRemoteAddr(request);
		//infoVisitorService.visite(user, site, info, ip);
				
		model.addAttribute("token", token);
		model.addAttribute("articleInfo", articleInfo);
		
		return "/widget/cms/articleDetail.html";
	}
	
	@RequestMapping("/cms/articlePage")
	public String articlePage(Model model,HttpServletRequest request,HttpServletResponse response, String token, Integer cateId, Integer pageNumber, Integer pageSize,
			Integer isSpecial, Integer showDescendants, String viewName){
		if(StringUtils.isNotEmpty(token)){
			request.getSession().setAttribute("token", token);
		}else{
			request.getSession().setAttribute("token", "");
		}
		
		if(StringUtils.isBlank(viewName)){
			viewName = "";
		}
		
		Site site = mobileCommonService.getCurrentMobileSite();

		if (site == null) {
			site = cmsCacheService.getDefaultSiteCache();
		}
		
		Node node = nodeQueryService.getNodeById(cateId);
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		String treeNumber = null;
		Sort sort = new Sort(Direction.ASC, "treeNumber");
		List<Node> nodeList = nodeQueryService.findNodeList(searchParams, site.getId(), cateId, treeNumber, sort);
				
		model.addAttribute("token", token);
		model.addAttribute("cateId", cateId);
		model.addAttribute("isSpecial", isSpecial);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("showDescendants", showDescendants);
		
		model.addAttribute("node", node);
		model.addAttribute("nodes", nodeList);
		
		return "/widget/cms/articlePage" + viewName + ".html";
		
	}
	

	
}
