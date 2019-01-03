package cn.mooc.app.module.cms.mcenter.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;


import cn.mooc.app.core.utils.HttpResponseUtil;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.model.DiscoverServer;
import cn.mooc.app.module.cms.model.DiscoverServerItem;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.util.DiscoverUtil;
import cn.mooc.app.module.cms.util.JsonMapper;

@Controller
@RequestMapping("/cms/discover")
public class DiscoverController extends CmsModuleController {
	@Value("${serviceJsonPath}")
	private static String serviceJsonPath;
	@Autowired
	private DiscoverUtil discoverUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(DiscoverController.class);
	
	private static List<Map<String, Object>> menu_list = new ArrayList<Map<String, Object>>();
	
	public static List<Map<String, Object>> getMenu_list(){
		
		if(menu_list == null || menu_list.size() == 0){
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			String uploadsPath = servletContext.getRealPath("/") ;
			String text = "";
			JsonMapper mapper = new JsonMapper();
			if((StringUtil.isNull(serviceJsonPath))){
				serviceJsonPath = "/uploads/servicejson/menu_json.txt";
			}
			try{
				File file = new File(uploadsPath+serviceJsonPath);
				if(file.exists()){
					try{
						text = FileUtils.readFileToString(file, "UTF-8");
						logger.info("发现文本：{}", text);
						
						menu_list = mapper.fromJson(text, ArrayList.class);
					}catch(IOException e){
						logger.error("读取发现文档失败：", e);
					}
				}
			}catch(Exception e){
				logger.error("读取目录文档失败：", e);
			}
		}
		return menu_list;
	}
	
