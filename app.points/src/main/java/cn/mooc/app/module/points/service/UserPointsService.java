package cn.mooc.app.module.points.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.transaction.annotation.Transactional;

import cn.mooc.app.core.data.entity.SysUserEntity;
import cn.mooc.app.core.data.specifications.SpecDynamic;
import cn.mooc.app.core.data.specifications.SpecExpression;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.StringUtil;
import cn.mooc.app.core.web.model.PagerParam;
import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.entity.UserDayPoints;
import cn.mooc.app.module.points.data.entity.UserPoints;
import cn.mooc.app.module.points.data.entity.UserPointsLog;
import cn.mooc.app.module.points.data.rds.UserPointsRepository;
@Service
@Transactional
public class UserPointsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserPointsRepository dao;
	
	@Autowired
	private UserDayPointsService dayService;
	@Autowired
	private PointsRuleService ruleService;
	@Autowired
	private SysDataService userService;
	@Autowired
	private PointsLevelService levelService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private UserPointsLogService logService;
	
	public Page<UserPoints> findAll(Map<String, Object> searchParams, PagerParam pageParam) {
		String userName = "";
		if(searchParams.containsKey("EQ_user.userName")){
			userName = StringUtil.strnull(searchParams.get("EQ_user.userName"));
			searchParams.remove("EQ_user.userName");
		}
		Map<String, SpecExpression> filters = SpecExpression.parse(searchParams);
		Specification<UserPoints> spec = SpecDynamic.buildSpec(filters.values());
		spec = specFun(spec, userName);
		return dao.findAll(spec, pageParam.getPageRequest());
	}
	
	public Specification specFun(final Specification<UserPoints> fsp, final String userName){
		Specification<UserPoints> spec = new Specification<UserPoints>() {
			public Predicate toPredicate(Root<UserPoints> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				if(StringUtil.isNotEmpty(userName)){
					Subquery<SysUserEntity> subquery = query.subquery(SysUserEntity.class);
					Root<SysUserEntity> subroot = subquery.from(SysUserEntity.class);
					Predicate p = cb.and(cb.equal(subroot.get("id"), root.get("id")), cb.like(subroot.get("userName"), userName));
					subquery.where(p).select(subroot);
					pred = cb.and(pred, cb.exists(subquery));
				}
				return pred;
			}
		};
		return spec;
	}
	
	public List<UserPoints> findList() {
		return dao.findAll();
	}
	
	public UserPoints get(Long id) {
		UserPoints p = dao.findOne(id);
		if(p == null){
			p = new UserPoints();
			p.setPoints(0D);
			p.setCurrency(0D);
			p.setLevel(levelService.findByScore(0D));
			p.setId(id);
			p = dao.save(p);
		}
		return p;
	}
	
	public UserPoints save(UserPoints bean) {
		return dao.save(bean);
	}
	/**
	 * 用户积分操作
	 * @param userId	操作用户Id
	 * @param ruleCode	积分规则代码
	 * @param infoId	文章代号
	 * @param flag	执行或者撤销，执行为true，撤销为false,暂时只对评论和点赞有效
	 */
//	public void updateUserPoints(Long userId, String ruleCode, Integer infoId, boolean flag){
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date d = new Date();
//		String day = format.format(d);
//		addPointsDay(ruleCode, userId, infoId, day, d, flag);
//	}
	
