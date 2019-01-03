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

import cn.mooc.app.module.points.data.entity.ExchangeGood;
import cn.mooc.app.module.points.data.rds.ExchangeGoodRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * ExchangeGoodService
 * R币兑换商品明细服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class ExchangeGoodService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeGoodRepository exchangeGoodRepository;
	
	public ExchangeGood getExchangeGoodById(Integer id) {
		ExchangeGood entity = exchangeGoodRepository.findOne(id);
		return entity;
	}
	
	public List<ExchangeGood> getAllExchangeGoods(){
		return this.exchangeGoodRepository.findAll();
	}
	
	public Page<ExchangeGood> findExchangeGoodPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ExchangeGood> spec = SpecDynamic.buildSpec(filters.values());

		return exchangeGoodRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	public List<ExchangeGood> findByExchangeRuleId(Integer ruleId){
		return exchangeGoodRepository.findByExchangeRuleId(ruleId);
	}
	
	@Transactional
	public ExchangeGood saveExchangeGood(ExchangeGood entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.exchangeGoodRepository.save(entity);
	}

	@Transactional
	public ExchangeGood updateExchangeGood(ExchangeGood entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.exchangeGoodRepository.save(entity);
	}
	
	@Transactional
	public boolean delExchangeGood(Integer id){
		try{
			this.exchangeGoodRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delExchangeGoods(Integer[] ids){
		try{
			List<ExchangeGood> entities = this.exchangeGoodRepository.findAll(Arrays.asList(ids));
			this.exchangeGoodRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
