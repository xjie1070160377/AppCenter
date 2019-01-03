package cn.mooc.app.core.web.servlet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

public class RequestParamArgumentResolver extends RequestParamMethodArgumentResolver {

	public RequestParamArgumentResolver() {
		super(true);
		
	}
	
	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest webRequest) throws Exception {
		
		Object arg = super.resolveName(name, parameter, webRequest);
		if (arg == null) {
			//以下逻辑主要是让Beetl中的Action Tag标签支持传参数
			Class<?> pType = parameter.getParameterType();
			String pName = parameter.getParameterName();
			//如果request没有对应的参数信息			
			HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
			Map<String, Object> pMap = (Map<String, Object>) servletRequest.getSession().getAttribute("pMap");

			if(pMap!=null && pMap.containsKey(pName)){
				return pMap.get(pName);
				
			}
			
		}
		
		return arg;
		
	}

}
