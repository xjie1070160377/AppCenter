package cn.mooc.app.module.guestbook.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.data.specifications.CriteriaExpression;
import cn.mooc.app.core.exception.SaveFailureException;
import cn.mooc.app.core.service.MongoDbOpr;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.guestbook.data.entity.GuestbookType;
import cn.mooc.app.module.guestbook.data.nosql.GuestbookTypeRepository;

/**
 * @author linwei
 * @date 2016年8月27日
 * @description 留言类型服务
 */
@Service
public class GuestbookTypeService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GuestbookTypeRepository guestbookTypeRepository;
	@Autowired
	private MongoDbOpr mongoDbOpr;
	@Autowired
	private SysContext sysContext;
	
	
	public GuestbookType getGuestbookTypeById(String id) {
		GuestbookType entity = guestbookTypeRepository.findOne(id);
		return entity;
	}
	
	public List<GuestbookType> getGuestbookTypes(){
		return this.guestbookTypeRepository.findAll();
	}
	
	public Page<GuestbookType> findGuestbookTypeList(Map<String, Object> searchParams, PagerParam pageParam){
		Criteria criteria = CriteriaExpression.parse(searchParams);
		return mongoDbOpr.findPage(pageParam.getPageRequest(), criteria, GuestbookType.class);
	}
	
	@Transactional
	public GuestbookType saveGuestbookType(GuestbookType entity) throws Exception {
		return this.guestbookTypeRepository.save(entity);
	}

	@Transactional
	public GuestbookType updateGuestbookType(GuestbookType entity) throws Exception{
		if(StringUtils.isEmpty(entity.getName())){
			throw new SaveFailureException("名称不能为空.");
		}
		return this.guestbookTypeRepository.save(entity);
	}
	
	@Transactional
	public boolean delGuestbookType(String id){
		try{
			this.guestbookTypeRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	private int delGuestbookTypes(String[] ids){
		try{
			for(String id : ids){
				this.guestbookTypeRepository.delete(id);
			}
			return ids.length;
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}

}
