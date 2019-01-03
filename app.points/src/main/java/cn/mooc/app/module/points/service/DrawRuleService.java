package cn.mooc.app.module.points.service;

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

import cn.mooc.app.module.points.data.entity.DrawRule;
import cn.mooc.app.module.points.data.entity.ExchangeGood;
import cn.mooc.app.module.points.data.entity.Good;
import cn.mooc.app.module.points.data.entity.GoodsChance;
import cn.mooc.app.module.points.data.rds.DrawRuleRepository;
import cn.mooc.app.module.points.data.rds.GoodsChanceRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * DrawRuleService
 * 抽奖规则服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class DrawRuleService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DrawRuleRepository drawRuleRepository;
	@Autowired
	private GoodsChanceRepository goodsChanceRepository;
	@Autowired
	private GoodService goodService;
	
	public DrawRule getDrawRuleById(Integer id) {
		DrawRule entity = drawRuleRepository.findOne(id);
		return entity;
	}
	
	public List<DrawRule> getAllDrawRules(){
		return this.drawRuleRepository.findAll();
	}
	
	public Page<DrawRule> findDrawRulePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<DrawRule> spec = SpecDynamic.buildSpec(filters.values());

		return drawRuleRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public DrawRule saveDrawRule(DrawRule entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.drawRuleRepository.save(entity);
	}
	@Transactional
	public DrawRule saveDrawRule(DrawRule entity, List<Map> detail_list) throws Exception {
		entity = saveDrawRule(entity);
		updateGoodsChance(entity, detail_list);
		return entity;
	}

	@Transactional
	public DrawRule updateDrawRule(DrawRule entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.drawRuleRepository.save(entity);
	}
	@Transactional
	public DrawRule updateDrawRule(DrawRule entity, List<Map> detail_list) throws Exception{
		entity = updateDrawRule(entity);
		updateGoodsChance(entity, detail_list);
		return entity;
	}
	
	private void updateGoodsChance(DrawRule entity, List<Map> detailList) {
		if(detailList == null || detailList.size() == 0){
			goodsChanceRepository.deleteByDrawRuleId(entity.getId());
		}else{
			List<GoodsChance> goods = goodsChanceRepository.findByDrawRuleId(entity.getId());
			List<Integer> ids = new ArrayList<Integer>();
			for(GoodsChance good : goods){
				ids.add(good.getGood().getId());
			}
			for(Map params : detailList){
				Integer goodId = Integer.parseInt(params.get("goodId")+"");
				Integer remianTotal = Integer.parseInt(params.get("remianTotal")+"");
				Integer total = Integer.parseInt(params.get("total")+"");
				Double chance = Double.parseDouble(params.get("chance")+"");
				if(ids.contains(goodId)){
					GoodsChance bean = goodsChanceRepository.findByDrawRuleIdAndGoodId(entity.getId(), goodId);
					bean.setTotal(total);
					bean.setRemianTotal(remianTotal);
					bean.setChance(chance);
					goodsChanceRepository.save(bean);
					ids.remove(goodId);
				}else{
					GoodsChance bean = new GoodsChance();
					Good good = goodService.getGoodById(goodId);
					bean.setGood(good);
					bean.setDrawRule(entity);
					bean.setTotal(total);
					bean.setRemianTotal(remianTotal);
					bean.setChance(chance);
					goodsChanceRepository.save(bean);
				}
			}
			if(ids != null && ids.size() > 0){
				for(Integer id : ids){
					goodsChanceRepository.deleteByDrawRuleIdAndGoodId(entity.getId(), id);
				}
			}
		}
	}

	@Transactional
	public boolean delDrawRule(Integer id){
		try{
			this.drawRuleRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delDrawRules(Integer[] ids){
		try{
			List<DrawRule> entities = this.drawRuleRepository.findAll(Arrays.asList(ids));
			this.drawRuleRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
