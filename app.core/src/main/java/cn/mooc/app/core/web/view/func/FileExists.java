package cn.mooc.app.core.web.view.func;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContext;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class FileExists implements Function {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Object call(Object[] paras, Context ctx) {
		
		if(paras.length == 0){
			return false;
		}
		
		String url = (String) paras[0];
		if(url.startsWith("http")){			
			try {
				URL fileUrl = new URL(url);
				InputStream inputStream = fileUrl.openStream();
				return true;
			} catch (IOException e) {
				logger.error("", e);
				return false;
			}
		}else{
			try{
				ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
				String uploadsPath = servletContext.getRealPath("/") ;
				return new File(uploadsPath+url).exists();
			}catch (Exception e) {
				logger.error("", e);
				return false;
			}
		}
		
		
	}

}