package cn.mooc.app.core.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mooc.app.core.data.entity.SysLogEntity;
import cn.mooc.app.core.enums.LogType;
import cn.mooc.app.core.event.OprEvent;
import cn.mooc.app.core.service.SysDataService;
import cn.mooc.app.core.utils.DateTimeUtil;
import cn.mooc.app.core.web.shiro.model.LoginUserInfo;

@Service
public class PluginDataService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysDataService sysDataService;
	
	
	public void oprEventLog(OprEvent event, LogType logType, String logDesc) {
		//
		logger.info("日志类型：{} 日志描述：{}", logType, logDesc);
		
		//
		long userId =0;
		String userName = "";
		String ip = event.getIpAddr();
		String reqUrl = event.getReqUrl();
		LoginUserInfo loginUser = event.getLoginUser();
		if(loginUser!=null){
			userId = loginUser.getUserId();
			userName = loginUser.getUserName();
			
		}		
		
		//存入MongoDb
		SysLogEntity sysLogEntity = new SysLogEntity();
		sysLogEntity.setUserId(userId);
		sysLogEntity.setUserName(userName);
		sysLogEntity.setLogType(logType);		
		sysLogEntity.setOprPoint(reqUrl);
		sysLogEntity.setUserIp(ip);		
		sysLogEntity.setLogDesc(logDesc);
		sysLogEntity.setCreateTime(DateTimeUtil.getCurrentTime());
		
		this.sysDataService.sysLog(sysLogEntity);
		
	}

	
	public void obtainPoints(long userId, double points) throws Exception {
		//
		this.sysDataService.obtainPoints(userId, points);
	}

	
	public void spendPoints(long userId, double points) throws Exception {
		//
		double available = this.sysDataService.getAvailablePoints(userId);
		if(points<=0){
			throw new Exception("积分消费必须大于0");
		}
		if(available<points){
			throw new Exception("剩余积分不够当前消费");
		}
		
		this.sysDataService.spendPoints(userId, points);
		
	}
	
}
