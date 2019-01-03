package cn.mooc.app.module.sys.mcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.data.entity.SysMenuEntity;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.sys.service.WebSysService;

@Controller
@RequestMapping("/sys")
public class SysMenuController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebSysService webSysMenuService;

	@RequestMapping("/sysMenuList")
	public String sysMenuList(Model model, PagerParam pageParam) {
		return ModuleView("/sysMenuList");
	}

	@RequestMapping("/sysMenuListJson")
	@ResponseBody
	public Map<String, Object> sysMenuListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<SysMenuEntity> pageData = sysDataService.findSysMenuList(searchParams, pageParam);
		
		return HttpResponseUtil.successJson(pageData, pageParam);
	}
	
	@RequestMapping("/sysMenuTreeJson")
	@ResponseBody
	public Map<String, Object> sysMenuTreeJson(Model model, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);
		
		List<Map<String,Object>> treeData = webSysMenuService.getMCenterNavMenu(searchParams);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("rows", treeData);
		return resMap;
	}

	@RequestMapping("/sysMenuAdd")
	public String sysMenuAdd(Model model, SysMenuEntity entity) {
		
		List<SysMenuEntity> parentMenus = this.sysDataService.getChildMenus(0);
		SysMenuEntity rootMenu = new SysMenuEntity();
		rootMenu.setId(0);
		rootMenu.setMenuName("--顶级菜单--");
		parentMenus.add(0, rootMenu);		
		
		model.addAttribute("entity", entity);
		model.addAttribute("parentMenus", parentMenus);

		return ModuleView("/sysMenuForm");
	}

	@RequestMapping("/sysMenuEdit")
	public String sysMenuEdit(Model model, long menuId) {

		SysMenuEntity entity = sysDataService.getSysMenu(menuId);

		List<SysMenuEntity> parentMenus = this.sysDataService.getChildMenus(0);
		SysMenuEntity rootMenu = new SysMenuEntity();
		rootMenu.setId(0);
		rootMenu.setMenuName("--顶级菜单--");
		parentMenus.add(0, rootMenu);		
		
		model.addAttribute("entity", entity);
		model.addAttribute("parentMenus", parentMenus);

		return ModuleView("/sysMenuForm");
	}

	@RequestMapping(value = "/sysMenuSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysMenuSave(Model model, SysMenuEntity entity) {

		if(StringUtils.isBlank(entity.getMenuName())){
			return HttpResponseUtil.failureJson("菜单名不能为空");
		}
		
		if(StringUtils.isBlank(entity.getMenuUrl())){
			return HttpResponseUtil.failureJson("菜单地址不能为空");
		}
				
		String menuName = ValidatorUtil.filterSpace(entity.getMenuName());
		entity.setMenuName(menuName);
		
		try {
			this.sysDataService.saveSysMenu(entity);
			webContext.sysUserLog(LogType.UserOpr, "保存菜单信息：" + menuName);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/sysMenuDel", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysMenuDel(long menuId) {
		// 删除
		boolean result = sysDataService.delSysMenu(menuId);
		webContext.sysUserLog(LogType.UserOpr, "删除菜单：" + menuId);
		return result;
	}

	@RequestMapping(value = "/sysMenuDels", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysMenuDels(Long[] menuIds) {
		// 删除
		int result = sysDataService.delSysMenus(menuIds);
		webContext.sysUserLog(LogType.UserOpr, "删除菜单：" + StringUtils.join(menuIds, ","));
		return result > 0;
	}
	
}

