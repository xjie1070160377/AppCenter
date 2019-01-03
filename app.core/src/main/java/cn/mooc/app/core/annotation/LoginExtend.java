package cn.mooc.app.core.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginExtend {

	public void hasLogined(HttpServletRequest request, HttpServletResponse response);
	
}
