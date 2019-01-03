package cn.mooc.app.module.ad.service;

import java.util.Arrays;
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
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.ad.data.entity.AdSlot;
import cn.mooc.app.module.ad.data.rds.AdSlotRepository;

/**
 * AdSlotService
 * 广告版位服务
 * 
 * @author oyhx
 * @date 2016-05-06
 */
@Service
public class AdSlotService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdSlotRepository adSlotRepository;
	
	public AdSlot getAdSlotById(Integer id) {
		AdSlot entity = adSlotRepository.findOne(id);
		return entity;
	}
	
	public List<AdSlot> getAllAdSlots(){
		return this.adSlotRepository.findAll();
	}
	
	public Page<AdSlot> findAdSlotList(Map<String, Object> searchParams,PagerParam pageParam){

		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<AdSlot> spec = SpecDynamic.buildSpec(filters.values());
		
		return adSlotRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public AdSlot saveAdSlot(AdSlot entity) throws Exception {
//		entity.applyDefaultValue();

		if(entity.getName()== null){
			throw new SaveFailureException("名称不能为空.");
		}
		if(entity.getType() == null){
			throw new SaveFailureException("类型不能为空.");
		}
		if(entity.getWidth() == null){
			throw new SaveFailureException("宽度不能为空.");
		}
		if(entity.getHeight() == null){
			throw new SaveFailureException("高度不能为空.");
		}
		return this.adSlotRepository.save(entity);
	}

	@Transactional
	public AdSlot updateAdSlot(AdSlot entity) throws SaveFailureException {
//		entity.applyDefaultValue();

		if(StringUtils.isEmpty(entity.getName())){
			throw new SaveFailureException("名称不能为空.");
		}
		if(entity.getType() == null){
			throw new SaveFailureException("类型不能为空.");
		}
		if(entity.getWidth() == null){
			throw new SaveFailureException("宽度不能为空.");
		}
		if(entity.getHeight() == null){
			throw new SaveFailureException("高度不能为空.");
		}
		return this.adSlotRepository.save(entity);
	}
	
	@Transactional
	public boolean delAdSlot(Integer id){
		try{
			this.adSlotRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delAdSlots(Integer[] ids){
		try{
			List<AdSlot> entities = this.adSlotRepository.findAll(Arrays.asList(ids));
			this.adSlotRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public AdSlot findByNumber(String number) {
		return adSlotRepository.findByNumber(number);
	}
}
