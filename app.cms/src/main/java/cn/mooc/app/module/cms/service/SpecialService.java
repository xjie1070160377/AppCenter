package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Special;
import cn.mooc.app.module.cms.data.entity.SpecialCategory;
import cn.mooc.app.module.cms.data.rds.SpecialRepository;
import cn.mooc.app.module.cms.model.SpecialModel;

/**
 * SpecialService 专题管理服务
 * 
 * @author hwt
 * @date 2016-05-09
 */
@Service
public class SpecialService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SpecialRepository specialRepository;
	@Autowired
	private SpecialCategoryService specialCategoryService;
	@Autowired
	private CmsModelService cmsModelService;
	@Autowired
	private SiteService siteService;

	public Special getSpecialById(Integer id) {
		Special entity = specialRepository.findOne(id);
		return entity;
	}

	public List<Special> getAllSpecials(Integer siteId) {
		return this.specialRepository.findAll(siteId);
	}

	public Page<Special> findSpecialPage(Map<String, Object> searchParams, PagerParam pageParam, Integer siteId) {

		searchParams.put(SpecOperator.EQ + "_site.id", siteId);

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Special> spec = SpecDynamic.buildSpec(filters.values());
		return specialRepository.findAll(spec, pageParam.getPageRequest());
	}

	public List<Special> findSpecialList(Integer siteId) {
		Sort sort = new Sort(Direction.DESC, "id");
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Special> spec = SpecDynamic.buildSpec(filters.values());
		return specialRepository.findAll(spec, sort);
	}

	public Page<SpecialModel> findSpecialModelList(Map<String, Object> searchParams, PagerParam pageParam, Integer siteId) {
		List<SpecialModel> modeLs = new ArrayList<SpecialModel>();
		Page<Special> pageData = findSpecialPage(searchParams, pageParam, siteId);

		List<Special> pageList = pageData.getContent();

		for (Special special : pageList) {
			modeLs.add(new SpecialModel(special));
		}
		PageRequest pageRequest = new PageRequest(pageParam.getPageStart(), pageParam.getRows(), null);
		return new PageImpl<SpecialModel>(modeLs, pageRequest, pageData.getTotalElements());
	}

	@Transactional
	public Special saveSpecial(Special entity, Integer categoryId, Integer modelId, Integer siteId, Long creatorId) throws Exception {
		validate(entity, categoryId, modelId);
		entity.applyDefaultValue();
		SpecialCategory category = specialCategoryService.getSpecialCategoryById(categoryId);
		CmsModel model = cmsModelService.getCmsModelById(modelId);
		entity.setCategory(category);
		entity.setCreatorId(creatorId);
		entity.setModel(model);
		entity.setSite(siteService.getSiteById(siteId));
		return this.specialRepository.save(entity);
	}

	@Transactional
	public Special updateSpecial(Special entity, Integer categoryId, Integer modelId) throws Exception {
		validate(entity, categoryId, modelId);
		entity.applyDefaultValue();
		SpecialCategory category = specialCategoryService.getSpecialCategoryById(categoryId);
		CmsModel model = cmsModelService.getCmsModelById(modelId);
		entity.setCategory(category);
		entity.setModel(model);
		return this.specialRepository.save(entity);
	}

	private void validate(Special entity, Integer categoryId, Integer modelId) throws SaveFailureException {
		if (StringUtils.isEmpty(entity.getTitle())) {
			throw new SaveFailureException("名称不能为空.");
		}
		if (categoryId == null) {
			throw new SaveFailureException("专题分类.");
		}
		if (modelId == null) {
			throw new SaveFailureException("专题模型.");
		}
	}

	@Transactional
	public boolean delSpecial(Integer id) {
		try {
			this.specialRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Transactional
	public int delSpecials(Integer[] ids) {
		try {
			List<Special> entities = this.specialRepository.findAll(Arrays.asList(ids));
			this.specialRepository.delete(entities);
			return entities.size();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	@Transactional
	public void derefer(Special bean) {
		bean.setRefers(bean.getRefers() - 1);
	}

	@Transactional
	public List<Special> refer(Integer[] beanIds) {
		if (ArrayUtils.isEmpty(beanIds)) {
			return Collections.emptyList();
		}
		Set<Integer> beanIdSet = new HashSet<Integer>();
		List<Special> beans = new ArrayList<Special>(beanIds.length);
		for (Integer beanId : beanIds) {
			if (!beanIdSet.contains(beanId)) {
				beans.add(refer(beanId));
				beanIdSet.add(beanId);
			}
		}
		return beans;
	}

	@Transactional
	public Special refer(Integer beanId) {
		Special bean = getSpecialById(beanId);
		bean.setRefers(bean.getRefers() + 1);
		return bean;
	}

	public Page<Special> findPage(Integer[] siteId, Integer[] categoryId, Date beginDate, Date endDate, Boolean isWithImage, Boolean isRecommend, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(siteId)) {
			List<Integer> list = Arrays.asList(siteId);
			searchParams.put(SpecOperator.IN + "_site.id", list);
		}
		if (ArrayUtils.isNotEmpty(categoryId)) {
			List<Integer> list = Arrays.asList(categoryId);
			searchParams.put(SpecOperator.IN + "_category.id", list);
		}
		if (beginDate != null) {
			searchParams.put(SpecOperator.GE + "_creationDate", beginDate);
		}
		if (endDate != null) {
			searchParams.put(SpecOperator.NE + "_creationDate", endDate);
		}

		if (isWithImage != null) {
			searchParams.put(SpecOperator.EQ + "_withImage", isWithImage);
		}
		if (isRecommend != null) {
			searchParams.put(SpecOperator.EQ + "_recommend", isRecommend);
		}

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Special> spec = SpecDynamic.buildSpec(filters.values());
		return specialRepository.findAll(spec, pageParam.getPageRequest());
	}

	public List<Special> findList(Integer[] siteId, Integer[] categoryId, Date beginDate, Date endDate, Boolean isWithImage, Boolean isRecommend, PagerParam pageParam) {
		Page<Special> page = findPage(siteId, categoryId, beginDate, endDate, isWithImage, isRecommend, pageParam);
		return page.getContent();
	}

	public Page<Special> findAll(Integer siteId, Map<String, Object> params, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(params != null)
		searchParams.putAll(params);
		if (siteId != null) {
			searchParams.put(SpecOperator.EQ + "_site.id", siteId);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Special> spec = SpecDynamic.buildSpec(filters.values());
		return specialRepository.findAll(spec, pageParam.getPageRequest());
	}

	public void updateViews(Special special) {
		special.setViews((special.getViews() == null ? 0 : special.getViews()) + 1);
		specialRepository.save(special);
	}

	public List<Special> findByIds(Integer[] idArr) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<Integer> list = Arrays.asList(idArr);
		searchParams.put(SpecOperator.IN + "_id", list);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Special> spec = SpecDynamic.buildSpec(filters.values());
		return specialRepository.findAll(spec);
	}
}
