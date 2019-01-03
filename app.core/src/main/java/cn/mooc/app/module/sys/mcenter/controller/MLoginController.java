package cn.mooc.app.module.sys.mcenter.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mooc.app.core.utils.HttpUtil;

/**
 * 后台管理用户登录
 * 
 * @author Taven
 *
 */
@Controller
public class MLoginController extends SysModuleController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/login")
	public ModelAndView login(Model model, HttpServletRequest request) {
		
		String errorClassName = (String) request.getAttribute("shiroLoginFailure");
		boolean forceCheckCode = HttpUtil.getSessionAttr((HttpServletRequest) request, "forceCheckCode", false);
		
		model.addAttribute("forceCheckCode", forceCheckCode);
		model.addAttribute("errorClassName", errorClassName);
		logger.debug("method：{}  errorClassName：{}", request.getMethod(), errorClassName);
						
		return ModuleModelAndView("login");
		
	}
	
	
	
	
}
