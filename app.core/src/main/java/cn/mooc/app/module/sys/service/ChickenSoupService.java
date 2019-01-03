package cn.mooc.app.module.sys.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.sys.data.entity.ChickenSoup;
import cn.mooc.app.module.sys.data.nosql.ChickenSoupRepository;

@Service
public class ChickenSoupService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ChickenSoupRepository chickenSoupRepository;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	
	
	public Page<ChickenSoup> findChickenSoupPage(Map<String, Object> searchParams, PagerParam pageParam){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, ChickenSoup.class);
	}
	
	public ChickenSoup getChickenSoupById(String id) {
		ChickenSoup entity = chickenSoupRepository.findOne(id);
		return entity;
	}
	
	
	public ChickenSoup savechickenSoup(ChickenSoup entity) throws Exception {
		try {
			return this.chickenSoupRepository.save(entity);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}


	public ChickenSoup updatechickenSoup(ChickenSoup entity) {
		
		try {
			return this.chickenSoupRepository.save(entity);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}


	public boolean delchickenSoup(String id) {
		try {
			this.chickenSoupRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public int delchickenSoups(String[] ids) {
		try {
			for(String id : ids){
				this.chickenSoupRepository.delete(id);
			}
			return ids.length;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	public List<ChickenSoup> getAllChickenSoups(){
		return this.chickenSoupRepository.findAll();
	}


	
}
