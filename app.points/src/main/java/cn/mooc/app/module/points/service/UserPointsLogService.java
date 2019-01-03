package cn.mooc.app.module.points.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.data.specifications.SpecOperator;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.points.data.entity.CurrencyLog;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.entity.UserPoints;
import cn.mooc.app.module.points.data.entity.UserPointsLog;
import cn.mooc.app.module.points.data.rds.UserPointsLogRepository;

@Service
public class UserPointsLogService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserPointsLogRepository dao;
	@PersistenceContext
	private EntityManager em;
	
	public Page<UserPointsLog> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserPointsLog> spec = SpecDynamic.buildSpec(filters.values());
		return dao.findAll(spec, pageParam.getPageRequest());
	}
	
	public Page<UserPointsLog> findPointsLogPage(Map<String, Object> searchParams,PagerParam pageParam, String userName){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserPointsLog> spec = SpecDynamic.buildSpec(filters.values());
		spec = specPointsLog(spec, userName);
		return dao.findAll(spec, pageParam.getPageRequest());
		
	}
	
	private Specification<UserPointsLog> specPointsLog(final Specification<UserPointsLog> fsp, final String userName) {
		Specification<UserPointsLog> spec = new Specification<UserPointsLog>() {
			public Predicate toPredicate(Root<UserPointsLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if(StringUtil.isNotEmpty(userName)){
					Subquery<SysUserEntity> subquery =  query.subquery(SysUserEntity.class);
					Root<SysUserEntity> subroot = subquery.from(SysUserEntity.class);
					subquery.where(cb.equal(root.get("userPoints").get("id"), subroot.get("id")), cb.like((Path)subroot.get("userName"), userName));
					subquery.select(subroot);
					pred = cb.and(pred, cb.exists(subquery));
				}
				
				return pred;
			}
		};
		return spec;
	}

	public List<UserPointsLog> findList() {
		return dao.findAll();
	}

	public UserPointsLog get(Integer id) {
		return dao.findOne(id);
	}
	
	public UserPointsLog findLogByUserInfo(Long userId, Integer infoId, int isdiggs, int iscomment, int isread){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserPointsLog> query = cb.createQuery(UserPointsLog.class);
		Root<UserPointsLog> root = query.from(UserPointsLog.class);
		
		
		Predicate where = cb.and(cb.equal(root.get("infoId"), infoId), cb.equal(root.get("userPoints").get("id"), userId));
		
		Subquery<PointsRule> subquery = query.subquery(PointsRule.class);
		Root<PointsRule> rootsub = subquery.from(PointsRule.class);
		Predicate s1 = cb.and(cb.equal(rootsub.get("id"), root.get("rule").get("id")), cb.equal(rootsub.get("status"), 1), cb.equal(rootsub.get("isspecial"), 0));
		if(isdiggs==1){
			s1 = cb.and(s1, cb.equal(rootsub.get("ruleCode"), PointsRule.RULE_DIGGS));
		}
		if(iscomment==1){
			s1 = cb.and(s1, cb.equal(rootsub.get("ruleCode"), PointsRule.RULE_COMMENT));
		}
		if(isread == 1){
			s1 = cb.and(s1, cb.equal(rootsub.get("ruleCode"), PointsRule.RULE_READ));
		}
		subquery.where(s1);
		subquery.select(rootsub);
		
		Subquery<PointsRule> subquery1 = query.subquery(PointsRule.class);
		Root<PointsRule> rootsub1 = subquery1.from(PointsRule.class);
		Predicate s2 = cb.and(cb.equal(rootsub1.get("id"), root.get("rule").get("id")), cb.equal(rootsub1.get("status"), 1), cb.equal(rootsub1.get("isspecial"), 1));
		
		Subquery<PointsInfo> subquery11 = subquery1.subquery(PointsInfo.class);
		Root<PointsInfo> rootsub11 = subquery11.from(PointsInfo.class);
		
		Predicate s3 = cb.equal(rootsub11.get("rule").get("id"), rootsub1.get("id"));
		if(isdiggs==1){
			s3 = cb.and(s3, cb.equal(rootsub11.get("haslike"), 1));
		}
		if(iscomment==1){
			s3 = cb.and(s3, cb.equal(rootsub11.get("hascomments"), 1));
		}
		subquery11.where(s3);
		subquery11.select(rootsub11);
		
		subquery1.where(cb.and(s2, cb.exists(subquery11)));
		subquery1.select(rootsub1);
		
		Predicate o1 = cb.or(cb.exists(subquery1), cb.exists(subquery));
		
		query.where(cb.and(where, o1));
		
		query.select(root).distinct(false);
		query.orderBy(cb.desc(root.get("points")));
		TypedQuery<UserPointsLog> typedQuery = em.createQuery(query); 
		List<UserPointsLog> list = typedQuery.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public UserPointsLog save(UserPoints user, PointsRule rule, Integer infoId, String desc, Date date, Double opoints, Double points, Integer isas, Integer logtype){
		UserPointsLog log = new UserPointsLog();
		log.setCaption(desc);
		log.setCreatetime(date);
		log.setIsas(isas);
		log.setOrigPoints(opoints);
		log.setPoints(points);
		log.setUserPoints(user);
		log.setRule(rule);
		log.setInfoId(infoId);
		log.setLogtypeId(logtype);
		return dao.save(log);
	}

	public UserPointsLog save(UserPointsLog bean) {
		return dao.save(bean);
	}

	public UserPointsLog update(UserPointsLog bean) {
		return dao.save(bean);
	}

	public void delete(Integer... ids) {
		if (ids != null) {
			for (Integer id : ids) {
				dao.delete(id);
			}
		}
	}
	/**
	 * 获取最新的某条规则的日志
	 * @param userId
	 * @param ruleCode
	 * @param infoId
	 * @return
	 */
	public UserPointsLog getLastByUser(Long userId, String ruleCode, Integer infoId){
		Map<String, Object> searchParams = new HashMap<String, Object>();
//		List<Long> ulist = new ArrayList<Long>();
//		ulist.add(userId);
		searchParams.put(SpecOperator.EQ + "_userPoints.id", userId);
		searchParams.put(SpecOperator.EQ + "_rule.ruleCode", ruleCode);
		searchParams.put(SpecOperator.EQ + "_infoId", infoId);
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserPointsLog> spec = SpecDynamic.buildSpec(filters.values());
		
		Sort sort = new Sort(Direction.DESC, "createtime");
		Pageable pageable = new PageRequest(0, 1, sort);
		Page<UserPointsLog> page = dao.findAll(spec, pageable);
		if(page.getContent().size() > 0){
			return page.getContent().get(0);
		}else{
			return null;
		}
	}
}
