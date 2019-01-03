package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.module.cms.data.entity.CmsMessage;
import cn.mooc.app.module.cms.data.rds.CmsMessageRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CmsMessageService
 * 消息中心服务
 * 
 * @author zjj
 * @date 2016-08-10
 */
@Service
public class CmsMessageService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CmsMessageRepository cmsMessageRepository;
	
	public CmsMessage getCmsMessageById(Integer id) {
		CmsMessage entity = cmsMessageRepository.findOne(id);
		return entity;
	}
	
	public List<CmsMessage> getAllCmsMessages(){
		return this.cmsMessageRepository.findAll();
	}
	
	public Page<CmsMessage> findCmsMessagePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsMessage> spec = SpecDynamic.buildSpec(filters.values());

		return cmsMessageRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	public CmsMessage findLastMessage(){
		return cmsMessageRepository.findLastMessage();
	}
	
	@Transactional
	public CmsMessage saveCmsMessage(CmsMessage entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.cmsMessageRepository.save(entity);
	}

	@Transactional
	public CmsMessage updateCmsMessage(CmsMessage entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.cmsMessageRepository.save(entity);
	}
	
	@Transactional
	public boolean delCmsMessage(Integer id){
		try{
			this.cmsMessageRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCmsMessages(Integer[] ids){
		try{
			List<CmsMessage> entities = this.cmsMessageRepository.findAll(Arrays.asList(ids));
			this.cmsMessageRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
