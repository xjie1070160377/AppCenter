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
import cn.mooc.app.module.service.data.entity.ServMsgInfo;
import cn.mooc.app.module.service.data.entity.ServMsgInfoPK;
import cn.mooc.app.module.service.data.rds.ServMsgInfoRepository;

/**
 * ServMsgInfoService
 * 服务文章消息服务
 * 
 * @author zjj
 * @date 2016-09-13
 */
@Service
public class ServMsgInfoService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServMsgInfoRepository servMsgInfoRepository;
	
	public ServMsgInfo getServMsgInfoById(ServMsgInfoPK id) {
		ServMsgInfo entity = servMsgInfoRepository.findOne(id);
		return entity;
	}
	
	public List<ServMsgInfo> getAllServMsgInfos(){
		return this.servMsgInfoRepository.findAll();
	}
	
	public Page<ServMsgInfo> findServMsgInfoPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ServMsgInfo> spec = SpecDynamic.buildSpec(filters.values());

		return servMsgInfoRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ServMsgInfo save(ServMsgInfo entity) throws Exception {
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.servMsgInfoRepository.save(entity);
	}

	@Transactional
	public ServMsgInfo update(ServMsgInfo entity) throws Exception{
//		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.servMsgInfoRepository.save(entity);
	}
	
	@Transactional
	public boolean delServMsgInfo(ServMsgInfoPK id){
		try{
			this.servMsgInfoRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delServMsgInfos(ServMsgInfoPK[] ids){
		try{
			List<ServMsgInfo> entities = this.servMsgInfoRepository.findAll(Arrays.asList(ids));
			this.servMsgInfoRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
