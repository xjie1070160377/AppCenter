package cn.mooc.app.module.cms.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
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

import cn.mooc.app.module.cms.data.entity.SpecialCategory;
import cn.mooc.app.module.cms.data.rds.SpecialCategoryRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * SpecialCategoryService
 * 专题分类服务
 * 
 * @author hwt
 * @date 2016-05-06
 */
@Service
public class SpecialCategoryService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SpecialCategoryRepository specialCategoryRepository;
	@Autowired
	private SiteService siteService;
	
	public SpecialCategory getSpecialCategoryById(Integer id) {
		SpecialCategory entity = specialCategoryRepository.findOne(id);
		return entity;
	}
	
	public List<SpecialCategory> getAllSpecialCategorys(Integer siteId){
		return this.specialCategoryRepository.findAll(siteId);
	}
	
	public List<SpecialCategory> findList(Integer[] siteId, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(siteId)) {
			List<Integer> list = Arrays.asList(siteId);
			searchParams.put(SpecOperator.IN + "_site.id", list);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SpecialCategory> spec = SpecDynamic.buildSpec(filters.values());
		Page<SpecialCategory> page = specialCategoryRepository.findAll(spec, pageParam.getPageRequest());
		return page.getContent();
	}
	
	public Page<SpecialCategory> findSpecialCategoryList(Map<String, Object> searchParams,PagerParam pageParam, Integer siteId){

		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<SpecialCategory> spec = SpecDynamic.buildSpec(filters.values());
		
		return specialCategoryRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public SpecialCategory saveSpecialCategory(SpecialCategory entity, Integer SiteId) throws Exception {
		entity.applyDefaultValue();
		entity.setSite(siteService.getSiteById(SiteId));
		try {
			return this.specialCategoryRepository.save(entity);
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	@Transactional
	public SpecialCategory updateSpecialCategory(SpecialCategory entity) {
		entity.applyDefaultValue();
		try {
			return this.specialCategoryRepository.save(entity);
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	@Transactional
	public boolean delSpecialCategory(Integer id){
		try{
			this.specialCategoryRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delSpecialCategorys(Integer[] ids){
		try{
			List<SpecialCategory> entities = this.specialCategoryRepository.findAll(Arrays.asList(ids));
			this.specialCategoryRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
