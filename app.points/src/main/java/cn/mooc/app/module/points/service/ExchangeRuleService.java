package cn.mooc.app.module.points.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.ExchangeGood;
import cn.mooc.app.module.points.data.entity.ExchangeRule;
import cn.mooc.app.module.points.data.entity.Good;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.rds.ExchangeGoodRepository;
import cn.mooc.app.module.points.data.rds.ExchangeRuleRepository;

/**
 * ExchangeRuleService
 * R币兑换规则服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class ExchangeRuleService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExchangeRuleRepository exchangeRuleRepository;
	@Autowired
	private ExchangeGoodRepository exchangeGoodRepository;
	@Autowired
	private GoodService goodService;
	
	public ExchangeRule getExchangeRuleById(Integer id) {
		ExchangeRule entity = exchangeRuleRepository.findOne(id);
		return entity;
	}
	
	public List<ExchangeRule> getAllExchangeRules(){
		return this.exchangeRuleRepository.findAll();
	}
	
	public Page<ExchangeRule> findExchangeRulePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<ExchangeRule> spec = SpecDynamic.buildSpec(filters.values());

		return exchangeRuleRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public ExchangeRule saveExchangeRule(ExchangeRule entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.exchangeRuleRepository.save(entity);
	}

	@Transactional
	public ExchangeRule saveExchangeRule(ExchangeRule entity, List<Map> detail_list) throws Exception {
		entity = saveExchangeRule(entity);
		updateExchangeGoods(entity, detail_list);
		return entity;
	}
	
	@Transactional
	public ExchangeRule updateExchangeRule(ExchangeRule entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.exchangeRuleRepository.save(entity);
	}
	

	@Transactional
	public ExchangeRule updateExchangeRule(ExchangeRule entity, List<Map> detail_list) throws Exception{
		entity = updateExchangeRule(entity);
		updateExchangeGoods(entity, detail_list);
		return entity;
	}
	
	private void updateExchangeGoods(ExchangeRule entity, List<Map> detailList){
		if(detailList == null || detailList.size() == 0){
			exchangeGoodRepository.deleteByExchangeRuleId(entity.getId());
		}else{
			List<ExchangeGood> goods = exchangeGoodRepository.findByExchangeRuleId(entity.getId());
			List<Integer> ids = new ArrayList<Integer>();
			for(ExchangeGood good : goods){
				ids.add(good.getGood().getId());
			}
			for(Map params : detailList){
				Integer goodId = Integer.parseInt(params.get("goodId")+"");
				Integer sumTotal = Integer.parseInt(params.get("sumTotal")+"");
				Integer total = Integer.parseInt(params.get("total")+"");
				Integer remainTotal = sumTotal - total;
				Integer dayLimit = Integer.parseInt(params.get("dayLimit")+"");
				if(ids.contains(goodId)){
					ExchangeGood bean = exchangeGoodRepository.findByExchangeRuleIdAndGoodId(entity.getId(), goodId);
					bean.setSumTotal(sumTotal);
					bean.setTotal(total);
					bean.setRemainTotal(remainTotal);
					bean.setDayLimit(dayLimit);
					exchangeGoodRepository.save(bean);
					ids.remove(goodId);
				}else{
					ExchangeGood bean = new ExchangeGood();
					Good good = goodService.getGoodById(goodId);
					bean.setGood(good);
					bean.setExchangeRule(entity);
					bean.setSumTotal(sumTotal);
					bean.setTotal(total);
					bean.setRemainTotal(remainTotal);
					bean.setDayLimit(dayLimit);
					exchangeGoodRepository.save(bean);
				}
			}
			if(ids != null && ids.size() > 0){
				for(Integer id : ids){
					exchangeGoodRepository.deleteByExchangeRuleIdAndGoodId(entity.getId(), id);
				}
			}
		}
	}
	
	@Transactional
	public boolean delExchangeRule(Integer id){
		try{
			this.exchangeRuleRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delExchangeRules(Integer[] ids){
		try{
			List<ExchangeRule> entities = this.exchangeRuleRepository.findAll(Arrays.asList(ids));
			this.exchangeRuleRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
