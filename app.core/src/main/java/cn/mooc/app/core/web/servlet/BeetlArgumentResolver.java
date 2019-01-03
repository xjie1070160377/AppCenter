package cn.mooc.app.core.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * 可参考 org.springframework.web.method.annotation.RequestParamMethodArgumentResolver.resolveName
 * @author Taven
 *
 */
public class BeetlArgumentResolver implements HandlerMethodArgumentResolver {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//是否有某个注解的参数 parameter.getParameterAnnotation 
		//
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		//
		Class<?> pType = parameter.getParameterType();
		String pName = parameter.getParameterName();
		boolean isBaseType = BeanUtils.isSimpleProperty(pType);
		logger.debug("参数类型：{} 参数名：{}", pType.getName(), pName);
		
		throw new Exception("");
		
		//return null;
		
	}

}
