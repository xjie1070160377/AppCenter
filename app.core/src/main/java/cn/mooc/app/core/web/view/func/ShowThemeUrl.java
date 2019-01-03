package cn.mooc.app.core.web.view.func;

import org.beetl.core.Context;
import org.beetl.core.Function;


public class ShowThemeUrl implements Function {

	@Override
	public Object call(Object[] paras, Context ctx) {
		
		if(paras.length == 0){
			return false;
		}
		
		String path = (String) paras[0];
		if(path.startsWith("http")){
			return path;
		}else{
			if(path.startsWith("/")){
				return ctx.getGlobal("ctxPath")+ "/theme" + path;
			}else{
				return ctx.getGlobal("ctxPath")+ "/theme"  + "/" + path;
			}
			
		}
		
		
	}

}
