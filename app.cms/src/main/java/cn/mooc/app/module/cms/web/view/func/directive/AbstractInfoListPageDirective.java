package cn.mooc.app.module.cms.web.view.func.directive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.beetl.core.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.model.InfoFindListPageParams;
import cn.mooc.app.module.cms.model.InfoModel;
import cn.mooc.app.module.cms.service.AttributeService;
import cn.mooc.app.module.cms.service.CmsModelService;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;

/**
 * AbstractInfoListPageDirective
 * 
 * @author csmooc
 * 
 */
public abstract class AbstractInfoListPageDirective {
	public static final String SITE_ID = "siteId";

	public static final String MODEL = "model";
	public static final String MODEL_ID = "modelId";

	/**
	 * 节点ID
	 */
	public static final String NODE_ID = "nodeId";
	/**
	 * 节点编码
	 */
	public static final String NODE = "node";
	public static final String NODE_NUMBER = "nodeNumber";

	public static final String EXCLUDE_NODE_ID = "excludeNodeId";
	public static final String EXCLUDE_NODE = "excludeNode";
	public static final String EXCLUDE_NODE_NUMBER = "excludeNodeNumber";

	public static final String ATTR_ID = "attrId";
	public static final String ATTR = "attr";

	public static final String SPECIAL_ID = "specialId";
	public static final String SPECIAL_TITLE = "specialTitle";

//	public static final String TAG = "tag";
//	public static final String TAG_ID = "tagId";
//	public static final String TAG_NAME = "tagName";

	public static final String USER = "user";
	public static final String USER_ID = "userId";

	public static final String PRIORITY = "priority";
	public static final String BEGIN_DATE = "beginDate";
	public static final String END_DATE = "endDate";
	public static final String TITLE = "title";
	public static final String INCLUDE_ID = "includeId";
	public static final String EXCLUDE_ID = "excludeId";
	public static final String STATUS = "status";
	public static final String IS_INCLUDE_CHILDREN = "isIncludeChildren";
	public static final String IS_MAIN_NODE_ONLY = "isMainNodeOnly";
	public static final String IS_WITH_IMAGE = "isWithImage";
	public static final String IS_WITH_VIDEO = "isWithVideo";
//	public static final String IS_PERM = "isPerm";

	public static final String P1 = "p1";
	public static final String P2 = "p2";
	public static final String P3 = "p3";
	public static final String P4 = "p4";
	public static final String P5 = "p5";
	public static final String P6 = "p6";
	
//	public static final String LIMIT = "limit";

	private AttributeService attributeService;
	private CmsModelService cmsModelService;
	private NodeService nodeService;
	private SysDataService sysDataService;
	private InfoService infoService;
	
