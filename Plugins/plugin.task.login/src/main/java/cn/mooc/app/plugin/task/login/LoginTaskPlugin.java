package cn.mooc.app.plugin.task.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.context.SysContext;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.event.EventRegistry;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.event.listener.LoginEventListener;
import cn.mooc.app.core.plugin.PluginDataService;
import cn.mooc.app.core.plugin.support.TaskPlugin;
import cn.mooc.app.plugin.task.login.service.TestTransactionalService;

@Service
public class LoginTaskPlugin extends TaskPlugin implements LoginEventListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PluginDataService pluginDataService;
	@Autowired
	private TestTransactionalService testBssService;
	
	@Override
	public void registryListener(EventRegistry eventRegistry) {
		//
		eventRegistry.registry(this);
		logger.debug("{} 插件开始 监听Task消息事件", LoginTaskPlugin.class.getSimpleName());
	}
	
	@Override
	public void unRegistryListener(EventRegistry eventRegistry) {
		//
		eventRegistry.unRegistry(this);
		logger.debug("{} 插件取消 监听Task消息事件", LoginTaskPlugin.class.getSimpleName());
	}
	

	@Override
	public void logined(OprEvent event, SysContext sysContext) {
		logger.debug("{} 插件收到登录事件通知", LoginTaskPlugin.class.getSimpleName());

		//记录用户登录成功事件		
		pluginDataService.oprEventLog(event, LogType.UserLogin, "用户登录成功");
		
		testBssService.test();
		
		//计算积分逻辑
		//如果有积分计算逻辑，可在这里实现
		double points = this.getPluginInfo().getDataDouble("Points", 5);
		
		try {
			pluginDataService.obtainPoints(event.getLoginUser().getUserId(), points);
		} catch (Exception e) {
			logger.error("插件执行异常",e);
		}
		
	}

	@Override
	public boolean checkCanStart() {
		//
		
		return true;
	}

	@Override
	public boolean checkCanStop() {
		//
		return true;
	}





}
