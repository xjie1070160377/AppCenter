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
import cn.mooc.app.module.cms.event.listener.ReadEventListener;
import cn.mooc.app.module.points.data.entity.PointsRule;
import cn.mooc.app.plugin.task.points.service.PointsService;


@Service
public class ReadTaskPlugin extends TaskPlugin implements ReadEventListener{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PluginDataService pluginDataService;
	@Autowired
	private PointsService pointsService;

	@Override
	public void read(OprEvent event, SysContext sysContext) {
		logger.debug("{} 插件收到评论事件通知", ReadTaskPlugin.class.getSimpleName());
		//记录用户登录成功事件		
		pluginDataService.oprEventLog(event, LogType.PointsOpr, "阅读文章");
		
		try {
			Integer infoId = event.getParam("infoId");
			pointsService.updateUserPoints(event.getLoginUser().getUserId(), PointsRule.RULE_READ, infoId, true);
		} catch (Exception e) {
			logger.error("插件执行异常",e);
		}
	}
	
	@Override
	public void registryListener(EventRegistry eventRegistry) {
		eventRegistry.registry(this);
		logger.debug("{} 插件开始 监听Task消息事件", ReadTaskPlugin.class.getSimpleName());
	}

	@Override
	public void unRegistryListener(EventRegistry eventRegistry) {
		eventRegistry.unRegistry(this);
		logger.debug("{} 插件取消 监听Task消息事件", ReadTaskPlugin.class.getSimpleName());
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
