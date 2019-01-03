package cn.mooc.app.module.cms.service;

import java.util.ArrayList;
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

import cn.mooc.app.module.cms.data.entity.Collect;
import cn.mooc.app.module.cms.data.entity.CollectField;
import cn.mooc.app.module.cms.data.entity.Site;
import cn.mooc.app.module.cms.data.rds.CollectFieldRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CollectFieldService
 * 采集字段服务
 * 
 * @author linwei
 * @date 2017-06-23
 */
@Service
public class CollectFieldService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CollectFieldRepository collectFieldRepository;
	@Autowired
	private CollectService collectService;
	@Autowired
	private SiteService siteService;
	
	public CollectField getCollectFieldById(Integer id) {
		CollectField entity = collectFieldRepository.findOne(id);
		return entity;
	}
	
	public List<CollectField> getAllCollectFields(){
		return this.collectFieldRepository.findAll();
	}
	
	public Page<CollectField> findCollectFieldPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CollectField> spec = SpecDynamic.buildSpec(filters.values());

		return collectFieldRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public CollectField saveCollectField(CollectField entity) throws Exception {
		return this.collectFieldRepository.save(entity);
	}

	@Transactional
	public CollectField updateCollectField(CollectField entity) throws Exception{
		return this.collectFieldRepository.save(entity);
	}
	
	@Transactional
	public boolean delCollectField(Integer id){
		try{
			this.collectFieldRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCollectFields(Integer[] ids){
		try{
			List<CollectField> entities = this.collectFieldRepository.findAll(Arrays.asList(ids));
			this.collectFieldRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@Transactional
	public List<CollectField> save(String[] code, String[] name,
			Integer[] type, Integer collectId, Integer siteId) {
		List<CollectField> list = new ArrayList<CollectField>();
		for (int i = 0, len = code.length; i < len; i++) {
			CollectField bean = new CollectField();
			bean.setCode(code[i]);
			bean.setName(name[i]);
			bean.setType(type[i]);
			save(bean, collectId, siteId);
			list.add(bean);
		}
		return list;
	}
	
	@Transactional
	public CollectField save(CollectField bean, Integer collectId,
			Integer siteId) {
		Collect collect = collectService.getCollectById(collectId);
		bean.setCollect(collect);
		Site site = siteService.getSiteById(siteId);
		bean.setSite(site);
		bean.applyDefaultValue();
		bean = collectFieldRepository.save(bean);
		return bean;
	}
	
	@Transactional
	public List<CollectField> update(Integer[] id, Integer[] sourceType,
			String[] sourceText, String[] sourceUrl, String[] dataPattern,
			Boolean[] dataReg, String[] dataAreaPattern, Boolean[] dataAreaReg,
			String[] filter, String[] downloadType, String[] imageParam,
			String[] dateFormat) {
		List<CollectField> list = new ArrayList<CollectField>();
		for (int i = 0, len = id.length; i < len; i++) {
			CollectField bean = collectFieldRepository.findOne(id[i]);
			bean.setSourceType(sourceType[i]);
			bean.setSourceText(sourceText[i]);
			bean.setSourceUrl(sourceUrl[i]);
			bean.setDataPattern(dataPattern[i]);
			bean.setDataReg(dataReg[i]);
			bean.setDataAreaPattern(dataAreaPattern[i]);
			bean.setDataAreaReg(dataAreaReg[i]);
			bean.setFilter(filter[i]);
			bean.setDownloadType(downloadType[i]);
			bean.setImageParam(imageParam[i]);
			bean.setDateFormat(dateFormat[i]);
			collectFieldRepository.save(bean);
			list.add(bean);
		}
		return list;
	}
}
