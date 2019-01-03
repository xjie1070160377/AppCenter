package cn.mooc.app.module.widget.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/widget")
@Controller
public class ShortUrlController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/url/{code}")
	public void shortUrl(Model model,HttpServletRequest request,HttpServletResponse response, @PathVariable String code) {
		logger.debug("接收到URL短码：{}", code);
		//根据短码解析出对应的真实URL，并跳转（暂不实现，等后期需求）
		
		String toUrl = "";
		try {
			WebUtils.issueRedirect(request, response, toUrl);
		} catch (IOException e) {
			logger.error("shortUrl error：", e);
		}
		
	}
	
	
	
}
