package cn.mooc.app.module.cms.service;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

/**
 * ServletContext路径获取实现
 * 
 * @author csmooc
 * 
 */
@Service
public class ServletContextPathResolver implements PathResolver, ServletContextAware {
	public String getPath(String uri) {
		if (uri == null) {
			uri = "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(servletContext.getRealPath(""));
		if (!uri.startsWith("/")) {
			sb.append(File.separator);
		}
		sb.append(uri.replace('/', File.separatorChar));
		return sb.toString();
	}

	public String getPath(String uri, String prefix) {
		if (uri == null) {
			uri = "";
		}
		StringBuilder sb = new StringBuilder();
		if (StringUtils.startsWith(prefix, "file:")) {
			sb.append(prefix.substring(5));
		} else {
			sb.append(servletContext.getRealPath(""));
			if (StringUtils.isNotBlank(prefix)) {
				sb.append(prefix.replace('/', File.separatorChar));
			}
		}
		if (!uri.startsWith("/")) {
			sb.append(File.separator);
		}
		sb.append(uri.replace('/', File.separatorChar));
		return sb.toString();
	}

	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
