package cn.mooc.app.module.service.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.service.data.entity.AppService;
import cn.mooc.app.module.service.data.entity.ServInfo;
import cn.mooc.app.module.service.data.entity.ServInfoDetail;
import cn.mooc.app.module.service.data.entity.ServMsg;
import cn.mooc.app.module.service.data.entity.ServMsgInfo;
import cn.mooc.app.module.service.data.entity.ServMsgInfoPK;

public class MobileAppServConvert {

	public static MobileAppServInfo convertInfo(String siteUrl, ServInfo info, boolean addContent) {
		MobileAppServInfo servInfo = new MobileAppServInfo();
		servInfo.setInfoId(info.getInfoId());
		servInfo.setTitle(info.getDetail().getFullTitle());
		servInfo.setAuthor(info.getDetail().getAuthor());
//		String siteUrl = site.getSiteUrl();
		servInfo.setInfoImg(fullLinkPrefix(info.getDetail().getSmallImage(), siteUrl));
//		servInfo.setInfoImg(info.getDetail().getFSmallImage());
		servInfo.setNote(info.getDetail().getMetaDescription());
		if (addContent) {
			servInfo.setHtml(info.getDetail().getHtml() == null ? "" : fullContentPrefix(info.getDetail().getHtml(), siteUrl));
//			articleInfo.setContent(info.getText() == null ? "" : fullContentPrefix(info.getText(), siteUrl));
		} else {
			servInfo.setHtml("");
		}
		return servInfo;
	}
	public static String fullContentPrefix(String content, String prefix) {
		if (StringUtils.isNotBlank(content)) {
			String img="";     
			String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址     
			Pattern p_image = Pattern.compile(regEx_img,Pattern.CASE_INSENSITIVE);     
			Matcher m_image = p_image.matcher(content);   
		    while(m_image.find()){     
		         img = img + "," + m_image.group();     
		         Matcher m  = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src  
		         while(m.find()){  
		        	 String url = m.group(1);
		        	 if(url.startsWith("/upload")){
		        		 String newUrl = prefix + url;
		        		 content = content.replace(url, newUrl);
		        	 }
		         }  
		     } 
			//content = content.replace("\"/upload", "\"" + prefix + "/upload");
		}
		return content;
	}
	public static MobileAppServ convertAppService(String siteUrl, AppService appService, String orgName) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		MobileAppServ serv = new MobileAppServ();
		serv.setServiceId(appService.getServiceId());
		serv.setOrgName(orgName);
		serv.setServiceName(appService.getServiceName());
		serv.setServiceFullname(appService.getServiceFullname());
		Date createTime = appService.getCreateTime();
		if(createTime != null){
			String createTimeStr = df.format(createTime);
			serv.setCreateTime(createTimeStr);
			String createTimeFullStr = df2.format(createTime);
			serv.setCreateTimeFull(createTimeFullStr);
		}else {
			serv.setCreateTime("");
			serv.setCreateTimeFull("");
		}
		serv.setCharger(appService.getCharger());
		serv.setTelephone(appService.getTelephone());
		serv.setMobile(appService.getMobile());
		serv.setEmail(appService.getEmail());
		if(appService.getAuditTime() != null){
			String auditTimeStr = df.format(appService.getAuditTime());
			serv.setAuditTime(auditTimeStr);
		}else {
			serv.setAuditTime("");
		}
		serv.setAuditResult(appService.getAuditResult());
		serv.setNote(appService.getNote());
		serv.setAppServiceType(appService.getServicetype().getServiceTypeName());
		serv.setDocCount(appService.getDocCount());
		serv.setRegCount(appService.getRegCount());
		serv.setGoodCount(appService.getGoodCount());
		serv.setKeepCount(appService.getKeepCount());
		serv.setIsFree((appService.getIsFree()!=null&&appService.getIsFree()==1)?true:false);
		serv.setIntro(appService.getIntro());
//		String siteUrl = site.getSiteUrl();
		serv.setHeadImg(fullLinkPrefix(appService.getHeadImg(), siteUrl));
		return serv;
	}
	public static MobileAppServ convertAppService(String siteUrl, Map appService) {
		MobileAppServ serv = new MobileAppServ();
		serv.setServiceId(StringUtil.strnull2Int(appService.get("serviceId")));
		serv.setServiceName(StringUtil.strnull(appService.get("serviceName")));
		serv.setServiceFullname(StringUtil.strnull(appService.get("serviceFullname")));
		
		serv.setNote(StringUtil.strnull(appService.get("note")));
		serv.setAppServiceType(StringUtil.strnull(appService.get("serviceTypeName")));
		serv.setIntro(StringUtil.strnull(appService.get("intro")));
//		String siteUrl = site.getSiteUrl();
		serv.setHeadImg(fullLinkPrefix(StringUtil.strnull(appService.get("headImg")), siteUrl));
		serv.setIssubscribed(StringUtil.strnull2Int(appService.get("issubscribed")));
		return serv;
	}
	public static MobileAppServSimple convertAppServiceSimple(String siteUrl, Map appService) {
		MobileAppServSimple serv = new MobileAppServSimple();
		serv.setServiceId(StringUtil.strnull2Int(appService.get("serviceId")));
		serv.setServiceName(StringUtil.strnull(appService.get("serviceName")));
		serv.setServiceFullname(StringUtil.strnull(appService.get("serviceFullname")));
		
		serv.setAppServiceType(StringUtil.strnull(appService.get("serviceTypeName")));
		serv.setIntro(StringUtil.strnull(appService.get("intro")));
//		String siteUrl = site.getSiteUrl();
		serv.setHeadImg(fullLinkPrefix(StringUtil.strnull(appService.get("headImg")), siteUrl));
		serv.setIssubscribed(StringUtil.strnull2Int(appService.get("issubscribed")));
		return serv;
	}
	public static MobileAppServChannel convertAppServiceChannel(String serviceId) {
		MobileAppServChannel serv = new MobileAppServChannel();
		serv.setServiceId(StringUtil.strnull2Int(serviceId));
		serv.setMsgChannel(getMqttMsgChannel(serviceId));
		return serv;
	}
	
	public static String getJmsMsgChannel(String serviceId){
		return "sp." + serviceId;
	}
	
	public static String getMqttMsgChannel(String serviceId){
		return "sp/" + serviceId;
	}

	public static MobileServMsg convertMsg(String siteUrl, ServMsg msg) {
		MobileServMsg bean = new MobileServMsg();
		bean.setMsgid(msg.getMsgid());
		bean.setMsgType(msg.getMsgtype());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date pushTime = msg.getPushtime();
		if(pushTime != null){
			String pushTimeStr = df.format(pushTime);
			bean.setPushTime(pushTimeStr);
		}else {
			String pushTimeStr = df.format(msg.getCreatetime());
			bean.setPushTime(pushTimeStr);
		}
//		String siteUrl = "";
//		if(site !=null){
//			siteUrl = site.getSiteUrl();	
//		}
		
		
		Set<ServMsgInfo> set = msg.getMsgInfos();
		if(!set.isEmpty()){
			List<MobileServMsgDetail> detail = new ArrayList<MobileServMsgDetail>();
			Iterator<ServMsgInfo> iterator = set.iterator();
			while (iterator.hasNext()) {
				ServMsgInfo msgInfo = iterator.next();
				MobileServMsgDetail msgDetail = new MobileServMsgDetail();
				ServInfo info = msgInfo.getInfo();
				msgDetail.setTitle(info.getDetail().getFullTitle());
				msgDetail.setHeadImage(fullLinkPrefix(info.getDetail().getSmallImage(), siteUrl));
				
//				msgDetail.setHeadImage(info.getDetail().getFSmallImage());
				msgDetail.setIntro(info.getDetail().getMetaDescription());
				msgDetail.setInfoid(info.getInfoId());
				msgDetail.setIndex(msgInfo.getIndex());
				detail.add(msgDetail);
			}
			Collections.sort(detail);  
			bean.setDetail(detail);
		}
		return bean;
	}
	public static String fullLinkPrefix(String link, String prefix) {
		if (StringUtils.isNotBlank(link) && link.startsWith("/")) {
			link = prefix + link;
		}
		return link;
	}
	/**
	 * 将json字符串转换成List Map
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, String>> convertMsgInfoJson2ListMap(String jsonString) {
		JSONArray arry = JSONArray.fromObject("[" + jsonString + "]");
		List<Map<String, String>> rsList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < arry.size(); i++) {
			JSONObject jsonObject = arry.getJSONObject(i);
			for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
				Map<String, String> map = new HashMap<String, String>();
				String key = (String) iter.next();
				String value = jsonObject.get(key).toString();
				String[] keys = key.split("-");
				map.put(key, keys[1]);
				JSONObject obj = JSONObject.fromObject(value);
				for (Iterator<?> iter1 = obj.keys(); iter1.hasNext();) {
					String key1 = (String) iter1.next();
					String value1 = obj.get(key1).toString();
					map.put(key1, value1);
				}
				rsList.add(map);
			}
		}
		return rsList;
	}
	public static List<ServMsgInfo> convertListMap2ServInfoList(Long userId, String siteUrl, AppService appService, Integer orgId, ServMsg servMsg, String jsonString) {
		List<Map<String, String>> rsList = convertMsgInfoJson2ListMap(jsonString);
		return convertListMap2ServInfoList(userId, siteUrl, appService, orgId, servMsg, rsList);
	}
	/**
	 * 将List Map 转换成 List ServMsgInfo
	 * @param rsList
	 * @return
	 */
	public static List<ServMsgInfo> convertListMap2ServInfoList(Long userId, String siteUrl, AppService appService, Integer orgId, ServMsg servMsg, List<Map<String, String>> rsList) {
		List<ServMsgInfo> result = new ArrayList<ServMsgInfo>();
		for (Map<String, String> map : rsList) {
			ServInfo info = new ServInfo();
			info.setInfoId(map.get("infoid")==null?null:StringUtil.string2Int(map.get("infoid")));
			info.setPublishDate(new Timestamp(new Date().getTime()));
			info.setUserid(userId);
			info.setOrgId(orgId);
//			info.setFSiteId(site.getId());
			info.setComments(0);
			info.setDownloads(0);
			info.setDiggs(0);
			info.setPriority(0);
			info.setViews(0);
			info.setScore(0);
			info.setIsWithImage(false);
			info.setStatus("A");
			info.setHtmlStatus("A");
			info.setAppService(appService);
			
			ServInfoDetail infoDetail = new ServInfoDetail();
			infoDetail.setInfoId(info.getInfoId());
			infoDetail.setFullTitle(StringUtil.strnull(map.get("title")));
			infoDetail.setAuthor(StringUtil.strnull(map.get("author")));
			infoDetail.setHtml(StringUtil.strnull(map.get("html")));
			infoDetail.setSmallImage(StringUtil.strnull(map.get("img")));
			infoDetail.setMetaDescription(StringUtil.strnull(map.get("infoDesc")));
			infoDetail.setIsEm("A");
			infoDetail.setIsStrong("A");
			infoDetail.setTitle("A");
			infoDetail.setInfo(info);
			info.setDetail(infoDetail);
			
			ServMsgInfo servMsgInfo = new ServMsgInfo();
			servMsgInfo.setInfo(info);
			servMsgInfo.setMsg(servMsg);
			servMsgInfo.setIndex(StringUtil.string2Int(map.get("index")));
			ServMsgInfoPK servMsgInfoId = new ServMsgInfoPK();
			servMsgInfoId.setInfoId(info.getInfoId());
			servMsgInfoId.setMsgid(servMsg.getMsgid());
			servMsgInfo.setId(servMsgInfoId);
			
			result.add(servMsgInfo);
		}
		return result;
	}
}
