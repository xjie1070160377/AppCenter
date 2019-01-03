package cn.mooc.app.module.cms.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.AppVersion;
import cn.mooc.app.module.cms.data.rds.AppVersionRepository;

/**
 * AppVersionService
 * App版本管理服务
 * 
 * @author Taven
 * @date 2016-05-16
 */
@Service
public class AppVersionService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppVersionRepository appVersionRepository;
	@Value("${apkfileurl}")
	private String apkfileurl;
	

	public AppVersion getLatestVersion(Integer siteId) {
		List<AppVersion> list = appVersionRepository.findBySiteId(siteId);
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}
	
	public AppVersion getAppVersionById(Integer id) {
		AppVersion entity = appVersionRepository.findOne(id);
		return entity;
	}
	
	public List<AppVersion> getAllAppVersions(){
		return this.appVersionRepository.findAll();
	}
	
	public Page<AppVersion> findAppVersionList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AppVersion> spec = SpecDynamic.buildSpec(filters.values());
		
		return appVersionRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public AppVersion saveAppVersion(AppVersion entity) throws Exception {
		
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		
		appVersionRepository.save(entity);
		removeLastFile(entity.getId(), entity.getSite().getId(), true);
		return entity;
	}

	@Transactional
	public AppVersion updateAppVersion(AppVersion entity) throws Exception{
		
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		appVersionRepository.save(entity);
		removeLastFile(entity.getId(), entity.getSite().getId(), true);
		return entity;
	}
	
	@Transactional
	public boolean delAppVersion(Integer id){
		try{
			AppVersion old = appVersionRepository.findOne(id);
			Integer siteId = old.getSite().getId();
			AppVersion bean = getLatestVersion(siteId);
			
			this.appVersionRepository.delete(id);
			removeLastFile(bean.getId(), siteId, false);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAppVersions(Integer[] ids){
		try{
			List<AppVersion> entities = this.appVersionRepository.findAll(Arrays.asList(ids));
			List<Integer> siteIds = new ArrayList<Integer>();
			List<Map> list = new ArrayList<Map>();
			for(AppVersion v : entities){
				if(!siteIds.contains(v.getSite().getId())){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("siteId", v.getSite().getId());
					siteIds.add(v.getSite().getId());
					
					List<AppVersion> apps = new ArrayList<AppVersion>();
					apps.add(v);
					params.put("obj", apps);
					list.add(params);
				}else{
					int index = siteIds.indexOf(v.getSite().getId());
					Map params = list.get(index);
					List<AppVersion> apps = (List<AppVersion>) params.get("obj");
					apps.add(v);
				}
			}
			for(Map params : list){
				Integer siteId = (Integer) params.get("siteId");
				List<AppVersion> apps = (List<AppVersion>) params.get("obj");
				
				AppVersion bean = getLatestVersion(siteId);
				this.appVersionRepository.delete(apps);
				removeLastFile(bean.getId(), siteId, false);
			}
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	private void removeLastFile(Integer id,Integer siteId, boolean flag) throws IOException{
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		String uploadsPath = servletContext.getRealPath("/") ;
		AppVersion bean = getLatestVersion(siteId);
		if(bean != null){
			if((flag && id.equals(bean.getId())) || (!flag && !id.equals(bean.getId()))){
				File dest = new File(uploadsPath+apkfileurl);
				File source = new File(uploadsPath+bean.getUrl());
				if(source.exists()){
					if(dest.exists()){
						dest.delete();
					}
					FileUtils.copyFile(source, dest);
				}
			}
		}
	}
}
