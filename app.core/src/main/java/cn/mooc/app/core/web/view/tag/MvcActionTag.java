package cn.mooc.app.core.web.view.tag;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.ByteWriter;
import org.beetl.core.Tag;
import org.beetl.core.Template;
import org.beetl.ext.spring.BeetlSpringView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import cn.mooc.app.core.web.servlet.NiubiDispatcherServlet;
import cn.mooc.app.core.web.servlet.ParameterRequestWrapper;

/**
 * 实现模板中可以直接包含另外一个Action执行后的结果到当前视图
 * 使用方式：
 * 
 * <%action("/controller/action"){} %>
 * <%action("/controller/action",{title:'这是一个测试页面',user:user}){} %>
 * <%action("/cms/model/modelList"){} %>
 * 
 * @author Taven
 *
 */
public class MvcActionTag extends Tag {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void render() {
		
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		NiubiDispatcherServlet dispatcherServlet = (NiubiDispatcherServlet) request.getSession().getAttribute("dispatcherServlet");		
		
		String actionUrl = (String) this.args[0];
		if(StringUtils.isBlank(actionUrl)){
			throw new RuntimeException("参数错误，actionUrl不能为空");
		}
		
		Field hmField = ReflectionUtils.findField(DispatcherServlet.class, "handlerMappings");
		ReflectionUtils.makeAccessible(hmField);
		List<HandlerMapping> handlerMappings = (List<HandlerMapping>) ReflectionUtils.getField(hmField, dispatcherServlet);
		
		//传到模板中的逻辑写在 viewRender 方法里
		//这里是通过RequestParamArgumentResolver参数解析传到包含的controller中，
		//因各种原因，2016-10-19弃用该方式
		/*if (this.args.length == 2) {			
			Map<String, Object> pMap = (Map<String, Object>) this.args[1];
			request.getSession().setAttribute("pMap", pMap);
		}*/
		
