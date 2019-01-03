package cn.mooc.app.core.interceptor;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.mooc.app.core.annotation.SameUrlData;
import cn.mooc.app.core.utils.JsonUtil;
import cn.mooc.app.core.utils.StringUtil;

/**
 * 一个用户 相同url 同时提交 相同数据 验证 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单
 * 
 */
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			SameUrlData annotation = method.getAnnotation(SameUrlData.class);
			if (annotation != null) {
				if (repeatDataValidator(request)) {// 如果重复相同数据
					return false;
				} else {
					return true;
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	/**
	 * 验证同一个url数据是否相同提交 ,相同返回true
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	public boolean repeatDataValidator(HttpServletRequest httpServletRequest) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String params = JsonUtil.toJson(httpServletRequest.getParameterMap());
		String url = httpServletRequest.getRequestURI();
		Map<String, String> map = new HashMap<String, String>();
		map.put(url, params);
		String nowUrlParams = map.toString();//
		HttpSession session = httpServletRequest.getSession();
		Object preUrlParams = session.getAttribute("repeatData");
		if (preUrlParams == null)// 如果上一个数据为null,表示还没有访问页面
		{
			session.setAttribute("repeatData", nowUrlParams);
			session.setAttribute("repeatData_date", sdf.format(date));
			session.getAttribute("repeatData");
			session.getAttribute("repeatData_date");
			return false;
		} else// 否则，已经访问过页面
		{
			if (preUrlParams.toString().equals(nowUrlParams))// 如果上次url+数据和本次url+数据相同，则表示城府添加数据
			{
				String rdStr = StringUtil.strnull(session.getAttribute("repeatData_date"));
				// 相同url+数据 只保留3秒
				try {
					Date preRd = sdf.parse(rdStr);
					if (preRd != null && DateUtils.addSeconds(preRd, 3).before(date)) {
						session.setAttribute("repeatData_date", sdf.format(date));
						return false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return true;
			} else// 如果上次 url+数据 和本次url加数据不同，则不是重复提交
			{
				session.setAttribute("repeatData", nowUrlParams);
				session.setAttribute("repeatData_date", sdf.format(date));
				session.getAttribute("repeatData");
				session.getAttribute("repeatData_date");
				return false;
			}

		}
	}

}