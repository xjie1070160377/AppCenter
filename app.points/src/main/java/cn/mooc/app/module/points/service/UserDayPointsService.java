package cn.mooc.app.module.points.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.UserDayPoints;
import cn.mooc.app.module.points.data.rds.UserDayPointsRepository;

@Service
public class UserDayPointsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
	private UserDayPointsRepository dao;

	public Page<UserDayPoints> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserDayPoints> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}
	
	public List<UserDayPoints> findList() {
		return dao.findAll();
	}
	
	public UserDayPoints get(Integer id) {
		return dao.findOne(id);
	}
	
	public UserDayPoints findByUserTime(Long userId, Integer ruleId, String day){
		return dao.findByUserTime(userId, ruleId, day);
	}
	
	public UserDayPoints findByUser(Long userId, Integer ruleId){
		return dao.findByUser(userId, ruleId);
	}
	
	public UserDayPoints save(UserDayPoints bean) {
		return dao.save(bean);
	}
	
	public UserDayPoints update(UserDayPoints bean) {
		return dao.save(bean);
	}
	
	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
}
