package cn.mooc.app.core.web.view;

import java.io.File;
import java.util.Locale;

import org.springframework.web.servlet.view.JstlView;

public class MultiView extends JstlView {

	@Override
	public boolean checkResource(Locale locale) throws Exception {
		//判断资源文件是否存在		
		File file = new File(this.getServletContext().getRealPath("/") + this.getUrl()); 
		//System.out.println(file.exists());
		//ResourceUtils.getFile(this.getUrl())
		return file.exists();
	}
	
	
}
