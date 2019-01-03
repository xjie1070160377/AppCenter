package cn.mooc.app.module.guestbook.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.guestbook.data.entity.Guestbook;
import cn.mooc.app.module.guestbook.data.entity.Message;
import cn.mooc.app.module.guestbook.data.entity.Guestbook.State;
import cn.mooc.app.module.guestbook.data.nosql.MessageRepository;
import cn.mooc.app.module.guestbook.enums.DocumentType;

/**
 * @author linwei
 * @date 2016年9月1日
 * @description 留言服务
 */
@Service
public class MessageService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageRepository repository;
	@Autowired
	private GuestbookService guestbookService;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	private SysContext sysContext;

	public Message findOne(String _id){
		return repository.findOne(_id);
	}
	
	public List<Message> findByParentId(String parentId, Sort sort){
		return repository.findByParentId(parentId, sort); 
	}
	
	public List<Message> findByParentId(String parentId){
		Sort sort = new Sort(Direction.ASC, "createTime");
		return repository.findByParentId(parentId, sort); 
	}
	
	public List<Message> findAll(){
		return repository.findAll();
	}
	
	public Page<Message> findPage(Map<String, Object> searchParams, PagerParam pageParam){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, Message.class);
	}
	
	public long getTotals(Guestbook gb){
		Query query = new Query(Criteria
						.where("guestbookId").is(gb.get_id())
						.and("state").is(Message.AUDITED)
						.and("isVisibile").is(1)
						.and("type").is(DocumentType.Message)
						.and("parentId").exists(false));
		return mongoDbOpr.count(query, Message.class);
	}
	
	public long getReplys(Guestbook gb){
		Query query = new Query(Criteria
						.where("guestbookId").is(gb.get_id())
						.and("type").is(DocumentType.Reply));
		return mongoDbOpr.count(query, Message.class);
	}
	
	@Transactional
	public Message save(Message entity) throws Exception{
		return repository.save(entity);
	}
	
	@Transactional
	public boolean delete(String id){
		try {
			Message msg = this.findOne(id);
			deleteByParentId(id);
			repository.delete(id);
			
			Guestbook gb = guestbookService.findOne(msg.getGuestbookId());
			gb.setReplys(getReplys(gb));
			gb.setTotal(getTotals(gb));
			guestbookService.save(gb);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public boolean deleteByParentId(String parentId){
		try{
			List<Message> list = findByParentId(parentId);
			for(Message msg : list){
				List<Message> list1 = findByParentId(msg.get_id());
				if(CollectionUtils.isNotEmpty(list1)){
					deleteByParentId(msg.get_id());
				}
				repository.delete(msg.get_id());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
