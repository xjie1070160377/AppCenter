package cn.mooc.app.core.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;

/**
 * 为了实现一些更为牛逼的功能，特扩展spring 的 DispatcherServlet
 * @author Taven
 *
 */
public class NiubiDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 5241092982228174694L;


	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		request.getSession().setAttribute("dispatcherServlet", this);
		return super.getHandler(request);
		
	}
	
	public <T> List<T> getDefaultStrategies(ApplicationContext context, Class<T> strategyInterface) {
		
		return super.getDefaultStrategies(context, strategyInterface);
				
	}
	
	
}
