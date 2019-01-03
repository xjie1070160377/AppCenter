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
import cn.mooc.app.module.cms.data.entity.InfoDetail;
import cn.mooc.app.module.cms.data.rds.InfoDetailRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * InfoDetailService
 * 文章明细服务
 * 
 * @author hwt
 * @date 2016-05-19
 */
@Service
public class InfoDetailService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InfoDetailRepository infoDetailRepository;
	
	public InfoDetail getInfoDetailById(Integer id) {
		InfoDetail entity = infoDetailRepository.findOne(id);
		return entity;
	}
	
	public List<InfoDetail> getAllInfoDetails(){
		return this.infoDetailRepository.findAll();
	}
	
	public Page<InfoDetail> findInfoDetailPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<InfoDetail> spec = SpecDynamic.buildSpec(filters.values());
		
		return infoDetailRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public InfoDetail saveInfoDetail(InfoDetail entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.infoDetailRepository.save(entity);
	}

	@Transactional
	public InfoDetail updateInfoDetail(InfoDetail entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.infoDetailRepository.save(entity);
	}
	
	@Transactional
	public boolean delInfoDetail(Integer id){
		try{
			this.infoDetailRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delInfoDetails(Integer[] ids){
		try{
			List<InfoDetail> entities = this.infoDetailRepository.findAll(Arrays.asList(ids));
			this.infoDetailRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public InfoDetail save(InfoDetail detail, Info info) {
		info.setDetail(detail);
		detail.setInfo(info);
		detail.applyDefaultValue();
		infoDetailRepository.save(detail);
		return detail;
	}

	@Transactional
	public InfoDetail update(InfoDetail bean, Info info) {
		bean.setInfo(info);
		bean.applyDefaultValue();
		bean = infoDetailRepository.save(bean);
		info.setDetail(bean);
		return bean;
	}
}
