package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import cn.mooc.app.module.cms.data.entity.Attribute;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.data.rds.AttributeRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * AttributeService 文档属性服务
 * 
 * @author hwt
 * @date 2016-05-06
 */
@Service
public class AttributeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private SiteService siteService;

	public Attribute getAttributeById(Integer id) {
		Attribute entity = attributeRepository.findOne(id);
		return entity;
	}

	public Page<Attribute> findAttributeList(Map<String, Object> searchParams, PagerParam pageParam, Integer siteId) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Attribute> spec = SpecDynamic.buildSpec(filters.values());

		return attributeRepository.findAll(spec, pageParam.getPageRequest());

	}

	@Transactional
	public Attribute saveAttribute(Attribute entity, Integer siteId) throws Exception {
		entity.applyDefaultValue();
		entity.setSite(siteService.getSiteById(siteId));
		if (entity.getImageHeight() != null && entity.getImageWidth() != null) {
			entity.setWithImage(true);
		} else {
			entity.setWithImage(false);
		}
		try {
			return this.attributeRepository.save(entity);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@Transactional
	public Attribute updateAttribute(Attribute entity) {
		entity.applyDefaultValue();
		if (entity.getImageHeight() != null && entity.getImageWidth() != null) {
			entity.setWithImage(true);
		} else {
			entity.setWithImage(false);
		}
		try {
			return this.attributeRepository.save(entity);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	@Transactional
	public boolean delAttribute(Integer id) {
		try {
			this.attributeRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delAttributes(Integer[] ids) {
		try {
			List<Attribute> entities = this.attributeRepository.findAll(Arrays.asList(ids));
			this.attributeRepository.delete(entities);
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	public List<Attribute> findBySiteId(Integer siteId) {
		return attributeRepository.findBySiteId(siteId);
	}

	public List<Attribute> findByNumbers(String[] attr, Integer[] siteId) {
		if(attr == null){
			return new ArrayList<Attribute>();
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<String> attrList = Arrays.asList(attr);
		searchParams.put(SpecOperator.IN + "_number", attrList);
		List<Integer> siteIdList = Arrays.asList(siteId);
		searchParams.put(SpecOperator.IN + "_site.id", siteIdList);
		
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Attribute> spec = SpecDynamic.buildSpec(filters.values());
		
		return attributeRepository.findAll(spec);
	}
}
