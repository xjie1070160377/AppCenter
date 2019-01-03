package cn.mooc.app.module.api.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.ad.service.AdService;
import cn.mooc.app.module.api.model.MobileResultData;
import cn.mooc.app.module.api.service.MobileCommonService;
import cn.mooc.app.module.api.utils.JsonMapper;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.support.Servlets;

@Controller
public class MobileAdController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysContext sysContext;
	@Autowired
	private MobileCommonService mobileCommonService;
	@Autowired
	private AdService adService;
	
	@RequestMapping(value = { "/m-ad.htx" })
	public void getAd(HttpServletRequest request, HttpServletResponse response, String code, Integer dType, String version) {
		JsonMapper mapper = new JsonMapper();
		Site site = mobileCommonService.getCurrentMobileSite();
		String siteUrl = site.getSiteUrl();
		Sort sort = new Sort(Direction.ASC, "seq");
		if(StringUtils.isEmpty(code)){
			code = "picture";//first_picture
		}
		boolean isOpenAd = code.equals("picture") || code.equals("first_picture");
		List<Ad> adList = adService.findListByMobile(new String[]{code}, null, sort);
		if (adList == null || adList.isEmpty()) {
			if(code.equals("picture")){
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请添加开机启动图！")));
			}else if(code.equals("first_picture")){
				Servlets.writeHtml(response, mapper.toJson(MobileResultData.createError("请添加第一次开机启动图！")));
			}
			return;
		}
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for(Ad ad : adList){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("name", ad.getName() == null ? "" : ad.getName());
			dataMap.put("text", ad.getText() == null ? "" : ad.getText());
			dataMap.put("image", ad.getImage() == null ? "" : MobileModelConvert.fullLinkPrefix(ad.getImage(), siteUrl));
			//1：网站链接，2：文档链接，3：专题链接，4：图文链接
			dataMap.put("linkType", ad.getLinkType() == null ? "" : ad.getLinkType());
			dataMap.put("url", ad.getUrl() == null ? "" : ad.getUrl());
			ls.add(dataMap);
		}
		
		if(dType!=null && isOpenAd){
			logger.debug("dType：{}	version：{}", dType, version);
			//传了dType的，为新版本客户端
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("guideAd", ls);
			
			//检查是否开启节日活动
			boolean enable = sysContext.getSysConfigInt("app.holiday.ui.enable", 1) == 1 ? true : false;
			if(enable){
				this.loadStartConfig(resultMap, siteUrl);
			}			
			
			this.loadFloatAD(resultMap, siteUrl);
			
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(resultMap)));
		}else{
			Servlets.writeHtml(response, mapper.toJson(MobileResultData.createSuccess(ls)));
		}
		
	}
	
	private void loadStartConfig(Map<String, Object> resultMap, String siteUrl){
		//开机启动配置
		Map<String, Object> uiConfig = new HashMap<String, Object>();
		
		uiConfig.put("bottomBgImage", MobileModelConvert.fullLinkPrefix("/static/icon/bottomBgImage.png", siteUrl));
		
		String[] bottomItemIcons = new String[5];
		bottomItemIcons[0] = MobileModelConvert.fullLinkPrefix("/static/icon/A01.png", siteUrl);
		bottomItemIcons[1] = MobileModelConvert.fullLinkPrefix("/static/icon/A02.png", siteUrl);
		bottomItemIcons[2] = MobileModelConvert.fullLinkPrefix("/static/icon/A03.png", siteUrl);
		bottomItemIcons[3] = MobileModelConvert.fullLinkPrefix("/static/icon/A04.png", siteUrl);
		bottomItemIcons[4] = MobileModelConvert.fullLinkPrefix("/static/icon/A05.png", siteUrl);
		uiConfig.put("bottomItemIcons", bottomItemIcons);
		
		String[] bottomFocusItemIcons = new String[5];
		bottomFocusItemIcons[0] = MobileModelConvert.fullLinkPrefix("/static/icon/B01.png", siteUrl);
		bottomFocusItemIcons[1] = MobileModelConvert.fullLinkPrefix("/static/icon/B02.png", siteUrl);
		bottomFocusItemIcons[2] = MobileModelConvert.fullLinkPrefix("/static/icon/B03.png", siteUrl);
		bottomFocusItemIcons[3] = MobileModelConvert.fullLinkPrefix("/static/icon/B04.png", siteUrl);
		bottomFocusItemIcons[4] = MobileModelConvert.fullLinkPrefix("/static/icon/B05.png", siteUrl);
		uiConfig.put("bottomFocusItemIcons", bottomFocusItemIcons);
		
		resultMap.put("uiConfig", uiConfig);
		
		
				
		
	}
	
	private void loadFloatAD(Map<String, Object> resultMap, String siteUrl) {
		// 显示漂浮广告
		Map<String, Object> floatAd = new HashMap<String, Object>();
		Sort sort = new Sort(Direction.ASC, "seq");
		List<Ad> floatAdList = adService.findListByMobile(new String[] { "floatAd" }, null, sort);
		if (floatAdList != null && floatAdList.size() > 0) {
			Ad ad = floatAdList.get(0);
			floatAd.put("advId", ad.getId());
			floatAd.put("advTitle", StringUtils.isNotBlank(ad.getName()) ? ad.getName() : "");
			floatAd.put("description", StringUtils.isNotBlank(ad.getText()) ? ad.getText() : "");
			floatAd.put("imgUrl", StringUtils.isNotBlank(ad.getImage())
					? MobileModelConvert.fullLinkPrefix(ad.getImage(), siteUrl) : "");
			floatAd.put("linkType", ad.getLinkType() == null ? "" : ad.getLinkType());
			floatAd.put("url", ad.getUrl() == null ? "" : ad.getUrl());
			floatAd.put("imgWRatio", 0.9);
			floatAd.put("url", ad.getUrl() == null ? "" : ad.getUrl());

			resultMap.put("floatAd", floatAd);
		}
	}
	
}
