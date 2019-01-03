package cn.mooc.app.module.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class DefaultIndexAction implements IndexAction {

	@Override
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

		return "/theme/index.html";
		
	}

}
