package cn.mooc.app.module.points.mcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.util.JqGridHandler;
import cn.mooc.app.module.points.data.entity.UserPoints;
import cn.mooc.app.module.points.model.UserPointsModel;
import cn.mooc.app.module.points.service.UserPointsService;

@Controller
@RequestMapping("/points/user")
public class UserPointsController extends PointsModuleController {

	@Autowired
	UserPointsService service;
	@Autowired
	private SysDataService userService;
	
	@RequestMapping("/list")
	public ModelAndView list(Model model) {
		return ModuleModelAndView("/userPoints/list");
	}
	
	@RequestMapping("/page")
	@ResponseBody
	public Map<String, Object> page(Model model, PagerParam pageParam, HttpServletRequest request) {
		Map<String, Object> searchParams = JqGridHandler.getQueryWebParameter(request);

		Page<UserPoints> pageData = service.findAll(searchParams, pageParam);
		List<UserPointsModel> modelList = new ArrayList<UserPointsModel>();
		for(UserPoints up : pageData.getContent()){
			SysUserEntity user = userService.getUserInfoById(up.getId());
			UserPointsModel um = new UserPointsModel(up, user.getUserName());
			modelList.add(um);
		}
		return HttpResponseUtil.successJson(modelList, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
}
