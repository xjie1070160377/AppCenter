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
import cn.mooc.app.module.points.data.entity.CurrencyLog;
import cn.mooc.app.module.points.model.CurrencyLogModel;
import cn.mooc.app.module.points.service.CurrencyLogService;

/**
 * CurrencyLogController
 * R币获取兑换日志控制器控制器
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Controller
@RequestMapping("/points/currencyLog")
public class CurrencyLogController extends PointsModuleController {

	@Autowired
	private CurrencyLogService currencyLogService;
	@Autowired
	private WebContext webContext;

	@RequestMapping("/currencyLogList")
	public String currencyLogList(Model model, PagerParam pageParam) {
		return ModuleView("/currencyLog/currencyLogList");
	}

	@RequestMapping("/currencyLogListJson")
	@ResponseBody
	public Map<String, Object> currencyLogListJson(Model model, PagerParam pageParam, String columnFiled, String keyWord) {
		String userName = "";
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(columnFiled) && columnFiled.equals("userName")){
			userName = keyWord;
		}else{
			searchParams.put(columnFiled, keyWord);
		}

		Page<CurrencyLog> pageData = currencyLogService.findCurrencyLogPage(searchParams, pageParam, userName);
		List<CurrencyLogModel> list = new ArrayList<CurrencyLogModel>();
		for(CurrencyLog currencyLog : pageData.getContent()){
			SysUserEntity user = webContext.getSysUser(currencyLog.getUserId());
			CurrencyLogModel m = new CurrencyLogModel(currencyLog, user.getUserName());
			list.add(m);
		}
		return HttpResponseUtil.successJson(list, pageData.getTotalElements(), pageData.getTotalPages(), pageParam);
	}

}