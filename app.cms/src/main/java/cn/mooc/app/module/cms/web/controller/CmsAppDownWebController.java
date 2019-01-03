package cn.mooc.app.module.cms.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.module.cms.data.entity.AppVersion;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.service.AppVersionService;
import cn.mooc.app.module.cms.service.CmsContext;


@Controller
public class CmsAppDownWebController {
	private static final Logger logger = LoggerFactory.getLogger(CmsAppDownWebController.class);

	public static final String TEMPLATE = "app_code.html";
	public static final String TEMPLATE_GUID = "download_apk.html";
	
	@Autowired
	private SysContext sysContext;

	@RequestMapping(value = "/app.htx")
	public String appDownload(HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Site site = CmsContext.getWebCurrentSite(request);
		modelMap.addAttribute("site", site);
		return site.getTemplate(TEMPLATE);
	}
	
	@RequestMapping(value = { "/download_apk.htx" })
	public String download_apk(HttpServletRequest request,
			HttpServletResponse res, org.springframework.ui.Model modelMap) throws IOException{
		if(isMathBrowser(request, "iPhone|iPad")){
			//this.download_ios(request, res, modelMap);
			String ios_url = sysContext.getSysConfig("cms.ios_url");
			//跳转到itunes下载页面 
			if(StringUtils.isBlank(ios_url)){
				ios_url = "https://itunes.apple.com/cn/app/hong-ke/id1073056031";
			}
			WebUtils.issueRedirect(request, res, ios_url);

			return null;
		}
		
		if(isMathBrowser(request, "MicroMessenger")){
//			String WX_URL = properties.getProperty("apk_wx_url");
			//如果是微信浏览器进入的下载，直接跳转到应用宝或腾讯旗下网站地址进行下载
//			logger.debug("apk_wx_url : " + WX_URL);
//			WebUtils.issueRedirect(request, res, WX_URL);
			Site site = CmsContext.getWebCurrentSite(request);
			return site.getTemplate(TEMPLATE_GUID);
		}
		
		Integer siteId = CmsContext.getWebCurrentSite(request).getId();
		logger.info("-------------------------------------------------------------siteId"+siteId);
		AppVersion app = appService.getLatestVersion(siteId);
		logger.info("-------------------------------------------------------------app"+app);
		String apkpath = app.getUrl();
		String path = request.getSession().getServletContext().getRealPath("/");
		File file = new File(path+apkpath);
		if(file.exists()){
			OutputStream os = res.getOutputStream();
	        try {
	        	String apkName = sysContext.getSysConfig("cms.apk.name");
	        	if(StringUtils.isBlank(apkName)){
	        		apkName = "app_last.apk";
	        	}
	            res.setHeader("Content-Disposition", "attachment; filename="+apkName);
	            res.setContentType("application/octet-stream; charset=utf-8");
	            os.write(FileUtils.readFileToByteArray(file));
	            os.flush();
	        }catch(Exception e){
	        	logger.error("download_apk:"+e.getMessage());
	        } finally {
	            if (os != null) {
	                os.close();
	            }
	        }
		}
		return null;
	}
	
	private boolean isMathBrowser(HttpServletRequest request, String regex){
		String userAgent = request.getHeader("User-Agent");
		logger.debug("userAgent:{}",userAgent);
		Pattern p =Pattern.compile(regex,Pattern.MULTILINE);  
        Matcher m = p.matcher(userAgent);  
        return m.find();		
	}
	
	@Autowired
	private AppVersionService appService;
	
	@Autowired
	private Properties properties;
}
