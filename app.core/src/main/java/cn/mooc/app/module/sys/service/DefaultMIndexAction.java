package cn.mooc.app.module.sys.service;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public class DefaultMIndexAction implements MIndexAction {

	@Override
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		//
		Calendar now = Calendar.getInstance();
		Date endDate = now.getTime();
		now.add(Calendar.DAY_OF_MONTH, -7);
		Date startDate = now.getTime();
		model.addAttribute("endDate", endDate);
		model.addAttribute("startDate", startDate);
		
		return "/sys/index";
	}

}
