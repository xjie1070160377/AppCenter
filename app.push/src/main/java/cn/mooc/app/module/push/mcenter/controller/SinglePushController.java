package cn.mooc.app.module.push.mcenter.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.model.SysUserInfo;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.utils.ValidatorUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;
import cn.mooc.app.module.push.data.entity.IosToken;
import cn.mooc.app.module.push.service.IosTokenService;
import cn.mooc.app.module.push.service.MsgPushService;

@Controller
@RequestMapping("/push/single")
public class SinglePushController extends PushController {

	@Autowired
	private IosTokenService service;
	@Autowired
	private MsgPushService pushService;
	
	@RequestMapping("/form")
	public String modelFieldList(Model modelMap) {
		return ModuleView("/form");
	}
	
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> push(String userCode, Integer infoId, HttpServletRequest request, String showType){
		IosToken token = service.getTokenByUserCode(userCode);
		String msg = "";
		if(token != null){
			LoginUserInfo u = this.webContext.getCurrentUserInfo();
			SysUserInfo user = this.webContext.getSysUserFullInfo(token.getId());
			pushService.pushMsg("单个推送测试", "单个推送测试", "1" , infoId+"", null, showType, new Long[]{user.getUserId()}, "单个推送测试", u.getUserId(), u.getUserName(), ValidatorUtil.getIpAddr(request), request.getRequestURI());
			
			String username = StringUtil.isNull(user.getRealName())?(StringUtil.isNull(user.getNickName())?(userCode):user.getNickName()):user.getRealName();
			String device = "";
			if(token.getIsios()){
				msg = "已给IOS用户【"+username+"】推送消息，请确认查收！";
			}else{
				msg = "已给安卓用户【"+username+"】推送消息，请确认查收！";
			}
		}else{
			msg =  "【"+userCode+"】用户还没有使用移动设备推送过，无法推送！";
		}
		return HttpResponseUtil.successJson(msg);
	}
	
}
