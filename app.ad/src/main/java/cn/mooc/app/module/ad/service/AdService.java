package cn.mooc.app.module.ad.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import  javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.ad.data.entity.Ad;
import cn.mooc.app.module.ad.data.entity.AdSlot;
import cn.mooc.app.module.ad.data.rds.AdRepository;

/**
 * AdService
 * 广告服务
 * 
 * @author oyhx
 * @date 2016-05-09
 */
@Service
public class AdService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdRepository adRepository;
	@Autowired
	private AdSlotService adSlotService;
	
	public Ad getAdById(Integer id) {
		Ad entity = adRepository.findOne(id);
		return entity;
	}
	
	public List<Ad> getAllAds(){
		return this.adRepository.findAll();
	}
	
	public Page<Ad> findAdList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Ad> spec = SpecDynamic.buildSpec(filters.values());
		
		return adRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public Ad saveAd(Ad entity, Integer adSlotid) throws Exception {
		entity.applyDefaultValue();
		if(StringUtils.isEmpty(entity.getName())){
			throw new SaveFailureException("名称不能为空.");
		}
		if(adSlotid == null){
			throw new SaveFailureException("广告版位不能为空.");
		}
		AdSlot adSlot = adSlotService.getAdSlotById(adSlotid);
		entity.setAdSlot(adSlot);
		
		return this.adRepository.save(entity);
	}

	@Transactional
	public Ad updateAd(Ad entity, Integer adSlotid) throws Exception {
		entity.applyDefaultValue();
		if(StringUtils.isEmpty(entity.getName())){
			throw new SaveFailureException("名称不能为空.");
		}
		if(adSlotid == null){
			throw new SaveFailureException("广告版位不能为空.");
		}
		AdSlot adSlot = adSlotService.getAdSlotById(adSlotid);
		entity.setAdSlot(adSlot);
		
		return adRepository.save(entity);
		//return this.adRepository.save(entity);
	}
	
	@Transactional
	public boolean delAd(Integer id){
		try{
			this.adRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAds(Integer[] ids){
		try{
			List<Ad> entities = this.adRepository.findAll(Arrays.asList(ids));
			this.adRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	public Page<Ad> findPage(String[] slot, Integer[] slotId, PagerParam pageParam) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(slot)) {
			List<String> list = Arrays.asList(slot);
			searchParams.put(SpecOperator.IN + "_adSlot.number", list);
		}
		if (ArrayUtils.isNotEmpty(slotId)) {
			List<Integer> list = Arrays.asList(slotId);
			searchParams.put(SpecOperator.IN + "_adSlot.id", list);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Ad> spec = SpecDynamic.buildSpec(filters.values());
		return adRepository.findAll(spec, pageParam.getPageRequest());
	}
	public List<Ad> findList(String[] slot, Integer[] slotId, PagerParam pageParam) {
		if(pageParam.getRows() > 0){
			Page<Ad> page = findPage(slot, slotId, pageParam);
			return page.getContent();
		}else{
			return findList(slot, slotId, pageParam.getSort());
		}
	}

	public List<Ad> findList(String[] slot, Integer[] slotId, Sort sort) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(slot)) {
			List<String> list = Arrays.asList(slot);
			searchParams.put(SpecOperator.IN + "_adSlot.number", list);
		}
		if (ArrayUtils.isNotEmpty(slotId)) {
			List<Integer> list = Arrays.asList(slotId);
			searchParams.put(SpecOperator.IN + "_adSlot.id", list);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Ad> spec = SpecDynamic.buildSpec(filters.values());
		return adRepository.findAll(spec, sort);
	}
	
	public List<Ad> findListByMobile(String[] slot, Integer[] slotId, Sort sort) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if (ArrayUtils.isNotEmpty(slot)) {
			List<String> list = Arrays.asList(slot);
			searchParams.put(SpecOperator.IN + "_adSlot.number", list);
		}
		if (ArrayUtils.isNotEmpty(slotId)) {
			List<Integer> list = Arrays.asList(slotId);
			searchParams.put(SpecOperator.IN + "_adSlot.id", list);
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Ad> spec = SpecDynamic.buildSpec(filters.values());
		
		spec = specListAd(spec);
		return adRepository.findAll(spec, sort);
	}
	
	private Specification<Ad> specListAd(final Specification<Ad> fsp) {
		Specification<Ad> spec = new Specification<Ad>() {
			public Predicate toPredicate(Root<Ad> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
//				pred = cb.and(pred, cb.or(cb.isNull(root.get("beginDate"))
//						));
//				
				cb.lessThanOrEqualTo( (Path)root.get("beginDate"), new Date());
				
				pred = cb.and(pred, cb.or(cb.isNull(root.get("beginDate")), cb.lessThanOrEqualTo( (Path)root.get("beginDate"), new Date())),
						cb.or(cb.isNull(root.get("endDate")), cb.greaterThanOrEqualTo( (Path)root.get("endDate"), new Date())));
				
				return pred;
			}
		};
		return spec;
	}
}
