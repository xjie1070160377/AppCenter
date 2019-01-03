package cn.mooc.app.module.points.mcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.UserPointsLog;
import cn.mooc.app.module.points.model.UserPointsLogModel;
import cn.mooc.app.module.points.service.UserPointsLogService;

@Controller
@RequestMapping("/points/log")
public class UserPointsLogController extends PointsModuleController {

	@Autowired
	private UserPointsLogService logService;
	@Autowired
	private WebContext webContext;

	@RequestMapping("/pointsLogList")
	public String currencyLogList(Model model, PagerParam pageParam) {
		return ModuleView("/pointsLog/pointsLogList");
	}

	@RequestMapping("/pointsLogListJson")
	@ResponseBody
	public Map<String, Object> currencyLogListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		String userName = "";
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(columnFiled) && columnFiled.equals("userName")){
			userName = keyWord;
		}else{
			searchParams.put(columnFiled, keyWord);
		}

		Page<UserPointsLog> pageData = logService.findPointsLogPage(searchParams, pageParam, userName);
		List<UserPointsLogModel> list = new ArrayList<UserPointsLogModel>();
		for(UserPointsLog log : pageData.getContent()){
			SysUserEntity user = webContext.getSysUser(log.getUserPoints().getId());
			UserPointsLogModel m = new UserPointsLogModel(log, user.getUserName());
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}
}
