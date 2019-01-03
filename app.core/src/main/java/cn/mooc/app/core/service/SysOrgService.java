package cn.mooc.app.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysOrg;
import cn.mooc.app.core.data.rds.SysOrgRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;

@Service
@Transactional(readOnly = true)
public class SysOrgService {
	
	@Autowired
	private SysOrgRepository sysOrgRepository;
	
	public List<SysOrg> getAllSysorg(){
		return this.dao.findAll();
	}
	
	public SysOrg getSysOrgById(Integer id) {
		SysOrg entity = sysOrgRepository.findOne(id);
		return entity;
	}

	public List<Map<String, Object>> getOrgsForJstree(Integer selectedId) {
		Sort sort = new Sort(Direction.ASC, "treeNumber");
		List<SysOrg> orgList = findOrgList(null, null, false, new HashMap<String, Object>(), sort);
		List<Integer> openIdList = new ArrayList<Integer>();
		if (selectedId != null) {
			SysOrg org = dao.findOne(selectedId);
			if (org != null) {
				while (org.getParent() != null) {
					openIdList.add(org.getParent().getId());
					org = org.getParent();
				}
			}
		}
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		this.loadJsTree(orgList, tree, openIdList, selectedId);

		return tree;
	}
	
	public Page<SysOrg> findAdList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysOrg> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysOrgRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	public void loadJsTree(List<SysOrg> orgList, List<Map<String, Object>> menusTree, List<Integer> openIdList, Integer selectedId) {
		for (SysOrg org : orgList) {
			// 先遍历出第1级菜单
			if (org.getParent() == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", org.getId());
				map.put("text", org.getName());
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("opened", true);
				if (selectedId != null) {
					if (selectedId.equals(org.getId())) {
						map1.put("selected", true);
					}
				}
				map.put("state", map1);

				menusTree.add(map);
				this.loadChildJsTree(orgList, map, org, openIdList, selectedId);
			}
		}
	}
	
	private void loadChildJsTree(List<SysOrg> orgList, Map<String, Object> root, SysOrg currentOrg, List<Integer> openIdList, Integer selectedId) {
		List<Map<String, Object>> childMenus = new ArrayList<Map<String, Object>>();
		for (SysOrg org : orgList) {
			// 如果是当前菜单的子菜单
			if (org.getParent() != null && org.getParent().getId() == currentOrg.getId()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", org.getId());
				map.put("text", org.getName());
				Map<String, Object> map1 = new HashMap<String, Object>();
				if (openIdList != null && !openIdList.isEmpty()) {
					if (openIdList.contains(org.getId())) {
						map1.put("opened", true);
					}
				}
				if (selectedId != null) {
					if (selectedId.equals(org.getId())) {
						map1.put("selected", true);
					}
				}
				map.put("state", map1);

				childMenus.add(map);

				// 递归
				this.loadChildJsTree(orgList, map, org, openIdList, selectedId);
			}
		}
		root.put("children", childMenus);
	}
	
	public List<Map<String, Object>> getOrgsForGridTree(Map<String, Object> searchParams, Integer parentId, Boolean showDescendants) {
		SysOrg parent = null;
		if (parentId != null) {
			parent = dao.findOne(parentId);
		}
		if (parent == null) {
			Sort sort = new Sort(Direction.ASC, "treeNumber");
			List<SysOrg> orgList = findOrgList("", parentId, showDescendants, searchParams, sort);
			List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
			this.loadGridTree(orgList, tree);
			return tree;
		}else{
			String treeNumber = null;
			if (showDescendants != null && showDescendants && parent != null) {
				treeNumber = parent.getTreeNumber();
			}
			Sort sort = new Sort(Direction.ASC, "treeNumber");
			List<SysOrg> orgList = findOrgList(treeNumber, parentId, showDescendants, searchParams, sort);
			List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
			this.loadGridTree(orgList, tree);
			return tree;
		}
	}
	
	public void loadGridTree(List<SysOrg> orgList, List<Map<String, Object>> tree) {

		for (SysOrg org : orgList) {
			// 转换菜单数据为map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", org.getId());
			map.put("name", org.getName());
			map.put("number", org.getNumber());
			map.put("treeLevel", org.getTreeLevel());
			map.put("fullName", org.getFullName());
			map.put("contacts", org.getContacts());
			map.put("phone", org.getPhone());
			map.put("pId", org.getParent() == null ? null : org.getParent().getId());

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

				if (hasChildOrg(tree, menuId)) {
					map.put("isLeaf", false);
					this.loadChildMenus(tree, menuId);
				} else {
					map.put("isLeaf", true);
				}

			}
		}
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

