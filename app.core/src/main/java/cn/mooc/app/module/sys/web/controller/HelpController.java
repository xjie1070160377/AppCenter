package cn.mooc.app.module.sys.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelpController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/help/showLogger")
	public void showLogger(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		
		logger.debug("测试打印 debug");
		
		logger.info("测试打印 info");
		
		logger.error("测试打印 error");
		
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
		
	}
	
	
}