		for (HandlerMapping hm : handlerMappings) {
			if(hm instanceof RequestMappingHandlerMapping){
				
				//HandlerMethod handlerMethod = requestMappingHandlerMapping.lookupHandlerMethod(reuqestUrl, request);
				//Method lookupHandlerMethod = ReflectionUtils.findMethod(AbstractHandlerMethodMapping.class, "lookupHandlerMethod", String.class, HttpServletRequest.class);
				//ReflectionUtils.makeAccessible(lookupHandlerMethod);
				//HandlerMethod handler = (HandlerMethod) ReflectionUtils.invokeMethod(lookupHandlerMethod, hm, actionUrl, request);
				//
				HandlerMethod handler = this.lookupHandlerMethod(actionUrl, request, (RequestMappingHandlerMapping) hm);
				
				handler = (handler != null ? handler.createWithResolvedBean() : null);
				if(handler==null) {
					logger.warn("执行 createWithResolvedBean 后，handler 为 null");
					continue;
				}
				
				//向request对象中增加参数
				HashMap params =new HashMap(request.getParameterMap());
				
				if (this.args.length == 2) {	
					//把Tag标签中的参数设到request的参数中
					Map<String, Object> pMap = (Map<String, Object>) this.args[1];
					for (String key : pMap.keySet()) {
						if(!params.containsKey(key)){
							params.put(key, new String[]{pMap.get(key).toString()});
						}
					}
				}
				
				
				ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request, params);
				request = requestWrapper;
				
				//将当前request中的参数自动设置给action的方法，并执行它：(InvocableHandlerMethod)handler.invokeForRequest
				//Object[] args = handler.getMethodArgumentValues(request, mavContainer, providedArgs);
				//Object returnValue = handler.doInvoke(args);
				
				Method getHandlerExecutionChainMethod = ReflectionUtils.findMethod(AbstractHandlerMethodMapping.class, "getHandlerExecutionChain", Object.class, HttpServletRequest.class);
				ReflectionUtils.makeAccessible(getHandlerExecutionChainMethod);
				HandlerExecutionChain executionChain = (HandlerExecutionChain) ReflectionUtils.invokeMethod(getHandlerExecutionChainMethod, hm, handler, request);
								
				//HandlerAdapter ha = dispatcherServlet.getHandlerAdapter(handlerMethod);
				Method getHandlerAdapterMethod = ReflectionUtils.findMethod(DispatcherServlet.class, "getHandlerAdapter", Object.class);
				ReflectionUtils.makeAccessible(getHandlerAdapterMethod);
				HandlerAdapter ha = (HandlerAdapter) ReflectionUtils.invokeMethod(getHandlerAdapterMethod, dispatcherServlet, executionChain.getHandler());
				
				try {
					
					//取对应的Controller方法，并注入参数，执行action，返回对应的视图
					ModelAndView mv = ha.handle(request, response, executionChain.getHandler());

					//取对应的视图模板解析对象
					Field vrField = ReflectionUtils.findField(DispatcherServlet.class, "viewResolvers");
					ReflectionUtils.makeAccessible(vrField);
					List<ViewResolver> viewResolvers = (List<ViewResolver>) ReflectionUtils.getField(vrField, dispatcherServlet);
					
					Field lrField = ReflectionUtils.findField(DispatcherServlet.class, "localeResolver");
					ReflectionUtils.makeAccessible(lrField);
					LocaleResolver localeResolver = (LocaleResolver) ReflectionUtils.getField(lrField, dispatcherServlet);
					
					Locale locale = localeResolver.resolveLocale(request);
					response.setLocale(locale);
					
					for (ViewResolver viewResolver : viewResolvers) {
						View view = viewResolver.resolveViewName(mv.getViewName(), locale);
						if (view != null && view instanceof BeetlSpringView) {
							BeetlSpringView beetlSpringView = (BeetlSpringView)view;
							//进行页面渲染
							this.viewRender(beetlSpringView.getUrl(), mv.getModelMap());
						}
					}
					
				} catch (Exception e) {
					logger.error("执行handle方法异常",e);
				}
				
			}

		}
	
		
	}
	
	public HandlerMethod lookupHandlerMethod(String actionUrl, HttpServletRequest request, RequestMappingHandlerMapping hm) {
		//实现逻辑参考 AbstractHandlerMethodMapping.lookupHandlerMethod
		
		Map<RequestMappingInfo, HandlerMethod> hMappings = hm.getHandlerMethods();
		for (RequestMappingInfo info : hMappings.keySet()) {
			//info.getMatchingCondition(request);
			List<String> matches = info.getPatternsCondition().getMatchingPatterns(actionUrl);
			if(matches.size()>0){
				return hMappings.get(info);
			}
			
		}
		return null;
		
	}
	
	public void viewRender(String resourceId, Map<String, Object> model){
		
		Template t = gt.getTemplate(resourceId, this.ctx.getResourceId());
		//快速复制父模板的变量
		t.binding(this.ctx.globalVar);
		if (ctx.objectKeys != null && ctx.objectKeys.size() != 0)
		{
			t.dynamic(ctx.objectKeys);
		}
		
		for (Entry<String, Object> entry : model.entrySet()){
			Object value = entry.getValue();
			if (value instanceof Map || value instanceof Collection)
			{
				t.binding((String) entry.getKey(), value, true);
			}
			else
			{
				t.binding((String) entry.getKey(), value);
			}
		}

		if (this.args.length == 2)
		{
			Map<String, Object> map = (Map<String, Object>) this.args[1];
			for (Entry<String, Object> entry : map.entrySet())
			{
				Object value = entry.getValue();
				if (value instanceof Map || value instanceof Collection)
				{
					t.binding((String) entry.getKey(), value, true);
				}
				else
				{
					t.binding((String) entry.getKey(), value);
				}

			}

		}

		ByteWriter bw = ctx.byteWriter;
		t.renderTo(bw);
		
	}

}
