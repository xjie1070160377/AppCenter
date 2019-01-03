package cn.mooc.app.plugin.task.points.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.module.points.data.entity.UserPointsLog;
import cn.mooc.app.module.points.service.PointsRuleService;
import cn.mooc.app.module.points.service.UserPointsLogService;
import cn.mooc.app.module.points.service.UserPointsService;

@Service
public class PointsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PointsRuleService ruleService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private UserPointsLogService logService;
	@Autowired
	private UserPointsService userPointsService;
	
	
	/**
	 * 用户积分操作
	 * @param userId	操作用户Id
	 * @param ruleCode	积分规则代码
	 * @param infoId	文章代号
	 * @param flag	执行或者撤销，执行为true，撤销为false,暂时只对评论和点赞有效
	 */
	public void updateUserPoints(Long userId, String ruleCode, Integer infoId, boolean flag){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		String day = format.format(d);
		addPointsDay(ruleCode, userId, infoId, day, d, flag);
	}
	
	private void addPointsDay(String ruleCode, Long userId, Integer infoId, String day, Date d, boolean flag){
		Info info = null;
		String desc = "";
		if(infoId != null){
			info = infoService.getInfoById(infoId);
		}
		UserPointsLog log = null;
		switch(ruleCode){
			case PointsRule.RULE_LOGIN:desc = "每日登录获取积分"; break;
			case PointsRule.RULE_READ: desc = "阅读"+info.getTitle()+"增加积分";
				//同一篇文章只增加一次阅读积分
				log = logService.findLogByUserInfo(userId, infoId, 0, 0, 1);
				if(log != null){
					return;
				}
				break;
			case PointsRule.RULE_SHARE:desc = "分享"+info.getTitle()+"增加积分";break;
			case PointsRule.RULE_DIGGS:
				//被点赞加积分
				updateUserPoints(info.getCreatorId(), PointsRule.RULE_BEDIGGS, infoId, flag);
				if(flag){
					desc = "点赞"+info.getTitle()+"增加积分";break;
				}else{
					desc = "取消点赞"+info.getTitle()+"扣除积分";break;
				}
				
			case PointsRule.RULE_COMMENT:
				//被评论加积分
				updateUserPoints(info.getCreatorId(), PointsRule.RULE_BEDIGGS, infoId ,flag);
				if(flag){
					desc = "评论"+info.getTitle()+"增加积分";break;
				}else{
					desc = "取消评论"+info.getTitle()+"扣除积分";break;
				}
				
			case PointsRule.RULE_BECOMMENT:
				if(flag){
					desc = "被评论"+info.getTitle()+"增加积分";break;
				}else{
					desc = "取消评论，被评论"+info.getTitle()+"扣除积分";break;
				}
			case PointsRule.RULE_BEDIGGS:
				if(flag){
					desc = "被点赞"+info.getTitle()+"增加积分";break;
				}else{
					desc = "取消点赞，被点赞"+info.getTitle()+"扣除积分";break;
				}
		}
		PointsRule rule = null;
		//如果是撤销，根据日志查询当时记录的规则，日志中没有，暂时不做处理
		if(flag){
			if(ruleCode.equals(PointsRule.RULE_COMMENT) || ruleCode.equals(PointsRule.RULE_DIGGS)){
				int haslike = 0, haxcomment = 0;
				if(ruleCode.equals(PointsRule.RULE_COMMENT)){
					haslike = 1;
				}
				if(ruleCode.equals(PointsRule.RULE_DIGGS)){
					haxcomment = 1;
				}
				rule = ruleService.findSpecialRule(infoId, haslike, haxcomment);
				if(rule == null){
					rule = ruleService.findByCode(ruleCode);
				}
			}else{
				rule = ruleService.findByCode(ruleCode);
			}
		}else{
			log = logService.getLastByUser(userId, ruleCode, infoId);
			if(log != null){
				rule = log.getRule();
			}
		}
		//如果没有设置规则，直接退出
		if(rule == null){
			return;
		}
		if(flag){
			userPointsService.addPoints(rule, userId, infoId, day, d, desc);
		}else{
			userPointsService.subPoints(rule, userId, infoId, day, d, desc);
		}
	}
	
	
	
}
