package cn.mooc.app.core.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;

public class ShowFileUrl implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		
		if(paras.length == 0){
			return false;
		}
		
		String url = (String) paras[0];
		if(url.startsWith("http")){
			return url;
		}else{
			if(url.startsWith("/")){
				return ctx.getGlobal("ctxPath") + url;
			}else{
				return ctx.getGlobal("ctxPath") + "/" + url;
			}
			
		}
		
		
	}

}
