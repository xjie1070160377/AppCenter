package cn.mooc.app.module.guestbook.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.entity.Guestbook.State;
import cn.mooc.app.module.guestbook.data.nosql.GuestbookRepository;

@Service
public class GuestbookService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GuestbookRepository guestbookRepository;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	private SysContext sysContext;
	
	
	public Guestbook findOne(String id){
		return guestbookRepository.findOne(id);
	}
	
	public Guestbook findOneByUserId(Long userId){
		return guestbookRepository.findOneByUserId(userId);
	}
	
	public List<Guestbook> findAll(){
		return guestbookRepository.findAll();
	}
	
	public List<Guestbook> findList(Query query, Class<Guestbook> cls){
		return mongoDbOpr.find(query, cls);
	}
	
	public Page<Guestbook> findPage(Map<String, Object> searchParams, PagerParam pageParam){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, Guestbook.class);
	}
	

	@Transactional
	public Guestbook save(Guestbook entity) throws Exception{
		return guestbookRepository.save(entity);
	}
	
	@Transactional
	public boolean delete(String id){
		try {
			guestbookRepository.delete(id);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	public int getRanking(Guestbook guestbook){
		Sort sort = new Sort(Direction.DESC, "ranking", "total", "replys");
		Query query = new Query();
		query.with(sort);
		int ranking = 0;
		List<Guestbook> list = this.findList(query, Guestbook.class);
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).get_id().equals(guestbook.get_id())){
				ranking = i;
				return ++ranking;
			}
		}
		return ++ranking;
	}
}
