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
import cn.mooc.app.module.service.data.entity.AppServicetype;
import cn.mooc.app.module.service.data.rds.AppServicetypeRepository;

/**
 * AppServicetypeService
 * 服务号类型服务
 * 
 * @author zjj
 * @date 2016-08-16
 */
@Service
public class AppServicetypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppServicetypeRepository AppServicetypeRepository;
	
	public AppServicetype getAppServicetypeById(Integer id) {
		AppServicetype entity = AppServicetypeRepository.findOne(id);
		return entity;
	}
	
	public List<AppServicetype> getAllAppServicetypes(){
		return this.AppServicetypeRepository.findAll();
	}
	
	public Page<AppServicetype> findAppServicetypePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AppServicetype> spec = SpecDynamic.buildSpec(filters.values());

		return AppServicetypeRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public AppServicetype saveAppServicetype(AppServicetype entity) throws Exception {
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServicetypeRepository.save(entity);
	}

	@Transactional
	public AppServicetype updateAppServicetype(AppServicetype entity) throws Exception{
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServicetypeRepository.save(entity);
	}
	
	@Transactional
	public boolean delAppServicetype(Integer id){
		try{
			this.AppServicetypeRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAppServicetypes(Integer[] ids){
		try{
			List<AppServicetype> entities = this.AppServicetypeRepository.findAll(Arrays.asList(ids));
			this.AppServicetypeRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
