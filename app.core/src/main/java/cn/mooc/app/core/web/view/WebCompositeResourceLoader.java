package cn.mooc.app.core.web.view;

import java.util.ArrayList;
import java.util.List;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;

public class WebCompositeResourceLoader  implements ResourceLoader {

	private List<ResourceLoader> resourceLoaders = new ArrayList<ResourceLoader>();
	//默认使用缓存
	private boolean noCache = false;
	
	public void init(){
		
				
	}


	public List<ResourceLoader> getResourceLoaders() {
		return resourceLoaders;
	}

	public void setResourceLoaders(List<ResourceLoader> resourceLoaders) {
		this.resourceLoaders = resourceLoaders;
	}

	@Override
	public Resource getResource(String key) {
		//
		for (ResourceLoader resourceLoader : resourceLoaders) {
			Resource rs = resourceLoader.getResource(key);
			if(rs!=null && rs.getResourceLoader().exist(key)){
				return rs;
			}else{
				continue;
			}
		}
		return null;
	}

	@Override
	public boolean isModified(Resource key) {
		//
		if(noCache){
			return true;
		}
		
		return key.isModified();
	}

	@Override
	public boolean exist(String key) {
		// 
		for (ResourceLoader resourceLoader : resourceLoaders) {
			Resource rs = resourceLoader.getResource(key);
			if (rs == null) {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void close() {
		for (ResourceLoader resourceLoader : resourceLoaders) {
			resourceLoader.close();
		}
		
	}

	@Override
	public void init(GroupTemplate gt) {
		//清空模板缓存
		//gt.getProgramCache().clearAll();
		//		
		for (ResourceLoader resourceLoader : resourceLoaders) {
			resourceLoader.init(gt);
		}
		
	}

	@Override
	public String getResourceId(Resource resource, String key) {
		//
		if (resource == null){
			return key;
		}
		
		return resource.getResourceLoader().getResourceId(resource, key);
		
		//return key;
		
		
	}

	public boolean isNoCache() {
		return noCache;
	}


	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}
	
	
	
}
