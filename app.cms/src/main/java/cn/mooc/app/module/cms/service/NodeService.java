package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Global;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.NodeBuffer;
import cn.mooc.app.module.cms.data.entity.NodeDetail;
import cn.mooc.app.module.cms.data.entity.NodeRole;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.NodeRepository;
import cn.mooc.app.module.cms.model.NodeFindListPageParams;

/**
 * NodeService 栏目服务
 * 
 * @author hwt
 * @date 2016-05-11
 */
@Service
public class NodeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NodeRepository nodeRepository;
	@Autowired
	private SiteService siteService;
	@Autowired
	private CmsModelService modelService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private NodeBufferService nodeBufferService;
	@Autowired
	private NodeDetailService nodeDetailService;
	@Autowired
	private AttachmentRefService attachmentRefService;
	@Autowired
	private InfoNodeService infoNodeService;
	@Autowired
	private NodeRoleService nodeRoleService;
	@Autowired
	private WebContext webContext;
	@Autowired
	private SysDataService sysDataService;

	public Node getNodeById(Integer id) {
		Node entity = nodeRepository.findOne(id);
		return entity;
	}

	public Node findOne(Map<String, Object> searchParams) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());
		return nodeRepository.findOne(spec);
	}

	public List<Node> getAllNodes(Integer siteId) {
		return this.nodeRepository.findAll(siteId);
	}

	public Node findByNumber(Integer siteId, String number) {
		List<Node> list = nodeRepository.findBySiteIdAndNumber(siteId, number);
		return !list.isEmpty() ? list.get(0) : null;
	}

	public Page<Node> findNodeList(Map<String, Object> searchParams, PagerParam pageParam, Integer siteId) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());

		return nodeRepository.findAll(spec, pageParam.getPageRequest());

	}

	@Transactional
	public Node saveNode(Node entity, NodeDetail detail, Map<String, String> customs, Map<String, String> clobs,
			Integer parentId, Integer nodeModelId, Integer infoModelId, Integer workflowId, Long userId, Integer siteId,
			Long[] infoPermIds, Long[] nodePermIds) throws Exception {
		Site site = siteService.getSiteById(siteId);
		entity.applyDefaultValue();
		Node parent = null;
		if (parentId != null) {
			parent = getNodeById(parentId);
			entity.setParent(parent);
		}
		if (nodeModelId != null) {
			entity.setNodeModel(modelService.getCmsModelById(nodeModelId));
		}
		if (infoModelId != null) {
			entity.setInfoModel(modelService.getCmsModelById(infoModelId));
			entity.setRealNode(true);
		} else {
			// 首页必须为真实节点
			entity.setRealNode(entity.getParent() == null);
		}
		if (workflowId != null) {
			entity.setWorkflow(workflowService.getWorkflowById(workflowId));
		}
		entity.setCreatorId(userId);
		entity.setSite(site);

		entity.setClobs(clobs);

		entity.applyDefaultValue();
		treeSave(entity, parent);
		nodeRepository.save(entity);

		entity.setCustoms(customs);

		nodeDetailService.save(detail, entity);
		nodeBufferService.save(new NodeBuffer(), entity);
		attachmentRefService.update(entity.getAttachUrls(), Node.ATTACH_TYPE, entity.getId(), site.getGlobal());
		nodeRoleService.update(entity, infoPermIds, nodePermIds);
		return entity;
	}

	@Transactional
	public Node updateNode(Node entity, NodeDetail detail, Map<String, String> customs, Map<String, String> clobs,
			Integer nodeModelId, Integer infoModelId, Integer workflowId, Integer siteId, Integer parentId,
			Long[] infoPermIds, Long[] nodePermIds) throws Exception {
		Site site = siteService.getSiteById(siteId);
		entity.applyDefaultValue();
		if (nodeModelId != null) {
			entity.setNodeModel(modelService.getCmsModelById(nodeModelId));
		}
		if (infoModelId != null) {
			entity.setInfoModel(modelService.getCmsModelById(infoModelId));
			entity.setRealNode(true);
		} else {
			entity.setInfoModel(null);
			// 首页必须为真实节点
			entity.setRealNode(entity.getParent() == null);
		}
		if (workflowId != null) {
			entity.setWorkflow(workflowService.getWorkflowById(workflowId));
		} else {
			entity.setWorkflow(null);
		}
		if (!CollectionUtils.isEmpty(customs)) {
			entity.updateInfoCustoms(customs);
		}

		entity.getClobs().clear();
		if (!CollectionUtils.isEmpty(clobs)) {
			entity.getClobs().putAll(clobs);
		}
		entity.applyDefaultValue();
		entity = nodeRepository.save(entity);

		nodeDetailService.update(detail, entity);
		attachmentRefService.update(entity.getAttachUrls(), Node.ATTACH_TYPE, entity.getId(), site.getGlobal());
		nodeRoleService.update(entity, infoPermIds, nodePermIds);

		if (parentId != null && entity.getParent() != null && !parentId.equals(entity.getParent().getId())) {
			move(new Integer[] { entity.getId() }, parentId, siteId);
		}
		return entity;
	}

	public int move(Integer[] ids, Integer id, Integer siteId) {
		Node parent = nodeRepository.findOne(id);
		String parentTreeNumber = parent.getTreeNumber();
		long treeMax = parent.getTreeMaxLong();
		String modifiedTreeNumber, treeNumber;
		int count = 0;
		for (int i = 0, len = ids.length; i < len; i++) {
			nodeRepository.updateTreeMax(id, Node.long2hex(treeMax + 1));
			treeNumber = nodeRepository.findTreeNumber(ids[i]);
			modifiedTreeNumber = parentTreeNumber + "-" + Node.long2hex(treeMax++);
			count += nodeRepository.updateTreeNumber(treeNumber + "%", modifiedTreeNumber, treeNumber.length() + 1,
					siteId);
			nodeRepository.updateParentId(ids[i], id);
		}
		return count;
	}

	private void treeSave(Node bean, Node parent) {
		bean.setTreeMax(Node.long2hex(0));
		if (parent == null) {
			bean.setTreeLevel(0);
			bean.setTreeNumber(Node.long2hex(0));
			bean.setTreeMax(Node.long2hex(0));
		} else {
			bean.setTreeLevel(parent.getTreeLevel() + 1);
			String max = parent.getTreeMax();
			bean.setTreeNumber(parent.getTreeNumber() + "-" + max);
			long big = parent.getTreeMaxLong() + 1;
			parent.setTreeMax(Node.long2hex(big));
			nodeRepository.save(parent);
		}
	}

	@Transactional
	public boolean delNode(Integer id, Integer siteId) {
		Site site = siteService.getSiteById(siteId);
		try {
			Node bean = getNodeById(id);
			Set<Node> deleted = new HashSet<Node>();
			doDelete(bean, deleted, site.getGlobal());
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public void delNodes(Integer[] ids, Integer siteId) {
		Site site = siteService.getSiteById(siteId);
		try {
			for (Integer id : ids) {
				Node bean = getNodeById(id);
				Set<Node> deleted = new HashSet<Node>();
				doDelete(bean, deleted, site.getGlobal());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private Set<Node> doDelete(Node bean, Set<Node> deleted, Global global) {
		if (bean != null) {
			Collection<Node> children = bean.getChildren();
			if (!CollectionUtils.isEmpty(children)) {
				for (Node n : children) {
					doDelete(n, deleted, global);
				}
			}
			infoNodeService.deleteByNodeId(bean.getId());
			attachmentRefService.delete(Info.ATTACH_TYPE, bean.getId(), global);
			nodeRoleService.preNodeDelete(new Integer[] { bean.getId() });
			nodeRepository.delete(bean);
			deleted.add(bean);
		}
		return deleted;
	}

	public List<Map<String, Object>> getNodesForJstree(Integer siteId, Integer selectedId, Boolean showModel, Long userId) {
		Sort sort = new Sort(Direction.ASC, "treeNumber");
		List<Node> nodeList = findNodeList(null, siteId, null, null, sort, userId);

		List<Integer> openIdList = new ArrayList<Integer>();
		if (selectedId != null) {
			Node node = nodeRepository.findOne(selectedId);
			if (node != null) {
				while (node.getParent() != null) {
					openIdList.add(node.getParent().getId());
					node = node.getParent();
				}
			}
		}

		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		this.loadJsTree(nodeList, tree, openIdList, selectedId, showModel, userId);

		return tree;
	}

	public void loadJsTree(List<Node> nodeLs, List<Map<String, Object>> menusTree, List<Integer> openIdList,
			Integer selectedId, Boolean showModel, Long userId) {
		Set<SysRoleEntity> roleEntities = new HashSet<SysRoleEntity>();
		if(userId != null){
			SysUserEntity user = sysDataService.getUserInfoById(userId);
			roleEntities = user.getRoles();
		}
		
		for (Node node : nodeLs) {
			// 先遍历出第1级菜单
			if (node.getParent() == null && userId == null || !parentNodePerm(node, roleEntities) && userId != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", node.getId());
				map.put("text", node.getName());
				if (showModel != null && showModel) {
					map.put("text", node.getName() + "（" + node.getNodeModel().getName() + ","
							+ node.getInfoModel().getName() + "）");
				}
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("opened", true);
				if (selectedId != null) {
					if (selectedId.equals(node.getId())) {
						map1.put("selected", true);
					}
				}
				map.put("state", map1);

				menusTree.add(map);
				this.loadChildJsTree(nodeLs, map, node, openIdList, selectedId, showModel);
			}
		}
	}

	private void loadChildJsTree(List<Node> nodeLs, Map<String, Object> root, Node currentNode,
			List<Integer> openIdList, Integer selectedId, Boolean showModel) {
		List<Map<String, Object>> childMenus = new ArrayList<Map<String, Object>>();
		for (Node node : nodeLs) {
			// 如果是当前菜单的子菜单
			if (node.getParent() != null && node.getParent().getId() == currentNode.getId()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", node.getId());
				map.put("text", node.getName());
				if (showModel != null && showModel) {
					map.put("text", node.getName() + "（" + node.getNodeModel().getName() + ","
							+ node.getInfoModel().getName() + "）");
				}
				Map<String, Object> map1 = new HashMap<String, Object>();
				if (openIdList != null && !openIdList.isEmpty()) {
					if (openIdList.contains(node.getId())) {
						map1.put("opened", true);
					}
				}
				if (selectedId != null) {
					if (selectedId.equals(node.getId())) {
						map1.put("selected", true);
					}
				}
				map.put("state", map1);

				childMenus.add(map);

				// 递归
				this.loadChildJsTree(nodeLs, map, node, openIdList, selectedId, showModel);
			}
		}
		root.put("children", childMenus);
	}

	public List<Map<String, Object>> getNodesForGridTree(Map<String, Object> searchParams, Integer siteId,
			Integer parentId, Boolean showDescendants, Long userId) {
		Node parent = null;
		if (parentId != null) {
			parent = nodeRepository.findOne(parentId);
		}
		if (parent == null) {
			parent = findRoot(siteId);
			if (parent != null) {
				parentId = parent.getId();
			}
		}
		String treeNumber = null;
		if (showDescendants != null && showDescendants && parent != null) {
			treeNumber = parent.getTreeNumber();
		}
		Sort sort = new Sort(Direction.ASC, "treeNumber");
		List<Node> nodeList = findNodeList(searchParams, siteId, parentId, treeNumber, sort, userId);
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		this.loadGridTree(nodeList, tree);
		return tree;
	}

	public void loadGridTree(List<Node> nodeLs, List<Map<String, Object>> tree) {

		for (Node node : nodeLs) {
			// 转换菜单数据为map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", node.getId());
			map.put("name", node.getName());
			map.put("number", node.getNumber());
			map.put("treeLevel", node.getTreeLevel());
			map.put("nodeModel", node.getNodeModel().getName());
			map.put("infoModel", node.getInfoModel().getName());
			map.put("views", node.getViews());
			map.put("hidden", node.getHidden());
			map.put("pId", node.getParent() == null ? null : node.getParent().getId());

			tree.add(map);

		}

		for (Map<String, Object> map : tree) {
			Integer menuId = (Integer) map.get("id");
			Integer parentId = (Integer) map.get("pId");
			// 先遍历出第1级菜单
			if (parentId == null) {

				map.put("level", 0);
				map.put("parent", "");
				map.put("expanded", true);

				if (hasChildNode(tree, menuId)) {
					map.put("isLeaf", false);
					this.loadChildMenus(tree, menuId);
				} else {
					map.put("isLeaf", true);
				}

			}
		}
	}

	private boolean hasChildNode(List<Map<String, Object>> tree, Integer menuId) {
		for (Map<String, Object> node : tree) {
			Integer parentId = (Integer) node.get("pId");
			if (parentId == menuId) {
				return true;
			}
		}
		return false;
	}

	private void loadChildMenus(List<Map<String, Object>> tree, Integer currentNodeId) {

		for (Map<String, Object> node : tree) {
			Integer menuId = (Integer) node.get("id");
			Integer parentId = (Integer) node.get("pId");
			Integer treeLevel = (Integer) node.get("treeLevel");

			// 如果是当前菜单的子菜单
			if (parentId == currentNodeId) {
				node.put("level", treeLevel);
				node.put("parent", parentId);
				node.put("expanded", false);

				if (hasChildNode(tree, menuId)) {
					node.put("expanded", true);
					node.put("isLeaf", false);
					this.loadChildMenus(tree, menuId);
				} else {
					node.put("isLeaf", true);

				}
			}
		}
	}

	public List<Node> findNodeList(Map<String, Object> searchParams, Integer siteId, Integer parentId,
			String treeNumber, Sort sort) {
		if (searchParams == null) {
			searchParams = new HashMap<String, Object>();
		}
		if (StringUtils.isNotEmpty(treeNumber)) {
			searchParams.put(SpecOperator.BW + "_treeNumber", treeNumber);
		} else {
			searchParams.put(SpecOperator.EQ + "_parent.id", parentId);
		}
		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());
		return nodeRepository.findAll(spec, sort);
	}

	/**
	 * 带用户栏目权限
	 */
	public List<Node> findNodeList(Map<String, Object> searchParams, Integer siteId, Integer parentId,
			String treeNumber, Sort sort, Long userId) {
		if (searchParams == null) {
			searchParams = new HashMap<String, Object>();
		}
		if (StringUtils.isNotEmpty(treeNumber)) {
			searchParams.put(SpecOperator.BW + "_treeNumber", treeNumber);
		} else {
			searchParams.put(SpecOperator.EQ + "_parent.id", parentId);
		}
		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());
		spec = spec(userId, spec);
		return nodeRepository.findAll(spec, sort);
	}

	public Node findRoot(Integer siteId) {
		List<Node> list = nodeRepository.findBySiteIdAndParentIdIsNull(siteId);
		return !list.isEmpty() ? list.get(0) : null;
	}

	public List<Node> findList(NodeFindListPageParams findParams, Sort sort) {
		Specification<Node> spec = buildSpec(findParams);
		return nodeRepository.findAll(spec, sort);
	}

	public List<Node> findList(NodeFindListPageParams findParams, PagerParam pageParam) {
		if (pageParam.getRows() > 0) {
			Page<Node> page = findPage(findParams, pageParam);
			return page.getContent();
		} else {
			return findList(findParams, pageParam.getSort());
		}
	}

	public Page<Node> findPage(NodeFindListPageParams findParams, PagerParam pageParam) {

		Specification<Node> spec = buildSpec(findParams);

		Page<Node> page = nodeRepository.findAll(spec, pageParam.getPageRequest());
		return page;
	}

	public List<Node> findList(NodeFindListPageParams findParams) {

		Specification<Node> spec = buildSpec(findParams);

		Sort defSort = new Sort(Direction.ASC, "treeNumber");

		return nodeRepository.findAll(spec, defSort);
	}

	private Specification<Node> buildSpec(NodeFindListPageParams findParams) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (StringUtil.isNotNull(findParams.getSiteId())) {
			List<Integer> siteIdList = Arrays.asList(findParams.getSiteId());
			searchParams.put(SpecOperator.IN + "_site.id", siteIdList);
		}
		if (StringUtil.isNotNull(findParams.getParentId())) {
			searchParams.put(SpecOperator.EQ + "_parent.id", findParams.getParentId());
		}
		if (StringUtil.isNotNull(findParams.getTreeNumber())) {
			searchParams.put(SpecOperator.EQ + "_treeNumber", findParams.getTreeNumber());
		}
		if (StringUtil.isNotNull(findParams.getIsRealNode())) {
			searchParams.put(SpecOperator.EQ + "_realNode", findParams.getIsRealNode());
		}
		if (StringUtil.isNotNull(findParams.getIsHidden())) {
			searchParams.put(SpecOperator.EQ + "_hidden", findParams.getIsHidden());
		}
		if (ArrayUtils.isNotEmpty(findParams.getP1())) {
			List<Integer> p1List = Arrays.asList(findParams.getP1());
			searchParams.put(SpecOperator.IN + "_p1", p1List);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP2())) {
			List<Integer> p2List = Arrays.asList(findParams.getP2());
			searchParams.put(SpecOperator.IN + "_p2", p2List);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP3())) {
			List<Integer> p3List = Arrays.asList(findParams.getP3());
			searchParams.put(SpecOperator.IN + "_p3", p3List);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP4())) {
			List<Integer> p4List = Arrays.asList(findParams.getP4());
			searchParams.put(SpecOperator.IN + "_p4", p4List);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP5())) {
			List<Integer> p5List = Arrays.asList(findParams.getP5());
			searchParams.put(SpecOperator.IN + "_p5", p5List);
		}
		if (ArrayUtils.isNotEmpty(findParams.getP6())) {
			List<Integer> p6List = Arrays.asList(findParams.getP6());
			searchParams.put(SpecOperator.IN + "_p6", p6List);
		}
		if (ArrayUtils.isNotEmpty(findParams.getNodeNumber())) {
			List<String> nodeNumberList = Arrays.asList(findParams.getNodeNumber());
			searchParams.put(SpecOperator.IN + "_number", nodeNumberList);
		}
		if (StringUtil.isNotNull(findParams.getNodeModelNumber())) {
			searchParams.put(SpecOperator.EQ + "_nodeModel.number", findParams.getNodeModelNumber());
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());
		return spec;
	}

	private Specification<Node> buildSpec(Long userId, NodeFindListPageParams findParams) {
		return spec(userId, buildSpec(findParams));
	}

	private Specification<Node> spec(Long userId, Specification<Node> spec) {
		Specification<Node> sp = new Specification<Node>() {
			public Predicate toPredicate(Root<Node> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = spec.toPredicate(root, query, cb);
				Boolean isSuper = false;
				if(userId != null ){
					SysUserEntity userEntity = sysDataService.getUserInfoById(userId);
					isSuper = userEntity.isSuperUser();
				}
				if (userId != null && !isSuper) {
					Root<SysRoleEntity> root1 = query.from(SysRoleEntity.class);
					Join<Node, NodeRole> nodeRole = root.join("nodeRoles");
					Join<SysRoleEntity, SysUserEntity> users = root1.join("users");
					pred = cb.and(pred, cb.equal(nodeRole.get("roleId"), root1.get("id")));
					pred = cb.and(pred, cb.equal(nodeRole.get("nodePerm"), true));
					pred = cb.and(pred, cb.equal(users.get("id"), userId));
					query.distinct(true);
				}
				return pred;
			}
		};
		return sp;
	}


	public List<Node> findByNumber(String[] node, Integer[] siteId) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<String> nodeList = Arrays.asList(node);
		searchParams.put(SpecOperator.IN + "_number", nodeList);
		List<Integer> siteIdList = Arrays.asList(siteId);
		searchParams.put(SpecOperator.IN + "_site.id", siteIdList);

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());

		return nodeRepository.findAll(spec);
	}

	public List<Node> findByNumberLike(String[] nodeNumber, Integer[] siteId) {
		List<SpecExpression> filters = new ArrayList<SpecExpression>();
		for (String number : nodeNumber) {
			SpecExpression filter = new SpecExpression("number", SpecOperator.LIKE, number);
			filters.add(filter);
		}
		Specification<Node> spec = SpecDynamic.buildSpec(filters, false);

		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Integer> siteIdList = Arrays.asList(siteId);
		searchParams.put(SpecOperator.IN + "_site.id", siteIdList);
		Map<String, SpecExpression> filters2 = SpecExpression.parse(searchParams);
		Specification<Node> spec2 = SpecDynamic.buildSpec(filters2.values());

		spec2 = SpecDynamic.specAnd(spec2, spec);

		return nodeRepository.findAll(spec2);
	}

	@Transactional
	public Node refer(Integer nodeId) {
		Node node = nodeRepository.findOne(nodeId);
		node.setRefers(node.getRefers() + 1);
		return node;
	}

	@Transactional
	public void derefer(Node node) {
		node.setRefers(node.getRefers() - 1);
	}

	@Transactional
	public Node[] batchUpdate(Integer[] id, String[] name, String[] number, Boolean[] hidden, Integer siteId)
			throws Exception {
		Map<Integer, List<Integer>> listMap = new HashMap<Integer, List<Integer>>();
		Node[] beans = new Node[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			if (StringUtils.isEmpty(name[i])) {
				throw new SaveFailureException("名称不能为空！");
			}
			beans[i] = nodeRepository.findOne(id[i]);
			beans[i].setName(name[i]);
			beans[i].setNumber(number[i]);
			beans[i].setHidden(hidden[i]);
			nodeRepository.save(beans[i]);
			Node parent = beans[i].getParent();
			Integer parentId;
			if (parent != null) {
				parentId = parent.getId();
			} else {
				parentId = -1;
			}
			List<Integer> list = listMap.get(parentId);
			if (list != null) {
				list.add(id[i]);
			} else {
				list = new ArrayList<Integer>();
				list.add(id[i]);
				listMap.put(parentId, list);
			}
		}
		String parentTreeNumber, origTreeNumber, treeNumber;
		for (Entry<Integer, List<Integer>> entry : listMap.entrySet()) {
			Integer parentId = entry.getKey();
			List<Integer> ids = entry.getValue();
			if (parentId == -1) {
				continue;
			}
			int len = ids.size();
			if (nodeRepository.countByParentId(parentId) != len) {
				continue;
			}
			parentTreeNumber = nodeRepository.findTreeNumber(parentId);
			nodeRepository.appendModifiedFlag(parentTreeNumber + "-%", siteId);
			for (int i = 0; i < len; i++) {
				origTreeNumber = nodeRepository.findTreeNumber(ids.get(i));
				treeNumber = parentTreeNumber + "-" + Node.long2hex(i);
				nodeRepository.updateTreeNumber(origTreeNumber + "%", treeNumber, treeNumber.length() + 2, siteId);
			}
			// 修改父节点的treeMax
			nodeRepository.updateTreeMax(parentId, Node.long2hex(len));
		}
		return beans;
	}

	public List<Node> findByIds(Integer[] idArr) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Integer> list = Arrays.asList(idArr);
		searchParams.put(SpecOperator.IN + "_id", list);

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Node> spec = SpecDynamic.buildSpec(filters.values());

		return nodeRepository.findAll(spec);
	}

	public List<Node> findUserAllNode(Integer siteId, Long userId) {
		List<Node> fiexNodes = nodeRepository.findFixedNode(siteId);
		List<Node> userNodes = nodeRepository.findUserCustomNode(siteId, userId);
		if (fiexNodes == null) {
			fiexNodes = new ArrayList<Node>();
		}
		if (userNodes != null && userNodes.size() > 0) {
			fiexNodes.addAll(userNodes);
		}
		return fiexNodes;
	}

	public List<Node> findNonUserNode(Integer siteId) {
		return nodeRepository.findNonUserNode(siteId);
	}

	public List<Node> findOutUserNode(Integer siteId, Long userId) {
		return nodeRepository.findOutUserNode(siteId, userId);
	}

	public List<Node> findNotOutUserCustomNode(Integer siteId, Long userId) {
		return nodeRepository.findNotOutUserCustomNode(siteId, userId);
	}

	public List<Node> findUserNode(Integer siteId, Long userId) {
		return nodeRepository.findUserCustomNode(siteId, userId);
	}

	public List<Node> findCustomNode(Integer siteId, Long userId) {
		return nodeRepository.findNotUserCustomNode(siteId, userId);
	}

	/**
	 * 是否有父栏目权限
	 */
	public Boolean parentNodePerm(Node node, Set<SysRoleEntity> roles) {
		Node parent = node.getParent();
		if (parent == null) {
			return false;
		}
		Set<NodeRole> nodeRoles = parent.getNodeRoles();
		Boolean nodePerm = false;
		flag: for (NodeRole nr : nodeRoles) {
			for(SysRoleEntity role : roles){
				if (nr.getNodePerm().equals(true) && role.getId() == nr.getRoleId()) {
					nodePerm = true;
					break flag;
				}
			}
		}
		return nodePerm;
	}

}