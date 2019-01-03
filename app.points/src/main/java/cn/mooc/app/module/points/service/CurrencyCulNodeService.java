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

import cn.mooc.app.module.points.data.entity.CurrencyCulNode;
import cn.mooc.app.module.points.data.rds.CurrencyCulNodeRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * CurrencyCulNodeService
 * 剔除栏目参与R币服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class CurrencyCulNodeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyCulNodeRepository culNodeRepository;
	
	public CurrencyCulNode getCurrencyCulNodeById(Integer id) {
		CurrencyCulNode entity = culNodeRepository.findOne(id);
		return entity;
	}
	
	public List<CurrencyCulNode> findByRuleId(Integer ruleId){
		return culNodeRepository.findByRuleId(ruleId);
	}
	
	public List<CurrencyCulNode> getAllCurrencyCulNodes(){
		return this.culNodeRepository.findAll();
	}
	
	public Page<CurrencyCulNode> findCurrencyCulNodePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CurrencyCulNode> spec = SpecDynamic.buildSpec(filters.values());

		return culNodeRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public CurrencyCulNode saveCurrencyCulNode(CurrencyCulNode entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.culNodeRepository.save(entity);
	}

	@Transactional
	public CurrencyCulNode updateCurrencyCulNode(CurrencyCulNode entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.culNodeRepository.save(entity);
	}
	
	@Transactional
	public boolean delCurrencyCulNode(Integer id){
		try{
			this.culNodeRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCurrencyCulNodes(Integer[] ids){
		try{
			List<CurrencyCulNode> entities = this.culNodeRepository.findAll(Arrays.asList(ids));
			this.culNodeRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
