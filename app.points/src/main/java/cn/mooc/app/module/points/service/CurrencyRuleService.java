package cn.mooc.app.module.points.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.data.entity.Node;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.cms.service.NodeService;
import cn.mooc.app.module.points.data.entity.CurrencyCulNode;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.data.entity.CurrencySpecial;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.entity.UserDayCur;
import cn.mooc.app.module.points.data.entity.UserPoints;
import cn.mooc.app.module.points.data.rds.CurrencyCulNodeRepository;
import cn.mooc.app.module.points.data.rds.CurrencyRuleRepository;
import cn.mooc.app.module.points.data.rds.CurrencySpecialRepository;

/**
 * CurrencyRuleService
 * 获取R币规则服务
 * 
 * @author zjj
 * @date 2016-06-23
 */
@Service
public class CurrencyRuleService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyRuleRepository currencyRuleRepository;
	@Autowired
	private CurrencyCulNodeRepository currencyCulNodeRepository;
	@Autowired
	private CurrencySpecialRepository currencySpecialRepository;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private UserDayCurService dayCurService;
	@Autowired
	private UserPointsService pointsService;
	@Autowired
	private SysDataService userService;
	@Autowired
	private CurrencyLogService logService;
	
	public CurrencyRule getCurrencyRuleById(Integer id) {
		CurrencyRule entity = currencyRuleRepository.findOne(id);
		return entity;
	}
	
	public List<CurrencyRule> getAllCurrencyRules(){
		return this.currencyRuleRepository.findAll();
	}
	
	public Page<CurrencyRule> findCurrencyRulePage(Map<String, Object> searchParams,PagerParam pageParam){
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<CurrencyRule> spec = SpecDynamic.buildSpec(filters.values());

		return currencyRuleRepository.findAll(spec, pageParam.getPageRequest());
	}
	
	
	
	public List<CurrencyRule> findCurrencyRuleByInfo(Info info, String ruleCode){
		Specification<CurrencyRule> spec = specCurrencyRule(info, ruleCode);
		return currencyRuleRepository.findAll(spec);
	}
	
	private Specification<CurrencyRule> specCurrencyRule(final Info info, final String ruleCode) {
		Specification<CurrencyRule> spec = new Specification<CurrencyRule>() {
			public Predicate toPredicate(Root<CurrencyRule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = cb.equal(root.get("ruleCode"), ruleCode);
				pred = cb.and(pred, cb.equal(root.get("status"), CurrencyRule.YX_STATUS));
				
				Subquery<CurrencyCulNode> subquery = query.subquery(CurrencyCulNode.class);
				Root<CurrencyCulNode> root2 = subquery.from(CurrencyCulNode.class);
				Predicate p1 = cb.equal(root.get("id"), root2.get("rule").get("id"));
				p1 = cb.and(p1, cb.equal(root2.get("node").get("id"), info.getNode().getId()));
				subquery.where(p1);
				subquery.select(root2);
				
				pred = cb.and(pred, cb.not(cb.exists(subquery)));
				if(ruleCode.equals(CurrencyRule.DIGGIS_CODE) || ruleCode.equals(CurrencyRule.COMMENT_CODE)){
					Predicate p2 = cb.equal(root.get("ruleCode"), CurrencyRule.SPECIAL_CODE);
					p2 = cb.and(p2, cb.equal(root.join("currencySpecials", JoinType.LEFT).join("info", JoinType.LEFT).get("id"), info.getId()));
//					p2 = cb.and(p2, cb.equal(root.get("currencySpecials").get("info").get("id"), info.getId()));
					pred = cb.or(pred, p2);
//					pred = cb.or(pred, cb.and(cb.equal(root.get("ruleCode"), CurrencyRule.SPECIAL_CODE), cb.equal(root.get("currencySpecials").get("info").get("id"), info.getId())));
					//pred = cb.and(root.get("ruleCode").in(new String[]{CurrencyRule.SPECIAL_CODE, ruleCode}));
				}
				return pred;
			}
		};
		return spec;
	}
	
	@Transactional
	public CurrencyRule saveCurrencyRule(CurrencyRule entity) throws Exception {
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.currencyRuleRepository.save(entity);
	}
	@Transactional
	public CurrencyRule saveCurrencyRule(CurrencyRule entity, Integer[] nodeIds, Integer[] specialIds) throws Exception {
		// TODO Auto-generated method stub
		entity = saveCurrencyRule(entity);
		if(nodeIds != null && nodeIds.length > 0){
			for(Integer nodeId : nodeIds){
				CurrencyCulNode culNode = new CurrencyCulNode();
				culNode.setRule(entity);
				Node node = nodeService.getNodeById(nodeId);
				culNode.setNode(node);
				currencyCulNodeRepository.save(culNode);
			}
		}
		if(specialIds != null && specialIds.length > 0){
			for(Integer specialId : specialIds){
				CurrencySpecial special = new CurrencySpecial();
				special.setCurrencyRule(entity);
				Info info = infoService.getInfoById(specialId);
				special.setInfo(info);
				currencySpecialRepository.save(special);
			}
		}
		return entity;
	}
	@Transactional
	public CurrencyRule updateCurrencyRule(CurrencyRule entity) throws Exception{
		entity.applyDefaultValue();
		//if(StringUtils.isEmpty(entity.getName())){
		//	throw new SaveFailureException("名称不能为空.");
		//}
		return this.currencyRuleRepository.save(entity);
	}
	@Transactional
	public CurrencyRule updateCurrencyRule(CurrencyRule entity, Integer[] nodeIds, Integer[] specialIds) throws Exception{
		entity = updateCurrencyRule(entity);
		currencyCulNodeRepository.deleteByRuleId(entity.getId());
		currencySpecialRepository.deleteByCurrencyRuleId(entity.getId());
		if(nodeIds != null && nodeIds.length > 0){
			for(Integer nodeId : nodeIds){
				CurrencyCulNode culNode = new CurrencyCulNode();
				culNode.setRule(entity);
				Node node = nodeService.getNodeById(nodeId);
				culNode.setNode(node);
				currencyCulNodeRepository.save(culNode);
			}
		}
		if(specialIds != null && specialIds.length > 0){
			for(Integer specialId : specialIds){
				CurrencySpecial special = new CurrencySpecial();
				special.setCurrencyRule(entity);
				Info info = infoService.getInfoById(specialId);
				special.setInfo(info);
				currencySpecialRepository.save(special);
			}
		}
		return entity;
	}
	
	@Transactional
	public boolean delCurrencyRule(Integer id){
		try{
			this.currencyRuleRepository.delete(id);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public int delCurrencyRules(Integer[] ids){
		try{
			List<CurrencyRule> entities = this.currencyRuleRepository.findAll(Arrays.asList(ids));
			this.currencyRuleRepository.delete(entities);
			return entities.size();
		}catch(Exception e){
			logger.error(e.getMessage());
			return 0;
		}
	}
	//判断剔除关系放在外面的逻辑
	@Transactional
	public void addCurrency(CurrencyRule rule, String code, Long userId, Integer infoId, String day, Date d, String desc) throws Exception{
		Double total = rule.getTotal();
		Double maxTotal = rule.getMaxTotal();
		UserDayCur daycur = dayCurService.findByUserTime(userId, rule.getId(), day);
		UserPoints userPoints = pointsService.get(userId);
		Double currency = userPoints.getCurrency();
		if(currency == null){
			currency = 0D;
		}
		if(total+currency < 0){
			return;
		}
		SysUserEntity user = userService.getUserInfoById(userId);
		if(daycur == null){
			daycur = dayCurService.findByUser(userId, rule.getId());
			if(daycur == null){
				daycur = new UserDayCur();
				daycur.setLastTime(d);
				daycur.setTotal(total);
				daycur.setCurrencyRule(rule);
				daycur.setUserId(userId);
				dayCurService.saveUserDayCur(daycur);
			}else{
				daycur.setTotal(total);
				daycur.setLastTime(d);
				dayCurService.saveUserDayCur(daycur);
			}
			logService.save(userId, code, rule, null, null, null, infoId, desc, d, currency, total);
			
			userPoints.setCurrency(currency+total);
			userPoints = pointsService.save(userPoints);
		}else{
			Double omaxtotal = daycur.getTotal();
			if(maxTotal.doubleValue() == 0 || maxTotal.doubleValue() >= total.doubleValue()+omaxtotal.doubleValue()){
				daycur.setTotal(total.doubleValue()+omaxtotal.doubleValue());
				dayCurService.saveUserDayCur(daycur);
				
				logService.save(userId, code, rule, null, null, null, infoId, desc, d, currency, total);
				userPoints.setCurrency(currency+total);
				userPoints = pointsService.save(userPoints);
			}
		}
	}
	
	@Transactional
	public void subCurrency(CurrencyRule rule, String code, Long userId, Integer infoId, String day, Date d, String desc) throws Exception{
		Double total = rule.getTotal()*-1;
		Double maxTotal = rule.getMaxTotal();
		UserDayCur daycur = dayCurService.findByUserTime(userId, rule.getId(), day);
		UserPoints userPoints = pointsService.get(userId);
		Double currency = userPoints.getCurrency();
		
		if(total+currency < 0){
			return;
		}
		SysUserEntity user = userService.getUserInfoById(userId);
		if(daycur == null){
			daycur = dayCurService.findByUser(userId, rule.getId());
			if(daycur == null){
				daycur = new UserDayCur();
				daycur.setLastTime(d);
				daycur.setTotal(total);
				daycur.setCurrencyRule(rule);
				daycur.setUserId(userId);
				dayCurService.saveUserDayCur(daycur);
			}else{
				daycur.setTotal(total);
				daycur.setLastTime(d);
				dayCurService.saveUserDayCur(daycur);
			}
			logService.save(userId, code, rule, null, null, null, infoId, desc, d, currency, total);
			
			userPoints.setCurrency(currency+total);
			userPoints = pointsService.save(userPoints);
		}else{
			Double omaxtotal = daycur.getTotal();
			daycur.setTotal(total.doubleValue()+omaxtotal.doubleValue());
			dayCurService.saveUserDayCur(daycur);
			
			logService.save(userId, code, rule, null, null, null, infoId, desc, d, currency, total);
			userPoints.setCurrency(currency+total);
			userPoints = pointsService.save(userPoints);
		}
	}
	
	public CurrencyRule findByCode(String code){
		return currencyRuleRepository.findByCode(code);
	}
	
	public List<CurrencyRule> findValidRule(){
		return currencyRuleRepository.findValidRule();
	}

}
