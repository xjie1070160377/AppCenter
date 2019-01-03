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

import cn.mooc.app.module.points.data.entity.UserDayCur;
import cn.mooc.app.module.points.data.rds.UserDayCurRepository;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;

/**
 * UserDayCurService
 * 用户日获取R币信息服务
 * 
 * @author zjj
 * @date 2016-07-04
 */
@Service
public class UserDayCurService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDayCurRepository userDayCurRepository;
	
	public UserDayCur getUserDayCurById(Integer id) {
		UserDayCur entity = userDayCurRepository.findOne(id);
		return entity;
	}
	
	public List<UserDayCur> getAllUserDayCurs(){
		return this.userDayCurRepository.findAll();
	}
	
	public UserDayCur findByUserTime(Long userId, Integer ruleId, String date){
		return userDayCurRepository.findByUserTime(userId, ruleId, date);
	}
	
	public UserDayCur findByUser(Long userId, Integer ruleId){
		return userDayCurRepository.findByUser(userId, ruleId);
	}
	
	public Page<UserDayCur> findUserDayCurPage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserDayCur> spec = SpecDynamic.buildSpec(filters.values());

		return userDayCurRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	@Transactional
	public UserDayCur saveUserDayCur(UserDayCur entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.userDayCurRepository.save(entity);
	}

	@Transactional
	public UserDayCur updateUserDayCur(UserDayCur entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.userDayCurRepository.save(entity);
	}
	
	@Transactional
	public boolean delUserDayCur(Integer id){
		try{
			this.userDayCurRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delUserDayCurs(Integer[] ids){
		try{
			List<UserDayCur> entities = this.userDayCurRepository.findAll(Arrays.asList(ids));
			this.userDayCurRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
}
