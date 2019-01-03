package cn.mooc.app.module.sys.mcenter.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.data.entity.SysUserExt;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.module.sys.service.IndexPageService;
import cn.mooc.app.module.sys.service.WebSysService;

@Controller
public class MIndexController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebSysService webSysMenuService;
	@Autowired
	private IndexPageService indexPageService;
	
	@RequestMapping(value = { "/", "/main" })
	public String main(Model model) {
		long userId = this.webContext.getCurrentUserId();
		SysUserExt userExt = this.sysDataService.getSysUserExt(userId);
		List<Map<String,Object>> allMenusTree = webSysMenuService.getMCenterNavMenuTree();
		List<Map<String,Object>> menusTree = allMenusTree;
		if(!this.webContext.hasSuperRole()){
			//不是超级用户的，按角色拥有的菜单显示
			menusTree = webSysMenuService.getMCenterNavMenuTree(userId, allMenusTree);
		}
		
		model.addAttribute("userExt", userExt);
		model.addAttribute("menusTree", menusTree);
		model.addAttribute("allMenusTree", allMenusTree);

		return ModuleView("/main");
	}
	

	@RequestMapping(value = { "/index", "/mIndex" })
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

		return indexPageService.getMindexAction().index(model, request, response);
		
	}
	
	
}
