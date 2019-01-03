package cn.mooc.app.module.sys.mcenter.controller;

import java.io.File;
import java.util.ArrayList;
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

import cn.mooc.app.core.data.entity.SysConfigEntity;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.sys.model.SysSettings;

/**
 * sysConfigController
 * 系统参数控制器
 * 
 * @author Taven
 * @date 2016-05-06
 */
@Controller
@RequestMapping("/sys")
public class SysConfigController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;

	
	@RequestMapping("/sysConfigList")
	public String sysConfigList(Model model, PagerParam pageParam) {
		return ModuleView("/sysConfigList");
	}

	@RequestMapping("/sysConfigListJson")
	@ResponseBody
	public Map<String, Object> sysConfigListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(columnFiled, keyWord);

		Page<SysConfigEntity> pageData = sysDataService.findSysConfigList(searchParams, pageParam);

		return HttpResponseUtil.successJson(pageData, pageParam);
	}

	@RequestMapping("/sysConfigAdd")
	public String sysConfigAdd(Model model, SysConfigEntity entity) {
		model.addAttribute("entity", entity);

		return ModuleView("/sysConfigForm");
	}

	@RequestMapping("/sysConfigEdit")
	public String sysConfigEdit(Model model, String keyName) {

		SysConfigEntity entity = sysDataService.getSysConfig(keyName);
		model.addAttribute("entity", entity);

		return ModuleView("/sysConfigForm");
	}

	@RequestMapping(value = "/sysConfigSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysConfigSave(Model model, SysConfigEntity entity) {

		try {
			this.sysDataService.saveSysConfig(entity);
			
			webContext.sysUserLog(LogType.UserOpr, "保存配置项：" + entity.getKeyName());
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}

	@RequestMapping(value = "/sysConfigDel", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysConfigDel(String keyName) {
		// 删除
		boolean result = sysDataService.delSysConfig(keyName);
		webContext.sysUserLog(LogType.UserOpr, "删除配置项：" + keyName);
		return result;
	}

	@RequestMapping(value = "/sysConfigDels", method = RequestMethod.POST)
	@ResponseBody
	public boolean sysConfigDels(String[] keyNames) {
		// 删除
		int result = sysDataService.delSysConfigs(keyNames);
		webContext.sysUserLog(LogType.UserOpr, "删除配置项：" + StringUtils.join(keyNames, ","));
		return result > 0;
	}
	
	@RequestMapping("/sysSettings")
	public String sysSettings(Model model, SysSettings sysSettings) {

		Map<String, Object> map = this.sysDataService.getSysConfigMap();
		int position = map.containsKey("sys.watermark.position") ? Integer.parseInt(map.get("sys.watermark.position").toString()) : 9;
		int transparent = map.containsKey("sys.watermark.transparent") ? Integer.parseInt(map.get("sys.watermark.transparent").toString()) : 50;
		String watermark = map.containsKey("sys.watermark.url") ? map.get("sys.watermark.url").toString() : "";
		
		sysSettings.setPosition(position);
		sysSettings.setTransparent(transparent);
		sysSettings.setWatermark(watermark);
		
		model.addAttribute("settings", sysSettings);
				
		model.addAttribute("sysProps", System.getProperties());
		model.addAttribute("sysEnv", System.getenv());
		
		model.addAttribute("maxMemory", Runtime.getRuntime().maxMemory());
		model.addAttribute("freeMemory", Runtime.getRuntime().freeMemory());
		model.addAttribute("totalMemory", Runtime.getRuntime().totalMemory());
		model.addAttribute("cpuCount", Runtime.getRuntime().availableProcessors());
		model.addAttribute("workingDir", new File("").getAbsolutePath());
	      
		return ModuleView("/sysSettings");
	}
	
	
	@RequestMapping(value = "/sysSettingsSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sysSettingsSave(Model model, SysSettings sysSettings) {

		try {
			List<SysConfigEntity> dataList = new ArrayList<SysConfigEntity>();
			
			SysConfigEntity sysConfig = new SysConfigEntity();
			sysConfig.setKeyName("sys.watermark.position");
			sysConfig.setKeyValue(sysSettings.getPosition()+"");
			sysConfig.setCreateTime(DateTimeUtil.getCurrentTime());
			sysConfig.setNote("水印位置");
			dataList.add(sysConfig);
			
			sysConfig = new SysConfigEntity();
			sysConfig.setKeyName("sys.watermark.transparent");
			sysConfig.setKeyValue(sysSettings.getTransparent()+"");
			sysConfig.setCreateTime(DateTimeUtil.getCurrentTime());
			sysConfig.setNote("水印的透明度");
			dataList.add(sysConfig);
			
			sysConfig = new SysConfigEntity();
			sysConfig.setKeyName("sys.watermark.url");
			sysConfig.setKeyValue(sysSettings.getWatermark());
			sysConfig.setCreateTime(DateTimeUtil.getCurrentTime());
			sysConfig.setNote("水印图片地址");
			dataList.add(sysConfig);
			
			this.sysDataService.saveSysConfigs(dataList);
			
			webContext.sysUserLog(LogType.UserOpr, "修改上传设置");
		} catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		return HttpResponseUtil.successJson();
	}
	
}

