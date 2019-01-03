package cn.mooc.app.core.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class Beans implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		//该函数的开放，需要特别注意模板的修改权限，否则会有安全隐患
		if(paras.length == 0){
			return false;
		}
		String bean = (String) paras[0];
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		Object beanInst = webApplicationContext.getBean(bean);
		return beanInst;
	}

}
