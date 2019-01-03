package cn.mooc.app.module.interact.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.enums.MarkType;
import cn.mooc.app.module.interact.service.InteractMarkService;
@Controller
public class MarkWebController {
	@Autowired
	private WebContext webContext;
	@Autowired
	private InteractMarkService markService;
	
	/**
	 * 添加标记
	 * @param entity
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/mark_submit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMark(InteractMark entity, HttpServletRequest request, Model modelMap){
		Map<String, Object> result = new HashMap<>();
		
		if (entity.getFid() == 0) {
			result.put("messages", "评论内容不允许为空.");
			result.put("status", 0);
		}
		if (entity.getFtype() == null) {
			result.put("messages", "标记类型不允许为空.");
			result.put("status", 0);
		}
		LoginUserInfo user = webContext.getCurrentUserInfo();
		if(user == null){
			result.put("messages", "登录超时，请重新登录.");
			result.put("status", 0);
		}
		entity.setUserId(user.getUserId());
		entity.setUserName(user.getUserName());
		String ip = ValidatorUtil.getIpAddr(request);
		entity.setIp(ip);
		entity.setCreateTime(new Date());
		try {
			markService.saveInteractMark(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			result.put("messages", "保存失败.");
			result.put("status", 0);
			return result;
		}
		long count = markService.countMarks(entity.getFtype(), entity.getFid());
		result.put("messages", "保存成功.");
		result.put("status", 1);
		result.put("count", count);
		return result;
	}
	
	@RequestMapping(value = "/mark_del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delMark(Long fid, String ftype){
		Map<String, Object> result = new HashMap<>();
		LoginUserInfo user = webContext.getCurrentUserInfo();
		try {
			markService.cancelInteractMark(fid, MarkType.valueOf(ftype), user.getUserId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("messages", "保存失败.");
			result.put("status", 0);
			return result;
		}
		result.put("messages", "保存成功.");
		result.put("status", 1);
		long count = markService.countMarks(MarkType.valueOf(ftype), fid);
		result.put("count", count);
		return result;
	}
}