	@RequestMapping("view")
	public String view(HttpServletRequest request, Integer type, org.springframework.ui.Model modelMap) {
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
		String text = "";
		JsonMapper mapper = new JsonMapper();
		if(type == null){
			type = 1;
		}
		getMenu_list();
		if(menu_list != null && menu_list.size() > 0){
			modelMap.addAttribute("type_list", menu_list);
			modelMap.addAttribute("type", type);
			
			Integer t = type;
			List<Map<String, Object>> pls = new ArrayList<Map<String, Object>>();
			menu_list.forEach((Map<String, Object> p) ->{ if(StringUtil.strnull2Int(p.get("type")).equals(t)){pls.add(p);} });
			if(pls != null){
				Map<String, Object> params = pls.get(0);
				
				String filepath = StringUtil.strnull(params.get("filePath"));
				File file1 = new File(uploadsPath+filepath);
				if(file1.exists()){
					try{
						text = FileUtils.readFileToString(file1, "UTF-8");
						logger.info("发现文本：{}", text);
						
						Map info = mapper.fromJson(text, LinkedHashMap.class);
						Set<String> args = info.keySet();
						modelMap.addAttribute("serviceInfos", args);
						modelMap.addAttribute("info", info);
						modelMap.addAttribute("menuInfo", params);
						request.setAttribute("jsonText", mapper.toJson(info));
					}catch(IOException e){
						logger.error("读取发现文档失败：", e);
					}
				}
			}
		}
		return ModuleView("/discover/view");
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> update(String text, Integer type, HttpServletRequest resq, HttpServletResponse resp){
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
		JsonMapper mapper = new JsonMapper();
		getMenu_list();
		if(menu_list != null && menu_list.size() > 0){
			Integer t = type;
			List<Map<String, Object>> pls = new ArrayList<Map<String, Object>>();
			menu_list.forEach((Map<String, Object> p) ->{ if(StringUtil.strnull2Int(p.get("type")).equals(t)){pls.add(p);} });
			if(pls != null){
				Map<String, Object> params = pls.get(0);
			
				String filepath = StringUtil.strnull(params.get("filePath"));
				File file1 = new File(uploadsPath+filepath);
				String key = StringUtil.strnull(params.get("key"));
				try{
					logger.info("保存的发现文本：{}", text);
					Site site = this.getCurrentSite();
					Map info = mapper.fromJson(text, Map.class);
					Map newmap = sortMap(info);
					Set keyset = newmap.keySet();
					Iterator<String> keys = keyset.iterator();
					while(keys.hasNext()){
						String key1 = keys.next();
						Map p1 = (Map)((Map)newmap.get(key1)).get("items");
						Map newp1 = sortMap(p1);
						((Map)newmap.get(key1)).put("items", newp1);
					}
					text = mapper.toJson(newmap);
					
					FileUtils.write(file1, text, "UTF-8");
					
					List<DiscoverServer> info_list = new LinkedList<DiscoverServer>();
					Set<String> keySet = newmap.keySet();
					Iterator<String> keys0 = keySet.iterator();
					while(keys0.hasNext()){
						String key1 = keys0.next();
						Map p1 = (Map)newmap.get(key1);
						DiscoverServer server = new DiscoverServer();
						server.setServiceName(key1);
						
						Map p2 = (LinkedHashMap)p1.get("items");
						Set<String> keySet2 = p2.keySet();
						Iterator<String> keys2 = keySet2.iterator();
						
						List<DiscoverServerItem> items = new LinkedList<DiscoverServerItem>();
						while(keys2.hasNext()){
							String key2 = keys2.next();
							Map p3 = (Map)p2.get(key2);
							DiscoverServerItem item = new DiscoverServerItem();
							item.setName(key2);
							item.setLinkType(StringUtil.string2Int(p3.get("linktype")));
							String image = StringUtil.strnull(p3.get("image"));
							item.setImage(MobileModelConvert.fullLinkPrefix(image, site.getSiteUrl()));
							item.setUrl(StringUtil.strnull(p3.get("url")));
							item.setEnabled(StringUtil.strnull(p3.get("enabled")));
							item.setRange(StringUtil.strnull(p3.get("range")));
							items.add(item);
						}
						server.setItems(items);
						info_list.add(server);
					}
					discoverUtil.setDiscoverJson(key, info_list);
				}catch(IOException e){
					logger.error("读取发现文档失败：", e);
					return HttpResponseUtil.failureJson("保存失败："+e.getMessage());
				}
			}
		}
		return HttpResponseUtil.successJson();
	}
	
	private static Map sortMap(Map oldMap) {  
        ArrayList<Map.Entry<String, Map>> list = new ArrayList<Map.Entry<String, Map>>(oldMap.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Map>>() {  
  
            @Override  
            public int compare(Entry<String, Map> arg0,  
                    Entry<String, Map> arg1) {  
            	return StringUtil.string2Int(arg0.getValue().get("px")+"")-StringUtil.string2Int(arg1.getValue().get("px")+"");
            }
        });  
        Map newMap = new LinkedHashMap();  
        for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    }
	
	/**
	 * 新增服务
	 * @param type
	 * @param fwmc
	 * @param fwpx
	 * @param resq
	 * @param resp
	 * @return
	 */
	@RequestMapping("addService")
	@ResponseBody
	public Map<String, Object> addService(Integer type, String fwmc, String fwpx, HttpServletRequest resq, HttpServletResponse resp){
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
		JsonMapper mapper = new JsonMapper();
		getMenu_list();
		
		if(menu_list != null && menu_list.size() > 0){
			List<Map<String, Object>> pls = new ArrayList<Map<String, Object>>();
			menu_list.forEach((Map<String, Object> p) ->{ if(StringUtil.strnull2Int(p.get("type")).equals(type)){pls.add(p);} });
			
			if(pls != null){
				Map<String, Object> params = pls.get(0);
				Integer rtype = StringUtil.strnull2Int(params.get("type"));
				String filepath = StringUtil.strnull(params.get("filePath"));
				
				try{
					File file1 = new File(uploadsPath+filepath);
					String text = FileUtils.readFileToString(file1, "UTF-8");
					logger.info("发现文本：{}", text);
							
					Map info = mapper.fromJson(text, LinkedHashMap.class);
					Map p1 = new HashMap();
					p1.put("px", fwpx);
					p1.put("items", new HashMap());
					
					info.put(fwmc, p1);
					Map newmap = sortMap(info);
						
					text = mapper.toJson(newmap);
								
					FileUtils.write(file1, text, "UTF-8");
				}catch(IOException e){
					logger.error("读取发现文档失败：", e);
					return HttpResponseUtil.failureJson("保存失败："+e.getMessage());
				}	
			}	
		}
		return HttpResponseUtil.successJson();
	}
	
	
	/**
	 * 新增应用
	 * @param type
	 * @param fwmc
	 * @param name
	 * @param url
	 * @param linktype
	 * @param enabled
	 * @param range
	 * @param px
	 * @param image_0_0
	 * @param resq
	 * @param resp
	 * @return
	 */
	@RequestMapping("addApp")
	@ResponseBody
	public Map<String, Object> addApp(Integer type, String fwmc, String name, String url, String linktype, String enabled, String range, String px, String  image_0_0,
		HttpServletRequest resq, HttpServletResponse resp){
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
		JsonMapper mapper = new JsonMapper();
		getMenu_list();
		Site site = this.getCurrentSite();
		if(menu_list != null && menu_list.size() > 0){
			List<Map<String, Object>> pls = new ArrayList<Map<String, Object>>();
			menu_list.forEach((Map<String, Object> p) ->{ if(StringUtil.strnull2Int(p.get("type")).equals(type)){pls.add(p);} });
			
			if(pls != null){
				Map<String, Object> params = pls.get(0);
				Integer rtype = StringUtil.strnull2Int(params.get("type"));
				String filepath = StringUtil.strnull(params.get("filePath"));
				String key = StringUtil.strnull(params.get("key"));
				
				try{
					File file1 = new File(uploadsPath+filepath);
					String text = FileUtils.readFileToString(file1, "UTF-8");
					logger.info("发现文本：{}", text);
							
					Map info = mapper.fromJson(text, LinkedHashMap.class);
					
					Object child = info.get(fwmc);
					if(child != null){
						Map c = (Map)child;
						Map pc = (Map) c.get("items");
						Map pa = new HashMap();
						pa.put("linktype", linktype);
						pa.put("enabled", enabled);
						pa.put("image", image_0_0);
						pa.put("range", range);
						pa.put("px", px);
						pa.put("url", url);
						pc.put(name, pa);
						
						Map newmap = sortMap(pc);
						c.put("items", newmap);
						
						text = mapper.toJson(info);
						
						FileUtils.write(file1, text, "UTF-8");
						
						List<DiscoverServer> info_list = new LinkedList<DiscoverServer>();
						Set<String> keySet = info.keySet();
						Iterator<String> keys0 = keySet.iterator();
						while(keys0.hasNext()){
							String key1 = keys0.next();
							Map p1 = (Map)info.get(key1);
							DiscoverServer server = new DiscoverServer();
							server.setServiceName(key1);
							
							Map p2 = (LinkedHashMap)p1.get("items");
							Set<String> keySet2 = p2.keySet();
							Iterator<String> keys2 = keySet2.iterator();
							
							List<DiscoverServerItem> items = new LinkedList<DiscoverServerItem>();
							while(keys2.hasNext()){
								String key2 = keys2.next();
								Map p3 = (Map)p2.get(key2);
								DiscoverServerItem item = new DiscoverServerItem();
								item.setName(key2);
								item.setLinkType(StringUtil.string2Int(p3.get("linktype")));
								String image = StringUtil.strnull(p3.get("image"));
								item.setImage(MobileModelConvert.fullLinkPrefix(image, site.getSiteUrl()));
								item.setUrl(StringUtil.strnull(p3.get("url")));
								item.setEnabled(StringUtil.strnull(p3.get("enabled")));
								item.setRange(StringUtil.strnull(p3.get("range")));
								items.add(item);
							}
							server.setItems(items);
							info_list.add(server);
						}
						discoverUtil.setDiscoverJson(key, info_list);
					}
				}catch(IOException e){
					logger.error("读取发现文档失败：", e);
					return HttpResponseUtil.failureJson("保存失败："+e.getMessage());
				}	
			}	
		}
		return HttpResponseUtil.successJson();
	}
	
	
}
