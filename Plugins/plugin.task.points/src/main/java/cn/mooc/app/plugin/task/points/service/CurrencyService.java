package cn.mooc.app.plugin.task.points.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.module.cms.data.entity.Info;
import cn.mooc.app.module.cms.service.InfoService;
import cn.mooc.app.module.points.data.entity.CurrencyLog;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.service.CurrencyLogService;
import cn.mooc.app.module.points.service.CurrencyRuleService;

@Service
public class CurrencyService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurrencyRuleService ruleService;
	
	@Autowired
	private InfoService infoService;
	
	@Autowired
	private CurrencyLogService logService;

	public void updateUserCurrency(Long userId, String ruleCode, Integer infoId, boolean flag) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		String day = format.format(d);
		addCurrencyDay(ruleCode, userId, infoId, day, d, flag);
	}
	
	private void addCurrencyDay(String ruleCode, Long userId, Integer infoId, String day, Date d, boolean flag) throws Exception{
		Info info = null;
		String desc = "";
		List<CurrencyRule> ls = null;
		if(infoId != null){
			info = infoService.getInfoById(infoId);
			 ls = ruleService.findCurrencyRuleByInfo(info, ruleCode);
			if(ls == null || ls.size() == 0){
				return;
			}
		}
		CurrencyLog log = null;
		switch(ruleCode){
			case CurrencyRule.LOGIN_CODE : desc = "每日登录获取R币"; break;
			case CurrencyRule.REGISTER_CODE : desc="注册获取R币";break;
			case CurrencyRule.PUBLISH_CODE : desc="发布作品【"+info.getTitle()+"】获取R币";break;
			case CurrencyRule.DIGGIS_CODE:
				//被点赞加积分
				updateUserCurrency(info.getCreatorId(), CurrencyRule.BEEN_CODE, infoId, flag);
				if(flag){
					desc = "点赞"+info.getTitle()+"增加R币";break;
				}else{
					desc = "取消点赞"+info.getTitle()+"扣除R币";break;
				}
				
			case CurrencyRule.COMMENT_CODE:
				//被评论加积分
				updateUserCurrency(info.getCreatorId(), CurrencyRule.BEEN_CODE, infoId ,flag);
				if(flag){
					desc = "评论"+info.getTitle()+"增加R币";break;
				}else{
					desc = "取消评论"+info.getTitle()+"扣除R币";break;
				}
				
			case CurrencyRule.BEEN_CODE:
				if(flag){
					desc = "被评论/点赞"+info.getTitle()+"增加R币";break;
				}else{
					desc = "取消评论/点赞"+info.getTitle()+"扣除R币";break;
				}
			case CurrencyRule.DOWN_CODE : desc="邀请好友下载APK加R币";break;
			case CurrencyRule.ERROR_CODE : desc="后台报错上报加R币";break;
		}
		CurrencyRule rule = null;
		//如果是撤销，根据日志查询当时记录的规则，日志中没有，暂时不做处理
		if(!flag){
			log = logService.findLogByUserInfo(userId, infoId, ruleCode);
			if(log != null){
				rule = log.getCurrencyRule();
			}
		}else{
			if(ruleCode.equals(CurrencyRule.DIGGIS_CODE) || ruleCode.equals(CurrencyRule.COMMENT_CODE)){
				for(CurrencyRule r : ls){
					if(r.getIsspecial() == 1){
						rule = r;
						break;
					}
				}
				if(rule == null){
					rule = ls.get(0);
				}
			}else{
				rule = ruleService.findByCode(ruleCode);
			}
		}
		//如果没有设置规则，直接退出
		if(rule == null){
			return;
		}
		if(flag){
			ruleService.addCurrency(rule, ruleCode, userId, infoId, day, d, desc);
		}else{
			ruleService.subCurrency(rule, ruleCode, userId, infoId, day, d, desc);
		}
	}
}
