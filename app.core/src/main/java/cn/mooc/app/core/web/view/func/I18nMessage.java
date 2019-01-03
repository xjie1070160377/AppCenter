package cn.mooc.app.core.web.view.func;

import javax.servlet.http.HttpServletRequest;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;

public class I18nMessage implements Function {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object call(Object[] paras, Context ctx) {
		//
		if(paras.length == 0){
			return null;
		}
		String messageKey = (String) paras[0];
		//HttpServletRequest req = (HttpServletRequest) context.getGlobal("request");
		RequestContext requestContext = new RequestContext((HttpServletRequest) ctx.getGlobal("request"));
		String value = requestContext.getMessage(messageKey);
		
		if(paras.length == 2 && value.equals(messageKey)){
			return paras[1];
		}
		
		logger.debug("I18nMessage " + messageKey + "=" + value);
		
		return value;
	}

}
