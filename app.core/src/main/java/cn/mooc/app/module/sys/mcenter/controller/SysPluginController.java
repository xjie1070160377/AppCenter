package cn.mooc.app.module.sys.mcenter.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.plugin.PluginInfo;
import cn.mooc.app.core.plugin.manager.PluginsManager;
import cn.mooc.app.core.plugin.manager.PluginsRegister;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;

@Controller
@RequestMapping("/sys")
public class SysPluginController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PluginsRegister pluginRegister;
	@Autowired
	private PluginsManager pluginsManager;
	
	@RequestMapping("/pluginList")
	public String pluginList(Model model, PagerParam pageParam) {
		
		return ModuleView("/pluginList");
	}

	@RequestMapping("/pluginListJson")
	@ResponseBody
	public Map<String, Object> pluginListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		
		Page<PluginInfo> pageDataList = new PageImpl<PluginInfo>(pluginsManager.getAllPluginInfos());
		
		return HttpResponseUtil.successJson(pageDataList, pageParam);
	}
	
	@RequestMapping("/pluginEdit")
	public String pluginEdit(Model model, String pluginId) {
		PluginInfo pluginInfo = null;
		if(StringUtils.isNotEmpty(pluginId)){
			pluginInfo = this.pluginsManager.getPluginInfo(pluginId);
		}
		
		model.addAttribute("pluginInfo", pluginInfo);
		
		return ModuleView("/pluginEdit");
	}
	
	@RequestMapping(value = "/pluginUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pluginUpdate(Model model, HttpServletRequest request, String pluginId) {

		Map<String, String[]> paraMap = request.getParameterMap();
		
		try {
			PluginInfo pluginInfo = this.pluginsManager.getPluginInfo(pluginId);
			pluginInfo.getPluginData();
			for (String dataKey : pluginInfo.getPluginData().keySet()) {
				if(paraMap.containsKey(dataKey)){
					pluginInfo.setData(dataKey, paraMap.get(dataKey)[0]);
				}
				
			}
			this.pluginsManager.updatePluginInfo(pluginInfo);
			
			webContext.sysUserLog(LogType.UserOpr, "更新插件配置：" + pluginId);
			//
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}
	
	
	@RequestMapping("/pluginStart")
	@ResponseBody
	public Map<String, Object> pluginStart(Model model, String pluginId) {
				
		try {
			pluginsManager.startPlugin(pluginId);
			webContext.sysUserLog(LogType.UserOpr, "启动插件：" + pluginId);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("/pluginStop")
	@ResponseBody
	public Map<String, Object> pluginStop(Model model, String pluginId) {
		
		try {
			pluginsManager.stopPlugin(pluginId);
			webContext.sysUserLog(LogType.UserOpr, "停止插件：" + pluginId);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("/pluginRefresh")
	@ResponseBody
	public Map<String, Object> pluginRefresh(Model model, String pluginId) {

		try {
			pluginsManager.refreshPlugin(pluginId);
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("/pluginRefreshAll")
	@ResponseBody
	public Map<String, Object> refreshAll(Model model) {
		
		pluginsManager.loadAllPlugin();
		
		return HttpResponseUtil.successJson();
	}
	
}
