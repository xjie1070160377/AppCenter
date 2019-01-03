package cn.mooc.app.module.interact.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.interact.data.entity.InteractMark;
import cn.mooc.app.module.interact.data.nosql.InteractMarkRepository;
import cn.mooc.app.module.interact.enums.MarkType;

/**
 * InteractMarkService
 * 标记服务
 * 
 * @author oyhx
 * @date 2016-05-13
 */
@Service
public class InteractMarkService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InteractMarkRepository interactMarkRepository;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	
	public boolean hasMark(MarkType ftype, long fid, long userId){
		boolean result = false;
		List<InteractMark> list = interactMarkRepository.findList(fid, ftype, userId);
		if(list != null && list.size() > 0){
			return true;
		}
		return result;
	}
	
	public InteractMark getInteractMarkById(String id) {
		InteractMark entity = interactMarkRepository.findOne(new ObjectId(id));
		return entity;
	}
	
	public List<InteractMark> getAllInteractMarks(){
		return this.interactMarkRepository.findAll();
	}
	
	public List<InteractMark> findInteractMarkList(Map<String, Object> searchParams){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		Query query = new Query();
		query.addCriteria(criteria);
		return mongoDbOpr.find(query, InteractMark.class);
	}
	
	public Page<InteractMark> findInteractMarkList(Map<String, Object> searchParams, PagerParam pageParam){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, InteractMark.class);
	}
	
	@Transactional
	public InteractMark saveInteractMark(InteractMark entity) throws Exception {
		if(entity.getFid() == 0){
			throw new SaveFailureException("主键ID不能为空");
		}
		if(entity.getFtype() == null){
			throw new SaveFailureException("标记类型不能为空");
		}
		if(entity.getUserId() == 0){
			throw new SaveFailureException("用户ID不能为空");
		}
		if(entity.getCreateTime() == null){
			entity.setCreateTime(new Date());
		}
		entity.setMarkId(mongoDbOpr.getNextSeqIntFor(InteractMark.class));
		return this.interactMarkRepository.save(entity);
	}

	@Transactional
	public InteractMark updateInteractMark(InteractMark entity) throws Exception{
		if(entity.getFid() == 0){
			throw new SaveFailureException("主键ID不能为空");
		}
		if(entity.getFtype() == null){
			throw new SaveFailureException("标记类型不能为空");
		}
		if(entity.getUserId() == 0){
			throw new SaveFailureException("用户ID不能为空");
		}
		return this.interactMarkRepository.save(entity);
	}
	/**
	 * 取消标记操作
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean cancelInteractMark(long fid, MarkType type, Long userId) throws Exception {
		if(fid == 0){
			throw new SaveFailureException("主键ID不能为空");
		}
		if(type == null){
			throw new SaveFailureException("标记类型不能为空");
		}
		if(userId == 0){
			throw new SaveFailureException("用户ID不能为空");
		}
		//取消时，删除所有重复记录
		List<InteractMark> marks = interactMarkRepository.findList(fid, type, userId);
		for(InteractMark mark : marks){
			this.interactMarkRepository.delete(new ObjectId(mark.get_id()));
		}
		return true;
	}
	
	@Transactional
	public boolean delInteractMark(String id){
		try{
			this.interactMarkRepository.delete(new ObjectId(id));
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delInteractMarks(String[] ids){
		try{
			for(String id : ids){
				this.interactMarkRepository.delete(new ObjectId(id));
			}
			return ids.length;
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

	public long countMarks(MarkType ftype, long fid) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("EQ_ftype", ftype);
		searchParams.put("EQ_fid", fid);
		Criteria criteria = CriteriaExpression.parse(searchParams);
		Query query = new Query();
		query.addCriteria(criteria);
		return mongoDbOpr.count(query, InteractMark.class);
	}
}
