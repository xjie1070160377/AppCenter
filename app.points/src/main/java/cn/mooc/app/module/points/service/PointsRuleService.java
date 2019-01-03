package cn.mooc.app.module.points.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.rds.PointsRuleRepository;

@Service
public class PointsRuleService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private PointsRuleRepository dao;

	@Resource
	private PointsInfoService pointsInfoService;

	public Page<PointsRule> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<PointsRule> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}

	public List<PointsRule> findList() {
		return dao.findAll();
	}
	
	public List<PointsRule> findValidRule(){
		return dao.findValidRule();
	}

	public PointsRule get(Integer id) {
		return dao.findOne(id);
	}
	
	public PointsRule findByCode(String code){
		return dao.findByCode(code);
	}
	
	public PointsRule findSpecialRule(Integer infoId, Integer haslike, Integer hascomment){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PointsRule> query = cb.createQuery(PointsRule.class);
		Root<PointsRule> root = query.from(PointsRule.class);
		
		Subquery<PointsInfo> subquery = query.subquery(PointsInfo.class);
		Root<PointsInfo> rootFriend = subquery.from(PointsInfo.class);
		Predicate freidWhere = cb.and(cb.equal(rootFriend.get("info").get("id"), infoId), cb.equal(rootFriend.get("rule").get("id"), root.get("id")));
		if(haslike == 1){
			freidWhere = cb.and(freidWhere, cb.equal(rootFriend.get("haslike"), 1));
		}
		if(hascomment == 1){
			freidWhere = cb.and(freidWhere, cb.equal(rootFriend.get("hascomments"), 1));
		}
		subquery.where(freidWhere);
		subquery.select(rootFriend);
		
		
		Predicate whereInfoId = cb.equal(root.get("status"), 1);
		Predicate whereUser = cb.equal(root.get("isspecial"), 1);;
		whereInfoId = cb.and(whereInfoId, whereUser, cb.exists(subquery));
				
		query.where(whereInfoId);
		
		query.select(root).distinct(false);
		query.orderBy(cb.desc(root.get("points")));
		TypedQuery<PointsRule> typedQuery = em.createQuery(query); 
		List<PointsRule> list = typedQuery.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public PointsRule save(PointsRule bean) {
		return dao.save(bean);
	}
	
	public PointsRule save(PointsRule bean, List<Map> info_list) {
		bean = dao.save(bean);
		pointsInfoService.updateByRule(bean.getId(), info_list);
		return bean;
	}

	public PointsRule update(PointsRule bean) {
		return dao.save(bean);
	}
	
	public PointsRule update(PointsRule bean, List<Map> info_list) {
		pointsInfoService.updateByRule(bean.getId(), info_list);
		return dao.save(bean);
	}

	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				List<PointsInfo> pis = pointsInfoService.findListByRule(id);
				for(PointsInfo pi : pis){
					pointsInfoService.delete(pi.getId());
				}
				dao.delete(id);
			}
		}
	}
}
