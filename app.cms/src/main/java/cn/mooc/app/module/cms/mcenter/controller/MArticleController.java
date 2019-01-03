package cn.mooc.app.module.cms.mcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms")
public class MArticleController extends CmsModuleController {

	@RequestMapping("/Home")
	public String Home() {
		

		return "/cms/index";
		
	}
	
	
	
	
}
