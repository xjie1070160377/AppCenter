package cn.mooc.app.module.sys.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.data.entity.SysMenuEntity;
import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.sys.model.UserRoleForm;
import cn.mooc.app.module.sys.service.WebSysService;


@Controller
@RequestMapping("/sys")
public class SysRoleController extends SysModuleController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebSysService webSysMenuService;
	
	
	@RequestMapping("/sysRoleList")
	public String sysRoleList(Model model, PagerParam pageParam) {
		return ModuleView("/sysRoleList");
	}

	@RequestMapping("/sysRoleListJson")
	@ResponseBody
	public Map<String, Object> sysRoleListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<SysRoleEntity> pageData = sysDataService.findSysRoleList(searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/sysRoleAdd")
	public String sysRoleAdd(Model model, SysRoleEntity entity) {
		
		model.addAttribute("entity", entity);
		
		return ModuleView("/sysRoleForm");
	}

	@RequestMapping("/sysRoleEdit")
	public String sysRoleEdit(Model model, long roleId) {

		SysRoleEntity entity = sysDataService.getSysRole(roleId);

		model.addAttribute("entity", entity);

		return ModuleView("/sysRoleForm");
	}

	@RequestMapping(value = "/sysRoleSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysRoleSave(Model model, SysRoleEntity entity) {

		if(StringUtils.isBlank(entity.getRoleName())){
			return HttpResponseUtil.failureJson("角色名不能为空");
		}

		//过滤非法字符
		String roleName = ValidatorUtil.filterUnSafeChar(entity.getRoleName()).trim();
		entity.setRoleName(roleName);
		
		try {
			this.sysDataService.saveSysRole(entity);
			webContext.sysUserLog(LogType.UserOpr, "保存角色信息：" + entity.getRoleName());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/sysRoleDel", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysRoleDel(long roleId) {
		// 删除
		boolean result = sysDataService.delSysRole(roleId);
		webContext.sysUserLog(LogType.UserOpr, "删除角色：" + roleId);
		return result;
	}
	
	@RequestMapping("/roleResourceRelSet")
	public String roleResourceRelSet(Model model,long roleId) {
		//设置一个角色有那些菜roleResourceRelSet资源的权限
		
		SysRoleEntity roleEntity = this.sysDataService.getSysRole(roleId);
		
		Set<SysMenuEntity> menus = roleEntity.getMenus();
		List<Long> menuIds = new ArrayList<Long>();
		for (SysMenuEntity menu : menus) {
			menuIds.add(menu.getId());
		}
		
		model.addAttribute("role", roleEntity);
		model.addAttribute("menuIds", StringUtils.join(menuIds,","));
		
		return ModuleView("/roleResourceRelSet");

	}
	
	@RequestMapping(value = "/saveRoleResource", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveRoleResource(Model model,long roleId,long[] menuIds) {
		boolean resultStatus = true;				
		//需要注意的是jsTree如果一个节点下所有子节点都被选中，则只会返回这个父节点的ID，下面的子节点ID不会返回
								
		resultStatus = this.sysDataService.saveRoleResource(roleId, menuIds);
		webContext.sysUserLog(LogType.UserOpr, "修改角色 " + roleId+ " 的授权信息： "+ (resultStatus?"成功":"修改失败"));
		if(resultStatus){
			webSysMenuService.reloadPermission();
		}
		
		Map<String, Object> resMap = HttpResponseUtil.resMapJson(resultStatus, resultStatus ? "" : "授权失败");
		
		return HttpResponseUtil.getAjaxFormJson(resMap); 
		
	}
	
	@RequestMapping(value = "/roleMenuTreeJson")
	@ResponseBody
	public List<Map<String,Object>> allMenuJson(Model model) {		
				
		List<Map<String,Object>> menusTree = webSysMenuService.getAllMenuForJstree();

		return menusTree;
		
	}
	
	@RequestMapping("/userRoleRelSet")
	public String userRoleRelSet(Model model,long userId) {
		//设置一个用户有那些角色的权限
		SysUserEntity userEntity = this.sysDataService.getUserInfoById(userId);
		//取所有角色
		List<SysRoleEntity> allRoles = this.sysDataService.getAvailableRoles();

		UserRoleForm userRole = new UserRoleForm();
		userRole.setId(userEntity.getId());
		userRole.setUserName(userEntity.getUserName());
		for (SysRoleEntity roleEntity : userEntity.getRoles()) {
			userRole.getRoles().add(roleEntity.getId());
		}
		
		model.addAttribute("allRoles", allRoles);
		model.addAttribute("userRole", userRole);
		
		return ModuleView("/userRoleRelSet");

	}
	
	@RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveUserRole(Model model,UserRoleForm userRoleForm) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		long userId = userRoleForm.getId();
		if(userId<=0){
			resMap = HttpResponseUtil.failureJson("没有指定用户");
			return HttpResponseUtil.getAjaxFormJson(resMap);
		}		
		
		try {
			resultStatus = this.sysDataService.saveUserRole(userRoleForm);
			String roles =  StringUtils.join(userRoleForm.getRoles().toArray(), ",");
			webContext.sysUserLog(LogType.UserOpr, "修改用户 " + userId+ " 拥有角色 " + roles +" 的授权信息： "+ (resultStatus?"成功":"修改失败"));
			resMap = HttpResponseUtil.resMapJson(resultStatus, resultStatus ? "" : "授权失败");
			if(resultStatus){
				webSysMenuService.reloadPermission();
			}
		} catch (SaveFailureException e) {
			logger.error("saveUserRole",e);
			
		}
		
		return HttpResponseUtil.getAjaxFormJson(resMap);
		
	}
	
	@RequestMapping(value = "/reloadPermission")
	@ResponseBody
	public Map<String,Object> reloadPermission(Model model) {		
				
		webSysMenuService.reloadPermission();
		
		return HttpResponseUtil.successJson();
		
	}
	
}