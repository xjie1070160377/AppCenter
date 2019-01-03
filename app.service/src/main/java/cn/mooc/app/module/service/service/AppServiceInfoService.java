package cn.mooc.app.module.service.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.service.data.entity.AppServiceInfo;
import cn.mooc.app.module.service.data.rds.AppServiceInfoRepository;

/**
 * AppServiceInfoService
 * 服务号文章资讯实服务
 * 
 * @author zjj
 * @date 2016-08-16
 */
@Service
public class AppServiceInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppServiceInfoRepository AppServiceInfoRepository;
	
	public AppServiceInfo getAppServiceInfoById(Integer id) {
		AppServiceInfo entity = AppServiceInfoRepository.findOne(id);
		return entity;
	}
	
	public List<AppServiceInfo> getAllAppServiceInfos(){
		return this.AppServiceInfoRepository.findAll();
	}
	
	public Page<AppServiceInfo> findAppServiceInfoPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AppServiceInfo> spec = SpecDynamic.buildSpec(filters.values());

		return AppServiceInfoRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public AppServiceInfo saveAppServiceInfo(AppServiceInfo entity) throws Exception {
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServiceInfoRepository.save(entity);
	}

	@Transactional
	public AppServiceInfo updateAppServiceInfo(AppServiceInfo entity) throws Exception{
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServiceInfoRepository.save(entity);
	}
	
	@Transactional
	public boolean delAppServiceInfo(Integer id){
		try{
			this.AppServiceInfoRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAppServiceInfos(Integer[] ids){
		try{
			List<AppServiceInfo> entities = this.AppServiceInfoRepository.findAll(Arrays.asList(ids));
			this.AppServiceInfoRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
