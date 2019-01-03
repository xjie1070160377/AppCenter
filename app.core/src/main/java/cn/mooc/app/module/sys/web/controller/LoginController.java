package cn.mooc.app.module.sys.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.Captcha.Builder;
import cn.apiclub.captcha.backgrounds.TransparentBackgroundProducer;
import cn.apiclub.captcha.gimpy.DropShadowGimpyRenderer;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import cn.apiclub.captcha.text.renderer.WordRenderer;
import cn.mooc.app.core.annotation.LoginCheck;
import cn.mooc.app.core.context.WebContext;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.HttpUtil;

/**
 * 普通用户登录
 * 
 * @author Taven
 *
 */
@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	@Autowired
	private WebContext webContext;
	
//	@RequestMapping("/login")
//	public ModelAndView login(Model model) {
//
//		
//		return new ModelAndView("/login");
//	}
	
	
	@RequestMapping("/help/forceChangePwd")
	@LoginCheck
	public ModelAndView forceChangePwd(Model model, String backUrl) {
		//登录后，因安全策略原因导致必须修改密码的页面
		model.addAttribute("backUrl", backUrl);
		
		return new ModelAndView("/help/forceChangePwd.html");
	}
	
	
	@RequestMapping(value = "/help/forceUpdateUserPwd", method = RequestMethod.POST)
	@LoginCheck
	@ResponseBody
	public Map<String, Object> forceUpdateUserPwd(Model model, HttpServletRequest request, String passWord,String rePassWord) {
		
		long userId = this.webContext.getCurrentUserId();
		
		boolean resultStatus = true;
		
		if(StringUtils.isBlank(passWord) || StringUtils.isBlank(rePassWord)){
			return HttpResponseUtil.failureJson("密码不能为空");
		}
		
		if(!passWord.equals(rePassWord)){
			return HttpResponseUtil.failureJson("两次输入的密码不一致");
		}		
		
		if(!this.sysDataService.checkSysUserPwdSafe(passWord)){
			//增加该逻辑的目的是因为需要进入系统后台的用户，密码不能过于简单
			return HttpResponseUtil.failureJson("新的密码过于简单，修改失败");
		}
		
		try {
			resultStatus = this.sysDataService.changeSysUserPwd(userId, passWord);
			webContext.sysUserLog(LogType.UserOpr, "修改用户 " + userId+ " 的密码： "+ (resultStatus?"成功[Force]":"修改密码失败[Force]"));
			if(resultStatus){
				HttpUtil.setSessionAttr(request, "forceChangePwd", false);
			}
			return HttpResponseUtil.resMapJson(resultStatus, resultStatus?"":"修改密码失败");
		} catch (Exception e) {
			logger.error("saveUserPwd", e);
			return HttpResponseUtil.resMapJson(false, e.getMessage());
		}
		
		
	}
	
	@RequestMapping("/help/showVerifyCode")
	public void showVerifyCode(Model model,HttpServletRequest request,HttpServletResponse response, String codeName) {
		
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.RED);
		
		List<Font> fonts = new ArrayList<Font>();
		fonts.add(new Font("Geneva", 2, 32));
		fonts.add(new Font("Courier", 3, 32));
		fonts.add(new Font("Arial", 1, 32));
	    
		//WordRenderer wordRenderer = new ColoredEdgesWordRenderer(colors, fonts);
		WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
		
		Builder builder = new Captcha.Builder(150, 50).addText(wordRenderer);
		builder.gimp(new DropShadowGimpyRenderer());
		builder.addBackground(new TransparentBackgroundProducer());
		
		Captcha captcha = builder.build();
				
		HttpUtil.setSessionAttr(request, codeName, captcha.getAnswer());
		//request.getSession().setAttribute("verifyCode", captcha.getAnswer());
		
		CaptchaServletUtil.writeImage(response, captcha.getImage());
				
	}
	

	
}
