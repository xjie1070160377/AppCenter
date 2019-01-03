package cn.mooc.app.module.sys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SysLogEntity;
import cn.mooc.app.core.data.entity.SysMenuEntity;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.data.nosql.SysLogRepository;
import cn.mooc.app.core.data.rds.SysMenuRepository;
import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.core.web.shiro.auth.db.DbShiroDataService;

@Service
public class WebSysService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private SysMenuRepository sysMenuRepository;
	@Autowired
	private SysLogRepository sysLogRepository;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	private DbShiroDataService dbShiroDataService;
	
	public List<Map<String,Object>> getMCenterNavMenu(Map<String, Object> searchParams){
		//
		List<SysMenuEntity> allMenus = this.findSysMenuList(searchParams);
		//排序
		this.menusSort(allMenus);
		List<Map<String,Object>> menusTree = new ArrayList<Map<String,Object>>();
		
		this.loadMenus(allMenus, menusTree);
		
		return menusTree;
	}
	

	public List<SysMenuEntity> findSysMenuList(Map<String, Object> searchParams){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SysMenuEntity> spec = SpecDynamic.buildSpec(filters.values());
		
		return sysMenuRepository.findAll(spec);
		
	}
	
	
	public void menusSort(List<SysMenuEntity> allMenus){
		 
		Collections.sort(allMenus, new Comparator<SysMenuEntity>() {

			@Override
			public int compare(SysMenuEntity o1, SysMenuEntity o2) {
				//小于-1、等于0, 大于1
                return o1.getSort() < o2.getSort() ? -1 : o1.getSort() == o2.getSort() ? 0 : 1;
			}
		});
		
		//数组反转
        //Collections.reverse(allMenus);
	}
	
	
	public void loadMenus(List<SysMenuEntity> allMenus,List<Map<String,Object>> menusTree){
		
		for (SysMenuEntity menu  : allMenus) {
			long menuId =  menu.getId();
			long parentId = menu.getParentId();
			//先遍历出第1级菜单
			if(parentId==0){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menu.getId());
				node.put("menuName",menu.getMenuName());
	            node.put("menuArea",menu.getMenuArea());
	            node.put("menuUrl",menu.getMenuUrl());
	            node.put("urlTarget",menu.getUrlTarget());
	            node.put("createTime",menu.getCreateTime());
	            node.put("pId",menu.getParentId());
	            
	            node.put("level",0);
	            node.put("parent","");
	            node.put("expanded",true);
	            
	            menusTree.add(node);
	            
	            if(hasChildNode(allMenus, menuId)){
	            	node.put("isLeaf",false);
	            	this.loadChildMenus(allMenus, menusTree, menuId);
	            }else{
	            	node.put("isLeaf",true);	            	
	            }
				
			}
		}
		
				
	}
	
	private void loadChildMenus(List<SysMenuEntity> allMenus, List<Map<String,Object>> menusTree,long currentMenuId){

		for (SysMenuEntity menu  : allMenus) {
			long menuId =  menu.getId();
			long parentId = menu.getParentId();
			//如果是当前菜单的子菜单
			if(parentId == currentMenuId){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menu.getId());
				node.put("menuName",menu.getMenuName());
	            node.put("menuArea",menu.getMenuArea());
	            node.put("menuUrl",menu.getMenuUrl());
	            node.put("urlTarget",menu.getUrlTarget());
	            node.put("createTime",menu.getCreateTime());
	            node.put("pId",menu.getParentId());
	            
	            node.put("level",1);
	            node.put("parent", parentId);
	            node.put("expanded",false);
	            
	            menusTree.add(node);
				//递归
				//this.loadChildMenus(allMenus, menusTree, menu);
				if(hasChildNode(allMenus, menuId)){
	            	node.put("isLeaf",false);
	            	this.loadChildMenus(allMenus, menusTree, menuId);
	            }else{
	            	node.put("isLeaf",true);
	            	
	            }
			}
			
		}

				
	}
	
	private boolean hasChildNode(List<SysMenuEntity> allMenus,long menuId){
		for (SysMenuEntity menu  : allMenus) {
			long parentId = menu.getParentId();
			if(parentId==menuId){
                return true;
            }
		}


        return false;
    }
	
	
	
	public List<Map<String,Object>> getMCenterNavMenuTree(){
		//一次加载出所有菜单树
		//
		List<SysMenuEntity> allMenus = sysMenuRepository.getMNavMenus();
		//排序
		this.menusSort(allMenus);
		List<Map<String,Object>> menusTree = new ArrayList<Map<String,Object>>();
		
		this.loadMenusTree(allMenus, menusTree);
		
		return menusTree;
	}
	
	public List<Map<String,Object>> getMCenterNavMenuTree(long userId){
		List<Map<String,Object>> allMenusTree = this.getMCenterNavMenuTree();
		return this.getMCenterNavMenuTree(userId, allMenusTree);
	}
	
	public List<Map<String,Object>> getMCenterNavMenuTree(long userId, List<Map<String,Object>> allMenusTree){
		
		List<Map<String,Object>> menusTree = new ArrayList<Map<String,Object>>();
		Map<Long,Object> userMenus = new HashMap<Long, Object>();
		Map<Long,Object> pMenus = new HashMap<Long, Object>();
		
		SysUserEntity sysUserEntity = sysDataService.getUserInfoById(userId);
		Set<SysRoleEntity> userRoles = sysUserEntity.getRoles();
		for (SysRoleEntity userRole : userRoles) {			
			for(SysMenuEntity menuEntity : userRole.getMenus()){
				userMenus.put(menuEntity.getId(), sysUserEntity);
				pMenus.put(menuEntity.getParentId(), sysUserEntity);
			}
		}
		
		
		
		for (Map<String,Object> menuMap : allMenusTree) {
			long id = (long) menuMap.get("id");
			if(userMenus.containsKey(id) || pMenus.containsKey(id)){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menuMap.get("id"));
				node.put("menuName",menuMap.get("menuName"));
	            node.put("menuArea",menuMap.get("menuArea"));
	            node.put("menuUrl",menuMap.get("menuUrl"));
	            node.put("urlTarget",menuMap.get("urlTarget"));
	            node.put("iconCls",menuMap.get("iconCls"));
	            node.put("pId",menuMap.get("pId"));
	            List<Map<String,Object>> childMenus = (List<Map<String, Object>>) menuMap.get("children");
	            this.loadChildPermissionMenus(userMenus, pMenus, childMenus, node);
				menusTree.add(node);
			}
			
		}
		
		return menusTree;
	}
	
	public void loadChildPermissionMenus(Map<Long,Object> userMenus, Map<Long,Object> pMenus,List<Map<String,Object>> childMenus, Map<String,Object> node){
		List<Map<String,Object>> childNodes = new ArrayList<Map<String,Object>>();	
		for (Map<String, Object> map : childMenus) {
			long id = (long) map.get("id");
			if(userMenus.containsKey(id)){
				Map<String,Object> child = new HashMap<String, Object>();
				
				child.put("id",map.get("id"));
				child.put("menuName",map.get("menuName"));
				child.put("menuArea",map.get("menuArea"));
				child.put("menuUrl",map.get("menuUrl"));
				child.put("urlTarget",map.get("urlTarget"));
				child.put("iconCls",map.get("iconCls"));
				child.put("pId",map.get("pId"));
				
				childNodes.add(child);
				
				//
				//this.loadChildPermissionMenus(userMenus, pMenus, childMenus, child);
			}
		}
		
		node.put("children", childNodes);
		
	}
	
	
	public void loadMenusTree(List<SysMenuEntity> allMenus,List<Map<String,Object>> menusTree){
		
		
		for (SysMenuEntity menu : allMenus) {
			//先遍历出第1级菜单
			if(menu.getParentId()==0){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menu.getId());
				node.put("menuName",menu.getMenuName());
	            node.put("menuArea",menu.getMenuArea());
	            node.put("menuUrl",menu.getMenuUrl());
	            node.put("urlTarget",menu.getUrlTarget());
	            node.put("iconCls",menu.getIconCls());
	            node.put("pId",0);
	            	            
	            menusTree.add(node);
				this.loadChildMenuTree(allMenus, node, menu);
			}
			
		}
		
				
	}
	
	private void loadChildMenuTree(List<SysMenuEntity> allMenus,Map<String,Object> rootNode,SysMenuEntity currentMenu){
		List<Map<String,Object>> childMenus = new ArrayList<Map<String,Object>>();	
		for (SysMenuEntity menu : allMenus) {
			//如果是当前菜单的子菜单
			if(menu.getParentId() == currentMenu.getId()){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menu.getId());
				node.put("menuName",menu.getMenuName());
	            node.put("menuArea",menu.getMenuArea());
	            node.put("menuUrl",menu.getMenuUrl());
	            node.put("urlTarget",menu.getUrlTarget());
	            node.put("iconCls",menu.getIconCls());
	            node.put("pId", menu.getParentId());
				
	            childMenus.add(node);
								
				//递归
				this.loadChildMenuTree(allMenus, node, menu);
			}
			
		}
		rootNode.put("children",childMenus);
				
	}
	
	public List<Map<String,Object>> getAllMenuForJstree(){
		//
		List<SysMenuEntity> allMenus = this.sysMenuRepository.findAll();
		//排序
		this.menusSort(allMenus);
		List<Map<String,Object>> menusTree = new ArrayList<Map<String,Object>>();
		
		this.loadMenusJsTree(allMenus, menusTree);
		
		return menusTree;
	}
	
	public void loadMenusJsTree(List<SysMenuEntity> allMenus,List<Map<String,Object>> menusTree){
		
		
		for (SysMenuEntity menu : allMenus) {
			//先遍历出第1级菜单
			if(menu.getParentId()==0){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menu.getId());
				node.put("text",menu.getMenuName());
	            node.put("iconCls",menu.getIconCls());
	            	            
	            menusTree.add(node);
				this.loadChildMenuJsTree(allMenus, node, menu);
			}
			
		}
		
				
	}
	
	private void loadChildMenuJsTree(List<SysMenuEntity> allMenus,Map<String,Object> rootNode,SysMenuEntity currentMenu){
		List<Map<String,Object>> childMenus = new ArrayList<Map<String,Object>>();	
		for (SysMenuEntity menu : allMenus) {
			//如果是当前菜单的子菜单
			if(menu.getParentId() == currentMenu.getId()){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id",menu.getId());
				node.put("text",menu.getMenuName());
	            node.put("iconCls",menu.getIconCls());
				
	            childMenus.add(node);
								
				//递归
				this.loadChildMenuTree(allMenus, node, menu);
			}
			
		}
		rootNode.put("children",childMenus);
				
	}
	
	public Page<SysLogEntity> findLogList(String columnFiled,Object keyWord,PagerParam pageParam){
		Sort sort = null;
		if(ArrayUtils.isNotEmpty(pageParam.getSidx())){
			sort = new Sort(Sort.Direction.fromString(pageParam.getSord()), pageParam.getSidx());			
		}
		PageRequest pageRequest = new PageRequest(pageParam.getPageStart(), pageParam.getRows(), sort);
				
		if (keyWord == null || StringUtils.isBlank(keyWord.toString())) {
			return sysLogRepository.findAll(pageRequest);
		}else{
			return sysLogRepository.findLogList(columnFiled,keyWord,pageRequest);
		}
		
	}
	
	public Page<SysLogEntity> findLogList(Map<String, Object> searchParams, PagerParam pageParam){
		Sort sort = null;
		if(ArrayUtils.isNotEmpty(pageParam.getSidx())){
			sort = new Sort(Sort.Direction.fromString(pageParam.getSord()), pageParam.getSidx());			
		}
		PageRequest pageRequest = new PageRequest(pageParam.getPageStart(), pageParam.getRows(), sort);
				
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageRequest, criteria, SysLogEntity.class);
		
	}
	
	public Page<SysUserExt> findUserExtList(Map<String, Object> searchParams, PagerParam pageParam){
		Sort sort = null;
		if(ArrayUtils.isNotEmpty(pageParam.getSidx())){
			sort = new Sort(Sort.Direction.fromString(pageParam.getSord()), pageParam.getSidx());			
		}
		PageRequest pageRequest = new PageRequest(pageParam.getPageStart(), pageParam.getRows(), sort);
				
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageRequest, criteria, SysUserExt.class);
		
	}
	
	public void reloadPermission() {
		this.dbShiroDataService.reloadPermission();
	}
	
}
