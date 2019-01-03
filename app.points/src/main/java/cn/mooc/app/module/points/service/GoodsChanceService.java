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

import cn.mooc.app.module.points.data.entity.GoodsChance;
import cn.mooc.app.module.points.data.rds.GoodsChanceRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * GoodsChanceService
 * 商品抽奖概率服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class GoodsChanceService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GoodsChanceRepository goodsChanceRepository;
	
	public GoodsChance getGoodsChanceById(Integer id) {
		GoodsChance entity = goodsChanceRepository.findOne(id);
		return entity;
	}
	
	public List<GoodsChance> getAllGoodsChances(){
		return this.goodsChanceRepository.findAll();
	}
	
	public Page<GoodsChance> findGoodsChancePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<GoodsChance> spec = SpecDynamic.buildSpec(filters.values());

		return goodsChanceRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public GoodsChance saveGoodsChance(GoodsChance entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.goodsChanceRepository.save(entity);
	}

	@Transactional
	public GoodsChance updateGoodsChance(GoodsChance entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.goodsChanceRepository.save(entity);
	}
	
	@Transactional
	public boolean delGoodsChance(Integer id){
		try{
			this.goodsChanceRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delGoodsChances(Integer[] ids){
		try{
			List<GoodsChance> entities = this.goodsChanceRepository.findAll(Arrays.asList(ids));
			this.goodsChanceRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public List<GoodsChance> findByDrawRuleId(Integer ruleId) {
		// TODO Auto-generated method stub
		return goodsChanceRepository.findByDrawRuleId(ruleId);
	}
}
