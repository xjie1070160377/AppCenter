package cn.mooc.app.module.sys.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.module.sys.model.UserRegForm;
import cn.mooc.app.module.sys.service.IndexPageService;

@Controller
public class IndexController {

	//private final static Logger logger= LoggerFactory.getLogger(IndexController.class);
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IndexPageService indexPageService;
	
	@RequestMapping(value = { "/", "/index" })
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		//首页默认取core里面的，如果有模块设置了首页的实现接口，则用具体模块的
		
		return indexPageService.getIndexAction().index(model, request, response);

	}
	
	@RequestMapping("/ulogin")
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

		return indexPageService.getLoginAction().login(model, request, response);
	}
	
	
	@RequestMapping("/error/{code}")
	public ModelAndView error(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable int code) {

		
		//return new ModelAndView("/error/"+code);
		return new ModelAndView("/error/"+code+".html");
	}
	
	@RequestMapping(value = "/heartBeat")
	@ResponseBody
	public Map<String, Object> heartBeat(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		return HttpResponseUtil.successJson();
	}
	
	@RequestMapping(value = "/serverTime")
	@ResponseBody
	public Map<String, Object> serverTime(Model model) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		resMap.put("serverTime", DateTimeUtil.getCurrentTime());
		
		return HttpResponseUtil.resMapJson(resMap, true, "");
		
	}

	@RequestMapping("/registry")
	public String registry(@Validated UserRegForm userRegForm) {

		return "/index";

	}
	
	@RequestMapping("/tmpIndex")
	public String registry(Model model) {
		return "/sys/login";
	}
	
}