				if (hasChildOrg(tree, menuId)) {
					node.put("expanded", true);
					node.put("isLeaf", false);
					this.loadChildMenus(tree, menuId);
				} else {
					node.put("isLeaf", true);

				}
			}
		}
	}
	
	private boolean hasChildOrg(List<Map<String, Object>> tree, Integer menuId) {
		for (Map<String, Object> org : tree) {
			Integer parentId = (Integer) org.get("pId");
			if (parentId == menuId) {
				return true;
			}
		}
		return false;
	}
	
	public List<SysOrg> findOrgList(String topTreeNumber, Integer parentId,
			boolean showDescendants, Map<String, Object> params, Sort sort) {
		String treeNumber = null;
		if (showDescendants) {
			if (parentId != null) {
				SysOrg parent = dao.findOne(parentId);
				if (parent == null) {
					return null;
				}
				treeNumber = parent.getTreeNumber();
			} else {
				treeNumber = "";
			}
		}
		return dao.findAll(spec(topTreeNumber, parentId, treeNumber, params), sort);
	}
	
	public Page<SysOrg> findList(String topTreeNumber, Integer parentId,
			boolean showDescendants, Map<String, Object> params, PagerParam pageParam) {
		String treeNumber = null;
		if (showDescendants) {
			if (parentId != null) {
				SysOrg parent = dao.findOne(parentId);
				if (parent == null) {
					return null;
				}
				treeNumber = parent.getTreeNumber();
			} else {
				treeNumber = "";
			}
		}
		return dao.findAll(spec(topTreeNumber, parentId, treeNumber, params),
				pageParam.getPageRequest());
	}


	private Specification<SysOrg> spec(final String topTreeNumber,
			final Integer parentId, final String treeNumber,
			Map<String, Object> searchParams) {
		Map<String, SpecExpression> filters = SpecExpression
				.parse(searchParams);
		final Specification<SysOrg> fs = SpecDynamic.buildSpec(filters
				.values());
		Specification<SysOrg> sp = new Specification<SysOrg>() {
			public Predicate toPredicate(Root<SysOrg> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fs.toPredicate(root, query, cb);
				if (StringUtils.isNotBlank(topTreeNumber)) {
					Path<String> tnPath = root.get("treeNumber");
					pred = cb.and(pred, cb.like(tnPath, topTreeNumber + "%"));
				}
				if (treeNumber != null) {
					Path<String> tnPath = root.get("treeNumber");
					pred = cb.and(pred, cb.like(tnPath, treeNumber + "%"));
				} else if (parentId != null) {
					Path<Integer> pidPath = root.get("parent").get("id");
					pred = cb.and(pred, cb.equal(pidPath, parentId));
				} 
//				else {
//					pred = cb.and(pred, root.get("parent").isNull());
//				}
				return pred;
			}
		};
		return sp;
	}

	public List<SysOrg> findList() {
		return dao.findAll(new Sort("treeNumber"));
	}

	public List<SysOrg> findList(String treeNumber) {
		if (StringUtils.isNotBlank(treeNumber)) {
			return dao.findByTreeNumberStartingWith(treeNumber, new Sort(
					"treeNumber"));
		} else {
			return dao.findAll(new Sort("treeNumber"));
		}
	}

	public SysOrg findRoot() {
		List<SysOrg> roots = dao.findByParentIdIsNull();
		if (roots.isEmpty()) {
			return null;
		} else {
			return roots.get(0);
		}
	}
	
	public SysOrg findByOid(Integer oid){
		return dao.findByOid(oid);
	}

	public SysOrg get(Integer id) {
		return dao.findOne(id);
	}

	@Transactional
	public SysOrg save(SysOrg bean, Integer parentId) {
		SysOrg parent = null;
		if (parentId != null) {
			parent = dao.findOne(parentId);
			bean.setParent(parent);
			parent.addChild(bean);
		}
		bean.applyDefaultValue();
		treeSave(bean, parent);
		bean = dao.save(bean);
		return bean;
	}

	@Transactional
	private void treeSave(SysOrg bean, SysOrg parent) {
		bean.setTreeMax(SysOrg.long2hex(0));
		if (parent == null) {
			String treeMax = dao.findMaxRootTreeNumber();
			if(StringUtil.isNull(treeMax)){
				treeMax = "0";
			}
			long maxLong = SysOrg.hex2long(treeMax);
			treeMax = SysOrg.long2hex(maxLong + 1);
			bean.setTreeLevel(0);
			bean.setTreeNumber(treeMax);
			bean.setTreeMax(SysOrg.long2hex(0));
		} else {
			bean.setTreeLevel(parent.getTreeLevel() + 1);
			String treeMax = parent.getTreeMax();
			bean.setTreeNumber(parent.getTreeNumber() + "-" + treeMax);
			long big = parent.getTreeMaxLong() + 1;
			parent.setTreeMax(SysOrg.long2hex(big));
			dao.save(parent);
		}
	}
	
	@Transactional
	public SysOrg[] batchUpdate(Integer[] id) throws Exception {
		Map<Integer, List<Integer>> listMap = new HashMap<Integer, List<Integer>>();
		SysOrg[] beans = new SysOrg[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = dao.findOne(id[i]);
			SysOrg parent = beans[i].getParent();
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
			if (dao.countByParentId(parentId) != len) {
				continue;
			}
			parentTreeNumber = dao.findTreeNumber(parentId);
			dao.appendModifiedFlag(parentTreeNumber + "-%");
			for (int i = 0; i < len; i++) {
				origTreeNumber = dao.findTreeNumber(ids.get(i));
				treeNumber = parentTreeNumber + "-" + SysOrg.long2hex(i);
				dao.updateTreeNumber(origTreeNumber + "%", treeNumber, treeNumber.length() + 2);
			}
			// 修改父节点的treeMax
			dao.updateTreeMax(parentId, SysOrg.long2hex(len));
		}
		return beans;
	}

	@Transactional
	public SysOrg update(SysOrg bean, Integer parentId) {
		bean.applyDefaultValue();
		SysOrg entity = dao.getOne(bean.getId());
		entity.setAddress(bean.getAddress());
		entity.setContacts(bean.getContacts());
		entity.setDescription(bean.getDescription());
		entity.setFax(bean.getFax());
		entity.setFullName(bean.getFullName());
		entity.setName(bean.getName());
		entity.setNumber(bean.getNumber());
		entity.setPhone(bean.getPhone());
		dao.save(entity);

		SysOrg parent = entity.getParent();
		if ((parent != null && !parent.getId().equals(parentId))
				|| (parent == null && parentId != null)) {
			move(new Integer[] { entity.getId() }, parentId);
		}

		return entity;
	}

	@Transactional
	public SysOrg[] batchUpdate(Integer[] id, String[] name, String[] number,
			String[] phone, String[] address, boolean isUpdateTree) {
		Map<Integer, List<Integer>> listMap = new HashMap<Integer, List<Integer>>();
		SysOrg[] beans = new SysOrg[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			beans[i] = dao.findOne(id[i]);
			beans[i].setName(name[i]);
			beans[i].setNumber(number[i]);
			beans[i].setPhone(phone[i]);
			beans[i].setAddress(address[i]);
			dao.save(beans[i]);
			if (isUpdateTree) {
				SysOrg parent = beans[i].getParent();
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
		}
		String parentTreeNumber, origTreeNumber, treeNumber;
		for (Entry<Integer, List<Integer>> entry : listMap.entrySet()) {
			Integer parentId = entry.getKey();
			List<Integer> ids = entry.getValue();
			if (parentId == -1) {
				int len = ids.size();
				if (dao.countRoot() != len) {
					continue;
				}
				dao.appendModifiedFlag("%");
				for (int i = 0; i < len; i++) {
					origTreeNumber = dao.findTreeNumber(ids.get(i));
					treeNumber = SysOrg.long2hex(i);
					dao.updateTreeNumber(origTreeNumber + "%", treeNumber,
							treeNumber.length() + 2);
				}
			} else {
				parentTreeNumber = dao.findTreeNumber(parentId);
				dao.appendModifiedFlag(parentTreeNumber + "-%");
				int len = ids.size();
				if (dao.countByParentId(parentId) != len) {
					continue;
				}
				for (int i = 0; i < len; i++) {
					origTreeNumber = dao.findTreeNumber(ids.get(i));
					treeNumber = parentTreeNumber + "-" + SysOrg.long2hex(i);
					dao.updateTreeNumber(origTreeNumber + "%", treeNumber,
							treeNumber.length() + 2);
				}
				// 修改父节点的treeMax
				dao.updateTreeMax(parentId, SysOrg.long2hex(len));
			}
		}
		return beans;
	}

	@Transactional
	public int move(Integer[] ids, Integer id) {
		int count = 0;
		String modifiedTreeNumber, treeNumber;
		if (id == null) {
			long treeMax = SysOrg.hex2long(dao.findMaxRootTreeNumber()) + 1;
			for (int i = 0, len = ids.length; i < len; i++) {
				treeNumber = dao.findTreeNumber(ids[i]);
				modifiedTreeNumber = SysOrg.long2hex(treeMax++);
				count += dao.updateTreeNumber(treeNumber + "%",
						modifiedTreeNumber, treeNumber.length() + 1);
				dao.updateParentId(ids[i], id);
			}
		} else {
			SysOrg parent = dao.findOne(id);
			String parentTreeNumber = parent.getTreeNumber();
			long treeMax = parent.getTreeMaxLong();
			for (int i = 0, len = ids.length; i < len; i++) {
				dao.updateTreeMax(id, SysOrg.long2hex(treeMax + 1));
				treeNumber = dao.findTreeNumber(ids[i]);
				modifiedTreeNumber = parentTreeNumber + "-"
						+ SysOrg.long2hex(treeMax++);
				count += dao.updateTreeNumber(treeNumber + "%",
						modifiedTreeNumber, treeNumber.length() + 1);
				dao.updateParentId(ids[i], id);
			}
		}
		return count;
	}

	private SysOrg doDelete(Integer id) {
		SysOrg entity = dao.findOne(id);
		if (entity != null) {
			dao.delete(entity);
		}
		return entity;
	}

	@Transactional
	public SysOrg delete(Integer id) {
		return doDelete(id);
	}

	@Transactional
	public SysOrg[] delete(Integer[] ids) {
		SysOrg[] beans = new SysOrg[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = doDelete(ids[i]);
		}
		return beans;
	}

	@Autowired
	private SysOrgRepository dao;
}
