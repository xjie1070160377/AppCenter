package cn.mooc.app.module.cms.mcenter.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.annotation.SameUrlData;
import cn.mooc.app.core.data.entity.SysMenuEntity;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.NodeDetail;
import cn.mooc.app.module.cms.data.entity.NodeRole;
import cn.mooc.app.module.cms.data.entity.Workflow;
import cn.mooc.app.module.cms.service.CmsModelService;
import cn.mooc.app.module.cms.service.NodeRoleService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.cms.service.SiteService;
import cn.mooc.app.module.cms.service.WorkflowService;
import cn.mooc.app.module.cms.support.Constants;
import cn.mooc.app.module.cms.support.Servlets;


/**
 * NodeController 栏目控制器
 * 
 * @author hwt
 * @date 2016-05-11
 */
@Controller
@RequestMapping("/cms/node")
public class NodeController extends CmsModuleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NodeService nodeService;
	@Autowired
	private CmsModelService modelService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private NodeRoleService nodeRoleService;

	@RequestMapping("/nodeList")
	public String nodeList(Integer queryParentId, Boolean showDescendants, Model modelMap, PagerParam pageParam) {
		Integer siteId = getCurrentSiteId();
		if (queryParentId == null) {
			Node node = nodeService.findRoot(siteId);
			if (node != null) {
				queryParentId = node.getId();
			}
		}
		modelMap.addAttribute("queryParentId", queryParentId);
		modelMap.addAttribute("showDescendants", showDescendants);
		return ModuleView("/node/nodeList");
	}

	@RequestMapping(value = "/nodeTreeJson")
	@ResponseBody
	public List<Map<String, Object>> nodeTreeJson(Integer queryParentId, Model model) {
		Integer siteId = getCurrentSiteId();
		Long userId = webContext.getCurrentUserId();
		List<Map<String, Object>> tree = nodeService.getNodesForJstree(siteId, queryParentId, true, userId);
		return tree;
	}

	@RequestMapping("/nodeListJson")
	@ResponseBody
	public Map<String, Object> nodeListJson(Integer queryParentId, Boolean showDescendants, Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		Integer siteId = getCurrentSiteId();
		Long userId = webContext.getCurrentUserId();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		List<Map<String, Object>> treeData = nodeService.getNodesForGridTree(searchParams, siteId, queryParentId, showDescendants, userId);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rows", treeData);
		return resMap;
	}

	@RequestMapping("/nodeAdd")
	public String nodeAdd(Integer cid, Integer parentId, Integer modelId, Integer queryParentId, Boolean showDescendants, Model modelMap) throws Exception {
		Integer siteId = getCurrentSiteId();

		// 复制节点
		Node bean = null;
		if (cid != null) {
			bean = nodeService.getNodeById(cid);
		}
		// 父节点
		Node parent = null;
		if (bean != null) {
			parent = bean.getParent();
		} else if (parentId != null) {
			parent = nodeService.getNodeById(parentId);
		} else if(queryParentId != null){
			parent = nodeService.getNodeById(queryParentId);
		}else{
			parent = nodeService.findRoot(siteId);
			if (parent != null && queryParentId == null) {
				queryParentId = parent.getId();
			}
		}
		// 模型
		CmsModel model = null;
		if (modelId != null) {
			// 指定模型
			model = modelService.getCmsModelById(modelId);
		} else if (bean != null) {
			// 复制节点
			model = bean.getNodeModel();
		} else if (parent != null && parent.getParent() != null) {
			// 指定了父节点，且父节点不是根节点。
			model = parent.getNodeModel();
		} else if (nodeService.findRoot(siteId) != null) {
			// 根节点存在，获取默认节点模型
			model = modelService.findDefault(siteId, Node.NODE_MODEL_TYPE);
			// 没有定义节点模型
			if (model == null) {
				throw new Exception("node.error.nodeModelNotFound");
			}
		} else {
			// 根节点不存在，获取默认首页模型
			model = modelService.findDefault(siteId, Node.HOME_MODEL_TYPE);
			// 没有定义首页模型
			if (model == null) {
				throw new Exception("node.error.nodeHomeModelNotFound");
			}
		}

		String modelType = model.getType();
		List<CmsModel> nodeModelList = modelService.findList(siteId, modelType);
		List<CmsModel> infoModelList = modelService.findList(siteId, Info.MODEL_TYPE);
		CmsModel infoModel = null;
		if (bean != null && bean.getInfoModel() != null) {
			infoModel = bean.getInfoModel();
		} else if (parent != null) {
			infoModel = parent.getInfoModel();
		} else if (!infoModelList.isEmpty()) {
			infoModel = infoModelList.get(0);
		}
		List<Workflow> workflowList = workflowService.findList(siteId);

		modelMap.addAttribute("bean", new Node());
		modelMap.addAttribute("customs", new HashMap<String, String>());
		modelMap.addAttribute("workflowList", workflowList);
		modelMap.addAttribute("parent", parent);
		modelMap.addAttribute("contentNode", new Node());
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("nodeModelList", nodeModelList);
		modelMap.addAttribute("infoModel", infoModel);
		modelMap.addAttribute("infoModelList", infoModelList);
		modelMap.addAttribute("cid", cid);
		modelMap.addAttribute("queryParentId", queryParentId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute(Constants.OPRT, Constants.CREATE);
		modelMap.addAttribute("SYS_ADMIN_PATH", siteService.getSYS_ADMIN_PATH());
		modelMap.addAttribute("ctxPath", siteService.getCtxPath());
		return ModuleView("/node/node_form");
	}

	@RequestMapping("/nodeEdit")
	public String nodeEdit(Integer id, Integer modelId,  Integer queryParentId, Boolean showDescendants, Model modelMap) {

		Integer siteId = getCurrentSiteId();
		Node bean = nodeService.getNodeById(id);
		Node parent = bean.getParent();
		if (queryParentId == null) {
			queryParentId = parent.getId();
		}
		List<Node> contentNodes=null;
		if(bean.getP4()!=null) {
			//获取内容所属栏目
			contentNodes = nodeService.findByIds(new Integer[] {bean.getP4()});
		}
		if(contentNodes!=null&&contentNodes.size()>0) {
			modelMap.addAttribute("contentNode", contentNodes.get(0));
		}else {
			modelMap.addAttribute("contentNode", new Node());
		}
		modelMap.addAttribute("bean", bean);
		List<CmsModel> infoModelList = modelService.findList(siteId, Info.MODEL_TYPE);
		List<CmsModel> modelList = modelService.findList(siteId, Node.NODE_MODEL_TYPE);
//		List<CmsModel> typeList = new ArrayList<>();
//		CmsModel cms1= new CmsModel();
//		cms1.setId(1);
//		cms1.setName("1.一行二列之样式（宽:高=1:1）");
//		typeList.add(cms1);
//		CmsModel cms2= new CmsModel();
//		cms1.setId(2);
//		cms1.setName("2.一行三列之样式（宽:高=4:3）");
//		typeList.add(cms2);
		
		List<CmsModel> nodeModelList;
		if (bean.getParent() == null) {
			// 首页模型
			nodeModelList = modelService.findList(siteId, Node.HOME_MODEL_TYPE);
		} else {
			nodeModelList = modelList;
		}
		CmsModel model;
		// 允许修改栏目模型
		if (modelId != null) {
			model = modelService.getCmsModelById(modelId);
		} else {
			model = bean.getNodeModel();
		}
		List<Workflow> workflowList = workflowService.findList(siteId);
		modelMap.addAttribute("workflowList", workflowList);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("parent", parent);
		modelMap.addAttribute("bean", bean);
		modelMap.addAttribute("customs", bean.getCustoms());
		modelMap.addAttribute("modelList", modelList);
		modelMap.addAttribute("nodeModelList", nodeModelList);
		modelMap.addAttribute("infoModelList", infoModelList);
		modelMap.addAttribute("infoModel", bean.getInfoModel());
		modelMap.addAttribute("queryParentId", queryParentId);
		modelMap.addAttribute("showDescendants", showDescendants);
		modelMap.addAttribute(Constants.OPRT, Constants.EDIT);
		modelMap.addAttribute("SYS_ADMIN_PATH", siteService.getSYS_ADMIN_PATH());
		modelMap.addAttribute("ctxPath", siteService.getCtxPath());

		return ModuleView("/node/node_form");
	}

	@RequestMapping(value = "/nodeSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nodeSave(Integer queryParentId, Boolean showDescendants, Model model, Node entity, NodeDetail detail, Integer parentId, Integer nodeModelId, Integer infoModelId,
			Integer workflowId, HttpServletRequest request) {
		Integer siteId = getCurrentSiteId();
		Long userId = this.webContext.getCurrentUserId();
		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		Map<String, String> clobs = Servlets.getParameterMap(request, "clobs_");
		Long[] nodePermIds = getNodePermIds(parentId);
		Long[] infoPermIds = getInfoPermIds(parentId);
		try {			
			this.nodeService.saveNode(entity, detail, customs, clobs, parentId, nodeModelId, infoModelId, workflowId, userId, siteId, infoPermIds, nodePermIds);
			logger.info("save Node, name={}.", entity.getName());
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson("新增失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/nodeUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nodeUpdate(Integer queryParentId, Integer parentId, Boolean showDescendants, Model model, @ModelAttribute("entity") Node entity, @ModelAttribute("detail") NodeDetail detail,
		Integer nodeModelId, Integer infoModelId, Integer workflowId, HttpServletRequest request) {
		Integer siteId = getCurrentSiteId();
		Map<String, String> customs = Servlets.getParameterMap(request, "customs_");
		Map<String, String> clobs = Servlets.getParameterMap(request, "clobs_");
		Long[] nodePermIds = getNodePermIds(parentId);
		Long[] infoPermIds = getInfoPermIds(parentId);
		try {
			
			this.nodeService.updateNode(entity, detail, customs, clobs, nodeModelId, infoModelId, workflowId, siteId, parentId, infoPermIds, nodePermIds);
			
		} catch (SaveFailureException e) {
			e.printStackTrace();
			return HttpResponseUtil.failureJson(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		
			return HttpResponseUtil.failureJson("保存失败");
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/nodeDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nodeDel(Integer id) {
		Integer siteId = getCurrentSiteId();
		// 删除
		try {
			nodeService.delNode(id, siteId);
			logger.info("delete Node, id={}.", id);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/nodeDels", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> nodeDels(Integer[] ids) {
		Integer siteId = getCurrentSiteId();
		// 删除
		try {
			nodeService.delNodes(ids, siteId);
			logger.info("delete Nodes, ids={}.", ids);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchUpdate(Integer[] id, String[] name, String[] number, Boolean[] hidden) {
		Integer siteId = getCurrentSiteId();
		try {
			if (ArrayUtils.isNotEmpty(id)) {
				Node[] beans = nodeService.batchUpdate(id, name, number, hidden, siteId);
				for(Node bean : beans) {
					logger.info("update Node, name={}.", bean.getName());
				}
			}
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("/roleResourceRelSet")
	public String roleResourceRelSet(Model model, long roleId, String type) {
		SysRoleEntity roleEntity = this.sysDataService.getSysRole(roleId);
		List<NodeRole> nodeRoles = nodeRoleService.findByRoleId(roleId);
		List<Integer> infoPerms = new ArrayList<Integer>();
		List<Integer> nodePerms = new ArrayList<Integer>();
		for (NodeRole nr : nodeRoles) {
			if(nr.getInfoPerm()){
				infoPerms.add(nr.getNode().getId());
			}
			if(nr.getNodePerm()){
				nodePerms.add(nr.getNode().getId());
			}
		}
		model.addAttribute("role", roleEntity);
		if(type == "node"){
			model.addAttribute("nodeIds", StringUtils.join(nodePerms,","));
		}else{
			model.addAttribute("nodeIds", StringUtils.join(infoPerms,","));
		}
		return ModuleView("/node/roleResourceRelSet");
	}

	/**
	 * 保存角色的栏目和文档权限
	 */
	@RequestMapping(value = "/saveNodeRole", method = RequestMethod.POST)
	@SameUrlData
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveNodeRole(Model model, Long roleId, Integer[] infoPermIds, Integer[] nodePermIds) {
		boolean resultStatus = true;				
		nodeRoleService.update(roleId, infoPermIds, nodePermIds, getCurrentSiteId());
		webContext.sysUserLog(LogType.UserOpr, "修改角色 " + roleId+ " 的授权信息： "+ (resultStatus?"成功":"修改失败"));
		Map<String, Object> resMap = HttpResponseUtil.resMapJson(resultStatus, resultStatus ? "" : "授权失败");
		return HttpResponseUtil.getAjaxFormJson(resMap); 
		
	}
	
	@ModelAttribute
	public void preloadBean(Integer oid, org.springframework.ui.Model modelMap) {
		if (oid != null) {
			Node bean = nodeService.getNodeById(oid);
			if (bean != null) {
				modelMap.addAttribute("entity", bean);
				NodeDetail detail = bean.getDetail();
				if (detail == null) {
					detail = new NodeDetail();
					detail.setId(oid);
					detail.setNode(bean);
				}
				modelMap.addAttribute("detail", detail);
			}
		}
	}
	
	private Long[] getInfoPermIds(Integer parentId) {
		Collection<SysRoleEntity> roles = null;
		List<Long> ids = new ArrayList<>();
		if (parentId != null) {
			Node node = nodeService.getNodeById(parentId);
			if (node != null) {
				ids = node.getInfoPerms();
			}
		}
		if (ids.size() == 0) {
			roles = sysDataService.findSysRoleEnabledList();
			for (SysRoleEntity role : roles) {
				ids.add(role.getId());
			}
		}
		return ids.toArray(new Long[ids.size()]);
	}

	private Long[] getNodePermIds(Integer parentId) {
		Collection<SysRoleEntity> roles = null;
		List<Long> ids = new ArrayList<>();
		if (parentId != null) {
			Node node = nodeService.getNodeById(parentId);
			if (node != null) {
				ids = node.getInfoPerms();
			}
		}
		if (ids.size() == 0) {
			roles = sysDataService.findSysRoleEnabledList();
			for (SysRoleEntity role : roles) {
				ids.add(role.getId());
			}
		}
		return ids.toArray(new Long[ids.size()]);
	}
}