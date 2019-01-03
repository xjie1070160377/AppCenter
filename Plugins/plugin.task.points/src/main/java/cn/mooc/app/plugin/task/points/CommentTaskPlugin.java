package cn.mooc.app.plugin.task.points;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.event.EventRegistry;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.plugin.PluginDataService;
import cn.mooc.app.core.plugin.support.TaskPlugin;
import cn.mooc.app.module.interact.event.listener.CommentEventListener;
import cn.mooc.app.module.interact.event.listener.UnCommentEventListener;
import cn.mooc.app.module.points.data.entity.CurrencyRule;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.plugin.task.points.service.CurrencyService;
import cn.mooc.app.plugin.task.points.service.PointsService;


@Service
public class CommentTaskPlugin extends TaskPlugin implements CommentEventListener, UnCommentEventListener{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PluginDataService pluginDataService;
	@Autowired
	private PointsService pointsService;
	@Autowired
	private CurrencyService currencyService;
	
	/**
	 * 评论加积分
	 */
	public void comment(OprEvent event, SysContext sysContext) {
		logger.debug("{} 插件收到评论事件通知", CommentTaskPlugin.class.getSimpleName());
		//记录用户登录成功事件		
		pluginDataService.oprEventLog(event, LogType.PointsOpr, "评论");
		
		try {
			Integer infoId = event.getParam("infoId");
			pointsService.updateUserPoints(event.getLoginUser().getUserId(), PointsRule.RULE_COMMENT, infoId, true);
			currencyService.updateUserCurrency(event.getLoginUser().getUserId(), CurrencyRule.COMMENT_CODE, infoId, true);
		} catch (Exception e) {
			logger.error("插件执行异常",e);
		}
	}

	/**
	 * 取消评论减积分
	 */
	public void unComment(OprEvent event, SysContext sysContext) {
		logger.debug("{} 插件收到取消评论事件通知", CommentTaskPlugin.class.getSimpleName());
		//记录用户登录成功事件		
		pluginDataService.oprEventLog(event, LogType.PointsOpr, "取消评论");
		
		try {
			Integer infoId = event.getParam("infoId");
			pointsService.updateUserPoints(event.getLoginUser().getUserId(), PointsRule.RULE_COMMENT, infoId, false);
			currencyService.updateUserCurrency(event.getLoginUser().getUserId(), CurrencyRule.COMMENT_CODE, infoId, false);
		} catch (Exception e) {
			logger.error("插件执行异常",e);
		}
	}
	
	@Override
	public void registryListener(EventRegistry eventRegistry) {
		eventRegistry.registry(this);
		logger.debug("{} 插件开始 监听Task消息事件", CommentTaskPlugin.class.getSimpleName());
	}

	@Override
	public void unRegistryListener(EventRegistry eventRegistry) {
		eventRegistry.unRegistry(this);
		logger.debug("{} 插件取消 监听Task消息事件", CommentTaskPlugin.class.getSimpleName());
	}

	@Override
	public boolean checkCanStart() {
		return true;
	}

	@Override
	public boolean checkCanStop() {
		return true;
	}

}
