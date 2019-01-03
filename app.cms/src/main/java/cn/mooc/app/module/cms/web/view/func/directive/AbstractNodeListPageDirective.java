package cn.mooc.app.module.cms.web.view.func.directive;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.mooc.app.core.utils.Beetls;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.model.NodeFindListPageParams;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;

public abstract class AbstractNodeListPageDirective {
	/**
	 * 站点ID。整型。
	 */
	public static final String SITE_ID = "siteId";
	/**
	 * 父节点ID。整型。
	 */
	public static final String PARENT_ID = "parentId";
	/**
	 * 父节点编码。字符串。
	 */
	public static final String PARENT = "parent";
	/**
	 * 是否前台隐藏。布尔型。
	 */
	public static final String IS_HIDDEN = "isHidden";
	/**
	 * 是否真实节点（是否有信息的节点）。布尔型。
	 */
	public static final String IS_REAL_NODE = "isRealNode";
	/**
	 * 是否包含子节点。布尔型。
	 */
	public static final String IS_INCLUDE_CHILDREN = "isIncludeChildren";
	
	public static final String NODE = "node";
	
	public static final String PARENTNULL = "parentNull";

	public static final String P1 = "p1";
	public static final String P2 = "p2";
	public static final String P3 = "p3";
	public static final String P4 = "p4";
	public static final String P5 = "p5";
	public static final String P6 = "p6";
	
	public Object doExecute(Map<String, Object> params, Context ctx, boolean isPage) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		NodeService nodeService = (NodeService) webApplicationContext.getBean(NodeService.class);
		SiteService siteService = (SiteService) webApplicationContext.getBean(SiteService.class);

		
		Integer parentId = Beetls.getInteger(params, PARENT_ID);
		String parent = Beetls.getString(params, PARENT);
		if (parentId == null) {
			Integer sid = siteService.getWebCurrentSiteId();
			if (StringUtils.isNotBlank(parent)) {
				Node pnode = nodeService.findByNumber(sid, parent);
				if (pnode != null) {
					parentId = pnode.getId();
				}
			} else {
				Node root = nodeService.findRoot(sid);
				if (root != null) {
					parentId = root.getId();
				}
			}
		}
		Boolean isHidden = Beetls.getBoolean(params, IS_HIDDEN);
		if(isHidden == null){
			isHidden = false;
		}

		Boolean isRealNode = Beetls.getBoolean(params, IS_REAL_NODE);

		boolean isIncludeChildren = Beetls.getBoolean(params, IS_INCLUDE_CHILDREN, false);
		String treeNumber = null;
		if (isIncludeChildren && parentId != null) {
			Node pnode = nodeService.getNodeById(parentId);
			if (pnode != null) {
				treeNumber = pnode.getTreeNumber();
			}
			parentId = null;
		}

		Integer[] siteId = Beetls.getIntegers(params, SITE_ID);
		if (siteId == null && parentId == null && StringUtils.isBlank(treeNumber)) {
			siteId = new Integer[] { siteService.getWebCurrentSiteId() };
		}

		Integer[] p1 = Beetls.getIntegers(params, P1);
		Integer[] p2 = Beetls.getIntegers(params, P2);
		Integer[] p3 = Beetls.getIntegers(params, P3);
		Integer[] p4 = Beetls.getIntegers(params, P4);
		Integer[] p5 = Beetls.getIntegers(params, P5);
		Integer[] p6 = Beetls.getIntegers(params, P6);
		
		String[] nodeNumber = Beetls.getStrings(params, NODE);
		Boolean parentNull = Beetls.getBoolean(params, PARENTNULL);
		if(nodeNumber!=null && parentNull!=null && parentNull) {
			parentId = null;
		}
		NodeFindListPageParams findParams = new NodeFindListPageParams();
		findParams.setSiteId(siteId);
		findParams.setParentId(parentId);
		findParams.setTreeNumber(treeNumber);
		findParams.setIsRealNode(isRealNode);
		findParams.setIsHidden(isHidden);
		findParams.setP1(p1);
		findParams.setP2(p2);
		findParams.setP3(p3);
		findParams.setP4(p4);
		findParams.setP5(p5);
		findParams.setP6(p6);
		findParams.setNodeNumber(nodeNumber);
		try {
			if(isPage){
				PagerParam pageParam = Beetls.getPageable(params, ctx, "treeNumber", Direction.ASC);
				Page<Node> nodePage = nodeService.findPage(findParams, pageParam);
				return nodePage;
			}else{
				PagerParam pageParam = Beetls.getLimitable(params, ctx, "treeNumber", Direction.ASC);
				List<Node> list = nodeService.findList(findParams, pageParam);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
