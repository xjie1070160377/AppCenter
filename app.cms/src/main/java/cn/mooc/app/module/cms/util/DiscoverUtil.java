package cn.mooc.app.module.cms.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import cn.mooc.app.core.service.CacheService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.module.cms.model.DiscoverServer;
import cn.mooc.app.module.cms.model.DiscoverServerItem;
import cn.mooc.app.module.cms.model.MobileModelConvert;
import cn.mooc.app.module.cms.service.SiteService;

@Service
public class DiscoverUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CacheService cacheService;
	@Autowired
	private SiteService siteService;
	@Value("${serviceJsonPath}")
	private String serviceJsonPath;
	
	public void main(){
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
		
	}
	
	public List<DiscoverServer> getDiscoverJson(String key, String filePath){
		if(cacheService.exists(key)){
			return cacheService.getCache(key);
		}else if(StringUtil.isNotEmpty(filePath)){
			
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			String uploadsPath = servletContext.getRealPath("/") ;
			File file = new File(uploadsPath+filePath);
			if(file.exists()){
				String text = "";
				try{
					text = FileUtils.readFileToString(file, "UTF-8");
				}catch(IOException e){
					logger.error("DiscoverUtil read file error!", e);
					return null;
				}
				JsonMapper mapper = new JsonMapper();
				Map params = mapper.fromJson(text, LinkedHashMap.class);
				List<DiscoverServer> list = new LinkedList<DiscoverServer>();
				Set<String> keySet = params.keySet();
				Iterator<String> keys = keySet.iterator();
				while(keys.hasNext()){
					String key1 = keys.next();
					Map p1 = (Map)params.get(key1);
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
						item.setName(StringUtil.strnull(key2));
						item.setLinkType(StringUtil.strnull2Int(p3.get("linktype")));
						String image = StringUtil.strnull(p3.get("image"), "");
						item.setImage(MobileModelConvert.fullLinkPrefix(image, siteService.getCurrentSite().getSiteUrl()));
						item.setUrl(StringUtil.strnull(p3.get("url"), ""));
						item.setEnabled(StringUtil.strnull(p3.get("enabled")));
						item.setRange(StringUtil.strnull(p3.get("range"), ""));
						items.add(item);
					}
					server.setItems(items);
					list.add(server);
				}
				String json = mapper.toJson(list);
				setDiscoverJson(key, list);
				return list;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	public void setDiscoverJson(String key, final List<DiscoverServer> json){
		cacheService.setCache(key, json);
	}
}
