package cn.mooc.app.module.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface IndexAction {

	public String index(Model model, HttpServletRequest request, HttpServletResponse response);
	
}