//	private void addPointsDay(String ruleCode, Long userId, Integer infoId, String day, Date d, boolean flag){
//		Info info = null;
//		String desc = "";
//		if(infoId != null){
//			info = infoService.getInfoById(infoId);
//		}
//		UserPointsLog log = null;
//		switch(ruleCode){
//			case PointsRule.RULE_LOGIN:desc = "每日登录获取积分"; break;
//			case PointsRule.RULE_READ: desc = "阅读"+info.getTitle()+"增加积分";
//				//同一篇文章只增加一次阅读积分
//				log = logService.findLogByUserInfo(userId, infoId, 0, 0, 1);
//				if(log != null){
//					return;
//				}
//				break;
//			case PointsRule.RULE_SHARE:desc = "分享"+info.getTitle()+"增加积分";break;
//			case PointsRule.RULE_DIGGS:
//				//被点赞加积分
//				updateUserPoints(info.getCreatorId(), PointsRule.RULE_BEDIGGS, infoId, flag);
//				if(flag){
//					desc = "点赞"+info.getTitle()+"增加积分";break;
//				}else{
//					desc = "取消点赞"+info.getTitle()+"扣除积分";break;
//				}
//				
//			case PointsRule.RULE_COMMENT:
//				//被评论加积分
//				updateUserPoints(info.getCreatorId(), PointsRule.RULE_BEDIGGS, infoId ,flag);
//				if(flag){
//					desc = "评论"+info.getTitle()+"增加积分";break;
//				}else{
//					desc = "取消评论"+info.getTitle()+"扣除积分";break;
//				}
//				
//			case PointsRule.RULE_BECOMMENT:
//				if(flag){
//					desc = "被评论"+info.getTitle()+"增加积分";break;
//				}else{
//					desc = "取消评论，被评论"+info.getTitle()+"扣除积分";break;
//				}
//			case PointsRule.RULE_BEDIGGS:
//				if(flag){
//					desc = "被点赞"+info.getTitle()+"增加积分";break;
//				}else{
//					desc = "取消点赞，被点赞"+info.getTitle()+"扣除积分";break;
//				}
//		}
//		PointsRule rule = null;
//		//如果是撤销，根据日志查询当时记录的规则，日志中没有，暂时不做处理
//		if(flag){
//			if(ruleCode.equals(PointsRule.RULE_COMMENT) || ruleCode.equals(PointsRule.RULE_DIGGS)){
//				int haslike = 0, haxcomment = 0;
//				if(ruleCode.equals(PointsRule.RULE_COMMENT)){
//					haslike = 1;
//				}
//				if(ruleCode.equals(PointsRule.RULE_DIGGS)){
//					haxcomment = 1;
//				}
//				rule = ruleService.findSpecialRule(infoId, haslike, haxcomment);
//				if(rule == null){
//					rule = ruleService.findByCode(ruleCode);
//				}
//			}else{
//				rule = ruleService.findByCode(ruleCode);
//			}
//		}else{
//			log = logService.getLastByUser(userId, ruleCode, infoId);
//			if(log != null){
//				rule = log.getRule();
//			}
//		}
//		//如果没有设置规则，直接退出
//		if(rule == null){
//			return;
//		}
//		if(flag){
//			addPoints(rule, userId, infoId, day, d, desc);
//		}else{
//			subPoints(rule, userId, infoId, day, d, desc);
//		}
//	}
	public void subPoints(PointsRule rule, Long userId, Integer infoId, String day, Date d, String desc){
		Double points = rule.getIsas()*rule.getPoints()*-1;
		UserDayPoints userDayPoints = dayService.findByUserTime(userId, rule.getId(), day);
		UserPoints userPoints = this.get(userId);
		Double opoints = userPoints.getPoints();
		//积分小于0 不操作
		if(opoints+points < 0){
			return;
		}
		if(userDayPoints == null){
			userDayPoints = dayService.findByUser(userId, rule.getId());
			if(userDayPoints == null){
				userDayPoints = new UserDayPoints();
				userDayPoints.setLastTime(d);
				userDayPoints.setPoints(points);
				userDayPoints.setUserPoints(userPoints);
				userDayPoints.setRule(rule);
				userDayPoints.setOpnum(-1);
				dayService.save(userDayPoints);
				
				logService.save(userPoints, rule, infoId, desc, d, opoints, points, rule.getIsas(), UserPointsLog.TYPE_RULE);
				
				userPoints.setPoints(opoints+points);
				userPoints.setLevel(levelService.findByScore(userPoints.getPoints()));
				userPoints = dao.save(userPoints);
				
				
			}else{
				userDayPoints.setPoints(points);
				userDayPoints.setLastTime(d);
				userDayPoints.setOpnum(-1);
				dayService.save(userDayPoints);
				
				
				logService.save(userPoints, rule, infoId, desc, d, opoints, points, rule.getIsas(), UserPointsLog.TYPE_RULE);
				
				userPoints.setPoints(opoints+points);
				userPoints.setLevel(levelService.findByScore(userPoints.getPoints()));
				dao.save(userPoints);
			}
		}else{
			userDayPoints.setPoints(points.doubleValue()+userDayPoints.getPoints());
			userDayPoints.setOpnum(userDayPoints.getOpnum()-1);
			dayService.save(userDayPoints);
			
			logService.save(userPoints, rule, infoId, desc, d, opoints, points, rule.getIsas(), UserPointsLog.TYPE_RULE);
			userPoints.setPoints(opoints+points);
			userPoints.setLevel(levelService.findByScore(userPoints.getPoints()));
			dao.save(userPoints);
		}
	}
	
	public void addPoints(PointsRule rule, Long userId, Integer infoId, String day, Date d, String desc){
		Double points = rule.getIsas()*rule.getPoints();
		Double maxPoints = rule.getMaxPoints()==null?0:rule.getMaxPoints();
		Integer opnum = rule.getMaxOpnum()==null?0:rule.getMaxOpnum();
		UserDayPoints userDayPoints = dayService.findByUserTime(userId, rule.getId(), day);
		UserPoints userPoints = this.get(userId);
		Double opoints = userPoints.getPoints();
		//积分小于0 不操作
		if(opoints+points < 0){
			return;
		}
		
		if(userDayPoints == null){
			userDayPoints = dayService.findByUser(userId, rule.getId());
			if(userDayPoints == null){
				userDayPoints = new UserDayPoints();
				userDayPoints.setLastTime(d);
				userDayPoints.setPoints(points);
				userDayPoints.setUserPoints(userPoints);
				userDayPoints.setRule(rule);
				userDayPoints.setOpnum(1);
				dayService.save(userDayPoints);
				
				logService.save(userPoints, rule, infoId, desc, d, opoints, points, rule.getIsas(), UserPointsLog.TYPE_RULE);
				
				userPoints.setPoints(opoints+points);
				userPoints.setLevel(levelService.findByScore(userPoints.getPoints()));
				userPoints = dao.save(userPoints);
				
				
			}else{
				userDayPoints.setPoints(points);
				userDayPoints.setLastTime(d);
				userDayPoints.setOpnum(1);
				dayService.save(userDayPoints);
				
				
				logService.save(userPoints, rule, infoId, desc, d, opoints, points, rule.getIsas(), UserPointsLog.TYPE_RULE);
				
				userPoints.setPoints(opoints+points);
				userPoints.setLevel(levelService.findByScore(userPoints.getPoints()));
				dao.save(userPoints);
			}
		}else{
			Double omax = userDayPoints.getPoints();
			Integer maxopnum = userDayPoints.getOpnum();
			if((maxPoints.doubleValue()==0 || maxPoints.doubleValue()>=points.doubleValue()+omax.doubleValue()) && (opnum.intValue() == 0 || opnum.intValue()>=maxopnum.intValue()+1)){
				userDayPoints.setPoints(points.doubleValue()+omax.doubleValue());
				userDayPoints.setOpnum(userDayPoints.getOpnum()+1);
				dayService.save(userDayPoints);
				
				logService.save(userPoints, rule, infoId, desc, d, opoints, points, rule.getIsas(), UserPointsLog.TYPE_RULE);
				userPoints.setPoints(opoints+points);
				userPoints.setLevel(levelService.findByScore(userPoints.getPoints()));
				dao.save(userPoints);
			}
		}
	}
	
	
	public UserPoints update(UserPoints bean) {
		return dao.save(bean);
	}
	
	public void delete(Long... ids) {
		if (ids != null) {
			for (Long id : ids) {
				dao.delete(id);
			}
		}
	}
}
