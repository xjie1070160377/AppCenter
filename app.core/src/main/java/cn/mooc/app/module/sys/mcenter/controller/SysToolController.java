package cn.mooc.app.module.sys.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.HttpUtil;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.web.shiro.auth.filter.AuthFilterRegistry;

@Controller
@RequestMapping("/sys")
public class SysToolController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CacheService cacheService;
	@Autowired
	private AuthFilterRegistry authFilterRegistry;
	
	@RequestMapping("/sysTools")
	public String sysTools(Model model) {
		 //
		
		return ModuleView("/sysTools");
	}
	
	@RequestMapping(value = "/getCache", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCache(Model model, String cacheKey) {
		if(StringUtils.isBlank(cacheKey)){
			return HttpResponseUtil.failureJson("cacheKey不能为空");
		}
		try{
			Object cacheValue = this.cacheService.getCache(cacheKey);
			if(cacheValue!=null){
				return HttpResponseUtil.successJson(JsonUtil.toJson(cacheValue));
			}else{
				return HttpResponseUtil.failureJson("缓存对象为空");
			}
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}
		
		
	}
	
	@RequestMapping(value = "/delCache", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delCache(Model model, String cacheKey) {
		if(StringUtils.isBlank(cacheKey)){
			return HttpResponseUtil.failureJson("cacheKey不能为空");
		}
		
		try{
			this.cacheService.delCache(cacheKey);
			webContext.sysUserLog(LogType.UserOpr, "删除缓存：" + cacheKey);
			return HttpResponseUtil.successJson();
			}catch (Exception e) {
				return HttpResponseUtil.failureJson(e.getMessage());
			}	
		
	}
	
	@RequestMapping(value = "/cleanCache")
	@ResponseBody
	public Map<String, Object> cleanCache(Model model) {
		
		try{
			this.cacheService.cleanCache();
			webContext.sysUserLog(LogType.UserOpr, "清空所有缓存");
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}		
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/reLoadAuthFilter")
	@ResponseBody
	public Map<String, Object> reLoadAuthFilter(Model model, String filterKey) {
		
		try{
			authFilterRegistry.reLoadAuthFilter(filterKey);
			webContext.sysUserLog(LogType.UserOpr, "重新加载验证权限");
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}		
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/getRegFilters")
	@ResponseBody
	public Map<String, Object> getRegFilters(Model model) {
		
		try{
			Map<String, Object> resMap = new HashMap<String, Object>();
			
			Map<String, Filter> filters = authFilterRegistry.getRegFilters();
			for (String filterName : filters.keySet()) {
				resMap.put(filterName, filters.get(filterName).getClass().getName());
			}
			
			return HttpResponseUtil.successJson(resMap, "加载成功");
		}catch (Exception e) {
			return HttpResponseUtil.failureJson(e.getMessage());
		}		
		
	}
	
	@RequestMapping(value = "/forward")
	@ResponseBody
	public Map<String, Object> forward(Model model, String restApi, String auth, String status, String fromAddr, String toAddr) {
		if(StringUtils.isBlank(restApi)){
			return HttpResponseUtil.failureJson("restApi不能为空");
			
		}
		
		if(StringUtils.isEmpty(auth)){
			return HttpResponseUtil.failureJson("auth不能为空");
			
		}
		
		if(StringUtils.isEmpty(status)){
			status = "0";
		}
		
		try{
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			formParams.add(new BasicNameValuePair("auth", auth));
			formParams.add(new BasicNameValuePair("status", status));
			formParams.add(new BasicNameValuePair("fromAddr", fromAddr));
			formParams.add(new BasicNameValuePair("toAddr", toAddr));
			String result = HttpUtil.simplePost(restApi+"/ForwardWork", formParams, 1000 * 3);
			
			return HttpResponseUtil.successJson(result);
			}catch (Exception e) {
				return HttpResponseUtil.failureJson(e.getMessage());
			}	
		
	}
	
	@RequestMapping(value = "/forwardSummary")
	@ResponseBody
	public Map<String, Object> forwardSummary(Model model, String restApi, String auth) {
		if(StringUtils.isBlank(restApi)){
			return HttpResponseUtil.failureJson("restApi不能为空");
			
		}
		
		try{
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			String result = HttpUtil.simplePost(restApi+"/ServerSummary", formParams, 1000 * 3);
			
			return HttpResponseUtil.successJson(result);
			}catch (Exception e) {
				return HttpResponseUtil.failureJson(e.getMessage());
			}	
		
	}
	
}
