package cn.mooc.app.module.points.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysRoleEntity;
import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.WorkflowProcess;
import cn.mooc.app.module.points.data.entity.CurrencyLog;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.data.entity.DrawRule;
import cn.mooc.app.module.points.data.entity.ExchangeGood;
import cn.mooc.app.module.points.data.entity.ExchangeRule;
import cn.mooc.app.module.points.data.entity.PointsInfo;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.entity.UserPointsLog;
import cn.mooc.app.module.points.data.rds.CurrencyLogRepository;

/**
 * CurrencyLogService
 * R币获取兑换日志控制器服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class CurrencyLogService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyLogRepository currencyLogRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	public CurrencyLog getCurrencyLogById(Integer id) {
		CurrencyLog entity = currencyLogRepository.findOne(id);
		return entity;
	}
	
	public List<CurrencyLog> getAllCurrencyLogs(){
		return this.currencyLogRepository.findAll();
	}
	
	public Page<CurrencyLog> findCurrencyLogPage(Map<String, Object> searchParams,PagerParam pageParam, String userName){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CurrencyLog> spec = SpecDynamic.buildSpec(filters.values());
		spec = specCurrencyLog(spec, userName);
		return currencyLogRepository.findAll(spec, pageParam.getPageRequest());
		
	}
	
	private Specification<CurrencyLog> specCurrencyLog(final Specification<CurrencyLog> fsp, final String userName) {
		Specification<CurrencyLog> spec = new Specification<CurrencyLog>() {
			public Predicate toPredicate(Root<CurrencyLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if(StringUtil.isNotEmpty(userName)){
					Subquery<SysUserEntity> subquery =  query.subquery(SysUserEntity.class);
					Root<SysUserEntity> subroot = subquery.from(SysUserEntity.class);
					subquery.where(cb.equal(root.get("userId"), subroot.get("id")), cb.like((Path)subroot.get("userName"), userName));
					subquery.select(subroot);
					pred = cb.and(pred, cb.exists(subquery));
				}
				
				return pred;
			}
		};
		return spec;
	}
	
	@Transactional
	public CurrencyLog saveCurrencyLog(CurrencyLog entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.currencyLogRepository.save(entity);
	}

	@Transactional
	public CurrencyLog save(Long userId, String ruleCode, CurrencyRule currencyRule, ExchangeRule changeRule, ExchangeGood changGoods, DrawRule drawRule, Integer dataId, String desc, Date date, Double ototal, Double total) throws Exception{
		CurrencyLog log = new CurrencyLog();
		log.applyDefaultValue();
		log.setUserId(userId);
		log.setRuleCode(ruleCode);
		log.setDataId(dataId);
		log.setCurrencyRule(currencyRule);
		log.setExchangeRule(changeRule);
		log.setExchangeGood(changGoods);
		log.setDrawRule(drawRule);
		log.setCreatetime(date);
		log.setQuantity(total);
		log.setRemain(ototal+total);
		log.setCaption(desc);
		
		return currencyLogRepository.save(log);
	}
	
	@Transactional
	public CurrencyLog updateCurrencyLog(CurrencyLog entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.currencyLogRepository.save(entity);
	}
	
	@Transactional
	public boolean delCurrencyLog(Integer id){
		try{
			this.currencyLogRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCurrencyLogs(Integer[] ids){
		try{
			List<CurrencyLog> entities = this.currencyLogRepository.findAll(Arrays.asList(ids));
			this.currencyLogRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	public CurrencyLog findLogByUserInfo(Long userId, Integer infoId, String ruleCode){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CurrencyLog> query = cb.createQuery(CurrencyLog.class);
		Root<CurrencyLog> root = query.from(CurrencyLog.class);
		
		
		Predicate where = cb.isNotNull(root.get("currencyRule").get("id"));
		where = cb.and(where, cb.equal(root.get("userId"), userId), cb.equal(root.get("dataId"), infoId), cb.equal(root.get("ruleCode"), ruleCode));
		
		
		
		query.where(where);
		
		query.select(root).distinct(false);
		query.orderBy(cb.desc(root.get("createtime")));
		TypedQuery<CurrencyLog> typedQuery = em.createQuery(query); 
		List<CurrencyLog> list = typedQuery.getResultList();
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
