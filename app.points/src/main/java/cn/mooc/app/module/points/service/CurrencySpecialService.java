package cn.mooc.app.module.points.service;

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

import cn.mooc.app.module.points.data.entity.CurrencySpecial;
import cn.mooc.app.module.points.data.rds.CurrencySpecialRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CurrencySpecialService
 * 指定专题文章获取R币规则服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class CurrencySpecialService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencySpecialRepository currencySpecialRepository;
	
	public CurrencySpecial getCurrencySpecialById(Integer id) {
		CurrencySpecial entity = currencySpecialRepository.findOne(id);
		return entity;
	}
	
	public List<CurrencySpecial> findByRuleId(Integer ruleId){
		return currencySpecialRepository.findByCurrencyRuleId(ruleId);
	}
	
	public List<CurrencySpecial> getAllCurrencySpecials(){
		return this.currencySpecialRepository.findAll();
	}
	
	public Page<CurrencySpecial> findCurrencySpecialPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CurrencySpecial> spec = SpecDynamic.buildSpec(filters.values());

		return currencySpecialRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public CurrencySpecial saveCurrencySpecial(CurrencySpecial entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.currencySpecialRepository.save(entity);
	}

	@Transactional
	public CurrencySpecial updateCurrencySpecial(CurrencySpecial entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.currencySpecialRepository.save(entity);
	}
	
	@Transactional
	public boolean delCurrencySpecial(Integer id){
		try{
			this.currencySpecialRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCurrencySpecials(Integer[] ids){
		try{
			List<CurrencySpecial> entities = this.currencySpecialRepository.findAll(Arrays.asList(ids));
			this.currencySpecialRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
