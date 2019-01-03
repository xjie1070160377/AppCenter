package cn.mooc.app.module.service.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
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
import cn.mooc.app.module.service.data.entity.AppServiceInfo;
import cn.mooc.app.module.service.data.entity.AppServiceInfoPK;
import cn.mooc.app.module.service.data.entity.ServInfo;
import cn.mooc.app.module.service.data.rds.AppServiceInfoRepository;
import cn.mooc.app.module.service.data.rds.ServInfoRepository;

/**
 * ServInfoService
 * 服务号文章服务
 * 
 * @author zjj
 * @date 2016-08-16
 */
@Service
public class ServInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServInfoRepository ServInfoRepository;
	@Autowired
	private AppServiceInfoRepository appServiceInfoDao;
	
	public ServInfo getServInfoById(Integer id) {
		ServInfo entity = ServInfoRepository.findOne(id);
		return entity;
	}
	
	public List<ServInfo> getAllServInfos(){
		return this.ServInfoRepository.findAll();
	}
	
	public Page<ServInfo> findServInfoPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ServInfo> spec = SpecDynamic.buildSpec(filters.values());

		return ServInfoRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ServInfo saveServInfo(ServInfo entity) throws Exception {
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.ServInfoRepository.save(entity);
	}

	@Transactional
	public ServInfo updateServInfo(ServInfo entity) throws Exception{
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.ServInfoRepository.save(entity);
	}
	
	@Transactional
	public ServInfo update(ServInfo bean,AppService appService) {
		bean = ServInfoRepository.save(bean);
		AppServiceInfo servInfo = new AppServiceInfo();
		servInfo.setAppService(appService);
		servInfo.setId(new AppServiceInfoPK(appService.getServiceId(), bean.getInfoId()));
		servInfo.setOpTime(new Timestamp(new Date().getTime()));
		appServiceInfoDao.save(servInfo);
		return bean;
	}
	
	@Transactional
	public boolean delServInfo(Integer id){
		try{
			this.ServInfoRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delServInfos(Integer[] ids){
		try{
			List<ServInfo> entities = this.ServInfoRepository.findAll(Arrays.asList(ids));
			this.ServInfoRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
