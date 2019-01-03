package cn.mooc.app.module.cms.mcenter.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.service.InfoService;
@Controller
@RequestMapping("/cms/hit")
public class HitsSettingController extends CmsModuleController {

	@Autowired
	private InfoService service;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,
			org.springframework.ui.Model modelMap){
		return ModuleView("hit/index");
	}
	
	/**
	 * 设置倍数
	 * @param js
	 * @param bs
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("setting1")
	@ResponseBody
	public Map<String, Object> setting1(Integer js, Integer bs, HttpServletRequest request,
			org.springframework.ui.Model modelMap){
		if(js == null){
			return HttpResponseUtil.failureJson("点击数要求小于数不能为空！");
		}else if(bs == null){
			return HttpResponseUtil.failureJson("倍数不能为空！");
		}else{
			/*User user = Context.getCurrentUser(request);
			Integer siteId = Context.getCurrentSiteId(request);
			String ip = Servlets.getRemoteAddr(request);*/
			service.updateHitByBs(js, bs);
			/*logService.operation("hitcount.setting", "修改点击数小于"+js+"的文章，在原有基础上翻"+"倍", null, null, ip, user.getId(), siteId);*/
		}
		return HttpResponseUtil.successJson();
	}
	
	/**
	 * 设置随机数
	 * @param js
	 * @param bs
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("setting2")
	@ResponseBody
	public Map<String, Object> setting2(Integer js, Integer xhits, Integer dhits, HttpServletRequest request,
			org.springframework.ui.Model modelMap){
		if(js == null){
			return HttpResponseUtil.failureJson("点击数要求小于数不能为空！");
		}else if(xhits == null || dhits == null){
			return HttpResponseUtil.failureJson("点击数范围不能为空！");
		}else{
			service.updateHitByJs(js, xhits, dhits);
			/*User user = Context.getCurrentUser(request);
			Integer siteId = Context.getCurrentSiteId(request);
			String ip = Servlets.getRemoteAddr(request);
			logService.operation("hitcount.setting", "将点击数小于"+js+"的文章的设置为小于"+xhits+"大于"+dhits+"的随机数", null, null, ip, user.getId(), siteId);*/
		}
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping("setting3")
	@ResponseBody
	public Map<String, Object> setting3(Integer infoId, Integer hits, HttpServletRequest request,
			org.springframework.ui.Model modelMap){
		Info info = service.getInfoById(infoId);
		if(info==null){
			return HttpResponseUtil.failureJson("不存在代号为【"+infoId+"】的文章信息;");
		}
		if(hits == null){
			return HttpResponseUtil.failureJson("文章点击数设置不能为空!");
		}
		service.updateHitcount(info, hits);
		/*User user = Context.getCurrentUser(request);
		Integer siteId = Context.getCurrentSiteId(request);
		String ip = Servlets.getRemoteAddr(request);
		logService.operation("hitcount.setting", "将文章代号为"+infoId+"的点击数设置为 "+hits, null, null, ip, user.getId(), siteId);*/
		return HttpResponseUtil.successJson();
	}
}
