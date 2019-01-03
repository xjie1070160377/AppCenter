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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.CmsModel;
import cn.mooc.app.module.cms.data.entity.CmsModelField;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.CmsModelRepository;

/**
 * CmsModelService 模型管理服务
 * 
 * @author hwt
 * @date 2016-05-09
 */
@Service
public class CmsModelService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CmsModelRepository cmsModelRepository;
	@Autowired
	private CmsModelFieldService cmsModelFieldService;
	@Autowired
	private SiteService siteService;

	public CmsModel getCmsModelById(Integer id) {
		CmsModel entity = cmsModelRepository.findOne(id);
		return entity;
	}

	public List<CmsModel> getAllCmsModels(Integer siteId) {
		return this.cmsModelRepository.findAll(siteId);
	}

	public Page<CmsModel> findCmsModelPage(Map<String, Object> searchParams, PagerParam pageParam, Integer siteId) {

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsModel> spec = SpecDynamic.buildSpec(filters.values());

		return cmsModelRepository.findAll(spec, pageParam.getPageRequest());

	}

	@Transactional
	public CmsModel saveCmsModel(CmsModel entity) throws Exception {
		entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.cmsModelRepository.save(entity);
	}

	@Transactional
	public CmsModel updateCmsModel(CmsModel entity) throws Exception {
		entity.applyDefaultValue();
		// if(StringUtils.isEmpty(entity.getName())){
		// throw new SaveFailureException("名称不能为空.");
		// }
		return this.cmsModelRepository.save(entity);
	}

	@Transactional
	public boolean delCmsModel(Integer id) {
		try {
			this.cmsModelFieldService.clearByModelId(id);
			this.cmsModelRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public List<CmsModel> findList(Integer siteId, String type) {
		return cmsModelRepository.findList(siteId, type);
	}

	public CmsModel findDefault(Integer siteId, String type) {
		List<CmsModel> modeList = findList(siteId, type);
		return (modeList == null || modeList.isEmpty()) ? null : modeList.get(0);
	}

	public List<CmsModel> findByNumbers(String[] model, Integer[] siteId) {
		if (model == null) {
			return new ArrayList<CmsModel>();
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		List<String> modelList = Arrays.asList(model);
		searchParams.put(SpecOperator.IN + "_number", modelList);
		List<Integer> siteIdList = Arrays.asList(siteId);
		searchParams.put(SpecOperator.IN + "_site.id", siteIdList);

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CmsModel> spec = SpecDynamic.buildSpec(filters.values());

		return cmsModelRepository.findAll(spec);
	}

	@Transactional
	public void save(CmsModel entity, Integer siteId, Map<String, String> customs) throws Exception {
		Site site = siteService.getSiteById(siteId);
		entity.setSite(site);
		entity.setCustoms(customs);
		entity.applyDefaultValue();
		cmsModelRepository.save(entity);
	}

	@Transactional
	public void copy(Integer oid, CmsModel entity, Integer siteId, Map<String, String> customs) throws Exception {
		save(entity, siteId, customs);
		if (oid != null) {
			CmsModel obean = getCmsModelById(oid);
			if (obean != null) {
				CmsModelField field;
				for (CmsModelField ofield : obean.getFields()) {
					field = cmsModelFieldService.copy(ofield, entity);
					entity.addField(field);
				}
			}
		}
	}

	@Transactional
	public void update(CmsModel entity, Map<String, String> customs) throws Exception {
		if (customs != null) {
			entity.getCustoms().clear();
			entity.getCustoms().putAll(customs);
		}
		entity.applyDefaultValue();
		cmsModelRepository.save(entity);
	}

	@Transactional
	public CmsModel[] batchUpdate(Integer[] id, String[] name, String[] number) throws Exception {
		Map<String, Integer> seqMap = new HashMap<String, Integer>();
		CmsModel[] beans = new CmsModel[id.length];
		for (int i = 0, len = id.length; i < len; i++) {
			if(StringUtils.isEmpty(name[i])){
				throw new SaveFailureException("名称不能为空！");
			}
			beans[i] = getCmsModelById(id[i]);
			String type = beans[i].getType();
			Integer seq = seqMap.get(type);
			if (seq == null) {
				seq = 0;
				seqMap.put(type, seq);
			} else {
				seq++;
				seqMap.put(type, seq);
			}
			beans[i].setSeq(seq);
			beans[i].setName(name[i]);
			beans[i].setNumber(number[i]);
			update(beans[i], null);
		}
		return beans;
	}
}
