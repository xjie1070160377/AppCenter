package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.entity.NodeRole;
import cn.mooc.app.module.cms.data.rds.NodeRoleRepository;

@Service
@Transactional(readOnly = true)
public class NodeRoleService {

	@Autowired
	private NodeService nodeService;
	@Autowired
	private SysDataService dataService;
	@Autowired
	private NodeRoleRepository dao;
	@Autowired
	private SiteService siteService;
	@Autowired
	private SysDataService sysDataService;

	@Transactional
	public NodeRole save(Node node, long roleId, Boolean infoPerm, Boolean nodePerm) {
		NodeRole bean = null;
		List<NodeRole> nodeRoles = this.findByNodeIdAndRoleId(node.getId(), roleId);
		if(nodeRoles.size() > 0){
			bean = nodeRoles.get(0);
		}
		if(bean == null){
			bean = new NodeRole();
		}
		bean.setNode(node);
		bean.setRoleId(roleId);
		if(infoPerm != null){
			bean.setInfoPerm(infoPerm);
		}
		if(nodePerm != null){
			bean.setNodePerm(nodePerm);
		}
		bean.applyDefaultValue();
		bean = dao.save(bean);
		return bean;
	}

	/**
	 * @param roleId
	 * @param infoPermIds
	 *            本角色拥有的文章栏目权限
	 * @param nodePermIds
	 *            本角色拥有的栏目权限
	 */
	@Transactional
	public void update(Long roleId, Integer[] infoPermIds, Integer[] nodePermIds, Integer siteId) {
		List<Node> nodes = nodeService.getAllNodes(siteId);
		List<NodeRole> nrs = dao.findByRoleId(roleId);
		Integer nodeId;
		boolean contains, infoPerm, nodePerm;
		for (Node node : nodes) {
			contains = false;
			nodeId = node.getId();
			infoPerm = ArrayUtils.contains(infoPermIds, nodeId);
			nodePerm = ArrayUtils.contains(nodePermIds, nodeId);
			for (NodeRole nr : nrs) {
				if (nr.getNode().getId().equals(nodeId)) {
					if (infoPermIds != null) {
						nr.setInfoPerm(infoPerm);
					}
					if (nodePermIds != null) {
						nr.setNodePerm(nodePerm);
					}
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(node, roleId, infoPerm, nodePerm);
			}
		}
	}

	/**
	 * @param node
	 * @param infoPermIds
	 *            拥有本栏目文章权限的角色
	 * @param nodePermIds
	 *            拥有本栏目权限的角色
	 */
	@Transactional
	public void update(Node node, Long[] infoPermIds, Long[] nodePermIds) {
		Integer nodeId = node.getId();
		List<SysRoleEntity> roles = sysDataService.findSysRoleEnabledList();
		List<NodeRole> nrs = dao.findByNodeId(nodeId);
		long roleId;
		boolean contains, infoPerm, nodePerm;
		for (SysRoleEntity role : roles) {
			contains = false;
			roleId = role.getId();
			infoPerm = ArrayUtils.contains(infoPermIds, roleId);
			nodePerm = ArrayUtils.contains(nodePermIds, roleId);
			for (NodeRole nr : nrs) {
				if (nr.getRoleId() == roleId) {
					if (infoPermIds != null) {
						nr.setInfoPerm(infoPerm);
					}
					if (nodePermIds != null) {
						nr.setNodePerm(nodePerm);
					}
					contains = true;
					break;
				}
			}
			if (!contains) {
				save(node, roleId, infoPermIds == null ? null : infoPerm , nodePermIds == null ? null : nodePerm);
			}
		}
	}

	@Transactional
	public void preNodeDelete(Integer[] ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.deleteByNodeId(id);
			}
		}
	}

	@Transactional
	public NodeRole findOne(Integer id) {
		return dao.findOne(id);
	}

	public List<NodeRole> findByNodeIdAndRoleId(Integer nodeId, long roleId) {
		return dao.findByNodeIdAndRoleId(nodeId, roleId);
	}

	public List<NodeRole> findByNodeId(Integer nodeId) {
		return dao.findByNodeId(nodeId);
	}

	public List<NodeRole> findByRoleId(long roleId) {
		return dao.findByRoleId(roleId);
	}

	public List<NodeRole> findByRoleIds(List<Long> roleIds) {
		if (roleIds == null || roleIds.isEmpty()) {
			return new ArrayList<>();
		}
		Map<String, Object> searchParams = new HashMap<>();
		searchParams.put(SpecOperator.IN + "_roleId", roleIds);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<NodeRole> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec);
	}

}
