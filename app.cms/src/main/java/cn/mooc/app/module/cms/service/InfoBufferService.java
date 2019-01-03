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

import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.InfoBuffer;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.InfoBufferRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * InfoBufferService
 * 文章明细服务
 * 
 * @author hwt
 * @date 2016-05-19
 */
@Service
public class InfoBufferService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InfoBufferRepository infoBufferRepository;
	@Autowired
	private InfoService infoService;
	
	public InfoBuffer getInfoBufferById(Integer id) {
		InfoBuffer entity = infoBufferRepository.findOne(id);
		return entity;
	}
	
	public List<InfoBuffer> getAllInfoBuffers(){
		return this.infoBufferRepository.findAll();
	}
	
	public Page<InfoBuffer> findInfoBufferPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<InfoBuffer> spec = SpecDynamic.buildSpec(filters.values());
		
		return infoBufferRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public InfoBuffer saveInfoBuffer(InfoBuffer entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.infoBufferRepository.save(entity);
	}

	@Transactional
	public InfoBuffer updateInfoBuffer(InfoBuffer entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.infoBufferRepository.save(entity);
	}
	
	@Transactional
	public boolean delInfoBuffer(Integer id){
		try{
			this.infoBufferRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delInfoBuffers(Integer[] ids){
		try{
			List<InfoBuffer> entities = this.infoBufferRepository.findAll(Arrays.asList(ids));
			this.infoBufferRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public InfoBuffer save(InfoBuffer bean, Info info) {
		bean.setInfo(info);
		bean.applyDefaultValue();
		info.setBuffer(bean);
		return bean;
	}

	@Transactional
	public int updateViews(Integer id) {
		Info info = infoService.getInfoById(id);
		InfoBuffer buffer = getInfoBufferById(id);
		if (buffer == null) {
			buffer = new InfoBuffer();
			save(buffer, info);
		}
		//2017-9-8李靖宇要求设置点击数为31倍
		info.setViews(info.getViews() + 31);
		return info.getViews();
	}
}