	public Object doExecute(Map<String, Object> params, Context ctx, boolean isPage) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		nodeService = (NodeService) webApplicationContext.getBean(NodeService.class);
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);
		cmsModelService = (CmsModelService)webApplicationContext.getBean(CmsModelService.class);
		attributeService = (AttributeService)webApplicationContext.getBean(AttributeService.class);
		sysDataService = (SysDataService)webApplicationContext.getBean(SysDataService.class);
		infoService = (InfoService)webApplicationContext.getBean(InfoService.class);
		
		Integer[] siteId = Beetls.getIntegers(params, SITE_ID);
		if (siteId == null && params.get(SITE_ID) == null) {
			siteId = new Integer[] { siteService.getWebCurrentSiteId() };
		}

		Integer[] modelId = Beetls.getIntegers(params, MODEL_ID);
		String[] model = Beetls.getStrings(params, MODEL);

		Integer[] nodeId = Beetls.getIntegers(params, NODE_ID);
		String[] node = Beetls.getStrings(params, NODE);
		String[] nodeNumber = Beetls.getStrings(params, NODE_NUMBER);

		Integer[] excludeNodeId = Beetls.getIntegers(params, EXCLUDE_NODE_ID);
		String[] excludeNode = Beetls.getStrings(params, EXCLUDE_NODE);
		String[] excludeNodeNumber = Beetls.getStrings(params, EXCLUDE_NODE_NUMBER);

		Integer[] attrId = Beetls.getIntegers(params, ATTR_ID);
		String[] attr = Beetls.getStrings(params, ATTR);

		Integer[] specialId = Beetls.getIntegers(params, SPECIAL_ID);
		String[] specialTitle = Beetls.getStrings(params, SPECIAL_TITLE);

		Long[] userId = Beetls.getLongs(params, USER_ID);
		String[] user = Beetls.getStrings(params, USER);

		Integer[] priority = Beetls.getIntegers(params, PRIORITY);
		Date beginDate = Beetls.getDate(params, BEGIN_DATE);
		Date endDate = Beetls.getDate(params, END_DATE);
		String[] title = Beetls.getStrings(params, TITLE);

		Integer[] includeId = Beetls.getIntegers(params, INCLUDE_ID);
		Integer[] excludeId = Beetls.getIntegers(params, EXCLUDE_ID);
		String[] status = Beetls.getStrings(params, STATUS);
		if (status == null) {
			status = new String[] { Info.NORMAL };
		}

		boolean isIncludeChildren = Beetls.getBoolean(params, IS_INCLUDE_CHILDREN, false);
		boolean isMainNodeOnly = Beetls.getBoolean(params, IS_MAIN_NODE_ONLY, false);
		Boolean isWithImage = Beetls.getBoolean(params, IS_WITH_IMAGE);
		Boolean isWithVideo = Beetls.getBoolean(params, IS_WITH_VIDEO);

		String[] treeNumber = null;
		Integer[] mainNodeId = null;
		List<Integer> nodeIdList = getNodeIdList(nodeId, node, nodeNumber, siteId);
		nodeId = nodeIdList.toArray(new Integer[nodeIdList.size()]);
		if (isIncludeChildren) {
			treeNumber = getNodeTreeNumberList(nodeIdList).toArray(new String[nodeIdList.size()]);
			nodeId = null;
		} else if (isMainNodeOnly) {
			mainNodeId = nodeId;
			nodeId = null;
		}

		String[] excludeTreeNumber = null;
		Integer[] excludeMainNodeId = null;
		List<Integer> excludeNodeIdList = getNodeIdList(excludeNodeId, excludeNode, excludeNodeNumber, siteId);
		excludeMainNodeId = excludeNodeIdList.toArray(new Integer[excludeNodeIdList.size()]);
		if (isIncludeChildren) {
			excludeTreeNumber = getNodeTreeNumberList(excludeNodeIdList).toArray(new String[excludeNodeIdList.size()]);
			excludeMainNodeId = null;
		}

		List<Integer> modelIdList = getModelIdList(modelId, model, siteId);
		modelId = modelIdList.toArray(new Integer[modelIdList.size()]);

		List<Integer> attrIdList = getAttrIdList(attrId, attr, siteId);
		attrId = attrIdList.toArray(new Integer[attrIdList.size()]);

		List<Long> userIdList = getUserIdList(userId, user);
		userId = userIdList.toArray(new Long[userIdList.size()]);

		Integer[] p1 = Beetls.getIntegers(params, P1);
		Integer[] p2 = Beetls.getIntegers(params, P2);
		Integer[] p3 = Beetls.getIntegers(params, P3);
		Integer[] p4 = Beetls.getIntegers(params, P4);
		Integer[] p5 = Beetls.getIntegers(params, P5);
		Integer[] p6 = Beetls.getIntegers(params, P6);
		
		InfoFindListPageParams findParams = new InfoFindListPageParams();
		findParams.setModelId(modelId);
		findParams.setNodeId(nodeId);
		findParams.setAttrId(attrId);
		findParams.setSpecialId(specialId);
		findParams.setSiteId(siteId);
		findParams.setMainNodeId(mainNodeId);
		findParams.setUserId(userId);
		findParams.setTreeNumber(treeNumber);
		findParams.setSpecialTitle(specialTitle);
		findParams.setPriority(priority);
		findParams.setBeginDate(beginDate);
		findParams.setEndDate(endDate);
		findParams.setTitle(title);
		findParams.setIncludeId(includeId);
		findParams.setExcludeId(excludeId);
		findParams.setExcludeMainNodeId(excludeMainNodeId);
		findParams.setExcludeTreeNumber(excludeTreeNumber);
		findParams.setIsWithImage(isWithImage);
		findParams.setIsWithVideo(isWithVideo);
		findParams.setStatus(status);
		findParams.setP1(p1);
		findParams.setP2(p2);
		findParams.setP3(p3);
		findParams.setP4(p4);
		findParams.setP5(p5);
		findParams.setP6(p6);
		try {
			if (isPage) {
				PagerParam pageParam = Beetls.getPageable(params, ctx, new String[]{ "publishDate", "id"}, Direction.DESC);
				Page<Info> pageData = infoService.findPageByUserPermission(findParams, pageParam);
				return pageData;
//				List<Info> pageList = pageData.getContent();
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				List<InfoModel> modeLs = new ArrayList<InfoModel>();
//				for (Info info : pageList) {
//					InfoModel infoModel = new InfoModel();
//					infoModel.setId(info.getId());
//					infoModel.setNodeName(info.getNode().getDisplayName());
//					infoModel.setCreatorId(info.getCreatorId());
//					infoModel.setCreator(sysDataService.getUserInfoById(info.getCreatorId()).getUserName());
//					infoModel.setTitle(info.getTitle());
//					infoModel.setModeName(info.getModel().getName());
//					infoModel.setPublishDate(sdf.format(info.getPublishDate()));
//					infoModel.setPriority(info.getPriority());
//					infoModel.setViews(info.getViews());
//					infoModel.setComments(info.getComments());
//					infoModel.setStatus(info.getStatus());
//					infoModel.setSmallImage(info.getSmallImage());
//					infoModel.setAuthor(info.getAuthor() != null ? info.getAuthor() : infoModel.getCreator());
//					modeLs.add(infoModel);
//				}
//				PageRequest pageRequest = new PageRequest(pageParam.getPageStart(), pageParam.getRows(), null);
//				return new PageImpl<InfoModel>(modeLs, pageRequest, pageData.getTotalElements());
			} else {
				PagerParam pageParam = Beetls.getLimitable(params, ctx, new String[]{"priority", "publishDate", "id"}, Direction.DESC);
				List<Info> infos = infoService.findListByUserPermission(findParams, pageParam);
				return infos;
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				List<InfoModel> modeLs = new ArrayList<InfoModel>();
//				for (Info info : infos) {
//					InfoModel infoModel = new InfoModel();
//					infoModel.setId(info.getId());
//					infoModel.setNodeName(info.getNode().getDisplayName());
//					infoModel.setCreatorId(info.getCreatorId());
//					infoModel.setCreator(sysDataService.getUserInfoById(info.getCreatorId()).getUserName());
//					infoModel.setTitle(info.getTitle());
//					infoModel.setModeName(info.getModel().getName());
//					infoModel.setPublishDate(sdf.format(info.getPublishDate()));
//					infoModel.setPriority(info.getPriority());
//					infoModel.setViews(info.getViews());
//					infoModel.setComments(info.getComments());
//					infoModel.setStatus(info.getStatus());
//					infoModel.setSmallImage(info.getSmallImage());
//					infoModel.setAuthor(info.getAuthor() != null ? info.getAuthor() : infoModel.getCreator());
//					modeLs.add(infoModel);
//				}
//				return modeLs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Integer> getModelIdList(Integer[] modelId, String[] model, Integer[] siteId) {
		List<Integer> list = new ArrayList<Integer>();
		if (ArrayUtils.isNotEmpty(modelId)) {
			list.addAll(Arrays.asList(modelId));
		}
		List<CmsModel> models = cmsModelService.findByNumbers(model, siteId);
		for (CmsModel m : models) {
			list.add(m.getId());
		}
		return list;
	}

	private List<Integer> getNodeIdList(Integer[] nodeId, String[] node, String[] nodeNumber, Integer[] siteId) {
		List<Integer> list = new ArrayList<Integer>();
		if (ArrayUtils.isNotEmpty(nodeId)) {
			list.addAll(Arrays.asList(nodeId));
		}
		if (ArrayUtils.isNotEmpty(node)) {
			List<Node> nodes = nodeService.findByNumber(node, siteId);
			for (Node n : nodes) {
				list.add(n.getId());
			}
		}
		if (ArrayUtils.isNotEmpty(nodeNumber)) {
			List<Node> nodes = nodeService.findByNumberLike(nodeNumber, siteId);
			for (Node n : nodes) {
				list.add(n.getId());
			}
		}
		return list;
	}

	private List<String> getNodeTreeNumberList(List<Integer> nodeList) {
		List<String> numbers = new ArrayList<String>(nodeList.size());
		Node node;
		for (Integer nodeId : nodeList) {
			node = nodeService.getNodeById(nodeId);
			numbers.add(node.getTreeNumber());
		}
		return numbers;
	}

	private List<Integer> getAttrIdList(Integer[] attrId, String[] attr, Integer[] siteId) {
		List<Integer> list = new ArrayList<Integer>();
		if (ArrayUtils.isNotEmpty(attrId)) {
			list.addAll(Arrays.asList(attrId));
		}
		List<Attribute> attrs = attributeService.findByNumbers(attr, siteId);
		for (Attribute a : attrs) {
			list.add(a.getId());
		}
		return list;
	}

//	private List<Integer> getTagIdList(Integer[] tagId, String[] tag, Integer[] siteId) {
//		List<Integer> list = new ArrayList<Integer>();
//		if (ArrayUtils.isNotEmpty(tagId)) {
//			list.addAll(Arrays.asList(tagId));
//		}
//		List<Tag> tags = tagService.findByName(tag, siteId);
//		for (Tag t : tags) {
//			list.add(t.getId());
//		}
//		return list;
//	}

	private List<Long> getUserIdList(Long[] userId, String[] user) {
		List<Long> list = new ArrayList<Long>();
		if (ArrayUtils.isNotEmpty(userId)) {
			list.addAll(Arrays.asList(userId));
		}
		List<SysUserEntity> users = sysDataService.findUserByUsername(user);
		for (SysUserEntity u : users) {
			list.add(u.getId());
		}
		return list;
	}

	
}
