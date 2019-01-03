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
import cn.mooc.app.module.service.data.entity.AppService;
import cn.mooc.app.module.service.data.rds.AppServiceRepository;

/**
 * AppServiceService
 * 服务号服务
 * 
 * @author zjj
 * @date 2016-08-16
 */
@Service
public class AppServiceService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AppServiceRepository AppServiceRepository;
	
	public AppService getAppServiceById(Integer id) {
		AppService entity = AppServiceRepository.findOne(id);
		return entity;
	}
	
	public List<AppService> getAllAppServices(){
		return this.AppServiceRepository.findAll();
	}
	
	public Page<AppService> findAppServicePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AppService> spec = SpecDynamic.buildSpec(filters.values());

		return AppServiceRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public AppService saveAppService(AppService entity) throws Exception {
		//entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServiceRepository.save(entity);
	}

	@Transactional
	public AppService updateAppService(AppService entity) throws Exception{
		//entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.AppServiceRepository.save(entity);
	}
	
	@Transactional
	public boolean delAppService(Integer id){
		try{
			this.AppServiceRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAppServices(Integer[] ids){
		try{
			List<AppService> entities = this.AppServiceRepository.findAll(Arrays.asList(ids));
			this.AppServiceRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	public AppService findByUserId(Long id) {
		List<AppService> list = AppServiceRepository.findByUserId(id);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
