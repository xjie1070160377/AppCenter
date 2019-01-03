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

import cn.mooc.app.module.points.data.entity.Good;
import cn.mooc.app.module.points.data.rds.GoodRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * GoodService
 * 商品信息服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class GoodService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GoodRepository goodRepository;
	
	public Good getGoodById(Integer id) {
		Good entity = goodRepository.findOne(id);
		return entity;
	}
	
	public List<Good> getAllGoods(){
		return this.goodRepository.findAll();
	}
	
	public Page<Good> findGoodPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<Good> spec = SpecDynamic.buildSpec(filters.values());

		return goodRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public Good saveGood(Good entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.goodRepository.save(entity);
	}

	@Transactional
	public Good updateGood(Good entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.goodRepository.save(entity);
	}
	
	@Transactional
	public boolean delGood(Integer id){
		try{
			this.goodRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delGoods(Integer[] ids){
		try{
			List<Good> entities = this.goodRepository.findAll(Arrays.asList(ids));
			this.goodRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
